package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppMetadata;

@MyBatisDao
public interface UppMetadataDao {
	
	/**
	 * 添加元数据表  upp_metadata
	 * @param param
	 * @throws Exception
	 */
	void addThemeMeta(UppMetadata uppMetadata) throws Exception;
	
	/**
	 * 修改元数据表  upp_metadata
	 * @param param
	 * @throws Exception
	 */
	void updateThemeMeta(UppMetadata uppMetadata) throws Exception;
	
	/**
	 * 删除元数据  upp_metadata
	 * @param theme_id
	 * @throws Exception
	 */
	void delThemeMeta(UppMetadata uppMetadata) throws Exception;
	
	/**
	 * 批量添加元数据
	 * @param list
	 * @throws Exception
	 */
	void addMetadataList(List<UppMetadata> uppMetadataList) throws Exception;
	
	/**
	 * 批量删除元数据
	 * @param list
	 * @throws Exception
	 */
	void delMetadataList(List<UppMetadata> uppMetadataList) throws Exception;
	
	/**
	 * 删除元数据  删除父级节点及以下所有子节点
	 * @param uppMetadata
	 * @throws Exception
	 */
	void delMetadataByParentId(UppMetadata uppMetadata) throws Exception;

	/**
	 * 查询元数据
	 * @param uppMetadata
	 * @return
	 */
	List<Map<String, Object>> getMetadataById(UppMetadata uppMetadata);
	
}
