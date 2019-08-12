/***
 * 关联元数据数据源
 */
var rela_metadata_store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:pathUrl+'/metadata/pageRelaMetadata/queryRelaMetadata'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'rela_metadata_id',
			mapping : 'rela_metadata_id'
		}, {
			name : 'rela_metadata_name',
			mapping : 'rela_metadata_name'
		}]),
		remoteSort : false,
		autoLoad :true
	});
/**
 * 图标下拉框
 */
var icon_path_store = new Ext.data.JsonStore({
	url:pathUrl+'/metadata/pageButton/queryIcons',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['icon_path','icon_desc'],
	autoLoad :true
});
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
    ['hidden','隐藏域'],
    ['add','增加按钮'],
    ['delete','删除按钮'],
    ['edit','编辑按钮'],
    ['update','更新按钮'],
    ['load','加载按钮'],
    ['import','导入按钮'],
    ['export','导出按钮'],
    ['query','查询按钮']
                       
];

/***
 * 添加页面的方法
 * @param parentNode
 * @param md_cate_cd
 */
function addPageStruct(parentNode, md_cate_cd,cateDesc){
	var url ='';
	if(md_cate_cd == category_type_page){
		url = pathUrl + '/metadata/pageStruct/add';
	}else{
		url = pathUrl + '/metadata/pageStruct/addSimplePageStruct';
	}
	
	var formItemAnchor = '91%';
	
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : url,
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
				xtype : 'textfield',
				name : 'page_struct_name',
				fieldLabel : cateDesc+'名称',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'hidden',
				id : 'rela_table_id',
				name : 'rela_table_id',
				fieldLabel : '关联表',
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'rela_table_name',
				name : 'rela_table_name',
				fieldLabel : '关联表',
				readOnly:true,
				anchor : formItemAnchor
			},{
				xtype : 'textarea',
				name : 'page_struct_desc',
				fieldLabel : cateDesc+'描述',
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				name : 'display_order',
				fieldLabel : '显示顺序',
				anchor : formItemAnchor
			}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addPageWindow = new Ext.Window({
		layout : 'fit',
		title : '添加'+cateDesc,
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 240,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addPageWindow.destroy();
			},
			afterrender:function(){
				var imgPath = pathUrl+'/public/images/icons/change.png';
				var div = Ext.getDom('rela_table_name').parentNode;
				var span = document.createElement("span");
				span.style.border = "1px solid #B5B8C8";
				span.style.padding = "1px 1px 1px 1px";
				span.style.margin = "0px 0px 0px 2px";
				span.style.verticalAlign = "MIDDLE";
				span.innerHTML = "<a href='javascript:showConfigPageTableTree()'><img src=\""+imgPath+"\"></a>";
				div.appendChild(span);
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if(!Ext.getCmp('rela_table_id').getValue()){
					Ext.Msg.alert('提示信息','请输入关联表。');
					return ;
				}
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addPageWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addPageWindow.destroy();
			}
		}]
	});
	
	addPageWindow.show();
}

/***
 * 添加简单页面结构的方法
 * @param parentNode
 * @param md_cate_cd
 */
function addSimplePageStrut(parentNode, md_cate_cd,cateDesc){
	var url ='';
	if(md_cate_cd == category_type_page){
		url = pathUrl + '/metadata/pageStruct/add';
	}else{
		url = pathUrl + '/metadata/pageStruct/addSimplePageStruct';
	}
	
	var formItemAnchor = '91%';
	
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : url,
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
				xtype : 'textfield',
				name : 'page_struct_name',
				fieldLabel : cateDesc+'名称',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textarea',
				name : 'page_struct_desc',
				fieldLabel : cateDesc+'描述',
				anchor : formItemAnchor
			}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addPageWindow = new Ext.Window({
		layout : 'fit',
		title : '添加'+cateDesc,
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 240,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addPageWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addPageWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addPageWindow.destroy();
			}
		}]
	});
	
	addPageWindow.show();
}


/***
 * 添加简单元数据的方法
 * @param parentNode
 * @param md_cate_cd
 */
function addSimpleMetadata(parentNode, md_cate_cd,metadata_name,metadata_desc){
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/addMetadata',
		border : true,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [{
				xtype : 'textfield',
				name : 'metadata_name',
				fieldLabel : metadata_name+'名称',
				allowBlank : false,
				anchor : '91%'
			},{
				xtype : 'textfield',
				name : 'metadata_desc',
				fieldLabel : metadata_name+'描述',
				allowBlank : false,
				anchor : '91%'
			}]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addMetadataWindow = new Ext.Window({
		layout : 'fit',
		title : '添加'+metadata_name,
		buttonAlign : 'center',
		modal : true,
		width : 420,
		height : 140,
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
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addMetadataWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
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
	
	addMetadataWindow.show();
}

/***
 * 添加按钮的方法(工具条按钮 表单按钮)
 * @param parentNode
 * @param md_cate_cd
 */
function addToolbarButton(parentNode, md_cate_cd){
	var formItemAnchor = '91%';
	var curr_func_code = '';
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/pageButton/add',
		border : true,
		split : true,
		frame : true,
		labelWidth : 100,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [
					{
						xtype : 'textfield',
						id : 'button_name',
						name : 'button_name',
						fieldLabel : '按钮名称',
						anchor : formItemAnchor,
						allowBlank : false
					},new UppButtonFunctionSelector(),{
						xtype:'combo',
						fieldLabel:'显示图标',
						editable: false,
						id:'iconPathCombo',
						valueField :'icon_path',
						displayField: 'icon_desc',
						store: icon_path_store,
						mode: 'local',
						hiddenName:'icon_path',
						triggerAction: 'all',
						value:' ',
						anchor : formItemAnchor
					},{
						xtype:'combo',
						fieldLabel:'关联表单',
						editable: false,
						hidden: (md_cate_cd != category_type_toolbar_button),
						id:'relaMetadataId',
						valueField :'rela_metadata_id',
						displayField: 'rela_metadata_name',
						store: rela_metadata_store,
						mode: 'local',
						hiddenName:'rela_metadata_id',
						triggerAction: 'all',
						anchor : formItemAnchor
					},{
						xtype:'combo',
						fieldLabel:'是否自定义SQL',
						editable: false,
						id:'isCustomerSql',
						valueField :'retrunValue',
						displayField: 'displayText',
						store: new Ext.data.SimpleStore({
									fields: ['retrunValue', 'displayText'],
									data: [['Y','是'],['N','否']]
								}),
						value:'Y',
						mode: 'local',
						hiddenName:'is_customer_sql',
						triggerAction: 'all',
						anchor : formItemAnchor,
						allowBlank:false,
						listeners:{
							select:function(combo, record, index){
								var value = record.get('retrunValue');
								if(value == 'Y'){
									Ext.getCmp('dml_sql').setVisible(true);
									
								}else{
									Ext.getCmp('dml_sql').setVisible(false);
								}
							}
						}
					},{
						xtype : 'textarea',
						height:150,
						name : 'dml_sql',
						id   : 'dml_sql',
						anchor : formItemAnchor,
						fieldLabel : 'DML语句'
					}
				]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addToolbarButtonWindow = new Ext.Window({
		layout : 'fit',
		title : '添加按钮',
		buttonAlign : 'center',
		modal : true,
		width : 650,
		height :400,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addToolbarButtonWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addToolbarButtonWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addToolbarButtonWindow.destroy();
			}
		}]
	});
	
	//工具条按钮才显示关联元数据
	if(md_cate_cd == category_type_toolbar_button){
		Ext.getCmp('buttonFuncCd').on('select',function(){
			//新增 编辑
			var func_code = this.getValue();
			if(isSameFuncCd(curr_func_code,func_code)){
				curr_func_code = func_code;
				return;
			}
			curr_func_code = func_code;
			var rela_md_cate_cd = getRelaMdCateCode(func_code);
			var prt_metadata_id = '';
			if(rela_md_cate_cd == category_type_excel){
				prt_metadata_id = '30';
			}else {
				prt_metadata_id = parentNode.parentNode.id;
			}
			
			configRelaMetadata(rela_md_cate_cd,prt_metadata_id);
			
		});
		
		Ext.getCmp('buttonFuncCd').getStore().on('load',function(){
			curr_func_code = Ext.getCmp('buttonFuncCd').getValue();
		});
	}
	
	addToolbarButtonWindow.show();
	//获取关联元数据
	configRelaMetadata('FRM',parentNode.parentNode.id);
	
}

/***
 * 关联元数据的的配置
 * @param md_cate_cd
 * @param prt_metadata_id
 */
function configRelaMetadata(rela_md_cate_cd,prt_metadata_id){
	Ext.getCmp('relaMetadataId').getStore().removeAll();
	Ext.getCmp('relaMetadataId').setValue('');
	Ext.getCmp('relaMetadataId').getStore().load({
		params:{
			md_cate_cd:rela_md_cate_cd,
			prt_metadata_id:prt_metadata_id
		}
	});
}


/**
 * 获取关联元数据cate_code 按钮
 * @param func_code
 * @returns {String}
 */
function getRelaMdCateCode(func_code){
	var md_cate_cd = '';
	if(func_code == '01' || func_code == '02'){//新增修改 按钮对应表单
		md_cate_cd = 'FRM';
	}else if(func_code == '07' || func_code == '08'){//导入导出对应EXCEL
		md_cate_cd = 'XLS';
	}
	return md_cate_cd;
}
 

/***
 * 判断所选功能是否为相似
 * @param curr_func_code
 * @param func_code
 * @returns {Boolean}
 */
function isSameFuncCd(curr_func_code,func_code){
	
	if(curr_func_code == func_code){//重复点击 不做处理
		return true;
	}
	
	//对于选择相似的功能 不进行重置
	if((curr_func_code == '01' || curr_func_code == '02') && 
	   (func_code == '01' || func_code == '02')){
		return true;
	}
	
	if((curr_func_code == '07' || curr_func_code == '08') && 
	   (func_code == '07' || func_code == '08') ){
		return true;
	}
	
	return false;
}


/***
 * 添加表单字段的方法
 * @param parentNode
 * @param md_cate_cd
 */
function addPageField(parentNode, md_cate_cd){
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
	
	var formItemAnchor = '91%';
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
				xtype : 'textfield',
				id : 'component_label',
				name : 'component_label',
				fieldLabel : '控件标签',
				allowBlank : false,
				anchor : formItemAnchor
			},new UppDimSourceSelector() , {
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
				anchor : formItemAnchor,
				value:'01'
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
					select:function(){
						var v = this.getValue();
						setAbledStatus(v)
					}
				},
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
				editable : false,
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
				anchor : formItemAnchor
			}]
		}
		]
	});
	
	//添加元数据表单项目
	var fields = getMetadataFormFields(parentNode, md_cate_cd);
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
						waitMsg : '正在处理,请稍后......',
						params:{
							field_list_id:parentNode.id
						},
						success : function(form, action) {
							addMetadataWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							refreshNode();
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
	
	});
	
	rela_table_col_store.load({
		params:{
			field_list_id:parentNode.id
		}
	})
	addMetadataWindow.show();
}

function setAbledStatus(type){
	if(type=='combobox'||type=='uxgrid'||type=='uxtree'){
		Ext.getCmp('dimCd').setDisabled(false);
		Ext.getCmp('default_value').setDisabled(false);
		Ext.getCmp('max_length').setDisabled(false);
		Ext.getCmp('isEditable').setDisabled(false);
		Ext.getCmp('isMustInput').setDisabled(false);
		Ext.getCmp('isPk').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
		Ext.getCmp('colBizTypeCd').setDisabled(false);
	}else if(type=='textfield'||type=='textarea'||type=='datefield'||type=='hidden'||type=='moneyfield'||type=='numberfield'){
		Ext.getCmp('dimCd').setDisabled(true);
		Ext.getCmp('default_value').setDisabled(false);
		Ext.getCmp('max_length').setDisabled(false);
		Ext.getCmp('isEditable').setDisabled(false);
		Ext.getCmp('isMustInput').setDisabled(false);
		Ext.getCmp('isPk').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
		Ext.getCmp('colBizTypeCd').setDisabled(false);
	}else if(type==null||type==''){
		Ext.getCmp('dimCd').setDisabled(true);
		Ext.getCmp('default_value').setDisabled(false);
		Ext.getCmp('max_length').setDisabled(false);
		Ext.getCmp('isEditable').setDisabled(false);
		Ext.getCmp('isMustInput').setDisabled(false);
		Ext.getCmp('isPk').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
		Ext.getCmp('colBizTypeCd').setDisabled(false);
	}else{
		Ext.getCmp('dimCd').setDisabled(true);
		Ext.getCmp('default_value').setDisabled(true);
		Ext.getCmp('max_length').setDisabled(true);
		Ext.getCmp('isEditable').setDisabled(true);
		Ext.getCmp('isMustInput').setDisabled(true);
		Ext.getCmp('isPk').setDisabled(true);
		Ext.getCmp('max_value').setDisabled(true);
		Ext.getCmp('min_value').setDisabled(true);
		Ext.getCmp('colBizTypeCd').setDisabled(true);
	}
}