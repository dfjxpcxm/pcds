var rule_type_flag = '' ,resetStatus = '0',record; 
var expander = new Ext.grid.RowExpander({
	tpl : new Ext.Template('<br><p><b>校验公式:</b> {chk_formula}</p><br>')
});
var meta_rule_ds = new Ext.data.JsonStore({
	url : pathUrl + '/checkRule/listMetadataCheckRule',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['chk_rule_code','chk_rule_name','chk_rule_desc',
      'chk_type_code','chk_type_name','chk_method_code','chk_method_name','rule_type_flag',
      'chk_formula','chk_formula_desc','chk_priority',
      'status_code','status_name','status_name','create_user_id','create_time',
      'update_user_id','update_time','display_order','chk_failure_tip','chk_action']
});
//校验规则column
var meta_rule_cm = [
 expander,
{
	header : '校验规则名称',
	dataIndex : 'chk_rule_name'
}, {
	header : '校验规则描述',
	dataIndex : 'chk_rule_desc'
},{
	header : '校验类型',
	dataIndex : 'chk_type_name'
},{
	header : '校验方法',
	dataIndex : 'chk_method_name'
}, {
	header : '规则类型',
	dataIndex : 'rule_type_flag',
	renderer:function(v){
		switch(v){
			case '0': return '内置';break; 
			case '1': return '自定义';break; 
		}
	}
}, {
	header : '校验公式描述',
	dataIndex : 'chk_formula_desc'
}, {
	header : '校验优先级',
	dataIndex : 'chk_priority'
}, {
	header : '校验动作',
	dataIndex : 'chk_action',
	renderer:function(v){
		if(v =='0'){
			return '通用';
		}else if(v =='1'){
			return '保存时校验';
		}else if(v =='9'){
			return '提交时校验';
		}
		return '';
	}
},{
	header : '状态',
	dataIndex : 'status_name'
}];

MyViewportUi = Ext.extend(Ext.Viewport, {
	layout : 'border',
	initComponent : function() {
		Ext.applyIf(this, {
			items : [
				metadataTreePanel = new Ext.tree.TreePanel({
	    		title : '元数据树',
	    		region : 'west',
	    		collapsible : false,
	    		id : 'metadataTreePanel',
	    		frame : false,
	    		loader : new Ext.tree.TreeLoader(),
	    		lines : true,
	    		split : true,
	    		border : true,
	    		autoScroll : true,
	    		bodyStyle : 'padding:5px',
	    		width : 360,
	    		root : getRootNode('root','元数据',expandMetadataNode)
	    	}),
			{
				xtype : 'grid',
				id:'ruleGrid',
				region:'center',
				title:'校验规则列表',
				columns : meta_rule_cm,
				store : meta_rule_ds,
				plugins : expander,
				loadMask:true,
				viewConfig : {
					forceFit : true},
				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
				bbar:new Ext.PagingToolbar({
					pageSize : 30,
					store : meta_rule_ds,
					displayInfo : true,
					displayMsg : '第{0}-{1}条记录,共{2}条记录',
					emptyMsg : "没有记录"
				}),
				tbar : [{
					xtype : 'button',
					text : '添加',
					iconCls : 'add',
					id:'add_btn',
					handler : function() {
						var node = metadataTreePanel.getSelectionModel().getSelectedNode();
						if(!node){
							Ext.MessageBox.alert('提示','请先选择元数据');
							return;
						}
						add(node);
					}
				},
				{
					xtype : 'button',
					text : '编辑',
					iconCls : 'edit',
					id:'edit_btn',
					handler : function() {
						var r = Ext.getCmp('ruleGrid').getSelectionModel().getSelected();
						var node = metadataTreePanel.getSelectionModel().getSelectedNode();
						if(!node){
							Ext.MessageBox.alert('提示','请先选择元数据');
							return;
						}
						if(!r){
							Ext.MessageBox.alert('提示','请先选择校验规则');
							return;
						}
						record = r;
						
						var editWindow = new AddWindowTab({doUrl: pathUrl+"/checkRule/editMetaRule"});
						editWindow.show();
						
						var attributes  = node.attributes;
						//元数据分类
						if(attributes.md_cate_cd == 'TAB'){
							Ext.getCmp('col').setDisabled(false);
							Ext.getCmp('tab').setDisabled(false);
						}else if(attributes.md_cate_cd == 'PAG' || attributes.md_cate_cd == 'XST'){ //页面和sheet页支持列间配置
							Ext.getCmp('col').setDisabled(false);
							Ext.getCmp('tab').setDisabled(true);
						}else{
							Ext.getCmp('col').setDisabled(true);
							Ext.getCmp('tab').setDisabled(true);
						}
						
						Ext.getCmp('metadata_id').setValue(node.id);
						setValues(r);
						self_col_ds.load({params:{metadata_id : node.id,md_cate_cd : attributes.md_cate_cd}});
						tab_ds.load();
						tar_col_ds.removeAll();
						val_array={tab1_col_1_array:[],tab1_col_2_array:[],tab2_col_1_array:[],tab2_col_2_array:[],chk_formula_array:[]};
					}
				},
				{
					xtype : 'button',
					text : '删除',
					iconCls : 'delete',
					id:'del_btn',
					handler : function() {
					
						var r = Ext.getCmp('ruleGrid').getSelectionModel().getSelected();
						var node = metadataTreePanel.getSelectionModel().getSelectedNode();
						if(!node){
							Ext.MessageBox.alert('提示','请先选择元数据');
							return;
						}
						if(!r){
							Ext.MessageBox.alert('提示','请先选择校验规则');
							return;
						}
						Ext.Msg.confirm('确认','确认删除？',function(btn){
							if(btn=='yes'){
								Ext.Ajax.request({
									url: pathUrl + '/checkRule/deleteMetaRule',
									method: 'POST',
									params: {metadata_id:node.id,chk_rule_code:r.get('chk_rule_code')},
									success:function(response, options){
										var json=Ext.util.JSON.decode(response.responseText);
										var res=json.success;
										meta_rule_ds.reload();
										Ext.MessageBox.alert('提示',json.info);
									},
									failure:function(response, options){
										Ext.MessageBox.alert('错误',response.responseText);
									}
								});
							}
						});
					}
				}]
			}
			]
		});

		MyViewportUi.superclass.initComponent.call(this);

	}
});

//可以添加校验规则的分类
var enableRuleCate = "TAB,表,COL,列,PAG,页面,FLD,字段,XST,XCL";

Ext.onReady(function() {
	var myview = new MyViewportUi();
	check_rule_ds.on('beforeload',function(){
		var checkMethodCode = Ext.getCmp('chkMethodCode'); 
		var check_method_code = checkMethodCode.getValue();
		var metadata_id = Ext.getCmp('metadata_id').getValue();
		var searchKey = Ext.getCmp("searchKey").getValue();
		var type_code = getTypeCode();
		check_rule_ds.baseParams = {chk_method_code:check_method_code,chk_type_code:type_code,metadata_id:metadata_id,searchKey:searchKey};
	});
	
	metadataTreePanel.on('click', function(node,e) {
		var attr = node.attributes;
		var enabledFlag = false;
		if(attr){
			var md_cate_cd = attr.md_cate_cd;
			enabledFlag = enableRuleCate.indexOf(md_cate_cd)>-1;
		}
		if(enabledFlag){
			Ext.getCmp('add_btn').setDisabled(false);
			Ext.getCmp('edit_btn').setDisabled(false);
			Ext.getCmp('del_btn').setDisabled(false);
		}else{
			Ext.getCmp('add_btn').setDisabled(true);
			Ext.getCmp('edit_btn').setDisabled(true);
			Ext.getCmp('del_btn').setDisabled(true);
		}
		meta_rule_ds.baseParams={metadata_id:node.id};
		meta_rule_ds.load();
	});
});

var getTypeCode = function (){
//	var items = Ext.getCmp('type_rg').items.items; 
//	var type_code = '';
//	for ( var i = 0; i < items.length; i++) {
//		if(items[i].checked){
//			type_code = items[i].inputValue;
//			break;
//		}
//	}
	return Ext.getCmp('type_rg').getValue().getRawValue();
};

function add (node){
	var attributes =  node.attributes;
	if(attributes){
		self_table = attributes.metadata_name;
		var addWindow = new AddWindowTab({doUrl : pathUrl + '/checkRule/addMetaRule'});
		addWindow.show();
		Ext.getCmp('metadata_id').setValue(node.id);
		Ext.getCmp('type_rg').setValue('00');
		
		//元数据分类
		if(attributes.md_cate_cd == 'TAB'){
			Ext.getCmp('col').setDisabled(false);
			Ext.getCmp('tab').setDisabled(false);
		}else if(attributes.md_cate_cd == 'PAG'  || attributes.md_cate_cd == 'XST'){
			Ext.getCmp('col').setDisabled(false);
			Ext.getCmp('tab').setDisabled(true);
		}else{
			Ext.getCmp('col').setDisabled(true);
			Ext.getCmp('tab').setDisabled(true);
		}
		
		self_col_ds.load({params:{metadata_id : node.id,md_cate_cd : attributes.md_cate_cd}});
		tab_ds.load();
		tar_col_ds.removeAll();
		val_array={tab1_col_1_array:[],tab1_col_2_array:[],tab2_col_1_array:[],tab2_col_2_array:[],chk_formula_array:[]};
	}
}

function setValues(r){
	resetStatus = '0';
	Ext.getCmp('chk_rule_code').setValue(r.get('chk_rule_code'));
	Ext.getCmp('old_chk_rule_code').setValue(r.get('chk_rule_code'));
	Ext.getCmp('chk_rule_name').setValue(r.get('chk_rule_name'));
	Ext.getCmp('chk_failure_tip').setValue(r.get('chk_failure_tip'));
	Ext.getCmp('type_rg').setValue(r.get('chk_type_code'));
	Ext.getCmp('chkMethodCode').setValue(r.get('chk_method_code'));
	Ext.getCmp('chkPriority').setValue(r.get('chk_priority'));
	Ext.getCmp('chkAction').setValue(r.get('chk_action'));
}