package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppDicRela;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppMetadataCategory;

/**
 * 
 * 类描述: 元数据数据库操作接口
 * @author chenxiangdong
 * 创建时间：2015-1-6下午06:07:18
 */
@MyBatisDao
public interface MetadataDao {

	/**
	 * 根据父节点查询元数据列表
	 * 
	 * @param parent_metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> queryMetadataByParentId(String parent_metadata_id) throws Exception;
	
	/**
	 * 查询元数据类型列表
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadataCategory> listMetadataCategory() throws Exception;

	/**
	 * 添加元数据
	 * 
	 * @param metadata
	 * @throws Exception
	 */
	public void addMetadata(UppMetadata metadata) throws Exception;
	
	/**
	 * 保存元数据信息
	 * @param metadata
	 * @throws Exception
	 */
	public void saveMetadata(UppMetadata metadata) throws Exception;
	
	/**
	 * 修改元数据展示顺序
	 * @param metadata
	 * @throws Exception
	 */
	public void updateDisplayOrder(UppMetadata metadata) throws Exception;
	
	/**
	 * 删除元数据
	 * @param metadata_id
	 * @throws Exception
	 */
	public void deleteMetadata(String metadata_id) throws Exception;
	
	/**
	 * 批量删除元数据
	 * @param params
	 * @throws Exception
	 */
	public void deleteMetadataBatch(Map<String, Object> params) throws Exception;
	
	/**
	 * 根据id获取元数据
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public UppMetadata getMetadataById(String metadata_id) throws Exception;
	
	/**
	 * 根据上级元数据id删除下级元数据记录
	 * @param table_id
	 * @throws Exception
	 */
	public void deleteMetadataByParentId(String table_id) throws Exception;
	
	
	/**
	 * 是否有下级元数据
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public boolean hasSubMeta(String metadata_id) throws Exception;
	
	/**
	 * 查询引用元数据
	 * 
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> queryReferMetadata(String metadata_id) throws Exception; 
	
	/**
	 * 删除元数据映射关系
	 * @param rela
	 * @throws Exception
	 */
	public void deleteMetaRela(UppDicRela rela) throws Exception;

}
