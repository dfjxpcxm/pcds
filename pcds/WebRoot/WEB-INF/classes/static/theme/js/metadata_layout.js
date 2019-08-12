/*
------------------------------------------------------------------------------
文件名称：theme_class_layout.js
说    明：JavaScript脚本，主题分类管理布局页面。
版    本：1.0
修改纪录:
------------------------------------------------------------------------------
时间              		修改人         		说明
2014-11-25      wanggl          创建
------------------------------------------------------------------------------
 */
Ext.onReady(function(){
	//Ext.QuickTips.init();
	
	var topMenu = [{
		text : '添加(a)',
		id : 'add_info',
		tooltip : '添加元数据',
		iconCls : 'add'
		,handler : function() {
			var sm = metadataTreePanel.getSelectionModel().getSelectedNode();
			if(sm == '' || sm == null){
				Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
				return;
			}
			addNode(metadataTreePanel);
		}
	},'-',{
		text : '修改(u)',
		id : 'edit_info',
		tooltip : '修改元数据',
		iconCls : 'edit'
		,handler : function() {
			var sm = metadataTreePanel.getSelectionModel().getSelectedNode();
			if(sm == '' || sm == null){
				Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
				return;
			}
			editNode(metadataTreePanel);
		}
	},'-',{
		text : '删除(d)',
		id : 'del_info',
		tooltip : '删除元数据',
		iconCls : 'delete'
		,handler : function() {
			var sm = metadataTreePanel.getSelectionModel().getSelectedNode();
			if(sm == '' || sm == null){
				Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
				return;
			}
			delNode(metadataTreePanel);
		}
//	},{
//		text : '刷新(a)',
//		tooltip : '刷新元数据',
//		iconCls : 'refresh'
//		,handler : function() {
//			doRefreshTree(metadataTreePanel);
//		}
	}];
	
	var n_panel = new Ext.Panel({
		region:'north',
		tbar:topMenu,
		border:false,
		height:100
	});

	var root=getRootNode('root','元数据',expandMetadataTreeNode);
	
	var metadataTreePanel = new Ext.tree.TreePanel({
		region:'center',
		collapsible: false,
		id:'metadataTreePanel',
		frame : false,
		loader: new Ext.tree.TreeLoader(),
		lines:false,
		autoScroll:true,
//		tbar:topMenu,
		bodyStyle : 'padding:5px',
		root:root
	});	

	var viewport = new Ext.Viewport({
		layout:'fit',
		border : false,
		items:[{
		    xtype : 'panel',
			layout : 'border',
			border : false,
			items:[
			       //n_panel,
			       metadataTreePanel
			]
		}]
	});
	
	metadataTreePanel.on('click', function(node,e) {
		if('root'==node.id){
			Ext.getCmp('add_info').enable();
			Ext.getCmp('edit_info').disable();
			Ext.getCmp('del_info').disable();
		}else if('root'==node.parentNode.id){
			Ext.getCmp('add_info').disable();
			Ext.getCmp('edit_info').enable();
			Ext.getCmp('del_info').disable();
		}else{
			Ext.getCmp('add_info').disable();
			Ext.getCmp('edit_info').disable();
			Ext.getCmp('del_info').disable();
		}
	});
    
});