package com.shuhao.clean.apps.base.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rx.util.tree.TreeStore;
import com.shuhao.clean.apps.base.dao.DataStoreDao;
import com.shuhao.clean.apps.sys.entity.SysResourceInfo;
import com.shuhao.clean.base.BaseCacheService;

/**
 * 内存仓库缓存组件,使用ecache 缓存对象
 * <br>
 * 说明：缓存的菜单,请不要频繁修改,非线程安全 ServerConstant.RESOURCE_AUTO_LOAD 自动加载菜单更新
 * @author chenxd
 *
 */
@Component
public class DataStore extends BaseCacheService{
	
	@Autowired
	private DataStoreDao dataStoreDao = null;
	
	//private final String bank_cache_key = "bank_cache_key";
	private final String SysResource_cache_key = "SysResource_cache_key";
	private final String SysResource_map_cache_key = "SysResource_map_cache_key";
	//private final String measure_cache_key = "measure_cache_key";
	//private final String product_cache_key = "product_cache_key"; //产品缓存
	
	public void initCache() throws Exception {
		this.getSysResourceStore();
		this.getSysResourceMap();
	}
	
	
	//加载菜单树
	public TreeStore getSysResourceStore()throws Exception{
		TreeStore SysResourceStore = super.getCacheObject(this.SysResource_cache_key, TreeStore.class);
		if(SysResourceStore == null) {
			SysResourceStore = new TreeStore();
			List<SysResourceInfo> sysResourceList = dataStoreDao.getResourceList();
			for (SysResourceInfo r : sysResourceList) {
				SysResourceStore.addTreeNode(r);
			}
			super.addToCache(this.SysResource_cache_key, SysResourceStore);
		}
		return SysResourceStore;
	}
	
	public void reloadSysResourceStore()throws Exception {
		super.expireCache(this.SysResource_cache_key);
		this.getSysResourceStore();
	}
	
	//加载系统菜单MAP
	@SuppressWarnings("unchecked")
	public Map<String, String> getSysResourceMap() throws Exception {
		Map<String, String> SysResourceMap = super.getCacheObject(this.SysResource_map_cache_key, Map.class);
		if(SysResourceMap == null) {
			SysResourceMap = new HashMap<String,String>();
			List<SysResourceInfo> SysResourceList = dataStoreDao.getResourceList();
			for (SysResourceInfo r : SysResourceList) {
				//获得全部功能菜单链接映射关系
				String url = null == r.getHandler() ? "" : r.getHandler() ;
				if(!"".equals(url)){
					SysResourceMap.put(r.getResource_id(), r.getHandler());
				}
			}
			super.addToCache(this.SysResource_map_cache_key, SysResourceMap);
		}
		return SysResourceMap;
	} 
	
	public void reloadSysResourceMap() throws Exception {
		super.expireCache(this.SysResource_map_cache_key);
		this.getSysResourceMap();
	}
}
