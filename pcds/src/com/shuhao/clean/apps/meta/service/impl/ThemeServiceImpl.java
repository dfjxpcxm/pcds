package com.shuhao.clean.apps.meta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.ThemeDao;
import com.shuhao.clean.apps.meta.entity.UppTheme;
import com.shuhao.clean.apps.meta.service.IMetadataService;
import com.shuhao.clean.apps.meta.service.IThemeService;

/**
 * 
 * 类描述: 元数据[主题]业务逻辑实现类
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:36:45
 */
@Service
public class ThemeServiceImpl implements IThemeService {
	
	@Autowired
	private IMetadataService metadataService = null;
	
	@Autowired
	private ThemeDao themeDao = null;
	
	/**
	 * 添加主题
	 * @param theme
	 * @throws Exception
	 */
	public void addTheme(UppTheme theme) throws Exception {
		this.metadataService.addMetadata(theme);
		this.themeDao.addTheme(theme);
	}
	
	/**
	 * 根据id获取主题对象
	 * @param theme_id
	 * @return
	 * @throws Exception
	 */
	public UppTheme getThemeById(String theme_id) throws Exception {
		return this.themeDao.getThemeById(theme_id);
	}
	
	/**
	 * 保存主题对象
	 * @param theme
	 * @throws Exception
	 */
	public void saveTheme(UppTheme theme) throws Exception {
		this.metadataService.saveMetadata(theme);
		this.themeDao.saveTheme(theme);
	}
	
	/**
	 * 删除主题
	 * @param theme_id
	 * @throws Exception
	 */
	public void deleteTheme(String theme_id) throws Exception {
		this.metadataService.deleteMetadata(theme_id);
		this.themeDao.deleteTheme(theme_id);
	}
	
}
