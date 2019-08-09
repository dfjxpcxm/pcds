package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.rx.util.tree.TreeStore;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppTheme;

/**
 * 主题分类管理
 * @author Wanggl
 *
 */
public interface ThemeClassService {

	/**
	 * 添加主题
	 * @param theme
	 * @param userInfo 
	 * @throws Exception
	 */
	void addTheme(UppTheme uppTheme) throws Exception;

	/**
	 * 删除主题
	 * @param theme_id
	 * @throws Exception
	 */
	void removeTheme(UppTheme uppTheme) throws Exception;

	/**
	 * 修改主题
	 * @param param
	 * @throws Exception
	 */
	void updateTheme(UppTheme uppTheme) throws Exception;

	/**
	 * 查询主题
	 * @param theme_id
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getThemeById(UppTheme uppThemed) throws Exception;

	/**
	 * 根据父节点 查询主题 
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	TreeStore getTreeNodeByParentNode(String nodeId) throws Exception;

	/**
	 * 元数据   树形结构
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	TreeStore getMetadataAsNode(String nodeId) throws Exception;

	/**
	 * 查询所有元数据分类
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> listAllMatadataCat() throws Exception;

	/**
	 * 添加元数据
	 * @param uppMetadata
	 * @throws Exception
	 */
	void addMetaData(UppMetadata uppMetadata) throws Exception;

	/**
	 * 查询元数据
	 * @param uppMetadata
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getMetadataById(UppMetadata uppMetadata) throws Exception;

	/**
	 * 修改元数据
	 * @param uppMetadata
	 * @throws Exception
	 */
	void updateMetadata(UppMetadata uppMetadata) throws Exception;

	/**
	 * 删除元数据
	 * @param uppMetadata
	 * @throws Exception
	 */
	void delMetadata(UppMetadata uppMetadata) throws Exception;

}
