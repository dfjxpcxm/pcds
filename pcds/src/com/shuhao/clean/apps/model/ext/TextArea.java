package com.shuhao.clean.apps.model.ext;

public class TextArea extends BaseField {
	private Integer growMax ;
	private boolean grow = true;
	private boolean allowBlank = true;
	private String blankText = "该项不能为空";
	private Integer minLength;
	private Integer MaxLength;
	private String minLengthText;
	private String maxLengthText ;
	
	public String output() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("_"+this.name).append("Area = new Ext.form.TextArea({").append(enter);
		buffer.append(this.fieldParams()).append(",").append(enter);
		buffer.append("allowBlank : ").append(this.allowBlank);
		if(this.allowBlank){
			buffer.append(",").append(enter);
			buffer.append("blankText : '").append(this.blankText).append("'");
		}
		if(minLength != null){
			buffer.append(",").append(enter);
			buffer.append("minLength : ").append(this.minLength).append(",").append(enter);
			buffer.append("minLengthText : '").append(this.minLengthText).append("'");
		}
		if(MaxLength != null){
			buffer.append(",").append(enter);
			buffer.append("MaxLength : ").append(this.MaxLength).append(",").append(enter);
			buffer.append("maxLengthText : '").append(this.maxLengthText).append("'");
		}
		buffer.append(",").append(enter);
		buffer.append(" grow : ").append(this.grow);
		
		if(this.grow){
			buffer.append(",").append(enter);
			buffer.append(" growMax : ").append(this.growMax);
		}
		buffer.append("})");
		return buffer.toString();
	}

	public Integer getGrowMax() {
		return growMax;
	}

	public void setGrowMax(Integer growMax) {
		this.growMax = growMax;
	}

	public boolean isGrow() {
		return grow;
	}

	public void setGrow(boolean grow) {
		this.grow = grow;
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
