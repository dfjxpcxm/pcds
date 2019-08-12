//下拉函数列表
ChkListPanel = Ext.extend(Ext.tree.TreePanel, {
	constructor: function(config) {
        config = Ext.apply({
    		region:'center',
    		rootVisible:false,
            lines:false,
            autoScroll:true,
    		border:false,
            root: new Ext.tree.TreeNode('ListPanel')
        }, config);
        ChkListPanel.superclass.constructor.call(this, config);
    },
	listeners : {
		dblclick : function(node) {
			if(node.attributes.content)
				RangeInsert(Ext.getCmp('chk_formula'),node.attributes.content); 
		}
	},
    initComponent: function(){  
    	ChkListPanel.superclass.initComponent.apply(this, arguments);
    	this.addCategoryNode('common','内置函数',expandChkFnTreeNode);
    	this.addCategoryNode('custom','自定义函数',expandChkFnTreeNode);
	},  
	addCategoryNode:function(id,text,fn){
		var node=new Ext.tree.TreeNode({
				id:id,
				text:text,
				cls:'category-node',
				leaf:false
				//expanded:true
			});
		if(fn!=undefined)
		{
			node.appendChild(new Ext.tree.TreeNode({
					text:'loading',
					cls: 'x-tree-node-loading',
					leaf:true
				}));
			node.on('expand',fn);
		}
		this.root.appendChild(node);
	},
	addLeafNode:function(categoryID,id,text){
		var p=this.root.findChild('id',categoryID);
		p.appendChild(
			new Ext.tree.TreeNode({
				id:id,
				text:text,
				leaf:true
			})
		);
	},
	refreshNode:function(){
		this.getNodeById('common').remove();
		this.getNodeById('custom').remove();
		this.addCategoryNode('common','内置函数',expandChkFnTreeNode);
    	this.addCategoryNode('custom','自定义函数',expandChkFnTreeNode);
    	this.getNodeById('common').expand();
    	this.getNodeById('custom').expand();
	}
});
Ext.reg('chkListPanel', ChkListPanel);