/**
 * 添加维度明细窗口
 * @class DetailTreeAddWindow
 * @extends Ext.Window
 */
function addDimtTableConsole(dim_record,cols_str){
	dimTableAddWindow = new Ext.Window({
		layout : 'fit',
		title : '添加维度明细',
		buttonAlign : 'center',
		modal : true,
		width : 350,
		height : 300,
		listeners : {
			'close' : function(){
				dimTableAddWindow.destroy();
			}
		},
		items : [{
			xtype : 'form',
			id : 'addFieldDetailForm',
			layout : 'form',
			labelWidth : 85,
			border : false,
			labelAlign : 'left',
			url : pathUrl + '/dimLinkAjax/addDimTable',
		    items : [cols_str]
		 }],
		buttons : [{
			text : '保存',
			handler : function() {
				var formPanel = Ext.getCmp("addFieldDetailForm");
				if (formPanel.form.isValid()) {
					if(dim_record.get('is_tree')=='Y'){
						//如果是树形的话,得到父节点ID
						var node=Ext.getCmp("dimLinkDetailTreePanel").getSelectionModel().getSelectedNode();		      	
						var prt_col_name=dim_record.get('prt_col_name');//父ID字段
				    	var root_value=dim_record.get('root_value');//父ID字段
				    	var formValue=Ext.get("addFieldDetailForm");
				        if(node!=null){
				        	root_value=node.attributes.id;
				        	Ext.getCmp(prt_col_name).setValue(root_value);
				        }
					}
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							Ext.Msg.alert("消息", action.result.info);
							if (action.result.success) {
								refreshDimTable(dim_record,dimTableAddWindow);
							}
						},
						failure : function(form, action) {
							Ext.Msg.alert("消息", action.result.info);
						}
					})
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息");
				}
				
			}
		}, {
			text : '取消',
			handler : function() {
				dimTableAddWindow.destroy();
			}
		}]
	});
	dimTableAddWindow.show();
}
/**
 * 编辑维度信息
 * @param cols_str
 */
function editDimtTableConsole(dim_record,cols_str){
	dimTableEditWindow = new Ext.Window({
		layout : 'fit',
		title : '编辑维度明细',
		buttonAlign : 'center',
		modal : true,
		width : 350,
		height : 300,
		listeners : {
			'close' : function(){
				dimTableEditWindow.destroy();
			}
		},
		items : [{
			xtype : 'form',
			id : 'editFieldDetailForm',
			layout : 'form',
			labelWidth : 85,
			border : false,
			labelAlign : 'left',
			url : pathUrl + '/dimLinkAjax/editDimTable',
		    items : [cols_str]
		 }],
		buttons : [{
			text : '保存',
			handler : function() {
				var formPanel = Ext.getCmp("editFieldDetailForm");
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							Ext.Msg.alert("消息", action.result.info);
							if (action.result.success) {
								refreshDimTable(dim_record,dimTableEditWindow);
							}
						},
						failure : function(form, action) {
							Ext.Msg.alert("消息", action.result.info);
						}
					})
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息");
				}
				
			}
		}, {
			text : '取消',
			handler : function() {
				dimTableEditWindow.destroy();
			}
		}]
	});
	
	dimTableEditWindow.show();
}
	
/*
 * 添加数据明细维度
 */
function getAddTableInfo(record){
	var is_tree=record.get('is_tree');
	var prt_col_name=record.get('prt_col_name');
	var table_name=record.get('table_name');
	var tabke_pk=record.get('tabke_pk');
	var cols_array=record.get('table_cols').split(";");
	
	var table_info_array=[{
		xtype : 'hidden',
		name : 'table_name',
		fieldLabel : '表名',
		value : table_name,
		anchor : '95%'
	},{
		xtype : 'hidden',
		name : 'tabke_pk',
		id : 'tabke_pk',
		fieldLabel : '主键',
		text : tabke_pk,
		anchor : '95%'
	},{
		xtype : 'hidden',
		name : 'is_tree',
		id : 'is_tree',
		value : is_tree
	}];
	if(is_tree=='Y'){
      	var prt_col_name=record.get('prt_col_name');//父ID字段
    	var root_value=record.get('root_value');//父ID字段
        prt_col_array=[{
    		xtype : 'hidden',
    		id: prt_col_name,
    		name : prt_col_name,
    		value : root_value
    	}];
        table_info_array=table_info_array.concat(prt_col_array);
	}
	var editColArray=new Array();
	for(var i=0;i<cols_array.length;i++){
		var cols=cols_array[i].split(",");
		var editRcord=[{
					 xtype : 'textfield',
					 name : cols[0],
					 fieldLabel : cols[1],
					 allowBlank : false,
					 anchor : '95%'
			    }];
		editColArray=editColArray.concat(editRcord);
	}
	table_info_array=table_info_array.concat(editColArray);
	return table_info_array;
}
/**
 *得到编辑显示信息 
 * @param record 选中数据源维度
 * @param pk_code 主键值
 * @param dim_talbe_json 选中行记录
 * @returns {Array}
 */
function editTableInfo(record,pk_code,dim_talbe_json){
	var is_tree=record.get('is_tree');
	var prt_col_name=record.get('prt_col_name');
	var table_name=record.get('table_name');
	var tabke_pk=record.get('tabke_pk');
	var cols_array=record.get('table_cols').split(";");
	var pkMap=convertToMap(tabke_pk.split(","));
	var table_info_array=[{
		xtype : 'hidden',
		name : 'table_name',
		fieldLabel : '表名',
		value : table_name,
		anchor : '95%'
	},{
		xtype : 'hidden',
		name : 'tabke_pk',
		id : 'tabke_pk',
		fieldLabel : '主键',
		value : tabke_pk,
		anchor : '95%'
	},{
		xtype : 'hidden',
		name : 'pk_code',
		id : 'pk_code',
		value : pk_code
	},{
		xtype : 'hidden',
		name : 'is_tree',
		id : 'is_tree',
		value : is_tree
	}];
	var editColArray=new Array();
	for(var i=0;i<cols_array.length;i++){
		var cols=cols_array[i].split(",");//一个编辑列信息;
		var cols_key=cols[0];//编辑列
		var cols_desc=cols[1];//列描述
		var edit_val=dim_talbe_json[cols_key];
		var flag=false;
		//如果当前编辑列是主键,这为不可编辑状态
		if(pkMap.get(cols_key)!=null){
			flag=true;
		}
		var editRcord=[{
					 xtype : 'textfield',
					 name : cols_key,
					 fieldLabel : cols_desc,
					 allowBlank : false,
					 anchor : '95%',
					 disabled:flag,
					 value:edit_val
			    }];
		editColArray=editColArray.concat(editRcord);
	}
	table_info_array=table_info_array.concat(editColArray);
	/*if(editColArray.length>0){
		var editFieldSet=[{
		    id: Ext.id(),
		    xtype: "fieldset",
		    fieldLabel: "编辑列",
		    items:editColArray
		 }];
		table_info_array=table_info_array.concat(editFieldSet);
	}*/
	return table_info_array;
}
/**
 * 删除维度明细记录
 * @param dimRecord 选中数据源记录
 * @param pk_code 主键值
 */
function deleteDimTable(dimRecord,pk_code) {
	var table_name=dimRecord.get('table_name');
	var table_pk=dimRecord.get('tabke_pk');
	var is_tree=dimRecord.get('is_tree');
	var prt_col_name=dimRecord.get('prt_col_name');
	Ext.Ajax.request({
		method : 'POST',
		url : pathUrl + '/dimLinkAjax/deleteDimTable',
		params : {
			table_name:table_name,
			table_pk:table_pk,
			is_tree:is_tree,
			prt_col_name:prt_col_name,
			pk_code:pk_code
		},
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (success) {
			    Ext.MessageBox.alert("提示信息", json.info);
				refreshDimTable(dimRecord,null);
			} else {
				Ext.MessageBox.alert("提示信息", json.info);
			}
		}
	});
};
/**
 * 维度明细选中判断
 */
//是否选中了纬度数据源和纬度明细数据
function dimTableCheck(){
	if(currentRecord){
		var isTree = currentRecord.get('is_tree');
		if (isTree == 'Y') {
			if(!Ext.getCmp("dimLinkDetailTreePanel").getSelectionModel().getSelectedNode()){
				Ext.Msg.alert("提示信息", "请选择纬度列表数据");
				return false;
			}
		}else{
			var array= Ext.getCmp("dimLinkDetailGrid").getSelectionModel().getSelections();//选中数据源维度对象
			if(array.length==0){
				Ext.Msg.alert("提示信息", "请选择纬度列表数据");
				return false;
			}
		}
		return true;
	}else{
		Ext.Msg.alert("提示信息", "请先选择数据源纬度数据");
		return false;
	}
}

/**
 *主键数组转换为map对象
 * @param pk_name_array
 * @returns {Map}
 */
function convertToMap(pk_name_array){
	var pkMap=new Map();
	for(var i=0;i<pk_name_array.length;i++){
		pkMap.put(pk_name_array[i],"");
	}
	return pkMap;
}
/**
 * 刷新维度明细
 * @param dim_record 选中数据源记录
 * @param operWindow 当前窗口对象
 */
function refreshDimTable(dim_record,operWindow){
	var is_tree=dim_record.get('is_tree');
	var dim_cd = dim_record.get('dim_cd');
	if(is_tree=='Y'){
		tree.removeAll();
		rootName(dim_cd);
	}else{
		dim_data_ds.reload();
	}
	if(operWindow!=null) 	operWindow.destroy();
}