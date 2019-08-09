package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppDatabase;
import com.shuhao.clean.apps.meta.entity.UppDatabaseUser;
import com.shuhao.clean.apps.meta.entity.UppTablespace;

/***
 * 数据库管理接口
 * 包括数据库 、表空间、用户
 * @author bixb
 * 
 */
@MyBatisDao
public interface UppDatabaseDao {
	
	/**
	 * 获取数据库信息
	 * @param database
	 * @return
	 * @throws Exception
	 */
	public List<UppDatabase> queryUppDatabaseList(UppDatabase uppDatabase)throws Exception;
	
	/**
	 * 获取表空间信息
	 * @param space
	 * @return
	 * @throws Exception
	 */
	public List<UppTablespace> queryUppTablespaceList(UppTablespace uppTablespace)throws Exception;
	
	/**
	 * 获取数据库用户信息
	 * @param owener
	 * @return
	 * @throws Exception
	 */
	public List<UppDatabaseUser> queryUppDatabaseUserList(UppDatabaseUser uppDatabaseUser)throws Exception;
	
	/**
	 * 新增数据库记录
	 * @param baseInfo
	 * @throws Exception
	 */
	public void addUppDatabase(UppDatabase uppDatabase)throws Exception;
	
	/**
	 * 新增表空间记录
	 * @param table
	 * @throws Exception
	 */
	public void addUppTablespace(UppTablespace uppTablespace)throws Exception;

	/**
	 * 新增数据库用户记录
	 * @param user
	 * @throws Exception
	 */
	public void addUppDatabaseUser(UppDatabaseUser uppDatabaseUser)throws Exception;
	
	/**
	 * 修改数据库记录
	 * @param baseInfo
	 * @throws Exception
	 */
	public void updateUppDatabase(UppDatabase uppDatabase)throws Exception;
	
	/**
	 * 修改表空间记录
	 * @param table
	 * @throws Exception
	 */
	public void updateUppTablespace(UppTablespace uppTablespace)throws Exception;
	
	/**
	 * 修改数据库用户记录
	 * @param user
	 * @throws Exception
	 */
	public void updateUppDatabaseUser(UppDatabaseUser uppDatabaseUser)throws Exception;
	
	/**
	 * 删除数据库记录
	 * @param database
	 * @throws Exception
	 */
	public void deleteUppDatabase(UppDatabase uppDatabase)throws Exception;
	
	/**
	 * 删除表空间记录
	 * @param tablespace
	 * @throws Exception
	 */
	public void deleteUppTablespace(UppTablespace uppTablespace)throws Exception;
	
	/**
	 * 根据数据库ID删除表空间记录
	 * @param tablespace
	 * @throws Exception
	 */
	public void deleteUppTablespaceByDbId(String database_id)throws Exception;
	
	/**
	 * 删除数据库用户记录
	 * @param user
	 * @throws Exception
	 */
	public void deleteUppDatabaseUser(UppDatabaseUser uppDatabaseUser)throws Exception;
	
	/**
	 * 根据数据库ID删除数据库用户记录
	 * @param user
	 * @throws Exception
	 */
	public void deleteUppDatabaseUserByDbId(String database_id)throws Exception;
	
	/**
	 * 测试数据库是否连接正常
	 * @param database_name
	 * @param database_ip
	 * @param database_type
	 * @param owner_name
	 * @param owner_password
	 * @param post
	 * @return
	 * @throws Exception
	 */
	//public boolean JdbcConnTest(String database_name,String database_ip,String database_type,String owner_name,String owner_password,String post) throws Exception;
	
	/***
	 * 批量添加数据库信息
	 * @param spaceListInfo
	 * @param database_id
	 * @throws Exception
	 */
	//public void batchAddUppDatabase(List<UppTablespace> spaceListInfo,String database_id)throws Exception;
	
	
	/***
	 * 批量添加数据库表空间信息
	 * @param spaceListInfo
	 * @param database_id
	 * @throws Exception
	 */
	public void addBatchUppTablespace(List<Map<String, Object>> list)throws Exception;
	
	/***
	 * 批量添加数据库用户信息
	 * @param userlist
	 * @param database_id
	 * @throws Exception
	 */
	public void addBatchUppDatabaseUser(List<Map<String, Object>> list)throws Exception;
	
}
