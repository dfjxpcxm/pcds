package com.shuhao.clean.apps.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.sys.dao.RoleDao;
import com.shuhao.clean.apps.sys.entity.SysRoleInfo;
import com.shuhao.clean.apps.sys.service.IRoleService;
import com.shuhao.clean.base.BaseService;
import com.shuhao.clean.utils.Identities;
/**
 * 系统角色Service操作实现类
 * @author zzm
 *
 */
@Service(value="roleService")
public class RoleServiceImpl extends BaseService implements IRoleService {
	
	@Autowired
	private RoleDao roleDao ;
	
	//获取角色列表
	public List<SysRoleInfo> listRole(SysRoleInfo role)throws Exception {
		return roleDao.listRole(role);
	}
	
	//添加角色
	public void addRole(SysRoleInfo role)throws Exception {
		role.setRole_id(Identities.getRandomID(18));
		roleDao.addRole(role);
	}

	//删除角色
	public void removeRole(SysRoleInfo role)throws Exception {
		roleDao.removeRole(role);//删除角色表
		roleDao.deleteRoleResourceRela(role.getRole_id());////删除角色对应资源
		roleDao.deleteRoleUserRela(role.getRole_id());//删除角色对应用户
	}
	
	//修改角色信息
	public void updateRoleInfo(SysRoleInfo role)throws Exception {
		roleDao.updateRoleInfo(role);
	}

	//通过角色ID获取角色信息
	public List<SysRoleInfo> getRoleInfoById(String roleID)throws Exception {
		return roleDao.getRoleInfoById(roleID);
	}
	
	//修改角色对应菜单
	public void updateRoleResource(String roleID, String[] resourceIDs)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		roleDao.deleteRoleResourceRela(roleID);
		for (int i = 0; i < resourceIDs.length; i++) {
			if(!"".equals(resourceIDs[i])){
				paramMap.put("role_id", roleID);
				paramMap.put("resource_id", resourceIDs[i]);
				roleDao.insertRoleResourceRela(paramMap);
			}
		}		
	}
}
