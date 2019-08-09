package com.shuhao.clean.apps.meta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.TemplManagerDao;
import com.shuhao.clean.apps.meta.service.ITemplManagerService;
import com.shuhao.clean.utils.GlobalUtil;


@Service(value="templManagerService")
public class TemplManagerServiceImpl implements ITemplManagerService{

	@Autowired
	private TemplManagerDao templManagerDao ;

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.ITemplManagerService#getTemplTree(java.util.Map)
	 */
	public List<Map<String, Object>> getTemplTree(Map<String, Object> map) {
		String nodeID = GlobalUtil.getStringValue(map, "nodeID");
		if("root".equals(nodeID)){
			return GlobalUtil.lowercaseListMapKey(templManagerDao.getTempls(map));
		}else{
			return GlobalUtil.lowercaseListMapKey(templManagerDao.getTemplCols(map));
		}
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.ITemplManagerService#addTmpl(java.util.Map)
	 */
	public void addTmpl(Map<String, Object> map) {
		String type = GlobalUtil.getStringValue(map, "type");
		if("templ".equals(type)){
			this.templManagerDao.addTmpl(map);
		}else{
			this.templManagerDao.addTmplCol(map);
		}
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.ITemplManagerService#deleteTempl(java.util.Map)
	 */
	public void deleteTempl(Map<String, Object> map) {
		String type = GlobalUtil.getStringValue(map, "type");
		if("templ".equals(type)){
			this.templManagerDao.deleteTempl(map);
		}else{
			this.templManagerDao.deleteTemplCol(map);
		}
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.ITemplManagerService#getTemplByID(java.util.Map)
	 */
	public List<Map<String, Object>> getTemplByID(Map<String, Object> map) {
		String type = GlobalUtil.getStringValue(map, "type");
		if("templ".equals(type)){
			return GlobalUtil.lowercaseListMapKey(templManagerDao.getTempls(map));
		}else{
			return GlobalUtil.lowercaseListMapKey(templManagerDao.getTemplCols(map));
		}
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.ITemplManagerService#editTmpl(java.util.Map)
	 */
	public void editTmpl(Map<String, Object> map) {
		String type = GlobalUtil.getStringValue(map, "type");
		if("templ".equals(type)){
			templManagerDao.editTmpl(map);
		}else{
			templManagerDao.editTmplCol(map);
		}
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.meta.service.ITemplManagerService#updateDisOrder(java.util.Map)
	 */
	public void updateDisOrder(Map<String, Object> map) {
		String tmpl_id = GlobalUtil.getStringValue(map, "tmpl_id");
		String disOrder = GlobalUtil.getStringValue(map, "orderParam");
		String[] disOrders = disOrder.split(";");
		for (int i = 0; i < disOrders.length; i++) {
			String order  = disOrders[i];
			if(GlobalUtil.isNotNull(order)){
				String[] orders =  order.split(",");
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("tmpl_id", tmpl_id);
				param.put("col_name", orders[0]);
				param.put("display_order", orders[1]);
				this.templManagerDao.updateDisOrder(param);
			}
		}
		
	}

}
