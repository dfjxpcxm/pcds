/**
 * FileName:     QueryDsService.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-6-15 下午2:16:11 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-6-15       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.query.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.query.entity.QueryDs;
import com.shuhao.clean.apps.query.entity.QueryDsMeta;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.utils.PageResult;
import com.shuhao.clean.utils.SheetConfig;
import com.shuhao.clean.utils.exttree.ExtTreeNode;

/**
 * @Description:   查询数据源相关service
 * 
 * @author:         gongzhiyang
 */
public interface QueryDsService {

	public QueryDs findDsById(QueryDs ds)throws Exception;
	
	public QueryDs getDsInfo(QueryDs ds)throws Exception;
	
	public void addDs(QueryDs ds)throws Exception;
	
	public void deleteDs(QueryDs ds)throws Exception;
	
	public void updateDs(QueryDs ds)throws Exception;
	
	public List<QueryDsMeta> checkDsSql(String ds)throws Exception;
	
	public void editDsAndMeta(Map<String, Object> dsMetas)throws Exception;
	
	public ExtTreeNode getDsTree(QueryDs ds)throws Exception;
	
	/**
	 * 动态显示分类
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> listByType(Map<String, Object> param)throws Exception;
	
	public PageResult<Map<String, Object>> getUserList(Map<String, Object> param)throws Exception;
	
	public void addUserDs(Map<String, Object> param)throws Exception;
	
	public void deleteUserDs(Map<String, Object> param)throws Exception;
	
	/**
	 * 获取用户配置的数据源
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ExtTreeNode getUserDsTree(String userId) throws Exception ;
	
	/**
	 * 生成页面
	 * @param dsId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String getPageView(String dsId,SysUserInfo user) throws Exception;
	
	/**
	 * 执行查询
	 * @param dsId
	 * @param requestParam
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public PageResult<Map<String,Object>> queryDataForList(String dsId,Map<String, Object> requestParam,SysUserInfo user,boolean isExport) throws Exception;

	/**
	 * 生成导出配置信息
	 * @param dsId
	 * @param requestParam
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public SheetConfig getExportConfig(String dsId,Map<String, Object> requestParam,SysUserInfo user)throws Exception;

}
