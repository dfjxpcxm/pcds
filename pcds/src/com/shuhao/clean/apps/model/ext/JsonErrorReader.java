package com.shuhao.clean.apps.model.ext;

import com.shuhao.clean.apps.model.ext.base.BaseExt;

public class JsonErrorReader extends BaseExt {

	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.id).append("Reader = new Ext.data.JsonReader({").append(enter);
		buffer.append("root : 'errors', ").append(enter);
		buffer.append("fields : [{name : 'msg'},{name : 'id'},{name:'showgrid'}],").append(enter);
		buffer.append("success : 'success',").append(enter);
		buffer.append("idProperty : '").append("id").append("'").append(enter);
		buffer.append("}").append(enter);
		buffer.append(")");
		return buffer.toString();
	}
}
