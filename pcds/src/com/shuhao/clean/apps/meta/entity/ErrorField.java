/**
 * FileName:     ErrorField.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-12-5 下午2:08:55 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-12-5       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.meta.entity;

/**
 * @Description:   TODO 表单验证返回的错误信息,id与表单的name对应
 * 
 * @author:         gongzhiyang
 */
public class ErrorField {
	private String id;
	private String msg;
	private boolean showgrid = false;
	
	public ErrorField(String id, String msg) {
		this.id = id;
		this.msg = msg;
	}
	
	public ErrorField(String id, String msg,boolean showgrid) {
		this.id = id;
		this.msg = msg;
		this.showgrid = showgrid;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isShowgrid() {
		return showgrid;
	}
	public void setShowgrid(boolean showgrid) {
		this.showgrid = showgrid;
	}
}
