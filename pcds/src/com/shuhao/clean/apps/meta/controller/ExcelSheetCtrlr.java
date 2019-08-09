package com.shuhao.clean.apps.meta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppExcelSheet;
import com.shuhao.clean.apps.meta.service.IExcelSheetService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: Excel sheet页元数据Action 
 * @author chenxiangdong
 * 创建时间：2015-1-13下午03:13:58
 */
@Controller
@RequestMapping("/metadata/sheet")
public class ExcelSheetCtrlr extends BaseCtrlr {
	
	@Autowired
	private IExcelSheetService excelSheetService;
	
	/**
	 * 添加Sheet页对象
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public Map<String, Object> add(UppExcelSheet sheet) throws Exception {
		try {
			String sheet_id = UID.next();
			sheet.setSheet_id(sheet_id);
			sheet.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.excelSheetService.addSheet(sheet);
			
			return doSuccessInfoResponse(sheet_id);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 保存sheet页
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppExcelSheet sheet) throws Exception {
		try {
			sheet.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.excelSheetService.saveSheet(sheet);
			
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 加载sheet页信息
	 * @param sheet_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load(String sheet_id) throws Exception {
		try {
			return getJsonResultMap(this.excelSheetService.getSheetById(sheet_id));
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 删除excel sheet页
	 * @param sheet_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String sheet_id) throws Exception {
		try {
			this.excelSheetService.deleteSheet(sheet_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 根据excel查询sheet页列表
	 * @param excel_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="querySheetByExcelId")
	@ResponseBody
	public Map<String, Object> querySheetByExcelId(String excel_id) throws Exception {
		try {
			return getJsonResultMap(this.excelSheetService.querySheetByExcelId(excel_id));
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
}
