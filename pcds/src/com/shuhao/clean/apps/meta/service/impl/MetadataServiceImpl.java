package com.shuhao.clean.apps.meta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.MetadataDao;
import com.shuhao.clean.apps.meta.entity.UppDicRela;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppMetadataCategory;
import com.shuhao.clean.apps.meta.service.IMetadataService;
import com.shuhao.clean.base.BaseService;

/**
 * 
 * 类描述: 元数据管理service实现类
 * @author chenxiangdong
 * 创建时间：2015-1-6下午05:07:53
 */
@Service
public class MetadataServiceImpl extends BaseService implements IMetadataService {

	@Autowired
	private MetadataDao metadataDao = null;

	/**
	 * 根据父节点查询元数据列表
	 * 
	 * @param parent_metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> queryMetadataByParentId(String parent_metadata_id) throws Exception {
		return this.metadataDao.queryMetadataByParentId(parent_metadata_id);
	}
	
	/**
	 * 查询元数据类型列表
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadataCategory> listMetadataCategory() throws Exception {
		return this.metadataDao.listMetadataCategory();
	}

	/**
	 * 添加元数据
	 * 
	 * @param metadata
	 * @throws Exception
	 */
	public void addMetadata(UppMetadata metadata) throws Exception {
		if(metadata.getMetadata_desc() == null){
			metadata.setMetadata_desc(metadata.getMetadata_name());
		}
		this.metadataDao.addMetadata(metadata);
	}

	/**
	 * 保存元数据信息
	 * 
	 * @param metadata
	 * @throws Exception
	 */
	public void saveMetadata(UppMetadata metadata) throws Exception {
		this.metadataDao.saveMetadata(metadata);
	}
	
	/**
	 * 修改元数据展示顺序
	 * @param metadata
	 * @throws Exception
	 */
	public void updateDisplayOrder(UppMetadata metadata) throws Exception {
		this.metadataDao.updateDisplayOrder(metadata);
	}
	
	/**
	 * 删除元数据
	 * @param metadata_id
	 * @throws Exception
	 */
	public void deleteMetadata(String metadata_id) throws Exception {
		if(this.metadataDao.hasSubMeta(metadata_id)) {
			throw new IllegalArgumentException("该元数据包含下级元数据,不能进行删除操作!");
		}
		this.metadataDao.deleteMetadata(metadata_id);
	}
	
	/**
	 * 批量删除元数据
	 * @param metadata_ids
	 * @throws Exception
	 */
	public void deleteMetadataBatch(String[] metadata_ids) throws Exception {
		if(metadata_ids.length == 0){
			return;
		}
		String del_para_str = "";
		for(int i = 0;i<metadata_ids.length;i++){
			if(this.metadataDao.hasSubMeta(metadata_ids[i])) {
				throw new IllegalArgumentException("元数据包含下级元数据,不能进行删除操作!");
			}
			
			if(i == metadata_ids.length -1){
				del_para_str = del_para_str + "'"+metadata_ids[i]+"'" ;
			}else{
				del_para_str =  del_para_str + "'"+metadata_ids[i]+"',"  ;
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("del_para_str", del_para_str);
		this.metadataDao.deleteMetadataBatch(params);
	}
	
	/**
	 * 根据id获取元数据
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public UppMetadata getMetadataById(String metadata_id) throws Exception {
		return this.metadataDao.getMetadataById(metadata_id);
	}
	
	/**
	 * 根据上级元数据id删除下级元数据记录
	 * @param table_id
	 * @throws Exception
	 */
	public void deleteMetadataByParentId(String table_id) throws Exception {
		this.metadataDao.deleteMetadataByParentId(table_id);
	}
	
	/**
	 * 查询引用元数据
	 * 
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> queryReferMetadata(String metadata_id) throws Exception {
		return this.metadataDao.queryReferMetadata(metadata_id);
	}
	
	/**
	 * 删除元数据映射关系
	 * @param rela
	 * @throws Exception
	 */
	public void deleteMetaRela(UppDicRela rela) throws Exception {
		String[] metadataIdArray = rela.getMetadata_id().split(",");
		for (String metadata_id : metadataIdArray) {
			rela.setMetadata_id(metadata_id);
			this.metadataDao.deleteMetaRela(rela);
		}
	}
}
