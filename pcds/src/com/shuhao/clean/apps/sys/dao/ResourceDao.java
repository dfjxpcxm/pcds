package com.shuhao.clean.apps.sys.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.sys.entity.SysResourceInfo;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;

/**
 * 系统菜单Dao接口
 * @author zzm
 *
 */
@MyBatisDao
public interface ResourceDao {
	
	//获取所有菜单
	public List<SysResourceInfo> getAllResource()throws Exception;
	
	//通过角色获取对应的菜单
	public List<SysResourceInfo> getResourceByRoleId(String roleId)throws Exception;
	
	//得到登陆用户的菜单
	public List<SysResourceInfo> getUserResource(SysUserInfo user)throws Exception;
	
	//得到登陆用户的菜单[不包含特殊授权]
	public List<SysResourceInfo> getUserNomalResource(SysUserInfo user)throws Exception;
	
	//是否拥有菜单
	public boolean userHasRole(Map<String, Object> paramMap) throws Exception;
	
	//添加菜单
	public void addResource(SysResourceInfo resource)throws Exception;
	
	//删除菜单
	public void removeResource(SysResourceInfo resource)throws Exception;
	
	//删除菜单与角色对象之间的关系
	public void removeResourceRoleRela(SysResourceInfo resource)throws Exception;
	
	//修改菜单
	public void updateResource(SysResourceInfo resource)throws Exception;
	
	//根据菜单ID查询菜单对象
	public List<SysResourceInfo>  getResourceById(String resourceID)throws Exception;
}
