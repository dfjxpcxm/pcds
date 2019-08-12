//当前选中数据库
var curr_database_id = '';
//当前选中用户
var curr_user_id = '';
//当前选中主题
var curr_theme_id = '';
//当前选中表
var curr_table_id = '';

//重置关联表的标识
var isReConfigRelaTable = false;


/**
 * 保存页面结构
 * @param {} infoPanel
 */
function savePageStruct(infoPanel) {
	var rela_table = Ext.getCmp('rela_table_id').getValue();
	if(relaTableId){
		if(rela_table){
			isReConfigRelaTable = !(relaTableId == rela_table);
		}
	}
	
	if(infoPanel.form.isValid()) {
		infoPanel.form.submit({
			waitMsg : '正在处理,请稍后......',
			params:{isReConfigRelaTable:isReConfigRelaTable},
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "保存成功!");
				if(pageFieldStore){
					pageFieldStore.load();
				}
				relaTableId = Ext.getCmp('rela_table_id').getValue();
				window.parent.updateTreeNodeName(metadata_id,Ext.getCmp("page_struct_desc").getValue());
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
 * 删除页面结构对象
 * @param {} page_struct_id
 */
function deletePageStruct() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/pageStruct/delete',
		params : {
			page_struct_id : metadata_id,
			md_cate_cd:Ext.getCmp('md_cate_cd').getValue()
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
 