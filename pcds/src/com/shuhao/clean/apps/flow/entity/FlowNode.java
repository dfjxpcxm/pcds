package com.shuhao.clean.apps.flow.entity;

public class FlowNode extends BaseFlow{
	
	private static final long serialVersionUID = 1L;
	private String left;
	private String top;
	private String Vmodel;
	private String Vvalue;
	private boolean Vcheck;
	
	
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getVmodel() {
		return Vmodel;
	}
	public void setVmodel(String vmodel) {
		Vmodel = vmodel;
	}
	public String getVvalue() {
		return Vvalue;
	}
	public void setVvalue(String vvalue) {
		Vvalue = vvalue;
	}
	public boolean isVcheck() {
		return Vcheck;
	}
	public void setVcheck(boolean vcheck) {
		Vcheck = vcheck;
	}
}
