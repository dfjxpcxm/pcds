/**
 * FileName:     JdbcRuleLoaderDao.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-28 上午9:21:11 
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
import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.entity.UppCheckRule;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
@MyBatisDao(value = "checkRuleUtilsDao")
public interface CheckRuleUtilsDao {
	
	/**
	 * 查询元数据ID取校验规则
	 * @param metaId
	 * @return
	 */
	public List<CheckRule> getCheckRuleByMetaId(String metaId);
	
	/**
	 * 按元数据属性获取校验规则,可以传入参数
	 * @param param
	 * @return
	 */
	public List<CheckRule> getCheckRuleByProps(Map<String, Object> param);
	
	/**
	 * 查询元数据规则列表
	 * @param metaId
	 * @return
	 */
	public List<CheckRule> getCheckRuleByMetaIds(@Param("metaIds") String metaIds);
	
	/**
	 * 查询所有通用规则
	 * @return
	 * @throws Exception
	 */
	public List<UppCheckRule> getAllCheckRules();
	
}
