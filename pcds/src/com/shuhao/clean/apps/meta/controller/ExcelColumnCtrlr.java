package com.shuhao.clean.apps.meta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppExcelColumn;
import com.shuhao.clean.apps.meta.service.IExcelColumnService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: 元数据[Excel列]Action 
 * @author chenxiangdong
 * 创建时间：2015-1-13下午05:35:16
 */
@Controller
@RequestMapping("/metadata/xlscolumn")
public class ExcelColumnCtrlr extends BaseCtrlr {
	
	@Autowired
	private IExcelColumnService excelColumnService;
	
	/**
	 * 添加Excel列对象
	 * @param column
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public Map<String, Object> add(UppExcelColumn column) throws Exception {
		try {
			column.setXls_col_id(UID.next());
			column.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.excelColumnService.addExcelColumn(column);
			
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 保存Excel列
	 * @param column
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppExcelColumn column) throws Exception {
		try {
			column.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.excelColumnService.saveExcelColumn(column);
			
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 加载Excel列信息
	 * @param column_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load(String column_id) throws Exception {
		try {
			return getJsonResultMap(this.excelColumnService.getExcelColumnById(column_id));
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 删除Excel列
	 * @param column_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String column_id) throws Exception {
		try {
			this.excelColumnService.deleteExcelColumn(column_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 根据sheet页查询Excel列列表
	 * @param sheet_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryColumnBySheetId")
	@ResponseBody
	public Map<String, Object> querySheetByExcelId(String sheet_id) throws Exception {
		try {
			return getJsonResultMap(this.excelColumnService.queryExcelColumnBySheetId(sheet_id));
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
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
			this.excelColumnService.updateDisOrder(params);
			return doSuccessInfoResponse("排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("排序失败:"+e.getMessage());
		}
	}
}
