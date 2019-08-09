/**
 * 保存Excel模板
 * @param {} infoPanel
 */
function saveExcel(infoPanel) {

	if(infoPanel.form.isValid()) {
		infoPanel.form.submit({
			waitMsg : '正在处理,请稍后......',
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "保存成功!");
				window.parent.updateTreeNodeName(metadata_id,Ext.getCmp("excel_tmpl_name").getValue());
			},
			failure : function(form, action) {
				Ext.MessageBox.alert("提示信息", action.result.info);
			}
		});
	} else {
		Ext.MessageBox.alert("提示信息", "请输入完整信息");
	}
	
}

/**
 * 删除Excel
 * 
 */
function deleteExcel() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/excel/delete',
		params : {
			excel_id : metadata_id
		},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				window.parent.deleteTreeNode(metadata_id);
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	})
}

/**
 * 添加Sheet页对象
 * @param {} tab
 */
function addSheet(tab) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/sheet/add',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		buttonAlign : 'center',
		items : [{
			xtype : 'hidden',
			name : 'prt_metadata_id',
			value : metadata_id
		}, {
			xtype : 'hidden',
			name : 'md_cate_cd',
			value : window.parent.category_type_sheet
		}, {
			xtype : 'hidden',
			name : 'status_cd',
			value : '02'
		}, {
			xtype : 'hidden',
			name : 'is_display',
			value : 'Y'
		}, {
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
		}, {
			xtype : 'numberfield',
			name : 'display_order',
			fieldLabel : '显示顺序',
			allowBlank : false,
			anchor : '91%'
		}, new TreeCombo({
			hiddenName : 'table.table_id',
			fieldLabel : '关联表',
			rootId : '10',
			rootName : '数据字典',
			listWidth : 280,
			listHeight : 320,
			allowBlank : true,
			otherAttributes : {
				md_cate_cd : 'CAT'
			},
			getTextFn : window.parent.getTextFn,
			expandFn : window.parent.expandDictionNode,
			filteClickFn : window.parent.filteClickFn
		})]
	});
	
	
	var addSheetWindow = new Ext.Window({
		layout : 'fit',
		title : '添加Sheet页',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 250,
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
							var sheet_id = action.result.info;
							var sheet_name = Ext.getCmp("add_sheet_name").getValue();
							tab.remove("addTab");
							tab.add(getSheetPanel(sheet_id, sheet_name));
							tab.setActiveTab("gridPanel_" + sheet_id);
							tab.add({
								id : 'addTab',
								title : '添加Sheet页(+)'
							});
							addSheetWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							window.parent.refreshNode();
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
 * 获取sheet页的panel
 * @param {} sheet_id
 * @param {} sheet_name
 */
function getSheetPanel(sheet_id, sheet_name) {
	var store = new Ext.data.JsonStore({
		url : pathUrl + '/metadata/xlscolumn/queryColumnBySheetId',
		root : 'results',
		totalProperty : 'totalCount',
		listeners : {
			beforeload : function() {
				store.baseParams = {
					sheet_id : sheet_id
				}
			}
		},
		fields : ['xls_col_id', 'xls_col_name', 'xls_col_label','is_must_input',
				'tableColumn.is_pk', 'tableColumn.column_id',
				'tableColumn.column_name', 'tableColumn.data_type_cd','dimSource.dim_name',
				'formula_expr', 'display_order', 'default_value']
	});
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
		header : 'Excel列ID',
		dataIndex : 'xls_col_id',
		hidden : true
	}, {
		header : 'Excel列名称',
		dataIndex : 'xls_col_name'
	}, {
		header : 'Excel列标签',
		dataIndex : 'xls_col_label'
	}, {
		header : '是否主键',
		width : 40,
		hidden:true,
		dataIndex : 'tableColumn.is_pk',
		renderer : function(val) {
			if (val == 'Y')
				return "是";
			else if (val == 'N')
				return "否";
			return val;
		}
	}, {
		header : '表列ID',
		dataIndex : 'tableColumn.column_id',
		hidden : true
	}, {
		header : '表列名称',
		width : 40,
		dataIndex : 'tableColumn.column_name'
	}, {
		header : '数据类型',
		width : 40,
		dataIndex : 'tableColumn.data_type_cd',
		renderer : function(val) {
			if (val == '01')
				return "文本";
			else if (val == '02')
				return "数值";
			else if (val == '03')
				return "日期";
			return val;
		}
	},{
		header : '是否必输',
		width : 40,
		dataIndex : 'is_must_input',
		renderer : function(val) {
			if (val == 'Y')
				return "是";
			else if (val == 'N')
				return "否";
			return val;
		}
	}, {
		header : '维度',
		width : 40,
		dataIndex : 'dimSource.dim_name'
	},
	{
		header : '公式表达式',
		width : 60,
		dataIndex : 'formula_expr'
	}, {
		header : '显示顺序',
		hidden:true,
		width : 35,
		dataIndex : 'display_order'
	}, {
		header : '默认值',
		width : 35,
		dataIndex : 'default_value'
	}]);
	
	var menu = [{
		text : '添加(a)',
		iconCls : 'add',
		handler : function() {
			addSheetColumn(sheet_id, store);
		}
	}, '-', {
		text : '编辑(e)',
		iconCls : 'edit',
		handler : function() {
			var record = Ext.getCmp("gridPanel_" + sheet_id).getSelectionModel().getSelections()[0];
			if(record == null) {
				Ext.MessageBox.alert("提示信息", "请选择一条记录!");
				return;
			}
			editSheetColumn(record.get('xls_col_id'), store);
		}
	}, '-', {
		text : '删除(d)',
		iconCls : 'delete',
		handler : function() {
			var record = Ext.getCmp("gridPanel_" + sheet_id).getSelectionModel().getSelections()[0];
			if(record == null) {
				Ext.MessageBox.alert("提示信息", "请选择一条要删除的记录!");
				return;
			}
			Ext.MessageBox.confirm("确认信息", "是否删除选中记录?", function(btn) {
				if (btn == 'yes')
					deleteSheetColumn(record.get('xls_col_id'), store);
			});
		}
	},'-',{
		text : '调整排序',
		iconCls : 'revoke',
		handler : function() {
			if(store.getCount()>0){
				showOrderWin(store);
			}
		}
	}];
	
	var gridPanel = new Ext.grid.GridPanel({
		id : 'gridPanel_' + sheet_id,
		title : sheet_name,
		autoScroll : true,
		loadMask : true,
		split : false,
		border : false,
		tbar : menu,
		ds : store,
		viewConfig : {forceFit : true},
		cm : cm
	});
	
	return gridPanel;
}

/**
 * 添加sheet页的列
 * @param {} sheet_id
 * @param {} store
 */
function addSheetColumn(sheet_id, store) {

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
			xtype : 'hidden',
			name : 'prt_metadata_id',
			value : sheet_id
		}, {
			xtype : 'hidden',
			name : 'md_cate_cd',
			value : window.parent.category_type_excelcol
		}, {
			xtype : 'hidden',
			name : 'status_cd',
			value : '02'
		}, {
			xtype : 'hidden',
			name : 'is_display',
			value : 'Y'
		}, {
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
		}, {
			xtype : 'textfield',
			name : 'formula_expr',
			fieldLabel : '公式表达式',
			anchor : '91%'
		}, new UrlRemoteCombo({
			url : pathUrl + '/metadata/column/listColumnByRela?metadata_id='+sheet_id,
			fieldLabel : '关联表字段',
			allowBlank : true,
			displayField : 'column_desc',
			valueField : 'column_id',
			hiddenName : 'tableColumn.column_id',
			id : 'relaColumnSelector',
			anchor : '91%'
		}),new ArrayCombo({
			id : 'isMustImput',
			hiddenName : 'is_must_input',
			data : [['Y','是'],['N','否']],
			fieldLabel : '是否必输',
			defaultValue : 'N',
			anchor : '95%'
		}), {
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
			url : pathUrl + '/dimLinkAjax/listDimLinks?insertBlankRow=true',
			fieldLabel : '维度',
			allowBlank : true,
			hiddenName : 'dimSource.dim_cd',
			displayField : 'dim_name',
			valueField : 'dim_cd',
			selectFirst : false,
			id : 'dimSelector',
			anchor : '91%'
		})]
	});
	
	
	var addColumnWindow = new Ext.Window({
		layout : 'fit',
		title : '添加excel列',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 310,
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
							store.reload();
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
				addColumnWindow.destroy();
			}
		}]
	});
	
	addColumnWindow.show();
}

/**
 * 编辑sheet页的列
 * @param {} column_id
 * @param {} store
 */
function editSheetColumn(column_id, store) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/xlscolumn/save',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'xls_col_id'}, 
			{name : 'xls_col_name'}, 
			{name : 'xls_col_label'},
			{name : 'formula_expr'},
			{name : 'display_order'},
			{name : 'default_value'},
			{name : 'is_must_input'},
			{name : 'tableColumn.column_id'},
			{name : 'dimSource.dim_cd'}
		]),
		items : [{
			xtype : 'hidden',
			name : 'xls_col_id',
			allowBlank : false,
			anchor : '95%'
		}, {
			xtype : 'textfield',
			name : 'xls_col_name',
			fieldLabel : 'Excel列名称',
			allowBlank : false,
			anchor : '95%'
		}, {
			xtype : 'textfield',
			name : 'xls_col_label',
			fieldLabel : 'Excel列标签',
			anchor : '95%'
		}, {
			xtype : 'textfield',
			name : 'formula_expr',
			fieldLabel : '公式表达式',
			anchor : '95%'
		}, new UrlRemoteCombo({
			url : pathUrl + '/metadata/column/listColumnByRela?metadata_id='+sheet_id,
			fieldLabel : '关联表字段',
			allowBlank : true,
			displayField : 'column_desc',
			valueField : 'column_id',
			hiddenName : 'tableColumn.column_id',
			id : 'relaColumnSelector',
			anchor : '95%'
		}),new ArrayCombo({
			id : 'isMustImput',
			hiddenName : 'is_must_input',
			data : [['Y','是'],['N','否']],
			fieldLabel : '是否必输',
			defaultValue : 'N',
			anchor : '95%'
		}), {
			xtype : 'numberfield',
			name : 'display_order',
			fieldLabel : '显示顺序',
			allowBlank : false,
			anchor : '95%'
		}, {
			xtype : 'textfield',
			name : 'default_value',
			fieldLabel : '默认值',
			anchor : '95%'
		}, dimCombo = new UrlRemoteCombo({
			url : pathUrl + '/dimLinkAjax/listDimLinks?insertBlankRow=true',
			fieldLabel : '维度',
			allowBlank : true,
			hiddenName : 'dimSource.dim_cd',
			displayField : 'dim_name',
			valueField : 'dim_cd',
			selectFirst : false,
			id : 'dimSelector',
			anchor : '95%'
		})]
	});
	
	
	formPanel.form.load({
		url : pathUrl + '/metadata/xlscolumn/load',
		params : {
			column_id : column_id
		}
	});
	var editColumnWindow = new Ext.Window({
		layout : 'fit',
		title : '添加excel列',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 310,
		items : [formPanel],
		listeners : {
			'close' : function(){
				editColumnWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							editColumnWindow.destroy();
							store.reload();
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
				editColumnWindow.destroy();
			}
		}]
	});
	
	editColumnWindow.show();
}

/**
 * 删除excel列
 * @param {} column_id
 * @param {} store
 */
function deleteSheetColumn(column_id, store) {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/xlscolumn/delete',
		params : {
			column_id : column_id
		},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				store.reload();
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	})
}