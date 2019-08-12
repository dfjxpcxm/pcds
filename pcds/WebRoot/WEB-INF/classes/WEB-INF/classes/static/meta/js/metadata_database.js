/**
 * 保存数据库
 * @param {} infoPanel
 */
function saveDatabase(infoPanel) {

	if(infoPanel.form.isValid()) {
		infoPanel.form.submit({
			waitMsg : '正在处理,请稍后......',
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "保存成功!");
				window.parent.updateTreeNodeName(metadata_id,Ext.getCmp("database_name").getValue());
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
 * 测试数据库连接是否成功
 * @param {} infoPanel
 */
function testConnect(infoPanel) {
	
	if(infoPanel.form.isValid()) {
		var test_user_name = Ext.getCmp("test_user_name").getValue();
		var test_user_password = Ext.getCmp("test_user_password").getValue();
		if(test_user_name == '' || test_user_password == '') {
			Ext.MessageBox.alert("提示信息", "请输入测试用户名和密码!");
			return;
		}
		infoPanel.form.submit({
			url : pathUrl + '/metadata/database/testConnect',
			waitMsg : '正在处理,请稍后......',
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "连接成功!");
				window.parent.updateTreeNodeName(metadata_id,Ext.getCmp("database_name").getValue());
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
function deleteDatabase() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/database/delete',
		params : {
			database_id : metadata_id
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