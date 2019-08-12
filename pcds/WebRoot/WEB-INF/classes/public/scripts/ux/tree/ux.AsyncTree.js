function getAsyncNode(id,text,fn)
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

function getRootNode(id, text, fn) {
	var root = new Ext.tree.AsyncTreeNode({
		id : id,
		text : text,
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

function getCheckedNode(id,text,checked,fn)
{
	var root = new Ext.tree.AsyncTreeNode({
		id:id,
		text:text,
		checked:checked,
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

/**
 * 元数据树展开方法
 */
function expandMetadataTree(node){
	if(node.firstChild.text == 'loading'){
		Ext.Ajax.request({
			url : pathUrl + '/selector/getMetadataTree',
			params : {
				metadata_id : node.id,
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
							id : tl[i].id,
							text : tl[i].text,
							//text :"["+ tl[i].id+"]"+tl[i].text,
							leaf : tl[i].leaf == 'Y'? true : false,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						cnode.on('expand',expandMetadataTree);
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

/**
* 模板树展开方法
*/
function expandBlmbTreeNode(node){
	if(node.firstChild.text == 'loading'){
		Ext.Ajax.request({
			url : pathUrl + '/selector/getBlmbTree',
			params : {
				tmpl_id : node.id,
				mode : 'DrillDown'
			},
			waitMsg : '正在处理,请稍候...',
			method : 'POST',
			callback : function(options,success,response){
				var json = Ext.util.JSON.decode(response.responseText);
				var tl = json.results;
				if(tl){
					for(var i=0;i<tl.length;i++){
						var text = tl[i].text;
						if(tl[i].template_type_cd=='02'){
							text = tl[i].text + '[' +('01'==tl[i].desc?'全部补录':'部分补录') + ']'+'[' +('02'==tl[i].status_cd?'发布':'未发布') + ']';
						}
						var cnode = new Ext.tree.AsyncTreeNode({
							id : tl[i].id,
							template_type_cd:tl[i].template_type_cd,
							text : text,
							leaf : tl[i].is_leaf,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						cnode.on('expand',expandBlmbTreeNode);
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