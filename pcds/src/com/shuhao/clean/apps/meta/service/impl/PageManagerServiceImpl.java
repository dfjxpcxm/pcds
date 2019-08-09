package com.shuhao.clean.apps.meta.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.security.domain.IUser;
import com.rx.util.tree.Tree;
import com.rx.util.tree.TreeNode;
import com.rx.util.tree.TreeStore;
import com.shuhao.clean.apps.meta.dao.BckTrackingMetaDao;
import com.shuhao.clean.apps.meta.dao.PageManagerDao;
import com.shuhao.clean.apps.meta.entity.BaseTreeInfo;
import com.shuhao.clean.apps.meta.entity.FnEvent;
import com.shuhao.clean.apps.meta.service.IPageManagerService;
import com.shuhao.clean.base.BaseJdbcService;
import com.shuhao.clean.constant.ExtConstant;
import com.shuhao.clean.constant.WorkFlowConstant;
import com.shuhao.clean.utils.FileUtil;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;
import com.shuhao.clean.utils.xml.XmlStrUtil;

@Service(value="pageManagerService")
public class PageManagerServiceImpl extends BaseJdbcService implements IPageManagerService {
	
	@Autowired
	private PageManagerDao pageManagerDao;
	
	@Autowired
	private BckTrackingMetaDao bakTrackMetaDao;
	
	/**
	 * 执行sql
	 */
	public void executeMetaData(Map<String, Object> params,IUser user) throws Exception {
		String execType  = String.valueOf(params.get("execType"));
		if(execType.equals("del")){
			String[] keys = String.valueOf(params.get("key")).split("_");
			//目前删除为循环删除，待改....批量
			for (int i = 0; i < keys.length; i++) {
				if(keys[i].equals("")){
					continue;
				}
				params.put("business_no", keys[i]);
				bakTrackMetaDao.executeMetaData(params);
			}
		}else{
			bakTrackMetaDao.executeMetaData(params);
		}
	}
	
	/**
	 * 查询补录数据
	 */
	public List<Map<String, Object>> getMetaData(Map<String,Object> params)throws Exception {
		return GlobalUtil.lowercaseListMapKey(bakTrackMetaDao.getMetaData(params));
	}
	/**
	 * 查询补录数据总数
	 */
	public int getMetaDataCounts(Map<String,Object> params) throws Exception{
		return bakTrackMetaDao.getMetaDataCounts(params);
	}
	/**
	 * 获取维度sql
	 */
	public String getDimLinkSql(Map<String,Object> params) throws Exception{
		return pageManagerDao.getDimLinkSql(params);
	}
	
	/**
	 * 获取维度树
	 */
	public  List<Map<String,Object>> getDimLinkForTree(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getDimLinkForTree(params));
	}
	/**
	 * 执行sql
	 */
	public List<Map<String, Object>> getDimLinkByComponentId(Map<String, Object> params) throws Exception {
		return GlobalUtil.lowercaseListMapKey(bakTrackMetaDao.getDimLinkDataByComponentId(params));
	}
	/**
	 * 通过id查询补录数据用于修改回显
	 */
	public List<Map<String,Object>> getMetaDataById(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackMetaDao.getMetaDataById(params));
	}

//	/**
//	 * 执行sql
//	 */
//	public List<Map<String, Object>> getMetaByTemplateId(Map<String,Object> params)
//			throws Exception {
//		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getMetaByTemplateId(params));
//	}
	
//	//根据模板获取分类元素
//	public List<Map<String,Object>> getMetaTypeByTemplateId(Map<String,Object> params) throws Exception{
//		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getMetaTypeByTemplateId(params));
//	}
//	
	//根据模板获取查询菜单元素
//	
//	public List<Map<String,Object>> getMenuByTemplateId(Map<String,Object> params) throws Exception{
//		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getMenuByTemplateId(params));
//	}
//	
	
	//查询模板树
	public List<Map<String, Object>> getTemplateTree(Map<String,Object> params)
			throws Exception {
		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getTemplateTree(params));
	}
	/**
	 * 查询模板
	 */
	public List<Map<String,Object>> getTemplateById(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getTemplateById(params));
	}
	
	public  List<Map<String,Object>>   getSheetMappingTab(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getSheetMappingTab(params));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IBckTrackingService#getTempletByParentId(java.util.Map)
	 */
	public List<Map<String, Object>> getTempletByParentId(
			Map<String, Object> param) {
		return GlobalUtil.lowercaseListMapKey(this.bakTrackMetaDao.getTempletByParentId(param));
	}

	/**
	 * 将XmlUtil 修改为 XmlStrUtil
	 * @param templateId
	 * @param metaName
	 * @param param
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IBckTrackingService#getEventResults(java.lang.String)
	 */
	public List<Map<String, Object>> getEventResults(String templateId,String metaName,String param)
			throws Exception {
		//查询单独配配置的事件
		FnEvent eventConf = (FnEvent)XmlStrUtil.getFnEvent(templateId,metaName);
		//查询通用事件
		if(eventConf==null){
			eventConf = (FnEvent)XmlStrUtil.getFnEvent(ExtConstant.COMMON_FIELD_EVENT_PID,metaName);
		}
		
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		if(eventConf!=null){
			String sql = eventConf.getInitSql();
			sql = sql.replace("#id", param);
			
			List<Map<String, Object>> dataList = GlobalUtil.lowercaseListMapKey(jdbcTemplate.queryForList(sql));
			if(dataList.size()>0){
				Map<String, Object> map = dataList.get(0);
				List<String> comps = eventConf.getComps();
				//如果没有配置响应填充字段，自己返回查询结果
				if(comps==null || comps.size()==0){
					return dataList;
				}else{
					for (String name : comps) {
						String value = GlobalUtil.getStringValue(map, name);
						if(GlobalUtil.isNotNull(value)){
							//传递到页面的name是小写的
							Map<String, Object> row = new HashMap<String, Object>();
							row.put("name", name);
							row.put("value", value);
							results.add(row);
						}
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * 返回事件结果集
	 */
	public List<Map<String, Object>> getEventStore(String templateId,String metaName,String param)
			throws Exception {
		//查询单独配配置的事件
		String sql = XmlStrUtil.getFnSql(templateId,metaName);
		//查询通用事件
		if(sql==null || sql.equals("")){
			sql =  XmlStrUtil.getFnSql(ExtConstant.COMMON_FIELD_EVENT_PID,metaName);
		}
		
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		if(sql!=null && !sql.equals("")){
			sql = sql.replace("#id", param);
			List<Map<String, Object>> dataList = GlobalUtil.lowercaseListMapKey(jdbcTemplate.queryForList(sql));
			return dataList;
		}
		return results;
	}

	public void batchExecSql(List<Map<String, Object>> sqlList)
			throws Exception {
		this.bakTrackMetaDao.batchExecSql(sqlList);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#batchExecSql(java.util.List, java.util.List)
	 */
	public void batchExecSql(List<Map<String, Object>> insSqlList,
			List<Map<String, Object>> delSqlList) throws Exception {
		if(delSqlList.size()>0){
			this.bakTrackMetaDao.batchExecSql(delSqlList);
		}
		this.bakTrackMetaDao.batchExecSql(insSqlList);

	}
	
	public String getTabNameByTempId(Map<String,Object> params) throws Exception{
		return this.pageManagerDao.getTabNameByTempId(params);
	}
	
	/**
	 * 取button配置信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getButtonByTmplId(Map<String,Object> params) throws Exception{
		List<Map<String, Object>> dataList = GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getButtonByTmplId(params),true);
		if(dataList.size()>0){
			return dataList.get(0);
		}
		return null;
	}
	
	
//	public void updateFlowData(Map<String,Object> params) throws Exception{
//		this.bakTrackMetaDao.updateFlowData(params);
//	}
	
	
	public void updateBusinessData(Map<String,Object> params) throws Exception{
		this.bakTrackMetaDao.updateBusinessData(params);
	}
	
	public void delBusinessData(Map<String,Object> params) throws Exception{
		this.bakTrackMetaDao.delBusinessData(params);
	}
	

	public List<Map<String, Object>> getMetaLinkJoinSql(Map<String, Object> params) throws Exception {
		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getMetaLinkJoinSql(params));
	}
	
	//导出模板
	public List<Map<String,Object>> getMetaInfoForExp(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getMetaInfoForExp(params));
	}
	
	public List<Map<String,Object>> getTemplateMetaInfo(String templateId) throws Exception{
		return GlobalUtil.lowercaseListMapKey(pageManagerDao.getTemplateMetaInfo(templateId));
	}
	
	/**
	 * 重构校验数据对象,将补录字段列表的默认值放入传入data中
	 * @param templateId
	 * @param validData
	 * @throws Exception
	 */
	public void rebuildValidData(String templateId,Map<String,Object> validData) throws Exception{
		//获取默认数据
		List<Map<String, Object>> dataList = GlobalUtil.lowercaseListMapKey(pageManagerDao.getTemplateMetaHideInfo(templateId));
		
		//完善校验参数信息，放入补录配置的隐藏信息
		for (Map<String, Object> map : dataList) {
			String key = getStringValue(map, "metadata_id")+"_"+getStringValue(map, "metadata_name");
			if(validData.containsKey(key)){
				continue;
			}else{
				validData.put(key, getStringValue(map, "component_default"));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IBckTrackingService#findMetaIdFromList(java.util.List)
	 */
	public String findMetaIdFromList(List<Map<String, Object>> dataList,String name)
			throws Exception {
		for (int i = dataList.size()-1; i >=0; i--) {
			Map<String, Object> map = dataList.get(i);
			if(getStringValue(map, "metadata_name").equals(name)){
				return getStringValue(map, "metadata_id");
//				dataList.remove(i);//移除
//				return metaId;
			}
		}
		return null;
	}
	
	/**
	 * 生成业务编号,规则：业务日期+入账机构+币种+产品科目+随机6位序号
	 * @param params
	 * @return
	 */
	public String getBusinessNo(Map<String, Object> params){
		String finance_org_id =  String.valueOf(params.get("finance_org_id")).trim();
		String acct_prod_id =  String.valueOf(params.get("acct_prod_id")).trim();
		String curr_cd =  String.valueOf(params.get("curr_cd")).trim();
		String biz_date =  String.valueOf(params.get("biz_date")==null ? params.get("int_start_date") : params.get("biz_date"));
		
		String business_no = "";
		//业务编号
		if(biz_date!=null&&!"".equals(biz_date)){
			business_no = biz_date.replace("-", "") + finance_org_id + curr_cd + acct_prod_id + UID.nt()+ UID.getShortSeq();
		}else{
			business_no = UID.next("BU");
		}
		return business_no;
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getPageEleByTemplateId(java.util.Map)
	 */
	public Map<String, Object> getPageEleByTemplateId(
			Map<String, Object> params) throws Exception {
		Map<String, Object> eleMap = null;
		eleMap = new HashMap<String, Object>();
//			List<Map<String,Object>> PAG = GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getPAG(params));//工具条按钮列表
//			if(PAG==null||PAG.size()==0){
//				throw new Exception("模板未关联页面");
//			}
		eleMap.putAll(params);
		List<Map<String,Object>> TBA = GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getTBA(eleMap),true);//工具条按钮列表
		eleMap.put("TBA", TBA); //按钮中配置的sql使用的是CLOB
		List<Map<String,Object>> FDL = GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getFDL(eleMap));//字段列表
		eleMap.put("FDL", FDL);
		List<Map<String,Object>> FML = GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getFML(eleMap));//表单列表
		eleMap.put("FML", FML);
		//获取表单下按钮列表
		for (int i = 0; i < FML.size(); i++) {
			 Map<String,Object> temp = FML.get(i);
			 List<Map<String,Object>> FRM  = GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getFRM(temp),true);
			 temp.put("FRM", FRM);
		}
		//查询字段业务类型
		List<Map<String,Object>> colBizTypes = GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getcolBizTypes(eleMap));
		eleMap.put("COL_BIZ_TYPE", colBizTypes);
		//查询关联字段
		String relaName = GlobalUtil.getStringValue(params, "rela_metadata_names");
		List<Map<String,Object>> relamesList  = new ArrayList<Map<String,Object>>();
		if(GlobalUtil.isNotNull(relaName)){
			String[] relaNames = relaName.split(",");
			for (String string : relaNames) {
				params.put("rela_name", string.toLowerCase());
				Map<String,Object> relaMap = new HashMap<String, Object>();
				List<Map<String,Object>> relaNamesList = GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getColRelaField(params));	
				relaMap.putAll(relaNamesList.get(0));
				List<Map<String,Object>> prtRelaNamesList = GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getPrtColRelaField(params));
				if(prtRelaNamesList==null||prtRelaNamesList.size()==0){
					throw new Exception("子模板与主模板关联字段配置出错");
				}
				relaMap.put("prt_field_name", GlobalUtil.getStringValue(prtRelaNamesList.get(0), "field_name"));
				relamesList.add(relaMap);
			}
		}
		eleMap.put("relaNames", relamesList);
		eleMap.putAll(params);
		return eleMap;
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getChildTempl(java.util.Map)
	 */
	public List<Map<String, Object>> getChildTempl(Map<String, Object> params) {
		return GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getChildTempl(params));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getFDL(java.util.Map)
	 */
	public List<Map<String, Object>> getFDL(Map<String, Object> params) {
		return 	GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getFDL(params));//字段列表	
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getWorkFlowById(java.util.Map)
	 */
	public List<Map<String, Object>> getWorkFlowById(Map<String, Object> params){
		return 	GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getWorkFlowById(params));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getExcelCol(java.util.Map)
	 */
	public List<Map<String, Object>> getExcelCol(Map<String, Object> params) {
		return GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getExcelCol(params));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getSheetListById(java.util.Map)
	 */
	public List<Map<String, Object>> getSheetListById(Map<String, Object> params) {
		return GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getSheetListById(params));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getExcelColByName(java.util.Map)
	 */
	public List<Map<String, Object>> getExcelColByName(
			Map<String, Object> tempParaMap) {
		return GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getExcelColByName(tempParaMap));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getExcelSheetByName(java.util.Map)
	 */
	public List<Map<String, Object>> getExcelSheetByName(
			Map<String, Object> tempParaMap) {
		return GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getExcelSheetByName(tempParaMap));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getFDLByTmplId(java.util.Map)
	 */
	public List<Map<String, Object>> getFDLByTmplId(Map<String, Object> params) {
		return GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getFDLByTmplId(params));
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getHiddenDefaultValue(java.util.Map)
	 */
	public List<Map<String, Object>> getHiddenDefaultValue(
			Map<String, Object> params) {
		return GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getHiddenDefaultValue(params));
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getSubTemplateById(java.util.Map)
	 */
	public List<Map<String, Object>> getSubTemplateById(
			Map<String, Object> params) throws Exception {
		return GlobalUtil.lowercaseListMapKey(this.pageManagerDao.getSubTemplateById(params));
	}
	
	/**
	 * 生成excel导出时的列头信息
	 * <BR> 返回：colName,excelData
	 * @param metaParamsList
	 * @param isDefault
	 * @param addLink
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getExcelColByMeta(List<Map<String,Object>> metaParamsList,boolean isDefault,boolean addLink) throws Exception {
		Map<String,List<String>> excelData = new LinkedHashMap<String, List<String>>();
		List<String> linkList = null;
		List<String> colName = new ArrayList<String>();
		
		//获取sheet页下字段
		if (metaParamsList != null && metaParamsList.size() > 0) {
			if(isDefault){
				for (int i = 0; i < metaParamsList.size(); i++) {
					Map<String,Object> paramsMap = metaParamsList.get(i);
					String metadata_name = GlobalUtil.parse2String(paramsMap.get("field_name"));
					String metadata_tab = GlobalUtil.parse2String(paramsMap.get("component_label"));
					String is_must_input = String.valueOf(paramsMap.get("is_must_input") == null ? "N" : paramsMap.get("is_must_input"));
					
					//新增数据类型
					String Cell_Text = "";
					String metadata_type = GlobalUtil.parse2String(paramsMap.get("component_type_id"));
					if(ExtConstant.EXT_DATEFIELD.equals(metadata_type)) {
						Cell_Text = metadata_tab + "#DATE";
					}else if(ExtConstant.EXT_MONEYFIELD.equals(metadata_type)){
						Cell_Text = metadata_tab + "#MONEY";
					}else if(ExtConstant.EXT_HIDDENFIELD.equals(metadata_type)){ //隐藏域
						Cell_Text = metadata_tab + "#HIDDEN";
					}else{
						Cell_Text = metadata_tab + "#STRING";
					}
					Cell_Text =Cell_Text +"#"+ is_must_input; //是否必输入
					
					//是否生成link信息
					if(addLink){
						String dim_code = GlobalUtil.parse2String(paramsMap.get("dim_cd"));
						String component_default = GlobalUtil.parse2String(paramsMap.get("default_value"));
						
						if (dim_code != null && !"".equals(dim_code)) {
							linkList = getLinkDataForExp(dim_code, component_default);
						}else {
							linkList = null;
						}
					}else{
						linkList = null;
					}
					
					excelData.put(Cell_Text, linkList);
					colName.add(metadata_name);
				}
			}else{
				for (int i = 0; i < metaParamsList.size(); i++) {
					Map<String,Object> paramsMap = metaParamsList.get(i);
					String metadata_name = GlobalUtil.parse2String(paramsMap.get("xls_col_name"));
					String metadata_tab = GlobalUtil.parse2String(paramsMap.get("xls_col_label"));
					//必输
					String is_must_input = String.valueOf(paramsMap.get("is_must_input") == null ? "N" : paramsMap.get("is_must_input"));
					String formula_expr = GlobalUtil.parse2String(paramsMap.get("formula_expr"));
					
					//新增数据类型
					String Cell_Text = "";
					String metadata_type = GlobalUtil.parse2String(paramsMap.get("data_type_cd"));
					if(ExtConstant.DATA_TYPE_DATE.equals(metadata_type)) {
						Cell_Text = metadata_tab + "#DATE";
					}else if(ExtConstant.DATA_TYPE_NUMBER.equals(metadata_type)){
						Cell_Text = metadata_tab + "#MONEY";
					}else{
						Cell_Text = metadata_tab + "#STRING";
					}
					Cell_Text = Cell_Text + "#" + is_must_input + "#" + formula_expr;
					
					//是否生成link信息
					if(addLink){
						String dim_code = GlobalUtil.parse2String(paramsMap.get("dim_cd"));
						String component_default = GlobalUtil.parse2String(paramsMap.get("default_value"));
						
						if (dim_code != null && !"".equals(dim_code)) {
							linkList = getLinkDataForExp(dim_code, component_default);
						}else {
							linkList = null;
						}
					}else{
						linkList = null;
					}
					
					excelData.put(Cell_Text, linkList);
					colName.add(metadata_name);
				}
			}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("colName", colName);
		result.put("excelData", excelData);
		return result;
	}
	
	
	/**
	 * 获取纬度数据
	 * @param dimCode
	 * @param rootValue
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> getLinkDataForExp(String dimCode,String rootValue) throws Exception{
		
		List<String> linkData = new ArrayList<String>();
		
		Map<String,Object> params  = new HashMap<String, Object>();
		params.put("dimcode", dimCode);
		List<Map<String, Object>> treeData = getDimLinkForTree(params);
		if (treeData != null && treeData.size() > 0) {
			Map<String, Object> treeMap = treeData.get(0);
			String isTree = (String)treeMap.get("is_tree");
			String sql = (String)treeMap.get("dim_sql_expr");
			String nodeId = (String)treeMap.get("code_col_name");
			String nodeName = (String)treeMap.get("label_col_name");
			if (isTree.equals("Y")) {
				String parentNodeName =  (String)treeMap.get("prt_col_name");
				String str = nodeId + " nodeId ,"+nodeName +" nodeName ," + parentNodeName + " parentNodeName ";
				sql = sql.replace("*", str);
				if (rootValue == null || "".equals(rootValue)) {
					rootValue  = (String)treeMap.get("root_value");
				}
				params.put("nodeId", rootValue);
				params.put("sql", sql);
				params.put("id", rootValue);//传入纬度的ID
				List<Map<String, Object>> linkDatas = getDimLinkByComponentId(params);
				
				TreeStore tree = new TreeStore();
				for (Map<String, Object> map : linkDatas) {
					BaseTreeInfo node = new BaseTreeInfo();
					String nodeid = GlobalUtil.parse2String(map.get("nodeid"));
					String nodename = GlobalUtil.parse2String(map.get("nodename"));
					String pnodeid = GlobalUtil.parse2String(map.get("parentnodename"));
					node.setNodeId(nodeid);
					node.setNodeName(nodename);
					node.setParentNodeId(pnodeid);
					tree.addTreeNode(node);
				}
				List<Tree> treeList = tree.getTreeListByParentID(rootValue);
				
				//开始递归构造tree
				getTreeCode(treeList, linkData, 0);
			}else {
				params.put("sql", sql);
				params.put("id", rootValue);//传入纬度的ID
				List<Map<String, Object>> linkList = getDimLinkByComponentId(params);
				if (linkList != null && linkList.size() > 0) {
					for (int i = 0; i < linkList.size(); i++) {
						Map<String, Object> map = linkList.get(i);
						String nodeid =GlobalUtil.parse2String(map.get(nodeId));
						String nodename = GlobalUtil.parse2String(map.get(nodeName));
						linkData.add("["+nodeid+"]"+nodename);
					}
				}
			}
			return linkData;
		}
		return null;
	}
	
	
	/**
	 * 树形缩进
	 * @param treeList
	 * @param data
	 * @param level
	 */
	private void getTreeCode(List<Tree> treeList,List<String> data, int level) {
		for (int i = 0 ; i < treeList.size() ; i++) {
			Tree tree = treeList.get(i);
			TreeNode root = tree.getRootNode();
			String node = "["+root.getNodeID()+"]"+root.getNodeName();
			String str = "";
			for (int j = 0; j < level; j++) {
				str = str+"  ";
			}
			data.add(str+node);
			getTreeCode(tree.getChildren(), data, (level+1));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getSubmitAccts(java.util.Map)
	 */
	public List<Map<String, Object>> getSubmitAccts(Map<String, Object> params)
			throws Exception {
		String tableName = String.valueOf(params.get("tableName"));
		String businessNos = String.valueOf(params.get("key"));
		String busniessConds = businessNos.replaceAll("_","','");
		
		//参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("businessNos", busniessConds);
		return this.pageManagerDao.getSubmitAccts(param);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#updateStatus(java.util.List, java.lang.String)
	 */
	public int updateStatus(Map<String, Object> params, String tableName) throws Exception {
		//update imp_lend t  set t.flow_status_code = '' where t.business_no in('')
		String businessNos = String.valueOf(params.get("key"));
		String busniessConds = businessNos.replaceAll("_","','");
		//参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("businessNos", busniessConds);
		param.put("status", WorkFlowConstant.DATA_STATUS_NF_DONE);
		return this.pageManagerDao.updateStatus(param);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IPageManagerService#getFieldType(java.lang.String)
	 */
	public String getFieldType(String metadataId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("metadata_id", metadataId);
		
		Map<String, Object> field = this.pageManagerDao.getFieldType(param);
		if(field!=null){
			return GlobalUtil.getIgnoreCaseValue(field, "component_type_id");
		}
		return "";
	}
	
	/**
	 * 生成初始化sql
	 * @throws Exception
	 */
	public void initTmplSqls(boolean isUpdate,String templateId,String templateName)throws Exception{
//		List<Map<String, Object>> dataList = GlobalUtil.lowercaseListMapKey(bakTrackMetaDao.getAllTemplate());

		String filePath = FileUtil.getWebRootPath()+File.separator+"upload"+File.separator+"temp"+File.separator;
		String fileName = "initTmpl.sql";
		
		File file = FileUtil.replaceFile(filePath,fileName);
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
//		for (int k = 0; k < dataList.size(); k++) {
			List<String> colName = new ArrayList<String>();
			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("templateId", dataList.get(k).get("tmpl_id"));
			params.put("templateId", templateId);
			
			String tabName = "";
			String tableId ="";
			//查询模版对应的表
			//List<Map<String,Object>> tabList = getTmplMappingTab(params);
			List<Map<String,Object>> tabList = new  ArrayList<Map<String,Object>>();
			if (tabList != null && tabList.size() > 0) {
				Map<String,Object> tabMap = tabList.get(0);
				if (tabMap != null && tabMap.size() > 0) {
					tabName = GlobalUtil.parse2String(tabMap.get("table_name"));
					tableId = GlobalUtil.parse2String(tabMap.get("table_id"));
				}
			}
			
			//查询表字段
			//List<Map<String,Object>> metaParamsList = GlobalUtil.lowercaseListMapKey(pageManagerDao.getTableColumns(tableId));
			List<Map<String,Object>> metaParamsList = new ArrayList<Map<String,Object>>();
			if (metaParamsList != null && metaParamsList.size() > 0) {
				for (int i = 0; i < metaParamsList.size(); i++) {
					Map<String,Object> paramsMap = metaParamsList.get(i);
					String metadata_name = GlobalUtil.parse2String(paramsMap.get("column_name"));
					colName.add(metadata_name);
				}
			}
			String sql = createImpSql(tabName, colName);
			
			//写入日志文件
			if(isUpdate){
				
			}else{
//				bw.write("--template_id : "+params.get("templateId")+",template_name:"+dataList.get(k).get("template_name"));
				bw.write("--template_id : "+params.get("templateId")+",template_name:"+templateName);
				bw.newLine();// 断行
				bw.write(sql);
				bw.newLine();// 断行
			}
//		}
		
		try {
			bw.flush();// 将数据更新至文件
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			fw.close();// 关闭文件流.
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private String createImpSql(String tableName,List<String> colList){
		//添加业务ID
		if(!colList.contains("BUSINESS_NO") && !colList.contains("business_no")){
			colList.add("business_no");
		}
//		colList.add("biz_date");
		StringBuffer sql = new StringBuffer();
		sql.append("merge into ").append(tableName).append(" t using (select ");
		if (colList != null && colList.size() > 0) {
			for (int i = 0; i < colList.size(); i++) {
				sql.append( "#"+colList.get(i).toLowerCase()).append(" as ").append(colList.get(i).toLowerCase());
				sql.append(",");
			}
			sql.append(" '01' as flow_status_code from dual ) s on ( t.acct_id = s.acct_id )");
			sql.append(" when matched then update set ");
			
			for (int i = 0; i < colList.size(); i++) {
				if (colList.get(i).toLowerCase().equals("acct_id")) {
					continue;
				}
				sql.append(" t."+colList.get(i).toLowerCase()).append(" = ").append("s."+colList.get(i).toLowerCase());
				sql.append(",");
//				if (colList.size() > 1 && colList.size() -1 != i) {
//				}
			}
			//更新流程状态
			sql.append("t.flow_status_code = s.flow_status_code");
			
			sql.append(" where ( t.flow_status_code in ('00','01','03','05','11') or t.flow_status_code is null)");
			sql.append(" or (t.biz_date < s.biz_date and (t.flow_status_code in ('10') ))");
			sql.append(" when not matched then insert (");
			
			for (int i = 0; i < colList.size(); i++) {
				if (i>0) {
					sql.append(",");
				}
				sql.append(colList.get(i).toLowerCase());
			}
			//流程状态信息
			sql.append(",flow_status_code");
			
			sql.append(") values (");
			
			for (int i = 0; i < colList.size(); i++) {
				if(i>0){
					sql.append(",");
				}
				sql.append("s."+colList.get(i).toLowerCase());
			}
			//流程状态信息
			sql.append(",s.flow_status_code");
			
			sql.append(")");
		}
		
		return sql.toString();
	}
}
