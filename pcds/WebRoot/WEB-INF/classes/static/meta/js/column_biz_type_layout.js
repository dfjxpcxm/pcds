var biz_type = new Ext.data.JsonStore({
	url : pathUrl + '/bizType/listMetadataBizType',
	root : 'results',
	totalProperty : 'totalCount',
	fields : [
	          'upp_rela_id', 
	          'col_biz_type_cd', 
	          'col_biz_type_desc', 
	          'is_public', 
	          'display_order']
});

//字段分类列表显示column
var biz_type_c = [
{
	header : '分类ID',
	dataIndex : 'col_biz_type_cd'
}, {
	header : '分类描述',
	dataIndex : 'col_biz_type_desc'
},{
	header : 'UPP_RELA_ID',
	dataIndex : 'upp_rela_id'
}, {
	header : 'IS Public',
	dataIndex : 'is_public',
	renderer:function(v){
		if(v=='Y'){
			return 'Y';
		}else{
			return 'N';
		}
	}
}, {
	header : '显示顺序',
	dataIndex : 'display_order'
}];


MyViewportUi = Ext.extend(Ext.Viewport, {
	layout : 'border',
	//初始化树
	initComponent : function() {
			Ext.applyIf(this, {
			items : [
				metadataTreePanel = new Ext.tree.TreePanel({
	    		title : '元数据树',
	    		region : 'west',
	    		collapsible : false,
	    		id : 'metadataTreePanel',
	    		frame : false,
	    		loader : new Ext.tree.TreeLoader(),
	    		lines : true,
	    		split : true,
	    		border : true,
	    		autoScroll : true,
	    		bodyStyle : 'padding:5px',
	    		width : 360,
	    		root : getRootNode(10,'元数据',expandMetadataNode)
	    	}),
			{
				xtype : 'grid',
				id:'typeGrid',
				region:'center',
				title:'分类字段列表',
				columns : biz_type_c,
				store : biz_type,
				loadMask:true,
				viewConfig : {
					forceFit : true},
				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
				bbar:new Ext.PagingToolbar({
					pageSize : 30,
					store : biz_type,
					displayInfo : true,
					displayMsg : '第{0}-{1}条记录,共{2}条记录',
					emptyMsg : "没有记录"
				}),
				tbar : [{
					xtype : 'button',
					text : '添加',
					iconCls : 'add',
					id:'add_btn',
					handler : function() {
						var node = metadataTreePanel.getSelectionModel().getSelectedNode();
						if(isTabMeta(node,'Y')){
							add(node);
						}
					}
				},
				{
					xtype : 'button',
					text : '编辑',
					iconCls : 'edit', 
					id:'edit_btn',
					handler : function() {
						var node = metadataTreePanel.getSelectionModel().getSelectedNode();
						//回显
						var r = Ext.getCmp('typeGrid').getSelectionModel().getSelected();
						
						if(!r){
							Ext.MessageBox.alert('提示','请先选择分类字段');
							return;
						}
						else if(isTabMeta(node,'Y')){
							var editWindow = new EditWindowTab({doUrl:pathUrl + '/bizType/updateBizType',metaNodeId:node.id});
								Ext.getCmp("editForm").getForm().loadRecord(r);
								//alert(r.get('upp_rela_id'));
								editWindow.show();
						}
					}
				},
				{
					xtype : 'button',
					text : '删除',
					iconCls : 'delete',
					id:'del_btn',
					handler : function() {
					
						var r = Ext.getCmp('typeGrid').getSelectionModel().getSelected();
						var node = metadataTreePanel.getSelectionModel().getSelectedNode();
						if(!node){
							Ext.MessageBox.alert('提示','请先选择元数据');
							return;
						}
						if(!r){
							Ext.MessageBox.alert('提示','请先选择分类字段');
							return;
						}
						Ext.Msg.confirm('确认','确认删除？',function(btn){
							if(btn=='yes'){
								Ext.Ajax.request({
									url: pathUrl + '/bizType/deleteMetaType',
									method: 'POST',
									params: {upp_rela_id:r.get('upp_rela_id'),col_biz_type_cd:r.get('col_biz_type_cd')},
									success:function(response, options){
										var json=Ext.util.JSON.decode(response.responseText);
										if(json.success){
											biz_type.reload();
										}else{
											Ext.MessageBox.alert('错误',json.info);
										}
									},
									failure:function(response, options){
										Ext.MessageBox.alert('错误',response.responseText);
									}
								});
							}
						});
					}
				}]
			}
			]
		});

		MyViewportUi.superclass.initComponent.call(this);
	}
});


Ext.onReady(function() {
	var myview = new MyViewportUi();
	var selectNode = null;
	
	metadataTreePanel.on('click', function(node,e) {
		selectNode = node;
	 	if(isTabMeta(node)){
			biz_type.load();
			
	 	}
	});
	
	 biz_type.on('beforeload', function() {
		biz_type.baseParams = {
			upp_rela_id : (selectNode ? selectNode.id:'')
		};
	});
});

var getTypeCode = function (){
	return Ext.getCmp('type_rg').getValue().getRawValue();
};
function add (node){
	var addWindow = new AddWindowTab({doUrl:pathUrl + '/bizType/addMetaType',metaNodeId:node.id});
	addWindow.show();
};