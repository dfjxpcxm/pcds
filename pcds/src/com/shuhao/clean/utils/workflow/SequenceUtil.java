package com.shuhao.clean.utils.workflow;

import java.util.Date;

public class SequenceUtil {
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 9999;
	
	/**
	 * 生成唯一主键(18位)，带前缀
	 * 
	 * @param prefix
	 * @return
	 */
	public static synchronized String next(String prefix) {
		if (seq > ROTATION)
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$04d",
				date, seq++);
 
		return prefix != null ? prefix + str : str;
	}

	/**
	 * 生成唯一主键不带前缀(18位)
	 * 
	 * @return
	 */
	public static synchronized String next() {
		return next(null);
	}
}
