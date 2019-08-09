package com.shuhao.clean.apps.meta.entity;

/**
 * 
 * 类描述: Excel元数据实体类
 * @author chenxiangdong 
 * 创建时间：2015-1-13上午10:11:31
 */
public class UppExcel extends UppMetadata {

	private static final long serialVersionUID = 1L;
	
	private String excel_id;
	private String excel_tmpl_name;
	
	public String getMetadata_id() {
		return excel_id;
	}
	
	public String getMetadata_name() {
		return excel_tmpl_name;
	}
	
	public String getMetadata_desc() {
		return this.excel_tmpl_name;
	}
	
	public String getExcel_id() {
		return excel_id;
	}

	public void setExcel_id(String excelId) {
		excel_id = excelId;
	}

	public String getExcel_tmpl_name() {
		return excel_tmpl_name;
	}

	public void setExcel_tmpl_name(String excelTmplName) {
		excel_tmpl_name = excelTmplName;
	}

}
