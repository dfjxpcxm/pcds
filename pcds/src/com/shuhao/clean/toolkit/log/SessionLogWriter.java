package com.shuhao.clean.toolkit.log;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.toolkit.log.dao.SessionLogWriterDao;

/**
 * 登录日志写入组件
 * 
 * @author gongzy
 *
 */
@Component
public class SessionLogWriter {
	
	private String sessionInvalidTime = "30";//session失效时间(分钟)
	
	@Autowired
	private SessionLogWriterDao sessionLogWriterDao ;
	
	public void addSessionLog(String sessionId, SysUserInfo user,String loginIP) throws Exception {
		if(sessionId.length() > 32) {
			sessionId = sessionId.substring(0, 32);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sessionId", sessionId);
		paramMap.put("user_id", user.getUser_id());
		paramMap.put("loginIP", loginIP);
		paramMap.put("owner_id", user.getBank_org_id());
		this.sessionLogWriterDao.addSessionLog(paramMap);
	}
	
	public void logOutLog(String sessionId) throws Exception {
		if(sessionId.length() > 32) {
			sessionId = sessionId.substring(0, 32);
		}
		this.sessionLogWriterDao.writeLogOut(sessionId);
	}
	
	public void sessionDestroyLog(String sessionId) throws Exception {
		if(sessionId.length() > 32) {
			sessionId = sessionId.substring(0, 32);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sessionId", sessionId);
		paramMap.put("sessionInvalidTime", sessionInvalidTime);
		this.sessionLogWriterDao.sessionDestroyLog(paramMap);
	}
}
