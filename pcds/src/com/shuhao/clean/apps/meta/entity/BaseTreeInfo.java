package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;

import com.rx.util.tree.TreeNode;

public class BaseTreeInfo implements TreeNode,Serializable{
	
	private String nodeId;
	private String nodeName;
	private String parentNodeId;

	public String getNodeID() {
		return this.nodeId;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public String getParentNodeID() {
		return this.parentNodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public String getParentNodeId() {
		return parentNodeId;
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
