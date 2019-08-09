package com.shuhao.clean.apps.sys.service.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.shuhao.clean.apps.sys.dao.DimTableDAO;
import com.shuhao.clean.apps.sys.entity.DBTable;
import com.shuhao.clean.apps.sys.entity.DBTableColumn;
import com.shuhao.clean.apps.sys.entity.DimTable;
import com.shuhao.clean.apps.sys.service.IDimTableService;
import com.shuhao.clean.apps.sys.util.DataHandle;
import com.shuhao.clean.base.BaseJdbcService;
import com.shuhao.clean.utils.ExcelImporter;
import com.shuhao.clean.utils.PageResult;

@Service(value="dimTableService")
public class DimTableServiceImpl extends BaseJdbcService implements IDimTableService{
	
	@Autowired
	private DimTableDAO dimTableDao ; 
	
	//维表的维表相关
	public List<Map<String, Object>> findDimInfo(String tableCode) throws Exception{
		return toLowerMapList(dimTableDao.findDimInfo(tableCode));
	}
	
	public PageResult<Map<String,Object>> listDimInfoPage(Map<String, Object> params) throws Exception{
		int total = dimTableDao.listDimInfoPageCount(params);
		List<Map<String,Object>> results = toLowerMapList(dimTableDao.listDimInfoPage(params));
		return new PageResult<Map<String,Object>>(total,results);
	}
	
	public void addDimInfo(DimTable dimTable) throws Exception{
		//增加校验
		String tableName = dimTable.getTable_name();
		DBTable table = this.getTableMeta(tableName);
		dimTableDao.addDimInfo(dimTable);
//		if(isSamePk(dimTable, table)){
//		}
	}
	
	public void updateDimInfo(DimTable dimTable) throws Exception{
		//增加校验
		String tableName = dimTable.getTable_name();
		DBTable table = this.getTableMeta(tableName);
		dimTableDao.updateDimInfo(dimTable);

//		if(isSamePk(dimTable, table)){
//		}
		
	}
	
	/**
	 * 比较前端配置与数据库中主键是否一致
	 * @param dimTable
	 * @param table
	 * @return
	 */
	private boolean isSamePk(DimTable dimTable,DBTable table) throws Exception{
		
		if(table.getPk() == null){
			throw new Exception("该表不存在主键信息");
		}
		String dimPk = dimTable.getPk_name();
		String[] dimPks = dimPk.split(",");
		List<String> dimPkList = new ArrayList<String>();
		for(int i = 0;i<dimPks.length;i++){
			dimPkList.add(dimPks[i]);
		}
		
		List<String> tablePkList = table.getPk(); 
		if(dimPkList.size() != tablePkList.size()){
			throw new Exception("主键信息有误");
		}
		
		Collections.sort(dimPkList);
		Collections.sort(tablePkList);	
		
		for(int i = 0;i<dimPkList.size();i++){
			if(dimPkList.get(i).equalsIgnoreCase(tablePkList.get(i))){
				continue;
			}else{
				throw new Exception("主键信息有误");
			}
		}
		return true;
	}
	
	public void deleteDimInfo(String tableCode) throws Exception{
		dimTableDao.deleteDimInfo(tableCode);
	}
	
	public PageResult<Map<String,Object>> queryDimTableData(Map<String, Object> params) throws Exception{
		int total = dimTableDao.queryDimTableDataCount(params);
		List<Map<String,Object>> results = toLowerMapList(dimTableDao.queryDimTableData(params));
		return new PageResult<Map<String,Object>>(total,results);
	}
	
	public void addDimTableData(Map<String, Object> params) throws Exception{
		Object table_name=params.get("table_name");
		params.remove("table_name");
		params.remove("is_tree");
		params.remove("tabke_pk");
		//列名称集合
		StringBuffer colsNames=new StringBuffer();
		//列值集合
		StringBuffer colsValues=new StringBuffer();
		for(String key:params.keySet()){
			Object val=DataHandle.toSqlParam(params.get(key));
			colsNames.append(key+",");
			colsValues.append(val+",");
		}
		String colsNamesStr=colsNames.substring(0, colsNames.length()-1);
		String colsValuesStr=colsValues.substring(0, colsValues.length()-1);
		Map<String,Object> addMap=new HashMap<String,Object>();
		addMap.put("col_name", colsNamesStr);
		addMap.put("col_value", colsValuesStr);
		addMap.put("table_name", table_name);
		dimTableDao.addDimTableData(addMap);
	}
	
	public void editDimTableData(Map<String, Object> params) throws Exception{
		//表名
		Object table_name=params.get("table_name");
		//主键列名
		Object pk_name=params.get("tabke_pk");
		//主键列值
		Object pk_code=params.get("pk_code");
		params.remove("table_name");
		params.remove("pk_code");
		params.remove("tabke_pk");
		params.remove("is_tree");
		String[] pkIdArray=String.valueOf(pk_name).split(",");
		String[] pkCodeArray=String.valueOf(pk_code).split(",");
		StringBuffer pk_condition_buffer=new StringBuffer();
		for(int i=0;i<pkIdArray.length;i++){
			String pk=pkIdArray[i];
			Object val=DataHandle.toSqlParam(pkCodeArray[i]);
			pk_condition_buffer.append(" and "+pk+"="+val+"");
		}
		//列名称-值集合
	   StringBuffer cols_name_value=new StringBuffer();
		for(String key:params.keySet()){
			Object val=DataHandle.toSqlParam(params.get(key));;
			cols_name_value.append(key+"="+val+",");
		}
		String cols_name_value_str=cols_name_value.substring(0, cols_name_value.length()-1);
		Map<String,Object> editMap=new HashMap<String,Object>();
		editMap.put("col_name_value", cols_name_value_str);
		editMap.put("table_name", table_name);
		editMap.put("pk_condition", pk_condition_buffer);
		dimTableDao.editDimTableData(editMap);
	}
	public void deleteDimTableData(Map<String, Object> params) throws Exception{
		//表名
		Object table_name=params.get("table_name");
		//主键列名
		Object pk_name=params.get("table_pk");
		//主键列值
		Object pk_code=params.get("pk_code");
		//父ID字段
		Object is_tree=String.valueOf(params.get("is_tree"));
		if(is_tree.equals("Y")){
			delTreeParam(params);
		}else{
			String[] pkIdArray=String.valueOf(pk_name).split(",");
			String[] pkCodeArray=String.valueOf(pk_code).split(",");
			StringBuffer pk_condition_buffer=new StringBuffer();
			for(int i=0;i<pkIdArray.length;i++){
				String pk=pkIdArray[i];
				Object val=DataHandle.toSqlParam(pkCodeArray[i]);
				pk_condition_buffer.append(""+pk+"="+val+" and ");
			}
			String pk_condition=pk_condition_buffer.substring(0, pk_condition_buffer.lastIndexOf("and"));
			pk_condition=" and("+pk_condition+")";
			Map<String,Object> delMap=new HashMap<String,Object>();
			delMap.put("table_name", table_name);
			delMap.put("pk_condition", pk_condition);
			dimTableDao.deleteDimTableData(delMap);
		}
	}
	public void batchDeleteDimTableData(Map<String, Object> params) throws Exception{
		//表名
		Object table_name=params.get("table_name");
		//主键列名
		Object pk_name=params.get("table_pk");
		//主键列值
		Object pk_code=params.get("pk_code");
		//父ID字段
		Object is_tree=String.valueOf(params.get("is_tree"));
		if(is_tree.equals("Y")){
			delTreeParam(params);
		}else{
			String[] pkIdArray=String.valueOf(pk_name).split(";");
			String[] pkCodeArray=String.valueOf(pk_code).split(";");
			for(int i=0;i<pkCodeArray.length;i++){
				StringBuffer pk_condition_buffer=new StringBuffer();
				String[] pkGroup=pkIdArray[0].split(",");
				Object[] valGroup=pkCodeArray[i].split(",");
				for(int j=0;j<pkGroup.length;j++){
					String pkName=pkGroup[j];
					Object val=DataHandle.toSqlParam(valGroup[j]);
					pk_condition_buffer.append(""+pkName+"="+val+" and ");
				}
				String pk_condition=pk_condition_buffer.substring(0, pk_condition_buffer.lastIndexOf("and"));
				pk_condition=" and("+pk_condition+")";
				Map<String,Object> delMap=new HashMap<String,Object>();
				delMap.put("table_name", table_name);
				delMap.put("pk_condition", pk_condition);
				dimTableDao.deleteDimTableData(delMap);
			}
		}
 	}
	
	public void saveDimTableDataForImp(Map<String, Object> params) throws Exception{

		ExcelImporter imp = null;  
        
		String[][] dataArray = null;//存放excel数据
    	String fileName = String.valueOf(params.get("file_name"));
    	imp = new ExcelImporter(fileName);
        dataArray = imp.getAppointSheetData();
        
        String fields_name = String.valueOf(params.get("fields_name"));
        String[] fields = fields_name.split(";");
        //封装字段参数
        StringBuffer col_name = new StringBuffer();
        String[] field = null;
        for(int i = 0;i<fields.length;i++){
        	field = fields[i].split(",");
        	col_name.append(field[0]).append(",");
        }
        col_name = col_name.deleteCharAt(col_name.length()-1);
        params.put("col_name",col_name);
        
        //获取字段对应的值
        List<String> col_values = this.getColValParams(dataArray, fields);
        //插入数据
        for(int i = 0;i<col_values.size();i++){
        	params.put("col_value",col_values.get(i));
        	dimTableDao.addDimTableData(params);
        }
        
	}
	
	private List<String> getColValParams(String[][] dataArray,String[] fields){
		List<String> resultList = new ArrayList<String>();
		int len = fields.length;
		
		String[] data = null;
		String colType = "";
		
		for (int i = 0; i < dataArray.length; i++) {
			String resultStr = "";
			data = dataArray[i];
			for(int j = 0;j<len;j++){
				//空值设置 在没有读取到的值进行设置
				if(data.length<j+1){
					resultStr = resultStr +"'',";
					continue;
				}
				
				colType = fields[j].split(",")[2];
				if("DATE".equalsIgnoreCase(colType)){
					resultStr = resultStr + "str_to_date('"+data[j]+"','%Y-%m-%d'),";
				}else{
					resultStr = resultStr +"'"+data[j]+"',";
				}
			}
			resultStr = resultStr.substring(0, resultStr.lastIndexOf(","));
			resultList.add(resultStr);
		}
		
		return resultList;
	}
	
	public DBTable getTableMeta(String tableName) throws Exception{
		Connection conn = null;
		ResultSet rs = null;
		DBTable table = null;
		try {
			conn = this.jdbcTemplate.getDataSource().getConnection();
			//主键
			DatabaseMetaData dbmd =  conn.getMetaData();
			//oracle 表名默认保存为大写 
			ResultSet pkRs = dbmd.getPrimaryKeys(null, null, tableName.toUpperCase());
			List<String> pkList = new ArrayList<String>();
			
			while(pkRs.next()){
				String pk = pkRs.getObject(4).toString().toLowerCase();
				if(pkList.contains(pk)){//去重
					continue;
				}
				pkList.add(pk);
			}
			
			//表结构
			String sql = "select * from " + tableName +" where 1=2 ";
			Statement stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int colCount =  rsmd.getColumnCount();
			table = new DBTable();
			DBTableColumn col = null;
			List<DBTableColumn> colList = new ArrayList<DBTableColumn>();
			for (int i = 1; i <= colCount; i++) {
				col = new DBTableColumn();
				col.setColumnName(rsmd.getColumnName(i).toLowerCase());
				col.setColumnType(rsmd.getColumnTypeName(i));
				col.setColumnLength(String.valueOf(rsmd.getColumnDisplaySize(i)));
				col.setColumnIsNullable(rsmd.isNullable(i)==1?true:false);
				col.setIsPK(pkList.contains(rsmd.getColumnName(i).toLowerCase()));
				colList.add(col);
			}
			table.setColumns(colList);
			if(pkList.size() != 0){
				table.setPk(pkList);
			}
			table.setTableCode(tableName);
			table.setTableName(tableName);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
			} catch (Exception e) {
			}
			try {
				if(conn != null){
					conn.close();
				}
			} catch (Exception e) {
			} 
		}
		return table;
	}

	public PageResult<Map<String, Object>> findDimTable(
			Map<String, Object> params) throws Exception {
		List<Map<String,Object>> results = toLowerMapList(dimTableDao.findDimTable(params));
		return new PageResult<Map<String,Object>>(results.size(),results);
	}
	
	public List<Map<String,Object>> findDimTableEditCols(Map<String, Object> params) throws Exception{
		//表名
				Object table_name=params.get("table_name");
				//主键列名
				Object pk_name=params.get("tabke_pk");
				//主键列值
				Object pk_code=params.get("pk_code");
				String[] pkIdArray=String.valueOf(pk_name).split(",");
				String[] pkCodeArray=String.valueOf(pk_code).split(",");
				StringBuffer pk_condition_buffer=new StringBuffer();
				for(int i=0;i<pkIdArray.length;i++){
					String pk=pkIdArray[i];
					Object val=DataHandle.toSqlParam(pkCodeArray[i]);
					pk_condition_buffer.append(" and "+pk+"="+val+"");
				}
				Map<String,Object> findMap=new HashMap<String,Object>();
				findMap.put("table_name", table_name);
				findMap.put("pk_condition", pk_condition_buffer.toString());
				//主键得到唯一记录
				List<Map<String,Object>> results = toLowerMapList(dimTableDao.findDimTable(findMap));
                return results;
	}
	  /**
	   * 递归删除树
	   * @Description: TODO
	   * @param @param delMap 删除条件map
	   * @param @throws Exception   
	   * @return void  
	   * @throws
	   */
	    public  void delTreeParam(Map<String,Object> delMap) throws Exception {
	    	String pk_condition="";
	    	Object pk_name=delMap.get("table_pk");
	    	Object prt_col_name=delMap.get("prt_col_name");
	    	String val=DataHandle.toSqlParam(delMap.get("pk_code"));
	    	if(delMap.get("pk_condition")==null||delMap.get("pk_condition")==""){
				pk_condition=" and("+prt_col_name+"="+val+")";
				delMap.put("pk_condition", pk_condition);
	    	}
	    	List<Map<String,Object>> childNodeList= toLowerMapList(dimTableDao.findDimTable(delMap));
			if(!childNodeList.isEmpty()&&childNodeList.size()>0){
				for(Map m:childNodeList){
					String childVal=DataHandle.toSqlParam(m.get(pk_name));
					pk_condition=" and("+prt_col_name+"="+childVal+")";
					Map<String,Object> childMap=delMap;
					childMap.put("pk_code",childVal);
					childMap.put("pk_condition",pk_condition);//构造查询condition
					delTreeParam(childMap);
				}
				pk_condition=" and("+pk_name+"="+val+")";
				delMap.put("pk_condition", pk_condition);
				dimTableDao.deleteDimTableData(delMap);
			}else{
				pk_condition=" and("+pk_name+"="+val+")";
				delMap.put("pk_condition", pk_condition);
				dimTableDao.deleteDimTableData(delMap);
			}
	    }
	
	}
