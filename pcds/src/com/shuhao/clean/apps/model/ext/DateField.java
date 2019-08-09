package com.shuhao.clean.apps.model.ext;

public class DateField extends BaseField {
	
	private String format = "Y-m-d";
	private boolean allowBlank = true;
	private String blankText = "该项不能为空";
	private Integer minLength;
	private Integer MaxLength;
	private String minLengthText;
	private String maxLengthText ;
	
	public String output() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("_"+this.name).append("Date = new Ext.form.DateField({").append(enter);
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
		if(isNotNull(this.MaxLength)){
			buffer.append(",").append(enter);
			buffer.append("MaxLength : ").append(this.MaxLength).append(",").append(enter);
			buffer.append("maxLengthText : '").append(this.maxLengthText).append("'");
		}
		buffer.append(",").append(enter);
		buffer.append("format : '").append(this.format).append("'").append(enter);
		buffer.append("})");
		return buffer.toString();
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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
		return MaxLength;
	}

	public void setMaxLength(Integer maxLength) {
		MaxLength = maxLength;
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
