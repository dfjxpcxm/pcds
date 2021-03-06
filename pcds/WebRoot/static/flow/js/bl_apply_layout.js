var task_id = '',r,gridDataStore;
Ext.onReady(function(){
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {
            id: 'task_id',
            header: "唯一任务ID",
            dataIndex: 'task_id',
            renderer:function(v){return "<a href='javascript:showDetail();'>"+v+"</a>";}
        },{
			id: 'tmpl_id',
            header: "模板ID", 
            dataIndex: 'tmpl_id',
            hidden:true
        },{
			id: 'table_id',
            header: "表ID", 
            dataIndex: 'table_id',
            hidden:true
        },{
			id: 'table_data_id',
            header: "表数据ID", 
            dataIndex: 'table_data_id',
            hidden:true
        },{
			id: 'apply_user_id',
            header: "申请用户", 
            dataIndex: 'apply_user_id',
            hidden:true
        },{
			id: 'apply_user_name',
            header: "申请用户", 
            dataIndex: 'apply_user_name'
        },{
        	id: 'flow_status_code',
            header: "状态", 
            dataIndex: 'flow_status_code',
            hidden:true
        },{
        	id: 'flow_status_name',
            header: "申请状态", 
            dataIndex: 'flow_status_name'
        },{
			id: 'apply_date',
            header: "申请时间", 
            dataIndex: 'apply_date'
        },{
        	id : 'approve_user_id',
        	header : '审批用户',
        	dataIndex : 'approve_user_id',
        	hidden : true
        },{
        	id : 'approve_user_name',
        	header : '审批用户',
        	dataIndex : 'approve_user_name'
        },{
        	id : 'approve_date',
        	header : '审批时间',
        	dataIndex : 'approve_date'
        }
    ]); 
	
	gridDataStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
		   url: pathUrl+'/flow/getApplyList'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount',
            id: 'custMgrID'
        }, [
            {name: 'task_id',mapping:'task_id',type:'string'},
            {name: 'tmpl_id',mapping:'tmpl_id',type:'string'},
            {name: 'table_id',mapping:'table_id',type:'string'},
            {name: 'table_data_id',mapping:'table_data_id',type:'string'},
            {name: 'apply_user_id',mapping:'apply_user_id',type:'string'},
            {name: 'apply_user_name',mapping:'apply_user_name',type:'string'},
            {name: 'flow_status_code',mapping:'flow_status_code',type:'string'},
            {name: 'flow_status_name',mapping:'flow_status_name',type:'string'},
            {name: 'apply_date',mapping:'apply_date',type:'string'},
            {name: 'approve_user_id',mapping:'approve_user_id',type:'string'},
            {name: 'approve_user_name',mapping:'approve_user_name',type:'string'},
            {name: 'approve_date',mapping:'approve_date',type:'string'}
            ]
         )
	});
	 gridDataStore.on('beforeload', function() {
		var flow_status_code = Ext.getCmp('status_code').getValue();
		gridDataStore.baseParams = {
			flow_status_code : flow_status_code
		};
	});
	
	//查询重新加载数据
	function btnclick(){
		gridDataStore.load({params : {start : 0,limit : 20,flow_status_code:Ext.getCmp('status_code').getValue()}})
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
				{
					xtype : 'combo',
					store : new Ext.data.SimpleStore({
						fields : ["v", "d"],
						data : [['00','初始'],['02','待审'],['03','撤回'],['05', '通过'], ['04', '退回']]
					}),
					valueField : "v",
					displayField : "d",
					mode : 'local',
					editable : false,
					forceSelection : true,
					hiddenName : 'flow_status_code',
					triggerAction : 'all',
					allowBlank : false,
					fieldLabel : '状态',
					id : 'status_code',
					name : 'flow_status_code',
					anchor : '100%',
					value:'02',
					listeners:{
						select:function(v){
							gridDataStore.load({params : {start : 0,limit : 20,flow_status_code:v.value}})
						}
					}
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
				split:true,
				ds: gridDataStore,
				cm: cm,	
				sm: sm,
				viewConfig: {forceFit:true},
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
	
	gridPanel.on('click', function(store,e) {
		r = gridPanel.getSelectionModel().getSelections()[0];
		task_id = r.get('task_id');
    });
});