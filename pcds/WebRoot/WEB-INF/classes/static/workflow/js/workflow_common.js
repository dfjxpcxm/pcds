//当为录入时，有撤回按钮， 当为审批是
var flowBar=new Ext.Toolbar({
	id : 'flowBar',
	items : ['&nbsp;&nbsp;<font style=color:blue;font-weight:bold>审批工具栏</font>','->',{
		id : 'doBack',
		text : '撤回',
		hidden:true,
		width : 80,
		tooltip : '撤销提交的审批数据',
		iconCls : 'back',
		handler : function(){
			doReback();
		}
	}, {
		text : '提交',
		width : 80,
		id : 'doSubmit',
		hidden:true,
		iconCls : 'submit',
		tooltip : '提交数据',
		handler : function(){
			showApproveWin();
		}
	}, {
		text : '审批',
		width : 80,
		id : 'doApprove',
		hidden:true,
		iconCls : 'spec_auth',
		tooltip : '执行审批',
		handler : function(){
			showApproveWinForApply();
		}
	}, {
		text : '跟踪',
		width : 80,
		iconCls : 'search',
		tooltip : '跟踪选择的审批数据当前进度',
		handler : function(){
			doTrack();
		}
	}/*,'-',{
		text : '流程详细',
		width : 100,
		iconCls : 'help',
		tooltip : '当前流程走向图',
		handler : function(){
			//showApproveWin();
			Ext.Msg.alert('关于','当前功能目的为展示流程进度');
		}
	}*/]
});

	
/**
 * 提交操作
 * @return {Boolean}
 */
function showApproveWin(){
	//判断用户是否勾选了需要提交数据 
	if(records==null||records.length <= 0){
		Ext.Msg.alert('提示','未选择数据');
		return false;
	}
   	 
   	var business_nos='';   //业务编号
    var task_ids = "";     //任务编号
    var current_nodes = "";
    var next_nodes = "";
    
    //获取选择提交的数据
    var start_status = '';
	for(var i=0;i<records.length;i++){
        var record=records[i];
        var business_no = record.get("business_no");//业务编号
        var task_status = record.get("task_status");//数据状态
        var task_id = record.get("task_id");//数据状态

        //不等于编辑、和退回
        if(task_status && task_status != '-1' && task_status != '00' && task_status != '03'){
        	Ext.Msg.alert('提示','选择第['+(i+1)+']条数据状态有误,请检查状态是否为[新建、编辑、撤回、退回].');
        	return false;
        }
        
        if(i == 0){
        	start_status = task_status;
        }else{
        	if(task_status != start_status){
        		Ext.Msg.alert('提示','不同状态的数据不能一起批量提交');
        		return false;
        	}
        }
        
        if(records.length-1 == i){
        	business_nos  += business_no;
        	task_ids += task_id;
        }else{
        	business_nos  += business_no+ '_';
        	task_ids += task_id+'_';
        }
    }
	
    //申请，审批差异化label
    var sufix = "审批" ;
    
	var approveWin = new Ext.Window({
		title : sufix + '信息',
		layout : 'form',
		width : 470,
		height : 250,
		modal : true,
		frame : true,
		border : false,
		closable : true,
		buttonAlign : 'center',
		labelWidth:80,
		bodyStyle : 'padding: 5px',
		items : [{
			id : 'approve_val',
			xtype: 'radiogroup',
			fieldLabel: '审批操作',
			hidden:true,
			disabled:true,
			items: [
			        {boxLabel: '继续提交', name: 'radio',value : '00', checked: true}
			        //,{boxLabel: '删除记录', name: 'radio',value : '01'}
			        ]
		},{
			id : 'approve_msg',
			xtype : 'textarea',
			fieldLabel : '备注(非必输)',
			width : 350,
			row : 5
		}],
		buttons : [{
			text : '确定',
			handler : function(){
				var approveValue = '';
				var approveMsg = Ext.getCmp('approve_msg').getValue();
				
				if(submitAgain){
					//判断是否为录入提交 
				   	approveValue = Ext.getCmp('approve_val').getValue().value;
					//当被退回时，必须输入审批意见
					/*if(approveValue == '01' && approveMsg == ''){
						Ext.Msg.alert('提示','请输入'+sufix+'意见.');
						return;
					}*/
				}
				
			    Ext.MessageBox.confirm('提示','确认提交选中的数据?',function(btn){
					if(btn == 'yes'){
						Ext.Ajax.request({
							url : pathUrl + '/workflow/doApprove',
							method : 'POST',
							params : {business_nos : business_nos,
								approveMsg:approveMsg,
								approveValue:approveValue,
								workflowId:workflowId,
								templateId:templateId,
								buttonType:'00'},
								failure: function(response, opts) {
									var json = Ext.util.JSON.decode(response.responseText);
									Ext.MessageBox.alert('错误',json.info);
								},
								success : function(response, opts){
									var json = Ext.util.JSON.decode(response.responseText);
									var errs = json.errors;
					            	if(errs){
					            		//弹出错误
					            		showErrors(errs);
					            	}else{
					            		if (json.success) { 
											//Ext.MessageBox.alert('提示',json.info);
					            			reloadMainGrid();
											approveWin.destroy();
										} else {
											Ext.MessageBox.alert('错误',json.info);
											return;
										}
					            	}
								}
						});
					}
				});
			}
		},{
			text : '取消',
			handler : function(){
				approveWin.destroy();
			}
		}]
	});
	
	var submitAgain = false;
	//task_ids == null表示新建数据，提交，否则 审批
    //判断当前提交的数据为退回数据还是录入数据 
    if(task_ids != ''){
    	var task_id = task_ids.split("_")[0];
		Ext.Ajax.request({
			url : pathUrl + '/workflow/checkDataUpd/'+task_id,
			method : 'POST',
			params : {},
			callback : function(options, success, response) {
				var rtn = parseInt(response.responseText);
				if (rtn > 0 ) {
					submitAgain = true;
					Ext.getCmp('approve_val').enable();
					Ext.getCmp('approve_val').show();
				}
			}
		});
    } 
    
	approveWin.show();
}
/**
 * 审批操作类
 * @return {Boolean}
 */
function showApproveWinForApply(){
	 //判断用户是否勾选了需要提交数据 
	 if(records==null||records.length <= 0){
    	Ext.Msg.alert('提示','未选择数据');
        return false;
   	 }
   	 
   	var business_nos='';   //业务编号
    var task_ids = "";     //任务编号
    var current_nodes = ""; //当前节点
    var net_nodes = "";//下级节点
    
    //获取选择提交的数据
    var start_status = '';
	for(var i=0;i<records.length;i++){
        var record=records[i];
        var business_no = record.get("business_no");//业务编号
        var task_status = record.get("task_status");//数据状态
        var task_id = record.get("task_id");//数据状态
//        var current_node = record.get("current_node_id");
//        var next_node = record.get("next_node_id");
         
        //只有待审状态才能审批
        if(task_status==null || task_status != '01'){ 
        	Ext.Msg.alert('提示','选择第['+(i+1)+']条数据状态有误,请检查状态是否为[待审].');
        	return false;
        }
        
        if(i == 0){
        	start_status = task_status;
        }else{
        	if(task_status != start_status){
        		Ext.Msg.alert('提示','不同状态的数据不能一起批量提交');
        		return false;
        	}
        }
        if(records.length-1 == i){
        	business_nos  += business_no;
        	//task_ids += task_id;
        }else{
        	business_nos  += business_no+ '_';
        	//task_ids += task_id+'_';
        }
    }
	
	//申请，审批差异化label
    var sufix = "审批" ;

	var approveWin = new Ext.Window({
		title : sufix + '信息',
		layout : 'form',
		width : 470,
		height : 250,
		modal : true,
		frame : true,
		border : false,
		closable : true,
		buttonAlign : 'center',
		bodyStyle : 'padding: 5px',
		labelWidth:80,
		items : [{
			id : 'approve_val',
			xtype: 'radiogroup',
			fieldLabel: '审批操作',
			items: [
			        {boxLabel: '同意', name: 'radio',value : '00' ,checked: true},
			        {boxLabel: '退回', name: 'radio',value : '01'}
			        ]
		},{
			id : 'approve_msg',
			xtype : 'textarea',
			fieldLabel : '备注',
			width : 350,
			row : 5
		}],
		buttons : [{
			text : '确定',
			handler : function(){
		   	 	var approveValue = Ext.getCmp('approve_val').getValue().value;
			    var approveMsg = Ext.getCmp('approve_msg').getValue();
				//当被退回时，必须输入审批意见
				if(approveValue == '01' && approveMsg == ''){
					Ext.Msg.alert('提示','请输入'+sufix+'意见');
					return;
				}
			    Ext.MessageBox.confirm('提示','确认提交选中的数据?',function(btn){
					if(btn == 'yes'){
						Ext.Ajax.request({
							url : pathUrl + '/workflow/doApprove',
							method : 'POST',
							params : { business_nos : business_nos,
								approveMsg:approveMsg,
								approveValue:approveValue,
								workflowId:workflowId,
								templateId:templateId,
								buttonType:'-1'},
							callback : function(options, success, response) {
								var json = Ext.util.JSON.decode(response.responseText);
								if (json.success) { 
									//Ext.MessageBox.alert('提示',json.info);
									reloadMainGrid();
									approveWin.destroy();
								} else {
									Ext.MessageBox.alert('错误',json.info);
									return;
								}
							}
						});
					}
				});
			}
		},{
			text : '取消',
			handler : function(){
				approveWin.destroy();
			}
		}]
	});
	
	approveWin.show();
}

/**
 * 录入主动撤销提交,增加:校验是否审批人 , 撤回的时候校验 ,排除超级管理员
 */
function doReback(){
	//判断用户是否勾选了需要撤销提交数据 
	if(records==null||records.length <= 0){
		Ext.Msg.alert('提示','未选择数据');
		return false;
	}
   	//判断数据是否可以撤销
    var task_ids = "";       //任务编号
    var business_nos="";
    
    //获取选择提交的数据
	for(var i=0;i<records.length;i++){
        var record=records[i];
        
        //校验是否有审批权限,排除超级管理员
    	if(hasApprove && hasApprove == 'Y' ){
    		var applyUser = record.get('apply_user_id');
    		if(applyUser && applyUser!=curtUserId && curtUserId!='00000'){
    			Ext.Msg.alert('提示信息','第['+(i+1)+']条记录提交人非当前用户，无法操作.');
    			return ;
    		}
    	}
    	
        var task_id = record.get("task_id");//任务ID
        var business_no = record.get("business_no");//任务ID
        var task_status = record.get("task_status");//数据状态
        if(task_status != '01'){
			Ext.Msg.alert('错误','当前数据不能撤回,请确认审批状态为[待审]!');
			return ;
        }
        if(records.length-1 == i){
        	task_ids += task_id;
        	business_nos += business_no;
        }else{
        	task_ids += task_id+'_';
        	business_nos += business_no+'_';
        }
    }
   	   Ext.MessageBox.confirm('提示','确认撤回选中的数据?',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : pathUrl + '/workflow/doCheckReback',
				method : 'POST',
				params : {task_ids:task_ids,workflowId:workflowId,templateId:templateId,business_nos:business_nos},
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (success) { 
						//执行撤销操作
						//Ext.MessageBox.alert('提示',json.info);
						reloadMainGrid();
					} else {
						Ext.MessageBox.alert('错误',json.info);
						return ;
					}
				}
			})
		}
	});
   	 
}

/**
 * 审批信息跟踪
 * @return {Boolean}
 */
function doTrack(){
	 if(records==null||records.length != 1){
    	Ext.Msg.alert('提示','请选择一条数据');
        	return false;
   	 }
   	 
   	 var task_id = records[0].get("task_id");//任务ID
   	 
   	 var flowModel = new Ext.grid.ColumnModel([ 
		{
			header : '操作人',
			dataIndex : 'user_name'
		},{
			header : '操作角色',
			hidden:true,
			dataIndex : 'approve_rolw'
		},{
			header : '操作',
			dataIndex : 'approve_info'
		}, {
			header : '操作时间',
			dataIndex : 'approve_date'
		}, {
			header : '操作说明',
			dataIndex : 'approve_desc'
		}]);
		
		
		flowStore = new Ext.data.JsonStore({
			url : pathUrl + '/workflow/doTrackApprove/'+task_id,
			root : 'results',
			fields : [ 'user_name','approve_rolw','approve_info','approve_date','approve_desc']
		});
		 var applyMsg = new Ext.grid.GridPanel({
	 		layout : 'fit',
			id : "applyMsg",
			region:'center',
			//title : '审批详情',
			ds : flowStore,
			cm : flowModel,
			loadMask : true,
			viewConfig : {
				forceFit : true
			}
		}); 
		
		
		var TrackWin = new Ext.Window({
		title : '审批信息',
		layout : 'fit',
		width : 500,
		height : 300,
		modal : true,
		frame : true,
		border : false,
		closable : true,
		bodyStyle : 'padding: 5px',
		buttonAlign : 'center',
		items : [applyMsg],
		buttons : [{
			text : '确定',
			handler : function(){
				TrackWin.destroy();
			}
		}]
	});
	TrackWin.show();
   	flowStore.load();
}

//-----------------------------------------------------------------------------------------------------------------