package com.shuhao.clean.apps.model.ext;

import com.shuhao.clean.apps.model.ext.base.ExtEvent;

public abstract class BaseField extends BoxComponent {
	protected String inputType;
	protected String name;
	protected Object value;
	protected String invalidText = "输入数据无效";
	protected boolean readOnly = false;
	protected boolean editable = true;
	protected String realName;
	
	protected String ctype; //控件类型
	private ExtEvent event;
	
	
	public String fieldParams() {
		StringBuffer buffer =new StringBuffer();
		buffer.append(this.baseParams()).append(",").append(enter);
		buffer.append("readOnly : ").append(this.readOnly);
		buffer.append(",").append(enter);
		buffer.append("editable : ").append(this.editable);
		if(isNotNull(this.inputType)){
			buffer.append(",").append(enter);
			buffer.append("inputType : '").append(this.inputType);
		}
		if(isNotNull(this.name)){
			buffer.append(",").append(enter);
			buffer.append("name : '").append(this.name).append("'");
		}
		if(isNotNull(this.realName)){
			buffer.append(",").append(enter);
			buffer.append("realName : '").append(this.realName).append("'");
		}
		if(isNotNull(this.invalidText)){
			buffer.append(",").append(enter);
			buffer.append("invalidText : '").append(this.invalidText).append("'");
		}
		if(isNotNull(this.value) ){
			if(value instanceof String){
				buffer.append(",").append(enter);
				buffer.append("value : '").append(this.value).append("'");
			}else{
				buffer.append(",").append(enter);
				buffer.append("value : ").append(this.value);
			}
		}
		if(this.event!=null){
			buffer.append(this.event.output());
		}
		return buffer.toString();
	}

	public ExtEvent getEvent() {
		return event;
	}

	public void setEvent(ExtEvent event) {
		this.event = event;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getInvalidText() {
		return invalidText;
	}

	public void setInvalidText(String invalidText) {
		this.invalidText = invalidText;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}


	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
}
