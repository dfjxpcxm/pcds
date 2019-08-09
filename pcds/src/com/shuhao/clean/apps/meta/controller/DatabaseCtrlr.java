package com.shuhao.clean.apps.meta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppDatabase;
import com.shuhao.clean.apps.meta.service.IDatabaseService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.UID;

/**
 * 类描述: 数据库action 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:07:14
 */
@Controller
@RequestMapping(value="/metadata/database")
public class DatabaseCtrlr extends BaseCtrlr {
	
	@Autowired
	private IDatabaseService databaseService = null;
	
	/**
	 * 添加数据库对象
	 * @param database
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public Map<String, Object> add(UppDatabase database) throws Exception {
		try {
			database.setDatabase_id(UID.next());
			database.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.databaseService.addDatabase(database);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 加载数据库属性
	 * @param database_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load(String database_id) throws Exception {
		try {
			return super.getJsonResultMap(this.databaseService.getDatabaseById(database_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存数据库信息
	 * @param database
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppDatabase database) throws Exception {
		try {
			database.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.databaseService.saveDatabase(database);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 删除数据库信息
	 * @param database_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String database_id) throws Exception {
		try {
			this.databaseService.deleteDatabase(database_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 测试数据库连接
	 * @param database
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="testConnect")
	@ResponseBody
	public Map<String, Object> testConnect(UppDatabase database) throws Exception {
		try {
			if(this.databaseService.testConnect(database)) {
				return doSuccessInfoResponse(null);
			} else {
				return doFailureInfoResponse("连接失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("连接失败: " + e.getMessage());
		}
	}
}
