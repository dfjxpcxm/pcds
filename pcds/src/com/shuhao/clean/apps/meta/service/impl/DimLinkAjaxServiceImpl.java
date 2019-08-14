package com.shuhao.clean.apps.meta.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.DimLinkAjaxDao;
import com.shuhao.clean.apps.meta.service.IDimLinkAjaxService;
import com.shuhao.clean.apps.sys.util.DataHandle;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.PageResult;
import com.shuhao.clean.utils.StringUtil;

@Service(value="dimLinkAjaxService")
public class DimLinkAjaxServiceImpl implements IDimLinkAjaxService{

	
	@Autowired
	private DimLinkAjaxDao dimLinkAjaxDao ;
	
	public PageResult<Map<String, Object>> listDimLinks(
			Map<String, Object> params){
		PageResult<Map<String,Object>> pr = new PageResult<Map<String,Object>>();
		pr.setResults(GlobalUtil.lowercaseListMapKey(dimLinkAjaxDao.listDimLinksPage(params)));
		pr.setTotalCount(dimLinkAjaxDao.listDimLinksTotal(params));
		return pr;
	}

	public void addDimLink(Map<String, Object> map) {
		dimLinkAjaxDao.addDimLink(map);
	}
	
	public List<? extends Object> queryFieldDetail(Map<String, Object> param) {
			Map<String,Object> sql = new HashMap<String, Object>();
			sql.put("sql", GlobalUtil.getStringValue(param, "dim_sql_expr").toUpperCase());
			List<Map<String, Object>> list = this.dimLinkAjaxDao.queryForList(sql);
			String isEditTable = String.valueOf(param.get("is_table_edit"));
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				//
				if(GlobalUtil.isNotNull(isEditTable) && isEditTable.equals("Y")){
					//把主键拼成联合主键pkId = id1+","+id2
					String pkStr = String.valueOf(param.get("tabke_pk"));
					String[] pkArray=pkStr.split(",");
					//主键值集合
					StringBuffer pkValues=new StringBuffer();
					for(String pk:pkArray){
						String val=DataHandle.toSqlParam(map.get(pk));//pk.toUpperCase()
						pkValues.append(val+",");
					}
					String pkValuesStr=pkValues.substring(0, pkValues.length()-1);
					map.put("pk_name", pkValuesStr);			
				}
				map.put("display_field", GlobalUtil.getStringValue(map, GlobalUtil.getStringValue(param,"label_col_name").toUpperCase()));
				map.put("value_field", GlobalUtil.getStringValue(map, GlobalUtil.getStringValue(param, "code_col_name").toUpperCase()));
			}
			return list;
	}

	public void editDimLink(Map<String, Object> map) {
		dimLinkAjaxDao.editDimLink(map);
	}	
	
	/**
	 * 显示树形查询
	 */
	public List<Map<String, Object>> queryForDimTree(Map paramsMap)
			throws Exception {
		String rootValue = (String) paramsMap.get("nodeId");
		String dimCode = (String) paramsMap.get("dimCode");
		Map<String,Object> sqlMap = new HashMap<String, Object>();
		sqlMap.put("sql", "select * from upp_dim_source where dim_cd = '"+ dimCode + "'");
		List<Map<String, Object>> list = GlobalUtil.lowercaseListMapKey(this.dimLinkAjaxDao.queryForList(sqlMap));
		if(null == list || list.size() == 0){
			return null;
		}
		Map map = (Map) list.get(0);
		String dim_expr = (String) map.get("dim_sql_expr");
		String parent_column_name = (String) map.get("prt_col_name");
		String column_code = (String) map.get("code_col_name");
		String label_col_name = (String) map.get("label_col_name");

		String sql = "";
		if (dim_expr.indexOf("where") == -1) {
			sql = dim_expr + " " + " where " + parent_column_name + " = '"
					+ rootValue + "' order by " + column_code;
		} else {
			sql = dim_expr + " " + " and " + parent_column_name + " = '"
					+ rootValue + "' order by " + column_code;
		}
		sqlMap.put("sql", sql);
		List dimlist = GlobalUtil.lowercaseListMapKey(this.dimLinkAjaxDao.queryForList(sqlMap));
		List resultList = new ArrayList();
		for (int i = 0; i < dimlist.size(); i++) {
			Map p = (Map) dimlist.get(i);
			Map newMap = new HashMap();
			newMap.put("id", p.get(column_code));
			newMap.put("name", p.get(label_col_name));
			resultList.add(newMap);
		}
		return GlobalUtil.lowercaseListMapKey(resultList);
	}

	/**
	 * 查询rootName
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findRootName(Map<String, String> paramsMap)
			throws Exception {
		String dimCode = (String) paramsMap.get("dimCode");
		Map<String,Object> sqlMap = new HashMap<String, Object>();
		sqlMap.put("sql", "select * from upp_dim_source where dim_cd = '" + dimCode + "'");
		List<Map<String, Object>> list = GlobalUtil.lowercaseListMapKey(this.dimLinkAjaxDao.queryForList(sqlMap));
		if(null == list || list.size() == 0){
			return null;
		}
		Map map = (Map) list.get(0);
		String dim_expr = (String) map.get("dim_sql_expr");
		String column_lable = (String) map.get("label_col_name");
		String rootValue = (String) map.get("root_value");
		String parent_column_name = (String) map.get("code_col_name");
		//String parent_id_field = (String) map.get("parent_id_field");

		String sql = "";
		if (dim_expr.indexOf("where") == -1) {
			sql = dim_expr + " " + " where " + parent_column_name + " = '"
					+ rootValue + "' ";
		} else {
			sql = dim_expr + " " + " and " + parent_column_name + " = '"
					+ rootValue + "'";
		}
		sqlMap.put("sql", sql);
		List dimlist = GlobalUtil.lowercaseListMapKey(dimLinkAjaxDao.queryForList(sqlMap));
		List resultList = new ArrayList();
		
		if(dimlist==null || dimlist.size()==0){
			Map newMap = new HashMap();
			newMap.put("id", rootValue);
			newMap.put("text", "["+rootValue+"]根节点");
			resultList.add(newMap);
		}else{
			for (int i = 0; i < dimlist.size(); i++) {
				Map p = (Map) dimlist.get(i);
				Map newMap = new HashMap();
				newMap.put("id", p.get(parent_column_name));
				newMap.put("text", "["+rootValue+"]"+p.get(column_lable));
				resultList.add(newMap);
			}
		}

		return GlobalUtil.lowercaseListMapKey(resultList);
	}

	public int checkLink(Map<String, Object> paramMap) throws Exception {
		return this.dimLinkAjaxDao.listDimLinksTotal(paramMap);
	}

	public void deleteDimLink(Map<String, Object> map) {
		this.dimLinkAjaxDao.deleteDimLink(map);
	}

}
