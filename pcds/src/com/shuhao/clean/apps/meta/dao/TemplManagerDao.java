package com.shuhao.clean.apps.meta.dao;


import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;

@MyBatisDao
public interface TemplManagerDao  {

	/**
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> getTempls(Map<String, Object> map);

	/**
	 * @param map
	 * @return 
	 */
	List<Map<String, Object>> getTemplCols(Map<String, Object> map);

	/**
	 * @param map
	 */
	void addTmpl(Map<String, Object> map);

	/**
	 * @param map
	 */
	void addTmplCol(Map<String, Object> map);

	/**
	 * @param map
	 */
	void deleteTempl(Map<String, Object> map);

	/**
	 * @param map
	 */
	void deleteTemplCol(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	void editTmpl(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	void editTmplCol(Map<String, Object> map);

	/**
	 * @param param
	 */
	void updateDisOrder(Map<String, Object> param);
	
	
}
