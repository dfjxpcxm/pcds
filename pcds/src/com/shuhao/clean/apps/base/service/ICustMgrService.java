package com.shuhao.clean.apps.base.service;

import java.util.Map;

import com.shuhao.clean.utils.PageResult;

/**
 * 客户经理Service接口
 * @author bixb
 *
 */
public interface ICustMgrService {
	
	/**
	 * 客户经理列表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public PageResult<Map<String, Object>> queryCustMgrList(Map<String, Object> paramMap)throws Exception;
	
	
}
