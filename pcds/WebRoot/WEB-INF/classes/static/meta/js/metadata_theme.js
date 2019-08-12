/**
 * 保存主题
 * @param {} infoPanel
 */
function saveTheme(infoPanel) {

	if(infoPanel.form.isValid()) {
		infoPanel.form.submit({
			waitMsg : '正在处理,请稍后......',
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "保存成功!");
				window.parent.updateTreeNodeName(metadata_id,Ext.getCmp("theme_name").getValue());
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
 * 删除主题
 * @param {} theme_id
 */
function deleteTheme() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/theme/delete',
		params : {
			theme_id : metadata_id
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