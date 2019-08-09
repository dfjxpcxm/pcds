package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shuhao.clean.annotation.MyBatisDao;

@MyBatisDao
public interface PageManagerDao {
	
	//0.获取模板结构树
	
	public List<Map<String,Object>> getTemplateTree(Map<String,Object> params) throws Exception;
	
	public List<Map<String,Object>> getTemplateById(Map<String,Object> params) throws Exception;
	
	
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
	
	//获取纬表查询的sql
	public String getDimLinkSql(Map<String,Object> params) throws Exception;
	
	//获取纬表查询的数据
	public  List<Map<String,Object>> getDimLinkForTree(Map<String,Object> params) throws Exception;
	//根据模板iD查询模板对应的表
	public String getTabNameByTempId(Map<String,Object> params) throws Exception;
	
	//取button配置信息
	public List<Map<String,Object>> getButtonByTmplId(Map<String,Object> params) throws Exception;
	
	//获取模板对应的所有维度代码的关联sql
	public List<Map<String,Object>> getMetaLinkJoinSql(Map<String,Object> params) throws Exception;
	
	//导出模板
	public List<Map<String,Object>> getMetaInfoForExp(Map<String,Object> params) throws Exception;
	
	/**
	 * 查询模版关联的元数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getTemplateMetaInfo(@Param("templateId") String templateId) throws Exception;
	
	/**
	 * 查询模版关联的隐藏字段
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getTemplateMetaHideInfo(@Param("templateId") String templateId) throws Exception;

	/**
	 * 取工具条按钮
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getTBA(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getFDL(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getFML(Map<String, Object> params);

	/**
	 * 取表单按钮
	 * @param params
	 * @return 
	 */
	public List<Map<String, Object>> getFRM(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getcolBizTypes(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getChildTempl(Map<String, Object> params);
	
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
	public List<Map<String, Object>> getSheetListById(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getExcelCol(Map<String, Object> params);

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
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getPAG(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getFDLByTmplId(Map<String, Object> params);
	
	/**
	 * 取隐藏域默认值
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getHiddenDefaultValue(Map<String, Object> params);
	
	/**
	 * 查询子模版
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getSubTemplateById(Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getSheetMappingTab(
			Map<String, Object> params);

	/**
	 * @param eleMap
	 * @return
	 */
	public List<Map<String, Object>> getColRelaField(Map<String, Object> eleMap);

	/**
	 * @param eleMap
	 * @return
	 */
	public List<Map<String, Object>> getPrtColRelaField(Map<String, Object> eleMap);
	
	/**
	 * 获取提交的账户信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getSubmitAccts(Map<String,Object> params) throws Exception;
	
	/**
	 * 更新状态
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateStatus(Map<String,Object> params) throws Exception;
	
	/**
	 * 取字段类型
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getFieldType(Map<String,Object> params) throws Exception;

}
