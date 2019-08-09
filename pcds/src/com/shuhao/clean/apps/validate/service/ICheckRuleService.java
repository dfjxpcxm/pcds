package com.shuhao.clean.apps.validate.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppTheme;
import com.shuhao.clean.apps.validate.entity.MetadataCheckRule;
import com.shuhao.clean.apps.validate.entity.UppCheckRule;
import com.shuhao.clean.utils.PageResult;

/**
 * 校验规则维护
 * @author Ning
 *
 */
public interface ICheckRuleService {

	/**
	 * 添加校验规则
	 * @param theme
	 * @param userInfo 
	 * @throws Exception
	 */
	String addCheckRule(UppCheckRule checkRule) throws Exception;

	/**
	 * 删除主题
	 * @param theme_id
	 * @throws Exception
	 */
	void deleteCheckRule(UppCheckRule checkRule) throws Exception;


	/**
	 * 修改主题
	 * @param param
	 * @throws Exception
	 */
	void updateCheckRule(UppCheckRule checkRule) throws Exception;
	
	/**
	 * 查询校验规则列表
	 * @param map
	 * @return
	 */
	PageResult<UppCheckRule> listCheckRule(Map<String, Object> map);
	/**
	 * 查询校验类型
	 * @param map
	 * @return
	 */
	PageResult<Map<String, Object>> listCheckType(Map<String, Object> map);
	
	/**查询元数据配置的校验规则
	 * @param map
	 * @return
	 */
	PageResult<UppCheckRule> listMetadataCheckRule(Map<String, Object> map);

	/**查询元数据
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getMetadata(Map<String, Object> map);

	/**配置元数据校验规则
	 * @param metadataCheckRule
	 */
	void addMetaRule(MetadataCheckRule metadataCheckRule);

	/**配置元数据校验规则
	 * @param metadataCheckRule
	 */
	void updateMetaRule(MetadataCheckRule metadataCheckRule);

	/**删除 配置的校验规则
	 * @param metadataCheckRule
	 */
	void deleteMetaRule(MetadataCheckRule metadataCheckRule);

	/**查询表字段
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listColumns(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listTargetTab(Map<String, Object> map);

	/**查询校验规则库
	 * @param map
	 * @return
	 */
	PageResult<UppCheckRule> listCheckRuleLib(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listCheckMethod(Map<String, Object> map);



}
