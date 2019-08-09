/**
 * FileName:     QueryDsServiceImpl.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-6-15 下午2:29:19 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-6-15       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.query.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.query.dao.QueryDsMetaDao;
import com.shuhao.clean.apps.query.entity.QueryDsMeta;
import com.shuhao.clean.apps.query.entity.UserQueryDsMeta;
import com.shuhao.clean.apps.query.service.QueryDsMetaService;
import com.shuhao.clean.base.BaseJdbcService;

/**
 * @Description:   TODO
 * 
 * @author:       
 */
@Service
public class QueryDsMetaServiceImpl extends BaseJdbcService implements QueryDsMetaService {
	@Autowired
	private QueryDsMetaDao queryDsMetaDao;
	
	public List<QueryDsMeta> getDsMeta(Map<String, Object> map) {
		return this.queryDsMetaDao.getDsMeta(map);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsMetaService#getDsMetaMap(java.util.Map)
	 */
	public List<Map<String, Object>> getDsMetaMap(Map<String, Object> map) {
		List<QueryDsMeta> userMetas = queryDsMetaDao.getDsMeta(map);
		//格式化结果集
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for (QueryDsMeta userQueryDsMeta : userMetas) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("ds_id", userQueryDsMeta.getDs_id());
			row.put("field_id", userQueryDsMeta.getField_id());
			row.put("field_label", userQueryDsMeta.getField_label());
			row.put("field_type", userQueryDsMeta.getField_type());
			row.put("is_dim", getBooleanValue(userQueryDsMeta.getIs_dim()));
			row.put("dim_cd", userQueryDsMeta.getDim_cd());
			row.put("is_query", getBooleanValue(userQueryDsMeta.getIs_query()));
			row.put("link_type_cd", userQueryDsMeta.getLink_type_cd());
			row.put("is_order", getBooleanValue(userQueryDsMeta.getIs_order()));
			row.put("is_hidden", getBooleanValue(userQueryDsMeta.getIs_hidden()));
			row.put("default_value", userQueryDsMeta.getDefault_value());
			row.put("display_order", userQueryDsMeta.getDisplay_order());
			results.add(row);
		}
		
		return results;
	}
	 

	public List<Map<String, Object>> getDimSource() {
		// TODO Auto-generated method stub
		//dim_cd,dim_name
		List<Map<String, Object>> dataList = queryDsMetaDao.getDimSource();
		Map<String, Object> row = new HashMap<String, Object>();
		row.put("dim_cd", "");
		row.put("dim_name", "--无--");
		dataList.add(row);
		return this.queryDsMetaDao.getDimSource();
	}

	public void updateOrder(Map<String, Object> params) throws Exception {
		//解析参数
		String paramStr =  String.valueOf(params.get("orderParam"));
		String[] fieldArr = paramStr.split(";");
		Map<String, Object> param = new HashMap<String, Object>();
		String ds_id = "";
		String field_id = "";
		String display_order = "";
		for(int i = 0;i<fieldArr.length;i++){
			ds_id =  fieldArr[i].split(",")[0];
			field_id =  fieldArr[i].split(",")[1];
			display_order = fieldArr[i].split(",")[2];
			//更新字段表
			param.put("ds_id", ds_id);
			param.put("field_id", field_id);
			param.put("display_order", display_order);
			queryDsMetaDao.updateOrder(param);
		}
	}
 
	public List<Map<String, Object>> getUserDsMeta(Map<String, Object> map) {
		List<UserQueryDsMeta> userMetas = queryDsMetaDao.getUserDsMeta(map);
		
		//格式化结果集
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for (UserQueryDsMeta userQueryDsMeta : userMetas) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("field_id", userQueryDsMeta.getField_id());
			row.put("field_label", userQueryDsMeta.getField_label());
			row.put("is_query", getBooleanValue(userQueryDsMeta.getIs_query()));
			row.put("link_type_cd", userQueryDsMeta.getLink_type_cd());
			row.put("is_order", getBooleanValue(userQueryDsMeta.getIs_order()));
			row.put("is_hidden", getBooleanValue(userQueryDsMeta.getIs_hidden()));
			row.put("default_value", userQueryDsMeta.getDefault_value());
			results.add(row);
		}
		
		return results;
	}
	
	//格式化boolean值
	private boolean getBooleanValue(String s){
		if(s!=null && s.equals("Y")){
			return true;
		}else{
			return false;
		}
	}

	public List<Map<String,Object>> findDsMetaById(Map<String, Object> map) throws Exception {
		return queryDsMetaDao.findDsMetaById(map);
	}

	public void editDsAndMeta(Map<String, Object> dsMetas) throws Exception {
		queryDsMetaDao.editDsAndMeta(dsMetas);
	}

	public void deleteDsAndMeta(Map<String, Object> dsMetas) throws Exception {
		queryDsMetaDao.deleteDsAndMeta(dsMetas);
		
	}

	public void addDsAndMeta(Map<String, Object> dsMetas) throws Exception {
		queryDsMetaDao.addDsAndMeta(dsMetas);
		
	}

	public List<QueryDsMeta> updateDsMeta(QueryDsMeta qdm) {
		queryDsMetaDao.updateDsMeta(qdm);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsMetaService#updateUserDsMeta(com.shuhao.clean.apps.query.entity.UserQueryDsMeta)
	 */
	public void updateUserDsMeta(UserQueryDsMeta userDsMeta) throws Exception {
		queryDsMetaDao.deleteUserDsMeta(userDsMeta);
		queryDsMetaDao.addUserDsMeta(userDsMeta);
	}
	
	public void addNewUserDsMetas(Map<String, Object> params) throws Exception {
		queryDsMetaDao.addNewUserDsMetas(params);
	}
	public void deleteUserDsMeta(UserQueryDsMeta userDsMeta) throws Exception{
		queryDsMetaDao.deleteUserDsMeta(userDsMeta);
	}
	
	public void updateUserDsMetaOrder(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> mapParams = new ArrayList<Map<String,Object>>();
		StringBuffer fieldIds = new StringBuffer();
		
		String infos = String.valueOf(params.get("orderParam"));
		String metas[] = infos.split(";");
		for (int i = 0; i < metas.length; i++) {
			String [] orderMeta = metas[i].split(",");
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("userId", params.get("userId"));
			row.put("dsId", params.get("dsId"));
			row.put("fieldId", orderMeta[0]);
			row.put("displayOrder", orderMeta[1]);
			//变更顺序的字段
			if(i>0){
				fieldIds.append("','");
			}
			fieldIds.append(orderMeta[0]);
			mapParams.add(row);
		}
		
		params.put("fieldIds", fieldIds.toString());
		//批量新增
		queryDsMetaDao.addNewUserDsMetas(params);
		//更新顺序	
		for (Map<String, Object> row : mapParams) {
			queryDsMetaDao.updateUserDsMetaOrder(row);
		}
	}
}
