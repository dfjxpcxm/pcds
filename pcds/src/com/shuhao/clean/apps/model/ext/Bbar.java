package com.shuhao.clean.apps.model.ext;

import com.shuhao.clean.apps.model.ext.base.BaseExt;

public class Bbar extends BaseExt {
	private String name = "pageTool";
	private int pageSize = 30;
	private boolean displayInfo = true;
	private String storeName;
	private String displayMsg = "显示第 {0} 条到 {1} 条记录，一共 {2} 条";
	private String emptyMsg = "没有记录";
	private String firstText = "第一页";
	private String prevText = "上一页";
	private String nextText = "下一页";
	private String lastText = "最后一页";
	private String refreshText = "刷新";

	
	public String output() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.name).append(" = new Ext.PagingToolbar({").append(enter);
		buffer.append("pageSize : ").append(this.pageSize).append(",").append(enter);
		buffer.append("displayInfo : ").append(this.displayInfo).append(",").append(enter);
		buffer.append("store : ").append(this.storeName).append(",").append(enter);
		buffer.append("displayMsg : '").append(this.displayMsg).append("',").append(enter);
		buffer.append("emptyMsg : '").append(this.emptyMsg).append("',").append(enter);
		buffer.append("firstText : '").append(this.firstText).append("',").append(enter);
		buffer.append("prevText : '").append(this.prevText).append("',").append(enter);
		buffer.append("nextText : '").append(this.nextText).append("',").append(enter);
		buffer.append("lastText : '").append(this.lastText).append("',").append(enter);
		buffer.append("refreshText : '").append(this.refreshText).append("'").append(enter);
		buffer.append("})");
		return buffer.toString();
	}
	
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isDisplayInfo() {
		return displayInfo;
	}
	public void setDisplayInfo(boolean displayInfo) {
		this.displayInfo = displayInfo;
	}


	public String getDisplayMsg() {
		return displayMsg;
	}
	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}
	public String getEmptyMsg() {
		return emptyMsg;
	}
	public void setEmptyMsg(String emptyMsg) {
		this.emptyMsg = emptyMsg;
	}
	public String getFirstText() {
		return firstText;
	}
	public void setFirstText(String firstText) {
		this.firstText = firstText;
	}
	public String getPrevText() {
		return prevText;
	}
	public void setPrevText(String prevText) {
		this.prevText = prevText;
	}
	public String getNextText() {
		return nextText;
	}
	public void setNextText(String nextText) {
		this.nextText = nextText;
	}
	public String getLastText() {
		return lastText;
	}
	public void setLastText(String lastText) {
		this.lastText = lastText;
	}
	public String getRefreshText() {
		return refreshText;
	}
	public void setRefreshText(String refreshText) {
		this.refreshText = refreshText;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getStoreName() {
		return storeName;
	}


	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	
	
}
