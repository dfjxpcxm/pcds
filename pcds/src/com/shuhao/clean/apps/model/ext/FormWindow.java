package com.shuhao.clean.apps.model.ext;


public class FormWindow extends FormPanel {
	
	protected boolean modal = true;
	protected boolean maximized = true;
	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("var Window"+this.id+" = Ext.extend(Ext.Window, {").append(enter);
		buffer.append("title : '"+this.title+"',").append(enter);
		buffer.append("buttonAlign : 'center',").append(enter);
		buffer.append("maximized : "+this.maximized+",").append(enter);
		buffer.append("	id : 'Win_"+this.id+"',").append(enter);
		buffer.append("	initComponent : function() {").append(enter);
		buffer.append("		Ext.applyIf(this, {").append(enter);
		buffer.append("			modal : true,").append(enter);
		buffer.append("			split : false,").append(enter);
		buffer.append("			layout : 'fit',").append(enter);
		buffer.append("			listeners : {").append(enter);
		buffer.append("				close : function() {").append(enter);
		buffer.append("				    Ext.getCmp('Win_"+this.id+"').destroy();").append(enter);
		buffer.append("				}").append(enter);
		/*buffer.append("				,show : function() {").append(enter);
		//dataForm赋值
		buffer.append("				    dataForm = Ext.getCmp('Win_"+this.id+"').items.items[0];").append(enter);
		buffer.append("				}").append(enter);*/
		buffer.append("			},").append(enter);
		buffer.append("			bodyStyle : 'padding: 10px',").append(enter);
		buffer.append("			maximized : ").append(this.maximized);
		if(this.items != null){
			buffer.append(",").append(enter);
			buffer.append("items : ").append(this.items.output());
		}
		if(this.buttons != null && this.buttons.size() > 0){
			buffer.append(",").append(enter);
			buffer.append("buttons : [ ");
			for (int i = 0; i < buttons.size(); i++) {
				Button button = buttons.get(i);
				if(i == buttons.size() -1){
					buffer.append(button.output()).append(enter);
				}else{
					buffer.append(button.output()).append(",").append(enter);
				}
			}
			buffer.append("]").append(enter);
		}
		buffer.append("});").append(enter);
		buffer.append("Window"+this.id+".superclass.initComponent.call(this);").append(this.enter);
		//dataForm赋值,龚知杨修改
		buffer.append("dataForm = _"+this.id+"Form;").append(this.enter);
		buffer.append("}").append(this.enter);
		buffer.append("	});").append(this.enter);
		return buffer.toString();
	}

	public boolean isModal() {
		return modal;
	}

	public void setModal(boolean modal) {
		this.modal = modal;
	}

	public boolean isMaximized() {
		return maximized;
	}

	public void setMaximized(boolean maximized) {
		this.maximized = maximized;
	}
	
	
	
}
