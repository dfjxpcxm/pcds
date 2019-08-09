package com.shuhao.clean.apps.meta.dao;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppDicRela;

/**
 * 
 * 类描述: 元数据映射关系dao接口
 * @author chenxiangdong
 * @创建时间：2015-1-15下午03:25:37
 */
@MyBatisDao
public interface UppDicRelaDao {
	
	/**
	 * 添加元数据映射关系
	 * @param rela
	 * @throws Exception
	 */
	public void addRela(UppDicRela rela) throws Exception;
	
	
	/**
	 * 根据元数据id删除映射记录
	 * @param metadata_id
	 * @throws Exception
	 */
	public void deleteRela(String metadata_id) throws Exception;
	
	
	/**
	 * 根据数据字典id删除映射关系
	 * @param db_id
	 * @throws Exception
	 */
	public void deleteRelaByDic(String db_id) throws Exception;
	
	
	/**
	 * 根据上级字典元数据id删除关系表记录
	 * @param parent_db_id
	 * @throws Exception
	 */
	public void deleteRelaByParentDbId(String parent_db_id) throws Exception;
	
}
