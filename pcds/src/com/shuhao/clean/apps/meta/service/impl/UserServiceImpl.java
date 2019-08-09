package com.shuhao.clean.apps.meta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.DBUserDao;
import com.shuhao.clean.apps.meta.entity.UppUser;
import com.shuhao.clean.apps.meta.service.IDBUserService;
import com.shuhao.clean.apps.meta.service.IMetadataService;

/**
 * 
 * 类描述: 元数据[数据库用户]业务逻辑实现类
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:31:28
 */
@Service
public class UserServiceImpl implements IDBUserService {
	
	@Autowired
	private IMetadataService metadataService = null;
	
	@Autowired
	private DBUserDao dbUserDao = null;
	
	/**
	 * 添加数据库用户对象
	 * @param user
	 * @throws Exception
	 */
	public void addUser(UppUser user) throws Exception {
		this.metadataService.addMetadata(user);
		this.dbUserDao.addUser(user);
	}
	
	
	/**
	 * 根据id获取数据库用户对象
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public UppUser getUserById(String user_id) throws Exception {
		return this.dbUserDao.getUserById(user_id);
	}
	
	
	/**
	 * 保存数据库用户对象
	 * @param user
	 * @throws Exception
	 */
	public void saveUser(UppUser user) throws Exception {
		this.metadataService.saveMetadata(user);
		this.dbUserDao.saveUser(user);
	}
	
	
	/**
	 * 删除用户
	 * @param user_id
	 * @throws Exception
	 */
	public void deleteUser(String user_id) throws Exception {
		this.metadataService.deleteMetadata(user_id);
		this.dbUserDao.deleteUser(user_id);
	}
}
