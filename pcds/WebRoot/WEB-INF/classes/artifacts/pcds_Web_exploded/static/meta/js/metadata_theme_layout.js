var formItemAnchor = '91%';

Ext.onReady(function(){
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		title : '主题属性',
		layout : 'form',
		url : pathUrl + "/metadata/theme/save",
		method : 'POST',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 0px 25px 5px 25px',
		autoScroll : true,
		buttonAlign : 'center',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'theme_id'}, 
			{name : 'theme_name'}, 
			{name : 'theme_desc'}, 
			{name : 'status_cd'}
		]),
		items : [{
				xtype : 'textfield',
				name : 'theme_id',
				itemCls  : 'uxHeight',
				readOnly : true,
				fieldLabel : '主题ID',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'theme_name',
				itemCls  : 'uxHeight',
				name : 'theme_name',
				fieldLabel : '主题名',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textarea',
				itemCls  : 'uxHeight',
				name : 'theme_desc',
				fieldLabel : '主题描述',
				anchor : formItemAnchor
			}],
		buttons : [{
			text : '保存',
			handler : function(){
				saveTheme(infoPanel);
			}
		},{
			text : '删除',
			handler : function(){
				Ext.MessageBox.confirm("确认信息", "是否删除该主题信息?", function(btn) {
					if (btn == 'yes')
						deleteTheme();
				});
			}
		}]
	});
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/theme/load',
		params : {
			theme_id : metadata_id
		}
	});
	
	var viewport = new Ext.Viewport({
		layout:'fit',
		border : false,
		items:[{
		    xtype : 'panel',
			layout : 'form',
			border : false,
			items:[
			       infoPanel
			]
		}]
	});
});
