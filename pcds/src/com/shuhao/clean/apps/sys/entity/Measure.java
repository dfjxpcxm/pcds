package com.shuhao.clean.apps.sys.entity;

import java.io.Serializable;

import com.rx.util.tree.TreeNode;

public class Measure implements TreeNode, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String measure_id;
	private String measure_name;
	private String parent_measure_id;
	private String begin_date;
	private String end_date;
	private String measure_tree_code;
	private String measure_level;
	private String measure_property;

	public String getMeasure_id() {
		return measure_id;
	}

	public void setMeasure_id(String measure_id) {
		this.measure_id = measure_id;
	}

	public String getMeasure_name() {
		return measure_name;
	}

	public void setMeasure_name(String measure_name) {
		this.measure_name = measure_name;
	}

	public String getParent_measure_id() {
		return parent_measure_id;
	}

	public void setParent_measure_id(String parent_measure_id) {
		this.parent_measure_id = parent_measure_id;
	}

	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getMeasure_tree_code() {
		return measure_tree_code;
	}

	public void setMeasure_tree_code(String measure_tree_code) {
		this.measure_tree_code = measure_tree_code;
	}

	public String getMeasure_level() {
		return measure_level;
	}

	public void setMeasure_level(String measure_level) {
		this.measure_level = measure_level;
	}

	public String getMeasure_property() {
		return measure_property;
	}

	public void setMeasure_property(String measure_property) {
		this.measure_property = measure_property;
	}

	public String getNodeID() {
		return measure_id;
	}

	public String getNodeName() {
		return measure_name;
	}

	public String getParentNodeID() {
		return parent_measure_id;
	}

}
