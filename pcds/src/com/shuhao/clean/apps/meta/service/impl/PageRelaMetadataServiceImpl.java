package com.shuhao.clean.apps.meta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.PageRelaMetadataDao;
import com.shuhao.clean.apps.meta.entity.UppDicFnRela;
import com.shuhao.clean.apps.meta.entity.UppTableColumn;
import com.shuhao.clean.apps.meta.service.IPageRelaMetadataService;
import com.shuhao.clean.utils.GlobalUtil;

/**
 * 
 * 类描述: 页面及页面元素关联元数据 业务逻辑实现类 
 * @author bixb
 * 创建时间：2015-1
 */
@Service
public class PageRelaMetadataServiceImpl implements IPageRelaMetadataService {
	

	@Autowired
	private PageRelaMetadataDao pageRelaMetadataDao = null ;
	
	/**
	 * 查询关联元数据
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaMetadata(Map<String, Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(this.pageRelaMetadataDao.queryRelaMetadata(params));
	}
	
	/**
	 * 增加数据字典与功能映射关系
	 * @param paramMap
	 * @throws Exception
	 */
	public void addDicFnRela(UppDicFnRela dicFn)throws Exception{
		pageRelaMetadataDao.addDicFnRela(dicFn);
	}
	
	/***
	 * 查询关联映射数据字典
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaDicData(Map<String, Object> params) throws Exception{
		return GlobalUtil.lowercaseListMapKey(pageRelaMetadataDao.queryRelaDicData(params));
	}
	
	/**
	 * 删除数据字典与功能映射关系
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteDicFnRela(UppDicFnRela dicFn)throws Exception{
		pageRelaMetadataDao.deleteDicFnRela(dicFn);
	}
	
	/**
	 * 根据关联表ID和页面ID获取增量字段列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<UppTableColumn> getNoRelaTableColumns(Map<String, Object> params) throws Exception{
		return pageRelaMetadataDao.getNoRelaTableColumns(params);
	}
	
}
