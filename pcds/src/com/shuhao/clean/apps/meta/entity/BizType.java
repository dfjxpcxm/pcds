package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;

/**
 * 
 * @Description:  业务分类字段
 * <br>
 * @author:        
 */
public class BizType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2617244191667098781L;

	private String col_biz_type_cd; //分类ID
	private String old_col_biz_type_cd;
	private String col_biz_type_desc;//分类描述
	private String upp_rela_id; //UPPrelaid
	private String old_upp_rela_id; //UPPrelaid
	private String is_public;//是否是public
	private String display_order;//显示顺序
	
	public String getOld_upp_rela_id() {
		return old_upp_rela_id;
	}
	public void setOld_upp_rela_id(String old_upp_rela_id) {
		this.old_upp_rela_id = old_upp_rela_id;
	}
	public String getCol_biz_type_cd() {
		return col_biz_type_cd;
	}
	public void setCol_biz_type_cd(String col_biz_type_cd) {
		this.col_biz_type_cd = col_biz_type_cd;
	}
	public String getCol_biz_type_desc() {
		return col_biz_type_desc;
	}
	public void setCol_biz_type_desc(String col_biz_type_desc) {
		this.col_biz_type_desc = col_biz_type_desc;
	}
	public String getUpp_rela_id() {
		return upp_rela_id;
	}
	public void setUpp_rela_id(String upp_rela_id) {
		this.upp_rela_id = upp_rela_id;
	}
	public String getIs_public() {
		return is_public;
	}
	public void setIs_public(String is_public) {
		this.is_public = is_public;
	}
	public String getDisplay_order() {
		return display_order;
	}
	public void setDisplay_order(String display_order) {
		this.display_order = display_order;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOld_col_biz_type_cd() {
		return old_col_biz_type_cd;
	}
	public void setOld_col_biz_type_cd(String old_col_biz_type_cd) {
		this.old_col_biz_type_cd = old_col_biz_type_cd;
	}
	
}
