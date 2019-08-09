package com.shuhao.clean.apps.meta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppTheme;
import com.shuhao.clean.apps.meta.service.IThemeService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: 主题Action 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:14:49
 */
@Controller
@RequestMapping(value="/metadata/theme")
public class ThemeCtrlr extends BaseCtrlr {
	
	@Autowired
	private IThemeService themeService = null;
	
	/**
	 * 添加主题对象
	 * @param theme
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@RequestMapping(value="add")
	@ResponseBody
	public Map<String, Object> add(UppTheme theme) throws Exception {
		try {
			theme.setTheme_id(UID.next());
			theme.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.themeService.addTheme(theme);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 加载主题属性
	 * @param theme_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load(String theme_id) throws Exception {
		try {
			return super.getJsonResultMap(this.themeService.getThemeById(theme_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存主题信息
	 * @param theme
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppTheme theme) throws Exception {
		try {
			theme.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.themeService.saveTheme(theme);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 删除主题信息
	 * @param theme_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String theme_id) throws Exception {
		try {
			this.themeService.deleteTheme(theme_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
}
