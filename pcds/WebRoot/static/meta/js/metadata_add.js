var category_type_database = 'DB';
var category_type_user = 'USR';
var category_type_theme = 'THM';
var category_type_table = 'TAB';
var category_type_column = 'COL';
var category_type_page = 'PAG';
var category_type_colfield_list = 'FDL';//字段列表
var category_type_toolbar = 'TBA';//工具条 无具体表
var category_type_toolbar_button = 'TBT';//工具条按钮   
var category_type_form = 'FRM';//表单
var category_type_form_button = 'FBT';//表单按钮 
var category_type_colfield = 'FLD';//表单字段
var category_type_excel = 'XLS';//Excel
var category_type_sheet = 'XST';//Excel sheet页
var category_type_excelcol = 'XCL';//sheet页中的列

/**
 * 添加方法,根据父节点的category类型,展示不同的表单界面
 * @param {} parentNodeId 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function doAdd(parentNodeId, md_cate_cd) {
	var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(parentNodeId);
	if(md_cate_cd == 'DB') {
		//添加数据库
		addDataBase(parentNode, md_cate_cd);
	} else if(md_cate_cd == 'USR') {
		//添加数据库用户
		addUser(parentNode, md_cate_cd);
	} else if(md_cate_cd == '01') {
		
	} else if(md_cate_cd == '01') {
		
	} else if(md_cate_cd == '01') {
		
	} else if(md_cate_cd == '01') {
		
	} else if(md_cate_cd == '01') {
		
	} else if(md_cate_cd == '01') {
		
	} else if(md_cate_cd == '01') {
		
	} else {
		Ext.MessageBox.alert("提示信息", "元数据类型错误：md_cate_cd[" + md_cate_cd + "]");
	}
}

/**
 * 根据添加类型和父级节点返回通用的元数据表单项
 * @param {} parentNode
 * @param {} md_cate_cd
 */
function getMetadataFormFields(parentNode,md_cate_cd) {
	var fields = new Array();
	fields = [{
		xtype : 'hidden',
		name : 'prt_metadata_id',
		value : parentNode.id
	},{
		xtype : 'hidden',
		name : 'md_cate_cd',
		value : md_cate_cd
	},{
		xtype : 'hidden',
		name : 'status_cd',
		value : '02'
	},{
		xtype : 'hidden',
		name : 'is_display',
		value : 'Y'
	}];
	return fields;
}

/**
 * 添加数据库
 * @param {} parentNode 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function addDataBase(parentNode, md_cate_cd) {
	var formItemAnchor = '83%';
	var formPanel = new Ext.form.FormPanel({
		layout : 'column',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
			columnWidth : '0.5',
			layout : 'form',
			items : [{
				xtype : 'textfield',
				name : 'database_name',
				fieldLabel : '数据库名称',
				allowBlank : false,
				anchor : formItemAnchor
			}, {
				xtype : 'textfield',
				name : 'connect_str',
				fieldLabel : '数据库连接串',
				allowBlank : false,
				anchor : formItemAnchor
			}, {
				xtype : 'textfield',
				name : 'test_user_name',
				id : 'test_user_name',
				fieldLabel : '测试用户名',
				anchor : formItemAnchor
			}, {
				xtype : 'textfield',
				inputType : 'password',
				name : 'test_user_password',
				id : 'test_user_password',
				fieldLabel : '测试用户密码',
				anchor : formItemAnchor
			}, {
				xtype : 'numberfield',
				name : 'display_order',
				fieldLabel : '显示顺序',
				allowBlank : true,
				anchor : formItemAnchor
			}]
		}, {
			columnWidth : '0.5',
			layout : 'form',
			items : [new ArrayCombo({
				id : 'dbTypeSelector',
				hiddenName : 'database_type_cd',
				data : [['00','Oracle'],['01','DB2'],['02','SQL Server'],['03','Infomix'],['04','MySQL'],['05','Sybase'],['06','Teradata']],
				fieldLabel : '数据库类型',
				defaultValue : '00',
				anchor : formItemAnchor
			}), {
				xtype : 'textfield',
				name : 'server_addr',
				fieldLabel : '服务器地址',
				allowBlank : false,
				anchor : formItemAnchor
			}, {
				xtype : 'numberfield',
				name : 'access_port',
				fieldLabel : '访问端口',
				allowBlank : false,
				anchor : formItemAnchor
			}, {
				xtype : 'textfield',
				name : 'char_encoding',
				fieldLabel : '字符编码',
				allowBlank : false,
				anchor : formItemAnchor
			}]
		}, {
			columnWidth : '0.95',
			layout : 'form',
			items : [{
				xtype : 'textarea',
				name : 'database_desc',
				fieldLabel : '数据库描述',
				anchor : '99%'
			}]
		}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addDatabaseWindow = new Ext.Window({
		layout : 'fit',
		title : '添加数据库',
		buttonAlign : 'center',
		modal : true,
		width : 620,
		height : 320,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addDatabaseWindow.destroy();
			}
		},
		buttons : [{
			text : '测试连接',
			handler : function() {
				var test_user_name = Ext.getCmp("test_user_name").getValue();
				var test_user_password = Ext.getCmp("test_user_password").getValue();
				if(test_user_name == '' || test_user_password == '') {
					Ext.Msg.alert("提示信息", "请输入测试的用户名和密码!");
					return;
				}
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						url : pathUrl + '/metadata/database/testConnect',
						waitMsg : '正在处理,请稍后......',
						success : function(form, action){
							Ext.MessageBox.alert("提示信息", "连接成功!");
							refreshNode();
						},
						failure : function(form, action) {
							Ext.MessageBox.alert("提示信息", action.result.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '保存',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						url : pathUrl + '/metadata/database/add',
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addDatabaseWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addDatabaseWindow.destroy();
			}
		}]
	});
	
	addDatabaseWindow.show();
}

/**
 * 添加数据库用户
 * @param {} parentNode 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function addUser(parentNode, md_cate_cd) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/user/add',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
				xtype : 'textfield',
				name : 'user_name',
				fieldLabel : '用户名',
				allowBlank : false,
				anchor : '91%'
			},{
				xtype : 'textfield',
				name : 'user_password',
				fieldLabel : '用户密码',
				inputType : 'password',
				allowBlank : false,
				anchor : '91%'
			},{
				xtype : 'textarea',
				name : 'user_desc',
				fieldLabel : '用户描述',
				anchor : '91%'
			}, {
				xtype : 'numberfield',
				name : 'display_order',
				fieldLabel : '显示顺序',
				allowBlank : true,
				anchor : '91%'
			}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addUserWindow = new Ext.Window({
		layout : 'fit',
		title : '添加用户',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 260,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addUserWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addUserWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示信息", action.result.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addUserWindow.destroy();
			}
		}]
	});
	
	addUserWindow.show();
}

/**
 * 添加主题
 * @param {} parentNode 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function addTheme(parentNode, md_cate_cd) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		border : true,
		url : pathUrl + '/metadata/theme/add',
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
				xtype : 'textfield',
				name : 'theme_name',
				fieldLabel : '主题名称',
				allowBlank : false,
				anchor : '91%'
			},{
				xtype : 'textarea',
				name : 'theme_desc',
				fieldLabel : '主题描述',
				anchor : '91%'
			}, {
				xtype : 'numberfield',
				name : 'display_order',
				fieldLabel : '显示顺序',
				allowBlank : true,
				anchor : '91%'
			}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addThemeWindow = new Ext.Window({
		layout : 'fit',
		title : '添加主题',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 260,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addThemeWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addThemeWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addThemeWindow.destroy();
			}
		}]
	});
	
	addThemeWindow.show();
}

/**
 * 添加数据库表
 * @param {} parentNode 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function addTable(parentNode, md_cate_cd) {
	var store = new Ext.data.JsonStore({
		url : pathUrl + '/metadata/table/loadByDatabase',
		root : 'results',
		totalProperty : 'totalCount',
		listeners : {
			beforeload : function() {
				var searchKey = Ext.getCmp("searchKey").getValue();
				store.baseParams = {
					parent_metadata_id : parentNode.id,
					searchKey : searchKey
				}
			}
		},
		fields : ['table_name','table_desc','table_data_source']
	});
	
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
		header : '表名',
		dataIndex : 'table_name'
	}, {
		header : '表描述',
		dataIndex : 'table_desc'
	}, {
		header : '数据表源库',
		dataIndex : 'table_data_source'
	}]);
	var menu = [{
		text : '关键字：'
	}, {
		xtype : 'textfield',
		name : 'searchKey',
		id : 'searchKey',
		emptyText : '输入表名搜索',
		listeners : {
			specialkey : function(field, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					store.load();				
				}
			}
		}
	}, {
		xtype : 'button',
		iconCls : 'search',
		text : '搜索',
		handler : function() {
			store.load();
		}
	}];
	
	var gridPanel = new Ext.grid.GridPanel({
		region : 'center',
		autoScroll : true,
		loadMask : true,
		split : false,
		border : false,
		tbar : menu,
		ds : store,
		viewConfig : {forceFit : true},
		cm : cm
	});
	gridPanel.getSelectionModel().on("rowselect",function(sm, number, record){
		Ext.getCmp("add_table_name").setValue(record.get("table_name"));
		Ext.getCmp("add_table_desc").setValue(record.get("table_desc"));
		Ext.getCmp("add_table_data_source").setValue(record.get("table_data_source"));
	});
	
	var formPanel = new Ext.form.FormPanel({
		region : 'east',
		width : 280,
		layout : 'form',
		url : pathUrl + '/metadata/table/add',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'top',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
				xtype : 'textfield',
				name : 'table_name',
				id : 'add_table_name',
				fieldLabel : '数据表名称',
				allowBlank : false,
				anchor : '91%'
			},{
				xtype : 'textarea',
				name : 'table_desc',
				id : 'add_table_desc',
				fieldLabel : '数据表描述',
				anchor : '91%'
			}, {
				xtype : 'textfield',
				name : 'table_data_source',
				id : 'add_table_data_source',
				fieldLabel : '数据表源库',
				anchor : '91%'
			}, {
				xtype : 'numberfield',
				name : 'display_order',
				fieldLabel : '显示顺序',
				allowBlank : true,
				anchor : '91%'
			}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addTableWindow = new Ext.Window({
		layout : 'border',
		title : '添加数据表',
		buttonAlign : 'center',
		modal : true,
		width : 680,
		height : 300,
		items : [gridPanel, formPanel],
		listeners : {
			'close' : function(){
				addTableWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							refreshNode();
						},failure: function(form, action) {
							Ext.Msg.alert("提示信息", "保存失败!");
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		},{
			text : '保存并关闭',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addTableWindow.destroy();
							refreshNode();
						},failure: function(form, action) {
							Ext.Msg.alert("提示信息", "保存失败!");
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addTableWindow.destroy();
			}
		}]
	});
	addTableWindow.show();
	store.load();
}

/**
 * 添加列
 * @param {} parentNode 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function addColumn(parentNode, md_cate_cd) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/column/add',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
				xtype : 'textfield',
				name : 'column_name',
				fieldLabel : '字段名称',
				allowBlank : false,
				anchor : '91%'
			}, {
				xtype : 'textfield',
				name : 'column_desc',
				fieldLabel : '字段描述',
				allowBlank : false,
				anchor : '91%'
			},new ArrayCombo({
				id : 'dataTypeSelector',
				hiddenName : 'data_type_cd',
				data : [['01','文本'],['02','数值'],['03','日期']],
				fieldLabel : '数据类型',
				defaultValue : '01',
				anchor : '91%'
			}),{
				xtype : 'numberfield',
				name : 'data_length',
				fieldLabel : '数据长度',
				anchor : '91%'
			},{
				xtype : 'numberfield',
				name : 'data_scale',
				fieldLabel : '数据刻度',
				anchor : '91%'
			},new ArrayCombo({
				id : 'isPKSelector',
				hiddenName : 'is_pk',
				data : [['N','否'],['Y','是']],
				fieldLabel : '是否主键',
				defaultValue : 'N',
				anchor : '91%'
			}),new ArrayCombo({
				id : 'nullableSelector',
				hiddenName : 'is_nullable',
				data : [['Y','是'],['N','否']],
				fieldLabel : '是否可空',
				defaultValue : 'Y',
				anchor : '91%'
			}), {
				xtype : 'numberfield',
				name : 'display_order',
				fieldLabel : '显示顺序',
				allowBlank : true,
				anchor : '91%'
			}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addColumnWindow = new Ext.Window({
		layout : 'fit',
		title : '添加列字段',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 340,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addColumnWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addColumnWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addColumnWindow.destroy();
			}
		}]
	});
	
	addColumnWindow.show();
}

/**
 * 添加Excel模板
 * @param {} parentNode 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function addExcel(parentNode, md_cate_cd) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/excel/add',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
			xtype : 'textfield',
			id : 'excel_tmpl_name',
			name : 'excel_tmpl_name',
			fieldLabel : '模板名',
			allowBlank : false,
			anchor : '91%'
		}, {
				xtype : 'numberfield',
				name : 'display_order',
				fieldLabel : '显示顺序',
				allowBlank : true,
				anchor : '91%'
			}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addExcelWindow = new Ext.Window({
		layout : 'fit',
		title : '添加Excel模板',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 150,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addExcelWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addExcelWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addExcelWindow.destroy();
			}
		}]
	});
	
	addExcelWindow.show();
}

/**
 * 添加Excel 列
 * @param {} parentNode 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function addSheet(parentNode, md_cate_cd) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/sheet/add',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
			xtype : 'textfield',
			name : 'sheet_name',
			id : 'add_sheet_name',
			fieldLabel : '工作簿名称',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'start_row',
			fieldLabel : '起始行',
			allowBlank : false,
			anchor : '91%'
		}, new TreeCombo({
			hiddenName : 'table.table_id',
			fieldLabel : '关联表',
			rootId : '10',
			rootName : '数据字典',
			listWidth : 260,
			listHeight : 320,
			allowBlank : true,
			otherAttributes : {md_cate_cd : 'CAT'},
			getTextFn : getTextFn,
			expandFn : expandDictionNode,
			filteClickFn : filteClickFn
		}), {
			xtype : 'numberfield',
			name : 'display_order',
			fieldLabel : '显示顺序',
			allowBlank : false,
			anchor : '91%'
		}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addSheetWindow = new Ext.Window({
		layout : 'fit',
		title : '添加Sheet页',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 200,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addSheetWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addSheetWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addSheetWindow.destroy();
			}
		}]
	});
	
	addSheetWindow.show();
}

/**
 * 添加Excel column列
 * @param {} parentNode 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function addSheetColumn(parentNode, md_cate_cd) {

	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/xlscolumn/add',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
			xtype : 'textfield',
			name : 'xls_col_name',
			fieldLabel : 'Excel列名称',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'xls_col_label',
			fieldLabel : 'Excel列标签',
			anchor : '91%'
		}, new ArrayCombo({
			id : 'isPKSelector',
			hiddenName : 'is_pk',
			data : [['N', '否'], ['Y', '是']],
			fieldLabel : '是否主键',
			defaultValue : 'N',
			anchor : '91%'
		}), {
			xtype : 'textfield',
			name : 'tab_col_id',
			fieldLabel : '表列ID',
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'tab_col_name',
			fieldLabel : '表列名称',
			anchor : '91%'
		}, new ArrayCombo({
			id : 'dataTypeSelector',
			hiddenName : 'data_type_cd',
			data : [['01', '文本'], ['02', '数值'], ['03', '日期']],
			fieldLabel : '数据类型',
			defaultValue : '01',
			anchor : '91%'
		}), {
			xtype : 'textfield',
			name : 'dim_cd',
			fieldLabel : '维度代码',
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'formula_expr',
			fieldLabel : '公式表达式',
			anchor : '91%'
		}, {
			xtype : 'numberfield',
			name : 'display_order',
			fieldLabel : '显示顺序',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'default_value',
			fieldLabel : '默认值',
			anchor : '91%'
		}, dimCombo = new UrlRemoteCombo({
			url : pathUrl + '/dimLinkAjax/listDimLinks',
			fieldLabel : '维度',
			allowBlank : true,
			displayField : 'dim_name',
			valueField : 'dim_value',
			selectFirst : false,
			id : 'dimSelector',
			anchor : '91%'
		})]
	});
	
	// 添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addColumnWindow = new Ext.Window({
		layout : 'fit',
		title : '添加excel列',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 360,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addColumnWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addColumnWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addColumnWindow.destroy();
			}
		}]
	});
	
	addColumnWindow.show();
}

/**
 * 展开数据字典节点
 * @param {} parentNode
 * @return {}
 */
function expandDictionNode(parentNode) {
	if(parentNode.firstChild == null) {
		return null;
	}
	
	if (parentNode.firstChild.text == 'loading') {
		Ext.Ajax.request({
			url : pathUrl + '/metadata/listSubMetadata',
			waitMsg : '正在处理，请稍候......',
			method : 'POST',
			params : {
				prt_metadata_id : parentNode.id
			},
			success : function(response, options) {
				var json = Ext.util.JSON.decode(response.responseText);
				var tl = json.results;
				if (tl) {
					for (var i = 0; i < tl.length; i++) {
						var leaf = isLeaf(tl[i].md_cate_cd);
						var cnode = new Ext.tree.AsyncTreeNode({
							id : tl[i].metadata_id,
							text : '[' + tl[i].category.md_cate_name + ']' + tl[i].metadata_desc,
							leaf : leaf,
							icon : pathUrl + '/static/meta/img/' + tl[i].md_cate_cd + '.png',
							md_cate_cd : tl[i].md_cate_cd,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});

						if (!leaf) {
							cnode.on('expand', expandDictionNode);
						}

						parentNode.appendChild(cnode);
					}
				} else {
					Ext.MessageBox.alert('提示信息', json.info);
				}

				if (parentNode.firstChild) {
					parentNode.firstChild.remove();
				}
			},
			failure : function(response, options) {
				Ext.MessageBox.alert('提示信息', response.responseText);
			}
		});
	}
}

/**
 * 过滤选中节点方法,根据md_cate_cd过滤只能选中表节点
 */
function filteClickFn(node) {
	return node.attributes.md_cate_cd == category_type_table;
}

/**
 * 获取表的文本
 * @param {} node
 */
function getTextFn(node) {
	var text = node.text;
	if(text.indexOf("]") > 0) {
		return text.split("]")[1];
	}
	return text;
}