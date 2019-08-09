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

function expandMeasureTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/angleMeasure/queryMeasureTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {node_id:node.id},
			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text+'('+tl[i].property+')',
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandMeasureTreeNode);
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