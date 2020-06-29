package com.shuhao.clean.apps.meta.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.base.service.ISelectorService;
import com.shuhao.clean.apps.meta.dao.PageStructDao;
import com.shuhao.clean.apps.meta.entity.UppDicFnRela;
import com.shuhao.clean.apps.meta.entity.UppMetadata;
import com.shuhao.clean.apps.meta.entity.UppPageButton;
import com.shuhao.clean.apps.meta.entity.UppPageField;
import com.shuhao.clean.apps.meta.entity.UppPageStruct;
import com.shuhao.clean.apps.meta.entity.UppTableColumn;
import com.shuhao.clean.apps.meta.service.IPageButtonService;
import com.shuhao.clean.apps.meta.service.IPageFieldService;
import com.shuhao.clean.apps.meta.service.IPageRelaMetadataService;
import com.shuhao.clean.apps.meta.service.IPageStructService;
import com.shuhao.clean.apps.meta.service.IMetadataService;
import com.shuhao.clean.constant.MetaConstant;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.UID;

/**
 * 
 * 类描述: 元数据[页面结构对象]业务逻辑实现类 
 * @author bixb
 * 创建时间：2015-1
 */
@Service
public class PageStructServiceImpl implements IPageStructService {
	
	@Autowired
	private IMetadataService metadataService = null;
	
	@Autowired
	private IPageFieldService pageFieldService = null;
	
	@Autowired
	private ISelectorService selectorService = null;
	
	@Autowired
	private IPageButtonService pageButtonService = null;
	
	@Autowired
	private IPageRelaMetadataService pageRelaMetadataService = null;
	
	@Autowired
	private PageStructDao pageStructDao = null;
	
	/**
	 * 添加简单页面结构对象
	 * @param pageStruct
	 * @throws Exception
	 */
	public void addSimplePageStruct(UppPageStruct pageStruct) throws Exception{
		//新增页面 
		this.pageStructDao.addPageStruct(pageStruct);
		//新增页面对应元数据
		this.metadataService.addMetadata(pageStruct);
	}
	
	/**
	 * 添加页面结构对象
	 * @param metadata
	 * @param pageStruct
	 * @throws Exception
	 */
	public void addPageStruct(UppPageStruct pageStruct) throws Exception {
		String page_metadata_id = pageStruct.getPage_struct_id();
		String user_id = pageStruct.getCreate_user_id();
		//新增页面 
		this.pageStructDao.addPageStruct(pageStruct);
		//新增页面对应元数据
		this.metadataService.addMetadata(pageStruct);
		
		//封装字段列表元数据
		UppMetadata fldListMetadata = this.initMetadata(page_metadata_id,MetaConstant.CATEGORY_TYPE_COLFIELD_LIST, user_id,null);
		//封装字段列表 
		UppPageStruct fldListPageStruct = this.initPageStrut(fldListMetadata);
		//新增字段列表元数据
		this.metadataService.addMetadata(fldListMetadata);
		//新增字段列表
		this.pageStructDao.addPageStruct(fldListPageStruct);
		
		//新增关联表字段到字段列表  并新增映射关系
		String rela_table_id =  pageStruct.getRela_table_id();
		if(rela_table_id!=null && !"".equals(rela_table_id)){
			Map<String, Object> fieldParams = new HashMap<String, Object>();
			fieldParams.put("page_struct_id", pageStruct.getPage_struct_id());
			fieldParams.put("rela_table_id", pageStruct.getRela_table_id());
			fieldParams.put("field_list_id", fldListPageStruct.getPage_struct_id());
			//新增页面字段
			pageFieldService.addPageFieldFromTable(fieldParams);
		}
		
		//封装表单元数据()
		UppMetadata addFormMetadata = this.initMetadata(page_metadata_id,MetaConstant.CATEGORY_TYPE_FORM, user_id,"新增表单");
		//封装表单 (新增表单)
		UppPageStruct addFormPageStruct = this.initPageStrut(addFormMetadata);
		//新增表单元数据(新增表单)
		this.metadataService.addMetadata(addFormMetadata);
		//新增表单(新增表单)
		this.pageStructDao.addPageStruct(addFormPageStruct);
		
		//封装修改表单元数据 (修改表单)
		UppMetadata updateFormMetadata = this.initMetadata(page_metadata_id,MetaConstant.CATEGORY_TYPE_FORM, user_id,"修改表单");
		//封装表单  (修改表单)
		UppPageStruct updateFormPageStruct = this.initPageStrut(updateFormMetadata);
		//新增表单元数据 (修改表单)
		this.metadataService.addMetadata(updateFormMetadata);
		//新增表单 (修改表单)
		this.pageStructDao.addPageStruct(updateFormPageStruct);
		
		//封装工具条元数据
		UppMetadata toolBarMetadata = this.initMetadata(page_metadata_id,MetaConstant.CATEGORY_TYPE_TOOLBAR, user_id,null);
		//封装工具条 
		UppPageStruct toolBarPageStruct = this.initPageStrut(toolBarMetadata);
		//新增工具条元数据
		this.metadataService.addMetadata(toolBarMetadata);
		//新增工具条
		this.pageStructDao.addPageStruct(toolBarPageStruct);
		
		/*
		 * 	01	新增
			02	编辑
		    03	删除
			04	新增保存
			05	修改保存
			06	取消
			07	导入
			08	导出
		*/
		//初始化工具条默认按钮
		List<UppPageButton> toolbarButtonList = this.initToolBarButtonList(toolBarMetadata.getMetadata_id(),user_id);
		initButtonDisplayOrder(toolbarButtonList);
		for(int i = 0;i < toolbarButtonList.size();i++){
			UppPageButton button = toolbarButtonList.get(i);
			if("01".equals(button.getButton_func_cd()) || "15".equals(button.getButton_func_cd())){
				button.setRela_metadata_id(addFormMetadata.getMetadata_id());
			}else if("02".equals(button.getButton_func_cd())){
				button.setRela_metadata_id(updateFormMetadata.getMetadata_id());
			}else if("07".equals(button.getButton_func_cd()) || "08".equals(button.getButton_func_cd())){
				//导入、导出 初始设置默认
				button.setRela_metadata_id(MetaConstant.DEFAULT_METADATA_ID);
			}
			//新增工具条默认按钮
			pageButtonService.addPageButton(button);
		}
		
		//初始化表单默认按钮(新增表单) 
		List<UppPageButton> addFormButtonList = this.initFormButtonList(addFormMetadata.getMetadata_id(),user_id,MetaConstant.CATEGORY_TYPE_FORM_BUTTON,"add");
		initButtonDisplayOrder(addFormButtonList);
		for(int i = 0;i < addFormButtonList.size();i++){
			//新增表单默认按钮(新增表单) 
			pageButtonService.addPageButton(addFormButtonList.get(i));
		}
		
		//初始化表单默认按钮(修改表单) 
		List<UppPageButton> updateFormButtonList = this.initFormButtonList(updateFormMetadata.getMetadata_id(),user_id,MetaConstant.CATEGORY_TYPE_FORM_BUTTON,"update");
		initButtonDisplayOrder(updateFormButtonList);
		for(int i = 0;i < updateFormButtonList.size();i++){
			//新增表单默认按钮(修改表单) 
			pageButtonService.addPageButton(updateFormButtonList.get(i));
		}
	}
	
	/**
	 * 初始化按钮排序
	 * @param buttonList
	 */
	private void initButtonDisplayOrder(List<UppPageButton> buttonList){
		
		for(int i = 0;i<buttonList.size();i++){
			buttonList.get(i).setDisplay_order(i+"");
		}
	}
	
	/**
	 * 初始化一个新的元数据
	 * @param prt_id
	 * @param md_cate_cd
	 * @return
	 */
	private UppMetadata initMetadata(String prt_id,String md_cate_cd,String user_id,String def_name){
		if(def_name == null){
			def_name = "默认";
		}
		UppMetadata metadata = new UppMetadata();
		String metadata_id = UID.next();
		metadata.setMetadata_id(metadata_id);
		metadata.setMetadata_name(def_name);
		metadata.setMetadata_desc(def_name);
		metadata.setPrt_metadata_id(prt_id);
		metadata.setMd_cate_cd(md_cate_cd);
		metadata.setCreate_user_id(user_id);
		metadata.setIs_display("Y");
		metadata.setStatus_cd("02");
		return metadata;
	}
	
	/**
	 * 初始化一个页面结构对象 
	 * 如 字段列表 工具条 等
	 * @param metadata
	 * @param md_cate_cd
	 * @return
	 */
	private UppPageStruct initPageStrut(UppMetadata metadata){
		UppPageStruct pageStruct = new UppPageStruct();
		pageStruct.setPage_struct_id(metadata.getMetadata_id());
		pageStruct.setPage_struct_name(metadata.getMetadata_name());
		pageStruct.setPage_struct_desc(metadata.getMetadata_desc());
		pageStruct.setMd_cate_cd(metadata.getMd_cate_cd());
		return pageStruct;
	}
	
	/**
	 * 初始化页面按钮列表
	 * @param prt_metada_id
	 * @param user_id
	 * @param md_cate_cd
	 * @return
	 * @throws Exception
	 */
	private List<UppPageButton> initFormButtonList(String prt_metada_id,String user_id,String md_cate_cd,String form_type) throws Exception{
		UppPageButton button = null;
		List<UppPageButton> resultList = new ArrayList<UppPageButton>();
		List<Map<String, Object>> buttonFuncList = selectorService.getUppButtonFunction(null);
		String[] funcArray = this.getFuncArray(md_cate_cd,form_type);
		
		Map<String, Object> buttonFuncMap = null;
		for (int i = 0; i < buttonFuncList.size(); i++) {
			 buttonFuncMap = buttonFuncList.get(i);
			 String button_func_cd = String.valueOf(buttonFuncMap.get("button_func_cd"));
			 
			 for(int j = 0;j<funcArray.length;j++){
				 if(button_func_cd.equals(funcArray[j])){
					 button = this.initPageButton(buttonFuncMap,md_cate_cd,prt_metada_id,user_id);
					 resultList.add(button);
					 break;
				 }
			 }
		}
		return resultList;
	}
	
	/**
	 * 初始化页面按钮列表
	 * @param prt_metada_id
	 * @param user_id
	 * @param md_cate_cd
	 * @return
	 * @throws Exception
	 */
	private List<UppPageButton> initToolBarButtonList(String prt_metada_id,String user_id) throws Exception{
		UppPageButton button = null;
		List<UppPageButton> resultList = new ArrayList<UppPageButton>();
		List<Map<String, Object>> buttonFuncList = selectorService.getUppButtonFunction(null);
		String[] funcArray =  this.getFuncArray(MetaConstant.CATEGORY_TYPE_TOOLBAR_BUTTON, null);
		
		Map<String, Object> buttonFuncMap = null;
		for (int i = 0; i < buttonFuncList.size(); i++) {
			 buttonFuncMap = buttonFuncList.get(i);
			 String button_func_cd = String.valueOf(buttonFuncMap.get("button_func_cd"));
			 
			 for(int j = 0;j<funcArray.length;j++){
				 if(button_func_cd.equals(funcArray[j])){
					 button = this.initPageButton(buttonFuncMap,MetaConstant.CATEGORY_TYPE_TOOLBAR_BUTTON,prt_metada_id,user_id);
					 resultList.add(button);
					 break;
				 }
			 }
		}
		
		//增加一个复制按钮
		buttonFuncMap = new HashMap<String, Object>();
		buttonFuncMap.put("button_func_cd", "15");
		buttonFuncMap.put("button_func_name", "复制");
		UppPageButton copyButton = this.initPageButton(buttonFuncMap,MetaConstant.CATEGORY_TYPE_TOOLBAR_BUTTON,prt_metada_id,user_id);
		resultList.add(2,copyButton);
		
		return resultList;
	}
	
	/**
	 * 初始化页面按钮
	 * @param buttonFuncMap
	 * @param md_cate_cd
	 * @param prt_metada_id
	 * @param user_id
	 * @return
	 */
	private UppPageButton initPageButton(Map<String, Object> buttonFuncMap,String md_cate_cd,String prt_metada_id,String user_id){
		 UppPageButton button = new UppPageButton();
		 button.setButton_id(UID.next());
		 button.setButton_name(String.valueOf(buttonFuncMap.get("button_func_name")));
		 button.setButton_func_cd(String.valueOf(buttonFuncMap.get("button_func_cd")));
		 button.setIs_customer_sql("N");
		 button.setMd_cate_cd(md_cate_cd);
		 button.setPrt_metadata_id(prt_metada_id);
		 button.setCreate_user_id(user_id);
		 button.setIs_display("Y");
		 button.setStatus_cd("02");
		 return button;
	}
	
	
	private String[] getFuncArray(String md_cate_cd,String formType){
		String[] buttonFunc = null;
		if(MetaConstant.CATEGORY_TYPE_TOOLBAR_BUTTON.equals(md_cate_cd)){
			//工具条按钮初始化类型  01 新增 02 编辑 03 删除  07 导入 08 导出
			buttonFunc = new String[]{"01","02","03","07","08"};
		}else if(MetaConstant.CATEGORY_TYPE_FORM_BUTTON.equals(md_cate_cd)){
			if("add".equals(formType)){
				//新增表单
				//表单按钮铸师魂类型    04新增保存   06 取消
				buttonFunc = new String[]{"04","06"};
			}else{
				//表单按钮铸师魂类型    05 修改保存  06 取消
				buttonFunc = new String[]{"05","06"};
			}
			
		}
		return buttonFunc;
		
	}
	
	
	/**
	 * 根据id获取页面结构对象
	 * @param page_struct_id
	 * @return
	 * @throws Exception
	 */
	public UppPageStruct getPageStructById(String page_struct_id) throws Exception {
		return this.pageStructDao.getPageStructById(page_struct_id);
	}
	
	/**
	 * 保存页面结构对象
	 * @param pageStruct
	 * @throws Exception
	 */
	public void savePageStruct(UppPageStruct pageStruct,Map<String, Object> params) throws Exception {
		this.metadataService.saveMetadata(pageStruct);
		if("true".equals(String.valueOf(params.get("isReConfigRelaTable")))){
			this.pageFieldService.addPageFieldFromTable(params);
		}
		this.pageStructDao.savePageStruct(pageStruct);
	}
	
	/**
	 * 删除页面结构对象
	 * @param page_struct_id
	 * @throws Exception
	 */
	public void deletePageStruct(String page_struct_id,String page_md_cate_cd) throws Exception {
		//查看是否有子节点  如果有子节点 则不删除 返回异常信息
		List<UppMetadata> childList = metadataService.queryMetadataByParentId(page_struct_id);
		//如果是字段列表节点  执行级联删除
		if(MetaConstant.CATEGORY_TYPE_COLFIELD_LIST.equals(page_md_cate_cd)){
			String[] metadata_ids = getFieldsIdArray(childList);
			//删除字段列表
			pageFieldService.deletePageFieldBatch(metadata_ids);
			
		}else{
			//如果是页面节点 表单节点 工具条节点 不进行级联删除
			if(childList != null && childList.size()>0){
				//throw new Exception("请先删除子节点");
				String[] metadata_ids = getFieldsIdArray(childList);
				//删除字段列表
				pageFieldService.deletePageFieldBatch(metadata_ids);
				//删除与表的映射
				UppDicFnRela delRela = new UppDicFnRela();
				delRela.setMetadata_id(page_struct_id);
				pageRelaMetadataService.deleteDicFnRela(delRela);

			}else{
				if(MetaConstant.CATEGORY_TYPE_PAGE.equals(page_md_cate_cd)){
					//删除与表的映射
					UppDicFnRela delRela = new UppDicFnRela();
					delRela.setMetadata_id(page_struct_id);
					pageRelaMetadataService.deleteDicFnRela(delRela);
				}
				
			}
		}
		
		this.metadataService.deleteMetadata(page_struct_id);
		this.pageStructDao.deleteTmplPageRela(page_struct_id);
		this.pageStructDao.deletePageStruct(page_struct_id);
		return;
		
	}
	
	/**
	 * 封装ID到数组
	 * @param childList
	 * @return
	 */
	private String[] getFieldsIdArray(List<UppMetadata> childList){
		String[] fieldIds = new String[childList.size()];
		for(int i = 0;i<childList.size();i++){
			fieldIds[i] = childList.get(i).getMetadata_id();
		}
		return fieldIds;
	}
	
	/***
	 * 同步页面与表的关联关系
	 * 同步方式:01 增量 02 覆盖
	 * 增量 为只新增没有的关联字段记录
	 * 覆盖 删除原有关联字段记录 重新初始化新的记录
	 * 参数中含有md_cate_cd 为所选节点的类型值
	 * 如果为 页面 页面ID为  metadata_id
	 * 如果为字段列表 页面ID需从数据库中获得
	 * @param params
	 * @throws Exception
	 */
	public void syncPageRelaTable(Map<String, Object> params) throws Exception {
		String syncType = String.valueOf(params.get("sync_type"));
//		String page_id = String.valueOf(params.get("curr_page_id"));
		String rela_table_id = String.valueOf(params.get("rela_table_id"));
		String field_list_id = String.valueOf(params.get("field_list_id"));
		
		String md_cate_cd = String.valueOf(params.get("md_cate_cd"));
		String metadata_id = String.valueOf(params.get("metadata_id"));
		String page_id = "";
		if(MetaConstant.CATEGORY_TYPE_PAGE.equals(md_cate_cd)){
			page_id = metadata_id;
		}else if(MetaConstant.CATEGORY_TYPE_COLFIELD_LIST.equals(md_cate_cd)){
			UppMetadata fieldListObj = metadataService.getMetadataById(metadata_id);
			page_id = fieldListObj.getPrt_metadata_id();
		}

		//如果不存在 则需要根据页面获取
		if(rela_table_id == null || "".equals(rela_table_id)){
			Map<String, Object> relaPara = new HashMap<String, Object>();
			relaPara.put("metadata_id", page_id);
			List<Map<String, Object>> relaTable = pageRelaMetadataService.queryRelaDicData(relaPara);
			if(relaTable == null || relaTable.size() == 0){
				throw new Exception("页面没有关联表");
			}
			rela_table_id = String.valueOf(relaTable.get(0).get("db_obj_id"));
			params.put("rela_table_id", rela_table_id);
		}
		if(field_list_id == null || "".equals(field_list_id)){
			Map<String, Object> fieldListPara = new HashMap<String, Object>();
			fieldListPara.put("prt_metadata_id", page_id);
			fieldListPara.put("md_cate_cd", MetaConstant.CATEGORY_TYPE_COLFIELD_LIST);
			List<Map<String, Object>> fieldList = pageRelaMetadataService.queryRelaMetadata(fieldListPara);
			if(fieldList == null || fieldList.size() == 0){
				throw new Exception("页面没有字段列表");
			}
			field_list_id = String.valueOf(fieldList.get(0).get("rela_metadata_id"));
			params.put("field_list_id", field_list_id);
		}
		
		if("02".equals(syncType)){
			//新增关联表字段到字段列表  并新增映射关系
			if(rela_table_id!=null && !"".equals(rela_table_id)){
				
				Map<String, Object> fieldParams = new HashMap<String, Object>();
				fieldParams.put("page_struct_id", page_id);
				fieldParams.put("rela_table_id", rela_table_id);
				fieldParams.put("field_list_id", field_list_id);
				//新增页面字段
				pageFieldService.addPageFieldFromTable(fieldParams);
			}
		}else if("01".equals(syncType)){
			//根据关联表ID和页面ID获取增量字段列表
			List<UppTableColumn> colList = pageRelaMetadataService.getNoRelaTableColumns(params);
			if(colList == null || colList.size() == 0){
				return;
			}
			//初始化页面字段列表
			List<UppPageField> fieldList = initAddPageFields(colList,params);
			//新增页面字段 
			pageFieldService.addPageFields(fieldList);
			
		}
	}
	
	private List<UppPageField> initAddPageFields(List<UppTableColumn> colList,Map<String, Object> params){
		List<UppPageField> fieldList = new ArrayList<UppPageField>();
		UppTableColumn col = null;
		for (int i = 0; i < colList.size(); i++) {
			col = colList.get(i);
			UppPageField field = new UppPageField();
			this.initPageFieldFromCol(field, col);
			this.initPageFieldMetadata(field);
			field.setCreate_user_id(String.valueOf(params.get("user_id")));
			field.setPrt_metadata_id(String.valueOf(params.get("field_list_id")));
			fieldList.add(field);
		}
		return fieldList;
	}
	
	private void initPageFieldFromCol(UppPageField field,UppTableColumn col){
		field.setField_id(UID.next());
		field.setField_name(col.getColumn_name());
		field.setRela_table_column(col.getColumn_id());
		
		if(col.getColumn_desc() == null){
			field.setComponent_label(col.getColumn_name());
		}else{
			field.setComponent_label(col.getColumn_desc());
		}
		
		//如果是字符型 则设置最大长度
		if("01".equals(col.getData_type_cd())){
			field.setMax_length(col.getData_length());
		}
		field.setComponent_type_id("textfield");
		field.setIs_hidden("N");
		field.setIs_query_cond("N");
		field.setIs_editable("Y");
		field.setIs_must_input("N");
		field.setIs_pk("N");
		field.setCol_biz_type_cd("01");//初始化为账户类
	}
	
	private void initPageFieldMetadata(UppPageField field){
		//封装元数据属性
		field.setMetadata_id(field.getField_id());
		field.setMetadata_name(field.getField_name());
		field.setMd_cate_cd(MetaConstant.CATEGORY_TYPE_COLFIELD);
		field.setIs_display("Y");
		field.setStatus_cd("02");
	}
	
	
	/**
	 * 查询通用页面结构所属页面
	 * @param metadata_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> querySimplePageParent(String metadata_id) throws Exception{
		List<Map<String, Object>> dataList = pageStructDao.querySimplePageParent(metadata_id);
		return GlobalUtil.lowercaseListMapKey(dataList);
	}
	
	/**
	 * 查询按钮所属页面
	 * @param button_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryPageByBtn(String button_id) throws Exception{
		List<Map<String, Object>> dataList = pageStructDao.queryPageByBtn(button_id);
		return GlobalUtil.lowercaseListMapKey(dataList);
	}
	 
}
