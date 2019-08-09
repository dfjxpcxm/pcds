package com.shuhao.clean.apps.meta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.service.ITemplManagerService;
import com.shuhao.clean.base.BaseCtrlr;

/**
 * 模板维护
 * @author dongln
 * 
 */
@Controller
@RequestMapping("/templManager")
public class TemplManagerCtrlr extends BaseCtrlr {
	
	@Autowired
	private ITemplManagerService templManagerService;

	/**
	 * 获取模板树
	 * @return
	 */
	@RequestMapping(value="getTemplTree")
	@ResponseBody
	public Object getTemplTree(){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> map = this.getRequestParam();
			List<Map<String,Object>> list = templManagerService.getTemplTree(map);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取模板
	 * @return
	 */
	@RequestMapping(value="getTemplByID")
	@ResponseBody
	public Object getTemplByID(){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> map = this.getRequestParam();
			List<Map<String,Object>> list = templManagerService.getTemplByID(map);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 添加模板
	 * @return
	 */
	@RequestMapping(value="addTmpl")
	@ResponseBody
	public Object addTmpl(){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> map = this.getRequestParam();
			templManagerService.addTmpl(map);
			result.put("success", true);
			result.put("info", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("info", "添加失败");
		}
		return result;
	}
	/**
	 * 编辑模板
	 * @return
	 */
	@RequestMapping(value="editTmpl")
	@ResponseBody
	public Object editTmpl(){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> map = this.getRequestParam();
			templManagerService.editTmpl(map);
			result.put("success", true);
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("info", "操作失败");
		}
		return result;
	}
	/**
	 * 删除模板
	 * @return
	 */
	@RequestMapping(value="deleteTempl")
	@ResponseBody
	public Object deleteTempl(){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> map = this.getRequestParam();
			templManagerService.deleteTempl(map);
			result.put("success", true);
			result.put("info", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("info", "删除失败");
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="updateDisOrder")
	@ResponseBody
	public Object updateDisOrder(){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> map = this.getRequestParam();
			templManagerService.updateDisOrder(map);
			result.put("success", true);
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("info", "操作失败");
		}
		return result;
	}
}
