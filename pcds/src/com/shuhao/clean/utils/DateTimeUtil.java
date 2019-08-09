package com.shuhao.clean.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;



/**
 * @author Admin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class DateTimeUtil {
	public static Date parse(String s) throws java.text.ParseException {
		if (s == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(s);

	}
	public static Date parse2(String source){
		if (source == null) {
			return null;
		}
		SimpleDateFormat mat = new SimpleDateFormat("yyyy/MM/dd");
		Date dd = new Date();
		try {
			dd = mat.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dd;
	}
	
	public static String getXX_Date(String source){
		String str="";
		if(source!=null&&!"".equalsIgnoreCase(source)){
			str=source.replaceAll("-","/");
		}
		
		return str;
	}
	
	
	public static Date getNowDateTime(){
		Calendar cd=Calendar.getInstance();
		
		return cd.getTime();
		
	}
	
	public static void main(String [] a)throws Exception{
		String runDate = "2011-07-30";
		String dateId = "2011-07-31";
		
		System.out.println(before(dateId, runDate));
		
		System.out.println(get_MDY_String(runDate));
		
		System.out.println(getYQMDays(dateId));
		
		System.out.println(getDiffDays(dateId, runDate));
		
		System.out.println(getSysNextDate(dateId));
		System.out.println(getPreviousMonthFirst(dateId));
		System.out.println(getFullTime(new Date()));
	//	System.out.println(getDateOfRunInfo());
		
//		System.out.println(LastDateOfMonth("2007-12-11"));
	}
	public static String parse_time(String s) throws java.text.ParseException {
		if (s == null) {
			return null;
		}
		DateFormat f = DateFormat.getDateInstance(DateFormat.DEFAULT);
		Date d=f.parse(s);
		SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss");
		return sdf.format(d);

	}
	public static String toDateString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdff.format(date);
		return dateStr;
	}

	public static String toDateTimeString(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String s = "";
		s += cal.get(Calendar.YEAR);
		s += "-";
		s += cal.get(Calendar.MONTH) + 1;
		s += "-";
		s += cal.get(Calendar.DATE);
		s += " ";
		s += cal.get(Calendar.HOUR_OF_DAY);
		s += ":";
		s += cal.get(Calendar.MINUTE);
		s += ":";
		s += cal.get(Calendar.SECOND);
		return s;
	}

	public static String toTimeString(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String s = "" + cal.get(Calendar.HOUR_OF_DAY);
		s += ":";
		s += cal.get(Calendar.MINUTE);
		s += ":";
		s += cal.get(Calendar.SECOND);
		return s;
	}
	
	/*
	 * 获得当前日期的格式  yyyyMMdd
	 */
	public static String get_YYYYMMDD_Date(){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			Calendar cal=Calendar.getInstance();
			dt=sdf.format(cal.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	/*
	 * 将字符日期yyyy-MM-dd转换为yyyyMMdd.
	*/
	public static String get_YYYYMMDD_Date(String dateStr){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			dt=sdf.format(parse(dateStr));
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	
	/*
	 * 将字符日期yyyy-MM-dd转换为yyyyMMdd.
	 */
	public static String get_YYYYMMDD_Date(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(date);
		return dt;
	}
	
	/*
	 * 将字符日期yyyy-MM-dd转换为yyyyMMdd.
	*/
	public static String getYYYY_MM_DD_Date(String dateStr){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			dt=sdf.format(parse(dateStr));
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	
	/*
	 * 获得当前日期的格式  yyyy-MM-dd
	 */
	public static String get_YYYY_MM_DD_Date(){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			dt=sdf.format(cal.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	
	public static String get_YYYY_MM_DD_Date(Date date){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			
			dt=sdf.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	
	/*
	 * 获得当前时间格式的字符串
	 */
	public static String get_hhmmss_time(){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
			Calendar cal=Calendar.getInstance();
			dt=sdf.format(cal.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	
	public static String get_Sixhhmmss_time(){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("HHmmss");
			Calendar cal=Calendar.getInstance();
			dt=sdf.format(cal.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	

	/**
	 * 计算某日期之后N天的日期
	 * 
	 * @param theDateStr
	 * @param days
	 * @return String
	 */
	public static String getDate(String theDateStr, int days) {
		Date theDate = java.sql.Date.valueOf(theDateStr);
		Calendar c = new GregorianCalendar();
		c.setTime(theDate);
		c.add(GregorianCalendar.DATE, days);
		java.sql.Date d = new java.sql.Date(c.getTime().getTime());
		return d.toString();
	}
	/**
	 * 计算一旬的头一天
	 * @param theDate
	 * @param days
	 * @return
	 */
     public static java.sql.Date getDayOfPerMonth(String theDataStr){
     	Date theDate = java.sql.Date.valueOf(theDataStr);
     	Calendar c= new GregorianCalendar();
     	c.setTime(theDate);
     	int day=c.get(Calendar.DAY_OF_MONTH);
     	if(day<=10){
     		c.set(Calendar.DAY_OF_MONTH,1);
     	}else if(day>10&&day<=20){
     		c.set(Calendar.DAY_OF_MONTH,11);
     	}else{
     		c.set(Calendar.DAY_OF_MONTH,21);
     	}
     	c.add(Calendar.DAY_OF_MONTH,-1);
     	
     	
     	return new java.sql.Date(c.getTime().getTime());
     }
     /**
      * 判断是否为该月最后一天
      * @param theDate
      * @param days
      * @return
      */
     public static boolean isLastDayOfMonth(String theDataStr){
    	theDataStr = theDataStr.replaceAll("/", "-");//防止yyyy/mm/dd日期类型报错
     	Date theDate = java.sql.Date.valueOf(theDataStr);
     	Calendar c= new GregorianCalendar();
     	c.setTime(theDate);
     	int nowDay=c.get(Calendar.DAY_OF_MONTH);
     	c.set(Calendar.DAY_OF_MONTH,1);
     	c.add(Calendar.MONTH,1);
     	c.add(Calendar.DAY_OF_MONTH,-1);
     	int lowDayOfMonth=c.get(Calendar.DAY_OF_MONTH);
     	if(nowDay==lowDayOfMonth){
     		return true;
     	}else{
     		return false;
     	}
     }

     /**
      * 获取指定月份的最后一天
      * @param theDataStr
      * @return
      */
     public static String LastDateOfMonth(String theDataStr){
     	Date theDate = java.sql.Date.valueOf(theDataStr);
     	Calendar c= new GregorianCalendar();
     	c.setTime(theDate);
     	c.set(Calendar.DAY_OF_MONTH,1);
     	c.add(Calendar.MONTH,1);
     	c.add(Calendar.DAY_OF_MONTH,-1);
     	return (new java.sql.Date(c.getTime().getTime())).toString();
     }

     /**
	 * 计算某日期之后N天的日期
	 * 
	 * @param theDate
	 * @param days
	 * @return Date
	 */
	public static java.sql.Date getDate(Date theDate, int days) {
		Calendar c = new GregorianCalendar();
		c.setTime(theDate);
		c.add(GregorianCalendar.DATE, days);
		return new java.sql.Date(c.getTime().getTime());
	}
	
	/**
	 * 计算某日期之后N的日期
	 * 
	 * @param theDate
	 * @param field，如GregorianCalendar.DATE,GregorianCalendar.MONTH
	 * @param amount 数目
	 * @return Date
	 */
	public static java.sql.Date getDate(Date theDate, int field, int amount) {
		Calendar c = new GregorianCalendar();
		c.setTime(theDate);
		c.add(field, amount);
		return new java.sql.Date(c.getTime().getTime());
	}
	
	public static Date getDates(Date theDate, int field, int amount) {
		Calendar c = new GregorianCalendar();
		c.setTime(theDate);
		c.add(field, amount);
		return new Date(c.getTime().getTime());
	}
	
    //获得两个日期(字符串)之间的天数
	public static int getDiffDays(String begin_dt,String end_dt){
		Date end = java.sql.Date.valueOf(end_dt);
		Date begin = java.sql.Date.valueOf(begin_dt);
		int days = DateTimeUtil.getDaysBetween(begin,end);
		return days;
	}	
	
	public static int getDiffDays(Date begin_dt,Date end_dt){
		int days = DateTimeUtil.getDaysBetween(begin_dt,end_dt);
		return days;
	}

	/**
	 * 计算两日期之间的天数
	 * 
	 * @param start
	 * @param end
	 * @return int
	 */
	public static int getDaysBetween(Date start, Date end) {
		if(start==null)return 0;
		boolean negative = false;
		if (end.before(start)) {
			negative = true;
			Date temp = start;
			start = end;
			end = temp;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		GregorianCalendar calEnd = new GregorianCalendar();
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);
		if (cal.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)) {
			if (negative)
				return (calEnd.get(Calendar.DAY_OF_YEAR) - cal
						.get(Calendar.DAY_OF_YEAR))
						* -1;
			return calEnd.get(Calendar.DAY_OF_YEAR)
					- cal.get(Calendar.DAY_OF_YEAR);
		}
		int counter = 0;
		while (calEnd.after(cal)) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
			counter++;
		}
		if (negative)
			return counter * -1;
		return counter;
	}
	
	/**
	 * 以指定时间格式返回指定时间
	 * 
	 * @param dt 指定时间
	 * @param format 时间格式，如yyyyMMdd
	 * @return 返回指定格式的时间
	 */
	public static String getTime(Date dt, String format) {
		SimpleDateFormat st = new SimpleDateFormat(format);
		return st.format(dt);
	}

	/**
	 * 日期解析
	 * 
	 * @param source 日期字符
	 * @param format 解析格式，如果为空，使用系统默认格式解析
	 * @return 日期
	 */
	public static Date parse(String source, String format) {
		if (source == null) {
			return null;
		}

		DateFormat df = null;
		if (format != null) {
			df = new SimpleDateFormat(format);
		} else {
			df = DateFormat.getDateInstance(DateFormat.DEFAULT);
		}
		try {
			return df.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static Date parseYYYYMMDD(String dateStr) throws ParseException {
		if (dateStr == null) {
			return null;
		}
		SimpleDateFormat mat = new SimpleDateFormat("yyyyMMdd");
		Date dd = new Date();
		dd = mat.parse(dateStr);
		return dd;
	}
	
	public static String getYMDDate(String date){
		String d = "";
		try {
			DateFormat f = new SimpleDateFormat("yyyyMMdd");
			Date da = f.parse(date);
			f = new SimpleDateFormat("yyyy/MM/dd");
			d = f.format(da);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static String getFullTime(Date date){ 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
		Calendar c = Calendar.getInstance();    
		c.setTime(date);
		//如果时分秒==0
		if(c.get(Calendar.HOUR)==0 && c.get(Calendar.MINUTE)==0 && c.get(Calendar.SECOND)==0){
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		return sdf.format(c.getTime());    
	}
	
	/////////////////以下新增////////////////
	/**
	 * 获取上月第一天
	 * @return
	 */
	public static String getPreviousMonthFirst(String dateStr){      
	       String str = "";    
	       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");      
	       Date date=new Date();
	       try {
	    	   date=sdf.parse(dateStr);
	       } catch (Exception e) {
	    	   e.printStackTrace();
		   }
	       Calendar lastDate = Calendar.getInstance();    
	       lastDate.setTime(date);
	       lastDate.set(Calendar.DATE,1);//设为当前月的1号    
	       lastDate.add(Calendar.MONTH,-1);//减一个月，变为下月的1号    
	       str=sdf.format(lastDate.getTime());    
	       
	       return str;      
	}
	/**
	 * 获取后一天
	 * @param dateStr
	 * @return
	 */
	public static String getSysNextDate(String dateStr){      
	       String str = "";    
	       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");      
	       Date date=new Date();
	       try {
	    	   date=sdf.parse(dateStr);
	       } catch (Exception e) {
	    	   e.printStackTrace();
		   }
	       Calendar lastDate = Calendar.getInstance();    
	       lastDate.setTime(date);
	       lastDate.add(Calendar.DAY_OF_YEAR,1);//日期+1 
	       str=sdf.format(lastDate.getTime());    
	       
	       return str;      
	}
	/**
	 * 获取年天数，季天数，月天数
	 * @param dateStr
	 * @return
	 */
	public static Map<String, Integer> getYQMDays(String dateStr){     
		   Map<String, Integer> days=new HashMap<String, Integer>();
	       Date date=new Date();
	       try {
	    	  // date=sdf.parse(dateStr);
	       } catch (Exception e) {
	    	   e.printStackTrace();
		   }
	       Calendar lastDate = Calendar.getInstance();    
	       lastDate.setTime(date);
	       //月天数
	       days.put("md", lastDate.get(Calendar.DAY_OF_MONTH));
	       //年天数
	       days.put("yd", lastDate.get(Calendar.DAY_OF_YEAR));
	       //计算季天数
	       int m=lastDate.get(Calendar.MONTH)+1;
	       int q=(m-1)/3+1;
	       int qfirst=(q-1)*3+1;
	       lastDate.set(Calendar.DAY_OF_MONTH, 1); 
	       lastDate.set(Calendar.MONTH, qfirst-1);
	       Date ldate=lastDate.getTime();
	       int qd=getDiffDays(ldate, date);
	       days.put("qd", qd);
	       return days;      
	}
	
	public static String getYMDate(String date) {
		String d = "";
		try {
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Date da=f.parse(date);
			f = new SimpleDateFormat("yyyyMM");
			d = f.format(da);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return d;
	}
	
	public static boolean before(String beginDate,String endDate){
		Date begin = java.sql.Date.valueOf(beginDate);
		Date end = java.sql.Date.valueOf(endDate);
		return begin.before(end);
	}
	
	/**
	 * 格式化日期可以匹配，yyyy-MM-dd ,yyyy/MM/dd ,yyyyMMdd
	 * <br>
	 * 不支持：yyyy.mm.dd
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(String dateStr)throws Exception{
		if(dateStr==null || dateStr.trim().equals("")){
			throw new Exception(dateStr + "日期为空，转换日期异常。");
		}
		
		DateFormat format = null;
		if(dateStr.indexOf("-")>-1){
			if(dateStr.split("-").length<3){
				throw new Exception(dateStr + "日期格式输入有误，是否想输入如下格式[yyyy-MM-dd]的日期？");
			}
			format = new SimpleDateFormat("yyyy-MM-dd");
		}else if(dateStr.indexOf("/")>-1){
			if(dateStr.split("/").length<3){
				throw new Exception(dateStr + "日期格式输入有误，是否想输入如下格式[yyyy/MM/dd]的日期？");
			}
			format = new SimpleDateFormat("yyyy/MM/dd");
		}/*else if(dateStr.indexOf(".")>-1){
			if(dateStr.split("\\.").length<3){
				throw new Exception(dateStr + "日期格式输入有误，是否想输入如下格式[yyyy.MM.dd]的日期？");
			} 
			format = new SimpleDateFormat("yyyy.MM.dd");
		}*/else{
			//转换科学计数法数字
			if(dateStr.indexOf(".")>-1){
				dateStr = GlobalUtil.parse2Decimal(dateStr);
			}
			if(dateStr.length() != 8){
				throw new Exception(dateStr + "日期格式输入有误，是否想输入如下格式[yyyyMMdd]的日期？");
			}
			format = new SimpleDateFormat("yyyyMMdd");
		}
		return format.parse(dateStr);
	}
	
	/**
	 * 返回简单yyyy-MM-dd类型日期
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String getSimpleDate(String dateStr)throws Exception{
		if("".equals(dateStr)){
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(parseDate(dateStr));
	}
	
	/*************************华丽的分割线，以下为 DateTimeUtil应用***********************************************************************/
	/**
	 * 返回 mdy('month','day','year') 字符串
	 * @param sysDate
	 * @return
	 */
	public static String get_MDY_String(String sysDate){
		return " str_to_date('"+sysDate+"','%Y-%m-%d')";
	}
	
	/**
	 * 根据系统日期 T，生成日期上月1号~ T+1范围日期
	 * @param sysDate
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getDateRange(String sysDate)throws Exception{
		Map<String, String> range=new HashMap<String, String>();
		range.put("beginDate",getPreviousMonthFirst(sysDate));//上月1号
		range.put("endDate", getSysNextDate(sysDate));
		return range;
	}
}