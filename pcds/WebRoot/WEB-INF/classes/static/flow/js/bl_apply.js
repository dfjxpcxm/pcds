function approveInfo(gridDataStore,rs,status_code){
    var infos='';
	for(var i=0,l=rs.length;i<l;i++){
        var r=rs[i];
        infos+=(i==0?r.get('bl_info_id'):"@"+r.get('bl_info_id'));
//        infos+=','+r.get('last_cust_mgr_id')+','+(r.get('last_cust_mgr_name')==''||r.get('last_cust_mgr_name')==null?'!':r.get('last_cust_mgr_name'));
    }
	Ext.MessageBox.confirm('提示','确认'+('05'==status_code?'通过':'退回')+'选中的信息?',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : pathUrl + '/flow/approveFlow',
				method : 'POST',
				params : { infos : infos,flow_status_code : status_code },
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (success) { 
						Ext.MessageBox.alert('提示',json.info);
						gridDataStore.reload();
					} else {
						Ext.MessageBox.alert(json.info);
					}
				}
			})
		}
	});
}

function showDetail(){
	var win = new Ext.Window({
		maximized : true,
		title : '数据明细',
		modal : true,
		bodyStyle : 'padding:10px;',
		buttonAlign : 'center',
		items : [{
			xtype : 'form',
			id : 'updatePanel',
			baseCls : 'x-plain',
			labelWidth : 85,
			items : [
			{
				xtype : 'textfield',
				fieldLabel : '数据明细',
				allowBlank : false,
				id : "theme_name",
				name : 'theme_name',
				anchor : '100%'
			}
			]
		}],
		buttons : [{
			text : '提交',
			hidden : '02'==r.get("flow_status_code")||'05'==r.get("flow_status_code"),
			handler : function() {
				applyData('02');
			}
		}, {
			text : '撤回',
			hidden : '02'!=r.get("flow_status_code"),
			handler : function() {
				applyData('03');
			}
		}, {
			text : '关闭',
			handler : function() {
				win.destroy();
			}
		}]
	});
	win.show();
	
	function applyData(flow_status_code){
//		if(r.get("apply_user_id")==login_user_id){
//			Ext.MessageBox.alert('提示','不能审批自己提交的数据哦！！！');
//			return;
//		}
		Ext.Ajax.request({
	        url: pathUrl+'/flow/updateApproStatus',
	        params: {task_id:task_id,flow_status_code:flow_status_code,remark:'02'==flow_status_code?'提交':'撤回',status:'00'},
	        failure:function(response, options){
	            Ext.MessageBox.alert(response.responseText);
	        },
	        success:function(response, options){
	            var json=Ext.util.JSON.decode(response.responseText);
	            Ext.Msg.alert('消息',json.info);
	            if (json.success) {
	            	win.destroy();
	            	gridDataStore.reload();
	            }
	        }
	    });
	}
}

