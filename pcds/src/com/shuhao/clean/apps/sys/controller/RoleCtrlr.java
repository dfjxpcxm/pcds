package com.shuhao.clean.apps.sys.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shuhao.clean.apps.sys.entity.SysRoleInfo;
import com.shuhao.clean.apps.sys.service.IRoleService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.annotation.UseLog;
/**
 * <p>Title:角色管理控制类</p>
 * <p>Description: 角色管理</p>
 * <p>History:</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: D&A 数浩科技</p>
 * @since 2014-8-20
 * @author gongzy
 * @version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleCtrlr extends BaseCtrlr {

	@Autowired
	public IRoleService roleService;
	/**
	 * 获取角色列表
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0009")
	@UseLog
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String, Object> list(SysRoleInfo sysRoleInfo) throws Exception {
		List<SysRoleInfo> roleList = roleService.listRole(sysRoleInfo);
		return doJSONResponse(roleList);
	}

	/**
	 * 获取角色列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listById/{role_id}")
	@ResponseBody
	public Map<String, Object>listById(@PathVariable String role_id) throws Exception {
		List<SysRoleInfo> roleList = roleService.getRoleInfoById(role_id);
		return doJSONResponse(roleList);
	}
	
	/**
	 * 添加角色
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0010")
	@UseLog
	@RequestMapping(value="add")
	@ResponseBody
	public Map<String, Object> add(SysRoleInfo sysRoleInfo) {
		try {
			//SysRoleInfo role = getParamObject(SysRoleInfo.class);
			roleService.addRole(sysRoleInfo);
			return doSuccessInfoResponse("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败:"+e.getMessage());
		}
	}
	
	/**
	 * 修改角色
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0011")
	@UseLog
	@RequestMapping(value="update")
	@ResponseBody
	public Map<String, Object> update(SysRoleInfo sysRoleInfo) {
		try {
//			SysRoleInfo role = getParamObject(SysRoleInfo.class);
			roleService.updateRoleInfo(sysRoleInfo);
			return doSuccessInfoResponse("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("修改失败:"+e.getMessage());
		}
	}
	
	/**
	 * 删除角色
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0012")
	@UseLog
	@RequestMapping(value="remove")
	@ResponseBody
	public Map<String, Object> remove(SysRoleInfo sysRoleInfo){
		try {
//			SysRoleInfo role = getParamObject(SysRoleInfo.class);
			roleService.removeRole(sysRoleInfo);
			return doSuccessInfoResponse("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败:"+e.getMessage());
		}
	}

	/**
	 * 修改角色和菜单关系
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0013")
	@UseLog
	@RequestMapping(value="updateRoleResource")
	@ResponseBody
	public Map<String, Object> updateRoleResource() throws Exception {
		SysRoleInfo role = new SysRoleInfo();
		String roleID = request.getParameter("role_id");
		String resourceIDs = request.getParameter("resource_ids");
		role.setRole_id(roleID);
		try {
			roleService.updateRoleResource(roleID, resourceIDs.split(","));
			return doSuccessInfoResponse("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("保存失败:"+e.getMessage());
		}
	}
}
