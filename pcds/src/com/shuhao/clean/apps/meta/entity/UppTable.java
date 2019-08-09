package com.shuhao.clean.apps.meta.entity;

/**
 * 
 * 类描述: 数据库表对象
 * 
 * @author chenxiangdong 创建时间：2015-1-5上午09:32:40
 */
public class UppTable extends UppMetadata {

	private static final long serialVersionUID = -2449738912909630883L;

	private String table_id;
	private String table_name;
	private String table_desc;
	
	public String getMetadata_id() {
		return table_id;
	}
	
	public String getMetadata_name() {
		return table_name;
	}
	
	public String getMetadata_desc() {
		return this.table_name + "(" + table_desc + ")";
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

	public String getTable_desc() {
		return table_desc;
	}

	public void setTable_desc(String tableDesc) {
		table_desc = tableDesc;
	}

}
