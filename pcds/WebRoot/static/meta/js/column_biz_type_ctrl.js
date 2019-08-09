//是否是表元数据
function isTabMeta(node,showMsg){
	if(node && node.attributes['md_cate_cd'] != 'TAB'){
		if(showMsg){
			Ext.MessageBox.alert('提示','请先选择表元数据');
		}
		return false;
	}
	return true;
}

EditWindowTab = Ext.extend(Ext.Window, {
	title : '编辑数据分类',
	buttonAlign : 'center',
	id : 'editWindow',
	width : 400,
	height : 300,
	initComponent : function() {
		var metaNodeId = this.metaNodeId||'';
		var r1 = Ext.getCmp('typeGrid').getSelectionModel().getSelected();
		var old_id= r1.get('upp_rela_id');
		var old_cd= r1.get('col_biz_type_cd');
		Ext.applyIf(this, {
			modal : true,
			layout:'fit',
			listeners : {
				close : function() {
					Ext.getCmp("editWindow").destroy();
				}
			},
			items : [{
				xtype:'form',
				id : 'editForm',
				layout : 'form',
				labelWidth : 85,
				border:false,
				bodyStyle : 'padding: 10px',
				defaults:{
					anchor:'90%'
				},
				url : pathUrl + '/bizType/updateBizType',
				items:[
				    {
						 xtype:'textfield',
						 fieldLabel : '分类id',
						 name:'col_biz_type_cd',
						 id : 'col_biz_type_cd',
						 allowBlank:false
						 
						// anchor : '97%'
					 },{
						 xtype:'textfield',
						 fieldLabel : '分类id',
						 name:'old_col_biz_type_cd',
						 id : 'old_col_biz_type_cd',
						 allowBlank:false,
						 value:old_cd,
						 hidden:true
						// anchor : '97%'
					 },
					 	{
							xtype : 'textfield',
							name : 'col_biz_type_desc',
							id : 'col_biz_type_desc',
							fieldLabel : '分类描述',
							allowBlank : false,
							displayField : 'col_biz_type_desc',
							valueField : 'id',
							mode : 'local',
							triggerAction : 'all'
					   },{
						   	xtype : 'combo',
							name : 'is_public',
							id : 'is_public',
							fieldLabel : '是否公有',
							allowBlank : false,
							//disabled:true,
							displayField : 'is_public',
							valueField : 'id',
							mode : 'local',
							triggerAction : 'all',
							editable: false,
							store : new Ext.data.SimpleStore({
								fields : ['id', 'is_public'],
								data :
									[[1,'Y'],[2,'N']]
							}),
							listeners:{select:function(c,r,i){
								if(c.getValue()==1){
									Ext.getCmp('upp_rela_id').setValue('public_table');
									Ext.getCmp('upp_rela_id').setVisible(true);
								}else{
									Ext.getCmp('upp_rela_id').setValue(metaNodeId);
									Ext.getCmp('upp_rela_id').setVisible(false);
								}
							}}
						},{
						   xtype:'textfield',
						   fieldLabel : '关联id',
						   name:'upp_rela_id',
						   id : 'upp_rela_id',
						   //value:metaNodeId, 
						   readOnly:true
				  	 	},{
						   xtype:'textfield',
						   fieldLabel : '关联id',
						   name:'old_upp_rela_id',
						   id : 'old_upp_rela_id',
						   value:old_id,
						   hidden:true
				  	 	},
					{  
						xtype : 'numberfield',
						name : 'display_order',
						id : 'display_order',
						allowBlank:false,
						fieldLabel : '显示顺序'
				}
				]
			
			}  ],
		buttons : [{
				text : '保存',
				id : 'edit_pro',
				handler : function() {
					var formPanel = Ext.getCmp("editForm");
					if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
								if(action.result.success) {
									biz_type.reload();
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
		EditWindowTab.superclass.initComponent.call(this);
		}
});

AddWindowTab = Ext.extend(Ext.Window, {
	title : "添加数据分类",
	id : 'addWindow',
	width : 400,
	height : 300,
	initComponent : function() {
		var metaNodeId = this.metaNodeId||'';
		Ext.applyIf(this, {
			modal : true,
			layout:'fit',
			listeners : {
				close : function() {
					Ext.getCmp("addWindow").destroy();
				}
			},
			items : [{
				xtype:'form',
				id : 'addForm',
				layout : 'form',
				labelWidth : 85,
				border:false,
				bodyStyle : 'padding: 10px',
				defaults:{
					anchor:'90%'
				},
				url : pathUrl + '/bizType/addMetaType',
				items:[
					{
						 xtype:'textfield',
						 fieldLabel : '分类id',
						 name:'col_biz_type_cd',
						 id : 'col_biz_type_cd',
						 allowBlank:false
						// anchor : '97%'
					 },{
							xtype : 'textfield',
							name : 'col_biz_type_desc',
							id : 'col_biz_type_desc',
							fieldLabel : '分类描述',
							allowBlank : false,
							displayField : 'col_biz_type_desc',
							valueField : 'id',
							mode : 'local',
							triggerAction : 'all'
					   },{
						   	xtype : 'combo',
							name : 'is_public',
							id : 'is_public',
							fieldLabel : '是否公有',
							allowBlank : false,
							//disabled:true,
							displayField : 'is_public',
							valueField : 'id',
							mode : 'local',
							triggerAction : 'all',
							emptyText: '请选择...',
							editable: false,
							value:2,
							store : new Ext.data.SimpleStore({
								fields : ['id', 'is_public'],
								data :
									[[1,'Y'],[2,'N']]
							}),
							listeners:{select:function(c,r,i){
								if(c.getValue()==1){
									Ext.getCmp('upp_rela_id').setValue('public_table');
									Ext.getCmp('upp_rela_id').setVisible(true);
								}else{
									Ext.getCmp('upp_rela_id').setValue(metaNodeId);
									Ext.getCmp('upp_rela_id').setVisible(false);
								}
							}}
						},{
						   xtype:'textfield',
						   fieldLabel : '关联id',
						   name:'upp_rela_id',
						   id : 'upp_rela_id',
						   value:metaNodeId, 
						   hidden:true,
						   readOnly:true
				  	 	},
					{  
						xtype : 'numberfield',
						name : 'display_order',
						id : 'display_order',
						allowBlank:false,
						fieldLabel : '显示顺序'
				}
				]
			
			}  ],
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
									biz_type.reload();
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
		AddWindowTab.superclass.initComponent.call(this);
		}
	});
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

