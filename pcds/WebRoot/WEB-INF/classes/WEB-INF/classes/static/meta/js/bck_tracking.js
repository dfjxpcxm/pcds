/**
 * 添加数据源维度窗口
 * @class AddWindow
 * @extends Ext.Window
 */
//Ext.form.Field.prototype.msgTarget = 'under';
var dim_store = new Ext.data.JsonStore({
	url : pathUrl + '/dimLinkAjax/listDimLinks',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['dim_code', 'dim_name']
});
//控件类型
var component_array = [
    ['textfield','文本框'],
    ['numberfield','数字框'],
    ['textarea','文本域'],
    ['combo','选择框'],
    ['button','按钮']
                       
];

AddWindow = Ext.extend(Ext.Window, {
	title : '添加维度',
	buttonAlign : 'center',
	id : 'addMdProWindow',
	width : 500,
	height : 500,
	autoScroll:true,
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'form',
			listeners : {
				close : function() {
					Ext.getCmp("addMdProWindow").destroy();
				}
			},
			bodyStyle : 'padding: 10px',
			items : [{
				xtype : 'form',
				id : 'addMdProForm',
				layout : 'form',
				labelWidth : 85,
				border : false,
				labelAlign : 'left',
				url : pathUrl + '/managerFnMdPro/addPro',
				items : [{
					xtype : 'textfield',
					name : 'metadata_id',
					id : 'metadata_id',
					fieldLabel : '元&nbsp;数&nbsp;据&nbsp;ID',
					allowBlank : false,
					anchor : '95%',
					regex : /^[0-9a-zA-Z_]+$/,
					regexText : '元数据ID不允许包含特殊字符和汉字，请检查！',
					listeners : {
//						blur : function(field){
//							if(field.getValue()!=null){
//								Ext.Ajax.request({
//									url : pathUrl + '/dimLinkAjax/checkLink',
//									params : {
//										metadata_id : Ext.getCmp('metadata_id').getValue()
//									},
//									callback : function (options,request,response){
//										var json = Ext.util.JSON.decode(response.responseText);
//										if(json.success){
//											Ext.getCmp('save_pro').setDisabled(false);
//										}else{
//											field.markInvalid(json.info);
//											Ext.getCmp('save_pro').setDisabled(true);
//										}
//									}
//								});
//							}
//						}
					}
				},{
					xtype : 'textfield',
					name : 'metadata_name',
					id : 'metadata_name',
					fieldLabel : '元数据名称',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'begin_cell',
					id : 'begin_cell',
					fieldLabel : '开始单元格',
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'end_cell',
					id : 'end_cell',
					fieldLabel : '结束单元格',
					anchor : '95%'
				},{
					xtype : 'combo',
					fieldLabel : '状态代码',
					displayField : 'text',
					valueField : 'id',
					editable : false,
					hiddenName : 'statue_code',
					mode : 'local',
					allowBlank : false,
					value : '02',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						fields : ['id', 'text'],
						data : [['01', '禁用'], ['02', '正常'], ['03', '删除']]
					}),
					anchor : '95%'
				}, {
					xtype : 'combo',
					fieldLabel : '维度代码',
					displayField : 'dim_name',
					valueField : 'dim_code',
					editable : false,
					hiddenName : 'dim_code',
					mode : 'local',
					allowBlank : false,
					triggerAction : 'all',
					store:dim_store,
					anchor : '95%'
				}, {
					xtype : 'combo',
					store : new Ext.data.SimpleStore({
						fields : ['id', 'text'],
						data : component_array
					}),
					displayField : 'text',
					valueField : 'id',
					fieldLabel : '控件类型',
					editable : false,
					hiddenName : 'component_type',
					mode : 'local',
					allowBlank : false,
					triggerAction : 'all',
					value:'textfield',
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'component_id',
					id : 'component_id',
					fieldLabel : '控件ID',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'component_tab',
					id : 'component_tab',
					fieldLabel : '控件标签',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'component_name',
					id : 'component_name',
					fieldLabel : '控件NAME',
					allowBlank : false,
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'component_default',
					id : 'component_default',
					fieldLabel : '控件默认值',
					anchor : '95%'
				}, {
					xtype : 'numberfield',
					name : 'component_length',
					id : 'component_length',
					fieldLabel : '控件长度',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'combo',
					fieldLabel : '控件是否隐藏',
					displayField : 'text',
					valueField : 'id',
					editable : false,
					hiddenName : 'component_hide',
					mode : 'local',
					allowBlank : false,
					value : 'false',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						fields : ['id', 'text'],
						data : [['true', '是'], ['false', '否']]
					}),
					anchor : '95%'
				},{
					xtype : 'numberfield',
					name : 'display_order',
					id : 'display_order',
					fieldLabel : '显示顺序',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'numberfield',
					name : 'com_condition',
					id : 'com_condition',
					fieldLabel : '控件条件',
					anchor : '95%'
				},{
					xtype : 'textarea',
					name : 'dml_sql',
					id : 'dml_sql',
					fieldLabel : 'DML语言',
					allowBlank : false,
					autoScroll : true,
					height : 80,
					anchor : '95%'
				}]
			}],
			buttons : [{
				text : '保存',
				id : 'save_pro',
				handler : function() {
					var formPanel = Ext.getCmp("addMdProForm");
					if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
								if(action.result.success) {
									src_pro_ds.reload();
									Ext.getCmp('addMdProWindow').destroy();
								}
							},
							failure : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
							}
						})
					} else {
						Ext.Msg.alert("提示信息", "请输入完整信息");
					}
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp("addMdProWindow").destroy();
				}
			}]
		});
		AddWindow.superclass.initComponent.call(this);
	}
});

/**
 * 修改数据源维度窗口
 * @class EditWindow
 * @extends Ext.Window
 */
EditWindow = Ext.extend(Ext.Window, {
	title : '编辑维度',
	buttonAlign : 'center',
	id : 'editDmProWindow',
	width : 500,
	height : 500,
	autoScroll:true,
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'form',
			listeners : {
				close : function() {
					Ext.getCmp("editDmProWindow").destroy();
				}
			},
			bodyStyle : 'padding: 10px',
			items : [{
				xtype : 'form',
				id : 'editMdProForm',
				layout : 'form',
				labelWidth : 85,
				border : false,
				labelAlign : 'left',
				url : pathUrl + '/managerFnMdPro/editPro',
				items : [{
					xtype : 'textfield',
					name : 'metadata_id',
					id : 'metadata_id',
					fieldLabel : '元&nbsp;数&nbsp;据&nbsp;ID',
					allowBlank : false,
					readOnly : true,
					style : 'background:#F0F0F0;color : #A0A0A0',
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'metadata_name',
					id : 'metadata_name',
					fieldLabel : '元数据名称',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'begin_cell',
					id : 'begin_cell',
					fieldLabel : '开始单元格',
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'end_cell',
					id : 'end_cell',
					fieldLabel : '结束单元格',
					anchor : '95%'
				},{
					xtype : 'combo',
					fieldLabel : '状态代码',
					displayField : 'text',
					valueField : 'id',
					editable : false,
					hiddenName : 'statue_code',
					mode : 'local',
					allowBlank : false,
					value : '02',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						fields : ['id', 'text'],
						data : [['01', '禁用'], ['02', '正常'], ['03', '删除']]
					}),
					anchor : '95%'
				}, {
					xtype : 'combo',
					fieldLabel : '维度代码',
					displayField : 'dim_name',
					valueField : 'dim_code',
					editable : false,
					hiddenName : 'dim_code',
					mode : 'local',
					allowBlank : false,
					triggerAction : 'all',
					store:dim_store,
					anchor : '95%'
				}, {
					xtype : 'combo',
					store : new Ext.data.SimpleStore({
						fields : ['id', 'text'],
						data : component_array
					}),
					displayField : 'text',
					valueField : 'id',
					fieldLabel : '控件类型',
					editable : false,
					hiddenName : 'component_type',
					mode : 'local',
					allowBlank : false,
					triggerAction : 'all',
					value:'textfield',
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'component_id',
					id : 'component_id',
					fieldLabel : '控件ID',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'component_tab',
					id : 'component_tab',
					fieldLabel : '控件标签',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'component_name',
					id : 'component_name',
					fieldLabel : '控件NAME',
					allowBlank : false,
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'component_default',
					id : 'component_default',
					fieldLabel : '控件默认值',
					anchor : '95%'
				}, {
					xtype : 'numberfield',
					name : 'component_length',
					id : 'component_length',
					fieldLabel : '控件长度',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'combo',
					fieldLabel : '控件是否隐藏',
					displayField : 'text',
					valueField : 'id',
					editable : false,
					hiddenName : 'component_hide',
					mode : 'local',
					allowBlank : false,
					value : 'false',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						fields : ['id', 'text'],
						data : [['true', '是'], ['false', '否']]
					}),
					anchor : '95%'
				},{
					xtype : 'numberfield',
					name : 'display_order',
					id : 'display_order',
					fieldLabel : '显示顺序',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'numberfield',
					name : 'com_condition',
					id : 'com_condition',
					fieldLabel : '控件条件',
					anchor : '95%'
				},{
					xtype : 'textarea',
					name : 'dml_sql',
					id : 'dml_sql',
					fieldLabel : 'DML语言',
					allowBlank : false,
					autoScroll : true,
					height : 80,
					anchor : '95%'
				}]
			}],
			buttons : [{
				text : '保存',
				handler : function() {
					var formPanel = Ext.getCmp("editMdProForm");
					if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
//								Ext.Msg.alert("消息", action.result.info);
								if (action.result.success) {
									src_pro_ds.reload();
									Ext.getCmp('editDmProWindow').destroy();
								}
							},
							failure : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
							}
						})
					} else {
						Ext.Msg.alert("提示信息", "请输入完整信息");
					}
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp("editDmProWindow").destroy();
				}
			}]
		});
		AddWindow.superclass.initComponent.call(this);
	}
});

/**
 * 删除维度记录
 * @param {} 
 */
function deleteMdPro(metadata_id) {
	Ext.Ajax.request({
		method : 'POST',
		url : pathUrl + '/managerFnMdPro/deletePro',
		params : {
			metadata_id : metadata_id
		},
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (success) {
				Ext.MessageBox.alert("提示信息", json.info);
				src_pro_ds.reload();
			} else {
				Ext.MessageBox.alert("提示信息", json.info);
			}
		}
	});
};
