package com.shuhao.clean.apps.meta.service;

import com.shuhao.clean.apps.meta.entity.UppTheme;

/**
 * 
 * 类描述: 元数据[主题]业务逻辑接口 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:36:45
 */
public interface IThemeService {
	
	/**
	 * 添加主题
	 * @param theme
	 * @throws Exception
	 */
	public void addTheme(UppTheme theme) throws Exception;
	
	/**
	 * 根据id获取主题对象
	 * @param theme_id
	 * @return
	 * @throws Exception
	 */
	public UppTheme getThemeById(String theme_id) throws Exception;
	
	/**
	 * 保存主题对象
	 * @param theme
	 * @throws Exception
	 */
	public void saveTheme(UppTheme theme) throws Exception;
	
	/**
	 * 删除主题
	 * @param theme_id
	 * @throws Exception
	 */
	public void deleteTheme(String theme_id) throws Exception;
	
}
