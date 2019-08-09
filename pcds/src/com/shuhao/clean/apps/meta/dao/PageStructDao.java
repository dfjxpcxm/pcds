package com.shuhao.clean.apps.meta.dao;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppPageStruct;

/**
 * 
 * 类描述: 元数据[页面结构]dao接口
 * @author bixb
 *  
 */
@MyBatisDao
public interface PageStructDao {
	
	/**
	 * 添加页面结构对象
	 * @param pageStruct
	 * @throws Exception
	 */
	public void addPageStruct(UppPageStruct pageStruct) throws Exception;
	
	
	/**
	 * 根据id获取页面结构对象
	 * @param page_struct_id
	 * @return
	 * @throws Exception
	 */
	public UppPageStruct getPageStructById(String page_struct_id) throws Exception;
	
	
	/**
	 * 保存页面结构对象
	 * @param pageStruct
	 * @throws Exception
	 */
	public void savePageStruct(UppPageStruct pageStruct) throws Exception;
	
	/**
	 * 删除页面结构对象
	 * @param PageStruct_id
	 * @throws Exception
	 */
	public void deletePageStruct(String page_struct_id) throws Exception;
	
	/**
	 * 查询通用页面结构所属页面
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> querySimplePageParent(String metadata_id) throws Exception;
	
	/**
	 * 查询按钮所属页面
	 * @param button_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryPageByBtn(String button_id) throws Exception;


	/**删除模板页面关联关系
	 * @param page_struct_id
	 */
	public void deleteTmplPageRela(String page_struct_id);
}
