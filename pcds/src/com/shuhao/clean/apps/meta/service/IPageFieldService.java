package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppPageField;

/**
 * 
 * 类描述: 元数据[页面字段]业务逻辑接口
 * @author bixb
 * 创建时间：2015-1
 */
public interface IPageFieldService {
	
	/**
	 * 添加页面字段对象
	 * @param pageField
	 * @throws Exception
	 */
	public void addPageField(UppPageField pageField,Map<String, Object> params) throws Exception;
	
	/**
	 * 从表中添加表的字段
	 * @param pageField
	 * @throws Exception
	 */
	public void addPageFieldFromTable(Map<String, Object> params) throws Exception;
	
	/***
	 *  从表中添加表的字段(添加指定字段、元数据、映射)
	 * @param params
	 * @param fieldList
	 * @throws Exception
	 */
	public void addPageFields(List<UppPageField> fieldList) throws Exception;
	
	/**
	 * 根据id获取页面字段对象
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public UppPageField getPageFieldById(String metadata_id) throws Exception;
	
	/**
	 * 根据页面ID获取页面字段对象
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPageFieldByPageId(String metadata_id) throws Exception;
	
	/**
	 * 根据字段列表ID获取页面字段对象
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPageFieldByListId(String metadata_id) throws Exception;
	
	/**
	 * 保存页面字段对象
	 * @param pageField
	 * @throws Exception
	 */
	public void savePageField(UppPageField pageField) throws Exception;
	
	/**
	 * 删除页面字段对象
	 * @param metadata_id
	 * @throws Exception
	 */
	public void deletePageField(String metadata_id) throws Exception;
	
	/**
	 * 批量删除页面字段对象
	 * @param metadata_id
	 * @throws Exception
	 */
	public void deletePageFieldBatch(String[] metadata_ids) throws Exception;
 
	
	/**
	 * 获取字段顺序列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getFieldsForDisOrder(Map<String, Object> params) throws Exception;
	
	/**
	 * 更新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateDisOrder(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询关联表的可配置字段
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaTableCols(Map<String, Object> params) throws Exception;
	
}
