package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

/**
 * 表及表相关维护
 * @author wangch
 */
public interface ITableManageService {
	
	/**
	 * 查询数据库
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDataBase()throws Exception;

	/**
	 * 数据库用户
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDBUser(Map<String, Object> paramMap)throws Exception;

	/**
	 * 查询本地表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getLocalTable(Map<String, Object> paramMap)throws Exception;

	/**
	 * 查询本地表数量
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int getLocalTableCount(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 查询远程表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getRemoteTable(Map<String, Object> paramMap)throws Exception;
	
	
	/**
	 * 获取本地表信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getLocalTableInfo(Map<String, Object> paramMap) throws Exception;//String table_id,

	/**
	 * 主题
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getThemeList()throws Exception;

	/**
	 * 字段类别
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getConTableTypeList()throws Exception;
	
	/**
	 * 字段数据类型
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDataTypeList()throws Exception;
	
	/**
	 * 表空间
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTableSpaceList(Map<String, Object> paramMap)throws Exception;
	

	/**
	 * 远程表信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTableInfo(Map<String, Object> paramMap)throws Exception;

	/**
	 * 添加表
	 * @param paramMap
	 * @throws Exception
	 */
	public void addTable(Map<String, Object> paramMap)throws Exception;

	/**
	 * 修改表
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateTableInfo(Map<String, Object> paramMap)throws Exception;

	/**
	 * 删除表
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteTable(Map<String, Object> paramMap)throws Exception;

	public void deleteField(Map<String, Object> paramMap)throws Exception;

	public void addField(Map<String, Object> paramMap) throws Exception;

	public void updateField(Map<String, Object> paramMap)throws Exception;
	
	public int getTableDataCount(Map<String, Object> paramMap)  throws Exception;
	
}
