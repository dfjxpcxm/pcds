package com.shuhao.clean.apps.validate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.validate.dao.CheckRuleDao;
import com.shuhao.clean.apps.validate.entity.MetadataCheckRule;
import com.shuhao.clean.apps.validate.entity.UppCheckRule;
import com.shuhao.clean.apps.validate.service.ICheckRuleService;
import com.shuhao.clean.base.BaseJdbcService;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.PageResult;

/**
 * 校验规则管理类
 * 
 * @author:        Ning
 */
@Service(value="checkRuleService")
public class CheckRuleServiceImpl  extends BaseJdbcService implements ICheckRuleService {

	@Autowired
	private CheckRuleDao checkRuleDao;

	
	public String addCheckRule(UppCheckRule checkRule) throws Exception {
		String rule_code = "rl"+GlobalUtil.getRandomID(18);
		checkRule.setChk_rule_code(rule_code);
		this.checkRuleDao.addCheckRule(checkRule);
		return rule_code;
	}

	
	public void deleteCheckRule(UppCheckRule checkRule) throws Exception {
		this.checkRuleDao.deleteCheckRule(checkRule);
	}

	
	public void updateCheckRule(UppCheckRule checkRule) throws Exception {
		this.checkRuleDao.updateCheckRule(checkRule);
	}

	
	public PageResult<UppCheckRule> listCheckRule(Map<String, Object> map) {
		PageResult<UppCheckRule> pr = new PageResult<UppCheckRule>();
		pr.setResults(this.checkRuleDao.listCheckRulePage(map));
		pr.setTotalCount(this.checkRuleDao.listCheckRuleTotal(map));
		return pr;
	}

	
	public PageResult<Map<String, Object>> listCheckType(Map<String, Object> map) {
		PageResult<Map<String, Object>> pr = new PageResult<Map<String, Object>>();
		pr.setResults(GlobalUtil.lowercaseListMapKey(this.checkRuleDao.listCheckType(map)));
		return pr;
	}

	/**
	 * 查询元数据配置的校验规则
	 */
	public PageResult<UppCheckRule> listMetadataCheckRule(Map<String, Object> map){
		PageResult<UppCheckRule> pr = new PageResult<UppCheckRule>();
		pr.setResults(this.checkRuleDao.listMetadataCheckRulePage(map));
		pr.setTotalCount(this.checkRuleDao.listMetadataCheckRuleTotal(map));
		return pr;
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckRuleService#getMetadata(java.util.Map)
	 */
	
	public List<Map<String, Object>> getMetadata(Map<String, Object> map) {
		return GlobalUtil.lowercaseListMapKey(this.checkRuleDao.getMetadata(map));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckRuleService#addMetaRule(com.shuhao.clean.apps.validate.entity.MetadataCheckRule)
	 */
	
	public void addMetaRule(MetadataCheckRule metadataCheckRule) {
		this.checkRuleDao.addMetaRule(metadataCheckRule);
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckRuleService#editMetaRule(com.shuhao.clean.apps.validate.entity.MetadataCheckRule)
	 */
	
	public void updateMetaRule(MetadataCheckRule metadataCheckRule) {
		this.checkRuleDao.updateMetaRule(metadataCheckRule);
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckRuleService#deleteMetaRule(com.shuhao.clean.apps.validate.entity.MetadataCheckRule)
	 */
	
	public void deleteMetaRule(MetadataCheckRule metadataCheckRule) {
		this.checkRuleDao.deleteMetaRule(metadataCheckRule);
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckRuleService#listColumns(java.util.Map)
	 */
	public List<Map<String, Object>> listColumns(Map<String, Object> map) {
		//按分类，查询不同的字段,页面查询字段列表
		if(map.get("md_cate_cd")!=null && String.valueOf(map.get("md_cate_cd")).equals("PAG")){
			return GlobalUtil.lowercaseListMapKey(this.checkRuleDao.listPageFields(map));
		}else{
			return GlobalUtil.lowercaseListMapKey(this.checkRuleDao.listColumns(map));
		}
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckRuleService#listTargetTab(java.util.Map)
	 */
	
	public List<Map<String, Object>> listTargetTab(Map<String, Object> map) {
		return GlobalUtil.lowercaseListMapKey(this.checkRuleDao.listTargetTab(map));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckRuleService#listCheckRuleLib(java.util.Map)
	 */
	
	public PageResult<UppCheckRule> listCheckRuleLib(Map<String, Object> map) {
		PageResult<UppCheckRule> pr = new PageResult<UppCheckRule>();
		pr.setResults(this.checkRuleDao.listCheckRuleLibPage(map));
		pr.setTotalCount(this.checkRuleDao.listCheckRuleLibTotal(map));
		return pr;
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckRuleService#listCheckMethod(java.util.Map)
	 */
	
	public List<Map<String, Object>> listCheckMethod(
			Map<String, Object> map) {
		return GlobalUtil.lowercaseListMapKey(this.checkRuleDao.listCheckMethod(map));
	}
	

}
