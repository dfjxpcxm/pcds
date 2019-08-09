package com.shuhao.clean.apps.sys.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.sys.entity.SysRoleInfo;

/**
 * 系统角色Dao接口
 * @author zzm
 *
 */
@MyBatisDao
public interface RoleDao {
	
	//获取角色列表
	public List<SysRoleInfo> listRole(SysRoleInfo role)throws Exception;
	
	//添加角色
	public void addRole(SysRoleInfo role)throws Exception;
	
	//通过角色ID获取角色信息
	public List<SysRoleInfo> getRoleInfoById(String roleID)throws Exception;
	
	//修改角色信息
	public void updateRoleInfo(SysRoleInfo role)throws Exception;

	//删除角色
	public void removeRole(SysRoleInfo role)throws Exception;
	
	//删除角色对应资源关系
	public void deleteRoleResourceRela(String roleID)throws Exception;
	
	//删除角色对应用户关系
	public void deleteRoleUserRela(String roleID)throws Exception;
	
	//插入角色对应资源
	public void insertRoleResourceRela(Map<String, Object> paramMap)throws Exception;

}
