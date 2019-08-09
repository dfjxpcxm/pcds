package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;

import com.rx.util.tree.TreeNode;

public class UppTheme extends UppMetadata implements TreeNode, Serializable {

	private static final long serialVersionUID = 1L;
	private String theme_id;
	private String theme_name;
	private String theme_desc;
	private String status_cd;
	
	public String getMetadata_id() {
		return theme_id;
	}
	
	public String getMetadata_name() {
		return theme_name;
	}
	
	public String getMetadata_desc() {
		return theme_name;
	}
	
	public String getTheme_id() {
		return theme_id;
	}

	public void setTheme_id(String themeId) {
		theme_id = themeId;
	}

	public String getTheme_name() {
		return theme_name;
	}

	public void setTheme_name(String themeName) {
		theme_name = themeName;
	}

	public String getTheme_desc() {
		return theme_desc;
	}

	public void setTheme_desc(String themeDesc) {
		theme_desc = themeDesc;
	}

	public String getStatus_cd() {
		return status_cd;
	}

	public void setStatus_cd(String statusCd) {
		status_cd = statusCd;
	}

}
