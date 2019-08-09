package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;


/**
 * 数据库授权用户实体对象
 * @author bixb
 * 
 */
public class UppDatabaseUser implements  Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String db_user_id; // 用户元数据ID
	private String database_id; // 数据库的id
	private String owner_name; // 用户名
	private String owner_desc; // 用户描述
	private String owner_password; // 登录数据库的密码
	private String default_tablespace_name; // 默认表空间名称
	private String temp_tablespace_name; // 临时表空间名称
	private String display_order; // 显示顺序
	private String is_display; // 是否显示 1 是 0 否

	/**
	 * 转换成顶级元数据对象
	 */
	/*public UppMetadata getUppMetadata(){
		this.setMetadata_id(this.db_user_id);
		this.setMetadata_name(this.owner_name);
		this.setParent_metadata_id("database");
		*//**
		 * 		业务元数据	10					功能元数据	20
				主题			1010				功能页面		2010
				表			101010				工具条		201010
				字段			1010101				显示字段		2010101

		 *//*
		this.setMetadata_cate_code("1010");//该实体类为主题实体类    代码为1010
		this.setStatus_code("02");//02正常???
		this.setDisplay_order(this.display_order);
		this.setCreate_user_id(this.getCreate_user_id());
		this.setUpdate_user_id(this.getUpdate_user_id());
		return super.getUppMetadata();
	}*/
	
	public String getDatabase_id() {
		return database_id;
	}

	public void setDatabase_id(String database_id) {
		this.database_id = database_id;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getOwner_desc() {
		return owner_desc;
	}

	public String getDb_user_id() {
		return db_user_id;
	}

	public void setDb_user_id(String db_user_id) {
		this.db_user_id = db_user_id;
	}

	public void setOwner_desc(String owner_desc) {
		this.owner_desc = owner_desc;
	}


	public String getDisplay_order() {
		return display_order;
	}

	public void setDisplay_order(String display_order) {
		this.display_order = display_order;
	}

	public String getOwner_password() {
		return owner_password;
	}

	public void setOwner_password(String owner_password) {
		this.owner_password = owner_password;
	}

	public String getDefault_tablespace_name() {
		return default_tablespace_name;
	}

	public void setDefault_tablespace_name(String default_tablespace_name) {
		this.default_tablespace_name = default_tablespace_name;
	}

	public String getTemp_tablespace_name() {
		return temp_tablespace_name;
	}

	public void setTemp_tablespace_name(String temp_tablespace_name) {
		this.temp_tablespace_name = temp_tablespace_name;
	}

	public String getIs_display() {
		return is_display;
	}

	public void setIs_display(String is_display) {
		this.is_display = is_display;
	}
}
