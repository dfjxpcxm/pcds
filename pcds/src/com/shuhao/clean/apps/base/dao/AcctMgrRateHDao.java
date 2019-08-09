package com.shuhao.clean.apps.base.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;

/**
 * 账户分配Dao
 * @author bixb
 *
 */
@MyBatisDao
public interface AcctMgrRateHDao {
	
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
	public void addAcctMgrRateH(List<Map<String, Object>> list)throws Exception;
	
	/**
	 * 删除账户分配关系
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteAcctMgrRateH(Map<String, Object> paramMap)throws Exception;
	
}
