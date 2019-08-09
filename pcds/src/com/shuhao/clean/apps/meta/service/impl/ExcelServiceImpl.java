package com.shuhao.clean.apps.meta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.ExcelDao;
import com.shuhao.clean.apps.meta.entity.UppExcel;
import com.shuhao.clean.apps.meta.service.IExcelService;
import com.shuhao.clean.apps.meta.service.IMetadataService;

/**
 * 
 * 类描述: Excel元数据业务逻辑实现类 
 * @author chenxiangdong
 * 创建时间：2015-1-13上午10:19:57
 */
@Service
public class ExcelServiceImpl implements IExcelService {
	
	@Autowired
	private IMetadataService metadataService;
	
	@Autowired
	private ExcelDao excelDao;
	
	/**
	 * 添加excel模板对象
	 * @param excel
	 * @throws Exception
	 */
	public void addExcel(UppExcel excel) throws Exception {
		this.metadataService.addMetadata(excel);
		this.excelDao.addExcel(excel);
	}
	
	
	/**
	 * 保存excel模板对象
	 * @param excel
	 * @throws Exception
	 */
	public void saveExcel(UppExcel excel) throws Exception {
		this.metadataService.saveMetadata(excel);
		this.excelDao.saveExcel(excel);
	}
	
	
	/**
	 * 根据id加载excel模板对象
	 * @param excel_id
	 * @return
	 * @throws Exception
	 */
	public UppExcel getExcelById(String excel_id) throws Exception {
		return this.excelDao.getExcelById(excel_id);
	}
	
	
	/**
	 * 删除excel对象
	 * @param excel_id
	 * @throws Exception
	 */
	public void deleteExcel(String excel_id) throws Exception {
		this.excelDao.deleteExcel(excel_id);
		this.metadataService.deleteMetadata(excel_id);
	}
}
