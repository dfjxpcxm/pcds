package com.shuhao.clean.apps.base.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.base.service.ICustMgrService;
import com.shuhao.clean.base.BaseCtrlr;
import com.shuhao.clean.utils.PageResult;

@Controller
@RequestMapping(value="/custMgr")
public class CustMgrCtrlr extends BaseCtrlr {
	
	@Autowired
	private ICustMgrService custMgrService;

    /**
     * 客户经理列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="queryCustMgrList")
    @ResponseBody
    public Object queryCustMgrList() throws Exception {
        
        try {
			Map<String, Object> params = this.getRequestParam();
			if(!params.containsKey("bank_org_id")){
				params.put("bank_org_id", this.getCurrentUser().getBank_org_id());
			}
			params.put("busi_line_id", this.getCurrentUser().getBusi_line_id());
			this.insertPageParamToMap(params);
			PageResult<Map<String, Object>> result = custMgrService.queryCustMgrList(params);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
    }
    
    
}
