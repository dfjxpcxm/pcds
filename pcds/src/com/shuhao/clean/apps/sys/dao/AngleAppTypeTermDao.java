package com.shuhao.clean.apps.sys.dao;

import java.util.List;
import java.util.Map;
import com.shuhao.clean.annotation.MyBatisDao;

/**
 * 应用类型期限DAO
 * 应用类型表、  应用类型期限关联关系表、期限表
 * @author bixb
 *
 */
@MyBatisDao
public interface AngleAppTypeTermDao  {
	
	/**
	 * 查询应用类型
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAppType(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询应用类型对应总数
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int queryAppTypeCount(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询应用类型对应期限
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAppTypeTerm(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询期限
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryTerms(Map<String, Object> params) throws Exception;
	
	/**
	 * 维护应用类型期限
	 * @param dimTable
	 * @throws Exception
	 */
	public void addAppTypeTerm(List<Map<String, Object>> list ) throws Exception;
	
	/**
	 * 删除应用类型期限
	 * @param dimTable
	 * @throws Exception
	 */
	public void deleteAppTypeTerm(Map<String, Object> params) throws Exception;
	
	
}
