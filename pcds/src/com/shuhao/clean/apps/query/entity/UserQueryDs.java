/**
 * FileName:     CqDsMeta.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-6-12 下午4:51:10 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-6-12       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.query.entity;

/**
 * @Description:   自定义查询数据源 ,tool_cq_ds
 * 
 * @author:         gongzhiyang
 */
public class UserQueryDs extends QueryDs{
	private String user_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
