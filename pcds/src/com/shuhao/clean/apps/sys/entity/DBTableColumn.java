package com.shuhao.clean.apps.sys.entity;

public class DBTableColumn {
	private String columnCode;
	private String columnName;
	private String columnValue;
	private String columnType;
	private String columnLength;
	private String columnDecimalNumber;
	private boolean columnIsNullable;
	private boolean isPK;
	
	/**
	 * @return Returns the columnCode.
	 */
	public String getColumnCode() {
		return this.columnCode;
	}
	/**
	 * @param columnCode The columnCode to set.
	 */
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	/**
	 * @return Returns the columnName.
	 */
	public String getColumnName() {
		return this.columnName;
	}
	/**
	 * @param columnName The columnName to set.
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return Returns the columnType.
	 */
	public String getColumnType() {
		return this.columnType;
	}
	/**
	 * @param columnType The columnType to set.
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	/**
	 * @return Returns the columnValue.
	 */
	public String getColumnValue() {
		return this.columnValue;
	}
	/**
	 * @param columnValue The columnValue to set.
	 */
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	/**
	 * @return Returns the columnLength.
	 */
	public String getColumnLength() {
		return this.columnLength;
	}
	/**
	 * @param columnLength The columnLength to set.
	 */
	public void setColumnLength(String columnLength) {
		this.columnLength = columnLength;
	}
	/**
	 * @return Returns the columnDecimalNumber.
	 */
	public String getColumnDecimalNumber() {
		return this.columnDecimalNumber;
	}
	/**
	 * @param columnDecimalNumber The columnDecimalNumber to set.
	 */
	public void setColumnDecimalNumber(String columnDecimalNumber) {
		this.columnDecimalNumber = columnDecimalNumber;
	}
	/**
	 * @return Returns the columnIsNullable.
	 */
	public boolean getColumnIsNullable() {
		return this.columnIsNullable;
	}
	/**
	 * @param columnIsNullable The columnIsNullable to set.
	 */
	public void setColumnIsNullable(boolean columnIsNullable) {
		this.columnIsNullable = columnIsNullable;
	}
	/**
	 * @return Returns the isPK.
	 */
	public boolean getIsPK() {
		return this.isPK;
	}
	/**
	 * @param isPK The isPK to set.
	 */
	public void setIsPK(boolean isPK) {
		this.isPK = isPK;
	}
	
}
