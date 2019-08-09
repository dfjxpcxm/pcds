/**
 * FileName:     Panel.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     Ning
 * @version     V1.0  
 * Createdate:         2015-1-19 上午10:03:05
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-1-19       Ning          1.0             1.0
 * Why & What is modified: 
 */
package com.shuhao.clean.apps.model.ext;



/**
 * @author Ning
 *
 */
public class Panel extends BasePanel {

	private String panelName = "panel"+System.currentTimeMillis();
	/**
	 * @return the panelName
	 */
	public String getPanelName() {
		return panelName;
	}
	/**
	 * @param panelName the panelName to set
	 */
	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.model.ext.Layout#output()
	 */
	@Override
	public String output() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" var ").append(panelName).append(" = new Ext.Panel({");
		sb.append(this.getBasePanel());
		sb.append("});");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.model.ext.BasePanel#getCurrentRef()
	 */
	public String getCurrentRef() {
		return this.panelName;
	}
}
