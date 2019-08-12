
	var dbUserWin=null;
	var user_databaseId="";
	/**
	 * 新增数据库授权用户
	 * @param {} database_id 数据库Id
	 */
	function doAddPrivUser(database_id){
     	user_databaseId=database_id;
     	var checkboxSelectionModel = new Ext.grid.CheckboxSelectionModel({
			handleMouseDown : Ext.emptyFn
		});
	
	   //表空间数据源 按数据库查询
		var userStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url :pathUrl+'/uppDatabase/queryUserList'
			}),
			reader : new Ext.data.JsonReader({
				root : 'results',
				totalProperty : 'totalCount',
				id : 'owner_name'
			}, [{
				name : 'database_id'
			}, {
				name : 'owner_name'
			}, {
				name : 'owner_desc'
			},
			 {
				name : 'display_order'
			}, {
				name : 'is_display'
			}
			]),
			remoteSort : true
		});
		var infoColumnModel = new Ext.grid.ColumnModel([checkboxSelectionModel, {
			header : "用户名",
			dataIndex : 'owner_name'
		}
		, {
			header : "用户描述",
			dataIndex : 'owner_desc',
			editor : new Ext.form.TextField({
				allowNegative : false
			})
		}
		, {
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
			title : '添加授权用户信息',
			width : 550,
			height : 450,
			modal:true,
			plain : true,
			border : false,
			layout : 'border',
			// padding:'5px 5px 5px 5px',
			items : [gridPanel = new Ext.grid.EditorGridPanel({
				region : 'center',
				frame : false,
				split : true,
				ds : userStore,
				clicksToEdit: 1,
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
				url : pathUrl + '/uppDatabase/addBatchUppDatabaseUser',
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
								if(array.get('owner_password')==""){
									Ext.MessageBox.alert('错误', '用户密码必须填写！');
									return;
								}
							if (i == 0) {
								infos = array.get('owner_name')+ ','
										+ array.get('owner_desc')+ ','
										+ array.get('is_display')+ ','
										+ array.get('display_order');
							} else {
								infos += "@"
										+  array.get('owner_name')+ ','
										+ array.get('owner_desc')+ ','
										+ array.get('is_display')+ ','
										+ array.get('display_order');
							}
						}
						Ext.getCmp('database_id').setValue(user_databaseId);
						Ext.getCmp('infos').setValue(infos);
						formPanel.form.submit({
							waitMsg : '正在处理，请稍候。。。。。。',
							failure : function(form, action) {
								Ext.MessageBox.alert('错误', action.result.info);
							},
							success : function(form, action) {
								Ext.MessageBox.alert('提示', action.result.info);
								userDS.reload();
								window.destroy();
							}
						});
					} else {
						Ext.MessageBox.alert('错误', '用户密码必须填写！');
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
		
		userStore.load({
			params : {
	            database_id:user_databaseId
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
			for (var i = 0; i < userDS.data.length; i++) {
				if (userDS.data.get(i).data["owner_name"] == '')
					continue;
				var num = userStore.indexOfId(userDS.data.get(i).data["owner_name"].toUpperCase());
	
				numberArray[i] = num;
			}
			gridPanel.getSelectionModel().selectRows(numberArray, true);
		}
		 function refreshInfoDataStore(){
			 if(userDS.getCount()>0){
				 for (var i = 0; i < userDS.getCount(); i++) { 
					 var uppObj = userDS.getAt(i);
					 var db_user_id = uppObj.get('db_user_id');
					 var owner_name = uppObj.get('owner_name');
					 for(var j = 0;j < userStore.getCount();j++){
						 var obj = userStore.getAt(j);
						 if(obj.get('owner_name') == owner_name){
							 userStore.removeAt(j);
							 //obj.set('tablespace_id',tablespace_id);
							 break;
						 }
					 }
				 }
				 for(var j = 0;j < userStore.getCount();j++){
					 var index = j+1;
					 userStore.getAt(j).set('display_order',index);
				 }
				 
				 
			 }
		 }
	 
	}
	
	/**
	 * 修改数据库授权用户
	 * @param {} id 数据库ID
	 * @param {} userName 用户名称
	 */
	function updatePrivUserInfo(id,userName){
	 	dbUserWin = new MyUserWindowUi();	 
	 	//user_databaseId=id;
	 	var form = dbUserWin.get("dbUserForm").form;
		 form.reader= new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id: 'database_id'
        }, [
            {name: 'db_user_id'},
           {name: 'database_id'},
            {name: 'owner_name'},
            {name: 'owner_desc'},
            {name: 'owner_password'},
            {name: 'display_order'},
            {name: 'is_display'}
        ]);
    
    	form.load({url:pathUrl+'/uppDatabase/queryUppDatabaseUserList', params: {db_user_id: id,owner_name:userName}});
    	dbUserWin.show();
	}
	
	/**
	 * 删除数据库授权用户
	 * @param {} basiId 数据库ID
	 * @param {} ownerName 用户名称
	 */
	function deletePrivUserInfo(db_user_id,ownerName){
		Ext.MessageBox.confirm('Message','确认删除选中的用户吗?', 
		function(btn) {
			if(btn == 'yes')
			{
				Ext.Ajax.request({
					url: pathUrl+'/uppDatabase/deleteUppDatabaseUser', //url to server side script
					method: 'POST',
					params: {db_user_id:db_user_id,owner_name:ownerName},//the unique id(s)
					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						if (success) { 
							userDS.reload();
							Ext.MessageBox.alert('确认', json.info);
						} else {
							Ext.MessageBox.alert('错误',json.info);
						
						}
					}
				});
			}
		}
	);
	}
	
	//维护数据库授权用户表单布局	
	MyUserWindowUi = Ext.extend(Ext.Window, {
    id: 'userWin',
    modal:true,
    height: 260,
    width: 545,
    title: '编辑用户信息',
    border:false,
    resizable:false,
    initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                	id:"dbUserForm",
                    xtype: 'form',
                    frame:true,
                    autoHeight: true,
                    autoWidth: true,
                    height: 153,
                    width: 535,
                    border: false,
                    title: '',
                    url:pathUrl+'/uppDatabase/addUppDatabaseUser',
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
										    name: 'db_user_id',
										    xtype: 'hidden',
											allowBlank:false,
											anchor: '95%',
											readOnly:true,
										    fieldLabel: '用户元数据ID'
										},
                                        {
                                            name: 'owner_name',
                                            xtype: 'textfield',
											allowBlank:false,
											anchor: '95%',
											readOnly:true,
                                            fieldLabel: '用户名'
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
                                    style:"padding-left:24px",
                                    labelWidth: 54,
                                    labelAlign:'right',
                                    items: [
                                        {
                                        	name:"owner_desc",
                                            xtype: 'textarea',
                                            height: 53,
                                            width: 408,
                                            fieldLabel: '用户描述'
                                        }
                                    ]
                                },
                                  {
				                    xtype: 'panel',
				                    baseCls: 'x-plain', 
				                    autoWidth: true,
				                    height: 55,
				                     style:"padding-top:10px;",
				                    layout: 'column',
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
				                            	var form = dbUserWin.get("dbUserForm").form;
				                            	if(form.isValid()){
				                            		form.submit({      
										            waitMsg:'正在处理，请稍候。。。。。。',
										            params:{},
										            failure: function(form, action) {
													    Ext.MessageBox.alert('错误', action.result.info);
													},
													success: function(form, action) {
													    Ext.MessageBox.alert('确认', '保存完毕！');
														userDS.reload();
													    dbUserWin.destroy();
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
				                            	 dbUserWin.destroy();
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