package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;


public interface ITemplManagerService {

	/**
	 * @param map 
	 * @return 
	 * 
	 */
	List<Map<String, Object>> getTemplTree(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	void addTmpl(Map<String, Object> map);

	/**
	 * @param map
	 */
	void deleteTempl(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getTemplByID(Map<String, Object> map);

	/**
	 * @param map
	 */
	void editTmpl(Map<String, Object> map);

	/**
	 * @param map
	 */
	void updateDisOrder(Map<String, Object> map);
	
}
