Ext.onReady(function(){
	var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {
            id: 'dim_code',
            header: "维表代码",
            //width:45,
            dataIndex: 'dim_code'
        },{
			id: 'table_name',
            header: "维表名称", 
            dataIndex: 'table_name'
        },{
			id: 'dim_name',
            header: "维表描述", 
            dataIndex: 'dim_name'
        },{
			id: 'pk_name',
            header: "维表主键字段", 
            dataIndex: 'pk_name',
			hidden: true
        },{
			id: 'fields_name',
            header: "维表显示字段", 
            dataIndex: 'fields_name',
			hidden: true
        }
    ]); 
	
	var tableDataCm = new Ext.grid.ColumnModel([
	                                   new Ext.grid.RowNumberer(),
	                                   {
	                                       id: 'dim_code',
	                                       header: "",
	                                       //width:45,
	                                       dataIndex: 'dim_code'
	                                   }
	                               ]); 
	
	var gridDataStore = new Ext.data.JsonStore({
        url : pathUrl + '/managerDimTable/listDimInfo',
        idProperty :'dim_code',
        totalProperty:'totalCount',
        root:"results",
        fields:['dim_code','table_name','dim_name','pk_name','fields_name'] 
    });
	
	var tableDataStore = new Ext.data.JsonStore({
        url : pathUrl + '/managerDimTable/queryDimTableData',
        idProperty :'dim_code',
        totalProperty:'totalCount',
        root:"results",
        fields:['dim_code'] 
    })
	 
	gridDataStore.load({params: {start:0,limit:10}});

	var menubar = [{
        text:'添加(a)',
        tooltip:'添加维表信息',
        iconCls:'add',
        handler: function(){
            addDimInfo(gridDataStore);
        }
    },'-',{
        text:'删除(d)',
        tooltip:'删除维表信息',
        iconCls:'delete',
        handler: function(){
			var code = gridPanel.getSelectionModel().getSelections()[0].get('dim_code');
			deleteDimInfo(gridDataStore,code);
        }
    },'-'];	
	
	//维表数据按钮
	var tableDataMenubar = [{
        text:'添加(a)',
        tooltip:'添加维表数据',
        iconCls:'add',
        handler: function(){
        	doAddDimTableData();
        }
    },'-',{
        text:'修改(e)',
        tooltip:'修改维表数据',
        iconCls:'edit',
        handler: function(){
        	doEditDimTableData();
        }
    },'-',{
        text:'删除(d)',
        tooltip:'删除维表数据',
        iconCls:'delete',
        handler: function(){
        	doDeleteDimTableData();
        }
    },'-',{
        text:'导入(i)',
        tooltip:'导入维表数据',
        iconCls:'importdata',
        handler: function(){
        	showImpWindow();
        }
    },'-'];	
	
	//查询重新加载数据
	function btnclick(){
			var dim_code = simpleForm.findById('dim_code').getValue();
			var table_name = simpleForm.findById('table_name').getValue();

            gridDataStore.load({params:{moduleCode:'0091',dim_code:dim_code,table_name:table_name,start:0,limit:10}});
            //gridPanel.reconfigure(ds,cm); 
    };
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			mainPanel=new Ext.Panel({
				region:'center',
				el: 'center',
				layout:'border',
				items:[
					simpleForm=new Ext.FormPanel({
						region:'north',
						el:'cond',
						collapsible: true,
						split:false,
						labelAlign: 'left',
						title: '设置查询条件',
						buttonAlign:'center',
						bodyStyle:'padding:5px',
						frame:true,
						labelWidth:60,
						method:'post',
						width:'30%',
						height:200,
						items:[{
							layout:'form',
							border:false,
							labelSeparator:':',
							items:[{
								xtype:'textfield',
                				fieldLabel: '维表代码',
                				name: 'dim_code',
								id:'dim_code',
								editable: true,
               					anchor:'90%'
							},{
								xtype:'textfield',
                				fieldLabel: '维表名称',
                				name: 'table_name',
								id: 'table_name',
								editable: true,
               					anchor:'90%'
							}]
						}],
						buttons: [{
							text: '&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;',
							handler:btnclick
						}]
					}),
					gridPanel=new Ext.grid.GridPanel({
						region:'center',
						el:'list',
						//height:'50%',
						title:'维表列表',
						ds: gridDataStore,
						cm: cm,	
						viewConfig: {forceFit:true},
						tbar: menubar,
						bbar: new Ext.PagingToolbar({
                    		pageSize: 10,
                   	 		store: gridDataStore,
                    		displayInfo: true,
                    		displayMsg: '第{0}-{1}条记录,共{2}条记录'
                    		//,
                    		//emptyMsg: "没有记录"
                		})
					})
				]			
			}),
			tableDataPanel=new Ext.grid.GridPanel({
				region:'east',
				el: 'east',
				width:'70%',
				ds: tableDataStore,
				cm: tableDataCm,	
				title:'维表数据',
				tbar: tableDataMenubar,
				bbar: new Ext.PagingToolbar({
            		pageSize: 20,
           	 		store: tableDataStore,
            		displayInfo: true,
            		displayMsg: '第{0}-{1}条记录,共{2}条记录'
            		//,
            		//emptyMsg: "没有记录"
        		})
			})
			/*tabPanel = new Ext.TabPanel({
				region:'east',
				el: 'east',
				width:'70%',				
				activeTab: 0,
				defaults: {autoScroll:true},
				id:'tabPanel',
				tabPosition:'bottom',
        		deferredRender:false,
        		frame:true,
        		plain:false,
				plugins: new Ext.ux.TabCloseMenu(),
				items:[{
					title:"维表数据维护",
                	body:new Ext.ux.ManagedIFrame({autoCreate:{ src:'', frameBorder: 0, cls:'x-panel-body ',width: '100%', height: '100%'}}),
					closable:true
				}]			
			})*/		
		]
	});

	gridPanel.on('dblclick', function(store,e) {
		if(gridPanel.getSelectionModel().getCount() == 0){
			return;
		}
		var code = gridPanel.getSelectionModel().getSelections()[0].get('dim_code');
		doEditDimInfo(gridDataStore,code);
    });
	
	gridPanel.getSelectionModel().on('rowselect', function(sm, index, record){
        var table=record.get('table_name');
		var desc=record.get('dim_name');
		var dim_code=record.get('dim_code');
		
		//校验表是否存在
		Ext.Ajax.request({
			url:pathUrl + '/managerDimTable/getTableMeta',
			params:{table_name:table},
			callback:function(options,success,response){
				var json = Ext.util.JSON.decode(response.responseText);
				if(json.failure == true){
					Ext.Msg.alert('提示信息',json.info);
				} 
				getTableDataStore(dim_code,table);
				
			}
		});
    });

	gridStore=gridDataStore;

});

/**
 * 获取维表数据信息
 * @returns
 */
function getTableDataStore(dimCode,table_name){
	//获取维表配置信息
	Ext.Ajax.request({
		url:pathUrl + '/managerDimTable/findDimInfo/'+dimCode,
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			if(!json.failure){
				//封装字段  取小写值 用于统一显示 
				var fields_name = json.results[0].fields_name.toLowerCase();
				var fields = fields_name.split(';');
				tableDataCm = getColumn(fields);
				tableDataStore = getStore(fields,table_name);
				//更新数据源
				tableDataPanel.reconfigure(tableDataStore,tableDataCm);
				tableDataPanel.getBottomToolbar().bind(tableDataStore);
				tableDataPanel.getStore().load({
					params:{table_name:table_name,start:0,limit:20},
					callback:function(record,options,success){
						if(!success){
							
						}
					}
				});
				
			}else{
				Ext.Msg.alert('错误信息',json.info);
			}
		}
		
	});
	
}

/**
 * 获取数据源配置
 * @param filelds
 */
function getColumn(fields){
	//将取得的字符串适用小写配置?
	var fieldObj = new Array();
	fieldObj.push(new Ext.grid.RowNumberer());
	for(var i = 0;i<fields.length;i++){
		var field = new Object();
		field.id = fields[i].split(',')[0].toLowerCase();
		field.header = fields[i].split(',')[1].toLowerCase();
		field.dataIndex = fields[i].split(',')[0].toLowerCase();
		fieldObj.push(field);
	}
	return new Ext.grid.ColumnModel(fieldObj);
	
}

/**
 * 封装维表数据源
 * @param fields
 * @param table_name
 * @returns {Ext.data.JsonStore}
 */
function getStore(fields,table_name){
	
	var fieldsObj = new Array();
	for(var i = 0;i<fields.length;i++){
		fieldsObj.push(fields[i].split(',')[0]);
	}
	
	var store = new Ext.data.JsonStore({
        url : pathUrl + '/managerDimTable/queryDimTableData/'+table_name,
        totalProperty:'totalCount',
        root:"results",
        fields:fieldsObj
    });
	//参数选项
	store.on('beforeload',function(){
		store.baseParams={
				table_name:table_name
		}
	});
	
	return store;
}


