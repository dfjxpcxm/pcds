var mainPanel ;
var editPanel;
var demo;
var flowStore;
var doStatus = '';

var property={
	height:800,
	//toolBtns:["start","end","task","node","chat","state","plug","join","fork","complex mix"],
	toolBtns:["start","end","task","join","fork"],
	haveTool:true,
	haveHead:false,
	//headBtns:["new","open","save","undo","redo","reload"],//如果haveHead=true，则定义HEAD区的按钮
	headBtns:["undo","redo","reload"],
	haveGroup:false,
	useOperStack:false,
	offsetY:30
};
 
var remark={
	cursor:"选择指针",
	direct:"转换连线",
	start:"开始结点",
	"end":"结束结点",
	"task":"任务结点",
//	node:"自动结点",
//	chat:"决策结点",
//	state:"状态结点",
//	plug:"附加插件",
	fork:"分支结点",
	"join":"联合结点"
//	"complex mix":"复合结点",
//	group:"组织划分框编辑开关"
};

Ext.onReady(function(){
	
	var flowModel = new Ext.grid.ColumnModel([ 
        new Ext.grid.RowNumberer(),
		{
			header : '流程编号',
			hidden:true,
			dataIndex : 'flow_tmpl_id'
		},{
			header : '流程名称',
			dataIndex : 'flow_tmpl_name'
		}, {
			header : '流程描述',
			dataIndex : 'flow_tmpl_desc'
		}, {
			header : '创建人',
			hidden:true,
			dataIndex : 'create_user_name'
		}, {
			header : '创建时间',
			hidden:true,
			dataIndex : 'create_time'
		}, {
			header : '最终修改人',
			hidden:true,
			dataIndex : 'update_user_name'
		}, {
			header : '最终修改时间',
			hidden:true,
			dataIndex : 'update_time'
		}, {
			header : '流程状态',
			dataIndex : 'status_cd',
			renderer : workFlowStatus
		}]);
		
		
		flowStore = new Ext.data.JsonStore({
			url : pathUrl + '/workflow/getWorkFlowList.action',
			root : 'results',
			//autoLoad : true,
			totalProperty : 'totalCount',
			fields : [ 'flow_tmpl_id','flow_tmpl_name','flow_tmpl_desc','create_user_name','create_time','update_user_name','update_time','status_cd','json_code']
		});
		
		flowStore.load({params:{start : 0,limit : 30}});
				
		var flowBar = ['->',
				{
					text : '自定义流程',
					iconCls : 'add',
					handler : function() {
//						editPanel.setDisabled(false);
						Ext.getCmp('save').setDisabled(false);
						doStatus = 'add';
						demo.clearData();
						demo.setTitle("workflow");
					}
				},
				'-',
				{
					text : '锁定并编辑',
					iconCls : 'lock',
					handler : function() {
						if (mainPanel.getSelectionModel().getSelections().length != 1) {
							Ext.Msg.alert('错误', '请在【流程列表】选择一条记录！');
							return;
						}
						var flow_id = mainPanel.getSelectionModel().getSelections()[0].get("flow_tmpl_id");
						var flow_name = mainPanel.getSelectionModel().getSelections()[0].get("flow_tmpl_name");
						var status_cd = mainPanel.getSelectionModel().getSelections()[0].get("status_cd");
						if(doStatus == 'add'){
							Ext.Msg.alert('错误','您选择了【自定义流程】,无法编辑,请选择流程');
							return;
						}
						
						if(status_cd == 'Y'){
							Ext.Msg.alert('错误',flow_name+' 已发布,不能编辑,请先取消发布');
							return;
						}
//						editPanel.setDisabled(false);
						Ext.getCmp('save').setDisabled(false);
						doStatus = 'edit';
						
					}
				},
				'-',
				{
					text : '删除',
					iconCls : 'delete',
					handler : function() {
						if (mainPanel.getSelectionModel().getSelections().length <= 0) {
							Ext.MessageBox.alert('错误', '最少选择一条记录');
							return;
						}
						var key = "";
						var record = mainPanel.getSelectionModel().getSelections();
						for(var i= 0; i < record.length; i++){
							var workflow_id = record[i].get("flow_tmpl_id");
							var workflow_name = record[i].get("flow_tmpl_name");
							var status_cd = record[i].get("status_cd");
							if(status_cd == 'Y'){
								Ext.Msg.alert('错误',workflow_name+' 已发布,不能删除,请先取消发布');
								return;
							}
							key += workflow_id+"#";
						}
						doDelWorkFlow(key);
					}
				},
				'-',
				{
					text : '发布',
					iconCls : 'publish',
					menu:[{
						id : 'public',
						text : '发布',
						iconCls : 'submit',
						handler : function() {
						if (mainPanel.getSelectionModel().getSelections().length <= 0) {
							Ext.MessageBox.alert('错误', '最少选择一条记录');
							return;
						}
						var key = "";
						var record = mainPanel.getSelectionModel().getSelections();
						for(var i= 0; i < record.length; i++){
							var workflow_id = record[i].get("flow_tmpl_id");
							key += workflow_id+"#";
						}
						doPubWorkFlow(key,'Y');
					}
					},{
						id : 'rebpub',
						text : '取消发布',
						iconCls : 'revoke',
						handler : function() {
							if (mainPanel.getSelectionModel().getSelections().length <= 0) {
								Ext.MessageBox.alert('错误', '最少选择一条记录');
								return;
							}
							var key = "";
							var record = mainPanel.getSelectionModel().getSelections();
							for(var i= 0; i < record.length; i++){
								var workflow_id = record[i].get("flow_tmpl_id");
								key += workflow_id+"#";
							}
							doPubWorkFlow(key,'N');
						}
					}]
				}];

		
		 mainPanel = new Ext.grid.GridPanel({
			id : "mainpanel",
			region:'east',
			title : '流程列表',
			width : 300,
			ds : flowStore,
			cm : flowModel,
		    sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
			//tbar : flowBar,
			loadMask : true,
			viewConfig : {
				forceFit : true
			},
			bbar : new Ext.PagingToolbar({ 
     		  pageSize 		: 30,
    	 	  store	 		: flowStore,
     		  displayInfo 	: true,
     		  displayMsg	: '第{0}-{1}条记录,共{2}条记录',
     		  emptyMsg		: "没有记录"
 		  })
		});
		
		mainPanel.getSelectionModel().on("rowselect",function(sm,rowIndex,record){
//			editPanel.setDisabled(true);
			Ext.getCmp('save').setDisabled(true);
			doStatus = '';
			var flow_id =record.get('flow_tmpl_id');
			var flow_name =record.get('flow_tmpl_name');
			var json_code = record.get("json_code");
			
			demo.clearData();
			
			//若反回数据过长，启用异步请求方式
			
			if(json_code.length >= 3000){
				Ext.Ajax.request({
					url: pathUrl+'/workflow/getFlowById.action',
					method: 'POST',
					params: {flow_tmpl_id:flow_id},
					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						demo.loadData(Ext.util.JSON.decode(json.info));
					}
				});
			}else{
				demo.loadData(Ext.util.JSON.decode(json_code));
			}
			demo.setTitle(flow_name);
		});
		
		
		
		
	editPanel = new Ext.form.FormPanel({
		id : 'editPanel',
		//title : '流程详情',
		tbar:flowBar,
		layout : 'form',
		region:'center',
		border : false,
		split : true,
		frame : false,
	//	labelWidth : 60,
		labelAlign : 'left',
		bodyStyle : 'padding: 1px 1px 1px 1px',
		buttonAlign : 'center',
		autoScroll : true,
		items : [
		{
			html:'<div id="demo" style="margin:0px;float:left"></div>'
		}
	],
	buttons : [{
		text : '保存',
		id : 'save',
		iconCls:'save',
		handler : function(){
			var jsonCode = JSON.stringify(demo.exportData());
			if(jsonCode == 'false'){
				return;
			}
			if(doStatus == 'add'){
				doAddWorkFlow(jsonCode);
			}else if(doStatus == 'edit'){
				var flow_id = mainPanel.getSelectionModel().getSelections()[0].get("flow_tmpl_id");
				var flow_name = mainPanel.getSelectionModel().getSelections()[0].get("flow_tmpl_name");
				var flow_desc = mainPanel.getSelectionModel().getSelections()[0].get("flow_tmpl_desc");
				doEditWorkFlow(flow_id,flow_name,flow_desc,jsonCode);
			}
			
		}
	},{
		text : '取消',
		iconCls : 'revoke',
		handler : function(){
//			editPanel.setDisabled(true);
			Ext.getCmp('save').setDisabled(true);
			doStatus = '';
		}
	}]
	});
	var viewport = new Ext.Viewport({
		border : false,
		layout : 'fit',
		items : [{
			xtype : 'panel',
			layout : "border",
			border : false,
			items : [ mainPanel, editPanel ]
		} ]
	});
	
	//设置宽度
	property.width = (document.body.clientWidth-300);
	property.height = (document.body.clientHeight-70);
	
	demo=$.createGooFlow($("#demo"),property);
	demo.setNodeRemarks(remark);
	demo.onItemDel=function(id,type){
     	this.blurItem();
     	return true;
	};
//	editPanel.setDisabled(true);
	Ext.getCmp('save').setDisabled(true);
});