package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.rx.security.domain.IUser;

public interface IPageManagerService {
	
	
	//0.获取模板结构树
	
	public List<Map<String,Object>> getTemplateTree(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getTemplateById(Map<String,Object> params) throws Exception;
	/*
	 *
	 */
	public  List<Map<String,Object>>   getSheetMappingTab(Map<String,Object> params) throws Exception;
	
	//1.根据模板获取元数据
	
//	public List<Map<String,Object>> getMetaByTemplateId(Map<String,Object> params) throws Exception;
	
	//根据模板获取分类元素
//	public List<Map<String,Object>> getMetaTypeByTemplateId(Map<String,Object> params) throws Exception;
	
	//根据模板获取查询菜单元素
	
//	public List<Map<String,Object>> getMenuByTemplateId(Map<String,Object> params) throws Exception;
	
	
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
	
	public void executeMetaData(Map<String,Object> params,IUser user)throws Exception;
	
	//5.获取总记录数
	public int getMetaDataCounts(Map<String,Object> params) throws Exception;

	/**根据模板父级ID获取模板
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getTempletByParentId(
			Map<String, Object> param);
	
	/**
	 * 前端调用的ajax事件,返回查询结果
	 * @param templateId
	 * @param metaName
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getEventResults(String templateId,String metaName,String param) throws Exception;
	
	/**
	 * 返回事件结果集
	 * @param templateId
	 * @param metaName
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getEventStore(String templateId,String metaName,String param) throws Exception;
	
	/**
	 * 批量执行sql
	 * @param sqlList
	 * @throws Exception
	 */
	public void batchExecSql(List<Map<String,Object>> sqlList)throws Exception;
	
	public void batchExecSql(List<Map<String,Object>> insSqlList,List<Map<String,Object>> delSqlList)throws Exception;

	
	public String getTabNameByTempId(Map<String,Object> params) throws Exception;
	/**
	 * 取button配置信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getButtonByTmplId(Map<String,Object> params) throws Exception;
	
//	public void updateFlowData(Map<String,Object> params) throws Exception;
	
	public void updateBusinessData(Map<String,Object> params) throws Exception;
	
	public void delBusinessData(Map<String,Object> params) throws Exception;
	
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
	 * 生成业务编号
	 * @param params
	 * @return
	 */
	public String getBusinessNo(Map<String, Object> params);
	
	/**
	 * 重构校验数据对象
	 * @param templateId
	 * @param validData
	 * @throws Exception
	 */
	public void rebuildValidData(String templateId,Map<String,Object> validData) throws Exception;

	/**
	 * 获取模版配置的元数据信息
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getPageEleByTemplateId(
			Map<String, Object> params) throws Exception;

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getChildTempl(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getFDL(Map<String, Object> params);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getWorkFlowById(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getExcelCol(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getSheetListById(Map<String, Object> params);

	/**
	 * @param tempParaMap
	 * @return
	 */
	public List<Map<String, Object>> getExcelColByName(
			Map<String, Object> tempParaMap);

	/**
	 * @param tempParaMap
	 * @return
	 */
	public List<Map<String, Object>> getExcelSheetByName(
			Map<String, Object> tempParaMap);

	/**
	 * 按模版ID 查询字段列表
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getFDLByTmplId(Map<String, Object> params);
	
	/**
	 * 取hidden默认值
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getHiddenDefaultValue(Map<String, Object> params);

	/**
	 * 生成excel导出时的列头信息
	 * <br> 返回：colName,excelData
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getExcelColByMeta(List<Map<String,Object>> metaParamsList,boolean isDefault,boolean addLink) throws Exception;

	
	/**
	 * 查询维度数据列表
	 * @param dimCode
	 * @param rootValue
	 * @return
	 * @throws Exception
	 */
	public List<String> getLinkDataForExp(String dimCode,String rootValue) throws Exception;

		
	/**
	 * 获取子模版信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getSubTemplateById(Map<String,Object> params) throws Exception;
	
	/**
	 * 获取提交的账户信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getSubmitAccts(Map<String,Object> params) throws Exception;
	/**
	 * 更新数据状态
	 * @param accts
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public int updateStatus(Map<String,Object> params,String tableName) throws Exception;
	
	/**
	 * 取字段类型
	 * @param metadataId
	 * @return
	 * @throws Exception
	 */
	public String getFieldType(String metadataId) throws Exception;


	public boolean getFlowDataCountsByTplId(String tmpId) throws Exception;
}
