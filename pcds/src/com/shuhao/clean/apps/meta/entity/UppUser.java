package com.shuhao.clean.apps.meta.entity;

/**
 * 
 * 类描述:数据库用户对象
 * 
 * @author chenxiangdong 创建时间：2015-1-5上午09:28:28
 */
public class UppUser extends UppMetadata {

	private static final long serialVersionUID = 1L;
	
	private String user_id;
	private String database_id;
	private String user_name;
	private String user_password;
	private String user_desc;
	
	public String getMetadata_id() {
		return user_id;
	}
	
	public String getMetadata_name() {
		return user_name;
	}
	
	public String getMetadata_desc() {
		return user_name;
	}
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public String getDatabase_id() {
		return database_id;
	}

	public void setDatabase_id(String databaseId) {
		database_id = databaseId;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String userName) {
		user_name = userName;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String userPassword) {
		user_password = userPassword;
	}

	public String getUser_desc() {
		return user_desc;
	}

	public void setUser_desc(String userDesc) {
		user_desc = userDesc;
	}

}
