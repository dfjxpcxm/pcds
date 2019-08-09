package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;

/**
 * 页面按钮定义
 * @author bixb
 * 
 */
public class UppPageButton extends UppMetadata implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String button_id;
	private String button_name;
	private String md_cate_cd;
	private String button_func_cd;
	private String icon_path;
	private String rela_metadata_id;
	private String is_customer_sql;
	private String dml_sql;
	
	public String getMetadata_desc() {
		return button_name;
	}
	
	public String getMetadata_id() {
		return button_id;
	}
	
	public String getMetadata_name() {
		return button_name;
	}

	public String getButton_id() {
		return button_id;
	}

	public void setButton_id(String button_id) {
		this.button_id = button_id;
	}

	public String getButton_name() {
		return button_name;
	}

	public void setButton_name(String button_name) {
		this.button_name = button_name;
	}

	public String getMd_cate_cd() {
		return md_cate_cd;
	}

	public void setMd_cate_cd(String md_cate_cd) {
		this.md_cate_cd = md_cate_cd;
	}

	public String getButton_func_cd() {
		return button_func_cd;
	}

	public void setButton_func_cd(String button_func_cd) {
		this.button_func_cd = button_func_cd;
	}

	public String getIcon_path() {
		return icon_path;
	}

	public void setIcon_path(String icon_path) {
		this.icon_path = icon_path;
	}

	public String getRela_metadata_id() {
		return rela_metadata_id;
	}

	public void setRela_metadata_id(String rela_metadata_id) {
		this.rela_metadata_id = rela_metadata_id;
	}

	public String getIs_customer_sql() {
		return is_customer_sql;
	}

	public void setIs_customer_sql(String is_customer_sql) {
		this.is_customer_sql = is_customer_sql;
	}

	public String getDml_sql() {
		return dml_sql;
	}

	public void setDml_sql(String dml_sql) {
		this.dml_sql = dml_sql;
	}

	 
}
