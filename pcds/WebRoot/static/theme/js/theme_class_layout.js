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
Ext.onReady(function() {
	var topMenu = [{
		text : '添加(a)',
		tooltip : '添加主题',
		iconCls : 'add'
		,handler : function() {
			var sm = ThemeTreePanel.getSelectionModel().getSelectedNode();
			if(sm == '' || sm == null){
				Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
				return;
			}
			doAddTheme(ThemeTreePanel);
		}
	}, '-', {
		text : '编辑(e)',
		tooltip : '编辑主题',
		iconCls : 'edit'
		,handler : function() {
			var sm = ThemeTreePanel.getSelectionModel().getSelectedNode();
			if(sm == '' || sm == null){
				Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
				return;
			}
			var id = ThemeTreePanel.getSelectionModel().getSelectedNode().id;
			if (id == 'theme')
				return;
			doEditTheme(ThemeTreePanel);
		}
	}, '-', {
		text : '删除(d)',
		tooltip : '删除主题',
		iconCls : 'delete'
		,handler : function() {
			var sm = ThemeTreePanel.getSelectionModel().getSelectedNode();
			if(sm == '' || sm == null){
				Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
				return;
			}
			var id = ThemeTreePanel.getSelectionModel().getSelectedNode().id;
			if (id == 'theme')
				return;

			Ext.MessageBox.confirm("确认信息", "是否删除该菜单?", function(btn) {
				if (btn == 'yes')
					doDeleteTheme(ThemeTreePanel);
			});
		}
	}];
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [{
			xtype : 'panel',
			layout : 'border',
			border : false,
			style : 'padding:0px 4px 4px 0px;',
			items : [
//			{
//				xtype : 'panel',
//				region : 'north',
//				tbar : topMenu,
//				border : false
//			}, 
			ThemeTreePanel = new Ext.tree.TreePanel({
				region : 'center',
				id : 'themeTreePanel',
				frame : false,
				loader : new Ext.tree.TreeLoader(),
				lines : false,
				bodyStyle : 'padding:5px 5px',
				autoScroll : true,
				split : true,
				tbar : topMenu,
				root:getRootNode('theme','主题',expandThemeTreeNode)
			})]
		}]
	});

	ThemeTreePanel.on('dblclick', function(node,e) {
        if(node.id=='theme')
            return;
        doEditTheme(ThemeTreePanel);
    });
});
