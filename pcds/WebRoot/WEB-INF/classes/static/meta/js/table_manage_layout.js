var leftGrid_sd,myCreateWindowUi,importGridSd=null;
Ext.onReady(function() {
	Ext.QuickTips.init();
	databaseStore.load(); //加载数据库信息store
	dataType_ds.load();
	conTableType_ds.load();

var leftTbar=[ {
					xtype : 'button',
					text : '导入表',
					iconCls : 'add',
					handler : create
				},{
					xtype : 'button',
					text : '删除',
//					hidden:true,
					iconCls : 'delete',
					handler : deleteTable
				}, {
					xtype : 'button',
					text : '刷新',
					iconCls : 'refresh',
					handler : refreshLTableList
				} ];
				
	
var leftGrid={
				xtype : 'grid',
				id : 'localTableList',
				title : '元数据列表',
				width : 450,
				region : 'center',
				border:false,
				tbar : leftTbar,
				cm : leftGrid_cm,
				autoScroll : true,
				split : true,
				frame : false,
				ds : leftGrid_sd,
				loadMask : true,
				viewConfig : {
					forceFit : true
				},
				bbar : new Ext.PagingToolbar( {
					pageSize : 30,
					store : leftGrid_sd,
					displayInfo : true,
					displayMsg : '第{0}-{1}条记录,共{2}条记录',
					emptyMsg : "没有记录"
				})
			} ;

var rightTBar= [ {
					xtype : 'button',
					text : '配置',
					iconCls : 'edit',
					handler : dispose
				},{
					xtype : 'button',
					text : '删除字段',
					id:'delField',
					iconCls : 'delete',
					hidden:true,
					handler : deleteField
				}, {
					xtype : 'button',
					text : '刷新',
					iconCls : 'refresh',
					handler : refreshTab
				} ];
var leftRegion={
					xtype : 'panel',
					layout : 'border',
					split : true,
					collapsible : true,
					width : 400,
					title : '元数据管理',
					region : 'west',
					items : [ {
						xtype : 'panel',
						layout : 'form',
						region : 'north',
						border : false,
						labelAlign : 'right',
						labelWidth : 50,
						height : 60,
						defaults : {
							width : 200
						},
						items : [ {
							xtype : 'panel',
							border : false,
							height : 5
						}, {
							xtype : 'combo',
							id : 'database',
							fieldLabel : '数据库',
							valueField : 'database_id',
							displayField : 'database_name',
							name : 'database_id',
							mode : 'local',
							editable : false,
							store : databaseStore,
							triggerAction : 'all',
							emptyText : '---请选择---',
							listeners : {
								select : databaseSelect
							}
						}, {
							xtype : 'combo',
							id : 'owner',
							fieldLabel : '用户',
							valueField : 'db_user_id',
							displayField : 'owner_name',
							hiddenName : 'owner_name',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							store : ownerStore,
							emptyText : '---请选择---',
							listeners : {
								select : refreshLTableList
							}
						} ]
					},leftGrid
						]
				};
				
var centerRegion={ //-------------右侧列表------------------
					xtype : 'panel',
					layout : 'border',
					region : 'center',
					border : false,
					items : [ {
						xtype : 'tabpanel',
						id : 'tab',
						region : 'center',
						autoScroll : true,
						activeTab : 0,
						tbar :rightTBar,
						items : [ {
							xtype : 'form',
							id : 't0',
							title : '表信息',
							autoScroll : true,
							labelAlign : 'right',
							labelWidth : 110,
							border : false,
							defaultType : 'textfield',
							defaults : {
								readOnly : true
							},
							items : [ 
							{
								xtype : 'panel',
								border : false,
								height : 5
							},{
								fieldLabel : '表ID',
								name : 'table_id',
								hidden:true,
								width : 190
							}, {
								fieldLabel : '表名',
								name : 'table_name',
								width : 190
							}, {
								xtype : 'textarea',
								fieldLabel : '表描述',
								name : 'table_desc',
								width : 190
							}, {
								fieldLabel : '引用的数据库',
								name : 'database_name',
								width : 190
							}, {
								fieldLabel : '用户名',
								name : 'owner_name',
								width : 190
							}, {
								fieldLabel : '表空间名称',
								name : 'tablespace_name',
								width : 190
							}, {
								fieldLabel : '表分类',
								name : 'theme_name',
								width : 190
							}, {
								fieldLabel : '状态',
								name : 'statue_desc',
								width : 190,
								renderer:function(v){
									if(v=='01'){
										return '禁用';
									}else if(v=='02'){
										return '正常';
									}else{
										return '删除';
									}
								}
							}, {
								fieldLabel : '是否显示',
								name : 'displaydesc',
								width : 190,
								renderer:function(v){
									if(v=='Y'){
										return '显示';
									}else{
										return '隐藏';
									}
								}
							}],
							listeners : {
								activate : activate
							}
						}, {
							id : 't1',
							title : '字段信息',
							autoScroll : true,
							layout:'fit',
							items : [ {
								xtype : 'grid',
								id:'fieldGrid',
								border : false,
								//autoScroll : true,
								store : lds1,
								columns : lcm1
							} ],
							listeners : {
								activate : activate
							}
						}
						]
					} ]
				} ;
				
	var myview = new Ext.Viewport({
		layout : 'fit',
		items : [ {
					layout : 'border',
					border : false,
					//style : 'padding:4px 4px 4px 4px;',
					items : [ 
						leftRegion, centerRegion]
				} ]
	});

	
	Ext.getCmp("localTableList").on("click",
		function() {
			var g = Ext.getCmp("localTableList");
			if(g.getSelectionModel().getSelections().length>0){
				var table_id=getSelectNode("localTableList").table_id;
				var obj_tab=getMyActiveTab();
				 loadLocalStore(table_id,obj_tab.id);
				 addFlag=false;
			}
		});
		
});

//导入
function createWindowUi (){
	addFlag=true;
	myCreateWindowUi=new Ext.Window({
		id : 'createWindow',
		title : '导入表',
		width : 850,
		height : 450,
		modal : true,
		resizable : false,
		layout : 'fit',
		buttonAlign : 'center',
		buttons : [ {
			text : '确定',
			handler : function() {
				createTable();
			}
		}, {
			text : '取消',
			handler : function() {
				myCreateWindowUi.destroy();
				myCreateWindowUi = null;
			}
		} ],
		items:[{
			layout:'border',
			items:[
				{
					xtype : 'grid',
					id : 'tableList',
					region : 'west',
					title : '源表("@"标记为本地已存表)',
					width : 400,
					border:false,
					tbar:[{xtype:'textfield',id:'inputCon',emptyText:'请输入表名查询',width:300},
						'->',{xtype:'button',text:'查询',iconCls:'search',handler:function (){
							var searchCon=Ext.getCmp("inputCon").getValue();
	    					refreshSTableList(searchCon);
					}}],
					cm : new Ext.grid.ColumnModel( [ 
							{
								xtype : 'gridcolumn',
								dataIndex : 'show_table_name',
								align : 'left',
								header : '表名',
								width : 220,
								sortable : true
							},{
								xtype : 'gridcolumn',
								dataIndex : 'table_desc',
								align : 'left',
								header : '描述',
								width : 220,
								sortable : true
							}
						]),
					autoScroll : true,
					split : true,
					frame : false,
					ds : importGridSd,
					loadMask : true
					
				},{
					xtype : 'form',
					id : 'rt0',
					title : '表信息',
					autoScroll : true,
					labelAlign : 'right',
					labelWidth : 95,
					border : false,
					region : 'center',
					defaultType : 'textfield',
					items : [ {
						xtype : 'panel',
						border : false,
						height : 5
					}, {
						fieldLabel : '表名',
						name : 'table_name',
						width : 190
					}, {
						fieldLabel : '表描述',
						name : 'table_desc',
						width : 190
					}, {
						fieldLabel : '引用的数据库',
						name : 'database_name',
						width : 190,
						value : getBaseInfo().database_name
					}, {
						fieldLabel : '用户名',
						name : 'owner_name',
						width : 190
					},{
						xtype : 'combo',
						id : 'tablespace_id_import',
						fieldLabel : '表空间',
						valueField : 'tablespace_id',
						displayField : 'tablespace_name',
						name : 'tablespace_id',
						hiddenName:'tablespace_id',
						mode : 'local',
						allowBlank : false,
						width : 190,
						triggerAction : 'all',
						editable : false,
						store : tableSpace_ds,
						emptyText : '---请选择---'
						
					}, /*{
						fieldLabel : '表空间名称',
						name : 'tablespace_name',
						width : 190
					}, {
						fieldLabel : '分区类型',
						name : 'partition_type_code',
						width : 190
					},{
							xtype : 'combo',
							id : 'theme_id_import',
							fieldLabel : '主题',
							valueField : 'theme_id',
							displayField : 'theme_name',
							name:'theme_id',
							hiddenName:'theme_id',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							allowBlank : false,
							store : theme_ds,
							width : 190,
							emptyText : '---请选择---'
					},*/new ThemeCombo({width:190,id:'themeCombo',name:'theme_id',hiddenName:'theme_id',allowBlank : false}),{
						fieldLabel : '状态',
						xtype : 'combo',
						mode : 'local',
						allowBlank : false,
						blankText : '不能为空',
						store : new Ext.data.ArrayStore({
									fields : ['display', 'value'],
									data : [['禁用', '01'], ['正常', '02'], ['删除', '03']]
								}),
						triggerAction : 'all',
						displayField : 'display',
						valueField : 'value',
						name : 'status_code',
						hiddenName:'status_code',
						id : 'statusCode',
						width : 190
				} ]
						
				}
			]		
		}]
		
		
	});
	
	myCreateWindowUi.on("close",function(){
		myCreateWindowUi.destroy();
		myCreateWindowUi = null;
	});
	myCreateWindowUi.show();
	theme_ds.load();
	
    var database_id=Ext.getCmp('database').getValue();
	tableSpace_ds.load({params:{database_id:database_id}});
	
	Ext.getCmp("statusCode").setValue("02");
	Ext.getCmp("statusCode").setRawValue("正常");
	Ext.getCmp("tableList").on("click",
		function() {
			var g = Ext.getCmp("tableList");
			var table_id=g.getSelectionModel().getSelections()[0].get("table_name");
			var obj_tab=getMyActiveTab();
			loadSourceStore(table_id,obj_tab.id);
			//alert(table_id+"---"+obj_tab.id);
		});
}	
	
function myDisposeWindowUi(){
	var component=null;	
	if('t0' == getMyActiveTab().id){
	component={
				xtype : 'form',
				id : 'local',
				autoScroll : true,
				labelAlign : 'right',
				labelWidth : 95,
				border : false,
				region : 'center',
				defaultType : 'textfield',
				items : [ 
									
					{
							xtype : 'panel',
							border : false,
							height : 5
						}, {
							fieldLabel : '表ID',
							name : 'table_id',
							hidden:true,
							width : 190
						}, {
							fieldLabel : '表名',
							name : 'table_name',
							hiddenName : 'table_name',
							allowBlank : false,
							width : 190
						}, {
							xtype : 'textarea',
							fieldLabel : '表描述',
							name : 'table_desc',
							width : 190
						},{
							xtype : 'combo',
							id : 'database_id_up',
							fieldLabel : '数据库',
							valueField : 'database_id',
							displayField : 'database_name',
							name:'database_id',
							hiddenName:'database_id',
							mode : 'local',
							editable : false,
							allowBlank : false,
							store : databaseStore1,
							width : 190,
							triggerAction : 'all',
							emptyText : '---请选择---'
						}, {
							xtype : 'combo',
							id : 'db_user_id_up',
							fieldLabel : '用户',
							valueField : 'db_user_id',
							displayField : 'owner_name',
							name : 'db_user_id',
							hiddenName : 'db_user_id',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							allowBlank : false,
							store : ownerStore1,
							width : 190,
							emptyText : '---请选择---'
							
						} ,{
							xtype : 'combo',
							id : 'tablespace_id_up',
							fieldLabel : '表空间',
							valueField : 'tablespace_id',
							displayField : 'tablespace_name',
							name : 'tablespace_id',
							hiddenName : 'tablespace_id',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							allowBlank : false,
							width : 190,
							store : tableSpace_ds,
							emptyText : '---请选择---'
							
						} /*,{
							xtype : 'combo',
							fieldLabel : '主题',
							name : 'theme_id',
							valueField : 'theme_id',
							displayField : 'theme_name',
							hiddenName : 'theme_id',
							mode : 'local',
							allowBlank : false,
							editable : false,
							triggerAction : 'all',
							width : 190,
							store : theme_ds
						}*/,new ThemeCombo({width:190,id:'themeCombo',name:'theme_id',hiddenName:'theme_id',allowBlank : false}), {
								fieldLabel : '状态',
								xtype : 'combo',
								mode : 'local',
								allowBlank : false,
								blankText : '不能为空',
								store : new Ext.data.ArrayStore({
											fields : ['display', 'value'],
											data : [['禁用', '01'], ['正常', '02'], ['删除', '03']]
										}),
								triggerAction : 'all',
								displayField : 'display',
								valueField : 'value',
								hiddenName : 'status_code',
								name : 'status_code',
								id : 'statusCode',
								width : 190
						}, {
							xtype : 'radiogroup',
							fieldLabel : '是否显示',
							name : 'is_display',
							hiddenName : 'is_display',
							width : 100,
							items : [ {
								name : 'is_display',
								inputValue : 'Y',
								checked:true,
								boxLabel : '是'
							}, {
								name : 'is_display',
								inputValue : 'N',
								boxLabel : '否'
							} ]
						}, {
							fieldLabel : '显示顺序',
							name : 'display_order',
							xtype:'numberfield',
							hiddenName : 'display_order',
							width : 190
						}]
			} ;

	}else{
		component={
				xtype : 'form',
				id : 'local',
				autoScroll : true,
				labelAlign : 'right',
				labelWidth : 95,
				border : false,
				region : 'center',
				defaultType : 'textfield',
				items : [ 
									
					{
							xtype : 'panel',
							border : false,
							height : 5
						}, {
							fieldLabel : '列ID',
							name : 'column_id',
							hidden:true,
							width : 190
						}, {
							fieldLabel : '列名',
							name : 'column_name',
							hiddenName : 'column_name',
							allowBlank : false,
							width : 190
						}, {
							xtype : 'textarea',
							fieldLabel : '列描述',
							name : 'column_desc',
							width : 190
						},{
					         xtype: 'combo',
					         fieldLabel : '字段类型',
					         id : 'data_type_code_up',
					         allowBlank:false,
					         editable : false,
					         mode: 'local',
					         name:'data_type_code',
					         hiddenName:'data_type_code',
					         triggerAction: 'all',
					         width : 190,
					         valueField: 'data_type_code',
					         displayField: 'data_type_name',
					         store: dataType_ds,
					         emptyText : '---请选择---'
					         
					     }, {
					         xtype: 'combo',
					         fieldLabel : '数据类型',
					         mode: 'local',
					         name:'column_type_code',
					         hiddenName:'column_type_code',
					         id:'column_type_code_up',
					         triggerAction: 'all',
					         allowBlank:false,
					         width : 190,
					         valueField: 'column_type_code',
					         displayField: 'column_type_name',
					         store: conTableType_ds,
					         emptyText : '---请选择---'}
					     ,{
							fieldLabel : '字段长度',
							name : 'data_length',
							hiddenName : 'data_length',
							xtype:'numberfield',
							allowBlank : false,
							width : 190
						}, {
							fieldLabel : '字段精度',
							name : 'data_scale',
							hiddenName : 'data_scale',
							xtype:'numberfield',
							width : 190
						},  {
							fieldLabel : '默认值',
							name : 'default_value',
							hiddenName : 'default_value',
							width : 190
						},{
							xtype : 'radiogroup',
							fieldLabel : '是否显示',
							name : 'is_display',
							hiddenName : 'is_display',
							width : 190,
							items : [ {
								name : 'is_display',
								inputValue : 'Y',
								checked:true,
								boxLabel : '是'
							}, {
								name : 'is_display',
								inputValue : 'N',
								boxLabel : '否'
							} ]
						}, {
							xtype : 'radiogroup',
							fieldLabel : '是否可为空',
							name : 'is_nullable',
							hiddenName : 'is_nullable',
							width : 190,
							items : [ {
								name : 'is_nullable',
								inputValue : 'Y',
								checked:true,
								boxLabel : '是'
							}, {
								name : 'is_nullable',
								inputValue : 'N',
								boxLabel : '否'
							} ]
						}, {
							xtype : 'radiogroup',
							fieldLabel : '是否可编辑',
							name : 'is_editable',
							hiddenName : 'is_editable',
							width : 190,
							items : [ {
								name : 'is_editable',
								inputValue : 'Y',
								checked:true,
								boxLabel : '是'
							}, {
								name : 'is_editable',
								inputValue : 'N',
								boxLabel : '否'
							} ]
						}, {
							fieldLabel : '显示顺序',
							name : 'display_order',
							hiddenName : 'display_order',
							xtype:'numberfield',
							width : 190
						}]
			} ;
			

	/*component= {
		region:'center',
		layout:'border',
		items:[{
				xtype : 'grid',
				id : 'source',
				title : '源信息',
				ddGroup : 'g2',
				enableDragDrop : true,
				split : true,
				sm : new Ext.grid.RowSelectionModel(),
				columns : getColumns('s', getMyActiveTab().id),
				store : getStore('s', getMyActiveTab().id),
				region : 'north',
				height : 200
			},{
				xtype : 'editorgrid',
				id : 'local',
				title : '本地信息',
				ddGroup : 'g1',
				enableDragDrop : true,
				split : true,
				clicksToEdit : 2,
				sm : new Ext.grid.RowSelectionModel(),
				columns : getColumns('l', getMyActiveTab().id),
				store : getStore('l', getMyActiveTab().id),
				region : 'center',
				tbar : [ {
					xtype : 'button',
					text : '添加',
					handler : function() {
					    addRecord();
					}
				}, {
					xtype : 'button',
					text : '删除',
					handler : function() {
					    deleteRecord();
					}
				} ]
			}]
		};	*/
	
	}
	var  obj_window=new Ext.Window({
		id : 'disposeWindow',
		title : '配置表',
		layout : 'fit',
		width : 750,
		height : 450,
		modal : true,
		maximized : true,
		resizable : false,
		buttonAlign : 'center',
		items:[{
				layout : 'border',
				items:[component]
		 }],
		buttons : [{
				text : '新增表',
				id : 'addBtn',
				handler : function() {
					clearForm();
				}
				},{
				text : '保存',
				handler : function() {
					disposeInfo();
				}
			}, {
				text : '关闭',
				handler : function() {
					//this.ownerCt.ownerCt.close();
					obj_window.destroy();
					obj_window = null;
				}
			} ]
	});
	 
	 return obj_window;
}

