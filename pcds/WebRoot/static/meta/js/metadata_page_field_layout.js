var formItemAnchor = '91%';

var biz_type_store = new Ext.data.JsonStore({
	url : pathUrl + '/managerFnMdPro/listBizType',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['col_biz_type_cd', 'col_biz_type_desc']
});

/***
 * 关联表字段
 */
var rela_table_col_store = new Ext.data.JsonStore({
	url : pathUrl + '/metadata/pageField/queryRelaTableCols',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['column_id', 'column_name'],
	autoLoad :false
});

//控件类型
var component_array = [
    ['textfield','文本框'],
    ['numberfield','数字框'],
    ['textarea','文本域'],
    ['combobox','下拉框'],
    ['moneyfield','金额框'],
    ['datefield','日期框'],
    ['custfield','分配框'],
    ['uxtree','下拉树'],
    ['hidden','隐藏域']
];



Ext.onReady(function(){
	
	rela_table_col_store.load({
		params:{
			field_id:metadata_id
		}
	})

	biz_type_store.load({
		params:{
			field_id:metadata_id
		}
	})
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		title : '页面字段属性',
		url : pathUrl + "/metadata/pageField/save",
		method : 'POST',
		border : true,
		split : true,
		frame : true,
		labelWidth : 100,
		labelAlign : 'left',
		bodyStyle : 'padding: 0px 25px 5px 25px',
		autoScroll : true,
		buttonAlign : 'center',
		layout:'column',
		itemCls  : 'uxHeight',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'field_id'}, 
			{name : 'field_name'}, 
			{name : 'component_label'} ,
			{name : 'component_type_id'}, 
			{name : 'dim_cd'}, 
			{name : 'default_value'}, 
			{name : 'col_biz_type_cd'}, 
			{name : 'is_hidden'},
			{name : 'display_order'},
			{name : 'is_editable'},
			{name : 'is_must_input'},
			{name : 'is_pk'},
			{name : 'max_value'},
			{name : 'min_value'},
			{name : 'max_length'},
			{name : 'is_query_cond'},
			{name : 'rela_table_column'}
			
		]),
		items : [{
			columnWidth : '0.5',
			layout : 'form',
			items : [{
				xtype : 'textfield',
				id : 'field_id',
				name : 'field_id',
				readOnly : true,
				fieldLabel : '字段ID',
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'field_name',
				name : 'field_name',
				fieldLabel : '字段名称',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'component_label',
				name : 'component_label',
				fieldLabel : '控件标签',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'combo',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					data : component_array
				}),
				displayField : 'text',
				valueField : 'id',
				fieldLabel : '控件类型',
				id:'componentTypeId',
				name:'component_type_id',
				editable : false,
				hiddenName : 'component_type_id',
				mode : 'local',
				triggerAction : 'all',
				listeners:{
					select:function(){
						var v = this.getValue();
						setAbledStatus(v)
						if(v == 'combobox' || v == 'uxtree') {
							myDim.loadDimData((v == 'uxtree'));
						} else {
							myDim.clearValue();
						}
					}
				},
				anchor : formItemAnchor
			},myDim = new UppDimSourceSelector() , {
				xtype : 'textfield',
				name : 'default_value',
				id : 'default_value',
				fieldLabel : '默认值',
				anchor : formItemAnchor
			}, {
				xtype : 'textfield',
				name : 'max_value',
				id : 'max_value',
				fieldLabel : '最大值',
				allowBlank : false,
				anchor : formItemAnchor
			}, {
				xtype : 'textfield',
				name : 'min_value',
				id : 'min_value',
				fieldLabel : '最小值111',
				allowBlank : false,
				anchor : formItemAnchor
			}, {
				xtype : 'textfield',
				name : 'max_length',
				id : 'max_length',
				fieldLabel : '最大长度',
				allowBlank : false,
				anchor : formItemAnchor
			}
			]
		}, {
			columnWidth : '0.5',
			layout : 'form',
			items : [ {
				xtype : 'combo',
				fieldLabel : '业务类型',
				name:'col_biz_type_cd',
				id:'colBizTypeCd',
				displayField : 'col_biz_type_desc',
				valueField : 'col_biz_type_cd',
				editable : false,
				hiddenName : 'col_biz_type_cd',
				mode : 'local',
				triggerAction : 'all',
				store :biz_type_store,
				anchor : formItemAnchor
			}, {
				xtype : 'combo',
				fieldLabel : '关联表字段',
				id:'relaTableColumn',
				displayField : 'column_name',
				valueField : 'column_id',
				editable : false,
				name:'rela_table_column',
				hiddenName : 'rela_table_column',
				mode : 'local',
				triggerAction : 'all',
				store :rela_table_col_store,
				anchor : formItemAnchor
			},{
				xtype : 'combo',
				fieldLabel : '是否可编辑',
				id:'isEditable',
				name:'is_editable',
				editable:false,
				displayField : 'text',
				valueField : 'id',
				hiddenName : 'is_editable',
				mode : 'local',
				triggerAction : 'all',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					data : [['Y', '是'], ['N', '否']]
				}),
				anchor : formItemAnchor
			},{
				xtype : 'combo',
				fieldLabel : '是否必输',
				id:'isMustInput',
				name:'is_must_input',
				displayField : 'text',
				valueField : 'id',
				editable : false,
				hiddenName : 'is_must_input',
				mode : 'local',
				triggerAction : 'all',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					data : [['Y', '是'], ['N', '否']]
				}),
				anchor : formItemAnchor
			},{
				xtype : 'combo',
				fieldLabel : '是否主键',
				name:'is_pk',
				id:'isPk',
				displayField : 'text',
				valueField : 'id',
				editable : false,
				hiddenName : 'is_pk',
				mode : 'local',
				triggerAction : 'all',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					data : [['Y', '是'], ['N', '否']]
				}),
				anchor : formItemAnchor
			}, {
				xtype : 'combo',
				fieldLabel : '是否隐藏',
				id:'isHidden',
				name:'is_hidden',
				displayField : 'text',
				valueField : 'id',
				editable : false,
				hiddenName : 'is_hidden',
				mode : 'local',
				triggerAction : 'all',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					data : [['Y', '是'], ['N', '否']]
				}),
				anchor : formItemAnchor
			}, {
				xtype : 'combo',
				fieldLabel : '是否查询条件',
				id:'isQueryCond',
				name:'is_query_cond',
				displayField : 'text',
				valueField : 'id',
				editable : false,
				hiddenName : 'is_query_cond',
				mode : 'local',
				triggerAction : 'all',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					data : [['Y', '是'], ['N', '否']]
				}),
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				name : 'display_order',
				fieldLabel : '显示顺序',
				anchor : formItemAnchor
			}]
		}
		],
		buttons : [{
			text : '保存',
			handler : function(){
				savePageField(infoPanel);
			}
		},{
			text : '删除',
			handler : function(){
				Ext.MessageBox.confirm("确认信息", "是否删除该页面字段信息?", function(btn) {
					if (btn == 'yes'){
						deletePageField();
					}
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
	
	Ext.getCmp("relaTableColumn").on('select',function(combo, record, index){
		//获取字段名和描述 分别赋值到 表单中
		var fieldStr = record.get('column_name');
		var fieldStrArr = fieldStr.split(' ');
		var columnName = fieldStrArr[0];
		var columnDesc = '';
		if(fieldStrArr.length > 1){
			columnDesc = fieldStrArr[1];
		}
		Ext.getCmp('field_name').setValue(columnName);
		Ext.getCmp('component_label').setValue(columnDesc);
	
	})
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/pageField/load',
		params : {
			field_id : metadata_id
		},
		success:function(){
			var type = Ext.getCmp('componentTypeId').getValue();
			setAbledStatus(type);
			reLoadDimCdStore(type);
		}
	});
});

