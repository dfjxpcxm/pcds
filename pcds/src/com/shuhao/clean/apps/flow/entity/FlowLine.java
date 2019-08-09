package com.shuhao.clean.apps.flow.entity;

public class FlowLine extends BaseFlow{
	private static final long serialVersionUID = 1L;
	private String from;
	private String to;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
}
