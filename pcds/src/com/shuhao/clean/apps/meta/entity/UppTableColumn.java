/**
 * FileName:     UppTableColumns.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-28 上午10:11:25 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-28       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.meta.entity;

/**
 * @Description: TODO
 * 
 * @author: gongzhiyang
 */
public class UppTableColumn extends UppMetadata {

	private static final long serialVersionUID = 2611636708048207983L;

	private String column_id;
	private String table_id;
	private String column_name;
	private String column_desc;
	private String data_type_cd;
	private String data_length;
	private String data_scale;
	private String is_pk;
	private String is_nullable;
	private String obj_status_cd;
	
	public String getMetadata_id() {
		return column_id;
	}
	
	public String getMetadata_name() {
		return column_name;
	}
	
	public String getMetadata_desc() {
		return this.column_name + "[" + column_desc + "]";
	}
	
	public String getColumn_id() {
		return column_id;
	}

	public void setColumn_id(String columnId) {
		column_id = columnId;
	}

	public String getTable_id() {
		return table_id;
	}

	public void setTable_id(String tableId) {
		table_id = tableId;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String columnName) {
		column_name = columnName;
	}

	public String getColumn_desc() {
		/*if(column_desc!=null){
			return GlobalUtil.trimToSpace(column_desc);
		}*/
		return column_desc;
	}

	public void setColumn_desc(String columnDesc) {
		column_desc = columnDesc;
	}

	public String getData_type_cd() {
		return data_type_cd;
	}

	public void setData_type_cd(String dataTypeCd) {
		data_type_cd = dataTypeCd;
	}

	public String getData_length() {
		return data_length;
	}

	public void setData_length(String dataLength) {
		data_length = dataLength;
	}

	public String getData_scale() {
		return data_scale;
	}

	public void setData_scale(String dataScale) {
		data_scale = dataScale;
	}

	public String getIs_pk() {
		return is_pk;
	}

	public void setIs_pk(String isPk) {
		is_pk = isPk;
	}

	public String getIs_nullable() {
		return is_nullable;
	}

	public void setIs_nullable(String isNullable) {
		is_nullable = isNullable;
	}

	public String getObj_status_cd() {
		return obj_status_cd;
	}

	public void setObj_status_cd(String objStatusCd) {
		obj_status_cd = objStatusCd;
	}

}
