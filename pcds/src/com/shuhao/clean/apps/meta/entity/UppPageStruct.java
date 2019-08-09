package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;

/**
 * 页面结构定义
 * @author bixb
 * 
 */
public class UppPageStruct extends UppMetadata implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String page_struct_id;
	private String page_struct_name;
	private String page_struct_desc;
	private String md_cate_cd;
	
	private String rela_table_id;
	private String rela_table_name;
	
	public String getMetadata_desc(){
		return page_struct_desc;
	}
	public String getMetadata_id() {
		return page_struct_id;
	}
	
	public String getMetadata_name() {
		return page_struct_name;
	}

	public String getPage_struct_id() {
		return page_struct_id;
	}

	public void setPage_struct_id(String page_struct_id) {
		this.page_struct_id = page_struct_id;
	}

	public String getPage_struct_name() {
		return page_struct_name;
	}

	public void setPage_struct_name(String page_struct_name) {
		this.page_struct_name = page_struct_name;
	}

	public String getPage_struct_desc() {
		return page_struct_desc;
	}

	public void setPage_struct_desc(String page_struct_desc) {
		this.page_struct_desc = page_struct_desc;
	}

	public String getMd_cate_cd() {
		return md_cate_cd;
	}

	public void setMd_cate_cd(String md_cate_cd) {
		this.md_cate_cd = md_cate_cd;
	}
	
	
	public String getRela_table_id() {
		return rela_table_id;
	}

	public void setRela_table_id(String rela_table_id) {
		this.rela_table_id = rela_table_id;
	}

	public String getRela_table_name() {
		return rela_table_name;
	}

	public void setRela_table_name(String rela_table_name) {
		this.rela_table_name = rela_table_name;
	}
	 
	 
}
