package com.shuhao.clean.apps.meta.dao;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppTable;

/**
 * 
 * 类描述: 元数据[数据库表]dao接口 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:38:49
 */
@MyBatisDao
public interface TableDao {
	
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
	 * 删除表关联的字段类型
	 * @param table_id
	 * @throws Exception
	 */
	public void deleteTableColType(String table_id) throws Exception;
}
