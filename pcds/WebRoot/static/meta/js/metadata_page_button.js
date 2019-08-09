/**
 * 保存页面按钮
 * @param {} infoPanel
 */
function savePageButton(infoPanel) {

	if(infoPanel.form.isValid()) {
		infoPanel.form.submit({
			waitMsg : '正在处理,请稍后......',
			success : function(form, action){
				Ext.MessageBox.alert("提示信息", "保存成功!");
				window.parent.updateTreeNodeName(metadata_id,Ext.getCmp("button_name").getValue());
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
 * 删除页面按钮对象
 * @param {} page_struct_id
 */
function deletePageButton() {
	Ext.Ajax.request({
		url : pathUrl + '/metadata/pageButton/delete',
		params : {
			button_id : metadata_id
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
 * 关联元数据的的配置
 * @param md_cate_cd
 * @param prt_metadata_id
 */
function configRelaMetadata(rela_md_cate_cd,prt_metadata_id,value){
	Ext.getCmp('relaMetadataId').getStore().removeAll();
	Ext.getCmp('relaMetadataId').setValue('');
	Ext.getCmp('relaMetadataId').getStore().load({
		params:{
			md_cate_cd:rela_md_cate_cd,
			prt_metadata_id:prt_metadata_id
		},
		callback:function(){
			if(value){
				Ext.getCmp('relaMetadataId').setValue(value);
			}
		}
	
	});
}
/**
 * 获取关联元数据cate_code
 * @param func_code
 * @returns {String}
 */
function getRelaMdCateCode(func_code){
	var md_cate_cd = '';
	if(func_code == '01' || func_code == '02' || func_code == '15'){//新增修改 按钮对应表单
		md_cate_cd = 'FRM';
	}else if(func_code == '07' || func_code == '08'){//导入导出对应EXCEL
		md_cate_cd = 'XLS';
	}
	return md_cate_cd;
}
/**
 * 获取关联元数据的父节点
 * @param rela_md_cate_cd
 * @returns
 */
function getRelaParentId(rela_md_cate_cd){
	var prt_metadata_id;
	//如果关联的是EXL 修改父节点为EXCEL的父节点  否则为当前页面ID
	if(rela_md_cate_cd == window.parent.category_type_excel){
		prt_metadata_id = '30';
	}
	
	return prt_metadata_id;
}



/***
 * 判断所选功能是否为相似
 * @param curr_func_code
 * @param func_code
 * @returns {Boolean}
 */
function isSameFuncCd(curr_func_code,func_code){
	
	if(curr_func_code == func_code){//重复点击 不做处理
		return true;
	}
	
	//对于选择相似的功能 不进行重置
	if((curr_func_code == '01' || curr_func_code == '02') && 
	   (func_code == '01' || func_code == '02')){
		return true;
	}
	
	if((curr_func_code == '07' || curr_func_code == '08') && 
	   (func_code == '07' || func_code == '08') ){
		return true;
	}
	
	return false;
}
