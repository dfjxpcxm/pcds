package com.shuhao.clean.apps.sys.dao;

import com.shuhao.clean.annotation.MyBatisDao;

/**
 * 登录相关数据访问
 * @author gongzy
 *
 */
@MyBatisDao
public interface LoginDao {

	public String getSysDate() throws Exception;
	
	public String getCurrentMonth() throws Exception;
}
