Ext.onReady(function(){
	//Ext.QuickTips.init();

	var root=getRootNode('8888','总行',expandBankOrgTreeNode);

	var tb = new Ext.Toolbar('north');
	tb.add({
        text:'添加(a)',
        tooltip:'添加新机构',
        iconCls:'add',
        handler: function(){
            if(bankOrgTreePanel.getSelectionModel().getSelectedNode()==null)
			{
				Ext.MessageBox.alert('错误', 
					'请选择一个节点，继续进行！'
				);
				return;
			}
            bankOrgAddor(bankOrgTreePanel);
        }
    },'-',{
        text:'删除(s)',
        tooltip:'删除选择机构',
        iconCls:'delete',
        handler: function(){
            if(bankOrgTreePanel.getSelectionModel().getSelectedNode()==null)
			{
				Ext.MessageBox.alert('错误', 
					'请选择一个节点，继续进行！'
				);
				return;
			}

			var id=bankOrgTreePanel.getSelectionModel().getSelectedNode().id;
            bankOrgDeletor(bankOrgTreePanel,id);
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
			bankOrgTreePanel = new Ext.tree.TreePanel({
				region:'center',
				contentEl:'center',
				collapsible: false,
				id:'bankOrgTreePanel',
				frame:false,
				loader: new Ext.tree.TreeLoader(),
				lines:false,
				bodyStyle:'padding:5px',
				autoScroll:true,
				root:root
			})		
		]
	})
	
	bankOrgTreePanel.on('dblclick', function(node,e) {
        bankOrgEditor(bankOrgTreePanel);
    });
    
});