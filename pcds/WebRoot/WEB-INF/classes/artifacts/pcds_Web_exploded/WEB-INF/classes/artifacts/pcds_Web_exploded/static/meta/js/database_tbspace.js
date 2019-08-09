
	var tbspacewin=null;
	var database_id ="";
	 var  spaceGridDS=null;
	/**
	 * 新增表空间
	 * @param {} dbid 数据库ID
	 */
	function doAddTabSpace(dbid){
		//表空间数据源
    	database_id=dbid;
  
		var checkboxSelectionModel = new Ext.grid.CheckboxSelectionModel({
			handleMouseDown : Ext.emptyFn
		});
	
		//表空间数据源  从选中的数据库中查询出来
		var InfoDataStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url :pathUrl+'/uppDatabase/queryTablespaceList'
			}),
	
			reader : new Ext.data.JsonReader({
				root : 'results',
				totalProperty : 'totalCount',
				id : 'tablespace_name'
			}, [{
				name : 'database_id'
			}, {
				name : 'tablespace_name'
			}, {
				name : 'tablespace_desc'
			},
			 {
				name : 'display_order'
			}, {
				name : 'is_display'
			}, {
				name : 'tablespace_id'
			}
			]),
			remoteSort : true
		});
		var infoColumnModel = new Ext.grid.ColumnModel([checkboxSelectionModel, {
			header : "表空间名称",
			dataIndex : 'tablespace_name'
		}, {
			header : "表空间描述",
			dataIndex : 'tablespace_desc',
			editor : new Ext.form.TextField({
				allowNegative : false
			})
		}, {
			header : "是否显示",
			dataIndex : 'is_display',
			renderer:function(v){
				if(v == "1"){
					return "是";
				}
				return "否";
			}
		}, {
			header : "显示顺序",
			dataIndex : "display_order",
			align : 'display_order',
			editor : new Ext.form.NumberField({
				allowBlank : false,
				allowNegative : false
			})
		}]);
	
		var gridPanel;
		var formPanel;
		var window = new Ext.Window({
			title : '添加表空间信息',
			width : 550,
			height : 450,
			plain : true,
			border : false,
			modal:true,
			layout : 'border',
			// padding:'5px 5px 5px 5px',
			items : [gridPanel = new Ext.grid.EditorGridPanel({
				region : 'center',
				frame : false,
				split : true,
				clicksToEdit: 1,
				ds : InfoDataStore,
				cm : infoColumnModel,
				sm : checkboxSelectionModel,
				autoScroll : true,
				loadMask : true,
				viewConfig : {
					forceFit : true
				}
			}), formPanel = new Ext.form.FormPanel({
				region : 'north',
				frame : false,
				split : true,
				baseCls : 'x-plain',
				url : pathUrl + '/uppDatabase/addBatchUppTablespace',
				buttonAlign : 'center',
				items : [{
					xtype : "hidden",
					id : "database_id",
					name : "database_id"
				}, {
					xtype : 'hidden',
					id : 'infos',
					name : 'infos'
				}]
			})],
	
			buttons : [{
				text : '保存',
				handler : function() {
					if (formPanel.form.isValid()) {
						var infos = "";
						if(gridPanel.getSelectionModel().getSelections().length <=0){
							Ext.MessageBox.alert('错误', "没有数据，不能保存");
							return ;
						}
						for (var i = 0; i < gridPanel.getSelectionModel()
								.getSelections().length; i++) {
								var array = gridPanel.getSelectionModel().getSelections()[i];
							if (i == 0) {
								infos = array.get('tablespace_name')+ ','
										+ array.get('tablespace_desc')+ ','
										+ array.get('is_display')+ ','
										+ array.get('display_order');
							} else {
								infos += "@"
										+  array.get('tablespace_name')+ ','
										+ array.get('tablespace_desc')+ ','
										+ array.get('is_display')+ ','
										+ array.get('display_order');
							}
						}
						Ext.getCmp('database_id').setValue(dbid);
						Ext.getCmp('infos').setValue(infos);
						formPanel.form.submit({
							waitMsg : '正在处理，请稍候。。。。。。',
							failure : function(form, action) {
								Ext.MessageBox.alert('错误', action.result.info);
							},
							success : function(form, action) {
								Ext.MessageBox.alert('提示', action.result.info);
								tablespaceDS.reload();
								window.destroy();
							}
						});
					} else {
						Ext.MessageBox.alert('错误', '表空间的顺序必填！');
					}
				}
			}, {
				text : '取消',
				handler : function() {
					window.destroy();
				}
			}],
			buttonAlign : 'center'
		});
		
		window.show();
	
		InfoDataStore.load({
		params : {
            database_id:database_id
		},
		callback : function(r,options,success){
			if(success){
				//doSelect();
				refreshInfoDataStore();
			}
			else{
				Ext.MessageBox.alert("提示","数据库连接失败，请测试数据库是否连接成功");
			}
		}
	});

	
	 function doSelect() {
		var numberArray = new Array();
		for (var i = 0; i < tablespaceDS.data.length; i++) { 
			if (tablespaceDS.data.get(i).data["tablespace_name"] == ''){
				continue;
			}
			var num = InfoDataStore.indexOfId(tablespaceDS.data.get(i).data["tablespace_name"]);

			numberArray[i] = num;
		}
		 
		gridPanel.getSelectionModel().selectRows(numberArray, true);
	}
	 
	 function refreshInfoDataStore(){
		 if(tablespaceDS.getCount()>0){
			 for (var i = 0; i < tablespaceDS.getCount(); i++) { 
				 var uppObj = tablespaceDS.getAt(i);
				 var tablespace_id = uppObj.get('tablespace_id');
				 var tablespace_name = uppObj.get('tablespace_name');
				 for(var j = 0;j < InfoDataStore.getCount();j++){
					 var obj = InfoDataStore.getAt(j);
					 if(obj.get('tablespace_name') == tablespace_name){
						 InfoDataStore.removeAt(j);
						 //obj.set('tablespace_id',tablespace_id);
						 break;
					 }
				 }
			 }
			 for(var j = 0;j < InfoDataStore.getCount();j++){
				 var index = j+1;
				 InfoDataStore.getAt(j).set('display_order',index);
			 }
			 
			 
		 }
	 }

	}
	
	/**
	 * 修改表空间信息
	 * @param {} tbname 表空间名称
	 * @param {} dbid  数据库ID
	 */
	function updateTabSpaceInfo(tbname,tablespace_id){
	 	tbspacewin = new MyWindowUi();	 
	 	//database_id = dbid;
	 	var form = tbspacewin.get("dbSpaceForm").form;
		 form.reader= new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id: 'tablespace_id'
        }, [
            {name: 'tablespace_id'},
            {name: 'database_id'},
            {name: 'tablespace_name'},
            {name: 'tablespace_desc'},
            {name: 'display_order'},
            {name: 'is_display'}
        ]);
    // load form and assign value to fields
    	form.load({url:pathUrl+'/uppDatabase/queryUppTablespaceList', params: {tablespace_id:tablespace_id,tablespace_name:tbname}});
   		
    	tbspacewin.show();
	}
	
	/**
	 * 删除数据库表空间
	 * @param {} spaceName 表空间名称
	 * @param {} basiId 表空间ID
	 */
	function deleteTabSpaceInfo(spaceName ,tablespace_id){
		Ext.MessageBox.confirm('Message','确认删除选中的表空间吗?', 
		function(btn) {
			if(btn == 'yes')
			{
				Ext.Ajax.request({
					url: pathUrl+'/uppDatabase/deleteUppTablespace', //url to server side script
					method: 'POST',
					params: {tablespace_id: tablespace_id,tablespace_name:spaceName},//the unique id(s)

					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						if (success) { 
							Ext.MessageBox.alert('确认', json.info);
							tablespaceDS.reload();
						} else {
							Ext.MessageBox.alert('错误',json.info);
						
						}
					}
				});
			}
		}
	);
	}
	

	
	//维护数据库表空间信息表单
MyWindowUi = Ext.extend(Ext.Window, {
    id: 'dbaseWin',
    modal:true,
    height: 260,
    width: 545,
    title: '编辑表空间',
    border:false,
    resizable:false,
    initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                	id:"dbSpaceForm",
                    xtype: 'form',
                    frame:true,
                    autoHeight: true,
                    autoWidth: true,
                    height: 153,
                    width: 535,
                    border: false,
                    title: '',
                    url:pathUrl+'/uppDatabase/addUppTablespace',
                    items: [
                        {
                            xtype: 'panel',
                            autoHeight: true,
                            height: 155,
                            width: 522,
                            layout: 'column',
                            border: false,
                            title: '',
                            items: [
                                {
                                    xtype: 'panel',
                                    autoHeight: true,
                                    height: 71,
                                    width: "50%",
                                    layout: 'form',
                                    border: false,
                                    padding: 10,
                                    title: '',
                                    labelAlign:"right",
                                    labelWidth: 70,
                                    items: [
                                        {
                                            name: 'tablespace_name',
                                            xtype: 'textfield',
											allowBlank:false,
											anchor: '95%',
											readOnly:true,
                                            fieldLabel: '表空间名称'
                                        },
                                        {
                                            xtype: 'combo',
                                            name: 'is_display',
                                            store: new Ext.data.SimpleStore({
											fields: ["is_display", "displayText"],
											data: [['1','是'],['0','否']]	}),
											valueField :"is_display",
											displayField: "displayText",
											hiddenName:'is_display',
											autoSelect:true,
											mode: 'local',
											triggerAction : "all",
											allowBlank:false,
                                            height: 50,
                                            editable: false,
                                            anchor: '95%',
                                            fieldLabel: '是否显示'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    autoHeight: true,
                                    height: 72,
                                    width: "50%",
                                    layout: 'form',
                                    border: false,
                                    padding: 10,
                                    title: '',
                                    labelWidth: 60,
                                    items: [
                                        {
                                        	name:"display_order",
                                            xtype: 'numberfield',
                                            anchor: '95%',
                                            allowBlank:false,
                                            fieldLabel: '显示顺序'
                                        },
                                        {
                                        	name:"tablespace_id",
                                            xtype: 'hidden',
                                            anchor: '95%',
                                            allowBlank:false,
                                            fieldLabel: '表空间ID'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    autoScroll: true,
                                    autoWidth: true,
                                    height: 63,
                                    width: 531,
                                    layout: 'form',
                                    border: false,
                                    title: '',
                                    style:"padding-left:10px",
                                    labelWidth: 70,
                                    items: [
                                        {
                                        	name:"tablespace_desc",
                                            xtype: 'textarea',
                                            height: 53,
                                            width: 408,
                                            fieldLabel: '表空间描述'
                                        }
                                    ]
                                },
                                  {
				                    xtype: 'panel',
				                    baseCls: 'x-plain', 
				                    autoWidth: true,
				                    height: 55,
				                    layout: 'column',
				                     style:"padding-top:10px;",
				                    border: false,
				                    title: '',
				                    items: [
				                        {
				                            xtype: 'button',
				                            style: 'margin-left:260px;',
				                            height: 25,
				                            width: 66,
				                            text: '保存',
				                            handler:function(){
				                            	var form = tbspacewin.get("dbSpaceForm").form;
				                            	
				                            	if(form.isValid()){
				                            		form.submit({      
										            waitMsg:'正在处理，请稍候。。。。。。',
										            params:{database_id : encodeURI(database_id)},
										            failure: function(form, action) {
													    Ext.MessageBox.alert('错误', action.result.info);
													},
													success: function(form, action) {
													    Ext.MessageBox.alert('确认', '保存完毕！');
														tablespaceDS.reload();
													    tbspacewin.destroy();
													}
										       	 });   
				                            	} 
				                            	else{
													Ext.MessageBox.alert('错误', '请填写必输项！');
												}  
				                            }
				                        },
				                        {
				                            xtype: 'button',
				                            style: 'margin-left:10px;',
				                            height: 25,
				                            width: 66,
				                            text: '取消',
				                            handler:function(){
				                            	 tbspacewin.destroy();
				                            }
				                        }
				                    ]
                }
                            ]
                        }
                    ]
                }
              
            ]
        });

        MyWindowUi.superclass.initComponent.call(this);
    }
});