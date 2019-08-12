function doSelectAppType(id,termDataStore){
	termDataStore.load({params:{app_type_code:id}});
}

function appTypeAssociateInfo(dataStore,id,appTypeName,infoStore){
	var checkboxSelectionModel = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	var infoColumnModel = new Ext.grid.ColumnModel([
        checkboxSelectionModel,
				{
            header: "期限代码",
            dataIndex: 'terms_id'
        },{
            header: "期限名称", 
            dataIndex: 'terms_name'
        }
    ]);
    
    var InfoDataStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: pathUrl+'/angleAppTypeTerm/queryTerms'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id:'terms_id'
        }, [
            {name: 'terms_id'},
			{name: 'terms_name'}
        ]),
        remoteSort: true
    });
    
	  var gridPanel;
    var formPanel;
    var window = new Ext.Window({
        title: '期限设定',
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
		        url:pathUrl+'/angleAppTypeTerm/saveAppTypeTerm?moduleCode=0075',
		        buttonAlign:'center',
		        
		        reader: new Ext.data.JsonReader({
						root: 'results'
					}, [
					{name: 'infos'}]
		        ),
		        items: [{
		        	xtype:'fieldset',
					title: '产品名称',
					collapsible: false,
					defaultType: 'textfield',		
					height:75,
					items:[{
							hideLabel:true,
							allowBlank: false,
							id:'app_type_name',
							name: 'app_type_name',
							value:appTypeName ,
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
									infos = gridPanel.getSelectionModel().getSelections()[i].get('terms_id');
								else
									infos += "@"+gridPanel.getSelectionModel().getSelections()[i].get('terms_id');
							}
							Ext.getCmp('infos').setValue(infos);
							
			 		        formPanel.form.submit({      
					            waitMsg:'正在处理，请稍候。。。。。。',
					            params:{app_type_code:id},
					            failure: function(form, action) {
								    Ext.MessageBox.alert('错误', action.result.info);
								},
								success: function(form, action) {
								    Ext.MessageBox.alert('确认', '保存完毕！');
								    window.destroy();
									dataStore.reload();
									infoStore.reload({params:{app_type_code:id} } );
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
    formPanel.form.load({url:pathUrl+'/angleAppTypeTerm/queryAppTypeTerm', params: {app_type_code: id} });

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
