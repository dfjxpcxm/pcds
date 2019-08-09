package com.shuhao.clean.apps.meta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.DatabaseDao;
import com.shuhao.clean.apps.meta.entity.UppDatabase;
import com.shuhao.clean.apps.meta.service.IDatabaseService;
import com.shuhao.clean.apps.meta.service.IMetadataService;
import com.shuhao.clean.utils.DatabaseType;

/**
 * 
 * 类描述: 元数据[数据库对象]业务逻辑实现类 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午05:03:14
 */
@Service
public class DatabaseServiceImpl implements IDatabaseService {
	
	@Autowired
	private IMetadataService metadataService = null;
	
	@Autowired
	private DatabaseDao databaseDao = null;
	
	/**
	 * 添加数据库对象
	 * @param metadata
	 * @param database
	 * @throws Exception
	 */
	public void addDatabase(UppDatabase database) throws Exception {
		this.metadataService.addMetadata(database);
		this.databaseDao.addDatabase(database);
	}
	
	
	/**
	 * 根据id获取数据库对象
	 * @param database_id
	 * @return
	 * @throws Exception
	 */
	public UppDatabase getDatabaseById(String database_id) throws Exception {
		return this.databaseDao.getDatabaseById(database_id);
	}
	
	/**
	 * 保存数据库对象
	 * @param database
	 * @throws Exception
	 */
	public void saveDatabase(UppDatabase database) throws Exception {
		this.metadataService.saveMetadata(database);
		this.databaseDao.saveDatabase(database);
	}
	
	/**
	 * 删除数据库对象
	 * @param database_id
	 * @throws Exception
	 */
	public void deleteDatabase(String database_id) throws Exception {
		this.metadataService.deleteMetadata(database_id);
		this.databaseDao.deleteDatabase(database_id);
	}
	
	/**
	 * 测试数据库连接
	 * @param database
	 * @return
	 * @throws Exception
	 */
	public boolean testConnect(UppDatabase database) throws Exception {
		String databaseTypeCode = database.getDatabase_type_cd();
		String url = database.getConnect_str();
		String testUserName = database.getTest_user_name();
		String testPassword = database.getTest_user_password();
		return DatabaseType.getValue(databaseTypeCode).testConnect(url, testUserName, testPassword);
	}
	
}
