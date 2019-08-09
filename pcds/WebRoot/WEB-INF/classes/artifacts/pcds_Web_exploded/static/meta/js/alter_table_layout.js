Ext.onReady(function(){

	var confitionPanel = new Ext.form.FormPanel({
		title : '查询条件',
		region : 'north',
		height : 73,
		layout : 'column',
		method : 'POST',
		border : false,
		split : false,
		labelWidth : 70,
		labelAlign : 'right',
		autoScroll : true,
		frame:true,
		buttonAlign : 'center',
		items : [{
			columnWidth : .25,
			border:false,
			layout : 'form',
			items : [dbCombo = new UrlRemoteCombo({
				url : pathUrl + '/meta/alterTable/queryMetadata?parent_metadata_id=10&md_cate_cd=DB',
				fieldLabel : '数据库',
				allowBlank : true,
				displayField : 'metadata_name',
				valueField : 'metadata_id',
				selectFirst : true,
				id : 'dbSelector',
				listeners : {
					select : function(combo, record) {
						var database_id = record.get('metadata_id');
						userCombo.clearValue();
						userCombo.getStore().load({
							params : {
								md_cate_cd : 'USR',
								parent_metadata_id : database_id
							}
						})
					}
				},
				anchor : '91%'
			})]
		},{
			columnWidth : .25,
			layout : 'form',
			border:false,
			items : [userCombo = new UrlRemoteCombo({
				url : pathUrl + '/meta/alterTable/queryMetadata',
				fieldLabel : '用户',
				allowBlank : false,
				autoLoad : false,
				selectFirst : true,
				displayField : 'metadata_name',
				valueField : 'metadata_id',
				id : 'userSelector',
				listeners : {
					select : function(combo, record) {
						var user_id = record.get('metadata_id');
						tableCombo.clearValue();
						tableCombo.getStore().load({
							params : {
								md_cate_cd : 'TAB',
								parent_metadata_id : user_id
							}
						})
					}
				},
				anchor : '91%'
			})]
		},{
			columnWidth : .25,
			layout : 'form',
			border:false,
			items : [tableCombo = new UrlRemoteCombo({
				url : pathUrl + '/meta/alterTable/queryMetadata',
				fieldLabel : '表',
				allowBlank : false,
				autoLoad : false,
				selectFirst : true,
				displayField : 'metadata_name',
				valueField : 'metadata_id',
				id : 'tableSelector',
				listeners : {
					select : function(combo, record) {
						store.load();
					}
				},
				anchor : '91%'
			})]
		}]
	});
	
	
	var store = new Ext.data.JsonStore({
		url : pathUrl + '/meta/alterTable/queryColumns',
		root : 'results',
		totalProperty : 'totalCount',
		listeners : {
			beforeload : function() {
				var database_id = dbCombo.getValue();
				var owner_id = userCombo.getValue();
				var table_id = tableCombo.getValue();
				store.baseParams = {
					database_id : database_id,
					owner_id : owner_id,
					table_id : table_id
				}
			}
		},
		fields : ['database_id','owner_id','table_id','column_name','column_desc','data_type_cd','data_length','data_scale','is_pk','is_nullable']
	});
	
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
		header : '列名',
		dataIndex : 'column_name'
	},{
		header : '列描述',
		dataIndex : 'column_desc'
	},{
		header : '数据类型',
		dataIndex : 'data_type_cd',
		renderer : function(val) {
			if (val == '01')
				return "文本";
			else if (val == '02')
				return "数值";
			else if (val == '03')
				return "日期";
			return val;
		}
	},{
		header : '数据长度',
		dataIndex : 'data_length'
	},{
		header : '数据刻度',
		dataIndex : 'data_scale'
	},{
		header : '是否主键',
		dataIndex : 'is_pk',
		renderer : function(val) {
			if (val == 'Y')
				return "是";
			else if (val == 'N')
				return "不是";
			return val;
		}
	},{
		header : '是否可空',
		dataIndex : 'is_nullable',
		renderer : function(val) {
			if (val == 'Y')
				return "可以";
			else if (val == 'N')
				return "不可以";
			return val;
		}
	}]);
	
	var menu = [{
		text : '添加(a)',
		iconCls : 'add',
		handler : function() {
			var database_id = dbCombo.getValue();
			var owner_id = userCombo.getValue();
			var table_id = tableCombo.getValue();
			var table_name = tableCombo.getRawValue();
			if(database_id == '' || owner_id == '' || table_id == '') {
				Ext.MessageBox.alert("提示信息", "请先依次选择:数据库-->用户-->表!");
				return;
			}
			addColumn(database_id, owner_id, table_id, table_name, store);
		}
	}, '-', {
		text : '编辑(e)',
		iconCls : 'edit',
		handler : function() {
			var record = gridPanel.getSelectionModel().getSelections()[0];
			if(record == null) {
				Ext.MessageBox.alert("提示信息", "请选择一条记录!");
				return;
			}
			editColumn(record, store)
		}
	}];
	
	var gridPanel = new Ext.grid.GridPanel({
		region : 'center',
		title : "表字段列表",
		autoScroll : true,
		loadMask : true,
		split : false,
		border : false,
		tbar : menu,
		ds : store,
		viewConfig : {forceFit : true},
		cm : cm
	});
	
	var viewport = new Ext.Viewport({
		layout : 'fit',
		border : false,
		items:[{
			xtype:'panel',
			layout : 'border',
			items : [confitionPanel, gridPanel]
		}]
	});
	
});