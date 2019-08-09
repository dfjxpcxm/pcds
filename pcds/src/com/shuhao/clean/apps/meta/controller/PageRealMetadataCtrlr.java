package com.shuhao.clean.apps.meta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.meta.service.IPageRelaMetadataService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.constant.MetaConstant;
import com.shuhao.clean.utils.GlobalUtil;

/**
 * 类描述: 页面及页面元素关联元数据action
 * 独立出来 公用 
 * @author bixb
 * 创建时间：2015-1
 */
@Controller
@RequestMapping(value="/metadata/pageRelaMetadata")
public class PageRealMetadataCtrlr extends BaseCtrlr {
	
	@Autowired
	private IPageRelaMetadataService pageRelaMetadataService = null;
	
	/**
	 * 查询关联元数据,默认给MetaConstant.DEFAULT_METADATA_ID
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryRelaMetadata")
	@ResponseBody
	public Object queryRelaMetadata() throws Exception {
		try {
			Map<String, Object> params = this.getRequestParam();
			List<Map<String, Object>> dataList =  pageRelaMetadataService.queryRelaMetadata(params);
			if("XLS".equals(GlobalUtil.getStringValue(params,"md_cate_cd"))){
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("rela_metadata_id", MetaConstant.DEFAULT_METADATA_ID);
				data.put("rela_metadata_name", "默认全部字段");
				dataList.add(0,data);
			}
			return getJsonResultMap(dataList); 
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("获取关联元数据出错："+e.getMessage());
		}
		
	}
}
