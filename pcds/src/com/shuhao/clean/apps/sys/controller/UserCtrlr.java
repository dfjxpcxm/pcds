package com.shuhao.clean.apps.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rx.util.tree.Tree;
import com.rx.util.tree.TreeNode;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.apps.sys.service.IUserService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.Constant;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.annotation.UseLog;
import com.shuhao.clean.utils.ResourcesUtils;
/**
 * 系统用户Action
 * @author chenxd
 *
 */
@Controller
@RequestMapping("/user")
public class UserCtrlr extends  BaseCtrlr {
	
	
	@Autowired
	private IUserService userService = null;
	
	/**
	 * 查询用户列表
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0014")
	@UseLog
	@RequestMapping(value="getUserList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, Object> paramMap = getRequestParam();
		try {
			List<Map<String, Object>> userList = userService.listUsers(insertPageParamToMap(paramMap));
			setTotalCountToRequest(userService.getTotalNum());
			
			return doJSONResponse(userList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取用户详细信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0015")
	@UseLog
	@RequestMapping(value="getUserById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> get()throws Exception{
		String user_id = request.getParameter("user_id");
		try {
			SysUserInfo user = userService.findUserById(user_id);
			List<SysUserInfo> dataList = new ArrayList<SysUserInfo>();
			dataList.add(user);
			return doJSONResponse(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加用户
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0016")
	@UseLog
	@RequestMapping(value="addUser")
	@ResponseBody
	public Map<String, Object> add()throws Exception {
		SysUserInfo user = getParamObject(SysUserInfo.class);
		try {
			userService.addUser(user);
			return doSuccessInfoResponse("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败:"+e.getMessage());
		}
	}
	
	/**
	 * 修改用户信息
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0017")
	@UseLog
	@RequestMapping(value="editUser")
	@ResponseBody
	public Map<String, Object> edit()throws Exception {
		SysUserInfo user = getParamObject(SysUserInfo.class);
		try {
			userService.updateUser(request,user);
			return doSuccessInfoResponse("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("修改失败:"+e.getMessage());
		}
	}
	
	/**
	 * 删除用户记录
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0018")
	@UseLog
	@RequestMapping(value="deleteUser")
	@ResponseBody
	public Map<String, Object> delete()throws Exception {
		String user_id = request.getParameter("user_id");
		try {
			userService.removeUser(user_id);
			return doSuccessInfoResponse("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("删除失败:"+e.getMessage());
		}
	}
	
	/**
	 * 获取用户角色列表
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0019")
	@UseLog
	@RequestMapping(value="getUserRole")
	@ResponseBody
	public Map<String, Object> getRole()throws Exception {
		try {
			Map<String,Object> paramMap = super.getRequestParam();
			List<Map<String, Object>> userList = userService.getUserRole(paramMap);
			return doJSONResponse(userList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存用户角色
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0020")
	@UseLog
	@RequestMapping(value="saveUserRole")
	@ResponseBody
	public Map<String, Object> saveUserRole()throws Exception {
		SysUserInfo user = getParamObject(SysUserInfo.class);
		String idStr = request.getParameter("role_id");
		idStr = idStr == null ? "" : idStr;
		try {
			userService.saveUserRole(user, idStr.split(","));
			return doSuccessInfoResponse("操作成功");
		} catch (Exception e) {
			return doFailureInfoResponse("操作失败:"+e.getMessage());
		}
		
	}
	
	@RequestMapping(value="checkPassword")
	public String checkPassword() throws Exception{
		String  check_password = request.getParameter("password");
		SysUserInfo user = (SysUserInfo)session.getAttribute("currentUser");
		user.setPassword(check_password);
		int v = userService.checkPassword(user);
		if(v>0){
			doSuccessInfoResponse("原密码输入正确");
		}else{
			doFailureInfoResponse("原密码输入错误");
		}
		return null;
	}
	
	/**
	 * 修改用户密码
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="SYS_0021")
	@UseLog
	@RequestMapping(value="modifyPassword")
	public String modifyPassword() throws Exception {
		String password = request.getParameter("password");
		try {
			SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("currentUser");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("password", password);
			paramMap.put("user_id", user.getUser_id());
			userService.modifyPassword(paramMap);
			doSuccessInfoResponse("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			doFailureInfoResponse("修改失败:"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 组装特殊授权菜单树
	 * */
	@FunDesc(code="SYS_0023")
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getResourceTree")
	public String getResourceTree(){
		try {
			Map<String, Object> paramMap = getRequestParam();
			paramMap.put("current_user_id", this.getCurrentUser().getUser_id());
			StringBuffer sb = new StringBuffer();
			//获得当前用户的菜单树
			List<Tree> subTreeList = this.userService.getCurrentUserResourceList(paramMap).getTreeListByParentID(Constant.ROOT_RESOURCE_ID);
			//获得待分配用户已经拥有的菜单id列表
			Map<String,String> speciallyResource = this.userService.getSpeciallyResource(paramMap);
			sb.append("<tree id=\"\">");
			sb.append("<item id=\"root\" text=\"资源树\" open=\"1\" im0=\"tree.gif\" im1=\"tree.gif\" im2=\"tree.gif\" call=\"1\" >");
			for (int i = 0; i < subTreeList.size(); i++) {
				Tree childTree = subTreeList.get(i);
				sb.append(dealTree(childTree,speciallyResource));
			}
			sb.append("</item>");
			sb.append("</tree>");
			super.doSuccessInfoResponse(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="delTree")
	private String dealTree(Tree tree,Map<String,String> speciallyResource) {
		StringBuffer sb = new StringBuffer();
		TreeNode node = tree.getRootNode();
		if (tree.getChildren().size() == 0)
			sb.append("<item id=\""
				+ node.getNodeID()
				+ "\" text=\""
				+ node.getNodeName()
				+ "\" open=\"1\" im0=\"leaf.gif\" im1=\"leaf.gif\" im2=\"leaf.gif\" call=\"1\" "+(speciallyResource.containsKey(node.getNodeID()) ? "checked=\"1\"" : "")+">");
		else
			sb.append("<item id=\""
				+ node.getNodeID()
				+ "\" text=\""
				+ node.getNodeName()
				+ "\" open=\"1\" im0=\"folderOpen.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" "+(speciallyResource.containsKey(node.getNodeID()) ? "checked=\"1\"" : "")+">");
		
		List<Tree> subTreeList = tree.getChildren();
		for (int i = 0; i < subTreeList.size(); i++) {
			Tree subTree = subTreeList.get(i);
			sb.append(dealTree(subTree,speciallyResource));
		}
		sb.append("</item>");
		return sb.toString();
	}
	
	
	/**
	 * 保存特殊授权
	 * @throws Exception 
	 * */
	@RequestMapping(value="saveSpeciallyAuthorize")
	public String saveSpeciallyAuthorize() throws Exception{
		try{
			Map<String, Object> paramMap = getRequestParam();
			paramMap.put("current_user_id", this.getCurrentUser().getUser_id());
			
			this.userService.saveSpeciallyAuthorize(paramMap);
			this.doSuccessInfoResponse("操作成功!");
		}catch(Exception e){
			e.printStackTrace();
			this.doFailureInfoResponse("操作失败!");
		}
		return null;
	}
	
	
	/**
	 * 获得当前用的菜单列表   用于用户登陆初始化用户权限菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUserResource",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getLoginUserResource()throws Exception{
		try {
			Map<String, Object> paramMap = getRequestParam();
			paramMap.put("current_user_id", this.getCurrentUser().getUser_id());
			Map<Integer, com.shuhao.clean.utils.Tree> treeStore = this.userService.getLoginUserResource(paramMap);
			//菜单源码
			ResourcesUtils utils = new ResourcesUtils();
			String jsResource = utils.getBaseMenuResource(treeStore);
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < utils.FUNC_LIST.size(); i++) {
				if(i == utils.FUNC_LIST.size()-1){
					buffer.append("new ").append(utils.FUNC_LIST.get(i)).append("()").append("\n");
				}else{
					buffer.append("new ").append(utils.FUNC_LIST.get(i)).append("()").append(",").append("\n");
				}
			}
			//构建创建js对象
			String jsObject = buffer.toString();
			return doSuccessInfoResponse(jsResource+"--"+jsObject);
			
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取用户菜单失败:"+e.getMessage());
		}
	}
	
}
