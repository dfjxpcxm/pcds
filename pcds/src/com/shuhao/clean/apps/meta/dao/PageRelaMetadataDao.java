package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppDicFnRela;
import com.shuhao.clean.apps.meta.entity.UppTableColumn;

/**
 * 
 * 类描述: 页面及页面元素关联元数据dao接口
 * @author bixb
 *  
 */
@MyBatisDao
public interface PageRelaMetadataDao {
	
	 
	/**
	 * 查询关联元数据
	 * @param button_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaMetadata(Map<String, Object> params) throws Exception;
	
	/**
	 * 增加数据字典与功能映射关系
	 * @param paramMap
	 * @throws Exception
	 */
	public void addDicFnRela(UppDicFnRela dicFn)throws Exception;
	
	/***
	 * 查询关联映射数据字典
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaDicData(Map<String, Object> params) throws Exception;
	
	/**
	 * 删除数据字典与功能映射关系
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteDicFnRela(UppDicFnRela dicFn)throws Exception;
	
	/**
	 * 根据关联表ID和页面ID获取增量字段列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<UppTableColumn> getNoRelaTableColumns(Map<String, Object> params) throws Exception;
	
	
}
