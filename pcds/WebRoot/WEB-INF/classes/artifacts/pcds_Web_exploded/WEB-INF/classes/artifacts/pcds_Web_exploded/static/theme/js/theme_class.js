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
function doAddTheme(tree) {
	var pid = tree.getSelectionModel().getSelectedNode().id;
	var addWindow = new Ext.Window({
		title : '主题维护',
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
			url :pathUrl+ '/themeClass/addTheme',
			items : [
			{
				xtype : 'textfield',
				fieldLabel : '主题名称',
				allowBlank : false,
				id : "theme_name",
				name : 'theme_name',
				anchor : '100%'
			}, {
				xtype : 'hidden',
				name : 'parent_theme_id',
				value : pid,
				anchor : '100%'
			}, {
				xtype : 'textfield',
				fieldLabel : '主题描述',
				allowBlank : false,
				id : "theme_desc",
				name : 'theme_desc',
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
				xtype : 'combo',
				store : new Ext.data.SimpleStore({
					fields : ["v", "d"],
					data : [['01', '显示'], ['02', '不显示']]
				}),
				valueField : "v",
				displayField : "d",
				mode : 'local',
				editable : false,
				forceSelection : true,
				hiddenName : 'is_display',
				triggerAction : 'all',
				allowBlank : false,
				fieldLabel : '是否显示',
				name : 'is_display',
				anchor : '100%',
				value:'01'
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
							var nodeID = action.result.theme_id;
							var nodeName = action.result.theme_name;
							var node = new Ext.tree.TreeNode({
								id : nodeID,
								//text : '[' + nodeID + ']' + nodeName,
								text : nodeName,
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

function editNode(id) {
	if (id == 'theme')
		return;
	doEditTheme(tree, id);
}

/*
 * 用途 ：编辑菜单 
 * 输入 ：tree:菜单树; id:节点id 
 * 返回 ：
 */
function doEditTheme(tree) {
	var id = tree.getSelectionModel().getSelectedNode().id;
	var editWindow = new Ext.Window({
		title : '主题维护',
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
			url : pathUrl+ '/themeClass/updateTheme',
			reader : new Ext.data.JsonReader({
				root : 'results'
			}, [{
				name : 'theme_id'
			}, {
				name : 'theme_name'
			}, {
				name : 'parent_theme_id'
			}, {
				name : 'theme_desc'
			}, {
				name : 'status_code'
			}, {
				name : 'is_display'
			}, {
				name : 'display_order'
			}]),
			items : [{
				xtype : 'textfield',
				fieldLabel : '主题代码',
				readOnly : true,
				allowBlank : false,
				name : 'theme_id',
				anchor : '100%',
				hidden:true
			}, {
				xtype : 'textfield',
				fieldLabel : '主题名称',
				allowBlank : false,
				id : "theme_name",
				name : 'theme_name',
				anchor : '100%'
			}, {
				xtype : 'textfield',
				fieldLabel : '主题描述',
				allowBlank : false,
				id : "theme_desc",
				name : 'theme_desc',
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
				xtype : 'combo',
				store : new Ext.data.SimpleStore({
					fields : ["v", "d"],
					data : [['01', '显示'], ['02', '不显示']]
				}),
				valueField : "v",
				displayField : "d",
				mode : 'local',
				editable : false,
				forceSelection : true,
				hiddenName : 'is_display',
				triggerAction : 'all',
				allowBlank : false,
				fieldLabel : '是否显示',
				name : 'is_display',
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
							var nodeID = action.result.theme_id;
							var nodeName = action.result.theme_name;
							//tree.getSelectionModel().getSelectedNode().setText('[' + nodeID + ']' + nodeName);
							tree.getSelectionModel().getSelectedNode().setText(nodeName);
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
		url : pathUrl + '/themeClass/getThemeById',
		params : {
			theme_id : id
		}
	});
	editWindow.on("close", function() {
		editWindow.destroy();
	});
	editWindow.show();
}

/*
 * 用途 ： 删除菜单 
 * 输入 ： tree:菜单树; id:节点id 
 * 返回 ： null
 */
function doDeleteTheme(tree) {
	Ext.Ajax.request({
		url : pathUrl + '/themeClass/removeTheme',
		params : {
			theme_id : tree.getSelectionModel().getSelectedNode().id
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

/** 指标节点展开方法  **/
function expandThemeTreeNode(node){
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/themeClass/tree',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {nodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
//							text:'['+tl[i].id+']'+tl[i].text,
							text:tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandThemeTreeNode);
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
};

