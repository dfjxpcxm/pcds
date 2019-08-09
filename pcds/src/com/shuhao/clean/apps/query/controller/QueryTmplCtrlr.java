/**
 * FileName:     QueryTmplCtrlr.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-6-12 下午4:31:13 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-6-12       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.query.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shuhao.clean.apps.query.entity.QueryDs;
import com.shuhao.clean.apps.query.entity.QueryDsMeta;
import com.shuhao.clean.apps.query.entity.UserQueryDsMeta;
import com.shuhao.clean.apps.query.service.QueryDsMetaService;
import com.shuhao.clean.apps.query.service.QueryDsService;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.LoginConstant;
import com.shuhao.clean.utils.ExportDataUtil;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.SheetConfig;
import com.shuhao.clean.utils.exttree.ExtTreeNode;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
@Controller
@RequestMapping("/queryTmpl")
public class QueryTmplCtrlr extends BaseCtrlr{

	@Autowired
	private QueryDsService queryDsService;
	@Autowired
	private QueryDsMetaService queryDsMetaService;
	/**
	 * 查询数据源树
	 * @return
	 */
	@RequestMapping(value="/getDsTree")
	public void getDsTree(QueryDs ds,HttpServletResponse response)throws Exception{
		try {
			ExtTreeNode treeNode = this.queryDsService.getDsTree(ds);
			doExtTreeJSONResponse(treeNode.getChildren(), response);
		} catch (Exception e) {
			e.printStackTrace();
			List<ExtTreeNode> eList = new ArrayList<ExtTreeNode>();
			ExtTreeNode treeNode = new ExtTreeNode();
			treeNode.setId("errorRootId");
			treeNode.setText("展示指标树失败:" + e.getMessage());
			treeNode.setLeaf(true);
			eList.add(treeNode);
			doExtTreeJSONResponse(eList, response);
		}
	}
	
	/**
	 * 查询数据源
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="findDsById")
	@ResponseBody
	public Object findDsById(QueryDs ds) throws Exception{
		try {
			List<QueryDs> dsList = new ArrayList<QueryDs>();
			dsList.add(this.queryDsService.findDsById(ds));
			return doJSONResponse(dsList);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询异常:" + e.getMessage());
		}
	}
	
	/**
	 * 查询数据源详细信息
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getDsInfo")
	@ResponseBody
	public Object getDsInfo(QueryDs ds) throws Exception{
		try {
			List<QueryDs> dsList = new ArrayList<QueryDs>();
			dsList.add(this.queryDsService.getDsInfo(ds));
			return doJSONResponse(dsList);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询异常:" + e.getMessage());
		}
	}
	
	/**
	 * 添加数据源
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addDs")
	@ResponseBody
	public Map<String,Object> addDs(QueryDs ds) throws Exception{
		try {
			this.queryDsService.addDs(ds);
			return doSuccessInfoResponse("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败:" + e.getMessage());
		}
	}
	
	/**
	 * 修改数据源
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateDs")
	@ResponseBody
	public Map<String,Object> updateFlow(QueryDs ds) throws Exception{
		try {
			this.queryDsService.updateDs(ds);
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
	@RequestMapping(value="deleteDs")
	@ResponseBody
	public Map<String,Object> deleteDs(QueryDs ds) throws Exception{
		try {
			this.queryDsService.deleteDs(ds);
			return doSuccessInfoResponse("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败:" + e.getMessage());
		}
	}
	
	
	@RequestMapping(value="checkDsSql")
	@ResponseBody
	public Map<String,Object> checkDsSql(String dsSql) throws Exception{
		Map<String,Object> param = getRequestParam();
		try {
			List<QueryDsMeta> dsMetaList = this.queryDsService.checkDsSql(dsSql);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("results", dsMetaList);
			result.put("success", true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("校验sql失败:" + e.getMessage());
		}
	}
	
	/**
	 * 得到字段集合和维度集合数据
	 * @Description: TODO
	 * @param @param dsSql
	 * @param @return
	 * @param @throws Exception   
	 * @return Map<String,Object>  
	 * @throws
	 */
	@RequestMapping(value="getDsSqlData")
	@ResponseBody
	public Object getDsSqlData(String dsSql) throws Exception{
		Map<String,Object> param = getRequestParam();
		try{
			//条件查询参数
			Object filterValue=param.get("filterValue");
			List<QueryDsMeta> newMetaList = this.queryDsService.checkDsSql(dsSql);
			Map<String,Object> dsMap=new HashMap<String, Object>();
			dsMap.put("ds_id", param.get("ds_id"));
			List<QueryDsMeta> oldMetaList=queryDsMetaService.getDsMeta(dsMap);
			//字段列表
			List<QueryDsMeta> metaList=new ArrayList<QueryDsMeta>();
			//维度列表
			List<QueryDsMeta> dimList=new ArrayList<QueryDsMeta>();
			for(QueryDsMeta newMeta:newMetaList){
				boolean flag=false;
				QueryDsMeta queryDsMeta=null;
				for(QueryDsMeta oldMeta:oldMetaList){
					if(newMeta.getField_id().toString().equals(oldMeta.getField_id().toString())){
						queryDsMeta=oldMeta;
						flag=true;
						break;
					}
				}
				//如果当前查询记录在原先中不存在,这新增
				if(flag==false){
					queryDsMeta=newMeta;
				}
				if(StringUtils.isNotBlank(queryDsMeta.getIs_dim())
						&&queryDsMeta.getIs_dim().equals("Y")){
					dimList.add(queryDsMeta);
				}
				metaList.add(queryDsMeta);
			}
			//字段列表条件查询集合
			List<QueryDsMeta> metaSearchList = new ArrayList<QueryDsMeta>();
			//维度列表条件查询集合
			List<QueryDsMeta> dimSearchList = new ArrayList<QueryDsMeta>();
			//条件查询字段列表和维度列表
			if(filterValue!=null&&filterValue.toString().trim().length()>0){
				String searchKey=String.valueOf(filterValue).toUpperCase();
				for(QueryDsMeta dsMeta:metaList){
					if(dsMeta.getField_id().toUpperCase().indexOf(searchKey)!=-1
							||dsMeta.getField_label().toUpperCase().indexOf(searchKey)!=-1){
						metaSearchList.add(dsMeta);
						//如果维度列表中字段id与字段列表一致,取出
						for(QueryDsMeta dimData:dimList){
							if(dimData.getField_id().equals(dsMeta.getField_id())){
								dimSearchList.add(dimData);
								break;
							}
						}
					}
				}
				metaList=metaSearchList;
				dimList=dimSearchList;
			}
			Map<String,Object> results=new HashMap<String, Object>();
			results.put("metaResults", metaList);
			results.put("dimResults", dimList);
			results.put("success", true);
			return results;
		}catch(Exception e){
			e.printStackTrace();
			return doFailureInfoResponse("查询失异常:" + e.getMessage());
		}
	}
	
	/**
	 * 配置数据字段
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="configDsAndMeta")
	@ResponseBody
	public Map<String,Object> configDsAndMeta() throws Exception{
		Map<String,Object> param = getRequestParam();
		try {
			String ds_id=String.valueOf(param.get("ds_id"));
			String ds_sql=String.valueOf(param.get("ds_sql"));
			//元字段json
			Object dsMetaStr=param.get("dsMetaJson");
			//选中维度json
			Object dimStr=param.get("dimJson");
			String dsMetaJson="[]";
			String dimJson="[]";
			if(dsMetaStr!=null&dsMetaStr.toString().length()>0){
				dsMetaJson=String.valueOf(dsMetaStr);
			}if(dimStr!=null&dimStr.toString().length()>0){
				dimJson=String.valueOf(dimStr);
			}
			//sql查询的jsonArray
			JSONArray jsonArray=JSONArray.fromObject(dsMetaJson);
			//维度jsonArray
			JSONArray dimJSONArray=JSONArray.fromObject(dimJson);
			QueryDs search=new QueryDs();
			search.setDs_id(ds_id);
			QueryDs queryDs=queryDsService.getDsInfo(search);
			String oldDs_sql=queryDs.getDs_sql();
			if(oldDs_sql==null||!oldDs_sql.equals(ds_sql)){
				queryDs.setDs_sql(ds_sql);
				//更新数据集记录
				queryDsService.updateDs(queryDs);
			}
			if(oldDs_sql!=null&&!oldDs_sql.equals(ds_sql)){
				Map<String, Object> detaMap=new HashMap<String, Object>();
				detaMap.put("ds_id", ds_id);
				//得到某数据源对应的所有字段
				List<QueryDsMeta> queryDsMetasList=queryDsMetaService.getDsMeta(detaMap);
				//数据库中原有的
				JSONArray dsMetaJSONArray=JSONArray.fromObject(queryDsMetasList);
				for (int i = 0; i < dsMetaJSONArray.size(); i++) {
					JSONObject oldJsonObj=(JSONObject)dsMetaJSONArray.get(i);
					boolean flag=false;//原有数据库中字段是否存在状态
					for(int j=0;j<jsonArray.size();j++){
						JSONObject newJsonObj=(JSONObject)jsonArray.get(j);
						if(oldJsonObj.get("field_id").toString().toUpperCase().
								equals(newJsonObj.get("field_id").toString().toUpperCase())){
							flag=true;
							break;
						}
					}
					//如果不存在,删除原有记录
					if(flag==false){
						detaMap.put("field_id", oldJsonObj.get("field_id"));
						queryDsMetaService.deleteDsAndMeta(detaMap);
						SysUserInfo user=(SysUserInfo)request.getSession().getAttribute(LoginConstant.CURRENT_USER);
						UserQueryDsMeta userQueryDsMeta=new UserQueryDsMeta();
						userQueryDsMeta.setDim_cd(ds_id);
						userQueryDsMeta.setField_id(oldJsonObj.get("field_id").toString());
						userQueryDsMeta.setUser_id(user.getUser_id());
						queryDsMetaService.deleteUserDsMeta(userQueryDsMeta);
					}
				}
			}
			int dimOrder=0;//排序字段
			//如果有记录,则排序字段从最大记录开始
			Map<String, Object> dsMap=new HashMap<String, Object>();
			dsMap.put("ds_id", ds_id);
			//得到数据源字段列表
			List<QueryDsMeta> dsList=queryDsMetaService.getDsMeta(dsMap);
			if(dsList.size()>0){
			 String display_order=dsList.get(dsList.size()-1).getDisplay_order();
			 dimOrder=Integer.parseInt(display_order);
			}
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObj=(JSONObject)jsonArray.get(i);
				jsonObj.put("ds_id", ds_id);
				jsonObj.put("is_dim", "N");
				jsonObj.put("dim_cd", "");
				jsonObj.put("is_query", "N");
				jsonObj.put("is_hidden", "N");
				jsonObj.put("default_value", "");
				jsonObj.put("is_order", "N");
				for(int j=0;j<dimJSONArray.size();j++){
					JSONObject dimJsonObj=(JSONObject)dimJSONArray.get(j);
					//有维度对应字段设置为Y
					if(dimJsonObj.get("field_id").toString().equals(jsonObj.get("field_id").toString())){
						jsonObj.put("is_dim", "Y");
						break;
					}
				}
				Map<String,Object> metaMap=new HashMap<String,Object>();
				metaMap.put("ds_id", ds_id);
				metaMap.put("field_id", jsonObj.get("field_id"));
				List<Map<String,Object>> metalist=GlobalUtil.lowercaseListMapKey(queryDsMetaService.findDsMetaById(metaMap));
				if(metalist.size()>0){
					Map<String,Object> editMap=metalist.get(0);
					editMap.put("field_label", jsonObj.get("field_label"));
					editMap.put("is_dim", jsonObj.get("is_dim"));
					queryDsMetaService.editDsAndMeta(editMap);
				}else{
					//没有记录时,默认顺序从1开始
					dimOrder++;
					jsonObj.put("display_order", dimOrder);
					metaMap=new HashMap<String,Object>(jsonObj);
					queryDsMetaService.addDsAndMeta(metaMap);
				}
			}
			return doSuccessInfoResponse("配置数据源成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("配置数据源失败:" + e.getMessage());
		}
	}
	
	/**
	 * 查询ds对应的元数据信息
	 * @return
	 */
	@RequestMapping(value="/getDsMeta")
	@ResponseBody
	public Object getDsMeta(){
		
		return null;
	}
	
	/******************************查询权限***************************************/
	@RequestMapping(value="listByType")
	@ResponseBody
	public Object listByType() {
		try {
			Map<String, Object> param = getRequestParam();
			List<Map<String,Object>> dataList = this.queryDsService.listByType(param);
			return doJSONResponse(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询失败:" + e.getMessage());
		}
	}
	
	/**
	 * 查询用户列表
	 * @param alloted
	 * @return
	 */
	@RequestMapping(value="getUserList/{alloted}")
	@ResponseBody
	public Object getUserList(@PathVariable String alloted){
		try {
			Map<String, Object> param = getRequestParam();
			param.put("alloted", alloted);
			return this.queryDsService.getUserList(param);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询失败:" + e.getMessage());
		}
	}
	
	/**
	 * 分配用户查询权限
	 * @return
	 */
	@RequestMapping(value="addUserDs")
	@ResponseBody
	public Object addUserDs(){
		try {
			Map<String, Object> param = getRequestParam();
			queryDsService.addUserDs(param);
			return doSuccessInfoResponse("分配用户查询权限成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("分配用户查询权失败:" + e.getMessage());
		}
	}
	
	/**
	 * 分配用户查询权限
	 * @return
	 */
	@RequestMapping(value="deleteUserDs")
	@ResponseBody
	public Object deleteUserDs(){
		try {
			Map<String, Object> param = getRequestParam();
			queryDsService.deleteUserDs(param);
			return doSuccessInfoResponse("分配用户查询权限成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("分配用户查询权失败:" + e.getMessage());
		}
	}
	
	/**
	 * 查询用户数据源树
	 * @return
	 */
	@RequestMapping(value="/getUserDsTree")
	public void getUserDsTree(QueryDs ds,HttpServletResponse response)throws Exception{
		try {
			ExtTreeNode treeNode = this.queryDsService.getUserDsTree(getCurrentUser().getUser_id());
			doExtTreeJSONResponse(treeNode.getChildren(), response);
		} catch (Exception e) {
			e.printStackTrace();
			List<ExtTreeNode> eList = new ArrayList<ExtTreeNode>();
			ExtTreeNode treeNode = new ExtTreeNode();
			treeNode.setId("errorRootId");
			treeNode.setText("展示指标树失败:" + e.getMessage());
			treeNode.setLeaf(true);
			eList.add(treeNode);
			doExtTreeJSONResponse(eList, response);
		}
	}
	
	/**
	 * 初始化页面
	 * @param dsId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="installPage/{dsId}")
	public ModelAndView installPage(@PathVariable String dsId,ModelAndView model) throws Exception{
		//查询元字段信息
		String extCode = queryDsService.getPageView(dsId, getCurrentUser());
		request.setAttribute("dsId", dsId);
		request.setAttribute("extCode", extCode);
		model.setViewName("query/user_query_page");
		return model;
	}
	
	/**
	 * 展示模板元素数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getData/{dsId}")
	@ResponseBody
	public Object getData(@PathVariable String dsId,ModelAndView model){
		try {
			return queryDsService.queryDataForList(dsId, getRequestParam(), getCurrentUser(),false);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询失败:" + e.getMessage());
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
	@RequestMapping(value="exportData/{dsId}")
	public void exportDataFile(@PathVariable String dsId,HttpServletRequest request, HttpServletResponse response) throws Exception{
		OutputStream stream = null;
		try{
			List<SheetConfig> shCfgs = new ArrayList<SheetConfig>();
			SheetConfig shCfg = queryDsService.getExportConfig(dsId, getRequestParam(), getCurrentUser());
			shCfgs.add(shCfg);
			//开始生成Excel模板
	        HSSFWorkbook workbook = ExportDataUtil.exportData(shCfgs);
	        response.setContentType ("application/ms-excel") ;
	        response.setHeader("Content-Disposition", "attachment; filename=" + new String((shCfg.getSheetName()+"_导出数据").getBytes("gb2312"),"iso-8859-1") + ".xls");
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
}
