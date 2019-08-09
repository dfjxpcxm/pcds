package com.shuhao.clean.toolkit.log;

import java.util.List;

import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.aop.FunctionLoggerAop;

/**
 * 默认方法日志写入类
 * @author gongzy
 *
 */
public class DefaultFunctionLoggerAop extends FunctionLoggerAop{
	
	public void persistLogger(String sessionId, FunDesc function,
			List<SqlExecutor> sqlExecutors) {
		// TODO Auto-generated method stub
		
	}
	
	public void persistLogger(String sessionId, FunDesc function) {
		// TODO Auto-generated method stub
		
	}
}
