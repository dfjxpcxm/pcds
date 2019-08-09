package com.shuhao.clean.apps.base.dao;

import java.util.List;
import com.shuhao.clean.annotation.MyBatisDao;
import com.shuhao.clean.apps.base.entity.DmdBankInterOrg;
import com.shuhao.clean.apps.base.entity.DmdProduct;
import com.shuhao.clean.apps.sys.entity.Measure;
import com.shuhao.clean.apps.sys.entity.SysResourceInfo;

/**
 * 内存仓库Dao
 * @author chenxd
 *
 */
@MyBatisDao
public interface DataStoreDao {
	
	//获取机构树表
	public List<DmdBankInterOrg> getBankList()throws Exception;
	
	//产品列表 
	public List<DmdProduct> getProductList()throws Exception;
	
	//获取菜单树列表
	public List<SysResourceInfo> getResourceList()throws Exception;
	
	//获取指标树列表
	public List<Measure> getMeasureList()throws Exception;
}
