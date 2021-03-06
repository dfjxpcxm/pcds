package com.shuhao.clean.apps.meta.service;

import com.shuhao.clean.apps.meta.entity.UppExcel;

/**
 * 
 * 类描述: Excel元数据业务逻辑接口 
 * @author chenxiangdong
 * 创建时间：2015-1-13上午10:16:54
 */
public interface IExcelService {
	
	/**
	 * 添加excel模板对象
	 * @param excel
	 * @throws Exception
	 */
	public void addExcel(UppExcel excel) throws Exception;
	
	
	/**
	 * 保存excel模板对象
	 * @param excel
	 * @throws Exception
	 */
	public void saveExcel(UppExcel excel) throws Exception;
	
	
	/**
	 * 根据id加载excel模板对象
	 * @param excel_id
	 * @return
	 * @throws Exception
	 */
	public UppExcel getExcelById(String excel_id) throws Exception;
	
	
	/**
	 * 删除excel对象
	 * @param excel_id
	 * @throws Exception
	 */
	public void deleteExcel(String excel_id) throws Exception;
}
