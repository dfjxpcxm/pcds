/**
 * 添加列
 */
function addColumn(database_id, owner_id, table_id, table_name, store) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/meta/alterTable/add',
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
			name : 'database_id',
			allowBlank : false,
			value : database_id,
			anchor : '91%'
		}, {
			xtype : 'hidden',
			name : 'owner_id',
			allowBlank : false,
			value : owner_id,
			anchor : '91%'
		}, {
			xtype : 'hidden',
			name : 'table_id',
			allowBlank : false,
			value : table_id,
			anchor : '91%'
		}, {
			xtype : 'hidden',
			name : 'table_name',
			allowBlank : false,
			value : table_name,
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'column_name',
			fieldLabel : '列名',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'column_desc',
			fieldLabel : '列描述',
			allowBlank : false,
			anchor : '91%'
		}, new ArrayCombo({
			id : 'dataTypeSelector',
			hiddenName : 'data_type_cd',
			data : [['01', '文本'], ['02', '数值'], ['03', '日期']],
			fieldLabel : '数据类型',
			defaultValue : '01',
			anchor : '91%'
		}), {
			xtype : 'numberfield',
			name : 'data_length',
			fieldLabel : '数据长度',
			anchor : '91%'
		}, {
			xtype : 'numberfield',
			name : 'data_scale',
			fieldLabel : '数据刻度',
			anchor : '91%'
		}, {
			xtype : 'hidden',
			name : 'is_pk',
			allowBlank : false,
			value : 'N',
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'is_pk_display',
			readOnly : true,
			fieldLabel : '是否主键',
			value : '否',
			anchor : '91%'
		}, new ArrayCombo({
			id : 'nullableSelector',
			hiddenName : 'is_nullable',
			data : [['Y', '可以'], ['N', '不可以']],
			fieldLabel : '是否可空',
			defaultValue : 'Y',
			anchor : '91%'
		})]
	});

	var addColumnWindow = new Ext.Window({
		layout : 'fit',
		title : '添加列',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 330,
		items : [formPanel],
		listeners : {
			'close' : function() {
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
							store.reload();
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
				addColumnWindow.destroy();
			}
		}]
	});

	addColumnWindow.show();
}

/**
 * 编辑列
 */
function editColumn(record, store) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/meta/alterTable/edit',
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
			{name : 'database_id'}, 
			{name : 'owner_id'}, 
			{name : 'table_id'},
			{name : 'column_name'},
			{name : 'column_desc'}
		]),
		items : [{
			xtype : 'hidden',
			name : 'database_id',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'hidden',
			name : 'owner_id',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'hidden',
			name : 'table_id',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'hidden',
			name : 'table_name',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'column_name',
			fieldLabel : '列名',
			readOnly : true,
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'column_desc',
			fieldLabel : '列描述',
			allowBlank : false,
			anchor : '91%'
		}]
	});
	
	formPanel.getForm().loadRecord(record);
	formPanel.getForm().setValues({table_name:tableCombo.getRawValue()});
	
	var editColumnWindow = new Ext.Window({
		layout : 'fit',
		title : '编辑列',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 160,
		items : [formPanel],
		listeners : {
			'close' : function() {
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
							Ext.Msg.alert("提示信息", "操作成功!");
							store.reload();
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
				editColumnWindow.destroy();
			}
		}]
	});

	editColumnWindow.show();
	
	
}