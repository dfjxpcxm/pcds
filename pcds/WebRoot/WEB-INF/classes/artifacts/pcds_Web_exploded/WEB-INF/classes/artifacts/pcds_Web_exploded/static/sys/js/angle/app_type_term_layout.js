Ext.onReady(function(){
	var appTypeColumnModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
				{
					  id: 'app_type_code',
            header: "应用类型代码",
            dataIndex: 'app_type_code'
        },{
            header: "应用类型名称", 
            dataIndex: 'app_type_name'
        }
    ]);
    
    var appTypeStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: pathUrl+'/angleAppTypeTerm/queryAppType'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id: 'app_type_code'
        }, [
            {name: 'app_type_code'},
						{name: 'app_type_name'}
        ]),
        remoteSort: true
    });
 
  	
    var termColumnModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
				{
            header: "期限代码",
            dataIndex: 'terms_id'
        },{
            header: "期限名称", 
            dataIndex: 'terms_name'
        }
    ]); 
    
	var termDataStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: pathUrl+'/angleAppTypeTerm/queryAppTypeTerm'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id: 'termsID'
        }, [
             {name: 'terms_id'},
						 {name: 'terms_name'}
        ])
    });
	//termDataStore.load();

	var termMenubar = [{
        text:'期限设定',
        iconCls:'edit',
        tooltip:'期限设定',
        handler: function(){
            if(appTypeGridPanel.getSelectionModel().getSelections().length == 0)
						{
							Ext.MessageBox.alert('错误', 
								'请选择一条记录，继续进行！'
							);
							return;
						}

						var id=appTypeGridPanel.getSelectionModel().getSelections()[0].get('app_type_code');
						var appTypeName=appTypeGridPanel.getSelectionModel().getSelections()[0].get('app_type_name');
			      appTypeAssociateInfo(appTypeStore,id,appTypeName,termDataStore);
        }
   }];

	var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			appTypeGridPanel=new Ext.grid.GridPanel({
				region:'west',
				contentEl:'west',
				width:500,
				title:'应用类型列表',
				frame:false,
				split:true,
				ds: appTypeStore,
				cm: appTypeColumnModel,	
				viewConfig: {forceFit:true},	                       
				autoScroll: true,
				autoExpandColumn:2,
				loadMask: true,
				bbar: new Ext.PagingToolbar({
            		pageSize: 30,
           	 		store: appTypeStore,
            		displayInfo: true,
            		displayMsg: '第{0}-{1}条记录,共{2}条记录'
        		}),
				height:300
			}),
			mainPanel=new Ext.Panel({
				contentEl: 'center',
				region:'center',
				frame:false,	 
				border:false,
				layout:'border',
				items:[
						proTermGridPanel=new Ext.grid.GridPanel({
							region:'center',
							contentEl:'terms',
							title:'应用类型期限列表',
							frame:false,
							split:true,
							ds: termDataStore,
							cm: termColumnModel,	
							viewConfig: {forceFit:true},	                        
							tbar: termMenubar,
							loadMask:true
							//height:300
						})
				]
			})			
		]	
	})
	
	appTypeGridPanel.getSelectionModel().on('rowselect', function(sm, index, record,store,store){
        doSelectAppType(record.get('app_type_code'),termDataStore);
    });

	appTypeGridPanel.render();
	appTypeStore.load({params: {start:0,limit:30}});
	
});