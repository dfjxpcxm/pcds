package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppDicFnRela;

/**
 * 表管理类接口
 */
@MyBatisDao
public interface TableManageDao
{
	public List<Map<String,Object>> getDataBaseList()throws Exception;
	
	public List<Map<String,Object>> getOwnerList(Map<String, Object> paramMap)throws Exception;
	
	public List<Map<String,Object>> getLocalTableList(Map<String, Object> paramMap)throws Exception;
	
	public int getLocalTableCount(Map<String, Object> paramMap)throws Exception;
	
	public List<Map<String, Object>> getLocalTableInfo(Map<String, Object> paramMap) throws Exception;
	
	public List<Map<String, Object>> getThemeList()throws Exception;
	
	public List<Map<String, Object>> getConTableTypeList()throws Exception;
	
	public List<Map<String, Object>> getDataTypeList()throws Exception;
	
	public List<Map<String, Object>> getTableSpaceList(Map<String, Object> paramMap)throws Exception;
	
	public void addTable(Map<String, Object> paramMap)throws Exception;

	/**
	 * 配置时修改表信息
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateTableInfo(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 删除表字段
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteTableField(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 插入表字段
	 * @param paramMap
	 * @throws Exception
	 */
	public void addTableField(Map<String, Object> paramMap)throws Exception;
	
	public void deleteTable(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 增加功能与业务之间关系
	 * @param paramMap
	 * @throws Exception
	 */
	public void addDicFnRela(UppDicFnRela dicFn)throws Exception;
	
	/**
	 * 删除功能与业务之间关系
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteDicFnRela(UppDicFnRela dicFn)throws Exception;
	
	/**
	 * 查询功能页面元数据id
	 * @param paramMap
	 * @throws Exception
	 */
	public List<Map<String, Object>> getFnMetaId(Map<String, Object> paramMap)throws Exception;
	
	public void deleteField(Map<String, Object> paramMap)throws Exception;

	public void updateField(Map<String, Object> paramMap)throws Exception;
	
	public int getTableDataCount(Map<String, Object> paramMap)  throws Exception ;

	/**
	 * 删除表及关联信息
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteTableGlInfo(Map<String, Object> paramMap) throws Exception;
}
