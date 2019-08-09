package com.shuhao.clean.apps.model.ext;

public class MoneyField extends BaseField {
	protected boolean allowBlank = true;
	protected String blankText = "该项不能为空";
	protected Integer minLength;
	protected Integer MaxLength;
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
		if(isNotNull(this.MaxLength)){
			buffer.append(",").append(enter);
			buffer.append("MaxLength : ").append(this.MaxLength).append(",").append(enter);
			buffer.append("maxLengthText : '").append(this.maxLengthText).append("'");
		}
		buffer.append(",").append(enter);
		buffer.append("listeners :{").append(enter);
		buffer.append("	blur : function(field) { ");
		buffer.append("		field.setValue(fmoney(field.getValue()));");
		buffer.append("	}");
//		buffer.append("	show : function(field) { ");
//		buffer.append("		field.setValue(fmoney(field.getValue()));");
//		buffer.append("	}");
		buffer.append("}").append(enter);
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
