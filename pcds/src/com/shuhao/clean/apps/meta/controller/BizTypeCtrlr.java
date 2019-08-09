package com.shuhao.clean.apps.meta.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.BizType;
import com.shuhao.clean.apps.meta.service.IBizTypeService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.toolkit.log.annotation.UseLog;
/**
 * 字段分类维护
 * @author 
 * 
 */
@Controller
@RequestMapping("/bizType")
public class BizTypeCtrlr extends BaseCtrlr {
	
	@Autowired
	private IBizTypeService bizTypeService;
	/**
	 * 查询元数据配置的校验规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listMetadataBizType")
	@ResponseBody
	public Object listMetadataBizType() throws Exception {
		Map<String,Object> map = this.getRequestParam();
		Map<String, Object> result = new HashMap<String, Object>();
		this.insertPageParamToMap(map);
		try {
			List<BizType> pr = this.bizTypeService.listMetadataBizType(map);
			return doJSONResponse(pr);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(true));
			result.put("info", "查询失败:"+e.getMessage());
		}
		return result;
	}
					
	//@FunDesc(code="BLPT_10")?
	@UseLog
	@RequestMapping(value="addMetaType")
	@ResponseBody
	public Object addMetaType() throws Exception {
		BizType BizType = this.getParamObject(BizType.class);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.bizTypeService.addMetaType(BizType);
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
	 * 删除分类
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteMetaType")
	@ResponseBody
	public Object deleteMetaType() throws Exception {
		//MetadataCheckRule metadataCheckRule = this.getParamObject(MetadataCheckRule.class);
		BizType BizType = this.getParamObject(BizType.class);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.bizTypeService.deleteMetaType(BizType);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	//@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="updateBizType")
	@ResponseBody
	public Object updateBizType() throws Exception {
		BizType BizType = this.getParamObject(BizType.class);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.bizTypeService.updateBizType(BizType);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", Boolean.valueOf(false));
			result.put("info", "操作失败:"+e.getMessage());
		}
		return result;
	}
	
	
	
}