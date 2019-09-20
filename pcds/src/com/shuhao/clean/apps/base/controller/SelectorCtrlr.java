package com.shuhao.clean.apps.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shuhao.clean.utils.exttree.ExtTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.base.service.ISelectorService;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.PageResult;

@Controller
@RequestMapping(value="/selector")
public class SelectorCtrlr extends BaseCtrlr {

	@Autowired
	private ISelectorService selectorService = null;

    /**
     * 归属条线下拉框
     */
    @RequestMapping(value="queryBusiLineList")
    @ResponseBody
    public Map<String, Object> queryBusiLineList() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryBusiLineList(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }

    
    /**
     * 职位类型下拉框
     */
    @RequestMapping(value="queryJobTitle")
    @ResponseBody
    public  Map<String, Object> queryJobTitle() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryJobTitle(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    /**
     * 岗位
     */
    @RequestMapping(value="queryPostType")
    @ResponseBody
    public  Map<String, Object> queryPostType() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryPostType(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    /**
     * 技术职称
     */
    @RequestMapping(value="queryTechTitle")
    @ResponseBody
    public  Map<String, Object> queryTechTitle() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryTechTitle(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    /**
     * 职位类型下拉框
     */
    @RequestMapping(value="queryGender")
    @ResponseBody
    public  Map<String, Object> queryGender() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryGender(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    /**
     * 民族下拉框
     */
    @RequestMapping(value="queryEthnicity")
    @ResponseBody
    public  Map<String, Object> queryEthnicity() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryEthnicity(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    /**
     * 教育背景
     */
    @RequestMapping(value="queryEduBack")
    @ResponseBody
    public  Map<String, Object> queryEduBack() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryEduBack(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    /**
     * 用户状态
     */
    @RequestMapping(value="queryMgrStatus")
    @ResponseBody
    public  Map<String, Object> queryMgrStatus() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryMgrStatus(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    /**
     * 序列
     */
    @RequestMapping(value="queryCustJobSeq")
    @ResponseBody
    public  Map<String, Object> queryCustJobSeq() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryCustJobSeq(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    @RequestMapping(value="listMonth")
    @ResponseBody
    public  Map<String, Object> listMonth() throws Exception {
    	List<Map<String, Object>> dataList = selectorService.getMonths();
    	return doJSONResponse(dataList);
    }
    
    /**
     * 指标体系树
     */
    @RequestMapping(value="queryMeasureTree")
    @ResponseBody
    public  Map<String, Object> queryMeasureTree() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryMeasureTree(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    /**
     * 机构类型
     */
    @RequestMapping(value="queryBankOrgType")
    @ResponseBody
    public Map<String, Object> queryBankOrgType() throws Exception {
    	Map<String, Object> params =  this.getRequestParam();
        List<Map<String, Object>> dataList = selectorService.queryBankOrgType(params);
        setTotalCountToRequest(dataList.size()+"");
        return doJSONResponse(dataList);
    }
    
    /**
     * 账户类型
     * @return
     * @throws Exception
     */
    @RequestMapping(value="listCategoryType")
    @ResponseBody
    public  Map<String, Object> listCategoryType() throws Exception {
    	List<Map<String, Object>> dataList = selectorService.getCategoryType();
    	return doJSONResponse(dataList);
    }
    
    /**
     * 查询客户经理
     * @return
     * @throws Exception
     */
    @RequestMapping(value="queryCustMgrList")
    @ResponseBody
	public Object queryCustMgrList( ) throws Exception
    {
		Map<String, Object> params =  this.getRequestParam();
		PageResult<Map<String, Object>> pageResult=selectorService.getCustMgrs(params);
		return pageResult;		 
    }
    
    /**
     * 查询数据库类型
     * @return
     * @throws Exception
     */
    @RequestMapping(value="queryUppDatabaseType")
    @ResponseBody
	public Object queryUppDatabaseType( ) throws Exception
    {
		Map<String, Object> params =  this.getRequestParam();
		List<Map<String, Object>> dataList=selectorService.queryUppDatabaseType(params);
		return doJSONResponse(dataList);		 
    }
    
    /**
     * 元数据树
     * @return
     * @throws Exception
     */
	@RequestMapping(value="getMetadataTree")
    @ResponseBody
    public Map<String, Object> getMetadataTree() throws Exception {

        String metadataId = request.getParameter("metadata_id");//选择节点

        if (null == metadataId || "".equals(metadataId))
        	metadataId = "metath10";

        
        List<UppMetadata> melist = this.selectorService.getMetadataStore(metadataId);
        
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for (UppMetadata uppMetadata : melist) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", uppMetadata.getMetadata_id());
			map.put("text", uppMetadata.getMetadata_name());
			list.add(map);
		}
        return doJSONResponse(list);
    }
	
	/**
     * 补录模板树
     * @return
     * @throws Exception
     */
	@RequestMapping(value="getBlmbTree")
    @ResponseBody
    public Map<String, Object> getBlmbTree() throws Exception {

        String tmpl_id = request.getParameter("tmpl_id");//选择节点

        if (null == tmpl_id || "".equals(tmpl_id))
        	tmpl_id = "root";

        List<Map<String,Object>> blmbList = this.selectorService.getBlmbList(tmpl_id);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        
        for (int i = 0; i < blmbList.size(); i++) {
        	Map<String,Object> map = new HashMap<String, Object>();
        	Map<String, Object> row = (Map<String,Object>) blmbList.get(i);
        	map.put("id", (String)row.get("tmpl_id"));
        	map.put("text", (String)row.get("template_name"));
        	map.put("desc", (String)row.get("enter_type_cd"));
        	map.put("template_type_cd", (String)row.get("template_type_cd"));
        	map.put("status_cd", (String)row.get("status_cd"));
        	map.put("is_leaf", this.selectorService.getBlmbList((String)row.get("tmpl_id")).size()>0?false:true);
        	list.add(map);
		}
        return doJSONResponse(list);
    }
	
	 /**
     * 查询按钮功能
     * @return
     * @throws Exception
     */
    @RequestMapping(value="queryUppButtonFunction")
    @ResponseBody
	public Object queryUppButtonFunction( ) throws Exception
    {
		Map<String, Object> params =  this.getRequestParam();
		List<Map<String, Object>> dataList=selectorService.queryUppButtonFunction(params);
		return doJSONResponse(dataList);		 
    }
    
    /**
     * 查询维度
     * @return
     * @throws Exception
     */
    @RequestMapping(value="queryUppDimSource")
    @ResponseBody
	public Object queryUppDimSource( ) throws Exception
    {
		Map<String, Object> params =  this.getRequestParam();
		List<Map<String, Object>> dataList=selectorService.queryUppDimSource(params);
		return doJSONResponse(dataList);		 
    }


    /**
     * 查询按钮功能
     * @return
     * @throws Exception
     */
    @RequestMapping(value="listBankOrganization")
/*    @ResponseBody*/
    public void listBankOrganization() throws Exception {
        Map<String, Object> params = this.getRequestParam();
        try {
            ExtTreeNode treeNode = selectorService.listBankOrganization(params);
            doExtTreeJSONResponse(treeNode.getChildren(), response);
        } catch (Exception e) {
            e.printStackTrace();
            List<ExtTreeNode> eList = new ArrayList<ExtTreeNode>();
            ExtTreeNode treeNode = new ExtTreeNode();
            treeNode.setId("errorRootId");
            treeNode.setText("展示机构树失败:" + e.getMessage());
            treeNode.setLeaf(true);
            eList.add(treeNode);
            doExtTreeJSONResponse(eList, response);
        }
    }
}
