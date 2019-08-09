package com.shuhao.clean.apps.meta.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rx.util.tree.Tree;
import com.rx.util.tree.TreeNode;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppTheme;
import com.shuhao.clean.apps.meta.service.ThemeClassService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.MetaConstant;
import com.shuhao.clean.toolkit.log.annotation.FunDesc;
import com.shuhao.clean.toolkit.log.annotation.UseLog;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;

/**
 * 主题分类管理
 * @author Wanggl
 *
 */
@Controller
@RequestMapping("/themeClass")
public class ThemeClassAction extends BaseCtrlr{
	
	@Autowired
	private ThemeClassService themeClassService;
	
	/**
	 * 生成树状主题分类
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@FunDesc(code="BLPT_10")
	@RequestMapping(value="tree")
	@ResponseBody
	public Map<String, Object> tree() throws Exception {
		String nodeId = this.getParam("nodeID");
		StringBuffer sb = new StringBuffer();
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			List<Tree> nodeList = this.themeClassService.getTreeNodeByParentNode(nodeId).getTreeListByParentID(nodeId);
			for (int i = 0; i < nodeList.size(); i++) {
				Tree subTree = (Tree)nodeList.get(i);
				TreeNode node = subTree.getRootNode();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", node.getNodeID());
				map.put("text", node.getNodeName());
				map.put("leaf", Boolean.valueOf(false));
//				if(subTree.getChildren().size()==0)
//					map.put("leaf", Boolean.valueOf(true));
//				else
//					map.put("leaf", Boolean.valueOf(false));
				list.add(map);
			}
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doSuccessInfoResponse(sb.toString());
		}
	}
	
	/**
	 * 添加主题
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="addTheme")
	@ResponseBody
	public Map<String,Object> addTheme() throws Exception{
		UppTheme uppTheme  = getParamObject(UppTheme.class);
		try {
			//主题元数据ID自动生成，以th开头
			uppTheme.setTheme_id(UID.next("th"));
			uppTheme.setCreate_user_id(this.getCurrentUser().getUser_name()+"["+this.getCurrentUser().getUser_id()+"]");
			this.themeClassService.addTheme(uppTheme);
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("success", "true");
			results.put("theme_id", uppTheme.getTheme_id());
			results.put("theme_name", uppTheme.getTheme_name());
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败:" + e.getMessage());
		}
	}
	
	/**
	 * 删除主题
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="removeTheme")
	@ResponseBody
	public Map<String, Object> removeTheme() throws Exception {
		UppTheme uppTheme  = getParamObject(UppTheme.class);
		try {
			this.themeClassService.removeTheme(uppTheme);
			return doSuccessInfoResponse(" 删除成功");
		} catch (Exception e) {
			return doFailureInfoResponse("删除失败:"+e.getMessage());
		}
	}
	
	/**
	 * 修改主题
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="updateTheme")
	@ResponseBody
	public Map<String, Object> updateTheme()throws Exception {
		UppTheme uppTheme  = getParamObject(UppTheme.class);
		uppTheme.setUpdate_user_id(this.getCurrentUser().getUser_name()+"["+this.getCurrentUser().getUser_id()+"]");
		try {
			this.themeClassService.updateTheme(uppTheme);
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("success", "true");
			results.put("theme_id", uppTheme.getTheme_id());
			results.put("theme_name", uppTheme.getTheme_name());
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("修改失败:"+e.getMessage());
		}
	}
	
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="getThemeById")
	@ResponseBody
	public Map<String,Object> getThemeById() throws Exception{
		UppTheme uppTheme  = getParamObject(UppTheme.class);
		try {
			List<Map<String,Object>> list = GlobalUtil.lowercaseListMapKey(this.themeClassService.getThemeById(uppTheme));
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 生成树状主题分类
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@FunDesc(code="BLPT_10")
	@RequestMapping(value="queryMetadataTreeNode")
	@ResponseBody
	public Map<String, Object> queryMetadataTreeNode() throws Exception {
		String nodeId = this.getParam("nodeID");
		StringBuffer sb = new StringBuffer();
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
//			List<Tree> nodeList = this.themeClassService.getTreeNodeByParentNode(nodeId).getTreeListByParentID(nodeId);
			List<Tree> nodeList = this.themeClassService.getMetadataAsNode(nodeId).getTreeListByParentID(nodeId);
			for (int i = 0; i < nodeList.size(); i++) {
				Tree subTree = (Tree)nodeList.get(i);
				UppMetadata node = (UppMetadata) subTree.getRootNode();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", node.getNodeID());
				map.put("text", node.getNodeName());
				map.put("leaf", Boolean.valueOf(false));
//				map.put("metadata_cate_code", node.getMetadata_cate_code());
//				map.put("desc", node.getMetadata_desc());
				list.add(map);
			}
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doSuccessInfoResponse(sb.toString());
		}
	}
	
	/**
	 * 查询所有元数据分类
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_40")
	@UseLog
	@RequestMapping(value="listAllMatadataCat")
	@ResponseBody
	public Map<String,Object> listAllMatadataCat() throws Exception{
		try {
			List<Map<String,Object>> list = this.themeClassService.listAllMatadataCat();
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 添加元数据
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="addMetaData")
	@ResponseBody
	public Map<String,Object> addMetaData() throws Exception{
		UppMetadata uppMetadata = getParamObject(UppMetadata.class);
		try {
			//元数据ID自动生成
			uppMetadata.setMetadata_id(UID.next(MetaConstant.PREFIX_ME));
			uppMetadata.setCreate_user_id(this.getCurrentUser().getUser_id());
			
			this.themeClassService.addMetaData(uppMetadata);
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("success", "true");
			results.put("metadata_id", uppMetadata.getMetadata_id());
			results.put("metadata_name", uppMetadata.getMetadata_name());
//			results.put("metadata_desc", uppMetadata.getMetadata_desc());
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("添加失败:" + e.getMessage());
		}
	}
	
	/**
	 * 查询元数据
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="getMetadataById")
	@ResponseBody
	public Map<String,Object> getMetadataById() throws Exception{
		UppMetadata uppMetadata = getParamObject(UppMetadata.class);
		try {
			List<Map<String,Object>> list = this.themeClassService.getMetadataById(uppMetadata);
			return doJSONResponse(list);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("加载失败：原因是"+e.getMessage());
		}
	}
	
	/**
	 * 修改元数据
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="updateMetadata")
	@ResponseBody
	public Map<String, Object> updateMetadata()throws Exception {
		UppMetadata uppMetadata = getParamObject(UppMetadata.class);
		uppMetadata.setUpdate_user_id(this.getCurrentUser().getUser_name()+"["+this.getCurrentUser().getUser_id()+"]");
		try {
			this.themeClassService.updateMetadata(uppMetadata);
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("success", "true");
			results.put("metadata_id", uppMetadata.getMetadata_id());
			results.put("metadata_name", uppMetadata.getMetadata_name());
//			results.put("metadata_desc", uppMetadata.getMetadata_desc());
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("修改失败:"+e.getMessage());
		}
	}
	
	/**
	 * 删除元数据
	 * @return
	 * @throws Exception
	 */
	@FunDesc(code="BLPT_10")
	@UseLog
	@RequestMapping(value="delMetadata")
	@ResponseBody
	public Map<String, Object> delMetadata() throws Exception {
		UppMetadata uppMetadata = getParamObject(UppMetadata.class);
		try {
			this.themeClassService.delMetadata(uppMetadata);
			return doSuccessInfoResponse(" 删除成功");
		} catch (Exception e) {
			return doFailureInfoResponse("删除失败:"+e.getMessage());
		}
	}
}
