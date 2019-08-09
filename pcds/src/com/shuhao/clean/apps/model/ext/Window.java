package com.shuhao.clean.apps.model.ext;


public class Window extends FormPanel {
	
	protected boolean modal = true;
	protected boolean maximized = false;
	public String output() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("windows = new Ext.Window({").append(enter);
		buffer.append("modal : ").append(this.modal).append(",").append(enter);
		buffer.append("maximized : ").append(this.maximized).append(",").append(enter);
		buffer.append(getBasePanel()).append(enter);
		buffer.append("});").append(enter);
		buffer.append("windows.show();");
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
