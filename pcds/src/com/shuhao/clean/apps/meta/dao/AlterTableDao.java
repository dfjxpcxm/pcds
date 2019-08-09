package com.shuhao.clean.apps.meta.dao;

import java.util.List;

import com.shuhao.clean.annotation.MyBatisDao;

/**
 * 
 * 类描述: 修改物理表DAO接口
 * @author chenxiangdong
 * @创建时间：2015-1-16下午02:46:19
 */
@MyBatisDao
public interface AlterTableDao {
	
	/**
	 * 根据目标类型查询允许的上级类型
	 * @param categoryArray
	 * @return
	 * @throws Exception
	 */
	public List<String> queryAllowParentList(String[] categoryArray) throws Exception;
	
}
