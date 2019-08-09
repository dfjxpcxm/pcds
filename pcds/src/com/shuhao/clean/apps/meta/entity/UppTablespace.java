package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;

/**
 * 数据库表空间实体对象
 * 
 * @author bixb
 * 
 */
public class UppTablespace  implements  Serializable{
	
	private static final long serialVersionUID = 1L;
	private String tablespace_id; // 表空间元数据id
	private String database_id; // 数据库元数据id
	private String tablespace_name; // 表空间名称
	private String tablespace_desc; // 表空间描述
	private String display_order; // 显示顺序
	private String is_display; // 是否显示 1 是 0 否

	public String getTablespace_name() {
		return tablespace_name;
	}

	public void setTablespace_name(String tablespace_name) {
		this.tablespace_name = tablespace_name;
	}

	public String getDatabase_id() {
		return database_id;
	}

	public void setDatabase_id(String database_id) {
		this.database_id = database_id;
	}

	public String getTablespace_desc() {
		return tablespace_desc;
	}

	public void setTablespace_desc(String tablespace_desc) {
		this.tablespace_desc = tablespace_desc;
	}

	public String getTablespace_id() {
		return tablespace_id;
	}
	
	public String getDisplay_order() {
		return display_order;
	}

	public void setDisplay_order(String display_order) {
		this.display_order = display_order;
	}

	public void setTablespace_id(String tablespace_id) {
		this.tablespace_id = tablespace_id;
	}

	public String getIs_display() {
		return is_display;
	}

	public void setIs_display(String is_display) {
		this.is_display = is_display;
	}
	
	/**
	 * 转换成顶级元数据对象
	 */
	/*public UppMetadata getUppMetadata(){
		this.setMetadata_id(this.tablespace_id);
		this.setMetadata_name(this.tablespace_name);
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

}
