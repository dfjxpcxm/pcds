package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;

/**
 * 页面字段定义
 * @author bixb
 * 
 */
public class UppPageField extends UppMetadata implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String field_id; 
	private String field_name; 
	private String component_label; 
	private String component_type_id; 
	private String dim_cd; 
	private String default_value; 
	private String col_biz_type_cd; 
	private String is_hidden; 
	private String display_order; 
	private String is_editable; 
	private String is_must_input; 
	private String is_pk; 
	private String max_value; 
	private String min_value; 
	private String max_length; 
	private String is_query_cond;
	
	//关联表字段
	private String rela_table_column;
	
	public String getMetadata_desc() {
		if(super.getMetadata_desc() != null){
			return super.getMetadata_desc();
		}else{
			return component_label+"["+field_name+"]";
		}
	}
	
	public String getMetadata_id() {
		return field_id;
	}
	
	public String getMetadata_name() {
		return field_name;
	}

	public String getField_id() {
		return field_id;
	}

	public void setField_id(String field_id) {
		this.field_id = field_id;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getComponent_label() {
		return component_label;
	}

	public void setComponent_label(String component_label) {
		this.component_label = component_label;
	}

	public String getComponent_type_id() {
		return component_type_id;
	}

	public void setComponent_type_id(String component_type_id) {
		this.component_type_id = component_type_id;
	}

	public String getDim_cd() {
		return dim_cd;
	}

	public void setDim_cd(String dim_cd) {
		this.dim_cd = dim_cd;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getCol_biz_type_cd() {
		return col_biz_type_cd;
	}

	public void setCol_biz_type_cd(String col_biz_type_cd) {
		this.col_biz_type_cd = col_biz_type_cd;
	}

	public String getIs_hidden() {
		return is_hidden;
	}

	public void setIs_hidden(String is_hidden) {
		this.is_hidden = is_hidden;
	}

	public String getDisplay_order() {
		return display_order;
	}

	public void setDisplay_order(String display_order) {
		this.display_order = display_order;
	}

	public String getIs_editable() {
		return is_editable;
	}

	public void setIs_editable(String is_editable) {
		this.is_editable = is_editable;
	}

	public String getIs_must_input() {
		return is_must_input;
	}

	public void setIs_must_input(String is_must_input) {
		this.is_must_input = is_must_input;
	}

	public String getIs_pk() {
		return is_pk;
	}

	public void setIs_pk(String is_pk) {
		this.is_pk = is_pk;
	}

	public String getMax_value() {
		return max_value;
	}

	public void setMax_value(String max_value) {
		this.max_value = max_value;
	}

	public String getMin_value() {
		return min_value;
	}

	public void setMin_value(String min_value) {
		this.min_value = min_value;
	}

	public String getMax_length() {
		return max_length;
	}

	public void setMax_length(String max_length) {
		this.max_length = max_length;
	}

	public String getIs_query_cond() {
		return is_query_cond;
	}

	public void setIs_query_cond(String is_query_cond) {
		this.is_query_cond = is_query_cond;
	}

	public String getRela_table_column() {
		return rela_table_column;
	}

	public void setRela_table_column(String rela_table_column) {
		this.rela_table_column = rela_table_column;
	}	 
}
