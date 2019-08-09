package com.shuhao.clean.apps.meta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.MetaSelectorDao;
import com.shuhao.clean.apps.meta.service.IMetaSelectorService;
import com.shuhao.clean.apps.validate.dao.CheckRuleDao;
import com.shuhao.clean.utils.GlobalUtil;

/**
 * 元数据selector
 * @author Ning
 *
 */
@Service(value="metaSelectorService")
public class MetaSelectorServiceImpl  implements IMetaSelectorService{

	@Autowired
	private MetaSelectorDao metaSelectorDao;
	public List<Map<String, Object>> listTemplateStatus(Map<String,Object> map) {
		return GlobalUtil.lowercaseListMapKey(this.metaSelectorDao.listTemplateStatus(map));
	}

}
