package com.shuhao.clean.apps.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.util.tree.Tree;
import com.rx.util.tree.TreeNode;
import com.rx.util.tree.TreeStore;
import com.shuhao.clean.apps.base.cache.DataStore;
import com.shuhao.clean.apps.sys.dao.ResourceDao;
import com.shuhao.clean.apps.sys.entity.SysResourceInfo;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.apps.sys.service.IResourceService;
import com.shuhao.clean.base.BaseService;
import com.shuhao.clean.constant.Constant;
import com.shuhao.clean.constant.ServerConstant;
import com.shuhao.clean.utils.GlobalUtil;
/**
 * 系统菜单Service实现类
 * @author zzm
 *
 */
@Service(value="resourceService")
public class ResourceServiceImpl extends BaseService implements IResourceService {
	
	@Autowired
	private ResourceDao resourceDao ;
	
	@Autowired
	private DataStore dataStore ;
	
	//得到登陆用户管理的菜单
	public List<SysResourceInfo> getUserManagerResource(SysUserInfo user)throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", user.getUser_id());
		paramMap.put("role_id", Constant.MANAGER_ROLE_ID);
		if(this.resourceDao.userHasRole(paramMap))
			return this.resourceDao.getAllResource();
		else
			return this.resourceDao.getUserNomalResource(user);
		
	}
	
	//获取所有菜单
	public List<SysResourceInfo> getAllResource()throws Exception {
		return resourceDao.getAllResource();
	}
	
	//通过角色获取对应的菜单
	public List<SysResourceInfo> getResourceByRoleId(String roleId)throws Exception {
		return resourceDao.getResourceByRoleId(roleId);
	}
	
	//得到登陆用户的菜单
	public List<SysResourceInfo> getUserResource(SysUserInfo user)throws Exception {
		return resourceDao.getUserResource(user);
	}
	
	//添加菜单
	public void addResource(SysResourceInfo resource)throws Exception {
		if(resourceDao.getResourceById(resource.getResource_id()).size()>0){
			throw new Exception("资源代码为【"+resource.getResource_id()+"】已经被使用,请重新输入");
		}
		resourceDao.addResource(resource);
		//自动加载菜单更新
		if(ServerConstant.RESOURCE_AUTO_LOAD){
			dataStore.reloadSysResourceStore();
			dataStore.reloadSysResourceMap();
		}
	}
	
	//删除菜单 联动删除菜单和角色关系
	public void removeResource(SysResourceInfo resource)throws Exception {
		List treeList = dataStore.getSysResourceStore().getTreeListByParentID(resource.getResource_id());
		if(treeList != null && treeList.size()>0){
			throw new Exception("资源代码为【"+resource.getResource_id()+"】的菜单下有子菜单，请先删除子菜单。");
		}
		resourceDao.removeResource(resource);//删除菜单表
		resourceDao.removeResourceRoleRela(resource);//删除菜单与角色关系表
		if(ServerConstant.RESOURCE_AUTO_LOAD){
			dataStore.reloadSysResourceStore();
			dataStore.reloadSysResourceMap();
		}
	}
	
	//修改菜单
	public void updateResource(SysResourceInfo resource)throws Exception {
		resourceDao.updateResource(resource);
	}
	
	//根据菜单ID查询菜单对象
	public List<SysResourceInfo>  getResourceById(String resourceID)throws Exception {
		return resourceDao.getResourceById(resourceID);
	}
	
	public String getUserMenu(List<SysResourceInfo> userResourceList, String encryptBaseKey)throws Exception {
		StringBuffer menu = new StringBuffer();
		menu.append("<ul id='nav'>");
		
		TreeStore userResourceStore = new TreeStore();
		for (SysResourceInfo resource : userResourceList) {
			userResourceStore.addTreeNode(resource);
		}
		
		getTreeCode(userResourceStore.getTreeListByParentID(Constant.ROOT_RESOURCE_ID), menu, 0, encryptBaseKey);
		menu.append("</ul>");
		
		return menu.toString();
	}
	
	//返回树形菜单代码
	private void getTreeCode(List<Tree> treeList,StringBuffer sb, int level, String baseKey) {	
		for (int i = 0 ; i < treeList.size() ; i++) {
			Tree tree = treeList.get(i);
			TreeNode root = tree.getRootNode();
			
			sb.append("<li class=\"level"+level+"\">");
			sb.append("<a");
			if(tree.getChildren().size() > 0){
				sb.append(" class=\"haschildren"+level+"\"");
			}
			sb.append(" id=\"m_"+root.getNodeID()+"\" href=\"javascript:void(0)\""+getMenuAction(root,level, baseKey)+">").append(root.getNodeName()).append("</a>");
			
			if(tree.getChildren().size() > 0)
				sb.append("<ul id=\""+root.getNodeID()+"\" class=\"collapsed\">");
			
			getTreeCode(tree.getChildren(), sb, (level+1), baseKey);
			if(tree.getChildren().size() > 0)
				sb.append("</ul>");
			
			sb.append("</li>");
			if(i==treeList.size()-1){
				sb.append("<li class=\"last_level"+level+"\"></li>");
			}
		}
	}
	
	/**
	 * 获取菜单动作代码
	 * <p>修改跳转方式为：/menus/resource_id</p>
	 * @param node
	 * @param level
	 * @param baseKey
	 * @return
	 */
	private String getMenuAction(TreeNode node, int level, String baseKey){
		SysResourceInfo resource = (SysResourceInfo) node;
		if(GlobalUtil.trimToNull(resource.getHandler()) != null){
			//包含链接动作节点
			
			String rid = resource.getResource_id();
			String handler = resource.getHandler();
			String rurl = "/menus/"+rid;
			//将get方式的参数添加到url后面
			if(handler.indexOf("?")>-1){
				rurl+=handler.substring(handler.indexOf("?"));
			}
//			String rurl = resource.getHandler();
			if(Constant.EncrypyMenu) {
				rid = GlobalUtil.encryptValStr(baseKey, resource.getResource_id());
				String[] strs = rurl.split("/");
				rurl = "/";
				for(int i = 0;i < strs.length-1;i++){
					if(null == strs[i] || "".equals(strs[i])){
						continue;
					}else{
						rurl = rurl + strs[i] + "/";
					}
				}
			}
			
			return " onclick=\"gotoPage('"+rid+"','"+resource.getResource_name()+"','"+rurl+"')\"";
//			return " onclick=\"gotoPage('"+rid+"','"+resource.getResource_name()+"','"+rurl+"')\"";
		}else{
			if(level == 0)
				return " onclick=\"DoMenu('"+resource.getResource_id()+"')\"";
			return " onclick=\"DoSecMenu('"+resource.getResource_id()+"')\"";
		}
	}
	 
}
