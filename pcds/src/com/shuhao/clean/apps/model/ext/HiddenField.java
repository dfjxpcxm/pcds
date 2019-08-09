package com.shuhao.clean.apps.model.ext;


public class HiddenField extends BaseField{
	
	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("_"+this.name).append("Hidden  = new Ext.form.Hidden({");
		buffer.append("name : '").append(this.name).append("',").append(enter);
		buffer.append("realName : '").append(this.realName).append("',").append(enter);
		if(isNotNull(this.value)){
			buffer.append("value : '").append(this.value).append("'").append(enter);
		}else{
			buffer.append("value : ''").append(enter);
		}
		buffer.append("})");
		return buffer.toString();
	}
	
}
