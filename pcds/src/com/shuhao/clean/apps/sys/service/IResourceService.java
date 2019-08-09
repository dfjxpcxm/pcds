package com.shuhao.clean.apps.sys.service;

import java.util.List;

import com.shuhao.clean.apps.sys.entity.SysResourceInfo;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;

/**
 * 系统菜单Service接口
 * @author zzm
 *
 */
public interface IResourceService {
	//获取所有菜单
	public List<SysResourceInfo> getAllResource()throws Exception;
	
	//通过角色获取对应的菜单
	public List<SysResourceInfo> getResourceByRoleId(String roleId)throws Exception;
	
	//得到登陆用户的菜单
	public List<SysResourceInfo> getUserResource(SysUserInfo user)throws Exception;
	
	//得到登陆用户管理的菜单
	public List<SysResourceInfo> getUserManagerResource(SysUserInfo user)throws Exception;
	
	//添加菜单
	public void addResource(SysResourceInfo resource)throws Exception;
	
	//删除菜单
	public void removeResource(SysResourceInfo resource)throws Exception;
	
	//修改菜单
	public void updateResource(SysResourceInfo resource)throws Exception;
	
	//根据菜单ID查询菜单对象
	public List<SysResourceInfo>  getResourceById(String resourceID)throws Exception;
	
	/**
	 * 获取用户菜单
	 * @param userResourceList
	 * @param encryptBaseKey
	 * @return
	 * @throws Exception
	 */
	public String getUserMenu(List<SysResourceInfo> userResourceList, String encryptBaseKey)throws Exception;
}
