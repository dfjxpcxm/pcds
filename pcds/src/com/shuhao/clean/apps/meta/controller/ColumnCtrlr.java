package com.shuhao.clean.apps.meta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppTableColumn;
import com.shuhao.clean.apps.meta.service.IColumnService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: 表的列字段Action 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:16:20
 */
@Controller
@RequestMapping(value="/metadata/column")
public class ColumnCtrlr extends BaseCtrlr {
	
	@Autowired
	private IColumnService columnService = null;
	
	/**
	 * 添加列表对象
	 * @param table
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public Map<String, Object> add(UppTableColumn column) throws Exception {
		try {
			column.setColumn_id(UID.next());
			column.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.columnService.addColumn(column);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 加载表的列字段属性
	 * @param column_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load(String column_id) throws Exception {
		try {
			return super.getJsonResultMap(this.columnService.getColumnById(column_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存表的列字段信息
	 * @param column
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppTableColumn column) throws Exception {
		try {
			column.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.columnService.saveColumn(column);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 删除表的列字段信息
	 * @param column_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String column_id) throws Exception {
		try {
			this.columnService.deleteColumn(column_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 查询表对象下面的字段列表
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listTableColumns")
	@ResponseBody
	public Map<String, Object> listTableColumns(String table_id) throws Exception {
		try {
			return super.getJsonResultMap(this.columnService.listTableColumns(table_id));
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 批量修改字段排序
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateColumnOrder")
	@ResponseBody
	public Map<String, Object> updateColumnOrder(String table_id) throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			params.put("user_id", this.getCurrentUser().getUser_id());
			this.columnService.updateColumnOrder(params);
			return doSuccessInfoResponse("排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("排序失败:"+e.getMessage());
		}
	}
	
	/**
	 * 根据元数据关系查询标的列字段列表
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listColumnByRela")
	@ResponseBody
	public Map<String, Object> listColumnByRela(String metadata_id) throws Exception {
		try {
			return super.getJsonResultMap(this.columnService.listTableColumnByRela(metadata_id));
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
}
