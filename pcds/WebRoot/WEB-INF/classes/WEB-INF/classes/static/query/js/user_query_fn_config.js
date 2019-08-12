//连接类型下拉框
var linkTypeCombo = new ArrayCombo({
	id : 'linkTypeCd',
	hiddenName : 'link_type_cd',
	data : [
	        ['=', '='],
	        ['!=', '!='], 
	        ['>', '>'],
	        ['>=', '>='],
	        ['<', '<'],
	        ['<=', '<='],
	        ['like', 'like'],
	        ['not like', 'not like']
	],
	fieldLabel : '',
	width : 70 
})

//调整排序所用的字段
var column_ds = new Ext.data.JsonStore({
	url : pathUrl + '/queryTmplMeta/getUserDsMeta',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['field_id','field_label','is_query','link_type_cd','is_order','is_hidden','default_value']
});

var column_cm = [
    new Ext.grid.RowNumberer(),
	{
		header : '字段名',
		dataIndex : 'field_id',
		width:100
	}, {
		header : '字段描述',
		dataIndex : 'field_label',
		width:150
	}, {
        xtype: 'booleancolumn',
        header : '查询条件',
		dataIndex : 'is_query',
        align: 'center',
        width: 70,
        trueText: 'Y',
        falseText: 'N',
        editor: {
            xtype: 'checkbox',
            listeners:{
            	'check':function(t,checked){
            		if(checked){
            			linkTypeCombo.setDisabled(false);
            		}else{
            			linkTypeCombo.setDisabled(true);
            		}
            	}
            }
        }
    },{
		header : '关联方式',
		dataIndex : 'link_type_cd',
		width:80,
		editor: linkTypeCombo
	}, {
        xtype: 'booleancolumn',
        header : '排序字段',
		dataIndex : 'is_order',
        align: 'center',
        width: 70,
        trueText: 'Y',
        falseText: 'N',
        editor: {
            xtype: 'checkbox'
        }
    },{
        xtype: 'booleancolumn',
        header : '是否隐藏',
		dataIndex : 'is_hidden',
        align: 'center',
        width: 70,
        trueText: 'Y',
        falseText: 'N',
        editor: {
            xtype: 'checkbox'
        }
    }, {
		header : '默认值',
		dataIndex : 'default_value',
		width:120,
		editor: {
			xtype: 'textfield'
		}
	}
];

var orderWin;
var initOrder = [];
//调整排序
function showOrderWin(dsId){
	
	if(orderWin){
		orderWin.show();
	}else{
		//编辑器
		var editor = new Ext.ux.grid.RowEditor({
			saveText: '修改',
			cancelText:'取消',
			commitChangesText :'数据有变动，请选择【修改】或【取消】',
			listeners:{
				'beforeedit':function(t,i){
					//设置默认值
					var r = column_ds.getAt(i);
					if(r.get('is_query') === true){
						linkTypeCombo.setDisabled(false);
					}else{
						linkTypeCombo.setDisabled(true);
					}
					return true;
				},
				'validateedit' : function(t,c,r,i){
					//'is_query','is_order','is_hidden','default_value'
					if(c['is_query'] === true ){
						if(!r.get('link_type_cd')){
							Ext.Msg.alert('提示信息','请选择【关联方式】');
							return false;
						}
					}
					//全部记录
					var params={
							ds_id : userDsId,
							field_id : r.get('field_id'),
							is_query : formatBoolCol(r.get('is_query')),
							link_type_cd : r.get('link_type_cd'),
							is_order : formatBoolCol(r.get('is_order')),
							is_hidden : formatBoolCol(r.get('is_hidden')),
							default_value : r.get('default_value') 
					}
					//获取变更记录
					Ext.iterate(c, function(name, value){
						if('is_query,is_order,is_hidden'.indexOf(name)>-1){
							value = formatBoolCol(value);
						}
		                params[name] = value;
		            });
					
					//特殊处理
					if(params.is_query=='N'){
						c['link_type_cd'] = '';
					}
					
					//更新
					Ext.Ajax.request({
		    		   method:'post',
		    		   url : pathUrl + '/queryTmplMeta/updateUserDsMeta',
					   params:params,
					   success: function(response, opts) {
					   		var json=Ext.util.JSON.decode(response.responseText);
					   		if(json.success){
					   			//开始编辑record
					   			r.beginEdit();
					            Ext.iterate(c, function(name, value){
					                r.set(name, value);
					            });
					            r.endEdit();
					   		}else{
					   			Ext.Msg.alert('提示信息',json.info);
					   		}
					   },
					   failure: function(response, opts) {
					      Ext.Msg.alert('修改失败',reponse.responseText);
					   }
					});
					return false;
				}
			}
		});
		
		var gridPanel = new Ext.grid.GridPanel ({
			id:'metaConfigPanel',
			border:false,
			columns : column_cm,
			store : column_ds,
			enableDragDrop:true, 
			dropConfig:{appendOnly:true},
			ddGroup:'GridDD',
			plugins: [editor],
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
			    		  var i = column_ds.indexOf(rows[0]); 
			    		  sortStore(rows,i==0?0:i-1,gridPanel);
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
			    		  var i = column_ds.indexOf(rows[0]); 
			    		  if(i == column_ds.getCount()-1){//最底部 不进行移动
			    			  return;
			    		  }
			    		  sortStore(rows,i+rows.length,gridPanel);
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
			    		  sortStore(rows,0,gridPanel);
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
			    		  sortStore(rows,column_ds.getCount()-1,gridPanel);
			    	  }
			      }
			      ]
		});
		
		orderWin = new Ext.Window({
			title:'>>>>>双击列表中的数据开始编辑,拖动数据可修改显示顺序<<<<<',
			buttonAlign : 'center',
			id : 'addWindow',
			closeAction: 'hide',
			width : 720,
			height : 500,
			layout:'fit',
			modal : true,
			items:[
			       gridPanel
			],
			listeners:{
				'beforehide':function(t){
            		editor.stopEditing(false);
				}
			},
	        buttons:[
	                {
	                	text:'更新显示顺序',
	                	handler:function(){
	                		updateDisOrder(column_ds,dsId);
	                		orderWin.hide();
	                	}
	                },{
	                	text:'取消',
	                	handler:function(){
	                		orderWin.hide();
	                	}
	                }
	         ]
		});
		
//		orderWin.on('beforedestroy',function(){
//			Ext.dd.ScrollManager.unregister(gridPanel.getView().getEditorParent());
//		});
		orderWin.show();
		
		var dt = new Ext.dd.DropTarget(gridPanel.container,{
			ddGroup:'GridDD',
			copy:false,
			notifyDrop:function(dd, e, data){
				var rows = data.selections;  
				var index = dd.getDragData(e).rowIndex;  
				sortStore(rows,index,gridPanel);
			}
		});
		Ext.dd.ScrollManager.register(gridPanel.getView().getEditorParent());
	}
	
	//加载ds
	column_ds.load({
		params:{
			dsId:dsId
		},
		callback:function(rs){
			if(rs.length>0){
				for ( var int = 0; int < rs.length; int++) {
					var record = rs[int];
					initOrder.push(record.get("field_id")+','+(int+1)+';');
				}
			}
		}
	});
}
//将booleancolumn转换为Y或N
function formatBoolCol(val){
	if(val && val === true){
		return 'Y';
	}else{
		return 'N';
	}
}
//保存
function updateDisOrder(store,id){
	var params = "";
	if(store&&store.getCount()>0){
		for ( var int = 0; int < store.getCount(); int++) {
			var record = store.getAt(int);
			var newIndex = record.get("field_id")+','+(store.indexOf(record)+1)+';';
			if(initOrder[int]==newIndex){
				continue;
			}
			params += record.get("field_id")+','+(store.indexOf(record)+1)+';';
		}
	}
	 
	//如果params=''未做任何修改，返回
	if(!params){
		return ;
	}

	//field.setValue(index+1);
	Ext.Ajax.request({
		   url:  pathUrl + '/queryTmplMeta/updateUserDsMetaOrder',
		   params:{orderParam : params,dsId:userDsId},
		   method : 'POST',
		   callback: function(success,options,response){
			   var json = Ext.util.JSON.decode(response.responseText);
			   if(json.success){
				   Ext.Msg.alert('提示信息','排序成功');
				   Ext.getCmp('dsMetaGridPanel').store.reload({params:{ds_id:id}});
			   }else{
				   Ext.Msg.alert('提示信息',json.info);
			   }
		   },
		   failure: function(){}
	});
}
//排序
function sortStore(rows,index,gridPanel){
	  if (typeof(index) == "undefined") {  
          return;  
      }  
      var gridIndex = column_ds.indexOf(rows[0]);  
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
          	column_ds.remove(rowData);  
          
          column_ds.insert(index, rowData); 
          selectArray.push( column_ds.indexOf(rowData));
      } 
      gridPanel.getSelectionModel().selectRows(selectArray);
      gridPanel.getView().refresh();//刷新序号    
      gridPanel.getView().focusRow(selectArray[0]);
}