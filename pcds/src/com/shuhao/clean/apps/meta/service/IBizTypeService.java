package com.shuhao.clean.apps.meta.service;

import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.BizType;
import com.shuhao.clean.apps.meta.entity.UppTable;
import com.shuhao.clean.utils.PageResult;

/**
 * 字段分类接口
 * @author 
 *
 */
public interface IBizTypeService {

	List<BizType> listMetadataBizType(Map<String, Object> map);

	void addMetaType(BizType bizType);

	void deleteMetaType(BizType bizType);

	void updateBizType(BizType bizType);

}
