package com.shuhao.clean.apps.meta.entity;

/**
 * 
 * 类描述: 元数据[Excel Sheet页]实体类
 * @author chenxiangdong 
 * 创建时间：2015-1-13下午02:56:25
 */
public class UppExcelSheet extends UppMetadata {

	private static final long serialVersionUID = 1L;

	private String sheet_id;
	private String start_row;
	private String sheet_name;
	
	private UppTable table = new UppTable();
	
	public String getMetadata_id() {
		return sheet_id;
	}

	public String getMetadata_name() {
		return sheet_name;
	}
	
	public String getMetadata_desc() {
		return this.sheet_name;
	}

	public String getSheet_id() {
		return sheet_id;
	}

	public void setSheet_id(String sheetId) {
		sheet_id = sheetId;
	}

	public String getStart_row() {
		return start_row;
	}

	public void setStart_row(String startRow) {
		start_row = startRow;
	}

	public String getSheet_name() {
		return sheet_name;
	}

	public void setSheet_name(String sheetName) {
		sheet_name = sheetName;
	}

	public UppTable getTable() {
		return table;
	}

	public void setTable(UppTable table) {
		this.table = table;
	}
}
