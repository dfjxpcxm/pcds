var baseUrl = pathUrl+'/queryTmpl';
//类型下拉框
TypeSelector=function(){
	var store = new Ext.data.JsonStore({
		url : baseUrl + '/listByType',
		root:'results',
		fields:['id_field','value_field'],
		listeners:{
			'load':function(t,r,o){
				if (t.getCount() > 0) {
					var value = t.getAt(0).get('id_field');
					Ext.getCmp('typeList').setValue(value);
				}
			}
		}
	})
	
	TypeSelector.superclass.constructor.call(this,{
		store:store,
		valueField:'id_field',
		displayField:'value_field',
		mode : 'local',
		editable : false,
		triggerAction : 'all',
		fieldLabel : '',
		hiddenName : 'type_list',
		name : 'type_list',
		id : 'typeList',
		width:120
	});
}
Ext.extend(TypeSelector,Ext.form.ComboBox)
 
//删除分配
function removeUserDs(gridPanel,dsId){
    
	var infos = getUserIds(gridPanel);
	
	Ext.MessageBox.confirm('提示','确认将选中的用户移除查询权限',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : baseUrl + '/deleteUserDs',
				method : 'POST',
				params : {dsId : dsId, userIds : infos},
				callback : function(options, success, response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (success) { 
						gridPanel.store.reload({params:{ds_id:dsId}});
					} else {
						Ext.MessageBox.alert(json.info);
					}
				}
			})
		}
	});
}

function getUserIds(gridPanel){
	var userIds='';
	var rs = gridPanel.getSelectionModel().getSelections();
	for(var i=0;i<rs.length;i++){
        var r=rs[i];
        if(i>0){
        	userIds+=',';
        }
        userIds+=r.get('user_id');
    }
	return userIds;
}
