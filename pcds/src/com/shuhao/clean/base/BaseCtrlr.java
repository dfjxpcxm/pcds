package com.shuhao.clean.base;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.constant.LoginConstant;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.exttree.ExtTreeNode;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>Title: 基础控制类，所有controller类的父类</p>
 * <p>Description: 包含servlet api,封装部分返回map方法，通过ResponseBody返回json，<br>
 * 普通map返回json直接返回map
 * </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: D&A 数浩科技</p>
 * @since 2014-8-20
 * @author gongzy
 * @version 1.0
 */
public abstract class BaseCtrlr {
	/**
	 * 日志记录
	 */
	protected Log logger = LogFactory.getLog(this.getClass());
	
//	@Autowired
	protected HttpServletRequest request ;
//	@Autowired
	protected HttpServletResponse response ;
	
	@Autowired
	protected HttpSession session ;
	
	@ModelAttribute
	public void init(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
		this.session = request.getSession(true);
	}
 
	public HttpServletRequest getRequest() {
		request = ((ServletRequestAttributes)RequestContextHolder
			      .getRequestAttributes()).getRequest();
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		((ServletRequestAttributes) RequestContextHolder
			      .getRequestAttributes()).getRequest().getSession();
		return session;
	}
	
	protected Map<String, Object> getJsonResultMap(List<? extends Object> dataList) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("results", dataList);
		result.put("success", true);
		return result;
	}
	
	protected Map<String, Object> getJsonResultMap(List<? extends Object> dataList, int totalCount) {
		Map<String, Object> result = getJsonResultMap(dataList);
		result.put("totalCount", totalCount);
		return result;
	}
	
	protected Map<String, Object> getJsonResultMap(Object obj) {
		List<Object> list = new ArrayList<Object>();
		if(obj != null){
			list.add(obj);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("results", list);
		result.put("success", true);
		return result;
	}
	
	
	/**
	 * 返回执行成功的Json字符串
	 * @param info
	 * @throws Exception
	 */
	protected Map<String, Object> doSuccessInfoResponse(String info) {
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", Boolean.valueOf(true));
		results.put("info", info);
		return results;
	}
	
	/**
	 * 成功返回json，包含results 结果
	 * @param info
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> doSuccessInfoResponse(String info,Object obj) {
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", Boolean.valueOf(true));
		results.put("info", info);
		results.put("results", obj);
		return results;
	}
	
	/**
	 * 返回执行成功的Json字符串
	 * @param info
	 * @throws Exception
	 */
	protected Map<String, Object> doFailureInfoResponse(String info) {
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", Boolean.valueOf(false));
		results.put("info", info);
		return results;
	}

	/**
	 * 返回数组json
	 * @param list
	 * @param isMap
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */
	protected Object doJSONArrayResponse(List<? extends Object> list,boolean isMap) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			if(isMap)
				array.put(list.get(i));
			else
				array.put(BeanUtils.describe(list.get(i)));
		}
		return array;
	}
	
	/**
	 *将list以Json形式返回客户端
	 * @param list
	 * @throws Exception 
	 */
	protected Map<String, Object> doJSONResponse(List<? extends Object> list) {
		Map<String, Object> results = new HashMap<String, Object>();
		Integer total = (Integer) request.getAttribute("total");
		if(total != null){
			results.put("totalCount", total);
		}
		results.put("results", list);
		return results;
	}
	
	/**
	 * 返回json字符串
	 * @param info
	 * @param status
	 * @return
	 */
	protected String getJsonstrResponse(String info,boolean status) {
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("success", status);
		results.put("info", info);
		JSONObject json = new JSONObject(results);
		return json.toString();
	}
	
	/**
	 * 强制返回错误json字符串
	 * @param out
	 * @param msg
	 * @return
	 */
	protected Object writeJsonError(PrintWriter out,String msg){
		msg = getJsonstrResponse("Error:<br>"+msg, false);
		out.print(msg);
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 返回ext tree json
	 * @param treeList
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void doExtTreeJSONResponse(List<? extends ExtTreeNode> treeList, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(new JSONArray(treeList).toString());
	}
	
	/**
	 * 获取缓存公共参数
	 * @return
	 */
	protected Map<String, Object> getContext(){
		Map<String, Object> context = new HashMap<String, Object>();
		SysUserInfo userInfo =(SysUserInfo) session.getAttribute(LoginConstant.CURRENT_USER);
		context.put("bank_org_id", userInfo.getBank_org_id());
		context.put("month_id", session.getAttribute(LoginConstant.CURRENT_MONTH));
		context.put("date_id", session.getAttribute(LoginConstant.SYS_DATE));
		context.put("flow_status", "00");//流程状态
		return context;
	}
	
	/**
	 * 将从前端获取的参数封装到Map
	 * @param request
	 * @return properties 封装属性值的Map对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getRequestParam() {
		Map<String, Object> properties = new HashMap<String, Object>();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String value = request.getParameter(paramName);
			if(trimToNull(value) == null)
				value = "";
			if("start".equals(paramName)||"limit".equals(paramName) ){
				properties.put(paramName, Integer.parseInt(value));
			}else{
				properties.put(paramName, value);
			}
				
			
		}
		return properties;
	}
	
	/**
	 * 按key获取request中的参数
	 * @param key
	 * @return
	 */
	public String getParam(String key){
		return request.getParameter(key);
	}
 
	
	/**
	 * 将request对象中参数封装成对象返回
	 * @param <T>
	 * @param cls
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getParamObject(Class<T> cls) throws InstantiationException, IllegalAccessException, InvocationTargetException{
		T obj = cls.newInstance();
		Map<String, String[]> paramMap = request.getParameterMap();
		BeanUtils.populate(obj, paramMap);
		return obj;
	}
	
	/**
	 * 获取当前登陆用户
	 * @param request
	 * @return
	 */
	public SysUserInfo getCurrentUser(){
		return (SysUserInfo) session.getAttribute(LoginConstant.CURRENT_USER);
	}
	
	/**
	 * 获取系统日期
	 * @param request
	 * @return
	 */
	public String getSysDate(){
		return (String) session.getAttribute(LoginConstant.SYS_DATE);
	}
	
	/**
	 * 获取分页对象 start + rowend
	 * @param request
	 * @return PageRange 
	 */
	protected Map<String, Object> insertPageParamToMap(Map<String, Object> paramMap) {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		if(start == null){
			start = "0";
			start = String.valueOf(Integer.parseInt(start));
			limit = "30";
		}
		if(!start.equals("0")) {
			start = String.valueOf(Integer.parseInt(start));
		}
		paramMap.put("start", Integer.parseInt(start));
		paramMap.put("endrow", Integer.parseInt(start) + Integer.parseInt(limit));
		return paramMap;
	}
	
	/**
	 * 将分页查询的总记录数设置到Request对象中
	 */
	protected void setTotalCountToRequest(String totalCount) {
		request.setAttribute("total", new Integer(totalCount));
	}
	
	
	/**
	 * 封装调用业务逻辑方法及响应客户端Ajax请求
	 * @param logicBean	业务逻辑Bean
	 * @param methodName	业务逻辑类需要调用的方法
	 * @param params	调用业务逻辑方法时需要传入的参数数组
	 * @param totalMethod 计算总数方法
	 * @category 暂不支持重载
	 */
	@SuppressWarnings("unchecked")
	protected void doResponse(Object logicBean, String methodName, Object[] params, String totalMethod) throws Exception{
		try {
			Method[] methods = logicBean.getClass().getInterfaces()[0].getDeclaredMethods();
			
			boolean needLog = false;
			String code = null;
			//增加common方法日志功能
			if(methodName.split("@").length > 1) {
				needLog = true;
				code = methodName.split("@")[1];
				methodName = methodName.split("@")[0];
				totalMethod = (null == totalMethod ? null : totalMethod.replaceAll("@" + code, ""));
			}
			//查询总记录数
			if(totalMethod != null) {
				for (Method method : methods) {
					if(!totalMethod.equals(method.getName()))
						continue;
					Map<String, Object> paramMap = (Map<String, Object>) params[0];
					paramMap = this.insertPageParamToMap(paramMap);
					Integer totalCount = (Integer) method.invoke(logicBean, paramMap);
					request.setAttribute("total", totalCount);
					break;
				}
			}
			
			boolean executed = false;
			
			for (Method method : methods) {
				if(!methodName.equals(method.getName()))
					continue;
				
				executed = true;
				if(method.getReturnType().toString().indexOf("java.util.List") != -1) {
					List<? extends Object> list = (List<? extends Object>) method.invoke(logicBean, params);
					//返回List
					doJSONResponse(list);
				}else if(method.getReturnType().toString().equals("void")){
					method.invoke(logicBean, params);
					doSuccessInfoResponse("操作成功");
				}else {
					Object obj = method.invoke(logicBean, params);
					
					List<Object> list = new ArrayList<Object>();
					list.add(obj);
					doJSONResponse(list);
				}
				break;
			}
			if(!executed)
				throw new Exception("no such method : "+methodName);
			
			if(needLog) {
//				FunctionLogAdvice advice = getBean("funLogAdvice", FunctionLogAdvice.class);
//				advice.writeFunctionLog(session.getId(), code);
			}
		} catch (InvocationTargetException e) {
			e.getTargetException().printStackTrace();
			doFailureInfoResponse("操作失败: "+e.getTargetException().getMessage());
		}
	}
	
	/**
	 * 从map中获取value
	 * @param paramMap
	 * @param key
	 * @return
	 */
	protected String getStringValue(Map<String, Object> paramMap, String key) {
		return GlobalUtil.getStringValue(paramMap, key);
	}
	
	/**
	 * string去空格
	 * @param str
	 * @return
	 */
	protected String trimToNull(String str) {
		return GlobalUtil.trimToNull(str);
	}
}