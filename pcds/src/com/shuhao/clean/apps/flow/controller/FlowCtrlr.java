package com.shuhao.clean.apps.flow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.shuhao.clean.apps.flow.service.FlowService;
import com.shuhao.clean.apps.flow.util.JsonUtil;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.annotation.UseLog;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;
import com.shuhao.clean.utils.exttree.ExtTreeNode;

/**
 * 流程管理
 * @author Wanggl
 *
 */
@Controller
@RequestMapping("/flow")
public class FlowCtrlr extends BaseCtrlr {
	
	@Autowired
	private FlowService flowService;
	
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="flowList")
	@ResponseBody
	public Map<String,Object> flowList() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		Map<String,Object> results = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list = GlobalUtil.lowercaseListMapKey(this.flowService.flowList(param));
			results.put("totalCount", list.size());
			results.put("results", list);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 添加流程状态
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="addFlowStatus")
	@ResponseBody
	public Map<String,Object> addFlowStatus() throws Exception{
		Map<String,Object> param = getRequestParam();
		param.put("create_user_id", this.getCurrentUser().getUser_name()+"["+this.getCurrentUser().getUser_id()+"]");
		try {
			//流程状态代码自动生成，不用前端显示出来，以fw开头
			param.put("flow_status_code", "fe");
			this.flowService.addFlowStatus(param);
			return doSuccessInfoResponse("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败:" + e.getMessage());
		}
	}
	
	/**
	 * 修改流程状态
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="updateFlow")
	@ResponseBody
	public Map<String,Object> updateFlow() throws Exception{
		Map<String,Object> param = getRequestParam();
		try {
			this.flowService.updateFlow(param);
			return doSuccessInfoResponse("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("修改失败:" + e.getMessage());
		}
	}
	
	/**
	 * 删除流程
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="delFlow")
	@ResponseBody
	public Map<String,Object> delFlow() throws Exception{
		Map<String,Object> param = getRequestParam();
		try {
			this.flowService.delFlow(param);
			return doSuccessInfoResponse("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败:" + e.getMessage());
		}
	}
	
	/**
	 * 由ID查找流程
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="getFlowById")
	@ResponseBody
	public Map<String,Object> getFlowById() throws Exception{
		Map<String,Object> param = getRequestParam();
		try {
			List<Map<String,Object>> list = GlobalUtil.lowercaseListMapKey(this.flowService.getFlowById(param));
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("载入数据失败:" + e.getMessage());
		}
	}
	
	/**
	 * 补录类型
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="queryUppEnterType")
	@ResponseBody
	public Map<String,Object> queryUppEnterType() throws Exception{
		try {
			doJSONResponse(this.flowService.queryUppEnterType());
		} catch (Exception e) {
			e.printStackTrace();
			doFailureInfoResponse("载入数据失败："+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 添加补录模板
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="addBlmbNode")
	@ResponseBody
	public Map<String,Object> addBlmbNode() throws Exception{
		Map<String,Object> param = getRequestParam();
		param.put("create_user_id", this.getCurrentUser().getUser_name()+"["+this.getCurrentUser().getUser_id()+"]");
		try {
			//模板代码自动生成，以up开头，需要注意上级模板代码的默认设置。
			param.put("tmpl_id", UID.next("up"));
			if(param.get("is_shared") == null){
				param.put("is_shared", "N");
			}else{
				param.put("is_shared", "Y");
			}
			this.flowService.addBlmb(param);
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("success", "true");
			results.put("node_id", (String)param.get("tmpl_id"));
			results.put("node_name", (String)param.get("template_name"));
			results.put("node_desc", (String)param.get("enter_type_cd"));
			results.put("template_type_cd", (String)param.get("template_type_cd"));
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败:" + e.getMessage());
		}
	}
	
	/**
	 * 修改补录模板
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="updateBlmb")
	@ResponseBody
	public Map<String,Object> updateBlmb() throws Exception{
		Map<String,Object> param = getRequestParam();
		param.put("update_user_id", this.getCurrentUser().getUser_id());
		try {
			if(param.get("is_shared") == null){
				param.put("is_shared", "N");
			}else{
				param.put("is_shared", "Y");
			}
			this.flowService.updateBlmb(param);
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("success", "true");
			results.put("node_id", (String)param.get("tmpl_id"));
			results.put("node_name", (String)param.get("template_name"));
			results.put("node_desc", (String)param.get("enter_type_cd"));
			results.put("template_type_cd", (String)param.get("template_type_cd"));
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("修改失败:" + e.getMessage());
		}
	}
	/**
	 * 发布补录模板
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="publicBlmb")
	@ResponseBody
	public Map<String,Object> publicBlmb() throws Exception{
		Map<String,Object> param = getRequestParam();
		param.put("update_user_id", this.getCurrentUser().getUser_id());
		try {
			this.flowService.publicBlmb(param);
			List<Map<String,Object>> list = this.flowService.findBlmbById(param);
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("info", "操作成功");
			results.putAll(list.get(0));
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("发失败:" + e.getMessage());
		}
	}
	
	/**
	 * 删除补录模板
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="delBlmb")
	@ResponseBody
	public Map<String,Object> delBlmb() throws Exception{
		Map<String,Object> param = getRequestParam();
		try {
			this.flowService.delBlmb(param);
			return doSuccessInfoResponse("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败:" + e.getMessage());
		}
	}
	/**
	 * 查询模板信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="findBlmbById")
	@ResponseBody
	public Map<String,Object> findBlmbById() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		try {
//			List<Map<String,Object>> list = GlobalUtil.lowercaseListMapKey(this.themeClassService.getThemeById(uppTheme));
			List<Map<String,Object>> list = this.flowService.findBlmbById(param);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 查询所有表信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="listAllTable")
	@ResponseBody
	public Map<String,Object> listAllTable() throws Exception{
		try {
			List<Map<String,Object>> list = this.flowService.listAllTable();
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	/**
	 * 查询所有补录页面
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="listAllPage")
	@ResponseBody
	public Map<String,Object> listAllPage() throws Exception{
		try {
			List<Map<String,Object>> list = this.flowService.listAllPage();
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 查询节点对应表信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="nodeTableList")
	@ResponseBody
	public Map<String,Object> nodeTableList() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		try {
			List<Map<String,Object>> list = this.flowService.nodeTableList(param);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 查询节点对应功能信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="nodeGnList")
	@ResponseBody
	public Map<String,Object> nodeGnList() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		try {
			List<Map<String,Object>> list = this.flowService.nodeGnList(param);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 申请页面  list
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="getApplyList")
	@ResponseBody
	public Map<String,Object> getApplyList() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		param.put("login_user_id", this.getCurrentUser().getUser_id());
		try {
			int totalCount = this.flowService.getApplyListCount(param);
			List<Map<String,Object>> list = this.flowService.getApplyList(param);
			request.setAttribute("total", totalCount);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 申请页面的提交与撤回
	 * 前端需传一个参数  apply_status  '02' 提交      '03'撤回
	 * 	1	00	初始	
		2	01	编辑	
		3	02	待审		
		4	03	撤回		
		5	04	退回		
		6	05	通过		
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="applyFlow")
	@ResponseBody
	public Map<String,Object> applyFlow() throws Exception{
		Map<String,Object> param = this.getRequestParam();//apply_status  '01' 提交      ‘02’撤回
		String apply_status = (String)param.get("apply_status");
		param.put("apply_user_id", this.getCurrentUser().getUser_id());
		try {
			this.flowService.applyFlow(param);
			doSuccessInfoResponse(("01".equals(apply_status)?"提交":"撤回")+"成功！");
		} catch (Exception e) {
			e.printStackTrace();
			doFailureInfoResponse("操作失败：原因是"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 审批页面  list
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="getApproveList")
	@ResponseBody
	public Map<String,Object> getApproveList() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		param.put("login_user_id", this.getCurrentUser().getUser_id());
		try {
			int totalCount = this.flowService.getApproveListCount(param);
			List<Map<String,Object>> list = this.flowService.getApproveList(param);
			request.setAttribute("total", totalCount);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 审批页面的通过与退回
	 * 前端需传一个参数  flow_status_code  '05' 通过      '04'退回
	 * 	1	00	初始	
		2	01	编辑	
		3	02	待审		
		4	03	撤回		
		5	04	退回		
		6	05	通过		
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="approveFlow")
	@ResponseBody
	public Map<String,Object> approveFlow() throws Exception{
		Map<String,Object> param = this.getRequestParam();//approve_status  '01' 通过      ‘02’退回
		param.put("approve_user_id", this.getCurrentUser().getUser_id());
		try {
			this.flowService.approveFlow(param);
			return doSuccessInfoResponse("审批成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("操作失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 查询所有流程模板信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="listAllFlowTmpl")
	@ResponseBody
	public Map<String,Object> listAllFlowTmpl() throws Exception{
		try {
			List<Map<String,Object>> list = this.flowService.listAllFlowTmpl();
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 修改状态
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="updateApproStatus")
	@ResponseBody
	public Map<String,Object> updateApproStatus() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		String status = GlobalUtil.getStringValue(param, "status");//功能分类代码   00：录入    01：审批
		param.put("00".equals(status)?"apply_user_id":"approve_user_id", this.getCurrentUser().getUser_id());
		try {
			this.flowService.updateApproStatus(param);
			return doSuccessInfoResponse("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("审批失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 插入审批流水
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="addApproTrans")
	@ResponseBody
	public Map<String,Object> addApproTrans() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		param.put("approve_user_id", this.getCurrentUser().getUser_id());
		try {
			this.flowService.addApproTrans(param);
			return doSuccessInfoResponse("审批成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 查询所有模板信息
	 * <br>
	 * queryAll =  yes or no
	 * //类型为02补录模版，03包含子模版的补录模版
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="listAllTmpl/{queryAll}")
	public void listAllTmpl(@PathVariable String queryAll,HttpServletResponse response) throws Exception{
		Map<String,Object> param = this.getRequestParam();
		param.put("login_user_id", this.getCurrentUser().getUser_id());
		String tmpl_id = GlobalUtil.getStringValue(param, "tmpl_id");
		String jsonStr ="";
		if (null == tmpl_id || "".equals(tmpl_id))
        	tmpl_id = "root";
		param.put("tmpl_id", tmpl_id);
		try {
			param.put("queryAll", queryAll);
			ExtTreeNode treeNode = this.flowService.getMyTmpl(param);
			jsonStr = JsonUtil.toJson(treeNode.getChildren());
			doExtTreeJSONResponseJson(jsonStr, response);
		} catch (Exception e) {
			e.printStackTrace();
			List<ExtTreeNode> eList = new ArrayList<ExtTreeNode>();
			ExtTreeNode treeNode = new ExtTreeNode();
			treeNode.setId("errorRootId");
			treeNode.setText("展示指标树失败:" + e.getMessage());
			treeNode.setLeaf(true);
			eList.add(treeNode);
			jsonStr = JsonUtil.toJson(eList);
			doExtTreeJSONResponseJson(jsonStr, response);
		}
	}



	public void doExtTreeJSONResponseJson( String json, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}
	/**
	 * 查询差额信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="queryTotalCe")
	@ResponseBody
	public Map<String,Object> queryTotalCe() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		param.put("user_bank_id", this.getCurrentUser().getBank_org_id());
		try {
			String result_info = this.flowService.queryTotalCe(param);
			return doSuccessInfoResponse(GlobalUtil.isNotNull(result_info)?"与总账差额"+result_info:"该批次数据未查询到总分差额");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询总分校验失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 获取模板用户
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="getUserByBlmb")
	@ResponseBody
	public Map<String,Object> getUserByBlmb() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		try {
			int totalCount = this.flowService.getUserByBlmbCount(param);
			List<Map<String,Object>> list = this.flowService.getUserByBlmb(param);
			request.setAttribute("total", totalCount);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 获取用户模板
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="getBlmbByUserId")
	@ResponseBody
	public Map<String,Object> getBlmbByUserId() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		try {
			List<Map<String,Object>> blmbList = this.flowService.getBlmbByUserId(param);
			return doJSONResponse(blmbList);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 获取模板
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="listBlmb")
	@ResponseBody
	public Map<String,Object> listBlmb() throws Exception{
		try {
			List<Map<String,Object>> blmbList = this.flowService.listBlmb();
			return doJSONResponse(blmbList);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}

	/**
	 * 添加用户补录模板
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="addUserBlmb")
	@ResponseBody
	public Map<String,Object> addUserBlmb() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		try {
			this.flowService.addUserBlmb(param);
			return doSuccessInfoResponse("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 添加用户补录模板
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="addUserBlmb2")
	@ResponseBody
	public Map<String,Object> addUserBlmb2() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		try {
			this.flowService.addUserBlmb2(param);
			return doSuccessInfoResponse("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败：原因是"+e.getMessage());
		}
	}
	
	
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="testInfo/{text}")
	@ResponseBody
	public ModelAndView testInfo(@PathVariable String text,ModelAndView model) throws Exception{
		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/plain;charset=UTF-8");
//		text = URLDecoder.decode(text,"utf-8");
		return new ModelAndView(new RedirectView(request.getContextPath()+"/public/pages/error.jsp?text="+text));
	}
	
	/**
	 * 查询用户列表
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0014")
	@UseLog
	@RequestMapping(value="getUserList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, Object> param = getRequestParam();
		param.put("login_org_id", this.getCurrentUser().getBank_org_id());
		try {
			int totalCount = this.flowService.getUserListCount(param);
			List<Map<String,Object>> list = this.flowService.getUserList(param);
			request.setAttribute("total", totalCount);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 移除用户模板权限
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="removeUser")
	@ResponseBody
	public Map<String,Object> removeUser() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		try {
			this.flowService.removeUser(param);
			return doSuccessInfoResponse("");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败：原因是"+e.getMessage());
		}
	}
	
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="getJsonData")
	@ResponseBody
	public Map<String,Object> getJsonData() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		try {
			String jsondata = this.flowService.getJsonData(param);
//			return doSuccessInfoResponse("{\"title\":\"newFlow_1\",\"nodes\":{\"flow_node_1\":{\"name\":\"申请\",\"left\":43,\"top\":212,\"type\":\"start round\",\"width\":24,\"height\":24,\"alt\":true},\"flow_node_2\":{\"name\":\"一级审批\",\"left\":115,\"top\":216,\"type\":\"task\",\"width\":86,\"height\":24,\"alt\":true},\"flow_node_11\":{\"name\":\"通过\",\"left\":207,\"top\":152,\"type\":\"state\",\"width\":86,\"height\":24,\"alt\":true},\"flow_node_12\":{\"name\":\"退回\",\"left\":199,\"top\":314,\"type\":\"state\",\"width\":86,\"height\":29,\"alt\":true},\"flow_node_18\":{\"name\":\"通过\",\"left\":388,\"top\":153,\"type\":\"state\",\"width\":86,\"height\":24,\"alt\":true},\"flow_node_19\":{\"name\":\"退回\",\"left\":396,\"top\":291,\"type\":\"state\",\"width\":86,\"height\":24,\"alt\":true},\"flow_node_20\":{\"name\":\"n级审批\",\"left\":489,\"top\":208,\"type\":\"task\",\"width\":86,\"height\":24,\"alt\":true},\"flow_node_21\":{\"name\":\"通过\",\"left\":571,\"top\":145,\"type\":\"state\",\"width\":86,\"height\":24,\"alt\":true},\"flow_node_22\":{\"name\":\"结束\",\"left\":691,\"top\":213,\"type\":\"end\",\"width\":86,\"height\":24,\"alt\":true},\"flow_node_23\":{\"name\":\"退回\",\"left\":576,\"top\":287,\"type\":\"state\",\"width\":86,\"height\":24,\"alt\":true},\"flow_node_13\":{\"name\":\"二级审批\",\"left\":307,\"top\":214,\"type\":\"task\",\"width\":86,\"height\":24,\"alt\":true}},\"lines\":{\"flow_line_5\":{\"type\":\"sl\",\"from\":\"flow_node_1\",\"to\":\"flow_node_2\",\"name\":\"\"},\"flow_line_14\":{\"type\":\"sl\",\"from\":\"flow_node_2\",\"to\":\"flow_node_11\",\"name\":\"\"},\"flow_line_16\":{\"type\":\"sl\",\"from\":\"flow_node_2\",\"to\":\"flow_node_12\",\"name\":\"\"},\"flow_line_17\":{\"type\":\"sl\",\"from\":\"flow_node_12\",\"to\":\"flow_node_1\",\"name\":\"\"},\"flow_line_25\":{\"type\":\"sl\",\"from\":\"flow_node_13\",\"to\":\"flow_node_19\",\"name\":\"\"},\"flow_line_26\":{\"type\":\"sl\",\"from\":\"flow_node_19\",\"to\":\"flow_node_1\",\"name\":\"\"},\"flow_line_27\":{\"type\":\"sl\",\"from\":\"flow_node_18\",\"to\":\"flow_node_20\",\"name\":\"\"},\"flow_line_28\":{\"type\":\"sl\",\"from\":\"flow_node_20\",\"to\":\"flow_node_21\",\"name\":\"\"},\"flow_line_29\":{\"type\":\"sl\",\"from\":\"flow_node_21\",\"to\":\"flow_node_22\",\"name\":\"\"},\"flow_line_31\":{\"type\":\"sl\",\"from\":\"flow_node_20\",\"to\":\"flow_node_23\",\"name\":\"\"},\"flow_line_32\":{\"type\":\"sl\",\"from\":\"flow_node_23\",\"to\":\"flow_node_1\",\"name\":\"\"},\"flow_line_24\":{\"type\":\"sl\",\"from\":\"flow_node_13\",\"to\":\"flow_node_18\",\"name\":\"\"},\"flow_line_15\":{\"type\":\"sl\",\"from\":\"flow_node_11\",\"to\":\"flow_node_13\",\"name\":\"\"}},\"areas\":{},\"initNum\":35}");
			return doSuccessInfoResponse(jsondata);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败：原因是"+e.getMessage());
		}
	}
	
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="saveJsonData")
	@ResponseBody
	public Map<String,Object> saveJsonData() throws Exception{
		Map<String,Object> param = this.getRequestParam();
		param.put("login_user_id", this.getCurrentUser().getUser_id());
		param.put("flow_name", "流程");
		try {
//			this.flowService.removeUser(param);
			this.flowService.saveJsonData(param);
			return doSuccessInfoResponse(param.get("flow_id").toString());
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 查询所有表信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="listAllFlow")
	@ResponseBody
	public Map<String,Object> listAllFlow() throws Exception{
		try {
			List<Map<String,Object>> list = this.flowService.listAllFlow();
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	/**
	 * 查询页面信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="getPageInfo")
	@ResponseBody
	public Map<String,Object> getPageInfo() throws Exception{
		try {
			Map<String,Object> map = getRequestParam();
			List<Map<String,Object>> list = this.flowService.getPageInfo(map);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	/**
	 * 查询页面字段
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="getPageFdl")
	@ResponseBody
	public Map<String,Object> getPageFdl() throws Exception{
		try {
			Map<String,Object> map = getRequestParam();
			List<Map<String,Object>> list = this.flowService.getPageFdl(map);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	/**
	 * 查询流程信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="getFlowInfo")
	@ResponseBody
	public Map<String,Object> getFlowInfo() throws Exception{
		try {
			Map<String,Object> map = getRequestParam();
			List<Map<String,Object>> list = this.flowService.getFlowInfo(map);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="deleteFlowInfo")
	@ResponseBody
	public Map<String,Object> deleteFlowInfo() throws Exception{
		try {
			Map<String, Object> param = this.getRequestParam();
			this.flowService.deleteFlowInfo(param);
			return doSuccessInfoResponse("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败："+e.getMessage());
		}
	}
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="setCurrent")
	@ResponseBody
	public Map<String,Object> setCurrent() throws Exception{
		try {
			Map<String, Object> param = this.getRequestParam();
			this.flowService.setCurrent(param);
			return doSuccessInfoResponse("设置成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("设置失败："+e.getMessage());
		}
	}

	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="exec")
	@ResponseBody
	public Map<String,Object> execCmd() throws Exception{
		try {
			Map<String, Object> param = this.getRequestParam();
			String result = this.flowService.execCmd(param);
			return doSuccessInfoResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("调用shell失败:" + e.getMessage());
		}
	}
}
