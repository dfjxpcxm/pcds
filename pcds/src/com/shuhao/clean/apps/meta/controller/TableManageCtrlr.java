package com.shuhao.clean.apps.meta.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.service.ITableManageService;
import com.shuhao.clean.base.BaseCtrlr;

/**
 * 数据库表管理Action
 */
@Controller
@RequestMapping(value="tableManageAction")
public class TableManageCtrlr  extends BaseCtrlr
{
	@Autowired
	public ITableManageService tableManageService;
	
	@ResponseBody
	@RequestMapping(value="/getDataBase")
	public Object getDataBase(){
		try{
			List<Map<String, Object>> list=tableManageService.getDataBase();
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getDBUser")
	public Object getDBUser(){
		Map<String, Object> paramMap=getRequestParam();
		try{
			List<Map<String, Object>> list=tableManageService.getDBUser(paramMap);
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getLocalTable")
	public Object getLocalTable(){
		Map<String, Object> paramMap=getRequestParam();
		insertPageParamToMap(paramMap);
		try{
			List<Map<String, Object>> list=tableManageService.getLocalTable(paramMap);
			int count=tableManageService.getLocalTableCount(paramMap);
			request.setAttribute("total", count);
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getRemoteTable")
	public Object getRemoteTable(){
		//String database_name=request.getParameter("database_name");
		//String dbUser=request.getParameter("owner_name");
		Map<String, Object> paramMap=getRequestParam();
		try{
			List<Map<String, Object>> list=tableManageService.getRemoteTable(paramMap);
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getThemeList")
	public Object getThemeList(){
		try{
			List<Map<String, Object>> list=tableManageService.getThemeList();
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getConTableTypeList")
	public Object getConTableTypeList(){
		try{
			List<Map<String, Object>> list=tableManageService.getConTableTypeList();
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getLocalTableInfo")
	public Object getLocalTableInfo(){
		Map<String, Object> paramMap=getRequestParam();
		try{
			paramMap.put("table_id", getStringValue(paramMap, "table_id").trim());
			List<Map<String, Object>> list=tableManageService.getLocalTableInfo(paramMap);
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getTableInfo")
	public Object getTableInfo(){
		Map<String, Object> paramMap=getRequestParam();
		try{
			List<Map<String, Object>> list=tableManageService.getTableInfo(paramMap);
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}

	
	@ResponseBody
	@RequestMapping(value="/createTable")
	public Object createTable(){
		Map<String, Object> paramMap=getRequestParam();
		paramMap.put("user_id", getCurrentUser().getUser_id());
		paramMap.put("sysDate",  getSysDate());
		try{
			tableManageService.addTable(paramMap);
			return doSuccessInfoResponse("添加成功！");
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/disposeTableInfo")
	public Object disposeTableInfo(){
		Map<String, Object> paramMap=getRequestParam();
		paramMap.put("user_id", getCurrentUser().getUser_id());
		try{
			tableManageService.updateTableInfo(paramMap);
			return doSuccessInfoResponse("修改成功！");
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/addField")
	public Object addField(){
		Map<String, Object> paramMap=getRequestParam();
		paramMap.put("user_id", getCurrentUser().getUser_id());
		try{
			tableManageService.addField(paramMap);
			return doSuccessInfoResponse("修改成功！");
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/updateField")
	public Object updateField(){
		Map<String, Object> paramMap=getRequestParam();
		paramMap.put("user_id", getCurrentUser().getUser_id());
		try{
			tableManageService.updateField(paramMap);
			return doSuccessInfoResponse("修改成功！");
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getTableSpaceList")
	public Object getTableSpaceList(){
		Map<String, Object> paramMap=getRequestParam();
		try{
			List<Map<String, Object>> list=tableManageService.getTableSpaceList(paramMap);
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 字段的类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getDataTypeList")
	public Object getDataTypeList(){
		try{
			List<Map<String, Object>> list=tableManageService.getDataTypeList();
			return doJSONResponse(list);
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteTable")
	public Object deleteTable(){
		Map<String, Object> paramMap=getRequestParam();
		
		try{
			tableManageService.deleteTable(paramMap);
			return doSuccessInfoResponse("删除成功！");
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getTableDataCount")
	public Object getTableDataCount(){
		Map<String, Object> paramMap=getRequestParam();
		
		try{
			int count = tableManageService.getTableDataCount(paramMap);
			if(count>0){
				return doFailureInfoResponse("存在数据不能删除！");
			}
			return doSuccessInfoResponse("");
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败,原因是:"+e.getMessage());
		}
	}
	@ResponseBody
	@RequestMapping(value="/deleteField")
	public Object deleteField(){
		Map<String, Object> paramMap=getRequestParam();
		try{
			tableManageService.deleteField(paramMap);
			return doSuccessInfoResponse("删除成功！");
		}catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}

}
