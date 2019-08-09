package com.shuhao.clean.apps.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.sys.entity.DBTable;
import com.shuhao.clean.apps.sys.entity.DBTableColumn;
import com.shuhao.clean.apps.sys.entity.DimTable;
import com.shuhao.clean.apps.sys.service.IDimTableService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.ServerConstant;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.annotation.UseLog;
import com.shuhao.clean.utils.PageResult;

@Controller
@RequestMapping("/managerDimTable")
public class ManagerDimTableCtrlr extends BaseCtrlr {
	
	@Autowired
	private IDimTableService dimTableService;
	
	/**
	 * 维表的维表 操作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="findDimInfo/{dimCode}")
	@ResponseBody
	public Object findDimInfo(@PathVariable String dimCode)
			throws Exception {
		try {
			List<Map<String, Object>> dataList = dimTableService.findDimInfo(dimCode);
			return doJSONResponse(dataList);
		} catch (Exception e) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}
	
	@RequestMapping(value="listDimInfo")
	@ResponseBody
	public Object listDimInfo(DimTable dimTable)
			throws Exception {
		try{
			Map<String, Object> params = getRequestParam();
			PageResult<Map<String, Object>> page = dimTableService.listDimInfoPage(params);
			return page;
		}catch(Exception e){
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();		
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}

	@UseLog
	@FunDesc(code="SYS_401")
	@RequestMapping(value="addDimInfo")
	@ResponseBody
	public Map<String, Object> addDimInfo(DimTable dimTable)
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dimTableService.addDimInfo(dimTable);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "保存失败:" + e.getMessage());
		}
		return result;
	}

	@UseLog
	@FunDesc(code="SYS_402")
	@RequestMapping(value="updateDimInfo")
	@ResponseBody
	public Map<String, Object> updateDimInfo(DimTable dimTable)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dimTableService.updateDimInfo(dimTable);
			result.put("success", Boolean.valueOf(true));			
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "保存失败:" + e.getMessage());
		}
		return result;
	}

	@UseLog
	@FunDesc(code="SYS_403")
	@RequestMapping(value="deleteDimInfo/{dimCode}")
	@ResponseBody
	public Map<String, Object> deleteDimInfo(@PathVariable String dimCode)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dimTableService.deleteDimInfo(dimCode);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "删除失败:" + e.getMessage());
		}
		return result;
	}

	/**
	 * 维表的维表 操作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryDimTableData/{table_name}")
	@ResponseBody
	public Object queryDimTableData(@PathVariable String table_name)
			throws Exception {
		try {
			Map<String, Object>  params = this.getRequestParam();
			//获取表结构  用于得到查询字段
			DBTable table = dimTableService.getTableMeta(String.valueOf(params.get("table_name")));
			StringBuffer colNames = new StringBuffer();
			List<DBTableColumn> cols =  table.getColumns();
			for (int i = 0; i < cols.size(); i++) {
				DBTableColumn col = cols.get(i);
				
				if("DATE".equalsIgnoreCase(col.getColumnType())){
					colNames.append(" to_char(").append(col.getColumnName()).append(",'yyyy-mm-dd') ")
					.append(col.getColumnName()).append(",");
				}else{
					colNames.append(col.getColumnName()).append(",");
				}
			}
			params.put("col_names", colNames.substring(0, colNames.lastIndexOf(",")));
			
			PageResult<Map<String, Object>> page = dimTableService.queryDimTableData(params);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}
	
	@RequestMapping(value="addDimTableData")
	@ResponseBody
	public Map<String, Object> addDimTableData()
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.getRequestParam();
		try {
			String fields_name = (String)params.get("fields_name");
			
			String[] fields = fields_name.split(";"); 
			StringBuffer col_name = new StringBuffer();
			StringBuffer col_value = new StringBuffer();
			
			for(int i = 0;i<fields.length;i++){
				String field = fields[i].split(",")[0];
				String field_type = fields[i].split(",")[2];
				col_name.append(field).append(",");
				if("DATE".equalsIgnoreCase(field_type)){
					col_value.append("str_to_date('").append(params.get(field)).append("','%Y-%m-%d'),");
				}else{
					col_value.append("'").append(params.get(field)).append("',");
				}
			}
			col_name = col_name.deleteCharAt(col_name.lastIndexOf(","));
			col_value =  col_value.deleteCharAt(col_value.lastIndexOf(","));
			params.put("col_name", col_name);
			params.put("col_value", col_value);
			dimTableService.addDimTableData(params);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "保存失败:" + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="editDimTableData")
	@ResponseBody
	public Map<String, Object> editDimTableData()
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.getRequestParam();
		try {
			String fields_name = (String)params.get("fields_name");
			String pk_name = (String)params.get("pk_name");
			
			String[] fields = fields_name.split(";"); 
			StringBuffer col_name_value = new StringBuffer();
			
			for(int i = 0;i<fields.length;i++){
				String field = fields[i].split(",")[0];
				String field_type = fields[i].split(",")[2];
				if("DATE".equalsIgnoreCase(field_type)){
					col_name_value.append(field).append("= str_to_date('").append(params.get(field)).append("','%Y-%m-%d') ").append(",");
				}else{
					col_name_value.append(field).append("='").append(params.get(field)).append("',");
				}
			}
			col_name_value = col_name_value.deleteCharAt(col_name_value.lastIndexOf(","));
			params.put("col_name_value", col_name_value);
			
			if(pk_name == null || "".equals(pk_name)){
				throw new Exception("主键未设置，不可执行修改操作");
			}
			
			String pk_condition = "";
			String[] pk_names = pk_name.split(",");
			for(int i = 0;i<pk_names.length;i++){
				pk_condition =  pk_condition + " and " + pk_names[i] + " = '"+params.get(pk_names[i]) + "'";
			}
			
			params.put("pk_condition", pk_condition);
			
			dimTableService.editDimTableData(params);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "保存失败:" + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="deleteDimTableData")
	@ResponseBody
	public Map<String, Object> deleteDimTableData()
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.getRequestParam();
		try {
			String pk_name = (String)params.get("pk_name");
			
			if(pk_name == null || "".equals(pk_name)){
				throw new Exception("主键未设置，不可执行删除操作");
			}
			
			String pk_condition = "";
			String[] pk_names = pk_name.split(",");
			for(int i = 0;i<pk_names.length;i++){
				pk_condition =  pk_condition + " and " + pk_names[i] + " = '"+params.get(pk_names[i]) + "'";
			}
			
			params.put("pk_condition", pk_condition);
			dimTableService.deleteDimTableData(params);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "保存失败:" + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="impDimTableData")
	@ResponseBody
	public Map<String, Object> saveDimTableDataForImp()
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.getRequestParam();
		try {
			String file_name = (String)params.get("file_name");
			String fields_name = (String)params.get("fields_name");
			String path = request.getRealPath("/");
			String tempPath = path + ServerConstant.UPLOAD_DIRCTORY +"temp/";
			params.put("file_name", tempPath + file_name);
			
			dimTableService.saveDimTableDataForImp(params);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "保存失败:" + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="getTableMeta")
	@ResponseBody
	public Object getTableMeta(String table_name)
			throws Exception {
		try {
			DBTable table = dimTableService.getTableMeta(table_name);
			return table;
		} catch (Exception e) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}
	
}
