package com.shuhao.clean.apps.model.ext;


public class TreeBox extends BaseField {
	
	private String name;
//	private String nodeId;
//	private String nodeName;
	private String anchor;
	private String textLabel;
	private String url;
	private String rootId;
	private String rootName;
	private String dim_code;
	private String realName;
	private boolean readOnly = false;
	private boolean allowBlank = true;
	private boolean hidden = false;
	
	
	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("new TreeCombo({" ).append(enter);
		buffer.append("name:'").append(this.name).append("',").append(enter);
		buffer.append("label:'").append(this.textLabel).append("',").append(enter);
		buffer.append("rootId:'").append(this.rootId).append("',").append(enter);
		buffer.append("rootName:'").append(this.rootName).append("',").append(enter);
		buffer.append("realName:'").append(this.realName).append("',").append(enter);
		if(getValue()!=null){
			buffer.append("value:'").append(getValue()).append("',").append(enter);
		}
		buffer.append("dim_code:'").append(this.dim_code).append("',").append(enter);
		buffer.append("readOnly:").append(this.readOnly).append(",").append(enter);
		buffer.append("hidden:").append(this.hidden).append(",").append(enter);
		buffer.append("allowBlank:").append(this.allowBlank).append(enter);
		buffer.append("})").append(enter);
		return buffer.toString();
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public String getTextLabel() {
		return textLabel;
	}

	public void setTextLabel(String textLabel) {
		this.textLabel = textLabel;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isAllowBlank() {
		return allowBlank;
	}

	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}

	public String getDim_code() {
		return dim_code;
	}

	public void setDim_code(String dim_code) {
		this.dim_code = dim_code;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	
}
