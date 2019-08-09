package com.shuhao.clean.apps.sys.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rx.framework.web.json.JSONObject;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.apps.sys.service.ILoginService;
import com.shuhao.clean.apps.sys.service.IResourceService;
import com.shuhao.clean.apps.sys.service.IUserService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.LoginConstant;
import com.shuhao.clean.toolkit.log.SessionLogWriter;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.annotation.UseLog;
import com.shuhao.clean.utils.Md5Util;

/**
 * <p>Title: 登录控制类，所有controller类的父类</p>
 * <p>Description: 包含servlet api,封装部分返回map方法，通过ResponseBody返回json，<br>
 * 普通map返回json直接返回map
 * </p>
 * <p>History:</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: D&A 数浩科技</p>
 * @since 2014-8-20
 * @author gongzy
 * @version 1.0
 */
@Controller
@RequestMapping(value="/login")
public class LoginCtrlr extends BaseCtrlr implements LoginConstant {
	private Logger log = Logger.getLogger(LoginCtrlr.class);
	@Autowired
	private IUserService userService ;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private ILoginService loginService ;
	
	@Autowired
	private SessionLogWriter sessionLogWriter;
	
	/**
	 * Ajax登陆方法
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0001")
	@UseLog
	@RequestMapping(value="doLogin1")
	@ResponseBody
	public Map<String, Object> doLogin1() throws Exception {
		//获取前端登陆参数
		String user_id = request.getParameter("user_id");
		String password = request.getParameter("password");

		//JSON返回结果Map
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			//根据输入用户名查询用户列表
			SysUserInfo user = userService.findUserById(user_id);

			//判断用户不存在的情况
			if(user == null){
				log.info("不存在用户["+user_id+"]");
				return doFailureInfoResponse("不存在用户["+user_id+"]");
			}

			//验证密码是否一致
			password = Md5Util.getPasswordForMD5(password);
			if(!user.getPassword().equals(password)){
				log.info("用户["+user_id+"]密码输入错误");
				return doFailureInfoResponse("密码输入错误");
			}

			//登陆成功处理
			session.setAttribute(CURRENT_USER, user);
			//加载系统时间
			session.setAttribute(SYS_DATE, loginService.getSysDate());
			//当前月
			String currentMonth = loginService.getCurrentMonth();
			session.setAttribute(CURRENT_MONTH, currentMonth);
			//全部菜单
//			session.setAttribute("AllResource", resourceService.getAllResource());
			//用户菜单
			session.setAttribute(USER_RESOURCE, resourceService.getUserResource(user));

			//初始化年份
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("year_id",  currentMonth.substring(0, 4));
//			paramMap.put("owner_id",  user.getBank_org_id());
//			this.userService.initSysYear(paramMap);

			//登录成功之后记录登录日志
//			String loginIP = this.getUserIP(request);
//			sessionLogWriter.addSessionLog(session.getId(), user, loginIP);

			return doSuccessInfoResponse("登陆成功");
		} catch (Exception e) {
			e.printStackTrace();
			results.put("info", e.getMessage());
			return doFailureInfoResponse(e.getMessage());
		}
	}

	/**
	 * Ajax登陆方法
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0001")
	@UseLog
	@RequestMapping(value="doLogin")
	@ResponseBody
	public String doLogin() throws Exception {
		//获取前端登陆参数
//		String user_id = request.getParameter("user_id");
//		String password = request.getParameter("password");
		String user_id = "00000";
		String password ="password";

		//JSON返回结果Map
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			//根据输入用户名查询用户列表
			SysUserInfo user = userService.findUserById(user_id);
			//判断用户不存在的情况
			if(user == null){
				log.info("不存在用户["+user_id+"]");
			}
			//验证密码是否一致
			password = Md5Util.getPasswordForMD5(password);
			if(!user.getPassword().equals(password)){
				log.info("用户["+user_id+"]密码输入错误");
			}
			//登陆成功处理
			session.setAttribute(CURRENT_USER, user);
			//加载系统时间
			session.setAttribute(SYS_DATE, loginService.getSysDate());
			//当前月
			String currentMonth = loginService.getCurrentMonth();
			session.setAttribute(CURRENT_MONTH, currentMonth);
			//全部菜单
//			session.setAttribute("AllResource", resourceService.getAllResource());
			//用户菜单
			session.setAttribute(USER_RESOURCE, resourceService.getUserResource(user));

			//初始化年份
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("year_id",  currentMonth.substring(0, 4));
//			paramMap.put("owner_id",  user.getBank_org_id());
//			this.userService.initSysYear(paramMap);

			//登录成功之后记录登录日志
//			String loginIP = this.getUserIP(request);
//			sessionLogWriter.addSessionLog(session.getId(), user, loginIP);

		} catch (Exception e) {
			e.printStackTrace();
			results.put("info", e.getMessage());

		}
		((HttpServletResponse)response).sendRedirect(
				((HttpServletRequest)request).getContextPath()+"/main.jsp");
		return  null;
	}
	
	/**
	 * Ajax跨域登陆方法
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0001")
	@UseLog
	@RequestMapping(value="jsoupLogin")
	@ResponseBody
	public String jsoupLogin() throws Exception {
		//获取前端登陆参数
		String user_id = request.getParameter("user_id");
		String param = request.getParameter("callbackparam");
		Map<String, Object> result = new HashMap<String, Object>();
		
		//JSON返回结果Map
		try {
			//根据输入用户名查询用户列表
			SysUserInfo user = userService.findUserById(user_id);
			
			//判断用户不存在的情况
			if(user == null){
				log.info("不存在用户["+user_id+"]");
				result.put("status", "false");
				result.put("info", "不存在用户["+user_id+"]");
				JSONObject jsonObj = new JSONObject(result);
				return param + "(" + jsonObj.toString() + ")";
			}
			
			//登陆成功处理
			session.setAttribute(CURRENT_USER, user);
			//加载系统时间
			session.setAttribute(SYS_DATE, loginService.getSysDate());
			//当前月
			String currentMonth = loginService.getCurrentMonth();
			session.setAttribute(CURRENT_MONTH, currentMonth);
			//全部菜单
//			session.setAttribute("AllResource", resourceService.getAllResource());
			//用户菜单
			session.setAttribute(USER_RESOURCE, resourceService.getUserResource(user));
			
			//初始化年份
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("year_id",  currentMonth.substring(0, 4));
//			paramMap.put("owner_id",  user.getBank_org_id());
//			this.userService.initSysYear(paramMap);
			
			//登录成功之后记录登录日志
//			String loginIP = this.getUserIP(request);
//			sessionLogWriter.addSessionLog(session.getId(), user, loginIP);
			
			result.put("status", "true");
			JSONObject jsonObj = new JSONObject(result);
			return param + "(" + jsonObj.toString() + ")";
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "false");
			result.put("info", e.getMessage());
			JSONObject jsonObj = new JSONObject(result);
			return param + "(" + jsonObj.toString() + ")";
		}
	}
	
	/**
	 * 用户退出
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0002")
	@UseLog
	@RequestMapping(value="doLogout")
	@ResponseBody
	public Map<String, Object> doLogout(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			Enumeration<String> e = session.getAttributeNames();
			while(e.hasMoreElements()){ 
				String sessionName = e.nextElement();
				session.removeAttribute(sessionName);
			}
			logger.info("清空session完成");
			//清空Cookie
			Cookie[] cookies=request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			this.sessionLogWriter.logOutLog(session.getId());
			session.invalidate();
			return doSuccessInfoResponse("退出成功");
		} catch (Exception e) {
			e.printStackTrace();
			results.put("info", e.getMessage());
			return doFailureInfoResponse(e.getMessage());
		}
	}	
	
	/**
	 * 获取用户登录的IP地址
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String getUserIP(HttpServletRequest request) throws Exception {
		if(request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
