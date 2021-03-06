package com.shuhao.clean.apps.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.sys.entity.SysOrgInfo;
import com.shuhao.clean.apps.sys.entity.SysResourceInfo;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;

/**
 * 用户操作Dao接口
 * @author chenxd
 *
 */
@MyBatisDao
public interface UserDao {
	
	//通过查询条件,查询出用户列表
	public List<Map<String, Object>> listUsers(Map<String, Object> paramMap)throws Exception;
	
	//查询用户列表数量
	public String getUsersCount(Map<String, Object> paramMap)throws Exception;
	
	//添加用户
	public void addUser(SysUserInfo user)throws Exception;
	
	//删除用户
	public void removeUser(String userID)throws Exception;
	
	//删除用户与角色关系
	public void removeUserRoleRela(SysUserInfo user)throws Exception;
	
	//修改用户信息
	public void updateUser(SysUserInfo user)throws Exception;
	
	//根据用户ID查找用户
	public List<SysUserInfo> findUserById(String userID)throws Exception;
	
	//修改用户密码
	public void modifyPassword(Map<String, Object> paramMap)throws Exception;
	
	//添加用户角色
	public void addUserRole(Map<String, Object> paramMap)throws Exception;
	
	//根据用户ID获取用户角色
	public List<Map<String, Object>> getUserRole(Map<String, Object> paramMap)throws Exception;
	
	//插入用户与角色关系
	public void insertUserRoleRela(Map<String, Object> paramMap)throws Exception;
	
	//校验密码
	public int checkPassword(SysUserInfo user)throws Exception;
	
	//获得待分配用户已经拥有的特殊权限
	public List<Map<String,Object>> getSpeciallyResourceList(Map<String,Object> paramMap) throws Exception;
	
	//获得当前用的菜单列表
	public List<SysResourceInfo> getCurrentUserResourceList(Map<String,Object> paramMap) throws Exception;
	
	//获得当前用的菜单列表   用于用户登陆初始化用户权限菜单
	public List<Map<String,Object>> getLoginUserResource(Map<String,Object> paramMap) throws Exception;
	
	public void deleteSpeciallyAuthorize(Map<String,Object> paramMap) throws Exception;
	
	public void saveSpeciallyAuthorize(Map<String,Object> paramMap) throws Exception;
	
	public int isExistSpecialOrg(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 查询某用户所有的权限机构
	 * */
	public List<Map<String, Object>> getAllBankOrgIdByUserId(String login_user_id) throws Exception;
	
	/**
	 * 删除用户在某权限机构下的角色
	 * */
	public void deleteRoleByUserIdAndBankOrgId(Map<String, Object> paramMap) throws Exception;
	
	public void deleteInitPage(String userID) throws Exception;


	public int findOrgInfoById(String orgid) throws Exception;


	public void updataOrg(SysOrgInfo org) throws Exception;
	public void addOrg(SysOrgInfo org) throws Exception;
	public void addUserOrgInfo(Map<String, Object> paramMap) throws Exception;


}
