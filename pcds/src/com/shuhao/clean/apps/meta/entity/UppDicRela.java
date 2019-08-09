package com.shuhao.clean.apps.meta.entity;

/**
 * 
 * 类描述: 元数据映射关系
 * @author chenxiangdong
 * @创建时间：2015-1-15下午03:17:36
 */
public class UppDicRela {

	private String metadata_id;
	private String db_obj_id;
	
	public UppDicRela() {}
	
	public UppDicRela(String metadata_id, String db_obj_id) {
		this.metadata_id = metadata_id;
		this.db_obj_id = db_obj_id;
	}
	
	public String getMetadata_id() {
		return metadata_id;
	}

	public void setMetadata_id(String metadataId) {
		metadata_id = metadataId;
	}

	public String getDb_obj_id() {
		return db_obj_id;
	}

	public void setDb_obj_id(String dbObjId) {
		db_obj_id = dbObjId;
	}

}
