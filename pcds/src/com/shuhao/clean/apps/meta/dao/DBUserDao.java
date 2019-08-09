package com.shuhao.clean.apps.meta.dao;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppUser;

/**
 * 
 * 类描述: 元数据[数据库用户]dao接口 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:31:28
 */
@MyBatisDao
public interface DBUserDao {
	
	/**
	 * 添加数据库用户对象
	 * @param user
	 * @throws Exception
	 */
	public void addUser(UppUser user) throws Exception;
	
	
	/**
	 * 根据id获取数据库用户对象
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public UppUser getUserById(String user_id) throws Exception;
	
	
	/**
	 * 保存数据库用户对象
	 * @param user
	 * @throws Exception
	 */
	public void saveUser(UppUser user) throws Exception;
	
	
	/**
	 * 删除用户
	 * @param user_id
	 * @throws Exception
	 */
	public void deleteUser(String user_id) throws Exception;
}
