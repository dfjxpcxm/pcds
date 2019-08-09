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
 * @Description:   TODO 表单验证返回的信息 
 * 
 * @author:         gongzhiyang
 */
public class CallbackField {
	private String msg;
	
	public CallbackField(String msg) {
		this.msg = msg;
	}
	 
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	} 
}
