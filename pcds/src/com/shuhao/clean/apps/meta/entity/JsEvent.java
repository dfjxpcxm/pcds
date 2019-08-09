/**
 * FileName:     JsEvent.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-9 上午11:02:54 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-9       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.meta.entity;

import org.dom4j.Element;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public class JsEvent extends XmlObject {
	private String jscode;
	 
	public String getJscode() {
		return jscode;
	}

	public void setJscode(String jscode) {
		this.jscode = jscode;
	}



	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.entity.XmlObject#transXmlObject(org.dom4j.Element)
	 * <br>
	 * element.element('event')
	 */
	public void transXmlObject(Element event) {
		logger.debug("解析js类型事件。");
		this.setEventName(event.attributeValue("name"));
		this.setParam(event.attributeValue("param"));
		this.setExeType(event.attributeValue("exetype"));
		this.setJscode(event.element("jscode").getTextTrim());
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.entity.XmlObject#getOutput()
	 */
	public String getOutput() {
		return this.jscode;
	}
}
