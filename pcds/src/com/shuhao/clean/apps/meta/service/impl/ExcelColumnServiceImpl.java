package com.shuhao.clean.apps.meta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.ExcelColumnDao;
import com.shuhao.clean.apps.meta.dao.UppDicRelaDao;
import com.shuhao.clean.apps.meta.entity.UppDicRela;
import com.shuhao.clean.apps.meta.entity.UppExcelColumn;
import com.shuhao.clean.apps.meta.service.IExcelColumnService;
import com.shuhao.clean.apps.meta.service.IMetadataService;

/**
 * 
 * 类描述: 元数据[Excel列]业务逻辑实现类 
 * @author chenxiangdong
 * 创建时间：2015-1-13下午05:23:08
 */
@Service
public class ExcelColumnServiceImpl implements IExcelColumnService {
	
	@Autowired
	private IMetadataService metadataService;
	
	@Autowired
	private ExcelColumnDao excelColumnDao;
	
	@Autowired
	private UppDicRelaDao dicRelaDao;
	
	/**
	 * 添加Excel列
	 * @param column
	 * @throws Exception
	 */
	public void addExcelColumn(UppExcelColumn column) throws Exception {
		this.metadataService.addMetadata(column);
		this.excelColumnDao.addExcelColumn(column);
		if(column.getTableColumn().getColumn_id() != null) {
			this.dicRelaDao.addRela(new UppDicRela(column.getXls_col_id(), column.getTableColumn().getColumn_id()));
		}
	}
	
	
	/**
	 * 根据id获取Excel列信息
	 * @param column_id
	 * @return
	 * @throws Exception
	 */
	public UppExcelColumn getExcelColumnById(String column_id) throws Exception {
		return this.excelColumnDao.getExcelColumnById(column_id);
	}
	
	
	/**
	 * 保存Excel列信息
	 * @param column
	 * @throws Exception
	 */
	public void saveExcelColumn(UppExcelColumn column) throws Exception {
		this.metadataService.saveMetadata(column);
		this.excelColumnDao.saveExcelColumn(column);
		this.dicRelaDao.deleteRela(column.getXls_col_id());
		if(column.getTableColumn().getColumn_id() != null) {
			this.dicRelaDao.addRela(new UppDicRela(column.getXls_col_id(), column.getTableColumn().getColumn_id()));
		}
	}
	
	
	/**
	 * 删除Excel列信息
	 * @param column_id
	 * @throws Exception
	 */
	public void deleteExcelColumn(String column_id) throws Exception {
		this.metadataService.deleteMetadata(column_id);
		this.excelColumnDao.deleteExcelColumn(column_id);
		this.dicRelaDao.deleteRela(column_id);
	}
	
	
	/**
	 * 查询sheet页下所有列信息
	 * @param sheet_id
	 * @return
	 * @throws Exception
	 */
	public List<UppExcelColumn> queryExcelColumnBySheetId(String sheet_id) throws Exception {
		return this.excelColumnDao.queryExcelColumnBySheetId(sheet_id);
	}
	
	/**
	 * 更新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateDisOrder(Map<String, Object> params) throws Exception{
		//解析参数
		String paramStr =  String.valueOf(params.get("orderParam"));
		String[] fieldArr = paramStr.split(";");
		Map<String, Object> param = new HashMap<String, Object>();
		String metadata_id = "";
		String display_order = "";
		for(int i = 0;i<fieldArr.length;i++){
			metadata_id =  fieldArr[i].split(",")[0];
			display_order = fieldArr[i].split(",")[1];
			//更新字段表
			param.put("field_id", metadata_id);
			param.put("display_order", display_order);
			excelColumnDao.updateDisOrder(param);
			
			param.put("metadata_id", metadata_id);
			param.put("user_id", params.get("user_id"));
			//更新元数据
			this.excelColumnDao.saveMetadata(param);
		}
	}
}
