package com.shuhao.clean.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import oracle.sql.CLOB;

import org.apache.commons.beanutils.BeanUtils;

import com.rx.util.CommonUtil;


public class GlobalUtil {
	
	public static void main(String[] args) {
		String s = "select a.bill_amt as 票据金额,\r\n b.trans_amt as 合同金额,\r\n a.bill_amt - b.trans_amt as 票据金额大于合同金额\r\nfrom (select t.contract_no, sum(t.bill_amt)+ as bill_amt\r\n from imp_acpt_bill_txn t\r\n group by t.contract_no) a,\r\n (select contract_no, trans_amt\r\n from IMP_REDISC_INRIGHT) b\r\nwhere a.contract_no = b.contract_no\r\n and a.contract_no = '111111111111'\r\n and (not a.bill_amt <= b.trans_amt)";
		System.out.println(trimToSpace(s));
		
		String str = "select a.user_id,a.user_name from user where a.user_id = [@userId] and username=[@userName] and a.user_id = [@userId]*[$A10]";
	
		List<String> ps = getParams(str, "[@", "]");
		System.out.println(ps);
		
		Object o = null;
		System.out.println(String.valueOf(o));
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("aaa", "");
		System.out.println(m.get("bbb"));
		System.out.println(m.get("aaa"));
	}
 
	/**
	 * 如果 param是null或是空串 返回null,否则返回param去掉两端空格
	 * @param param 输入参数
	 * @return
	 */
	public static String trimToNull(String param) {
		if(param!=null&&param.trim().length()>0) {
			return param.trim();
		} 
		return null;
	}
	
	/**
	 * 将开头和结尾的空
	 * @param str
	 * @return
	 */
	public static String trimLR(String str){
		return str.replaceAll("^\\s*|\\s*$", "");
	}
	
	/**
	 * 将空白符转换为空格,包含\r\n\t
	 * @param str
	 * @return
	 */
	public static String trimToSpace(String str){
		return str.replaceAll("\\s+", " ");
	}
	
	/**
	 * 将string类型的object中的 \r\n\t替换成空格
	 * @param str
	 * @return
	 */
	public static Object trimObject(Object str){
		if(str!=null && str instanceof java.lang.String){
			return String.valueOf(str).replaceAll("\\s+", " ");
		}
		return str;
	}
	
	/**
	 * 判断字符串非空（null和空串）
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		return str!=null&&str.trim().length()>0;
	}
	/**
	 * 判断字符串非空（null和空串）
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return str==null||str.trim().length()==0;
	}
	
	/**
	 * 将list中的map key转化成小写
	 * 
	 * @param map
	 * @return
	 */
	public static List<Map<String, Object>> lowercaseListMapKey(List<Map<String, Object>> dataList) {
		return lowercaseListMapKey(dataList, false , false);
	}
	
	/**
	 * 将list中的map key转化成小写，是否处理value中包含的clob值
	 * @param dataList
	 * @param transClob
	 * @return
	 */
	public static List<Map<String, Object>> lowercaseListMapKey(List<Map<String, Object>> dataList,boolean transClob) {
		return lowercaseListMapKey(dataList, transClob , false);
	}
	
	/**
	 * 将list中的map key转化成小写,并且将value中的空白替换成空格
	 * @param map
	 * @return
	 */
	public static List<Map<String, Object>> lowercaseListMapKeyAndTrimVal(List<Map<String, Object>> dataList) {
		return lowercaseListMapKey(dataList, false , true);
	}
	
	
	/**
	 * 将list中的map key转化成小写,同时处理clob转换为string
	 * 
	 * @param dataList
	 * @param transClob
	 * @return
	 */
	public static List<Map<String, Object>> lowercaseListMapKey(List<Map<String, Object>> dataList,boolean transClob,boolean trimValue) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> dataMap : dataList) {
			if(transClob){ //转换clob
				resultList.add(lowercaseMapKeyAndClob(dataMap));
			}else if(trimValue){ //转换key为小写并且处理value中的空白
				resultList.add(lowercaseMapKeyAndTrimVal(dataMap));
			}else{
				resultList.add(lowercaseMapKey(dataMap));
			}
		}
		return resultList;
	}
	
	/**
	 * 将object转换为小写
	 * @param object
	 * @return
	 */
	public static String lowercaseObject(Object object){
		if(object==null)
			return "";
		
		return object.toString().toLowerCase();
	}

	/**
	 * 将传入map的key变成小写
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> lowercaseMapKey(Map<String, Object> map) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : map.entrySet()) {
			rsMap.put(entry.getKey().toLowerCase(), entry.getValue());
		}
		return rsMap;
	}
	
	/**
	 * 转换日期
	 * @param map
	 * @return
	 */
	public static List<Map<String, Object>> lowercaseKeyAndDate(List<Map<String, Object>> dataList) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> dataMap : dataList) {
			resultList.add(lowercaseKeyAndDate(dataMap));
		}
		return resultList;
	}
	/**
	 * 转换日期
	 * @param map
	 * @return
	 */
	public static Map<String, Object> lowercaseKeyAndDate(Map<String, Object> map) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : map.entrySet()) {
			if(entry.getValue()!=null && (entry.getValue() instanceof java.sql.Timestamp)){
				rsMap.put(entry.getKey().toLowerCase(), DateTimeUtil.getFullTime((Timestamp)entry.getValue()));
			}else{
				rsMap.put(entry.getKey().toLowerCase(), entry.getValue());
			}
		}
		return rsMap;
	}
	
	/**
	 * key转换为小写并且处理value中的空白符
	 * @param map
	 * @return
	 */
	public static Map<String, Object> lowercaseMapKeyAndTrimVal(Map<String, Object> map) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : map.entrySet()) {
			rsMap.put(entry.getKey().toLowerCase(), trimObject(entry.getValue()));
		}
		return rsMap;
	}
	
	/**
	 * 将传入map的key变成小写,同事处理clob类型的字段转换为string
	 * @param map
	 * @return
	 */
	public static Map<String, Object> lowercaseMapKeyAndClob(Map<String, Object> map) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : map.entrySet()) {
			//如果value==null 继续
			if(entry.getValue()==null){
				rsMap.put(entry.getKey().toLowerCase(), entry.getValue());
				continue;
			}
			//axis5.3 weblogic jdbc驱动中ClOB类型转换
			if("weblogic.jdbc.wrapper.Clob_oracle_sql_CLOB".equals(entry.getValue().getClass().getName())){ 
				try {
					Method method = entry.getValue().getClass().getMethod("getVendorObj",new Class[]{});
					CLOB clob = (oracle.sql.CLOB)method.invoke(entry.getValue());
					rsMap.put(entry.getKey().toLowerCase(),clobToString(clob));
				} catch (Exception e) {
					rsMap.put(entry.getKey().toLowerCase(), entry.getValue());
					e.printStackTrace();
				}  
			//oracle jdbc驱动中clob类型转换
			}else if("oracle.sql.CLOB".equals(entry.getValue().getClass().getName())){ 
				try {
					rsMap.put(entry.getKey().toLowerCase(),clobToString((CLOB)entry.getValue()));
				} catch (SQLException e) {
					rsMap.put(entry.getKey().toLowerCase(), entry.getValue());
					e.printStackTrace();
				} catch (IOException e) {
					rsMap.put(entry.getKey().toLowerCase(), entry.getValue());
					e.printStackTrace();
				}
			}else{
				rsMap.put(entry.getKey().toLowerCase(), entry.getValue());
			}
		}
		return rsMap;
	}
	
	/**
	 * clob转换为字符串
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String clobToString(CLOB clob) throws SQLException, IOException {
    	String reString = "";
    	if( clob == null || clob.getCharacterStream() == null )
    		return "";
    	Reader is = clob.getCharacterStream();// 得到流
    	BufferedReader br = new BufferedReader(is);
    	String s = br.readLine();
    	StringBuffer sb = new StringBuffer();
    	while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
    		sb.append(s).append(" ");
    		s = br.readLine();
    	}
    	reString = sb.toString();
    	return reString;
    }
	
	/**
	 * 返回空的map
	 * @param map
	 * @return
	 */
	public static Map<String, Object> clearMap(Map<String, Object> map){
		Map<String, Object> clearMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : map.entrySet()) {
			 if(entry.getValue() == null || entry.getValue() instanceof java.lang.String){
				 clearMap.put(entry.getKey(), "");
			 } else if(entry.getValue() instanceof java.lang.Boolean){
				 clearMap.put(entry.getKey(), entry.getValue());
			 } else{
				 clearMap.put(entry.getKey(), "0");
			 }
		}
		return clearMap;
	}
	
	/**
	 * 给map全部设置默认值
	 * @param map
	 * @param defaultValue
	 * @return
	 */
	public static Map<String, Object> setDefaultMap(Map<String, Object> map,String defaultValue){
		Map<String, Object> clearMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : map.entrySet()) {
			clearMap.put(entry.getKey(), defaultValue);
		}
		return clearMap;
	}
	

	/**
	 * 返回double类型值
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getStringValue(Map<String, Object> map,String key)
	{
		Object obj = map.get(key);
		/*if (obj == null)
			obj = map.get(key.toUpperCase());*/
		
		return CommonUtil.getStringValue(obj);
	}
	
	/**
	 * 先取原始值，在取小写值，取不到在尝试取大写值
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getIgnoreCaseValue(Map<String, Object> map,String key)
	{
		Object obj = map.get(key);
		
		if (obj == null)
			obj = map.get(key.toLowerCase());
		
		if (obj == null)
			obj = map.get(key.toUpperCase());
		
		return CommonUtil.getStringValue(obj);
	}
	
	/**
	 * 包含指定的值
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean containsValueIgnoreCase(Map<String, Object> map,String key)
	{
		Object obj = map.get(key);
		
		if (obj == null)
			obj = map.get(key.toLowerCase());
		
		if (obj == null)
			obj = map.get(key.toUpperCase());
		
		return obj != null;
	}
	
	/**
	 * 包含指定的value不区分大小写
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean containsKeyIgnoreCase(Map<String, Object> map,String key)
	{
		return map.containsKey(key) ? true : (map.containsKey(key.toLowerCase()) ? true : (map.containsKey(key.toUpperCase()) ? true : false));
	}
	/**
	 * 返回double类型值
	 * @param map
	 * @param key
	 * @return
	 */
	public static double getDoubleValue(Map<String, Object> map,String key)
	{
		Object obj = map.get(key);
		if (obj == null)
			obj = map.get(key.toUpperCase());
		
		return CommonUtil.getDoubleValue(obj);
	}
	/**
	 * 返回int类型值
	 * @param map
	 * @param key
	 * @return
	 */
	public static int getIntValue(Map<String, Object> map,String key)
	{
		Object obj = map.get(key);
		/*if (obj == null)
			obj = map.get(key.toUpperCase());*/
		
		if(obj==null||obj.toString().equals("")){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 将List<Map<String, Object>>转换为List<cls>指定的集合
	 * @param class1
	 * @param mapping
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public static List<Object> populate(Class<?> class1,String[][] mapping,List<Map<String, Object>> dataList) throws Exception
	{
		List<Object> list=new ArrayList<Object>();

		Field[] fields=class1.getDeclaredFields();
		List<String> trimList=new ArrayList<String>();
		for(int j=0;j<fields.length;j++)
		{
			if(fields[j].getType().getName().equals("java.lang.String"))
				trimList.add(fields[j].getName());
		}
		String[] trimArray=new String[trimList.size()];
		trimList.toArray(trimArray);

		for(int i=0;i<dataList.size();i++)
		{
			Map<String, Object> row= dataList.get(i);
			
			if(mapping!=null)
			{
				for(int j=0;j<mapping.length;j++)
					row.put(mapping[j][1],row.get(mapping[j][0]));
			}
			
			for(int j=0;j<trimArray.length;j++)
			{
				Object value=row.get(trimArray[j]);
				if(value==null)
					continue;
				row.put(trimArray[j],value.toString().trim());
			}
			
			Object obj=class1.newInstance();
			BeanUtils.populate(obj,row);
			
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * 将Map转换为cls对应的对象
	 * @param cls
	 * @param mapping
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Object populate(Class<?> cls,String[][] mapping,Map<String, Object> map) throws Exception
	{
		Field[] fields=cls.getDeclaredFields();
		List<String> trimList=new ArrayList<String>();
		for(int j=0;j<fields.length;j++)
		{
			if(fields[j].getType().getName().equals("java.lang.String"))
				trimList.add(fields[j].getName());
		}
		String[] trimArray=new String[trimList.size()];
		trimList.toArray(trimArray);
		
		if(mapping!=null)
		{
			for(int j=0;j<mapping.length;j++)
				map.put(mapping[j][1],map.get(mapping[j][0]));
		}
		
		for(int j=0;j<trimArray.length;j++)
		{
			Object value=map.get(trimArray[j]);
			if(value==null)
				continue;
			map.put(trimArray[j],value.toString().trim());
		}
		
		Object obj=cls.newInstance();
		BeanUtils.populate(obj,map);

		return obj;
	}
	
	/**
	 * 返回当前登录用户
	 * @param session	用户Session
	 * @return	用户对象
	 */
	public static Object getCurrentUser(HttpSession session){
		return session.getAttribute("currentUser");
	}
	
	/**
	 * 将数组中的对象以字符串连接返回
	 * @param objArray
	 */
	public static String getArrayString(Object[] objArray) {
		String str = "";
		for(int i=0;i<objArray.length;i++) {
			if(i==0) {
				str += "[";
			}
			if(i==(objArray.length-1)) {
				str += objArray[i].toString() + "]";
			} else {
				str += objArray[i].toString() + ",";
			}
		}
		return str;
	}
	
	/**
	 * 将一个Object转化为Integer，如果不能转化则报出异常
	 * @param obj
	 * @return
	 */
	public static Integer parse2Integer(Object obj) {
		if(obj == null) {
			return null;
		}
		if(obj instanceof Integer) {
			return (Integer)obj;
		}
		Integer rs = Integer.parseInt(obj.toString());
		return rs;
	}
	
	/**
	 * 将一个Object转化为String
	 * @param obj
	 * @return
	 */
	public static String parse2String(Object obj) {
		if(obj == null) {
			return "";
		}
		if(obj instanceof String) {
			return (String)obj;
		}
		String rs = String.valueOf(obj);
		return rs;
	}
	
	/**
	 * 将一个Object转化为date
	 * @param obj
	 * @return
	 */
	public static Date parse2Date(Object obj) {
		if(obj == null) {
			return null;
		}
		if(obj instanceof Date) {
			return (Date)obj;
		}
		throw new RuntimeException("obj不是日期类型");
	}
	
	/**
	 * 将一个Object转化为yyyy-mm-dd
	 * @param obj
	 * @return
	 */
	public static String parseDate(String obj) {
		if(GlobalUtil.isNull((String)obj)) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		try {
			dt = df.parse(obj);
			return df.format(dt);
		} catch (Exception e) {
			throw new RuntimeException("obj不是日期类型");
		}
		
	}
	
	/**
	 * 判断日期串是否为指定格式，返回布尔值
	 * 
	 * @param strDate
	 *            String型，日期字符串
	 * @param strFormat
	 *            String型，日期格式，年、月、日均为小写，如:y、m、d
	 * @return 
	 * 			  boolean值， true，日期为指定格式；false，日期不是指定格式
	 */
	public static boolean isDate(String strDate, String strFormat) {
		String strReg = "";
		if (strFormat.equalsIgnoreCase("yyyy-mm-dd")) {
			strReg = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";
		} else if (strFormat.equalsIgnoreCase("yyyy-mm")) {
			strReg = "[0-9]{4}-[0-9]{1,2}";
		} else if (strFormat.equalsIgnoreCase("yyyy/mm/dd")) {
			strReg = "[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}";
		} else if (strFormat.equalsIgnoreCase("dd/mm/yyyy")) {
			strReg = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
		} else if (strFormat.equalsIgnoreCase("yyyy/mm/dd")) {
			strReg = "[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}";
		} else if (strFormat.equalsIgnoreCase("yyyy\\mm\\dd")) {
			strReg = "[0-9]{4}\\[0-9]{1,2}\\[0-9]{1,2}";
		} else if (strFormat.equalsIgnoreCase("dd\\mm\\yyyy")) {
			strReg = "[0-9]{1,2}\\[0-9]{1,2}\\[0-9]{4}";
		}

		if (strDate.matches(strReg)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 匹配汉字,判断是否是汉字
	 * @param name
	 * @return
	 */
	public static boolean isZh_cn(String name) {
		return Pattern.matches("^[\u4E00-\u9FA5]([\u4E00-\u9FA5]|\\s)*", name);
	}
	
	/**
	 * 判断字符串是否为数字类型，包括整型或者是小数类型，返回布尔值。
	 * 
	 * @param strValue
	 *            String型，数字字符串
	 * @return 
	 * 			  boolean值：true，数值型字符串；false，非字符型字符串
	 */
	public static boolean isNumber(String strValue) {
		return Pattern.matches("-*[0-9]*[.]{0,1}[0-9]*",strValue);
	}
	
	/**
	 * 判断是否是长数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		return  Pattern.matches("[0-9]*", str);    
	}
	
	/**
	 * 将科学计数法字符串转换成长数值型
	 * @param str
	 * @return
	 */
	public static String parse2Decimal(String str){
		try {
			return new BigDecimal(str).toPlainString();
		} catch (Exception e) {
			return str;
		}
	}
	
	/**
	 * 替换所有单引号
	 * @param str
	 * @return
	 */
	public static String replaceMask(String str) {
		return str.replaceAll("'", "''");
	}
	
	  /**
	   * 获取当前时间
	   * 精确到分钟:2005-01-17 17:22:55
	   * @return
	   */
	  public static String getNowTime() {
	    long times = System.currentTimeMillis();
	    return new Date(times).toString() + " " + new Time(times).toString();
	  }
	  
	  public static String getCurrentTime() {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  return sdf.format(new Date());
	}
	  
  /**
	 * 返回sub位置
	 * @param str
	 * @param sub
	 * @return
	 */
	public static int getIndex(String str,String sub,int fromIndex){
		return str.indexOf(sub,fromIndex)>-1?str.indexOf(sub,fromIndex):str.indexOf(sub.toUpperCase(),fromIndex);
	}
	public static int getIndex(String str,String sub){
		int fromIndex=0;
		return str.indexOf(sub,fromIndex)>-1?str.indexOf(sub,fromIndex):str.indexOf(sub.toUpperCase(),fromIndex);
	}
	public  static void renderObjact(Object obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if(obj==null || obj.getClass().getName().startsWith("java")){
			return;
		}
		Class<?> cls = obj.getClass();
		Field [] field = cls.getDeclaredFields();
		for(Field f:field){
			Class<?> type = f.getType();
			if(type.getName()=="java.lang.String"){
				String name = f.getName();
				StringBuffer setmethodName = new StringBuffer();
				setmethodName.append("set");
				setmethodName.append(name.substring(0, 1).toUpperCase());
				setmethodName.append(name.substring(1));
				Method set = cls.getMethod(setmethodName.toString(), String.class);
				StringBuffer getmethodName = new StringBuffer();
				getmethodName.append("get");
				getmethodName.append(name.substring(0, 1).toUpperCase());
				getmethodName.append(name.substring(1));
				Method get = cls.getMethod(getmethodName.toString());
				Object result  = get.invoke(obj);
				if(result!=null){
					String val = result.toString();
					set.invoke(obj,replaceMask(val));
				}
			}
		}
		 
	}
	public  static String  renderObjact(Object obj,String tableName ,int sql_type,String condition) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		StringBuffer sql = new StringBuffer();
		switch(sql_type){
		case 1:
			Map<String,String> result1 = preHandle(obj,true);
			sql.append("insert into "+tableName+"(");
			String temp1 = result1.keySet().toString();
			sql.append(temp1.substring(1, temp1.length()-1));
			sql.append(") values(");
			String temp2 = result1.values().toString();
			sql.append(temp2.substring(1, temp2.length()-1));
			sql.append(")"); 
			break;
		case 2:
			sql.append("update "+tableName+" set ");
			Map<String,String> result2 = preHandle(obj,false);
			Set<Entry<String,String>> entryset = result2.entrySet();
			String temp = entryset.toString();
			sql.append(temp.substring(1, temp.length()-1));
			sql.append(CommonUtil.getStringValue(condition));
			break;
		case 3:
			sql.append("delete from "+tableName+" ");
			sql.append(CommonUtil.getStringValue(condition));
			break;
	  }
	  return sql.toString();
	}
	public static Map<String,String> preHandle(Object obj,boolean add) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if(obj==null ){
			return null;
		}
		Map<String,String> result = new HashMap<String,String>();
		Class<?> cls = obj.getClass();
		Field [] field = cls.getDeclaredFields();
		for(Field f:field){
			Class<?> type = f.getType();
			if(type.getName()=="java.lang.String"){
				String name = f.getName();
				StringBuffer getmethodName = new StringBuffer();
				getmethodName.append("get");
				getmethodName.append(name.substring(0, 1).toUpperCase());
				getmethodName.append(name.substring(1));
				Method get = cls.getMethod(getmethodName.toString());
				Object o = get.invoke(obj);
				if(add){
					if(name.endsWith("date")){
						result.put(name, "sysdate");
						continue;
					}
					if(o==null){
						result.put(name, "null");
					}else{
						result.put(name, "'"+o.toString()+"'");
					}
				}else{	
					if(o!=null){
						result.put(name, "'"+o.toString()+"'");
					} 
				}
			}
		}
		return result;
	}
	
	//生成随机数
	private static String codeList = "abcdefghijklmnopqrstuvwxyz1234567890";
	public static String getRadomCode(int length){
		String s = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int pos = random.nextInt(codeList.length()-1);
			s += codeList.substring(pos, pos+1);
		}
		return s;
	}
	
	//生成随机字符串
	public static String getRandomID(int length){
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		sb.append(System.currentTimeMillis());
		int size = length - sb.length();
		for (int i = 0; i < size; i++)
			sb.append(random.nextInt(10));

		String s = sb.toString();
		if (s.length() > length)
			s = s.substring(s.length() - length);
		return s;
	}
	
	//按照字节数截取字符串长度
	public static String getCutString(String str,int byteLength){
		if(trimToNull(str) == null)
			return str;
		
		char[] strArray = str.toCharArray();
		int length = strArray.length;
		
		String returnStr = "";
		int strByteLength = str.getBytes().length;
		
		for (int i = 0; i < length; i++) {
			returnStr += strArray[i];
			int temp = returnStr.getBytes().length;
			if(temp > byteLength && temp < strByteLength){
				returnStr += "...";
				break;
			}
		}
		
		return returnStr;
	}
	
	/**
	 * 根据baseKey对valStr进行加密
	 * */
	public static String encryptValStr(String baseKey ,String valStr){
		if(null == valStr || "".equals(valStr)){
			return baseKey;
		}
		
		if((null == baseKey || "".equals(baseKey))){
			return getASCII(valStr + "00",0);
		}
		String value = getASCII(valStr,Integer.parseInt(baseKey.substring(2, 3), 16));
		int ridLength = valStr.length() * 2 + 2;
		int index = (baseKey.length() - ridLength <= 0) ? 0 : (baseKey.length() - ridLength);
		StringBuffer key = new StringBuffer(baseKey.substring(0, index));
		return key.append(value + ((index < 10) ? "0"+index : String.valueOf(index))).toString();
	}
	
	/**
	 * 得到某字符串的每个字符的十六进制ASCII码（大写）
	 * */
	private static String getASCII(String value,int in){
		String afterEn = "";
		for(int i = 0;i < (null == value ? 0 : value.length());i++ ){
			String s = Integer.toHexString((int)value.charAt(i) + in).toUpperCase();
			String str = s.length() <= 1 ? "0"+s : s;
			afterEn += str;				
		}
		return afterEn;
	}
	
	/**
	 * 根据baseKey对value进行解密
	 * */
	public static String unEncryptValue(String baseKey,String value){
		if(null == value || "".equals(value) || value.length() < 2){
			return "";
		}
		int in = 0;
		if((null != baseKey && !"".equals(baseKey))){
			in = Integer.parseInt(baseKey.substring(2, 3), 16);
		}
		int index = Integer.parseInt(value.substring(value.length()-2, value.length()));
		if(index < 0 || index > value.length()){
			return "";
		}
		String valStr = value.substring(index, value.length() - 2);	//加密后的明文(十六进制的ASCII)
		String unEnStr = "";
		for(int i = 0;i < (null == valStr ? 0 : valStr.length());i+=2){
			char ch = (char)(Integer.parseInt(valStr.substring(i, i+2), 16) - in);
			unEnStr += ch;
		}
		return unEnStr;
	}
	
	/**
	 * MD5加密字符串
	 * @param plainText
	 * @return
	 */
	public static String Md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
//			.substring(8,24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将字符串转化为sqlDate
	 * @param str eg:20120101
	 * @param format eg: yyyyMMdd
	 * @return java.sql.Date
	 */
	public static java.sql.Date parseSQLDate(String str ,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date udate;
		try {
			udate = sdf.parse(str);
			java.sql.Date date = new java.sql.Date(udate.getTime());
			return date;
		} catch (ParseException e) {
			new Exception("日期格式转换出错.");
		}
		return null;
	}
	
	/**
	 * list反转
	 * @param list
	 * @return
	 */
	public  static <T> List<T> reverse(List<T> list){
		List<T> newList = new ArrayList<T>();
		for (int i = list.size() - 1; i >=0; i--) {
			newList.add(list.get(i));
		}
		return newList;
	}
	
	public  static String join(String[] str, String split){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			if(i>0)
				sb.append(split);
			sb.append(str[i]);
		} 
		return sb.toString();
	}
	
	public  static String join(List<String> str, String split){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.size(); i++) {
			if(i>0)
				sb.append(split);
			sb.append(str.get(i));
		} 
		return sb.toString();
	}
	
	/**
	 * 获取参数并执行普通替换,可以传入默认值
	 * @param str
	 * @param left
	 * @param right
	 * @param map
	 * @param defaultValue
	 * @return
	 */
	public static String getParamsForReplace(String str,String left,String right,Map<String, Object> map,String defaultValue)
	{
		String param=getParam(str,left,right);
		while(!param.equals(""))
		{
			String v = getIgnoreCaseValue(map,param);
			str=CommonUtil.replace(str , left + param + right , v.equals("") ? defaultValue : v);
			param=getParam(str,left,right);
		}
		return str;
	}
	
	/**
	 * 获取参数并执行普通替换
	 * @param str
	 * @param left
	 * @param right
	 * @param map
	 * @return
	 */
	public static String getParamsForReplace(String str,String left,String right,Map<String, Object> map)
	{
		String param=getParam(str,left,right);
		while(!param.equals(""))
		{
		    str=CommonUtil.replace(str , left + param + right , getIgnoreCaseValue(map,param));
			param=getParam(str,left,right);
		}
		return str;
	}
	
	/**
	 * 取参数列表
	 * @param str
	 * @param left
	 * @param right
	 * @return
	 */
	public static List<String> getParams(String str,String left,String right)
	{
		List<String> params = new ArrayList<String>();
		String param=getParam(str,left,right);
		while(!param.equals(""))
		{
			params.add(param);
			str=CommonUtil.replace(str,left+param+right,"");
			param=getParam(str,left,right);
		}
		return params;
	}
	
	/**
	 * 取参数
	 * @param string
	 * @param leftFlag
	 * @param rightFlag
	 * @return
	 */
	public static String getParam(String string,String leftFlag,String rightFlag)
    {
        String param = "";
        while (string.indexOf(rightFlag) < string.indexOf(leftFlag) && string.indexOf(rightFlag)>=0)
        {
            string = string.substring(string.indexOf(rightFlag) + rightFlag.length());
        }

        if (string.indexOf(leftFlag) >= 0 && string.indexOf(rightFlag) >= 0)
        {
            int pos1 = string.indexOf(leftFlag);
            int pos2 = string.indexOf(rightFlag);
            param = string.substring(pos1 + leftFlag.length(), pos2);
        }

        return param;
    }
	
	/**
	 * 替换
	 * @param str
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String replace(String str, String str1, String str2)
	{
		for (int pos = str.indexOf(str1); pos >= 0; pos = str.indexOf(str1))
			str = String.valueOf((new StringBuffer(str.substring(0, pos))).append(str2).append(str.substring(str1.length() + pos)));

		return str;
	}
	
	/**
	 * 转码
	 * @param str
	 * @param code
	 * @return
	 */
	public static String transCode(Object str,String code){
		String value = String.valueOf(str);
		try {
			value = new String(value.getBytes("UTF-8"),code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
}
