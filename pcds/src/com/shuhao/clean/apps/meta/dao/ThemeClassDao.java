package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppTheme;

/**
 * 主题分类管理
 * @author Wanggl
 *
 */
@MyBatisDao
public interface ThemeClassDao {

	/**
	 * 添加主题   表upp_theme
	 * @param param
	 * @throws Exception
	 */
	void addTheme(UppTheme uppTheme) throws Exception;

	/**
	 * 删除主题   表upp_theme
	 * @param theme_id
	 * @throws Exception
	 */
	void removeTheme(UppTheme uppTheme) throws Exception;

	/**
	 * 修改主题  表upp_theme
	 * @param param
	 * @throws Exception
	 */
	void updateTheme(UppTheme uppTheme) throws Exception;

	/**
	 * 查询主题信息
	 * @param themeId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getThemeById(UppTheme uppTheme) throws Exception;

	/**
	 * 由父节点 查询主题信息
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	List<UppTheme> findTheme(String nodeId) throws Exception;

	/**
	 * 元数据  树形结构
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	List<UppMetadata> getMetadataAsNode(String nodeId) throws Exception;

	/**
	 * 查询所有元数据分类
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> listAllMatadataCat() throws Exception;
}
