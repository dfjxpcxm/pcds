//
//Ext.form.Field.prototype.msgTarget = 'under';
var check_rule_ds = new Ext.data.JsonStore({
	url : pathUrl + '/checkRule/listCheckRule',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['chk_rule_code','chk_rule_name','chk_rule_desc',
      'chk_type_code','chk_type_desc','chk_method_code','chk_method_name','rule_type_flag',
      'chk_formula','chk_formula_desc','chk_priority',
      'status_code','status_name','create_user_id','create_time',
      'update_user_id','update_time','display_order','chk_failure_tip']
});
//校验规则column
var check_rule_cm = [
{
	header : '校验规则名称',
	dataIndex : 'chk_rule_name'
}, {
	header : '规则类型',
	dataIndex : 'rule_type_flag',
	renderer:function(v){
		switch(v){
			case '0': return '内置';break; 
			case '1': return '自定义';break; 
		}
	}
}
];

//['04', '存储过程'],暂不支持
//单元格校验类型
var cell_array = [
    ['01', '正则表达式'],
    ['02', 'JAVA程序'], 
    ['03', 'SQL语句']
    ];

//列校验类型
var col_array = [['02', 'JAVA程序'],['03', 'SQL语句']];
//表间校验类型
var tab_col_array = [['03', 'SQL语句']];

//优先级,控制校验的执行属性顺序
var priorityLevel = [
	['1', '1'],['2', '2'],['3', '3'],['4', '4'],['5', '5'],
	['6', '6'],['7', '7'],['8', '8'],['9', '9'],['10', '10']];

//校验动作
var chk_action_array = [
	['0', '通用'],['2', '独立校验']];

//查询校验规则函数
function expandChkFnTreeNode(node)
{
	var chkMethod = Ext.getCmp('chkMethodCode').getValue();;
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url : pathUrl + '/checkRule/getChkFnList',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {nodeId:node.id,chkMethod:chkMethod},
			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  
			success:function(response, options){
				Ext.MessageBox.hide();
				
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text: tl[i].text,
							qtip: tl[i].desc,
							content: tl[i].content,
							leaf:true
						});
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			}
		});
	}
}


AddWindow = Ext.extend(Ext.Window, {
	title : '添加校验规则',
	buttonAlign : 'center',
	id : 'addWindow',
	width : 900,
	height : 500,
	autoScroll:true,
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'border',
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
				region:'center',
				labelWidth : 85,
				border : true,
				labelAlign : 'left',
				buttonAlign : 'center',
				frame:true,
				border:true,
				url : pathUrl + '/checkRule/addMetaRule',
				items : [
				     {
				    	 xtype:'hidden',
				    	 id:'metadata_id',
				    	 name:'metadata_id'
				     },
				    {
				    	xtype : 'radiogroup',
						name : 'chk_type_code',
						id : 'type_rg',
						fieldLabel : '校验类型',
						focusClass :'',
						listeners : {
					    	 change:function(){
				    	 				var type_code = getTypeCode();
				 						var checkMethodCode = Ext.getCmp('checkMethodCode'); 
//				 						checkMethodCode.getStore().loadData((type_code=='00'||type_code=='03')?cell_array:tab_col_array,false);
										check_rule_ds.reload();
										Ext.getCmp('chk_rule_name').reset();
										Ext.getCmp('chk_priority').reset();
										Ext.getCmp('chk_formula').reset();
										Ext.getCmp('chk_formula_desc').reset();
										Ext.getCmp('chk_failure_tip').reset();
				       			}
				       		},
						items:[
					       {
					    	xtype:'radio',
					    	boxLabel:'通用校验',
					    	id:'general',
					    	name:'check_type_code',
					    	inputValue:'03'
					       }, {
					    	xtype:'radio',
					    	boxLabel:'表间校验',
					    	id:'tab',
					    	name:'check_type_code',
					    	inputValue:'02'
					       },{
						    	xtype:'radio',
						    	boxLabel:'列校验',
						    	id:'col',
						    	name:'check_type_code',
						    	inputValue:'01'
						     },{
						    	xtype:'radio',
						    	boxLabel:'单元格校验',
						    	id:'cell',
						    	name:'check_type_code',
						    	inputValue:'00'
						     }
						 ],
						anchor : '95%'
				    },
				   {
					   layout:'column',
					   items:[
							{
								columnWidth:'.5',
								layout:'form',
								items:[{
									xtype : 'combo',
									name : 'check_method_code',
									id : 'checkMethodCode',
									fieldLabel : '校验方法',
									allowBlank : false,
									displayField : 'text',
									valueField : 'id',
									editable : false,
									hiddenName : 'check_method_code',
									mode : 'local',
									triggerAction : 'all',
									store : new Ext.data.SimpleStore({
										fields : ['id', 'text'],
										data : cell_array
									}),
									listeners:{
										select:function(){
											check_rule_ds.reload();
											Ext.getCmp('chk_rule_name').reset();
											Ext.getCmp('chk_priority').reset();
											Ext.getCmp('chk_formula').reset();
											Ext.getCmp('chk_formula_desc').reset();
											Ext.getCmp('chk_failure_tip').reset();
										}
									},
									anchor : '95%'
								}]
							}, 
							{
								columnWidth:'.5',
								layout:'form',
								items:[
								   {
									   xtype:'numberfield',
									   name:'chk_priority',
									   id:'chk_priority',
									   fieldLabel:'校验优先级',
									   anchor : '95%'
								   }
								]
							}  
					    ]
				   },{
					   xtype:'textfield',
					   fieldLabel : '校验规则',
					   name:'chk_rule_name',
					   id : 'chk_rule_name',
					   allowBlank:false,
					   readOnly:true,
					   anchor : '97%'
				   } ,{
					   xtype:'hidden',
					   name:'chk_rule_code',
					   id : 'chk_rule_code',
					   anchor : '97%'
				   },{
						xtype : 'textfield',
						name : 'chk_failure_tip',
						id : 'chk_failure_tip',
						fieldLabel : '失败提示',
						disabled:true,
						value:'',
						anchor : '97%'
					} 
				   ,{
					xtype : 'textarea',
					name : 'chk_formula',
					id : 'chk_formula',
					fieldLabel : '校验公式',
					autoScroll : true,
					height : 150,
					disabled:true,
					value:'',
					anchor : '97%'
				},{
					xtype : 'textarea',
					name : 'chk_formula_desc',
					id : 'chk_formula_desc',
					fieldLabel : '校验公式描述',
					autoScroll : true,
					height : 150,
					disabled:true,
					anchor : '97%'
				}]
			},{
				xtype : 'grid',
				width : 400,
				region:'east',
				columns : check_rule_cm,
				store : check_rule_ds,
				loadMask:true,
				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
//				viewConfig : {
//					forceFit : true
//				},
				listeners : {
					rowclick : function(grid, rowIndex, e) {
						var record = check_rule_ds.getAt(rowIndex);
						Ext.getCmp('chk_priority').setValue(record.get('chk_priority'));
						Ext.getCmp('chk_formula').setValue(record.get('chk_formula'));
						Ext.getCmp('chk_formula_desc').setValue(record.get('chk_formula_desc'));
						Ext.getCmp('chk_failure_tip').setValue(record.get('chk_failure_tip'));
						Ext.getCmp('chk_rule_code').setValue(record.get('chk_rule_code'));
						Ext.getCmp('chk_rule_name').setValue(record.get('chk_rule_name'));
						rule_type_flag = record.get('rule_type_flag')
						if(rule_type_flag!='1'){
							Ext.getCmp('chk_formula').setDisabled(true);
							Ext.getCmp('chk_formula_desc').setDisabled(true);
							Ext.getCmp('chk_failure_tip').setDisabled(true);
						}else{
							Ext.getCmp('chk_formula').setDisabled(false);
							Ext.getCmp('chk_formula_desc').setDisabled(false);
							Ext.getCmp('chk_failure_tip').setDisabled(false);
						}
					}
				  },tbar : ["关键字：", {
						xtype : 'textfield',
						name : 'searchKey',
						id:'searchKey',
						listeners : {
							specialkey : function(field, e) {
								if (e.getKey() == Ext.EventObject.ENTER) {
									check_rule_ds.reload();
								}
							}
						},
						width : 300 
					}, {
						xtype : 'button',
						iconCls : 'search',
						handler : function() {
							check_rule_ds.reload();
						}
					}]
				}
				],
			buttons : [{
				text : '保存',
				id : 'save_pro',
				handler : function() {
					var formPanel = Ext.getCmp("addForm");
					if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
								if(action.result.success) {
									meta_rule_ds.reload();
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
 * 修改配置校验规则
 * @class EditWindow
 * @extends Ext.Window
 */
EditWindow = Ext.extend(Ext.Window, {
	title : '配置校验规则',
	buttonAlign : 'center',
	id : 'editWindow',
	width : 900,
	height : 500,
	autoScroll:true,
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'border',
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
				region:'center',
				labelWidth : 85,
				border : true,
				labelAlign : 'left',
				buttonAlign : 'center',
				frame:true,
				url : pathUrl + '/checkRule/editMetaRule',
				items : [
				     {
				    	 xtype:'hidden',
				    	 id:'metadata_id',
				    	 name:'metadata_id'
				     }, {
				    	 xtype:'hidden',
				    	 id:'old_chk_rule_code',
				    	 name:'old_chk_rule_code'
				     },
				    {
				    	xtype : 'radiogroup',
						name : 'chk_type_code',
						id : 'type_rg',
						fieldLabel : '校验类型',
						focusClass :'',
						listeners : {
					    	 change:function(){
				    	 				var type_code = getTypeCode();
				 						var checkMethodCode = Ext.getCmp('checkMethodCode'); 
//				 						checkMethodCode.getStore().loadData((type_code=='00'||type_code=='03')?cell_array:tab_col_array,false);
										check_rule_ds.reload();
										if(resetStatus=='0'){
											resetStatus='1';
										}else{
											Ext.getCmp('chk_rule_name').reset();
											Ext.getCmp('chk_priority').reset();
											Ext.getCmp('chk_formula').reset();
											Ext.getCmp('chk_formula_desc').reset();
											Ext.getCmp('chk_failure_tip').reset();
										}
				       			}
				       		},
						items:[
					       {
					    	xtype:'radio',
					    	boxLabel:'通用校验',
					    	id:'general',
					    	name:'check_type_code',
					    	inputValue:'03'
					       }, {
					    	xtype:'radio',
					    	boxLabel:'表间校验',
					    	id:'tab',
					    	name:'check_type_code',
					    	inputValue:'02'
					       },{
						    	xtype:'radio',
						    	boxLabel:'列校验',
						    	id:'col',
						    	name:'check_type_code',
						    	inputValue:'01'
						     },{
						    	xtype:'radio',
						    	boxLabel:'单元格校验',
						    	id:'cell',
						    	name:'check_type_code',
						    	inputValue:'00'
						     }
						 ],
						anchor : '95%'
				    },
				   {
					   layout:'column',
					   items:[
							{
								columnWidth:'.5',
								layout:'form',
								items:[{
									xtype : 'combo',
									name : 'chk_method_code',
									id : 'checkMethodCode',
									fieldLabel : '校验类型',
									allowBlank : false,
									displayField : 'text',
									valueField : 'id',
									editable : false,
									hiddenName : 'check_method_code',
									mode : 'local',
									triggerAction : 'all',
									store : new Ext.data.SimpleStore({
										fields : ['id', 'text'],
										data : cell_array
									}),
									listeners:{
										select:function(){
											check_rule_ds.reload();
											if(resetStatus=='0'){
												resetStatus='1';
											}else{
												Ext.getCmp('chk_rule_name').reset();
												Ext.getCmp('chk_priority').reset();
												Ext.getCmp('chk_formula').reset();
												Ext.getCmp('chk_formula_desc').reset();
												Ext.getCmp('chk_failure_tip').reset();
											}
										}
									},
									anchor : '95%'
								}]
							}, 
							{
								columnWidth:'.5',
								layout:'form',
								items:[
								   {
									   xtype:'numberfield',
									   name:'chk_priority',
									   id:'chk_priority',
									   fieldLabel:'校验优先级',
									   anchor : '95%'
								   }
								]
							}  
					    ]
				   },{
					   xtype:'textfield',
					   fieldLabel : '校验规则',
					   name:'chk_rule_name',
					   id : 'chk_rule_name',
					   allowBlank:false,
					   anchor : '97%'
				   } ,{
					   xtype:'hidden',
					   name:'chk_rule_code',
					   id : 'chk_rule_code',
					   anchor : '97%'
				   },{
						xtype : 'textfield',
						name : 'chk_failure_tip',
						id : 'chk_failure_tip',
						fieldLabel : '失败提示',
						disabled:true,
						value:'',
						anchor : '97%'
					} 
				   ,{
					xtype : 'textarea',
					name : 'chk_formula',
					id : 'chk_formula',
					fieldLabel : '校验公式',
					autoScroll : true,
					height : 150,
					disabled:true,
					value:'',
					anchor : '97%'
				},{
					xtype : 'textarea',
					name : 'chk_formula_desc',
					id : 'chk_formula_desc',
					fieldLabel : '校验公式描述',
					autoScroll : true,
					height : 150,
					disabled:true,
					anchor : '97%'
				}]
			},{
				xtype : 'grid',
				width : 400,
				region:'east',
				columns : check_rule_cm,
				store : check_rule_ds,
				loadMask:true,
				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
//				viewConfig : {
//					forceFit : true
//				},
				listeners : {
					rowclick : function(grid, rowIndex, e) {
						var record = check_rule_ds.getAt(rowIndex);
						Ext.getCmp('chk_priority').setValue(record.get('chk_priority'));
						Ext.getCmp('chk_formula').setValue(record.get('chk_formula'));
						Ext.getCmp('chk_formula_desc').setValue(record.get('chk_formula_desc'));
						Ext.getCmp('chk_failure_tip').setValue(record.get('chk_failure_tip'));
						Ext.getCmp('chk_rule_code').setValue(record.get('chk_rule_code'));
						Ext.getCmp('chk_rule_name').setValue(record.get('chk_rule_name'));
						rule_type_flag = record.get('rule_type_flag')
						if(rule_type_flag!='1'){
							Ext.getCmp('chk_formula').setDisabled(true);
							Ext.getCmp('chk_formula_desc').setDisabled(true);
							Ext.getCmp('chk_failure_tip').setDisabled(true);
						}else{
							Ext.getCmp('chk_formula').setDisabled(false);
							Ext.getCmp('chk_formula_desc').setDisabled(false);
							Ext.getCmp('chk_failure_tip').setDisabled(false);
						}
					}
				  },tbar : ["关键字：", {
						xtype : 'textfield',
						name : 'searchKey',
						id:'searchKey',
						listeners : {
							specialkey : function(field, e) {
								if (e.getKey() == Ext.EventObject.ENTER) {
									check_rule_ds.reload();
								}
							}
						},
						width : 300 
					}, {
						xtype : 'button',
						iconCls : 'search',
						handler : function() {
							check_rule_ds.reload();
						}
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
								if (action.result.success) {
									meta_rule_ds.reload();
									Ext.Msg.alert("消息", action.result.info);
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
		EditWindow.superclass.initComponent.call(this);
	}
});



/**
 * grid下拉框
 */
GridSelector = function(obj) {
	var random = new Date().getMilliseconds();
	var ds = obj.ds; // 必需
	var width = obj.width ? obj.width : 300;
	var height = obj.height ? obj.height : 200;
	var anchor = obj.anchor ? obj.anchor : '90%';
	var valueField = obj.valueField; // 必需
	var displayField = obj.displayField; // 必需
	this.store = ds;
	obj.id = obj.id ? obj.id : random;
	obj.value = obj.value ? obj.value : "";
	this.fieldLabel = obj.fieldLabel; // 必需
	this.hidden = obj.hidden ? obj.hidden : false;
	this.displayField = obj.displayField ? obj.displayField : obj.fieldLabel;
	this.valueField = valueField;
	this.hiddenName = obj.name ? obj.name : valueField;
	this.name = obj.name ? obj.name : valueField;
	this.editable = obj.editable ? obj.editable : false;
	this.allowBlank = obj.allowBlank ? obj.allowBlank : false;
	this.width = obj.labelWidth ? obj.labelWidth : 190;
	this.anchor = anchor;
//	ds.load({
//		callback : obj.callback
//	});
	var grid = {
		xtype : 'grid',
		width : width,
		height : height,
		columns : obj.cm,
		store : ds,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
//		viewConfig : {
//			forceFit : true
//		},
		listeners : {
			rowclick : function(grid, rowIndex, e) {
				showMenu.hide();
				var record = ds.getAt(rowIndex);
				Ext.getCmp(obj.id).setValue(record.get(valueField));
				Ext.getCmp(obj.chk_priority).setValue(record.get(obj.chk_priority));
				Ext.getCmp(obj.chk_formula).setValue(record.get(obj.chk_formula));
				Ext.getCmp(obj.chk_formula_desc).setValue(record.get(obj.chk_formula_desc));
				Ext.getCmp(obj.id).setRawValue(record.get(displayField));
				rule_type_flag = record.get('rule_type_flag')
				if(rule_type_flag!='1'){
					Ext.getCmp('chk_formula').setDisabled(true);
					Ext.getCmp('chk_formula_desc').setDisabled(true);
				}else{
					Ext.getCmp('chk_formula').setDisabled(false);
					Ext.getCmp('chk_formula_desc').setDisabled(false);
				}
				if (obj.listeners) {
					var items = obj.itemIDs.split(",");
					for (var i = 0; i < items.length; i++) {
						var info = '';
						record.fields.each(function(c, d) {
							if (record.get(c.name) != '' && c.name != undefined)
								info += ',' + c.name + ':' + "'"
										+ record.get(c.name) + "'";
						});
						var gid = obj.id.substring(0, 4);
						if (info != '') {
							info = info.substring(1);
							info = '{' + info + '}';
						}
						if (Ext.getCmp(items[i])) {
							Ext.getCmp(items[i]).setValue(null);
							Ext.getCmp(items[i]).getStore().reload({
								params : {
									itemID : items[i],
									comboID : record.get(valueField),
									filter : info
								}
							});
						} else if (Ext.getCmp(gid + items[i])) {
							Ext.getCmp(gid + items[i]).setValue(null);
							Ext.getCmp(gid + items[i]).getStore().reload({
								params : {
									itemID : items[i],
									comboID : record.get(valueField),
									filter : info
								}
							});
						}
					}
				}
			}
		},
		tbar : ["关键字：", {
			xtype : 'textfield',
			name : 'keyWord',
			width : 100
		}, {
			xtype : 'button',
			iconCls : 'search',
			handler : function() {
				var keyWord = this.previousSibling().getValue();
				if (obj.searchName) {
					var params = {};
					params[obj.searchName] = keyWord; // 必需
					ds.load({
						params : params,
						callback : obj.callback
					});
				} else
					ds.load({
						params : {
							keyWord : keyWord
						},
						callback : obj.callback
					});
			}
		}]
	};
	if (obj.bbar)
		grid.bbar = {
			xtype : 'paging',
			pageSize : obj.pageSize ? obj.pageSize : 10,
			store : ds,
			displayInfo : true,
			displayMsg : '第{0}-{1}条记录,共{2}条',
			emptyMsg : "没有记录"
		};
	var showMenu = new Ext.menu.Menu({
		items : [grid]
	});

	GridSelector.superclass.constructor.call(this, {
		id : obj.id,
		mode : 'local',
		value : obj.value,
		blankText : '不能为空',
		store : ds,
		triggerAction : 'all'
	});
	this.expand = function() {
		if (this.menu == null) {
			this.menu = showMenu;
		}

		this.menu.show(this.el, "tl-bl?");
	};
};
Ext.extend(GridSelector, Ext.form.ComboBox);






/**
 * 获取树的根节点
 * @param {} nodeId 根节点id
 * @param {} nodeName 根节点名
 * @param {} expandFn 节点的展开事件方法
 */
function getRootNode(nodeId, nodeName, expandFn){
	var root = new Ext.tree.AsyncTreeNode({
		id : nodeId,
		text : nodeName,
		qtip : nodeName,
		md_cate_cd : 'ROT',
		children : [{
			text : 'loading',
			cls : 'x-tree-node-loading',
			leaf : true
		}]
	});
	
	if (expandFn != undefined)
		root.on('expand', expandFn);
	
	return root;
}

var leafTypes = ["COL","FLD","FBT","TBT","XCL"];

function isLeaf(type) {
	for (var i = 0; i < leafTypes.length; i++) {
		if(leafTypes[i] == type)
			return true;
	}
	return false;
}

/**
 * 展开元数据节点方法
 * @param {} parentNode 被展开式父节点
 */
function expandMetadataNode(parentNode) {
	
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
							text : '[' + tl[i].category.md_cate_name + ']' +' '+ tl[i].metadata_desc,
							leaf : leaf,
							icon : pathUrl + '/static/meta/img/' + tl[i].md_cate_cd + '.png',
							md_cate_cd : tl[i].md_cate_cd,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						
						if(!leaf) {
							cnode.on('expand', expandMetadataNode);
						}
						parentNode.appendChild(cnode);
					}
//					Ext.getCmp("view").doLayout();
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