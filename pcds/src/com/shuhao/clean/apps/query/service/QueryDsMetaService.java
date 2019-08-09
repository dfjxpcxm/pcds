/**
 * FileName:     QueryDsMetaService.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-6-15 下午2:16:32 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-6-15       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.query.service;

import java.util.List;
import java.util.Map;
import com.shuhao.clean.apps.query.entity.QueryDsMeta;
import com.shuhao.clean.apps.query.entity.UserQueryDsMeta;

/**
 * @Description:   查询数据源元数据相关service
 * 
 * @author:         gongzhiyang
 */
public interface QueryDsMetaService {

	public List<QueryDsMeta> getDsMeta(Map<String, Object> map);
	
	public List<Map<String, Object>> getDsMetaMap(Map<String, Object> map);

	List<Map<String, Object>> getDimSource();


	/**
	 * 更新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateOrder(Map<String, Object> params) throws Exception;
	
	public List<Map<String, Object>> getUserDsMeta(Map<String, Object> map);
	
	public List<Map<String,Object>> findDsMetaById(Map<String, Object> map)throws Exception;
	
	public void editDsAndMeta(Map<String, Object> dsMetas)throws Exception;
	
	public void deleteDsAndMeta(Map<String, Object> dsMetas)throws Exception;
	
	public void addDsAndMeta(Map<String, Object> dsMetas)throws Exception;

	public List<QueryDsMeta> updateDsMeta(QueryDsMeta qdm);
	
	/**
	 * 更新用户元数据
	 * @param userDsMeta
	 * @throws Exception
	 */
	public void updateUserDsMeta(UserQueryDsMeta userDsMeta)throws Exception;
	
	public void addNewUserDsMetas(Map<String, Object> params) throws Exception;
	
	public void updateUserDsMetaOrder(Map<String, Object> params) throws Exception;
	
	public void deleteUserDsMeta(UserQueryDsMeta userDsMeta) throws Exception;
	
}
