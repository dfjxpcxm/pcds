
var	MetadataTreePanel = function(param){
	MetadataTreePanel.superclass.constructor.call(this,{
				title:'元数据',
				region:'west',
				width:300,
				collapsible: false,
				id:'measureTreePanel',
				frame:false,
				loader: new Ext.tree.TreeLoader(),
				lines:true,
				bodyStyle:'padding:5px',
				autoScroll:true,
				root:getRootNode(param.rootId,param.rootName,expandMetadataTreeNode)
	});
};
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


//nodeType 分类
function expandMetadataTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url : pathUrl + '/metadata/getMetadataTree/'+node.id,
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {nodeId:node.id},
			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id : tl[i].id,
							text : tl[i].text,
							leaf : tl[i].leaf,
							iconCls : getIconCls(tl[i].md_cate_cd),
							attributes : {nodeType : tl[i].nodeType ,metadata_name:tl[i].name},
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
				var msg = '出错!';
				Ext.MessageBox.alert(response.responseText+' '+msg);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

Ext.extend(MetadataTreePanel,Ext.tree.TreePanel);
