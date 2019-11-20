package com.shuhao.clean.apps.sys.controller;

import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shuhao.clean.apps.sys.entity.SysOrgInfo;
import com.shuhao.clean.apps.sys.synchrodata.CookieUtil;
import com.shuhao.clean.apps.sys.synchrodata.Dom4jUtil;
import com.shuhao.clean.apps.sys.synchrodata.PropertiesUtil;
import com.shuhao.clean.apps.sys.synchrodata.SynchronizedDataConstants;
import com.shuhao.clean.apps.sys.synchrodata.WebClient;
import com.shuhao.clean.utils.StringUtil;
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

import org.apache.commons.lang3.StringUtils;

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
//        String user_id ="";

		//获取前端登陆参数
		String userId = getCasLoginUsername();
		if(null == userId || "".equals(userId) || "null".equals(userId)){
			userId = CookieUtil.getValue(request, SynchronizedDataConstants.CAS_LOGIN_USER);
			if (StringUtil.isNullStr(userId)){
				userId = "00000";
				System.out.println("*********************取得cas用户名失败，设置为默认管理员【admin】！------------------");
			}
		}

		//根据输入用户名查询用户列表
		SysUserInfo user = userService.findUserById(userId);
		if(user == null){
			List<Map<String,Object>> list = this.getPortalUser(userId);
			if(null !=list && list.size()>0){
				//同步用户数据
				getUserInfo(list);
			}
			//同步机构数据
			List<Map<String,Object>> orgList = this.getPortalOrgInfo(userId);
			if(null !=orgList && orgList.size()>0){
				//同数据
				getOrgInfo(orgList);
			}

			user = userService.findUserById(userId);
		}
		//JSON返回结果Map
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			//根据输入用户名查询用户列表
//			SysUserInfo user = userService.findUserById(user_id);
			//判断用户不存在的情况
			if(user == null){
				log.info("不存在用户["+userId+"]");
				results.put("info", "不存在用户["+userId+"]");
				return  null;
			}
			//验证密码是否一致
	/*		password = Md5Util.getPasswordForMD5(password);
			if(!user.getPassword().equals(password)){
				log.info("用户["+user_id+"]密码输入错误");
			}*/
			//登陆成功处理
			session.setAttribute(CURRENT_USER, user);
			//加载系统时间
			session.setAttribute(SYS_DATE, loginService.getSysDate());
			//当前月
			String currentMonth = loginService.getCurrentMonth();
			session.setAttribute(CURRENT_MONTH, currentMonth);
            session.setAttribute("casUrl",this.getServerIp());
			//全部菜单
//			session.setAttribute("AllResource", resourceService.getAllResource());
			//用户菜单
			session.setAttribute(USER_RESOURCE, resourceService.getUserResource(user));

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
	 * param mapping
	 * param form
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



	private String getCasLoginUsername() {
		String username = request.getRemoteUser();
		if(StringUtils.isNotBlank(username))
			return username;
		Principal pal = request.getUserPrincipal();
		if(pal != null){
			username = pal.getName();
			if(username != null)
				return username;
		}
		Object obj = request.getAttribute("credentials");
		if(obj != null){
			return obj.toString();
		}

		return username;
	}



	public List<Map<String,Object>> getPortalUser(String user_id) throws Exception{
		WebClient web = new WebClient();
		Map<String,Object> mp = new HashMap<String, Object>();
		mp.put("arg0",getParamsByReq(request, "thisName"));
		mp.put("arg1",user_id);
		String operationName = SynchronizedDataConstants.GET_ONEUSER_WSDL_OPERATION_NAME;
		String retXml = web.getWsdlResultByCode(mp,operationName); //传入参数名，参数值，方法名
		List<Map<String,Object>> retList = Dom4jUtil.readDom4jXml(retXml);
		return retList;
	}


	public List<Map<String,Object>> getPortalOrgInfo(String user_id) throws Exception{
		WebClient web = new WebClient();
		Map<String,Object> mp = new HashMap<String, Object>();
		mp.put("arg0",user_id);
		String operationName = SynchronizedDataConstants.GET_ONE_ORG_WSDL_OPERATION_NAME;
		String retXml = web.getWsdlResultByCode(mp,operationName); //传入参数名，参数值，方法名
		List<Map<String,Object>> retList = Dom4jUtil.readDom4jXml(retXml);
		return retList;
	}

	public static String getParamsByReq(HttpServletRequest request, String name) {
		ServletContext servletContext = request.getSession().getServletContext();
		String val = servletContext.getInitParameter(name);
		return val;
	}

	public void getUserInfo(List<Map<String, Object>> retList) throws Exception{
		for (int i = 0; i < retList.size(); i++) {
			if (ifUser((String) retList.get(i).get("user_name"))){
				userService.updataUser(setUser(retList.get(i)));
			}else {
				userService.addUser(setUser(retList.get(i)));
				userService.addUserRoleInfo((String) retList.get(i).get("user_name"),DEFAULT_ROLE);
			}
		}
	}





	public void getOrgInfo(List<Map<String, Object>> retList) throws Exception{
		for (int i = 0; i < retList.size(); i++) {
			if (ifOrg((String) retList.get(i).get("dep_global_id"))){
				userService.updataOrg(setOrg(retList.get(i)));
			}else {
				userService.addOrg(setOrg(retList.get(i)));
				userService.addUserOrgInfo((String) retList.get(i).get("user_name"),(String) retList.get(i).get("DEP_GLOBAL_ID"));
			}
		}
	}

	public boolean ifUser(String user_name) throws Exception{
		SysUserInfo  userInfo = userService.findUserById(user_name);
		if(userInfo != null ){
			return true;
		}
		return false;
	}


	public boolean ifOrg(String orgid) throws Exception{
		boolean bool = userService.findOrgInfoById(orgid);
		return bool;
	}


	public SysUserInfo setUser(Map<String, Object> map){
		SysUserInfo user = new SysUserInfo();
		user.setUser_id((String) map.get("user_name"));
		user.setUser_name((String) map.get("user_real_name"));
		user.setTelephone((String) map.get("user_tel"));
		user.setAddress((String) map.get("user_addr"));
		user.setPassword("X03MO1qnZdYdgyfeuILPmQ==");
		user.setBank_org_id("350010000000");
		user.setOwner_org_name("350010000000");
		return user;
	}


	public SysOrgInfo setOrg(Map<String, Object> map){
		SysOrgInfo org = new SysOrgInfo();
		org.setBank_org_id((String) map.get("dep_global_id"));
		org.setBank_org_name((String) map.get("dep_name"));
		org.setParent_bank_org_id((String) map.get("sup_dep_global_id"));
		return org;
	}


	public  String getServerIp(){
		String casUrl = PropertiesUtil.getPropery("cas.server.ip");
		String path = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort();
		String retProtalUrl = path.concat("/pcmss");
		String logoutUrl = casUrl.concat("/logout?service=").concat(urlEncode(retProtalUrl));
		return logoutUrl;
	}

	public static String urlEncode(String str) {
		if("".equals(str) || null ==str)
			return str;
		try {
			return java.net.URLEncoder.encode(str, "UTF-8");
		} catch (Exception ex) {
			return str;
		}
	}


	public final static String DEFAULT_ROLE = "U_00002";
}
