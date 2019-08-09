/**
 * FileName:     QueryTmplCtrlr.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-6-12 下午4:31:13 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-6-12       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.query.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.query.entity.QueryDsMeta;
import com.shuhao.clean.apps.query.entity.UserQueryDsMeta;
import com.shuhao.clean.apps.query.service.QueryDsMetaService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.GlobalUtil;

/**
 * @Description:   TODO
 * 
 * @author:         
 * @param <DimLinkAjaxService>
 */
@Controller
@RequestMapping("/queryTmplMeta")
public class QueryTmplMetaCtrlr<DimLinkAjaxService> extends BaseCtrlr{

	@Autowired
	private QueryDsMetaService queryDsMetaService;
	/**
	 * 查询ds对应的元数据信息
	 * @return
	 */
	@RequestMapping(value="getDsMeta")
	@ResponseBody
	public Object getDsMeta() throws Exception{
		Map<String,Object> map = this.getRequestParam();
		try {
			return doJSONResponse(this.queryDsMetaService.getDsMetaMap(map));
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询失败："+e.getMessage());
		}
	}  
	
	@RequestMapping(value="getDimSource")
	@ResponseBody
	public Object getDimSource() throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list=GlobalUtil.lowercaseListMapKey(queryDsMetaService.getDimSource());
			result.put("result",list);
		} catch (Exception e) {
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
		return result;
	}

	/**
	 * 更新排序
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateOrder")
	@ResponseBody
	public Object updateOrder() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			params.put("user_id", this.getCurrentUser().getUser_id());
			this.queryDsMetaService.updateOrder(params);
			return doSuccessInfoResponse("排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("排序失败:"+e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param qdm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateDsMeta")
	@ResponseBody
	public Object updateDsMeta(QueryDsMeta qdm) throws Exception {
		try {
			if(qdm.getIs_query().equals("N")){
				qdm.setLink_type_cd("");
			}
			if(qdm.getIs_dim().equals("N")){
				qdm.setDim_cd("");
			}
			queryDsMetaService.updateDsMeta(qdm);
	       return doSuccessInfoResponse("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("保存失败:"+e.getMessage());
		}
	}
	
	/**
	 * 查询用户关联的数据源
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUserDsMeta")
	@ResponseBody
	public Object getUserDsMeta() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			params.put("userId", getCurrentUser().getUser_id());
			return doJSONResponse(queryDsMetaService.getUserDsMeta(params));
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("查询失败:"+e.getMessage());
		}
	}

	/**
	 * 更新用户元数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateUserDsMeta")
	@ResponseBody
	public Object updateUserDsMeta(UserQueryDsMeta userDsMeta) throws Exception {
		try {
			userDsMeta.setUser_id(getCurrentUser().getUser_id());
			if(userDsMeta.getIs_query().equals("N")){
				userDsMeta.setLink_type_cd("");
			}
			this.queryDsMetaService.updateUserDsMeta(userDsMeta);
			return doSuccessInfoResponse("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("修改失败:"+e.getMessage());
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="updateUserDsMetaOrder")
	@ResponseBody
	public Object updateUserDsMetaOrder() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			params.put("userId", this.getCurrentUser().getUser_id());
			this.queryDsMetaService.updateUserDsMetaOrder(params);
			return doSuccessInfoResponse("排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("排序失败:"+e.getMessage());
		}
	}
}
