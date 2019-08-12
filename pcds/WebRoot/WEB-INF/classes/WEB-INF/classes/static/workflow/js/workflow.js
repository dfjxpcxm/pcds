var roleStore = new Ext.data.JsonStore({
	url : pathUrl + '/workflow/getUserRole',
	root : 'results',
	fields : ['nodeid', 'nodename']
});
roleStore.load();

/*var postStore = new Ext.data.JsonStore({
	url : pathUrl + '/workflow/getPostInfo',
	root : 'results',
	fields : ['nodeid', 'nodename']
});
postStore.load();*/

/*var teamStore = new Ext.data.JsonStore({
	url : pathUrl + '/workflow/getTeamInfo',
	root : 'results',
	fields : ['nodeid', 'nodename']
});
teamStore.load();
*/
var empStore = new Ext.data.JsonStore({
	url : pathUrl + '/workflow/getUserInfo',
	root : 'results',
	fields : ['nodeid', 'nodename']
});
empStore.load();
	
function doAddWorkFlow(jsonCode) {
	var set1 = new Ext.form.FieldSet({
			title : '基本信息',
			layout : 'form',
			autoHeight : true,
			bodyStyle : 'padding:2px 2px 0',
			items : [{
				xtype : 'textfield',
				name : 'flow_tmpl_name',
				fieldLabel : '流程名称',
				allowBlank : false,
				anchor : '97%'
			},{
				xtype : 'textarea',
				id : 'flow_tmpl_desc',
				name : 'flow_tmpl_desc',
				fieldLabel : '流程描述',
				autoScroll : true,
				height : 50,
				anchor : '97%'
			},{
				xtype : 'hidden',
				id : 'json_code',
				name : 'json_code',
				value : jsonCode
			}]
	});
	var addFlowPanel = new Ext.form.FormPanel({
		layout : 'form',
		border : false,
		frame : true,
		labelWidth : 65,
		labelAlign : 'left',
		url : pathUrl + '/workflow/addWorkFlow',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : false,
		items : [set1]
	});
	var addFlowWin = new Ext.Window({
		id : 'addFlowWin',
		title : '新流程',
		modal : true,
		width : 400,
		height : 200,
		layout : 'fit',
		maximized :false,
		buttonAlign : 'center',
		items : [addFlowPanel],
		buttons : [{
			text : '确定',
			handler : function() {
				if (addFlowPanel.form.isValid()) {
					addFlowPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addFlowWin.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							flowStore.reload();
//							editPanel.setDisabled(true);
							Ext.getCmp('save').setDisabled(true);
						},
						failure : function(action){
							Ext.Msg.alert('错误',action.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addFlowWin.destroy();
				//editPanel.setDisabled(true);
			}
		}]
	});
	addFlowWin.show();
}

function doEditWorkFlow(flow_id,flow_name,flow_desc,json_code){
	var set1 = new Ext.form.FieldSet({
			title : '基本信息',
			layout : 'form',
			autoHeight : true,
			bodyStyle : 'padding:2px 2px 0',
			items : [{
				xtype : 'textfield',
				name : 'flow_tmpl_name',
				fieldLabel : '流程名称',
				allowBlank : false,
				value : flow_name,
				anchor : '97%'
			},{
				xtype : 'textarea',
				id : 'flow_tmpl_desc',
				name : 'flow_tmpl_desc',
				fieldLabel : '流程描述',
				autoScroll : true,
				height : 50,
				value : flow_desc,
				anchor : '97%'
			},{
				xtype : 'hidden',
				id : 'json_code',
				name : 'json_code',
				value : json_code
			},{
				xtype : 'hidden',
				id : 'flow_tmpl_id',
				name : 'flow_tmpl_id',
				value : flow_id
			}]
	});
	var editFlowPanel = new Ext.form.FormPanel({
		layout : 'form',
		border : false,
		frame : true,
		labelWidth : 65,
		labelAlign : 'left',
		url : pathUrl + '/workflow/eidtWorkFlow',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : false,
		items : [set1]
	});
	var editFlowWin = new Ext.Window({
		id : 'editFlowWin',
		title : '编辑流程',
		modal : true,
		width : 400,
		height : 200,
		layout : 'fit',
		maximized :false,
		buttonAlign : 'center',
		items : [editFlowPanel],
		buttons : [{
			text : '确定',
			handler : function() {
				if (editFlowPanel.form.isValid()) {
					editFlowPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							editFlowWin.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							flowStore.reload();
//							editPanel.setDisabled(true);
							Ext.getCmp('save').setDisabled(true);
							doStatus = '';
						},
						failure : function(form,action){
							Ext.Msg.alert('错误',action.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				editFlowWin.destroy();
			}
		}]
	});
	editFlowWin.show();
}


/**
 * 删除流程
 * 
 */
function doDelWorkFlow(key) {
	Ext.MessageBox.confirm('确认','继续?', 
		function(btn) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : pathUrl + '/workflow/delWorkFlow',
					params : {
						key : key
					},
					method : 'POST',
					callback : function(options, success, response) {
						var json = Ext.util.JSON.decode(response.responseText);
						if (json.success) {
							Ext.MessageBox.alert('提示', '删除成功');
							flowStore.reload();
						} else {
							Ext.MessageBox.alert('错误', json.info);
						}
//						editPanel.setDisabled(true);
						Ext.getCmp('save').setDisabled(true);
						doStatus = '';
						demo.clearData();
					}
				});
			}
		});
}

/**
 * 发布/取消发布流程
 * 
 */
function doPubWorkFlow(key,status) {
	Ext.MessageBox.confirm('确认','继续?', 
		function(btn) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : pathUrl + '/workflow/pubWorkFlow',
					params : {
						key : key,
						status_cd : status
					},
					method : 'POST',
					callback : function(options, success, response) {
						var json = Ext.util.JSON.decode(response.responseText);
						if (json.success) {
							Ext.MessageBox.alert('提示', '操作成功');
							flowStore.reload();
						} else {
							Ext.MessageBox.alert('错误', json.info);
						}
//						editPanel.setDisabled(true);
						Ext.getCmp('save').setDisabled(true);
						doStatus = '';
					}
				});
			}
		});
}

function flowTaskWin(id,node){
	var set1 = new Ext.form.FieldSet({
			title : '基本信息',
			layout : 'form',
			autoHeight : true,
			bodyStyle : 'padding:2px 2px 0',
			items : [{
				xtype : 'textfield',
				name : 'flow_node_id',
				fieldLabel : '节点ID',
				allowBlank : false,
				readOnly : true,
				value : id,
				anchor : '97%'
			},{
				id : 'flow_node_name',
				xtype : 'textfield',
				name : 'flow_node_name',
				allowBlank : false,
				fieldLabel : '节点名称',
				value : node.name,
				anchor : '97%'
			},{
				xtype : 'combo',
				fieldLabel : '节点类型',
				name:'flow_node_type',
				displayField : 'text',
				valueField : 'id',
				editable : false,
				hiddenName : 'flow_node_type',
				mode : 'local',
				readOnly : true,
				triggerAction : 'all',
				value : node.type,
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					//data : [['task', '人机交互'], ['node', '选部门策略'], ['chat', '选岗位策略'], ['state', '选团队策略'], ['plug', '选人员策略'], ['fork', '一分二'], ['join', '二合一']]
					data : [['task', '人机交互'], ['fork', '一分二'], ['join', '二合一']]
				}),
				anchor : '97%'
			},{
				xtype : 'combo',
				fieldLabel : '任务方案',
				name:'flow_node_mode',
				displayField : 'text',
				valueField : 'id',
				editable : false,
				id : 'flow_node_Vmodel',
				mode : 'local',
				allowBlank : false,
				triggerAction : 'all',
				value : node.Vmodel,
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					data : [['00', '选机构策略'], 
					        ['01', '选角色策略'],
//					        ['02', '选部门策略'], 
//					        ['03', '选岗位策略'], 
//					        ['04', '选团队策略'], 
					        ['05', '选人员策略']]
				}),
				anchor : '97%',
				listeners : {
					select : function(){
						var value = this.getValue();
						panelShowByModel(value,node.Vvalue,'select');
					}
				}
			},{
				id : 'p_org',
				xtype : 'panel',
				layout : 'form',
				items : [
					//new TreeCombo({id : 'bank_org_id',label : '任务定位',anchor : '97%',name : 'bank_org_id',rootId : '8888',rootName : '总行', dim_code : 'bankOrgId',value : node.Vvalue})
					new Ext.MyField({id :'bank_org_id',fieldLabel : '任务定位',anchor : '89%',name:'bank_org_id'})
				]
			},{
				id : 'p_role',
				hidden : true,
				disabled : true,
				layout : 'form',
				xtype : 'panel',
				items : [{
				xtype : 'combo',
				fieldLabel : '任务定位',
				name:'flow_node_role',
				displayField : 'nodename',
				valueField : 'nodeid',
				editable : false,
				id : 'flow_node_role',
				mode : 'local',
				allowBlank : false,
				triggerAction : 'all',
				store : roleStore,
				anchor : '97%'
			}]
				
			},/*{
				id : 'p_dept',
				hidden : true,
				disabled : true,
				layout : 'form',
				xtype : 'panel',
				items : [
					//new TreeCombo({id : 'bank_org_dept', label : '任务定位',anchor : '97%',name : 'bank_org_dept',rootId : 'B99',rootName : '总部门', dim_code : 'linkdept',value : node.Vvalue})
					new Ext.MyField({id :'bank_org_dept',fieldLabel : '任务定位',anchor : '89%',name:'bank_org_dept'})
				]
				
			},{
				id : 'p_post',
				hidden : true,
				disabled : true,
				layout : 'form',
				xtype : 'panel',
				items : [{
					xtype : 'combo',
					fieldLabel : '任务定位',
					name:'flow_node_post',
					displayField : 'nodename',
					valueField : 'nodeid',
					editable : false,
					id : 'flow_node_post',
					mode : 'local',
					allowBlank : false,
					triggerAction : 'all',
					store : postStore,
					anchor : '97%'
				}]
			},{
				id : 'p_team',
				hidden : true,
				disabled : true,
				layout : 'form',
				xtype : 'panel',
				items : [{
					xtype : 'combo',
					fieldLabel : '任务定位',
					name:'flow_node_team',
					displayField : 'nodename',
					valueField : 'nodeid',
					editable : false,
					id : 'flow_node_team',
					mode : 'local',
					allowBlank : false,
					triggerAction : 'all',
					store : teamStore,
					anchor : '97%'
				}]
			},*/{
				id : 'p_user',
				layout : 'form',
				hidden : true,
				disabled : true,
				xtype : 'panel',
				items : [
					new Ext.MyField({id :'flow_node_user',fieldLabel : '任务定位',anchor : '89%',name:'flow_node_user'})
					]
			},{
				id : 'flow_node_Vcheck',
				xtype : 'checkbox',
				name : 'is_apply',
				fieldLabel : '是否为审批节点',
				anchor : '97%',
				checked  : node.Vcheck,
				listeners:{check:function(c,checked){
					if(checked){
						Ext.Msg.confirm('提示信息','确认设置节点为[审批]节点吗？',function(v){
							if(v=='no'){
								c.setValue(false);   
							}
						});
					}
				}}
			}]
	});
	
	
	
	var flowTaskWin = new Ext.Window({
		id : 'flowParWin',
		title : '编辑属性',
		width : 400,
		height : 270,
		modal : true,
		layout : 'fit',
//		draggable : false,
		border : false,
		bodyStyle : 'padding: 5px 5px 5px 5px',
		buttonAlign : 'center',
		items : [set1],
		buttons : [{
			text : '确定',
			handler : function() {
				var name = Ext.getCmp('flow_node_name').getValue();
				var Vtype = node.type;
				var Vmodel = Ext.getCmp('flow_node_Vmodel').getValue();
				var Vvalue = '';
				if(Vmodel == '00'){
					Vvalue = Ext.getCmp('bank_org_id').getValue();
				}else if(Vmodel == '01'){
					Vvalue = Ext.getCmp('flow_node_role').getValue();
				}else if(Vmodel == '02'){
					Vvalue = Ext.getCmp('bank_org_dept').getValue();
				}else if(Vmodel == '03'){
					Vvalue = Ext.getCmp('flow_node_post').getValue();
				}else if(Vmodel == '04'){
					Vvalue = Ext.getCmp('flow_node_team').getValue();
				}else{
					Vvalue = Ext.getCmp('flow_node_user').getValue();
				}
				var Vcheck = Ext.getCmp('flow_node_Vcheck').getValue();
				
				demo.setParams(id,name,'node',Vmodel,Vvalue,Vcheck);
				flowTaskWin.destroy();
			}
		}, {
			text : '取消',
			handler : function() {
				flowTaskWin.destroy();
			}
		}],
		listeners : {
			show : function(){
				panelShowByModel(node.Vmodel,node.Vvalue,'show');
			}
			
		}
	});
//	reloadTreeCombo(node.Vmodel);
	flowTaskWin.show();
}


function flowForkWin(id,node){
	
	var set1 = new Ext.form.FieldSet({
			title : '基本信息',
			layout : 'form',
			autoHeight : true,
			bodyStyle : 'padding:2px 2px 0',
			items : [{
				xtype : 'textfield',
				name : 'flow_node_id',
				fieldLabel : '节点ID',
				allowBlank : false,
				readOnly : true,
				value : id,
				anchor : '97%'
			},{
				id : 'flow_node_name',
				xtype : 'textfield',
				name : 'flow_node_name',
				allowBlank : false,
				fieldLabel : '节点名称',
				value : node.name,
				anchor : '97%'
			},{
				id : 'flow_node_Vtype',
				xtype : 'combo',
				fieldLabel : '节点类型',
				name:'flow_node_type',
				displayField : 'text',
				valueField : 'id',
				editable : false,
				hiddenName : 'flow_node_type',
				mode : 'local',
				allowBlank : false,
				readOnly : true,
				triggerAction : 'all',
				value : node.type,
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					//data : [['task', '人机交互'], ['node', '选部门策略'], ['chat', '选岗位策略'], ['state', '选团队策略'], ['plug', '选人员策略'], ['fork', '一分二'], ['join', '二合一']]
					data : [['task', '人机交互'], ['fork', '一分二'], ['join', '二合一']]
				}),
				anchor : '97%'
			},{
				id : 'flow_node_Vmodel',
				xtype : 'combo',
				fieldLabel : '预期取值',
				name:'flow_node_value',
				displayField : 'text',
				valueField : 'id',
				editable : false,
				mode : 'local',
				allowBlank : false,
				triggerAction : 'all',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					data : [['00', '是'], ['01', '否']]
				}),
				anchor : '97%',
				value : node.Vmodel
			},{
				id : 'flow_node_Vvalue',
				xtype : 'combo',
				fieldLabel : '预期路由节点',
				name:'flow_node_next',
				displayField : 'text',
				valueField : 'id',
				editable : false,
				mode : 'local',
				allowBlank : false,
				triggerAction : 'all',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'text'],
					data : demo.getNextNode(id)
				}),
				anchor : '97%',
				value : node.Vvalue
			}]
	});
	
	
	
	var flowParWin = new Ext.Window({
		id : 'flowParWin',
		title : '编辑属性',
		width : 400,
		height : 270,
		modal : true,
		layout : 'fit',
		border : false,
		draggable : false,
		bodyStyle : 'padding: 5px 5px 5px 5px',
		buttonAlign : 'center',
		items : [set1],
		buttons : [{
			text : '确定',
			handler : function() {
				var name = Ext.getCmp('flow_node_name').getValue();
				var Vmodel = Ext.getCmp('flow_node_Vmodel').getValue();
				var Vvalue = Ext.getCmp('flow_node_Vvalue').getValue();
				demo.setParams(id,name,'node',Vmodel,Vvalue,false);
				
				var attr = demo.getNextLine(id);
				
				//循环连线 
				for(var i=0;i<attr.length;i++){
					var n = attr[i];
					
					//n[0] = id
					//n[1] = 下级nodeID
					//n[2] = name
					
						if(n[1] == Vvalue){
							demo.setName(n[0],'是','line');
						}else{
							demo.setName(n[0],'否','line');
						}
				}
				flowParWin.destroy();
			}
		}, {
			text : '取消',
			handler : function() {
				flowParWin.destroy();
			}
		}]
	});
	flowParWin.show();
}


function workFlowStatus(v){
	switch(v)
    {
    case 'Y':
    	return "<span style='color:blue;'>已发布</span>";
    default:
    	return "<span style='color:red;'>未发布</span>";
   }
}

var TreeCombo=function(config){
	var id = config.id || 'treeCombo';
	var anchor = config.anchor || '90%';//占比
	var name = config.name ||'bank_org';//控件名称
	var is_expand = config.is_expand || false;//是否展开
	var fieldLabel =  config.label;     //标签名称
	var value = config.value;    //控件默认值
	//默认根节点
	var rootId = config.rootId;   //控件根节点
	var rootName = config.rootName ;    //控件根节点名称
	//是否隐藏显示
	var hidden = config.hidden || false;
	//隐藏ID
	var hiddenValue =config.rootId; 
	//隐藏维度条件
	var dim_code = config.dim_code;  //

	TreeCombo.superclass.constructor.call(this,{
		id : id,
		autoSelect:true,
		name : name,
		valueField : 'nodeid',
		displayField : 'nodename',
		uxType:'treeCombo',
		mode: 'local',
		triggerAction : "all",
		editable: false,
		anchor:anchor,
		hiddenName : name,
		realName:config.realName,
		allowBlank:config.allowBlank,
		value : value,
		fieldLabel : fieldLabel,
		store : {
			xtype:'arraystore',
			fields : ['nodeid','nodename'],
		    data:[['','']]
		}
	});
	//添加隐藏属性，
	this.getHiddenValue = function(){
		return hiddenValue;
	}
	//添加隐藏属性，
	this.setHiddenValue = function(v){
		return hiddenValue = v;
	}
	this.getDimCode = function(){
		return dim_code;
	}
	//展开事件
	this.expand=function(){
		var combo = this;
		if(!is_expand){
	        this.menu = new Ext.menu.Menu({
	            items : [
	            treePanel_ = new Ext.tree.TreePanel({
						border: false,
						lines:false,
						width: 260,
	                	height: 240,
						bodyStyle:'padding:2px',
						autoScroll:true,
						rootVisible: true,
	                	root :getRootNode(rootId, rootName,dim_code, expandTree),
	                	listeners: {
	                    click: function(node){
	                    	//设置选中值
	                    	combo.getStore().removeAll();
	                    	combo.getStore().insert(0,new Ext.data.Record({nodeid:node.id,nodename:node.text}));
	                    	combo.setValue(node.id);
	                    	//设置隐藏值
	                    	hiddenValue = node.id;
							//关闭面板
							this.ownerCt.hide();
							//毁掉函数
							if(null != config.callback){
								config.callback(node.id);
							}
	                    },
	                    load : function(node){
	                    	is_expand = true;
	                    }
	                }
					})]
	        });
	        this.menu.show(this.el, "tl-bl?");
	        treePanel_.getRootNode().expand();
		}else{
			this.menu.show(this.el, "tl-bl?");
		}
    };
}
Ext.extend(TreeCombo,Ext.form.ComboBox);

function getRootNode(id, text,dim_code, fn) {
	var root = new Ext.tree.AsyncTreeNode({
		id : id,
		text : text,
		qtip: text,
		dim_code : dim_code,
		children : [{
			text : 'loading',
			cls : 'x-tree-node-loading',
			leaf : true
		}]
	});

	if (fn != undefined)
		root.on('expand', fn);
	return root;
}


function expandTree(node){
	if(node.firstChild.text == 'loading'){
		Ext.Ajax.request({
			url : pathUrl + '/workflow/getMsgInfo/'+node.attributes.dim_code,
			params : {
				nodeId : node.id,
				dimcode :node.attributes.dim_code,
				mode : 'DrillDown'
			},
			waitMsg : '正在处理,请稍候...',
			method : 'POST',
			callback : function(options,success,response){
				var json = Ext.util.JSON.decode(response.responseText);
				var tl = json.results;
				if(tl){
					for(var i=0;i<tl.length;i++){
						var cnode = new Ext.tree.AsyncTreeNode({
							id : tl[i].nodeid,
							text : '['+tl[i].nodeid+']'+tl[i].nodename,
							leaf : tl[i].leaf == 'Y'? true : false,
							dim_code : node.attributes.dim_code,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						cnode.on('expand',expandTree);
						node.appendChild(cnode);
					}
				}else{
					Ext.MessageBox.alert('错误',json.info);
				}
				node.firstChild.remove();
			},
			failure : function(response,options){
				Ext.MessageBox.alert('错误',response.responseText);
			},
			success : function(options,response){
			}
		});
	}
}

function panelShowByModel(Vmodel,Vvalue,action){
	if(Vmodel == '00'){
		Ext.getCmp('p_org').setDisabled(false);
		Ext.getCmp('p_org').show();
		if(action=='show'){
			Ext.getCmp('bank_org_id').setValue(Vvalue);
		}
		Ext.getCmp('p_role').setDisabled(true);
		Ext.getCmp('p_role').hide();
		Ext.getCmp('p_user').setDisabled(true);
		Ext.getCmp('p_user').hide();
		
		/*Ext.getCmp('p_dept').setDisabled(true);
		Ext.getCmp('p_dept').hide();
		Ext.getCmp('p_post').setDisabled(true);
		Ext.getCmp('p_post').hide();
		Ext.getCmp('p_team').setDisabled(true);
		Ext.getCmp('p_team').hide();*/
	}else if(Vmodel == '01'){
		Ext.getCmp('p_role').setDisabled(false);
		Ext.getCmp('p_role').show();
		if(action=='show'){
			Ext.getCmp('flow_node_role').setValue(Vvalue);
		}
		Ext.getCmp('p_org').setDisabled(true);
		Ext.getCmp('p_org').hide();
		Ext.getCmp('p_user').setDisabled(true);
		Ext.getCmp('p_user').hide();
		
		/*Ext.getCmp('p_dept').setDisabled(true);
		Ext.getCmp('p_dept').hide();
		Ext.getCmp('p_post').setDisabled(true);
		Ext.getCmp('p_post').hide();
		Ext.getCmp('p_team').setDisabled(true);
		Ext.getCmp('p_team').hide();*/
	}/*else if(Vmodel == '02'){
		Ext.getCmp('p_dept').setDisabled(false);
		Ext.getCmp('p_dept').show();
		if(action=='show'){
			Ext.getCmp('bank_org_dept').setValue(Vvalue);
		}
		Ext.getCmp('p_org').setDisabled(true);
		Ext.getCmp('p_org').hide();
		Ext.getCmp('p_role').setDisabled(true);
		Ext.getCmp('p_role').hide();
		Ext.getCmp('p_post').setDisabled(true);
		Ext.getCmp('p_post').hide();
		Ext.getCmp('p_team').setDisabled(true);
		Ext.getCmp('p_team').hide();
		Ext.getCmp('p_user').setDisabled(true);
		Ext.getCmp('p_user').hide();
	}else if(Vmodel == '03'){
		Ext.getCmp('p_post').setDisabled(false);
		Ext.getCmp('p_post').show();
		if(action=='show'){
			Ext.getCmp('flow_node_post').setValue(Vvalue);
		}
		Ext.getCmp('p_org').setDisabled(true);
		Ext.getCmp('p_org').hide();
		Ext.getCmp('p_role').setDisabled(true);
		Ext.getCmp('p_role').hide();
		Ext.getCmp('p_dept').setDisabled(true);
		Ext.getCmp('p_dept').hide();
		Ext.getCmp('p_team').setDisabled(true);
		Ext.getCmp('p_team').hide();
		Ext.getCmp('p_user').setDisabled(true);
		Ext.getCmp('p_user').hide();
	}else if(Vmodel == '04'){
		Ext.getCmp('p_team').setDisabled(false);
		Ext.getCmp('p_team').show();
		if(action=='show'){
			Ext.getCmp('flow_node_team').setValue(Vvalue);
		}
		Ext.getCmp('p_org').setDisabled(true);
		Ext.getCmp('p_org').hide();
		Ext.getCmp('p_role').setDisabled(true);
		Ext.getCmp('p_role').hide();
		Ext.getCmp('p_dept').setDisabled(true);
		Ext.getCmp('p_dept').hide();
		Ext.getCmp('p_post').setDisabled(true);
		Ext.getCmp('p_post').hide();
		Ext.getCmp('p_user').setDisabled(true);
		Ext.getCmp('p_user').hide();
	}*/else{
		Ext.getCmp('p_user').setDisabled(false);
		Ext.getCmp('p_user').show();
		if(action=='show'){
			Ext.getCmp('flow_node_user').setValue(Vvalue);
		}
		Ext.getCmp('p_org').setDisabled(true);
		Ext.getCmp('p_org').hide();
		Ext.getCmp('p_role').setDisabled(true);
		Ext.getCmp('p_role').hide();
		
	/*	Ext.getCmp('p_dept').setDisabled(true);
		Ext.getCmp('p_dept').hide();
		Ext.getCmp('p_post').setDisabled(true);
		Ext.getCmp('p_post').hide();
		Ext.getCmp('p_team').setDisabled(true);
		Ext.getCmp('p_team').hide();*/
	}
}


//修改时候初始化下拉树
function reloadTreeCombo(Vmodel){
	var treeCombos ;
	var dimCode = '';   //目前依赖维度代码
	if(Vmodel == '00'){
		treeCombos = Ext.getCmp('bank_org_id');
		dimCode = "bankOrgId";
	}else if(Vmodel == '01'){
		return;
	}else if(Vmodel == '02'){
		treeCombos = Ext.getCmp('bank_org_dept');
		dimCode='linkdept';
	}else if(Vmodel == '03'){
		return;
	}else if(Vmodel == '04'){
		return;
	}else if(Vmodel == '05'){
		return;
	}
	if(treeCombos){
		for ( var i  = 0; i  < treeCombos.length; i ++) {
		  setTreeComboValue(treeCombos[i]);
		}
	}
}
//加载某个下拉树
function setTreeComboValue(tComb){
  Ext.Ajax.request({
	   url : pathUrl + '/bckTrackAjax/getDimValue/'+dimCode+'/'+tComb.getValue(),
	   method: 'POST',
	   success: function(response, opts) {
	      var json=Ext.util.JSON.decode(response.responseText);
	      var arys =json.results;
	      if(arys && arys.length>0){
	    	  tComb.getStore().removeAll();
	    	  tComb.getStore().insert(0,new Ext.data.Record({nodeid:arys[0].nodeid,nodename:arys[0].nodename}));
	    	  tComb.setValue(arys[0].nodeid);
	      }
	   } 
	});
}


/**
 * 用户策略选择面板
 */
Ext.MyField=Ext.extend(Ext.form.TextField ,{
	xtype:"textfield",
	readOnly:true,
	id:Ext.id(),
	constructor: function(config) {
		config = config||{};
		this.rxcfg = {};
		this.rxcfg.id = config.id;
		this.rxcfg.name = config.name;
		this.rxcfg.realName = config.realName;
		this.rxcfg.showIcon = config.showIcon;
		this.rxcfg.anchor = config.anchor||'83%';
		this.rxcfg.readOnly = config.readOnly||true;
		this.rxcfg.hidden = config.hidden||false;
		this.rxcfg.fieldLabel = config.fieldLabel||'客户经理';
		this.rxcfg.allowBlank= config.allowBlank || true;
		Ext.MyField.superclass.constructor.call(this, config);
    },
	initComponent: function(){
		Ext.applyIf(this, {
			id : this.rxcfg.id,
			anchor:this.rxcfg.anchor,
			fieldLabel:this.rxcfg.fieldLabel,
			name:this.rxcfg.name,
			realName:this.rxcfg.realName,
			readOnly : this.rxcfg.readOnly,
			hidden : this.rxcfg.hidden,
			allowBlank : this.rxcfg.allowBlank,
			listeners:{
				afterrender : function(c){
					if(!this.rxcfg.showIcon){
						var imgPath = pathUrl+'/public/images/icons/change.png'
						var div = Ext.getDom(c.getId()).parentNode;
						var span = document.createElement("span");
						span.style.border = "1px solid #B5B8C8";
						span.style.padding = "1px 1px 1px 1px";
						span.style.margin = "0px 0px 0px 2px";
						span.style.verticalAlign = "MIDDLE";
						span.innerHTML = "<a href='javascript:Ext.getCmp(\""+c.getId()+"\").showMgr(\""+c.getId()+"\")'><img src=\""+imgPath+"\"></a>";
						div.appendChild(span);
					}
				} 
			}
		});
		Ext.MyField.superclass.initComponent.call(this);
	} ,
	showMgr :function(par){
		if(par == 'bank_org_id'){
			showBankOrgWin(this,'org');
		}else if(par == 'bank_org_dept'){
			showBankOrgWin(this,'dept');
		}else{
			showAccountAllotWin(this); 
		}
	}
})


function showBankOrgWin(obj,params){
	var text = '';
	var rootId = '';
	var dim_code = '';
	if(params == 'org'){
		text = '[8888]总行';
		rootId = '8888';
		dim_code = 'bankOrgId';
	}else{
		text = '[B99]总部门';
		rootId = 'B99';
		dim_code = 'linkdept';
	}
	
	var chooseValue = '';
	
var Tree = new Ext.tree.TreePanel({
	layout:'fit',
	id:'TreePanel',
	lines:true,
	autoScroll:true,
	bodyStyle : 'padding:5px 5px',
	root :getRootNode(rootId, text,dim_code, expandTree),
	rootVisible:true,
	listeners: {
        click: function(n) {
			chooseValue = n.text;
        }
    }
});
	
	var BankOrgWin = new Ext.Window({
		title : '权限选择框',
		width : 300,
		height : 400,
		modal : true,
		layout : 'fit',
		border : false,
		bodyStyle : 'padding: 5px 5px 5px 5px',
		buttonAlign : 'center',
		items : [Tree],
		buttons : [{
			text : '确定',
			handler : function() {
				obj.setValue(chooseValue);
				BankOrgWin.destroy();
			}
		},{
			text : '取消',
			handler : function(){
				BankOrgWin.destroy();
			}
		}]
	});
	BankOrgWin.show();
}