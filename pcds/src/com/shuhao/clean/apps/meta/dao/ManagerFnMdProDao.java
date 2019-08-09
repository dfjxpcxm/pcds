package com.shuhao.clean.apps.meta.dao;


import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.UppFnMetadataProperty;

@MyBatisDao
public interface ManagerFnMdProDao  {
	//增加元数据属性
	void addFnMdPro(UppFnMetadataProperty property);
	//修改元数据属性
	void updateFnMdPro(UppFnMetadataProperty property);
	//删除元数据属性
	void deleteFnMdPro(UppFnMetadataProperty property);
	//删除元数据属性根据父级级袁术
	void deleteFnMdProByParent(UppFnMetadataProperty property);
	//查询元数据属性分页
	List<UppFnMetadataProperty> listFnMdProPage(Map<String,Object> map);
	//查询元数据属性总数
	int listFnMdProTotal(Map<String,Object> map);
	/**查询业务类型代码
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> listBizType(Map<String, Object> params);
	/**
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> fnColumns(Map<String, Object> params);
	/**
	 * @param params
	 */
	void updateDisOrder(Map<String, Object> params);
	
}
