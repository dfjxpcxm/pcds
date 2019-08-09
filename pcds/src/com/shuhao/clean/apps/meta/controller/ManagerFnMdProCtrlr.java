package com.shuhao.clean.apps.meta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppFnMetadataProperty;
import com.shuhao.clean.apps.meta.service.IManagerFnMdProService;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.PageResult;

/**
 * 功能元数据维护
 * @author Ning
 *
 */

@Controller
@RequestMapping("/managerFnMdPro")
public class ManagerFnMdProCtrlr extends BaseCtrlr {
	
	@Autowired
	private IManagerFnMdProService managerFnMdProService;
	
	/**
	 * 查询功能能元数据属性
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listFnMdPro")
	@ResponseBody
	public Object listFnMdPro() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			this.insertPageParamToMap(params);
			PageResult<UppFnMetadataProperty> result = this.managerFnMdProService.listFnMdPro(params);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}
	
	/**
	 * 添加功能元数据属性
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addPro")
	@ResponseBody
	public Object addPro() throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			SysUserInfo user = getCurrentUser();
			UppFnMetadataProperty property = this.getParamObject(UppFnMetadataProperty.class);
			String metadata_id = this.managerFnMdProService.addMetadata(user,property);
			result.put("success", true);
			result.put("metadata_id", metadata_id);
			result.put("info", "添加成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "添加失败:" + e.getMessage());
			return result;
		}
	}
	
	
	/**
	 * 修改功能元数据属性
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="editPro")
	@ResponseBody
	public Object editPro() throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			UppFnMetadataProperty property = this.getParamObject(UppFnMetadataProperty.class);
			this.managerFnMdProService.editFnMdPro(property);
			result.put("success", true);
			result.put("info", "修改成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "修改失败:" + e.getMessage());
			return result;
		}
	}
	
	
	
	/**
	 * 删除功能元数据属性
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deletePro")
	@ResponseBody
	public Object deletePro() throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			UppFnMetadataProperty property = this.getParamObject(UppFnMetadataProperty.class);
			this.managerFnMdProService.deleteFnMdPro(property);
			result.put("success", true);
			result.put("info", "删除成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "删除失败:" + e.getMessage());
			return result;
		}
	}
	
	/**
	 * 查询业务类型代码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listBizType")
	@ResponseBody
	public Object listBizType() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = this.getRequestParam();
			List<Map<String,Object>> list = this.managerFnMdProService.listBizType(params);
			result.put("results", list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}
	/**
	 * 查询字段列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="fnColumns")
	@ResponseBody
	public Object fnColumns() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = this.getRequestParam();
			List<Map<String,Object>> list = this.managerFnMdProService.fnColumns(params);
			result.put("results", list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}
	/**
	 * 更新字段排序
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateDisOrder")
	@ResponseBody
	public Object updateDisOrder() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = this.getRequestParam();
			this.managerFnMdProService.updateDisOrder(params);
			result.put("success", Boolean.valueOf(true));
			result.put("info", "操作成功:");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}
	
}
