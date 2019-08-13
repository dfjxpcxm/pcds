package com.shuhao.clean.apps.meta.controller; 
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shuhao.clean.apps.meta.entity.CallbackField;
import com.shuhao.clean.apps.meta.service.IPageManagerService;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.apps.validate.entity.PageEvent;
import com.shuhao.clean.apps.validate.service.ICheckService;
import com.shuhao.clean.apps.validate.service.IPageEventService;
import com.shuhao.clean.apps.validate.service.impl.CheckStrategy;
import com.shuhao.clean.apps.validate.service.impl.ImplCheckStrategy;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.ExtConstant;
import com.shuhao.clean.constant.MetaConstant;
import com.shuhao.clean.constant.WorkFlowConstant;
import com.shuhao.clean.utils.ExportDataUtil;
import com.shuhao.clean.utils.ExtUtils;
import com.shuhao.clean.utils.FileUpload;
import com.shuhao.clean.utils.FileUtil;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.Read2007ExcelWrapper;
import com.shuhao.clean.utils.SheetConfig;
import com.shuhao.clean.utils.workflow.WorkFlowUtil;
import com.shuhao.clean.utils.xml.XmlStrUtil;
import com.shuhao.clean.utils.xml.XmlUtil;

/**
 * 00 编辑、撤回、退回
 * 05 审批中
 * 10 审批通过
 * @Description:   创建补录页面
 * 
 * @author:         gongzhiyang
 */
@Controller
@RequestMapping("/pageManager")
public class PageManagerCtrlr extends BaseCtrlr {
	
	private String sheetName = "补录模板";
	
	@Autowired
	private IPageManagerService pageManagerService;
	@Autowired
	private ICheckService checkService;
	@Autowired
	private IPageEventService pageEventService;
	
	/**
	 * 前端调用事件方法,调试时可采用XmlUtils
	 * @param metaName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="doEvent/{templateId}/{metaName}")
	@ResponseBody
	public Object doEvent(@PathVariable String templateId,@PathVariable String metaName) throws Exception{
		try {
			String value = request.getParameter("value");
			List<Map<String, Object>> metaData = pageManagerService.getEventResults(templateId,metaName, value);
			return doJSONResponse(metaData);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 从配置文件中查询数据,调试时可采用XmlUtils
	 * @param templateId
	 * @param metaName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getStore/{templateId}/{metaName}")
	@ResponseBody
	public Object getStore(@PathVariable String templateId,@PathVariable String metaName) throws Exception{
		try {
			String value = request.getParameter("value");
			logger.info(value);
			List<Map<String, Object>> metaData = pageManagerService.getEventStore(templateId,metaName, value);
			return doJSONResponse(metaData);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 获取模板树
	 * @return
	 */
	@RequestMapping(value="getTemplateTree")
	@ResponseBody
	public Object getTemplateTree(){
		try {
			String parentNodeId = request.getParameter("nodeID");
			Map<String,Object> params  = new HashMap<String, Object>();
			params.put("nodeId", parentNodeId);
			List<Map<String, Object>> templateList = pageManagerService.getTemplateTree(params);
			return doJSONResponse(templateList);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/***
	 * 根据模板ID获取模板对应的元素
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="createMetaPage/{templateId}")
	public ModelAndView createMetaPageByTemplateId(@PathVariable String templateId,ModelAndView model) throws Exception{
		
		Object obj = session.getAttribute("workflowId_"+templateId);
		String  hasApply = "Y"; //默认有申请权限
		if(obj != null && !"".equals(obj)){
			hasApply = String.valueOf(request.getAttribute("hasApply"));
		}
		//是否有是审批权限
		String hasApprove = String.valueOf(request.getAttribute("hasApprove"));
		
//		String approve_role = "00";
//		request.getSession().setAttribute("approve_role", approve_role);
		String approve_role = "00";
		request.getSession().setAttribute("approve_role", approve_role);
		
		String path = "meta/meta_info.jsp?id="+Math.random();
		Map<String,Object> params  = new HashMap<String, Object>();
		params.put("templateId", templateId);
		params.put("tmpl_id", templateId);
		String extCode = null;
		try {
			//获取模板和子模版
			List<Map<String,Object>> templates=  pageManagerService.getChildTempl(params);
			if(templates==null||templates.size()==0){
				throw new Exception("模板未配置页面");
			}
			List<Map<String,Object>> eleList =  new ArrayList<Map<String,Object>>();
			for (Map<String,Object> map :templates) {
				//开始获取控件
				Map<String, Object> eleMap = pageManagerService.getPageEleByTemplateId(map);
				//生成增删改语句
				createSqls(eleMap);
//				eleMap.put("approve_role", approve_role);
				eleMap.put("approve_role", approve_role);
				eleList.add(eleMap);
			}
			
			//初始化document
			PageEvent pageEvent = pageEventService.getFirstPageEvent();
			XmlStrUtil.initDocument(pageEvent);
			
			extCode = ExtUtils.createPage(eleList,hasApply,hasApprove);
		} catch (UndeclaredThrowableException e) {
			extCode = " Ext.Msg.alert('错误','"+e.getUndeclaredThrowable().getMessage()+"')";
			e.printStackTrace();
		} catch (Exception e) {
			extCode = " Ext.Msg.alert('错误','"+e.getMessage()+"')";
			e.printStackTrace();
		}
		int j = path.indexOf(".jsp");
		if(j >-1 ){
			model.setViewName(path.substring(0,j));
		}
		request.setAttribute("extCode", extCode);
		
		//缓存当前模板挂载的流程ID
		request.setAttribute("templateId", templateId);
		return  model;
	}
	

	/**
	 * @param eleMap
	 */
	private void createSqls(Map<String, Object> eleMap) throws Exception {
		//先把button自定义SQL放入session
		List<Map<String,Object>> TBA = (List<Map<String, Object>>) eleMap.get("TBA");//工具条
		for (Map<String, Object> map : TBA) {
			String isCustomerSql = getStringValue(map, "is_customer_sql");
			if("Y".equals(isCustomerSql)){
				String button_id = GlobalUtil.getStringValue(map, "button_id");
				request.getSession().setAttribute("sql_"+button_id, GlobalUtil.getStringValue(map, "dml_sql"));
			}
		}
		List<Map<String,Object>> FML = (List<Map<String, Object>>) eleMap.get("FML");//表单列表
		for (Map<String, Object> map : FML) {
			List<Map<String,Object>> FRM = (List<Map<String, Object>>) map.get("FRM");
			for (Map<String, Object> map2 : FRM) {
				String isCustomerSql = getStringValue(map2, "is_customer_sql");
				if("Y".equals(isCustomerSql)){
					String button_id = GlobalUtil.getStringValue(map2, "button_id");
					request.getSession().setAttribute("sql_"+button_id,GlobalUtil.getStringValue(map, "dml_sql"));
				}
			}
		}
		
		
		//以下是生成默认模版sql
		List<Map<String,Object>> relaList = (List<Map<String, Object>>) eleMap.get("relaNames");
//		String relaMetadataNames = GlobalUtil.getStringValue(eleMap, "rela_metadata_names");//
//		String[] rela_names = null;
//		if(GlobalUtil.isNotNull(relaMetadataNames)){
//			rela_names = relaMetadataNames.split(";");
//		}
		String tableName = GlobalUtil.getStringValue(eleMap, "table_name");
		String tmpl_id = GlobalUtil.getStringValue(eleMap, "tmpl_id");
		String tableDataSource = GlobalUtil.getStringValue(eleMap, "table_data_source");
		StringBuffer insert = new StringBuffer("insert into "+tableDataSource+".").append(tableName).append(" ( ");
		StringBuffer insertValues = new StringBuffer("values ( ");
		StringBuffer update = new StringBuffer("update "+tableDataSource+".").append(tableName).append(" set ");
		StringBuffer where = new StringBuffer(" where 1=1 and business_no=#business_no");
		StringBuffer select = new StringBuffer("select ");
		StringBuffer selectWhere = new StringBuffer(" from "+tableDataSource+".").append(tableName).append(" where 1=1 ");
		StringBuffer load = new StringBuffer("select ");
		StringBuffer loadWhere = new StringBuffer(" from "+tableDataSource+".").append(tableName).append(" where 1=1 ");
		StringBuffer deleteByAcctID = new StringBuffer("delete from "+tableDataSource+"."+tableName+" where acct_id = #");
		
		
		//先在查询语句加关联元数据
		if(relaList!=null){
			for (Map<String,Object>  map: relaList) {
				String column_name = GlobalUtil.getStringValue(map, "column_name");
				String field_name = GlobalUtil.getStringValue(map, "field_name");
//				if(temp.length<2){
//					throw new Exception("模板关联字段配置格式出错！！！格式为：col_1,field_1;col_2,field_2....");
//				}
				selectWhere.append(" and ").append(column_name).append(" =#").append(field_name);
			}
		}
		List<Map<String,Object>> FDL =  (List<Map<String, Object>>) eleMap.get("FDL");//字段列表
		for (int i = 0; i < FDL.size(); i++) {
			Map<String,Object> map = FDL.get(i);
			String is_query_cond = getStringValue(map, "is_query_cond");
			String is_hidden = getStringValue(map, "is_hidden");
			String field_name = getStringValue(map, "field_name").toLowerCase();
			String column_name = getStringValue(map, "column_name").toLowerCase();
			String data_type_cd = getStringValue(map, "data_type_cd");
			String is_pk = getStringValue(map, "is_pk");
			insert.append(column_name);
			if("03".equals(data_type_cd)){
				insertValues.append("str_to_date(#").append(field_name).append(",'%Y-%m-%d')");
				select.append("date_format("+column_name+",'%Y-%m-%d') as "+field_name);
				load.append("date_format("+column_name+",'%Y-%m-%d') as "+field_name);
				update.append(column_name).append("=str_to_date(#").append(field_name).append(",'%Y-%m-%d')");
			}else{
				insertValues.append("#").append(field_name);
				select.append(column_name+" as "+field_name);
				load.append(column_name+" as "+field_name);
				update.append(column_name).append("=#").append(field_name);
			}
			if("Y".equals(is_query_cond)) {
				if("03".equals(data_type_cd)){
					selectWhere.append(" and ").append(column_name).append("=str_to_date(#").append(field_name).append(",'%Y-%m-%d')");
				}else{
					selectWhere.append(" and ").append(column_name).append("=#").append(field_name);
				}
			}
			//
			if("acct_id".equals(column_name)){
				deleteByAcctID.append(field_name);
			}
			if(i< FDL.size()-1){
				insert.append(", ");
				update.append(", ");
				insertValues.append(", ");
				select.append(", ");
				load.append(", ");
			}
		}
		
		//增加2个固定条件,flow_status_code,apply_user_id,apply_time
	/*	if(select.indexOf("flow_status_code")==-1){
			select.append(",flow_status_code as flow_status_code");
		}
		if(select.indexOf("apply_user_id")==-1){
			select.append(",apply_user_id as apply_user_id");
		}
		if(select.indexOf("apply_time")==-1){
			select.append(",to_char(apply_time,'yyyy-mm-dd') as apply_time");
		}*/
		
		insert.append(" )").append(insertValues).append(" )");
		update.append(where);
		select .append(selectWhere);
		load.append(loadWhere);
		request.getSession().setAttribute("insert_"+tmpl_id, insert.toString());
		request.getSession().setAttribute("update_"+tmpl_id, update.toString());
		request.getSession().setAttribute("select_"+tmpl_id, select.toString());
		request.getSession().setAttribute("load_"+tmpl_id, load.toString());
		request.getSession().setAttribute("deleteByAcctID_"+tmpl_id, deleteByAcctID.toString());
		//级联删除
		Map<String,Object> parentMap = eleMap;
		List<Map<String,Object>> templates = this.pageManagerService.getChildTempl(eleMap);
		List<Map<String,Object>> childList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : templates) {
			if("parent".equals(GlobalUtil.getStringValue(map, "type"))){
				parentMap = map;
			}else{
				childList.add(map);
			}
		}

		String parentTable = GlobalUtil.getStringValue(parentMap, "table_name");
		StringBuffer delete = new StringBuffer();
		for (int j = 0; j < childList.size(); j++) {
			Map<String, Object> map = childList.get(j);
			String childtableName = GlobalUtil.getStringValue(map, "table_name");
			String rela_metadata_names = GlobalUtil.getStringValue(map, "rela_metadata_names");
			String[] relaNames = rela_metadata_names.split(",");
			delete.append(" delete from "+childtableName+" where 1=1 ");
			if(relaNames==null||relaNames.length==0){
				throw new Exception("子模板与主模板为配置关联字段");
			}
			for (int i = 0; i < relaNames.length; i++) {
				String relaName = relaNames[i];
				delete.append(" and "+relaName+" in (select "+relaName+" from "+parentTable+" where business_no =#business_no)");
			}
			delete.append(";");
		}
		//delete.append(" delete from "+parentTable+" where business_no =#business_no;");
		delete.append(" delete from "+parentTable+" where business_no =#business_no;");
		
		String deleteSql ="";
		//deleteSql= "begin "+delete.toString()+" end;";
		deleteSql= delete.toString();
		request.getSession().setAttribute("delete_"+tmpl_id,deleteSql );
	}

	/**
	 * 自定义跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value="urlForword")
	public ModelAndView urlForword(ModelAndView model){
		String url = "meta/ImportFile.jsp";
		 int j = url.indexOf(".jsp");
		 if(j >-1 ){
				model.setViewName(url.substring(0,j));
			}
		 return model;
	}
	
	/**
	 * 展示模板元素数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getMetaData/{tmpl_id}")
	@ResponseBody
	public Object getMetaData(@PathVariable String tmpl_id,ModelAndView model){
		try {
			return doJSONResponse(this.queryDataForList(tmpl_id,"query"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return model;
	}
	
	/**
	 * @param param
	 * @param sqlWheres
	 * @param b
	 * @return
	 */
	private String findInArray(String param, String[] sqlWheres, boolean b) {
		for (String string : sqlWheres) {
			if(b){
				if(string.indexOf(param)!=-1){
					return string;
				}
			}else{
				param = param.toLowerCase();
				String str = string.toLowerCase();
				if(str.indexOf(param)!=-1){
					return string;
				}
			}
		}
		return null;
	}

	/**
	 * 操作模板元素数据(增．删．改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="executeMetaData",method=RequestMethod.POST)
	@ResponseBody
	public Object executeMetaData(@RequestParam String execType) throws Exception{
		try {
			Map<String,Object> map = getRequestParam();
			Map<String,Object> params  = new HashMap<String, Object>(); //参数
			String tmpl_id =GlobalUtil.getStringValue(map, "tmpl_id");
//			String type_000 =GlobalUtil.getStringValue(map, "type_000");
			String button_id =GlobalUtil.getStringValue(map, "button_id");
			
			//模版ID，放入到参数列表中
			params.put("tmpl_id", tmpl_id);  //模板ID
			params.put("execType", execType);  //按钮执行类型
			if (execType.equals("del")) {
				String sql = (String) request.getSession().getAttribute("sql_"+button_id);
				if(GlobalUtil.isNull(sql)){
					sql = (String) request.getSession().getAttribute("delete_"+tmpl_id);
				}
				params.put("sql", sql);
				params.put("key", map.get("key"));
				pageManagerService.executeMetaData(params,this.getCurrentUser());
			}else{
				String sql =  (String)map.get("sql");
				sql = (String) request.getSession().getAttribute("sql_"+button_id);
				if(GlobalUtil.isNull(sql)){
					if (execType.equals("add")) {
						sql = (String)request.getSession().getAttribute("insert_"+tmpl_id);
					}else {
						sql =  (String)request.getSession().getAttribute("update_"+tmpl_id);
					}
				}
				String regEx="[0-9][0-9][0-9][0-9]";
				for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
					String key =  iterator.next();
					Object o = map.get(key);
					if (!key.equals("sql") && !key.contains("_")) {
						params.put(key, o);
					}else {
						Pattern p = Pattern.compile(regEx);
						Matcher m = p.matcher(key);
						if (key.split("_").length > 1 && m.find()) {
							int n = key.split("_")[0].length();
							params.put(key.substring(n+1, key.length()), o);
						}else{
							params.put(key, o);
						}
					}
				}
				params.put("sql", sql);
				
				//执行校验
				String templateId = tmpl_id;
				CheckStrategy checkSty = new CheckStrategy();
				checkSty.addParam("templateId", templateId);
				checkSty.setCheckService(checkService);
				checkSty.setData(map);
				checkSty.setTrans(true);
				checkSty.setContext(getContext());
				Map<String, Object> errors = checkSty.doCheck();
				if(!errors.isEmpty()){
					//校验不通过，设置valid_success
					request.setAttribute("valid_success", "N");
					return errors;
				}

				if(execType.equals("add")){
					//放入补录用户ID
					String new_user_id = this.getCurrentUser().getUser_id();
					params.put("apply_user_id", new_user_id);
					//新增放入business_no
					String new_business_no = pageManagerService.getBusinessNo(params);
					params.put("business_no", new_business_no);
					
					//判断当前模板是否有挂载的有流程信息
					Object obj = session.getAttribute("workflowId_"+templateId);
					
					//如果没有审批流程，设置为10 ， 否则设置为00
					/*if(obj != null && !"".equals(obj)){
						params.put("flow_status_code", WorkFlowConstant.DATA_STATUS_NEW);
					}else{
						params.put("flow_status_code", WorkFlowConstant.DATA_STATUS_DONE);
					}*/
					
					//设置参数
					request.setAttribute("new_user_id", new_user_id);
					request.setAttribute("new_business_no", new_business_no);
				}
				pageManagerService.executeMetaData(params,this.getCurrentUser());
			}
			request.setAttribute("params", params);
			return doFormSuccess("操作成功!");
		} catch (Exception e) {
			//异常标识
			request.setAttribute("hasException", 1);
			if(e.getMessage().indexOf("违反唯一约束")!=-1){
				return doFormFailure("账号已存在");
			}
			return doFormFailure("操作失败"+e.getMessage());
		}
	}
	
	
	/**
	 * 独立校验，对应前端校验按钮<br>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="validData")
	@ResponseBody
	public Object validData() throws Exception{
		try {
			Map<String,Object> map = getRequestParam();
			String tmpl_id = request.getParameter("tmpl_id");
			//表名
			String tableName = pageManagerService.getTabNameByTempId(map);
			map.put("tableName", tableName);
			//提交前执行：总分校验
			List<Map<String, Object>> accts = pageManagerService.getSubmitAccts(map);
			//校验专用
			CheckStrategy checkSty = new CheckStrategy();
			checkSty.setCheckService(checkService);
			checkSty.setContext(getContext());
			checkSty.addParam("templateId", tmpl_id);
			//循环map执行校验
			for (Map<String, Object> acctMap : accts) {
				acctMap.put("templateId", tmpl_id);
				checkSty.setData(acctMap);
				Map<String, Object> errors = checkSty.doAfterCheck();
				if(!errors.isEmpty()){
					return errors;
				}
			}
			//更新状态
			int rows = pageManagerService.updateStatus(map, tableName);
			return doSuccessInfoResponse("成功校验["+rows+"]条数据。");
		} catch (RuntimeException e) {
			e.printStackTrace();
			return doSuccessInfoResponse("校验失败"+e.getMessage());
		}
	}
	
	/**
	 * 针对当前form提交的响应
	 * @param info
	 * @return
	 */
	private Map<String, Object> doFormSuccess(String info) {
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", Boolean.valueOf(true));
		List<CallbackField> list = new ArrayList<CallbackField>();
		list.add(new CallbackField(info));
		results.put("errors", list);
		return results;
	}
	
	/**
	 * 针对当前form提交的响应
	 * @param info
	 * @return
	 */
	private Map<String, Object> doFormFailure(String info) {
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", Boolean.valueOf(false));
		List<CallbackField> list = new ArrayList<CallbackField>();
		list.add(new CallbackField(info));
		results.put("errors", list);
		return results;
	}
	 
	/**
	 *按id查询模板对应表的数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getMetaDataById",method=RequestMethod.POST)
	@ResponseBody
	public Object getMetaDataById() throws Exception{
		try {
			Map<String,Object> params  = new HashMap<String, Object>();
			String tmpl_id = request.getParameter("tmpl_id");
			String button_id = request.getParameter("button_id");
			
			String sql = (String) request.getSession().getAttribute("sql_"+button_id);
			if(GlobalUtil.isNull(sql)){
				sql = (String) request.getSession().getAttribute("load_"+tmpl_id);
			}
			
			Map<String,Object> map = getRequestParam();
			String business_no =  (String)map.get("key");
			params.put("business_no", business_no);
			StringBuffer  buffer = new StringBuffer();
			if (!sql.contains("where")) {
				buffer.append(" where 1=1 ");
			}
			for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
				String key =  iterator.next();
				buffer.append("and ").append(key).append(" = '").append(params.get(key)).append("' ");
			}
			params.put("sql", sql+buffer.toString());
			params.put("bank_org_id", this.getCurrentUser().getBank_org_id());
			List<Map<String,Object>> dataList = pageManagerService.getMetaDataById(params);
			return doJSONResponse(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("操作失败"+e.getMessage());
		}
	}
	
	/**
	 * 读取模板文件
	 * @return
	 * @throws DocumentException
	 */
	public Document XmlHandler() throws DocumentException, MalformedURLException {
		File file = new File(this.getClass().getClassLoader().getResource("").getPath()+ File.separator + "xmls" + File.separator + "ExcelTmpl.xml");
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		return document;
	}
	
	/**
	 * 判断是否在ExcelTmpl.xml中配置了子模版导入信息
	 * @param templateId
	 * @return
	 * @throws DocumentException
	 */
	public boolean hasXmlDom(String templateId) throws DocumentException, MalformedURLException {
		return XmlHandler().selectNodes("/templates/template[@id='"+templateId+"']").size() > 0;
	}
	
	/**
	 * 导入子模板文件,导入父模版文件时需要执行校验
	 * @param imp_type
	 * @param templateId 补录模版ID
	 * @param metadata_id excel模版ID
	 * @param button_id 操作按钮的ID
	 * @param relaValues
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="importTempFile",method=RequestMethod.POST)
	public Object importTemplateFile(@RequestParam String imp_type,@RequestParam String templateId,@RequestParam String metadata_id ,@RequestParam String button_id,@RequestParam String relaValues,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> paramForCol = new HashMap<String, Object>();
//		String relaValues = request.getParameter("relaValues");
		paramForCol.put("metadata_id", metadata_id);
		response.setContentType("text/html;charset=UTF-8");
		String rtnMsg = "";
		PrintWriter out = response.getWriter();
		String basePath = XmlUtil.getWebRootPath();
		//缓冲路径
		String tempPath =basePath+"/upload/temp/";
		//文件上传地址
		String uploadPath =basePath+ "/upload/template/";
		//上传文件返回文件名
		String fullFileName = FileUpload.uploadFileIo(request, response, tempPath, uploadPath);
		
		boolean isParent = relaValues == null || "".equals(relaValues) || "null".equals(relaValues);
		try {
			List<Map<String,List<List<String>>>> data = null;
			if (fullFileName.endsWith("xls")) {
				data = Read2007ExcelWrapper.readXls(fullFileName, 0, true);
			}else if(fullFileName.endsWith("xlsx")){
				data = Read2007ExcelWrapper.readXlsx(fullFileName, 0);
			}else {
				new File(fullFileName).delete();
				rtnMsg = getJsonstrResponse("文件格式有误,上传文件只支持excel文件", false);
				out.print(rtnMsg);
				out.flush();
				out.close();
				return null;
			}
			
			//主表导入sql集合
			//List<Map<String,Object>> batchSqlList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> insSqlList = new ArrayList<Map<String,Object>>();
			//批量删除sql
			List<Map<String,Object>> delSqlList = new ArrayList<Map<String,Object>>();
			
			Map<String,Object> tmplMap = new HashMap<String, Object>();
			tmplMap.put("tmpl_id", templateId);
			//updata by bixb  查询不带隐藏域
			tmplMap.put("query_hidden", "N");
			//查询导入button配置的sql
			//tmplMap.put("button_func_cd", "07");//查导入
			//Map<String, Object> impButton = this.pageManagerService.getButtonByTmplId(tmplMap);

			//获取模版关联元数据
//			List<Map<String,Object>> tmplMetaList = pageManagerService.getFDL(tmplMap);
			String relaNames = "";
			//判断当前导入的主模板还是子模板
			if(!isParent){
				//获取关联字段
				List<Map<String,Object>> tmplList = pageManagerService.getTemplateById(tmplMap);
				if(tmplList!=null&&tmplList.size()>0){
					relaNames = GlobalUtil.getStringValue(tmplList.get(0),"rela_metadata_names");
				}
				
				if(relaNames == null || relaNames.equals("")){
					return writeJsonError(out,"子模板【"+templateId+"】未配置与父的关联字段.");
				}
			}
			
			//1.如果是子模版,并且在ExcelTmpl.xml中配置了导入信息
			if(!isParent && hasXmlDom(templateId)){
				for (int i = 0; i < data.size(); i++) {
					Map<String,List<List<String>>> sheet = data.get(i);
					//开始循环sheet
					for (Iterator<String> iterator = sheet.keySet().iterator(); iterator.hasNext();) {
						String key =  iterator.next();
						Map<String,String> map = executeHandler(templateId, key);
						String delSql = GlobalUtil.parse2String(map.get("delSql"));
						String insSql = GlobalUtil.parse2String(map.get("insSql"));
						String dataRow =  GlobalUtil.parse2String(map.get("startRow"));
						String mapping =  GlobalUtil.parse2String(map.get("mapping"));
						
						if ("".equals(delSql) ||"".equals(insSql) || "".equals(dataRow) || "".equals(mapping)) {
							throw new Exception("模板映射文件配置有误!");
						}
						String[] mappings =  mapping.split(",");
						int rowNum = Integer.parseInt(dataRow);
						List<List<String>> listRow =  sheet.get(key);
						boolean isdelNull = false;
						boolean isinsNull = false;
						
						//删除
						for (int j = rowNum-1; j < listRow.size(); j++) {
							Map<String, Object> params = new HashMap<String, Object>();
							List<String> listCol = listRow.get(j);
							for (int k = 0; k < mappings.length; k++) {
								if (listCol.get(k) == null || listCol.get(k).equals("")) {
									isdelNull = true;
									break;
								}
								params.put(mappings[k], listCol.get(k));
							}
							if (!isdelNull) {
								params.put("sql", delSql);
								insSqlList.add(params);
							}
						}
						
						//插入
						for (int j = rowNum-1; j < listRow.size(); j++) {
							Map<String, Object> params = new HashMap<String, Object>();
							List<String> listCol = listRow.get(j);
							for (int k = 0; k < mappings.length; k++) {
								if (listCol.get(k) == null || listCol.get(k).equals("")) {
									isinsNull = true;
									break;
								}
								params.put(mappings[k], listCol.get(k));
							}
							if (!isinsNull) {
								params.put("sql", insSql);
								params.put("contract_no", relaValues.split(",")[0]);
								insSqlList.add(params);
							}
						}
					}
				}
			}else{
				//普通子模版，和主模版导入
				for (int m = 0; m < data.size(); m++) {
					Map<String,List<List<String>>> sheet = data.get(m);
					
					//开始循环sheet
					for (Entry<String, List<List<String>>> entry : sheet.entrySet()) {
						if(!"dic_val".equals(entry.getKey())){
							Map<String,Object> tempParaMap = new HashMap<String, Object>();
							tempParaMap.put("sheet_name", entry.getKey());
							tempParaMap.put("metadata_id", metadata_id);//Excel模板元数据id
							List<List<String>>  dataList = entry.getValue();
							List<Map<String,Object>> colList = new ArrayList<Map<String,Object>>();
							String[] mappings = null;
							
							//隐藏域默认值
							List<Map<String, Object>> hiddenValues = null;
							int rowNum = 1;
							String tableName = "";
							//导入未配置关联excel,使用默认的当前表配置的字段信息
							if(MetaConstant.DEFAULT_METADATA_ID.equals(metadata_id)){
								//默认的时候使用页面元数据的校验规则
								rowNum = 2;
								tableName = pageManagerService.getTabNameByTempId(tmplMap);
								
								colList = pageManagerService.getFDLByTmplId(tmplMap);
								mappings = colList2ColArray(colList,"field_name");
								
								//列中增加隐藏域默认值列
								hiddenValues = pageManagerService.getHiddenDefaultValue(tmplMap);
								if(hiddenValues!=null && hiddenValues.size()>0){
									colList.addAll(hiddenValues);
								}
							}else{
								//配置了excel元数据的时候，使用excel单独配置的校验规则
								colList = this.pageManagerService.getExcelColByName(tempParaMap);
								mappings = colList2ColArray(colList,"xls_col_name");
								List<Map<String,Object>> listSheet = this.pageManagerService.getExcelSheetByName(tempParaMap);
								if(listSheet!=null&&listSheet.size()>0){
									rowNum = GlobalUtil.getIntValue(listSheet.get(0),"start_row");
									tableName = GlobalUtil.getStringValue(listSheet.get(0),"table_name");
								}
							}
							
							//按字段列表生成sql，delSql,insSql
							Map<String,Object> sqls = createImpSqls(colList,tableName,relaNames,relaValues,metadata_id);
							
							for (int j =rowNum-1; j < dataList.size(); j++) {
								Map<String, Object> insParams = new HashMap<String, Object>();
								Map<String, Object> delParams = new HashMap<String, Object>();
								//0.创建校验专用的map
								Map<String, Object> validData = new HashMap<String, Object>(); 
								
								List<String> listCol = dataList.get(j);
								int k = 0;
								for (; k < listCol.size(); k++) {
									String coldata =  listCol.get(k);
									if (coldata != null && coldata.contains("[") && coldata.contains("]")) {
										String s = coldata.split("\\[")[1];
										coldata = s.split("\\]")[0];
									}
									insParams.put(mappings[k], coldata);
									delParams.put(mappings[k], coldata);
									
									//1.创建校验数据对象
									String metaId = pageManagerService.findMetaIdFromList(colList, mappings[k]);
									validData.put(metaId+"_"+mappings[k], coldata);
								}
								
								//如果params列没有填满，剩下的列赋""
								if(k < mappings.length-1 ){
									for (int i = k+1 ; i < mappings.length; i++) {
										insParams.put(mappings[i], "");
										delParams.put(mappings[i], "");
									}
								}
								
								//生成主键
								String biz_date =  String.valueOf(insParams.get("biz_date")==null ? insParams.get("int_start_date") : insParams.get("biz_date"));
								if(biz_date==null||"".equals(biz_date)){
									biz_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
								}
								String business_no = pageManagerService.getBusinessNo(insParams);
								insParams.put("business_no", business_no);
								delParams.put("business_no", business_no);
								
								insParams.put("biz_date", biz_date);
								delParams.put("biz_date", biz_date);
								//放入补录用户ID
								insParams.put("apply_user_id", this.getCurrentUser().getUser_id());
								//判断当前模板是否有挂载的有流程信息
								Object obj = session.getAttribute("workflowId_"+templateId);
								//如果没有审批流程，设置为10 ， 否则设置为00
								if(obj != null && !"".equals(obj)){
									insParams.put("flow_status_code", WorkFlowConstant.DATA_STATUS_NEW);
								}else{
									insParams.put("flow_status_code", WorkFlowConstant.DATA_STATUS_DONE);
								}
								
								//1。1。放入隐藏域默认值
								if(hiddenValues!=null && hiddenValues.size()>0){
									for (Map<String, Object> d : hiddenValues) {
										insParams.put(String.valueOf(d.get("field_name")), d.get("default_value"));
										validData.put(String.valueOf(d.get("field_id")), d.get("default_value"));
									}
								}
								
								//----------------父模版执行校验------------------------
								if(isParent){
									//2.执行校验特殊处理,放入业务日期参数
									String metaId = pageManagerService.findMetaIdFromList(colList, "biz_date");
									validData.put(metaId+"_biz_date", biz_date);
									//3.完善校验参数信息，放入隐藏数据
									pageManagerService.rebuildValidData(templateId, validData);
									//4.执行校验
									ImplCheckStrategy checkSty = new ImplCheckStrategy();
									checkSty.addParam("templateId", templateId); //补录模版ID
									checkSty.addParam("excel_id", metadata_id); //excel模版ID
									checkSty.addParam("sheet_name", entry.getKey()); //当前的sheet页	
									
									checkSty.setCheckService(checkService);
									checkSty.setData(validData);
									checkSty.setTrans(true);
									checkSty.setContext(getContext());
									checkSty.setIndex(j);
									Map<String, Object> errors = null;
									if(MetaConstant.DEFAULT_METADATA_ID.equals(metadata_id)){
										errors = checkSty.doCheck();
									}else{
										errors = checkSty.doExcelCheck();
									}
									
									if(!errors.isEmpty()){
										JSONObject json = new JSONObject(errors);
										out.print(json.toString());
										out.flush();
										out.close();
										return null;
									}
								}else{
									//如果是子模版要判断子模版的关联参数是否相等
									String[] rela_names = relaNames.split(",");
									String[] rela_values = relaValues.split(",");
									for (int i = 0; i < rela_names.length; i++) {
										String relaValue = String.valueOf(insParams.get(rela_names[i]));
										if(!rela_values[i].equals(relaValue)){
											return writeJsonError(out,"选中父模板中的数据列["+rela_names[i]+"]=["+rela_values[i]+"],与子模板传入的该列值["+relaValue+"]不相等。");
										}
									}
								}
								//----------------------校验end---------------------------------
								//导入之前做按账号做一般性校验-
								
								//获取执行sql
								insParams.put("sql", GlobalUtil.getStringValue(sqls, "insSql"));
								delParams.put("sql", GlobalUtil.getStringValue(sqls, "delSql"));
								//0覆盖导入,1增量导入,覆盖导入时先删除,后插入
								//子模版只删除一次即可
								if("0".equals(imp_type)){ 
									if(isParent){
										delSqlList.add(delParams);
									}else{
										//子模版只删除一次
										if(delSqlList.size()<1){
											delSqlList.add(delParams);
										}
									}
								}
								//添加到sql批次中
								insSqlList.add(insParams);
							}
						}
					}
				}
			}
		 
			//生成批量导入的sql，然后执行批量
			if (insSqlList.size() > 0) {
				pageManagerService.batchExecSql(insSqlList,delSqlList);
				rtnMsg = getJsonstrResponse("导入成功,共导入["+insSqlList.size()+"条记录].", true);
			}else {
				rtnMsg = getJsonstrResponse("导入失败,模板文件为空或数据填写有误", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage()!=null&&e.getMessage().indexOf("违反唯一约束")!=-1){
				rtnMsg = getJsonstrResponse("导入失败,"+e.getMessage(), false);
			}else{
				rtnMsg = getJsonstrResponse("导入失败,"+e.getMessage(), false);
			}
		}finally{
			new File(fullFileName).delete();
		}
		out.print(rtnMsg);
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 创建导入sql <br>
	 * 数据类型：01 文本;02	数值;03日期
	 * @param colList
	 * @param tableName 
	 * @param relaValues 
	 * @param relaNames 
	 * @return
	 */
	private Map<String, Object> createImpSqls(List<Map<String, Object>> colList, String tableName, String relaNames, String relaValues,String metadata_id) {
		String biz_date = "biz_date";
		//是否是主模版
		boolean isParent = relaValues == null || "".equals(relaValues) || "null".equals(relaValues);

		Map<String,Object> sqlMap = new HashMap<String, Object>();
		String[] rela_names = relaNames.split(",");
		String[] rela_values = relaValues.split(",");
		StringBuffer insSql = new StringBuffer();
		StringBuffer delSql = new StringBuffer();
		StringBuffer values = new StringBuffer();
		insSql.append("insert into ").append(tableName).append(" ( ");
		values.append(" ) values ( ");
		delSql.append("delete from ").append(tableName).append(" where 1=1 ");
		//如果关联为null,默认使用acct_id
		if(relaNames==null ||relaNames.equals("")){
			delSql.append(" and acct_id = ");
		} 
		//循环字段列表
		for (int i = 0; i < colList.size(); i++) {
			Map<String,Object> map = colList.get(i);
		
			String column_name = GlobalUtil.getStringValue(map, "column_name");
			String xls_col_name = "";
			if(MetaConstant.DEFAULT_METADATA_ID.equals(metadata_id)){
				xls_col_name = GlobalUtil.getStringValue(map, "field_name").toLowerCase();
			}else{
				xls_col_name = GlobalUtil.getStringValue(map, "xls_col_name");
			}
			String data_type_cd = GlobalUtil.getStringValue(map, "data_type_cd");
			
			insSql.append(column_name);
			if("acct_id".equals(column_name.toLowerCase())){
				delSql.append("#").append(xls_col_name);
			}
			
			int index = getInArray(column_name, rela_names, false);
			if(index!=-1){
				//添加到delSql的条件中
				if("03".equals(data_type_cd)){
					values.append("str_to_date('"+rela_values[index]+"','%Y-%m-%d') ");
					delSql.append(" and ").append(rela_names[i]).append("= str_to_date('"+rela_values[index]+"','%Y-%m-%d')");
				}else{
					values.append("'"+rela_values[index]+"'");
					delSql.append(" and ").append(rela_names[i]).append("='"+rela_values[index]+"'");
				}
			}else{
				if("03".equals(data_type_cd)){
					values.append("str_to_date(#"+xls_col_name+",'%Y-%m-%d') ");
				}else{
					values.append("#").append(xls_col_name);
				}
			}
			if(i<colList.size()-1){
				insSql.append(",");
				values.append(",");
			}
			if("biz_date".equals(column_name)){
				biz_date = xls_col_name;
			}
		}
		if(delSql.toString().endsWith("=")){
			delSql.append("''");
		}
		//添加数据状态 状态00 或空的，以及审批完成并且业务日期小于录入日期的数据可以修改
		//delSql.append(" and ((flow_status_code in ('00') or flow_status_code is null )  or (biz_date < str_to_date('#"+biz_date+"','%Y-%m-%d') and (flow_status_code in ('10'))))");
		//update by bixb #业务日期 不加单引号
		if(delSql.indexOf("flow_status_code")>-1){
			delSql.append(" and ((flow_status_code in ('00','20') or flow_status_code is null )  or (biz_date < str_to_date(#"+biz_date+",'%Y-%m-%d') and (flow_status_code in ('10'))))");
		}
		
		//放入业务编号
		if(insSql.indexOf("business_no")==-1){
			insSql.append(",").append("business_no");
			values.append(",#").append("business_no");
		}
		//放入固定字段,只针对父模版，
		//20150522:(去掉apply_user_id","flow_status_code)
		/*if(isParent){
			String[] gen_cols = new String[]{"apply_user_id","flow_status_code"};
			for (String col : gen_cols) {
				if(insSql.indexOf(col)==-1){
					insSql.append(",").append(col);
					values.append(",#").append(col);
				}
			}
		}*/
		
		sqlMap.put("insSql", insSql.append(values).append(")").toString());
		sqlMap.put("delSql", delSql.toString());
		return sqlMap;
	}

	/**
	 * 返回column_name索引
	 * @param column_name
	 * @param rela_names
	 * @param caseSen
	 */
	private int getInArray(String column_name, String[] rela_names, boolean caseSen) {
		for (int i = 0; i < rela_names.length; i++) {
			String temp = rela_names[i];
			if(!caseSen){
				column_name = column_name.toLowerCase();
				temp = temp.toLowerCase();
			}
			if(column_name.equals(temp)){
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param colList
	 * @param colName
	 * @return
	 */
	private String[] colList2ColArray(List<Map<String, Object>> colList, String colName) {
		String[] cols = new String[colList.size()];
		for (int i = 0; i < colList.size(); i++) {
			cols[i] = GlobalUtil.getStringValue(colList.get(i), colName);
		}
		return cols;
	}

	/**
	 * 导出模板文件
	 * @param metadata_id   模板ID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="exportTempFile/{tmpl_id}/{metadata_id}/{button_id}")
	public void exportTemplateFile(@PathVariable String tmpl_id,@PathVariable String metadata_id,@PathVariable String button_id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		OutputStream stream = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("metadata_id", metadata_id);
			params.put("tmpl_id", tmpl_id);

			//开始获取模板名称
			String tempName = "";
			List<Map<String,Object>> tmplList = pageManagerService.getTemplateById(params);
			if (tmplList != null && tmplList.size() > 0) {
				Map<String,Object> tmplMap = tmplList.get(0);
				tempName = String.valueOf(tmplMap.get("template_name"));
			}
		
			List<Map<String,Object>> sheetList = new ArrayList<Map<String,Object>>();
			//default为默认导出全部字段
			if(MetaConstant.DEFAULT_METADATA_ID.equals(metadata_id)){
				Map<String,Object> sheetMap = new HashMap<String, Object>();
				params.put("query_hidden", "N");
				List<Map<String,Object>> fieldList = pageManagerService.getFDLByTmplId(params);
				if (fieldList != null && fieldList.size() > 0) {
					Map<String, Object> excelConfig = pageManagerService.getExcelColByMeta(fieldList, true ,true);
					
					sheetMap.put("colName", excelConfig.get("colName"));
					sheetMap.put("excelHeadData", excelConfig.get("excelData"));
					sheetMap.put("tabName", pageManagerService.getTabNameByTempId(params));//表名
					sheetMap.put("sheet_name", tempName);//sheetname
					sheetList.add(sheetMap);
				}
			}else{
				//获取sheet页列表
				sheetList = pageManagerService.getSheetListById(params);
				for (int m = 0; m < sheetList.size(); m++) {//循环生成sheet页
					Map<String,Object> sheetMap = sheetList.get(m);
					//开始获取模板映射的表名
					String tabName = "";
					List<Map<String,Object>> tabList = pageManagerService.getSheetMappingTab(sheetMap);
					if (tabList != null && tabList.size() > 0) {
						Map<String,Object> tabMap = tabList.get(0);
						tabName = String.valueOf(tabMap.get("table_name"));
					}
					sheetMap.put("tabName", tabName);//表名
					
					//excel单元格列表
					List<Map<String,Object>> cellList = pageManagerService.getExcelCol(sheetMap);
					//获取sheet页下字段
					Map<String, Object> excelConfig = pageManagerService.getExcelColByMeta(cellList, false ,true);
					sheetMap.put("colName", excelConfig.get("colName"));
					sheetMap.put("excelHeadData", excelConfig.get("excelData"));
				}
			}
			
			//开始生成Excel模板
	        HSSFWorkbook workbook = ExportDataUtil.expTemplate(sheetList);
	        response.setContentType ("application/ms-excel") ;
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String((tempName+"_"+sheetName).getBytes("gb2312"),"iso-8859-1") + ".xls");
			stream = response.getOutputStream();
			workbook.write(stream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 导出数据文件
	 * @param templateId   模板ID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="exportDataFile/{templateId}")
	public void exportDataFile(@PathVariable String templateId,HttpServletRequest request, HttpServletResponse response) throws Exception{
		OutputStream stream = null;
		try{
			Map<String,List<String>> excelData = new LinkedHashMap<String, List<String>>();
			List<String> colName = new ArrayList<String>();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tmpl_id", templateId);
			params.put("query_hidden", "N");
			//开始获取模板名称
			String tempName = "";
			List<Map<String,Object>> tmplList = pageManagerService.getTemplateById(params);
			if (tmplList != null && tmplList.size() > 0) {
				Map<String,Object> tmplMap = tmplList.get(0);
				tempName = GlobalUtil.parse2String(tmplMap.get("template_name"));
			}
			
			//查询模版FDL
			List<Map<String,Object>> metaParamsList = pageManagerService.getFDLByTmplId(params);
			if (metaParamsList != null && metaParamsList.size() > 0) {
				//获取sheet页下字段
				Map<String, Object> excelConfig = pageManagerService.getExcelColByMeta(metaParamsList, true ,true);
				colName = (List<String>)excelConfig.get("colName");
				excelData  = (Map<String,List<String>>)excelConfig.get("excelData");
			}
			
			//导出的sheet列表
			List<SheetConfig> shCfgs = new ArrayList<SheetConfig>();
			//父sheet
			SheetConfig pshCfg = new SheetConfig();
			pshCfg.setSheetName(sheetName);
			pshCfg.setColumn(colName);
			pshCfg.setColumnData(excelData);
			pshCfg.setDataList(queryDataForList(templateId,"export"));
			shCfgs.add(pshCfg);
			
			//查询子模版
			List<Map<String, Object>> subTmpls = pageManagerService.getSubTemplateById(params);
			if(subTmpls!=null && subTmpls.size()>0){
				//父sql
				String parentSql = (String) request.getSession().getAttribute("select_"+templateId);
				
				for (Map<String, Object> map : subTmpls) {
					String subTmplId = String.valueOf(map.get("tmpl_id"));
					String template_name = String.valueOf(map.get("template_name"));
					String relaCol = String.valueOf(map.get("rela_metadata_names"));
					
					Map<String, Object> subParams = new HashMap<String, Object>();
					subParams.put("tmpl_id", subTmplId);
					subParams.put("query_hidden", "N");
					
					List<Map<String,Object>> subTempFields = pageManagerService.getFDLByTmplId(subParams);
					Map<String, Object> subExcelCols = pageManagerService.getExcelColByMeta(subTempFields, true ,true);
	
					Map<String,List<String>> subColData = (Map<String,List<String>>) subExcelCols.get("excelData");
					List<String> subColName = (List<String>)subExcelCols.get("colName");
					
					SheetConfig shCfg = new SheetConfig();
					shCfg.setSheetName(template_name);
					shCfg.setColumn(subColName);
					shCfg.setColumnData(subColData);
					shCfg.setDataList(querySubDataForList(subTmplId,relaCol,parentSql));
					shCfgs.add(shCfg);
				}
			}
			//开始生成Excel模板
	        HSSFWorkbook workbook = ExportDataUtil.exportData(shCfgs);
	        response.setContentType ("application/ms-excel") ;
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String((tempName+"_导出数据").getBytes("gb2312"),"iso-8859-1") + ".xls");
			stream = response.getOutputStream();
			workbook.write(stream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 获取模板元素关联的纬表数据(用于树型结构)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getLinkData/{dimCode}")
	@ResponseBody
	public Object getLinkData(@PathVariable String dimCode) throws Exception{
		try {
			//首选获取元素关联的纬表的sql
			Map<String,Object> params  = new HashMap<String, Object>();
			params.put("dimcode", dimCode);
			String 	rootId = request.getParameter("nodeId");
			List<Map<String, Object>> treeData = pageManagerService.getDimLinkForTree(params);
			if (treeData != null && treeData.size() > 0) {
				Map<String, Object> treeMap = treeData.get(0);
				String isTree = (String)treeMap.get("is_tree");
				String sql = (String)treeMap.get("dim_sql_expr");
				if (isTree.equals("Y")) {
					String nodeId = (String)treeMap.get("code_col_name");
					String nodeName = (String)treeMap.get("label_col_name");
					String parentNodeName = (String)treeMap.get("prt_col_name");
					if ( rootId.length() > 10) {  
						rootId = (String)treeMap.get("root_value");
					}
					String str = nodeId + " nodeId ,"+nodeName +" nodeName";
					sql = sql.replace("*", str);
					params.put("nodeId", rootId);
					if (sql.indexOf("where")>-1) {
						sql = sql + "  and "+parentNodeName + " = #nodeId order by "+nodeId;
					}else{
						sql = sql + " where 1=1 and "+parentNodeName + " = #nodeId order by "+nodeId;
					}
				}
				params.put("sql", sql);
				List<Map<String, Object>> metaData = pageManagerService.getDimLinkByComponentId(params);
				return doJSONResponse(metaData);
			}else {
				return doFailureInfoResponse("未找到对应的维护信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}

	/**
	 * 读取模板文件
	 * @param templateId
	 * @return
	 * @throws DocumentException
	 */
	public Document XmlHandler(String templateId) throws DocumentException, MalformedURLException {
		File file = new File(PageManagerCtrlr.class.getClassLoader().getResource("").getPath()+ File.separator + "xmls" + File.separator + "ExcelTmpl.xml");
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		return document;
	}
	
	/**
	 * 子模板批量导入模板操作
	 * @param templateId
	 * @param name
	 * @return
	 * @throws DocumentException
	 */
	public  Map<String,String> executeHandler(String templateId,String name) throws DocumentException, MalformedURLException {
		Map<String, String> result = new HashMap<String, String>();
		Document document = XmlHandler(templateId);
		List list = document.selectNodes("/templates/template[@id='"+templateId+"']/excels/excel/sheet[@name='"+name+"']");
		if (list.size() > 0) {
			Element element = (Element)list.get(0);
			List sqlList = element.selectNodes("sql");
			if (sqlList.size() > 0) {
				Element eleSql =  (Element)sqlList.get(0);
				Element delSql = eleSql.element("delete");
				result.put("delSql", delSql.getTextTrim());
				Element insSql = eleSql.element("insert");
				result.put("insSql", insSql.getTextTrim());
			}
			Element eleMapping = element.element("mapping");
			String mapping = eleMapping.getTextTrim();
			result.put("mapping", mapping);
			List l =  element.selectNodes("body");
			if (l.size() > 0) {
				Element element2 = (Element)l.get(0);
				Element elementData = element2.element("dataStart");
				String startRow = elementData.getText();
				result.put("startRow", startRow);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param dimCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getDimValue/{dimCode}/{id}")
	@ResponseBody
	public Object getDimValue(@PathVariable String dimCode,@PathVariable String id) throws Exception{
		try {
			//首选获取元素关联的纬表的sql
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("dimcode", dimCode);
			List<Map<String, Object>> treeData = pageManagerService.getDimLinkForTree(params);
			if (treeData != null && treeData.size() > 0) {
				Map<String, Object> treeMap = treeData.get(0);
				String isTree = (String)treeMap.get("is_tree");
				String sql = (String)treeMap.get("dim_expr");
				if (isTree.equals("Y")) {
					String nodeId = (String)treeMap.get("column_code");
					String nodeName = (String)treeMap.get("column_label");
				 
					String str = nodeId + " nodeId ,"+nodeName +" nodeName";
					sql = sql.replace("*", str);
					params.put("nodeId", id);
					if (sql.indexOf("where")>-1) {
						sql = sql + " and "+nodeId + " = #nodeId ";
					}else{
						sql = sql + " where 1=1 and "+nodeId + " = #nodeId ";
					}
				}
				params.put("sql", sql);
				params.put("id", id);//传入纬度的ID
				List<Map<String, Object>> metaData = pageManagerService.getDimLinkByComponentId(params);
				return doJSONResponse(metaData);
			}else {
				return doFailureInfoResponse("未找到对应的维护信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 组装查询的sql语句
	 * @param baseSql
	 * @param linkSql
	 * @return
	 * @throws Exception
	 */
	private String sqlFormat(String baseSql ,List<Map<String,Object>> linkSql) throws Exception{
		String sql =   baseSql + "  t ";
		if (linkSql != null && linkSql.size() > 0) {
			for (int i = 0; i < linkSql.size(); i++) {
				Map<String,Object> map = linkSql.get(i);
				String meta_name = GlobalUtil.parse2String(map.get("column_name")).toLowerCase();
				String link_cond = GlobalUtil.parse2String(map.get("joincondition"));
				if(GlobalUtil.isNull(meta_name)){
					continue;
				}
				sql = sql.replaceAll(meta_name+" +as", link_cond);
//				sql = sql + link_sql +"   ";
			}
			for (int i = 0; i < linkSql.size(); i++) {
				Map<String,Object> map = linkSql.get(i);
				String meta_name = GlobalUtil.parse2String(map.get("column_name")).toLowerCase();
				String link_sql = GlobalUtil.parse2String(map.get("joinsql"));
				if(GlobalUtil.isNull(meta_name)){
					continue;
				}
				sql = sql + link_sql +"   ";
			}
		}
		return sql;
	}
	
	
	/**
	 * 按模版ID 查询数据
	 * @param tmpl_id 
	 * @param type
	 * @param type
	 * @throws Exception
	 */
	private List<Map<String,Object>> queryDataForList(String tmpl_id,String type) throws Exception {
		StringBuffer bf = new StringBuffer();
		Object obj = session.getAttribute("workflowId_"+tmpl_id);
		if(obj != null && !"".equals(obj)){
			Object node_obj = session.getAttribute("CurrentNodeId");
			if(node_obj != null){
				List<String> currNodeIds = (List<String>)node_obj;
				for (int i = 0; i < currNodeIds.size(); i++) {
					bf.append("'").append(currNodeIds.get(i)).append("',");
				}
			}
		}else{
			bf.append("");
		}
		
		String button_id = request.getParameter("button_id");
		String selectSql = (String) request.getSession().getAttribute("sql_"+button_id);
		if(GlobalUtil.isNull(selectSql)){
			selectSql = (String) request.getSession().getAttribute("select_"+tmpl_id);
		}
			
		//是否查询
		//String isQuery = request.getParameter("isQuery");
		//子模版
		String type_000 = request.getParameter("type_000");
		//开始组装sql
		Map<String,Object> params  = new HashMap<String, Object>();
		if (!"child".equals(type_000) && !type.equals("export")) {
			params.put("ispage", true);      //若分页，启用
		}
		String baseparams = request.getParameter("baseparams");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		start = start==null||"".equals(start)?"0":start;
		limit = limit==null||"".equals(limit)?"30":limit;
		String sqlWhere = "";
		String task_status = "";
		
		if (selectSql.indexOf("where") > -1 ) {
			String[] tmp =  selectSql.split("where");
			selectSql =tmp[0];
			sqlWhere = tmp[1];
		}else if(selectSql.indexOf("WHERE") > -1){
			String[] tmp =  selectSql.split("WHERE");
			selectSql = tmp[0];
			sqlWhere = tmp[1];
		}
		sqlWhere = sqlWhere.toLowerCase();
		String[] sqlWheres = sqlWhere.split("and");
		List<String> newSqlWhere  = new ArrayList<String>();
		
		Map<String,Object> sqlPar  = new HashMap<String, Object>();
		sqlPar.put("tmpl_id", tmpl_id);
		 
		List<Map<String,Object>> linkSqlList = pageManagerService.getMetaLinkJoinSql(sqlPar);
		if(baseparams!=null){
			String[] str1 = baseparams.split("&");
			for (int i = 0; i < str1.length; i++) {
				String value = "";
				String par = str1[i];
				String[] str2 = par.split("=");
				String key = str2[0];
				if (str2.length >1) {
					value = str2[1];
				}
				String param = ""; 
				String component_type = "";
				if (key.split("_").length > 1) {
					int n = key.split("_")[0].length();
					param = key.substring(n+1, key.length());
					
					//控件ID
					String metadata_id = key.split("_")[0];
					//获取空间类型，然后判断该条件是否启用模糊查询 
					component_type = pageManagerService.getFieldType(metadata_id);
				}else if(key.split("_").length == 1){
					param = key.split("_")[0];
					if (param.equals("StatusCombo")) {
						task_status = value;
						continue;
					}
				}else{
					param = key;
				}
				//如果是单独配置查询，使用了like，注意要加''
				if(value != null && !value.equals("")){
					String condition = findInArray(param, sqlWheres, false);
					if(condition!=null){
						if(component_type.equals(ExtConstant.EXT_TEXTFIELD)){
							//查询时，如果是textfield增加模糊查询
							String conds[] = condition.split("=");
							if(conds.length==2){
								condition = conds[0]+" like '%"+ conds[1].trim() +"%'";
							}
							newSqlWhere.add(condition);
							params.put(param.toLowerCase(), URLDecoder.decode(value,"UTF-8"));
						}else{
							//与BckTrackingMetaDaoImpl 第137行 action_type = 'query' 匹配
							String conds[] = condition.split("=");
							if(conds.length==2){
								condition = conds[0]+" = "+ conds[1].replace("#"+param, "'#"+param+"'");
							}
							newSqlWhere.add(condition);
							params.put(param.toLowerCase(), URLDecoder.decode(value,"UTF-8"));
						}
					}
				}
			}
		}

		StringBuffer buffer = new StringBuffer();
		SysUserInfo currUser = getCurrentUser();
		String user_id = currUser.getUser_id();
		String bufSql = sqlFormat(selectSql, linkSqlList);
		sqlWhere = " 1=1 ";
		for (String str : newSqlWhere) {
			sqlWhere += " and "+str;
		}
		String querySql = bufSql+ " where " +sqlWhere;
		
		//添加行业控制
		if(selectSql.indexOf("biz_line_id")>-1){
			querySql = querySql+ " and (t.biz_line_id in (select b.busi_line_id from dmd_busi_line_rela b where b.parent_line_id = '"+this.getCurrentUser().getBusi_line_id()+"') or t.biz_line_id = 'B010107' or t.biz_line_id is null)";
		}
		//子模版
		if("child".equals(type_000)){
			buffer.append(querySql);
		}else{
			//取模版信息
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("tmpl_id", tmpl_id);
			List<Map<String, Object>> template = pageManagerService.getTemplateById(p);
			boolean isShared = false;
			if(template!=null && template.size()>0){
				isShared = String.valueOf(template.get(0).get("is_shared")).equals("Y");
			}
			
			//关联审批状态
			Map<String, Object> queryParams = new HashMap<String, Object>();
			if("".equals(bf.toString())){
				isShared = true;
			} 
			//不限制是否拥有申请，审批权限
			String hasApprove = request.getParameter("hasApprove");//是否有审批权限
			queryParams.put("task_status", task_status);
			queryParams.put("user_id", user_id);
			queryParams.put("nodeIds", bf.toString());
			queryParams.put("isShared", isShared);
			queryParams.put("hasApprove", hasApprove);
			buffer.append(WorkFlowUtil.getQuerySql(querySql, queryParams));
		}
		
		params.put("sql", buffer.toString());
		params.put("start", start);
		params.put("action_type",type);
		params.put("limit",limit);
		params.put("login_user_id", this.getCurrentUser().getUser_id());
		params.put("bank_org_id", this.getCurrentUser().getBank_org_id());
		List<Map<String, Object>> metaData = pageManagerService.getMetaData(GlobalUtil.lowercaseMapKey(params));
		
		params.put("ispage", "");
		//如果是查询，返回总条数
		if("query".equals(type)){
			int totalCount = pageManagerService.getMetaDataCounts(GlobalUtil.lowercaseMapKey(params));
			request.setAttribute("total",totalCount);
		}
		return metaData;
	}
	
	/**
	 * 按模版ID 查询数据
	 * @param tmpl_id 
	 * @param parentSql
	 * @param relaCol
	 * @throws Exception 
	 */
	private List<Map<String,Object>> querySubDataForList(String tmpl_id,String relaCol,String parentSql) throws Exception {
		StringBuffer bf = new StringBuffer();
		Object obj = session.getAttribute("workflowId_"+tmpl_id);
		if(obj != null && !"".equals(obj)){
			Object node_obj = session.getAttribute("CurrentNodeId");
			if(node_obj != null){
				List<String> currNodeIds = (List<String>)node_obj;
				for (int i = 0; i < currNodeIds.size(); i++) {
					bf.append("'").append(currNodeIds.get(i)).append("',");
				}
			}
		}else{
			bf.append("");
		}
		
		//开始组装sql
		Map<String,Object> params  = new HashMap<String, Object>();
		String baseparams = request.getParameter("baseparams");
		String sqlWhere = "";
		String task_status = "";
		
		if (parentSql.indexOf("where") > -1 ) {
			String[] tmp =  parentSql.split("where");
			parentSql =tmp[0];
			sqlWhere = tmp[1];
		}else if(parentSql.indexOf("WHERE") > -1){
			String[] tmp =  parentSql.split("WHERE");
			parentSql = tmp[0];
			sqlWhere = tmp[1];
		}
		sqlWhere = sqlWhere.toLowerCase();
		String[] sqlWheres = sqlWhere.split("and");
		List<String> newSqlWhere  = new ArrayList<String>();
		
		//查询条件
		if(baseparams!=null){
			String[] str1 = baseparams.split("&");
			for (int i = 0; i < str1.length; i++) {
				String value = "";
				String par = str1[i];
				String[] str2 = par.split("=");
				String key = str2[0];
				if (str2.length >1) {
					value = str2[1];
				}
				String param = ""; 
				if (key.split("_").length > 1) {
					int n = key.split("_")[0].length();
					param = key.substring(n+1, key.length());
				}else if(key.split("_").length == 1){
					param = key.split("_")[0];
					if (param.equals("StatusCombo")) {
						task_status = value;
						continue;
					}
				}else{
					param = key;
				}
				if(value != null && !value.equals("")){
					String condition = findInArray(param, sqlWheres, false);
					if(condition!=null){
						newSqlWhere.add(condition);
						params.put(param.toLowerCase(), URLDecoder.decode(value,"UTF-8"));
					}
				}
			}
		}

	
		
		SysUserInfo currUser = getCurrentUser();
		String user_id = currUser.getUser_id();
		
		sqlWhere = " 1=1 ";
		for (String str : newSqlWhere) {
			sqlWhere += " and "+str;
		}
		String querySql = parentSql+ " where " +sqlWhere;
		
		//添加行业控制
		if(parentSql.indexOf("biz_line_id")>-1){
			querySql = querySql+ "and (biz_line_id in (select b.busi_line_id from dmd_busi_line_rela b where b.parent_line_id = '"+this.getCurrentUser().getBusi_line_id()+"') or biz_line_id = 'B010107' or biz_line_id is null)";
		}
		
		//是否共享设置
		Map<String, Object> sqlPar = new HashMap<String, Object>();
		sqlPar.put("tmpl_id", tmpl_id);
		List<Map<String, Object>> template = pageManagerService.getTemplateById(sqlPar);
		boolean isShared = false;
		if(template!=null && template.size()>0){
			isShared = String.valueOf(template.get(0).get("is_shared")).equals("Y");
		}
		
		//sql缓存
		StringBuffer buffer = new StringBuffer();
		if("".equals(bf.toString())){
			buffer.append(querySql);
			buffer.append(" order by business_no desc ");
		}else{
			String hasApprove = request.getParameter("hasApprove");//是否有审批权限
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("task_status", task_status);
			queryParams.put("user_id", user_id);
			queryParams.put("nodeIds", bf.toString());
			queryParams.put("isShared", isShared);
			queryParams.put("hasApprove", hasApprove);
			
			buffer.append(WorkFlowUtil.getQuerySql(querySql, queryParams));
		}
		
		
		//2.开始拼接子模版sql
		String loadSql = (String) request.getSession().getAttribute("select_"+tmpl_id);
		
		StringBuffer conditionPart2 = new StringBuffer();
		
		//查询关联的维表
		List<Map<String,Object>> linkSqlList = pageManagerService.getMetaLinkJoinSql(sqlPar);
	
		if (loadSql.indexOf("where") > -1 ) {
			loadSql = loadSql.split("where")[0];
		}else if(loadSql.indexOf("WHERE") > -1){
			loadSql = loadSql.split("WHERE")[0];
		}
		
		String bufSql = sqlFormat(loadSql, linkSqlList);
		
		//添加行业控制
		if(loadSql.indexOf("biz_line_id")>-1){
			conditionPart2.append(" and (a.biz_line_id in (select b.busi_line_id from dmd_busi_line_rela b where b.parent_line_id = '"+this.getCurrentUser().getBusi_line_id()+"') or a.biz_line_id = 'B010107' or a.biz_line_id is null)");
		}
		
		String sql = bufSql+ " where 1=1 and t." +relaCol +" in (select r."+relaCol+" from ("+querySql+") r)";
		
		params.put("sql", sql);
		params.put("login_user_id", this.getCurrentUser().getUser_id());
		params.put("bank_org_id", this.getCurrentUser().getBank_org_id());
		List<Map<String, Object>> metaData = pageManagerService.getMetaData(GlobalUtil.lowercaseMapKey(params));
		return metaData;
	}
	
	/**
	 * 下载由 updateTmplSql 生成的初始化文件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="downInitSql")
	public Object downInitSql(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			response.setHeader("Content-Disposition", "attachment; filename=initTmpl.sql");
			response.setContentType("application/x-download;charset=UTF-8");
			
			String filePath = FileUtil.getWebRootPath()+File.separator+"upload"+File.separator+"temp"+File.separator;
			String fileName = "initTmpl.sql";
		 
			byte[] bytes=FileUtil.getBytes(new File(filePath+fileName));
			if(bytes==null||bytes.length==0){
				throw new Exception("下载日志文件出错!");
			}
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
		}catch(Exception e)
		{
			e.printStackTrace();
			
			response.setContentType("text/html; charset=UTF-8");
			for (int k = 0; k < e.getStackTrace().length; k++) {
				String log = e.getStackTrace()[k].toString();
				response.getWriter().write(log+"\n");
			}
		}
		return null;
	}
}
