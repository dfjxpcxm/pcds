package com.shuhao.clean.apps.sys.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.sys.entity.DBTable;
import com.shuhao.clean.apps.sys.entity.DimTable;
import com.shuhao.clean.utils.PageResult;

public interface IDimTableService {

	public List<Map<String, Object>> findDimInfo(String tableCode) throws Exception;
	
	public PageResult<Map<String,Object>> listDimInfoPage(Map<String, Object> params) throws Exception;
	
	public void addDimInfo(DimTable dimTable) throws Exception;
	
	public void updateDimInfo(DimTable dimTable) throws Exception;
	
	public void deleteDimInfo(String tableCode) throws Exception; 
	
	/**
	 * 查询维表数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public PageResult<Map<String,Object>> queryDimTableData(Map<String, Object> params) throws Exception;
	
	/**
	 * 新增维表数据
	 * @param params
	 * @throws Exception
	 */
	public void addDimTableData(Map<String, Object> params) throws Exception;
	
	/**
	 * 修改维表数据
	 * @param params
	 * @throws Exception
	 */
	public void editDimTableData(Map<String, Object> params) throws Exception;
	
	/**
	 * 单条删除维表数据
	 * @param params
	 * @throws Exception
	 */
	public void deleteDimTableData(Map<String, Object> params) throws Exception;
	
	/**
	 * 批量删除维表数据
	 * @param params
	 * @throws Exception
	 */
	public void batchDeleteDimTableData(Map<String, Object> params) throws Exception;
	/**
	 * 导入维表数据
	 * @param params
	 * @throws Exception
	 */
	public void saveDimTableDataForImp(Map<String, Object> params) throws Exception;
	
	/**
	 * 根据表名查询表信息
	 * 查询表主键、字段列表信息
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public DBTable getTableMeta(String tableName) throws Exception;
	/**
	 * 条件查询维度明细表
	 * @Description: TODO
	 * @param @param params
	 * @param @return
	 */
	public PageResult<Map<String,Object>> findDimTable(Map<String, Object> params) throws Exception;
    /**
     * 得到编辑列内容
     * @Description: TODO
     * @param @param params
     */
	public List<Map<String,Object>> findDimTableEditCols(Map<String, Object> params) throws Exception;

}
