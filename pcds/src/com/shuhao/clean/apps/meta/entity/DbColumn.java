package com.shuhao.clean.apps.meta.entity;

/**
 * 
 * 类描述: 物理表列
 * @author chenxiangdong
 * @创建时间：2015-1-19下午03:18:23
 */
public class DbColumn {

	private String database_id;
	private String owner_id;
	private String table_id;
	private String table_name;
	private String column_name;
	private String column_desc;
	private String data_type_cd;
	private String data_length;
	private String data_scale;
	private String is_pk;
	private String is_nullable;

	public String getDatabase_id() {
		return database_id;
	}

	public void setDatabase_id(String databaseId) {
		database_id = databaseId;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String ownerId) {
		owner_id = ownerId;
	}

	public String getTable_id() {
		return table_id;
	}

	public void setTable_id(String tableId) {
		table_id = tableId;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String tableName) {
		table_name = tableName;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String columnName) {
		column_name = columnName;
	}

	public String getColumn_desc() {
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

}
