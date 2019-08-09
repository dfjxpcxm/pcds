var e_in_tmpl_id;
var page_cm = new Ext.grid.ColumnModel([
   new Ext.grid.RowNumberer(),
   {
       header: "页面结构id",
       dataIndex: 'page_struct_id',
       hidden:true
   },{
       header: "页面结构名称",
       dataIndex: 'page_struct_name'
   },{
       header: "页面结构描述",
       dataIndex: 'page_struct_desc'
   },{
       header: "版本号",
       dataIndex: 'version_no'
   },{
       header: "是否当前版本",
       dataIndex: 'is_curr_version',
       renderer:function(v,c,r){
    	   if(v=='Y'){
			var page_struct_id = r.get('page_struct_id');
			var page_struct_name = r.get('page_struct_name');
			var root=getRootNode(page_struct_id,page_struct_name,expandMetadataNode);
			 Ext.getCmp("metadataTreePanel").setRootNode(root);
			 Ext.getCmp("metadataTreePanel").expandAll();
			 return '当前版本';
    	   }else{
    		   return "<a href='#' onclick=\"setCurrent('"+r.get('page_struct_id')+"','"+r.get('tmpl_id')+"')\">设为当前版本</a>";
    	   }
       }
   }
    ]);

var page_store = new Ext.data.Store({
      proxy: new Ext.data.HttpProxy({
	   url: pathUrl+'/flow/getPageInfo'
   }),
   reader: new Ext.data.JsonReader({
       root: 'results',
       totalProperty: 'totalCount'
   }, [
       {name: 'tmpl_id',mapping:'tmpl_id',type:'string'},
       {name: 'page_struct_id',mapping:'page_struct_id',type:'string'},
       {name: 'page_struct_name',mapping:'page_struct_name',type:'string'},
       {name: 'database_id',mapping:'database_id',type:'string'},
       {name: 'page_struct_desc',mapping:'page_struct_desc',type:'string'},
       {name: 'version_no',mapping:'version_no',type:'string'},
       {name: 'is_curr_version',mapping:'is_curr_version',type:'string'}
      ]
   )
});

var e_c_cm = new Ext.grid.ColumnModel([
   new Ext.grid.RowNumberer(),
   {header: '字段名称',dataIndex: 'field_name'},
   {header: '控件类型', dataIndex: 'component_type_id'},
   {header: '控件标签', dataIndex: 'component_label'}
]);
var e_c_store = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
	   url: pathUrl+'/flow/getPageFdl'
    }),
    
    reader: new Ext.data.JsonReader({
        root: 'results',
        totalProperty: 'totalCount'
    }, [
		{name:'field_name',mapping:'field_name',type:'string'},
		{name:'component_type_id',mapping:'component_type_id',type:'string'},
		{name:'component_label',mapping:'component_label',type:'string'}
        ]
     )
});


var topMenu = [{
	text : '添加',
	iconCls : 'add',
	handler : function() {
		var node = blmbTree.getSelectionModel().getSelectedNode();
		if(node == '' || node == null){
			Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
			return;
		}
		doAddNode(blmbTree);
	}
}, '-', {
	text : '编辑',
	iconCls : 'edit',
	handler : function() {
		var node = blmbTree.getSelectionModel().getSelectedNode();
		if(node == '' || node == null){
			Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
			return;
		}
		var id = blmbTree.getSelectionModel().getSelectedNode().id;
		if (id == 'root')
			return;
		doEditNode(blmbTree);
	}
}, '-', {
	text : '删除',
	iconCls : 'delete',
	handler : function() {
		var node = blmbTree.getSelectionModel().getSelectedNode();
		if(!node){
			Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
			return;
		}
		if(node.hasChildNodes()){
			Ext.MessageBox.alert('提示信息', '该模板有子模板，请先删除子模板');
			return;
		}
		var id = blmbTree.getSelectionModel().getSelectedNode().id;
		if (id == 'root')
			return;
		
		Ext.MessageBox.confirm("确认信息", "是否删除该模板?", function(btn) {
			if (btn == 'yes')
				doDeleteNode(blmbTree);
		});
	}
}, '-',{
	text:'发布？',
	menu:[{
		text : '发布',
		iconCls : 'submit',
		handler : function() {
			var node = blmbTree.getSelectionModel().getSelectedNode();
			if(!node){
				Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
				return;
			}
			var id = node.id;
			var tmpl_type = node.attributes['attr1'];
			if (id == 'root' || tmpl_type=='01'){
				Ext.Msg.alert("提示信息","请选择补录模版。");
				return;
			}
			
			Ext.MessageBox.confirm("确认信息", "是否发布该模板?", function(btn) {
				if (btn == 'yes')
					publish(node,'02');
			});
		}
	},
	{
		text : '取消发布',
		iconCls : 'revoke',
		handler : function() {
			var node = blmbTree.getSelectionModel().getSelectedNode();
			if(!node){
				Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
				return;
			}
			var id = node.id;
			var tmpl_type = node.attributes['attr1'];
			if (id == 'root' || tmpl_type=='01'){
				Ext.Msg.alert("提示信息","请选择补录模版。");
				return;
			}
			
			Ext.MessageBox.confirm("确认信息", "是否取消发布该模板?", function(btn) {
				if (btn == 'yes')
					publish(node,'01');
			});
		}
	}
	]
},/*{
	text:'生成SQL',
	iconCls:'exportdata',
	handler:function(){
		var node = blmbTree.getSelectionModel().getSelectedNode();
		if(!node){
			Ext.MessageBox.alert('提示信息', '请选择一个节点,继续进行');
			return;
		}
		var id = blmbTree.getSelectionModel().getSelectedNode().id;
		if (id == 'root')
			return;
		var t_t_c = node.attributes.attr1;
		if(t_t_c!='02'){
			Ext.MessageBox.alert('提示信息', '请选择补录表');
			return;
		}
		createTmplSql(node);
	}
},*/'-',{
	text:'卸数',
	iconCls:'edit',
	handler:function(){ 
		showExecWindow();
	}
}];

function publish(node,status_cd){
	Ext.Ajax.request({
		url : pathUrl + '/flow/publicBlmb',
		params : {
			tmpl_id : node.id,
			status_cd : status_cd
		},
		method : 'POST',
		callback : function(options,success,response){
			var json =Ext.util.JSON.decode(response.responseText);
			node.setText(('02'==json.status_cd?'':'*') + json.template_name + '[' + ('01'==json.enter_type_cd?'全部补录':'部分补录') + ']');
			Ext.Msg.alert('提示',json.info);
		}
	});
}
	
var blmbTree = new Ext.tree.TreePanel({
	title:'补录模板',
	region:'west',
	width : 365,
	id:'blmbTreePanel',
	lines:true,
	autoScroll:true,
	bodyStyle : 'padding:5px 5px',
	loader: new Ext.tree.TreeLoader({dataUrl: pathUrl + '/flow/listAllTmpl/yes'}),
	root :new Ext.tree.AsyncTreeNode({
		text: '补录模板',
		iconCls:'folder_table',
		id:'root'
	}),
	split:true,
	rootVisible:true,
	tbar:topMenu,
	listeners: {
        click: function(n) {
        	var root=getRootNode('','',expandMetadataNode);
			 Ext.getCmp("metadataTreePanel").setRootNode(root);
			var tmpl_id = n.id;
			Ext.getCmp('flow_info').form.reset();
			Ext.getCmp('flow_info').load({
				url :pathUrl+ '/flow/getFlowInfo',
				params:{tmpl_id:tmpl_id}
			});
			page_store.load({params:{tmpl_id:tmpl_id}});
        }
    }
});

var page_panel={
	xtype:'grid',
	id:'page_info',
	title:'页面信息',
	region:'west',
	autoScroll : true,
	width:450,
	split:true,
	ds: page_store,
	cm: page_cm,	
	viewConfig: {forceFit:true},
	listeners:{
		rowdblclick:function(){
			var r = Ext.getCmp('page_info').getSelectionModel().getSelected();
			var page_struct_id = r.get('page_struct_id');
			var page_struct_name = r.get('page_struct_name');
			var root=getRootNode(page_struct_id,page_struct_name,expandMetadataNode);
			 Ext.getCmp("metadataTreePanel").setRootNode(root);
			 Ext.getCmp("metadataTreePanel").expandAll();
			 
//			e_c_store.load({params:{start:0,limit:50,page_struct_id:r.get('page_struct_id')}});
		}
	}
};


var flow_panel={
		xtype:'form',
		id:'flow_info',
		title:'流程信息',
		region:'center',
		autoScroll : false,
		split:true,
		labelWidth:80,
		buttonAlign:'center',
		bodyStyle : 'padding:10 10 20 20px',
		reader: new Ext.data.JsonReader({
            root: 'results',
            totalProperty: 'totalCount'
        }, [
            {name: 'flow_tmpl_name' },
            {name: 'flow_tmpl_id' },
            {name: 'flow_tmpl_desc'},
            {name: 'status_cd' }
        ]),
		items:[{
			xtype:'hidden',
			name:'flow_tmpl_id',
			id:'flow_tmpl_id'
		}
		,{
			xtype:'textfield',
			fieldLabel:'流程名称',
			id:'flow_tmpl_name',
			name:'flow_tmpl_name',
			readOnly:true,
			anchor:'95%'
		},
		{
			xtype:'textarea',
			fieldLabel:'流程描述',
			name:'flow_tmpl_desc',
			readOnly:true,
			anchor:'95%'
		},{
			xtype:'textfield',
			fieldLabel:'流程状态',
			name:'status_cd',
			readOnly:true,
			anchor:'95%'
		}],
		buttons:[
	         {
	        	 text:'查看流程图',
	        	 handler:function(){
	        		 var flow_tmpl_id = Ext.getCmp('flow_tmpl_id').getValue();
	        		 var flow_tmpl_name = Ext.getCmp('flow_tmpl_name').getValue();
	        		 showFlow(flow_tmpl_id,flow_tmpl_name);
	        	 }
	         }
		]
};

var field_panel=new Ext.grid.GridPanel({				
	region:'center',
	title:'功能列表',
	split:true,
	ds: e_c_store,
	cm: e_c_cm,	
	viewConfig: {forceFit:true}
});

var root=getRootNode('null','',expandMetadataNode);
var e_panel = new Ext.Panel({
	autoScroll:true,
	region : 'center',
	split : true,
	layout : 'border',
	items : [
         {
        	 xtype:'panel',
        	 region:'north',
        	 height:200,
        	 layout:'border',
        	 items:[page_panel,flow_panel]
         },	metadataTreePanel = new Ext.tree.TreePanel({
    		title : '元数据树',
    		region : 'center',
    		collapsible : false,
    		id : 'metadataTreePanel',
    		frame : false,
    		loader : new Ext.tree.TreeLoader(),
    		lines : true,
    		split : true,
    		border : true,
    		autoScroll : true,
    		bodyStyle : 'padding:5px',
    		width : 460,
    		root : root
    	})]
});
	
Ext.onReady(function() {
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:[{
			xtype:'panel',
			layout:'border',
			items:[blmbTree,e_panel]
		}]
	});
});



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

var leafTypes = ["XST","COL","FLD","FBT","TBT"];

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
							leaf : tl[i].is_leaf=='N' ? false : true,
							icon : pathUrl + '/static/meta/img/' + tl[i].md_cate_cd + '.png',
							md_cate_cd : tl[i].md_cate_cd,
							children : [{
								text : 'loading',
								cls : 'x-tree-node-loading',
								leaf : true
							}]
						});
						
						cnode.on('expand', expandMetadataNode);
						parentNode.appendChild(cnode);
					}
//					Ext.getCmp("view").doLayout();
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
var leafTypes = ["XST","COL","FLD","FBT","TBT"];

function isLeaf(type) {
	for (var i = 0; i < leafTypes.length; i++) {
		if(leafTypes[i] == type)
			return true;
	}
	return false;
}