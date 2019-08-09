package com.shuhao.clean.apps.base.service;

import java.util.List;
import java.util.Map;

/**
 * 账户分配Service接口
 * @author bixb
 *
 */
public interface IAcctMgrRateHService {
	
	/**
	 * 账户分配关系列表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAcctMgrRateH(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 新增账户分配关系
	 * @param paramMap
	 * @throws Exception
	 */
	public void saveBatchAcctMgrRateH(Map<String, Object> paramMap)throws Exception;
	
	
}
