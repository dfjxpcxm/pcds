var formItemAnchor = '91%';

Ext.onReady(function(){
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		title : '数据库用户属性',
		layout : 'form',
		url : pathUrl + "/metadata/user/save",
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
			{name : 'user_id'}, 
			{name : 'user_name'}, 
			{name : 'user_password'}, 
			{name : 'user_desc'}
		]),
		items : [{
				xtype : 'textfield',
				name : 'user_id',
				itemCls  : 'uxHeight',
				fieldLabel : '用户ID',
				readOnly : true,
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'user_name',
				itemCls  : 'uxHeight',
				name : 'user_name',
				fieldLabel : '用户名',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				name : 'user_password',
				inputType : 'password',
				itemCls  : 'uxHeight',
				fieldLabel : '密码',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textarea',
				itemCls  : 'uxHeight',
				name : 'user_desc',
				fieldLabel : '用户描述',
				anchor : formItemAnchor
			}],
		buttons : [{
			text : '保存',
			handler : function(){
				saveUser(infoPanel);
			}
		},{
			text : '删除',
			handler : function(){
				Ext.MessageBox.confirm("确认信息", "是否删除该数据库用户信息?", function(btn) {
					if (btn == 'yes')
						deleteUser();
				});
			}
		}]
	});
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/user/load',
		params : {
			user_id : metadata_id
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
