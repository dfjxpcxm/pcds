package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

/**
 * 校验规则维护
 * @author Ning
 *
 */
public interface IMetaSelectorService {

	/**
	 * 查询状态列表
	 * @return
	 */
	List<Map<String,Object>> listTemplateStatus(Map<String,Object> map);
}
