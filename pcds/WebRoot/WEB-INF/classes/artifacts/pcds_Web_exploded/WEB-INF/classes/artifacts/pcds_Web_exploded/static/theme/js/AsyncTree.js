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
		qtip: text,
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
 * 带复选框的树-机构
 * @param {Object} node
 * @return {TypeName} 
 */
function expandBankCkTreeNode(node)
{
	if(node.firstChild==null){
		return ;
	}
	
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getBankTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},
			success:function(response, options){
					var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							checked : false,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandBankCkTreeNode);
						node.appendChild(cnode);
					}
					//加载树节点完毕时候选中
					/*var checked=node.attributes.checked
					node.eachChild(function(child) {    
						child.ui.toggleCheck(checked);    
						child.attributes.checked = checked;    
						child.fireEvent('checkchange', child, checked);    
					});*/
					var checked=node.attributes.checked;
					node.eachChild(function(child) {    
						child.ui.toggleCheck(checked);    
						child.attributes.checked = checked;    
						child.fireEvent('checkchange', child, checked);    
					});
				}else{
					Ext.MessageBox.alert('错误',json.info);
				}
				node.firstChild.remove();
			},
			failure:function(response, options){
				Ext.MessageBox.alert('错误',response.responseText);
			}
		});
	}
}

/**
 * 带复选框的产品-机构
 * @param {Object} node
 * @return {TypeName} 
 * getProfProdTreeNode
 * getBasicMeasureTreeNode
 * 
 */
function expandMeasureCkTreeNode(node)
{
	if(node.firstChild==null){
		return ;
	}
	
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getProfProdTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {nodeID:node.id},
			success:function(response, options){
					var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							checked : false,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandMeasureCkTreeNode);
						node.appendChild(cnode);
					}
					//加载树节点完毕时候选中
					var checked=node.attributes.checked;
					node.eachChild(function(child) {    
						child.ui.toggleCheck(checked);    
						child.attributes.checked = checked;    
						child.fireEvent('checkchange', child, checked);    
					});
				}else{
					Ext.MessageBox.alert('错误',json.info);
				}
				node.firstChild.remove();
			},
			failure:function(response, options){
				Ext.MessageBox.alert('错误',response.responseText);
			}
		});
	}
}
/**
 * 条线树
 * @param {} node
 */
function expandBusiCkTreeNode(node)
{
    if(node.firstChild==null){
        return ;
    }
    
    if(node.firstChild.text=='loading'){
        Ext.Ajax.request({
            url: pathUrl+'/treeAjax.do?method=getBusiProductTreeNode',
            waitMsg:'正在处理，请稍候。。。。。。',
            method: 'POST',
            params: {parentNodeID:node.id},
            success:function(response, options){
                    var json=Ext.util.JSON.decode(response.responseText);
                var tl=json.results;
                if(tl)
                {
                    for(var i=0;i<tl.length;i++){
                        var cnode=new Ext.tree.AsyncTreeNode({
                            id:tl[i].id,
                            text:'['+tl[i].id+']'+tl[i].text,
                            leaf:tl[i].leaf,
                            checked : false,
                            children:[{
                                text:'loading',
                                cls: 'x-tree-node-loading',
                                leaf:true
                            }]
                        });
                        cnode.on('expand',expandBusiCkTreeNode);
                        node.appendChild(cnode);
                    }
                    var checked=node.attributes.checked;
					node.eachChild(function(child) {    
						child.ui.toggleCheck(checked);    
						child.attributes.checked = checked;    
						child.fireEvent('checkchange', child, checked);    
					});
                }else{
                    Ext.MessageBox.alert('错误',json.info);
                }
                node.firstChild.remove();
            },
            failure:function(response, options){
                Ext.MessageBox.alert('错误',response.responseText);
            }
        });
    }
}

function expandCustMgrTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../treeAjax.do?method=getCustMgrTreeNode',
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
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandCustMgrTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandSimpleBankTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../treeAjax.do?method=getBankTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandSimpleBankTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandBankWholeTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../treeAjax.do?method=getBankWholeTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandBankWholeTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandBankWholeTreeNodeForBudget(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../treeAjax.do?method=getBankWholeTreeNodeForBudget',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandBankWholeTreeNodeForBudget);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function appendEpmMeasureTreeNode(node)
{
	Ext.Ajax.request({
		url: '../../treeAjax.do?method=getEpmMeasureTreeNode',
		waitMsg:'正在处理，请稍候。。。。。。',
		method: 'POST',
		params: {parentNodeID:node.id,projectID:projectID,roleID:roleID},

		callback: function (options, success, response) {
			var json=Ext.util.JSON.decode(response.responseText);
			var tl=json.results;
			if(tl)
			{
				for(var i=0;i<tl.length;i++){
					var cnode=new Ext.tree.AsyncTreeNode({
						id:tl[i].id,
						text:'['+tl[i].id+']'+tl[i].text,
						leaf:tl[i].leaf,
						children:[{
							text:'loading',
							cls: 'x-tree-node-loading',
							leaf:true
						}]
					});
					cnode.on('expand',expandEpmMeasureTreeNode);
					node.appendChild(cnode);
				}
				node.expand();
			}
		},

		failure:function(response, options){
			Ext.MessageBox.hide();
			Ext.MessageBox.alert(response.responseText);
		},  

		success:function(response, options){
			Ext.MessageBox.hide();
		}
	});
}

function expandFeeMeasureTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../../treeAjax.do?method=getEpmMeasureTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id,projectID:'FEE',roleID:'31'},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandFeeMeasureTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandEpmMeasureTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../../treeAjax.do?method=getEpmMeasureTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id,projectID:projectID,roleID:roleID},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandEpmMeasureTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandBankTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getBankTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandBankTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandEvaMeasureTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../../treeAjax.do?method=getEvaMeasureTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandEvaMeasureTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}


function expandEvaParameterTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../../treeAjax.do?method=getEvaParameterTreeNode',
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
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:true
						});
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandEpmParameterTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../../treeAjax.do?method=getEpmParameterTreeNode',
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
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:true
						});
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandFeeParameterTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: '../../treeAjax.do?method=getFeeParameterTreeNode',
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
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:true
						});
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandBasicMeasureTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getBasicMeasureTreeNode',
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
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandBasicMeasureTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandBasicMeasureCheckedTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getBasicMeasureTreeNode',
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
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							checked:false,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandBasicMeasureCheckedTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandCostMeasureTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getCostMeasureTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandCostMeasureTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandProfitMeasureTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getProfitMeasureTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandProfitMeasureTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandProlossMeaTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getProlossMeasureTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.TreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf
						});
						cnode.appendChild(new Ext.tree.TreeNode({
							text:'loading',
							cls: 'x-tree-node-loading',
							leaf:true
						}));
						cnode.on('expand',expandProlossMeaTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandCostParameterTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getCostParameterTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.TreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandCostParameterTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

/*利润模型参数配置*/
function expandProfitParameterTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getProfitParameterTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.TreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandCostParameterTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

/*考核方案参数配置*/
function expandFundParameterTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getFundParameterTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.TreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandCostParameterTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandProductItemTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getProductItemTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {productID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.TreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandProductItemTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandProductTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getProductTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandProductTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

//成本分摊的产品分组
function expandProductAllocateTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getProductAllocateTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandProductAllocateTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}


function expandfundMeasureTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getFundMeasureTreeNode',
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
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandfundMeasureTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandBusiBankTreeNode(node)
{
	if(node.firstChild.text=='loading'){
		Ext.Ajax.request({
			url: pathUrl+'/treeAjax.do?method=getBusiBankTreeNode',
			waitMsg:'正在处理，请稍候。。。。。。',
			method: 'POST',
			params: {parentNodeID:node.id},

			callback: function (options, success, response) {
				var json=Ext.util.JSON.decode(response.responseText);
				var tl=json.results;
				if(tl)
				{
					for(var i=0;i<tl.length;i++){
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							text:'['+tl[i].id+']'+tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandBusiBankTreeNode);
						node.appendChild(cnode);
					}
				}
				node.firstChild.remove();
			},

			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			},  

			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
	}
}

function expandBankListTreeNode(node){
   //复选的时候展开,当前节点checked=false;
   /* if(node.getUI().checkbox){
        node.getUI().checkbox.checked = false;
    }*/
    Ext.Ajax.request({
        url: pathUrl+'/treeAjax.do?method=getBankWholeTreeNode',
        waitMsg:'正在处理，请稍候。。。。。。',
        method: 'POST',
        params: {parentNodeID:node.id},

        callback: function (options, success, response) {
            var json=Ext.util.JSON.decode(response.responseText);
            var tl=json.results;
            if(tl)
            {
                for(var i=0;i<tl.length;i++){
                    var cnode=new Ext.tree.AsyncTreeNode({
                        id:tl[i].id,
                        text:'['+tl[i].id+']'+tl[i].text,
                        leaf:tl[i].leaf,
                        checked:false,
                        children:[{
                            text:'loading',
                            cls: 'x-tree-node-loading',
                            leaf:true
                        }]
                    });
                    cnode.on('expand',expandBankReckonTreeNode);
                    cnode.on('checkchange', function(node, checked) {
                       //node.expand();
                    node.attributes.checked = checked;
                    node.eachChild(function(child) {
                            child.ui.toggleCheck(checked);
                            child.attributes.checked = checked;
                        });
                    }, cnode);
                    node.appendChild(cnode);
                }
            }
             if(node.firstChild!=null&&node.firstChild.text=='loading'){
                node.firstChild.remove();
            }
        },

        failure:function(response, options){
            Ext.MessageBox.hide();
            Ext.MessageBox.alert(response.responseText);
        },  

        success:function(response, options){
            Ext.MessageBox.hide();
        }
    });
}

function expandBankReckonTreeNode(node)
{
          //复选的时候展开,当前节点checked=false;
        /*if(node.getUI().checkbox){
            node.getUI().checkbox.checked = false;
        }*/
        if(node.firstChild!=null&&node.firstChild.text=='loading'){
	        Ext.Ajax.request({
	            url: pathUrl+'/treeAjax.do?method=getBankWholeTreeNode',
	            waitMsg:'正在处理，请稍候。。。。。。',
	            method: 'POST',
	            params: {parentNodeID:node.id},
	            callback: function (options, success, response) {
	                var json=Ext.util.JSON.decode(response.responseText);
	                var tl=json.results;
	                if(tl)
	                {
	                    for(var i=0;i<tl.length;i++){
	                        var cnode=new Ext.tree.AsyncTreeNode({
	                            id:tl[i].id,
	                            text:'['+tl[i].id+']'+tl[i].text,
	                            leaf:tl[i].leaf,
	                            checked:false,
	                            children:[{
	                                text:'loading',
	                                cls: 'x-tree-node-loading',
	                                leaf:true
	                            }]
	                        });
	                        cnode.on('expand',expandBankReckonTreeNode);
	                        cnode.on('checkchange', function(node, checked) {
	                           //node.expand();
	                        node.attributes.checked = checked;
	                        node.eachChild(function(child) {
	                                child.ui.toggleCheck(checked);
	                                child.attributes.checked = checked;
	                            });
	                        }, cnode);
	                        node.appendChild(cnode);
	                    }
	                }
			        node.firstChild.remove();
	            },
	
	            failure:function(response, options){
	                Ext.MessageBox.hide();
	                Ext.MessageBox.alert(response.responseText);
	            },  
	
	            success:function(response, options){
	                Ext.MessageBox.hide();
	            }
	        });
        }
}

//业务条线选择树
function expandBusiProdTreeNode(node) {
    if(node.firstChild!=null&&node.firstChild.text=='loading'){
        node.firstChild.remove();
    }
    Ext.Ajax.request({
        url: pathUrl+'/treeAjax.do?method=getProductTreeNode',
        waitMsg:'正在处理，请稍候。。。。。。',
        method: 'POST',
        params: {parentNodeID:node.id},

        callback: function (options, success, response) {
            var json=Ext.util.JSON.decode(response.responseText);
            var tl=json.results;
            if(tl)
            {
                for(var i=0;i<tl.length;i++){
                    var cnode=new Ext.tree.AsyncTreeNode({
                        id:tl[i].id,
                        text:'['+tl[i].id+']'+tl[i].text,
                        leaf:tl[i].leaf,
                        checked:false,
                        children:[{
                            text:'loading',
                            cls: 'x-tree-node-loading',
                            leaf:true
                        }]
                    });
                    cnode.on('expand',expandProductCKTreeNode);
                    cnode.on('checkchange', function(node, checked) {
				    //node.expand();
				    node.attributes.checked = checked;
				        node.eachChild(function(child) {
				            child.ui.toggleCheck(checked);
				            child.attributes.checked = checked;
				        });
				    }, cnode);
                    node.appendChild(cnode);
                }
            }
           //node.firstChild.remove();
        },

        failure:function(response, options){
            Ext.MessageBox.hide();
            Ext.MessageBox.alert(response.responseText);
        },  

        success:function(response, options){
            Ext.MessageBox.hide();
        }
    });
}

function expandProductCKTreeNode(node) {
    if(node.firstChild.text=='loading'){
        //复选的时候展开,当前节点checked=false;
    	var check = false;
    	
     	if(node.attributes.checked==true){
            check = true;
        }
        
        Ext.Ajax.request({
            url: pathUrl+'/treeAjax.do?method=getProductTreeNode',
            waitMsg:'正在处理，请稍候。。。。。。',
            method: 'POST',
            params: {parentNodeID:node.id},

            callback: function (options, success, response) {
                var json=Ext.util.JSON.decode(response.responseText);
                var tl=json.results;
                if(tl)
                {
                    for(var i=0;i<tl.length;i++){
                        var cnode=new Ext.tree.AsyncTreeNode({
                            id:tl[i].id,
                            text:'['+tl[i].id+']'+tl[i].text,
                            leaf:tl[i].leaf,
                            checked:check,
                            children:[{
                                text:'loading',
                                cls: 'x-tree-node-loading',
                                leaf:true
                            }]
                        });
                        //cnode.on('expand',expandProductCKTreeNode);
                        node.appendChild(cnode);
                    }
                }
                node.firstChild.remove();
            },

            failure:function(response, options){
                Ext.MessageBox.hide();
                Ext.MessageBox.alert(response.responseText);
            },  

            success:function(response, options){
                Ext.MessageBox.hide();
            }
        });
    }
}

/********************gongzhiyang新增*************************************/
/**
 * 机构树展开方法
 */
function expandBankTree(node){
	if(node.firstChild.text == 'loading'){
		Ext.Ajax.request({
			url : pathUrl + '/selectorAjax.do?method=listBankOrganization',
			params : {
				bank_org_id : node.id,
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
							id : tl[i].bankOrgID,
							text : tl[i].bankOrgName,
							//text :"["+ tl[i].bankOrgID+"]"+tl[i].bankOrgName,
							leaf : tl[i].leaf == 'Y'? true : false,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						cnode.on('expand',expandBankTree);
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
*产品树展开
*/
function expandProductTree(node){
	if(node.firstChild.text == 'loading'){
		Ext.Ajax.request({
			url : pathUrl + '/selectorAjax.do?method=listProfitProduct',
			params : {
				prof_prod_id : node.id,
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
							id : tl[i].productId,
							text : tl[i].productName,
							leaf : tl[i].leaf == 'Y'? true : false,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						cnode.on('expand',expandProductTree);
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

function expandMetadataTree(node)
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
						var cnode=new Ext.tree.AsyncTreeNode({
							id:tl[i].id,
							//text:'['+tl[i].id+']'+tl[i].text+'('+tl[i].property+')',
							text:tl[i].text,
							leaf:tl[i].leaf,
							children:[{
								text:'loading',
								cls: 'x-tree-node-loading',
								leaf:true
							}]
						});
						cnode.on('expand',expandMetadataTree);
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