/**
 * FileName:     BaseRegexManager.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-1 上午11:23:24 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-1       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.support;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shuhao.clean.apps.validate.vrules.domain.Regex;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public class BaseRegexManager {

	private static final Logger logger = Logger.getLogger(BaseRegexManager.class);

	private static Map<String, Regex> regexMap = new HashMap<String, Regex>();
	
	/**
		01：文本
		02：数值
		03：日期
	 */
		
	public static String REGEX_01 = "01";
	public static String REGEX_02 = "02";
	public static String REGEX_03 = "03";
	
	static{
		regexMap.put(REGEX_01, new Regex(REGEX_01, "请输入文本", "*"));
		regexMap.put(REGEX_02, new Regex(REGEX_02, "请输入数字", "^[+\\-]?\\d+(.[0-9]{1,4})?$"));
		regexMap.put(REGEX_03, new Regex(REGEX_02, "请输入日期[yyyy-mm-dd]", "^\\d{4}-\\d{1,2}-\\d{1,2}$"));
	}
	
	public static Regex lookRegex(String key){
		if(regexMap.containsKey(key)){
			return regexMap.get(key);
		}else{
			logger.error("不存在该类型["+key+"]的通用正则表达式");
		}
		return null;
	}
}
