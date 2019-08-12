
//全局变量 存放当前选中控件类型 是否为树
var cur_is_tree = '';

var column_ds = new Ext.data.JsonStore({
	url : pathUrl + '/metadata/pageField/getFieldsForDisOrder',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['field_id', 'field_name', 'component_label']
});
var column_cm = [
    new Ext.grid.RowNumberer(),
	{
		header : '字段名',
		dataIndex : 'field_name',
		width:160
	}, {
		header : '字段描述',
		dataIndex : 'component_label',
		width:160
	}
];

/**
 * 保存页面字段
 * @param {} infoPanel
 */
function savePageField(infoPanel) {

	if(infoPanel.form.isValid()) {
		infoPanel.form.submit({
			waitMsg : '正在处理,请稍后......',
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "保存成功!");
				var desc = Ext.getCmp("component_label").getValue()+"["+Ext.getCmp("field_name").getValue()+"]";
				window.parent.updateTreeNodeName(metadata_id,desc);
			},
			failure : function(form, action) {
				Ext.MessageBox.alert("提示信息", action.result.info);
			}
		});
	} else {
		Ext.MessageBox.alert("提示信息", "请输入完整信息");
	}
	
}



/**
 * 删除页面字段对象
 * @param {} page_struct_id
 */
function deletePageField() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/pageField/delete',
		params : {
			field_id : metadata_id
		},
		method : 'POST',
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				window.parent.deleteTreeNode(metadata_id);
			} else {
				Ext.MessageBox.alert('提示信息', json.info);
			}
		}
	})
}


/***
 * 重新加载维度信息(增加控件类型参数)  
 * @param type
 */
function reLoadDimCdStore(type){
	var is_tree ='N';
	if(type=='uxtree'){
		is_tree = 'Y';
	}
	//全局变量判断  是否为切换下拉列表 和树 如果没切换 则不重新加载维度
	if(!cur_is_tree){
		cur_is_tree = is_tree;
	}else{
		if(cur_is_tree == is_tree){
			return;
		}else{
			cur_is_tree = is_tree;
		}
		Ext.getCmp('dimCd').clearValue();
	}
	
	Ext.getCmp('dimCd').getStore().load({params:{is_tree:is_tree}});
}

function setAbledStatus(type){
	
	if(type=='combobox'||type=='uxgrid'||type=='uxtree'){
		Ext.getCmp('dimCd').setDisabled(false);
		Ext.getCmp('max_length').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
	}else if(type=='textfield'||type=='textarea'||type=='datefield'||type=='hidden'||type=='moneyfield'||type=='numberfield'){
		Ext.getCmp('dimCd').setDisabled(true);
		Ext.getCmp('max_length').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
	}else if(type==null||type==''){
		Ext.getCmp('dimCd').setDisabled(true);
		Ext.getCmp('max_length').setDisabled(false);
		Ext.getCmp('max_value').setDisabled(false);
		Ext.getCmp('min_value').setDisabled(false);
	}else{
		Ext.getCmp('dimCd').setDisabled(true);
		Ext.getCmp('max_length').setDisabled(true);
		Ext.getCmp('max_value').setDisabled(true);
		Ext.getCmp('min_value').setDisabled(true);
	}
}
