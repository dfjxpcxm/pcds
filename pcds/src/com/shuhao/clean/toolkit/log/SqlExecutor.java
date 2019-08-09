package com.shuhao.clean.toolkit.log;

public class SqlExecutor {
	
	private String sql;
	private String params;
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	@Override
	public String toString() {
		return "[params=" + params + ", sql=" + sql.replaceAll("[\\s \\n \\t]+", " ") + "]\n";
	}
	
}
