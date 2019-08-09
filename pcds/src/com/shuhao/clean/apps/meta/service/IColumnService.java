package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppTableColumn;

/**
 * 
 * 类描述: 元数据[表的列字段]业务逻辑接口 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:40:36
 */
public interface IColumnService {
	
	/**
	 * 添加数据库列对象
	 * 
	 * @param column
	 * @throws Exception
	 */
	public void addColumn(UppTableColumn column) throws Exception;
	

	/**
	 * 根据id获取表的列对象
	 * 
	 * @param column_id
	 * @return
	 * @throws Exception
	 */
	public UppTableColumn getColumnById(String column_id) throws Exception;
	

	/**
	 * 保存表的列字段信息
	 * 
	 * @param column
	 * @throws Exception
	 */
	public void saveColumn(UppTableColumn column) throws Exception;
	
	/**
	 * 删除标的列字段信息
	 * @param column_id
	 * @throws Exception
	 */
	public void deleteColumn(String column_id) throws Exception;
	
	/**
	 * 查询表对象下面的字段列表
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	public List<UppTableColumn> listTableColumns(String table_id) throws Exception;
	
	/**
	 * 更新字段排序
	 * @param params
	 * @throws Exception
	 */
	public void updateColumnOrder(Map<String, Object> params) throws Exception;
	
	/**
	 * 根据表id删除表下面的所有列
	 * @param table_id
	 * @throws Exception
	 */
	public void deleteColumnByTableId(String table_id) throws Exception;
	
	/**
	 * 根据元数据关系查询标的列字段列表
	 * @param rela_id
	 * @return
	 * @throws Exception
	 */
	public List<UppTableColumn> listTableColumnByRela(String rela_id) throws Exception;
}
