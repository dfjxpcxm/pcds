package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

/**
 * 已废弃(变更为IPageManagerService)
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
public interface IBckTrackingService {
	
	//0.获取模板结构树
	public List<Map<String,Object>> getTemplateTree(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getTemplateById(Map<String,Object> params) throws Exception;
	
	public  List<Map<String,Object>>   getTmplMappingTab(Map<String,Object> params) throws Exception;
	
	//1.根据模板获取元数据
	
	public List<Map<String,Object>> getMetaByTemplateId(Map<String,Object> params) throws Exception;
	
	//根据模板获取分类元素
	public List<Map<String,Object>> getMetaTypeByTemplateId(Map<String,Object> params) throws Exception;
	
	//根据模板获取查询菜单元素
	
	public List<Map<String,Object>> getMenuByTemplateId(Map<String,Object> params) throws Exception;
	
	//根据模板获取按钮元素
	
	public List<Map<String,Object>> getButtonByTemplateId(Map<String,Object> params) throws Exception;
	
	//根据分类获取表单元素
	
	public List<Map<String,Object>> getMetaByTypeId(Map<String,Object> params) throws Exception;
	
	//获取纬表关联的sql
	public String getDimLinkSql(Map<String,Object> params) throws Exception;
	
	//获取纬表查询的数据
	public  List<Map<String,Object>> getDimLinkForTree(Map<String,Object> params) throws Exception;
	
	//2.根据元数据id获取维表数据
	public List<Map<String,Object>> getDimLinkByComponentId(Map<String,Object> params) throws Exception;
	
	//3.根据模板id查询模板数据
	
	public List<Map<String,Object>> getMetaData(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getMetaDataById(Map<String,Object> params) throws Exception;
	
	//4.操作模板元素表数据
	
	public void executeMetaData(Map<String,Object> params)throws Exception;
	
	//5.获取总记录数
	public int getMetaDataCounts(Map<String,Object> params) throws Exception;

	/**根据模板父级ID获取模板
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getTempletByParentId(
			Map<String, Object> param);
	
	//查询事件结果
	public List<Map<String,Object>> getEventResults(String templateId,String metaName,String param) throws Exception;
	
	/**
	 * 批量执行sql
	 * @param sqlList
	 * @throws Exception
	 */
	public void batchExecSql(List<Map<String,Object>> sqlList )throws Exception;
	
	
	public String getTabNameByTempId(Map<String,Object> params) throws Exception;
	
	//public void updateFlowData(Map<String,Object> params) throws Exception;
	
	//获取模板对应的所有维度代码的关联sql
	public List<Map<String,Object>> getMetaLinkJoinSql(Map<String,Object> params) throws Exception;
	
	
	//导出模板
	public List<Map<String,Object>> getMetaInfoForExp(Map<String,Object> params) throws Exception;
	
	/**
	 * 获取模版关联的元数据
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getTemplateMetaInfo(String templateId) throws Exception;
	/**
	 * 从元数据列表中筛选metadata_id
	 * @param dataList
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String findMetaIdFromList(List<Map<String,Object>> dataList,String name) throws Exception;
	
	/**
	 * 重构校验数据对象
	 * @param templateId
	 * @param validData
	 * @throws Exception
	 */
	public void rebuildValidData(String templateId,Map<String,Object> validData) throws Exception;
}
