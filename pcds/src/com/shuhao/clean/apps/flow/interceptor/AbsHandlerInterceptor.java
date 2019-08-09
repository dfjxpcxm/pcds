package com.shuhao.clean.apps.flow.interceptor;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.constant.LoginConstant;
import com.shuhao.clean.utils.GlobalUtil;
/**
 * 拦截审批流程提交时执行的校验 ,主要应用于包含子模版的情况<br>
 *  执行按钮类型：buttonType = 00提交，01撤回，02跟踪，03流程详情,-1不处理类型
 * @author gongzhiyang
 *
 */
public abstract class AbsHandlerInterceptor implements HandlerInterceptor{
	
	protected final static Logger logger = Logger.getLogger(AbsHandlerInterceptor.class);
	
	/**
	 * 获取request中的参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getRequestParam(HttpServletRequest request) {
		Map<String, Object> properties = new HashMap<String, Object>();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String value = request.getParameter(paramName);
			if(GlobalUtil.trimToNull(value) == null)
				value = "";
			properties.put(paramName, value);
		}
		return properties;
	}
	
	/**
	 * 获取请求路径中的参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getPathVariable(HttpServletRequest request){
		Map<String, Object> pathVariables = (Map<String, Object>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		return pathVariables;
	}
	
	/**
	 * 获取session上下文参数信息
	 * @param request
	 * @return
	 */
	protected Map<String, Object> getContext(HttpServletRequest request){
		Map<String, Object> context = new HashMap<String, Object>();
		SysUserInfo userInfo =(SysUserInfo) request.getSession().getAttribute(LoginConstant.CURRENT_USER);
		context.put("bank_org_id", userInfo.getBank_org_id());
		context.put("month_id", request.getSession().getAttribute(LoginConstant.CURRENT_MONTH));
		context.put("date_id", request.getSession().getAttribute(LoginConstant.SYS_DATE));
		context.put("flow_status", "00");//流程状态
		return context;
	}
}  
