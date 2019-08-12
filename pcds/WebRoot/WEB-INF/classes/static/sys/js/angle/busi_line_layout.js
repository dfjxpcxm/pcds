Ext.onReady(function(){
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		
	var busiLineColumnModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
				{
            id: 'busi_line_code',
            header: "业务条线代码",
            dataIndex: 'busi_line_code'
        },{
            header: "业务条线名称", 
            dataIndex: 'busi_line_name'
        },{
            header: "上级业务条线名称", 
            dataIndex: 'parent_busi_line_name'
        }
    ]);
    
    var busiLineDataStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: pathUrl+'/angleBusiLine/queryBusiLine'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount'
        }, [
            {name: 'busi_line_code'},
            {name: 'busi_line_name'},
            {name: 'parent_busi_line_name'}
        ]),
        remoteSort: true
    });
 
  	
	 var busiLineTopBar = [{
        text:'新增(a)',
        tooltip:'新增',
        iconCls:'add',
        handler: function(){
            busiLineAdd(busiLineDataStore);
        }
   		},'-',{
        text:'编辑(e)',
        tooltip:'编辑',
        iconCls:'edit',
        handler: function(){
            if(busiLineGridPanel.getSelectionModel().getSelections().length == 0)
						{
							Ext.MessageBox.alert('错误', 
								'请选择一条记录，继续进行！'
							);
							return;
						}

						var id=busiLineGridPanel.getSelectionModel().getSelections()[0].get('busi_line_code');
				    updateBusiLine(busiLineDataStore,id);
        
    	  }
    	},'-',{
        text:'删除(d)',
        tooltip:'删除',
        iconCls:'delete',
        handler: function(){
            if(busiLineGridPanel.getSelectionModel().getSelections().length == 0)
						{
							Ext.MessageBox.alert('错误', 
								'请选择一条记录，继续进行！'
							);
							return;
						}

						var id=busiLineGridPanel.getSelectionModel().getSelections()[0].get('busi_line_code');
            deleteBusiLine(busiLineDataStore,productDataStore,id);
        }
    }];

    var productColumnModel = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
				{
            header: "产品代码",
            dataIndex: 'product_id'
        },{
            header: "产品名称", 
            dataIndex: 'product_name'
        }
    ]); 
    
	var productDataStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: pathUrl+'/angleBusiLine/queryBusiLineProduct'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id: 'product_id'
        }, [
             {name: 'product_id'},
			 {name: 'product_name'}
        ])
    });
	//productDataStore.load();

	var productMenubar = [{
        text:'产品设定',
        iconCls:'edit',
        tooltip:'产品设定',
        handler: function(){
            if(busiLineGridPanel.getSelectionModel().getSelections().length == 0)
						{
							Ext.MessageBox.alert('错误', 
								'请选择一条记录，继续进行！'
							);
							return;
						}

						var id=busiLineGridPanel.getSelectionModel().getSelections()[0].get('busi_line_code');
						var busi_line_name=busiLineGridPanel.getSelectionModel().getSelections()[0].get('busi_line_name');
			      busiLineAssociateInfo(busiLineDataStore,id,busi_line_name,productDataStore);
        }
   }];

	var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			busiLineGridPanel=new Ext.grid.GridPanel({
				region:'west',
				contentEl:'west',
				width:500,
				title:'业务条线列表',
				frame:false,
				split:true,
				ds: busiLineDataStore,
				cm: busiLineColumnModel,	
				viewConfig: {forceFit:true},	  
				tbar: busiLineTopBar,                      
				autoScroll: true,
				autoExpandColumn:2,
				loadMask: true,
				bbar: new Ext.PagingToolbar({
            		pageSize: 30,
           	 		store: busiLineDataStore,
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
						productGridPanel=new Ext.grid.GridPanel({
							region:'center',
							contentEl:'product',
							title:'产品列表',
							frame:false,
							split:true,
							ds: productDataStore,
							cm: productColumnModel,	
							viewConfig: {forceFit:true},	                        
							tbar: productMenubar,
							loadMask:true
							//height:300
						})
				]
			})
		]
		
	})
	
	busiLineGridPanel.getSelectionModel().on('rowselect', function(sm, index, record,store,store){
        doSelectBusiLine(record.get('busi_line_code'),productDataStore);
    });

	busiLineGridPanel.render();
	busiLineDataStore.load({params: {start:0,limit:30}});
	
});