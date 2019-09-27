var formItemAnchor = '91%';

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
/***
 * 业务类型
 */
var biz_type_store = new Ext.data.JsonStore({
	url : pathUrl + '/managerFnMdPro/listBizType',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['col_biz_type_cd', 'col_biz_type_desc'],
	autoLoad :true
});

//调整排序所用的字段
var column_ds = new Ext.data.JsonStore({
	url : pathUrl + '/metadata/pageField/getFieldsForDisOrder',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['field_id', 'field_name', 'component_label']
});
var column_cm = [
    new Ext.grid.RowNumberer(),
	{
		header : '字段名',
		dataIndex : 'field_name',
		width:160
	}, {
		header : '字段描述',
		dataIndex : 'component_label',
		width:160
	}
];

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

/***
 * 添加表单字段的方法
 * @param parentNode
 * @param md_cate_cd
 */
function addPageField(){
	
	var formPanel = new Ext.form.FormPanel({
		url : pathUrl + '/metadata/pageField/add',
		layout : 'column',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
			columnWidth : '0.5',
			layout : 'form',
			items : [ {
				xtype : 'combo',
				fieldLabel : '关联表字段',
				name:'rela_table_column',
				id:'relaTableColumn',
				displayField : 'column_name',
				valueField : 'column_id',
				editable : false,
				hiddenName : 'rela_table_column',
				mode : 'local',
				triggerAction : 'all',
				store :rela_table_col_store,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'field_name',
				name : 'field_name',
				fieldLabel : '字段名称',
				allowBlank : false,
				anchor : formItemAnchor
			}, {
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
				value:'textfield',
				listeners:{
					select:function(combo,record,index){
						var v = this.getValue();
						setAbledStatus(v);
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
				fieldLabel : '最小值',
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
				anchor : formItemAnchor,
				value:'01'
			}, {
				xtype : 'textfield',
				id : 'component_label',
				name : 'component_label',
				fieldLabel : '控件标签',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'combo',
				fieldLabel : '是否可编辑',
				id:'isEditable',
				name:'is_editable',
				displayField : 'text',
				valueField : 'id',
				hiddenName : 'is_editable',
				mode : 'local',
				value : 'Y',
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
				value : 'N',
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
				value : 'N',
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
				value : 'N',
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
				value : 'N',
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
				allowBlank : false,
				anchor : formItemAnchor
			}]
		}
		]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields();
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addMetadataWindow = new Ext.Window({
		layout : 'fit',
		title : '添加表单字段',
		buttonAlign : 'center',
		modal : true,
		width : 650,
		height :400,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addMetadataWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						params:{
							page_id:metadata_id,
							field_list_id:field_list_id//如果存在字段列表ID 则直接使用该参数
						},
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addMetadataWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							pageFieldStore.load();
							window.parent.refreshNode();
						},
						failure:function(form, action) {
							Ext.Msg.alert('提示信息',action.result.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addMetadataWindow.destroy();
			}
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
	
	var rela_table_id = Ext.getCmp('rela_table_id').getValue();
	if(isReConfigRelaTable){
		Ext.Msg.alert('提示信息','请先保存新配置的关联表');
		return;
	}else{
		
		if(md_cate_cd == window.parent.category_type_page){
			rela_table_col_store.load({
				params:{
					page_struct_id:metadata_id,
					rela_table_id:rela_table_id
				}
			})
		}else if(md_cate_cd == window.parent.category_type_colfield_list){
			rela_table_col_store.load({
				params:{
					field_list_id:metadata_id
				}
			})
		}
		
	}
	
	addMetadataWindow.show();
}

function setAbledStatus(type){
	if(type=='combobox'||type=='uxgrid'||type=='uxtree'){
		Ext.getCmp('dimCd').setDisabled(false);
		Ext.getCmp('max_length').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
	}else if(type=='textfield'||type=='textarea'||type=='datefield'||type=='hidden'||type=='moneyfield'||type=='numberfield'){
		Ext.getCmp('dimCd').setDisabled(true);
		Ext.getCmp('max_length').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
	}else if(type==null||type==''){
		Ext.getCmp('dimCd').setDisabled(true);
		Ext.getCmp('max_length').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
	}else{
		Ext.getCmp('dimCd').setDisabled(true);
		Ext.getCmp('max_length').setDisabled(true);
		Ext.getCmp('max_value').setDisabled(true);
		Ext.getCmp('min_value').setDisabled(true);
	}
}

/**
 * 返回通用的元数据表单项
 */
function getMetadataFormFields() {
	var fields = new Array();
	fields = [{
		xtype : 'hidden',
		name : 'md_cate_cd',
		value : window.parent.category_type_colfield
	},{
		xtype : 'hidden',
		name : 'status_cd',
		value : '02'
	},{
		xtype : 'hidden',
		name : 'is_display',
		value : 'Y'
	}];
	return fields;
}

/**
 * 点击编辑按钮
 */
function editPageField(field_id){
	
	var formPanel = new Ext.form.FormPanel({
		url : pathUrl + '/metadata/pageField/save',
		layout : 'column',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'field_id'}, 
			{name : 'field_name'}, 
			{name : 'component_label'}, 
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
			items : [ {
				xtype : 'combo',
				fieldLabel : '关联表字段',
				name:'rela_table_column',
				id:'relaTableColumn',
				displayField : 'column_name',
				valueField : 'column_id',
				editable : false,
				hiddenName : 'rela_table_column',
				mode : 'local',
				triggerAction : 'all',
				store :rela_table_col_store,
				anchor : formItemAnchor
			},{
				xtype : 'hidden',
				id : 'field_id',
				name : 'field_id',
				fieldLabel : '字段ID',
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'field_name',
				name : 'field_name',
				fieldLabel : '字段名称',
				allowBlank : false,
				anchor : formItemAnchor
			}, {
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
				anchor : formItemAnchor
			}, {
				xtype : 'textfield',
				name : 'min_value',
				id : 'min_value',
				fieldLabel : '最小值',
				anchor : formItemAnchor
			}, {
				xtype : 'textfield',
				name : 'max_length',
				id : 'max_length',
				fieldLabel : '最大长度',
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
				xtype : 'textfield',
				id : 'component_label',
				name : 'component_label',
				fieldLabel : '控件标签',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'combo',
				fieldLabel : '是否可编辑',
				id:'isEditable',
				name:'is_editable',
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
		]
	});
	 
	
	var updateMetadataWindow = new Ext.Window({
		layout : 'fit',
		title : '编辑表单字段',
		buttonAlign : 'center',
		modal : true,
		width : 650,
		height :400,
		items : [formPanel],
		listeners : {
			'close' : function(){
				updateMetadataWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						params:{
							page_id:metadata_id
						},
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							updateMetadataWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							pageFieldStore.load();
							window.parent.refreshNode();
						},
						failure:function(form, action){
							Ext.MessageBox.alert('提示信息',	action.result.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				updateMetadataWindow.destroy();
			}
		}]
	});
	
	var rela_table_id = Ext.getCmp('rela_table_id').getValue();
	if(isReConfigRelaTable){
		Ext.Msg.alert('提示信息','请先保存新配置的关联表');
		return;
	}else{
		//如果选择的页面节点  则给页面参数赋值
		rela_table_col_store.load({
			params:{
				field_id:field_id
			}
		})
	}
	
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
	updateMetadataWindow.show();
	
	//获取选中记录字段ID
	formPanel.form.load({
		url : pathUrl + '/metadata/pageField/load',
		params : {
			field_id : field_id
		},
		success:function(){
			Ext.getCmp('componentTypeId').fireEvent('select');
		}
	});
}

/**
 * 获取删除参数 
 * @param records
 * @returns {String}
 */
function getDelFieldsParams(records){
	if(records.length == 0){
		return '';
	}
	var delParams = '';
	for(var i = 0;i<records.length;i++){
		if(i == records.length-1){
			delParams = delParams +records[i].get('field_id') ;
		}else{
			delParams = delParams +records[i].get('field_id') + ',';
		}
	}
	return delParams;
}

//删除
function deletePageField(records,field_id){
	var delParams = getDelFieldsParams(records);
	Ext.Ajax.request({
		url:pathUrl + '/metadata/pageField/deleteBatch',
		params : {
			del_params: delParams
		},
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			if(json.success){
				Ext.Msg.alert('提示信息','删除成功');
				pageFieldStore.load();
				window.parent.refreshNode();
			}else{
				Ext.Msg.alert('提示信息',json.result.info);
			}
		}
	});
	
}

/**
 * 同步表字段与页面字段
 * @param {} 
 * @param {} 
 */
function syncTableField() {
	if(isReConfigRelaTable){
		Ext.Msg.alert('提示信息','已重置关联表，请先保存');
		return;
	}
	
	var rela_table_id = '';
	if(Ext.getCmp('rela_table_id')){
		rela_table_id = Ext.getCmp('rela_table_id').getValue();
	} 
	
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + "/metadata/pageStruct/syncPageRelaTable",
		method : 'POST',
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		items : [{
			xtype : 'hidden',
			name : 'md_cate_cd',
			value : md_cate_cd
		},{
			xtype : 'hidden',
			name : 'metadata_id',
			value : metadata_id
		}, {
			xtype : 'hidden',
			name : 'field_list_id',
			value : field_list_id
		},{
			xtype : 'hidden',
			name : 'sync_type',
			id : 'sync_type',
			value : '01'
		}, {
			xtype : 'fieldset',
			layout : 'form',
			bodyStyle : 'padding: 5px 5px 5px 25px',
			anchor : '100%',
			title : '同步方式',
			items : [{
				xtype : 'checkbox',
				boxLabel : '追加新字段',
				hideLabel : true,
				id : 'overwriteCheck',
				checked : true,
				listeners : {
					'check' : function(ckbox, checked) {
						if (checked) {
							Ext.getCmp("appendCheck").setValue(false);
							Ext.getCmp("sync_type").setValue("01");
							var info = "不会更新已有页面字段,仅添加没有关联的记录";
							document.getElementById("syncInfo").innerHTML = info;
						}
					}
				}
			}, {
				xtype : 'checkbox',
				boxLabel : '覆盖当前字段',
				hideLabel : true,
				id : 'appendCheck',
				listeners : {
					'check' : function(ckbox, checked) {
						if (checked) {
							Ext.getCmp("overwriteCheck").setValue(false);
							Ext.getCmp("sync_type").setValue("02");
							var info = "删除所有页面字段,然后将关联表中所有字段重新初始化到页面字段列表中";
							document.getElementById("syncInfo").innerHTML = info;
						}
					}
				}
			}]
		}, {
			xtype : 'panel',
			id : 'syncInfoPanel',
			html : '<hr/><div id="syncInfo" style="color:red;font-size:12px;">不会更新已有页面字段,仅添加没有关联的记录</div>'
		}]
	});
	
	var win = new Ext.Window({
		layout : 'fit',
		title : '同步关联表字段与页面字段',
		buttonAlign : 'center',
		modal : true,
		width : 340,
		height : 260,
		items : [formPanel],
		listeners : {
			'close' : function(){
				win.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						params:{
							rela_table_id:rela_table_id
						},
						success : function(form, action) {
							win.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							pageFieldStore.load();
							window.parent.refreshNode();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示信息", action.result.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				win.destroy();
			}
		}]
	});
	
	win.show();
}


//调整排序

function showOrderWin(field){
	var gridPanel = new Ext.grid.GridPanel ({
		columns : column_cm,
		store : column_ds,
		enableDragDrop:true, 
		dropConfig:{appendOnly:true},
		ddGroup:'GridDD',
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		tbar:[ 
		  {
	    	 text:'上移',
	    	 iconCls:'arrow-up',
	    	 handler:function(){
	    		 var rows = gridPanel.getSelectionModel().getSelections();  
	    		 if(!rows||!rows.length>0){
	    			 Ext.Msg.alert('提示','请选中一行数据！');
	    			 return;
	    		 }
		    	 var i = column_ds.indexOf(rows[0]); 
	    		 sortStore(rows,i==0?0:i-1,gridPanel);
	    	 }
	     },
	     {
	    	 text:'下移',
	    	 iconCls:'arrow-down',
	    	 handler:function(){ 
		    	 var rows = gridPanel.getSelectionModel().getSelections();  
		    	 if(!rows||!rows.length>0){
	    			 Ext.Msg.alert('提示','请选中一行数据！');
	    			 return;
	    		 }
		    	 var i = column_ds.indexOf(rows[0]); 
		    	 if(i == column_ds.getCount()-1){//最底部 不进行移动
		    		 return;
		    	 }
	    		 sortStore(rows,i+rows.length,gridPanel);
	    	 }
	     },
	     {
	    	 text:'移到顶部',
	    	 iconCls:'arrow-up',
	    	 handler:function(){
	    		 var rows = gridPanel.getSelectionModel().getSelections(); 
	    		 if(!rows||!rows.length>0){
	    			 Ext.Msg.alert('提示','请选中一行数据！');
	    			 return;
	    		 }
	    		 sortStore(rows,0,gridPanel);
	    	 }
	     },
	     {
	    	 text:'移到底部',
	    	 iconCls:'arrow-down',
	    	 handler:function(){ 
		    	 var rows = gridPanel.getSelectionModel().getSelections();
	    		 if(!rows||!rows.length>0){
	    			 Ext.Msg.alert('提示','请选中一行数据！');
	    			 return;
	    		 }  
	    		 sortStore(rows,column_ds.getCount()-1,gridPanel);
	    	 }
	     }
		 ]
     });
	var orderWin = new Ext.Window({
		title:'字段排序（隐藏【隐藏域】类型的控件）',
		buttonAlign : 'center',
		id : 'addWindow',
		width : 400,
		height : 500,
		layout:'fit',
		modal : true,
		items:[
		       gridPanel
		],
		buttons:[
		      {
		    	  text:'确定',
		    	  handler:function(){
		    		  updateDisOrder(column_ds,field);
		    		  orderWin.destroy();
		    	  }
		      },{
		    	  text:'取消',
		    	  handler:function(){
		    		  orderWin.destroy();
		    	  }
		      }
		 ]
	});
	orderWin.on('beforedestroy',function(){
		 Ext.dd.ScrollManager.unregister(gridPanel.getView().getEditorParent());
	});
	orderWin.show();
	var dt = new Ext.dd.DropTarget(gridPanel.container,{
		ddGroup:'GridDD',
		copy:false,
		notifyDrop:function(dd, e, data){
			  var rows = data.selections;  
	          var index = dd.getDragData(e).rowIndex;  
	          sortStore(rows,index,gridPanel);
		}
	});
	Ext.dd.ScrollManager.register(gridPanel.getView().getEditorParent());
	var prt_metadata_id = '';
	
	column_ds.load({
		params:{
			prt_metadata_id:field_list_id?field_list_id:prt_metadata_id,//如果有字段列表ID 则直接将字段列表ID设置为父级参数
			page_id:metadata_id
		},
		callback:function(){
			var index = column_ds.findBy(function(r){
				if(r.get('field_id')== metadata_id){
					return true;
				}else{
					return false;
				}
			});
			 var r =  column_ds.getAt(index);
			 gridPanel.getSelectionModel().selectRow(index);
		     gridPanel.getView().focusRow(index);
		}
	});
}
function updateDisOrder(store){
	var params = "";
	if(store&&store.getCount()>0){
		for ( var int = 0; int < store.getCount(); int++) {
			var record = store.getAt(int);
			params +=record.get("field_id")+','+(store.indexOf(record)+1)+';';
		}
	}
//	var metadata_id = Ext.getCmp('metadata_id').getValue();
	var index = store.findBy(function(r){
		if(r.get('field_id')==metadata_id){
			return true;
		}else{
			return false;
		}
	});
	
	//field.setValue(index+1);
	Ext.Ajax.request({
		   url:  pathUrl + '/metadata/pageField/updateDisOrder',
		   params:{orderParam : params},
		   method : 'POST',
		   callback: function(success,options,response){
			   var json = Ext.util.JSON.decode(response.responseText);
			   if(json.success){
				   Ext.Msg.alert('提示信息','排序成功');
				   pageFieldStore.load();
			   }else{
				   Ext.Msg.alert('提示信息',json.info);
			   }
		   },
		   failure: function(){}
	});
}
 function sortStore(rows,index,gridPanel){
	  if (typeof(index) == "undefined") {  
          return;  
      }  
      var gridIndex = column_ds.indexOf(rows[0]);  
      //
      if(index<=(gridIndex+rows.length-1)&&index>=gridIndex){
      	return;
      }
      //确定正序还是倒序  
      var mark = true;  
      if(index<gridIndex) 
      	mark = false;  
      var  selectArray = [];
      for(i = 0; i < rows.length; i++) {  
       var rowData;  
          if(mark){  
              rowData = rows[i];  
           }else{  
              rowData = rows[rows.length-i-1];  
          }  
          if(!this.copy)   
          	column_ds.remove(rowData);  
          column_ds.insert(index, rowData); 
          selectArray.push( column_ds.indexOf(rowData));
      } 
      gridPanel.getSelectionModel().selectRows(selectArray);
      gridPanel.getView().refresh();//刷新序号    
      gridPanel.getView().focusRow(selectArray[0]);
 }
