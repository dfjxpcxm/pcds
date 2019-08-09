/**
 * FileName:     SheetConfig.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-3-25 下午6:46:02 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-3-25       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.utils;

import java.util.List;
import java.util.Map;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public class SheetConfig {
	private String sheetName; //sheet名
	private List<String> column; //列
	private Map<String,List<String>> columnData; //列配置
	private List<Map<String, Object>> dataList ; //数据
	private String sql ; //导入模版sql执行insert sql
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public List<String> getColumn() {
		return column;
	}
	public void setColumn(List<String> column) {
		this.column = column;
	}
	public Map<String, List<String>> getColumnData() {
		return columnData;
	}
	public void setColumnData(Map<String, List<String>> columnData) {
		this.columnData = columnData;
	}
	public List<Map<String, Object>> getDataList() {
		return dataList;
	}
	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
}
