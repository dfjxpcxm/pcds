package com.shuhao.clean.apps.sys.dao;

import java.util.List;
import java.util.Map;
import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.sys.entity.DimTable;

@MyBatisDao
public interface DimTableDAO  {
	
	
	/**
	 * 按ID 查询记录
	 * @param dim_code
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findDimInfo(String dim_code) throws Exception;
	
	/**
	 * 分页查询结果
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> listDimInfoPage(Map<String, Object> params) throws Exception;
	
	/**
	 * 返回分页查询总数
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int listDimInfoPageCount(Map<String, Object> params) throws Exception;
	
	public void addDimInfo(DimTable dimTable) throws Exception;
	
	public void updateDimInfo(DimTable dimTable) throws Exception;
	
	public void deleteDimInfo(String tableCode) throws Exception;
	
	/**
	 * 查询表数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryDimTableData(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询表数据对应总数
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int queryDimTableDataCount(Map<String, Object> params) throws Exception;
	
	/**
	 * 新增维表数据
	 * @param dimTable
	 * @throws Exception
	 */
	public void addDimTableData(Map<String, Object> params) throws Exception;
	
	/**
	 * 修改维表数据
	 * @param dimTable
	 * @throws Exception
	 */
	public void editDimTableData(Map<String, Object> params) throws Exception;
	
	/**
	 * 删除维表数据
	 * @param dimTable
	 * @throws Exception
	 */
	public void deleteDimTableData(Map<String, Object> params) throws Exception;
	//批量删除维表数据
	public void batchDeleteDimTableData(Map<String, Object> params) throws Exception;
     /*
      * 条件查询维度明细表
      */
	public List<Map<String, Object>> findDimTable(Map<String, Object> params) throws Exception;
	   /*
     * 得到维度明细表,编辑列内容
     */
	public List<Map<String,Object>> findDimTableEditCols(Map<String, Object> params) throws Exception;
	
}
