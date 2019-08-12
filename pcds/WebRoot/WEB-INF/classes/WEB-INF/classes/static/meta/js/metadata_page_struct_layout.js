var formItemAnchor = '91%';
var pageFieldStore;
var pageFieldCm;
var pageFieldGridBar;
var pageFieldGrid;

var pageButtonStore;
var pageButtonCm;
var pageButtonGridBar;
var pageButtonGrid;

//用于存放 加载页面的关联表ID
var relaTableId;

Ext.onReady(function(){
	var pageTitle = getPageTitle();
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		//region:'center',
		title : pageTitle+'属性',
		url : pathUrl + "/metadata/pageStruct/save",
		method : 'POST',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 0px 25px 5px 25px',
		autoScroll : true,
		buttonAlign : 'center',
		itemCls  : 'uxHeight',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'page_struct_id'}, 
			{name : 'page_struct_name'}, 
			{name : 'page_struct_desc'}, 
			{name : 'md_cate_cd'},
			{name : 'rela_table_id'},
			{name : 'rela_table_name'}
		]),
		items : [
			{
				xtype : 'textfield',
				name : 'page_struct_id',
				fieldLabel : pageTitle+'ID',
				allowBlank : false,
				readOnly : true,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'page_struct_name',
				name : 'page_struct_name',
				fieldLabel : pageTitle+'名称',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'hidden',
				id : 'rela_table_id',
				name : 'rela_table_id',
				fieldLabel : '关联表',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'hidden',
				id : 'field_list_id',
				name : 'field_list_id',
				fieldLabel : '字段列表',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'rela_table_name',
				name : 'rela_table_name',
				fieldLabel : '关联表',
				allowBlank : true,
				readOnly:true,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'page_struct_desc',
				name : 'page_struct_desc',
				fieldLabel : pageTitle+'描述',
				anchor : '91%'
			},{
				xtype : 'hidden',
				id : 'md_cate_cd',
				name : 'md_cate_cd',
				fieldLabel : '分类代码',
				allowBlank : false,
				anchor : formItemAnchor
			}
		],
		buttons : [{
			text : '保存',
			handler : function(){
				//如果是通用页面结构 直接保存
				if(!is_show_field_grid){//判断是否传递数据字典参数  如果没有传则为通用页面结构
					savePageStruct(infoPanel);
					window.parent.refreshNode();
					isReConfigRelaTable = false;
					return;
				}
				
				//先获取当前页面下字段列表ID 用于保存关联表的时候的关联字段
				Ext.Ajax.request({
					url: pathUrl+'/metadata/pageRelaMetadata/queryRelaMetadata',
	                params: {prt_metadata_id:metadata_id,md_cate_cd:window.parent.category_type_colfield_list},
					method:'post',
					callback: function(options, success, response){
						var json = Ext.util.JSON.decode(response.responseText);
						if(json.success){
							if(json.results && json.results[0]){
								Ext.getCmp('field_list_id').setValue(json.results[0].rela_metadata_id);
							}
							savePageStruct(infoPanel);
							//页面表关联重置标识
							isReConfigRelaTable = false;
							pageFieldStore.load();
							window.parent.refreshNode();
						}
						
					}
				});
				
			}
		},{
			text : '删除',
			handler : function(){
				Ext.MessageBox.confirm("确认信息", "是否删除该页面结构信息?", function(btn) {
					if (btn == 'yes'){
						//页面表关联重置标识
						isReConfigRelaTable = false;
						deletePageStruct();
					}
				});
			}
		}]
	});
	
	//初始化字段列表信息
	if(is_show_field_grid){
		initPageFieldView();
		if(field_list_id){//如果选中的是字段列表 则含有字段列表ID参数 不进行关联表的设置
			infoPanel.remove('rela_table_name',true);
		}
	}else if(is_show_button_grid){
		initPageButtonView();
		infoPanel.remove('rela_table_name',true);
	}else{
		infoPanel.remove('rela_table_name',true);
	}
	
	var viewport ;
	if(is_show_field_grid){ //显示字段列表 
		 viewport = new Ext.Viewport({
				layout:'border',
				border : false,
				items:[
				       {
				    	   xtype:'panel',
				    	   region:'north',
				    	   height:(md_cate_cd=='PAG' ? 230 : 190),
				    	   items:[
				    	          infoPanel
				    	         ]
				       } ,
				       pageFieldGrid
				],
				listeners:{
					afterrender:function(){
						if(field_list_id){
							return;
						}
						var imgPath = pathUrl+'/public/images/icons/change.png';
						var div = Ext.getDom('rela_table_name').parentNode;
						var span = document.createElement("span");
						span.style.border = "1px solid #B5B8C8";
						span.style.padding = "1px 1px 1px 1px";
						span.style.margin = "0px 0px 0px 2px";
						span.style.verticalAlign = "MIDDLE";
						span.innerHTML = "<a href='javascript:showConfigPageTableTree()'><img src=\""+imgPath+"\"></a>";
						div.appendChild(span);
					}
				}
				
			});
	}else if(is_show_button_grid){ //显示按钮列表
		 viewport = new Ext.Viewport({
				layout:'border',
				border : false,
				items:[
				       {
				    	   xtype:'panel',
				    	   region:'north',
				    	   height:190,
				    	   items:[
				    	          infoPanel
				    	         ]
				       } ,
				       pageButtonGrid
				]
			});
	}else{
		 viewport = new Ext.Viewport({
				layout:'fit',//border
				border : false,
				items:[{
						xtype : 'panel',
						layout : 'form',
						border : false,
						items:[infoPanel]
					}
				]
			});
	}
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/pageStruct/load',
		params : {
			page_struct_id : metadata_id
		},
		success:function(){
			relaTableId = Ext.getCmp('rela_table_id').getValue();
		}
	});
	
	//查询字段列表
	if(pageFieldStore){
		pageFieldStore.load();
	}else if(pageButtonStore){
		pageButtonStore.load();
	}
	
});

function getBlankPanel(height) {
	return {
		xtype : 'panel',
		height : height,
		border : false
	}
}

function getYesNo(val){
	if (val == 'Y'){
		return "是";
	}else if (val == 'N'){
		return "否";
	}
	return val;
}


function initPageFieldView(){
	var listMethod = 'listPageField'
	if(field_list_id){
		listMethod = 'queryPageFieldByListId';
	}
	pageFieldStore = new Ext.data.JsonStore({
		url : pathUrl + '/metadata/pageField/'+listMethod,
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['field_id', 'field_name', 'component_label', 'component_type_id','dim_cd','dim_name', 
		          'default_value', 'col_biz_type_cd', 'col_biz_type_desc', 'display_order','is_editable',
				   'is_must_input','is_pk','max_value','min_value','max_length','is_query_cond'],
		listeners : {
			'beforeload' : function(){
				if(field_list_id){
					pageFieldStore.baseParams = {
							field_list_id :field_list_id
						}
				}else{
					pageFieldStore.baseParams = {
							page_struct_id : metadata_id
						}
				}
				
				
			}
		}
	});
	var smodel=new Ext.grid.CheckboxSelectionModel();
	
	pageFieldCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
	    smodel,
	{
		header : 'field_id',
		dataIndex : 'field_id',
		hidden : true
	}, {
		header : '字段名称',
		dataIndex : 'field_name',
		width:150
	}, {
		header : '字段标签',
		dataIndex : 'component_label',
		width:100
			
	}, {
		header : '控件类型',
		dataIndex : 'component_type_id',
		hidden:true
	}, {
		header : '维度',
		dataIndex : 'dim_cd',
		hidden:true
	}, {
		header : '维度',
		dataIndex : 'dim_name'
	}, {
		header : '默认值',
		dataIndex : 'default_value',
		width:80
	}, {
		header : '业务类型',
		dataIndex : 'col_biz_type_cd',
		hidden:true
	}, {
		header : '业务类型',
		dataIndex : 'col_biz_type_desc',
		width:80
	}, {
		header : '显示顺序',
		dataIndex : 'display_order',
		width:60
	}, {
		header : '是否可编辑',
		dataIndex : 'is_editable',
		align:'center',
		width:80,
		renderer : getYesNo
	}, {
		header : '是否必输',
		dataIndex : 'is_must_input',
		align:'center',
		renderer : getYesNo,
		width:70
	}, {
		header : '是否主键',
		dataIndex : 'is_pk',
		align:'center',
		renderer : getYesNo,
		width:70
	}, {
		header : '最大值',
		dataIndex : 'max_value',
		width:60
	}, {
		header : '最小值',
		dataIndex : 'min_value',
		width:60
	}, {
		header : '最大长度',
		dataIndex : 'max_length',
		width:60
	}, {
		header : '是否查询条件',
		dataIndex : 'is_query_cond',
		align:'center',
		renderer : getYesNo,
		width:60
	}
	]);
	pageFieldGridBar = [{
		text : '新增',
		iconCls : 'add',
		handler : function() {
			addPageField();
		}
	},'-',{
		text : '编辑',
		iconCls : 'edit',
		handler : function() {
			var record = Ext.getCmp("fieldPanel").getSelectionModel().getSelections();
			if(record.length == 0) {
				Ext.MessageBox.alert("提示信息","请选择一条记录!");
				return
			}
			if(record.length == 1){
				editPageField(record[0].get('field_id'));
			}else{
				Ext.MessageBox.alert("提示信息","只能选择一条记录!");
				return
			}
			
		}
	},'-',{
		text : '删除',
		iconCls : 'delete',
		handler : function() {
			var records = Ext.getCmp("fieldPanel").getSelectionModel().getSelections();
			if(records.length == 0) {
				Ext.MessageBox.alert("提示信息","请选择记录!");
				return
			}
			Ext.MessageBox.confirm("确认信息", "是否删除选中的列字段?", function(btn) {
				if (btn == 'yes'){
					deletePageField(records);
				}
			});
		}
	},'-',{
		text : '调整排序',
		iconCls : 'revoke',
		handler : function() {
			if(pageFieldStore.getCount()>0){
				showOrderWin();
			}
		}
	},'-',{
		text : '同步',
		iconCls : 'fresh',
		handler : function() {
			syncTableField();
		}
	}];
	
	pageFieldGrid = new Ext.grid.GridPanel({
		region:'center',
		id : 'fieldPanel',
        title:'页面字段列表',
	    autoScroll: true,
        loadMask: true,
		split:true,
		ds: pageFieldStore,
		cm: pageFieldCm,
		sm:smodel,
		tbar: pageFieldGridBar
		 
	});
}

/***
 * 初始化按钮列表
 */
function initPageButtonView(){
	var listMethod = 'queryPageButtons';
	
	pageButtonStore = new Ext.data.JsonStore({
		url : pathUrl + '/metadata/pageButton/'+listMethod,
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['button_id', 'button_name', 'md_cate_cd','button_func_cd','button_func_name','icon_path','rela_metadata_id','rela_metadata_name','is_customer_sql','dml_sql'],
		listeners : {
			'beforeload' : function(){
				pageButtonStore.baseParams = {
						prt_metadata_id : metadata_id
					}
				
			}
		}
	});
	var smodel=new Ext.grid.CheckboxSelectionModel();
	
	pageButtonCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
	    smodel,
	{
		header : '按钮ID',
		dataIndex : 'button_id',
		hidden : true
	}, {
		header : '字段名称',
		dataIndex : 'button_name',
		width:150
	}, {
		header : '元数据类型',
		dataIndex : 'md_cate_cd',
		hidden:true
	}, {
		header : '按钮功能代码',
		dataIndex : 'button_func_cd',
		hidden:true
	}, {
		header : '按钮功能',
		dataIndex : 'button_func_name',
		width:150
	}, {
		header : '关联表单ID',
		dataIndex : 'rela_metadata_id',
		hidden:true
	}, {
		header : '关联表单',
		dataIndex : 'rela_metadata_name',
		hidden:(md_cate_cd == window.parent.category_type_form),
		width:150
	}, {
		header : '图标路径',
		dataIndex : 'icon_path',
		width:120
	}, {
		header : '是否自定义SQL',
		dataIndex : 'is_customer_sql',
		width:100,
		align:'center',
		renderer:getYesNo
	}
	]);
	pageButtonGridBar = [{
		text : '新增',
		iconCls : 'add',
		handler : function() {
			addPageButton();
		}
	},'-',{
		text : '编辑',
		iconCls : 'edit',
		handler : function() {
			var record = Ext.getCmp("buttonPanel").getSelectionModel().getSelections();
			if(record.length == 0) {
				Ext.MessageBox.alert("提示信息","请选择一条记录!");
				return
			}
			if(record.length == 1){
				editPageButton(record[0].get('button_id'));
			}else{
				Ext.MessageBox.alert("提示信息","只能选择一条记录!");
				return
			}
			
		}
	},'-',{
		text : '删除',
		iconCls : 'delete',
		handler : function() {
			var records = Ext.getCmp("buttonPanel").getSelectionModel().getSelections();
			if(records.length == 0) {
				Ext.MessageBox.alert("提示信息","请选择记录!");
				return
			}
			Ext.MessageBox.confirm("确认信息", "是否删除选中的列字段?", function(btn) {
				if (btn == 'yes'){
					deletePageButton(records);
				}
			});
		}
	},'-',{
		text : '调整排序',
		iconCls : 'fresh',
		handler : function() {
			if(pageButtonStore.getCount()>0){
				showOrderWinForBtn();
			}
		}
	}];
	
	pageButtonGrid = new Ext.grid.GridPanel({
		region:'center',
		id : 'buttonPanel',
        title:'按钮列表',
	    autoScroll: true,
        loadMask: true,
		split:true,
		ds: pageButtonStore,
		cm: pageButtonCm,
		sm:smodel,
		tbar: pageButtonGridBar
	});
}

function getPageTitle(){
	if(md_cate_cd == window.parent.category_type_colfield_list){
		return '字段列表';
	}else if (md_cate_cd == window.parent.category_type_toolbar){
		return '工具条';
	}else if (md_cate_cd == window.parent.category_type_form){
		return '表单';
	}else if (md_cate_cd == window.parent.category_type_page){
		return '页面';
	}else{
		return '页面结构';
	}
}