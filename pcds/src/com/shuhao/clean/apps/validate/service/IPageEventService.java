package com.shuhao.clean.apps.validate.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.validate.entity.PageEvent;

/**
 * 页面事件维护
 * @author bixb
 *
 */
public interface IPageEventService{

	/***
	 * 添加页面事件
	 * @param pageEvent
	 * @throws Exception
	 */
	public void addPageEvent(PageEvent pageEvent) throws Exception;
	
	/***
	 * 修改页面事件
	 * @param pageEvent
	 * @throws Exception
	 */
	public void editPageEvent(PageEvent pageEvent) throws Exception;

	/***
	 * 删除页面事件
	 * @param params
	 * @throws Exception
	 */
	public void deletePageEvent(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询页面事件列表
	 * @param map
	 * @return
	 */
	public List<PageEvent> getPageEvents(Map<String, Object> params);
	
	/***
	 * 查询页面事件 
	 * @param params
	 * @return
	 */
	public PageEvent getPageEventById(Map<String, Object> params);

	/***
	 * 查询最新页面事件 
	 * @param params
	 * @return
	 */
	public PageEvent getFirstPageEvent() ;
}
