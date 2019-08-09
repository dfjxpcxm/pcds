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
 * @Description:   自定义查询数据源 ,tool_cq_ds_meta
 * 
 * @author:         gongzhiyang
 */
public class QueryDsMeta {
	private String ds_id;
	private String field_id;
	private String field_label;
	private String field_type;
	private String is_query;
	private String link_type_cd; //连接方式
	private String is_hidden;
	private String is_dim;
	private String dim_cd;
	private String default_value;
	private String display_order;
	private String is_order;
	
	public String getDs_id() {
		return ds_id;
	}
	public void setDs_id(String ds_id) {
		this.ds_id = ds_id;
	}
	public String getField_id() {
		return field_id;
	}
	public void setField_id(String field_id) {
		this.field_id = field_id;
	}
	public String getField_label() {
		return field_label;
	}
	public void setField_label(String field_label) {
		this.field_label = field_label;
	}
	public String getField_type() {
		return field_type;
	}
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}
	public String getIs_query() {
		return is_query;
	}
	public void setIs_query(String is_query) {
		this.is_query = is_query;
	}
	public String getIs_hidden() {
		return is_hidden;
	}
	public void setIs_hidden(String is_hidden) {
		this.is_hidden = is_hidden;
	}
	public String getIs_dim() {
		return is_dim;
	}
	public void setIs_dim(String is_dim) {
		this.is_dim = is_dim;
	}
	public String getDim_cd() {
		if(dim_cd==null){
			return "";
		}
		return dim_cd;
	}
	public void setDim_cd(String dim_cd) {
		this.dim_cd = dim_cd;
	}
	public String getDefault_value() {
		if(default_value==null){
			return "";
		}
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
	public String getDisplay_order() {
		return display_order;
	}
	public void setDisplay_order(String display_order) {
		this.display_order = display_order;
	}
	public String getIs_order() {
		return is_order;
	}
	public void setIs_order(String is_order) {
		this.is_order = is_order;
	}
	public String getLink_type_cd() {
		if(link_type_cd==null)
			return "";
		return link_type_cd;
	}
	public void setLink_type_cd(String link_type_cd) {
		this.link_type_cd = link_type_cd;
	}
	
}
