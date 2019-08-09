package com.shuhao.clean.apps.meta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.dao.PageFieldDao;
import com.shuhao.clean.apps.meta.entity.UppDicFnRela;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppPageField;
import com.shuhao.clean.apps.meta.entity.UppTableColumn;
import com.shuhao.clean.apps.meta.service.IColumnService;
import com.shuhao.clean.apps.meta.service.IMetadataService;
import com.shuhao.clean.apps.meta.service.IPageFieldService;
import com.shuhao.clean.apps.meta.service.IPageRelaMetadataService;
import com.shuhao.clean.constant.MetaConstant;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: 元数据[页面字段对象]业务逻辑实现类 
 * @author bixb
 * 创建时间：2015-1
 */
@Service
public class PageFieldServiceImpl implements IPageFieldService {
	
	@Autowired
	private IMetadataService metadataService = null;
	
	@Autowired
	private IPageRelaMetadataService pageRelaMetadataService = null;
	
	@Autowired
	private IColumnService columnService = null;
	
	@Autowired
	private PageFieldDao pageFieldDao = null;
	
	/**
	 * 添加页面字段对象
	 * @param pageField
	 * @param params 其他参数变量
	 * @throws Exception
	 */
	public void addPageField(UppPageField pageField,Map<String, Object> params) throws Exception {
		/***
		 * 查看是否含有字段列表参数 
		 * 如果没有则认为是从页面维护中添加而来
		 * 如果有直接添加
		 */
		String prt_metadata_id = "";
		if(params.get("field_list_id") == null || "".equals(params.get("field_list_id") )){
			//获取当前页面下字段列表
			params.put("prt_metadata_id", String.valueOf(params.get("page_id")));
			params.put("md_cate_cd", MetaConstant.CATEGORY_TYPE_COLFIELD_LIST);
			List<Map<String, Object>> fieldList = pageRelaMetadataService.queryRelaMetadata(params);
			if(fieldList == null || fieldList.size() == 0){
				throw new Exception("请先添加字段列表");
			}
			prt_metadata_id = String.valueOf(fieldList.get(0).get("rela_metadata_id"));
			
		}else{
			prt_metadata_id = String.valueOf(params.get("field_list_id"));
		}
		pageField.setPrt_metadata_id(prt_metadata_id);
		
		this.metadataService.addMetadata(pageField);
		//增加映射
		if(pageField.getRela_table_column() != null  && !"".equals(pageField.getRela_table_column())){
			UppDicFnRela dicFn = new UppDicFnRela();
			dicFn.setDb_obj_id(pageField.getRela_table_column());
			dicFn.setMetadata_id(pageField.getField_id());
			pageRelaMetadataService.addDicFnRela(dicFn);
		}
		
		this.pageFieldDao.addPageField(pageField);
		
	}
	
	/**
	 * 添加表的字段到页面
	 * @param pageField
	 * @throws Exception
	 */
	public void addPageFieldFromTable(Map<String, Object> params) throws Exception{
		//删除原有字段 
		String page_struct_id = String.valueOf(params.get("page_struct_id"));
		//关联表ID
		String tableId =  String.valueOf(params.get("rela_table_id"));
		//获取当前页面下字段列表ID
		String fieldListId =  String.valueOf(params.get("field_list_id"));
		if(page_struct_id == null || tableId == null){
			return;
		}
		
		if(fieldListId == null || "".equals(fieldListId)){
			//如果为空 说明没有字段 则直接新增一个字段列表
			fieldListId = UID.next();
			
			UppMetadata metadata = new UppMetadata();
			metadata.setMetadata_id(fieldListId);
			metadata.setMetadata_name("默认");
			metadata.setMetadata_desc("默认");
			metadata.setMd_cate_cd(MetaConstant.CATEGORY_TYPE_COLFIELD_LIST);
			metadataService.addMetadata(metadata);
			
		}else{
			//删除数据字典与功能映射
			UppDicFnRela delRela = new UppDicFnRela();
			List<UppMetadata> delPageFieldList = metadataService.queryMetadataByParentId(fieldListId);
			
			for(int i = 0;i<delPageFieldList.size();i++){
				delRela.setMetadata_id(delPageFieldList.get(i).getMetadata_id());
				//删除原有映射关系
				pageRelaMetadataService.deleteDicFnRela(delRela);
			}
			
			//如果存在字段列表 清空字段列表下字段
			pageFieldDao.deletePageFieldByFieldListId(fieldListId);
			
			//删除原有字段元数据
			metadataService.deleteMetadataByParentId(fieldListId);
			
			//删除页面原有映射
			UppDicFnRela delPageRela = new UppDicFnRela();
			delPageRela.setMetadata_id(page_struct_id);
			pageRelaMetadataService.deleteDicFnRela(delPageRela);
		}
				
		//获取数据字典新表字段 
		//List<UppMetadata> tableFieldList  = metadataService.queryMetadataByParentId(tableId);
		List<UppTableColumn> colList = columnService.listTableColumns(tableId);
		
		//封装应用功能字段 并增加
		UppPageField pageField = null;
		UppTableColumn col = null;
		for (int i = 0; i < colList.size(); i++) {
			col = colList.get(i);
			pageField = new UppPageField();
			pageField.setPrt_metadata_id(fieldListId);//父级为 字段列表ID
			pageField.setCreate_user_id(String.valueOf(params.get("user_id")));
			//初始化默认属性
			initPageFieldDefault(pageField,col); 
			
			//初始化元数据属性
			initPageFieldMetadata(pageField);
			
			//新增页面字段
			pageFieldDao.addPageField(pageField);
			//新增页面字段对应元数据
			metadataService.addMetadata(pageField);
			
			//新增功能与数据字典字段映射
			UppDicFnRela dicFn = new UppDicFnRela();
			dicFn.setDb_obj_id(col.getColumn_id());
			dicFn.setMetadata_id(pageField.getField_id());
			pageRelaMetadataService.addDicFnRela(dicFn);
		}
		
		//增加页面与表的关系映射
		UppDicFnRela pageTableDicFn = new UppDicFnRela();
		pageTableDicFn.setDb_obj_id(tableId);
		pageTableDicFn.setMetadata_id(page_struct_id);
		pageRelaMetadataService.addDicFnRela(pageTableDicFn);
	}
	
	/***
	 *  从表中添加表的字段(添加指定字段、元数据、映射)
	 * @param params
	 * @param fieldList
	 * @throws Exception
	 */
	public void addPageFields(List<UppPageField> fieldList) throws Exception{
		
		UppPageField pageField = null;
		for (int i = 0; i < fieldList.size(); i++) {
			pageField = fieldList.get(i);
			//新增字段
			pageFieldDao.addPageField(pageField);
			//新增页面字段对应元数据
			metadataService.addMetadata(pageField);
			//新增功能与数据字典字段映射
			UppDicFnRela dicFn = new UppDicFnRela();
			dicFn.setDb_obj_id(pageField.getRela_table_column());
			dicFn.setMetadata_id(pageField.getField_id());
			pageRelaMetadataService.addDicFnRela(dicFn);
			
		}
		
	}
	
	
	/***
	 * 初始化页面字段默认值
	 * @param pageField
	 */
	private void initPageFieldDefault(UppPageField pageField,UppTableColumn col){
		String pageFieldId = UID.next();
		pageField.setField_id(pageFieldId);
		pageField.setField_name(col.getColumn_name());
		pageField.setDisplay_order(col.getDisplay_order());
		
		if(col.getColumn_desc() != null){
			pageField.setMetadata_desc(col.getColumn_desc()+"["+col.getColumn_name()+"]");
		}else{
			pageField.setMetadata_desc(col.getColumn_name());
		}
		
		pageField.setMd_cate_cd(MetaConstant.CATEGORY_TYPE_COLFIELD);
		if(pageField.getMetadata_desc() == null){
			pageField.setComponent_label(pageField.getMetadata_name());
		}else{
			pageField.setComponent_label(getCompLabel(pageField.getMetadata_desc()));
		}
		if("Y".equals(col.getIs_nullable())){
			pageField.setIs_must_input("N");
		}else{
			pageField.setIs_must_input("Y");
		}
		//字符类型 初始化最大长度
		if("01".equals(col.getData_type_cd())){
			pageField.setMax_length(col.getData_length());
		}
		pageField.setIs_pk(col.getIs_pk());
		pageField.setComponent_type_id("textfield");
		pageField.setIs_hidden("N");
		pageField.setIs_query_cond("N");
		pageField.setIs_editable("Y");
		pageField.setCol_biz_type_cd("01");//初始化为账户类
	}
	
	/***
	 * 从元数据描述中提取默认控件标签
	 * 字段元数据 格式  字段名中文描述[字段名]
	 * @param metadataDesc
	 * @return
	 */
	private String getCompLabel(String metadataDesc){
		if(metadataDesc.indexOf("[")!= -1 && metadataDesc.indexOf("]")!= -1){
			String[] descArr =  metadataDesc.split("\\[");
			if(descArr.length == 0){
				return metadataDesc;
			}else{
				return descArr[0];
			}
		}
		return metadataDesc;
	}
	
	private void initPageFieldMetadata(UppPageField pageField){
		//封装元数据属性
		pageField.setMetadata_id(pageField.getField_id());
		pageField.setMetadata_name(pageField.getField_name());
		pageField.setMd_cate_cd(MetaConstant.CATEGORY_TYPE_COLFIELD);
		pageField.setIs_display("Y");
		pageField.setStatus_cd("02");
	}
	
	/**
	 * 根据id获取页面字段对象
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public UppPageField getPageFieldById(String metadata_id) throws Exception {
		return this.pageFieldDao.getPageFieldById(metadata_id);
	}
	
	/**
	 * 根据页面id获取页面字段对象
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPageFieldByPageId(String metadata_id) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("prt_metadata_id", metadata_id);
		params.put("md_cate_cd",MetaConstant.CATEGORY_TYPE_COLFIELD_LIST);
		//根据页面ID 获取字段列表ID 
		List<Map<String, Object>> fdlList = pageRelaMetadataService.queryRelaMetadata(params);
		if(fdlList == null || fdlList.size() == 0){
			return null;
		}
		String field_list_id = String.valueOf(fdlList.get(0).get("rela_metadata_id"));
		List<Map<String, Object>> result = pageFieldDao.getResultsByFieldListId(field_list_id);
		
		return GlobalUtil.lowercaseListMapKey(result);
	}
	
	/**
	 * 根据字段列表ID获取页面字段对象
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPageFieldByListId(String metadata_id) throws Exception{
		List<Map<String, Object>> result = pageFieldDao.getResultsByFieldListId(metadata_id);
		return GlobalUtil.lowercaseListMapKey(result);
	}
	
	/**
	 * 保存页面字段对象
	 * @param pageField
	 * @throws Exception
	 */
	public void savePageField(UppPageField pageField) throws Exception {
		//删除数据字典与功能映射
		UppDicFnRela delRela = new UppDicFnRela();
		delRela.setMetadata_id(pageField.getField_id());
		//删除原有映射关系
		pageRelaMetadataService.deleteDicFnRela(delRela);
		
		if(pageField.getRela_table_column() != null){
			//新增功能与数据字典字段映射
			UppDicFnRela dicFn = new UppDicFnRela();
			dicFn.setDb_obj_id(pageField.getRela_table_column());
			dicFn.setMetadata_id(pageField.getField_id());
			pageRelaMetadataService.addDicFnRela(dicFn);
		}
		
		this.metadataService.saveMetadata(pageField);
		this.pageFieldDao.savePageField(pageField);
	}
	
	/**
	 * 删除页面字段对象
	 * @param metadata_id
	 * @throws Exception
	 */
	public void deletePageField(String metadata_id) throws Exception {
		//删除数据字典与功能映射
		UppDicFnRela delRela = new UppDicFnRela();
		delRela.setMetadata_id(metadata_id);
		//删除原有映射关系
		pageRelaMetadataService.deleteDicFnRela(delRela);
		
		this.metadataService.deleteMetadata(metadata_id);
		this.pageFieldDao.deletePageField(metadata_id);
	}
	
	/**
	 * 批量删除页面字段对象
	 * @param metadata_id
	 * @throws Exception
	 */
	public void deletePageFieldBatch(String[] metadata_ids) throws Exception {
		if(metadata_ids.length == 0){
			return;
		}
		String del_para_str = "";
		//删除数据字典与功能映射
		UppDicFnRela delRela = new UppDicFnRela();
		for(int i = 0;i<metadata_ids.length;i++){
			if(i == metadata_ids.length -1){
				del_para_str = del_para_str + "'"+metadata_ids[i]+"'" ;
			}else{
				del_para_str =  del_para_str + "'"+metadata_ids[i]+"',"  ;
			}
			delRela.setMetadata_id(metadata_ids[i]);
			//删除原有映射关系
			pageRelaMetadataService.deleteDicFnRela(delRela);
			
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("del_para_str", del_para_str);
		this.pageFieldDao.deletePageFieldBatch(params);
		this.metadataService.deleteMetadataBatch(metadata_ids);
	}
	
	/***
	 *  获取字段顺序列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getFieldsForDisOrder(Map<String, Object> params) throws Exception{
		//如果没有传递父级参数 则默认取页面ID 然后取字段列表ID
		if(params.get("prt_metadata_id") != null && !"".equals(params.get("prt_metadata_id"))){
			return pageFieldDao.getFieldsForDisOrder(params);
		}else if(params.get("page_id") != null && !"".equals(params.get("page_id"))){
			return pageFieldDao.getFieldsToOrderByPageId(params);
		}else{
			throw new Exception("传入参数有误。");
		}
	}
	
	/**
	 * 更新排序
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateDisOrder(Map<String, Object> params) throws Exception{
		//解析参数
		String paramStr =  String.valueOf(params.get("orderParam"));
		String[] fieldArr = paramStr.split(";");
		Map<String, Object> param = new HashMap<String, Object>();
		String metadata_id = "";
		String display_order = "";
		for(int i = 0;i<fieldArr.length;i++){
			metadata_id =  fieldArr[i].split(",")[0];
			display_order = fieldArr[i].split(",")[1];
			//更新字段表
			param.put("field_id", metadata_id);
			param.put("display_order", display_order);
			pageFieldDao.updateDisOrder(param);
			
			param.put("metadata_id", metadata_id);
			param.put("user_id", params.get("user_id"));
			//更新元数据
			this.pageFieldDao.saveMetadata(param);
		}
	}
	
	/**
	 * 查询关联表的可配置字段
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryRelaTableCols(Map<String, Object> params) throws Exception{
		//如果有字段参数  则直接获取其他参数
		if(params.get("field_id") != null){
			//通过字段获取 页面和字段列表
			List<Map<String, Object>> fieldList = pageFieldDao.queryPrtByFieldId(params);
			fieldList = GlobalUtil.lowercaseListMapKey(fieldList);
			String field_list_id = String.valueOf(fieldList.get(0).get("field_list_id"));
			params.put("field_list_id", field_list_id);
			
			//如果没有关联表ID 则根据字段列表ID 获取关联表ID
			List<Map<String, Object>> relaTblColList = GlobalUtil.lowercaseListMapKey(pageFieldDao.queryRelaTbColByFldLst(params));
			if(relaTblColList == null || relaTblColList.size()==0){
				return null;
			}else{
				params.put("rela_table_id", relaTblColList.get(0).get("rela_table_id"));
			}
			
		}
		//如果有字段列表参数  则获取 关联表
		if(params.get("field_list_id") != null){
			//如果没有关联表ID 则根据字段列表ID 获取关联表ID
			List<Map<String, Object>> relaTblColList = GlobalUtil.lowercaseListMapKey(pageFieldDao.queryRelaTbColByFldLst(params));
			if(relaTblColList == null || relaTblColList.size()==0){
				return null;
			}else{
				params.put("rela_table_id", relaTblColList.get(0).get("rela_table_id"));
			}
			
		}
		//如果有页面参数 则获取字段列表  
		if(params.get("page_struct_id") != null){
			String page_id = String.valueOf(params.get("page_struct_id"));
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("prt_metadata_id", page_id);
			queryParam.put("md_cate_cd",MetaConstant.CATEGORY_TYPE_COLFIELD_LIST);
			//根据页面ID 获取字段列表ID 
			List<Map<String, Object>> fdlList = pageRelaMetadataService.queryRelaMetadata(queryParam);
			if(fdlList == null || fdlList.size() == 0){
				return null;
			}
			params.put("field_list_id", String.valueOf(fdlList.get(0).get("rela_metadata_id")));
		}
		
		List<Map<String, Object>> dataList = pageFieldDao.queryRelaTableCols(params);
		
		return GlobalUtil.lowercaseListMapKeyAndTrimVal(dataList);
	}
}
