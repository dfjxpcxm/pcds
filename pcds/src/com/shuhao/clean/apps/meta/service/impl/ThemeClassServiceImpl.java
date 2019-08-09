package com.shuhao.clean.apps.meta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.util.tree.TreeStore;
import com.shuhao.clean.apps.meta.dao.ThemeClassDao;
import com.shuhao.clean.apps.meta.dao.UppMetadataDao;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppTheme;
import com.shuhao.clean.apps.meta.service.ThemeClassService;
import com.shuhao.clean.base.BaseService;
/**
 * 主题分类管理
 * @author Wanggl
 *
 */
@Service(value="themeClassService")
public class ThemeClassServiceImpl extends BaseService implements ThemeClassService{
	
	@Autowired
	private ThemeClassDao themeClassDao;
	
	@Autowired
	private UppMetadataDao uppMetadataDao;

	/**
	 * 添加主题
	 */
	public void addTheme(UppTheme uppTheme) throws Exception {
		this.themeClassDao.addTheme(uppTheme);
//		this.uppMetadataDao.addThemeMeta(uppTheme.getUppMetadata());
	}

	/**
	 * 删除主题
	 */
	public void removeTheme(UppTheme uppTheme) throws Exception {
		this.themeClassDao.removeTheme(uppTheme);
//		this.uppMetadataDao.delThemeMeta(uppTheme.getUppMetadata());
	}

	/**
	 * 修改主题
	 */
	public void updateTheme(UppTheme uppTheme) throws Exception {
		this.themeClassDao.updateTheme(uppTheme);
//		this.uppMetadataDao.updateThemeMeta(uppTheme.getUppMetadata());
	}

	/**
	 * 查询主题
	 */
	public List<Map<String, Object>> getThemeById(UppTheme uppTheme)
			throws Exception {
		return this.themeClassDao.getThemeById(uppTheme);
	}

	/**
	 * 根据父节点 查询主题信息
	 */
	public TreeStore getTreeNodeByParentNode(String nodeId) throws Exception{
		TreeStore store = new TreeStore();
		
		List<UppTheme> themeList = this.themeClassDao.findTheme(nodeId);
		for (UppTheme uppTheme : themeList) {
			store.addTreeNode(uppTheme);
		}
		return store;
	}

	/**
	 * 元数据  树形结构
	 */
	public TreeStore getMetadataAsNode(String nodeId) throws Exception {
		TreeStore store = new TreeStore();
		
		List<UppMetadata> list = this.themeClassDao.getMetadataAsNode(nodeId);
		for (UppMetadata uppMetadata : list) {
			store.addTreeNode(uppMetadata);
		}
		return store;
	}

	
	public List<Map<String, Object>> listAllMatadataCat() throws Exception {
		return toLowerMapList(this.themeClassDao.listAllMatadataCat());
	}

	
	public void addMetaData(UppMetadata uppMetadata) throws Exception {
		this.uppMetadataDao.addThemeMeta(uppMetadata);
	}

	
	public List<Map<String, Object>> getMetadataById(UppMetadata uppMetadata)
			throws Exception {
		return toLowerMapList(this.uppMetadataDao.getMetadataById(uppMetadata));
	}

	
	public void updateMetadata(UppMetadata uppMetadata) throws Exception {
		this.uppMetadataDao.updateThemeMeta(uppMetadata);
	}

	
	public void delMetadata(UppMetadata uppMetadata) throws Exception {
		this.uppMetadataDao.delThemeMeta(uppMetadata);
	}

}
