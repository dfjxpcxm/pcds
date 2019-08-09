package com.shuhao.clean.apps.base.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppMetadata;

/**
 * 查询条件控件Dao
 * @author chenxd
 *
 */
@MyBatisDao
public interface SelectorDao {
	
	/**
	 * 查询业务条线
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBusiLineList(Map<String, Object> params)throws Exception;
	
	/**
	 * 职位类型
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryJobTitle(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 岗位
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryPostType(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 技术职称
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryTechTitle(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 性别
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryGender(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 民族
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryEthnicity(Map<String, Object> paramMap)throws Exception;
	
	
	/**
	 * 学历 教育背景
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryEduBack(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 用户状态
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMgrStatus(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 序列
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryCustJobSeq(Map<String, Object> paramMap)throws Exception;
	
	/**
	 *  查询月份
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> listMonth()throws Exception;
	/**
	 * 指标体系树
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMeasureTree(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 机构类型
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBankOrgType(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 查询客户经理
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getCustMgrs(Map<String, Object> paramMap) throws Exception;
	public int getCustMgrsCount(Map<String, Object> paramMap) throws Exception;
	
	/***
	 * 查询数据库类型
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryUppDatabaseType(
			Map<String, Object> paramMap) throws Exception;

	/**
	 * 获取所有元数据
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> metadataList(String metadataId) throws Exception;

	/**
	 * 模板树  --根据父级节点查询子节点
	 * @param tmplId  父级节点    
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getBlmbList(String tmplId) throws Exception;
	
	/***
	 * 查询元数据按钮功能
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryUppButtonFunction(Map<String, Object> paramMap) throws Exception;
	/**
	 * 查询功能按钮列表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getUppButtonFunction(Map<String, Object> paramMap) throws Exception;

	/***
	 * 查询维度
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryUppDimSource(Map<String, Object> paramMap) throws Exception;

}
