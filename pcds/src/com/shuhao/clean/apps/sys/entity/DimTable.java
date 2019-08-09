package com.shuhao.clean.apps.sys.entity;

import java.io.Serializable;
/**
 * 维表实体
 * @author gongzy
 *
 */
public class DimTable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8300216449919587049L;
	
	private String dim_code; //维表在维表的维表信息表中的代码
	private String table_name; //英文名字
	private String dim_name; //维表注释
	private String pk_name; //主键名 ('','';'','') 组合字符串
	private String fields_name; //column名 ('','';'','')组合字符串
	public String getDim_code() {
		return dim_code;
	}
	public void setDim_code(String dim_code) {
		this.dim_code = dim_code;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getDim_name() {
		return dim_name;
	}
	public void setDim_name(String dim_name) {
		this.dim_name = dim_name;
	}
	public String getPk_name() {
		return pk_name;
	}
	public void setPk_name(String pk_name) {
		this.pk_name = pk_name;
	}
	public String getFields_name() {
		return fields_name;
	}
	public void setFields_name(String fields_name) {
		this.fields_name = fields_name;
	}
}
