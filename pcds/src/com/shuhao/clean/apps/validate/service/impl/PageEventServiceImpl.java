package com.shuhao.clean.apps.validate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.validate.dao.PageEventDao;
import com.shuhao.clean.apps.validate.entity.PageEvent;
import com.shuhao.clean.apps.validate.service.IPageEventService;
import com.shuhao.clean.base.BaseJdbcService;

/**
 * 页面事件
 * @author:  bixb
 */
@Service(value="pageEventService")
public class PageEventServiceImpl  extends BaseJdbcService implements IPageEventService {

	@Autowired
	private PageEventDao pageEventDao;

	/***
	 * 添加页面事件
	 * @param pageEvent
	 * @throws Exception
	 */
	public void addPageEvent(PageEvent pageEvent) throws Exception {
		 
		pageEventDao.addPageEvent(pageEvent);
	}
	
	/***
	 * 修改页面事件
	 * @param pageEvent
	 * @throws Exception
	 */
	public void editPageEvent(PageEvent pageEvent) throws Exception{
		pageEventDao.editPageEvent(pageEvent);
	}

	/***
	 * 删除页面事件
	 * @param params
	 * @throws Exception
	 */
	public void deletePageEvent(Map<String, Object> params) throws Exception {
		
		pageEventDao.deletePageEvent(params);
	}

	/**
	 * 查询页面事件列表
	 * @param map
	 * @return
	 */
	public List<PageEvent> getPageEvents(Map<String, Object> params) {
		List<PageEvent> dataList =  pageEventDao.getPageEvents(params);
		for (int i = 0; i < dataList.size(); i++) {
			String fileContent = dataList.get(i).getEvent_file();
			fileContent = fileContent.replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;")
					.replaceAll("\n", "<br/>")
					.replaceAll(" ", "&nbsp;")
					.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			if(i == 0){
				fileContent = "<font color='red'>(本记录为目前系统使用配置)</font><br/>"+fileContent;
			}
			dataList.get(i).setEvent_file(fileContent);
		}
		return dataList;
	}

	/***
	 * 查询页面事件 
	 * @param params
	 * @return
	 */
	public PageEvent getPageEventById(Map<String, Object> params) {
		List<PageEvent> dataList =  pageEventDao.getPageEvents(params);
		if(dataList.size() > 0){
			PageEvent pageEvent = dataList.get(0);
			String fileContent = pageEvent.getEvent_file();
			pageEvent.setEvent_file(fileContent);
			return pageEvent;
		}
		return null;
	}
	 
	public PageEvent getFirstPageEvent() {
		return pageEventDao.getFristPageEvent();
	}
}
