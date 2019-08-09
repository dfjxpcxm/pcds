package com.shuhao.clean.apps.meta.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppDicRela;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppMetadataCategory;
import com.shuhao.clean.apps.meta.service.IMetadataService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;

/**
 * 元数据管理
 * @author Wanggl
 *
 */
@Controller
@RequestMapping("/metadata")
public class MetadataCtrlr extends BaseCtrlr {

	@Autowired
	private IMetadataService metadataService = null;

	@RequestMapping(value = "/page/{pageName}")
	public String gotoPage(@PathVariable(value = "pageName") String pageName) {
		return "meta/" + pageName;
	}

	/**
	 * 查询下级元数据列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listSubMetadata")
	@ResponseBody
	public Map<String, Object> listSubMetadata() throws Exception {
		String parent_metadata_id = this.getParam("prt_metadata_id");
		try {
			List<UppMetadata> subMetadataList = this.metadataService.queryMetadataByParentId(parent_metadata_id);
			return doJSONResponse(subMetadataList);
		} catch (Exception e) {
			e.printStackTrace();
			return doSuccessInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 获取元数据树
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMetadataTree/{nodeId}")
	@ResponseBody
	public Map<String, Object> getMetadataTree(@PathVariable String nodeId) throws Exception {
		try {
			List<UppMetadata> subMetadataList = this.metadataService.queryMetadataByParentId(nodeId);
			
			List<Map<String, Object>> nodeList = new ArrayList<Map<String,Object>>();
			for (UppMetadata uppMetadata : subMetadataList) {
				Map<String, Object> node = new HashMap<String, Object>();
				node.put("id", uppMetadata.getMetadata_id());
				node.put("name", uppMetadata.getMetadata_name());
				//设置树节点文本
				if(uppMetadata.getMd_cate_cd().equals("CAT")){
					node.put("text", uppMetadata.getMetadata_desc());
				}else if(GlobalUtil.isNull(uppMetadata.getMetadata_desc())){
					node.put("text","["+uppMetadata.getCategory().getMd_cate_name()+"]"+uppMetadata.getMetadata_name());
				}else{
					node.put("text","["+uppMetadata.getMetadata_name()+"]"+uppMetadata.getMetadata_desc());
				}
				
				node.put("nodeType", uppMetadata.getMd_cate_cd());
				node.put("iconCls", uppMetadata.getCategory().getIcon_path());
				node.put("isLeaf", false);
				
				nodeList.add(node);
			}
			return doJSONResponse(nodeList);
		} catch (Exception e) {
			e.printStackTrace();
			return doSuccessInfoResponse(e.getMessage());
		}
	}
	
	
	
	/**
	 * 查询元数据类型列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listMetaCategory")
	@ResponseBody
	public List<UppMetadataCategory> listMetaCategory() throws Exception {
		try {
			return this.metadataService.listMetadataCategory();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<UppMetadataCategory>();
		}
	}
	
	/**
	 * 添加元数据
	 * @param metadata
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addMetadata")
	@ResponseBody
	public Map<String, Object> add(UppMetadata metadata) throws Exception {
		try {
			metadata.setMetadata_id(UID.next());
			metadata.setCreate_user_id(super.getCurrentUser().getUser_id());
			metadataService.addMetadata(metadata);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 修改元数据展示顺序
	 * @param metadata
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/adjustOrder")
	@ResponseBody
	public Map<String, Object> adjustOrder(UppMetadata metadata) throws Exception {
		try {
			metadataService.updateDisplayOrder(metadata);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 删除元数据映射关系
	 * @param rela
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/removeRela")
	@ResponseBody
	public Map<String, Object> deleteRefer(UppDicRela rela) throws Exception {
		try {
			this.metadataService.deleteMetaRela(rela);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 查询元数据引用列表
	 * @param metadata
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listRefer")
	@ResponseBody
	public Map<String, Object> listReder(String metadata_id) throws Exception {
		try {
			List<UppMetadata> referList = this.metadataService.queryReferMetadata(metadata_id);
			return super.getJsonResultMap(referList);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
 
}
