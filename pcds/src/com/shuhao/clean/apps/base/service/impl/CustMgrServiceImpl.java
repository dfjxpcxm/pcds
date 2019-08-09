package com.shuhao.clean.apps.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.base.dao.CustMgrDao;
import com.shuhao.clean.apps.base.service.ICustMgrService;
import com.shuhao.clean.base.BaseService;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.PageResult;

/**
 * 查询条件控件Service接口
 * @author chenxd
 *
 */
@Service(value="custMgrService")
public class CustMgrServiceImpl extends BaseService implements ICustMgrService{
	
	@Autowired
	private CustMgrDao custMgrDao;

	public PageResult<Map<String, Object>> queryCustMgrList(Map<String, Object> paramMap) throws Exception {
		
		PageResult<Map<String, Object>> pageList = new PageResult<Map<String,Object>>();
		List<Map<String, Object>> dataList = custMgrDao.queryCustMgrList(paramMap);
		dataList = GlobalUtil.lowercaseListMapKey(dataList);
		pageList.setResults(dataList);
		pageList.setTotalCount(custMgrDao.queryCustMgrListTotalCount(paramMap));
		
		return pageList;
	}
	
	
}
