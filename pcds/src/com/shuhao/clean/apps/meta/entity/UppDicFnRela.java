package com.shuhao.clean.apps.meta.entity;

import java.io.Serializable;

/**
 * 表业务元数据与功能元数据之间的关系
 * @author wangch
 *
 */
public class UppDicFnRela implements  Serializable{
	private static final long serialVersionUID = -8156046673446908241L;
	private String metadata_id;
	private String db_obj_id;
	
	public UppDicFnRela(){
		
	}
	
	public UppDicFnRela(String db_obj_id,String metadata_id){
		this.metadata_id = metadata_id;
		this.db_obj_id = db_obj_id;
	}

	public String getMetadata_id() {
		return metadata_id;
	}

	public void setMetadata_id(String metadata_id) {
		this.metadata_id = metadata_id;
	}

	public String getDb_obj_id() {
		return db_obj_id;
	}

	public void setDb_obj_id(String db_obj_id) {
		this.db_obj_id = db_obj_id;
	}
	
}
