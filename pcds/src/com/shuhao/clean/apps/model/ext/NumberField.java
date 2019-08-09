package com.shuhao.clean.apps.model.ext;

public class NumberField extends BaseField {
	
	protected boolean allowBlank = true;
	protected String blankText = "该项不能为空";
	/*private Integer minValue;
	private Integer maxValue;*/
	private Double minValue;
	private Double maxValue;

	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("_"+this.name).append("Field = new Ext.form.NumberField({").append(enter);
		buffer.append(this.fieldParams()).append(",").append(enter);
		buffer.append("allowBlank : ").append(this.allowBlank);
		if(this.allowBlank){
			buffer.append(",").append(enter);
			buffer.append("blankText : '").append(this.blankText).append("'");
		}
		if(isNotNull(this.minValue)){
			buffer.append(",").append(enter);
			buffer.append("minValue : ").append(this.minValue);
		}
		if(isNotNull(this.maxValue)){
			buffer.append(",").append(enter);
			buffer.append("maxValue : ").append(this.maxValue);
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

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}
	
	
}
