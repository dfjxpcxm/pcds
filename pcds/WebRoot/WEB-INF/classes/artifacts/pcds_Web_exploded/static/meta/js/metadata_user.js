/**
 * 保存数据库用户
 * @param {} infoPanel
 */
function saveUser(infoPanel) {

	if(infoPanel.form.isValid()) {
		infoPanel.form.submit({
			waitMsg : '正在处理,请稍后......',
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "保存成功!");
				window.parent.updateTreeNodeName(metadata_id,Ext.getCmp("user_name").getValue());
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
 * 删除数据库对象
 * @param {} database_id
 */
function deleteUser() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/user/delete',
		params : {
			user_id : metadata_id
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