package com.shuhao.clean.apps.meta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.service.IDimLinkAjaxService;
import com.shuhao.clean.apps.sys.service.IDimTableService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.PageResult;

/**
 * 维度维护
 * @author dongln
 * 
 */
@Controller
@RequestMapping("/dimLinkAjax")
public class DimLinkAjaxCtrlr extends BaseCtrlr {
	
	@Autowired
	private IDimLinkAjaxService dimLinkAjaxService;
	@Autowired
	private IDimTableService dimTableService;
	/**
	 * 查询维度
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listDimLinks")
	@ResponseBody
	public Object listDimLinks(String insertBlankRow) throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			this.insertPageParamToMap(params);
			PageResult<Map<String, Object>> result = dimLinkAjaxService.listDimLinks(params);
			if("true".equals(insertBlankRow)) {
				Map<String, Object> blankRow = new HashMap<String, Object>();
				blankRow.put("dim_cd", "");
				blankRow.put("dim_name", "--------- 维度选择 ---------");
				result.getResults().add(0, blankRow);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}
	/**
	 * 添加数据源分组字段
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public Object add() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		String is_tree = getStringValue(map, "is_tree");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if ("N".equals(is_tree)){
				map.put("prt_col_name","");
				map.put("root_value","");
			}
			this.dimLinkAjaxService.addDimLink(map);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败："+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 删除数据源分组字段
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.dimLinkAjaxService.deleteDimLink(map);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败："+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询分组对象表达式明细
	 * 
	 * @return
	 * @throws Exception	
	 */
	@RequestMapping(value="listExpressionDetail")
	@ResponseBody
	public Object listExpressionDetail() throws Exception {
		Map<String,Object> paramMap = this.getRequestParam();
		Map<String,Object> result = new HashMap<String, Object>();
		this.insertPageParamToMap(paramMap);
		try {
			List<Map<String,Object>> dimLinkList = this.dimLinkAjaxService.listDimLinks(paramMap).getResults();
			if (dimLinkList.size() > 0) {
				result.put("results", this.dimLinkAjaxService.queryFieldDetail(dimLinkList.get(0)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 修改数据源分组字段属性
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="edit")
	@ResponseBody
	public Object edit() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		String is_tree = getStringValue(map, "is_tree");
		String is_table_edit = getStringValue(map, "is_table_edit");
	
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if ("N".equals(is_tree)) {
				map.put("prt_col_name","");
				map.put("root_value","");
			}if("N".equals(is_table_edit)){
				map.put("table_name","");
				map.put("tabke_pk","");
				map.put("table_cols","");
			}
			this.dimLinkAjaxService.editDimLink(map);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功："+e.getMessage());
		}
		return result;
	}
	/**
	 * 树形显示查询
	 * 
	 * @returntoUpperCase
	 * @throws Exception
	 */
	@RequestMapping(value="expandDimLinkTree")
	@ResponseBody
	public Object expandDimLinkTree() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String nodeId = request.getParameter("parentNodeID");
		String dim_code = request.getParameter("dim_cd");
		Map<String, String> paramsMap = new HashMap<String, String>();

		paramsMap.put("nodeId", nodeId);
		paramsMap.put("dimCode", dim_code);

		try {
			result.put("results", this.dimLinkAjaxService.queryForDimTree(paramsMap));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("falure", true);
			result.put("info", e.getMessage());
		}
		return result;
	}

	/**
	 * 查询rootName
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="findRootName")
	@ResponseBody
	public Object findRootName() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String dim_code = request.getParameter("dim_cd");
		Map<String, String> paramsMap = new HashMap<String, String>();

		paramsMap.put("dimCode", dim_code);
		try{
			result.put("results", this.dimLinkAjaxService.findRootName(paramsMap));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("falure", true);
			result.put("info", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 异步校验
	 */
	@RequestMapping(value="checkLink")
	@ResponseBody
	public Object checkLink() throws Exception{
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> paramMap = this.getRequestParam();
		int v = this.dimLinkAjaxService.checkLink(paramMap);
		if(v>0){
			result.put("success",false);
			result.put("info","对不起,维护ID["+paramMap.get("dim_code")+"]已存在,请重新输入!");
		}else{
			result.put("success",true);
			result.put("info","验证成功,该维护ID可用!");
		}
		return result;
	}
	/**
	 * 添加维度明细表
	 */
	@RequestMapping(value="addDimTable")
	@ResponseBody
	public Object addDimTable() throws Exception {
		//主键列值的数组
		Map<String,Object> map = this.getRequestParam();
		String is_tree = getStringValue(map, "is_tree");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.dimTableService.addDimTableData(map);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
			if ("N".equals(is_tree)){
				map.put("prt_col_name","");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败："+e.getMessage());
		}
		return result;
	}
	/**
	 * 编辑维度明细表
	 */
	@RequestMapping(value="editDimTable")
	@ResponseBody
	public Object editDimTable() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		String is_tree = getStringValue(map, "is_tree");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.dimTableService.editDimTableData(map);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
			if ("N".equals(is_tree)){
				map.put("prt_col_name","");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败："+e.getMessage());
		}
		return result;
	}
	/**
	 * 删除维度明细表
	 */
	@RequestMapping(value="deleteDimTable")
	@ResponseBody
	public Object deleteDimTable() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		String is_tree = getStringValue(map, "is_tree");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//this.dimTableService.deleteDimTableData(map);
			this.dimTableService.batchDeleteDimTableData(map);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
			if ("N".equals(is_tree)){
				map.put("prt_col_name","");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败："+e.getMessage());
		}
		return result;
	}
	/**
	 * 得到一行维度明细类信息
	 */
	@RequestMapping(value="findOneDimTable")
	@ResponseBody
	public Object findOneDimTable() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> results=this.dimTableService.findDimTableEditCols(map);
			Map<String,Object> dimTableMap=new HashMap<String,Object>();
			if(results.size()>0){
				dimTableMap=results.get(0);
			}
			result.put("results",dimTableMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败："+e.getMessage());
		}
		return result;
	}
}
