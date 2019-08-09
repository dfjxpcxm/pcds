package com.shuhao.clean.apps.meta.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shuhao.clean.apps.meta.entity.UppExcel;
import com.shuhao.clean.apps.meta.service.IExcelService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: 元数据[Excel]Action类 
 * @author chenxiangdong
 * 创建时间：2015-1-13上午10:09:30
 */
@Controller
@RequestMapping("/metadata/excel")
public class ExcelCtrlr extends BaseCtrlr {
	
	@Autowired
	private IExcelService excelService;
	
	/**
	 * 添加excel模板
	 * @param excel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public Map<String, Object> add(UppExcel excel) throws Exception {
		try {
			excel.setExcel_id(UID.next());
			excel.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.excelService.addExcel(excel);
			
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 保存excel模板
	 * @param excel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppExcel excel) throws Exception {
		try {
			excel.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.excelService.saveExcel(excel);
			
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 加载excel模板信息
	 * @param excel_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load(String excel_id) throws Exception {
		try {
			return getJsonResultMap(this.excelService.getExcelById(excel_id));
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 删除excel模板
	 * @param excel_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String excel_id) throws Exception {
		try {
			this.excelService.deleteExcel(excel_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
}
