package com.shuhao.clean.utils;

import java.text.DateFormat;
import java.util.Date;

/**
 * 生成唯一主键
 * 
 * @author Administrator
 * 
 */
public class UID {
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 9999;
	
	private static Integer index = null;
	private static final int MaxInde = 999999;

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
	
	/**
	 * 获取长序号6位
	 * @return
	 */
	public static synchronized String getInstance(){
		if (index == null) {
			index = 0;
		}else{
			index++;
		}
		if (index > MaxInde)
			index = 0;
		String str = String.format("%06d", index);
		return str;
	}
	
	/**
	 * 获取短序号4位
	 * @return
	 */
	public static synchronized String getShortSeq(){
		if (index == null) {
			index = 0;
		}else{
			index++;
		}
		if (index > ROTATION)
			index = 0;
		String str = String.format("%04d", index);
		return str;
	}
	
	/**
	 * 取时分秒
	 * @return
	 */
	public static synchronized String nt() {
		return DateFormat.getTimeInstance().format(new Date()).replace(":", "");
	}

	public static void main(String[] args) {
		System.out.println(String.format("%03d", 1));
		System.out.println(UID.nt());
		Date date = new Date();
		DateFormat df1 = DateFormat.getDateInstance();//日期格式，精确到日
		System.out.println(df1.format(date));
		DateFormat df2 = DateFormat.getDateTimeInstance();//可以精确到时分秒
		System.out.println(df2.format(date));
		DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
		System.out.println(df3.format(date).replace(":", ""));
		DateFormat df4 = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL); //显示日期，周，上下午，时间（精确到秒） 
		System.out.println(df4.format(date));  
		DateFormat df5 = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG); //显示日期,上下午，时间（精确到秒） 
		System.out.println(df5.format(date));
		DateFormat df6 = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT); //显示日期，上下午,时间（精确到分） 
		System.out.println(df6.format(date));
		DateFormat df7 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
		System.out.println(df7.format(date));
		System.out.println(UID.getInstance());
		System.out.println(UID.getInstance());
		System.out.println(UID.getInstance());
		System.out.println(UID.getInstance());
		System.out.println(UID.getInstance());
		System.out.println(UID.getInstance());
	}
}