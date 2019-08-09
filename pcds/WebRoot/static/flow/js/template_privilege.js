//function UserGrant(cust_mgr_id){
//	
//	var winTmplSm = new Ext.grid.CheckboxSelectionModel();
//	
//	var winTmplCm = new Ext.grid.ColumnModel([
//        new Ext.grid.RowNumberer(),
//        winTmplSm,
//        {
//			id: 'tmpl_id',
//            header: "tmpl_id", 
//            dataIndex: 'tmpl_id',
//            hidden : true
//        },{
//			id: 'template_name',
//            header: "模板名称", 
//            dataIndex: 'template_name'
//        },{
//			id: 'emplate_desc',
//            header: "模板描述", 
//            dataIndex: 'template_desc'
//        }
//    ]); 
//	
//	
//	var userTmplStore = new Ext.data.Store({
//		proxy : new Ext.data.HttpProxy({
//			url : pathUrl + '/flow/getBlmbByUserId'
//		}),
//		reader : new Ext.data.JsonReader({
//			root : 'results'
//		}, 
//		[{name : 'cust_mgr_id',mapping : 'cust_mgr_id'},
//		 {name : 'tmpl_id',mapping : 'tmpl_id'}
//		])
//	});
//	userTmplStore.load({params : {cust_mgr_id : cust_mgr_id},callback:userTmplLoad});
//	
//	var winTmplsInfoStore = new Ext.data.Store({
//		proxy : new Ext.data.HttpProxy({
//			url : pathUrl + '/flow/listBlmb'
//		}),
//		reader : new Ext.data.JsonReader({
//			root : 'results'
//		}, [{
//			name : 'tmpl_id',
//			mapping : 'tmpl_id'
//		}, {
//			name : 'template_name',
//			mapping : 'template_name'
//		}, {
//			name : 'template_desc',
//			mapping : 'template_desc'
//		}]) 
//    });
//    
//    function userTmplLoad(){
//    	winTmplsInfoStore.load({callback:doSelect})
//    }
//    
//    function doSelect(){
//    	var target = {},records=[];
//		for(var j=0;j<userTmplStore.getCount();j++){
//			var record = userTmplStore.getAt(j);
//			var roleId = record.get("tmpl_id");
//	        target[roleId] = record;
//	   	}
//	   	for(var i=0;i<winTmplsInfoStore.getCount();i++){
//			var record = winTmplsInfoStore.getAt(i);
//			var roleId = record.get("tmpl_id");
//			if(target[roleId]){
//				records.push(record);
//			}
//	   	}
//	   	winTmplsInfoGridPanel.getSelectionModel().selectRecords(records);//执行选中记录
//    }
//	
//	var winTmplsInfoGridPanel = new Ext.grid.GridPanel({
//		id:'winTmplsInfoGridPanelId',
//		anchor : '100% 100%',
//		ds: winTmplsInfoStore,
//		cm: winTmplCm,
//		sm:winTmplSm,
//		viewConfig: {forceFit:true},
//		loadMask : true
//	});
//	
//	var window = new Ext.Window({
//		title:'角色列表',
//        width: 600,
//        height:400,
//        layout:'fit',
//        model : true,
//        buttonAlign:'center',
//        items:[winTmplsInfoGridPanel],
//        buttons: [{
//            text: '保存', 
//            handler: function() {
//	        	var records = winTmplsInfoGridPanel.getSelectionModel().getSelections();
//	        	if(records.length<=0){
//	        		Ext.MessageBox.alert('选择提示','请给用户选择一个角色！');
//	        		return;
//	        	}
//	        	 var tmpls = [];
//	               	for(var i=0;i<records.length;i++){
//						var roleId = records[i].get("tmpl_id");
//						tmpls.push(roleId);
//				   	}
//	        	Ext.Ajax.request({
//	        		url : pathUrl + '/flow/addUserBlmb',
//	        		method : 'POST',
//	        		params : {cust_mgr_id:cust_mgr_id,user_tmpl:tmpls.join(",")},
//	        		callback: function (options, success, response) {
//						var json=Ext.util.JSON.decode(response.responseText);
//						if (json.success) { 
//							Ext.MessageBox.alert('提示','保存成功');
//							window.destroy();
//						} else {
//							Ext.MessageBox.alert('提示 ',json.info);
//						}
//					}
//	        	});
//	        }
//        },{
//            text: '取消',
//            handler: function(){window.destroy();}
//        }]
//    });
//	window.show();
//}

mbUserStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
		url : pathUrl + '/flow/getUserByBlmb'
	}),
	reader : new Ext.data.JsonReader({
		root : 'results'
	}, 
	[{name : 'cust_mgr_id',mapping : 'cust_mgr_id'},
	 {name : 'user_name',mapping : 'user_name'},
	 {name : 'tmpl_id',mapping : 'tmpl_id'},
	 {name : 'tmpl_name',mapping : 'tmpl_name'}
	])
});

//function userTmplLoad(){
//	c_n_store.load({params:{cust_mgr_id:Ext.getCmp('cust_mgr_id').getValue()},callback:doSelect})
//}

//function doSelect(){
//	var target = {},records=[];
//	for(var j=0;j<mbUserStore.getCount();j++){
//		var record = mbUserStore.getAt(j);
//		var cust_mgr_id = record.get("cust_mgr_id");
//	    target[cust_mgr_id] = record;
//	}
//	for(var i=0;i<c_n_store.getCount();i++){
//		var record = c_n_store.getAt(i);
//		var cust_mgr_id = record.get("cust_mgr_id");
//		if(target[cust_mgr_id]){
//			records.push(record);
//		}
//	}
//	c_n_panel.getSelectionModel().selectRecords(records);//执行选中记录
//}

function doSearch(){
	var cust_mgr_id = Ext.getCmp('cust_mgr_id').getValue();
	if(''==tmpl_id||null==tmpl_id){
		Ext.MessageBox.alert('提示','请先选择一个模板再进行查询');
		return;
	}
	c_n_store.load({params:{bank_org_id:'8888',start:0,limit:30,cust_mgr_id:cust_mgr_id,tmpl_id:tmpl_id}});
}

function grantUserBlmb(){
    var infos='';
    var rs = c_c_store.getRange();
	for(var i=0,l=rs.length;i<l;i++){
        var r=rs[i];
        infos+=(i==0?r.get('cust_mgr_id'):"@"+r.get('cust_mgr_id'));
        infos+=(','+r.get('user_name')+','+r.get('tmpl_id')+','+r.get('tmpl_name'));
//        infos+=','+r.get('last_cust_mgr_id')+','+(r.get('last_cust_mgr_name')==''||r.get('last_cust_mgr_name')==null?'!':r.get('last_cust_mgr_name'));
    }
	Ext.MessageBox.confirm('提示','确认将选中的用户分配模板权限',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : pathUrl + '/flow/addUserBlmb2',
				method : 'POST',
				params : { infos : infos},
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (success) { 
						Ext.MessageBox.alert('提示',json.info);
						c_c_store.removeAll();
						w_c_store.reload();
						c_n_store.reload();
					} else {
						Ext.MessageBox.alert(json.info);
					}
				}
			})
		}
	});
}

function resetStore(){
	c_n_store.reload();
	c_c_store.removeAll();
}

function removeUser(){
    var infos='';
    var rs = w_c_panel.getSelectionModel().getSelections();;
	for(var i=0,l=rs.length;i<l;i++){
        var r=rs[i];
        infos+=(i==0?r.get('cust_mgr_id'):"@"+r.get('cust_mgr_id'));
        infos+=(','+r.get('tmpl_id'));
//        infos+=','+r.get('last_cust_mgr_id')+','+(r.get('last_cust_mgr_name')==''||r.get('last_cust_mgr_name')==null?'!':r.get('last_cust_mgr_name'));
    }
	Ext.MessageBox.confirm('提示','确认将选中的用户移除模板权限',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : pathUrl + '/flow/removeUser',
				method : 'POST',
				params : { infos : infos},
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (success) { 
						w_c_store.reload();
						c_n_store.reload();
//						Ext.MessageBox.alert('提示',json.info);
					} else {
						Ext.MessageBox.alert(json.info);
					}
				}
			})
		}
	});
}