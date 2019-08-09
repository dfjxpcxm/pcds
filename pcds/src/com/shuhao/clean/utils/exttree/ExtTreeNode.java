package com.shuhao.clean.utils.exttree;

import java.util.ArrayList;
import java.util.List;

public class ExtTreeNode {
	private String id;
	private String text;
	private String iconCls;
	private Boolean leaf;
	private Boolean checked;
	private Boolean expanded;
	
	//参数预留
	private String attr1;
	private String attr2;
	
	private List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public List<ExtTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ExtTreeNode> children) {
		this.children = children;
	}
}
