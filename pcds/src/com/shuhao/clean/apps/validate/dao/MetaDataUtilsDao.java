/**
 * FileName:     MetaDataUtilsDao.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-28 上午10:02:53 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-28       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppMetadata;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
@MyBatisDao(value="metaDataUtilsDao")
public interface MetaDataUtilsDao {
	
	/**
	 * 获取元数据列表: metaId','metaId1','metaId2
	 * @param metaIds
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> getUppMetadatas(@Param("metaIds") String metaIds) throws Exception;
	
	/**
	 * 查询第一级下级元数据
	 * @param metaIds
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> getUppMetadataByPid(String metaId) throws Exception;
	
	/**
	 * 查询元数据 metaId
	 * @param metaId
	 * @return
	 * @throws Exception
	 */
	public UppMetadata getUppMetadata(String metaId) throws Exception;
	
	/**
	 * 获取元数据表
	 * @return
	 */
	public List<UppMetadata> getTableMeta(Map<String, Object> map);
	
	/**
	 * 查询数据
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getTableData(Map<String, Object> map);
	
	/**
	 * 查询sheet关联的表
	 * @param map
	 * @return
	 */
	public List<UppMetadata> getSheetTableMeta(Map<String, Object> map);
	
	/**
	 * 获取父表元数据
	 * @param map
	 * @return
	 */
	public List<UppMetadata> getParentTableMeta(Map<String, Object> map);
	
	/**
	 * 功能元数据
	 * @param map
	 * @return
	 */
	public List<UppMetadata> getFunctionMeta(Map<String, Object> map);
	
	/**
	 * 获取当前模版的sheet
	 * @param map
	 * @return
	 */
	public List<UppMetadata> getSheetXls(Map<String, Object> map);
	
}
