/**
 * 添加数据源维度窗口
 * @class AddWindow
 * @extends Ext.Window
 */
//Ext.form.Field.prototype.msgTarget = 'under';
AddWindow = Ext.extend(Ext.Window, {
	id : 'addFieldLinkWindow',
	buttonAlign : 'center',
	initComponent : function() {
		Ext.applyIf(this, {
			title : '添加维度',
			width : 430,
			height : 500,
			modal : true,
			layout : 'fit',
			frame:false,
			listeners : {
				close : function() {
					Ext.getCmp("addFieldLinkWindow").destroy();
				}
			},
			items : [{
				xtype : 'form',
				id : 'addFieldLinkForm',
				layout : 'form',
				autoScroll:true,
				labelWidth : 85,
				border : false,
				labelAlign : 'left',
				url : pathUrl + '/dimLinkAjax/add',
				items : [{
					xtype : 'textfield',
					name : 'dim_cd',
					id : 'dim_cd',
					fieldLabel : '维&nbsp;&nbsp;度&nbsp;ID',
					allowBlank : false,
					anchor : '95%',
					regex : /^[0-9a-zA-Z_]+$/,
					regexText : '维度ID不允许包含特殊字符和汉字，请检查！',
					listeners : {
						blur : function(field){
							if(field.getValue()!=null){
								Ext.Ajax.request({
									url : pathUrl + '/dimLinkAjax/checkLink',
									params : {
										dim_cd : Ext.getCmp('dim_cd').getValue()
									},
									callback : function (options,request,response){
										var json = Ext.util.JSON.decode(response.responseText);
										if(json.success){
											Ext.getCmp('save_link').setDisabled(false);
										}else{
											field.markInvalid(json.info);
											Ext.getCmp('save_link').setDisabled(true);
										}
									}
								});
							}
						}
					}
				},{
					xtype : 'textfield',
					name : 'dim_name',
					id : 'dim_name',
					fieldLabel : '维度名称',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'dim_desc',
					id : 'dim_desc',
					fieldLabel : '维度描述',
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'code_col_name',
					id : 'code_col_name',
					fieldLabel : 'ID字段名',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'label_col_name',
					id : 'label_col_name',
					fieldLabel : '标签字段',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'combo',
					store : new Ext.data.SimpleStore({
						fields : ['is_tree', 'is_tree_desc'],
						data : [['Y', '是'], ['N', '否']]
					}),
					displayField : 'is_tree_desc',
					valueField : 'is_tree',
					fieldLabel : '树形显示',
					editable : false,
					hiddenName : 'is_tree',
					mode : 'local',
					allowBlank : false,
					value : 'N',
					listeners : {
						select : function(combo, record, index) {
							judgeTree(record.get('is_tree'));
						}
					},
					triggerAction : 'all',
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'prt_col_name',
					id : 'prt_col_name',
					fieldLabel : '父ID值字段',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'root_value',
					id : 'root_value',
					fieldLabel : '根节点ID值',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'textarea',
					name : 'dim_sql_expr',
					id : 'dim_sql_expr',
					fieldLabel : 'SQL表达式',
					allowBlank : false,
					autoScroll : true,
					height : 50,
					emptyText : '请输入维度查询SQL',
					anchor : '95%'
				}, {
					xtype : 'textarea',
					name : 'cascade_sql_expr',
					id : 'cascade_sql_expr',
					fieldLabel : '级联关系SQL表达式',
					autoScroll : true,
					height : 50,
					anchor : '95%'
				},  {
					xtype : 'combo',
					store : new Ext.data.SimpleStore({
						fields : ['is_table_edit', 'is_table_edit_desc'],
						data : [['Y', '是'], ['N', '否']]
					}),
					displayField : 'is_table_edit_desc',
					valueField : 'is_table_edit',
					fieldLabel : '表编辑',
					editable : false,
					hiddenName : 'is_table_edit',
					mode : 'local',
					allowBlank : false,
					value : 'N',
					listeners : {
						select : function(combo, record, index) {
							dimTableConsole(record.get('is_table_edit'));
						}
					},
					triggerAction : 'all',
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'table_name',
					id : 'table_name',
					fieldLabel : '表名',
					allowBlank : false,
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'tabke_pk',
					id : 'tabke_pk',
					fieldLabel : '主键名',
					allowBlank : false,
					anchor : '95%'
				},  {
					xtype : 'textarea',
					name : 'table_cols',
					id : 'table_cols',
					fieldLabel : '表编辑字段',
					autoScroll : true,
					height : 40,
					anchor : '95%'
				}]
			}],
			buttons : [{
				text : '保存',
				id : 'save_link',
				handler : function() {
					var formPanel = Ext.getCmp("addFieldLinkForm");
					var tabke_pk= Ext.getCmp("tabke_pk").getValue();
					var dim_sql_expr= Ext.getCmp("dim_sql_expr").getValue();
					var table_cols=Ext.getCmp("table_cols").getValue();
					
					if(checkPkSelect(tabke_pk,dim_sql_expr)==false){
						Ext.Msg.alert("提示信息", "请在sql语句输入主键");
						return;
					}else if(checkEditColsPk(tabke_pk,table_cols)==false){
						Ext.Msg.alert("提示信息", "编辑列中必须输入主键");
						return;
					}else if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
								if (action.result.success) {
									src_dim_ds.reload();
									Ext.getCmp('addFieldLinkWindow').destroy();
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
					Ext.getCmp("addFieldLinkWindow").destroy();
				}
			}]
		});
		AddWindow.superclass.initComponent.call(this);

		Ext.getCmp('prt_col_name').setDisabled(true);
		Ext.getCmp('prt_col_name').setVisible(false);
		Ext.getCmp('root_value').setDisabled(true);
		Ext.getCmp('root_value').setVisible(false);
		dimTableConsole('N');
	}
});

/**
 * 修改数据源维度窗口
 * @class EditWindow
 * @extends Ext.Window
 */
EditWindow = Ext.extend(Ext.Window, {
	id : 'editDimLinkWindow',
	title : '编辑维度',
	buttonAlign : 'center',
	width : 430,
	height : 500,
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'fit',
			listeners : {
				close : function() {
					Ext.getCmp("editDimLinkWindow").destroy();
				}
			},
			items : [{
				xtype : 'form',
				id : 'editdimLinkForm',
				autoScroll:true,
				layout : 'form',
				labelWidth : 85,
				border : false,
				labelAlign : 'left',
				url : pathUrl + '/dimLinkAjax/edit',
				items : [{
					xtype : 'textfield',
					name : 'dim_cd',
					id : 'dim_cd',
					fieldLabel : '维&nbsp;&nbsp;度&nbsp;ID',
					allowBlank : false,
					readOnly : true,
					style : 'background:#F0F0F0;color : #A0A0A0',
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'dim_name',
					id : 'dim_name',
					fieldLabel : '维度名称',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'dim_desc',
					id : 'dim_desc',
					fieldLabel : '维度描述',
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'code_col_name',
					id : 'code_col_name',
					fieldLabel : 'ID字段名',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'label_col_name',
					id : 'label_col_name',
					fieldLabel : '标签字段',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'combo',
					store : new Ext.data.SimpleStore({
						fields : ['is_tree', 'is_tree_desc'],
						data : [['Y', '是'], ['N', '否']]
					}),
					displayField : 'is_tree_desc',
					valueField : 'is_tree',
					fieldLabel : '树形显示',
					editable : false,
					hiddenName : 'is_tree',
					mode : 'local',
					allowBlank : false,
					listeners : {
						select : function(combo, record, index) {
							judgeTree(record.get('is_tree'));
						}
					},
					triggerAction : 'all',
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'prt_col_name',
					id : 'prt_col_name',
					fieldLabel : '父ID值字段',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'root_value',
					id : 'root_value',
					fieldLabel : '根节点ID值',
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'textarea',
					name : 'dim_sql_expr',
					id : 'dim_sql_expr',
					fieldLabel : 'SQL表达式',
					allowBlank : false,
					autoScroll : true,
					height : 50,
					emptyText : '请输入维度查询SQL',
					anchor : '95%'
				}, {
					xtype : 'textarea',
					name : 'cascade_sql_expr',
					id : 'cascade_sql_expr',
					fieldLabel : '级联SQL表达式',
					autoScroll : true,
					height : 50,
					anchor : '95%'
				},  {
					xtype : 'combo',
					store : new Ext.data.SimpleStore({
						fields : ['is_table_edit', 'is_table_edit_desc'],
						data : [['Y', '是'], ['N', '否']]
					}),
					displayField : 'is_table_edit_desc',
					valueField : 'is_table_edit',
					fieldLabel : '表编辑',
					editable : false,
					hiddenName : 'is_table_edit',
					mode : 'local',
					allowBlank : false,
					listeners : {
						select : function(combo, record, index) {
							dimTableConsole(record.get('is_table_edit'));
						}
					},
					triggerAction : 'all',
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'table_name',
					id : 'table_name',
					fieldLabel : '表名',
					allowBlank : false,
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'tabke_pk',
					id : 'tabke_pk',
					fieldLabel : '主键名',
					allowBlank : false,
					anchor : '95%'
				},  {
					xtype : 'textarea',
					name : 'table_cols',
					id : 'table_cols',
					fieldLabel : '表编辑字段',
					autoScroll : true,
					height : 50,
					anchor : '95%'
				}]
			}],
			buttons : [{
				text : '保存',
				handler : function() {
					var formPanel = Ext.getCmp("editdimLinkForm");
					var tabke_pk= Ext.getCmp("tabke_pk").getValue();
					var dim_sql_expr= Ext.getCmp("dim_sql_expr").getValue();
					var table_cols=Ext.getCmp("table_cols").getValue();
					if(checkPkSelect(tabke_pk,dim_sql_expr)==false){
						Ext.Msg.alert("提示信息", "请在sql语句输入主键");
						return;
					}else if(checkEditColsPk(tabke_pk,table_cols)==false){
						Ext.Msg.alert("提示信息", "编辑列中必须输入主键");
						return;
					}else if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
//								Ext.Msg.alert("消息", action.result.info);
								if (action.result.success) {
									src_dim_ds.reload();
									Ext.getCmp('editDimLinkWindow').destroy();
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
					Ext.getCmp("editDimLinkWindow").destroy();
				}
			}]
		});
		EditWindow.superclass.initComponent.call(this);
	}
});

/**
 * 删除维度记录
 * @param {} link_id
 */
function deleteDimLink(dim_code) {
	Ext.Ajax.request({
		method : 'POST',
		url : pathUrl + '/dimLinkAjax/delete',
		params : {
			dim_cd : dim_code
		},
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (success) {
//				Ext.MessageBox.alert("提示信息", json.info);
				src_dim_ds.reload();
			} else {
				Ext.MessageBox.alert("提示信息", json.info);
			}
		}
	});
};

var currentRecord ;
/**
 * 当选中dimLinkGrid的时候触发方法
 */
function onRowSelect() {
	Ext.getCmp("dimLinkGrid").getSelectionModel().on("rowselect",
			function(mode, rowIndex, record) {
				currentRecord = record;//当前选中的记录
				
				dim_data_ds.removeAll();
				dim_cd = record.get('dim_cd');
				isTree = record.get('is_tree');
				rootValue = record.get('root_value');
				if (isTree == 'Y') {
					Ext.getCmp('dimLinkDetailTreePanel').setVisible(true);
					Ext.getCmp('dimLinkDetailGrid').setVisible(false);
					tree.removeAll();
					rootName(dim_cd);
				} else {
					Ext.getCmp('dimLinkDetailTreePanel').setVisible(false);
					Ext.getCmp('dimLinkDetailGrid').setVisible(true);
					dim_data_ds.load({
						params : {
							dim_cd : dim_cd
						}
					});
				}
				toolsButtonConsole(record.get('is_table_edit'));
			});
}

/**
 * 判断是否是树 来显示或隐藏父类字段和父类值
 * @param {} isTree ： Y:是； N:否
 */
function judgeTree(isTree) {
	if (isTree == 'Y') {
		Ext.getCmp('prt_col_name').setDisabled(false);
		Ext.getCmp('prt_col_name').setVisible(true);

		Ext.getCmp('root_value').setDisabled(false);
		Ext.getCmp('root_value').setVisible(true);
	} else {
		Ext.getCmp('prt_col_name').setDisabled(true);
		Ext.getCmp('prt_col_name').setVisible(false);

		Ext.getCmp('root_value').setDisabled(true);
		Ext.getCmp('root_value').setVisible(false);
	}
};

/**
 * 通过rootValue查询rootNode的名称
 * @param {} linkId
 */
function rootName(dimCode) {
	Ext.Ajax.request({
		method : 'POST',
		url : pathUrl + '/dimLinkAjax/findRootName',
		params : {
			dim_cd : dimCode
		},
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			var text = "根节点";
			if(json.results.length != 0){
				text = json.results[0].text;
			}
			if (success) {
				var node = getRootNode(rootValue, text, expandDimLinkTreeNode);
				tree.setRootNode(node);
//				node.expand();
			} else {
				Ext.Msg.alert('提示','加载失败!');
			}
		}
	});
}
/**
 * 通过是否编辑,控制表编辑内容是否显示
 */
function dimTableConsole(is_table_edit){
	if(is_table_edit=='Y'){
		Ext.getCmp('table_name').setDisabled(false);
		Ext.getCmp('table_name').setVisible(true);
		Ext.getCmp('tabke_pk').setDisabled(false);
		Ext.getCmp('tabke_pk').setVisible(true);
		Ext.getCmp('table_cols').setDisabled(false);
		Ext.getCmp('table_cols').setVisible(true);
	
	}else{
		Ext.getCmp('table_name').setDisabled(true);
		Ext.getCmp('table_name').setVisible(false);
		Ext.getCmp('tabke_pk').setDisabled(true);
		Ext.getCmp('tabke_pk').setVisible(false);
		Ext.getCmp('table_cols').setDisabled(true);
		Ext.getCmp('table_cols').setVisible(false);
	}
	toolsButtonConsole(is_table_edit);
}
/*
 * 维度明细操作按钮是否显示
 */
function toolsButtonConsole(is_table_edit){
	if(is_table_edit=='Y'){
		Ext.getCmp('dimDetailsTbar').setVisible(true);
	}else{
		Ext.getCmp('dimDetailsTbar').setVisible(false);
	}
}
/**
 * 判断sql查询语句中是否有主键
 * @param pkStr 维度明细主键
 * @param sql 查询语句
 */
function checkPkSelect(pkStr,sql){
	var flag=false;
	if(sql.indexOf("*")!=-1) {
		 var sqlStr=sql.substring(sql.indexOf("from"),sql.length);
		 if(sqlStr.indexOf("select")!=-1){
			var joinStr=sqlStr.substring(sqlStr.indexOf("select"),sqlStr.length);
			flag= commonPkValidate(pkStr,joinStr);
		 }else{
			 flag=true;
		 }
	}else{
		flag= commonPkValidate(pkStr,sql);
	}
	return flag;
}
/**
 * 检查编辑列中是否含有主键
 * @param pkStr 主键
 * @param table_cols 编辑列信息
 */
function checkEditColsPk(pkStr,table_cols){
	var flag=false;
	var editCols_array=table_cols.split(";");
	var pk_array=pkStr.split(",");
	var pk_map=convertToMap(pk_array);
	var count=0;
	for(var i=0;i<editCols_array.length;i++){
		var cols=editCols_array[i].split(",");
		if(pk_map.get(cols[0])!=null) count++;
	}
	if(count==pk_array.length) flag=true;
	return flag;
}
/**
 * 通用方法
 * @param pkStr 维度明细主键
 * @param sql 查询语句
 */
function commonPkValidate(pkStr,sqlStr){
	var pkArray=pkStr.split(",");
	var count=0;
	var checkFlag=false;
	//这里不用indexOf直接判断的原因是,查询语句中可能含有小名
	for(var i=0;i<pkArray.length;i++){
		if(sqlStr.indexOf(pkArray[i])!=-1) {
			count++;
		}
	}
	if(count==pkArray.length){
		checkFlag=true;
	}else{
		 checkFlag=false;
	}
	return checkFlag;
}
/**
 * 检查编辑列中是否含有主键
 * @param pkStr 主键
 * @param table_cols 编辑列
 */
function checkEditCols(pkStr,table_cols){
	var flag=false;
	var pk_array=pkStr.split(",");
	var cols_array=table_cols.split(",");
	var cols_map=convertToMap(cols_array);
	for(var i=0;i<pk_array.length;i++){
		if(cols_map.get(pk_array[i])!=null){
			flag=true;
			break;
		}
	}
	return flag;
}