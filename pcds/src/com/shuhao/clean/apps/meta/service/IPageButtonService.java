package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppPageButton;

/**
 * 
 * 类描述: 元数据[页面按钮]业务逻辑接口
 * @author bixb
 * 创建时间：2015-1
 */
public interface IPageButtonService {
	
	/**
	 * 添加页面按钮对象
	 * @param pageButton
	 * @throws Exception
	 */
	public void addPageButton(UppPageButton pageButton) throws Exception;
	
	/**
	 * 根据id获取页面按钮对象
	 * @param button_id
	 * @return
	 * @throws Exception
	 */
	public UppPageButton getPageButtonById(String button_id) throws Exception;
	
	/**
	 * 保存页面按钮对象
	 * @param pageButton
	 * @throws Exception
	 */
	public void savePageButton(UppPageButton pageButton) throws Exception;
	
	
	/**
	 * 删除页面按钮对象
	 * @param button_id
	 * @throws Exception
	 */
	public void deletePageButton(String button_id) throws Exception;
	
	/**
	 * 批量删除页面按钮对象
	 * @param metadata_id
	 * @throws Exception
	 */
	public void deletePageButtonBatch(String[] metadata_ids) throws Exception;
	
	/**
	 * 查询关联元数据
	 * @param button_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaMetadata(Map<String, Object> params) throws Exception;
	
	public List<Map<String, Object>> queryIcons() throws Exception;
 
	/**
	 * 查询页面按钮对象列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryPageButtons(Map<String, Object> params) throws Exception;
	
	/**
	 * 获取按钮顺序列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getButtonsForDisOrder(Map<String, Object> params) throws Exception;
	
	/**
	 * 更新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateDisOrder(Map<String, Object> params) throws Exception;
	
}
