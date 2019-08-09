package com.shuhao.clean.apps.meta.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppPageButton;
import com.shuhao.clean.apps.meta.service.IPageButtonService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;

/**
 * 类描述: 页面按钮action 
 * @author bixb
 * 创建时间：2015-1
 */
@Controller
@RequestMapping(value="/metadata/pageButton")
public class PageButtonCtrlr extends BaseCtrlr {
	
	@Autowired
	private IPageButtonService pageButtonService = null;
	
	/**
	 * 添加页面按钮对象
	 * @param pageButton
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public Map<String, Object> add(UppPageButton pageButton) throws Exception {
		try {
			pageButton.setButton_id(UID.next());
			pageButton.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.pageButtonService.addPageButton(pageButton);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 加载页面按钮属性
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			return super.getJsonResultMap(this.pageButtonService.getPageButtonById(params.get("button_id").toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存页面按钮信息
	 * @param pageButton
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppPageButton pageButton) throws Exception {
		try {
			pageButton.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.pageButtonService.savePageButton(pageButton);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 删除页面按钮信息
	 * @param button_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String button_id) throws Exception {
		try {
			this.pageButtonService.deletePageButton(button_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 批量删除页面按钮信息
	 * @param button_id
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
			this.pageButtonService.deletePageButtonBatch(metadata_ids);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败："+e.getMessage());
		}
	}
	
	/**
	 * 查询关联元数据
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryRelaMetadata")
	@ResponseBody
	public Object queryRelaMetadata() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			List<Map<String, Object>> dataList =  pageButtonService.queryRelaMetadata(params);
			return getJsonResultMap(GlobalUtil.lowercaseListMapKey(dataList)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询图标按钮下拉框
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryIcons")
	@ResponseBody
	public Object queryIcons() throws Exception {
		try {
			List<Map<String, Object>> dataList =  pageButtonService.queryIcons();
			return doJSONResponse(dataList); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询按钮列表
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryPageButtons")
	@ResponseBody
	public Object queryPageButtons() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			List<Map<String, Object>> dataList =  pageButtonService.queryPageButtons(params);
			return getJsonResultMap(GlobalUtil.lowercaseListMapKey(dataList)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加载页面按钮顺序
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getButtonsForDisOrder")
	@ResponseBody
	public Object getButtonsForDisOrder() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			List<Map<String, Object>> dataList = this.pageButtonService.getButtonsForDisOrder(params);
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
			this.pageButtonService.updateDisOrder(params);
			return doSuccessInfoResponse("排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("排序失败:"+e.getMessage());
		}
	}
	
}
