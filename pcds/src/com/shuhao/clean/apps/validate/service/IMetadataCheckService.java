
package com.shuhao.clean.apps.validate.service;

import java.util.List;

import com.shuhao.clean.apps.validate.entity.MetadataCheckRule;
/**
 * 
 * @Description   元数据规则查询
 * 
 * @author        gongzhiyang
 */
public interface IMetadataCheckService {

	/**
	 * 按ID查询校验规则
	 * @param params
	 * @return
	 */
	public List<MetadataCheckRule> getMetadataCheckRule(String metadataId);
	
	/**
	 * 按模版查询校验规则 
	 * @param templateId
	 * @return
	 */
	public List<MetadataCheckRule> getMetadataCheckRules(String templateId);
	
}
