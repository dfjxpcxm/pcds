package com.shuhao.clean.apps.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.base.dao.SelectorDao;
import com.shuhao.clean.apps.base.service.ISelectorService;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.base.BaseService;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.PageResult;


/**
 * 查询条件控件Service接口
 * @author chenxd
 *
 */
@Service(value="selectorService")
public class SelectorServiceImpl extends BaseService implements ISelectorService{
	
	@Autowired
	private SelectorDao selectorDao;
	
	/**
	 * 查询业务条线
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBusiLineList(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryBusiLineList(params));
	}
	
	/**
	 * 职位类型
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryJobTitle(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryJobTitle(params));
	}
	
	/**
	 * 岗位
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryPostType(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryPostType(params));
	}
	
	/**
	 * 技术职称
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryTechTitle(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryTechTitle(params));
	}
	
	/**
	 * 性别
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryGender(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryGender(params));
	}
	
	/**
	 * 民族
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryEthnicity(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryEthnicity(params));
	}
	
	
	/**
	 * 学历 教育背景
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryEduBack(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryEduBack(params));
	}
	
	/**
	 * 用户状态
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMgrStatus(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryMgrStatus(params));
	}
	
	/**
	 * 序列
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryCustJobSeq(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryCustJobSeq(params));
	}
	
	/**
	 * 返回月份
	 */
	public List<Map<String, Object>> getMonths()throws Exception{
		return toLowerMapList(selectorDao.listMonth());
	}
	/**
	 *  指标体系树
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMeasureTree(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryMeasureTree(params));
	}

	/**
	 *  机构类型
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBankOrgType(Map<String, Object> params)throws Exception{
		return toLowerMapList(selectorDao.queryBankOrgType(params));
	}
	
	public List<Map<String, Object>> getCategoryType() throws Exception {
		return null;
	}
	
	public PageResult<Map<String,Object>> getCustMgrs(Map<String, Object> paramMap)
			throws Exception {
		
		boolean isZh = GlobalUtil.isZh_cn(GlobalUtil.getStringValue( paramMap,"custMgrId"));
		paramMap.put("isZh", isZh);
		
		List<Map<String, Object>> results = toLowerMapList(selectorDao.getCustMgrs(paramMap));
		int totalCount = selectorDao.getCustMgrsCount(paramMap);
		return new PageResult<Map<String,Object>>(totalCount, results);
	}

	public List<Map<String, Object>> queryUppDatabaseType(Map<String, Object> paramMap) throws Exception {
		return toLowerMapList(selectorDao.queryUppDatabaseType(paramMap));
	}

	public List<UppMetadata> getMetadataStore(String metadataId) throws Exception {
//		TreeStore metadataStore = new TreeStore();
		List<UppMetadata> metadataList = this.selectorDao.metadataList(metadataId);
		return metadataList;
//		for (UppMetadata uppMetadata : metadataList) {
//			metadataStore.addTreeNode(uppMetadata);
//		}
//		List<Map<String,Object>> metadataList = toLowerMapList(this.selectorDao.metadataList());
//		for (int i = 0; i < metadataList.size(); i++) {
//			Map<String,Object> map = metadataList.get(i);
//			UppMetadata uppMetadata = new UppMetadata();
//			uppMetadata.setMetadata_id((String)map.get("metadata_id"));
//			uppMetadata.setMetadata_name((String)map.get("metadata_name"));
//			uppMetadata.setParent_metadata_id((String)map.get("parent_metadata_id"));
//			metadataStore.addTreeNode(uppMetadata);
//		}
//		return metadataStore;
	}

	public List<Map<String, Object>> getBlmbList(String tmplId)
			throws Exception {
		return toLowerMapList(this.selectorDao.getBlmbList(tmplId));
	}
	
	public List<Map<String, Object>> queryUppButtonFunction(Map<String, Object> paramMap) throws Exception {
		return toLowerMapList(selectorDao.queryUppButtonFunction(paramMap));
	}
	
	public List<Map<String, Object>> getUppButtonFunction(Map<String, Object> paramMap) throws Exception {
		return toLowerMapList(selectorDao.getUppButtonFunction(paramMap));
	}
	
	public List<Map<String, Object>> queryUppDimSource(Map<String, Object> paramMap) throws Exception {
		return toLowerMapList(selectorDao.queryUppDimSource(paramMap));
	}
	
}
