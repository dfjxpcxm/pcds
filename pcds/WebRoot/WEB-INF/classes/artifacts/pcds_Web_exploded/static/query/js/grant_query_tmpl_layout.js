Ext.onReady(function(){
	var searchUserId ;
	var dsId;
	var searchBar = ['查询方式：',new ArrayCombo({
			id : 'searchType',
			hiddenName : 'search_type',
			data : [['0', '按岗位'], ['1', '按角色']/*, ['2', '按机构']*/],
			fieldLabel : '',
			defaultValue : '0',
			width : 80 ,
			listeners:{
				'select':function(c,r,i){
					Ext.getCmp('typeList').clearValue();
					Ext.getCmp('typeList').getStore().load({params:{searchType:c.getValue()}});
				}
			}
		}), '-',new TypeSelector(),'-', '用户ID:',{
		xtype : 'textfield',
		id : 'suserId',
		name : 'suserId',
		width : 100
		/*,
		listeners : {
			specialkey : function(field, e) {
				searchUserId = Ext.getCmp('suserId').getValue();
				if (e.getKey() == Ext.EventObject.ENTER) {
					dsMetaDS.reload({
						params : {
							start : 0,
							limit : 30
						}
					});
				}
			}
		}*/
	}, {
		text : '查询',
		id:'searchButton',
		iconCls : "search",
		handler : function() {
			searchUserId = Ext.getCmp('suserId').getValue();
			dsCDS.reload({
				params : {
					start : 0,
					limit : 30
				}
			});
		}
	}];
	
	
	var dsTree = new Ext.tree.TreePanel({
		title:'数据集',
		region:'west',
		width : 260,
		id:'dsTreePanel',
		lines:true,
		autoScroll:true,
		bodyStyle : 'padding:5px 5px',
		loader: new Ext.tree.TreeLoader({dataUrl: pathUrl + '/queryTmpl/getDsTree'}),
		root :new Ext.tree.AsyncTreeNode({
			text: '数据集',
			iconCls:'folder_table',
			id:'root'
		}),
		split:true,
		rootVisible:true,
		listeners: {
	        click: function(n) {
	        	if(n.attributes['attr1'] && n.attributes['attr1']=='1'){
	        		Ext.getCmp('searchButton').setDisabled(false);

	        		dsId = n.id;
	        		dsCDS.reload({
	        			params : {
	        				start : 0,
	        				limit : 30
	        			}
	        		});
	        		
	        		
	        		var sType = Ext.getCmp("searchType").getValue();
	        		var typeValue = Ext.getCmp("typeList").getValue();
	        		dsEDS.reload({
	        			params : {
	        				ds_id:dsId,
		        			sType : sType,
		        			typeValue : typeValue,
		        			userId : searchUserId
	        			}
	        		});
	        	}else{
	        		dsId = '';
	        		dsCDS.removeAll();
	        		dsEDS.removeAll();
	        		Ext.getCmp('searchButton').setDisabled(false);
	        	}
	        }
	    }
	});

	//复选框
	var checksm = new Ext.grid.CheckboxSelectionModel();

	//数据源Store
	var dsCDS = new Ext.data.JsonStore({
		url : pathUrl + '/queryTmpl/getUserList/no',
		root: 'results',
		totalProperty: 'totalCount',
		fields: ['user_id','user_name']
	});
	
	dsCDS.on("beforeload", function(s) {
		var sType = Ext.getCmp("searchType").getValue();
		var typeValue = Ext.getCmp("typeList").getValue();
		s.baseParams = {
			ds_id:dsId,
			sType : sType,
			typeValue : typeValue,
			userId : searchUserId
		}
	});

	//数据源Grid列
	var dsCCM = new Ext.grid.ColumnModel([
	    //new Ext.grid.RowNumberer(),
	    checksm,
		{header: '用户ID', dataIndex: 'user_id'},
		{header: '用户名', dataIndex: 'user_name'} 
	]);

	
	var dsCGP = new Ext.grid.GridPanel({
		id: 'dsMetaGridPanel',
		title: '待分配用户列表',
		region: 'center',
		ddGroup : 'e_ddgroup',
		enableDragDrop   : true,
		stripeRows       : true,
		autoScorll: true,
		split: true,
		ds: dsCDS,
		cm: dsCCM,
		sm: checksm,
		tbar:searchBar,
		loadMask: true,
		viewConfig: {forceFit: true} ,
		bbar : new Ext.PagingToolbar({
			pageSize : 30,
			store : dsCDS,
			displayInfo : true,
			displayMsg : '第{0}-{1}条记录,共{2}条记录',
			emptyMsg : "没有记录"
		})
	});
	
	
	//复选框
	var checkesm = new Ext.grid.CheckboxSelectionModel();
	
	var dsEBar = [{
		text : '删除数据源权限（多选请按住 Ctrl键）',
		iconCls : "delete",
		handler : function() {
			if(dsEGP.getSelectionModel().getSelections()==0){
				Ext.Msg.alert('提示信息','请在已分配用户列表选择记录。');
				return ;
			}
			removeUserDs(dsEGP,dsId);
		}
	}];
		
	//数据源Store
	var dsEDS = new Ext.data.JsonStore({
		url : pathUrl + '/queryTmpl/getUserList/yes',
		root: 'results',
		totalProperty: 'totalCount',
		fields: ['user_id','user_name']
	});
	
	//数据源Grid列
	var dsECM = new Ext.grid.ColumnModel([
	    //new Ext.grid.RowNumberer(),
	    checkesm,
		{header: '用户ID', dataIndex: 'user_id'},
		{header: '用户名', dataIndex: 'user_name'} 
	]);
	
	var dsEGP = new Ext.grid.GridPanel({
		id: 'dsEGridPanel',
		title: '已分配用户列表',
		region: 'east',
		ddGroup : 'c_ddgroup',
		enableDragDrop   : true,
        stripeRows       : true,
		width:350,
		autoScorll: true,
		split: true,
		ds: dsEDS,
		cm: dsECM,
		sm:checkesm,
		tbar:dsEBar,
		loadMask: true,
		viewConfig: {forceFit: true} 
	});

	var viewport = new Ext.Viewport({
		layout:'border',
		items:[dsTree,{
				region : 'center',
				layout : 'border',
				items : [
				     dsCGP, dsEGP
				]
		}]	
	});
	
	//页面加载
	//加载类型
	Ext.getCmp('typeList').getStore().load({params:{searchType:Ext.getCmp('searchType').getValue()}});
	Ext.getCmp('searchButton').setDisabled(true);
	
	
	/****
	 *  Drop To Targets
	 ***/
	 var firstGridDropTargetEl =  dsEGP.getView().scroller.dom;
	 var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
         ddGroup    : 'e_ddgroup',
         notifyDrop : function(ddSource, e, data){
             var records =  ddSource.dragData.selections;
             Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
             dsEGP.store.add(records);
             
             //保存到数据库
             var infos = '';
             for(var i=0;i<records.length;i++){
            	 var record = records[i];
            	 if(i>0){
            		 infos+=",";
            	 }
            	 infos+=record.data.user_id;
             }
         	 Ext.Ajax.request({
				url : baseUrl + '/addUserDs',
				method : 'POST',
				params : { dsId : dsId,userIds : infos},
				callback : function(options, success, response) {
				}
			 })
             
             return true
         }
	 });


	 // This will make sure we only drop to the view scroller element
	/* var secondGridDropTargetEl = dsCGP.getView().scroller.dom;
	 var secondGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
         ddGroup    : 'c_ddgroup',
         notifyDrop : function(ddSource, e, data){
             var records =  ddSource.dragData.selections;
             Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
             dsCGP.store.add(records);
             return true
         }
	 });*/
});