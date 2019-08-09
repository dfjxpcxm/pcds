package com.shuhao.clean.apps.sys.dao;

import java.util.List;
import java.util.Map;
import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.sys.entity.Measure;

@MyBatisDao
public interface AngleMeasureDao  {
	
	
	/**
	 * 查询指标
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMeasureById(Map<String, Object> params) throws Exception;
	
	/**
	 * 新增指标
	 * @param Measure
	 * @throws Exception
	 */
	public void addMeasure(Measure measure) throws Exception;
	
	/**
	 * 修改指标
	 * @param Measure
	 * @throws Exception
	 */
	public void editMeasure(Measure measure) throws Exception;
	
	/**
	 * 删除指标
	 * @param Measure
	 * @throws Exception
	 */
	public void deleteMeasure(Map<String, Object> params) throws Exception;
	
	
}
