/**
 * FileName:     FnEvent.java
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

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public class FnEvent extends XmlObject {
	
	private String initSql;
	//关联的组件
	private List<String> comps;
	
	public String getInitSql() {
		return initSql;
	}
	public void setInitSql(String initSql) {
		this.initSql = initSql;
	}
	public List<String> getComps() {
		return comps;
	}
	public void setComps(List<String> comps) {
		this.comps = comps;
	}
	public void addComps(String comp) {
		if(this.comps==null){
			this.comps = new ArrayList<String>();
		}
		this.comps.add(comp);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.entity.XmlObject#transXmlObject(org.dom4j.Element)
	 * <br>
	 * element.element('event')
	 */
	public void transXmlObject(Element event) {
		logger.debug("解析ajax类型事件。");
		this.setEventName(event.attributeValue("name"));
		this.setExeType(event.attributeValue("exetype"));
		this.setInitSql(event.element("initsql").getTextTrim());
		Element initcomps = event.element("initcomps");
	    List<Element> comps = initcomps.elements("comp");
	    for (Element e : comps) {
			addComps(e.attributeValue("name"));
		}
	    //this.setCallback(event.element("callback").getTextTrim());
	}
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.entity.XmlObject#getOutput()
	 */
	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}
}
