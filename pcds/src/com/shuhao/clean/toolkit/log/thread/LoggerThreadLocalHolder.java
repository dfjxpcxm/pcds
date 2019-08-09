package com.shuhao.clean.toolkit.log.thread;

import java.util.List;

import com.shuhao.clean.toolkit.log.SqlExecutor;

public class LoggerThreadLocalHolder {
	
	public static final ThreadLocal<List<SqlExecutor>> SQL_EXECUTORS = new ThreadLocal<List<SqlExecutor>>();
	
}