/**
 * FileName:     UppTmplate.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-2-12 上午10:31:49 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-2-12       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.meta.entity;


import com.shuhao.clean.utils.exttree.ExtTreeNode;
import com.shuhao.clean.utils.exttree.ToExtTreeNode;

/**
 * 补录模版
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public class UppTmplate implements ToExtTreeNode{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5056830331161212146L;
	
	private String tmpl_id;
	private String prt_tmpl_id;
	private String template_name;
	private String template_desc;
	private String template_type_cd;
	private String enter_type_cd;
	private String create_user_id;
	private String create_time;
	private String update_user_id;
	private String update_time;
	private String status_cd;
	private String display_order;
	private String rela_metadata_names;
	public String getTmpl_id() {
		return tmpl_id;
	}
	public void setTmpl_id(String tmpl_id) {
		this.tmpl_id = tmpl_id;
	}
	public String getPrt_tmpl_id() {
		return prt_tmpl_id;
	}
	public void setPrt_tmpl_id(String prt_tmpl_id) {
		this.prt_tmpl_id = prt_tmpl_id;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getTemplate_desc() {
		return template_desc;
	}
	public void setTemplate_desc(String template_desc) {
		this.template_desc = template_desc;
	}
	public String getTemplate_type_cd() {
		return template_type_cd;
	}
	public void setTemplate_type_cd(String template_type_cd) {
		this.template_type_cd = template_type_cd;
	}
	public String getEnter_type_cd() {
		return enter_type_cd;
	}
	public void setEnter_type_cd(String enter_type_cd) {
		this.enter_type_cd = enter_type_cd;
	}
	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	public String getDisplay_order() {
		return display_order;
	}
	public void setDisplay_order(String display_order) {
		this.display_order = display_order;
	}
	public String getRela_metadata_names() {
		return rela_metadata_names;
	}
	public void setRela_metadata_names(String rela_metadata_names) {
		this.rela_metadata_names = rela_metadata_names;
	}
	
	/* (non-Javadoc)
	 * @see com.rx.util.tree.TreeNode#getNodeID()
	 */
	public String getNodeID() {
		// TODO Auto-generated method stub
		return this.tmpl_id;
	}
	
	/* (non-Javadoc)
	 * @see com.rx.util.tree.TreeNode#getNodeName()
	 */
	public String getNodeName() {
		// TODO Auto-generated method stub
		return this.template_name;
	}
	
	/* (non-Javadoc)
	 * @see com.rx.util.tree.TreeNode#getParentNodeID()
	 */
	public String getParentNodeID() {
		// TODO Auto-generated method stub
		return this.prt_tmpl_id;
	}
	/* (non-Javadoc)
	 * @see com.shuhao.clean.utils.exttree.ToExtTreeNode#conver2ExtTreeNode()
	 */
	public ExtTreeNode conver2ExtTreeNode() {
		ExtTreeNode node = new ExtTreeNode();
		node.setId(this.tmpl_id);
		String hasPublic = this.status_cd.equals("02") ? "" : "*";
		if(this.enter_type_cd ==null || "".equals(this.enter_type_cd)) {
			this.enter_type_cd = "02";
		}
		node.setText(hasPublic + this.template_name + (this.template_type_cd.equals("01")?"[目录]":(this.enter_type_cd.equals("01")?"[全部补录]":"[部分补录]")));
		node.setLeaf(false);
		node.setIconCls(getIcon(this.template_type_cd));
		node.setExpanded(true);
		node.setAttr1(this.template_type_cd);
		return node;
	}
	
	private String getIcon(String type){
		if(type.equals("01")){
			return "folder_table";
		}else if(type.equals("02")){
			return "table";
		}else{
			return "stack";
		}
	}
}
