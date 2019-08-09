/**
 * FileName:     TabPanel.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     Ning
 * @version     V1.0  
 * Createdate:         2014-12-8 下午8:50:44
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-8       Ning          1.0             1.0
 * Why & What is modified: 
 */
package com.shuhao.clean.apps.model.ext;

import java.util.List;

import com.shuhao.clean.apps.model.ext.base.BaseExt;


/**tabPanle
 * @author Ning
 *
 */
public class TabPanel extends BasePanel {

	private String region = "south";
	private int activeTab = 0;
	private List<BaseExt> panels;
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.model.ext.Ext#output()
	 */
	@Override
	public String output() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("{").append(enter).append("xtype:'tabpanel',").append(enter);
		if(width!=null){
			sb.append("width:"+width+",").append(enter);
		}
		if(height!=null){
			sb.append("height:"+height+",").append(enter);
		}
		sb.append("region:'"+region+"',").append(enter);
		sb.append("activeTab:"+activeTab+",").append(enter);
		sb.append("items:[").append(enter);
		for (int i = 0; i < panels.size(); i++) {
			BaseExt bp  = panels.get(i);
			sb.append(bp.output());
			if(i<panels.size()-1){
				sb.append(",");
			}
		}
		sb.append("]}").append(enter);
		return sb.toString();
	}
	/**
	 * @return the layout
	 */
	public String getLayout() {
		return layout;
	}
	/**
	 * @param layout the layout to set
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}
	/**
	 * @return the activeTab
	 */
	public int getActiveTab() {
		return activeTab;
	}
	/**
	 * @param activeTab the activeTab to set
	 */
	public void setActiveTab(int activeTab) {
		this.activeTab = activeTab;
	}
	/**
	 * @return the panels
	 */
	public List<BaseExt> getPanels() {
		return panels;
	}
	/**
	 * @param panels the panels to set
	 */
	public void setPanels(List<BaseExt> panels) {
		this.panels = panels;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * 没有直接的引用对象
	 */
	public String getCurrentRef() {
		// TODO Auto-generated method stub
		return null;
	}
}
