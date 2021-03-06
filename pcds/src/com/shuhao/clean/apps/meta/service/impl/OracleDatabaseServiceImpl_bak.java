package com.shuhao.clean.apps.meta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.service.IOracleDatabaseService;

/**
 * Oracle 数据库操作接口实现类
 * @author bixb
 *
 */
public class OracleDatabaseServiceImpl_bak implements IOracleDatabaseService {


	/**
	 * 查询指定数据库下的连接
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryDbLinkList() throws Exception{
		
		return null;	
		
	}
	
	/**
	 * 查询指定数据库下的用户
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> dataPrivUserList() throws Exception{ 
		
		return null;	
	}
	
	/**
	 * 查询指定数据库的表空间
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> tableSpaceInfoList() throws Exception{ 
		
		return null;	
	}
	
	/**
	 * 查询指定数据库和用户下的表
	 * @param owner_name
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTableList(String owner_name) throws Exception{ 
		
		return null;
	}
	
	/**
	 * 查询指定表分区信息
	 * @param owner_name
	 * @param table_name
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTablePartitionInfo(String owner_name, String table_name) throws Exception{
		return null;	
	}
	
	/***
	 * 查询指定表字段信息
	 * @param owner_name
	 * @param table_name
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTableFieldInfo(String owner_name, String table_name) throws Exception{
		return null;
	}
	
	/**
	 * 查询指定表约束
	 * @param owner_name
	 * @param table_name
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTableConsInfo(String owner_name, String table_name) throws Exception{ 
		return null;	
	}
	
	/**
	 * 查询指定表字段关系（外键）信息
	 * @param owner_name
	 * @param table_name
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTableFieldRelaInfo(String owner_name, String table_name) throws Exception{
		return null;	
	}
	
	/**
	 * 查询指定表分区键信息
	 * @param owner_name
	 * @param table_name
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTablePartFieldInfo(String owner_name, String table_name) throws Exception{ 
		return null;	
	}
	
	/**
	 * 查询指定表分区键值信息
	 * @param owner_name
	 * @param table_name
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTablePartFieldValueInfo(String owner_name, String table_name) throws Exception{
		return null;	
	}
	
}
