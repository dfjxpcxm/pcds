/**
 * FileName:     CqDsMeta.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-6-12 下午4:51:10 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-6-12       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.query.entity;

import com.shuhao.clean.utils.exttree.ExtTreeNode;
import com.shuhao.clean.utils.exttree.ToExtTreeNode;

/**
 * @Description:   自定义查询数据源 ,tool_cq_ds
 * 
 * @author:         gongzhiyang
 */
public class QueryDs implements ToExtTreeNode{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2781740785205886903L;
	private String ds_id;
	private String p_ds_id;
	private String ds_name;
	private String ds_category;
	private String status;
	private String ds_sql;
	public String getDs_id() {
		return ds_id;
	}
	public void setDs_id(String ds_id) {
		this.ds_id = ds_id;
	}
	public String getP_ds_id() {
		return p_ds_id;
	}
	public void setP_ds_id(String p_ds_id) {
		this.p_ds_id = p_ds_id;
	}
	public String getDs_name() {
		return ds_name;
	}
	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}
	public String getDs_category() {
		return ds_category;
	}
	public void setDs_category(String ds_category) {
		this.ds_category = ds_category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDs_sql() {
		return ds_sql;
	}
	public void setDs_sql(String ds_sql) {
		this.ds_sql = ds_sql;
	}
	/* (non-Javadoc)
	 * @see com.rx.util.tree.TreeNode#getNodeID()
	 */
	public String getNodeID() {
		return this.ds_id;
	}
	/* (non-Javadoc)
	 * @see com.rx.util.tree.TreeNode#getNodeName()
	 */
	public String getNodeName() {
		return this.ds_name;
	}
	/* (non-Javadoc)
	 * @see com.rx.util.tree.TreeNode#getParentNodeID()
	 */
	public String getParentNodeID() {
		return this.p_ds_id;
	}
	/* (non-Javadoc)
	 * @see com.shuhao.clean.utils.exttree.ToExtTreeNode#conver2ExtTreeNode()
	 */
	public ExtTreeNode conver2ExtTreeNode() {
		ExtTreeNode node = new ExtTreeNode();
		node.setId(this.ds_id);
		node.setText(this.ds_name+(this.ds_category.equals("0")?"[目录]": ""));
		node.setLeaf(false);
		node.setIconCls(getIcon(this.ds_category));
		node.setExpanded(true);
		node.setAttr1(this.ds_category);
		return node;
	}
	
	private String getIcon(String type){
		if(type.equals("0")){
			return "folder_table";
		} else{
			return "table";
		}
	}
}
