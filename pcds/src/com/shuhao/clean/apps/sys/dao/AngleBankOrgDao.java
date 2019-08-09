package com.shuhao.clean.apps.sys.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.base.entity.DmdBankInterOrg;

@MyBatisDao
public interface AngleBankOrgDao  {
	
	
	/**
	 * 查询指标
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBankOrgById(Map<String, Object> params) throws Exception;
	
	/**
	 * 新增指标
	 * @param BankOrg
	 * @throws Exception
	 */
	public void addBankOrg(DmdBankInterOrg measure) throws Exception;
	
	/**
	 * 修改指标
	 * @param BankOrg
	 * @throws Exception
	 */
	public void editBankOrg(DmdBankInterOrg measure) throws Exception;
	
	/**
	 * 删除指标
	 * @param BankOrg
	 * @throws Exception
	 */
	public void deleteBankOrg(Map<String, Object> params) throws Exception;
	
	
}
