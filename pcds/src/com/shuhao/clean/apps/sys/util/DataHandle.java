package com.shuhao.clean.apps.sys.util;
/**
 * 
 * ClassName: DataHandle 
 * @Description: 通用处理类
 * @author yanshuquan
 * @date 2015-6-3 下午2:40:02
 */
public class DataHandle {
	/**
	 * 
	 * @Description: 得到sql条件查询语句
	 * @param @param pkIdArray 主键数组
	 * @param @param pkCodeArray 主键值数组
	 * @param @return   
	 * @return String  
	 */
 public static String getSqlStr(String[] pkIdArray,String[] pkCodeArray){
	 StringBuffer sql_bf=new StringBuffer();
	 for(int i=0;i<pkCodeArray.length;i++){
		 for(int f=0;f<pkIdArray.length;f++){
			 Object pk=pkIdArray[f];
			 Object val=toSqlParam(pkCodeArray[i]);
			 sql_bf.append(""+pk+"="+val+" or "); 
		 }
	  }
 	String sql_str=sql_bf.substring(0, sql_bf.lastIndexOf("or"));
 	return sql_str;
 }
	/**
	 * 转换为sql语句识别的参数
	 * @Description: TODO
	 * @param @param str
	 * @param @return   
	 * @return String  
	 */
 public static String toSqlParam(Object str){
 	String param=String.valueOf(str);
 	if(param.indexOf("'")==-1){
 		param="'"+param+"'";
		}
 	return param;
 }
}
