/**
 * 保存表
 * @param {} infoPanel
 */
function saveTable(infoPanel) {

	if(infoPanel.form.isValid()) {
		infoPanel.form.submit({
			waitMsg : '正在处理,请稍后......',
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "保存成功!");
				window.parent.updateTreeNodeName(metadata_id,Ext.getCmp("table_name").getValue() + '(' + Ext.getCmp("table_desc").getValue() + ")");
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
 * 删除表
 * 
 */
function deleteTable() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/table/delete',
		params : {
			table_id : metadata_id
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
 * 添加列
 * @param {} parentNode 父节点对象
 * @param {} md_cate_cd 添加的元数据类型
 */
function addColumn(table_id, md_cate_cd, store) {
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
			xtype : 'hidden',
			name : 'prt_metadata_id',
			value : table_id
		}, {
			xtype : 'hidden',
			name : 'md_cate_cd',
			value : md_cate_cd
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
			name : 'column_name',
			fieldLabel : '字段名称',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'column_desc',
			fieldLabel : '字段描述',
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
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'numberfield',
			name : 'data_scale',
			fieldLabel : '数据刻度',
			anchor : '91%'
		}, new ArrayCombo({
			id : 'isPKSelector',
			hiddenName : 'is_pk',
			data : [['N', '否'], ['Y', '是']],
			fieldLabel : '是否主键',
			defaultValue : 'N',
			anchor : '91%'
		}), new ArrayCombo({
			id : 'nullableSelector',
			hiddenName : 'is_nullable',
			data : [['Y', '是'], ['N', '否']],
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
							store.reload();
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
				addColumnWindow.destroy();
			}
		}]
	});
	
	addColumnWindow.show();
}

/**
 * 编辑列
 * @param {} column_id 列id
 */
function editColumn(r, store) {
	
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + "/metadata/column/save",
		method : 'POST',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'column_id'},
			{name : 'column_name'}, 
			{name : 'column_desc'},
			{name : 'data_type_cd'},
			{name : 'data_length'},
			{name : 'data_scale'},
			{name : 'is_pk'},
			{name : 'is_nullable'},
			{name : 'display_order'}
		]),
		items : [{
			xtype : 'hidden',
			name : 'column_id',
			fieldLabel : '列ID',
			allowBlank : false,
			value:r.get('column_id'),
			anchor : '91%'
		}, {
			xtype : 'textfield',
			id : 'column_name',
			name : 'column_name',
			fieldLabel : '列名',
			allowBlank : false,
			value:r.get('column_name'),
			anchor : '91%'
		}, {
			xtype : 'textfield',
			name : 'column_desc',
			fieldLabel : '列描述',
			value:r.get('column_desc'),
			anchor : '91%'
		}, new ArrayCombo({
			id : 'dataTypeSelector',
			hiddenName : 'data_type_cd',
			data : [['01', '文本'], ['02', '数值'], ['03', '日期']],
			fieldLabel : '数据类型',
			defaultValue:r.get('data_type_cd'),
			anchor : '91%'
		}), {
			xtype : 'numberfield',
			name : 'data_length',
			fieldLabel : '数据长度',
			allowBlank : false,
			value:r.get('data_length'),
			anchor : '91%'
		}, {
			xtype : 'numberfield',
			name : 'data_scale',
			fieldLabel : '数据刻度',
			value:r.get('data_scale'),
			anchor : '91%'
		}, new ArrayCombo({
			id : 'isPKSelector',
			hiddenName : 'is_pk',
			data : [['N', '否'], ['Y', '是']],
			value : '0',
			fieldLabel : '是否主键',
			defaultValue:r.get('is_pk'),
			anchor : '91%'
		}), new ArrayCombo({
			id : 'nullableSelector',
			hiddenName : 'is_nullable',
			data : [['Y', '是'], ['N', '否']],
			fieldLabel : '是否可空',
			defaultValue:r.get('is_nullable'),
			anchor : '91%'
		}), {
			xtype : 'numberfield',
			name : 'display_order',
			fieldLabel : '顺序',
			value:r.get('display_order'),
			anchor : '91%'
		}]
	});
	
	var editColumnWindow = new Ext.Window({
		layout : 'fit',
		title : '编辑列字段',
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 340,
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
							Ext.Msg.alert("提示信息", "操作成功!");
							store.reload();
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
				editColumnWindow.destroy();
			}
		}]
	});
	
	/*formPanel.form.load({
		url : pathUrl + '/metadata/column/load',
		params : {
			column_id : column_id
		}
	});*/
	
	editColumnWindow.show();
	
	
}

/**
 * 删除列字段
 * 
 */
function deleteColumn(column_id, store) {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/column/delete',
		params : {
			column_id : column_id
		},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				store.reload();
				window.parent.refreshNode();
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	})
}

/**
 * 同步表字段
 * @param {} table_id
 * @param {} store
 */
function syncTableField(table_id,store) {
	
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + "/metadata/table/syncTableColumn",
		method : 'POST',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		items : [{
			xtype : 'hidden',
			name : 'table_id',
			value : table_id
		}, {
			xtype : 'hidden',
			name : 'overwrite',
			id : 'overwrite',
			value : '0'
		}, {
			xtype : 'fieldset',
			layout : 'form',
			bodyStyle : 'padding: 5px 5px 5px 25px',
			anchor : '100%',
			title : '同步方式',
			items : [{
				xtype : 'checkbox',
				boxLabel : '追加新字段',
				hideLabel : true,
				id : 'overwriteCheck',
				checked : true,
				listeners : {
					'check' : function(ckbox, checked) {
						if (checked) {
							Ext.getCmp("appendCheck").setValue(false);
							Ext.getCmp("overwrite").setValue("0");
							var info = "不会当前已有字段,仅添加与当前列字段中不同列名的记录";
							document.getElementById("info").innerHTML = info;
						}
					}
				}
			}, {
				xtype : 'checkbox',
				boxLabel : '覆盖当前字段',
				hideLabel : true,
				id : 'appendCheck',
				listeners : {
					'check' : function(ckbox, checked) {
						if (checked) {
							Ext.getCmp("overwriteCheck").setValue(false);
							Ext.getCmp("overwrite").setValue("1");
							var info = "删除所有现有字段,然后将表中所有字段自动添加到该表的下级字段中";
							document.getElementById("info").innerHTML = info;
						}
					}
				}
			}]
		}, {
			xtype : 'panel',
			id : 'infoPanel',
			html : '<hr/><div id="info" style="color:red;font-size:12px;">不会当前已有字段,仅添加与当前列字段中不同列名的记录</div>'
		}]
	});
	
	var win = new Ext.Window({
		layout : 'fit',
		title : '同步表',
		buttonAlign : 'center',
		modal : true,
		width : 340,
		height : 260,
		items : [formPanel],
		listeners : {
			'close' : function(){
				win.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							win.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							store.reload();
							window.parent.refreshNode();
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
				win.destroy();
			}
		}]
	});
	
	win.show();
}