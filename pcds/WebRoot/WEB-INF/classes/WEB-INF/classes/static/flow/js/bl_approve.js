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
		layout : 'border',
		items : [
//		{
//			xtype : 'form',
//			id : 'updatePanel',
//			baseCls : 'x-plain',
//			labelWidth : 85,
//			items : [
//			{
//				xtype : 'textfield',
//				fieldLabel : '数据明细',
//				allowBlank : false,
//				id : "theme_name",
//				name : 'theme_name',
//				anchor : '100%'
//			}
//			]
//		}
		c_panel = new Ext.Panel({
//			title : '数据明细',
			region : 'center',
			items : [
//			         contentEl:'c_iframe',
			         
			]
		}),
		s_panel = new Ext.form.FormPanel({
			region : 'south',
			height : 120,
			labelWidth : 70,
			buttonAlign : 'center',
			labelAlign : 'left',
			bodyStyle : 'padding:5px',
			frame : false,
			border : true,
			items : [
			         {
			        	 xtype : 'textarea',
			        	 fieldLabel : '审批意见',
			        	 id : 'remark',
			        	 name : 'remark',
			        	 height : 80,
			        	 anchor : '100%',
			        	 value : r.get("remark")
			         }
			],
			buttons : [{
				text : '通过',
				hidden : !('01'==approve_role&&'02'==r.get("flow_status_code"))&&!('05'==approve_role&&'04'==r.get("flow_status_code")),
				handler : function() {
					remark = (Ext.getCmp('remark').getValue()==''||Ext.getCmp('remark').getValue()==null)?"通过":Ext.getCmp('remark').getValue();
					approveData('01');
				}
			}, {
				text : '退回',
				hidden : !('01'==approve_role&&'02'==r.get("flow_status_code"))&&!('05'==approve_role&&'04'==r.get("flow_status_code")),
				handler : function() {
					if(Ext.getCmp('remark').getValue()==''||Ext.getCmp('remark').getValue()==null){
//						Ext.MessageBox.alert('提示','请输入退回原因');
						Ext.getCmp('remark').focus();
						Ext.getCmp('remark').allowBlank=false;
						Ext.getCmp('remark').focus();
						return;
					}
					remark = Ext.getCmp('remark').getValue();
					approveData('02');
				}
			}, {
				text : '关闭',
				handler : function() {
					win.destroy();
				}
			}]
		})
		
		]
		
	});
	win.show();
	
	function approveData(approve_status){
		if(r.get("apply_user_id")==login_user_id){
			Ext.MessageBox.alert('提示','不能审批自己提交的数据！！！');
			return;
		}
//		var returnInfo  = prompt('请输入退回原因','05'==flow_status_code?'通过':'退回');
		Ext.Ajax.request({
	        url: pathUrl+'/flow/approveFlow',
	        params: {task_id:task_id,approve_status:approve_status,
//						remark:'05'==flow_status_code?'通过':'退回',
						remark:remark,
						status:'01',
						approve_role:approve_role},
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

