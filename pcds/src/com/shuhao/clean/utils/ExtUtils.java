package com.shuhao.clean.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.shuhao.clean.apps.meta.entity.XmlObject;
import com.shuhao.clean.apps.model.ext.BaseField;
import com.shuhao.clean.apps.model.ext.Bbar;
import com.shuhao.clean.apps.model.ext.Button;
import com.shuhao.clean.apps.model.ext.ColumnModel;
import com.shuhao.clean.apps.model.ext.ComboBox;
import com.shuhao.clean.apps.model.ext.CustField;
import com.shuhao.clean.apps.model.ext.DateField;
import com.shuhao.clean.apps.model.ext.FormLayout;
import com.shuhao.clean.apps.model.ext.FormPanel;
import com.shuhao.clean.apps.model.ext.FormWindow;
import com.shuhao.clean.apps.model.ext.GridPanel;
import com.shuhao.clean.apps.model.ext.HiddenField;
import com.shuhao.clean.apps.model.ext.JsonReader;
import com.shuhao.clean.apps.model.ext.JsonStore;
import com.shuhao.clean.apps.model.ext.Layout;
import com.shuhao.clean.apps.model.ext.MoneyField;
import com.shuhao.clean.apps.model.ext.NumberField;
import com.shuhao.clean.apps.model.ext.Panel;
import com.shuhao.clean.apps.model.ext.TabPanel;
import com.shuhao.clean.apps.model.ext.Tbar;
import com.shuhao.clean.apps.model.ext.TextArea;
import com.shuhao.clean.apps.model.ext.TextField;
import com.shuhao.clean.apps.model.ext.ThreePanelLayout;
import com.shuhao.clean.apps.model.ext.TreeBox;
import com.shuhao.clean.apps.model.ext.base.BaseExt;
import com.shuhao.clean.apps.model.ext.base.ExtEvent;
import com.shuhao.clean.constant.ExtConstant;
import com.shuhao.clean.utils.xml.XmlStrUtil;
import com.shuhao.clean.utils.xml.XmlUtil;

public  class  ExtUtils {
	
	private static final String enter = "";
	
	private static final String load_url = "/pageManager/getMetaData";
	
	/**
	 * 为控件添加事件
	 */
	private synchronized static void addEvent(String templateId,BaseField field)throws Exception{
		//单独事件
		XmlObject eventObject = XmlStrUtil.getFnEvent(templateId,field.getRealName());
		//通用事件
		if(eventObject==null){
			eventObject = XmlStrUtil.getFnEvent(ExtConstant.COMMON_FIELD_EVENT_PID,field.getRealName());
		}
		if(eventObject!=null){
			ExtEvent e= new ExtEvent(templateId,field.getRealName());
			e.setEventObject(eventObject);
			field.setEvent(e);
		}
	}
	
	/*private synchronized static void addEvent(String templateId,BaseField field)throws Exception{
		//单独事件
		XmlObject eventObject = XmlUtil.getFnEvent(templateId,field.getRealName());
		//通用事件
		if(eventObject==null){
			eventObject = XmlUtil.getFnEvent(ExtConstant.COMMON_FIELD_EVENT_PID,field.getRealName());
		}
		if(eventObject!=null){
			ExtEvent e= new ExtEvent(templateId,field.getRealName());
			e.setEventObject(eventObject);
			field.setEvent(e);
		}
	}*/
	
	/**
	 * 载入页面要显示的加在onReady里
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static String onReady(List<Map<String,Object>> list,String hasApply) throws Exception{
		StringBuffer stores = new StringBuffer();//需要先加载的store
		StringBuffer formWindow = new StringBuffer();//formwindow
		StringBuffer function = new StringBuffer();//定义的函数
		List<BaseExt> items = new ArrayList<BaseExt>();
		ThreePanelLayout tpl = new ThreePanelLayout();
		TabPanel tb = new TabPanel();
		tb.setHeight(200);
		tb.setActiveTab(0);
		List<BaseExt> tbitems = new ArrayList<BaseExt>();
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> tempMap = list.get(i);
			String type = GlobalUtil.getStringValue(tempMap, "type");
			if("parent".equals(type)){
				GridPanel gp = (GridPanel) tempMap.get("gridPanel");
				FormPanel fp = (FormPanel) tempMap.get("queryForm");
				List<FormWindow> fwList = (List<FormWindow>) tempMap.get("formWindow");
				Map<String, String> storeMap =  (Map<String, String>) tempMap.get("storeMap");
				String functionStr = GlobalUtil.getStringValue(tempMap, "function");
				function.append(functionStr);
				
				tpl.setEastPanel(fp);
				tpl.setCenterPanel(gp);
				
				for (Iterator<String> iterator = storeMap.keySet().iterator(); iterator.hasNext();) {
					String key = iterator.next();
					String store = (String)storeMap.get(key);
					stores.append(store+";");
					stores.append(key+".load();").append(enter);
				}
				if (fwList!=null&&fwList.size()>0) {
					for (int j = 0; j < fwList.size(); j++) {
						FormWindow fw = fwList.get(j);
						formWindow.append(fw.output()).append("  ");
					}
				}
			}else if("child".equals(type)){
				GridPanel fp = (GridPanel) tempMap.get("gridPanel");
				Map<String, String> storeMap =  tempMap.get("storeMap")!=null?(Map<String, String>) tempMap.get("storeMap"):null;
				List<FormWindow> fwList = (List<FormWindow>) tempMap.get("formWindow");
				tbitems.add(fp);
				if(storeMap!=null){
					for (Iterator<String> iterator = storeMap.keySet().iterator(); iterator.hasNext();) {
						String key = iterator.next();
						String store = (String)storeMap.get(key);
						stores.append(store+";");
						stores.append(key+".load();");
					}
				}
				if (fwList!=null&&fwList.size()>0) {
					for (int j = 0; j < fwList.size(); j++) {
						FormWindow fw = fwList.get(j);
						formWindow.append(fw.output()).append("  ");
					}
				}
			}
		}
		tb.setPanels(tbitems);
		if(tbitems.size()>0){
			tpl.setSouthPanel(tb);
		}
		
		items.add(tpl);
		
		//

		StringBuffer buffer = new StringBuffer();
		StringBuffer funbuffer = new StringBuffer();
		Panel panel = new Panel();
		panel.setLayout("fit");
		panel.setId("tab_panel");
		panel.setPanelName("tbpanel");
		panel.setBodyStyle("padding:0px 0px 0 0px");
		panel.setItems(tpl);
		buffer.append(formWindow.toString());//定义window窗体
		if(function!=null){
			funbuffer.append(function).append(enter);
		}
		buffer.append(stores);
		buffer.append(panel.output()).append(enter);
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> tempMap = list.get(i);
				String actionEvent = (String) tempMap.get("actionEvent");
				buffer.append(actionEvent==null?"":actionEvent).append(enter);
		}
		
//			 custMgrStore.on("beforeload",function(store){
//				  var custMgrStr 		  = Ext.getCmp("searchCustMgr").getValue();							
//				  custMgrStore.baseParams = {
//					  enter_cust_mgr_id   : custMgrStr
//				  }; 
//			  });
		buffer.append(funbuffer.toString());
		return buffer.toString();
	}
	
	
	
//	/**
//	 * @param dataList
//	 * @param approve_role
//	 * @param templateId
//	 * @param tmplType
//	 * @param templateMap
//	 * @return
//	 * @throws Exception 
//	 */
//	public static GridPanel creatGrid(List<Map<String,Object>> dataList,String approve_role, String templateId, String tmplType, Map<String, Object> templateMap) throws Exception{
//	//开始生成 jsonstore　
//		
//		JsonReader reader = new JsonReader(dataList);
//		reader.setId("data"+templateId);
//		reader.setTotalProperty("totalCount");//设置总记录数变量　　取默认
//		reader.setRoot("results");//设置json数据根节点   取默认
//		
//
//		JsonStore store = new JsonStore();
//		if("parent".equals(tmplType)){
//			store.setId("data");
//		}else{
//			store.setId("data"+templateId);
//		}
//		store.setUrl(load_url + "/"+templateId);//设置请求地址
//		List<String> baseParams = new ArrayList<String>();
//		baseParams.add("start : 0");
//		baseParams.add("limit : 30");
//		store.setBaseParams(baseParams);  //设置默认参数
//		store.setAutoLoad(false); //是否自动加载
//		store.setReader(reader); //设置数据映射
//		
//	
//		//开始生成　grid  的列头
//		ColumnModel model = new ColumnModel(dataList,tmplType);
//		model.setId("data"+templateId);
//		
//		//开始生成　tbar　bbar   //默认包括新增，修改，删除
//		Tbar tbar = new Tbar();  
//		List<Button> bars = new ArrayList<Button>();
//		
//		if ("01".equals(approve_role)) {   //提交人员
//		
//				Button addBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_ADD,templateId);
//				bars.add(addBtn);
//				
//				Button updBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_UPD,templateId);
//				bars.add(updBtn);
//				
//				Button delBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_DEL,templateId);
//				bars.add(delBtn);
//				
//				if("parent".equals(tmplType)){
//					
//					Button copyBtn = new Button();
//					copyBtn.setText("复制");
//					copyBtn.setIconCls("copy");
//					copyBtn.setHandler("doCoypRowData();");
//					bars.add(copyBtn);
//					
//					Button expBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_EXP,templateId);
//					expBtn.setText("导出模板");
//					bars.add(expBtn);
//					
//					Button impBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_IMP,templateId);
//					impBtn.setText("导入模板");
//					impBtn.setHandler("doImportData('"+templateId+"',null)");
//					bars.add(impBtn);
//					
//					Button appBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_APPLY_UP);
//					bars.add(appBtn);
//					
//					Button bckBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_APPLY_BACK);
//					bars.add(bckBtn);
//				}
//				if(templateMap!=null){
//					List<Map<String, Object>> childbtnList = (List<Map<String, Object>>) templateMap.get("btn");
//					for (int i = 0; i < childbtnList.size(); i++) {
//						Map<String,Object> tmp = childbtnList.get(i);
//						if(ExtConstant.EXT_BUTTON_TYPE_IMP.equals(GlobalUtil.getStringValue(tmp, "component_type"))){
//							Button impBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_IMP,templateId);
//							bars.add(impBtn);
//						}
//					}
//				}
//			
//		}else if ("02".equals(approve_role)) {   //一级审批
//			if("parent".equals(tmplType)){
//				Button appBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_APPLY_AGREE);
//				appBtn.setHandler("approveFlow('02','0');");
//				bars.add(appBtn);
//				
//				Button bckBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_APPLY_RECALL);
//				bckBtn.setHandler("approveFlow('02','1');");
//				bars.add(bckBtn);
//			}
//		}else{              //二级审批
//			if("parent".equals(tmplType)){
//				Button appBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_APPLY_AGREE);
//				appBtn.setHandler("approveFlow('05','0');");
//				bars.add(appBtn);
//				
//				Button bckBtn = new Button(ExtConstant.EXT_BUTTON_TYPE_APPLY_RECALL);
//				bckBtn.setHandler("approveFlow('05','1');");
//				bars.add(bckBtn);
//			}
//		}
//		tbar.setTbars(bars);
//		
//		//分页
//		Bbar bbar = new Bbar();
//		bbar.setStoreName(store.getId()+"Store");
//		
//		//开始生成grid    布局，渲染位置，
//		
//		GridPanel grid = new GridPanel();
//		grid.setId("dataGrid"+templateId);
//		grid.setRegion("center");    //布局
//		grid.setDataStore(store);
//		grid.setColModel(model);
//		
//		
//		
//		grid.setTbar(tbar);
//		grid.setBodyStyle("");
//		if("child".equals(tmplType)){
//			String template_name = GlobalUtil.getStringValue(templateMap, "template_name");
//			grid.setClosable(false);
//			grid.setTitle(template_name);
//		}else{
//			grid.setBbar(bbar);
//		}		
//		return grid;
//	}
//	public static FormPanel creatQueryForm(List<BaseExt>  queryObj,String approve_role) throws Exception{
//		
//		int rowCount = 0;
//		if(queryObj.size()%4 == 0){
//			rowCount = queryObj.size()/4;
//		}else
//			rowCount = queryObj.size()/4 +1;
//		
//		//开始生成查询菜单
//		FormPanel form = new FormPanel();
//		form.setId("query");
//		form.setRegion("east");    //更改菜单布局只需更改此属性
//		form.setAutoHeight(true);
//		form.setTitle("设置查询条件");
//		form.setFrame(false);
//		form.setMethod("POST");
//		form.setClosable(false);
//		form.setLabelWidth(70);
//		form.setCollapsible(true);
//		
//		if (form.getRegion().equals("east")) {
//			form.setLayout("form");
//			form.setWidth(270);
//			List<Button> list  = new ArrayList<Button>();
//			Button btn = new Button(ExtConstant.EXT_BUTTON_TYPE_QUE);
//			btn.setXtype("button");
//			btn.setHandler("doQuery();");
//			list.add(btn);
//			
//			Button exp = new Button();
//			exp.setXtype("button");
//			exp.setText("导出");
//			exp.setHandler("doExport();");
//			list.add(exp);
//			
//			form.setButtons(list);
//			form.setButtonAlign("center");
//		}else{
//			form.setLayout("column");///列布局
//			form.setHeight(rowCount*29+40-rowCount);
//		}
//		
//		//计算新增布局
//		Layout layout = new FormLayout(queryObj,form.getRegion(),approve_role);
//		form.setItems(layout);
//		return form;
//	}

	/**
	 * @param eleMap
	 * @param hasApply 是否有申请权限
	 * @return
	 * @throws Exception 
	 */
	public static String createPage(List<Map<String, Object>> eleList,String hasApply,String hasApprove) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> eleMap : eleList) {
			String type = GlobalUtil.getStringValue(eleMap, "type");//父模板还是子模板
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);
			eleMap.put("storeMap", new HashMap<String, Object>());

			//创建表单字段（新增、编辑）
 			List<Map<String, Object>> fields = createFields(eleMap);
			
			//创建表单
			List<FormWindow> fwList = createFormWindow(eleMap,fields);
			map.put("formWindow", fwList);
			//父模版
			if("parent".equals(type)){
				boolean hideExport = hasApply.equals("N") && hasApprove.equals("N");
				//创建查询表单
				FormPanel queryForm = creatQueryForm(eleMap,hideExport);
				map.put("queryForm", queryForm);
				//创建父模版页面事件  
				String actionEvent = createActionEvent(eleList);
				map.put("actionEvent", actionEvent);
				//生成子父模版关联参数
				String function = createFunction(eleList);
				map.put("function", function);
			}
			
			GridPanel gp = createGrid(eleMap);//grid
			Tbar tbar = createTBA(eleMap);//工具条
			if("Y".equals(hasApply)){
				gp.setTbar(tbar);
			}
			map.put("gridPanel", gp);
			map.put("storeMap", eleMap.get("storeMap"));
			list.add(map);
		}
		return onReady(list,hasApply);
	}

	/**
	 * 子父模版关联事件
	 * @param eleList
	 */
	private static String createActionEvent(List<Map<String, Object>> eleList) {
		List<Map<String, Object>> childTemplLsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> parentMap = null;
		for (Map<String, Object> map : eleList) {
			String type = GlobalUtil.getStringValue(map, "type");
			if("parent".equals(type)){
				parentMap = map;
			}else{
				childTemplLsit.add(map);
			}
		}
		String type = GlobalUtil.getStringValue(parentMap, "type");
		StringBuffer buffer = new StringBuffer();
		//父模版选中一条记录事件
		buffer.append("Ext.getCmp('dataGrid"+GlobalUtil.getStringValue(parentMap, "tmpl_id")+"').on('rowclick',function(store,e){").append(enter);
		buffer.append(" storeArray=[];");
		buffer.append(" records = Ext.getCmp('dataGrid"+GlobalUtil.getStringValue(parentMap, "tmpl_id")+"').getSelectionModel().getSelections();");
		for (int i = 0; i < childTemplLsit.size();i++) {
			StringBuffer params  = new StringBuffer();
			String tmpl_names = GlobalUtil.getStringValue(childTemplLsit.get(i),"rela_metadata_names");
			String tmpl_id = GlobalUtil.getStringValue(childTemplLsit.get(i),"tmpl_id");

			//子模版与父模版关联的字段
			List<Map<String,Object>> relaList = (List<Map<String, Object>>) childTemplLsit.get(i).get("relaNames");
			
//			String[] names =null;
//			if(GlobalUtil.isNotNull(tmpl_names)){
//				names = tmpl_names.split(";");
//			}
			if(relaList!=null){
				for (int j = 0; j < relaList.size(); j++) {
					Map<String,Object> tempMap = relaList.get(j);
					String field_name = GlobalUtil.getStringValue(tempMap, "field_name");
					String prt_field_name = GlobalUtil.getStringValue(tempMap, "prt_field_name");
					params.append("'aa_"+field_name.toLowerCase()+"='+").append("records[0].get('"+prt_field_name.toLowerCase()+"')");
					if(j<relaList.size()-1){
						params.append("+'&'+");
					}
				}
			}
			buffer.append(" storeArray.push('"+tmpl_id+"');");
			buffer.append(" Ext.getCmp('dataGrid"+tmpl_id+"').getStore().baseParams={type_000:'child',tmpl_id :'"+tmpl_id+"',baseparams:"+params.toString()+"+''};").append(enter);
			buffer.append(" Ext.getCmp('dataGrid"+tmpl_id+"').getStore().load();");
		}
		buffer.append(" });").append(enter);
		//beforeload
		buffer.append("var parentStore = Ext.getCmp('dataGrid"+GlobalUtil.getStringValue(parentMap, "tmpl_id")+"').getStore();");
		buffer.append("parentStore.on('beforeload',function(store){");
		buffer.append("var pars = _queryForm.getForm().getValues(true);");
		buffer.append("parentStore.baseParams = {");
		buffer.append("baseparams : pars");
		//增加参数hasApprove是否有审批权限
		buffer.append(",hasApprove : hasApprove");
		buffer.append(" }; ");
		for (Map<String, Object> map : childTemplLsit) {
			String childTmpl = GlobalUtil.getStringValue(map, "tmpl_id");
			buffer.append(" Ext.getCmp('dataGrid"+childTmpl+"').getStore().removeAll();");
		}
		buffer.append("});");
		//click
		buffer.append("var parentGrid = Ext.getCmp('dataGrid"+GlobalUtil.getStringValue(parentMap, "tmpl_id")+"');");
		buffer.append("parentGrid.on('click',function(){");
		buffer.append(" records = parentGrid.getSelectionModel().getSelections();");
		buffer.append(" }); ");
		return buffer.toString();
	}
	
	/**
	 * 创建查询页面中关联函数，生成子父模版关联参数
	 * @param eleList
	 */
	private static String createFunction(List<Map<String, Object>> eleList) {
		StringBuffer buffer = new StringBuffer();
		StringBuffer getRelaValues = new StringBuffer();
		buffer.append(" function setRelaValue(tmpl_id,win,r){").append("\n");
		buffer.append(" if(!r){ return;}").append("\n");
		getRelaValues.append("var getRelaValues = function(tmpl_id,r){").append("\n");
		getRelaValues.append(" var relaValues = '';").append("\n");
		for (Map<String, Object> map : eleList) {
			String tmpl_id = GlobalUtil.getStringValue(map, "tmpl_id");
			//????
		//	String rela_metadata_names = GlobalUtil.getStringValue(map, "rela_metadata_names");
		//	String[] rela_names = rela_metadata_names.split(",");
			buffer.append("if(tmpl_id=='"+tmpl_id+"'){").append("\n");
			getRelaValues.append("if(tmpl_id=='"+tmpl_id+"'){").append("\n");
			buffer.append("var form_"+tmpl_id+" = win.findByType('form')[0];").append("\n");
			//取子父模版关联参数
			List<Map<String,Object>> relaList = (List<Map<String, Object>>) map.get("relaNames");
			for (int i = 0; i < relaList.size(); i++) {
				Map<String,Object> tempMap = relaList.get(i);
				String column_name = GlobalUtil.getStringValue(tempMap, "column_name");
				String field_name = GlobalUtil.getStringValue(tempMap, "field_name");
				String prt_field_name = GlobalUtil.getStringValue(tempMap, "prt_field_name");
				buffer.append("form_"+tmpl_id+".find('realName','"+field_name.toLowerCase()+"')[0].setValue(r.get('"+prt_field_name.toLowerCase()+"'));").append("\n");
				getRelaValues.append("relaValues +=r.get('"+prt_field_name.toLowerCase()+"');").append("\n");
				if(i < relaList.size()-1){
					getRelaValues.append(" relaValues +=',';").append("\n");
				}
			}
//			for (int i = 0; i < rela_names.length; i++) {
//				String real_name = rela_names[i].toLowerCase();
//				buffer.append("form_"+tmpl_id+".find('realName','"+real_name+"')[0].setValue(r.get('"+real_name+"'));").append("\n");
//				getRelaValues.append("relaValues +=r.get('"+real_name+"');").append("\n");
//				if(i < rela_names.length-1){
//					getRelaValues.append(" relaValues +=',';").append("\n");
//				}
//			}
			buffer.append("}").append("\n");
			getRelaValues.append(" return  relaValues;}").append("\n");
		}
		buffer.append("};").append("\n");
		getRelaValues.append("}");
		return buffer.append(getRelaValues.toString()).toString();
	}

	
	

	/**
	 * @param eleMap
	 * @param hideExport 是否隐藏导出数据
	 * @return
	 * @throws Exception 
	 */
	private static FormPanel creatQueryForm(Map<String, Object> eleMap,boolean hideExport) throws Exception {
		String tmpl_id = GlobalUtil.getStringValue(eleMap, "tmpl_id");
		List<BaseExt> queryObj = (List<BaseExt>) eleMap.get("query");
		int rowCount = 0;
		if(queryObj.size()%4 == 0){
			rowCount = queryObj.size()/4;
		}else
			rowCount = queryObj.size()/4 +1;
		
		//开始生成查询菜单
		FormPanel form = new FormPanel();
		form.setId("query");
		form.setRegion("east");    //更改菜单布局只需更改此属性
		form.setAutoHeight(true);
		form.setTitle("设置查询条件");
		form.setFrame(false);
		form.setMethod("POST");
		form.setLabelWidth(70);
		form.setCollapsible(true);
		
		if (form.getRegion().equals("east")) {
			form.setLayout("form");
			form.setWidth(270);
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
		}else{
			form.setLayout("column");///列布局
			form.setHeight(rowCount*29+40-rowCount);
		}
		
		//计算新增布局
		Layout layout = new FormLayout(null,eleMap,1);
		form.setItems(layout);
		return form;
	}

	/**
	 * @param eleMap
	 * @return
	 * @throws Exception 
	 */
	private static GridPanel createGrid(Map<String, Object> eleMap) throws Exception {
		List<Map<String,Object>> dataList = (List<Map<String,Object>>) eleMap.get("FDL");
		String tmpl_id = GlobalUtil.getStringValue(eleMap, "tmpl_id");
		String type = GlobalUtil.getStringValue(eleMap, "type");
		//开始生成 jsonstore　
			
		JsonReader reader = new JsonReader(dataList);
		reader.setId("reader"+tmpl_id+"");
		reader.setTotalProperty("totalCount");//设置总记录数变量　　取默认
		reader.setRoot("results");//设置json数据根节点   取默认
		//是否拥有审批流程
		reader.setHasWorkFlow(eleMap.get("flow_tmpl_id")!=null);

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
		ColumnModel model = new ColumnModel(dataList,eleMap);
		model.setId("cm"+tmpl_id);
		
		//分页
		Bbar bbar = new Bbar();
		bbar.setStoreName(store.getId()+"Store");
		//开始生成grid    布局，渲染位置，
		
		GridPanel grid = new GridPanel();
		grid.setId("dataGrid"+tmpl_id);
		grid.setRegion("center");    //布局
		grid.setDataStore(store);
		grid.setColModel(model);
		grid.setBodyStyle("");
		
		if("child".equals(type)){
			String template_name = GlobalUtil.getStringValue(eleMap, "template_name");
			grid.setClosable(false);
			grid.setTitle(template_name);
		}else{
			grid.setBbar(bbar);
		}		
		return grid;
	}

	/**
	 * 创建弹出框window及表单
	 * @param eleMap
	 * @param listBizTypeFields 
	 * @return
	 * @throws Exception 
	 */
	private static List<FormWindow> createFormWindow(Map<String, Object> eleMap, List<Map<String, Object>> listBizTypeFields) throws Exception {
		List<FormWindow> list = new ArrayList<FormWindow>();
		List<Map<String,Object>> formlist =  (List<Map<String, Object>>) eleMap.get("FML");
		
		//循环生成表单
		for (int i = 0; i < formlist.size(); i++) {
			Map<String,Object> tempForm = formlist.get(i);
			List<Map<String,Object>> btnlist =  (List<Map<String, Object>>) tempForm.get("FRM");
			List<Button> btns = new ArrayList<Button>();
			for (int j = 0; j < btnlist.size(); j++) {
				Map<String,Object> tempBtn = btnlist.get(j);
				tempBtn.putAll(eleMap);
				btns.add(new Button(tempBtn));
			}
			String metadata_id = GlobalUtil.getStringValue(tempForm, "metadata_id");
			//创建WIndow
			FormWindow fw = new FormWindow();
			fw.setTitle(GlobalUtil.getStringValue(tempForm, "metadata_name"));
			fw.setId(metadata_id);
			fw.setButtons(btns);
			//创建formPanel
			FormPanel fp = new FormPanel();
			fp.setId(metadata_id); //表单ID
			JsonReader reader = new JsonReader((List<Map<String, Object>>) eleMap.get("FDL"),"form");
			reader.setId("reader");
			reader.setTotalProperty("totalCount");//设置总记录数变量　　取默认
			reader.setRoot("results");//设置json数据根节点   取默认
			fp.setReader(reader);
			FormLayout editfl = new FormLayout(listBizTypeFields,eleMap,0);
			fp.setItems(editfl);
			fw.setItems(fp);
			list.add(fw);
		}
		return list;
	}

	/**
	 * @param eleMap
	 * @return
	 * @throws Exception 
	 */
	private static List<Map<String,Object>> createFields(Map<String, Object> eleMap) throws Exception {
		List<Map<String,Object>> colBizType = (List<Map<String, Object>>) eleMap.get("COL_BIZ_TYPE");
		List<Map<String,Object>> FDL = (List<Map<String, Object>>) eleMap.get("FDL");
		String tmpl_id = (String) eleMap.get("tmpl_id");
		for (int i = 0; i < colBizType.size(); i++) {
			Map<String,Object> biz_type= colBizType.get(i);
			List<BaseExt> tempList = new ArrayList<BaseExt>();
			String biz_type_cd = GlobalUtil.getStringValue(biz_type, "col_biz_type_cd");
			for (int j = 0; j < FDL.size(); j++) {
				Map<String,Object> fieldMap = FDL.get(j);
				String component_type_id = GlobalUtil.getStringValue(fieldMap, "component_type_id");
				if(biz_type_cd.equals(GlobalUtil.getStringValue(fieldMap, "col_biz_type_cd"))&&!ExtConstant.EXT_HIDDENFIELD.equals(component_type_id)){
					BaseExt extField = newField((Map<String,Object>)eleMap.get("storeMap"),fieldMap,false);
					//添加事件
					addEvent(tmpl_id, (BaseField) extField);
					tempList.add(extField);
				}
			}
			biz_type.put("fields", tempList);
		}
		List<BaseExt> hidden = new ArrayList<BaseExt>();
		
		List<BaseExt> query = new ArrayList<BaseExt>();
		for (int i = 0; i < FDL.size(); i++) {
			Map<String,Object> fieldMap = FDL.get(i);
			String component_type_id = GlobalUtil.getStringValue(fieldMap, "component_type_id");
			String is_query_cond = GlobalUtil.getStringValue(fieldMap, "is_query_cond");
			if(ExtConstant.EXT_HIDDENFIELD.equals(component_type_id)){
				BaseExt extField = newField((Map<String,Object>)eleMap.get("storeMap"),fieldMap,false);
				addEvent(tmpl_id, (BaseField) extField);
				hidden.add(extField);
			}
			if("Y".equals(is_query_cond)){
				BaseExt extField = newField((Map<String,Object>)eleMap.get("storeMap"),fieldMap,true);
				query.add(extField);
			}
		}
		eleMap.put("hidden", hidden);
		eleMap.put("query", query);
		return colBizType;
	}

	/**is_query是否查询条件控件如果是不做校验
	 * @param fieldMap
	 * @return
	 */
	private static BaseExt newField(Map<String,Object> storeMap,Map<String, Object> fieldMap,boolean is_qyery) {
		String mid = GlobalUtil.parse2String(fieldMap.get("field_id"));  //字段ID
		String type = GlobalUtil.parse2String(fieldMap.get("component_type_id"));   //类型
//		String id =GlobalUtil.parse2String(controlMap.get("component_id"));  //控件ID
		String name = GlobalUtil.parse2String(fieldMap.get("field_name"));
		if(name!=null){
			name = name.toLowerCase();  //控件name
		}
		String label = GlobalUtil.parse2String(fieldMap.get("component_label"));  //控件label
		String value = GlobalUtil.parse2String(fieldMap.get("default_value"));  //控件value
		String hide = GlobalUtil.parse2String(fieldMap.get("is_hidden"));  //隐藏
		String query = GlobalUtil.parse2String( fieldMap.get("is_query_cond"));  //是否为查询条件 
		String desc = GlobalUtil.parse2String( fieldMap.get("component_desc"));  //控件描述
		String dim_code = GlobalUtil.parse2String(fieldMap.get("dim_cd"));  //维度代码表主键
		String column_code = GlobalUtil.parse2String(fieldMap.get("column_code"));  //displayValue
		String column_label = GlobalUtil.parse2String(fieldMap.get("column_label"));  //displayLabel
		String if_editable = GlobalUtil.parse2String(fieldMap.get("is_editable"));
		String if_must_input = GlobalUtil.parse2String(fieldMap.get("is_must_input"));
		
		boolean isHide =  "Y".equals(hide) ? true : false;
		boolean isQuery =  "Y".equals(query) ? true : false;
		//是否可编辑,是否只读都使用isEditable属性，注意在日期和下拉框的时候只能设置editable，与isEditable返回值相反
		boolean isEditable =  "Y".equals(if_editable) ? false : true;   
		
		boolean isBlank =  "Y".equals(if_must_input) ? false : true;  //是否必须输入
		
		
		if(type.equals(ExtConstant.EXT_TEXTFIELD)){
			String max_length = GlobalUtil.parse2String( fieldMap.get("max_length"));
			String min_length = GlobalUtil.parse2String( fieldMap.get("min_length"));
			TextField text = new TextField();
			text.setName(mid+"_"+name);
			text.setRealName(name);
			text.setValue(value);
			text.setHidden(isHide);
			if(!is_qyery){
				if (min_length != null && !min_length.equals("")) {
					text.setMinLength(Integer.parseInt(min_length));
					text.setMinLengthText("输入长度不能少于"+Integer.parseInt(min_length));
				}
				if (max_length != null && !max_length.equals("")) {
					text.setMaxLength(Integer.parseInt(max_length));
					text.setMaxLengthText("输入长度不能多于"+Integer.parseInt(max_length));
				}
				text.setAllowBlank(isBlank);
			}
			if (!isBlank&&!is_qyery) {
				text.setFieldLabel(label +"<font color=red>*</font>");
			}else {
				text.setFieldLabel(label);
			}
			text.setReadOnly(isEditable);
			text.setAnchor("90%");
			return text;
		}else if(ExtConstant.EXT_NUMBERFIELD.equals(type)){
			NumberField number = new NumberField();
			String min_value = GlobalUtil.parse2String(fieldMap.get("min_value"));
			if("0E-30".equals(min_value)){
				min_value="0.000000000000000000000000000001";
			}
			String max_value = GlobalUtil.parse2String(fieldMap.get("max_value"));
			if(!is_qyery){
				if (max_value != null && !max_value.equals("")) {
					number.setMaxValue(Double.parseDouble(max_value));
					//number.setMaxValue(Integer.parse(max_value));
				}
				if (min_value != null && !min_value.equals("")) {
					number.setMinValue(Double.parseDouble(min_value));
				}
				number.setReadOnly(isEditable);
				number.setAllowBlank(isBlank);
			}
			number.setName(mid+"_"+name);
			number.setAnchor("90%");
			number.setHidden(isHide);
			number.setValue(value);
			number.setRealName(name);
			
			if (!isBlank&&!is_qyery) {
				number.setFieldLabel(label +"<font color=red>*</font>");
			}else {
				number.setFieldLabel(label);
			}
			fieldMap.put("type", "number");
			return number;
		}else if(ExtConstant.EXT_MONEYFIELD.equals(type)){
			MoneyField money = new MoneyField();
			money.setName(mid+"_"+name);
			money.setRealName(name);
			money.setValue(value);
			money.setHidden(isHide);
			if (!isBlank&&!is_qyery) {
				money.setFieldLabel(label +"<font color=red>*</font>");
			}else {
				money.setFieldLabel(label);
			}
			if(!is_qyery){
				money.setReadOnly(isEditable);
				money.setAllowBlank(isBlank);
			}
			money.setAnchor("90%");
			fieldMap.put("type", "money");
			return money;
		}else if(ExtConstant.EXT_CUSTFIELD.equals(type)){    //客户经理框
			CustField cust = new CustField();
			
			cust.setId(UID.next("E"));
			cust.setName(mid+"_"+name);
			cust.setRealName(name);
			cust.setValue(value);
			cust.setHidden(isHide);
			if (!isBlank&&!is_qyery) {
				cust.setFieldLabel(label +"<font color=red>*</font>");
			}else {
				cust.setFieldLabel(label);
			}
			
			if(!is_qyery){
				cust.setReadOnly(isEditable);
				cust.setAllowBlank(isBlank);
			}else{
				cust.setShowIcon(false);
				cust.setAnchor("90%");
			}
			
			fieldMap.put("type", "string");
			return cust;
		}else if(ExtConstant.EXT_DATEFIELD.equals(type)) {
			//日期框
			DateField date = new DateField();
			date.setName(mid+"_"+name);
			date.setRealName(name);
			date.setValue(value);
			date.setAnchor("90%");
			date.setHidden(isHide);
			if (!isBlank&&!is_qyery) {
				date.setFieldLabel(label +"<font color=red>*</font>");
			}else {
				date.setFieldLabel(label);
			}
			//date.setReadOnly(isEditable);
			//日期框，设置是否可编辑
			if(!is_qyery){
				date.setEditable(!isEditable);
				date.setAllowBlank(isBlank);
			}
			fieldMap.put("type", "date");
			return date;
		}else if(ExtConstant.EXT_TEXTAREA.equalsIgnoreCase(type)) {
			TextArea area = new TextArea();
			area.setName(mid+"_"+name);
			area.setRealName(name);
			area.setValue(value);
			area.setAnchor("90%");
			area.setHidden(isHide);
			if (!isBlank&&!is_qyery) {
				area.setFieldLabel(label +"<font color=red>*</font>");
			}else {
				area.setFieldLabel(label);
			}
			if(!is_qyery){
				area.setReadOnly(isEditable);
				area.setAllowBlank(isBlank);
			}
			fieldMap.put("type", "string");
			return area;
		}else if(ExtConstant.EXT_COMBOBOX.equalsIgnoreCase(type)) {
			ComboBox box = new ComboBox();
			box.setName(mid+"_"+name);
			box.setRealName(name);
			box.setHiddenName(mid+"_"+name);
			box.setHidden(isHide);
			box.setAnchor("90%");
			box.setEditable(false);
			if (!isBlank&&!is_qyery) {
				box.setFieldLabel(label +"<font color=red>*</font>");
			}else {
				box.setFieldLabel(label);
			}
			if(!is_qyery){
				box.setReadOnly(isEditable);
				box.setAllowBlank(isBlank);
			}
			box.setHidden(isHide);
			box.setValue(value);
			//开始获取维护表数据　生成store
			JsonStore store = new JsonStore();
			store.setAutoLoad(false);
			store.setUrl("/pageManager/getLinkData/"+dim_code);
			store.setId("_"+mid+"_"+name);
			
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			Map<String, Object> display = new HashMap<String, Object>();
			display.put("field_name", column_code);
			Map<String, Object> disvalue = new HashMap<String, Object>();
			disvalue.put("field_name", column_label);
			dataList.add(disvalue);
			dataList.add(display);
			//普通的字段的jsonreader不加默认的Field
			JsonReader reader = new JsonReader(dataList,false);
			reader.setId(mid);
			store.setReader(reader);
			
			box.setStoreName("_"+mid+"_"+name+"Store");
			storeMap.put(box.getStoreName(), store.output());
			
			box.setValueField(column_code);
			box.setDisplayField(column_label);
			fieldMap.put("type", "string");
			return box;
		}else if(ExtConstant.EXT_HIDDENFIELD.equalsIgnoreCase(type)){
			HiddenField  hidden = new HiddenField();
			hidden.setName(mid+"_"+name);
			hidden.setRealName(name);
			if(!is_qyery){
				hidden.setValue(value);
				hidden.setReadOnly(isEditable);
			}
			return hidden;
		}else if(ExtConstant.EXT_TREEBOX.equalsIgnoreCase(type)) {
			TreeBox tree = new TreeBox();
			tree.setName(mid+"_"+name);
			tree.setRealName(name);
			if (!isBlank&&!is_qyery) {
				tree.setTextLabel(label +"<font color=red>*</font>");
			}else {
				tree.setTextLabel(label);
			}
			if(!is_qyery){
				tree.setReadOnly(isEditable);
				tree.setAllowBlank(isBlank);
			}
			tree.setHidden(isHide);
			if (value != null && !value.equals("")) {
				tree.setRootId(value.split(",")[0]);
				if (value.split(",").length > 1) {
					tree.setRootName(value.split(",")[1]);
				}else{
					tree.setRootName("["+value+"]"+label);
				}
			}else {
				tree.setRootId(mid);
				tree.setRootName("["+value+"]"+label);
			}
			if (desc != null && !"".equals(desc)) {
				tree.setRootName("["+value+"]"+desc);
			}
			tree.setDim_code(dim_code);
			fieldMap.put("type", "string");
			return tree;
//		}else  if(type.equalsIgnoreCase(ExtConstant.EXT_CHECKBOX)) {
//		}else if(type.equalsIgnoreCase(ExtConstant.EXT_RADIOBOX)) {
		}
		return null;
	}

	/**
	 * @param eleMap 
	 * @return
	 */
	private static Tbar createTBA(Map<String, Object> eleMap) {
		Tbar tbar = new Tbar();
		List<Map<String,Object>> btnList = (List<Map<String, Object>>) eleMap.get("TBA");
		for (int i = 0; i < btnList.size(); i++) {
			Map<String,Object> temp = btnList.get(i);
			temp.putAll(eleMap);
			Button btn = new Button(temp);
			tbar.addBtn(btn);
		}
		return tbar;
	}
}
