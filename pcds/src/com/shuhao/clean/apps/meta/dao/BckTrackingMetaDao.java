package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

public interface BckTrackingMetaDao {
	
	//获取纬表关联的数据
	public List<Map<String, Object>> getDimLinkDataByComponentId(Map<String,Object> params)throws Exception;
	
	//3.获取模板对应的元素表数据
	public List<Map<String,Object>> getMetaData(Map<String,Object> params) throws Exception;
	
	//4.获取总记录数
	public int getMetaDataCounts(Map<String,Object> params) throws Exception;
	
	//5.操作模板数据
	public void executeMetaData(Map<String,Object> params)throws Exception;
	
	
	public List<Map<String,Object>> getMetaDataById(Map<String,Object> params) throws Exception;

	/**
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getTempletByParentId(Map<String, Object> param);
	
	/**
	 * 批量执行sql
	 * @param sqlList
	 * @throws Exception
	 */
	public void batchExecSql(List<Map<String,Object>> sqlList )throws Exception;
	
	/**
	 * 更改数据状态
	 * @param params
	 * @throws Exception
	 */
	public void updateBusinessData(Map<String,Object> params) throws Exception;
	
	/**
	 * 删除数据
	 * @param params
	 * @throws Exception
	 */
	public void delBusinessData(Map<String,Object> params) throws Exception;
}