var selectLinkId = '', isTree = '', tree = '', rootValue = '';
var expander = new Ext.grid.RowExpander({
	tpl : new Ext.Template('<br><p><b>校验公式:</b> {chk_formula}</p><br>')
});
// 校验规则库store
var check_rule_ds = new Ext.data.JsonStore({
	url : pathUrl + '/checkRule/listCheckRuleLib',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['chk_rule_code','chk_rule_name','chk_rule_desc','chk_type_code',
	    'chk_method_code','rule_type_flag','chk_formula','chk_formula_desc','chk_priority',
	    'status_code','status_name','chk_type_name','chk_method_name',
	   'create_user_id','create_time','update_user_id','update_time','display_order','chk_failure_tip']
	
});

//校验规则column
var check_rule_cm = [
     expander,
    {
	header : '校验规则名称',
	dataIndex : 'chk_rule_name'
}, {
	header : '校验规则描述',
	dataIndex : 'chk_rule_desc'
},{
	header : '校验类型',
	dataIndex : 'chk_type_name'
}, {
	header : '校验方法',
	dataIndex : 'chk_method_name'
}, {
	header : '规则类型',
	dataIndex : 'rule_type_flag',
	renderer:function(v){
		switch(v){
			case '0': return '内置';break; 
			case '1': return '自定义';break; 
		}
	}
},{
	header : '状态',
	dataIndex : 'status_name'
}, {
	header : '校验公式描述',
	dataIndex : 'chk_rule_desc'
}, {
	header : '校验失败提示',
	dataIndex : 'chk_failure_tip'
},{
	header : '校验优先级',
	dataIndex : 'chk_priority'
}, {
	header : '创建用户',
	dataIndex : 'create_user_id'
}, {
	header : '创建时间',
	dataIndex : 'create_time'
}, {
	header : '修改用户',
	dataIndex : 'update_user_id'
}, {
	header : '修改时间',
	dataIndex : 'update_time'
}];

//// 数据源维度明细store
//var dim_data_ds = new Ext.data.JsonStore({
//	url : pathUrl + '/dimLinkAjax/listExpressionDetail',
//	root : 'results',
//	fields : ['value_field', 'display_field']
//});
//
//// 数据源维度明细column
//var field_data_cm = [{
//	header : '标签字段',
//	dataIndex : 'display_field',
//	align : 'center'
//}, {
//	header : '值字段',
//	dataIndex : 'value_field',
//	align : 'center'
//}];

MyViewportUi = Ext.extend(Ext.Viewport, {
	layout : 'border',
	initComponent : function() {
		Ext.applyIf(this, {
			items : [
				chkRuleTreePanel = new ChkRuleTreePanel({rootId:'root',rootName:'校验规则'})
//			{
//				xtype : 'grid',
//				region : 'center',
//				id : 'ruleGrid',
//				title : '校验规则库',
//				columns : check_rule_cm,
//				plugins : expander,
//				store : check_rule_ds,
//				loadMask : true,
//				split : true,
//				sm : new Ext.grid.RowSelectionModel({
//					singleSelect : true
//				}),
//				flex : 1,
//				viewConfig : {
//					forceFit : true
//				},
//				bbar:new Ext.PagingToolbar({
//					pageSize : 30,
//					store : check_rule_ds,
//					displayInfo : true,
//					displayMsg : '第{0}-{1}条记录,共{2}条记录',
//					emptyMsg : "没有记录"
//				}),
//				tbar : [{
//					xtype : 'button',
//					text : '添加',
//					iconCls : 'add',
//					handler : function() {
//						var addWindow = new AddWindow();
//						addWindow.show();
//					}
//				}, '-', {
//					xtype : 'button',
//					text : '编辑',
//					iconCls : 'edit',
//					handler : function() {
//						var r = Ext.getCmp("ruleGrid").getSelectionModel().getSelected();
//						if (!r) {
//							Ext.Msg.alert("提示信息", "请选择需要修改的记录");
//						} else {
//							var editWindow = new EditWindow();
//							editWindow.show();
//							Ext.getCmp("editForm").getForm().loadRecord(r);
//						}
//					}
//				}, '-', {
//					xtype : 'button',
//					text : '删除',
//					iconCls : 'delete',
//					handler : function() {
//						var r = Ext.getCmp("ruleGrid").getSelectionModel().getSelected();
//						if (!r) {
//							Ext.Msg.alert("提示信息", "请选择需要删除的记录");
//						} else {
//							var chk_rule_code = r.get('chk_rule_code');
//							Ext.MessageBox.confirm('确认信息', '是否确认删除选中的数据源维度记录?',
//									function(btn) {
//										if (btn == 'yes')
//											deleteChkRule(chk_rule_code);
//									});
//						}
//					}
//				}, '->', {
//					xtype : 'textfield',
//					id : 'searchKey',
//					emptyText : '请输入规则名称...',
//					listeners : {
//						specialkey : function(field, e) {
//							if (e.getKey() == Ext.EventObject.ENTER) {
//								check_rule_ds.baseParams = {searchKey : Ext.getCmp("searchKey").getValue()}
//								check_rule_ds.load()
//							}
//						}
//					},
//					width : 150
//				}, {
//					xtype : 'button',
//					iconCls : 'search',
//					handler : function() {
//						check_rule_ds.baseParams = {searchKey : Ext.getCmp("searchKey").getValue()}
//						check_rule_ds.load()
//					}
//				}]
//			}
				, panel = new Ext.Panel({
					title:'编辑校验规则',
					id : 'editDmProPanel',
					width:800,
					autoScroll:true,
					region : 'east',
					split : true,
					layout : 'form',
					autoScroll : true,
					bodyStyle : 'padding: 20 0 20 20px',
					items : [
					{
					xtype : 'form',
					id : 'addForm',
					layout : 'form',
					labelWidth : 85,
					border : true,
					frame:true,
					labelAlign : 'left',
					buttonAlign :'center',
					reader: new Ext.data.JsonReader({
			            root: 'results',
			            totalProperty: 'totalCount'
			        }, [
			            {name: 'chk_rule_code'},
			            {name: 'chk_rule_name'},
			            {name: 'chk_type_name'},
			            {name: 'chk_method_name'},
			            {name: 'chk_type_code' },
			            {name: 'chk_rule_desc' },
			            {name: 'chk_method_code'},
			            {name: 'status_code'},
			            {name: 'rule_type_flag'},
			            {name: 'chk_priority'},
			            {name:'display_order'},
			            {name: 'chk_failure_tip'},
			            {name: 'chk_formula'},
			            {name: 'chk_formula_desc'}
			        ]),
					items : [
					{
						xtype:'panel',
						layout:'column',
						items:[
							{
								columnWidth:'.5',
								layout:'form',
								items:[
									{
										xtype : 'hidden',
										name : 'chk_rule_code',
										id : 'chk_rule_code'
									},
									{
										xtype : 'hidden',
										name : 'chk_type_code',
										id : 'chk_type_code'
									},
									{
										xtype : 'hidden',
										name : 'chk_method_code',
										id : 'chk_method_code'
									},
									{
										xtype : 'textfield',
										name : 'chk_rule_name',
										id : 'chk_rule_name',
										fieldLabel : '校验规则名称',
										allowBlank : false,
										anchor : '95%'
									},	{
											xtype : 'textfield',
											name : 'chk_method_name',
											fieldLabel : '校验方法',
											id:'chk_method_name',
											readOnly:true,
											anchor : '95%'
										},	{
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
											xtype : 'numberfield',
											name : 'chk_priority',
											id : 'chk_priority',
											fieldLabel : '校验优先级',
											anchor : '95%'
										}
								]
							
							},{
								columnWidth:'.5',
								layout:'form',
								items:[
									{
										xtype : 'textfield',
										name : 'chk_rule_desc',
										id : 'chk_rule_desc',
										fieldLabel : '校验规则描述',
										anchor : '95%'
									},{
										xtype : 'combo',
										name : 'chk_type_name',
										id:'chk_type_name',
										fieldLabel : '校验类型',
										readOnly:true,
										anchor : '95%'
									},	{
											xtype : 'combo',
											name : 'status_code',
											displayField : 'text',
											valueField : 'id',
											fieldLabel : '状态',
											hiddenName : 'status_code',
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
										},{
											xtype : 'textfield',
											name : 'display_order',
											id : 'display_order',
											fieldLabel : '显示顺序',
											anchor : '95%'
										}
								]
							
							}
						
						]
					}, {
							xtype : 'textfield',
							name : 'chk_failure_tip',
							id : 'chk_failure_tip',
							fieldLabel : '失败提示',
							anchor : '95%'
						},
					 {
						xtype : 'textarea',
						name : 'chk_formula',
						id : 'chk_formula',
						fieldLabel : '校验公式',
						height:150,
						anchor : '98%'
					},  {
						xtype : 'textarea',
						name : 'chk_formula_desc',
						id : 'chk_formula_desc',
						fieldLabel : '校验公式描述',
						height:100,
						anchor : '98%'
					}],
				buttons : [{
					text : '新增',
					id : 'add_rule',
					disabled:true,
					handler : function() {
						var node = chkRuleTreePanel.getSelectionModel().getSelectedNode();
						if(!node){
							return;
						}
						var id = node.id;
						var id_array = id.split(';');
						var formPanel = Ext.getCmp("addForm");
						formPanel.form.reset();
						this.setDisabled(true);
						Ext.getCmp('chk_method_code').setValue(id_array[1]);
						Ext.getCmp('chk_type_code').setValue(id_array[0]);
						Ext.getCmp('chk_type_name').setValue(node.parentNode.text);
						Ext.getCmp('chk_method_name').setValue(node.text);
						Ext.getCmp('save_rule').setDisabled(false);
					}
				},{
					text : '保存',
					id : 'save_rule',
					disabled:true,
					handler : function() {
						var url = '/checkRule/editCheckRule'; 
						var addFlag = false;
						var node = chkRuleTreePanel.getSelectionModel().getSelectedNode();
						if(!node){
							return;
						}
						var attr = node.attributes.attributes;
						if(attr){
							if(attr.type=='chk_method'){
								url	= '/checkRule/addCheckRule';
								addFlag = true;
							}
						}
						var formPanel = Ext.getCmp("addForm");
						if (formPanel.form.isValid()) {
							formPanel.form.submit({
								url : pathUrl + url,
								waitMsg : '正在处理,请稍后......',
								success : function(form, action) {
									Ext.Msg.alert("消息", action.result.info);
									if(addFlag){
										Ext.getCmp('save_rule').setDisabled(true);
										Ext.getCmp('add_rule').setDisabled(false);
										addNode(action.result.chk_rule_code);
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
				},{
					text : '删除',
					id:'del_rule',
					disabled:true,
					handler : function() {
					var node =  chkRuleTreePanel.getSelectionModel().getSelectedNode();
						if(!node){
							Ext.Msg.alert("消息","请先选择元数据");
							return;
						}
						Ext.Msg.confirm('Confirm','确认删除?',function(btn){
							if(btn=='yes'){
								var formPanel = Ext.getCmp("addForm");
								if (formPanel.form.isValid()) {
									formPanel.form.submit({
										url : pathUrl + '/checkRule/deleteChkRule',
										waitMsg : '正在处理,请稍后......',
										success : function(form, action) {
											Ext.Msg.alert("消息", action.result.info);
											node.remove();
											Ext.getCmp("addForm").form.reset();
										},
										failure : function(form, action) {
											Ext.Msg.alert("消息", action.result.info);
										}
									})
								} else {
									Ext.Msg.alert("提示信息", "请输入完整信息");
								}
							}
						})
						
					}
				}]
				}
			]
			})
			]
		});
		MyViewportUi.superclass.initComponent.call(this);
	}
});

Ext.onReady(function() {
	var myview = new MyViewportUi();
	check_type_ds.load();
	check_method_ds.load();
    
	chkRuleTreePanel.on('click', function(node,e) {
	    var formPanel = Ext.getCmp("addForm");
	    formPanel.form.reset();
	    var attr = node.attributes.attributes;
	    if(attr){
	    	if(attr.type=='chk_method'){
				Ext.getCmp('add_rule').setDisabled(false);
				Ext.getCmp('save_rule').setDisabled(true);
				Ext.getCmp('del_rule').setDisabled(true);
	    	}
	    	if(attr.type=='chk_rule'){
				Ext.getCmp('add_rule').setDisabled(true);
				Ext.getCmp('save_rule').setDisabled(false);
				Ext.getCmp('del_rule').setDisabled(false);
	    		formPanel.load({
			    	url:pathUrl + '/checkRule/listCheckRuleLib',
			    	params:{chk_rule_code:node.id}
		  		});
	    	}
	    	if(attr.type=='chk_type'){
	    		Ext.getCmp('add_rule').setDisabled(true);
				Ext.getCmp('save_rule').setDisabled(true);
				Ext.getCmp('del_rule').setDisabled(true);
	    	}
	    }else{
				Ext.getCmp('add_rule').setDisabled(true);
				Ext.getCmp('save_rule').setDisabled(true);
				Ext.getCmp('del_rule').setDisabled(true);
	    }
	  
	});
});

function addNode(id){	
	var cnode=new Ext.tree.AsyncTreeNode({
			id:id,
			text:Ext.getCmp('chk_rule_name').getValue(),
			leaf:true,
			attributes:{type:'chk_rule'}
	});
	var node =  chkRuleTreePanel.getSelectionModel().getSelectedNode();
	node.insertBefore(cnode,node.firstChild);
}
function del(id){	
	var node =  chkRuleTreePanel.getSelectionModel().getSelectedNode();
	node.remove();
}
