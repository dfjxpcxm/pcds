package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;

@MyBatisDao
public interface MetaSelectorDao {
	/**
	 * 查询状态列表
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> listTemplateStatus(Map<String,Object> map);
}
