/**
 * FileName:     ThreePanelLayout.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     Ning
 * @version     V1.0  
 * Createdate:         2014-12-8 下午8:40:48
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-8       Ning          1.0             1.0
 * Why & What is modified: 
 */
package com.shuhao.clean.apps.model.ext;


/**center east nourth 布局panel
 * @author Ning
 *
 */
public class ThreePanelLayout extends Layout {

	private String eastWidth;
	private String southHeight;
	private BasePanel centerPanel;
	private BasePanel eastPanel;
	private BasePanel southPanel;
	
	
	/**
	 * @return the eastWidth
	 */
	public String getEastWidth() {
		return eastWidth;
	}


	/**
	 * @param eastWidth the eastWidth to set
	 */
	public void setEastWidth(String eastWidth) {
		this.eastWidth = eastWidth;
	}




	/**
	 * @return the southHeight
	 */
	public String getSouthHeight() {
		return southHeight;
	}


	/**
	 * @param southHeight the southHeight to set
	 */
	public void setSouthHeight(String southHeight) {
		this.southHeight = southHeight;
	}


	/**
	 * @return the southPanel
	 */
	public BasePanel getSouthPanel() {
		return southPanel;
	}


	/**
	 * @param southPanel the southPanel to set
	 */
	public void setSouthPanel(BasePanel southPanel) {
		this.southPanel = southPanel;
	}


	/**
	 * @return the centerPanel
	 */
	public BasePanel getCenterPanel() {
		return centerPanel;
	}


	/**
	 * @param centerPanel the centerPanel to set
	 */
	public void setCenterPanel(BasePanel centerPanel) {
		this.centerPanel = centerPanel;
	}


	/**
	 * @return the eastPanel
	 */
	public BasePanel getEastPanel() {
		return eastPanel;
	}


	/**
	 * @param eastPanel the eastPanel to set
	 */
	public void setEastPanel(BasePanel eastPanel) {
		this.eastPanel = eastPanel;
	}



	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.model.ext.Ext#output()
	 */
	public String output() throws Exception {
		StringBuffer sb =  new StringBuffer();
		sb.append("{").append(enter).append("xtype:'panel',").append(enter);
		sb.append("layout:'border',").append(enter).append("items:[{").append(enter);
		sb.append("layout:'border',").append(enter).append("region:'center',").append(enter).append("items:[").append(enter);
		sb.append(centerPanel.output());
		if(southPanel!=null){
			sb.append(",").append(enter).append(southPanel.output()).append(enter);
		}
		sb.append("]}");
		if(eastPanel!=null){
			sb.append(",");
			sb.append(eastPanel.output()).append(enter);
		}
		sb.append("]}").append(enter);
		return sb.toString();
	}

}
