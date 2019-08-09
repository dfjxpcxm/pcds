package com.shuhao.clean.apps.validate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.validate.entity.PageEvent;
import com.shuhao.clean.apps.validate.service.IPageEventService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.UID;

/**
 * 页面事件
 * @author bixb
 * 
 */
@Controller
@RequestMapping("/pageEvent")
public class PageEventCtrlr extends BaseCtrlr {
	
	@Autowired
	private IPageEventService pageEventService;
	
	/**
	 * 添加页面事件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addPageEvent")
	@ResponseBody
	public Object addPageEvent(PageEvent pageEvent) throws Exception {
		pageEvent.setFile_id(UID.next());
		pageEvent.setUpdate_user(this.getCurrentUser().getUserID());
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.pageEventService.addPageEvent(pageEvent);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 修改页面事件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="editPageEvent")
	@ResponseBody
	public Object editPageEvent(PageEvent pageEvent) throws Exception {
		
		pageEvent.setUpdate_user(this.getCurrentUser().getUserID());
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.pageEventService.editPageEvent(pageEvent);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	/**
	 * 删除页面事件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deletePageEvent")
	@ResponseBody
	public Object deletePageEvent() throws Exception {
		Map<String, Object> params = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.pageEventService.deletePageEvent(params);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询页面事件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getPageEvents")
	@ResponseBody
	public Object getPageEvents() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PageEvent> dataList = this.pageEventService.getPageEvents(map);
			return doJSONResponse(dataList) ;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "查询失败:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询页面事件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getPageEventById")
	@ResponseBody
	public Object getPageEventById() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		this.insertPageParamToMap(map);
		try {
			PageEvent pageEvent = this.pageEventService.getPageEventById(map);
			List<PageEvent> dataList = new ArrayList<PageEvent>();
			dataList.add(pageEvent);
			return doJSONResponse(dataList) ;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "查询失败:"+e.getMessage());
		}
		return result;
	}
	
	
}
