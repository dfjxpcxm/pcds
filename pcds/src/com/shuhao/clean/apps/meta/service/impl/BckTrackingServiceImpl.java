package com.shuhao.clean.apps.meta.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.BckTrackingDao;
import com.shuhao.clean.apps.meta.dao.BckTrackingMetaDao;
import com.shuhao.clean.apps.meta.entity.FnEvent;
import com.shuhao.clean.apps.meta.service.IBckTrackingService;
import com.shuhao.clean.base.BaseJdbcService;
import com.shuhao.clean.constant.ExtConstant;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.xml.XmlUtil;
@Service(value="bckTrackingService")
public class BckTrackingServiceImpl extends BaseJdbcService implements IBckTrackingService {
	
	@Autowired
	private BckTrackingDao bakTrackDao;
	
	@Autowired
	private BckTrackingMetaDao bakTrackMetaDao;
	

	public void executeMetaData(Map<String, Object> params) throws Exception {
		bakTrackMetaDao.executeMetaData(params);
	}
	
	public List<Map<String, Object>> getMetaData(Map<String,Object> params)throws Exception {
		return GlobalUtil.lowercaseListMapKey(bakTrackMetaDao.getMetaData(params));
	}
	
	public int getMetaDataCounts(Map<String,Object> params) throws Exception{
		return bakTrackMetaDao.getMetaDataCounts(params);
	}
	
	public String getDimLinkSql(Map<String,Object> params) throws Exception{
		return bakTrackDao.getDimLinkSql(params);
	}
	
	//获取纬表查询的数据
	public  List<Map<String,Object>> getDimLinkForTree(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getDimLinkForTree(params));
	}

	public List<Map<String, Object>> getDimLinkByComponentId(Map<String, Object> params) throws Exception {
		return GlobalUtil.lowercaseListMapKey(bakTrackMetaDao.getDimLinkDataByComponentId(params));
	}
	
	public List<Map<String,Object>> getMetaDataById(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackMetaDao.getMetaDataById(params));
	}


	public List<Map<String, Object>> getMetaByTemplateId(Map<String,Object> params)
			throws Exception {
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getMetaByTemplateId(params));
	}
	
	//根据模板获取分类元素
	public List<Map<String,Object>> getMetaTypeByTemplateId(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getMetaTypeByTemplateId(params));
	}
	
	//根据模板获取查询菜单元素
	
	public List<Map<String,Object>> getMenuByTemplateId(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getMenuByTemplateId(params));
	}
	
	//根据模板获取按钮元素
	
	public List<Map<String,Object>> getButtonByTemplateId(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getButtonByTemplateId(params));
	}
	
	//根据分类获取表单元素
	
	public List<Map<String,Object>> getMetaByTypeId(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getMetaByTypeId(params));
	}
	

	public List<Map<String, Object>> getTemplateTree(Map<String,Object> params)
			throws Exception {
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getTemplateTree(params));
	}
	
	public List<Map<String,Object>> getTemplateById(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getTemplateById(params));
	}
	
	public  List<Map<String,Object>>   getTmplMappingTab(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getTmplMappingTab(params));
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IBckTrackingService#getTempletByParentId(java.util.Map)
	 */
	public List<Map<String, Object>> getTempletByParentId(
			Map<String, Object> param) {
		return GlobalUtil.lowercaseListMapKey(this.bakTrackMetaDao.getTempletByParentId(param));
	}

	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IBckTrackingService#getEventResults(java.lang.String)
	 */
	public List<Map<String, Object>> getEventResults(String templateId,String metaName,String param)
			throws Exception {
		//查询单独配配置的事件
		FnEvent eventConf = (FnEvent)XmlUtil.getFnEvent(templateId,metaName);
		//查询通用事件
		if(eventConf==null){
			eventConf = (FnEvent)XmlUtil.getFnEvent(ExtConstant.COMMON_FIELD_EVENT_PID,metaName);
		}
		
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		if(eventConf!=null){
			String sql = eventConf.getInitSql();
			sql = sql.replace("#id", param);
			
			List<Map<String, Object>> dataList = GlobalUtil.lowercaseListMapKey(jdbcTemplate.queryForList(sql));
			if(dataList.size()>0){
				Map<String, Object> map = dataList.get(0);
				List<String> comps = eventConf.getComps();
				for (String name : comps) {
					String value = GlobalUtil.getStringValue(map, name);
					if(GlobalUtil.isNotNull(value)){
						//传递到页面的name是小写的
						Map<String, Object> row = new HashMap<String, Object>();
						row.put("name", name);
						row.put("value", value);
						results.add(row);
					}
				}
			}
		}
		return results;
	}

	public void batchExecSql(List<Map<String, Object>> sqlList)
			throws Exception {
		this.bakTrackMetaDao.batchExecSql(sqlList);
	}
	
	public String getTabNameByTempId(Map<String,Object> params) throws Exception{
		return this.bakTrackDao.getTabNameByTempId(params);
	}
	
	
//	public void updateFlowData(Map<String,Object> params) throws Exception{
//		this.bakTrackMetaDao.updateFlowData(params);
//	}
//	
//	public void updateBusinessData(Map<String,Object> params) throws Exception{
//		this.bakTrackMetaDao.updateBusinessData(params);
//	}
	
	public void delBusinessData(Map<String,Object> params) throws Exception{
		this.bakTrackMetaDao.delBusinessData(params);
	}

	public List<Map<String, Object>> getMetaLinkJoinSql(Map<String, Object> params) throws Exception {
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getMetaLinkJoinSql(params));
	}
	
	//导出模板
	public List<Map<String,Object>> getMetaInfoForExp(Map<String,Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getMetaInfoForExp(params));
	}
	
	public List<Map<String,Object>> getTemplateMetaInfo(String templateId) throws Exception{
		return GlobalUtil.lowercaseListMapKey(bakTrackDao.getTemplateMetaInfo(templateId));
	}
	
	/**
	 * 重构校验数据对象
	 * @param templateId
	 * @param validData
	 * @throws Exception
	 */
	public void rebuildValidData(String templateId,Map<String,Object> validData) throws Exception{
		//获取默认数据
		List<Map<String, Object>> dataList = GlobalUtil.lowercaseListMapKey(bakTrackDao.getTemplateMetaHideInfo(templateId));
		
		//完善校验参数信息，放入补录配置的隐藏信息
		for (Map<String, Object> map : dataList) {
			String key = getStringValue(map, "metadata_id")+"_"+getStringValue(map, "metadata_name");
			if(validData.containsKey(key)){
				continue;
			}else{
				if(GlobalUtil.isNotNull(getStringValue(map, "component_default"))){
					validData.put(key, getStringValue(map, "component_default"));
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IBckTrackingService#findMetaIdFromList(java.util.List)
	 */
	public String findMetaIdFromList(List<Map<String, Object>> dataList,String name)
			throws Exception {
		for (int i = dataList.size()-1; i >=0; i--) {
			Map<String, Object> map = dataList.get(i);
			if(getStringValue(map, "metadata_name").equals(name)){
				return getStringValue(map, "metadata_id");
//				dataList.remove(i);//移除
//				return metaId;
			}
		}
		return null;
	}
}
