var formItemAnchor = '91%';

/***
 * 关联元数据数据源
 */
var rela_metadata_store=new Ext.data.Store({
	proxy:new Ext.data.HttpProxy({
		url:pathUrl+'/metadata/pageRelaMetadata/queryRelaMetadata'
	}),
	reader : new Ext.data.JsonReader({
		root : 'results'
	}, [{
		name : 'rela_metadata_id',
		mapping : 'rela_metadata_id'
	}, {
		name : 'rela_metadata_name',
		mapping : 'rela_metadata_name'
	}]),
	remoteSort : false 
});

if(showRelyMeta==1){
	rela_metadata_store.load();
}

/**
 * 图标下拉框
 */
var icon_path_store = new Ext.data.JsonStore({
	url:pathUrl+'/metadata/pageButton/queryIcons',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['icon_path','icon_desc'],
	autoLoad :true
});

Ext.onReady(function(){
	
	//属性展示面板
	var infoPanel = new Ext.form.FormPanel({
		title : '页面按钮属性',
		url : pathUrl + "/metadata/pageButton/save",
		method : 'POST',
		border : true,
		split : true,
		frame : true,
		labelWidth : 100,
		labelAlign : 'left',
		bodyStyle : 'padding: 0px 25px 5px 25px',
		autoScroll : true,
		buttonAlign : 'center',
		itemCls  : 'uxHeight',
		reader : new Ext.data.JsonReader({
			root : 'results'
		}, [
			{name : 'button_id'}, 
			{name : 'button_name'}, 
			{name : 'md_cate_cd'} ,
			{name : 'button_func_cd'}, 
			{name : 'icon_path'}, 
			{name : 'rela_metadata_id'}, 
			{name : 'is_customer_sql'}, 
			{name : 'dml_sql'}
			
		]),
		items : [
			{
				xtype : 'textfield',
				name : 'button_id',
				fieldLabel : '按钮ID',
				allowBlank : false,
				readOnly : true,
				anchor : formItemAnchor
			},{
				xtype : 'hidden',
				name : 'md_cate_cd',
				fieldLabel : '分类代码',
				allowBlank : false,
				anchor : formItemAnchor
			},{
				xtype : 'textfield',
				id : 'button_name',
				name : 'button_name',
				fieldLabel : '按钮名称',
				allowBlank : false,
				anchor : formItemAnchor
			},new UppButtonFunctionSelector(),{
				xtype:'combo',
				fieldLabel:'关联元数据',
				editable: false,
				hidden : (showRelyMeta == 0),
				id:'relaMetadataId',
				valueField :'rela_metadata_id',
				displayField: 'rela_metadata_name',
				store: rela_metadata_store,
				mode: 'local',
				hiddenName:'rela_metadata_id',
				triggerAction: 'all',
				anchor : formItemAnchor
			},{
				xtype:'combo',
				fieldLabel:'显示图标',
				editable: false,
				id:'iconPathCombo',
				valueField :'icon_path',
				displayField: 'icon_desc',
				store: icon_path_store,
				mode: 'local',
				hiddenName:'icon_path',
				triggerAction: 'all',
				value:' ',
				anchor : formItemAnchor
			},{
				xtype:'combo',
				fieldLabel:'是否自定义SQL',
				editable: false,
				id:'isCustomerSql',
				valueField :'retrunValue',
				displayField: 'displayText',
				store: new Ext.data.SimpleStore({
							fields: ['retrunValue', 'displayText'],
							data: [['Y','是'],['N','否']]
						}),
				value:'Y',
				mode: 'local',
				hiddenName:'is_customer_sql',
				triggerAction: 'all',
				anchor : formItemAnchor,
				allowBlank:false,
				listeners:{
					select:function(combo, record, index){
						var value = record.get('retrunValue');
						configDmlSql(value);
					}
				}
			},{
				xtype : 'textarea',
				name : 'dml_sql',
				id : 'dml_sql',
				height:180,
				fieldLabel : 'DML语句',
				hidden:true,
				anchor : '91%'
			}
		],
		buttons : [{
			text : '保存',
			handler : function(){
				savePageButton(infoPanel);
			}
		},{
			text : '删除',
			handler : function(){
				Ext.MessageBox.confirm("确认信息", "是否删除该页面按钮信息?", function(btn) {
					if (btn == 'yes')
						deletePageButton();
				});
			}
		}]
	});
	
	var viewport = new Ext.Viewport({
		layout:'fit',
		border : false,
		items:[{
		    xtype : 'panel',
			layout : 'form',
			border : false,
			items:[
			       infoPanel,
			       {
			    	   xtype:'panel',
			    	   cls:'p_help',
			    	   html:'说明：【关联元数据】作用是设置当前操作按钮关联的操作或需要的配置.<br><br>例如：【新增表单】表示点击按钮时弹出新增表单。'
			       }
			]
		}]
	});
	
	if(showRelyMeta==1){
		Ext.getCmp('buttonFuncCd').on('select',function(){
			//新增 编辑
			var func_code = this.getValue();
			
			if(isSameFuncCd(curr_func_code,func_code)){//判断是否为相似或者相同的功能
				curr_func_code = func_code;
				return;
			}
			curr_func_code = func_code;
			
			var rela_md_cate_cd=getRelaMdCateCode(func_code);
			var prt_metadata_id = getRelaParentId(rela_md_cate_cd);
			if(prt_metadata_id){
				configRelaMetadata(rela_md_cate_cd,prt_metadata_id);
			}else{
				Ext.Ajax.request({
					url:pathUrl + '/metadata/pageStruct/queryPageByBtn',
					params : {
						metadata_id : metadata_id
					},
					callback:function(options,success,response){
						var json = Ext.util.JSON.decode(response.responseText);
						if(json.success){
							var page_id = json.results[0].page_id;
							//获取关联元数据
							configRelaMetadata(rela_md_cate_cd,page_id);
						}else{
							Ext.Msg.alert('提示信息',json.result.info);
						}
					}
				});
			}
			
		});
	}
	
	infoPanel.form.load({
		url : pathUrl + '/metadata/pageButton/load',
		params : {
			button_id : metadata_id
		},
		success :function (form,action){
			//当前功能按钮功能
			curr_func_code = Ext.getCmp('buttonFuncCd').getValue();
			
			var is_customer_sql = Ext.getCmp('isCustomerSql').getValue()
			configDmlSql(is_customer_sql);
			
			var func_code = Ext.getCmp('buttonFuncCd').getValue();
			rela_md_cate_cd = getRelaMdCateCode(func_code);
			var value = Ext.getCmp('relaMetadataId').getValue();
			var prt_metadata_id = getRelaParentId(rela_md_cate_cd);
			if(prt_metadata_id){
				configRelaMetadata(rela_md_cate_cd,prt_metadata_id,value);
			}else{
				Ext.Ajax.request({
					url:pathUrl + '/metadata/pageStruct/queryPageByBtn',
					params : {
						metadata_id : metadata_id
					},
					callback:function(options,success,response){
						var json = Ext.util.JSON.decode(response.responseText);
						if(json.success){
							var page_id = json.results[0].page_id;
							//获取关联元数据
							configRelaMetadata(rela_md_cate_cd,page_id,value);
						}else{
							Ext.Msg.alert('提示信息',json.result.info);
						}
					}
				});
				
			}
		}
	});
});

/**
 * 设置DML语句定义控件显示
 * @param is_customer_sql
 */
function configDmlSql(is_customer_sql){
	if(is_customer_sql == 'N'){
		Ext.getCmp('dml_sql').setVisible(false);
		Ext.getCmp('dml_sql').allowBlank = true;
	}else{
		Ext.getCmp('dml_sql').setVisible(true);
		Ext.getCmp('dml_sql').allowBlank = false;
	}
}

function getSplitPanel(height) {
	return {
		xtype : 'panel',
		border : false,
		height : height
	}
}