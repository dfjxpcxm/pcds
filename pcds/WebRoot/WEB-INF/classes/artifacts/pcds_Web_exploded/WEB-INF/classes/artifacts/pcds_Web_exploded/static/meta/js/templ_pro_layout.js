var metadata_type = '', isTree = '', tree = '', rootValue = '';

MyViewportUi = Ext.extend(Ext.Viewport, {
	layout : 'border',
	initComponent : function() {
		Ext.applyIf(this, {
			items : [
			templTreePanel = new TemplTreePanel({rootId:'root',rootName:'模板'})
			, panel = new Ext.Panel({
					title : '模板属性',
					id : 'editPanel',
					width : 700,
					autoScroll:true,
					region : 'east',
					split : true,
					layout : 'form',
					autoScroll : true,
					buttonAlign:'center',
					bodyStyle : 'padding: 50 150 0 150px',
					items : [{}],
					buttons : [
					{
						text : '新增',
						id:'add_btn',
						hidden:true,
						handler : function() {
							var formPanel = Ext.getCmp("editMdProForm");
							formPanel.form.reset();
							var metadata_id = metadataTreePanel.getSelectionModel().getSelectedNode().id;
							Ext.getCmp('metadata_id').setValue(metadata_id);
							Ext.getCmp('metadata_id').el.parent().parent().first().dom.innerHTML='父级元数据ID:';
							var com = Ext.getCmp('metadata_name');
							com.el.dom.readOnly=false;
							com.el.dom.style.background='#FBFBFF';
							com.el.dom.style.color='#000000';
							this.setDisabled(true);
							Ext.getCmp('save_btn').setDisabled(false);
						}
					},
					{
						text : '保存',
						id:'edit_btn',
						hidden:true,
						handler : function() {
							var url = '/templManager/editTmpl';
//							var add_flag = false;
//							var metadata_id = metadataTreePanel.getSelectionModel().getSelectedNode().id;
//							if(metadata_type=='2010'){
//								add_flag = true;
//								url = '/templManager/addTmpl'
//							}
							var formPanel = Ext.getCmp("editForm");
							if (formPanel.form.isValid()) {
								formPanel.form.submit({
									url : pathUrl + url,
									waitMsg : '正在处理,请稍后......',
									success : function(form, action) {
										Ext.Msg.alert("消息", action.result.info);
										var node = Ext.getCmp('templTreePanel').getSelectionModel().getSelectedNode();
										var type_flag = Ext.getCmp('type').getValue()=='col';
										if(type_flag){
											var tmpl = {tmpl_id:Ext.getCmp('tmpl_id').getValue(),
													col_name:Ext.getCmp('col_name').getValue(),
													col_desc:Ext.getCmp('col_desc').getValue()};
											replaceNode(node,tmpl,2);
										}else{
											var tmpl = {tmpl_id:Ext.getCmp('tmpl_id').getValue(),
													tmpl_desc:Ext.getCmp('tmpl_desc').getValue()};
											replaceNode(node,tmpl,1);
										}
//										if(add_flag){
//											Ext.getCmp('save_btn').setDisabled(true);
//											Ext.getCmp('add_btn').setDisabled(false);
//											addNode(action.result.metadata_id);
//										}
									},
									failure : function(form, action) {
										Ext.Msg.alert("消息", action.result.info);
									}
								})
							} else {
								Ext.Msg.alert("提示信息", "请输入完整信息");
							}
						}
					}
					]
			})]
		});

		MyViewportUi.superclass.initComponent.call(this);

	}
});

Ext.onReady(function() {
	dim_store.load();
	var myview = new MyViewportUi();

	templTreePanel.on('click', function(node,e) {
		var editPanel = Ext.getCmp('editPanel');
		var attributes = node.attributes;
		if(attributes.type=='templ'){
			editPanel.removeAll();
			editPanel.insert(1,edit_tmpl_form);
			Ext.getCmp('edit_btn').show();
			editPanel.doLayout();
			editPanel.setTitle('模板属性');
			Ext.getCmp('editForm').load({
				url : pathUrl + '/templManager/getTemplByID',
				params: {type:node.attributes.type,tmpl_id:node.attributes.tmpl_id,col_name:node.attributes.col_name}
			});
		}
		if(attributes.type=='col'){
			editPanel.removeAll();
			editPanel.insert(1,edit_col_form);
			Ext.getCmp('edit_btn').show();
			editPanel.doLayout();
			editPanel.setTitle('字段属性');
			Ext.getCmp('editForm').load({
				url : pathUrl + '/templManager/getTemplByID',
				params: {type:node.attributes.type,tmpl_id:node.attributes.tmpl_id,col_name:node.attributes.col_name}
			});
		}
		if(node.id=='root'){
//			editPanel.remove('tmpl_form');
//			editPanel.remove('col_form');
			editPanel.removeAll();
			Ext.getCmp('edit_btn').hide();
			editPanel.doLayout();
		}
		
	});
});

function delNode(tree,node){
	node.remove();
}

function addTempl(){
	var editPanel = Ext.getCmp('editPanel');
	Ext.getCmp('edit_btn').hide();
	editPanel.removeAll();
	var treePanel = Ext.getCmp('templTreePanel');
	var node = treePanel.getSelectionModel().getSelectedNode();
	if(!node){
		Ext.Msg.alert('提示','请选择节点');
		return;
	}
	if(node&&node.attributes.type=='root'){
		var addwin = new (addWindow(tmpl_form))();
		addwin.show();
	}else{
		var addwin = new (addWindow(col_form))();
		Ext.getCmp('tmpl_id').setValue(node.attributes.tmpl_id);
		Ext.getCmp('prt_col_name').setValue(node.attributes.col_name);
		addwin.show();
	}
}

function delTempl(){
	var treePanel = Ext.getCmp('templTreePanel');
	var node = treePanel.getSelectionModel().getSelectedNode();
	var attributes = node.attributes;
	if(!node){
		Ext.Msg.alert('提示','请选择节点');
		return;
	}
	if(node.id=='root'){
		return;
	}
	Ext.Msg.confirm('确认','确认删除？',function(btn){
		if(btn=='yes'){
			Ext.Ajax.request({
				url : pathUrl + '/templManager/deleteTempl',
				params : {
					tmpl_id:attributes.tmpl_id,
					type:attributes.type,
					col_name:attributes.col_name
				},
				waitMsg : '正在处理,请稍候...',
				method : 'POST',
				callback : function(options,success,response){
					var json = Ext.util.JSON.decode(response.responseText);
					if(json.success){
						Ext.Msg.alert('提示',json.info);
						delNode(treePanel,node);
					}
				}
			});
		}
	});
}

function replaceNode(oldNode,tmpl,type){
	 if(type==1){
		 var cnode=new Ext.tree.AsyncTreeNode({
				id:tmpl.tmpl_id+';root',
				text:'['+tmpl.tmpl_id+']'+tmpl.tmpl_desc,
//				iconCls:getIconCls(tl[i].metadata_cate_code),
				type:'templ',
				tmpl_id:tmpl.tmpl_id,
				col_name:'root',
				children:[{
					text:'loading',
					cls: 'x-tree-node-loading',
					leaf:true
				}]
			}); 
	 }else{
		 var cnode=new Ext.tree.AsyncTreeNode({
				id:tmpl.tmpl_id+';'+tmpl.col_name,
				text:'['+tmpl.col_name+']'+tmpl.col_desc,
//				iconCls:getIconCls(tl[i].metadata_cate_code),
				type:'col',
				tmpl_id:tmpl.tmpl_id,
				col_name:tmpl.col_name,
				children:[{
					text:'loading',
					cls: 'x-tree-node-loading',
					leaf:true
				}]
			}); 
	 }
	 cnode.on('expand',expandMetadataTreeNode);
	 oldNode.parentNode.replaceChild(cnode,oldNode);
}