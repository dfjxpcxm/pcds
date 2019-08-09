package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.utils.PageResult;
public interface IDimLinkAjaxService {

	public PageResult<Map<String, Object>> listDimLinks(
			Map<String, Object> params);

	public void addDimLink(Map<String, Object> map);

	public List<? extends Object> queryFieldDetail(Map<String, Object> map);

	public void editDimLink(Map<String, Object> map);

	public List<? extends Object> queryForDimTree(Map<String, String> paramsMap) throws Exception;

	public int checkLink(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>>  findRootName(Map<String, String> paramsMap) throws Exception;

	public void deleteDimLink(Map<String, Object> map);
	
}
