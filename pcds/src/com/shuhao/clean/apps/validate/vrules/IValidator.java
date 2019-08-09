/**
 * FileName:     IValidator.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-27 上午11:35:14 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-27       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules;


/**
 * @Description   校验入口接口
 * 
 * @author         gongzhiyang
 */
public interface IValidator {
	/**
	 * 校验类型 正则表达式
	 */
	public static final String VTYPE_REXGX="01";
	
	/**
	 * 校验类型 java
	 */
	public static final String VTYPE_JAVA="02";
	/**
	 * 校验类型 sql
	 */
	public static final String VTYPE_SQL="03";
	
}
