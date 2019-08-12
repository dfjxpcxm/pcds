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
		remoteSort : false,
		autoLoad :true
	});
 
//调整排序所用的按钮
var btn_column_ds = new Ext.data.JsonStore({
	url : pathUrl + '/metadata/pageButton/getButtonsForDisOrder',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['button_id', 'button_name', 'button_func_name']
});
var btn_column_cm = [
    new Ext.grid.RowNumberer(),
	{
		header : '按钮名',
		dataIndex : 'button_name',
		width:160
	}, {
		header : '按钮功能',
		dataIndex : 'button_func_name',
		width:160
	}
];

/***
 * 添加按钮的方法
 * @param md_cate_cd
 */
function addPageButton(){
	var formItemAnchor = '91%';
	var curr_func_code = '';
	var formPanel = new Ext.form.FormPanel({
		layout : 'form',
		url : pathUrl + '/metadata/pageButton/add',
		border : true,
		split : true,
		frame : true,
		labelWidth : 100,
		labelAlign : 'left',
		bodyStyle : 'padding: 5px 5px 5px 5px',
		autoScroll : true,
		buttonAlign : 'center',
		items : [
					{
						xtype : 'textfield',
						id : 'button_name',
						name : 'button_name',
						fieldLabel : '按钮名称',
						anchor : formItemAnchor,
						allowBlank : false
					},new UppButtonFunctionSelector(),{
						xtype:'combo',
						fieldLabel:'关联元数据',
						editable: false,
						id:'relaMetadataId',
						valueField :'rela_metadata_id',
						displayField: 'rela_metadata_name',
						store: rela_metadata_store,
						mode: 'local',
						hiddenName:'rela_metadata_id',
						triggerAction: 'all',
						anchor : formItemAnchor
					},{
						xtype : 'textfield',
						id : 'icon_path',
						name : 'icon_path',
						fieldLabel : '显示图标路径',
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
								if(value == 'Y'){
									Ext.getCmp('dml_sql').setVisible(true);
									
								}else{
									Ext.getCmp('dml_sql').setVisible(false);
								}
							}
						}
					},{
						xtype : 'textarea',
						height:150,
						name : 'dml_sql',
						id   : 'dml_sql',
						anchor : formItemAnchor,
						fieldLabel : 'DML语句'
					}
				]
	});
	
	//添加元数据表单项目
	var fields = getMdFormFieldsForBtn(metadata_id);//metadata_id为当前工具条 或者表单ID
	
	for (var i = 0; i < fields.length; i++) {
		formPanel.add(fields[i]);
	}
	
	var addToolbarButtonWindow = new Ext.Window({
		layout : 'fit',
		title : '添加按钮',
		buttonAlign : 'center',
		modal : true,
		width : 650,
		height :400,
		items : [formPanel],
		listeners : {
			'close' : function(){
				addToolbarButtonWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							addToolbarButtonWindow.destroy();
							Ext.Msg.alert("提示信息", "操作成功!");
							window.parent.refreshNode();
							pageButtonStore.load();
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addToolbarButtonWindow.destroy();
			}
		}]
	});
	
	Ext.getCmp('buttonFuncCd').on('select',function(){
		//新增 编辑
		var func_code = this.getValue();
		if(isSameFuncCd(curr_func_code,func_code)){
			curr_func_code = func_code;
			return;
		}
		curr_func_code = func_code;
		var rela_md_cate_cd = getRelaMdCateCode(func_code);
		if(!rela_md_cate_cd){
			clearRelaMetadata();
			return;
		}
		var prt_metadata_id = '';
		if(rela_md_cate_cd == window.parent.category_type_excel){
			prt_metadata_id = '30';
			configRelaMetadata(rela_md_cate_cd,prt_metadata_id);
		}else {
			
			Ext.Ajax.request({
				url:pathUrl + '/metadata/pageStruct/querySimplePageParent',
				params : {
					metadata_id : metadata_id
				},
				callback:function(options,success,response){
					var json = Ext.util.JSON.decode(response.responseText);
					if(json.success){
						var page_id = json.results[0].page_id;
						//获取关联元数据
						configRelaMetadata(window.parent.category_type_form,page_id);
					}else{
						Ext.Msg.alert('提示信息',json.result.info);
					}
				}
			});
			
		}
		
	});
	
	Ext.getCmp('buttonFuncCd').getStore().on('load',function(){
		curr_func_code = Ext.getCmp('buttonFuncCd').getValue();
	});
	
	addToolbarButtonWindow.show();
	
	Ext.Ajax.request({
		url:pathUrl + '/metadata/pageStruct/querySimplePageParent',
		params : {
			metadata_id : metadata_id
		},
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			if(json.success){
				var page_id = json.results[0].page_id;
				//获取关联元数据
				configRelaMetadata(window.parent.category_type_form,page_id);
			}else{
				Ext.Msg.alert('提示信息',json.result.info);
			}
		}
	});
	
	//获取关联元数据
	//configRelaMetadata(window.parent.category_type_form,curr_page_id);
	
}


/**
 * 点击编辑按钮
 */
function editPageButton(button_id){
	var formItemAnchor = '91%';
	var formPanel = new Ext.form.FormPanel({
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
				xtype : 'hidden',
				name : 'button_id',
				fieldLabel : '按钮ID',
				allowBlank : false,
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
				xtype : 'textfield',
				id : 'icon_path',
				name : 'icon_path',
				fieldLabel : '显示图标路径',
				anchor : formItemAnchor
			},{
				xtype:'combo',
				fieldLabel:'关联元数据',
				editable: false,
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
				fieldLabel : 'DML语句',
				hidden:true,
				anchor : '91%'
			}
		]
	});
	
	var updateMetadataWindow = new Ext.Window({
		layout : 'fit',
		title : '编辑按钮',
		buttonAlign : 'center',
		modal : true,
		width : 650,
		height :400,
		items : [formPanel],
		listeners : {
			'close' : function(){
				updateMetadataWindow.destroy();
			}
		},
		buttons : [{
			text : '确定',
			handler : function() {
				if (formPanel.form.isValid()) {
					formPanel.form.submit({
						waitMsg : '正在处理,请稍后......',
						success : function(form, action) {
							updateMetadataWindow.destroy();
							window.parent.refreshNode();
							pageButtonStore.load();
							Ext.Msg.alert("提示信息", "操作成功!");
						},
						failure:function(form, action){
							Ext.MessageBox.alert('提示信息',	action.result.info);
						}
					});
				} else {
					Ext.Msg.alert("提示信息", "请输入完整信息!");
				}
			}
		}, {
			text : '取消',
			handler : function() {
				updateMetadataWindow.destroy();
			}
		}]
	});
	
	updateMetadataWindow.show();
	//获取选中记录按钮ID
	formPanel.form.load({
		url : pathUrl + '/metadata/pageButton/load',
		params : {
			button_id : button_id
		},
		success:function(){
			var value = Ext.getCmp('isCustomerSql').getValue();
			configDmlSql(value);
			
			var func_code = Ext.getCmp('buttonFuncCd').getValue();
			var rela_md_cate_cd = getRelaMdCateCode(func_code);
			//var prt_metadata_id = getRelaParentId(rela_md_cate_cd);
			var value = Ext.getCmp('relaMetadataId').getValue();
			
			if(rela_md_cate_cd == window.parent.category_type_excel){
				var prt_metadata_id = '30';
				configRelaMetadata(rela_md_cate_cd,prt_metadata_id,value);
			}else {
				Ext.Ajax.request({
					url:pathUrl + '/metadata/pageStruct/querySimplePageParent',
					params : {
						metadata_id : metadata_id
					},
					callback:function(options,success,response){
						var json = Ext.util.JSON.decode(response.responseText);
						if(json.success){
							var page_id = json.results[0].page_id;
							//获取关联元数据
							configRelaMetadata(window.parent.category_type_form,page_id,value);
						}else{
							Ext.Msg.alert('提示信息',json.result.info);
						}
					}
				});
				
			}
			
			//configRelaMetadata(rela_md_cate_cd,prt_metadata_id,value);
		}
	});
	
	Ext.getCmp('buttonFuncCd').on('select',function(){
		//新增 编辑
		var func_code = this.getValue();
		if(isSameFuncCd(curr_func_code,func_code)){
			curr_func_code = func_code;
			return;
		}
		curr_func_code = func_code;
		var rela_md_cate_cd = getRelaMdCateCode(func_code);
		var prt_metadata_id = '';
		if(!rela_md_cate_cd){
			clearRelaMetadata();
			return;
		}
		
		if(rela_md_cate_cd == window.parent.category_type_excel){
			prt_metadata_id = '30';
			configRelaMetadata(rela_md_cate_cd,prt_metadata_id);
		}else {
			Ext.Ajax.request({
				url:pathUrl + '/metadata/pageStruct/querySimplePageParent',
				params : {
					metadata_id : metadata_id
				},
				callback:function(options,success,response){
					var json = Ext.util.JSON.decode(response.responseText);
					if(json.success){
						var page_id = json.results[0].page_id;
						//获取关联元数据
						configRelaMetadata(window.parent.category_type_form,page_id);
					}else{
						Ext.Msg.alert('提示信息',json.result.info);
					}
				}
			});
			
		}
		//configRelaMetadata(rela_md_cate_cd,prt_metadata_id);
		
	});
	
	Ext.getCmp('buttonFuncCd').getStore().on('load',function(){
		curr_func_code = Ext.getCmp('buttonFuncCd').getValue();
	});
}

/**
 * 获取删除参数 
 * @param records
 * @returns {String}
 */
function getDelButtonsParams(records){
	if(records.length == 0){
		return '';
	}
	var delParams = '';
	for(var i = 0;i<records.length;i++){
		if(i == records.length-1){
			delParams = delParams +records[i].get('button_id') ;
		}else{
			delParams = delParams +records[i].get('button_id') + ',';
		}
	}
	return delParams;
}

//删除
function deletePageButton(records,button_id){
	var delParams = getDelButtonsParams(records);
	Ext.Ajax.request({
		url:pathUrl + '/metadata/pageButton/deleteBatch',
		params : {
			del_params: delParams
		},
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			if(json.success){
				Ext.Msg.alert('提示信息','删除成功');
				pageButtonStore.load();
				window.parent.refreshNode();
			}else{
				Ext.Msg.alert('提示信息',json.result.info);
			}
		}
	});
	
}

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

//调整排序
function showOrderWinForBtn(){
	var gridPanel = new Ext.grid.GridPanel ({
		columns : btn_column_cm,
		store : btn_column_ds,
		enableDragDrop:true, 
		dropConfig:{appendOnly:true},
		ddGroup:'GridDD',
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		tbar:[ 
		  {
	    	 text:'上移',
	    	 iconCls:'arrow-up',
	    	 handler:function(){
	    		 var rows = gridPanel.getSelectionModel().getSelections();  
	    		 if(!rows||!rows.length>0){
	    			 Ext.Msg.alert('提示','请选中一行数据！');
	    			 return;
	    		 }
		    	 var i = btn_column_ds.indexOf(rows[0]); 
	    		 sortStoreForBtn(rows,i==0?0:i-1,gridPanel);
	    	 }
	     },
	     {
	    	 text:'下移',
	    	 iconCls:'arrow-down',
	    	 handler:function(){ 
		    	 var rows = gridPanel.getSelectionModel().getSelections();  
		    	 if(!rows||!rows.length>0){
	    			 Ext.Msg.alert('提示','请选中一行数据！');
	    			 return;
	    		 }
		    	 var i = btn_column_ds.indexOf(rows[0]); 
		    	 if(i == btn_column_ds.getCount()-1){//最底部 不进行移动
		    		 return;
		    	 }
	    		 sortStoreForBtn(rows,i+rows.length,gridPanel);
	    	 }
	     },
	     {
	    	 text:'移到顶部',
	    	 iconCls:'arrow-up',
	    	 handler:function(){
	    		 var rows = gridPanel.getSelectionModel().getSelections(); 
	    		 if(!rows||!rows.length>0){
	    			 Ext.Msg.alert('提示','请选中一行数据！');
	    			 return;
	    		 }
	    		 sortStoreForBtn(rows,0,gridPanel);
	    	 }
	     },
	     {
	    	 text:'移到底部',
	    	 iconCls:'arrow-down',
	    	 handler:function(){ 
		    	 var rows = gridPanel.getSelectionModel().getSelections();
	    		 if(!rows||!rows.length>0){
	    			 Ext.Msg.alert('提示','请选中一行数据！');
	    			 return;
	    		 }  
	    		 sortStoreForBtn(rows,btn_column_ds.getCount()-1,gridPanel);
	    	 }
	     }
		 ]
     });
	var orderWin = new Ext.Window({
		title:'按钮排序',
		buttonAlign : 'center',
		id : 'addWindow',
		width : 400,
		height : 500,
		layout:'fit',
		modal : true,
		items:[
		       gridPanel
		 ],
		buttons:[
		      {
		    	  text:'确定',
		    	  handler:function(){
		    		  updateDisOrderForBtn(btn_column_ds);
		    		  orderWin.destroy();
		    		  pageButtonStore.load();
		    		  window.parent.refreshNode();
		    	  }
		      },{
		    	  text:'取消',
		    	  handler:function(){
		    		  orderWin.destroy();
		    	  }
		      }
		 ]
	});
	orderWin.on('beforedestroy',function(){
		 Ext.dd.ScrollManager.unregister(gridPanel.getView().getEditorParent());
	});
	orderWin.show();
	var dt = new Ext.dd.DropTarget(gridPanel.container,{
		ddGroup:'GridDD',
		copy:false,
		notifyDrop:function(dd, e, data){
			  var rows = data.selections;  
	          var index = dd.getDragData(e).rowIndex;  
	          sortStoreForBtn(rows,index,gridPanel);
		}
	});
	Ext.dd.ScrollManager.register(gridPanel.getView().getEditorParent());
	var prt_metadata_id = '';
	
	btn_column_ds.load({
		params:{
			prt_metadata_id:metadata_id,//如果有按钮列表ID 则直接将按钮列表ID设置为父级参数
			md_cate_cd:getButtonMdCateCd()
		},
		callback:function(){
			var index = btn_column_ds.findBy(function(r){
				if(r.get('button_id')== metadata_id){
					return true;
				}else{
					return false;
				}
			});
			 var r =  btn_column_ds.getAt(index);
			 gridPanel.getSelectionModel().selectRow(index);
		     gridPanel.getView().focusRow(index);
		}
	});
}
function updateDisOrderForBtn(store){
	var params = "";
	if(store&&store.getCount()>0){
		for ( var int = 0; int < store.getCount(); int++) {
			var record = store.getAt(int);
			params +=record.get("button_id")+','+(store.indexOf(record)+1)+';';
		}
	}
//	var metadata_id = Ext.getCmp('metadata_id').getValue();
	var index = store.findBy(function(r){
		if(r.get('button_id')==metadata_id){
			return true;
		}else{
			return false;
		}
	});
	//Button.setValue(index+1);
	Ext.Ajax.request({
		   url:  pathUrl + '/metadata/pageButton/updateDisOrder',
		   params:{orderParam : params},
		   method : 'POST',
		   callback: function(success,options,response){
			   var json = Ext.util.JSON.decode(response.responseText);
			   if(json.success){
				   Ext.Msg.alert('提示信息','排序成功');
				   pageButtonStore.load();
			   }else{
				   Ext.Msg.alert('提示信息',json.info);
			   }
		   },
		   failure: function(){}
	});
}
function sortStoreForBtn(rows,index,gridPanel){
	  if (typeof(index) == "undefined") {  
          return;  
      }  
      var gridIndex = btn_column_ds.indexOf(rows[0]);  
      //
      if(index<=(gridIndex+rows.length-1)&&index>=gridIndex){
      	return;
      }
      //确定正序还是倒序  
      var mark = true;  
      if(index<gridIndex) 
      	mark = false;  
      var  selectArray = [];
      for(i = 0; i < rows.length; i++) {  
       var rowData;  
          if(mark){  
              rowData = rows[i];  
           }else{  
              rowData = rows[rows.length-i-1];  
          }  
          if(!this.copy)   
          	btn_column_ds.remove(rowData);  
          btn_column_ds.insert(index, rowData); 
          selectArray.push( btn_column_ds.indexOf(rowData));
      } 
      gridPanel.getSelectionModel().selectRows(selectArray);
      gridPanel.getView().refresh();//刷新序号    
      gridPanel.getView().focusRow(selectArray[0]);
 }
 
 /**
  * 根据父级节点返回通用的元数据表单项
  * @param {} prt_metadata_id
  */
 function getMdFormFieldsForBtn(prt_metadata_id) {
 	var fields = new Array();
 	fields = [{
 		xtype : 'hidden',
 		name : 'prt_metadata_id',
 		value : prt_metadata_id
 	},{
 		xtype : 'hidden',
 		name : 'md_cate_cd',
 		value : getButtonMdCateCd()
 	},{
 		xtype : 'hidden',
 		name : 'status_cd',
 		value : '02'
 	},{
 		xtype : 'hidden',
 		name : 'is_display',
 		value : 'Y'
 	}];
 	return fields;
 }
 

 /**
  * 获取关联元数据cate_code 按钮
  * @param func_code
  * @returns {String}
  */
 function getRelaMdCateCode(func_code){
 	var md_cate_cd = '';
 	if(func_code == '01' || func_code == '02'){//新增修改 按钮对应表单
 		md_cate_cd = 'FRM';
 	}else if(func_code == '07' || func_code == '08'){//导入导出对应EXCEL
 		md_cate_cd = 'XLS';
 	}
 	return md_cate_cd;
 }
 
 /***
  * 获取当前按钮的元数据类型
  * 如果是工具条 返回工具条按钮类型
  * 如果是表单  返回表单按钮类型
  * @returns {String}
  */
 function getButtonMdCateCd(){
	 var rsultValue = '';
	 if(md_cate_cd == window.parent.category_type_toolbar){
		 rsultValue = window.parent.category_type_toolbar_button;
	 }else if(md_cate_cd == window.parent.category_type_form){
		 rsultValue = window.parent.category_type_form_button;
	 }
	 return rsultValue;
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


 /***
  * 关联元数据的的配置
  * @param md_cate_cd
  * @param prt_metadata_id
  */
 function configRelaMetadata(rela_md_cate_cd,prt_metadata_id,value){
	clearRelaMetadata();
 	Ext.getCmp('relaMetadataId').getStore().load({
 		params:{
 			md_cate_cd:rela_md_cate_cd,
 			prt_metadata_id:prt_metadata_id,
 			metadata_id:metadata_id
 		},
 		callback:function(){
 			if(value){
 				Ext.getCmp('relaMetadataId').setValue(value);
 			}
 		}
 	
 	});
 }
 
 function clearRelaMetadata(){
	 Ext.getCmp('relaMetadataId').getStore().removeAll();
	 Ext.getCmp('relaMetadataId').setValue('');
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
 
