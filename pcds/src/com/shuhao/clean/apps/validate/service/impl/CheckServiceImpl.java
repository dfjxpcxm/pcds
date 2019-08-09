/**
 * FileName:     ICheckServiceImpl.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-28 下午7:42:40 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-28       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.validate.dao.CheckRuleUtilsDao;
import com.shuhao.clean.apps.validate.dao.MetaDataUtilsDao;
import com.shuhao.clean.apps.validate.entity.CheckRule;
import com.shuhao.clean.apps.validate.service.ICheckService;
import com.shuhao.clean.apps.validate.vrules.IValidator;
import com.shuhao.clean.apps.validate.vrules.domain.ValidResult;
import com.shuhao.clean.apps.validate.vrules.msg.IMessage;
import com.shuhao.clean.apps.validate.vrules.msg.InvalidMessage;
import com.shuhao.clean.apps.validate.vrules.msg.MyMessage;
import com.shuhao.clean.apps.validate.vrules.support.MetaValidator;
import com.shuhao.clean.apps.validate.vrules.vobject.VCell;
import com.shuhao.clean.apps.validate.vrules.vobject.VRow;
import com.shuhao.clean.base.BaseJdbcService;
import com.shuhao.clean.constant.MetaConstant;
import com.shuhao.clean.utils.GlobalUtil;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
@Service(value="checkService")
public class CheckServiceImpl extends BaseJdbcService implements ICheckService {
	
	@Autowired
	private CheckRuleUtilsDao checkRuleUtilsDao;
	
	@Autowired
	private MetaDataUtilsDao metaDataUtilsDao;
	
	/**
	 * 普通单值校验 
	 * @param metaId 元数据
	 * @param value  元数据的值
	 * @throws Exception
	 */
	public List<IMessage> cellValid(String metaId,String value,Map<String, Object> context)throws Exception{
		UppMetadata meta = metaDataUtilsDao.getUppMetadata( metaId);
		VCell cell = new VCell(meta,value);
		cell.setCkRules(getCheckRules(meta.getMetadata_id())); 
		cell.setContext(context);
		return cell.doCheck();
	}
		
	/**
	 * 批量分解单值校验 
	 * @param metaId
	 * @param map
	 * @throws Exception
	 */
	public List<IMessage> cellsValid(String metaId, Map<String, Object> map,Map<String, Object> context)throws Exception{
		//0.转换map
		Map<String, Object> metaMap = transMap(map);
		//1。校验单元格
		List<UppMetadata> cols = getMetasFromMap(metaMap);
		//2.所有校验规则
		List<CheckRule> chkRules = getCheckRulesFromMap(metaMap);
		
		List<IMessage> msgs = new ArrayList<IMessage>();
		//3.转换成校验对象，并校验
		for (UppMetadata meta : cols) {
			VCell cell = new VCell(meta,String.valueOf(metaMap.get(meta.getMetadata_id())));
			List<CheckRule> cellRules = findCheckRules(meta.getMetadata_id(), chkRules);
			if(cellRules.size()==0){
				continue;
			}
			cell.setCkRules(cellRules);
			cell.setContext(context);
			msgs.addAll(cell.doCheck());
		}
		return msgs;
	}
	
	/**
	 * 从校验规则列表中查找校验规则
	 * @param metaId
	 * @param chkRules
	 * @return
	 */
	private List<CheckRule> findCheckRules(String metaId,List<CheckRule> chkRules){
		 List<CheckRule> list = new ArrayList<CheckRule>();
		 for (int i = chkRules.size()-1 ; i >= 0; i--) {
			 if(chkRules.get(i).getMetadata_id().equals(metaId)){
				 list.add(chkRules.get(i));
				 chkRules.remove(i);
			 }
		 }
		 return list;
	}
	
	/**
	 * 行级校验
	 * @param fnMetaId 页面元数据ID
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<IMessage> rowValid(String metaId,Map<String, Object> map,Map<String, Object> context,boolean isTrans)throws Exception{
		if(isTrans){
			map = transData(map);
		}
		List<IMessage> msgs = new ArrayList<IMessage>();
		List<UppMetadata> metas = metaDataUtilsDao.getUppMetadatas(metaId);
		for (UppMetadata meta : metas) {
			VRow cell = transToVRow(meta, map);
			cell.setContext(context);
			msgs.addAll(cell.doCheck());
		}
		return msgs;
	}
	
	/**
	 * 不需要转换map
	 */
	public List<IMessage> rowValid(String metaId,Map<String, Object> map,Map<String, Object> contect)throws Exception{
		return rowValid(metaId, map,contect,false);
	}
	
 
	/**
	 * 转换成VRow
	 * @param column
	 * @param value
	 * @return
	 */
	public VRow transToVRow(UppMetadata meta,Map<String, Object> value){
		VRow row = new VRow(meta,value);
		row.setCkRules(getCheckRules(meta.getMetadata_id())); 
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckService#getFunctionMeta(java.util.Map)
	 */
	public List<IMessage> validFunctionMeta(Map<String, Object> param,Map<String, Object> data,Map<String, Object> context,boolean isTrans) {
		List<UppMetadata> tables = metaDataUtilsDao.getFunctionMeta(param);
		return validRowMeta(tables, data, context, isTrans);
	}
	
	/**
	 * 校验excel元数据的sheet
	 * @param param
	 * @param data
	 * @param context
	 * @param isTrans
	 * @return
	 */
	public List<IMessage> validExcelSheet(Map<String, Object> param,Map<String, Object> data,Map<String, Object> context,boolean isTrans) {
		List<UppMetadata> tables = metaDataUtilsDao.getSheetXls(param);
		return validRowMeta(tables, data, context, isTrans);
	}
	
	/**
	 * 校验excel sheet关联的table
	 * @param param
	 * @param data
	 * @param context
	 * @param isTrans
	 * @return
	 */
	public List<IMessage> validExcelSheetTable(Map<String, Object> param,Map<String, Object> data,Map<String, Object> context,boolean isTrans) {
		List<UppMetadata> tables = metaDataUtilsDao.getSheetTableMeta(param);
		return validRowMeta(tables, data, context, isTrans);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckService#getTableMeta(java.util.Map)
	 */
	public List<IMessage> validTableMeta(Map<String, Object> map,Map<String, Object> data,Map<String, Object> context,boolean isTrans) {
		List<UppMetadata> funConfig = metaDataUtilsDao.getTableMeta(map);
		return validRowMeta(funConfig, data, context, isTrans);	
	}
	
	/**
	 * 校验行级元数据,表级
	 * @param metas
	 * @param data
	 * @param context
	 * @param isTrans 是否将id_name=value转换为name=value
	 * @return
	 */
	public List<IMessage> validRowMeta(List<UppMetadata> metas,Map<String, Object> data,Map<String, Object> context,boolean isTrans){
		//将id_name=value转换为name=value
		if(isTrans){
			data = transData(data);
		}
		List<IMessage> msgs = new ArrayList<IMessage>();
		for (UppMetadata meta : metas) {
			VRow cell = transToVRow(meta, data);
			cell.setContext(context);
			msgs.addAll(cell.doCheck());
		}
		return msgs;
	}
	
	/**
	 * 根据模版获取父表
	 * @param param
	 * @return
	 */
	private String getParentTableName(Map<String, Object> param){
		List<UppMetadata> parTables = metaDataUtilsDao.getParentTableMeta(param);
		if(parTables!=null && parTables.size()>0){
			UppMetadata parMeta = parTables.get(0);
			return parMeta.getMetadata_name();
		}
		return "";
	}
	
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckService#validAfterImport(java.util.Map)
	 */
	public List<IMessage> validAfterImport(Map<String, Object> params,Map<String, Object> data,Map<String, Object> context) {
		
		//查询表元数据
		List<UppMetadata> tables = metaDataUtilsDao.getTableMeta(params);
		
		//默认传入父表表名作为参数
		data.put(MetaConstant.HIDDEN_PARENT_TABLE, getParentTableName(params));

		List<IMessage> msgs = new ArrayList<IMessage>();
		
		for (UppMetadata table : tables) {
			params.put("metaId", table.getMetadata_id());
			List<CheckRule> rules = getCheckRulesByProps(params);
			for (CheckRule checkRule : rules) {
				ValidResult result = MetaValidator.validate(checkRule, context, data);
				if(result==null){
					msgs.add(new InvalidMessage(table.getFullId(),table.getMetadata_name(),checkRule.getChk_rule_code()));
					logger.debug("["+table.getFullId()+"]配置的规则["+checkRule.getChk_rule_code()+"]不存在");
					break;
				}
				else  {
					logger.debug("["+table.getFullId()+"]执行["+checkRule.getChk_rule_code()+"]["+checkRule.getChk_rule_name()+"]校验 ["+result.success+"]["+result.getResult()+"]");
					if(!result.success){
						//sql校验失败
						if(checkRule.getChk_method_code().equals(IValidator.VTYPE_SQL)){
							msgs.add(new MyMessage(table.getFullId(),table.getMetadata_name(),  checkRule.getChk_failure_tip() + ",["+result.getResult()+"]"));
						}else{
							msgs.add(new MyMessage(table.getFullId(),table.getMetadata_name(),  checkRule.getChk_failure_tip()));
						}
						break;
					}
				}
			}
		}
		return msgs;
	}
	
	/**
	 * 将前端传递的一条数据，将 meta_id_column_name = value 转换长 metaId = value形式
	 * @param map
	 * @return
	 */
	private Map<String, Object> transMap(Map<String, Object> map){
		Map<String, Object> metaMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : map.entrySet()) {
			String mxKey = entry.getKey();
			if(mxKey.indexOf("_")>-1){
				mxKey = mxKey.split("_")[0];
			}
			metaMap.put(mxKey, entry.getValue());
		}
		return metaMap;
	}
	
	/**
	 * 如果传递的map有封装成 id_name = value 的形式，需要转换成 name = value 的形式
	 * @param map
	 * @return
	 */
	private Map<String, Object> transData(Map<String, Object> map){
		Map<String, Object> metaMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : map.entrySet()) {
			String mxKey = entry.getKey();
			if(mxKey.indexOf("_")>-1){
				mxKey = mxKey.substring(mxKey.indexOf("_")+1);
			}
			metaMap.put(mxKey, entry.getValue());
		}
		return metaMap;
	}
	
	/**
	 * 获取功能元数据cell(单元)
	 * @param parentMetaId
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<UppMetadata> getMetasFromMap(Map<String, Object> map)throws Exception{
		List<String> metaIds = new ArrayList<String>(); 
		for (String string : map.keySet()) {
			metaIds.add(string);
		}
		//查询字段列表
		String metaJoins = GlobalUtil.join(metaIds, ",").replaceAll(",", "','");
		return  metaDataUtilsDao.getUppMetadatas(metaJoins);
	}
	
	
	public List<CheckRule> getCheckRulesFromMap(Map<String, Object> map)throws Exception{
		List<String> metaIds = new ArrayList<String>(); 
		for (String string : map.keySet()) {
			metaIds.add(string);
		}
		//查询字段列表
		String metaJoins = GlobalUtil.join(metaIds, ",").replaceAll(",", "','");
		return  checkRuleUtilsDao.getCheckRuleByMetaIds(metaJoins);
	}
	
	/**
	 * 按元数据ID关联校验规则
	 * @param metaId
	 * @return
	 */
	public List<CheckRule> getCheckRules(String metaId){
		return checkRuleUtilsDao.getCheckRuleByMetaId(metaId);
	}
	
	/**
	 * 按元数据属性关联校验规则
	 * @param metaId
	 * @return
	 */
	public List<CheckRule> getCheckRulesByProps(Map<String, Object> param){
		return checkRuleUtilsDao.getCheckRuleByProps(param);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.service.ICheckService#validSubmitApprove(java.util.Map, java.util.Map)
	 */
	public Map<String, Object> validSubmitApprove(Map<String, Object> params,
			Map<String, Object> context) {
		//1。校验数据主键列表
		String businessNos =GlobalUtil.getStringValue(params,"business_nos");
		//2。取表名
		List<UppMetadata> tables = metaDataUtilsDao.getTableMeta(params);
		
		if(tables.size()>0){
			UppMetadata table = tables.get(0);
			
			//3。取数据
			String tableName = table.getMetadata_name();
			Map<String, Object> param  = new HashMap<String, Object>();
			param.put("tableName", tableName);
			param.put("businessNos", businessNos.replaceAll("_", "','"));
			
			//List<Map<String, Object>> dataList = metaDataUtilsDao.getTableData(param);
			String sql = "select b.* from " + tableName + " b where b.business_no in ('"+businessNos.replaceAll("_", "','")+"')";
			List<Map<String, Object>> dataList = GlobalUtil.lowercaseListMapKey(jdbcTemplate.queryForList(sql));
			
			//4。循环校验批量数据
			for (int t = 0; t < dataList.size() ; t ++) {
				Map<String, Object> data = dataList.get(t);
				
				CheckStrategy checkSty = new CheckStrategy();
				List<IMessage> msgs = validTableMeta(params, data, context, false);
				Map<String, Object> errors = checkSty.formatMsg(msgs);
				if(!errors.isEmpty()){
					return errors;
				}
			}
		}
		return null;
	}
	
}
