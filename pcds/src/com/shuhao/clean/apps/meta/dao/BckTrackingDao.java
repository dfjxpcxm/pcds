package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shuhao.clean.annotation.MyBatisDao;

@MyBatisDao
public interface BckTrackingDao {
	
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
	
	//获取纬表查询的sql
	public String getDimLinkSql(Map<String,Object> params) throws Exception;
	
	//获取纬表查询的数据
	public  List<Map<String,Object>> getDimLinkForTree(Map<String,Object> params) throws Exception;

	//根据模板iD查询模板对应的表
	public String getTabNameByTempId(Map<String,Object> params) throws Exception;

	//根据模板iD查询模板对应的表
	public Map getTabMapByTempId(Map<String,Object> params) throws Exception;
	
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
}
