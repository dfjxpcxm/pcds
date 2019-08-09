package com.shuhao.clean.apps.base.entity;

import java.io.Serializable;

import com.rx.util.tree.TreeNode;

/**
 * 产品实体对象
 * <br>
 * dmd_product 
 * @author gongzy
 * 
 */
public class DmdProduct implements TreeNode, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4610240619630634231L;
	
	private String product_id;
	private String product_name;
	private String parent_product_id;
	private String asset_type_code;
	private String bal_dir;

	public String getNodeID() {
		return product_id;
	}

	public String getNodeName() {
		return product_name;
	}
 
	public String getParentNodeID() {
		return parent_product_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getParent_product_id() {
		return parent_product_id;
	}

	public void setParent_product_id(String parent_product_id) {
		this.parent_product_id = parent_product_id;
	}

	public String getAsset_type_code() {
		return asset_type_code;
	}

	public void setAsset_type_code(String asset_type_code) {
		this.asset_type_code = asset_type_code;
	}

	public String getBal_dir() {
		return bal_dir;
	}

	public void setBal_dir(String bal_dir) {
		this.bal_dir = bal_dir;
	}
}
