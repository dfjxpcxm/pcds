
	var win=null;
	//新增数据库信息
	function doAddDataBase(){
		databaseId = '';
     	win = new MyDbaseWindowUi();
     	win.show();
	}
	
	var databaseId="";
	//修改数据库信息，传入数据库ID
	function updateBaseInfo(id){
		databaseId = id;
	 	win = new MyDbaseWindowUi();	 
	 	var form = win.get("dbaseForm").form;
		 form.reader= new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id: 'database_id'
        }, [
           {name: 'database_id'},
            {name: 'database_name'},
            {name: 'database_ip'},
            {name: 'username'},
            {name: 'password'},
            {name: 'conn_type'},
            {name: 'access_post'},
            {name: 'config_date'},
            {name: 'encoding'},
            {name: 'conn_url'},
            {name: 'database_type_id'},
            {name: 'database_desc'},
            {name: 'display_order'},
            {name: 'is_display'}
        ]);
    // load form and assign value to fields
    	form.load({url:pathUrl+'/uppDatabase/queryUppDatabaseList', params: {database_id: id}});
       win.show();
	}
	
	//删除数据库信息
	function deleteBaseInfo(basiId){
		Ext.MessageBox.confirm('Message','删除数据库将级联删除对应的表空间和用户，确认执行删除操作吗?', 
		function(btn) {
			if(btn == 'yes')
			{
				Ext.Ajax.request({
					url: pathUrl+'/uppDatabase/deleteUppDatabase', //url to server side script
					method: 'POST',
					params: {database_id: basiId},//the unique id(s)

					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						if (success) { 
							baseDS.reload();
							tablespaceDS.reload({params:{database_id:basiId}});
    						userDS.reload({params:{database_id:basiId}});
    						//linkDS.reload({params:{database_id:basiId}});
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
	
	/**
	 * 数据库类型
	 */
		/*var dataBaseStore=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:pathUrl+'/uppDatabase/queryUppDatabaseType'
		}),
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [{
			name : 'database_type_id',
			mapping : 'database_type_id'
		}, {
			name : 'database_type_name',
			mapping : 'database_type_name'
		}]),
		remoteSort : false
	   });
	   dataBaseStore.load();*/
	   
	   var owner_name="";
	   var owner_password="";
	//维护数据库信息表单
MyDbaseWindowUi = Ext.extend(Ext.Window, {
    id: 'baseWin',
    modal:true,
    height:350,
    width: 545,
    title: '新增数据库',
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
                    height: 400,
                    width: 535,
                    border: false,
                    title: '',
                    url:pathUrl+'/uppDatabase/addUppDatabase',
                    items: [
                        {
                            xtype: 'panel',
                            autoHeight: true,
                            height: 280,
                            width: 522,
                            layout: 'column',
                            border: false,
                            title: '',
                            items: [
                                {
                                    xtype: 'panel',
                                    autoHeight: true,
                                    height: 230,
                                    width: "50%",
                                    layout: 'form',
                                    border: false,
                                    padding: 10,
                                    title: '',
                                    labelAlign:"right",
                                    labelWidth: 70,
                                    items: [
                                        {
                                            name: 'database_name',
                                            xtype: 'textfield',
											allowBlank:false,
											anchor: '95%',
                                            fieldLabel: '数据库名称'
                                        },new UppDatabaseTypeSelector(),
                                        /*{
                                            xtype: 'combo',
                                            id:"database_type",
                                            name: 'database_type_id',
                                            store: dataBaseStore,
											valueField :"database_type_id",
											displayField: "database_type_name",
											hiddenName:'database_type_id',
											autoSelect:true,
											mode: 'local',
											triggerAction : "all",
											allowBlank:false,
                                            height: 50,
                                            editable: false,
                                            anchor: '95%',
                                            fieldLabel: '数据库类型',
                                            listeners:{
                                            	select:function(cmbo,record,index){
                                            		win.get("dbaseForm").form.findField("connUrl").focus();
                                            	}
                                            }
                                        },*/
                                         {
                                            xtype: 'combo',
                                            name: 'conn_type',
                                            store: new Ext.data.SimpleStore({
															fields: ["conn_type", "conn_type_desc"],
															data: [['1','数据库']]	}),//,['2','SSH']
											valueField :"conn_type",
											displayField: "conn_type_desc",
											hiddenName:'conn_type',
											autoSelect:true,
											mode: 'local',
											triggerAction : "all",
											allowBlank:false,
                                            height: 50,
                                            editable: false,
                                            anchor: '95%',
                                            value:'1',
                                            fieldLabel: '连接方式'
                                        },
                                         {
                                            id: 'username',
                                            name: 'username',
                                            xtype: 'textfield',
											anchor: '95%',
											allowBlank:false,
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
                                            value:'1',
                                            fieldLabel: '是否显示'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    autoHeight: true,
                                    height: 280,
                                    width: "50%",
                                    layout: 'form',
                                    border: false,
                                    padding: 10,
                                    title: '',
                                    labelWidth: 60,
                                    items: [
                                        {
                                        	name:"database_ip",
                                            xtype: 'textfield',
                                            anchor: '95%',
                                            allowBlank:false,
                                            fieldLabel: '数据库IP'
                                        },
                                          {
                                        	name:"access_post",
                                            xtype: 'textfield',
                                            anchor: '95%',
                                            allowBlank:false,
                                            fieldLabel: '访问端口'
                                        },
                                         {
                                            xtype: 'combo',
                                            name: 'encoding',
                                            store: new Ext.data.SimpleStore({
											fields: ["encoding", "encoding_desc"],
											data: [['utf-8','utf-8'],['gbk','gbk'],['gb2312','gb2312'],['unicode','unicode'],['ascii','ascii'],['gb18030','gb18030']]}),
											valueField :"encoding",
											displayField: "encoding_desc",
											hiddenName:'encoding',
											autoSelect:true,
											mode: 'local',
											triggerAction : "all",
											allowBlank:false,
                                            height: 50,
                                            editable: false,
                                            anchor: '95%',
                                            value:'utf-8',
                                            fieldLabel: '字符编码'
                                        },
                                         {
                                            id: 'password',
                                            name: 'password',
                                             inputType:"password",
                                            xtype: 'textfield',
											anchor: '95%',
											allowBlank:false,
                                            fieldLabel: '用户密码'
                                        },
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
                                   // autoWidth: true,
                                    height: 120,
                                    width: 531,
                                    layout: 'form',
                                    border: false,
                                    title: '',
                                    padding:10,
                                    //style:"padding-left:10px;padding-top:10px",
                                    labelWidth: 70,
                                    items: [
                                     {
                                        	name:"conn_url",
                                        	id:"connUrl",
                                            xtype: 'textfield',
											anchor: '95%',
                                            fieldLabel: '连接字符串',
                                            listeners:{
                                            	focus:function(){
	                                            	var form = win.get("dbaseForm").form;
	                                            	var b1=form.findField("database_name").validate() ;
	                                            	var b2=form.findField("database_ip").validate() ;
	                                            	var b3=form.findField("database_type_id").validate();
	                                            	var b4=form.findField("access_post").validate();
	                                            	var datatype = form.findField("database_type_id").getRawValue();
	                                            	var database_name = form.findField("database_name").getValue();
	                                            	var post = form.findField("access_post").getValue();
					                            	var database_ip = form.findField("database_ip").getValue();
					                            	var url="";
					                            	if(datatype=='oracle' && b1 && b2 && b3 && b4){
					                            		url="jdbc:oracle:thin:@"+database_ip+":"+post+":"+database_name;
					                            	}
					                            	else if(datatype=='db2' && b1 && b2 && b3 && b4){
					                            		url= "jdbc:db2://"+database_ip+":"+post+"/"+database_name;
					                            	}
					                            	form.findField("connUrl").setValue(url);
                                            	}
                                            }
                                        },
                                        {
                                        	name:"database_desc",
                                            xtype: 'textarea',
                                            height: 60,
                                            width: 408,
                                            fieldLabel: '数据库描述'
                                        }
                                    ]
                                }/*,
                                  {
				                    xtype: 'panel',
				                    baseCls: 'x-plain', 
				                    autoWidth: true,
				                    height: 60,
				                    layout: 'column',
				                    border: false,
				                    title: '',
				                    items: [
				                        {
				                            xtype: 'button',
				                            //style: 'margin-left:230px;',
				                            height: 25,
				                            width: 66,
				                            text: '保存',
				                            handler:function(){
				                            	var form = win.get("dbaseForm").form;
				                            	if(form.isValid()){
				                            		form.submit({      
										            waitMsg:'正在处理，请稍候。。。。。。',
										            params:{database_id : encodeURI(databaseId)},
										            failure: function(form, action) {
													    Ext.MessageBox.alert('错误', action.result.info);
													},
													success: function(form, action) {
													    Ext.MessageBox.alert('确认', '保存完毕！');
													   // var databaseId=action.result.dataid;
														baseDS.reload();
														tablespaceDS.reload();
    													userDS.reload();
    													//linkDS.reload({params:{database_id:databaseId}});
													    win.destroy();
													    
													}
										       	 });   
				                            	} 
				                            	else{
													Ext.MessageBox.alert('错误', '请填写必输项！');
												}  
				                            }
				                        },
				                        {
				                        	id:"btnTest",
				                            xtype: 'button',
				                            style: 'margin-left:10px;',
				                            height: 25,
				                            width: 66,
				                            text: '测试',
				                            handler:function(){
				                            	var form = win.get("dbaseForm").form;
				                            	var database_name = form.findField("database_name").getValue();
				                            	var database_ip = form.findField("database_ip").getValue();
				                            	var name = form.findField("username").getValue();
				                            	var pwd = form.findField("password").getValue();
				                            	var access_post = form.findField("access_post").getValue();
				                            	var database_type = form.findField("database_type_id").getValue();
												 Ext.MessageBox.show({   
											                  msg : '测试中，请稍后...',                 // progressText : '保存中...',   
											                  width : 300,   
											                  wait : true,   
											                  progress : true,   
											                  closable : true,   
											                  waitConfig : {   
											                       interval : 200  
											                  },   
											                  icon : Ext.Msg.INFO   
											               });  
				                            	 Ext.Ajax.request({
															url: pathUrl+'/uppDatabase/testJdbcConn', //url to server side script
															method: 'POST',
															sync:true,
															params: {database_name:database_name,database_ip:database_ip,database_type_id:database_type,username:name,password:pwd,access_post:access_post},//the unique id(s)
															callback: function (options, success, response) {
																var json=Ext.util.JSON.decode(response.responseText);
																if (success) { 
																	Ext.MessageBox.alert('确认', json.info);
																} else {
																	Ext.MessageBox.alert('错误',json.info);
																}
															}
														});
				                            }
				                        },
				                        {
				                            xtype: 'button',
				                            style: 'margin-left:10px;',
				                            height: 25,
				                            width: 66,
				                            text: '取消',
				                            handler:function(){
				                            	 win.destroy();
				                            }
				                        }
				                    ]
                }*/
                            ]
                        }
                    ],
                    buttonAlign:'center',
                    buttons:[{
            			text : '保存',
            			id : 'save_',
            			handler : function() {
                        	var form = win.get("dbaseForm").form;
                        	if(form.isValid()){
                        		form.submit({      
					            waitMsg:'正在处理，请稍候。。。。。。',
					            params:{database_id : encodeURI(databaseId)},
					            failure: function(form, action) {
								    Ext.MessageBox.alert('错误', action.result.info);
								},
								success: function(form, action) {
								    Ext.MessageBox.alert('确认', '保存完毕！');
								   // var databaseId=action.result.dataid;
									baseDS.reload();
									tablespaceDS.reload();
									userDS.reload();
									//linkDS.reload({params:{database_id:databaseId}});
								    win.destroy();
								    
								}
					       	 });   
                        	} 
                        	else{
								Ext.MessageBox.alert('错误', '请填写必输项！');
							}  
            				
            			}
            
            		},{
            			text : '测试',
            			id : 'test',
            			handler : function() {

                        	var form = win.get("dbaseForm").form;
                        	var database_name = form.findField("database_name").getValue();
                        	var database_ip = form.findField("database_ip").getValue();
                        	var name = form.findField("username").getValue();
                        	var pwd = form.findField("password").getValue();
                        	var access_post = form.findField("access_post").getValue();
                        	var database_type = form.findField("database_type_id").getValue();
							 Ext.MessageBox.show({   
						                  msg : '测试中，请稍后...',                 // progressText : '保存中...',   
						                  width : 300,   
						                  wait : true,   
						                  progress : true,   
						                  closable : true,   
						                  waitConfig : {   
						                       interval : 200  
						                  },   
						                  icon : Ext.Msg.INFO   
						               });  
                        	 Ext.Ajax.request({
										url: pathUrl+'/uppDatabase/testJdbcConn', //url to server side script
										method: 'POST',
										sync:true,
										params: {database_name:database_name,database_ip:database_ip,database_type_id:database_type,username:name,password:pwd,access_post:access_post},//the unique id(s)
										callback: function (options, success, response) {
											var json=Ext.util.JSON.decode(response.responseText);
											if (success) { 
												Ext.MessageBox.alert('确认', json.info);
											} else {
												Ext.MessageBox.alert('错误',json.info);
											}
										}
									});
                        
            				
            			}
            
            		},{
            			text : '取消',
            			id : 'cancel',
            			handler : function() {
            				if(win){
            					win.destroy();
            				}else{
            					return;
            				}
            			}
            
            		}
                             
                             ]
                    
                }
              
            ]
        ,
        listeners:{
        	close:function(){win.destroy();}
        }
        
        }
        
        
        );

        MyDbaseWindowUi.superclass.initComponent.call(this);
    }
});
	
	
	