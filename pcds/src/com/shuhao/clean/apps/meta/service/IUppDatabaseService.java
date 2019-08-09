package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppDatabase;
import com.shuhao.clean.apps.meta.entity.UppDatabaseUser;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppTablespace;

/***
 * 数据库元数据管理接口
 * 包括数据库 、表空间、用户
 * @author bixb
 * 
 */
public interface IUppDatabaseService {
	
	/**
	 * 获取数据库信息
	 * @param database
	 * @return
	 * @throws Exception
	 */
	public List<UppDatabase> queryUppDatabaseList(UppDatabase database)throws Exception;
	
	/**
	 * 获取表空间信息
	 * @param space
	 * @return
	 * @throws Exception
	 */
	public List<UppTablespace> queryUppTableSpaceList(UppTablespace space)throws Exception;
	
	/**
	 * 获取数据库用户信息
	 * @param owener
	 * @return
	 * @throws Exception
	 */
	public List<UppDatabaseUser> queryUppDatabaseUserList(UppDatabaseUser owener)throws Exception;
	
	/**
	 * 新增数据库元数据记录
	 * @param baseInfo
	 * @throws Exception
	 */
	public void addUppDatabase(UppDatabase uppDatabase,UppMetadata uppMetadata)throws Exception;
	
	/**
	 * 新增表空间元数据记录
	 * @param table
	 * @throws Exception
	 */
	public void addUppTablespace(UppTablespace uppTablespace,UppMetadata uppMetadata)throws Exception;

	/**
	 * 新增数据库用户元数据记录
	 * @param user
	 * @throws Exception
	 */
	public void addUppDatabaseUser(UppDatabaseUser user,UppMetadata uppMetadata)throws Exception;
	
	/**
	 * 修改数据库元数据记录
	 * @param baseInfo
	 * @throws Exception
	 */
	public void updateUppDatabase(UppDatabase uppDatabase,UppMetadata uppMetadata)throws Exception;
	
	/**
	 * 修改表空间元数据记录
	 * @param table
	 * @throws Exception
	 */
	public void updateUppTablespace(UppTablespace uppTablespace,UppMetadata uppMetadata)throws Exception;
	
	/**
	 * 修改数据库用户元数据记录
	 * @param user
	 * @throws Exception
	 */
	public void updateUppDatabaseUser(UppDatabaseUser user,UppMetadata uppMetadata)throws Exception;
	
	/**
	 * 删除数据库元数据记录
	 * @param database
	 * @throws Exception
	 */
	public void deleteUppDatabase(UppDatabase database,UppMetadata uppMetadata)throws Exception;
	
	/**
	 * 删除表空间元数据记录
	 * @param tablespace
	 * @throws Exception
	 */
	public void deleteUppTableSpace(UppTablespace tablespace,UppMetadata uppMetadata)throws Exception;
	
	/**
	 * 删除数据库用户元数据记录
	 * @param user
	 * @throws Exception
	 */
	public void deleteUppDatabaseUser(UppDatabaseUser user,UppMetadata uppMetadata)throws Exception;
	
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
	public boolean testJdbcConn(UppDatabase database) throws Exception;
	
	/***
	 * 批量添加数据库信息
	 * @param spaceListInfo
	 * @param database_id
	 * @throws Exception
	 */
	public void batchAddUppDatabase(List<UppTablespace> spaceListInfo,String database_id)throws Exception;
	
	
	/***
	 * 批量添加数据库表空间信息
	 * @param spaceListInfo
	 * @param database_id
	 * @throws Exception
	 */
	public void addBatchUppTablespace(List<Map<String, Object>> spaceListInfo,List<UppMetadata> uppMetadataList)throws Exception;
	
	/***
	 * 批量添加数据库用户信息
	 * @param userlist
	 * @param database_id
	 * @throws Exception
	 */
	public void addBatchUppDatabaseUser(List<Map<String, Object>> userlist,List<UppMetadata> uppMetadataList)throws Exception;
	
}
