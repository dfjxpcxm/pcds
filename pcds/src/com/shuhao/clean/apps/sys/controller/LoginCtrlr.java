package com.shuhao.clean.apps.sys.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.shuhao.clean.utils.CommonUtil;
import com.shuhao.clean.utils.CommonUtils;
import com.shuhao.clean.utils.StringUtil;
import org.apache.axis2.databinding.types.soapencoding.DateTime;
//import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
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

import org.slf4j.Logger;


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
//	private Logger log = Logger.getLogger(LoginCtrlr.class);

	static Logger log = LoggerFactory.getLogger(LoginCtrlr.class);
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
		//获取CAS前端登陆参数
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
				//同步用户数据
			List<Map<String,Object>> list = this.getPortalUser(userId);
			if(null !=list && list.size()>0){
				//增加用户数据及用户与角色数据
				getUserInfo(list);
			}
			//同步机构数据
			List<Map<String,Object>> orgList = this.getPortalOrgInfo(userId);
			if(null !=orgList && orgList.size()>0){
				//增加机构数据及用户与机构数据
				getOrgInfo(orgList);
			}
			user = userService.findUserById(userId);
		}
		//JSON返回结果Map
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			//判断用户不存在的情况
			if(user == null){
				log.info("不存在用户["+userId+"]");
				results.put("info", "不存在用户["+userId+"]");
				return  null;
			}
			//登陆成功处理
			session.setAttribute(CURRENT_USER, user);
			//加载系统时间
			session.setAttribute(SYS_DATE, loginService.getSysDate());
			String currentMonth = loginService.getCurrentMonth();
			session.setAttribute(CURRENT_MONTH, currentMonth);
            session.setAttribute("casUrl",this.getServerIp());
			//用户菜单
			session.setAttribute(USER_RESOURCE, resourceService.getUserResource(user));
		} catch (Exception e) {
			e.printStackTrace();
			results.put("info", e.getMessage());
		}

			String operatedUser = "系统操作员编号:"+userId+"系统操作员名称:"+user.getUser_name();
			String operateType = "数据补录系统->登录日志";
			String requestResult = "系统操作员:"+userId+"登录数据补录系统服务日志";
			String operLog = "用户登录数据补录系统服务日志->登录日志";
			String serviceName = "服务名称:登录日志;服务方法名:";
			String ip = CommonUtils.getIpAddrAdvanced(request);

			loggerLogInfo(userId,operatedUser,operateType,requestResult,operLog,serviceName,ip);

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
		SysUserInfo userInfo =(SysUserInfo) session.getAttribute(LoginConstant.CURRENT_USER);
		String userId = userInfo.getUser_id();
		String operatedUser = "系统操作员编号:"+userId+"系统操作员名称:"+userInfo.getUser_name();
		String operateType = "数据补录系统->退出系统日志";
		String requestResult = "系统操作员:"+userId+"登录数据补录系统服务日志";
		String operLog = "用户登录数据补录系统服务日志->退出系统日志";
		String serviceName = "服务名称:退出系统日志;服务方法名:";
		String ip = CommonUtils.getIpAddrAdvanced(request);
		loggerLogInfo(userId,operatedUser,operateType,requestResult,operLog,serviceName,ip);
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
				+ ":8080";
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


	public  void loggerLogInfo( String userName, String operatedUser, String operateType, String requestResult, String operLog, String serviceName, String ip) {

		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();

		serviceName = "服务名称:登录日志;服务方法名:";
		String serName =serviceName+clazz+"->"+method;


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

		Date dt = new Date();
		String loginTime = sdf.format(dt);

		log.info("[{}]:[{}]:[{}]:[{}]:[{}]:[{}]:[{}]:[{}]:[{}]:[{}]",
					userName, ip, serName, "数据补录系统", operateType, loginTime, loginTime, 1, "成功", requestResult);


	}


	public final static String DEFAULT_ROLE = "U_00002";
}
