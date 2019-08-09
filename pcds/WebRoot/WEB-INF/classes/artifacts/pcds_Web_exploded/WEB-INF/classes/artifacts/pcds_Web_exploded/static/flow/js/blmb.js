/**
 * 展开数据字典节点
 * @param {} parentNode
 * @return {}
 */
function expandDictionNode(parentNode) {
	if(parentNode.firstChild == null) {
		return null;
	}
	
	if (parentNode.firstChild.text == 'loading') {
		Ext.Ajax.request({
			url : pathUrl + '/metadata/listSubMetadata',
			waitMsg : '正在处理，请稍候......',
			method : 'POST',
			params : {
				prt_metadata_id : parentNode.id
			},
			success : function(response, options) {
				var json = Ext.util.JSON.decode(response.responseText);
				var tl = json.results;
				if (tl) {
					for (var i = 0; i < tl.length; i++) {
						var leaf = isLeaf(tl[i].md_cate_cd);
						var cnode = new Ext.tree.AsyncTreeNode({
							id : tl[i].metadata_id,
							text : '[' + tl[i].category.md_cate_name + ']' + tl[i].metadata_desc,
							leaf : leaf,
							icon : pathUrl + '/static/meta/img/' + tl[i].md_cate_cd + '.png',
							md_cate_cd : tl[i].md_cate_cd,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});

						if (!leaf) {
							cnode.on('expand', expandDictionNode);
						}

						parentNode.appendChild(cnode);
					}
				} else {
					Ext.MessageBox.alert('提示信息', json.info);
				}

				if (parentNode.firstChild) {
					parentNode.firstChild.remove();
				}
			},
			failure : function(response, options) {
				Ext.MessageBox.alert('提示信息', response.responseText);
			}
		});
	}
}

/**
 * 过滤选中节点方法,根据md_cate_cd过滤只能选中表节点
 */
function filteClickFn(node) {
	return node.attributes.md_cate_cd == "TAB";
}

/**
 * 获取表的文本
 * @param {} node
 */
function getTextFn(node) {
	var text = node.text;
	if(text.indexOf("]") > 0) {
		return text.split("]")[1];
	}
	return text;
}

function isLeaf(type) {
	if("TAB"== type)
			return true;
	return false;
}


function doAddNode(tree){
	
	var node = tree.getSelectionModel().getSelectedNode();
	var parentNode = node.parentNode;
	var addWindow = new Ext.Window({
		title : '维护',
		width : 368,
		height : 420,
		modal : true,
		bodyStyle : 'padding:10px;',
		buttonAlign : 'center',
		items : [{
			xtype : 'form',
			id : 'updatePanel',
			labelWidth : 70,
			url :pathUrl+ '/flow/addBlmbNode',
			items : [
			    	{
						xtype : 'hidden',
						name : 'prt_tmpl_id',
						value : node.id
					},
					{
						xtype : 'textfield',
						fieldLabel : '模板名称',
						allowBlank : false,
						name : 'template_name',
						anchor : '100%'
					}, {
						xtype : 'textarea',
						fieldLabel : '模板描述',
						allowBlank : false,
						id : "template_desc",
						name : 'template_desc',
						anchor : '100%',
						height:70
					}, {
						xtype : 'combo',
						store : new Ext.data.SimpleStore({
							fields : ["v", "d"],
							data : [['01', '目录'], ['02', '补录表']]
						}),
						valueField : "v",
						displayField : "d",
						mode : 'local',
						editable : false,
						forceSelection : true,
						hiddenName : 'template_type_cd',
						triggerAction : 'all',
						allowBlank : false,
						fieldLabel : '模板类别',
						name : 'template_type_cd',
						anchor : '100%',
						listeners:{
							select:function(v){
								Ext.getCmp('is_table').setVisible('02'==v.value);
								Ext.getCmp('pageSelector').setDisabled('02'!=v.value);
								Ext.getCmp('version_no').setDisabled('02'!=v.value);
								Ext.getCmp('enterTypeSelector').setDisabled('02'!=v.value);
								//共享数据的判断
								Ext.getCmp('is_shared').setVisible('02'==v.value);
							}
						}
					},
					{
						layout:'form',
						id:'is_table',
						items:[
							{
								xtype : 'combo',
								store :new Ext.data.JsonStore({
							        url: pathUrl+ '/flow/listAllPage',
							        root: 'results',
							        autoLoad: true,
							        fields: ['page_struct_id','page_struct_name','page_struct_desc']
							    }),
								valueField : "page_struct_id",
								displayField : "page_struct_name",
								mode : 'local',
								editable : false,
								forceSelection : true,
								hiddenName : 'page_struct_id',
								triggerAction : 'all',
								allowBlank : false,
								fieldLabel : '选择页面',
								id:'pageSelector',
								name : 'page_struct_id',
								anchor : '100%'
							},{
								xtype : 'textfield',
								fieldLabel : '版本号',
								allowBlank : false,
								id:'version_no',
								name : 'version_no',
								anchor : '100%'
							},
							{
								xtype : 'combo',
								store : new Ext.data.SimpleStore({
									fields : ["v", "d"],
									data : [['01', '全部补录'], ['02', '部分补录']]
								}),
								valueField : "v",
								displayField : "d",
								mode : 'local',
								editable : false,
								forceSelection : true,
								hiddenName : 'enter_type_cd',
								triggerAction : 'all',
								allowBlank : false,
								fieldLabel : '补录类型',
								name : 'enter_type_cd',
								id:'enterTypeSelector',
								anchor : '100%'
							}
						]
					},
					{
						xtype : 'numberfield',
						fieldLabel : '显示顺序',
						allowBlank : false,
						id : "display_order",
						name : 'display_order',
						anchor : '100%'
					},{
						xtype : 'combo',
						store :new Ext.data.JsonStore({
					        url: pathUrl+ '/flow/listAllFlowTmpl',
					        root: 'results',
					        autoLoad: true,
					        fields: ['flow_tmpl_id','flow_tmpl_name'],
							listeners:{
								load:function(){
									Ext.getCmp('FlowSelector').setValue('');
								}
							}
					    }),
						valueField : "flow_tmpl_id",
						displayField : "flow_tmpl_name",
						mode : 'local',
						editable : false,
						forceSelection : true,
						hiddenName : 'flow_tmpl_id',
						triggerAction : 'all',
						allowBlank : false,
						fieldLabel : '选择流程',
						id:'FlowSelector',
						emptyText : '请选择一个流程',
						name : 'flow_tmpl_id',
						anchor : '100%'
					},{
						xtype : 'textfield',
						id : 'rela_metadata_names',
						name : 'rela_metadata_names',
						fieldLabel : '关联字段',
						anchor : '100%',
						readOnly : true,
						disabled :(node.id=='root' || node.attributes.attr1=='01'),
						allowBlank:(node.id=='root' || node.attributes.attr1=='01'),
						listeners : {
							'focus' : function (){
								showWin();
							}
						}
					},{
						xtype : 'checkbox',
						id : 'is_shared',
						name : 'is_shared',
						fieldLabel : '共享数据',
						anchor : '100%'
					}
			]
		}],
		buttons : [{
			text : '保存并发布',
			handler : function() {
				var formPanel = Ext.getCmp("updatePanel");
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍候......',
						params:{status_cd:'02'},
						failure : function(form, action) {
							Ext.MessageBox.alert('提示信息', action.result.info);
						},
						success : function(form, action) {
							var nodeID = action.result.node_id;
							var nodeName = action.result.node_name;
							var nodeDesc = action.result.node_desc;
							var template_type_cd = action.result.template_type_cd;
							var node = new Ext.tree.TreeNode({
								id : nodeID,
								//text : '[' + nodeID + ']' + nodeName,
								text :('01'==template_type_cd)?nodeName:(nodeName + '[' + ('01'==nodeDesc?'全部补录':'部分补录') + ']'),
								leaf : false
							});
							tree.getSelectionModel().getSelectedNode().appendChild(node);
							tree.getSelectionModel().getSelectedNode().expand();
							addWindow.destroy();
						}
					});
				} else {
					Ext.MessageBox.alert('提示信息', '请填写必输项');
				}
			}
		},{
			text : '保存暂不发布',
			handler : function() {
				var formPanel = Ext.getCmp("updatePanel");
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍候......',
						params:{status_cd:'01'},
						failure : function(form, action) {
							Ext.MessageBox.alert('提示信息', action.result.info);
						},
						success : function(form, action) {
							var nodeID = action.result.node_id;
							var nodeName = action.result.node_name;
							var nodeDesc = action.result.node_desc;
							var template_type_cd = action.result.template_type_cd;
							var node = new Ext.tree.TreeNode({
								id : nodeID,
								//text : '[' + nodeID + ']' + nodeName,
								text :('01'==template_type_cd) ? nodeName : ('*' + nodeName + '[' + ('01'==nodeDesc?'全部补录':'部分补录') + ']'),
								leaf : false
							});
							tree.getSelectionModel().getSelectedNode().appendChild(node);
							tree.getSelectionModel().getSelectedNode().expand();
							addWindow.destroy();
						}
					});
				} else {
					Ext.MessageBox.alert('提示信息', '请填写必输项');
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addWindow.destroy();
			}
		}]
	});
	addWindow.on("close", function() {
		addWindow.destroy();
	});
	addWindow.show();
}

function doEditNode(tree) {
	var node = tree.getSelectionModel().getSelectedNode();
	var parentNode = node.parentNode;
	var editWindow = new Ext.Window({
		title:'编辑',
		width : 368,
		height : 420,
		minWidth : 368,
		minHeight : 218,
		modal : true,
		layout : 'form',
		buttonAlign : 'center',
		items : [{
			xtype : 'form',
			id : 'updatePanel',
			labelWidth : 70,
			bodyStyle : 'padding:10px;',
			url : pathUrl+ '/flow/updateBlmb',
			reader : new Ext.data.JsonReader({
				root : 'results'
			}, [{name : 'tmpl_id'}, 
			    {name : 'template_name'}, 
			    {name : 'prt_tmpl_id'}, 
			    {name : 'template_desc'}, 
			    {name : 'status_cd'}, 
			    {name : 'template_type_cd'}, 
			    {name : 'enter_type_cd'}, 
			    {name : 'display_order'}, 
			    {name : 'page_struct_id'}, 
			    {name : 'flow_tmpl_id'},
			    {name : 'rela_metadata_names'},
			    {name : 'version_no'},
			]),
			items : [
			{
				xtype : 'textfield',
				fieldLabel : '模板ID',
				allowBlank : false,
				name : 'tmpl_id',
				anchor : '100%',
				hidden:true
			},{
				xtype : 'textfield',
				fieldLabel : '模板名称',
				allowBlank : false,
				name : 'template_name',
				anchor : '100%'
			}, {
				xtype : 'textarea',
				fieldLabel : '模板描述',
				allowBlank : false,
				id : "template_desc",
				name : 'template_desc',
				anchor : '100%',
				height:70
			}, {
				xtype : 'combo',
				store : new Ext.data.SimpleStore({
					fields : ["v", "d"],
					data : [['01', '目录'], ['02', '补录表']]
				}),
				valueField : "v",
				displayField : "d",
				mode : 'local',
				editable : false,
				forceSelection : true,
				hiddenName : 'template_type_cd',
				triggerAction : 'all',
				allowBlank : false,
				fieldLabel : '模板类别',
				name : 'template_type_cd',
				anchor : '100%',
				listeners:{
					select:function(v){
						Ext.getCmp('is_table').setVisible('02'==v.value);
						Ext.getCmp('pageSelector').setDisabled('02'!=v.value);
						Ext.getCmp('version_no').setDisabled('02'!=v.value);
						//共享数据的判断
						Ext.getCmp('is_shared').setVisible('02'==v.value);
					}
				}
			},
			{
				layout:'form',
				id:'is_table',
				items:[
					{
						xtype : 'combo',
						store :new Ext.data.JsonStore({
					        url: pathUrl+ '/flow/listAllPage',
					        root: 'results',
					        autoLoad: true,
					        fields: ['page_struct_id','page_struct_name','page_struct_desc']
					    }),
						valueField : "page_struct_id",
						displayField : "page_struct_name",
						mode : 'local',
						editable : false,
						forceSelection : true,
						hiddenName : 'page_struct_id',
						triggerAction : 'all',
						allowBlank : false,
						fieldLabel : '选择页面',
						id:'pageSelector',
						name : 'page_struct_id',
						anchor : '100%'
					},{
						xtype : 'textfield',
						fieldLabel : '版本号',
						allowBlank : false,
						id:'version_no',
						name : 'version_no',
						anchor : '100%'
					},{
						xtype : 'combo',
						store : new Ext.data.SimpleStore({
							fields : ["v", "d"],
							data : [['01', '全部补录'], ['02', '部分补录']]
						}),
						valueField : "v",
						displayField : "d",
						mode : 'local',
						editable : false,
						forceSelection : true,
						hiddenName : 'enter_type_cd',
						triggerAction : 'all',
						allowBlank : false,
						fieldLabel : '补录类型',
						name : 'enter_type_cd',
						id:'enterTypeSelector',
						anchor : '100%'
					}
				]
			},
			{
				xtype : 'numberfield',
				fieldLabel : '显示顺序',
				allowBlank : false,
				id : "display_order",
				name : 'display_order',
				anchor : '100%'
			},{
				xtype : 'combo',
				store :new Ext.data.JsonStore({
			        url: pathUrl+ '/flow/listAllFlowTmpl',
			        root: 'results',
			        autoLoad: true,
			        fields: ['flow_tmpl_id','flow_tmpl_name']
			    }),
				valueField : "flow_tmpl_id",
				displayField : "flow_tmpl_name",
				mode : 'local',
				editable : false,
				forceSelection : true,
				hiddenName : 'flow_tmpl_id',
				triggerAction : 'all',
				allowBlank : false,
				fieldLabel : '选择流程',
				id:'FlowSelector',
				emptyText : '请选择一个流程',
				name : 'flow_tmpl_id',
				anchor : '100%'
			},{
				xtype : 'textfield',
				id : 'rela_metadata_names',
				name : 'rela_metadata_names',
				fieldLabel : '关联字段',
				anchor : '100%',
				readOnly : true,
				disabled : (parentNode.id=='root' || parentNode.attributes.attr1=='01')?true:false,
				allowBlank: (parentNode.id=='root' || parentNode.attributes.attr1=='01')?true:false,
				listeners : {
					'focus' : function (){
						showWin();
					}
				}
			},{
				xtype : 'checkbox',
				id : 'is_shared',
				name : 'is_shared',
				fieldLabel : '共享数据',
				anchor : '100%'
			}
			]
		}],
		buttons : [{
			text : '保存',
			handler : function() {
				var formPanel = Ext.getCmp("updatePanel");
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍候......',
						failure : function(form, action) {
							Ext.MessageBox.alert('提示信息', action.result.info);
						},
						success : function(form, action) {
							var nodeID = action.result.node_id;
							var nodeName = action.result.node_name;
							var nodeDesc = action.result.node_desc;
							var template_type_cd = action.result.template_type_cd;
							//tree.getSelectionModel().getSelectedNode().setText('[' + nodeID + ']' + nodeName);
							
//							tree.getSelectionModel().getSelectedNode().setText(nodeName);
							var node  = tree.getSelectionModel().getSelectedNode();
							
							var nodeText = node.text;
							node.setText(('01'==template_type_cd)?nodeName:(nodeName + '[' + ('01'==nodeDesc?'全部补录':'部分补录') + ']'+nodeText.substring(nodeText.indexOf("]")+1)));
							editWindow.destroy();
							Ext.getCmp('flow_info').form.reset();
							Ext.getCmp('flow_info').load({
								url :pathUrl+ '/flow/getFlowInfo',
								params:{tmpl_id:node.id}
							});
							page_store.reload();
						}
					});
				} else {
					Ext.MessageBox.alert('提示信息', '请填写必输项');
				}
			}
		}, {
			text : '取消',
			handler : function() {
				editWindow.destroy();
			}
		}]
	});

	editWindow.on("close", function() {
		editWindow.destroy();
	});
	editWindow.show();
	
	var p = Ext.getCmp("updatePanel");
	p.form.load({
		url : pathUrl + '/flow/findBlmbById',
		params : {tmpl_id : node.id},
		success:function(form,action){
			var json = Ext.util.JSON.decode(action.response.responseText);
			var template_type_cd = json.results[0].template_type_cd;
			Ext.getCmp('is_table').setVisible('02'==template_type_cd);
			Ext.getCmp('pageSelector').setDisabled('02'!=template_type_cd);
			Ext.getCmp('version_no').setDisabled('02'!=template_type_cd);
			Ext.getCmp('enterTypeSelector').setDisabled('02'!=template_type_cd);
			e_in_tmpl_id = json.results[0].tmpl_id;
			//共享数据的判断
			Ext.getCmp('is_shared').setVisible('02'==template_type_cd);
			var is_shared = json.results[0].is_shared;
			if(is_shared == 'Y'){
				Ext.getCmp('is_shared').setValue(true);
			}
		}
	});
}

function doDeleteNode(tree) {
	Ext.Ajax.request({
		url : pathUrl + '/flow/delBlmb',
		params : {
			tmpl_id : tree.getSelectionModel().getSelectedNode().id
		},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				tree.getSelectionModel().getSelectedNode().remove();
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	});
}

/**
 * 调用远程shell脚本
 */
function execShl(run_date) {
	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在执行，请稍等..."});
	myMask.show();
	Ext.Ajax.request({
		url : pathUrl + '/flow/exec',
		params : {run_date:run_date},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			myMask.hide();
			if(json.info!=''){
				Ext.MessageBox.alert('调用结果', json.info);
			}else{
				Ext.MessageBox.alert('调用结果', "调用远程shell成功。");
				Ext.getCmp('execWindow').close();
			}
		}
	});
}

function setCurrent(page_struct_id,tmpl_id){
		Ext.Ajax.request({
			url : pathUrl + '/flow/setCurrent',
			params : {
				page_struct_id : page_struct_id,
				tmpl_id : tmpl_id
			},
			method : 'POST',
			callback : function(options,success,response){
				var json = Ext.util.JSON.decode(response.responseText);
				page_store.reload();
				Ext.Msg.alert('提示',json.info);
			}
		});
	
}

function showWin(){
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	var e_in_cm = new Ext.grid.ColumnModel([
	sm,
		{header: '字段名称',dataIndex: 'column_name'},
		{header: '字段描述', dataIndex: 'column_desc'}
		]);
	var e_in_store = new Ext.data.Store({
		id : 'e_in_store',
		proxy: new Ext.data.HttpProxy({
			url: pathUrl+'/flow/nodeGnList'
		}),
	
		reader: new Ext.data.JsonReader({
			root: 'results',
			totalProperty: 'totalCount'
		}, [
			{name:'column_name',mapping:'column_name',type:'string'},
			{name:'column_desc',mapping:'column_desc',type:'string'},
			]
		)
	});
	var page_struct_id = Ext.getCmp('pageSelector').getValue();
	e_in_store.load({params:{start:0,limit:50,page_struct_id:page_struct_id}});
			var showWin = new Ext.Window({
				width : 400,
				height : 465,
				minWidth : 368,
				minHeight : 218,
				id:'showWin',
				modal : true,
				bodyStyle : 'padding:10px;',
				buttonAlign : 'center',
				layout:'border',
				items:[
				       a_in_panel = new Ext.grid.GridPanel({
				    	   region:'center',
				    	   split:true,
					   	   ds: e_in_store,
					   	   cm: e_in_cm,	
					   	   sm: sm,
					   	   viewConfig: {forceFit:true}
				       })
				],
				buttons : [{
					text : '确定',
					handler : function() {
						var rs = a_in_panel.getSelectionModel().getSelections();
						if(!rs || rs.length==0){
					        Ext.Msg.alert('提示','请至少选择一条记录');
					        return;
					    }
					    var infos='';
						for(var i=0,l=rs.length;i<l;i++){
					        var r=rs[i];
					        infos+=(i==0?r.get('column_name'):","+r.get('column_name'));
					    }
						Ext.getCmp('rela_metadata_names').setValue(infos);
						Ext.getCmp('showWin').destroy();
					}
				}, {
					text : '取消',
					handler : function() {
					Ext.getCmp('showWin').destroy();
					}
				}]
			});
			showWin.show();
}
function showFlow(flow_id,flow_mame){
	var property={
			width:780,
			height:420,
			haveTool:false,
			haveHead:false,
			haveGroup:false,
			useOperStack:false
		};
	
	var win = new Ext.Window({
		title:flow_mame,
		width:800,
		height:500,
		modal :true,
		buttonAlign:'center',
		items:[
		   	editPanel = new Ext.form.FormPanel({
				id : 'editPanel',
				//title : '流程详情',
				layout : 'form',
				region:'center',
				border : true,
				split : true,
				frame : false,
				labelWidth : 60,
				labelAlign : 'left',
				bodyStyle : 'padding: 1px 1px 1px 1px',
				buttonAlign : 'center',
				autoScroll : true,
				items : [
				{
					html:'<div id="demo" style="margin:5px;float:left"></div>'
				}
			]})
		],
	buttons:[
        {
        	text:'关闭',
        	handler:function(){
        		win.destroy();
        	}
        }
	]
		
	});
	win.show();
	var demo=$.createGooFlow($("#demo"),property);
	demo.clearData();
	
	Ext.Ajax.request({
		url: pathUrl+'/workflow/getFlowById',
		method: 'POST',
		params: {flow_tmpl_id:flow_id},
		callback: function (options, success, response) {
			var json=Ext.util.JSON.decode(response.responseText);
			demo.loadData(Ext.util.JSON.decode(json.info));
		}
	});
	
}


function createTmplSql(node){
	var nodeId =node.id;
	var nodeName = node.text
	Ext.Ajax.request({
		url : pathUrl + '/pageManager/createTmplSql',
		params : {nodeId:nodeId,nodeName:nodeName},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				//生成导出文件
				document.expForm.submit();
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	});
}

function showExecWindow(){

	var execWindow = new Ext.Window({
		title : '后台卸数',
		width : 300,
		height : 110,
		modal : true,
		id:'execWindow',
		bodyStyle : 'padding:10px;',
		buttonAlign : 'center',
		layout:'fit',
		items : [
		       {
				xtype : 'form',
				id : 'execPanel',
				baseCls : 'x-plain',
				labelWidth : 50,
				items :[{
					xtype : 'datefield',
					fieldLabel : '日期',
					id : "run_date",
					name : 'run_date',
					format:'Ymd',
					anchor : '95%'
			     }]
					
		       }],
		buttons : [{
			text : '执行',
			handler : function() {
				var run_date = Ext.getCmp('run_date').getRawValue();
				if(run_date){
					execShl(run_date);
				}else{
					Ext.Msg.alert('提示信息','日期不可为空');
				}
				
			}
		}, {
			text : '取消',
			handler : function() {
				execWindow.destroy();
			}
		}]
	});
	execWindow.on("close", function() {
		execWindow.destroy();
	});
	execWindow.show();
}

