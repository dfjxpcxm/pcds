/*
------------------------------------------------------------------------------
文件名称：theme_class.js
说    明：JavaScript脚本，提供添加、编辑、查看、删除方法。
版    本：1.0
修改纪录:
------------------------------------------------------------------------------
时间             		 修改人         		说明
2014-11-25      wanggl         创建
------------------------------------------------------------------------------
*/

/*
用途 ：添加菜单
输入 ：tree:主题树; id:父节点id
返回 ： 
*/
function getRootNode(id,text,fn)
{
	var root = new Ext.tree.AsyncTreeNode({
		id:id,
		text:text,
		children:[{
			text:'loading',
			cls: 'x-tree-node-loading',
			leaf:true
		}]
	});

	if(fn!=undefined)
		root.on('expand',fn);

	return root;
}

function expandMetadataTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/themeClass/queryMetadataTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {nodeID:node.id},
			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var m_c_c = tl[i].metadata_cate_code;
						var nodeId = tl[i].id;
						var nodeName = tl[i].text;
						var nodeDesc = tl[i].desc;
						var cnode=new Ext.tree.AsyncTreeNode({
							id:nodeId,
//							text:'['+tl[i].id+']'+tl[i].text+'('+tl[i].property+')',
//							text:(m_c_c!=null||m_c_c.length>4||'2010'==m_c_c)?nodeName+'['+nodeDesc+']':nodeName,
//							text:!(m_c_c==''||m_c_c==null)&&(m_c_c.length>4||'2010'==m_c_c)?nodeName+'['+nodeDesc+']':nodeName,
							text:!(m_c_c==''||m_c_c==null)&&(m_c_c.length>4||'2010'==m_c_c)?nodeName+((nodeDesc==''||nodeDesc==null)?'':'['+nodeDesc+']'):nodeName,
							leaf:tl[i].leaf,
							iconCls:getIconCls(m_c_c),
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandMetadataTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				var msg = '出错!'
				Ext.MessageBox.alert(response.responseText+' '+msg);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function doRefreshTree(metadataTreePanel){
//	metadataTreePanel.load(pathUrl+'/themeClass/queryMetadataTreeNode');
	expandMetadataTreeNode('root');
}

function addNode(tree){
	var pid = tree.getSelectionModel().getSelectedNode().id;
	var addWindow = new Ext.Window({
		title : '元数据维护',
		width : 368,
		height : 251,
		minWidth : 368,
		minHeight : 218,
		modal : true,
		bodyStyle : 'padding:10px;',
		buttonAlign : 'center',
		items : [{
			xtype : 'form',
			id : 'updatePanel',
			baseCls : 'x-plain',
			labelWidth : 85,
			url :pathUrl+ '/themeClass/addMetaData',
			items : [
			{
				xtype : 'textfield',
				fieldLabel : '元数据名称',
				allowBlank : false,
				id : "metadata_name",
				name : 'metadata_name',
				anchor : '100%'
			}, 
			
			{
				xtype : 'textfield',
				fieldLabel : '描述',
				id : 'metadata_desc',
				name : 'metadata_desc',
				anchor : '100%'
			},
			
			{
				xtype : 'hidden',
				name : 'parent_metadata_id',
				value : pid,
				anchor : '100%'
			}, {
				xtype : 'combo',
				store :new Ext.data.JsonStore({
			        url: pathUrl+ '/themeClass/listAllMatadataCat',
			        root: 'results',
			        autoLoad: true,
			        fields: ['metadata_cate_code','metadata_cate_name']
			    }),
				valueField : "metadata_cate_code",
				displayField : "metadata_cate_name",
				mode : 'local',
				editable : false,
				forceSelection : true,
				hiddenName : 'metadata_cate_code',
				triggerAction : 'all',
				allowBlank : false,
				fieldLabel : '元数据分类',
				name : 'metadata_cate_code',
				anchor : '100%'
			}, {
				xtype : 'combo',
				store : new Ext.data.SimpleStore({
					fields : ["retrunValue", "displayText"],
					data : [['01', '禁用'], ['02', '正常'],['03','删除']]
				}),
				valueField : "retrunValue",
				displayField : "displayText",
				mode : 'local',
				editable : false,
				forceSelection : true,
				hiddenName : 'status_code',
				triggerAction : 'all',
				allowBlank : false,
				fieldLabel : '状态代码',
				name : 'status_code',
				anchor : '100%',
				value:'02'
			}, {
				xtype : 'numberfield',
				fieldLabel : '显示顺序',
				allowBlank : false,
				id : "display_order",
				name : 'display_order',
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
							var nodeID = action.result.metadata_id;
							var nodeName = action.result.metadata_name;
							var nodeDesc = action.result.metadata_desc;
							var node = new Ext.tree.TreeNode({
								id : nodeID,
								//text : '[' + nodeID + ']' + nodeName,
								text : nodeName + '[' + nodeDesc + ']',
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

function editNode(tree){
	var id = tree.getSelectionModel().getSelectedNode().id;
	var editWindow = new Ext.Window({
		title : '元数据维护',
		width : 368,
		height : 240,
		minWidth : 368,
		minHeight : 218,
		modal : true,
		bodyStyle : 'padding:10px;',
		layout : 'fit',
		buttonAlign : 'center',
		items : [{
			xtype : 'form',
			id : 'updatePanel',
			baseCls : 'x-plain',
			labelWidth : 75,
			url : pathUrl+ '/themeClass/updateMetadata',
			reader : new Ext.data.JsonReader({
				root : 'results'
			}, [{
				name : 'metadata_id'
			}, {
				name : 'metadata_name'
			}, {
				name : 'parent_metadata_id'
			}, {
				name : 'metadata_cate_code'
			}, {
				name : 'status_code'
			}, {
				name : 'display_order'
			}, {
				name : 'metadata_desc'
			}]),
			items : [{
				xtype : 'textfield',
				fieldLabel : '元数据代码',
				readOnly : true,
				allowBlank : false,
				name : 'metadata_id',
				anchor : '100%',
				hidden:true
			}, {
				xtype : 'textfield',
				fieldLabel : '元数据名称',
				allowBlank : false,
				id : "metadata_name",
				name : 'metadata_name',
				anchor : '100%'
			}, {
				xtype : 'textfield',
				fieldLabel : '描述',
				id : "metadata_desc",
				name : 'metadata_desc',
				anchor : '100%'
			}, {
				xtype : 'combo',
				store :new Ext.data.JsonStore({
			        url: pathUrl+ '/themeClass/listAllMatadataCat',
			        root: 'results',
			        autoLoad: true,
			        fields: ['metadata_cate_code','metadata_cate_name']
			    }),
				valueField : "metadata_cate_code",
				displayField : "metadata_cate_name",
				mode : 'local',
				editable : false,
				forceSelection : true,
				hiddenName : 'metadata_cate_code',
				triggerAction : 'all',
				allowBlank : false,
				fieldLabel : '元数据分类',
				name : 'metadata_cate_code',
				anchor : '100%'
			}, {
				xtype : 'combo',
				store : new Ext.data.SimpleStore({
					fields : ["retrunValue", "displayText"],
					data : [['01', '禁用'], ['02', '正常'],['03','删除']]
				}),
				valueField : "retrunValue",
				displayField : "displayText",
				mode : 'local',
				editable : false,
				forceSelection : true,
				hiddenName : 'status_code',
				triggerAction : 'all',
				allowBlank : false,
				fieldLabel : '状态代码',
				name : 'status_code',
				anchor : '100%'
			}, {
				xtype : 'numberfield',
				fieldLabel : '显示顺序',
				allowBlank : false,
				id : "display_order",
				name : 'display_order',
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
							var nodeID = action.result.metadata_id;
							var nodeName = action.result.metadata_name;
							var nodeDesc = action.result.metadata_desc;
							//tree.getSelectionModel().getSelectedNode().setText('[' + nodeID + ']' + nodeName);
							tree.getSelectionModel().getSelectedNode().setText(nodeName + '[' + nodeDesc + ']');
							editWindow.destroy();
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
	var p = Ext.getCmp("updatePanel");
	p.form.load({
		url : pathUrl + '/themeClass/getMetadataById',
		params : {
			metadata_id : id
		}
	});
	editWindow.on("close", function() {
		editWindow.destroy();
	});
	editWindow.show();
}

function delNode(tree){
	Ext.Ajax.request({
		url : pathUrl + '/themeClass/delMetadata',
		params : {
			metadata_id : tree.getSelectionModel().getSelectedNode().id
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