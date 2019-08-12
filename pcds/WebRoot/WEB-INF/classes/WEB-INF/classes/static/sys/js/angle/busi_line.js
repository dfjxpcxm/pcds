function busiLineAdd(dataStore){
	  var formPanel = new Ext.form.FormPanel({
	    labelAlign:'top',
        baseCls: 'x-plain',
        labelWidth: 75,
        url:pathUrl+'/angleBusiLine/addBusiLine?moduleCode=0071',

        items: [{
			xtype : 'textfield' ,
			fieldLabel: '业务条线代码',
			allowBlank: false,
			name: 'busi_line_code',
			id: 'busi_line_code',
			anchor: '90%'
		},
		{
			xtype : 'textfield' ,
			fieldLabel: '业务条线名称',
			allowBlank: false,
			name: 'busi_line_name',
			id: 'busi_line_name',
			anchor: '90%'  
		},
			parentLineComboBox=new Ext.form.ComboBox({
					store: dataStore,
					valueField :'busi_line_code',
					displayField:'busi_line_name',
					mode: 'local',
					allowBlank: true,
					editable: false,
					hiddenName:'parent_busi_line_code',
					triggerAction: 'all',
					fieldLabel:'上级业务条线',
					name: 'parentLine_ID',
					id: 'parentLine_ID',
					anchor: '90%' 
		})]
	});

    var window = new Ext.Window({
        title: '添加业务条线',
        width: 400,
        height:300,
        layout:'fit',
        plain:true,
        buttonAlign:'center',
        bodyStyle:'padding:10px;',
        items: formPanel,
        
        buttons: [{
            text: '保存', 
            handler: function() {
                if (formPanel.form.isValid()) 
				{
	 		        formPanel.form.submit({      
			            waitMsg:'正在处理，请稍候。。。。。。',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.results.info);
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('确认', '保存完毕！');
						    window.destroy();
							dataStore.reload();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('错误', '请填写必输项！');
				}             
	        }
        },{
            text: '取消',
            handler: function(){window.destroy();}
        }]
    });

    window.show();

};

function updateBusiLine(dataStore,id) {
	
    var formPanel = new Ext.form.FormPanel({
        baseCls: 'x-plain', 
        labelAlign: 'top',
        labelWidth: 75,
        url:pathUrl+'/angleBusiLine/updateBusiLine?moduleCode=0072',
        
        reader: new Ext.data.JsonReader({
				root: 'results'
			}, [ {name: 'busi_line_code'},
            {name: 'busi_line_name'},
            {name: 'parent_busi_line_code'},
            {name: 'parent_busi_line_name'}]
        ),
        items: [{
			xtype : 'textfield' ,
			fieldLabel: '业务条线代码',
			allowBlank: false,
			name: 'busi_line_code',
			id: 'busi_line_code',
			anchor: '90%',
			readOnly :true
		},
		{
			xtype : 'textfield' ,
			fieldLabel: '业务条线名称',
			allowBlank: false,
			name: 'busi_line_name',
			id: 'busi_line_name',
			anchor: '90%'  
		},
			parentLineComboBox=new Ext.form.ComboBox({
					store: dataStore,
					valueField :'busi_line_code',
					displayField:'busi_line_name',
					mode: 'local',
					allowBlank: true,
					editable: false,
					hiddenName:'parent_busi_line_code',
					triggerAction: 'all',
					fieldLabel:'上级业务条线',
					name: 'parentLine_ID',
					id: 'parentLine_ID',
					anchor: '90%' 
		}),{
			xtype : 'hidden' ,
			fieldLabel: '业务条线代码',
			allowBlank: false,
			name: 'parent_busi_line_code',
			id: 'parent_busi_line_code',
			anchor: '90%'
		},
		{
			xtype : 'hidden' ,
			fieldLabel: '业务条线名称',
			allowBlank: false,
			name: 'parent_busi_line_name',
			id: 'parent_busi_line_name',
			anchor: '90%'  
		}]
	});

    // load form and assign value to fields
    formPanel.form.load({url:pathUrl+'/angleBusiLine/queryBusiLineByCode', params: {busi_line_code: id},
	    	success:function(){ 

			    		parentLineComboBox.setValue(Ext.get('parent_busi_line_code').getValue());
					  	parentLineComboBox.setRawValue(Ext.get('parent_busi_line_name').getValue());			
			    		
			    	}
  	
    	});     

    // define window and show it in desktop
    var window = new Ext.Window({
		title: '业务条线维护',
        width: 400,
        height:300,
        layout:'fit',
        plain:true,
        buttonAlign:'center',
        bodyStyle:'padding:10px;',
        items: formPanel,

        buttons: [{
            text: '保存', 
            handler: function() {
                if (formPanel.form.isValid()) 
				{
	 		        formPanel.form.submit({		
	 		            //params:{product_id : rid;},	      
			            waitMsg:'正在处理，请稍候。。。。。。',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.result.info);
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('确认', '保存完毕！');
						    window.destroy();
							dataStore.reload();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('错误', '请填写必输项！');
				}             
	        }
        },{
            text: '取消',
            handler: function(){window.destroy();}
        }]
    });

    window.show();
};

function deleteBusiLine(dataStore,productDataStore,id){
	Ext.MessageBox.confirm('提示信息','确认删除选中的业务条线吗?', 
		function(btn) {
			if(btn == 'yes')
			{
				Ext.Ajax.request({
					url: pathUrl+'/angleBusiLine/deleteBusiLine?moduleCode=0073', //url to server side script
					method: 'POST',
					params: {busi_line_code: id},//the unique id(s)

					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						if (success) { 
							Ext.MessageBox.alert('确认', json.info);
							dataStore.reload();
							productDataStore.reload();
						} else {
							Ext.MessageBox.alert('错误',json.info);
						
						}
					}
				});
			}
		}
	);
};


function doSelectBusiLine(id,productDataStore){
	productDataStore.load({params:{busi_line_code:id}});
}

function busiLineAssociateInfo(dataStore,id,busi_line_name,infoStore){
	var checkboxSelectionModel = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	var infoColumnModel = new Ext.grid.ColumnModel([
        checkboxSelectionModel,
				{
            header: "产品代码",
            dataIndex: 'product_id'
        },{
            header: "产品名称", 
            dataIndex: 'product_name'
        }
    ]);
    
    var InfoDataStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: pathUrl+'/angleBusiLine/queryProduct'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id:'product_id'
        }, [
            {name: 'product_id'},
			{name: 'product_name'}
        ]),
        remoteSort: true
    });
    
	  var gridPanel;
    var formPanel;
    var window = new Ext.Window({
        title: '产品设定',
        width: 500,
        height:500,
        plain:true,
        border:false,
        layout:'border',
        items: [
        	gridPanel=new Ext.grid.GridPanel({
					region:'center',
					frame:false,	 
					split:true,
					ds: InfoDataStore,
					cm: infoColumnModel,	
					sm: checkboxSelectionModel,
					autoScroll: true,
					loadMask:true,
					viewConfig: {forceFit:true}
				}),
			formPanel  = new Ext.form.FormPanel({
				region:'south',
				frame:false,	 
				split:true,
		        baseCls: 'x-plain',
		        height:100,
		        url:pathUrl+'/angleBusiLine/saveBusiLineProduct?moduleCode=0074',
		        buttonAlign:'center',
		        
		        reader: new Ext.data.JsonReader({
						root: 'results'
					}, [
					{name: 'infos'}]
		        ),
		        items: [{
		        	xtype:'fieldset',
					title: '业务条线名称',
					collapsible: false,
					defaultType: 'textfield',		
					height:75,
					items:[{
							hideLabel:true,
							allowBlank: false,
							id:'busi_line_name',
							name: 'busi_line_name',
							value:busi_line_name ,
							readOnly:true,
							anchor: '90%'
							},
							{
								xtype:'hidden',
								name:'infos',
								id:'infos'
							}]
					}],	

        
		        buttons: [{
		            text: '保存', 
		            handler: function() {
		                if (formPanel.form.isValid()) 
						{	
							var infos="";
							for(var i=0;i<gridPanel.getSelectionModel().getSelections().length;i++)
							{
								if(i==0)
									infos = gridPanel.getSelectionModel().getSelections()[i].get('product_id');
								else
									infos += "@"+gridPanel.getSelectionModel().getSelections()[i].get('product_id');
							}
							Ext.getCmp('infos').setValue(infos);
							
			 		        formPanel.form.submit({      
					            waitMsg:'正在处理，请稍候。。。。。。',
					            params:{busi_line_code:id},
					            failure: function(form, action) {
								    Ext.MessageBox.alert('错误', action.result.info);
								},
								success: function(form, action) {
								    Ext.MessageBox.alert('确认', '保存完毕！');
								    window.destroy();
									dataStore.reload();
									infoStore.reload({params:{busi_line_code:id} } );
								   }
					        });                   
		                } else{
							Ext.MessageBox.alert('错误', '请填写必输项！');
						}             
			        }
		        },{
		            text: '取消',
		            handler: function(){window.destroy();}
		        }]
          })
        ]
    });

    window.show();
    
    var gridLoadState=false,formLoadState=false;

    formPanel.form.on('actioncomplete', formLoaded);
    formPanel.form.load({url:pathUrl+'/angleBusiLine/queryBusiLineProduct', params: {busi_line_code: id} });

	InfoDataStore.load({callback:gridLoaded});

	function gridLoaded()
	{
		gridLoadState=true;
		if(formLoadState)
			doSelect();
	}

	function formLoaded()
	{
		formLoadState=true;
		if(gridLoadState)
			doSelect();
	}

	function doSelect()
	{
		var infos=Ext.getCmp("infos").getValue();
		
		if(infos==""||infos == null)
			return;
		var infoArray=infos.split('@');
		var numberArray=new Array();
		if(infoArray.length>0){
			for(var i=0;i<infoArray.length;i++)
			{
				var num=InfoDataStore.indexOfId(infoArray[i]);
				numberArray[i]=num;
			}
		}

		gridPanel.getSelectionModel().selectRows(numberArray);
	}
	
	 
};
