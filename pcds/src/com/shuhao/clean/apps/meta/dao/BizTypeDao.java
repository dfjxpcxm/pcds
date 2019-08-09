package com.shuhao.clean.apps.meta.dao;
import java.util.List;
import java.util.Map;

import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.meta.entity.BizType;
import com.shuhao.clean.apps.meta.entity.UppTable;

/**
 * 
 * @Description:   TODO
 * 
 * @author:       
 */
@MyBatisDao
public interface BizTypeDao {

	List<BizType> listMetadataBizTypePage(Map<String, Object> map);
	
	int listMetadataBizTypeTotal(Map<String, Object> map);
	
	void addMetaType(BizType bizType);

	void deleteMetaType(BizType bizType);

	void updateBizType(BizType bizType);

}