var selectLinkId = '', isTree = '', tree = '', rootValue = '';

var root=getRootNode('root','模板信息',expandTemplateTreeNode);

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

function expandTemplateTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/bckTrackAjax/getTemplateTree',
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
							id:tl[i].tmpl_id,
							text:tl[i].template_name,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandTemplateTreeNode);
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
Ext.onReady(function() {
	MyViewportUi = new Ext.Viewport({
			layout : 'border',
			items : [
			treeForm = new Ext.form.FormPanel({
				title:'模板信息',
				width : 200,
				region:'west',
				method : 'POST',
				contentEl:'west',
				autoScroll:true,
				collapsible : true,
				frame:true,
				items : [treePanel = new Ext.tree.TreePanel({
						collapsible: false,
						id:'templateTreePanel',
						frame:false,
						loader: new Ext.tree.TreeLoader(),
						lines:false,
						bodyStyle:'padding:5px',
						autoScroll:true,
						root:root
					})]
			})	,mainPanel=new Ext.Panel({
						region:'center',
						contentEl: 'center',
						resizeEl: 'center-iframe',
						border:false
					})
				]
		});
		treePanel.on('click', function(node,e) {
		if(node.id != 'root'){
			var form = treeForm.getForm().getEl().dom;
			form.action = pathUrl + "/bckTrackAjax/createMetaPage/"+node.id;
			form.target = "center-iframe";
			form.submit();
		}
	});
});