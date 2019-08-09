package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;

@MyBatisDao
public interface DimLinkAjaxDao  {
	

	public List<Map<String, Object>> listDimLinksPage(Map<String, Object> params);

	public int listDimLinksTotal(Map<String, Object> params);

	public void addDimLink(Map<String, Object> map);


	public List<Map<String, Object>> queryForList(Map<String, Object> sql);

	public void editDimLink(Map<String, Object> map);
	
	public void deleteDimLink(Map<String, Object> map);
	
	
}
