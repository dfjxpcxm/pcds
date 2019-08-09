package com.shuhao.clean.apps.meta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.BizTypeDao;
import com.shuhao.clean.apps.meta.entity.BizType;
import com.shuhao.clean.apps.meta.entity.UppTable;
import com.shuhao.clean.apps.meta.service.IBizTypeService;
import com.shuhao.clean.base.BaseJdbcService;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.PageResult;

/**
 * 字段分类管理实现类
 * 
 * @author:       
 */
@Service(value="bizTypeService")
public class BizTypeServiceImpl  extends BaseJdbcService implements IBizTypeService {

	@Autowired
	private BizTypeDao BizTypeDao;

	public List<BizType> listMetadataBizType(Map<String, Object> map) {
		return this.BizTypeDao.listMetadataBizTypePage(map);
	}

	public void addMetaType(BizType bizType) {
		// TODO Auto-generated method stub
		this.BizTypeDao.addMetaType(bizType);
	}

	public void deleteMetaType(BizType bizType) {
		// TODO Auto-generated method stub
		this.BizTypeDao.deleteMetaType(bizType);
	}

	public void updateBizType(BizType bizType) {
		// TODO Auto-generated method stub
		this.BizTypeDao.updateBizType(bizType);
	}
}
