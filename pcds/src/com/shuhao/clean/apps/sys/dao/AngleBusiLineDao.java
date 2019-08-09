package com.shuhao.clean.apps.sys.dao;

import java.util.List;
import java.util.Map;
import com.shuhao.clean.annotation.MyBatisDao;

/**
 * 业务条线DAO
 * 业务条线表、  业务条线关联关系表、产品表
 * @author bixb
 *
 */
@MyBatisDao
public interface AngleBusiLineDao  {
	
	/**
	 * 查询业务条线
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBusiLine(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询业务条线对应总数
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int queryBusiLineCount(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询业务条线对应产品
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBusiLineProduct(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询产品
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryProduct(Map<String, Object> params) throws Exception;
	
	/**
	 * 维护业务条线产品
	 * @param dimTable
	 * @throws Exception
	 */
	public void addBusiLineProduct(List<Map<String, Object>> list ) throws Exception;
	
	/**
	 * 删除业务条线产品
	 * @param dimTable
	 * @throws Exception
	 */
	public void deleteBusiLineProduct(Map<String, Object> params) throws Exception;
	
	/**
	 * 新增业务条线
	 * @param dimTable
	 * @throws Exception
	 */
	public void addBusiLine(Map<String, Object> params) throws Exception;
	
	/**
	 * 修改业务条线
	 * @param dimTable
	 * @throws Exception
	 */
	public void updateBusiLine(Map<String, Object> params) throws Exception;
	
	/**
	 * 删除业务条线
	 * @param dimTable
	 * @throws Exception 
	 */
	public void deleteBusiLine(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询业务条线
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBusiLineByCode(Map<String, Object> params) throws Exception;
	
}
