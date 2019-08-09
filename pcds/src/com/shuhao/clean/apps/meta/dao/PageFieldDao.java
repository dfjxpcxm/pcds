package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppPageField;

/**
 * 
 * 类描述: 元数据[页面字段]dao接口
 * @author bixb
 *  
 */
@MyBatisDao
public interface PageFieldDao {
	
	/**
	 * 添加页面字段对象
	 * @param pageField
	 * @throws Exception
	 */
	public void addPageField(UppPageField pageField) throws Exception;
	
	/**
	 * 根据id获取页面字段对象
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public UppPageField getPageFieldById(String metadata_id) throws Exception;
	
	/**
	 * 根据字段列表id获取页面字段对象
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getResultsByFieldListId(String metadata_id) throws Exception;
	
	/**
	 * 保存页面字段对象
	 * @param pageField
	 * @throws Exception
	 */
	public void savePageField(UppPageField pageField) throws Exception;
	
	/**
	 * 删除页面字段对象
	 * @param PageField_id
	 * @throws Exception
	 */
	public void deletePageField(String metadata_id) throws Exception;
	
	/**
	 * 批量删除页面字段对象
	 * @param PageField_id
	 * @throws Exception
	 */
	public void deletePageFieldBatch(Map<String, Object> params ) throws Exception;
	
	/**
	 * 删除删除字段列表下所有字段对象
	 * @param PageField_id
	 * @throws Exception
	 */
	public void deletePageFieldByFieldListId(String fieldListId) throws Exception;
	
	/***
	 * 按字段列表ID获取字段顺序列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getFieldsForDisOrder(Map<String, Object> params) throws Exception;
	
	/**
	 * 按页面Id查询字段顺序列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getFieldsToOrderByPageId(Map<String, Object> params) throws Exception;
	
	/**
	 * 更新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateDisOrder(Map<String, Object> params) throws Exception;
	
	/**
	 * 更新元数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void saveMetadata(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询关联表的可配置字段
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaTableCols(Map<String, Object> params) throws Exception;
	
	/**
	 * 根据字段列表ID 获取所属页面关联表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaTbColByFldLst(Map<String, Object> params) throws Exception;
	
	/**
	 * 根据字段ID 获取字段列表ID
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryPrtByFieldId(Map<String, Object> params) throws Exception;
}
