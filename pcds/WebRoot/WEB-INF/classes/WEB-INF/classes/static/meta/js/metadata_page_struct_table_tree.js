
/***
 * 显示数据库元数据树
 */
function showConfigPageTableTree(){
	var configPageTableWindow ;
	var root=getRootNode('10','数据字典',expandMetadataNode);
	var formItemAnchor = '91%';
	var initPanel = new Ext.tree.TreePanel({
		//title : '元数据树',
		collapsible : false,
		id : 'metadataTreePanel',
		frame : false,
		loader : new Ext.tree.TreeLoader(),
		lines : true,
		split : true,
		border : true,
		autoScroll : true,
		bodyStyle : 'padding:5px',
		width : 300,
		root : root
	});	
	
	configPageTableWindow = new Ext.Window({
		layout : 'fit',
		title : '配置关联表',
		buttonAlign : 'center',
		modal : true,
		width : 350,
		height : 400,
		items : [initPanel],
		listeners : {
			'close' : function(){
				configPageTableWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				var sm = Ext.getCmp('metadataTreePanel').getSelectionModel();
				var sn = sm.getSelectedNode();
				if(sn){
					var md_cate_cd = sn.attributes.md_cate_cd;
					if(md_cate_cd != 'TAB'){
						Ext.Msg.alert('提示信息','请选择关联表');
						return;
					}else{
						isReConfigRelaTable = true;
						Ext.getCmp('rela_table_name').setValue(sn.attributes.metadata_desc);
						Ext.getCmp('rela_table_id').setValue(sn.id);
						configPageTableWindow.destroy();
					}
					
				}else{
					Ext.Msg.alert('提示信息','请选择关联表');
					return;
				}
				 
			}
		}, {
			text : '取消',
			handler : function() {
				configPageTableWindow.destroy();
			}
		}]
	});
	
	configPageTableWindow.show();
}


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
		md_cate_cd : 'CATDB',
		expanded:true,
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
						var leaf = false;
						var cnode = new Ext.tree.AsyncTreeNode({
							id : tl[i].metadata_id,
							text : '[' + tl[i].category.md_cate_name + ']' + tl[i].metadata_desc,
							leaf : leaf,
							icon : pathUrl + '/static/meta/img/' + tl[i].md_cate_cd + '.png',
							md_cate_cd : tl[i].md_cate_cd,
							metadata_desc:tl[i].metadata_desc,
							expanded:tl[i].md_cate_cd=='TAB' ? false:true,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						
						if(!leaf) {
							cnode.on('expand', expandMetadataNode);
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
 

