package com.shuhao.clean.apps.meta.dao;

import java.util.List;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppExcelSheet;

/**
 * 
 * 类描述: Excel Sheet页元数据dao接口 
 * @author chenxiangdong
 * 创建时间：2015-1-13下午02:58:50
 */
@MyBatisDao
public interface ExcelSheetDao {
	
	/**
	 * 添加Sheet页对象
	 * @param sheet
	 * @throws Exception
	 */
	public void addSheet(UppExcelSheet sheet) throws Exception;
	
	
	/**
	 * 根据id获取sheet页对象
	 * @param sheet_id
	 * @return
	 * @throws Exception
	 */
	public UppExcelSheet getSheetById(String sheet_id) throws Exception;
	
	
	/**
	 * 保存sheet页对象
	 * @param sheet
	 * @throws Exception
	 */
	public void saveSheet(UppExcelSheet sheet) throws Exception;
	
	
	/**
	 * 删除sheet页对象
	 * @param sheet_id
	 * @throws Exception
	 */
	public void deleteSheet(String sheet_id) throws Exception;
	
	
	/**
	 * 根据Excel id查询sheet页列表
	 * @param excel_id
	 * @return
	 * @throws Exception
	 */
	public List<UppExcelSheet> querySheetByExcelId(String excel_id) throws Exception;
	
}
