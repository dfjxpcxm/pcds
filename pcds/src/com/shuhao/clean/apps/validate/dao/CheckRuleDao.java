
package com.shuhao.clean.apps.validate.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.validate.entity.MetadataCheckRule;
import com.shuhao.clean.apps.validate.entity.UppCheckRule;
/**
 * 
 * @Description:   TODO
 * 
 * @author:        Ning
 */
@MyBatisDao
public interface CheckRuleDao {
	

	/**
	 * 添加校验规则
	 * @param checkRule
	 * @throws Exception
	 */
	void addCheckRule(UppCheckRule checkRule) throws Exception;
	
	/**
	 * 修改校验规则  
	 * @param checkRule
	 * @throws Exception
	 */
	void updateCheckRule(UppCheckRule checkRule) throws Exception;
	
	/**
	 * 删除校验规则
	 * @param checkRule
	 * @throws Exception
	 */
	void deleteCheckRule(UppCheckRule checkRule) throws Exception;
	
	/**
	 * 分页查询校验规则
	 * @param map
	 * @return
	 */
	List<UppCheckRule> listCheckRulePage(Map<String, Object> map);
	
	/**
	 * 查询校验规则总数
	 * @param map
	 * @return
	 */
	int listCheckRuleTotal(Map<String, Object> map);
	/**
	 * 查询校验类型
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listCheckType(Map<String, Object> map);

	/**查询元数据配置的校验规则
	 * @param map 
	 * @return
	 */
	List<UppCheckRule> listMetadataCheckRulePage(Map<String, Object> map);
	
	/**查询元数据配置的校验规则总数
	 * @param map 
	 * @return
	 */
	int listMetadataCheckRuleTotal(Map<String, Object> map);
	
	/**查询元数据
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getMetadata(Map<String, Object> map);
	
	/**
	 * @param metadataCheckRule
	 */
	void addMetaRule(MetadataCheckRule metadataCheckRule);

	/**
	 * @param metadataCheckRule
	 */
	void updateMetaRule(MetadataCheckRule metadataCheckRule);

	/**是年初配置校验规则
	 * @param metadataCheckRule
	 */
	void deleteMetaRule(MetadataCheckRule metadataCheckRule);

	/**
	 * 查询表字段
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listColumns(Map<String, Object> map);
	
	/**
	 * 查询页面字段列表
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listPageFields(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listTargetTab(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<UppCheckRule> listCheckRuleLibPage(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	int listCheckRuleLibTotal(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listCheckMethod(Map<String, Object> map);

}
