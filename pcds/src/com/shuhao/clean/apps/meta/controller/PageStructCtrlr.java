package com.shuhao.clean.apps.meta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.entity.UppPageStruct;
import com.shuhao.clean.apps.meta.service.IPageStructService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.UID;

/**
 * 类描述: 页面结构action 
 * @author bixb
 * 创建时间：2015-1
 */
@Controller
@RequestMapping(value="/metadata/pageStruct")
public class PageStructCtrlr extends BaseCtrlr {
	
	@Autowired
	private IPageStructService pageStructService = null;
	
	/**
	 * 添加简单页面结构对象
	 * @param pageStruct
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addSimplePageStruct")
	@ResponseBody
	public Map<String, Object> addSimplePageStruct(UppPageStruct pageStruct) throws Exception {
		try {
			pageStruct.setPage_struct_id(UID.next());
			pageStruct.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.pageStructService.addSimplePageStruct(pageStruct);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 添加页面结构对象
	 * @param pageStruct
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public Map<String, Object> add(UppPageStruct pageStruct) throws Exception {
		try {
			pageStruct.setPage_struct_id(UID.next());
			pageStruct.setCreate_user_id(super.getCurrentUser().getUser_id());
			this.pageStructService.addPageStruct(pageStruct);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return super.doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 加载页面结构属性
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="load")
	@ResponseBody
	public Map<String, Object> load() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			UppPageStruct result = this.pageStructService.getPageStructById(params.get("page_struct_id").toString());
			return super.getJsonResultMap(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存页面结构信息
	 * @param pageStruct
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(UppPageStruct pageStruct) throws Exception {
		try {
			String user_id = super.getCurrentUser().getUser_id();
			pageStruct.setUpdate_user_id(user_id);
			Map<String, Object>  params = this.getRequestParam();
			params.put("user_id", user_id);
			this.pageStructService.savePageStruct(pageStruct,params);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 删除页面结构信息
	 * @param pageStruct_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> delete() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			String page_struct_id = String.valueOf(params.get("page_struct_id"));
			String page_md_cate_cd = String.valueOf(params.get("md_cate_cd"));
			
			this.pageStructService.deletePageStruct(page_struct_id,page_md_cate_cd);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	 
	/***
	 * 同步页面与表的关联关系
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="syncPageRelaTable")
	@ResponseBody
	public Map<String, Object> syncPageRelaTable() throws Exception {
		try {
			String user_id = super.getCurrentUser().getUser_id();
			Map<String, Object>  params = this.getRequestParam();
			params.put("user_id", user_id);
			this.pageStructService.syncPageRelaTable(params);
			return doSuccessInfoResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse(e.getMessage());
		}
	}
	
	/**
	 * 查询通用页面结构所属页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="querySimplePageParent")
	@ResponseBody
	public Object querySimplePageParent() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			return super.getJsonResultMap(this.pageStructService.querySimplePageParent(params.get("metadata_id").toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询页面结构所属页面失败:"+e.getMessage());
		}
	}
	
	/***
	 * 查询按钮所属页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryPageByBtn")
	@ResponseBody
	public Object queryPageByBtn() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			return super.getJsonResultMap(this.pageStructService.queryPageByBtn(params.get("metadata_id").toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询按钮所属页面失败:"+e.getMessage());
		}
	}
}
