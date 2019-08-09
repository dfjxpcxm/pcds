package com.shuhao.clean.apps.meta.entity;

/**
 * 
 * 类描述: 元数据[Excel列]实体类
 * 
 * @author chenxiangdong 创建时间：2015-1-13下午05:14:48
 */
public class UppExcelColumn extends UppMetadata {

	private static final long serialVersionUID = 1L;

	private String xls_col_id;
	private String xls_col_name;
	private String xls_col_label;
	private String formula_expr;
	private String default_value;
	private String is_must_input; //是否必输

	private UppTableColumn tableColumn = new UppTableColumn();
	
	private DimSource dimSource = new DimSource();

	public String getMetadata_id() {
		return xls_col_id;
	}

	public String getMetadata_name() {
		return xls_col_name;
	}

	public String getMetadata_desc() {
		return this.xls_col_name;
	}

	public String getXls_col_id() {
		return xls_col_id;
	}

	public void setXls_col_id(String xlsColId) {
		xls_col_id = xlsColId;
	}

	public String getXls_col_name() {
		return xls_col_name;
	}

	public void setXls_col_name(String xlsColName) {
		xls_col_name = xlsColName;
	}

	public String getXls_col_label() {
		return xls_col_label;
	}

	public void setXls_col_label(String xlsColLabel) {
		xls_col_label = xlsColLabel;
	}

	public String getFormula_expr() {
		return formula_expr;
	}

	public void setFormula_expr(String formulaExpr) {
		formula_expr = formulaExpr;
	}

	public UppTableColumn getTableColumn() {
		return tableColumn;
	}

	public void setTableColumn(UppTableColumn tableColumn) {
		this.tableColumn = tableColumn;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String defaultValue) {
		default_value = defaultValue;
	}

	public DimSource getDimSource() {
		return dimSource;
	}

	public void setDimSource(DimSource dimSource) {
		this.dimSource = dimSource;
	}

	public String getIs_must_input() {
		return is_must_input;
	}

	public void setIs_must_input(String is_must_input) {
		this.is_must_input = is_must_input;
	}

}
