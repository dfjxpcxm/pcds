var formItemAnchor = '91%';

Ext.onReady(function(){
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		title : '数据库属性',
		layout : 'column',
		url : pathUrl + "/metadata/database/save",
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
			{name : 'database_id'}, 
			{name : 'database_name'}, 
			{name : 'connect_str'}, 
			{name : 'test_user_name'}, 
			{name : 'test_user_password'}, 
			{name : 'database_type_cd'}, 
			{name : 'server_addr'}, 
			{name : 'access_port'}, 
			{name : 'char_encoding'}, 
			{name : 'database_desc'}
		]),
		items : [{
			columnWidth : '0.5',
			layout : 'form',
			items : [{
				xtype : 'textfield',
				name : 'database_id',
				itemCls  : 'uxHeight',
				fieldLabel : '数据库ID',
				allowBlank : false,
				readOnly : true,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'database_name',
				itemCls  : 'uxHeight',
				name : 'database_name',
				fieldLabel : '数据库名称',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				name : 'connect_str',
				itemCls  : 'uxHeight',
				fieldLabel : '数据库连接串',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'test_user_name',
				itemCls  : 'uxHeight',
				name : 'test_user_name',
				fieldLabel : '测试用户名',
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'test_user_password',
				itemCls  : 'uxHeight',
				name : 'test_user_password',
				inputType : 'password',
				fieldLabel : '测试用户密码',
				anchor : formItemAnchor
			}]
		},{
			columnWidth : '0.5',
			layout : 'form',
			items : [new ArrayCombo({
				id : 'dbTypeSelector',
				hiddenName : 'database_type_cd',
				data : [['00','Oracle'],['01','DB2'],['02','SQL Server'],['03','Infomix'],['04','MySQL'],['05','Sybase'],['06','Teradata']],
				fieldLabel : '数据库类型',
				defaultValue : '00',
				itemCls  : 'uxHeight',
				anchor : formItemAnchor
			}),{
				xtype : 'textfield',
				name : 'server_addr',
				itemCls  : 'uxHeight',
				fieldLabel : '服务器地址',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'numberfield',
				name : 'access_port',
				itemCls  : 'uxHeight',
				fieldLabel : '访问端口',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				name : 'char_encoding',
				itemCls  : 'uxHeight',
				fieldLabel : '字符编码',
				allowBlank : false,
				anchor : formItemAnchor
			}]
		},{
			columnWidth : '0.95',
			layout : 'form',
			items : [{
				xtype : 'textarea',
				itemCls  : 'uxHeight',
				name : 'database_desc',
				fieldLabel : '数据库描述',
				anchor : '91%'
			}]
		}],
		buttons : [{
			text : '测试连接',
			handler : function(){
				testConnect(infoPanel);
			}
		},{
			text : '保存',
			handler : function(){
				saveDatabase(infoPanel);
			}
		},{
			text : '删除',
			handler : function(){
				Ext.MessageBox.confirm("确认信息", "是否删除该数据库信息?", function(btn) {
					if (btn == 'yes')
						deleteDatabase();
				});
			}
		}]
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
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/database/load',
		params : {
			database_id : metadata_id
		}
	});
});

function getSplitPanel(height) {
	return {
		xtype : 'panel',
		border : false,
		height : height
	}
}
