var baseUrl = pathUrl+'/queryTmpl';
//新增
function addDsNode(tree,node) {
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : baseUrl + '/addDs',
		split : true,
		frame : true,
		border:false,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [ {
			xtype : 'hidden',
			name : 'p_ds_id',
			value : node.id
		}, new ArrayCombo({
			id : 'dsCategory',
			hiddenName : 'ds_category',
			data : [['0', '目录'], ['1', '数据源'] ],
			fieldLabel : '分类',
			defaultValue : '0',
			anchor : '91%' 
		}), {
			xtype : 'textfield',
			id : 'ds_id',
			name : 'ds_id',
			fieldLabel : '数据源编号',
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype : 'textfield',
			id : 'ds_name',
			name : 'ds_name',
			fieldLabel : '数据源描述',
			allowBlank : false,
			anchor : '91%'
		}, new ArrayCombo({
			id : 'dsStatus',
			hiddenName : 'status',
			data : [['Y', '启用'], ['N', '禁用']],
			fieldLabel : '状态',
			defaultValue : 'Y',
			anchor : '91%'
		})]
	});

	var addDsWindow = new Ext.Window({
		layout : 'fit',
		title : '添加数据源',
		buttonAlign : 'center',
		modal : true,
		width : 350,
		height : 280,
		items : [formPanel],
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							var nodeID = Ext.getCmp('ds_id').getValue();
							var nodeName = Ext.getCmp('ds_name').getValue();
							var nodeType = Ext.getCmp('dsCategory').getValue();
							var nnode = new Ext.tree.TreeNode({
								id : nodeID,
								attr1:nodeType,
								text :('1'==nodeType)?nodeName:(nodeName + '[目录]'),
								iconCls :('1'==nodeType)?'table':'folder_table',
								leaf : false
							});
							tree.getSelectionModel().getSelectedNode().appendChild(nnode);
							tree.getSelectionModel().getSelectedNode().expand();
							
							addDsWindow.destroy();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示信息", action.result.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addDsWindow.destroy();
			}
		}]
	});

	addDsWindow.show();
}

/**
 * 编辑列
 */
function editDsNode(tree, node) {
	
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : baseUrl + '/updateDs',
		border : false,
		split : true,
		frame : true,
		labelWidth : 85,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'ds_id'}, 
			{name : 'p_ds_id'}, 
			{name : 'ds_name'}, 
			{name : 'status'},
			{name : 'ds_category'} 
		]),
		items : [ new ArrayCombo({
			id : 'dsCategory',
			hiddenName : 'ds_category',
			data : [['0', '目录'], ['1', '数据源'] ],
			fieldLabel : '分类',
			anchor : '91%' 
		}), {
			xtype : 'textfield',
			id:'ds_id',
			name : 'ds_id',
			fieldLabel : '数据源编号',
			readOnly:true,
			allowBlank : false,
			anchor : '91%'
		}, {
			xtype:'hidden',
			name:'p_ds_id'
		},{
			xtype : 'textfield',
			id:'ds_name',
			name : 'ds_name',
			fieldLabel : '数据源描述',
			allowBlank : false,
			anchor : '91%'
		}, new ArrayCombo({
			id : 'dsStatus',
			hiddenName : 'status',
			data : [['Y', '启用'], ['N', '禁用']],
			fieldLabel : '状态',
			anchor : '91%'
		})]
	});
	
	var editDsWindow = new Ext.Window({
		layout : 'fit',
		title : '编辑数据源',
		buttonAlign : 'center',
		modal : true,
		width : 350,
		height : 280,
		items : [formPanel],
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							var nodeName = Ext.getCmp('ds_name').getValue();
							var nodeType =  Ext.getCmp('dsCategory').getValue();
							
							node.setText(('1'==nodeType)?nodeName:(nodeName + '[目录]'));
							node.setIconCls(('1'==nodeType)?'table':'folder_table'),
							node.attributes.attr1=nodeType;

							editDsWindow.destroy();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示信息", action.result.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				editDsWindow.destroy();
			}
		}]
	});

	editDsWindow.show();
	
	//初始化
	formPanel.getForm().load({
		url : baseUrl + '/findDsById',
		params : {ds_id : node.id}});
}

function deleteDsNode(node,tree){
        Ext.MessageBox.confirm('Message', '确认要删除节点:[' + node.text + ']吗?删除节点将同时删除字段信息。', function(btn) {
        if (btn == 'yes') {
            Ext.Ajax.request({
                url : baseUrl+'/deleteDs', 
                method : 'POST',
                params : {
                    ds_id : node.id
                },
                success : function(response,options) {
                    var json = Ext.util.JSON.decode(response.responseText);
                    if (json.success) {
                    	//删除ds
                    	node.remove(true);
                    	//删除列表
                    	Ext.getCmp('dsMetaGridPanel').store.removeAll();
                    } else {
                        Ext.MessageBox.alert("错误信息",json.info);
                        Ext.MessageBox.hide();
                    }
                },failure: function(response,options){
                    Ext.Msg.alert('错误','保存失败:'+response.responseText);
                }
            });
        }
    });
}



function coVerUserTemplet(templetID,templetDesc){
    Ext.Msg.confirm('提示信息', '确认要覆盖当前模版['+templetDesc+']吗？', function(btn){
        if (btn == 'yes'){
           Ext.Ajax.request({
               url: url+'?method=saveUserTemplet&moduleCode=0018',
               params: {fonction_id:functionID,templetID:templetID,measureList:measureList,dim1List:bankList,dim2List:productList,showDim:showDim},
               success: function(response,options){
                    var json=Ext.util.JSON.decode(response.responseText);
                    if(json.success){
                        Ext.Msg.alert('保存成功','可以在模版下拉框中选择该模版！')
                        userTempletDS.reload();
                    }else {
                        Ext.Msg.alert('错误',json.info);
                    }
               },
               failure: function(response,options){
                    Ext.Msg.alert('错误','保存失败:'+response.responseText);
               }
            });
        }else{
            saveUserTemplet();
        }
    });
}

function saveUserTemplet(){
	
	Ext.Msg.prompt('确认要保存当前过滤条件吗？', '请输入模版名称:', function(btn, text){
	    if (btn == 'ok'){
	    	var totalCount = Ext.getCmp('tmSelector').store.getTotalCount()
	    	for (var j = 0; j < totalCount; j++) {
        		var localIndex = Ext.getCmp('tmSelector').store.getAt(j).get('templet_desc');
        		if(text == localIndex){
        			Ext.Msg.alert('错误','模版名称名称重复,请从新填写！');
        			return ;
        		}
        	}
	       Ext.Ajax.request({
			   url: url+'?method=saveUserTemplet&moduleCode=0018',
			   params: {fonction_id:functionID,templet_desc:text,measureList:measureList,dim1List:bankList,dim2List:productList,showDim:showDim},
			   success: function(response,options){
			   		var json=Ext.util.JSON.decode(response.responseText);
			   		if(json.success){
			   			Ext.Msg.alert('保存成功','可以在模版下拉框中选择该模版！')
			   			userTempletDS.reload();
			   		}else {
						Ext.Msg.alert('错误',json.info);
					}
			   },
			   failure: function(response,options){
			   		Ext.Msg.alert('错误','保存失败:'+response.responseText);
			   }
			});
	    }
	});
} 

//将booleancolumn转换为Y或N
function formatBoolCol(val){
	if(val && val === true){
		return 'Y';
	}else{
		return 'N';
	}
}

//连接类型下拉框
var linkTypeCombo = new ArrayCombo({
	id : 'linkTypeCd',
	hiddenName : 'link_type_cd',
	data :  [
 	        ['=', '='],
	        ['!=', '!='], 
	        ['>', '>'],
	        ['>=', '>='],
	        ['<', '<'],
	        ['<=', '<='],
	        ['like', 'like'],
	        ['not like', 'not like']
	],
	fieldLabel : '',
	width : 70 
})
//得到当前选中的数据源记录
function getCurrentDsRecord(){
	var dsRecord=Ext.getCmp('dsTreePanel').getSelectionModel().getSelectedNode();
	return dsRecord;
}