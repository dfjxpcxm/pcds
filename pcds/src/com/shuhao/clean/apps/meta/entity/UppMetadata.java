package com.shuhao.clean.apps.meta.entity;

import com.rx.util.tree.TreeNode;

/**
 * 元数据实体类
 * 
 */
public class UppMetadata implements TreeNode {

	private static final long serialVersionUID = 1L;

	private String metadata_id;
	private String prt_metadata_id;
	private String metadata_name;
	private String md_cate_cd;
	private String status_cd;
	private String is_display;
	private String display_order;
	private String create_user_id;
	private String create_time;
	private String update_user_id;
	private String update_time;
	private String metadata_desc;
	private String is_leaf; //是否是叶子节点 N是，Y否
	
	/**
	 * 获取控件完整ID<br>
	 * getMetadata_name转换为小写，与前端显示匹配
	 * @return
	 */
	public String getFullId(){
		return metadata_id+"_"+(getMetadata_name()!=null ? getMetadata_name().toLowerCase():getMetadata_name());
	}

	private UppMetadataCategory category;

	public String getMetadata_id() {
		return metadata_id;
	}

	public void setMetadata_id(String metadataId) {
		metadata_id = metadataId;
	}

	public String getPrt_metadata_id() {
		return prt_metadata_id;
	}

	public void setPrt_metadata_id(String prtMetadataId) {
		prt_metadata_id = prtMetadataId;
	}

	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadataName) {
		metadata_name = metadataName;
	}

	public String getMd_cate_cd() {
		return md_cate_cd;
	}

	public void setMd_cate_cd(String mdCateCd) {
		md_cate_cd = mdCateCd;
	}

	public String getStatus_cd() {
		return status_cd;
	}

	public void setStatus_cd(String statusCd) {
		status_cd = statusCd;
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

	public String getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(String createUserId) {
		create_user_id = createUserId;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String createTime) {
		create_time = createTime;
	}

	public String getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(String updateUserId) {
		update_user_id = updateUserId;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String updateTime) {
		update_time = updateTime;
	}

	public UppMetadataCategory getCategory() {
		return category;
	}

	public void setCategory(UppMetadataCategory category) {
		this.category = category;
	}

	public String getNodeID() {
		return this.metadata_id;
	}

	public String getNodeName() {
		return this.prt_metadata_id;
	}

	public String getParentNodeID() {
		return this.metadata_name;
	}

	public String getMetadata_desc() {
		return metadata_desc;
	}

	public void setMetadata_desc(String metadataDesc) {
		metadata_desc = metadataDesc;
	}

	public String getIs_leaf() {
		return is_leaf==null ? "N" : is_leaf;
	}

	public void setIs_leaf(String is_leaf) {
		this.is_leaf = is_leaf;
	}
	
	

}
