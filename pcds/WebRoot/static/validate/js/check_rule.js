// 校验类型store
var check_type_ds = new Ext.data.JsonStore({
	url : pathUrl + '/checkRule/listCheckType',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['chk_type_cd','chk_type_desc']
	
});

//var status_store = new Ext.data.JsonStore({
//	url : pathUrl + '/metaSelector/listTemplateStatus',
//	root : 'results',
//	totalProperty : 'totalCount',
//	fields : ['status_cd', 'status_name'],
//	autoLoad:true
//});

// 方法store
var check_method_ds = new Ext.data.JsonStore({
	url : pathUrl + '/checkRule/listCheckMethod',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['chk_method_cd','chk_method_name']
	          
});
/**
 * 添加数据源维度窗口
 * @class AddWindow
 * @extends Ext.Window
 */
//Ext.form.Field.prototype.msgTarget = 'under';
AddWindow = Ext.extend(Ext.Window, {
	title : '添加校验规则',
	buttonAlign : 'center',
	id : 'addWindow',
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
					Ext.getCmp("addWindow").destroy();
				}
			},
			bodyStyle : 'padding: 10px',
			items : [{
				xtype : 'form',
				id : 'addForm',
				layout : 'form',
				labelWidth : 85,
				border : false,
				labelAlign : 'left',
				url : pathUrl + '/checkRule/addCheckRule',
				items : [{
					xtype : 'textfield',
					name : 'chk_rule_name',
					id : 'chk_rule_code',
					fieldLabel : '校验规则名称',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'chk_rule_desc',
					id : 'chk_rule_desc',
					fieldLabel : '校验规则描述',
					anchor : '95%'
				},{
					xtype : 'combo',
					name : 'chk_method_cd',
					displayField : 'chk_method_name',
					valueField : 'chk_method_cd',
					fieldLabel : '校验方法',
					hiddenName : 'chk_method_cd',
					mode : 'local',
					allowBlank : false,
					triggerAction : 'all',
					store : check_method_ds,
					editable : false,
					value:'01',
					anchor : '95%'
				},{
					xtype : 'combo',
					name : 'chk_type_cd',
					displayField : 'chk_type_desc',
					valueField : 'chk_type_cd',
					fieldLabel : '校验类型',
					hiddenName : 'chk_type_cd',
					mode : 'local',
					allowBlank : false,
					triggerAction : 'all',
					store : check_type_ds,
					editable : false,
					value:'01',
					anchor : '95%'
				},{
					xtype : 'combo',
					name : 'rule_type_flag',
					displayField : 'text',
					valueField : 'id',
					fieldLabel : '规则类型',
					hiddenName : 'rule_type_flag',
					mode : 'local',
					allowBlank : false,
					editable : false,
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						fields:['id','text'],
						data:[['0','内置'],['1','自定义']]
					}),
					value:'0',
					anchor : '95%'
				},{
					xtype : 'combo',
					name : 'status_cd',
					displayField : 'text',
					valueField : 'id',
					fieldLabel : '状态',
					hiddenName : 'status_cd',
					mode : 'local',
					allowBlank : false,
					editable : false,
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						fields:['id','text'],
						data:[['01','禁用'],['02','正常'],['03','删除']]
					}),
					value:'02',
					anchor : '95%'
				}
				,{
					xtype : 'textfield',
					name : 'chk_priority',
					id : 'chk_priority',
					fieldLabel : '校验优先级',
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'display_order',
					id : 'display_order',
					fieldLabel : '显示顺序',
					anchor : '95%'
				}, {
					xtype : 'textfield',
					name : 'chk_failure_tip',
					id : 'chk_failure_tip',
					fieldLabel : '失败提示',
					anchor : '95%'
				}, {
					xtype : 'textarea',
					name : 'chk_formula',
					id : 'chk_formula',
					fieldLabel : '校验公式',
					height:150,
					anchor : '95%'
				},  {
					xtype : 'textarea',
					name : 'chk_formula_desc',
					id : 'chk_formula_desc',
					fieldLabel : '校验公式描述',
					anchor : '95%'
				}]
			}],
			buttons : [{
				text : '保存',
				id : 'save_link',
				handler : function() {
					var formPanel = Ext.getCmp("addForm");
					if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
								if (action.result.success) {
									check_rule_ds.reload();
									Ext.getCmp('addWindow').destroy();
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
					Ext.getCmp("addWindow").destroy();
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
	title : '编辑校验规则',
	buttonAlign : 'center',
	id : 'editWindow',
	width : 500,
	height : 500,
	autoScroll : true,
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'form',
			listeners : {
				close : function() {
					Ext.getCmp("editWindow").destroy();
				}
			},
			bodyStyle : 'padding: 10px',
			items : [{
				xtype : 'form',
				id : 'editForm',
				layout : 'form',
				labelWidth : 85,
				border : false,
				labelAlign : 'left',
				url : pathUrl + '/checkRule/editCheckRule',
				items : [{
					xtype:'hidden',
					name:'chk_rule_code',
					id:'chk_rule_code',
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'chk_rule_name',
					id : 'chk_rule_name',
					fieldLabel : '校验规则名称',
					allowBlank : false,
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'chk_rule_desc',
					id : 'chk_rule_desc',
					fieldLabel : '校验规则描述',
					anchor : '95%'
				},{
					xtype : 'combo',
					name : 'chk_method_cd',
					displayField : 'chk_method_name',
					valueField : 'chk_method_cd',
					fieldLabel : '校验方法',
					hiddenName : 'chk_method_cd',
					mode : 'local',
					allowBlank : false,
					editable : false,
					triggerAction : 'all',
					store : check_method_ds,
					anchor : '95%'
				},{
					xtype : 'combo',
					name : 'chk_type_cd',
					displayField : 'chk_type_name',
					valueField : 'chk_type_cd',
					fieldLabel : '校验类型',
					hiddenName : 'chk_type_cd',
					mode : 'local',
					allowBlank : false,
					editable : false,
					triggerAction : 'all',
					store : check_type_ds,
					anchor : '95%'
				},{
					xtype : 'combo',
					name : 'rule_type_flag',
					displayField : 'text',
					valueField : 'id',
					fieldLabel : '规则类型 ',
					hiddenName : 'rule_type_flag',
					mode : 'local',
					allowBlank : false,
					editable : false,
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						fields:['id','text'],
						data:[['0','内置'],['1','自定义']]
					}),
					anchor : '95%'
				},{
					xtype : 'combo',
					name : 'status_cd',
					displayField : 'text',
					valueField : 'id',
					fieldLabel : '状态',
					hiddenName : 'status_cd',
					mode : 'local',
					allowBlank : false,
					editable : false,
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						fields:['id','text'],
						data:[['01','禁用'],['02','正常'],['03','删除']]
					}),
					value:'02',
					anchor : '95%'
				}
				,{
					xtype : 'textfield',
					name : 'chk_priority',
					id : 'chk_priority',
					fieldLabel : '校验优先级',
					anchor : '95%'
				},{
					xtype : 'textfield',
					name : 'display_order',
					id : 'display_order',
					fieldLabel : '显示顺序',
					anchor : '95%'
				},  {
					xtype : 'textfield',
					name : 'chk_failure_tip',
					id : 'chk_failure_tip',
					fieldLabel : '失败提示',
					anchor : '95%'
				},{
					xtype : 'textarea',
					name : 'chk_formula',
					id : 'chk_formula',
					fieldLabel : '校验公式',
					height:150,
					anchor : '95%'
				},  {
					xtype : 'textarea',
					name : 'chk_formula_desc',
					id : 'chk_formula_desc',
					fieldLabel : '校验公式描述',
					anchor : '95%'
				}]
			}],
			buttons : [{
				text : '保存',
				handler : function() {
					var formPanel = Ext.getCmp("editForm");
					if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
								if (action.result.success) {
									check_rule_ds.reload();
									Ext.getCmp('editWindow').destroy();
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
					Ext.getCmp("editWindow").destroy();
				}
			}]
		});
		AddWindow.superclass.initComponent.call(this);
	}
});

/**
 * 删除校验规则
 * @param {} 
 */
function deleteChkRule(chk_rule_code) {
	Ext.Ajax.request({
		method : 'POST',
		url : pathUrl + '/checkRule/deleteChkRule',
		params : {
			chk_rule_code: chk_rule_code
		},
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (success) {
//				Ext.MessageBox.alert("提示信息", json.info);
				check_rule_ds.reload();
			} else {
				Ext.MessageBox.alert("提示信息", json.info);
			}
		}
	});
};

var	ChkRuleTreePanel = function(param){
	
	ChkRuleTreePanel.superclass.constructor.call(this,{
		title:'规则库',
		region:'center',
		contentEl:'center',
		collapsible: false,
		id:'chkRuleTreePanel',
		frame:false,
		lines:true,
		bodyStyle:'padding:5px',
		autoScroll:true,
		loader: new Ext.tree.TreeLoader(),
		root: new Ext.tree.AsyncTreeNode({
			id:param.rootId, 
			text:param.rootName,
			expanded: true,
			children: [{
				id:'00',
				text: '通用',
				leaf: false,
				attributes:{type:'chk_type'},
				children:[{
					text:'loading',
					cls: 'x-tree-node-loading',
					leaf:true
				}],
				listeners:{
					'expand' : expandChkRuleTreeNode
				}
			}, {
				id:'01',
				text: '列间',
				leaf: false,
				attributes:{type:'chk_type'},
				children:[{
					text:'loading',
					cls: 'x-tree-node-loading',
					leaf:true
				}],
				listeners:{
					'expand' : expandChkRuleTreeNode
				}
			}, {
				id:'02',
				text: '表间',
				leaf: false,
				attributes:{type:'chk_type'},
				children:[{
					text:'loading',
					cls: 'x-tree-node-loading',
					leaf:true
				}],
				listeners:{
					'expand' : expandChkRuleTreeNode
				}
			}]
		})
	});
}

function getRootNode(id,text,fn)
{
	var root = new Ext.tree.AsyncTreeNode({
		id:id,
		text:text,
		children:[{
			text:'loading',
			cls: 'x-tree-node-loading',
			leaf:true
		}]
	});

	if(fn!=undefined)
		root.on('expand',fn);

	return root;
}

function expandChkRuleTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/checkRule/queryChkRuleTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {nodeID:node.id},
			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
//							text:'['+tl[i].id+']'+tl[i].text+'('+tl[i].property+')',
							text:tl[i].text,
							leaf:tl[i].leaf,
							attributes:{type:tl[i].type},
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandChkRuleTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				var msg = '出错!'
				Ext.MessageBox.alert(response.responseText+' '+msg);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

Ext.extend(ChkRuleTreePanel,Ext.tree.TreePanel);
