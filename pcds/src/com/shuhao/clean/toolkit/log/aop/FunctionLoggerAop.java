package com.shuhao.clean.toolkit.log.aop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;

import com.shuhao.clean.toolkit.log.SqlExecutor;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.annotation.UseLog;
import com.shuhao.clean.toolkit.log.thread.LoggerThreadLocalHolder;

public abstract class FunctionLoggerAop{
	
	/**
	 * 记录用户方法访问日志,记录sql
	 * @param pjp
	 * @param function
	 * @param needLog
	 * @return
	 * @throws Throwable
	 */
	public Object logUserFunVisit(ProceedingJoinPoint pjp,FunDesc function,UseLog needLog) throws Throwable {
		Object object = pjp.proceed();
		//取sql
		List<SqlExecutor> sqlExecutors = LoggerThreadLocalHolder.SQL_EXECUTORS.get();
		//取登录用户
		HttpServletRequest request = (HttpServletRequest) pjp.getThis().getClass().getDeclaredMethod("getServletRequest").invoke(pjp.getThis());
		//记录功能访问日志
		persistLogger(request.getSession().getId(),function,sqlExecutors);
		return object;
	}
	
	/**
	 * 记录用户方法访问日志,不记录sql
	 * @param pjp
	 * @param function
	 * @param needLog
	 * @return
	 * @throws Throwable
	 */
	 public Object logUserFunVisitNoSql(ProceedingJoinPoint jp,FunDesc funDesc) throws Throwable {
		Object object = jp.proceed();
		try {
			HttpServletRequest request = (HttpServletRequest) jp.getTarget().getClass().getSuperclass().getDeclaredMethod("getServletRequest").invoke(jp.getThis());
			this.persistLogger(request.getSession().getId(), funDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	 /**
	  * 写入日志,写入sql
	  * @param userInfo
	  * @param function
	  * @param sqlExecutors
	  */
	public abstract void persistLogger(String sessionId,FunDesc function,List<SqlExecutor> sqlExecutors);
	
	/**
	 * 写入日志,不写入sql
	 * @param sessionId
	 * @param function
	 */
	public abstract void persistLogger(String sessionId,FunDesc function);
}
