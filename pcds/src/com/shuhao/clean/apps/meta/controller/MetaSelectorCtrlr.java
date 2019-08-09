package com.shuhao.clean.apps.meta.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.service.IMetaSelectorService;
import com.shuhao.clean.base.BaseCtrlr;

/**
 * 元数据selector
 * @author dongln
 * 
 */
@Controller
@RequestMapping("/metaSelector")
public class MetaSelectorCtrlr extends BaseCtrlr {
	
	@Autowired
	private IMetaSelectorService metaSelectorService;
	
	/**
	 * 查询状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listTemplateStatus")
	@ResponseBody
	public Object listTemplateStatus() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("results", this.metaSelectorService.listTemplateStatus(map));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作失败");
		}
		return result;
	}
}
