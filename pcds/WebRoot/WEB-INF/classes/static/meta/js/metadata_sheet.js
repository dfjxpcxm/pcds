/**
 * 保存Excel模板
 * @param {} infoPanel
 */
function saveSheet(infoPanel) {
	if(infoPanel.form.isValid()) {
		infoPanel.form.submit({
			waitMsg : '正在处理,请稍后......',
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "保存成功!");
				window.parent.updateTreeNodeName(metadata_id,Ext.getCmp("sheet_name").getValue());
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
function deleteSheet() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/sheet/delete',
		params : {
			sheet_id : metadata_id
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
 * 添加sheet页的列
 * @param {} sheet_id
 * @param {} store
 */
var addColumnWindow ;
var addFormPanel ;
function addSheetColumn(sheet_id, store) {
	addFormPanel = new Ext.form.FormPanel({
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
			url : pathUrl + '/metadata/column/listColumnByRela?metadata_id='+metadata_id,
			fieldLabel : '关联表字段',
			allowBlank : true,
			displayField : 'column_desc',
			valueField : 'column_id',
			hiddenName : 'tableColumn.column_id',
			id : 'addRelaColumnSelector',
			anchor : '91%'
		}), new ArrayCombo({
			id : 'isMustImput',
			hiddenName : 'is_must_input',
			data : [['Y','是'],['N','否']],
			fieldLabel : '是否必输',
			defaultValue : 'N',
			anchor : formItemAnchor
		}),{
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
			id : 'addDimSelector',
			anchor : '91%'
		})]
	});
	
	addColumnWindow = new Ext.Window({
		layout : 'form',
		title : '添加excel列',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 300,
		items : [addFormPanel],
		listeners : {
			'close' : function(){
				addColumnWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (addFormPanel.form.isValid()) {
					addFormPanel.form.submit({
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
var editColumnWindow;
var editFormPanel;
function editSheetColumn(column_id, store) {

	editFormPanel = new Ext.form.FormPanel({
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
		    {name : 'is_must_input'},
		    {name : 'display_order'},
		    {name : 'default_value'},
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
		    }, new ArrayCombo({
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
		    }, relaColumn = new UrlRemoteCombo({
		    	url : pathUrl + '/metadata/column/listColumnByRela?metadata_id='+metadata_id,
		    	fieldLabel : '关联表字段',
		    	allowBlank : true,
		    	displayField : 'column_desc',
		    	valueField : 'column_id',
		    	hiddenName : 'tableColumn.column_id',
		    	id : 'relaColumnSelector',
		    	anchor : '95%'
		    }) ,dimCombo = new UrlRemoteCombo({
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
	
	editColumnWindow = new Ext.Window({
		layout : 'fit',
		title : '编辑excel列',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 310,
		items : [editFormPanel],
		listeners : {
			'close' : function(){
				editColumnWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (editFormPanel.form.isValid()) {
					editFormPanel.form.submit({
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
	
	//初始化编辑表单
	editFormPanel.form.load({
		url : pathUrl + '/metadata/xlscolumn/load',
		params : {
			column_id : column_id
		}
	});
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