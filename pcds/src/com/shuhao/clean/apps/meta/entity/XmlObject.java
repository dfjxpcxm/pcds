/**
 * FileName:     XmlObject.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-9 上午11:25:25 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-9       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.meta.entity;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public abstract class XmlObject {
	
	protected static Logger logger = LoggerFactory.getLogger(XmlObject.class);
	
	private String eventName;
	private String param;
	private String exeType;
	
	private String callback; //回调
	/**
	 * 转换
	 * @param element
	 */
	public abstract void transXmlObject(Element element);
	/**
	 * 自定义输出
	 */
	public abstract String getOutput();
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getExeType() {
		return exeType;
	}
	public void setExeType(String exeType) {
		this.exeType = exeType;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	/**
	 * 将开头和结尾的空
	 * @param str
	 * @return
	 */
	public String trim(String str){
		return str.replaceAll("^\\s*|\\s*$", "");
	}
}
