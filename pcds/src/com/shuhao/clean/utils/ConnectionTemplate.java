package com.shuhao.clean.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * 类描述: 数据库连接使用模板,封装了通用的关闭连接和异常处理
 * @author chenxiangdong
 * 创建时间：2015-1-9下午04:03:14
 */
public abstract class ConnectionTemplate<T> {
	
	public T process(final Connection conn) throws Exception {
		
		try {
			return doWork(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(conn != null) {
				conn.close();
			}
		}
	}
	
	public abstract T doWork(Connection conn) throws SQLException;
	
}
