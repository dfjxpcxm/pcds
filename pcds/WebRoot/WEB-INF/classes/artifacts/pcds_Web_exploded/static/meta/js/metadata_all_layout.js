var addControlJson = null;

var referenceRoot = new Ext.tree.AsyncTreeNode({
	id : 'referenceRoot',
	text : '引用根节点',
	expanded : true,
	children : []
});

Ext.onReady(function() {

	initAddControlJson();

	var root = getRootNode('root', '元数据', expandMetadataNode);
	root.on("click", dispacheToConfig);

	var metadataTreePanel = new Ext.tree.TreePanel({
		title : '元数据树',
		region : 'center',
		collapsible : false,
		id : 'metadataTreePanel',
		frame : false,
		loader : new Ext.tree.TreeLoader(),
		lines : true,
		split : true,
		border : true,
		autoScroll : true,
		bodyStyle : 'padding:5px',
		root : root
	});

	var referenceTreePanel = new Ext.tree.TreePanel({
		//title : '元数据引用关系',
		region : 'south',
		collapsible : false,
		id : 'referenceTreePanel',
		frame : false,
		loader : new Ext.tree.TreeLoader(),
		lines : true,
		split : true,
		border : true,
		autoScroll : true,
		tbar : ['数据字典元数据被引用关系：'],
		/**
		tbar : [{
			text : '删除',
			iconCls : 'delete',
			handler : function(){
				var metaNodeArray = referenceTreePanel.getChecked();
				if(metaNodeArray.length == 0) {
					Ext.Msg.alert("提示信息","请勾选需要删除的关系!");
					return;
				}
				Ext.MessageBox.confirm("确认信息", "是否删除选中的关系?", function(btn) {
					if (btn == 'yes') {
						deleteRela(metaNodeArray);
					}
				});
			}
		}],*/
		bodyStyle : 'padding:5px',
		height : 110,
		rootVisible : false,
		root : referenceRoot
	});

	var operPanel = new Ext.Panel({
		region : 'center',
		border : true,
		split : true,
		contentEl : 'center'
	});

	var viewport = new Ext.Viewport({
		layout : 'fit',
		id : 'view',
		border : false,
		items : [{
			xtype : 'panel',
			layout : 'border',
			border : false,
			items : [operPanel, {
				region : 'west',
				xtype : 'panel',
				border : false,
				layout : 'border',
				width : 460,
				items : [metadataTreePanel, referenceTreePanel]
			}]
		}]
	});
	root.expand();
	referenceRoot.expand();
});

function initAddControlJson() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/listMetaCategory',
		method : 'POST',
		callback : function(options, success, response) {
			addControlJson = Ext.util.JSON.decode(response.responseText);
		}
	})
}