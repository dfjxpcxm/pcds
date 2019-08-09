//是否为新增流程标识
var isNewFlow = false;
var curr_flow_id = '';//当前流程ID
var curr_obj = '';//当前选中的节点对象

Ext.onReady(function(){
	
	//查询重新加载数据
	/*function btnclick(){
		demo.destrory();
		init();
		demo.loadDataAjax({
			url : pathUrl+'/flow/getJsonData',
			type : 'POST',
			data : 'flow_id='+v.value,
			dataType : 'json'
		});
    };*/
    
    var flow_info_cm = new Ext.grid.ColumnModel([
                                        new Ext.grid.RowNumberer(),
                                   		{header:'流程号',dataIndex:'flow_id',hidden:true},
                                   		{header:'流程名',dataIndex:'flow_name',width:165},
                                      	{header:'创建日期',dataIndex:'create_time',width:95}
                                   	]);
    var flow_info_store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
 		   url: pathUrl+'/flow/listAllFlow'
       }),
       autoLoad:true,
       reader: new Ext.data.JsonReader({
     	 root: 'results',
          totalProperty: 'totalCount'
       }, [
          {name: 'flow_id',mapping:'flow_id',type:'string'},
          {name: 'flow_name',mapping:'flow_name',type:'string'},
          {name: 'create_time',mapping:'create_time',type:'string'}
       ])
 	});
    
    var flow_info_grid = new Ext.grid.GridPanel({
		region:'east',
		el:'east',
		id:'flow_info_grid',
		width:'280',
		split : true,
		store: flow_info_store,
		cm: flow_info_cm,	
//		viewConfig: {forceFit:true},
		tbar:[
			{
				xtype:'label',
				style:'padding:0 10px 0 5px;',
				html:'流程列表'
			},{
				xtype:'button',
				text:'移除',
				iconCls:'delete',
				handler:function(){
					deleteFlowInfo();
				}
			}
		],
		loadMask:true
		/*bbar : new Ext.PagingToolbar({
			pageSize : 30,
			displayInfo : true,
			store: flow_info_store,
			displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg : "没有记录",
			firstText : '第一页',
			prevText : '上一页',
			nextText : '下一页',
			lastText : '最后一页',
			refreshText : '刷新'
		})*/
	});
    
    flow_info_grid.getSelectionModel().on('rowselect',function(sm,rowIndex,record){
    		var flow_id = record.get('flow_id');
	    	demo.destrory();
			init();
			isNewFlow = false;
			curr_flow_id = flow_id;
			demo.loadDataAjax({
				url : pathUrl+'/flow/getJsonData',
				type : 'POST',
				data : 'flow_id='+flow_id,
				dataType : 'json',
				callback:function(status){
					
						}
			});
    })
    
    flow_info_store.on('load',function(store,options){
    	if(!curr_flow_id){
    		if(store.getCount()>0){
        		//选中
        		var indexArr = new Array();
        		indexArr.push(0);
        		Ext.getCmp('flow_info_grid').getSelectionModel().selectRows(indexArr,false);
        	}
    	}
    	
    });
    
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			c_panel = new Ext.Panel({
				region:'center',
				el:'center'
			}),
			flow_info_grid
		]
	});
});

/****bak
simpleForm=new clean.ColumnFrom({
				region:'east',
				el: 'east',
				width:275,
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
				{
					xtype : 'combo',
					store :new Ext.data.JsonStore({
				        url: pathUrl+ '/flow/listAllFlow',
				        root: 'results',
				        autoLoad: true,
				        fields: ['flow_id','flow_name']
				    }),
					valueField : "flow_id",
					displayField : "flow_name",
					mode : 'local',
					editable : false,
					forceSelection : true,
					hiddenName : 'flow_id',
					triggerAction : 'all',
					allowBlank : false,
					fieldLabel : '流程',
					id : 'flow_id',
					name : 'flow_id',
					anchor : '100%',
					listeners:{
						select:function(v){
							demo.destrory();
							init();
							isNewFlow = false;
							curr_flow_id = v.value;
							demo.loadDataAjax({
								url : pathUrl+'/flow/getJsonData',
								type : 'POST',
								data : 'flow_id='+v.value,
								dataType : 'json'
							});
						}
					}
				}],
			 
				buttons: [{
					text: '查询',
					width:70,
					handler:btnclick
				}]
				
			})
 * bak
 */


function showGrantUserWindow(id,model,obj){
	if(obj.type!='task'&&obj.type!='end'){
		return;
	}
	var w_sm = new Ext.grid.CheckboxSelectionModel();
	var w_cm = new Ext.grid.ColumnModel([
	    w_sm,
   		{header:'客户经理编号',dataIndex:'cust_mgr_id'},
   		{header:'用户名',dataIndex:'user_name'}
   	]);
   	var w_store = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({
   		   url: pathUrl+'/flow/getUserByBlmb'
         }),
         reader: new Ext.data.JsonReader({
       	 root: 'results',
            totalProperty: 'totalCount'
         }, [
            {name: 'cust_mgr_id',mapping:'cust_mgr_id',type:'string'},
            {name: 'user_name',mapping:'user_name',type:'string'}
         ]),
         listeners : {
        	 beforeload : function(store,options ){
        		store.baseParams={
        			tmpl_id : tmpl_id
        		};
        	}
         }
   	});
   	
    var w_panel = new Ext.grid.GridPanel({
   		region:'west',
   		split : true,
   		store: w_store,
   		cm: w_cm,
   		sm:w_sm,
   		width:350,
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
   		loadMask:true
   	});
    
    var c_cm = new Ext.grid.ColumnModel([
		{header:'客户经理编号',dataIndex:'cust_mgr_id'},
		{header:'用户名',dataIndex:'user_name'}
	]);
	var c_store = new Ext.data.Store({
      proxy: new Ext.data.HttpProxy({
		   url: pathUrl+'/flow/getUserByBlmb'
      }),
      reader: new Ext.data.JsonReader({
    	 root: 'results',
         totalProperty: 'totalCount'
      }, [
         {name: 'cust_mgr_id',mapping:'cust_mgr_id',type:'string'},
         {name: 'user_name',mapping:'user_name',type:'string'}
      ]),
      listeners : {
     	 beforeload : function(store,options ){
     		store.baseParams={
     			tmpl_id : tmpl_id
     		};
     	}
      }
	});
	
	var c_panel = new Ext.grid.GridPanel({
		region:'center',
		split : true,
		store: c_store,
		cm: c_cm,	
		viewConfig: {forceFit:true},
		loadMask:true,
		bbar : new Ext.PagingToolbar({
			pageSize : 30,
			displayInfo : true,
			store: c_store,
			displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg : "没有记录",
			firstText : '第一页',
			prevText : '上一页',
			nextText : '下一页',
			lastText : '最后一页',
			refreshText : '刷新'
		})
	});
	var win = new Ext.Window({
		modal : true,
		bodyStyle : 'padding:10px;',
		buttonAlign : 'center',
		width : 700,
		height : 500,
		layout : 'border',
		items : [
		         w_panel,c_panel
		]
	});
	win.show();
}