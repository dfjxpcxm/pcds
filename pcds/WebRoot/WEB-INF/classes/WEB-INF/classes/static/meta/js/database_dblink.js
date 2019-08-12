
	var dbLinkWin=null;
	var dbLinkBaseId="";
	//新增连接信息
	function doAddLinkInfo(baseId){
     	dbLinkBaseId = baseId;
     		var checkboxSelectionModel = new Ext.grid.CheckboxSelectionModel({
		handleMouseDown : Ext.emptyFn
	});
	
   //表空间数据源
	var InfoDataStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :pathUrl+'/baseConfigAction.do?method=querydbLinkList'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results',
			totalProperty : 'totalCount',
			id : 'dblink_name'
		}, [{
			name : 'database_id'
		}, {
			name : 'dblink_name'
		}, {
			name : 'dblink_desc'
		},
		 {
			name : 'display_order'
		}, {
			name : 'display_flag'
		}
		]),
		remoteSort : true
	});
	var infoColumnModel = new Ext.grid.ColumnModel([checkboxSelectionModel, {
		header : "连接名称",
		dataIndex : 'tablespace_name'
	}, {
		header : "连接描述",
		dataIndex : 'tablespace_desc',
		editor : new Ext.form.TextField({
			allowNegative : false
		})
	}, {
		header : "是否显示",
		dataIndex : 'display_flag',
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
		title : '添加数据库连接信息',
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
			url : pathUrl + '/baseConfigAction.do?method=batchAddDblink',
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
							infos = array.get('dblink_name')+ ','
									+ array.get('dblink_desc')+ ','
									+ array.get('display_flag')+ ','
									+ array.get('display_order');
						} else {
							infos += "@"
									+  array.get('dblink_name')+ ','
									+ array.get('dblink_desc')+ ','
									+ array.get('display_flag')+ ','
									+ array.get('display_order');
						}
					}
					Ext.getCmp('database_id').setValue(dbLinkBaseId);
					Ext.getCmp('infos').setValue(infos);
					formPanel.form.submit({
						waitMsg : '正在处理，请稍候。。。。。。',
						failure : function(form, action) {
							Ext.MessageBox.alert('错误', action.result.info);
						},
						success : function(form, action) {
							Ext.MessageBox.alert('提示', action.result.info);
							linkDS.reload();
							window.destroy();
						}
					});
				} else {
					Ext.MessageBox.alert('错误', '数据库连接的顺序必填！');
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
            database_id:dbLinkBaseId
		},
		callback : function(r,options,success){
			if(success){
				doSelect();
			}
			else{
				Ext.MessageBox.alert("提示","数据库连接失败，请测试数据库是否连接成功");
			}
		}
	});

	
	 function doSelect() {
		var numberArray = new Array();
		for (var i = 0; i < linkDS.data.length; i++) {
			if (linkDS.data.get(i).data["dblink_name"] == '')
				continue;
			var num = InfoDataStore.indexOfId(linkDS.data.get(i).data["dblink_name"]);

			numberArray[i] = num;
		}
		gridPanel.getSelectionModel().selectRows(numberArray, true);
	}
	}
	
	/**
	 * 修改数据库连接信息
	 * @param {} baseId 数据库ID
	 * @param {} linkName 连接名称
	 */
	function updateDbLinkInfo(baseId,linkName){
	 	dbLinkWin = new MyDbLinkWinUi();	 
	 	var form = dbLinkWin.get("dbaseForm").form;
		 form.reader= new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id: 'database_id'
        }, [
           {name: 'database_id'},
            {name: 'dblink_name'},
            {name: 'dblink_desc'},
            {name: 'display_order'},
            {name: 'display_flag'}
        ]);
    // load form and assign value to fields
    	form.load({url:pathUrl+'/baseConfigAction.do?method=queryDbLinkList', params: {database_id: baseId,dblink_name:linkName}});
	}
	
	/**
	 * 删除数据库连接信息
	 * @param {} baseId  数据库ID
	 * @param {} linkName 链接名称
	 */
	function deleteDbLinkInfo(baseId,linkName){
		Ext.MessageBox.confirm('Message','确认删除选中的连接吗?', 
		function(btn) {
			if(btn == 'yes')
			{
				Ext.Ajax.request({
					url: pathUrl+'/baseConfigAction.do?method=deleteDataLink', //url to server side script
					method: 'POST',
					params: {database_id: baseId,dblink_name:linkName},//the unique id(s)
					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						if (success) { 
							linkDS.reload();
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
	
	//数据库连接表单
	MyDbLinkWinUi = Ext.extend(Ext.Window, {
    id: 'linkWin',
    modal:true,
    height: 260,
    width: 545,
    title: '编辑连接信息',
    border:false,
    resizable:false,
    initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                	id:"dbaseForm",
                    xtype: 'form',
                    frame:true,
                    autoHeight: true,
                    autoWidth: true,
                    height: 153,
                    width: 535,
                    border: false,
                    title: '',
                    url:pathUrl+'/baseConfigAction.do?method=addDbLink',
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
                                            name: 'dblink_name',
                                            xtype: 'textfield',
											allowBlank:false,
											anchor: '95%',
											readOnly:true,
                                            fieldLabel: '连接名称'
                                        },
                                        {
                                            xtype: 'combo',
                                            name: 'display_flag',
                                            store: new Ext.data.SimpleStore({
											fields: ["display_flag", "displayText"],
											data: [['1','是'],['2','否']]	}),
											valueField :"display_flag",
											displayField: "displayText",
											hiddenName:'display_flag',
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
                                    style:"padding-left:10px",
                                    labelWidth: 70,
                                    items: [
                                        {
                                        	name:"dblink_desc",
                                            xtype: 'textarea',
                                            height: 53,
                                            width: 408,
                                            fieldLabel: '连接描述'
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
				                            	var form = dbLinkWin.get("dbaseForm").form;
				                            	if(form.isValid()){
				                            		form.submit({      
										            waitMsg:'正在处理，请稍候。。。。。。',
										            params:{database_id : encodeURI(dbLinkBaseId)},
										            failure: function(form, action) {
													    Ext.MessageBox.alert('错误', action.result.info);
													},
													success: function(form, action) {
													    Ext.MessageBox.alert('确认', '保存完毕！');
														linkDS.reload();
													    dbLinkWin.destroy();
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
				                            	 dbLinkWin.destroy();
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

        MyDbLinkWinUi.superclass.initComponent.call(this);
    }
});