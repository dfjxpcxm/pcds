var formItemAnchor = '91%';

Ext.onReady(function(){
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		title : '列字段属性',
		layout : 'form',
		url : pathUrl + "/metadata/column/save",
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
			{name : 'column_id'}, 
			{name : 'column_name'}, 
			{name : 'column_desc'},
			{name : 'data_type_cd'},
			{name : 'data_length'},
			{name : 'data_scale'},
			{name : 'is_pk'},
			{name : 'is_nullable'}
		]),
		items : [{
				xtype : 'hidden',
				name : 'column_id',
				fieldLabel : '列ID',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'column_name',
				itemCls  : 'uxHeight',
				name : 'column_name',
				fieldLabel : '列名',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				itemCls  : 'uxHeight',
				id : 'column_desc',
				name : 'column_desc',
				fieldLabel : '列描述',
				allowBlank : false,
				anchor : formItemAnchor
			},new ArrayCombo({
				id : 'dataTypeSelector',
				hiddenName : 'data_type_cd',
				data : [['01','文本'],['02','数值'],['03','日期']],
				fieldLabel : '数据类型',
				defaultValue : '01',
				itemCls  : 'uxHeight',
				anchor : formItemAnchor
			}),{
				xtype : 'numberfield',
				itemCls  : 'uxHeight',
				name : 'data_length',
				fieldLabel : '数据长度',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'numberfield',
				itemCls  : 'uxHeight',
				name : 'data_scale',
				itemCls  : 'uxHeight',
				fieldLabel : '数据刻度',
				anchor : formItemAnchor
			},new ArrayCombo({
				id : 'isPKSelector',
				hiddenName : 'is_pk',
				itemCls  : 'uxHeight',
				data : [['N','否'],['Y','是']],
				value : '0',
				fieldLabel : '是否主键',
				defaultValue : 'N',
				anchor : '91%'
			}),new ArrayCombo({
				id : 'nullableSelector',
				hiddenName : 'is_nullable',
				data : [['Y','是'],['N','否']],
				itemCls  : 'uxHeight',
				fieldLabel : '是否可空',
				defaultValue : 'Y',
				anchor : '91%'
			})],
		buttons : [{
			text : '保存',
			handler : function(){
				saveColumn(infoPanel);
			}
		},{
			text : '删除',
			handler : function(){
				Ext.MessageBox.confirm("确认信息", "是否删除该表信息?", function(btn) {
					if (btn == 'yes')
						deleteColumn();
				});
			}
		}]
	});
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/column/load',
		params : {
			column_id : metadata_id
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
