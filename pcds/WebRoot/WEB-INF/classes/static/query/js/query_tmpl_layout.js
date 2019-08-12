Ext.onReady(function(){
	//数据源工具条
	var dsTbar = [{
		text : '新增',
		iconCls : 'add',
		handler:function(){
			var snode = dsTree.getSelectionModel().getSelectedNode();
			if(!snode){
				Ext.Msg.alert('提示信息','请在下表选择一个节点。');
				return ;
			}
			addDsNode(dsTree,snode);
		}
	},'-',{
		text : '编辑',
		iconCls : 'edit',
		handler:function(){
			var snode = dsTree.getSelectionModel().getSelectedNode();
			if(!snode){
				Ext.Msg.alert('提示信息','请在下表选择一个节点。');
				return ;
			}
			if(snode.id=='root'){
				return ;
			}
			editDsNode(dsTree,snode);
		}
	},'-',{
		text : '删除',
		iconCls : 'delete',
		handler:function(){
			var snode = dsTree.getSelectionModel().getSelectedNode();
			if(!snode){
				Ext.Msg.alert('提示信息','请在下表选择一个节点。');
				return ;
			}
			if(snode.id=='root'){
				return ;
			}
			if(snode.childNodes.length>0){
				Ext.Msg.alert('提示信息','包含下级节点，无法删除。');
				return ;
			}
			deleteDsNode(snode)
		}
	},'-',{
		text : '配置查询项',
        tooltip:'配置维度过滤、展示指标等',
		iconCls : 'page_white_wrench',
		handler:function(){
			var snode = dsTree.getSelectionModel().getSelectedNode();
			if(!snode){
				Ext.Msg.alert('提示信息','请在下表选择一个节点。');
				return ;
			}
			if(snode.id=='root' || snode.attributes['attr1']=='0'){
				Ext.Msg.alert('提示信息','请选择数据源节点。');
				return ;
			}
            showDimWindow(snode);
		}
	}];
	
	var dsTree = new Ext.tree.TreePanel({
		title:'数据集',
		region:'west',
		width : 260,
		id:'dsTreePanel',
		lines:true,
		autoScroll:true,
		bodyStyle : 'padding:5px 5px',
		loader: new Ext.tree.TreeLoader({dataUrl: pathUrl + '/queryTmpl/getDsTree'}),
		root :new Ext.tree.AsyncTreeNode({
			text: '数据集',
			iconCls:'folder_table',
			id:'root'
		}),
		split:true,
		rootVisible:true,
		tbar:dsTbar,
		listeners: {
	        click: function(n) {
	        	if(n.attributes['attr1'] && n.attributes['attr1']=='1'){
	        		dsMetaDS.load({params:{ds_id:n.id}});
	        	}else{
	        		dsMetaDS.removeAll();
	        	}
	        }
	    }
	});
	
	//数据源元数据工具条
	var dsMetaTBar = [{
		text : '锁定并编辑',
		iconCls : 'page_white_wrench',
		handler:function(){
           	var r1=Ext.getCmp('dsMetaGridPanel').getSelectionModel().getSelected();
           	editor.startEditing(r1,false);
		}
	}, 
    {
        text:'调整字段顺序',
        iconCls:'list',
        handler:function(){
        	var snode = dsTree.getSelectionModel().getSelectedNode();
			if(!snode){
				Ext.Msg.alert('提示信息','请在下表选择一个节点。');
				return ;
			}
			if(snode.id=='root' || snode.attributes['attr1']=='0'){
				Ext.Msg.alert('提示信息','请选择数据源节点。');
				return ;
			}
			showOrderWin(snode);
        }
    }];
	
	//编辑器
	var editor = new Ext.ux.grid.RowEditor({
		 saveText: '更新',
   		 cancelText: '取消',
   		 commitChangesText :'数据有变动，请选择【更新】或【取消】',
         listeners:{
        	 'beforeedit':function(t,i){
					//设置默认值
					var r = dsMetaDS.getAt(i);
					if(r.get('is_query') === true){
						linkTypeCombo.setDisabled(false);
					}else{
						linkTypeCombo.setDisabled(true);
					}
					//维度
					if(r.get('is_dim') === true){
						dimCombo.setDisabled(false);
					}else{
						dimCombo.setDisabled(true);
					}
					return true;
			},
        	'validateedit' : function(t,c,r,i){
        		//'ds_id','field_id','field_label','is_dim','field_type','dim_cd','is_query','is_hidden','default_value','display_order','is_order'
				//'is_query','is_order','is_hidden','default_value'
				//全部记录
				var params={
						ds_id : r.get('ds_id'),
						field_id : r.get('field_id'),
						field_label : r.get('field_label'),
						field_type : r.get('field_type'),
						is_dim : formatBoolCol(r.get('is_dim')),
						dim_cd : r.get('dim_cd'),
						is_query : formatBoolCol(r.get('is_query')),
						link_type_cd : r.get('link_type_cd'),
						is_order : formatBoolCol(r.get('is_order')),
						is_hidden : formatBoolCol(r.get('is_hidden')),
						default_value : r.get('default_value'),
						display_order : r.get('display_order') 
				}
				//获取变更记录
				Ext.iterate(c, function(name, value){
					if('is_query,is_order,is_hidden,is_dim'.indexOf(name)>-1){
						value = formatBoolCol(value);
					}
	                params[name] = value;
	            });
				
				//特殊处理
				if(params.is_query=='N'){
					c['link_type_cd'] = '';
				}
				//特殊处理
				if(params.is_dim=='N'){
					c['dim_cd'] = '';
				}
				
				//更新
				Ext.Ajax.request({
	    		   method:'post',
	    		   url : pathUrl + '/queryTmplMeta/updateDsMeta',
				   params:params,
				   success: function(response, opts) {
				   		var json=Ext.util.JSON.decode(response.responseText);
				   		if(json.success){
				   			//开始编辑record
				   			r.beginEdit();
				            Ext.iterate(c, function(name, value){
				                r.set(name, value);
				            });
				            r.endEdit();
				   		}else{
				   			Ext.Msg.alert('提示信息',json.info);
				   		}
				   },
				   failure: function(response, opts) {
				      Ext.Msg.alert('修改失败',reponse.responseText);
				   }
				});
				return false;
			}
        }
	});
 
	//数据源Store
	var dsMetaDS = new Ext.data.JsonStore({
		url : pathUrl + '/queryTmplMeta/getDsMeta',
		root: 'results',
		totalProperty: 'totalCount',
	/*	autoLoad: true,*/
		fields: ['ds_id','field_id','field_label','is_dim','field_type','dim_cd','is_query','link_type_cd','is_hidden','default_value','display_order','is_order']
	});


	//字段类型
	var fieldTypeCombo = new ArrayCombo({
		id : 'fieldTypeCombo',
		hiddenName : 'field_type_combo',
		data : [
				[ 'combobox','复选框'],
				[ 'uxtree','树形'], 
				[ 'textfield','文本框'],
				[ 'numberfield','数值'],
				[ 'moneyfield','钱币'],
				[ 'datefield','日期'],
				[ 'hidden','隐藏']
				],
		fieldLabel : '',
		defaultValue : '0',
		anchor : '90%'
	}) 
	
	//维度
	var dimCombo = new Ext.form.ComboBox({
		valueField : 'dim_cd',
		displayField : 'dim_name',
		mode : 'local',
		name : 'dim_combo',
		hiddenName : 'dim_combo',
		editable : false,
		triggerAction : 'all',
		fieldLabel : '',
		store:new Ext.data.JsonStore({
            	url : pathUrl + '/queryTmplMeta/getDimSource',
				root : 'result',
				totalProperty : 'totalCount',
				defaultValue : '',
				autoLoad:true,
				fields : ['dim_cd', 'dim_name']
        }),
		anchor : '90%'
	})
	 
	//数据源Grid列
	var grid = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
		{	
			header: '数据源ID',
			dataIndex: 'ds_id',
			hidden:true
			},
		{
			header: '字段名', 
			dataIndex: 'field_id',
			width: 70 
		},
		{
			header: '字段标签', 
			dataIndex: 'field_label',
			width: 70,
			editor: {
                 xtype: 'textfield'
            }
		},
		{
			header: '字段类型',
			dataIndex:'field_type',
			width: 70,
			editor: fieldTypeCombo 
		},
        {
			header: '是否维度',
			dataIndex:'is_dim',
			xtype: 'booleancolumn',
			trueText: 'Y',
			falseText: 'N',
			align: 'center',
            width: 50,
			editor: {
                xtype: 'checkbox',
            	listeners:{
            		'check':function(t,checked){
            			if(checked){
            				dimCombo.setDisabled(false);
            			}else{
            				dimCombo.setDisabled(true);
            			}
            		}
            	}
            }
		},
		{
			header: '关联维度',
			dataIndex:'dim_cd',
			width: 70,
			editor: dimCombo
		},
		{
			header: '是否是查询',
			dataIndex:'is_query',
			xtype: 'booleancolumn',
			trueText: 'Y',
			falseText: 'N',
			defaultValue : 'N',
			align: 'center',
            width: 70,
			editor: {
                xtype: 'checkbox',
                listeners:{
                	'check':function(t,checked){
                		if(checked){
                			//默认=
                			linkTypeCombo.setDisabled(false);
                		}else{
                			linkTypeCombo.setDisabled(true);
                		}
                	}
                }
            }
		},
		{	
			header: '关联方式',
			dataIndex:'link_type_cd',
			width: 50,
			editor:linkTypeCombo
        },
		{	
			xtype: 'booleancolumn',
			id:'is_hidden',
			header: '是否隐藏',
			dataIndex:'is_hidden',
			align: 'center',
			defaultValue : 'N',
            width: 50,
			trueText: 'Y',
			falseText: 'N',
			editor: {
            	xtype: 'checkbox'
            }
        },
		{	
			header: '默认值',
			dataIndex:'default_value',
			width: 50,
			editor: {
				xtype: 'textfield'
            }
        },
        {
        	header: '是否排序', 
			dataIndex:'is_order',
			defaultValue : 'N',
			xtype: 'booleancolumn',
			trueText: 'Y',
			falseText: 'N',
			align: 'center',
            width: 50,
			editor: {
                xtype: 'checkbox'
            }
		},{
			header: '顺序', 
			dataIndex:'display_order',
			width: 50 
        }
	]);

	//行选择模式
	var sm = new Ext.grid.RowSelectionModel({
		singleSelect: true
	});
		
	var dsMetaGP = new Ext.grid.GridPanel({
		id: 'dsMetaGridPanel',
		title: '显示字段列表',
		region: 'center',
		autoScorll: true,
		split: true,
		plugins: [editor],
		ds: dsMetaDS,
		cm: grid,
		sm: sm,
		tbar:dsMetaTBar,
		loadMask: true,
		viewConfig: {forceFit: true} 
	});
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[dsTree,dsMetaGP]	
	});
});