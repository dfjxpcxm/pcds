var formItemAnchor = '91%';

Ext.onReady(function(){
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		title : '表属性',
	    region:'north',
	    height:190,
		layout : 'form',
		url : pathUrl + "/metadata/table/save",
		method : 'POST',
		border : true,
		split : false,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 0px 25px 5px 25px',
		autoScroll : true,
		buttonAlign : 'center',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'table_id'}, 
			{name : 'table_name'}, 
			{name : 'table_desc'}
		]),
		items : [{
				xtype : 'textfield',
				name : 'table_id',
				fieldLabel : '表ID',
				readOnly : true,
				itemCls  : 'uxHeight',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'table_name',
				itemCls  : 'uxHeight',
				name : 'table_name',
				fieldLabel : '表名',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				itemCls  : 'uxHeight',
				id : 'table_desc',
				name : 'table_desc',
				fieldLabel : '表描述',
				anchor : formItemAnchor
			}],
		buttons : [{
			text : '保存',
			handler : function(){
				saveTable(infoPanel);
			}
		},{
			text : '删除',
			handler : function(){
				Ext.MessageBox.confirm("确认信息", "是否删除该表信息?", function(btn) {
					if (btn == 'yes')
						deleteTable();
				});
			}
		}]
	});
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/table/load',
		params : {
			table_id : metadata_id
		}
	});
	
	var columnStore = new Ext.data.JsonStore({
		url : pathUrl + '/metadata/column/listTableColumns',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['column_id', 'column_name', 'column_desc', 'data_type_cd',
				'data_length', 'data_scale', 'is_pk', 'is_nullable','display_order'],
		listeners : {
			'beforeload' : function(){
				columnStore.baseParams = {
					table_id : metadata_id
				}
			}
		}
	});
	
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
		header : '列ID',
		dataIndex : 'column_id',
		hidden : true
	}, {
		header : '列名称',
		dataIndex : 'column_name'
	}, {
		header : '列描述',
		dataIndex : 'column_desc'
	}, {
		header : '数据类型',
		width : 50,
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
	}, {
		header : '数据长度',
		dataIndex : 'data_length',
		width : 40
	}, {
		header : '数据刻度',
		dataIndex : 'data_scale',
		width : 40
	}, {
		header : '是否主键',
		dataIndex : 'is_pk',
		width : 40,
		renderer : function(val) {
			if (val == 'Y')
				return "是";
			else if (val == 'N')
				return "否";
			return val;
		}
	}, {
		header : '是否可空',
		width : 40,
		dataIndex : 'is_nullable',
		renderer : function(val) {
			if (val == 'Y')
				return "是";
			else if (val == 'N')
				return "否";
			return val;
		}
	},{
		header : '顺序',
		dataIndex : 'display_order',
		width : 30
	}]);
	var bar = [{
		text : '新增',
		iconCls : 'add',
		handler : function() {
			addColumn(metadata_id, window.parent.category_type_column, columnStore);
		}
	},'-',{
		text : '编辑',
		iconCls : 'edit',
		handler : function() {
			var record = Ext.getCmp("gridPanel").getSelectionModel().getSelections()[0];
			if(record == null) {
				Ext.MessageBox.alert("提示信息","请选择一条列记录!");
				return
			}
			editColumn(record, columnStore);
		}
	},'-',{
		text : '删除',
		iconCls : 'delete',
		handler : function() {
			var record = Ext.getCmp("gridPanel").getSelectionModel().getSelections()[0];
			if(record == null) {
				Ext.MessageBox.alert("提示信息","请选择一条列记录!");
				return
			}
			
			Ext.MessageBox.confirm("确认信息", "是否删除选中的列字段?", function(btn) {
				if (btn == 'yes')
					deleteColumn(record.get('column_id'), columnStore);
			});
		}
	},'-',{
		text : '调整排序',
		iconCls : 'revoke',
		handler : function() {
			if(columnStore.getCount()>0){
				showOrderWin();
			}
		}
	},'-',{
		text : '同步',
		iconCls : 'refresh',
		handler : function() {
			syncTableField(metadata_id, columnStore);
		}
	}];
	var gridPanel = new Ext.grid.GridPanel({
		id : 'gridPanel',
        title:'表字段列表',
        region:'center',
	    autoScroll: true,
        loadMask: true,
		split:true,
		ds: columnStore,
		cm: cm,
		tbar: bar,
		viewConfig : {
			forceFit : true
		}
	});
	
	var viewport = new Ext.Viewport({
	    xtype : 'panel',
		layout : 'border',
		border : false,
		items:[
		       infoPanel,
		       gridPanel
		]
	});
	
	columnStore.load();
});

function getBlankPanel(height) {
	return {
		xtype : 'panel',
		height : height,
		border : false
	}
}
