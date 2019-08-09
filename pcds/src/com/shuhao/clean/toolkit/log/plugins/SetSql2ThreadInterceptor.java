package com.shuhao.clean.toolkit.log.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.shuhao.clean.toolkit.log.SqlExecutor;
import com.shuhao.clean.toolkit.log.thread.LoggerThreadLocalHolder;

/**
 * 拦截sql和参数到线程变量
 * @author qisheng
 */

@Intercepts({
	//@Signature(type= Executor.class,method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
	@Signature(type= Executor.class,method = "update",args = {MappedStatement.class, Object.class})
	})
public class SetSql2ThreadInterceptor implements Interceptor {
	
	public Object intercept(Invocation invocation) throws Throwable {
		
		List<SqlExecutor> sqlExecutors = LoggerThreadLocalHolder.SQL_EXECUTORS.get();
		if(sqlExecutors == null) {
			sqlExecutors = new ArrayList<SqlExecutor>();
			LoggerThreadLocalHolder.SQL_EXECUTORS.set(sqlExecutors);
		}
		SqlExecutor sqlExecutor = new SqlExecutor();
		sqlExecutor.setParams(invocation.getArgs()[1]==null?null:invocation.getArgs()[1].toString());
		MappedStatement sqlMapper = (MappedStatement) invocation.getArgs()[0];
		sqlExecutor.setSql(sqlMapper.getBoundSql(invocation.getArgs()[1]).getSql());
		sqlExecutors.add(sqlExecutor);
		
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}
}
