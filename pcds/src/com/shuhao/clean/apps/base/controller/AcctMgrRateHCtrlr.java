package com.shuhao.clean.apps.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuhao.clean.apps.base.service.IAcctMgrRateHService;
import com.shuhao.clean.base.BaseCtrlr;

/***
 * 账户分配
 * @author bixb
 *
 */
@Controller
@RequestMapping(value="/acctMgrRateH")
public class AcctMgrRateHCtrlr extends BaseCtrlr {
	
	@Autowired
	private IAcctMgrRateHService acctMgrRateHService;

    /**
     * 客户经理列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="queryAcctMgrRateH")
    @ResponseBody
    public Object queryAcctMgrRateH() throws Exception {
        
        try {
			Map<String, Object> params = this.getRequestParam();
			
			List<Map<String, Object>> result = acctMgrRateHService.queryAcctMgrRateH(params);
			return doJSONResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("failure", Boolean.valueOf(true));
			result.put("info", "查询失败:" + e.getMessage());
			return result;
		}
    }
    
    
    @RequestMapping(value="saveBatchAcctMgrRateH")
    @ResponseBody
    public Object saveAcctMgrRateH() throws Exception {
        
        try {
			Map<String, Object> params = this.getRequestParam();
			
			acctMgrRateHService.saveBatchAcctMgrRateH(params);
			return doSuccessInfoResponse("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			return doFailureInfoResponse("保存失败:" + e.getMessage());
		}
        
    }
    
    
}
