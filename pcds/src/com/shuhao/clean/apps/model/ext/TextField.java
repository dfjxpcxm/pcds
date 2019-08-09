package com.shuhao.clean.apps.model.ext;

public class TextField extends BaseField {
	protected boolean allowBlank = true;
	protected String blankText = "该项不能为空";
	protected Integer minLength;
	protected Integer maxLength;
	protected String minLengthText;
	protected String maxLengthText ;
	
	public String output(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("_"+this.name).append("Field = new Ext.form.TextField({").append(enter);
		buffer.append(this.fieldParams()).append(",").append(enter);
		buffer.append("allowBlank : ").append(this.allowBlank);
		if(this.allowBlank){
			buffer.append(",").append(enter);
			buffer.append("blankText : '").append(this.blankText).append("'");
		}
		if(isNotNull(this.minLength)){
			buffer.append(",").append(enter);
			buffer.append("minLength : ").append(this.minLength).append(",").append(enter);
			buffer.append("minLengthText : '").append(this.minLengthText).append("'");
		}
		if(isNotNull(this.maxLength)){
			buffer.append(",").append(enter);
			buffer.append("maxLength : ").append(this.maxLength).append(",").append(enter);
			buffer.append("maxLengthText : '").append(this.maxLengthText).append("'");
		}
		buffer.append(enter);
		buffer.append("})").append(enter);
		return buffer.toString();
	}

	public boolean isAllowBlank() {
		return allowBlank;
	}

	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}

	public String getBlankText() {
		return blankText;
	}

	public void setBlankText(String blankText) {
		this.blankText = blankText;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getMinLengthText() {
		return minLengthText;
	}

	public void setMinLengthText(String minLengthText) {
		this.minLengthText = minLengthText;
	}

	public String getMaxLengthText() {
		return maxLengthText;
	}

	public void setMaxLengthText(String maxLengthText) {
		this.maxLengthText = maxLengthText;
	}
	
}
