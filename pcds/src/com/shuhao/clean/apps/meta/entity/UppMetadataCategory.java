package com.shuhao.clean.apps.meta.entity;

import java.util.List;

/**
 * 
 * 类描述: 元数据分类对象
 * @author chenxiangdong 
 * 创建时间：2014-12-30下午04:54:33
 * <br>旧分类:<br>
 *  元数据分类				
	业务元数据	10	                       功能元数据	20
	主题	    1010	         功能页面		2010
	—表	    101010	   —工具条		201010
	——字段	1010101	   ——显示字段	2010101
 */
public class UppMetadataCategory {

	private String md_cate_cd;
	private String md_cate_name;
	private String is_display;
	private String display_order;
	private String icon_path;
	private String md_cate_desc;
	
	private List<UppMetadataRelaDef> relaDefList;
	
	public List<UppMetadataRelaDef> getRelaDefList() {
		return relaDefList;
	}

	public void setRelaDefList(List<UppMetadataRelaDef> relaDefList) {
		this.relaDefList = relaDefList;
	}

	public String getMd_cate_cd() {
		return md_cate_cd;
	}

	public void setMd_cate_cd(String mdCateCd) {
		md_cate_cd = mdCateCd;
	}

	public String getMd_cate_name() {
		return md_cate_name;
	}

	public void setMd_cate_name(String mdCateName) {
		md_cate_name = mdCateName;
	}

	public String getIs_display() {
		return is_display;
	}

	public void setIs_display(String isDisplay) {
		is_display = isDisplay;
	}

	public String getDisplay_order() {
		return display_order;
	}

	public void setDisplay_order(String displayOrder) {
		display_order = displayOrder;
	}

	public String getIcon_path() {
		return icon_path;
	}

	public void setIcon_path(String iconPath) {
		icon_path = iconPath;
	}

	public String getMd_cate_desc() {
		return md_cate_desc;
	}

	public void setMd_cate_desc(String mdCateDesc) {
		md_cate_desc = mdCateDesc;
	}

}
