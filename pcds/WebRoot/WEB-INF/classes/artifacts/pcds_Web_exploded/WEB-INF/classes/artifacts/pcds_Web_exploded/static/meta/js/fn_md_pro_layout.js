var metadata_type = '', isTree = '', tree = '', rootValue = '';

MyViewportUi = Ext.extend(Ext.Viewport, {
	layout : 'border',
	initComponent : function() {
		Ext.applyIf(this, {
			items : [
			metadataTreePanel = new MetadataTreePanel({rootId:'metath20',rootName:'功能元数据'})
			, panel = new Ext.Panel({
					title : '功能元数据属性',
					id : 'editDmProPanel',
					width : 700,
					autoScroll:true,
					region : 'east',
					split : true,
					layout : 'form',
					autoScroll : true,
					bodyStyle : 'padding: 20 0 20 20px',
					items : [{
						xtype : 'form',
						id : 'editMdProForm',
						layout : 'form',
						labelWidth : 85,
						border : true,
						labelAlign : 'left',
						buttonAlign : 'center',
						frame:true,
						 reader: new Ext.data.JsonReader({
					            root: 'results',
					            totalProperty: 'totalCount'
					        }, [
					            {name: 'metadata_id' },
					            {name: 'metadata_name'},
					            {name: 'begin_cell' },
					            {name: 'end_cell'},
					            {name: 'status_code'},
					            {name: 'dim_code'},
					            {name: 'component_id'},
					            {name:'component_tab'},
					            {name: 'component_name'},
					            {name: 'component_type'},
					            {name: 'component_default'},
					            {name: 'component_length'},
					            {name: 'component_hide'},
					            {name: 'component_query'},
					            {name: 'display_order'},
					            {name: 'com_condition'},
					            {name: 'if_editable'},
					            {name: 'if_must_input'},
					            {name: 'if_pk'},
					            {name: 'dml_sql'},
					            {name: 'max_value'},
					            {name: 'min_value'},
					            {name: 'max_length'},
					            {name: 'min_length'},
					            {name: 'col_biz_type_cd'},
					            {name: 'component_desc'}
					        ]),
						items : [
						   {
							   layout:'column',
							   items:[
									{
										columnWidth:'.5',
										layout:'form',
										items:[
										{
											xtype:'hidden',
											name:'metadata_cate_code',
											id:'metadata_cate_code'
										},
										{
											xtype : 'textfield',
											name : 'metadata_id',
											id : 'metadata_id',
											fieldLabel : '元&nbsp;数&nbsp;据&nbsp;ID',
											allowBlank : false,
											readOnly : true,
											style : 'background:#F0F0F0;color : #A0A0A0',
											anchor : '95%'
										},{
											xtype : 'textfield',
											name : 'begin_cell',
											id : 'begin_cell',
											fieldLabel : '开始单元格',
											anchor : '95%'
										},{
											xtype : 'combo',
											fieldLabel : '状态代码',
											name:'status_code',
											displayField : 'text',
											valueField : 'id',
											editable : false,
											hiddenName : 'status_code',
											mode : 'local',
											allowBlank : false,
											value : '02',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({
												fields : ['id', 'text'],
												data : [['01', '禁用'], ['02', '正常'], ['03', '删除']]
											}),
											anchor : '95%'
										}	, {
											xtype : 'combo',
											store : new Ext.data.SimpleStore({
												fields : ['id', 'text'],
												data : component_array
											}),
											displayField : 'text',
											valueField : 'id',
											fieldLabel : '控件类型',
											name:'component_type',
											editable : false,
											hiddenName : 'component_type',
											mode : 'local',
											triggerAction : 'all',
											value:'textfield',
											listeners:{
												select:function(){
													var v = this.getValue();
													setAbledStatus(v)
												}
											},
											anchor : '95%'
										}, {
											xtype : 'textfield',
											name : 'component_tab',
											id : 'component_tab',
											fieldLabel : '控件标签',
											anchor : '95%'
										}	,  {
											xtype : 'textfield',
											name : 'component_default',
											id : 'component_default',
											fieldLabel : '控件默认值',
											anchor : '95%'
										},  {
											xtype : 'numberfield',
											name : 'min_value',
											id : 'min_value',
											validator :function(v){
//												var max_value = Ext.getCmp('max_value').getValue();
//												if(v>max_value){
//													return "最小值大于最大值";
//												}
//												return true;
											},
											fieldLabel : '最小值',
											anchor : '95%'
										},{
											xtype : 'numberfield',
											name : 'min_length',
											id : 'min_length',
											fieldLabel : '控件最小长度',
											validator :function(v){
//												var max_length = Ext.getCmp('max_length').getValue();
//												if(v>max_length){
//													return "最小值大于最大值";
//												}
//												return true;
											},
											anchor : '95%'
										}, {
											xtype : 'textfield',
											name : 'component_desc',
											id : 'component_desc',
											fieldLabel : '控件描述',
											anchor : '95%'
										}, {
											xtype : 'combo',
											fieldLabel : '控件是否隐藏',
											name:'component_hide',
											displayField : 'text',
											valueField : 'id',
											editable : false,
											hiddenName : 'component_hide',
											mode : 'local',
											value : 'N',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({
												fields : ['id', 'text'],
												data : [['Y', '是'], ['N', '否']]
											}),
											anchor : '95%'
										} , {
											xtype : 'combo',
											fieldLabel : '是否可编辑',
											name:'if_editable',
											id:'ifEditable',
											displayField : 'text',
											valueField : 'id',
											editable : false,
											hiddenName : 'if_editable',
											mode : 'local',
											value : 'Y',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({
												fields : ['id', 'text'],
												data : [['Y', '是'], ['N', '否']]
											}),
											anchor : '95%'
										},new Ext.MyField({
											id:'display_order',
											name:'display_order',
											showIcon:true
										}
										)
									   ]
									}, 
									{
										columnWidth:'.5',
										layout:'form',
										items:[{
											xtype : 'textfield',
											name : 'metadata_name',
											id : 'metadata_name',
											fieldLabel : '元数据名称',
											allowBlank : false,
											readOnly : true,
											style : 'background:#F0F0F0;color : #A0A0A0',
											anchor : '95%'
										}, {
											xtype : 'textfield',
											name : 'end_cell',
											id : 'end_cell',
											fieldLabel : '结束单元格',
											anchor : '95%'
										}	,{
											xtype : 'textfield',
											name : 'component_id',
											id : 'component_id',
											fieldLabel : '控件ID',
											anchor : '95%'
										},  {
											xtype : 'combo',
											fieldLabel : '维度代码',
											id:'dimCode',
											displayField : 'dim_name',
											valueField : 'dim_code',
											editable : false,
											disabled : true,
											hiddenName : 'dim_code',
											mode : 'local',
											triggerAction : 'all',
											store:dim_store,
											listeners:{
												select:function(combo,r,i){
													Ext.getCmp('component_tab').setValue(r.get('dim_name'))
													Ext.getCmp('component_name').setValue(r.get('column_code'))
												}
											},
											anchor : '95%'
										},{
											xtype : 'textfield',
											name : 'component_name',
											id : 'component_name',
											fieldLabel : '控件NAME',
											allowBlank : false,
											anchor : '95%'
										}	, {
											xtype : 'numberfield',
											name : 'component_length',
											id : 'component_length',
											fieldLabel : '控件长度',
											anchor : '95%'
										},{
											xtype : 'numberfield',
											name : 'max_value',
											id : 'max_value',
											fieldLabel : '最大值',	validator :function(v){
												var min_value = Ext.getCmp('min_value').getValue();
												if(v<min_value){
													return "最小值大于最大值";
												}
												return true;
											},
											anchor : '95%'
										}, {
											xtype : 'numberfield',
											name : 'max_length',
											id : 'max_length',
											fieldLabel : '控件最大长度',	validator :function(v){
												var min_length = Ext.getCmp('min_length').getValue();
												if(v<min_length){
													return "最小值大于最大值";
												}
												return true;
											},
											anchor : '95%'
										}, {
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
											anchor : '95%'
										} , {
											xtype : 'combo',
											fieldLabel : '是否必输',
											name:'if_must_input',
											id:'ifMustInput',
											displayField : 'text',
											valueField : 'id',
											editable : false,
											hiddenName : 'if_must_input',
											mode : 'local',
											value : 'N',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({
												fields : ['id', 'text'],
												data : [['Y', '是'], ['N', '否']]
											}),
											anchor : '95%'
										} , {
											xtype : 'combo',
											fieldLabel : '是否主键',
											name:'if_pk',
											id:'ifPk',
											displayField : 'text',
											valueField : 'id',
											editable : false,
											hiddenName : 'if_pk',
											mode : 'local',
											value : 'N',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({
												fields : ['id', 'text'],
												data : [['Y', '是'], ['N', '否']]
											}),
											anchor : '95%'
										} ,{
											xtype : 'combo',
											fieldLabel : '是否查询控件',
											name:'component_query',
											displayField : 'text',
											valueField : 'id',
											editable : false,
											hiddenName : 'component_query',
											mode : 'local',
											value : 'N',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({
												fields : ['id', 'text'],
												data : [['Y', '是'], ['N', '否']]
											}),
											anchor : '95%'
										} ]
									}  
							    ]
						   },{
								xtype : 'numberfield',
								name : 'com_condition',
								id : 'com_condition',
								fieldLabel : '控件条件',
								anchor : '97%'
							}     
						  ,{
							xtype : 'textarea',
							name : 'dml_sql',
							id : 'dml_sql',
							fieldLabel : 'DML语言',
							autoScroll : true,
							disabled : true,
							height : 165,
							anchor : '97%'
						}],
						buttons : [
						{
							text : '新增',
							id:'add_btn',
							hidden:true,
							handler : function() {
								var formPanel = Ext.getCmp("editMdProForm");
								formPanel.form.reset();
								var metadata_id = metadataTreePanel.getSelectionModel().getSelectedNode().id;
								Ext.getCmp('metadata_id').setValue(metadata_id);
								Ext.getCmp('metadata_id').el.parent().parent().first().dom.innerHTML='父级元数据ID:';
								var com = Ext.getCmp('metadata_name');
								com.el.dom.readOnly=false;
								com.el.dom.style.background='#FBFBFF';
								com.el.dom.style.color='#000000';
								this.setDisabled(true);
								Ext.getCmp('save_btn').setDisabled(false);
							}
						},
						{
							text : '保存',
							id:'save_btn',
							disabled:true,
							handler : function() {
								var url = '/managerFnMdPro/editPro';
								var add_flag = false;
								var metadata_id = metadataTreePanel.getSelectionModel().getSelectedNode().id;
								if(metadata_type=='2010'){
									add_flag = true;
									url = '/managerFnMdPro/addPro'
								}
								var formPanel = Ext.getCmp("editMdProForm");
								if (formPanel.form.isValid()) {
									formPanel.form.submit({
										url : pathUrl + url,
										waitMsg : '正在处理,请稍后......',
										success : function(form, action) {
											Ext.Msg.alert("消息", action.result.info);
											if(add_flag){
												Ext.getCmp('save_btn').setDisabled(true);
												Ext.getCmp('add_btn').setDisabled(false);
												addNode(action.result.metadata_id);
											}
										},
										failure : function(form, action) {
											Ext.Msg.alert("消息", action.result.info);
										}
									})
								} else {
									Ext.Msg.alert("提示信息", "请输入完整信息");
								}
							}
						},
						{
							text : '删除',
							id:'del_btn',
							disabled:true,
							handler : function() {
							var node =  metadataTreePanel.getSelectionModel().getSelectedNode();
								if(!node){
									Ext.Msg.alert("消息","请先选择元数据");
									return;
								}
								Ext.Msg.confirm('Confirm','确认删除?',function(btn){
									if(btn=='yes'){
										var formPanel = Ext.getCmp("editMdProForm");
//										if (formPanel.form.isValid()) {
											formPanel.form.submit({
												url : pathUrl + '/managerFnMdPro/deletePro',
												waitMsg : '正在处理,请稍后......',
												success : function(form, action) {
													Ext.Msg.alert("消息", action.result.info);
													delNode();
												},
												failure : function(form, action) {
													Ext.Msg.alert("消息", action.result.info);
												}
											})
//										} else {
//											Ext.Msg.alert("提示信息", "请输入完整信息");
//										}
									}
								})
								
							}
						}
						]
					}]
			})]
		});

		MyViewportUi.superclass.initComponent.call(this);

	}
});

Ext.onReady(function() {
	dim_store.load();
	var myview = new MyViewportUi();

	metadataTreePanel.on('click', function(node,e) {
		Ext.getCmp('metadata_id').el.parent().parent().first().dom.innerHTML='元&nbsp;数&nbsp;据&nbsp;ID：';
		var com = Ext.getCmp('metadata_name');
		com.el.dom.readOnly=true;
		com.el.dom.style.background='#F0F0F0';
		com.el.dom.style.color='#A0A0A0';
	    var formPanel = Ext.getCmp("editMdProForm");
	   	var attr = node.attributes.attributes;
	    if(attr){
	    		Ext.Ajax.request({
					url: pathUrl + '/managerFnMdPro/listFnMdPro',
					method: 'POST',
					params: {metadata_id:node.id},
					success:function(response, options){
						var json=Ext.util.JSON.decode(response.responseText);
						var res=json.results;
						if(res){
							formPanel.form.reset();
							var type = res[0].component_type;
							setAbledStatus(type)
						    formPanel.load({
						    	url:pathUrl + '/managerFnMdPro/listFnMdPro',
						    	params:{metadata_id:node.id}
						    });
						}
					},
					failure:function(response, options){
						Ext.MessageBox.alert('错误',response.responseText);
					}
				});
	    	var type = attr.metadata_cate_code;
	    	Ext.getCmp('metadata_cate_code').setValue(type);
	    	metadata_type = type;
	    	if(type=='2010'){
	    		Ext.getCmp('save_btn').setDisabled(true);
	    		Ext.getCmp('add_btn').show();
	    		Ext.getCmp('add_btn').setDisabled(false);
	    		Ext.getCmp('del_btn').setDisabled(true);
	    	}else{
	    		Ext.getCmp('save_btn').setDisabled(false);
	    		Ext.getCmp('add_btn').hide();
	    		Ext.getCmp('del_btn').setDisabled(false);
			}
	   }else{
	   		Ext.getCmp('save_btn').setDisabled(true);
	   }
	});
});

function setAbledStatus(type){
	if(type=='combobox'||type=='uxgrid'||type=='uxtree'){
		Ext.getCmp('dml_sql').setDisabled(true);
		Ext.getCmp('dimCode').setDisabled(false);
		Ext.getCmp('dml_sql').setDisabled(true);
		Ext.getCmp('component_default').setDisabled(false);
		Ext.getCmp('component_length').setDisabled(false);
		Ext.getCmp('ifEditable').setDisabled(false);
		Ext.getCmp('ifMustInput').setDisabled(false);
		Ext.getCmp('ifPk').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
		Ext.getCmp('colBizTypeCd').setDisabled(false);
	}else if(type=='textfield'||type=='textarea'||type=='datefield'||type=='hidden'||type=='moneyfield'||type=='numberfield'){
		Ext.getCmp('dimCode').setDisabled(true);
		Ext.getCmp('dml_sql').setDisabled(true);
		Ext.getCmp('component_default').setDisabled(false);
		Ext.getCmp('component_length').setDisabled(false);
		Ext.getCmp('ifEditable').setDisabled(false);
		Ext.getCmp('ifMustInput').setDisabled(false);
		Ext.getCmp('ifPk').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
		Ext.getCmp('colBizTypeCd').setDisabled(false);
	}else if(type==null||type==''){
		Ext.getCmp('dimCode').setDisabled(true);
		Ext.getCmp('dml_sql').setDisabled(true);
		Ext.getCmp('component_default').setDisabled(false);
		Ext.getCmp('component_length').setDisabled(false);
		Ext.getCmp('ifEditable').setDisabled(false);
		Ext.getCmp('ifMustInput').setDisabled(false);
		Ext.getCmp('ifPk').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
		Ext.getCmp('colBizTypeCd').setDisabled(false);
	}else{
		Ext.getCmp('dimCode').setDisabled(true);
		Ext.getCmp('dml_sql').setDisabled(false);
		Ext.getCmp('component_default').setDisabled(true);
		Ext.getCmp('component_length').setDisabled(true);
		Ext.getCmp('ifEditable').setDisabled(true);
		Ext.getCmp('ifMustInput').setDisabled(true);
		Ext.getCmp('ifPk').setDisabled(true);
		Ext.getCmp('max_value').setDisabled(true);
		Ext.getCmp('min_value').setDisabled(true);
		Ext.getCmp('colBizTypeCd').setDisabled(true);
	}
}

function addNode(id){
	var cnode=new Ext.tree.AsyncTreeNode({
			id:id,
			text:Ext.getCmp('metadata_name').getValue(),
			iconCls: getIconCls('2010101'),
			leaf:true,
			attributes:{metadata_cate_code:'2010101'}
	});
	var node =  metadataTreePanel.getSelectionModel().getSelectedNode();
	node.insertBefore(cnode,node.firstChild);
}
function delNode(){
	var cnode=new Ext.tree.AsyncTreeNode({
			id:Ext.getCmp('metadata_id').getValue(),
			text:Ext.getCmp('metadata_name').getValue(),
			iconCls: getIconCls('2010101'),
			leaf:true,
			attributes:{metadata_cate_code:'2010101'}
	});
	var node =  metadataTreePanel.getSelectionModel().getSelectedNode();
	node.remove();
}