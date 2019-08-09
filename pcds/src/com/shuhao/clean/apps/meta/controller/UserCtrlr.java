package com.shuhao.clean.apps.meta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppUser;
import com.shuhao.clean.apps.meta.service.IDBUserService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: 数据库用户Action 
 * @author chenxiangdong
 * 创建时间：2015-1-6下午04:13:27
 */
@Controller("metaUserCtrlr")
@RequestMapping(value="/metadata/user")
public class UserCtrlr extends BaseCtrlr {
	
	@Autowired
	private IDBUserService dbUserService = null;
	
	/**
	 * 添加数据库用户对象
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public Map<String, Object> add(UppUser user) throws Exception {
		try {
			user.setUser_id(UID.next());
			user.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.dbUserService.addUser(user);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 加载数据库用户属性
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load(String user_id) throws Exception {
		try {
			return super.getJsonResultMap(this.dbUserService.getUserById(user_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存数据库用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppUser user) throws Exception {
		try {
			user.setUpdate_user_id(super.getCurrentUser().getUser_id());
			this.dbUserService.saveUser(user);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 删除数据库用户信息
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete(String user_id) throws Exception {
		try {
			this.dbUserService.deleteUser(user_id);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
}
