package com.shuhao.clean.apps.meta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.ColumnDao;
import com.shuhao.clean.apps.meta.dao.UppDicRelaDao;
import com.shuhao.clean.apps.meta.entity.UppTableColumn;
import com.shuhao.clean.apps.meta.service.IColumnService;
import com.shuhao.clean.apps.meta.service.IMetadataService;

/**
 * 
 * 类描述: 元数据[表的列字段]业务逻辑实现类
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:40:36
 */
@Service
public class ColumnServiceImpl implements IColumnService {
	
	@Autowired
	private IMetadataService metadataService = null;
	
	@Autowired
	private ColumnDao columnDao = null;
	
	@Autowired
	private UppDicRelaDao dicRelaDao;
	
	/**
	 * 添加数据库列对象
	 * @param column
	 * @throws Exception
	 */
	public void addColumn(UppTableColumn column) throws Exception {
		this.metadataService.addMetadata(column);
		this.columnDao.addColumn(column);
	}
	

	/**
	 * 根据id获取表的列对象
	 * @param column_id
	 * @return
	 * @throws Exception
	 */
	public UppTableColumn getColumnById(String column_id) throws Exception {
		return this.columnDao.getColumnById(column_id);
	}
	

	/**
	 * 保存表的列字段信息
	 * @param column
	 * @throws Exception
	 */
	public void saveColumn(UppTableColumn column) throws Exception {
		this.metadataService.saveMetadata(column);
		this.columnDao.saveColumn(column);
	}
	
	/**
	 * 删除标的列字段信息
	 * @param column_id
	 * @throws Exception
	 */
	public void deleteColumn(String column_id) throws Exception {
		this.metadataService.deleteMetadata(column_id);
		this.columnDao.deleteColumn(column_id);
		dicRelaDao.deleteRelaByDic(column_id);
	}
	
	/**
	 * 查询表对象下面的字段列表
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	public List<UppTableColumn> listTableColumns(String table_id) throws Exception {
		return this.columnDao.listTableColumns(table_id);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IColumnService#updateColumnOrder(java.util.Map)
	 */
	public void updateColumnOrder(Map<String, Object> params) throws Exception {
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
			param.put("metadata_id", metadata_id);
			param.put("display_order", display_order);
			this.columnDao.updateColumnOrder(param);
		}
	}
	
	/**
	 * 根据表id删除表下面的所有列
	 * @param table_id
	 * @throws Exception
	 */
	public void deleteColumnByTableId(String table_id) throws Exception {
		this.columnDao.deleteColumnByTableId(table_id);
		this.metadataService.deleteMetadataByParentId(table_id);
		this.dicRelaDao.deleteRelaByParentDbId(table_id);
	}
	
	/**
	 * 根据元数据关系查询标的列字段列表
	 * @param rela_id
	 * @return
	 * @throws Exception
	 */
	public List<UppTableColumn> listTableColumnByRela(String rela_id) throws Exception {
		return this.columnDao.listTableColumnByRela(rela_id);
	}
}
