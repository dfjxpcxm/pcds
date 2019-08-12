Ext.onReady(function(){
	var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {
            id: 'flow_status_code',
            header: "流程状态代码",
            dataIndex: 'flow_status_code',
            hidden:true
        },{
			id: 'flow_status_name',
            header: "流程状态名称", 
            dataIndex: 'flow_status_name'
        },{
			id: 'flow_status_desc',
            header: "流程状态描述", 
            dataIndex: 'flow_status_desc'
        },{
			id: 'icon_path',
            header: "图标", 
            dataIndex: 'icon_path'
        }
    ]); 
	
	var gridDataStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
		   url: pathUrl+'/flow/flowList'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id: 'custMgrID'
        }, [
            {name: 'flow_status_code',mapping:'flow_status_code',type:'string'},
            {name: 'flow_status_name',mapping:'flow_status_name',type:'string'},
            {name: 'flow_status_desc',mapping:'flow_status_desc',type:'string'},
            {name: 'icon_path',mapping:'icon_path',type:'string'}
            ]
         )
	});
//    gridDataStore.on('beforeload', function() {
//		var search1 = Ext.getCmp('search1').getValue();
//		var search2 = Ext.getCmp('search2').getValue();
//		var search3 = Ext.getCmp('search3').getValue();
//		gridDataStore.baseParams = {
//			search : search,
//			search1 : search1,
//			search2 : search2,
//			search3 : search3
//		};
//	});
	
	var menubar = [{
        text:'添加(a)',
        tooltip:'添加用户',
        iconCls:'add',
        handler: function(){
            addInfo(gridDataStore);
        }
    },'-',{
        text:'删除(d)',
        tooltip:'删除用户',
        iconCls:'delete',
        handler: function(){
			var record = gridPanel.getSelectionModel().getSelections()[0];
			if(record==null){
				Ext.Msg.alert('提示','请选择一条记录!');
				return false;
			}
			var id = record.get('flow_status_code');
			deleteInfo(gridDataStore,id);
        }
    },'-',{
        text:'修改(u)',
        tooltip:'修改用户',
        iconCls:'edit',
        handler: function(){
        	var record = gridPanel.getSelectionModel().getSelections()[0];
        	if(record==null){
				Ext.Msg.alert('提示','请选择一条记录!');
				return false;
			}
			var id = record.get('flow_status_code');
        	editInfo(gridDataStore,id);
        }
    }];
	
	//查询重新加载数据
	function btnclick(){
		gridDataStore.load({params : {start : 0,limit : 20}})
    };
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			simpleForm=new clean.ColumnFrom({
				region:'north',
				el: 'east',
				collapsible: true,
				columnSize : [0.25,0.25,0.25,0.25],
				split:true,
				labelAlign: 'left',
				title: '设置查询条件',
				buttonAlign:'center',
				bodyStyle:'padding:5px',
				frame:true,
				labelWidth:70,
				method:'post',
				items : [
				new MetadataCombo({width:135,anchor:'81%'}),{
					xtype : 'textfield',
					fieldLabel : '姓名',
					width : 135,
					id : 'search',
					anchor : '81%',
					name : 'search'
				}, {
					xtype : 'textfield',
					fieldLabel : '员工代码',
					width : 135,
					id : 'search1',
					anchor : '81%',
					name : 'search'
				}, {
					xtype : 'textfield',
					fieldLabel : '客户经理号',
					width : 135,
					id : 'search2',
					anchor : '81%',
					name : 'search'
				}, {
					xtype : 'textfield',
					fieldLabel : '身份证号',
					width : 135,
					id : 'search3',
					anchor : '81%',
					name : 'search'
				}],
			 
				buttons: [{
					text: '查询',
					width:70,
					handler:btnclick
				}]
				
			}),
			gridPanel=new Ext.grid.GridPanel({				
				region:'center',
				el:'center',
				//title:'用户列表',
				split:true,
				ds: gridDataStore,
				cm: cm,	
				viewConfig: {forceFit:true},
				//autoHeight:true,
				tbar: menubar,
				bbar: new Ext.PagingToolbar({
	    			pageSize: 20,
	        		store: gridDataStore,
	        		displayInfo: true,
	        		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        		emptyMsg: "没有记录"
	    		})
			})
		]
	})
//双击进行修改
	gridPanel.on('dblclick', function(store,e) {
		var record = gridPanel.getSelectionModel().getSelections()[0];
    	if(record==null){
			Ext.Msg.alert('提示','请选择一条记录!');
			return false;
		}
		var id = record.get('flow_status_code');
    	editInfo(gridDataStore,id);
    });

	gridStore=gridDataStore;
});