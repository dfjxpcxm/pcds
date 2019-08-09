package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppTable;

/**
 * 
 * 类描述: 元数据[数据库表]业务逻辑接口 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:38:49
 */
public interface ITableService {
	
	/**
	 * 添加数据库表
	 * @param table
	 * @throws Exception
	 */
	public void addTable(UppTable table) throws Exception;
	
	
	/**
	 * 根据id获取数据库表对象
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	public UppTable getTableById(String table_id) throws Exception;
	
	
	/**
	 * 保存数据库表对象
	 * @param table
	 * @throws Exception
	 */
	public void saveTable(UppTable table) throws Exception;
	
	/**
	 * 删除表对象
	 * @param table_id
	 * @throws Exception
	 */
	public void deleteTable(String table_id) throws Exception;
	
	/**
	 * 同步表字段
	 * @param table_id
	 * @param overwrite
	 * @param user_id
	 * @throws Exception
	 */
	public void syncTableColumn(String table_id, boolean overwrite, String user_id) throws Exception;
	
	/**
	 * 根据数据库和用户查询出表列表
	 * @param parent_metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> listTableByDatabase(String parent_metadata_id, final String searchKey) throws Exception;
	
}
