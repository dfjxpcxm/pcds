package com.shuhao.clean.apps.sys.entity;

import java.util.List;

import com.shuhao.clean.utils.StringUtil;

public class DBTable {

	private String tableName;
	private String tableCode;
	private List<String> pk;
	private List<String> pkValue;
	private List<DBTableColumn> columns;
	
	/**
	 * @return Returns the tableCode.
	 */
	public String getTableCode() {
		return this.tableCode;
	}
	/**
	 * @param tableCode The tableCode to set.
	 */
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	/**
	 * @return Returns the tableName.
	 */
	public String getTableName() {
		return this.tableName;
	}
	/**
	 * @param tableName The tableName to set.
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return Returns the pk.
	 */
	public List<String> getPk() {
		return this.pk;
	}
	/**
	 *@return
	 */
	public String getPkStr()
	{
		return StringUtil.arrayListToStr(pk,";");
	}
	/**
	 * @param pk The pk to set.
	 */
	public void setPk(List<String> pk) {
		this.pk = pk;
	}
	/**
	 * @return Returns the pkValue.
	 */
	public List<String> getPkValue() {
		return this.pkValue;
	}
	/**
	 * @return Returns the pkValueStr.
	 */
	public String getPkValueStr()
	{
		return StringUtil.arrayListToStr(pkValue,";");
	}
	/**
	 * @param pkValue The pkValue to set.
	 */
	public void setPkValue(List<String> pkValue) {
		this.pkValue = pkValue;
	}
	/**
	 * @return Returns the columns.
	 */
	public List<DBTableColumn> getColumns() {
		return this.columns;
	}
	
	public String getColumnsCodeStr()
	{
		String columnsCodeStr="";
		List<DBTableColumn> columns=this.getColumns();
		DBTableColumn column ;
		for(int i=0;i<columns.size();i++)
		{
			column = columns.get(i);
			columnsCodeStr=columnsCodeStr+column.getColumnCode()+",";
		}
		return columnsCodeStr.substring(0,columnsCodeStr.length());
	}	
	/**
	 * @param columns The columns to set.
	 */
	public void setColumns(List<DBTableColumn> columns) {
		this.columns = columns;
	}
	/*
	 * find the specified column according to columnCode
	 */
	public DBTableColumn getColumn(String columnCode)
	{
		List<DBTableColumn> columns = this.getColumns();
		DBTableColumn column;
		for(int i=0;i<columns.size();i++)
		{
			column = columns.get(i);
			if(column.getColumnCode().equals(columnCode)) return column;
		}
		return null;
	}
}