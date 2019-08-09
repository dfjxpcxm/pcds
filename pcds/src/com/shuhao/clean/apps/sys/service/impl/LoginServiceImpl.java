package com.shuhao.clean.apps.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.sys.dao.LoginDao;
import com.shuhao.clean.apps.sys.service.ILoginService;

@Service(value = "loginService")
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private LoginDao loginDao;

	// 获取系统时间
	public String getSysDate() throws Exception {
		return loginDao.getSysDate();
	}

	// 获取当前对象
	public String getCurrentMonth() throws Exception {
		return loginDao.getCurrentMonth();
	}
}
