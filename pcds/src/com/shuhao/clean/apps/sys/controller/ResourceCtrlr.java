package com.shuhao.clean.apps.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rx.util.tree.Tree;
import com.rx.util.tree.TreeNode;
import com.rx.util.tree.TreeStore;
import com.shuhao.clean.apps.base.cache.DataStore;
import com.shuhao.clean.apps.sys.entity.SysResourceInfo;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.apps.sys.service.IResourceService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.Constant;
import com.shuhao.clean.constant.LoginConstant;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.annotation.UseLog;

/**
 * 系统菜单的增删改查
 * @author zzm
 *
 */
@Controller
@RequestMapping("/sysResource")
public class ResourceCtrlr extends BaseCtrlr {

	@Autowired
	private IResourceService resourceService ;

	@Autowired
	public DataStore dataStore;
	
	@RequestMapping(value="getUserResource",method = RequestMethod.POST)
	public @ResponseBody String getUserResource(HttpServletRequest request,ModelAndView model) throws Exception {
		
		SysUserInfo user = (SysUserInfo) session.getAttribute("currentUser");
		if(user == null)
			throw new ServletException("用户未登陆");
		String menu = "";
		try {
			model.setViewName("main");
			//获取登陆用户菜单
		 
		 List<SysResourceInfo> userResourceList = resourceService.getUserResource(user);
		//	List<SysResourceInfo> userResourceList = (List<SysResourceInfo>)session.getAttribute(LoginConstant.USER_RESOURCE);
			//加载用户菜单
			menu = resourceService.getUserMenu(userResourceList, session.getId());
		} catch (Exception e) {
			//session.inva	lidate();
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
		return menu;
	}
	
	
	@RequestMapping(value="{menuId}",method = RequestMethod.GET)
	public ModelAndView menu(String menuId, HttpServletRequest request,ModelAndView model) throws Exception {
		String menu = "";
		try {
			model.setViewName("main");
			//获取登陆用户菜单
//			List<SysResourceInfo> userResourceList = resourceService.getUserResource(user);
			List<SysResourceInfo> userResourceList = (List<SysResourceInfo>)session.getAttribute(LoginConstant.USER_RESOURCE);
			//加载用户菜单
			menu = resourceService.getUserMenu(userResourceList, session.getId());
		} catch (Exception e) {
			//session.invalidate();
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
		return model;
	}
	
	
	
	/**
	 * 返回页面展示菜单树所需的XML字符串
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@FunDesc(code="SYS_0003")
	@RequestMapping(value="tree")
	@ResponseBody
	public Map<String, Object> tree() throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Tree> subTreeList = dataStore.getSysResourceStore().getTreeListByParentID(Constant.ROOT_RESOURCE_ID);
		sb.append("<tree id=\"\">");
		sb.append("<item id=\"root\" text=\"资源树\" open=\"1\" im0=\"tree.gif\" im1=\"tree.gif\" im2=\"tree.gif\" call=\"1\" >");
		for (int i = 0; i < subTreeList.size(); i++) {
			Tree childTree = subTreeList.get(i);
			sb.append(dealTree(childTree));
		}
		sb.append("</item>");
		sb.append("</tree>");
		return doSuccessInfoResponse(sb.toString());
		//request.setAttribute("xml", sb.toString());
		//return "resource";//ShowResource
	}
	
	/**
	 * 返回展示树形节点及其子节点的XML字符串
	 * @param childTree	需要展示的树
	 * @param tree
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String dealTree(Tree tree) {
		StringBuffer sb = new StringBuffer();
		TreeNode node = tree.getRootNode();
		if (tree.getChildren().size() == 0)
			sb.append("<item id=\""
				+ node.getNodeID()
				+ "\" text=\""
				+ node.getNodeName()
				+ "\" open=\"1\" im0=\"leaf.gif\" im1=\"leaf.gif\" im2=\"leaf.gif\" call=\"1\" >");
		else
			sb.append("<item id=\""
				+ node.getNodeID()
				+ "\" text=\""
				+ node.getNodeName()
				+ "\" open=\"1\" im0=\"folderOpen.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" >");
		List<Tree> subTreeList = tree.getChildren();
		for (int i = 0; i < subTreeList.size(); i++) {
			Tree subTree = subTreeList.get(i);
			sb.append(dealTree(subTree));
		}
		sb.append("</item>");
		return sb.toString();
	}

	/**
	 * 根据角色ID,返回页面展示菜单树所需的XML字符串
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@FunDesc(code="SYS_0004")
	@RequestMapping(value="checkResource/{role_id}")
	public String checkResource(@PathVariable String role_id) throws Exception {
		StringBuffer sb = new StringBuffer();

		List<SysResourceInfo> resourceList = resourceService.getResourceByRoleId(role_id);
		
		List<SysResourceInfo> ownerResourceList = this.resourceService.getUserManagerResource(getCurrentUser());
		
		List<Tree> subTreeList = null;
		TreeStore store = new TreeStore();
		for (SysResourceInfo resource : ownerResourceList) {
			store.addTreeNode(resource);
		}
//		if(role.getRole_id() == null || "".equals(role.getRole_id()))
//		subTreeList = DataStore.getResourceStore().getTreeListByParentID(Constant.ROOT_RESOURCE_ID);
		subTreeList = store.getTreeListByParentID(Constant.ROOT_RESOURCE_ID);
		
		sb.append("<tree id=\"\">");
		sb.append("<item id=\"root\" text=\"资源树\" open=\"1\" im0=\"tree.gif\" im1=\"tree.gif\" im2=\"tree.gif\" call=\"1\" >");
			for (int i = 0; i < subTreeList.size(); i++) {
				Tree childTree = subTreeList.get(i);
				sb.append(dealTree(childTree,resourceList));
			}
		sb.append("</item>");
		sb.append("</tree>");
		request.setAttribute("xml", sb.toString());
		request.setAttribute("selectRoleID",role_id);
		return "sys/role_resource";
	}
	
	/**
	 * 返回展示树形节点及其子节点的XML字符串
	 * @param childTree	需要展示的树
	 * @return	XML字符串
	 */
	@SuppressWarnings("unchecked")
	public String dealTree(Tree tree,List<SysResourceInfo> resourceList){
		StringBuffer sb = new StringBuffer();
		TreeNode node = tree.getRootNode();
		if(tree.getChildren().size()==0)
			sb.append("<item id=\""+node.getNodeID()
				+"\" text=\""+node.getNodeName()
				+"\" open=\"1\" im0=\"leaf.gif\" im1=\"leaf.gif\" im2=\"leaf.gif\" call=\"1\"");
		else
			sb.append("<item id=\""+node.getNodeID()
					+"\" text=\""+node.getNodeName()
					+"\" open=\"1\" im0=\"folderOpen.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" ");
		if(isExist(node, resourceList))
			sb.append(" checked=\"1\" ");
		sb.append(">");
		List<Tree> subTreeList = tree.getChildren();
		for (int i = 0; i < subTreeList.size(); i++) {
			Tree subTree = subTreeList.get(i);
			sb.append(dealTree(subTree,resourceList));
		}
		sb.append("</item>");
		return sb.toString();
	}
	
	/**
	 * 添加菜单
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0005")
	@UseLog
	@RequestMapping(value="addResource")
	@ResponseBody
	public Map<String, Object> addResource() throws Exception {
		SysResourceInfo resource = getParamObject(SysResourceInfo.class);
		try {
			resourceService.addResource(resource);
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("success", "true");
			results.put("resourceID", resource.getResource_id());
			results.put("resourceName", resource.getResource_name());
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败:" + e.getMessage());
		}
	}

	/**
	 * 删除菜单
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0006")
	@UseLog
	@RequestMapping(value="removeResource")
	@ResponseBody
	public Map<String, Object> remove() throws Exception {
		SysResourceInfo resource = getParamObject(SysResourceInfo.class);
		try {
			resourceService.removeResource(resource);
			return doSuccessInfoResponse(" 删除成功");
		} catch (Exception e) {
			return doFailureInfoResponse("删除失败:"+e.getMessage());
		}
	}

	/**
	 * 通过菜单ID查询菜单信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getResourceById")
	@ResponseBody
	public Map<String, Object> findById() throws Exception {
		String resource_id = request.getParameter("resource_id");
		try {
			List<SysResourceInfo> list = resourceService.getResourceById(resource_id);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 修改菜单
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0007")
	@UseLog
	@RequestMapping(value="updateResource")
	@ResponseBody
	public Map<String, Object> update()throws Exception {
		SysResourceInfo resource = getParamObject(SysResourceInfo.class);
		try {
			resourceService.updateResource(resource);
			return doSuccessInfoResponse("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("修改失败:"+e.getMessage());
		}
	}
	
	/**
	 * 重新加载菜单到内存
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0008")
	@UseLog
	@RequestMapping(value="reloadResource")
	@ResponseBody
	public Map<String, Object> reload() throws Exception {
		dataStore.reloadSysResourceStore();
		dataStore.reloadSysResourceMap();
		return doSuccessInfoResponse("加载成功");
	}
	
	/**
	 * 判断用户是否拥有该菜单
	 * @param node
	 * @param resourceList
	 * @return
	 */
	public boolean isExist(TreeNode node,List<SysResourceInfo> resourceList){
		for (Object obj : resourceList) {
			if(node.getNodeID().equals(((SysResourceInfo)obj).getNodeID()))
				return true;
		}
		return false;
	}
}
