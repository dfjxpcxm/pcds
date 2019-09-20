package com.shuhao.clean.apps.flow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.flow.dao.FlowDao;
import com.shuhao.clean.apps.flow.service.FlowService;
import com.shuhao.clean.apps.meta.entity.UppTmplate;
import com.shuhao.clean.base.BaseService;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.RemoteShellTool;
import com.shuhao.clean.utils.UID;
import com.shuhao.clean.utils.exttree.ExtTreeNode;
import com.shuhao.clean.utils.exttree.ExtTreeUtils;

@Service(value="flowService")
public class FlowServiceImpl extends BaseService implements FlowService {
	
	@Autowired
	private FlowDao flowDao;

	public List<Map<String, Object>> flowList(Map<String, Object> param) throws Exception{
		return this.flowDao.flowList(param);
	}

	public void addFlowStatus(Map<String, Object> param) throws Exception {
		this.flowDao.addFlowStatus(param);
	}

	public void delFlow(Map<String, Object> param) throws Exception {
		this.flowDao.delFlow(param);
	}

	public void updateFlow(Map<String, Object> param) throws Exception {
		this.flowDao.updateFlow(param);
	}

	public List<Map<String, Object>> getFlowById(Map<String, Object> param)
			throws Exception {
		return this.flowDao.getFlowById(param);
	}

	@SuppressWarnings("unchecked")
	
	public List<? extends Object> queryUppEnterType() throws Exception {
		return toLowerMapList((List<Map<String, Object>>) this.flowDao.queryUppEnterType());
	}

	
	public void addBlmb(Map<String, Object> param) throws Exception {
		this.flowDao.addBlmb(param);
		String template_type_code = (String)param.get("template_type_cd");
		if("02".equals(template_type_code)){
			this.flowDao.addBlmbPage(param);//添加补录模板与表关系
		}
		String flow_tmpl_id = GlobalUtil.getStringValue(param, "flow_tmpl_id");
		if(GlobalUtil.isNotNull(flow_tmpl_id)){
			this.flowDao.addblmbFlow(param);//添加补录模板与流程模板关系
		}
	}

	
	public void delBlmb(Map<String, Object> param) throws Exception {
		this.flowDao.delBlmb(param);
//		String template_type_code = (String)param.get("template_type_code");
//		if("02".equals(template_type_code)){
			this.flowDao.delBlmbPage(param);//删除补录模板与表关系
			this.flowDao.delBlmbFlow(param);//删除补录模板与流程模板关系
//		}
	}

	
	public void updateBlmb(Map<String, Object> param) throws Exception {
		this.flowDao.updateBlmb(param);
		String template_type_cd = (String)param.get("template_type_cd");
		String flow_tmpl_id = GlobalUtil.getStringValue(param, "flow_tmpl_id");
		this.flowDao.delBlmbPage(param);//删除补录模板与表关系
		this.flowDao.delBlmbFlow(param);//删除补录模板与流程模板关系
		if("02".equals(template_type_cd)){
			this.flowDao.addBlmbPage(param);//添加补录模板与表关系
			if(GlobalUtil.isNotNull(flow_tmpl_id)){
				this.flowDao.addblmbFlow(param);//添加补录模板与流程模板关系
			}
		}
	}

	
	public List<Map<String, Object>> findBlmbById(Map<String, Object> param)
			throws Exception {
		return toLowerMapList(this.flowDao.findBlmbById(param));
	}

	
	public List<Map<String, Object>> listAllTable() throws Exception {
		return toLowerMapList(this.flowDao.listAllTable());
	}

	
	public List<Map<String, Object>> nodeTableList(Map<String, Object> param) throws Exception {
		return toLowerMapList(this.flowDao.nodeTableList(param));
	}

	
	public List<Map<String, Object>> nodeGnList(Map<String, Object> param)
			throws Exception {
		return toLowerMapList(this.flowDao.nodeGnList(param));
	}
	
	public List<Map<String, Object>> getApplyList(Map<String, Object> param)
			throws Exception {
		return toLowerMapList(this.flowDao.getApplyList(param));
	}
	
	public void applyFlow(Map<String, Object> param) throws Exception {
		this.flowDao.applyFlow(param);
	}

	
	public void approveFlow(Map<String, Object> param) throws Exception {
		String approve_role = GlobalUtil.getStringValue(param, "approve_role");
		String approve_status = GlobalUtil.getStringValue(param, "approve_status");//01 通过  02  退回
		if("01".equals(approve_role)){
			param.put("status_code", "01".equals(approve_status)?"04":"10");
			this.flowDao.reApprove(param);
		}else{
			param.put("status_code", "01".equals(approve_status)?"11":"10");
			this.flowDao.resultApprove(param);
		}
	}

	
	public List<Map<String, Object>> listAllFlowTmpl() throws Exception {
		return toLowerMapList(this.flowDao.listAllFlowTmpl());
	}

	
	public List<Map<String, Object>> getApproveList(Map<String, Object> param)
			throws Exception {
		return toLowerMapList(this.flowDao.getApproveList(param));
	}

	
	public int getApproveListCount(Map<String, Object> param) throws Exception {
		return this.flowDao.getApproveListCount(param);
	}
	
	public int getApplyListCount(Map<String, Object> param) throws Exception {
		return this.flowDao.getApplyListCount(param);
	}

	
	public void addApproTrans(Map<String, Object> param) throws Exception {
		this.flowDao.addApproTrans(param);
	}

	
	public void updateApproStatus(Map<String, Object> param) throws Exception {
		this.flowDao.updateApproStatus(param);
		this.flowDao.addApproTrans(param);
	}
	
	public ExtTreeNode getMyTmpl(Map<String, Object> param)throws Exception{
		List<UppTmplate> tmpls = flowDao.getAllTmpl(param);
		return ExtTreeUtils.listProjectTree(tmpls, "根节点");
	}

	/**
	 * 总分差额校验，需要修改：默认产品号对应总账科目
	 */
	public String queryTotalCe(Map<String, Object> param) throws Exception{
		String table_name = this.flowDao.getTableName(param);
		String product_id = this.flowDao.getProductId(param);
		if(GlobalUtil.isNull(table_name)||GlobalUtil.isNull(product_id)){
			return "";
		}else{
			
			param.put("table_name", table_name);
			param.put("product_id", product_id);
			return this.flowDao.queryTotalCe(param);
		}
	}

	public List<Map<String, Object>> getBlmbByUserId(Map<String, Object> param)
			throws Exception {
		return toLowerMapList(this.flowDao.getBlmbByUserId(param));
	}

	public List<Map<String, Object>> listBlmb() throws Exception {
		return toLowerMapList(this.flowDao.listBlmb());
	}

	public void addUserBlmb(Map<String, Object> param) throws Exception {
		this.flowDao.delUserBlmb(param);
		String cust_mgr_id = GlobalUtil.getStringValue(param, "cust_mgr_id");
		String user_tmpls = GlobalUtil.getStringValue(param, "user_tmpl");
		String[] user_tmpls_s = user_tmpls.split(",");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < user_tmpls_s.length; i++) {
			Map<String,Object> row = new HashMap<String, Object>();
			row.put("cust_mgr_id", cust_mgr_id);
			row.put("tmpl_id", user_tmpls_s[i]);
			list.add(row);
		}
		this.flowDao.addUserBlmb(list);
	}
	
	public void addUserBlmb2(Map<String, Object> param) throws Exception {
		String infos = GlobalUtil.getStringValue(param, "infos");
		String[] info = infos.split("@");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < info.length; i++) {
			Map<String,Object> row = new HashMap<String, Object>();
			row.put("cust_mgr_id", info[i].split(",")[0]);
			row.put("user_name", info[i].split(",")[1]);
			row.put("tmpl_id", info[i].split(",")[2]);
			row.put("tmpl_name", info[i].split(",")[3]);
//			row.put("tmpl_id", user_tmpls_s[i]);
			list.add(row);
		}
		this.flowDao.addUserBlmb2(list);
	}

	
	public List<Map<String, Object>> getUserByBlmb(Map<String, Object> param)
			throws Exception {
		return toLowerMapList(this.flowDao.getUserByBlmb(param));
	}

	
	public List<Map<String, Object>> getUserList(Map<String, Object> param)
			throws Exception {
		return toLowerMapList(this.flowDao.getUserList(param));
	}

	
	public int getUserListCount(Map<String, Object> param) throws Exception {
		return this.flowDao.getUserListCount(param);
	}

	
	public void removeUser(Map<String, Object> param) throws Exception {
		String infos = GlobalUtil.getStringValue(param, "infos");
		String[] info = infos.split("@");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < info.length; i++) {
			Map<String,Object> row = new HashMap<String, Object>();
			row.put("cust_mgr_id", info[i].split(",")[0]);
			row.put("tmpl_id", info[i].split(",")[1]);
			list.add(row);
		}
		this.flowDao.removeUser(list);
	}

	
	public int getUserByBlmbCount(Map<String, Object> param) throws Exception {
		return this.flowDao.getUserByBlmbCount(param);
	}

	public void saveJsonData(Map<String, Object> param) throws Exception {
		
		List<Object> list = new ArrayList<Object>();
		
		String jsondata = GlobalUtil.getStringValue(param, "jsondata");
		
		JSONObject dataJson=new JSONObject(jsondata);
		JSONObject nodes=dataJson.getJSONObject("nodes");
		
		String flow_id = "";
		boolean isAddData = false;
		//是否新增流程标识
		if(param.get("flow_id") == null || "".equals(param.get("flow_id").toString())){
			isAddData = true;//新增流程参数标识
			flow_id = UID.next();
			param.put("flow_id", flow_id);
		}else{
			flow_id = param.get("flow_id").toString();
		}
		
		String flow_name = dataJson.get("title").toString();
		param.put("flow_name", flow_name);
		
		//流程节点的封装
		int j = 0;
		for (int i = 0; i < nodes.length(); i++) {
			do{
				j++;
			}while(!nodes.has("flow_node_"+j));
			
			JSONObject info=nodes.getJSONObject("flow_node_"+j);
			Map<String,Object> row = new HashMap<String, Object>();
			row.put("flow_id", flow_id);
			row.put("node_id", "flow_node_"+j);
			row.put("node_name", info.getString("name"));
			row.put("node_type", info.getString("type"));
			row.put("node_model", "node");
			row.put("node_from_id", "");
			row.put("node_to_id", "");
			list.add(row);
		}
		//连接线的封装
		JSONObject lines=dataJson.getJSONObject("lines");
		j = 0;
		for (int i = 0; i < lines.length(); i++) {
			do{
				j++;
			}while(!lines.has("flow_line_"+j));
			
			JSONObject info=lines.getJSONObject("flow_line_"+j);
			Map<String,Object> row = new HashMap<String, Object>();
			row.put("flow_id", flow_id);
			row.put("node_id", "flow_line_"+j);
			row.put("node_name", "");
			row.put("node_type", "");
			row.put("node_model", "line");
			row.put("node_from_id", info.getString("from"));
			row.put("node_to_id", info.getString("to"));
			list.add(row);
		}
		
		if(isAddData){
			flowDao.addFlowInfo(param);
			if(list.size()>0){
				flowDao.addNodeDetail(list);
			}
		}else{
			flowDao.updateFlowInfo(param);//修改流程信息
			flowDao.deleteNodeDetail(param);//删除原有流程节点信息
			if(list.size()>0){//新增流程节点信息
				flowDao.addNodeDetail(list);
			}
		}
		
	}
	
	public void deleteFlowInfo(Map<String, Object> param) throws Exception {
		flowDao.deleteNodeDetail(param);
		flowDao.deleteFlowInfo(param);
		
	}

	public String getJsonData(Map<String, Object> param) throws Exception {
		return this.flowDao.getJsonData(param);
	}

	public List<Map<String, Object>> listAllFlow() throws Exception {
		return toLowerMapList(this.flowDao.listAllFlow());
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.flow.service.FlowService#listAllPage()
	 */
	public List<Map<String, Object>> listAllPage() {
		return toLowerMapList(this.flowDao.listAllPage());
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.flow.service.FlowService#getPageInfo(java.util.Map)
	 */
	public List<Map<String, Object>> getPageInfo(Map<String, Object> map) {
		return toLowerMapList(this.flowDao.getPageInfo(map));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.flow.service.FlowService#getPageFdl(java.util.Map)
	 */
	public List<Map<String, Object>> getPageFdl(Map<String, Object> map) {
		return toLowerMapList(this.flowDao.getPageFdl(map));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.flow.service.FlowService#setCurrent(java.util.Map)
	 */
	public void setCurrent(Map<String, Object> param) {
		this.flowDao.setCurrent(param);
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.flow.service.FlowService#getFlowInfo(java.util.Map)
	 */
	public List<Map<String, Object>> getFlowInfo(Map<String, Object> map) {
		return toLowerMapList(this.flowDao.getFlowInfo(map));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.flow.service.FlowService#publicBlmb(java.util.Map)
	 */
	public void publicBlmb(Map<String, Object> param) {
		this.flowDao.publicBlmb(param);
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.flow.service.FlowService#execCmd()
	 */
	public String execCmd(Map<String, Object> param) throws Exception {
		String run_date = String.valueOf(param.get("run_date"));
		RemoteShellTool tool = new RemoteShellTool();
		List<String> results = tool.execStrPara(run_date);
		StringBuffer sb = new StringBuffer();
		for (String string : results) {
			if(string.indexOf("ERROR")>-1){
				sb.append(string);
			}
			System.out.println(string);
		}
		return sb.toString();
	}
}
