package com.shuhao.clean.apps.model.ext;


public class CustField extends BaseField {
	private String id;
	private String name;
	private String anchor;
	private String realName;
	private String fieldLabel;
	private boolean showIcon = true;
	private boolean readOnly = false;
	private boolean allowBlank = true;
	private boolean hidden = false;
	
	
	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("new Ext.MyField({" ).append(enter);
		buffer.append("id:'").append(this.id).append("',").append(enter);
		buffer.append("name:'").append(this.name).append("',").append(enter);
		buffer.append("anchor:'").append(this.anchor).append("',").append(enter);
		buffer.append("fieldLabel:'").append(this.fieldLabel).append("',").append(enter);
		buffer.append("realName:'").append(this.realName).append("',").append(enter);
		buffer.append("readOnly:").append(this.readOnly).append(",").append(enter);
		buffer.append("hidden:").append(this.hidden).append(",").append(enter);
		buffer.append("showIcon:").append(this.showIcon).append(",").append(enter);
		buffer.append("allowBlank:").append(this.allowBlank).append(enter);
		buffer.append("})").append(enter);
		return buffer.toString();
	}


	
	
	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}


	public String getAnchor() {
		return anchor;
	}


	public String getRealName() {
		return realName;
	}


	public String getFieldLabel() {
		return fieldLabel;
	}


	public boolean isReadOnly() {
		return readOnly;
	}


	public boolean isAllowBlank() {
		return allowBlank;
	}


	public boolean isHidden() {
		return hidden;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}


	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}


	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}


	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}




	public boolean isShowIcon() {
		return showIcon;
	}




	public void setShowIcon(boolean showIcon) {
		this.showIcon = showIcon;
	}
	
	
}
