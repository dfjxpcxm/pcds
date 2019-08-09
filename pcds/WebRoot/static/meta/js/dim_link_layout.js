var dim_cd = '', isTree = '', tree = '', rootValue = '';
var expander = new Ext.grid.RowExpander({
	tpl :new Ext.Template('<br><p><b>维度查询语句:</b> {dim_sql_expr}</p><br><p><b>级联SQL语句:</b> {cascade_sql_expr}</p><br>')
});

// 数据源维度store
var src_dim_ds = new Ext.data.JsonStore({
	url : pathUrl + '/dimLinkAjax/listDimLinks',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['dim_cd', 'dim_name', 'dim_sql_expr', 'is_tree','dim_desc',
			'code_col_name', 'label_col_name', 'prt_col_name', 'root_value','cascade_sql_expr',
			'is_table_edit','table_name','tabke_pk','table_cols'],
	autoLoad : true
});

// 数据源维度column
var src_dim_cm = [expander,{
	header : '维度ID',
	dataIndex : 'dim_cd'
}, {
	header : '维度名称',
	dataIndex : 'dim_name'
}, {
	header : '维度描述',
	dataIndex : 'dim_desc'
},{
	header : '标签字段',
	dataIndex : 'label_col_name'
}, {
	header : '值字段',
	dataIndex : 'code_col_name'
}, {
	header : '是否树形',
	dataIndex : 'is_tree',
	hidden : true
}, {
	header : '启用表编辑',
	dataIndex : 'is_table_edit',
	hidden : true
}];

// 数据源维度明细store
var dim_data_ds = new Ext.data.JsonStore({
	url : pathUrl + '/dimLinkAjax/listExpressionDetail',
	root : 'results',
	fields : ['value_field', 'display_field','pk_name']
});
var smodel = new Ext.grid.CheckboxSelectionModel();
// 数据源维度明细column
var field_data_cm = ([smodel,{
	header : '标签字段',
	dataIndex : 'display_field',
	align : 'center'
	}, {
		header : '值字段',
		dataIndex : 'value_field',
		align : 'center'
	}, {
		header : '主键', //0000026716@00240
		dataIndex : 'pk_name',
		hidden:true
	}
]);

Ext.onReady(function() {
	var myview = new MyViewportUi();
	src_dim_ds.on("beforeload", function() {
		dim_data_ds.removeAll();
		Ext.getCmp('dimLinkDetailTreePanel').setVisible(false);
	});
	onRowSelect();
	
	//处理从数据源配置传递的参数
//	if(null != link_id_in_dataconfig && '' != link_id_in_dataconfig){
//		src_dim_ds.on("load",afterLoad);
//	}
});

MyViewportUi = Ext.extend(Ext.Viewport, {
	layout : 'border',
	initComponent : function() {
		Ext.applyIf(this, {
			items : [{
				xtype : 'grid',
				region : 'center',
				id : 'dimLinkGrid',
				title : '数据源维度',
				columns : src_dim_cm,
				plugins : expander,
				store : src_dim_ds,
				loadMask : true,
				split : true,
				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
				flex : 1,
				viewConfig : {
					forceFit : true
				},
				bbar:new Ext.PagingToolbar({
					pageSize : 30,
					store : src_dim_ds,
					displayInfo : true,
					displayMsg : '第{0}-{1}条记录,共{2}条记录',
					emptyMsg : "没有记录"
				}),
				tbar : [{
					xtype : 'button',
					text : '添加',
					iconCls : 'add',
					handler : function() {
						var addWindow = new AddWindow();
						addWindow.show();
					}
				}, '-', {
					xtype : 'button',
					text : '编辑',
					iconCls : 'edit',
					handler : function() {
						var array = Ext.getCmp("dimLinkGrid").getSelectionModel().getSelections();
						if (array.length == 0) {
							Ext.Msg.alert("提示信息", "请选择需要修改的记录");
						} else {
							var editWindow = new EditWindow();
							editWindow.show();
							Ext.getCmp("editdimLinkForm").getForm().loadRecord(Ext.getCmp("dimLinkGrid").getSelectionModel().getSelected());
							var selectDimData=array[0];
							judgeTree(selectDimData.get('is_tree'));
							dimTableConsole(selectDimData.get('is_table_edit'));
						}
					}
				}, '-', {
					xtype : 'button',
					text : '删除',
					iconCls : 'delete',
					handler : function() {
						var array = Ext.getCmp("dimLinkGrid").getSelectionModel().getSelections();
						if (array.length == 0) {
							Ext.Msg.alert("提示信息", "请选择需要删除的记录");
						} else {
							var dim_code = array[0].get('dim_cd');
							Ext.MessageBox.confirm('确认信息', '是否确认删除选中的数据源维度记录?',
									function(btn) {
										if (btn == 'yes')
											deleteDimLink(dim_code);
									});
						}
					}
				}, '->', {
					xtype : 'textfield',
					id : 'searchKey',
					emptyText : '请输入维度ID或维度名...',
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								src_dim_ds.load({
									params : {
										moduleCode:'0384',
										searchKey : Ext.getCmp("searchKey").getValue()
									}
								})
							}
						}
					},
					width : 150
				}, {
					xtype : 'button',
					iconCls : 'search',
					handler : function() {
						src_dim_ds.load({
							params : {
								moduleCode:'0384',
								searchKey : Ext.getCmp("searchKey").getValue()
							}
						})
					}
				}]
			}, panel = new Ext.Panel({
				region : 'east',
				title : '维度明细',
				width : 420,
				split : true,
				layout : 'fit',
				autoScroll : true,				
				tbar : new Ext.Toolbar({id:'dimDetailsTbar',
					hidden:true,
					items:[
					{
						text : '添加',
						xtype : 'button',
						iconCls : 'add',
						handler : function() {
								if(currentRecord){
									var isTree = currentRecord.get('is_tree');
									if (isTree == 'Y') {
										if(!Ext.getCmp("dimLinkDetailTreePanel").getSelectionModel().getSelectedNode()){
											Ext.Msg.alert("提示信息", "请选择纬度列表数据作为父级节点");
											return;
										}
									}
									var edit_cols=getAddTableInfo(currentRecord);
									//document.write(edit_cols);
									addDimtTableConsole(currentRecord,edit_cols);
								}else{
									Ext.Msg.alert("提示信息", "请先选择数据源纬度数据");
								}
						     }
						}, '-', {
							xtype : 'button',
							text : '编辑',
							iconCls : 'edit',
								handler : function() {
								if(dimTableCheck()){
						    		var table_name=currentRecord.get('table_name');
						    		var tabke_pk=currentRecord.get('tabke_pk');
						    		var pk_code;
						 
						    		if(currentRecord.get("is_tree")=='N'){
						    			var dim_table_array=Ext.getCmp("dimLinkDetailGrid").getSelectionModel().getSelections();//选中维度明细对象
								    	var dim_table_Record=dim_table_array[0];
							    		pk_code=dim_table_Record.get('pk_name');
						    		}else{
						    			var node=Ext.getCmp("dimLinkDetailTreePanel").getSelectionModel().getSelectedNode();
							    		pk_code=node.attributes.id;				    		
						    		}
						    		
									Ext.Ajax.request({
						    			method : 'POST',
						    			url : pathUrl + '/dimLinkAjax/findOneDimTable',
						    			params : {
						    				table_name : table_name,
						    				tabke_pk:tabke_pk,
						    				pk_code:pk_code
						    			},
						    			callback : function(options, success, response) {
						    				var json = Ext.util.JSON.decode(response.responseText);
						    				var dim_talbe_json=json.results;
						    				if(success){
						    					var edit_cols=editTableInfo(currentRecord,pk_code,dim_talbe_json);
												editDimtTableConsole(currentRecord,edit_cols);
						    				}else {
						    					Ext.MessageBox.alert("提示信息", json.info);
						    				}
						    			}
						    		});
								}
							}
						}, '-', {
							xtype : 'button',
							text : '删除',
							iconCls : 'delete',
							handler : function() {
								if(dimTableCheck()){
									var pk_code="";//主键值
									if(currentRecord.get('is_tree')=='N'){
							    		var dim_table_array=Ext.getCmp("dimLinkDetailGrid").getSelectionModel().getSelections();//选中维度明细对象
						    			for(var i=0;i<dim_table_array.length;i++){
						    				var dim_table_Record=dim_table_array[i];
								    		pk_code+=dim_table_Record.get('pk_name')+";";
						    			}
						    			pk_code=pk_code.substring(0, pk_code.length-1);
									}else{
								    	var node=Ext.getCmp("dimLinkDetailTreePanel").getSelectionModel().getSelectedNode();
							    		pk_code=node.attributes.id;
									}
									
									Ext.MessageBox.confirm('确认信息', '是否确认删除选中的数据源维度记录?',
											function(btn) {
												if (btn == 'yes')
													deleteDimTable(currentRecord,pk_code);
											});
								}
							}
						}
									                             
				 ]}),
				items : [{
					xtype : 'grid',
					id : 'dimLinkDetailGrid',
					columns : field_data_cm,
					store : dim_data_ds,
					loadMask : true,
					/*sm : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),*/
					sm:smodel,
					flex : 1,
					autoHeight: true,
					autoScroll : true,
					border : false,
					frame : false,
					viewConfig : {
						forceFit : true
					}
				}, tree = new Ext.tree.TreePanel({
					id : 'dimLinkDetailTreePanel',
					animate : true,
					border : false,
					loader : new Ext.tree.TreeLoader(),
					lines : false,
					autoScroll : true,
					frame : false,
					bodyStyle : 'padding:5px 5px',
					root : getRootNode('', '', expandDimLinkTreeNode),
					rootVisible : false
				})]
			})]
		});

		MyViewportUi.superclass.initComponent.call(this);

		Ext.getCmp('dimLinkDetailTreePanel').setVisible(false);
	}
});


//afterLoad = function(){
//	for(var i=0;i<src_dim_ds.getCount();i++){
//		if(link_id_in_dataconfig == src_dim_ds.getAt(i).get("link_id")){
//			Ext.getCmp("dimLinkGrid").getSelectionModel().selectRow(i);
//			break;
//		}else{
//			continue;
//		}
//	}
//}

