var formItemAnchor = '91%';

Ext.onReady(function(){
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		region : 'north',
		height : 222,
		title : 'Sheet模板属性',
		layout : 'form',
		url : pathUrl + "/metadata/sheet/save",
		method : 'POST',
		border : false,
		split : false,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 10px 25px 5px 25px',
		buttonAlign : 'center',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'sheet_id'
		}, {
			name : 'start_row'
		}, {
			name : 'sheet_name'
		}, {
			name : 'display_order'
		}, {
			name : 'tid',
			mapping : 'table.table_id'
		}, {
			name : 'tname',
			mapping : 'table.table_desc'
		}]),
		items : [{
			xtype : 'textfield',
			name : 'sheet_id',
			fieldLabel : 'sheet页ID',
			readOnly : true,
			allowBlank : false,
			anchor : formItemAnchor
		}, {
			xtype : 'hidden',
			id : 'tid',
			name : 'tid'
		}, {
			xtype : 'hidden',
			id : 'tname',
			name : 'tname'
		}, {
			xtype : 'textfield',
			id : 'sheet_name',
			name : 'sheet_name',
			fieldLabel : '模板名',
			allowBlank : false,
			anchor : formItemAnchor
		}, {
			xtype : 'numberfield',
			name : 'start_row',
			fieldLabel : '起始行',
			allowBlank : false,
			anchor : formItemAnchor
		}, {
			xtype : 'numberfield',
			name : 'display_order',
			fieldLabel : '显示顺序',
			allowBlank : false,
			anchor : '91%'
		}, treeCombo = new TreeCombo({
			hiddenName : 'table.table_id',
			fieldLabel : '关联表',
			rootId : '10',
			rootName : '数据字典',
			listWidth : 240,
			listHeight : 320,
			allowBlank : true,
			otherAttributes : {
				md_cate_cd : 'CAT'
			},
			getTextFn : window.parent.getTextFn,
			expandFn : window.parent.expandDictionNode,
			filteClickFn : window.parent.filteClickFn
		})],
		buttons : [{
			text : '保存',
			handler : function() {
				saveSheet(infoPanel);
			}
		}, {
			text : '删除',
			handler : function() {
				Ext.Msg.confirm("确认信息", "是否删除该Sheet页信息?", function(btn) {
					if (btn == 'yes')
						deleteSheet();
				});
			}
		}]
	});
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/sheet/load',
		params : {
			sheet_id : metadata_id
		},
		success : function() {
			var tid = Ext.getCmp("tid").getValue();
			var tname = Ext.getCmp("tname").getValue();
			treeCombo.setVal(tname,tid);
		}
	});
	
	var store = new Ext.data.JsonStore({
		url : pathUrl + '/metadata/xlscolumn/queryColumnBySheetId',
		root : 'results',
		totalProperty : 'totalCount',
		listeners : {
			beforeload : function() {
				store.baseParams = {
					sheet_id : metadata_id
				}
			}
		},
		fields : ['xls_col_id', 
		          'xls_col_name', 
		          'xls_col_label',
		          'is_must_input',
				  'tableColumn.is_pk', 
				  'tableColumn.column_id',
				  'tableColumn.column_name', 
				  'tableColumn.data_type_cd',
				  'formula_expr', 
				  'display_order', 
				  'default_value', 
				  'dimSource.dim_cd', 
				  'dimSource.dim_name']
	});
	
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
		header : 'Excel列ID',
		dataIndex : 'xls_col_id',
		hidden : true
	}, {
		header : 'Excel列名称',
		dataIndex : 'xls_col_name'
	}, {
		header : 'Excel列标签',
		dataIndex : 'xls_col_label'
	}, {
		header : '是否主键',
		hidden:true,
		dataIndex : 'tableColumn.is_pk',
		width : 40,
		renderer : function(val) {
			if (val == 'Y')
				return "是";
			else if (val == 'N')
				return "否";
			return val;
		}
	}, {
		header : '表列ID',
		dataIndex : 'tableColumn.column_id',
		hidden : true
	}, {
		header : '表列名称',
		width : 40,
		dataIndex : 'tableColumn.column_name'
	}, {
		header : '数据类型',
		dataIndex : 'tableColumn.data_type_cd',
		width : 40,
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
		header : '是否必输',
		width : 40,
		dataIndex : 'is_must_input',
		renderer : function(val) {
			if (val == 'Y')
				return "是";
			else if (val == 'N')
				return "否";
			return val;
		}
	}, {
		header : '维度',
		width : 40,
		dataIndex : 'dimSource.dim_name'
	}, {
		header : '公式表达式',
		width : 60,
		dataIndex : 'formula_expr'
	}, {
		header : '显示顺序',
		hidden:true,
		width : 35,
		dataIndex : 'display_order'
	}, {
		header : '默认值',
		width : 30,
		dataIndex : 'default_value'
	}]);
	
	var menu = [{
		text : '添加(a)',
		iconCls : 'add',
		handler : function() {
			addSheetColumn(metadata_id, store);
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
			editSheetColumn(record.get('xls_col_id'), store);
		}
	}, '-', {
		text : '删除(d)',
		iconCls : 'delete',
		handler : function() {
			var record = gridPanel.getSelectionModel().getSelections()[0];
			if(record == null) {
				Ext.MessageBox.alert("提示信息", "请选择一条要删除的记录!");
				return;
			}
			Ext.MessageBox.confirm("确认信息", "是否删除选中记录?", function(btn) {
				if (btn == 'yes')
					deleteSheetColumn(record.get('xls_col_id'), store);
			});
		}
	},'-',{
		text : '调整排序',
		iconCls : 'revoke',
		handler : function() {
			if(store.getCount()>0){
				showOrderWin(store);
			}
		}
	}];
	
	var gridPanel = new Ext.grid.GridPanel({
		region : 'center',
		title : "Excel列字段",
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
		layout : 'border',
		border : false,
		items : [infoPanel, gridPanel]
	});
	store.load();
});
