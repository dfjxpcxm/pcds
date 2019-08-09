package com.shuhao.clean.apps.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.base.dao.AcctMgrRateHDao;
import com.shuhao.clean.apps.base.service.IAcctMgrRateHService;
import com.shuhao.clean.base.BaseService;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;

/**
 * 账户分配Service接口
 * @author bixb
 *
 */
@Service(value="acctMgrRateHService")
public class AcctMgrRateHServiceImpl extends BaseService implements IAcctMgrRateHService{
	
	@Autowired
	private AcctMgrRateHDao acctMgrRateHDao;

	public List<Map<String, Object>> queryAcctMgrRateH(
			Map<String, Object> paramMap) throws Exception {
		List<Map<String, Object>> dataList = acctMgrRateHDao.queryAcctMgrRateH(paramMap);
		return GlobalUtil.lowercaseListMapKey(dataList);
	}

	public void saveBatchAcctMgrRateH(Map<String, Object> paramMap)
			throws Exception {
		//删除原有关系
		acctMgrRateHDao.deleteAcctMgrRateH(paramMap);
		
		String rateInfo = String.valueOf(paramMap.get("allot_rates"));
		if(rateInfo == null || "".equals(rateInfo)){
			return;
		}
		//封装集合
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String[] rateInfos = rateInfo.split("@");
		String rateStr =null;
		Map<String, Object> relaObj = null;
		for(int i = 0;i<rateInfos.length;i++){
			rateStr =  rateInfos[i];
			relaObj = new HashMap<String, Object>();
			relaObj.put("cust_mgr_id", rateStr.split(",")[0]);
			relaObj.put("alloc_rate", rateStr.split(",")[1]);
			relaObj.put("begin_date", String.valueOf(paramMap.get("begin_date")));
			relaObj.put("acct_id", String.valueOf(paramMap.get("acct_id")));
			relaObj.put("begin_date", String.valueOf(paramMap.get("begin_date")));
			relaObj.put("business_no", UID.next());
			list.add(relaObj);
		}
		
		//新增关系
		acctMgrRateHDao.addAcctMgrRateH(list);
		
	}
	
	
	
}
