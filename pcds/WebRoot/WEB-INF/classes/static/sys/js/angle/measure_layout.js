Ext.onReady(function(){
	//Ext.QuickTips.init();

	var root=getRootNode('~13','指标树',expandMeasureTreeNode);

	var tb = new Ext.Toolbar('north');
	tb.add({
        text:'添加(a)',
        tooltip:'添加新指标',
        iconCls:'add',
        handler: function(){
            if(measureTreePanel.getSelectionModel().getSelectedNode()==null)
			{
				Ext.MessageBox.alert('错误', 
					'请选择一个节点，继续进行！'
				);
				return;
			}
            measureAddor(measureTreePanel);
        }
    },'-',{
        text:'删除(s)',
        tooltip:'删除选择指标',
        iconCls:'delete',
        handler: function(){
            if(measureTreePanel.getSelectionModel().getSelectedNode()==null)
			{
				Ext.MessageBox.alert('错误', 
					'请选择一个节点，继续进行！'
				);
				return;
			}

			var id=measureTreePanel.getSelectionModel().getSelectedNode().id;
            measureDeletor(measureTreePanel,id);
        }
    });

	var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'north',
				contentEl:'north',
				tbar: tb,
				border:false,
				height:26
			},
			measureTreePanel = new Ext.tree.TreePanel({
				region:'center',
				contentEl:'center',
				collapsible: false,
				id:'measureTreePanel',
				frame:false,
				loader: new Ext.tree.TreeLoader(),
				lines:false,
				bodyStyle:'padding:5px',
				autoScroll:true,
				root:root
			})		
		]
	})
	
	measureTreePanel.on('dblclick', function(node,e) {
        measureEditor(measureTreePanel);
    });
    
});