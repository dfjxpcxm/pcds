package com.shuhao.clean.utils.exttree;

/**
 * <p>Title: 简单树节点</p>
 * <p>Description:  <br>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: D&A 数浩科技</p>
 * @since 2014-9-23
 * @author gongzy
 * @version 1.0
 */
public class SimpleTreeNode implements ToExtTreeNode{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3924204513418598074L;
	
	private String nodeId;
	private String nodeName;
	private String parentNodeId;
	private String checked;

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getNodeID() {
		return this.nodeId;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public String getParentNodeID() {
		return this.parentNodeId;
	}
	
	public ExtTreeNode conver2ExtTreeNode() {
		ExtTreeNode node = new ExtTreeNode();
		node.setId(this.nodeId);
		node.setText("["+this.nodeId+"]"+this.nodeName);
		node.setLeaf(false);
		node.setChecked(Boolean.valueOf(this.checked));
		node.setExpanded(true);
		return node;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
}