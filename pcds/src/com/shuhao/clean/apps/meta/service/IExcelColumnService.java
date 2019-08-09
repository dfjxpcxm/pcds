package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppExcelColumn;

/**
 * 
 * 类描述: 元数据[Excel列]业务逻辑接口 
 * @author chenxiangdong
 * 创建时间：2015-1-13下午05:23:08
 */
public interface IExcelColumnService {
	
	/**
	 * 添加Excel列
	 * @param column
	 * @throws Exception
	 */
	public void addExcelColumn(UppExcelColumn column) throws Exception;
	
	
	/**
	 * 根据id获取Excel列信息
	 * @param column_id
	 * @return
	 * @throws Exception
	 */
	public UppExcelColumn getExcelColumnById(String column_id) throws Exception;
	
	
	/**
	 * 保存Excel列信息
	 * @param column
	 * @throws Exception
	 */
	public void saveExcelColumn(UppExcelColumn column) throws Exception;
	
	
	/**
	 * 删除Excel列信息
	 * @param column_id
	 * @throws Exception
	 */
	public void deleteExcelColumn(String column_id) throws Exception;
	
	
	/**
	 * 查询sheet页下所有列信息
	 * @param sheet_id
	 * @return
	 * @throws Exception
	 */
	public List<UppExcelColumn> queryExcelColumnBySheetId(String sheet_id) throws Exception;
	
	
	/**
	 * 更新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateDisOrder(Map<String, Object> params) throws Exception;
}
