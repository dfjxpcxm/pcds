function doAddDimInfo(dataStore) {
	
    var formPanel = new Ext.form.FormPanel({
		frame:true,
        labelWidth: 80,	
        url: pathUrl+'/managerDimTable/addDimInfo',        
        items: [{
        		xtype : 'textfield' ,
                fieldLabel: '维表代码',
                name: 'dim_code',//如果用tableCode页面不显示
				id:'dim_code_add',
                allowBlank:false,
        		anchor:'90%'
            },{
        		xtype : 'textfield' ,
                fieldLabel: '维表名称',
                id:'table_name_add',
                name: 'table_name',//如果用table_name页面不显示
                allowBlank:false,
        		anchor:'90%'
            },{
            	xtype : 'textfield' ,
                fieldLabel:'维表描述',
				id:'dim_name_add',
				name:'dim_name',
				allowBlank:false,
        		anchor:'90%'
            }, {
            	xtype : 'textfield' ,
                fieldLabel:'维表主键字段',
				id:'pk_name_add',
				name:'pk_name',
				allowBlank:false,
        		anchor:'90%'
            },{
            	xtype : 'textfield' ,
                fieldLabel:'维表显示字段',
				id:'fields_name_add',
				name:'fields_name',
				allowBlank:false,
        		anchor:'90%'
            }
        ]
	});

    var window = new Ext.Window({
        title: '添加维表',
        width: 400,
        height:300,
        layout:'fit',
        plain:true,
        modal:true,
        buttonAlign:'center',
        items: formPanel,
        buttons: [{
            text: '保存', 
            handler: function() {
                if (formPanel.form.isValid()) 
				{
	 		        formPanel.form.submit({      
			            waitMsg:'正在处理，请稍候。。。。。。',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.result.info);
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('确认', '保存完毕！');
						    window.destroy();
							dataStore.reload();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('错误', '请填写必输项！');
				}             
	        }
        },{
            text: '取消',
            handler: function(){window.destroy();}
        }]
    });

    window.show();
};

function doEditDimInfo(dataStore,tableCode) {	
	
	 var formPanel = new Ext.form.FormPanel({
		frame:true,
        labelWidth: 80,	
        url: pathUrl+'/managerDimTable/updateDimInfo',
		reader: new Ext.data.JsonReader({
				root: 'results'
			}, [{name: 'dim_code' },
				{name: 'table_name' },
				{name: 'dim_name'},
				{name: 'pk_name'},
				{name: 'fields_name'}]
        ),
        items: [{
        		xtype : 'textfield' ,
                fieldLabel: '维表代码',
                id:'dim_code_update',
                name: 'dim_code',//如果用tableCode页面不显示
                allowBlank:false,
        		anchor:'90%'
            },{
        		xtype : 'textfield' ,
                fieldLabel: '维表名称',
                id:'table_name_update',
                name: 'table_name',//如果用table_name页面不显示
                allowBlank:false,
        		anchor:'90%'
            },{
            	xtype : 'textfield' ,
                fieldLabel:'维表描述',
				id:'dim_name_update',
				name:'dim_name',
				allowBlank:false,
        		anchor:'90%'
            }, {
            	xtype : 'textfield' ,
                fieldLabel:'维表主键字段',
				id:'pk_name_update',
				name:'pk_name',
				allowBlank:false,
        		anchor:'90%'
            },{
            	xtype : 'textfield' ,
                fieldLabel:'维表显示字段',
				id:'fields_name_update',
				name:'fields_name',
				allowBlank:false,
        		anchor:'90%'
            }
        ]
	});

    formPanel.form.load({url: pathUrl+'/managerDimTable/findDimInfo/'+tableCode });    

    // define window and show it in desktop
    var window = new Ext.Window({
		title: '维护维表',
        width: 400,
        height:300,
        layout:'fit',
        plain:true,
        modal:true,
        buttonAlign:'center',
        items: formPanel,
        buttons: [{
            text: '保存', 
            handler: function() {
                if (formPanel.form.isValid()) 
				{
	 		        formPanel.form.submit({		
	 		            //params:{productID : rid;},	      
			            waitMsg:'正在处理，请稍候。。。。。。',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('错误', action.result.info);
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('确认', '保存完毕！');
						    window.destroy();
							dataStore.reload();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('错误', '请填写必输项！');
				}             
	        }
        },{
            text: '取消',
            handler: function(){window.destroy();}
        }]
    });

    window.show();
};

function doDeleteDimInfo(dataStore,tableCode){
	Ext.MessageBox.confirm('Message','警告!请确认要删除选中的记录吗?', 
		function(btn) {
			if(btn == 'yes')
			{
				Ext.Ajax.request({
					url: pathUrl+'/managerDimTable/deleteDimInfo/'+tableCode, //url to server side script
					method: 'POST',
					params: {},//the unique id(s)
					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						if (success) { //success will be true if the request succeeded
							Ext.MessageBox.alert("提示信息",json.info);
							//Ext.MessageBox.hide();
							dataStore.reload();
						} else {
							Ext.MessageBox.alert("提示信息",json.info);
							//Ext.MessageBox.hide();
						}
					}
				});
			}
		}
	);
}



/**
 * 添加维表数据
 */
function doAddDimTableData(){
	var selectionModel = gridPanel.getSelectionModel();
	if(selectionModel.getCount() == 0){
		Ext.Msg.alert('提示信息','请选择维表记录');
		return;
	}
	//获取当前选中维表记录
	var record = selectionModel.getSelections()[0];
	
	var fields_name = record.get('fields_name');
	var pk_name = record.get('pk_name');
	var table_name = record.get('table_name');
	
	//根据表名获取表结构相关数据
	Ext.Ajax.request({
		url:pathUrl + '/managerDimTable/getTableMeta',
		params:{table_name:table_name},
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			
			if(json.failure == true){
				Ext.Msg.alert('提示信息',json.info);
			}else{
				var addWinItems = getAddWinItems(record,json);
				if(!addWinItems){
					return;
				}
				showAddTableDataWin(addWinItems)
				
			} 
			
		}
	});
	
}

/**
 * 显示新增窗口
 * @param addWinItems
 */
function showAddTableDataWin(addWinItems){
	var addWindow = new Ext.Window({
		title : '维表数据维护',
		width : 450,
		autoHeight:true,
		plain : true,
		modal : true,
		resizable:false,
		bodyStyle : 'padding:10px;',
		layout : 'fit',
		buttonAlign : 'center',
		items : [{
			xtype : 'form',
			id : 'updatePanel',
			baseCls : 'x-plain',
			labelWidth : 100,
			url :pathUrl+ '/managerDimTable/addDimTableData',
			items : addWinItems
		}],
		buttons : [{
			text : '保存',
			handler : function() {
				var formPanel = Ext.getCmp("updatePanel");
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍候......',
						failure : function(form, action) {
							Ext.Msg.alert('提示信息', action.result.info);
						},
						success : function(form, action) {
							addWindow.destroy();
							Ext.Msg.alert('提示信息', '保存完毕');
							tableDataStore.reload();
						}
					});
				} else {
					Ext.MessageBox.alert('提示信息', '请填写必输项');
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addWindow.destroy();
			}
		}]
	});
	
	addWindow.on("close", function() {
		addWindow.destroy();
	});
	addWindow.show();
}

/**
 * 获取新增窗口选项
 * @param record
 * @returns
 */
function getAddWinItems(record,json){
	
	var fields_name = record.get('fields_name');
	var pk_name = record.get('pk_name');
	var table_name = record.get('table_name');
	
	var addWinItmes = new Array();
	var fields = fields_name.split(';');
	
	var colTypeStr = '';
	
	var resultFields = getColTypes(fields,json);
	if(!resultFields){
		return null;
	}
	
	var table_name_hid = new Ext.form.Hidden({name:'table_name',value:table_name});
	var fields_name_hid = new Ext.form.Hidden({name:'fields_name',value:resultFields.join(';')});
	addWinItmes.push(table_name_hid);
	addWinItmes.push(fields_name_hid);
	for(var i = 0;i<resultFields.length ;i++){
		var field_code = resultFields[i].split(',')[0];
		var field_name = resultFields[i].split(',')[1];
		var field_type = resultFields[i].split(',')[2];
		var field_isPk = resultFields[i].split(',')[3] == 'true';
		var field = null;
		if(field_type == 'DATE'){
			field = {
					xtype : 'datefield',
					fieldLabel : field_name,
					allowBlank : !field_isPk,
					value:new Date(),
					name : field_code,
					anchor : '100%',
					format:'Y-m-d'
				};
		}else{
			field = {
					xtype : 'textfield',
					fieldLabel : field_name,
					allowBlank : !field_isPk,
					name : field_code,
					anchor : '100%'
				};
		}
		
		addWinItmes.push(field);
	}
	return addWinItmes;
}

/**
 * 判断某字段是否为主键
 * @param field_code
 * @param pk_name
 * @returns
 */
function isPk(field_code,pk_name){
	var pk = pk_name.split(',');
	for(var i = 0;i<pk.length;i++){
		if(field_code == pk[i]){
			return true;
		}
	}
	return false;
}

/**
 * 判断某字段是否为主键  
 * 从数据库查询中得来
 * @param field_code
 * @param pk_arry
 * @returns
 */
function is_pk(field_code,pk_arry){
	for(var i = 0;i<pk_arry.length;i++){
		if(field_code == pk_arry[i]){
			return true;
		}
	}
	return false;
	
}


/**
 * 修改维表数据
 */
function doEditDimTableData(){
	
	var gridSelectionModel = gridPanel.getSelectionModel();
	if(gridSelectionModel.getCount() == 0){
		Ext.Msg.alert('提示信息','请选择维表记录');
		return;
	}
	
	var tableDataSelectionModel = tableDataPanel.getSelectionModel();
	if(tableDataSelectionModel.getCount() == 0){
		Ext.Msg.alert('提示信息','请选择需要修改的维表数据');
		return;
	}
	
	//获取当前选中维表记录
	var dimRecord = gridSelectionModel.getSelections()[0];
	var pk_name = dimRecord.get('pk_name');
	var fields_name = dimRecord.get('fields_name');
	var table_name = dimRecord.get('table_name');
	
	var dataRecord = tableDataSelectionModel.getSelections()[0];
	//根据表名获取表结构相关数据
	Ext.Ajax.request({
		url:pathUrl + '/managerDimTable/getTableMeta',
		params:{table_name:table_name},
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			/*var pks = json.pk;//屏蔽主键信息的判断
			if(!pks  || pks.length == 0){
				Ext.Msg.alert('提示信息','主键信息不存在,无法继续操作');
				return;
			}*/
			
			if(json.failure == true){
				Ext.Msg.alert('提示信息',json.info);
			}else{
				var editWinItems =getEditWinItems(dimRecord,dataRecord,json);
				showEditTableDataWin(editWinItems)
			} 
		}
	});
	
}

/**
 * 显示编辑窗口
 * @param editWinItems
 */
function showEditTableDataWin(editWinItems){
	
	var editWindow = new Ext.Window({
		title : '维表数据维护',
		width : 450,
		autoHeight:true,
		plain : true,
		modal : true,
		resizable:false,
		bodyStyle : 'padding:10px;',
		layout : 'fit',
		buttonAlign : 'center',
		items : [{
			xtype : 'form',
			id : 'updatePanel',
			baseCls : 'x-plain',
			labelWidth : 100,
			url :pathUrl+ '/managerDimTable/editDimTableData',
			items : editWinItems
		}],
		buttons : [{
			text : '保存',
			handler : function() {
				var formPanel = Ext.getCmp("updatePanel");
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍候......',
						failure : function(form, action) {
							Ext.Msg.alert('提示信息', action.result.info);
						},
						success : function(form, action) {
							editWindow.destroy();
							Ext.Msg.alert('提示信息', '保存完毕');
							tableDataStore.reload();
						}
					});
				} else {
					Ext.MessageBox.alert('提示信息', '请填写必输项');
				}
			}
		}, {
			text : '取消',
			handler : function() {
				editWindow.destroy();
			}
		}]
	});
	
	editWindow.on("close", function() {
		editWindow.destroy();
	});
	editWindow.show();
}

/**
 * 获取修改窗口选项
 * @param record
 * @returns
 */
function getEditWinItems(dimRecord,dataRecord,json){
	var fields_name = dimRecord.get('fields_name');
	var pk_name = dimRecord.get('pk_name');
	var table_name = dimRecord.get('table_name');
	
	var editWinItmes = new Array();
	var fields = fields_name.split(';');
	var resultFields = getColTypes(fields,json);
	
	var table_name_hid = new Ext.form.Hidden({name:'table_name',value:table_name});
	var fields_name_hid = new Ext.form.Hidden({name:'fields_name',value:resultFields.join(';')});
	var pk_name_hid = new Ext.form.Hidden({name:'pk_name',value:pk_name});
	
	editWinItmes.push(table_name_hid);
	editWinItmes.push(fields_name_hid);
	editWinItmes.push(pk_name_hid);
	
	for(var i = 0;i<fields.length ;i++){
		var field_code = fields[i].split(',')[0];
		var field_name = fields[i].split(',')[1];
		var field_type = fields[i].split(',')[2];
		var isPk = fields[i].split(',')[3] == 'true';
		var field = null;
		if('DATE' == field_type){
			field ={
					xtype : 'datefield',
					fieldLabel : field_name,
					allowBlank : !isPk,
					readOnly : isPk,
					name : field_code,
					value: dataRecord.get(field_code),
					format:'Y-m-d',
					anchor : '100%'
				}
		}else{
			field ={
					xtype : 'textfield',
					fieldLabel : field_name,
					allowBlank : !isPk,
					readOnly : isPk,
					name : field_code,
					value: dataRecord.get(field_code),
					anchor : '100%'
				}
		}
		editWinItmes.push(field);
	}
	return editWinItmes;
	
}


/**
 * 删除维表数据
 */
function doDeleteDimTableData(){
	
	var gridSelectionModel = gridPanel.getSelectionModel();
	if(gridSelectionModel.getCount() == 0){
		Ext.Msg.alert('提示信息','请选择维表记录');
		return;
	}
	
	var tableDataSelectionModel = tableDataPanel.getSelectionModel();
	if(tableDataSelectionModel.getCount() == 0){
		Ext.Msg.alert('提示信息','请选择需要删除的维表数据');
		return;
	}
	
	//当前选中维表记录
	var dimRecord = gridSelectionModel.getSelections()[0];
	var pk_name = dimRecord.get('pk_name');
	var fields_name = dimRecord.get('fields_name');
	
	//当前选中维表数据记录
	var dataRecord = tableDataSelectionModel.getSelections()[0];
	var deleteParams = getDeleteParams(dimRecord,dataRecord);
	
	Ext.Msg.confirm('删除提示','确认要删除选中的记录吗?',function(btn){
		if (btn == 'yes') {
			Ext.Ajax.request({
				url :pathUrl+ '/managerDimTable/deleteDimTableData',
				params:deleteParams,
				callback:function(options,success,response){
					var json = Ext.util.JSON.decode(response.responseText);
					Ext.Msg.alert('提示信息',json.info);
					tableDataStore.reload();
				}
			});
		}
	});
}

/**
 * 获取删除维表数据记录的请求参数
 * @param dimRecord
 * @param dataRecord
 * @returns {___delParam0}
 */
function getDeleteParams(dimRecord,dataRecord){
	
	var pk_name = dimRecord.get('pk_name');
	var table_name = dimRecord.get('table_name');
	
	if(pk_name == null || pk_name == ''){
		Ext.Msg.alert('提示信息','主键记录为空 ,无法删除');
		return ;
	}
	var pks = pk_name.split(',');
	var delParam = new Object();
	delParam.table_name = table_name;
	delParam.pk_name = pk_name;
	for(var i = 0;i<pks.length;i++){
		delParam[pks[i]] = dataRecord.get(pks[i]);
	}
	return delParam;
}

/**
 * 封装fields 增加类型
 * colname,viewname,coltype,ispk
 * @param fields
 * @param json
 */
function getColTypes(fields,json){
	var resultFields = fields;
	/*var pks = json.pk;//屏蔽主键信息的判断
	if(!pks  || pks.length == 0){
		Ext.Msg.alert('提示信息','主键信息不存在');
		return "";
	}*/
	var pks = '';//从配置中获取
	var gridSelectionModel = gridPanel.getSelectionModel();
	pks = gridSelectionModel.getSelections()[0].get('pk_name').split(',');
	 
	for(var i = 0;i<json.columns.length;i++){
		var col = json.columns[i];
		for(var j = 0;j<fields.length;j++){
			var field = fields[j];
			if(col.columnName == field.split(',')[0]){
				resultFields[j] = fields[j] +','+ col.columnType+','+is_pk(col.columnName,pks);
				break;
			}
		}
	}
	return resultFields;
}

/**
 * 显示导入窗口
 */
function showImpWindow(){
	
	var gridSelectionModel = gridPanel.getSelectionModel();
	if(gridSelectionModel.getCount() == 0){
		Ext.Msg.alert('提示信息','请选择维表记录');
		return;
	}
	var dimRecord = gridSelectionModel.getSelections()[0];
	var table_name = dimRecord.get('table_name');
	
	var impWindow = new Ext.Window({
		title : '维表数据导入 :  '+table_name,
		width : 450,
		autoHeight:true,
		plain : true,
		modal : true,
		resizable:false,
		bodyStyle : 'padding:10px;',
		layout : 'fit',
		buttonAlign : 'center',
		items : [
		         
	        	new Ext.form.FormPanel({
						id : 'uploadWinForm',
						baseCls : 'x-plain',
//									labelWidth : 70,
						url :pathUrl+ '/file/upload',
						fileUpload:true
						})
		         
		    ],
		buttons : [{
			text : '保存',
			handler : function() {
				var formPanel = Ext.getCmp("uploadWinForm");
				//校验文件格式 
				var fName = upForm.file1.value;
				if(!checkFileType(fName)){
					return;
				}
				
				Ext.Ajax.request({  
                    url : pathUrl+'/file/upload',  
                    isUpload : true,  
                    form :'upForm' ,
                    success:function(response){
                    	var fileName = response.responseText;
                    	if( fileName.indexOf('上传失败') != -1){
                    		Ext.Msg.alert('提示信息',fileName);
                    	}else{
                    		//执行保存操作
                    		//获取表名 文件名 提交后 进行保存操作 
                    		//当前选中维表记录
                    		var fields_name = dimRecord.get('fields_name');
                    		var fields = fields_name.split(';');
                    		
                    		//根据表名获取表结构相关数据
                    		Ext.Ajax.request({
                    			url:pathUrl + '/managerDimTable/getTableMeta',
                    			params:{table_name:table_name},
                    			callback:function(options,success,response){
                    				var json = Ext.util.JSON.decode(response.responseText);
                    				
                    				if(json.failure == true){
                    					Ext.Msg.alert('提示信息',json.info);
                    				}else{
                    					var resultFields = getColTypes(fields,json);
                    					fields_name = resultFields.join(';');
                    					impRequest(table_name,fileName,fields_name);
                    				} 
                    				
                    			}
                    		});
                    		
                    		impWindow.destroy(); 
                    	}
                    }
                });  
				
			}
		}, {
			text : '取消',
			handler : function() {
				impWindow.destroy();
			}
		}]
	});
	
	impWindow.on("close", function() {
		impWindow.destroy();
	});
	impWindow.show();
	//由于html属性失效 所以采取如下方式初始化
	var uploadWinForm = Ext.getDom('uploadWinForm');
	uploadWinForm.innerHTML='<form id="upForm"> 请选择文件:&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<input type="file" name="file1" style="width:268"></form>';

}

/**
 * 校验文件类型 
 * 排除excel文件
 * @param fName
 * @returns {Boolean}
 */
function checkFileType(fName){
	if(!fName){
		Ext.Msg.alert('提示信息','请选择文件');
		return false;
	}else{
		var index = fName.lastIndexOf('.');
		if(index == -1){
			Ext.Msg.alert('提示信息','文件格式不正确');
			return false;
		}
		var fileType = fName.substr(index+1,fName.length);
		if(fileType.indexOf('xls') == -1 && fileType.indexOf('xlsx') == -1 ){
			Ext.Msg.alert('提示信息','文件格式不正确');
			return false;
		}
	}
	return true;
}

/**
 * 导入数据请求方法
 * @param table_name
 * @param fileName
 * @param fields_name
 */
function impRequest(table_name,fileName,fields_name){
	Ext.Ajax.request({
		url:pathUrl+'/managerDimTable/impDimTableData',
		params:{table_name:table_name,file_name:fileName,fields_name:fields_name},
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			if(json.success){
				tableDataStore.reload();
			}
			Ext.Msg.alert('提示信息',json.info);
			
		}
	});
}
