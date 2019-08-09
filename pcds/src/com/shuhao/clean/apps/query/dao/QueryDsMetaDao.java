package com.shuhao.clean.apps.query.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.query.entity.QueryDsMeta;
import com.shuhao.clean.apps.query.entity.UserQueryDsMeta;

@MyBatisDao
public interface QueryDsMetaDao {
	public List<QueryDsMeta> getDsMeta(Map<String, Object> map);


	List<Map<String, Object>> getDimSource();

	/**
	 * 更新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateOrder(Map<String, Object> params) throws Exception;
	
	public List<UserQueryDsMeta> getUserDsMeta(Map<String, Object> map);
	
	public List<Map<String,Object>> findDsMetaById(Map<String, Object> map)throws Exception;
	
	public void editDsAndMeta(Map<String, Object> dsMetas)throws Exception;
	
	public void deleteDsAndMeta(Map<String, Object> dsMetasMap)throws Exception;
	
	public void addDsAndMeta(Map<String, Object> dsMetas)throws Exception;

	public void updateDsMeta(QueryDsMeta qdm);
	
	
	public void addUserDsMeta(UserQueryDsMeta userDsMeta);
	public void deleteUserDsMeta(UserQueryDsMeta userDsMeta);
	public void addNewUserDsMetas(Map<String, Object> params);
	public void updateUserDsMetaOrder(Map<String, Object> params);
}
