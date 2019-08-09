package com.shuhao.clean.apps.meta.service;

import com.shuhao.clean.apps.meta.entity.UppDatabase;

/**
 * 
 * 类描述: 元数据[数据库对象]业务逻辑接口
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:28:40
 */
public interface IDatabaseService {
	
	/**
	 * 添加数据库对象
	 * @param database
	 * @throws Exception
	 */
	public void addDatabase(UppDatabase database) throws Exception;
	
	/**
	 * 根据id获取数据库对象
	 * @param database_id
	 * @return
	 * @throws Exception
	 */
	public UppDatabase getDatabaseById(String database_id) throws Exception;
	
	/**
	 * 保存数据库对象
	 * @param database
	 * @throws Exception
	 */
	public void saveDatabase(UppDatabase database) throws Exception;
	
	
	/**
	 * 删除数据库对象
	 * @param database_id
	 * @throws Exception
	 */
	public void deleteDatabase(String database_id) throws Exception;
	
	/**
	 * 测试数据库连接
	 * @param database
	 * @return
	 * @throws Exception
	 */
	public boolean testConnect(UppDatabase database) throws Exception;
	
	
}
