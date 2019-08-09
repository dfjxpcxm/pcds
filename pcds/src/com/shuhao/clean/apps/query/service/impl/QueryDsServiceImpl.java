/**
 * FileName:     QueryDsServiceImpl.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2015-6-15 下午2:29:19 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-6-15       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.query.service.impl;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuhao.clean.apps.meta.service.IPageManagerService;
import com.shuhao.clean.apps.model.ext.BaseField;
import com.shuhao.clean.apps.model.ext.Bbar;
import com.shuhao.clean.apps.model.ext.Button;
import com.shuhao.clean.apps.model.ext.ColumnModel;
import com.shuhao.clean.apps.model.ext.ComboBox;
import com.shuhao.clean.apps.model.ext.DateField;
import com.shuhao.clean.apps.model.ext.FormLayout;
import com.shuhao.clean.apps.model.ext.FormPanel;
import com.shuhao.clean.apps.model.ext.GridPanel;
import com.shuhao.clean.apps.model.ext.HiddenField;
import com.shuhao.clean.apps.model.ext.JsonReader;
import com.shuhao.clean.apps.model.ext.JsonStore;
import com.shuhao.clean.apps.model.ext.Layout;
import com.shuhao.clean.apps.model.ext.MoneyField;
import com.shuhao.clean.apps.model.ext.NumberField;
import com.shuhao.clean.apps.model.ext.Panel;
import com.shuhao.clean.apps.model.ext.TextField;
import com.shuhao.clean.apps.model.ext.ThreePanelLayout;
import com.shuhao.clean.apps.model.ext.TreeBox;
import com.shuhao.clean.apps.query.dao.QueryDsDao;
import com.shuhao.clean.apps.query.dao.QueryDsMetaDao;
import com.shuhao.clean.apps.query.entity.QueryDs;
import com.shuhao.clean.apps.query.entity.QueryDsMeta;
import com.shuhao.clean.apps.query.entity.UserQueryDsMeta;
import com.shuhao.clean.apps.query.service.QueryDsService;
import com.shuhao.clean.apps.sys.entity.SysUserInfo;
import com.shuhao.clean.base.BaseJdbcService;
import com.shuhao.clean.constant.ExtConstant;
import com.shuhao.clean.utils.GlobalUtil;
import com.shuhao.clean.utils.PageRange;
import com.shuhao.clean.utils.PageResult;
import com.shuhao.clean.utils.SheetConfig;
import com.shuhao.clean.utils.exttree.ExtTreeNode;
import com.shuhao.clean.utils.exttree.ExtTreeUtils;

/**
 * @Description:   TODO
 * 
 * @author:         gongzhiyang
 */
@Service
public class QueryDsServiceImpl extends BaseJdbcService implements QueryDsService {
	
	@Autowired
	private QueryDsDao queryDsDao;
	
	@Autowired
	private QueryDsMetaDao queryDsMetaDao;
	
	@Autowired
	IPageManagerService pageManagerService;
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#addDs(com.shuhao.clean.apps.query.entity.QueryDs)
	 */
	public void addDs(QueryDs ds) throws Exception {
		// TODO Auto-generated method stub
		queryDsDao.addDs(ds);
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#deleteDs(com.shuhao.clean.apps.query.entity.QueryDs)
	 */
	public void deleteDs(QueryDs ds) throws Exception {
		// TODO Auto-generated method stub
		//queryDsDao.deleteDs(ds);
		//删除关联
		//删除用户
		queryDsDao.deleteDsInfo(ds);
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#updateDs(com.shuhao.clean.apps.query.entity.QueryDs)
	 */
	public void updateDs(QueryDs ds) throws Exception {
		// TODO Auto-generated method stub
		queryDsDao.updateDs(ds);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#findDsById(com.shuhao.clean.apps.query.entity.QueryDs)
	 */
	public QueryDs findDsById(QueryDs ds) throws Exception {
		// TODO Auto-generated method stub
		return queryDsDao.findDsById(ds);
	}
	/**
	 * 取详细信息
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	public QueryDs getDsInfo(QueryDs ds) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", ds.getDs_id());
		return queryDsDao.getDsInfo(param);
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#checkDsSql(com.shuhao.clean.apps.query.entity.QueryDs)
	 */
	public List<QueryDsMeta> checkDsSql(String dsSql) throws Exception  {
		List<QueryDsMeta> fields = new ArrayList<QueryDsMeta>();
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn = this.jdbcTemplate.getDataSource().getConnection();
			statement = conn.prepareStatement(dsSql);
			resultSet = statement.executeQuery(dsSql);
			ResultSetMetaData rsMetaData = resultSet.getMetaData();
			int count = rsMetaData.getColumnCount();
			for (int i = 1; i <= count; i++) {
				String name = rsMetaData.getColumnName(i);
				String label = rsMetaData.getColumnLabel(i);
				String type = rsMetaData.getColumnTypeName(i);
				
				QueryDsMeta dsMeta = new QueryDsMeta();
				dsMeta.setField_id(name);
				dsMeta.setField_label(label);
				dsMeta.setField_type(getType(type));
				
				fields.add(dsMeta);
			}
			if(fields.size()==0){
				throw new Exception("sql异常，未查询到字段信息。");
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally{
			try {
				if(resultSet!=null){
					resultSet.close();
				}
			} catch (SQLException e) {
			}
			try {
				if(statement!=null){
					statement.close();
				}
			} catch (SQLException e) {
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		return fields;
	}
	//返回字段类型
	private String getType(String type){
		if(type.indexOf("CHAR")>-1 || type.indexOf("LOB")>-1){
			return ExtConstant.EXT_TEXTFIELD;
		}else if(type.indexOf("DATE") >-1 || type.indexOf("TIME")>-1){
			return ExtConstant.EXT_DATEFIELD;
		}else{
			return ExtConstant.EXT_NUMBERFIELD;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#editDsAndMeta(java.util.Map)
	 */
	public void editDsAndMeta(Map<String, Object> dsMetas) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#getDsTree(com.shuhao.clean.apps.query.entity.QueryDs)
	 */
	public ExtTreeNode getDsTree(QueryDs ds) throws Exception {
		List<QueryDs> tmpls = queryDsDao.getDsList();
		return ExtTreeUtils.listProjectTree(tmpls, "根节点");
	}
	
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#listByType(com.shuhao.clean.apps.query.entity.QueryDs)
	 */
	public List<Map<String, Object>> listByType(Map<String, Object> param)
			throws Exception {
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		Map<String, Object> row = new HashMap<String, Object>();
		row.put("id_field", "");
		row.put("value_field", "--全部--");
		dataList.add(row);
		dataList.addAll(GlobalUtil.lowercaseListMapKey(queryDsDao.listByType(param)));
		return dataList;
	}
	
	public PageResult<Map<String, Object>> getUserList(Map<String, Object> param)
			throws Exception {
		PageResult<Map<String, Object>> page = new PageResult<Map<String,Object>>();
		if(GlobalUtil.getStringValue(param,"alloted").equals("yes")){
			page.setResults(GlobalUtil.lowercaseListMapKey(queryDsDao.getAllotedUserList(param)));
		}else{
			page.setResults(GlobalUtil.lowercaseListMapKey(queryDsDao.getUserList(param)));
			page.setTotalCount(queryDsDao.getUserListCount(param));
		}
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#addUserDs(java.util.Map)
	 */
	public void addUserDs(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String userIds = String.valueOf(param.get("userIds"));
		String dsId = String.valueOf(param.get("dsId"));
		String [] userIdArray = userIds.split(",");
		
		for (int i = 0; i < userIdArray.length; i++) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("user_id", userIdArray[i]);
			row.put("ds_id", dsId);
			queryDsDao.addUserDs(row);
		}
	}
	
	
	public void deleteUserDs(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String userIds = String.valueOf(param.get("userIds"));
		String dsId = String.valueOf(param.get("dsId"));
		String [] userIdArray = userIds.split(",");
		
		for (int i = 0; i < userIdArray.length; i++) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("user_id", userIdArray[i]);
			row.put("ds_id", dsId);
			queryDsDao.deleteUserDs(row);
		}
	}
	
	public ExtTreeNode getUserDsTree(String userId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		List<QueryDs> tmpls = queryDsDao.getUserDsList(param);
		return ExtTreeUtils.listProjectTree(tmpls, "根节点");
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.query.service.QueryDsService#getPageView(java.lang.String, com.shuhao.clean.apps.sys.entity.SysUserInfo)
	 */
	public String getPageView(String dsId, SysUserInfo user) throws Exception {
		//1.查询ds
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);
		//QueryDs ds = queryDsDao.getDsInfo(param);
		//2.查询字段配置
		param.put("userId", user.getUser_id());
		List<UserQueryDsMeta> userDsMetas = queryDsMetaDao.getUserDsMeta(param);
		
		//3.生成代码
		Map<String, Object> eleMap = new HashMap<String, Object>();
		eleMap.put("tmpl_id", dsId);
		eleMap.put("storeMap", new HashMap<String, Object>());
		eleMap.put("dsDims", GlobalUtil.lowercaseListMapKey(queryDsDao.getDsDim(param)));
		
		createFields(userDsMetas, eleMap);
		return createQueryPage(eleMap);
	}
	
	/**
	 * 初始化字段信息
	 * @param userDsMetas
	 * @param eleMap
	 */
	private void createFields(List<UserQueryDsMeta> userDsMetas,Map<String, Object> eleMap){
		List<BaseField> fields = new ArrayList<BaseField>();
		List<BaseField> hidden = new ArrayList<BaseField>();
		List<BaseField> query = new ArrayList<BaseField>();
		//维度列表
		List<Map<String, Object>> dims = (List<Map<String, Object>>) eleMap.get("dsDims");
		
		for (UserQueryDsMeta userQueryDsMeta : userDsMetas) {
			boolean isQuery = userQueryDsMeta.getIs_query()!=null && userQueryDsMeta.getIs_query().equals("Y");
			boolean isHidden = userQueryDsMeta.getIs_hidden()!=null && userQueryDsMeta.getIs_hidden().equals("Y");
			
			BaseField extField = newField((Map<String,Object>)eleMap.get("storeMap"), userQueryDsMeta, getDimMap(userQueryDsMeta.getDim_cd(), dims));
			fields.add(extField);
			if(isQuery){
				query.add(extField);
			} 
			if(isHidden){
				hidden.add(extField);
			}
		}
		eleMap.put("fields", fields);
		eleMap.put("hidden", hidden);
		eleMap.put("query", query);
	}
	
	private Map<String, Object> getDimMap(String dimCd,List<Map<String, Object>> dims){
		for (Map<String, Object> map : dims) {
			if(map.get("dim_cd").equals(dimCd)){
				return map;
			}
		}
		return null;
	}
	
	/**
	 * 创建查询页
	 * @param eleMap
	 * @return
	 * @throws Exception
	 */
	private String createQueryPage(Map<String, Object> eleMap)throws Exception{
		
		StringBuffer stores = new StringBuffer();//预先加载的store
		//代码缓存
		StringBuffer codeBuffer = new StringBuffer();
		//eleMap - tmpl_id,query
		//east-queryForm
		FormPanel quryForm = creatQueryForm(eleMap,false);
		//创建父模版页面事件  
		String actionHandler = addHandler(eleMap);
		eleMap.put("actionHandler", actionHandler);
		
		//center-grid
		GridPanel grid = createGrid(eleMap);
		
		//panel-mainpanel
		ThreePanelLayout tpl = new ThreePanelLayout();
		tpl.setEastPanel(quryForm);
		tpl.setCenterPanel(grid);
		
		Panel panel = new Panel();
		panel.setBorder(false);
		panel.setLayout("fit");
		panel.setId("mainpanel");
		panel.setPanelName("mainpanel");
		panel.setBodyStyle("padding:0px 0px 0 0px");
		panel.setItems(tpl);
		
		//初始化store
		Map<String, String> storeMap =  (Map<String, String>) eleMap.get("storeMap");
		for (Iterator<String> iterator = storeMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			String store = (String)storeMap.get(key);
			stores.append(store+";");
			stores.append(key+".load();").append("\n");
		}
		//生成代码
		codeBuffer.append(stores); //预先加载的store
		codeBuffer.append(panel.output());
		codeBuffer.append(actionHandler);
		
		return codeBuffer.toString();
	}
	
	/**
	 * 添加事件handler
	 * @param eleMap
	 * @return
	 */
	private String  addHandler(Map<String, Object> eleMap){
		String tmpl_id = GlobalUtil.getStringValue(eleMap, "tmpl_id");
		//grid beforeload
		StringBuffer buffer = new StringBuffer();
		buffer.append("var parentStore = Ext.getCmp('dataGrid"+tmpl_id+"').getStore();");
		buffer.append("parentStore.on('beforeload',function(store){");
		buffer.append("var pars = _queryForm.getForm().getValues(true);");
		buffer.append("parentStore.baseParams = {");
		buffer.append("baseparams : pars");
		buffer.append(" }; ");
		buffer.append(" }); ");
		return buffer.toString();
	}
	
	/**
	 * @param eleMap
	 * @param hideExport 是否隐藏导出数据
	 * @return
	 * @throws Exception 
	 */
	private static FormPanel creatQueryForm(Map<String, Object> eleMap,boolean hideExport) throws Exception {
		//模版dsId
		String tmpl_id = GlobalUtil.getStringValue(eleMap, "tmpl_id");
		//开始生成查询菜单
		FormPanel form = new FormPanel();
		form.setId("query");
		form.setRegion("east");    //更改菜单布局只需更改此属性
		form.setAutoHeight(true);
		form.setTitle("设置查询条件");
		form.setFrame(false);
		form.setMethod("POST");
		form.setLabelWidth(70);
		form.setLabelAlign("top");
		form.setCollapsible(true);
		form.setLayout("form");
		form.setWidth(240);
		
		List<Button> list  = new ArrayList<Button>();
		Button btn = new Button(ExtConstant.EXT_BUTTON_TYPE_QUE);
		btn.setXtype("button");
		btn.setIconCls("search");
		btn.setHandler("doQuery('"+tmpl_id+"');");
		list.add(btn);
		
		if(!hideExport){
			Button exp = new Button();
			exp.setXtype("button");
			exp.setText("导出数据");
			exp.setIconCls("export");
			exp.setHandler("doExport('"+tmpl_id+"');");
			list.add(exp);
		}
		
		form.setButtons(list);
		form.setButtonAlign("center");
		
		//计算新增布局
		Layout layout = new FormLayout(null,eleMap,1);
		form.setItems(layout);
		
		return form;
	}
	
	private static final String load_url = "/queryTmpl/getData";

	/**
	 * @param eleMap
	 * @return
	 * @throws Exception 
	 */
	private static GridPanel createGrid(Map<String, Object> eleMap) throws Exception {
		
		List<BaseField> dataList = (List<BaseField>) eleMap.get("fields");
		String tmpl_id = GlobalUtil.getStringValue(eleMap, "tmpl_id");
		
		//开始生成 jsonstore　
		JsonReader reader = new JsonReader(dataList,0);
		reader.setId("reader"+tmpl_id+"");
		reader.setTotalProperty("totalCount");//设置总记录数变量　　取默认
		reader.setRoot("results");//设置json数据根节点   取默认

		JsonStore store = new JsonStore();
		store.setId("data"+tmpl_id);
		store.setUrl(load_url + "/"+tmpl_id);//设置请求地址
		List<String> baseParams = new ArrayList<String>();
		baseParams.add("start : 0");
		baseParams.add("limit : 30");
		store.setBaseParams(baseParams);  //设置默认参数
		store.setAutoLoad(false); //是否自动加载
		store.setReader(reader); //设置数据映射
		
		if(dataList==null||dataList.size()<1){
			throw new Exception("字段列表未配置");
		}
		//开始生成　grid  的列头
		ColumnModel model = new ColumnModel(eleMap);
		model.setId("cm"+tmpl_id);
		model.setSelModel(false);
		model.setAddRowNum(true);
		model.addDefaultAttr("sortable", false);
		model.addDefaultAttr("menuDisabled", true);
		
		//分页
		Bbar bbar = new Bbar();
		bbar.setStoreName(store.getId()+"Store");
		//开始生成grid    布局，渲染位置，
		
		GridPanel grid = new GridPanel();
		grid.setId("dataGrid"+tmpl_id);
		grid.setBorder(false);
		grid.setRegion("center");    //布局
		grid.setStripeRows(true);
		grid.setDataStore(store);
		grid.setColModel(model);
		grid.setBodyStyle("");
		grid.setBbar(bbar);
		grid.setAddsm(false);
	 	
		return grid;
	}
	
	
	
	/**is_query是否查询条件控件如果是不做校验
	 * @param fieldMap
	 * @return
	 */
	private static BaseField newField(Map<String,Object> storeMap ,UserQueryDsMeta dsMeta,Map<String, Object> dimMap) {
		String mid = "id_"+dsMeta.getField_id().toLowerCase();  //字段ID
		String name = dsMeta.getField_id().toLowerCase();
		String label = dsMeta.getField_label();   //控件label
		String type = dsMeta.getField_type();   //类型
		String value = dsMeta.getDefault_value();  //控件value  8888,总行
		String value_desc = ""; //描述
		if(value!=null && value.indexOf(",")>-1){
			String [] valAry = value.split(",");
			value = valAry[0];
			value_desc = valAry[1];
		}
		
		String hide = dsMeta.getIs_hidden();  //隐藏
		//String query = dsMeta.getIs_query();  //是否为查询条件 
		String dim_code = dsMeta.getDim_cd();  //维度代码表主键
		//根据纬度重新定义类型field_type
		String dim_code_col ="";
		String dim_label_col ="";
		
		if(dimMap!=null && !dimMap.isEmpty()){
			String isTree = String.valueOf(dimMap.get("is_tree"));
			if(isTree.equals("Y")){
				type = ExtConstant.EXT_TREEBOX;
			}else{
				type = ExtConstant.EXT_COMBOBOX;
			}
			dim_code_col = String.valueOf(dimMap.get("code_col_name"));
			dim_label_col = String.valueOf(dimMap.get("label_col_name"));
		}else if(type == ExtConstant.EXT_TREEBOX || type == ExtConstant.EXT_COMBOBOX){
			//设置type=ExtConstant.EXT_TEXTFIELD
			type = ExtConstant.EXT_TEXTFIELD;
		}
	 
		boolean isHide =  "Y".equals(hide) ? true : false;
		//boolean isQuery =  "Y".equals(query) ? true : false;
		if(type.equals(ExtConstant.EXT_TEXTFIELD)){
			TextField text = new TextField();
			text.setId(mid);
			text.setName(name);
			text.setFieldLabel(label);
			text.setValue(value);
			text.setHidden(isHide);
			text.setAnchor("90%");
			text.setCtype(ExtConstant.EXT_TEXTFIELD);
			return text;
		}else if(ExtConstant.EXT_NUMBERFIELD.equals(type)){
			NumberField number = new NumberField();
			number.setId(mid);
			number.setName(name);
			number.setFieldLabel(label);
			number.setAnchor("90%");
			number.setHidden(isHide);
			number.setValue(value);
			number.setRealName(name);
			number.setCtype(ExtConstant.EXT_NUMBERFIELD);
			return number;
		}else if(ExtConstant.EXT_MONEYFIELD.equals(type)){
			MoneyField money = new MoneyField();
			money.setId(mid);
			money.setName(name);
			money.setFieldLabel(label);
			money.setValue(value);
			money.setHidden(isHide);
			money.setAnchor("90%");
			money.setCtype(ExtConstant.EXT_MONEYFIELD);
			return money;
		}else if(ExtConstant.EXT_DATEFIELD.equals(type)) {
			//日期框
			DateField date = new DateField();
			date.setId(mid);
			date.setName(name);
			date.setFieldLabel(label);
			date.setValue(value);
			date.setAnchor("90%");
			date.setHidden(isHide);
			date.setCtype(ExtConstant.EXT_DATEFIELD);
			return date;
		}else if(ExtConstant.EXT_HIDDENFIELD.equalsIgnoreCase(type)){
			HiddenField  hidden = new HiddenField();
			hidden.setId(mid);
			hidden.setName(name);
			hidden.setCtype(ExtConstant.EXT_HIDDENFIELD);
			return hidden;
		}else if(ExtConstant.EXT_COMBOBOX.equalsIgnoreCase(type)) {
			ComboBox box = new ComboBox();
			box.setId(mid);
			box.setName(name);
			box.setHiddenName(name);
			box.setHidden(isHide);
			box.setAnchor("90%");
			box.setEditable(false);
			box.setFieldLabel(label);
			box.setHidden(isHide);
			box.setValue(value);
			//开始获取维护表数据　生成store
			JsonStore store = new JsonStore();
			store.setAutoLoad(false);
			store.setUrl("/pageManager/getLinkData/"+dim_code);
			store.setId("_"+mid); //combo store name
			
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			Map<String, Object> display = new HashMap<String, Object>();
			display.put("field_name", dim_code_col);
			Map<String, Object> disvalue = new HashMap<String, Object>();
			disvalue.put("field_name", dim_label_col);
			dataList.add(disvalue);
			dataList.add(display);
			//普通的字段的jsonreader不加默认的Field
			JsonReader reader = new JsonReader(dataList,false);
			reader.setId(mid);
			store.setReader(reader);
			//store name
			box.setStoreName("_"+mid+"Store");
			storeMap.put(box.getStoreName(), store.output());
			
			box.setValueField(dim_code_col);
			box.setDisplayField(dim_label_col);
			box.setCtype(ExtConstant.EXT_COMBOBOX);
			return box;
		} else if(ExtConstant.EXT_TREEBOX.equalsIgnoreCase(type)) {
			TreeBox tree = new TreeBox();
			tree.setId(mid);
			tree.setName(name);
			tree.setRealName(name);
			tree.setTextLabel(label);
			tree.setFieldLabel(label);
			tree.setHidden(isHide);
			
			if (value != null && !value.equals("")) {
				tree.setRootId(value);
				if (value_desc != null && !"".equals(value_desc)) {
					tree.setRootName("["+value+"]"+value_desc);
					tree.setValue("["+value+"]"+value_desc);
				}else{
					tree.setRootName("["+value+"]"+label);
					tree.setValue("["+value+"]"+label);
				}
			}else {
				tree.setRootId(mid);
				tree.setRootName("["+value+"]"+label);
			}
			
			tree.setDim_code(dim_code);
			tree.setCtype(ExtConstant.EXT_TREEBOX);
			return tree;
		}
		return null;
	}
	
	
	/**
	 * 按模版ID 查询数据
	 * @param tmpl_id 
	 * @param treeList
	 * @param data
	 * @param level
	 * @throws Exception 
	 */
	public PageResult<Map<String,Object>> queryDataForList(String dsId,Map<String, Object> requestParam,SysUserInfo user,boolean isExport) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);
		QueryDs ds = queryDsDao.getDsInfo(param);
		//2.查询字段配置
		param.put("userId", user.getUser_id());
		List<UserQueryDsMeta> userDsMetas = queryDsMetaDao.getUserDsMeta(param);
		
		//配需列表
		List<String> orderList = new ArrayList<String>();
		for (UserQueryDsMeta meta : userDsMetas) {
			if(meta.getIs_order()!=null && meta.getIs_order().equals("Y")){
				orderList.add(meta.getField_id());
			}
		}
		
		//模版sql
		String baseSql = ds.getDs_sql();
			
		String baseparams = String.valueOf(requestParam.get("baseparams"));
		String start = String.valueOf(requestParam.get("start"));
		String limit = String.valueOf(requestParam.get("limit"));
		start = start==null||"".equals(start)?"0":start;
		limit = limit==null||"".equals(limit)?"30":limit;
		
		List<String> conditions = new ArrayList<String>();
		if(baseparams!=null){
			String[] str1 = baseparams.split("&");
			for (int i = 0; i < str1.length; i++) {
				String value = "";
				String par = str1[i];
				String[] str2 = par.split("=");
				String key = str2[0];
				if (str2.length >1) {
					value = str2[1];
				}
				
				//如果是单独配置查询，使用了like，注意要加''
				if(value != null && !value.equals("")){
					UserQueryDsMeta dsMeta = getUserQueryDsMeta(userDsMetas, key);
					if(dsMeta!=null){
						value = URLDecoder.decode(value,"UTF-8");
						if(value.trim().equals("")){
							continue;
						}
						//如果是tree节点，取[]中的值
						if(value.indexOf("[")>-1){
							value = GlobalUtil.getParam(value, "[", "]");
						}
						//连接条件，默认=
						String link_type = dsMeta.getLink_type_cd();
						if(link_type==null){
							link_type = "=";
						}
						
						//生成查询条件
						if(dsMeta.getField_type().equals(ExtConstant.EXT_COMBOBOX) || dsMeta.getField_type().equals(ExtConstant.EXT_TREEBOX)){
							//固定值
							conditions.add(" and x."+key+" "+link_type+" '"+ value +"'");
						}else if(dsMeta.getField_type().equals(ExtConstant.EXT_DATEFIELD)){
							//日期
							conditions.add(" and trunc(x."+key+") "+link_type+" str_to_date('"+ value +"','%Y-%m-%d')");
						}else if(dsMeta.getField_type().equals(ExtConstant.EXT_NUMBERFIELD) || dsMeta.getField_type().equals(ExtConstant.EXT_MONEYFIELD)){
							//数值
							conditions.add(" and x."+key+" "+link_type+" "+value);
						}else{
							//字符
							if(link_type.indexOf("like")>-1){
								conditions.add(" and x."+key+" "+link_type+" '%"+ value +"%'");
							}else{
								conditions.add(" and x."+key+" "+link_type+" '"+ value +"'");
							}
						}
					}
				}
			}
		}
		
		StringBuffer sqlTmpl = new StringBuffer("select x.* from ("+baseSql+") x where 1=1 ");
		if(conditions.size()>0){
			for (String string : conditions) {
				sqlTmpl.append(string);
			}
		}
		//记录sql
		String countSql  = sqlTmpl.toString();
		//排序
		if(orderList.size()>0){
			sqlTmpl.append(" order by ");
			for (int i = 0; i < orderList.size(); i++) {
				if(i>0){
					sqlTmpl.append(",");
				}
				sqlTmpl.append("x.").append(orderList.get(i));
			}
		}
		
		//分页对象
		PageResult<Map<String,Object>> result = new PageResult<Map<String,Object>>();
		
		//判断导出
		if(!isExport){
			List<Map<String, Object>> results = queryPageResult(sqlTmpl.toString(), new PageRange(start, limit));
			result.setResults(results);
			result.setTotalCount(getTotalCount(countSql));
		}else{
			List<Map<String, Object>> results = this.jdbcTemplate.queryForList(sqlTmpl.toString());
			result.setResults(results);
		}
		return result;
	}
	
	/**
	 * 自定义分页查询
	 * @param sql
	 * @param range
	 * @return
	 */
	protected List<Map<String, Object>> queryPageResult(String sql,PageRange range){
		//StringBuffer sqlBuffer = new StringBuffer("WITH T1 AS (");
		StringBuffer sqlBuffer = new StringBuffer("select e.* from ( ");
		sqlBuffer.append(sql);
		/*sqlBuffer.append("	),T2 AS (SELECT ROWNUM AS FRAMEWORKROWNUM, T1.* FROM T1)");
		sqlBuffer.append("SELECT * FROM T2 WHERE FRAMEWORKROWNUM BETWEEN ("+range.getStart()+"+1) AND ("+range.getStart()+"+"+range.getLimit()+")");*/
		sqlBuffer.append(" ) ");
		sqlBuffer.append(" e limit #start,#limit ");
		return GlobalUtil.lowercaseKeyAndDate(jdbcTemplate.queryForList(sqlBuffer.toString()));
	}
	
	/**
	 * 检索 UserQueryDsMeta,注意field_id小写
	 * @param dsMetas
	 * @param metaId
	 * @return
	 */
	private UserQueryDsMeta getUserQueryDsMeta(List<UserQueryDsMeta> dsMetas,String metaId){
		for (UserQueryDsMeta userQueryDsMeta : dsMetas) {
			if(userQueryDsMeta.getField_id().toLowerCase().equals(metaId)){
				return userQueryDsMeta;
			}
		}
		return null;
	}
	
	/**
	 * 获取导出配置信息
	 * @param dsId
	 * @param requestParam
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public SheetConfig getExportConfig(String dsId,Map<String, Object> requestParam,SysUserInfo user)throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dsId", dsId);
		QueryDs ds = queryDsDao.getDsInfo(param);
		//2.查询字段配置
		param.put("userId", user.getUser_id());
		List<UserQueryDsMeta> userDsMetas = queryDsMetaDao.getUserDsMeta(param);
		
		Map<String, Object> excelConfig = getExcelColByMeta(userDsMetas ,true);
		List<String> colName = (List<String>)excelConfig.get("colName");
		Map<String,List<String>> excelData  = (Map<String,List<String>>)excelConfig.get("excelData");
		
		SheetConfig pshCfg = new SheetConfig();
		pshCfg.setSheetName(ds.getDs_name());
		pshCfg.setColumn(colName);
		pshCfg.setColumnData(excelData);
		pshCfg.setDataList(queryDataForList(dsId,requestParam,user,true).getResults());
		
		return pshCfg;
	}
	
	/**
	 * 生成excel导出时的列头信息
	 * <BR> 返回：colName,excelData
	 * @param metaParamsList
	 * @param isDefault
	 * @param addLink
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getExcelColByMeta(List<UserQueryDsMeta> dsMetas,boolean addLink) throws Exception {
		Map<String,List<String>> excelData = new LinkedHashMap<String, List<String>>();
		List<String> linkList = null;
		List<String> colName = new ArrayList<String>();
		//获取sheet页下字段
		if (dsMetas != null && dsMetas.size() > 0) { 
			
			for (int i = 0; i < dsMetas.size(); i++) {
				UserQueryDsMeta meta = dsMetas.get(i);
				
				String metadata_name = meta.getField_id();
				String metadata_tab = meta.getField_label();
				String metadata_type = meta.getField_type();
				
				//新增数据类型
				String Cell_Text = "";
				if(ExtConstant.EXT_DATEFIELD.equals(metadata_type)) {
					Cell_Text = metadata_tab + "#DATE";
				}else if(ExtConstant.EXT_MONEYFIELD.equals(metadata_type)){
					Cell_Text = metadata_tab + "#MONEY";
				}else if(ExtConstant.EXT_HIDDENFIELD.equals(metadata_type)){ //隐藏域
					Cell_Text = metadata_tab + "#HIDDEN";
				}else{
					Cell_Text = metadata_tab + "#STRING";
				}
				Cell_Text =Cell_Text +"#N"; //是否必输入
				
				//是否生成link信息
				if(addLink){
					String dim_code = meta.getDim_cd();
					String component_default = meta.getDefault_value();
					
					if (dim_code != null && !"".equals(dim_code)) {
						linkList = pageManagerService.getLinkDataForExp(dim_code, component_default);
					}else {
						linkList = null;
					}
				}else{
					linkList = null;
				}
				
				excelData.put(Cell_Text, linkList);
				colName.add(metadata_name);
			}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("colName", colName);
		result.put("excelData", excelData);
		return result;
	}
}
