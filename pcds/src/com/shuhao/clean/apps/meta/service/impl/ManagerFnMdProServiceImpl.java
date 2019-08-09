package com.shuhao.clean.apps.meta.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.flow.dao.FlowDao;
import com.shuhao.clean.apps.meta.dao.ManagerFnMdProDao;
import com.shuhao.clean.apps.meta.dao.UppMetadataDao;
import com.shuhao.clean.apps.meta.entity.UppFnMetadataProperty;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.service.IManagerFnMdProService;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.PageResult;
import com.shuhao.clean.utils.UID;

@Service(value="managerFnMdProService")
public class ManagerFnMdProServiceImpl implements IManagerFnMdProService{
	@Autowired
	private ManagerFnMdProDao managerFnMdProDao;
	@Autowired
	private UppMetadataDao uppMetadataDao;
	@Autowired
	private FlowDao flowDao;
	/**
	 * 增加功能元数据属性
	 * @param property
	 * @throws Exception
	 */
	public void addFnMdPro(UppFnMetadataProperty property)throws Exception{
		this.managerFnMdProDao.addFnMdPro(property);
	}
	/**
	 * 删除功能元数据属性
	 * @param property
	 * @throws Exception
	 */
	public void deleteFnMdPro(UppFnMetadataProperty property)throws Exception{
		UppMetadata uppMetadata = new UppMetadata();
		uppMetadata.setMetadata_id(property.getMetadata_id());
		this.managerFnMdProDao.deleteFnMdPro(property);
		this.uppMetadataDao.delThemeMeta(uppMetadata);
		this.flowDao.delAndAddBlmbGn(property.getMetadata_id());
	}
	/**
	 * 修改功能元数据属性
	 * @param property
	 * @throws Exception
	 */
	public void editFnMdPro(UppFnMetadataProperty property)throws Exception{
		this.managerFnMdProDao.updateFnMdPro(property);
		this.flowDao.delAndAddBlmbGn(property.getMetadata_id());
	}
	/**
	 * 分页查询功能元数据属性
	 * @param map
	 * @return
	 */
	public PageResult<UppFnMetadataProperty> listFnMdPro(Map<String,Object> map){
		PageResult<UppFnMetadataProperty> pr = new PageResult<UppFnMetadataProperty>();
		pr.setResults(this.managerFnMdProDao.listFnMdProPage(map));
		pr.setTotalCount(this.managerFnMdProDao.listFnMdProTotal(map));
		return pr;
	}
	public String addMetadata(SysUserInfo user,UppFnMetadataProperty property) throws Exception {
		String metadata_id = UID.next("cl");
		List<UppMetadata> list = new ArrayList<UppMetadata>();
		UppMetadata metadata = new UppMetadata();
		metadata.setMetadata_id(metadata_id);
		metadata.setMetadata_name(property.getMetadata_name());
//		metadata.setParent_metadata_id(property.getMetadata_id());
//		metadata.setMetadata_cate_code("2010101");
//		metadata.setMetadata_desc(property.getComponent_desc());
//		metadata.setCreate_user_id(user.getUserID());
//		metadata.setStatus_code("02");
		metadata.setDisplay_order("0");
		list.add(metadata);
		this.uppMetadataDao.addMetadataList(list);
		property.setMetadata_id(metadata_id);
		this.managerFnMdProDao.addFnMdPro(property);
		this.flowDao.delAndAddBlmbGn(metadata_id);
		return metadata_id;
	}
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IManagerFnMdProService#listBizType(java.util.Map)
	 */
	public List<Map<String, Object>> listBizType(Map<String, Object> params) {
		return GlobalUtil.lowercaseListMapKey(this.managerFnMdProDao.listBizType(params));
	}
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IManagerFnMdProService#fnColumns(java.util.Map)
	 */
	public List<Map<String, Object>> fnColumns(Map<String, Object> params) {
		return GlobalUtil.lowercaseListMapKey(this.managerFnMdProDao.fnColumns(params));
	}
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.IManagerFnMdProService#updateDisOrder(java.util.Map)
	 */
	public void updateDisOrder(Map<String, Object> params) {
		String disOrder = GlobalUtil.getStringValue(params, "orderParam");
		String[] disOrders = disOrder.split(";");
		for (int i = 0; i < disOrders.length; i++) {
			String order  = disOrders[i];
			if(GlobalUtil.isNotNull(order)){
				String[] orders =  order.split(",");
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("metadata_id", orders[0]);
				map.put("display_order", orders[1]);
				this.managerFnMdProDao.updateDisOrder(map);
			}
		}
	}
}
