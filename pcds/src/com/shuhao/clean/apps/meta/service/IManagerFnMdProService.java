package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.UppFnMetadataProperty;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.utils.PageResult;

public interface IManagerFnMdProService {
	/**
	 * 增加功能元数据属性
	 * @param property
	 * @throws Exception
	 */
	public void addFnMdPro(UppFnMetadataProperty property)throws Exception;
	/**
	 * 删除功能元数据属性
	 * @param property
	 * @throws Exception
	 */
	public void deleteFnMdPro(UppFnMetadataProperty property)throws Exception;
	/**
	 * 修改功能元数据属性
	 * @param property
	 * @throws Exception
	 */
	public void editFnMdPro(UppFnMetadataProperty property)throws Exception;
	/**
	 * 分页查询功能元数据属性
	 * @param map
	 * @return
	 */
	public PageResult<UppFnMetadataProperty> listFnMdPro(Map<String,Object> map);
	/**
	 * 添加元数据
	 * @param user 
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String addMetadata(SysUserInfo user, UppFnMetadataProperty property) throws Exception;
	/**
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> listBizType(Map<String, Object> params);
	/**查询字段列表
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> fnColumns(Map<String, Object> params);
	/**
	 * @param params
	 * @return
	 */
	public void updateDisOrder(Map<String, Object> params);
}
