package com.shuhao.clean.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shuhao.clean.constant.LoginConstant;

/**
 * <p>Title: 登录拦截器类</p>
 * <p>Description: 如果检测到用户未登录，跳转到登录页面</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: D&A 数浩科技</p>
 * @since 2014-8-20
 * @author gongzy
 * @version 1.0
 */
public class LoginFilter implements Filter {
	
    //private String errorPage = null;   //错误页面
    private String loginPage = null;   //登录页面
    private String loginAction = null; //登录action
    private String localLogin = null; //登录action
    private String ErrorPage = null; //
    
    
	public void init(FilterConfig filterConfig) throws ServletException {
		//this.errorPage = filterConfig.getInitParameter("ErrorPage");
		this.loginPage = filterConfig.getInitParameter("LoginPage");
		this.loginAction = filterConfig.getInitParameter("LoginAction");
		this.localLogin = filterConfig.getInitParameter("LocalLoginAction");
		this.ErrorPage=filterConfig.getInitParameter("ErrorPage");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String contextPath = req.getContextPath();
		String userRequestURI = null == req.getRequestURI() ? "" : req.getRequestURI();
		
		// ------ modify at 2014-12-17 -------------
		//不需要过滤掉文件类型 ,demo排除
		boolean canAnonymousAccess = //(userRequestURI.equalsIgnoreCase(contextPath))
//		|| (userRequestURI.equalsIgnoreCase(contextPath + "/"))||
		 (userRequestURI.equalsIgnoreCase(contextPath + this.loginAction)) //跨域登陆
		|| (userRequestURI.equalsIgnoreCase(contextPath + this.localLogin)) //本地登录地址
		|| (userRequestURI.equalsIgnoreCase(contextPath+ this.loginPage)
		|| (userRequestURI.equalsIgnoreCase(contextPath+ this.ErrorPage))
		|| userRequestURI.endsWith(".css") || userRequestURI.endsWith(".js")
		|| userRequestURI.endsWith(".CSS") || userRequestURI.endsWith(".JS")
		|| userRequestURI.endsWith(".gif") || userRequestURI.endsWith(".png")
		|| userRequestURI.endsWith(".GIF") || userRequestURI.endsWith(".PNG")
		|| userRequestURI.endsWith(".JPG") || userRequestURI.endsWith(".jpg") 
		);
		
		boolean stopLogin= false;
		
//		boolean stopLogin= (userRequestURI.equalsIgnoreCase("/pcds")
//					|| userRequestURI.equalsIgnoreCase("/pcds/")
//					|| userRequestURI.equalsIgnoreCase("/pcds/main.jsp")
//					|| userRequestURI.equalsIgnoreCase("/pcds/login.jsp")
//					|| userRequestURI.equalsIgnoreCase("/pcds/banner.jsp"))
//				? true :false;
		
		if(canAnonymousAccess && !stopLogin) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if(stopLogin){
			request.getRequestDispatcher(this.ErrorPage).forward(request, response);
			return;
		}
		
		//--------------------------------
		if(session == null || session.getAttribute(LoginConstant.CURRENT_USER) == null) {
			((HttpServletResponse)response).sendRedirect(req.getContextPath()+"/login/doLogin");
//			session.invalidate();
//			request.getRequestDispatcher(this.loginPage).forward(request, response);
			//((HttpServletResponse)response).sendRedirect(req.getContextPath()+this.loginPage);
			return;
		}


		 
		filterChain.doFilter(request, response);
	}
	
	public void destroy() {
	}
}
