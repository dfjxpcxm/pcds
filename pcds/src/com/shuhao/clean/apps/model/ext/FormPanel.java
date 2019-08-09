package com.shuhao.clean.apps.model.ext;


public class FormPanel extends BasePanel  {
	private String url;
	private int labelWidth = 90;
	private String region = "north" ;
	private String method = "POST";
	private String labelAlign = "left";
	private String labelSepaator = ":";
	private boolean collapsible = false;
	private boolean split = true;
	private String submitUtl ;
	private JsonReader reader;
	private JsonErrorReader errorReader;
	
	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("_"+this.id).append("Form = new Ext.form.FormPanel({").append(enter);
		if(isNotNull(this.region))
			buffer.append("region : '").append(this.region).append("',").append(enter);
		buffer.append("labelWidth : ").append(this.labelWidth).append(",").append(enter);
		buffer.append("labelAlign : '").append(this.labelAlign).append("',").append(enter);
		buffer.append("labelSepaator : '").append(this.labelSepaator).append("',").append(enter);
		buffer.append("collapsible : ").append(this.collapsible).append(",").append(enter);
		buffer.append("split : ").append(this.split).append(",").append(enter);
		if(reader != null){
			buffer.append("reader : ").append(this.reader.output()).append(",").append(enter);
		}
		if(this.errorReader != null) {
			buffer.append("errorReader : ").append(this.errorReader.output()).append(",").append(enter);
		}
		buffer.append(this.getBasePanel()).append(enter);
		buffer.append(defaultEvent());//加载默认事件
		buffer.append("})");
		return buffer.toString();
	}
	
	
//	actioncomplete: function(form, action){
//        if(action.type == 'load'){
//             初始化
//        }
//    }
	public String defaultEvent(){
		StringBuffer sb = new StringBuffer();
		sb.append(",listeners:{");
		sb.append("actioncomplete: function(form, action){");
		sb.append("if(action.type == 'load'){");
		//formload的时候初始化信息
		sb.append("reloadTreeCombo();");
		sb.append("}");
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getLabelAlign() {
		return labelAlign;
	}

	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}

	public String getLabelSepaator() {
		return labelSepaator;
	}

	public void setLabelSepaator(String labelSepaator) {
		this.labelSepaator = labelSepaator;
	}

	public boolean isCollapsible() {
		return collapsible;
	}

	public void setCollapsible(boolean collapsible) {
		this.collapsible = collapsible;
	}

	public boolean isSplit() {
		return split;
	}

	public void setSplit(boolean split) {
		this.split = split;
	}

	public String getSubmitUtl() {
		return submitUtl;
	}

	public void setSubmitUtl(String submitUtl) {
		this.submitUtl = submitUtl;
	}

	public JsonReader getReader() {
		return reader;
	}

	public void setReader(JsonReader reader) {
		this.reader = reader;
	}

	public JsonErrorReader getErrorReader() {
		return errorReader;
	}

	public void setErrorReader(JsonErrorReader errorReader) {
		this.errorReader = errorReader;
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.model.ext.BasePanel#getCurrentRef()
	 */
	public String getCurrentRef() {
		return "_"+this.id+"Form";
	}
}
