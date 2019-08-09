package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppPageStruct;

/**
 * 
 * 类描述: 元数据[页面结构]业务逻辑接口
 * @author bixb
 * 创建时间：2015-1
 */
public interface IPageStructService {
	
	/**
	 * 添加页面结构对象
	 * 初始化页面 字段列表 表单 等
	 * @param pageStruct
	 * @throws Exception
	 */
	public void addPageStruct(UppPageStruct pageStruct) throws Exception;
	
	/**
	 * 添加简单页面结构对象
	 * @param pageStruct
	 * @throws Exception
	 */
	public void addSimplePageStruct(UppPageStruct pageStruct) throws Exception;
	
	/**
	 * 根据id获取页面结构对象
	 * @param pageStruct_id
	 * @return
	 * @throws Exception
	 */
	public UppPageStruct getPageStructById(String pageStruct_id) throws Exception;
	
	/***
	 * 保存页面结构对象
	 * @param pageStruct
	 * @param params 关联表的参数
	 * @throws Exception
	 */
	public void savePageStruct(UppPageStruct pageStruct,Map<String, Object> params) throws Exception;
	
	
	/**
	 * 删除页面结构对象
	 * @param pageStruct_id
	 * @throws Exception
	 */
	public void deletePageStruct(String pageStruct_id,String page_md_cate_cd) throws Exception;
	
	/***
	 * 同步页面与表的关联关系
	 * 同步方式:01 增量 02 覆盖
	 * 增量 为只新增没有的关联字段记录
	 * 覆盖 删除原有关联字段记录 重新初始化新的记录
	 * @param params
	 * @throws Exception
	 */
	public void syncPageRelaTable(Map<String, Object> params) throws Exception ;
 
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
}
