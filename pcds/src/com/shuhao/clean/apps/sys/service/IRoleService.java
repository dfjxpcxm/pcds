package com.shuhao.clean.apps.sys.service;

import java.util.List;

import com.shuhao.clean.apps.sys.entity.SysRoleInfo;

/**
 * 系统角色Service接口
 * @author zzm
 *
 */
public interface IRoleService {
	
	//获取角色列表
	public List<SysRoleInfo> listRole(SysRoleInfo role)throws Exception;
	
	//添加角色
	public void addRole(SysRoleInfo role)throws Exception;
	
	//删除角色
	public void removeRole(SysRoleInfo role)throws Exception;
				
	//修改角色信息
	public void updateRoleInfo(SysRoleInfo role)throws Exception;
	
	//通过角色ID获取角色信息
	public List<SysRoleInfo> getRoleInfoById(String roleID)throws Exception;

	public void updateRoleResource(String roleID, String[] resourceIDs)throws Exception;

}
