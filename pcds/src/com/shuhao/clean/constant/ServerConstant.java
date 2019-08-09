package com.shuhao.clean.constant;

/**
 * 服务器级别的常量
 * @author gongzy
 *
 */
public interface ServerConstant {
	//应用访问路径
	public static final String WEB_DIR = "http://192.168.15.74:8080";

    public static final String TOMCAT_WEB_DIR = ""; 
    
	/**
	 * zip文件临时目录
	 */
	public static String ZIP_UPLOAD_DIR_TMP ="/ftp/upload/temp/";
	
	/**
	 * 是否实时更新菜单,菜单维护时使用该标识符
	 */
	public static final boolean RESOURCE_AUTO_LOAD = true;
	
	/**
	 * 上传文件路径 
	 */
	public static String UPLOAD_DIRCTORY = "upload/";
	
}
