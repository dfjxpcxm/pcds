package com.shuhao.clean.apps.base.entity;

import java.io.Serializable;

import com.rx.util.tree.TreeNode;

/**
 * 机构实体对象
 * 
 * @author chenxd
 * 
 */
public class DmdBankInterOrg implements TreeNode, Serializable {
	private static final long serialVersionUID = 1L;

	private String bank_org_id;
	private String bank_org_name;
	private String parent_bank_org_id;
	private String bank_org_type_code;
	private String location_code;
	private String bank_org_level;
	private String display_order;

	public String getNodeID() {
		return bank_org_id;
	}

	public String getNodeName() {
		return bank_org_name;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public String getParentNodeID() {
		return parent_bank_org_id;
	}

	public String getBank_org_id() {
		return bank_org_id;
	}

	public String getBank_org_name() {
		return bank_org_name;
	}

	public String getParent_bank_org_id() {
		return parent_bank_org_id;
	}

	public void setBank_org_id(String bank_org_id) {
		this.bank_org_id = bank_org_id;
	}

	public void setBank_org_name(String bank_org_name) {
		this.bank_org_name = bank_org_name;
	}

	public void setParent_bank_org_id(String parent_bank_org_id) {
		this.parent_bank_org_id = parent_bank_org_id;
	}
	
	public String getBank_org_type_code() {
		return bank_org_type_code;
	}

	public void setBank_org_type_code(String bank_org_type_code) {
		this.bank_org_type_code = bank_org_type_code;
	}

	public String getBank_org_level() {
		return bank_org_level;
	}

	public void setBank_org_level(String bank_org_level) {
		this.bank_org_level = bank_org_level;
	}

	public String getDisplay_order() {
		return display_order;
	}

	public void setDisplay_order(String display_order) {
		this.display_order = display_order;
	}

}
