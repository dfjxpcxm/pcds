/**
*
*元数据功能操作方法js
*
*/

/**
 * 获取树的根节点
 * @param {} nodeId 根节点id
 * @param {} nodeName 根节点名
 * @param {} expandFn 节点的展开事件方法
 */
function getRootNode(nodeId, nodeName, expandFn){
	var root = new Ext.tree.AsyncTreeNode({
		id : nodeId,
		text : nodeName,
		qtip : nodeName,
		md_cate_cd : 'ROT',
		children : [{
			text : 'loading',
			cls : 'x-tree-node-loading',
			leaf : true
		}]
	});
	
	if (expandFn != undefined)
		root.on('expand', expandFn);
	
	return root;
}

var leafTypes = [category_type_sheet,category_type_column,category_type_colfield,category_type_form_button,category_type_toolbar_button];

function isLeaf(type) {
	for (var i = 0; i < leafTypes.length; i++) {
		if(leafTypes[i] == type)
			return true;
	}
	return false;
}

/**
 * 展开元数据节点方法
 * @param {} parentNode 被展开式父节点
 */
function expandMetadataNode(parentNode) {
	
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
							text : '[' + tl[i].category.md_cate_name + ']' +' '+ tl[i].metadata_desc,
							//leaf : leaf,
							leaf : tl[i].md_cate_cd == category_type_sheet ? true : tl[i].is_leaf == 'Y',
							icon : pathUrl + '/static/meta/img/' + tl[i].md_cate_cd + '.png',
							md_cate_cd : tl[i].md_cate_cd,
							display_order : tl[i].display_order,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						
						if(!leaf) {
							cnode.on('expand', expandMetadataNode);
							cnode.on("contextmenu", showRightMenu);
						}
						
						cnode.on("click", dispacheToConfig);
						
						parentNode.appendChild(cnode);
					}
					Ext.getCmp("view").doLayout();
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
 * 更新树节点名称
 * @param {} nodeId 需要更新的节点id
 * @param {} newName 新的名称
 */
function updateTreeNodeName(nodeId, newName) {
	var node = Ext.getCmp("metadataTreePanel").getNodeById(nodeId);
	if(node) {
		var oldName = node.text;
		var categoryName = oldName.substring(0,oldName.indexOf(']') + 1);
		node.setText(categoryName + " " + newName);
		
		//查询引用节点
		queryReferMeta(node);
	}
}

/**
 * 删除树节点并选中
 * @param {} nodeId
 */
function deleteTreeNode(nodeId) {
	var node = Ext.getCmp("metadataTreePanel").getNodeById(nodeId);
	if (node) {
		var siblingNode = node.nextSibling;
		var parentNode = node.parentNode;
		parentNode.removeChild(node);
		if (siblingNode != null) {
			siblingNode.fireEvent("click", siblingNode);
		} else {
			parentNode.fireEvent("click", parentNode);
		}
	}
	
	node = Ext.getCmp("referenceTreePanel").getNodeById(nodeId + "_refer");
	if(node) {
		node.remove();
	}
}

var lastNodeId = '';
/**
 * 根据选择的节点类型右侧面板加载对应的页面
 * @param {} node
 */
function dispacheToConfig(node, clear) {
	
	if(node.id == lastNodeId) {
		return;
	}
	
	lastNodeId = node.id;
	
	var category_type = node.attributes.md_cate_cd;
	
	if(node.id == 'root' || node.parentNode.id == 'root') {
		document.getElementById('center').src = "";
		return;
	}
	
	var pageName = '';
	var param = '';
	
	if(clear != false)
		clearReferTree();
	if(category_type == category_type_database) {
		pageName = 'database';
	} else if(category_type == category_type_user) {
		pageName = 'user';
	} else if(category_type == category_type_theme) {
		pageName = 'theme';
	} else if(category_type == category_type_table) {
		pageName = 'table';
		queryReferMeta(node);
	} else if(category_type == category_type_column) {
		pageName = 'column';
		queryReferMeta(node);
	} else if(category_type == category_type_page) {
		//增加数据字典元数据ID 子节点字段列表ID  参数值的传递  
		param='&is_show_field_grid=true'+"&md_cate_cd="+node.attributes.md_cate_cd;
		pageName = 'page_struct';
	}else if(category_type == category_type_toolbar) {
		param='&is_show_button_grid=true&md_cate_cd='+node.attributes.md_cate_cd;
		pageName = 'page_struct';
	} else if(category_type == category_type_toolbar_button) {
		param='&showRelyMeta=1';
		pageName = 'page_button';//工具条按钮
	}else if(category_type == category_type_colfield_list) {
		//增加数据字典元数据ID 子节点字段列表ID  参数值的传递  
		param='&is_show_field_grid=true&field_list_id='+node.id+"&md_cate_cd="+node.attributes.md_cate_cd;
		pageName = 'page_struct';
	}else if(category_type == category_type_form) { //表单
		param='&is_show_button_grid=true&md_cate_cd='+node.attributes.md_cate_cd;
		pageName = 'page_struct';
	} else if(category_type == category_type_colfield) {//表单字段
		pageName = 'page_field';
	}else if(category_type == category_type_form_button) {//表单按钮
		param='&showRelyMeta=0'; //表单按钮不显示关联表单
		pageName = 'page_button';
	} else if(category_type == category_type_excel) {
		pageName = 'excel';
	} else if(category_type == category_type_sheet) {
		pageName = 'sheet';
	} else if(category_type == category_type_excelcol) {
		pageName = 'excelcolumn';
	}
	
	var nid = node.id;
	if(node.id.indexOf("_refer") != -1) {
		nid = nid.replace("_refer","");	
	}
	
	document.getElementById('center').src = pathUrl + "/metadata/page/metadata_"+pageName+"?metadata_id=" + nid + param;
}

function noclearDispacher(node){
	dispacheToConfig(node,false);
}

/**
 * 查询引用元数据
 * @param {} node
 */
var lastReferNode = null;
var hasReferNodeCate = "TAB,COL"; //是否查询关联节点的节点类型
function queryReferMeta(node) {
	if(hasReferNodeCate.indexOf(node.attributes.md_cate_cd)==-1){
		return ;
	}
	
	lastReferNode = node;
	while(referenceRoot.childNodes[0] != null) {
		referenceRoot.childNodes[0].remove();
	}
	var pNode = new Ext.tree.AsyncTreeNode({
		id : node.id+"_refer",
		text : node.text,
		leaf : false,
		icon : node.attributes.icon,
		md_cate_cd : node.attributes.md_cate_cd,
		children : []
	});
	referenceRoot.appendChild(pNode);
	referenceRoot.expand();
	
	Ext.Ajax.request({
		url : pathUrl + '/metadata/listRefer',
		params : {
			metadata_id : node.id
		},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				var tl = json.results;
				for (var i = 0; i < tl.length; i++) {
					var cnode = new Ext.tree.AsyncTreeNode({
						id : tl[i].metadata_id + "_refer",
						text : '[' + tl[i].category.md_cate_name + ']' + ' ' + tl[i].metadata_desc,
						leaf : true,
//						checked : false,
						icon : pathUrl + '/static/meta/img/' + tl[i].md_cate_cd + '.png',
						md_cate_cd : tl[i].md_cate_cd,
						expanded : true,
						children : []
					});
					cnode.on("click",noclearDispacher);
					pNode.appendChild(cnode);
				}
				Ext.getCmp("view").doLayout();
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	})
	pNode.expand();
}

/**
 * 刷新节点
 * 
 * @param {}
 *            nodeId
 */
function refreshNode() {
	var node = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
	
	var expanded = node.isExpanded();
	
	if(expanded) {
		node.toggle();
	}
	
	while(node.childNodes.length > 0) {
		node.removeChild(node.childNodes[0]);
	}
	
	node.appendChild({
		text : 'loading',
		cls : 'x-tree-node-loading',
		leaf : true
	});
	node.expand();
}

function adjustOrder() {
	var node = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
	var display_order = '';
	if(node.attributes.display_order) {
		display_order = node.attributes.display_order;
	}
	var editWindow = new Ext.Window({
		layout : 'fit',
		title : '调整元数据展示顺序',
		buttonAlign : 'center',
		modal : true,
		width : 360,
		height : 130,
		items : [{
			xtype : 'form',
			id : 'adjustForm',
			layout : 'form',
			url : pathUrl + '/metadata/adjustOrder',
			border : true,
			split : true,
			frame : true,
			labelWidth : 75,
			labelAlign : 'left',
			bodyStyle : 'padding: 5px 5px 5px 5px',
			autoScroll : true,
			buttonAlign : 'center',
			items : [{
				xtype : 'hidden',
				name : 'metadata_id',
				value : node.id
			}, {
				xtype : 'numberfield',
				fieldLabel : '显示顺序',
				id : 'adjust_display_order',
				name : 'display_order',
				value : display_order,
				anchor : '91%'
			}]
		}],
		listeners : {
			'close' : function() {
				editWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				var formPanel = Ext.getCmp("adjustForm");
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							node.attributes.display_order = Ext.getCmp("adjust_display_order").getValue();
							editWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
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
				editWindow.destroy();
			}
		}]
	});
	
	editWindow.show();
}

/**
 * 右键菜单
 */
var rightMenu = new Ext.menu.Menu({
	id : 'rightClickMenu',
	width:150,
	items : [{
		text : '刷新',
		iconCls : 'refresh',
		handler : function() {
			refreshNode();
		}
	},{
		text : '调整顺序',
		iconCls : 'edit',
		handler : function() {
			adjustOrder();
		}
	},{
		text : '添加数据库',
		iconCls : 'add',
		id : category_type_database + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addDataBase(parentNode, category_type_database);
		}
	},'-',{
		text : '添加用户',
		iconCls : 'add',
		id : category_type_user + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addUser(parentNode, category_type_user);
		}
	},'-',{
		text : '添加主题',
		iconCls : 'add',
		id : category_type_theme + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addTheme(parentNode, category_type_theme);
		}
	},'-',{
		text : '添加表',
		iconCls : 'add',
		id : category_type_table + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addTable(parentNode, category_type_table);
		}
	},'-',{
		text : '添加列',
		iconCls : 'add',
		id : category_type_column + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addColumn(parentNode, category_type_column);
		}
	},'-',{
		text : '添加页面',
		iconCls : 'add',
		id : category_type_page + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addPageStruct(parentNode, category_type_page,'页面');
		}
	},'-',{
		text : '添加工具条',
		iconCls : 'add',
		id : category_type_toolbar + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addSimplePageStrut(parentNode, category_type_toolbar,'工具条');
		}
	},'-',{
		text : '添加工具条按钮',
		iconCls : 'add',
		id : category_type_toolbar_button + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addToolbarButton(parentNode, category_type_toolbar_button);
		}
	},'-',{
		text : '添加字段列表',
		iconCls : 'add',
		id : category_type_colfield_list + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addSimplePageStrut(parentNode, category_type_colfield_list,'字段列表');
		}
	},'-',{
		text : '添加表单',
		iconCls : 'add',
		id : category_type_form + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addSimplePageStrut(parentNode, category_type_form,'字段列表');
		}
	},'-',{
		text : '添加表单字段',
		iconCls : 'add',
		id : category_type_colfield + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addPageField(parentNode, category_type_colfield);
		}
	},'-',{
		text : '添加表单按钮',
		iconCls : 'add',
		id : category_type_form_button + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addToolbarButton(parentNode, category_type_form_button);
		}
	},'-',{
		text : '添加Excel模板',
		iconCls : 'add',
		id : category_type_excel + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addExcel(parentNode, category_type_excel);
		}
	},'-',{
		text : '添加Sheet页',
		iconCls : 'add',
		id : category_type_sheet + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addSheet(parentNode, category_type_sheet);
		}
	},'-',{
		text : '添加Excel数据列',
		iconCls : 'add',
		id : category_type_excelcol + '_menu',
		handler : function() {
			var parentNode = Ext.getCmp("metadataTreePanel").getNodeById(lastNodeId);
			addSheetColumn(parentNode, category_type_excelcol);
		}
	}],
	
	hideAllItems : function(){
		var array = this.items.items;
		for (var i = 0; i < array.length; i++) {
			if(array[i].id.indexOf("_menu") != -1) {
				array[i].hide();
				if((i+1) < array.length)
					array[i+1].hide();
			}
		}
	}
});

/**
 * 展示右键菜单方法
 * @param {} node
 * @param {} event
 */
function showRightMenu(node, event) {
	node.fireEvent("click",node);
	event.preventDefault();
	setMenuVisiable(node);
	rightMenu.showAt(event.getXY());
}

/**
 * 根据节点设置右键菜单的隐藏属性
 * @param {} node
 */
function setMenuVisiable(node) {
	var category_type = node.attributes.md_cate_cd;
	var json = null;
	for (var i = 0; i < addControlJson.length; i++) {
		if(addControlJson[i].md_cate_cd == category_type) {
			json = addControlJson[i];
			break;
		}
	}
	
	rightMenu.hideAllItems();
	
	if(json == null) {
		return;
	}
	
	for (var i = 0; i < json.relaDefList.length; i++) {
		var menuItem = Ext.getCmp(json.relaDefList[i].md_cate_cd+"_menu");
		if(menuItem != null)
			menuItem.setVisible(true);
	}
}

function clearReferTree() {
	if (referenceRoot.childNodes[0] != null) {
		referenceRoot.childNodes[0].remove();
	}
}

/**
 * 删除元数据依赖关系
 * @param {} ids
 */
function deleteRela(metaNodeArray) {
	var ids = "";
	for (var i = 0; i < metaNodeArray.length; i++) {
		if(i > 0) {
			ids += ",";
		}
		ids += metaNodeArray[i].id.replace("_refer","")
	}
	Ext.Ajax.request({
		url : pathUrl + '/metadata/removeRela',
		params : {
			metadata_id : ids,
			db_obj_id : lastNodeId
		},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				var idarray = ids.split(",");
				for (var i = 0; i < metaNodeArray.length; i++) {
					metaNodeArray[i].remove();
				}
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	})
}
