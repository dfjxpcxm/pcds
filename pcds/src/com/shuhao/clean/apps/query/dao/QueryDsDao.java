/**
 * FileName:     QueryDsDao.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-6-15 下午2:36:19 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-6-15       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.query.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.query.entity.QueryDs;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
@MyBatisDao
public interface QueryDsDao {
	
	public void addDs(QueryDs ds)throws Exception;
	
	public void deleteDs(QueryDs ds)throws Exception;
	
	//删除ds及关联信息
	public void deleteDsInfo(QueryDs ds)throws Exception;
	
	public void updateDs(QueryDs ds)throws Exception;
	
	public List<QueryDs> getDsList()throws Exception;
	
	public QueryDs findDsById(QueryDs ds)throws Exception;
	
	public List<Map<String, Object>> listByType(Map<String, Object> param)throws Exception;
	
	public List<Map<String, Object>> getUserList(Map<String, Object> param)throws Exception;
	
	public List<Map<String, Object>> getAllotedUserList(Map<String, Object> param)throws Exception;
	
	public int getUserListCount(Map<String, Object> param)throws Exception;
	
	public void addUserDs(Map<String, Object> param)throws Exception;
	
	public void deleteUserDs(Map<String, Object> param)throws Exception;
	
	/**
	 * 查询用户数据源
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<QueryDs> getUserDsList(Map<String, Object> param)throws Exception;
	/**
	 * 查询数据源详细信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public QueryDs getDsInfo(Map<String, Object> param)throws Exception;
	/**
	 * 查询ds关联的维度
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDsDim(Map<String, Object> param)throws Exception;
}
