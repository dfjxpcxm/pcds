package com.shuhao.clean.apps.base.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;

/**
 * 客户经理Dao
 * @author bixb
 *
 */
@MyBatisDao
public interface CustMgrDao {
	
	/**
	 * 查询业务条线
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryCustMgrList(Map<String, Object> params)throws Exception;
	
	/***
	 * 客户经理列表总数
	 * @param params
	 * @return
	 */
	public int queryCustMgrListTotalCount(Map<String, Object> params);
}
