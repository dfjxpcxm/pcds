var url=pathUrl+'/tableManageAction';
var url1=pathUrl+'/baseConfigAction.do';
var str_localTables,str_nowSelect;
var lMask,sMask,tMask,bMask;
//数据库列表 store
var databaseStore=new Ext.data.JsonStore({
    url: url+'/getDataBase',
    root: 'results',
    fields: ['database_id','database_name']
});

//数据库用户列表 store
var ownerStore=new Ext.data.JsonStore({
    url: url+'/getDBUser',
    root: 'results',
    fields: ['database_id','db_user_id','owner_name']
});

//增加或者修改时数据库列表 store
var databaseStore1=new Ext.data.JsonStore({
    url: url+'/getDataBase',
    root: 'results',
    fields: ['database_id','database_name']
});
//增加或者修改时数据库用户列表 store
var ownerStore1=new Ext.data.JsonStore({
    url: url+'/getDBUser',
    root: 'results',
    fields: ['database_id','db_user_id','owner_name']
});

//主题
var theme_ds=new Ext.data.JsonStore({
    url: url+'/getThemeList',
    root: 'results',
    fields: ['theme_id','theme_name']
});

var tableSpace_ds=new Ext.data.JsonStore({
    url: url+'/getTableSpaceList',
    root: 'results',
    fields: ['tablespace_id', 'tablespace_name']
});

//字段类别
var conTableType_ds=new Ext.data.JsonStore({
    url: url+'/getConTableTypeList',
    root: 'results',
    fields: ['column_type_code','column_type_name']
});

//字段类型
var dataType_ds=new Ext.data.JsonStore({
    url: url+'/getDataTypeList',
    root: 'results',
    fields: ['data_type_code','data_type_name']
});

var partitionType_ds=new Ext.data.JsonStore({
    url: url+'?method=getPartitionTypeList',
    root: 'results',
    fields: ['partition_type_code','partition_type_desc']
});

//字段类型
var dataType_ds=new Ext.data.JsonStore({
    url: url+'/getDataTypeList',
    root: 'results',
    fields: ['data_type_code','data_type_name']
});
//约束类型
var constraintsType_ds=new Ext.data.JsonStore({
    url: url+'?method=getConstraintsTypeList',
    root: 'results',
    fields: ['constraints_type_code','constraints_type_desc']
});

var leftGrid_cm= new Ext.grid.ColumnModel( [ 
				{
					xtype : 'gridcolumn',
					dataIndex : 'table_id',
					align : 'left',
					header : '表id',
					hidden:true,
					sortable : true
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'table_name',
					align : 'left',
					header : '表名',
					width:220,
					sortable : true
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'table_desc',
					align : 'left',
					header : '描述',
					width:220,
					sortable : true
				}, {
					xtype : 'gridcolumn',
					align : 'left',
					dataIndex : 'display_order',
					header : '序号',
					hidden:true,
					sortable : true
				}
				]);
		      
var leftGrid_sd = new Ext.data.JsonStore( {
		root : 'results',
		id:'table_id',
		totalProperty : 'totalCount',
		fields : [ 'table_id',
			       'table_name',
			       'theme_id',
			       'table_desc',
			       'display_order',
			       'db_user_id',
			       'database_id',
			       'tablespace_id',
			       'status_code',
			       'is_exist',
			       'statue_desc',
			       'displaydesc',
			       'is_display',
			       'display_order',
			       'theme_name',
			       'database_name',
			       'tablespace_name',
			       'owner_name'
			    ]
	});
	
//本地数据库用户下表信息列表 store
var importGridSd=new Ext.data.JsonStore( {
						root : 'results',
						id:'table_id',
						fields : [ 
							       'table_name',
							       'show_table_name',
							       'owner_name',
							       'tablespace_name',
							       'partition_type_code',
							       'database_id',
							       'table_desc'
							    ]
					});


//本地所选择表的字段信息grid columns
var lcm1=[
    {header:'<span style="color:red">*</span>列名',dataIndex:'column_name',width:150,align:'left',
    	editor:{xtype:'textfield',allowBlank:false}},
    {header:'列描述',dataIndex:'column_desc',width:150,align:'left',editor:{xtype:'textfield'}},
    {header:'是否显示',align:'left',dataIndex:'is_display',width:100,editor:{xtype:'checkbox',align:'left',boxLabel:'',value:'Y',checked:true},
     renderer:function(v){if(v){return v=='Y'?'是':'否';}}},
    {header:'<span style="color:red">*</span>字段类型',dataIndex:'data_type_name',width:100,align:'left',
	     editor:{
	         xtype: 'combo',
	         typeAhead: true,
	         allowBlank:false,
	         mode: 'local',
	         triggerAction: 'all',
	         valueField: 'data_type_code',
	         displayField: 'data_type_name',
	         store: dataType_ds}},
	         
	{header:'<span style="color:red">*</span>数据类型',dataIndex:'column_type_name',width:100,align:'left',
	     editor:{
	         xtype: 'combo',
	         typeAhead: true,
	         mode: 'local',
	         triggerAction: 'all',
	         allowBlank:false,
	         valueField: 'column_type_code',
	         displayField: 'column_type_name',
	         store: conTableType_ds}},
     
    {header:'<span style="color:red">*</span>数据长度',dataIndex:'data_length',width:80,
    	 align:'right',editor:{xtype:'textfield',allowBlank:false}},
    	 
    {header:'数据刻度',dataIndex:'data_scale',width:80,
    	 align:'right',editor:{xtype:'textfield'}},
    	 
    {header:'可否为空',align:'left',dataIndex:'is_nullable',width:80,
    	 editor:{xtype:'checkbox',align:'center',boxLabel:'',value:'Y'},
     renderer:function(v){if(v){return v=='Y'?'是':'否';}}},
     
     {header:'可否编辑',align:'left',dataIndex:'is_editable',width:80,
    	 editor:{xtype:'checkbox',align:'center',boxLabel:'',value:'Y',checked:true},
    	   renderer:function(v){if(v){return v=='Y'?'是':'否';}}},
    	   
    {header:'默认值',dataIndex:'default_value',width:80,
    	 align:'left',editor:{xtype:'textfield'}},
    	 
    {header:'字段顺序号',dataIndex:'display_order',width:80,
    	 align:'left',editor:{xtype:'numberfield'}}
];
//本地所选择表的字段信息列表 store
var lds1=new Ext.data.JsonStore({
    url: url,
    root: 'results',
    fields: ['column_id','table_id','column_name','column_desc','data_type_code',
	 		  'column_type_code','data_length','data_scale','is_editable','is_nullable',
	 		  'default_value','display_order','is_display','data_type_name','column_type_name'
    ]
});

//--------------------------------------------------------
/*
//源所选择表的字段信息grid columns
var scm1=[
    {header:'列名',dataIndex:'column_name',width:200,align:'left'},
    {header:'字段类型',dataIndex:'data_type_code',width:150,align:'left'},
    {header:'数据长度',dataIndex:'data_length',width:100,align:'right'},
    {header:'数据刻度',dataIndex:'data_scale',width:100,align:'right'},
    {header:'可否为空',dataIndex:'is_nullable',width:100,align:'left',
     renderer:function(v){return v=='1'?'是':'否';}},
    {header:'默认值',dataIndex:'default_value',width:100,align:'left'},
    {header:'字段顺序号',dataIndex:'display_order',width:100,align:'left'}
];
//源所选择表的字段信息列表 store
var sds1=new Ext.data.JsonStore({
    url: url,
    root: 'results',
    fields: ['column_name','column_desc','is_display','data_type_code','column_type_code','data_length',
        'data_scale','is_nullable','default_value','display_order'
    ]
});

*/

//获取选择的数据库ID、用户名和数据库连接名
function getBaseInfo(){
    var str_owner=Ext.getCmp('owner').getRawValue();
    var db_user_id=Ext.getCmp('owner').getValue();
    var str_database=Ext.getCmp('database').getValue();
    var database_name=Ext.getCmp('database').getRawValue();
    var obj_o=databaseStore.getAt(databaseStore.find('database_id',str_database));
    if(obj_o){
        return {database_id:str_database,db_user_id:db_user_id,owner_name:str_owner,database_name:database_name};
    }
    return false;
}
//获取选择的节点
function getSelectNode(treePanel_id){
	if(Ext.getCmp(treePanel_id).getSelectionModel().getSelections()[0]!=null){
		var table_id=Ext.getCmp(treePanel_id).getSelectionModel().getSelections()[0].get("table_id");
		var table_name=Ext.getCmp(treePanel_id).getSelectionModel().getSelections()[0].get("table_name");
		return {table_id:table_id,table_name:table_name};
	}
	return null;
}

//获取选择的节点
function getSelectFieldNode(treePanel_id){
	if(Ext.getCmp(treePanel_id).getSelectionModel().getSelections()[0]!=null){
		var column_id=Ext.getCmp(treePanel_id).getSelectionModel().getSelections()[0].get("column_id");
		return {column_id:column_id};
	}
	return null;
}
//获取激活的选项卡
function getMyActiveTab(){
    return Ext.getCmp('tab').getActiveTab();
}
//获取数据store
function getColumns(los,type){
    if('l'==los){
    	if('t0'==type)
		    return leftGrid_cm;
        if('t1'==type)
		    return lcm1;
		
	}else if('s'==los){
		if('t0'==type)
		    return leftGrid_cm;
		if('t1'==type)
		    return scm1;
	}
	return null;
}
//获取数据store
function getStore(los,type){
    if('l'==los){
        if('t0'==type)
		    return leftGrid_sd;
		else if('t1'==type)
		    return lds1;
	}else if('s'==los){
		if('t0'==type)
		    return importGridSd;
		//else if('t1'==type)
		    //return sds1;
	}
	return null;
}

//数据库选择事件
function databaseSelect(combo, record, index){
    ownerStore.removeAll();
    ownerStore.load({
        params:{database_id:record.get('database_id')},
        callback: function(r, options, success){
            if(!success){
                Ext.MessageBox.alert('消息','加载用户列表失败！');
            }
            Ext.getCmp('owner').setValue('');
        }
    });
}


//本地table列表节点单击事件
function lTreeNodeClick(node,e){
    /*var obj_tab=getMyActiveTab();
    var arr_stores=getStore('l','all');
    for(var i=0;i<arr_stores.length;i++)
        arr_stores[i].removeAll();
    loadLocalStore(node.id,obj_tab.id);*/
	
}

			
//源table列表节点单击事件
function sTreeNodeClick(node,e){
    var obj_tab=getMyActiveTab();
    var arr_stores=getStore('s','all');
    for(var i=0;i<arr_stores.length;i++)
        arr_stores[i].removeAll();
    loadSourceStore(node.text,obj_tab.id);
}

//tab激活事件
function activate(tab){
	 if(tab.id=='t0'){
    	 Ext.getCmp("delField").hide();
    }else{
    	 Ext.getCmp("delField").show();
    }
	if('t0'!=tab.id && getStore('l',tab.id).getTotalCount()>0)
	    return;
    var obj_node=getSelectNode('localTableList');
    if(obj_node){
        loadLocalStore(obj_node.table_id,tab.id);
    }
}

function loadOtherInfo(los,params){
	var obj_mask='l'==los?lMask:sMask;
    var obj_store='l'==los?leftGrid_sd:sds;
    obj_mask.show();
}

//刷新选项卡
function refreshTab(){
	var obj_tab=getMyActiveTab();
    var obj_node=getSelectNode('localTableList');
    if(obj_node){
        if(!('t0'==obj_tab.id))
            getStore('l',obj_tab.id).removeAll();
        loadLocalStore(obj_node.table_id,obj_tab.id);
    }
}
//刷新本地table列表
function refreshLTableList(){
    refreshTableList('l');
}
//刷新源table列表
function refreshSTableList(searchCon){
    refreshTableList('s',searchCon);
}

//刷新table列表los[本地or源]
function refreshTableList(los,searchCon){
	var obj_o=getBaseInfo();
	if(obj_o){
        var storeUrl=('l'==los?url+'/getLocalTable':url+'/getRemoteTable');
        var obj_selected=getSelectNode('localTableList');
        if(obj_selected){
            str_nowSelect=obj_selected.table_name;
        }
        var arr_stores=getStore(los,'all');
    	/*for(var i=0;i<arr_stores.length;i++){
            arr_stores[i].removeAll();
    	} */
        
        if('l'==los){
	        leftGrid_sd.proxy=new Ext.data.HttpProxy({url:storeUrl});
        	leftGrid_sd.load({params:{database_id:obj_o.database_id,database_name:obj_o.database_name,owner_name:obj_o.owner_name,db_user_id:obj_o.db_user_id}});
        }else{
        	importGridSd.proxy=new Ext.data.HttpProxy({url:storeUrl});
        	importGridSd.load({params:{database_id:obj_o.database_id,database_name:obj_o.database_name,owner_name:obj_o.owner_name,db_user_id:obj_o.db_user_id,searchCon:searchCon}});
        }
    }
}

//加载本地数据store
function loadLocalStore(table_id,type){
    var obj_store=getStore('l',type);
    if('t0'==type){
    	var obj_record=obj_store.getAt(obj_store.find('table_id',table_id));
    	Ext.getCmp(type).getForm().loadRecord(obj_record);
    }else{
        var obj_params={table_id:table_id,type:type};
        obj_store.proxy=new Ext.data.HttpProxy({url:url+'/getLocalTableInfo'});
        obj_store.load({
            params:obj_params,
            callback: function(r, options, success){
                if(!success)
                    Ext.MessageBox.alert('消息','加载数据信息失败！');
            }
        });
    }
}
//加载源数据store
function loadSourceStore(table_name,type){
    var obj_store=getStore('s',"t0");
    var obj_record=obj_store.getAt(obj_store.find('table_name',table_name));
    Ext.getCmp('rt0').getForm().loadRecord(obj_record);
    /*if('t0'==type){
    	
    }else{
        var obj_params={
            //method:'getTableInfo',
            database_id:getBaseInfo().database_id,
            database_name:getBaseInfo().database_name,
            owner_name:getBaseInfo().owner_name,
            db_user_id:getBaseInfo().db_user_id,
            table_name:table_name,
            type:type
        };
        if(obj_store!=null){
	        obj_store.proxy=new Ext.data.HttpProxy({url:url+'/getTableInfo'});
	        obj_store.load({
	            params:obj_params,
	            callback: function(r, options, success){
	                if(!success)
	                    Ext.MessageBox.alert('消息','加载数据信息失败！');
	            }
	        });
        }
    }*/
}

//创建table
function create(){
    if(getBaseInfo().owner_name){
    	createWindowUi();
    	var searchCon=Ext.getCmp("inputCon").getValue();
	    refreshSTableList(searchCon);
	}else{
	    Ext.MessageBox.alert('消息','请选择数据库和用户!');
	}
}
//配置表信息
var addFlag=true;
var addFieldFlag=true;
var  obj_window=null;
function dispose(){
    var obj_tab=getMyActiveTab();
    var obj_node=getSelectNode('localTableList');
    //Ext.getCmp("table_id_field").setValue(obj_node.table_id);
    var field_obj_node=getSelectFieldNode('fieldGrid');
    if(!obj_node && obj_tab.id!='t0'){
        Ext.MessageBox.alert('消息','请先选择表！');
        return;
    }
   obj_window=myDisposeWindowUi();
   if((obj_node==null && obj_tab=='t0' ) || (field_obj_node==null && obj_tab=='t1')){
    	obj_window.title='新增'+obj_tab.title;
   }else{
   		obj_window.title='修改'+obj_tab.title;
   }
   
   
    
    obj_window.on('close',function(p){
        if ('t0' == getMyActiveTab().id){
	        refreshLTableList();
        }
	    refreshTab();
	    obj_window.destroy();
    });
    obj_window.show();
    theme_ds.load();
    dataType_ds.load();
    tableSpace_ds.load();
    databaseStore1.load();
    ownerStore1.load();
    
    //grid拖拽
    /*if(!('t0'==obj_tab.id)){
    	//Ext.getCmp("addBtn").hide();
        /*var sourceEl = Ext.getCmp('source').getView().scroller.dom;
        var sourceTarget = new Ext.dd.DropTarget(sourceEl, {
            ddGroup: 'g1',
            notifyDrop: function(ddSource, e, data){
                var records=ddSource.dragData.selections;
                Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
                return true;
            }
        });
        var localEl = Ext.getCmp('local').getView().scroller.dom;
        var localTarget = new Ext.dd.DropTarget(localEl, {
            ddGroup: 'g2',
            notifyDrop: function(ddSource, e, data){
                var records=ddSource.dragData.selections;
                Ext.getCmp('local').store.add(records);
                return true;
            }
        });*/
    //}
    if('t0'==obj_tab.id){
    	//修改
    	if(obj_node){
    		addFlag=false;
    		Ext.getCmp("addBtn").show();
    		Ext.getCmp("addBtn").setText("新增表");
	        var obj_store=getStore('l','t0');
	        var obj_record=obj_store.getAt(obj_store.find('table_id',obj_node.table_id));
	    	Ext.getCmp('local').getForm().loadRecord(obj_record);
	        Ext.getCmp("themeCombo").setValue(obj_record.get("theme_id"));
	        Ext.getCmp("themeCombo").setRawValue(obj_record.get("theme_name"));
	    	return;
    	}else{//新增
    		addFlag=true;
    		clearForm();
    		obj_window.setTitle('新增'+obj_tab.title);
    	}
    }else{
    	//修改
    	if(field_obj_node){
    		addFieldFlag=false;
	    	Ext.getCmp("addBtn").show();
	    	Ext.getCmp("addBtn").setText("新增字段");
	    	 var obj_store=getStore('l','t1');
	       	 var obj_record=obj_store.getAt(obj_store.find('column_id',field_obj_node.column_id));
			Ext.getCmp('local').getForm().loadRecord(obj_record);
			return;
    	}else{//新增
    		addFieldFlag=true;
    		clearForm();
    		obj_window.setTitle('新增'+obj_tab.title);
    	}
    	//loadSourceStore(obj_node.table_name,obj_tab.id);
    	
    }
    //getStore('s',obj_tab.id).removeAll();
    
    
}
//保存创建table
function createTable(){
	var obj_record=Ext.getCmp("tableList").getSelectionModel().getSelections()[0];
	if(obj_record.get('show_table_name').indexOf('@')!=-1){
		Ext.MessageBox.alert('消息',"该表已经存在！");
		return;    			
	}
	var formPanel = Ext.getCmp("rt0");
	if (formPanel.form.isValid()) {
		Ext.MessageBox.confirm('确认信息','确认导入表到本地?',function(btn){
        if(btn=='yes')
	            saveStore('s','t0');
	    });
	}
    
}
//配置表信息
function disposeInfo(){
	//修改表信息
    if('t0'==getMyActiveTab().id ){
    	if(!addFlag){
	        var obj_record=leftGrid_sd.getAt(leftGrid_sd.find('table_id',getSelectNode('localTableList').table_id));
	        var obj_form=Ext.getCmp('local').getForm();
	        obj_form.updateRecord(obj_record);
    		
    	}
    	var formPanel = Ext.getCmp("local");
		if (formPanel.form.isValid()) {
			 Ext.MessageBox.confirm('确认信息','确认保存?',function(btn){
		        if(btn=='yes')
		            saveStore('l',getMyActiveTab().id);
		    });
		}
    }else{
    	 var formPanel = Ext.getCmp("local");
    	 if (formPanel.form.isValid()) {
	    	 Ext.MessageBox.confirm('确认信息','确认保存?',function(btn){
			        if(btn=='yes')
			            saveStore('l',getMyActiveTab().id);
			    });
    	 }   
    }
}


//保存信息
function saveStore(los,type){
	var path=null;
	if(('s'==los && 't0'==type) || ('l'==los && addFlag && 't0'==type)){
		path=url+'/createTable';
	}
	
	if(!addFlag && 't0'==type ){
		path=url+'/disposeTableInfo';
	}
	
	if(addFieldFlag && 't1'==type ){
		path=url+'/addField';
	}
	
	if(!addFieldFlag && 't1'==type ){
		path=url+'/updateField';
	}
    Ext.Ajax.request({
        url: path+"?"+getParams(los,type),
        method:'post',
        //params: 
        failure : function(response, options) {
            Ext.MessageBox.alert('消息',Ext.util.JSON.decode(response.responseText).info)
		},
		success : function(response, options) {
			var obj_json = Ext.util.JSON.decode(response.responseText);
			Ext.MessageBox.alert('消息',obj_json.info);
			if(obj_json.success){
			    if('s'==los && 't0'==type){
			        //Ext.getCmp('createWindow').destroy();
			    }
			    else{
			        Ext.getCmp('disposeWindow').destroy();
			    }
			    refreshLTableList();
			    var obj_tab=getMyActiveTab();
    			var obj_node=getSelectNode('localTableList');
			    loadLocalStore(obj_node.table_id,obj_tab.id);
				    
			}
		}
   });
}
//添加记录
function addRecord(){
    var obj_grid=Ext.getCmp('local');
    var obj_store=obj_grid.getStore();
    var obj_record=obj_store.recordType;
    var obj_r=new obj_record();
    obj_grid.stopEditing();
    obj_store.insert(0,obj_r);
    obj_grid.startEditing(0, 0);
}
//删除记录
function deleteRecord(){
    var obj_grid=Ext.getCmp('local');
    var obj_store=obj_grid.getStore();
    var arr_records=obj_grid.getSelectionModel().getSelections();
    for(var i=0;i<arr_records.length;i++){
        obj_store.remove(arr_records[i]);
    }
}
function getParams(los,type){
    var obj_store=getStore(los,type);
    //新增表信息参数
    var params="";
	var str_params,field_params,database_id,db_user_id,owner_name,table_id,database_name,
			addTable,column_id='';
	database_id=getBaseInfo().database_id;
	db_user_id=getBaseInfo().db_user_id;
	owner_name=getBaseInfo().owner_name;
	database_name=getBaseInfo().database_name;
	str_id=(getSelectNode('localTableList') ? getSelectNode('localTableList').table_id : "") ;
	column_id=(getSelectFieldNode('fieldGrid') ? getSelectNode('fieldGrid').table_id : "") ;
	
	if('s'==los && addFlag){
		str_params=Ext.getCmp("rt0").getForm().getValues(true);
		
		//str_params="";
		 //var obj_record=Ext.getCmp("tableList").getSelectionModel().getSelections()[0];
		 //str_params=obj_record.get('table_name')+','+Ext.getCmp("tablespace_id_import").getValue()+','+obj_record.get('table_desc')
		 			//+','+Ext.getCmp('theme_id').getValue()+","+Ext.getCmp('statusCode').getValue();
		 addTable='01';
		//return {database_id:getBaseInfo().database_id,owner_name:getBaseInfo().owner_name,
		 			//db_user_id:getBaseInfo().db_user_id,records:str_params};
		
		//params={database_id:getBaseInfo().database_id,owner_name:getBaseInfo().owner_name,db_user_id:getBaseInfo().db_user_id,records:str_params};			
	}
	if('s'!=los && type=='t0' && addFlag){	
		str_params=Ext.getCmp("local").getForm().getValues(true);;
		/*var formVal=Ext.getCmp("local").getForm().getValues(false);
		str_params+=(formVal.table_name);
		str_params+=(','+Ext.getCmp("tablespace_id_up").getValue());
		str_params+=(','+formVal.table_desc);
	    str_params+=(','+formVal.theme_id);
		str_params+=(','+formVal.status_code);
		str_params+=(','+formVal.is_display);
		str_params+=(','+formVal.display_order);*/
		//return {database_id:Ext.getCmp("database_id_up").getValue(),
				//db_user_id:Ext.getCmp("db_user_id_up").getValue(),records:str_params};
		database_id=Ext.getCmp("database_id_up").getValue();
		db_user_id=Ext.getCmp("db_user_id_up").getValue();
		addTable='02';
	}
	if('l'==los && type=='t0' && !addFlag){	
		str_params=Ext.getCmp("local").getForm().getValues(true);;
		/*var formVal=Ext.getCmp("local").getForm().getValues(false);
		str_params+=(formVal.table_name);
		str_params+=(','+Ext.getCmp("tablespace_id_up").getValue());
		str_params+=(','+formVal.table_desc);
	    str_params+=(','+formVal.theme_id);
		str_params+=(','+formVal.status_code);
		str_params+=(','+formVal.is_display);
		str_params+=(','+formVal.display_order);*/
		//return {database_id:Ext.getCmp("database_id_up").getValue(),
				//db_user_id:Ext.getCmp("db_user_id_up").getValue(),records:str_params};
	}
	if('l'==los && type=='t1'){	
		str_params=Ext.getCmp("local").getForm().getValues(true);
		/*var formVal=Ext.getCmp("local").getForm().getValues(false);
		str_params+=(formVal.table_name);
		str_params+=(','+Ext.getCmp("tablespace_id_up").getValue());
		str_params+=(','+formVal.table_desc);
	    str_params+=(','+formVal.theme_id);
		str_params+=(','+formVal.status_code);
		str_params+=(','+formVal.is_display);
		str_params+=(','+formVal.display_order);
		//return {database_id:Ext.getCmp("database_id_up").getValue(),
				//db_user_id:Ext.getCmp("db_user_id_up").getValue(),records:str_params};
		database_id=Ext.getCmp("database_id_up").getValue();
		db_user_id=Ext.getCmp("db_user_id_up").getValue();*/
	}
	
	var status_code=null;
	if(Ext.getCmp("localTableList").getSelectionModel().getSelections()[0]!=null){
 	 	status_code=Ext.getCmp("localTableList").getSelectionModel().getSelections()[0].get('status_code');
 	 	table_id=(getSelectNode('localTableList') ? getSelectNode('localTableList').table_id : "") ;
	}
	
	//obj_store=sds1;
  /* for(var i=0;i<obj_store.getCount();i++){
	    var obj_record=obj_store.getAt(i);
	    field_params+=(i==0?obj_record.get('column_name'):'@'+obj_record.get('column_name'));
	    field_params+=(','+obj_record.get('column_desc'));
	    field_params+=(','+(obj_record.get('is_display')));
	    field_params+=(','+obj_record.get('data_type_code'));
	    field_params+=(','+obj_record.get('column_type_code'));
	    field_params+=(','+obj_record.get('data_scale'));
	    field_params+=(','+obj_record.get('is_editable'));
	    field_params+=(','+obj_record.get('data_length'));
	    field_params+=(','+(obj_record.get('is_nullable')));
	    field_params+=(','+obj_record.get('default_value'));
	    field_params+=(','+obj_record.get('display_order'));
	}*/
	 //params={database_id:database_id,db_user_id:db_user_id,owner_name:owner_name,database_name:database_name,
    		//table_id:str_id,status_code:status_code,type:type,records:str_params,fieldrecords:field_params,addTable:addTable};
	
	params=str_params+"&database_id="+database_id+"&db_user_id="
			+db_user_id+"&owner_name="+owner_name+"&database_name="+database_name
			+"&addTable="+addTable+"&type="+type+"&table_id="+table_id+"&column_id="+column_id;
//     /alert(params);
	 return params;
	
	//-------------------------------
   /* if('t0'==type && addFlag){
    	if('s'==los ){
    		 var obj_record=Ext.getCmp("tableList").getSelectionModel().getSelections()[0];
			 str_params=obj_record.get('table_name')+','+Ext.getCmp("tablespace_id_import").getValue()+','+obj_record.get('table_desc')
			 			+','+Ext.getCmp('theme_id').getValue()+","+Ext.getCmp('statusCode').getValue();
			//return {database_id:getBaseInfo().database_id,owner_name:getBaseInfo().owner_name,
			 			//db_user_id:getBaseInfo().db_user_id,records:str_params};
			database_id=getBaseInfo().database_id;
			db_user_id=getBaseInfo().db_user_id;
			owner_name=getBaseInfo().owner_name;
			params={database_id:getBaseInfo().database_id,owner_name:getBaseInfo().owner_name,db_user_id:getBaseInfo().db_user_id,records:str_params};			
    	}else{
    		var formVal=Ext.getCmp("local").getForm().getValues(false);
    		str_params+=(formVal.table_name);
			str_params+=(','+Ext.getCmp("tablespace_id_up").getValue());
			str_params+=(','+formVal.table_desc);
		    str_params+=(','+formVal.theme_id);
			str_params+=(','+formVal.status_code);
			str_params+=(','+formVal.is_display);
			str_params+=(','+formVal.display_order);
			//return {database_id:Ext.getCmp("database_id_up").getValue(),
					//db_user_id:Ext.getCmp("db_user_id_up").getValue(),records:str_params};
			database_id=Ext.getCmp("database_id_up").getValue();
			db_user_id=Ext.getCmp("db_user_id_up").getValue();
    	}
    	
    	
    }else{
        var str_id=(getSelectNode('localTableList') ? getSelectNode('localTableList').table_id : "") ;
        //修改表和表字段参数
        if('t0'==type && !addFlag){
        	var formVal=Ext.getCmp("local").getForm().getValues(false);
		    field_params+=(formVal.theme_id);
			field_params+=(','+formVal.is_display);
			field_params+=(','+formVal.status_code);
			field_params+=(','+formVal.database_id);
			field_params+=(','+formVal.db_user_id);
			field_params+=(','+formVal.tablespace_id);
			field_params+=(','+formVal.table_name);
			field_params+=(','+formVal.table_desc);
			field_params+=(','+formVal.display_order);
			       
        }else if('t1'==type){
	 		var status_code=Ext.getCmp("localTableList").getSelectionModel().getSelections()[0].get('status_code');
           for(var i=0;i<obj_store.getCount();i++){
			    var obj_record=obj_store.getAt(i);
			    field_params+=(i==0?obj_record.get('column_name'):'@'+obj_record.get('column_name'));
			    field_params+=(','+obj_record.get('column_desc'));
			    field_params+=(','+(obj_record.get('is_display')));
			    field_params+=(','+obj_record.get('data_type_code'));
			    field_params+=(','+obj_record.get('column_type_code'));
			    field_params+=(','+obj_record.get('data_scale'));
			    field_params+=(','+obj_record.get('is_editable'));
			    field_params+=(','+obj_record.get('data_length'));
			    field_params+=(','+(obj_record.get('is_nullable')));
			    field_params+=(','+obj_record.get('default_value'));
			    field_params+=(','+obj_record.get('display_order'));
			}
			
			//alert(field_params);
        }
        //return {table_id:str_id,status_code:status_code,type:type,records:field_params};
        params={database_id:database_id,db_user_id:db_user_id,owner_name:owner_name,
        		table_id:str_id,status_code:status_code,type:type,records:str_params,fieldrecords:field_params};
    }
    return params;*/
}


function deleteTable(){
	var selectRow=getSelectNode('localTableList');
	if(selectRow==null){
		Ext.MessageBox.alert('消息',"请选择行！");
		return;
	}
	
		var table_name=selectRow.table_name;
		 Ext.Ajax.request({
	        url: url+"/getTableDataCount",
	        params: {table_name:table_name},
	        failure : function(response, options) {
	            Ext.MessageBox.alert('消息',Ext.util.JSON.decode(response.responseText).info)
			},
			success : function(response, options) {
				var result = Ext.util.JSON.decode(response.responseText);
				if(result.success){
					Ext.MessageBox.confirm('确认信息','确认删除嘛?',function(btn){
				        if(btn=='yes'){
				        	var table_id=selectRow.table_id;
							 Ext.Ajax.request({
						        url: url+"/deleteTable",
						        params: {table_id:table_id},
						        failure : function(response, options) {
						            Ext.MessageBox.alert('消息',Ext.util.JSON.decode(response.responseText).info)
								},
								success : function(response, options) {
									var obj_json = Ext.util.JSON.decode(response.responseText);
									Ext.MessageBox.alert('消息',obj_json.info);
									refreshLTableList();
									Ext.getCmp('t0').getForm().reset();
								}
						   });
				        }
				    });
				}else{
					 Ext.MessageBox.alert('消息',result.info)
				}
			}
	   });

	
    
}

function deleteField(){
	var selectRow=getSelectFieldNode('fieldGrid');
	if(selectRow==null){
		Ext.MessageBox.alert('消息',"请选择行！");
		return;
	}
	 Ext.MessageBox.confirm('确认信息','确认删除嘛?',function(btn){
        if(btn=='yes'){
        	var column_id=selectRow.column_id;
			 Ext.Ajax.request({
		        url: url+"/deleteField",
		        params: {column_id:column_id},
		        failure : function(response, options) {
		            Ext.MessageBox.alert('消息',Ext.util.JSON.decode(response.responseText).info)
				},
				success : function(response, options) {
					var obj_json = Ext.util.JSON.decode(response.responseText);
					Ext.MessageBox.alert('消息',obj_json.info);
					refreshLTableList();
					var table_id=getSelectNode("localTableList").table_id;
					var obj_tab=getMyActiveTab();
					 loadLocalStore(table_id,obj_tab.id);
				}
		   });
        	
        }
            
    });
}

function clearForm(){
	if(Ext.getCmp('local')){
		Ext.getCmp('local').getForm().reset();
	}
	Ext.getCmp("addBtn").hide();
	addFlag=true;
	addFieldFlag=true;
	if(obj_window!=null){
		var obj_tab=getMyActiveTab();
		obj_window.setTitle('新增'+obj_tab.title);
	}
}
