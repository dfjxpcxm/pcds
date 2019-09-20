//Ext.onReady(function(){
//	var cm = new Ext.grid.ColumnModel([
//        new Ext.grid.RowNumberer(),
//        {
//            id: 'cust_mgr_id',
//            header: "客户经理编号",
//            dataIndex: 'cust_mgr_id'
//        },{
//			id: 'user_name',
//            header: "用户名", 
//            dataIndex: 'user_name'
//        },{
//			id: 'clerkID',
//            header: "CBS工号", 
//            dataIndex: 'clerkID',
//            hidden: true
//        },{
//			id: 'khdxRoleName',
//            header: "评定对象角色", 
//            dataIndex: 'khdxRoleName',
//            hidden: true
//        }
//        ,{
//			id: 'bankOrgID',
//            header: "管理机构", 
//            dataIndex: 'c_bankOrgID',
//             hidden: true
//        }
//        ,{
//			id: 'ownerOrgID',
//            header: "所属部门", 
//            dataIndex: 'c_ownerOrgID',
//            hidden: true
//        },{id:'busi_line_id',
//           dataIndex:'busi_line_id',
//           hidden:true
//        }
//    ]); 
//	
//	var gridDataStore = new Ext.data.Store({
//        proxy: new Ext.data.HttpProxy({
//		   url: pathUrl+'/user/getUserList'
//        }),
//        reader: new Ext.data.JsonReader({
//            root: 'results',
//            totalProperty: 'totalCount',
//            id: 'custMgrID'
//        }, [
//            {name: 'cust_mgr_id',mapping:'cust_mgr_id',type:'string'},
//            {name: 'user_name',mapping:'user_name',type:'string'},
//            /*{name: 'roleName',mapping:'roleName',type:'string'},*/
//            {name: 'clerkID',mapping:'clerkID',type:'string'},
//            {name: 'khdxRoleName',mapping:'khdxRoleName',type:'string'},
//            {name: 'c_bankOrgID',mapping:'bankOrgID',type:'string'},
//            {name: 'c_ownerOrgID',mapping:'ownerOrgID',type:'string'},
//            {name:'busi_line_id',mapping:'busiLineId',type:'string'}
//        ]),
//        listeners : {
//        	beforeload : function(store,options ){
//        		store.baseParams={
//        			searchField : searchField
//        		}
//        	}
//        }
//    });
//	
//	gridDataStore.load({params:{bank_org_id:userOrgID,start:0,limit:30}});
//	
//	var menubar = [{
//        text:'用户模板授权',
//        tooltip:'用户授权',
//        iconCls:'design',
//        handler: function(){
//        	 var m = gridPanel.getSelectionModel().getSelections();
//		    if(m.length > 0)
//		    {
//				var cust_mgr_id =  m[0].get('cust_mgr_id');
//				UserGrant(cust_mgr_id);
//			}else{
//        		Ext.MessageBox.alert("提示","请选择一个用户");
//        	}
//        }
//    }];
//	
//	//查询重新加载数据
//	function btnclick(){
////		var orgID = simpleForm.findById('bankSelector').getValue();
//		var cust_mgr_id = simpleForm.findById('search').getValue();
//		gridDataStore.load({params:{moduleCode:'0090',bank_org_id:'8888',start:0,limit:30,cust_mgr_id:cust_mgr_id}});
//    };
//			
//	var viewport = new Ext.Viewport({
//		layout:'border',
//		items:[
//			simpleForm=new clean.ColumnFrom({
//				region:'north',
//				columnSize:[0.33,0.33,0.25],
//				buttonLayout:'0',
//				el: 'east',
//				collapsible: true,
//				split:true,
//				labelAlign: 'left',
//				title: '设置查询条件',
//				buttonAlign:'center',
//				frame:true,
//				labelWidth:60,
//				method:'post',
//				items : [
//				//         bankSelector = new BankWholeSelector(), 
//				{
//				xtype : 'textfield',
//				fieldLabel : '模糊查询',
//				anchor : '81%',
//				emptyText : '请输入用户名/编号',
//				id : 'search',
//				name : 'search',
//				listeners : {
//					blur : function (field){
//						searchField = field.getValue();
//					},
//					specialkey : function(field, e){
//						searchField = field.getValue();
//						if (e.getKey() == Ext.EventObject.ENTER) {
////						var orgID = simpleForm.findById('bankSelector').getValue();
//							gridDataStore.load({
//								params : {
////									bank_org_id : orgID,
//									start : 0,
//									limit : 30
//								}
//							});
//						}
//					} 
//				}
//			}],
//			 
//				buttons: [{
//					text: '&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;',
//					handler:btnclick
//				}]
//				
//			}),
//			gridPanel=new Ext.grid.GridPanel({				
//				region:'center',
//				el:'center',
//				width:'100%',
//				//title:'用户列表',
//				ds: gridDataStore,
//				cm: cm,	
//				viewConfig: {forceFit:true},
//				//autoHeight:true,
//				tbar: menubar,
//				loadMask:true,
//				bbar : new Ext.PagingToolbar({
//					pageSize : 30,
//					displayInfo : true,
//					store: gridDataStore,
//					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
//					emptyMsg : "没有记录",
//					firstText : '第一页',
//					prevText : '上一页',
//					nextText : '下一页',
//					lastText : '最后一页',
//					refreshText : '刷新'
//				})
//			})
//		]
//	})
//
////	gridPanel.on('dblclick', function(store,e) {
////		var record = gridPanel.getSelectionModel().getSelections();
////		if(record.length > 0){
////			var cust = record[0].get('custMgrID');
////			var bankOrgId=record[0].get('c_bankOrgID');
////			var ownerOrgId=record[0].get('c_ownerOrgID');
////			editUser(gridDataStore,bankOrgId,ownerOrgId,cust);
////		}
////		
////    });
//
//	gridStore=gridDataStore;
////	bankSelector.initUI();
//});

var c_n_store,c_c_store,c_c_panel,c_n_panel,tmpl_id,tmpl_name,mbUserStore,w_c_store,w_n_panel,w_c_panel;

Ext.onReady(function(){
	
	var w_c_sm = new Ext.grid.CheckboxSelectionModel();
	var w_c_cm = new Ext.grid.ColumnModel([
		w_c_sm,
		{header:'客户经理编号',dataIndex:'cust_mgr_id'},
		{header:'用户名',dataIndex:'user_name'},
   		{header:'tmpl_id',dataIndex:'tmpl_id',hidden:true},
   		{header:'模板',dataIndex:'tmpl_name',hidden:true}
	]);
	w_c_store = new Ext.data.Store({
      proxy: new Ext.data.HttpProxy({
		   url: pathUrl+'/flow/getUserByBlmb'
      }),
      reader: new Ext.data.JsonReader({
    	 root: 'results',
         totalProperty: 'totalCount'
      }, [
         {name: 'cust_mgr_id',mapping:'cust_mgr_id',type:'string'},
         {name: 'user_name',mapping:'user_name',type:'string'},
         {name: 'tmpl_id',mapping:'tmpl_id',type:'string'},
         {name: 'tmpl_name',mapping:'tmpl_name',type:'string'}
      ]),
      listeners : {
		beforeload : function(store,options ){
     		store.baseParams={
//     			cust_mgr_id : Ext.getCmp('cust_mgr_id').getValue()
     			tmpl_id : tmpl_id
     		}
     	}
      }
	});
	
	w_c_panel = new Ext.grid.GridPanel({
		region:'center',
		split : true,
		store: w_c_store,
		cm: w_c_cm,	
		sm:w_c_sm,
		viewConfig: {forceFit:true},
		tbar:[
			{
				xtype:'label',
				style:'padding:0 10px 0 5px;',
				html:'已分配客户经理列表'
			},{
				xtype:'button',
				text:'移除',
				iconCls:'delete',
				handler:function(){
					removeUser();
				}
			}
		],
		loadMask:true,
		bbar : new Ext.PagingToolbar({
			pageSize : 30,
			displayInfo : true,
			store: w_c_store,
			displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg : "没有记录",
			firstText : '第一页',
			prevText : '上一页',
			nextText : '下一页',
			lastText : '最后一页',
			refreshText : '刷新'
		})
	});
	
	w_n_panel =  new Ext.tree.TreePanel({
		split:true,
		region:'north',
		height:300,
		collapsible: false,
		id:'blmbTreePanel',
		lines:false,
		autoScroll:true,
		bodyStyle : 'padding:5px 5px',
		loader: new Ext.tree.TreeLoader({dataUrl: pathUrl + '/flow/listAllTmpl/yes'}),
		root :new Ext.tree.AsyncTreeNode({
			text: '补录模板',
			iconCls:'folder_table',
			id:'root'
		}),
		split:true,
		rootVisible:true,
		listeners: {
	        click: function(n) {
				tmpl_id = n.id;
				tmpl_name = n.text.substring(0,n.text.indexOf('['));
//				mbUserStore.load({params : {tmpl_id : tmpl_id},callback:userTmplLoad});
				//c_n_store.load({params:{start:0,limit:30,tmpl_id:tmpl_id}});
				w_c_store.load({params:{start:0,limit:30,tmpl_id:tmpl_id}});
				c_n_store.load({params:{start:0,limit:30,tmpl_id:tmpl_id,cust_mgr_id:Ext.getCmp('cust_mgr_id').getValue()}});
	        }
	    }
	});
	
	var w_panel = new Ext.Panel({
		region:'west',
		width:450,
		layout:'border',
		split:true,
		items:[w_n_panel,w_c_panel]
	});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var c_n_cm = new Ext.grid.ColumnModel([
		sm,
		{header:'客户经理编号',dataIndex:'cust_mgr_id'},
		{header:'用户名',dataIndex:'user_name'},
   		{header:'tmpl_id',dataIndex:'tmpl_id',hidden:true},
   		{header:'模板',dataIndex:'tmpl_name',hidden:true}
	]);
	c_n_store = new Ext.data.Store({
      proxy: new Ext.data.HttpProxy({
		   url: pathUrl+'/flow/getUserList'
      }),
      reader: new Ext.data.JsonReader({
    	 root: 'results',
         totalProperty: 'totalCount'
      }, [
         {name: 'cust_mgr_id',mapping:'cust_mgr_id',type:'string'},
         {name: 'user_name',mapping:'user_name',type:'string'},
	        {name: 'tmpl_id',mapping:'tmpl_id',type:'string'},
	        {name: 'tmpl_name',mapping:'tmpl_name',type:'string'}
      ]),
      listeners : {
		beforeload : function(store,options ){
     		store.baseParams={
     			cust_mgr_id : Ext.getCmp('cust_mgr_id').getValue(),
     			tmpl_id : tmpl_id
     		}
     	}
      }
	});
	
	var c_c_cm = new Ext.grid.ColumnModel([
   		new Ext.grid.RowNumberer(),
   		{header:'客户经理编号',dataIndex:'cust_mgr_id'},
   		{header:'用户名',dataIndex:'user_name'},
   		{header:'tmpl_id',dataIndex:'tmpl_id',hidden:true},
   		{header:'模板',dataIndex:'tmpl_name'}
   	]);
	
   	c_c_store = new Ext.data.JsonStore({
   		fields : [{name: 'cust_mgr_id',mapping:'cust_mgr_id',type:'string'},
	        {name: 'user_name',mapping:'user_name',type:'string'},
	        {name: 'tmpl_id',mapping:'tmpl_id',type:'string'},
	        {name: 'tmpl_name',mapping:'tmpl_name',type:'string'}
	    ],
		root   : 'records'
   	});
	
	c_n_panel = new Ext.grid.GridPanel({
		ddGroup          : 'c_c_DDGroup',
		split : true,
		height:300,
		region:'north',
		store: c_n_store,
		cm: c_n_cm,	
		sm:sm,
		viewConfig: {forceFit:true},
		enableDragDrop   : true,
        stripeRows       : true,
		tbar:[
	      	{
				xtype:'label',
				style:'padding:0 10px 0 5px;',
				html:'客户经理:'
			},{
				xtype:'textfield',
				id:'cust_mgr_id',
				name:'cust_mgr_id',
				anchor:'100%'
			},{
				xtype:'button',
				iconCls:'search',
				handler:function(){
					doSearch();
				}
			},'<font color=red>(选中数据向下方【分配列表】拖动)</font>'
		],
		loadMask:true,
		bbar : new Ext.PagingToolbar({
			pageSize : 30,
			displayInfo : true,
			store: c_n_store,
			displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg : "没有记录",
			firstText : '第一页',
			prevText : '上一页',
			nextText : '下一页',
			lastText : '最后一页',
			refreshText : '刷新'
		})
	});
	
	c_c_panel = new Ext.grid.GridPanel({
		ddGroup          : 'c_n_DDGroup',
		title:'分配列表',
		split : true,
		region:'center',
		store: c_c_store,
		cm: c_c_cm,	
		enableDragDrop   : true,
        stripeRows       : true,
		viewConfig: {forceFit:true},
		style:'padding:0 0 2px 0',
		buttonAlign:'center',
		buttons:[
	         {
	        	text:'确定',
	        	handler:function(){
	        	 	grantUserBlmb();
	         	}
	         },{
	        	text:'重置',
	        	handler:function(){
	        	 	resetStore();
         		}
	         }
		]
	});
	
	var c_panel = new Ext.Panel({
		region:'center',
		layout:'border',
		split : true,
		items:[c_n_panel,c_c_panel]
	});
	
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:[{
		       layout:'border',
		       items:[w_panel,c_panel]
		}]
	});
	
	/****
	 * Setup Drop Targets
	 ***/
	 // This will make sure we only drop to the  view scroller element
	 var firstGridDropTargetEl =  c_n_panel.getView().scroller.dom;
	 var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
         ddGroup    : 'c_n_DDGroup',
         notifyDrop : function(ddSource, e, data){
             var records =  ddSource.dragData.selections;
             for(var i=0;i<records.length;i++){
            	 var record = records[i];
            	 record.data.tmpl_id = '';
            	 record.data.tmpl_name = '';
             }
             Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
             c_n_panel.store.add(records);
             return true
         }
	 });


	 // This will make sure we only drop to the view scroller element
	 var secondGridDropTargetEl = c_c_panel.getView().scroller.dom;
	 var secondGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
         ddGroup    : 'c_c_DDGroup',
         notifyDrop : function(ddSource, e, data){
 			 if(''==tmpl_id||null==tmpl_id){
 				 Ext.MessageBox.alert('提示','请先选择一个模板再进行操作！');
 				 return;
 			 }
             var records =  ddSource.dragData.selections;
             for(var i=0;i<records.length;i++){
            	 var record = records[i];
            	 record.data.tmpl_id = tmpl_id;
            	 record.data.tmpl_name = tmpl_name;
             }
             Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
             c_c_panel.store.add(records);
             return true
         }
	 });
})