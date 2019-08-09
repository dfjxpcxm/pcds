package com.shuhao.clean.apps.model.ext;

public class FieldSet extends BasePanel {
	private String title;
	private boolean collapsible = true;
	private boolean collapsed = true;
	private boolean autoHeight = true;
	private String bodyStyle = "padding:5px 5px 0";
	private String layout;
	private String item ;
	
	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
			buffer.append(this.id).append("Set = new Ext.form.FieldSet({");
			buffer.append("title : '").append(this.title).append("',").append(enter);
			buffer.append("layout : '").append(this.layout).append("',").append(enter);
			buffer.append("autoHeight : ").append(this.autoHeight).append(",").append(enter);
			buffer.append("bodyStyle:'").append(this.bodyStyle).append("',").append(enter);
			buffer.append("collapsible :").append(this.collapsible).append(",").append(enter);
			buffer.append("collapsed :").append(this.collapsed).append(",").append(enter);
			if (this.item != null && !"".equals(this.item)) {
				buffer.append("items : [").append(this.item).append("]").append(enter);
			}
			buffer.append("})");
		return buffer.toString();
	}

	public boolean isCollapsible() {
		return collapsible;
	}

	public void setCollapsible(boolean collapsible) {
		this.collapsible = collapsible;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.model.ext.BasePanel#getCurrentRef()
	 */
	public String getCurrentRef() {
		return this.id+"Set";
	}
}
