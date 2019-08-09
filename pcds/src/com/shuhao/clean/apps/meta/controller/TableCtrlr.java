package com.shuhao.clean.apps.meta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppTable;
import com.shuhao.clean.apps.meta.service.ITableService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: 数据库表Action
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:15:36
 */
@Controller
@RequestMapping(value="/metadata/table")
public class TableCtrlr extends BaseCtrlr {
	
	@Autowired
	private ITableService tableService = null;
	
	/**
	 * 添加数据库表对象
	 * @param table
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public Map<String, Object> add(UppTable table) throws Exception {
		boolean addSuccess = false;
		String errorInfo = null;
		try {
			table.setTable_id(UID.next());
			table.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.tableService.addTable(table);
			addSuccess = true;
			this.tableService.syncTableColumn(table.getTable_id(), true, table.getCreate_user_id());
		} catch (Exception e) {
			e.printStackTrace();
			errorInfo = e.getMessage();
		}
		
		if(addSuccess) {
			return doSuccessInfoResponse(null);
		} else {
			return super.doFailureInfoResponse(errorInfo);
		}
	}
	
	/**
	 * 加载数据库表属性
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load(String table_id) throws Exception {
		try {
			return super.getJsonResultMap(this.tableService.getTableById(table_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存数据库表信息
	 * @param table
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppTable table) throws Exception {
		try {
			table.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.tableService.saveTable(table);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 删除数据库表信息
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String table_id) throws Exception {
		try {
			this.tableService.deleteTable(table_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 同步表字段
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="syncTableColumn")
	@ResponseBody
	public Map<String, Object> syncTableColumn(String table_id, boolean overwrite) throws Exception {
		try {
			this.tableService.syncTableColumn(table_id, overwrite, getCurrentUser().getUser_id());
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 根据数据库和用户查询出表列表
	 * @param parent_metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="loadByDatabase")
	@ResponseBody
	public Map<String, Object> loadByDatabase(String parent_metadata_id, String searchKey) throws Exception {
		try {
			if(searchKey != null && searchKey.trim().length() == 0) {
				searchKey = null;
			}
			return getJsonResultMap(this.tableService.listTableByDatabase(parent_metadata_id, searchKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
