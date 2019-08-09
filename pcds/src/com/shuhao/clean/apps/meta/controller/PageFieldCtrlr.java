package com.shuhao.clean.apps.meta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppPageField;
import com.shuhao.clean.apps.meta.service.IPageFieldService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;

/**
 * 类描述: 页面字段action 
 * @author bixb
 * 创建时间：2015-1
 */
@Controller
@RequestMapping(value="/metadata/pageField")
public class PageFieldCtrlr extends BaseCtrlr {
	
	@Autowired
	private IPageFieldService pageFieldService = null;
	
	/**
	 * 添加页面字段对象
	 * @param pageField
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public Map<String, Object> add(UppPageField pageField) throws Exception {
		try {
			String user_id = super.getCurrentUser().getUser_id();
			Map<String, Object> params = this.getRequestParam();
			params.put("user_id", user_id);
			
			pageField.setField_id(UID.next());
			pageField.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.pageFieldService.addPageField(pageField,params);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse("添加页面字段对象失败:"+e.getMessage());
		}
	}
	
	/**
	 * 加载页面字段属性
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			UppPageField pageField = this.pageFieldService.getPageFieldById(params.get("field_id").toString());
			return super.getJsonResultMap(pageField);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载字段属性失败:"+e.getMessage());
		}
	}
	
	/**
	 * 根据页面ID获取页面字段属性
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listPageField")
	@ResponseBody
	public Object listPageField() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			String page_struct_id =String.valueOf(params.get("page_struct_id"));
			List<Map<String, Object>> results = this.pageFieldService.getPageFieldByPageId(page_struct_id);
			return super.getJsonResultMap(results);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("根据页面ID获取页面字段:"+e.getMessage());
		}
	}
	
	/**
	 * 根据字段列表ID获取页面字段属性
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryPageFieldByListId")
	@ResponseBody
	public Object queryPageFieldByListId() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			String field_list_id =String.valueOf(params.get("field_list_id"));
			List<Map<String, Object>> results = this.pageFieldService.getPageFieldByListId(field_list_id);
			return super.getJsonResultMap(results);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("根据页面ID获取页面字段:"+e.getMessage());
		}
	}
	
	
	/**
	 * 保存页面字段信息
	 * @param pageField
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppPageField pageField) throws Exception {
		try {
			pageField.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.pageFieldService.savePageField(pageField);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("保存失败:"+e.getMessage());
		}
	}
	
	/**
	 * 删除页面字段信息
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String field_id) throws Exception {
		try {
			this.pageFieldService.deletePageField(field_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败："+e.getMessage());
		}
	}
	
	/**
	 * 批量删除页面字段信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="deleteBatch")
	@ResponseBody
	public Map<String, Object> deleteBatch() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			String delParaStr = String.valueOf(params.get("del_params"));
			String[] metadata_ids = delParaStr.split(",");
			if(metadata_ids.length == 0){
				return doFailureInfoResponse("删除参数为空");
			}
			this.pageFieldService.deletePageFieldBatch(metadata_ids);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败："+e.getMessage());
		}
	}
	 
	
	/**
	 * 加载页面字段顺序
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getFieldsForDisOrder")
	@ResponseBody
	public Object getFieldsForDisOrder() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			List<Map<String, Object>> dataList = this.pageFieldService.getFieldsForDisOrder(params);
			return super.getJsonResultMap(GlobalUtil.lowercaseListMapKey(dataList));
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取字段顺序列表失败:"+e.getMessage());
		}
	}
	
	/**
	 * 更新排序
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateDisOrder")
	@ResponseBody
	public Object updateDisOrder() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			params.put("user_id", this.getCurrentUser().getUser_id());
			this.pageFieldService.updateDisOrder(params);
			return doSuccessInfoResponse("排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("排序失败:"+e.getMessage());
		}
	}
	
	/**
	 * 查询关联表的可配置字段
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryRelaTableCols")
	@ResponseBody
	public Object queryRelaTableCols() throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = this.getRequestParam();
			List<Map<String, Object>> results = this.pageFieldService.queryRelaTableCols(params);
			result.put("results", results);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
	}
}
