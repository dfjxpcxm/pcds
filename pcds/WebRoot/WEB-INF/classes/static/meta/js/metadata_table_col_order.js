//调整排序所用的字段
var column_ds = new Ext.data.JsonStore({
	url : pathUrl + '/metadata/column/listTableColumns',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['column_id', 'column_name', 'column_desc']
});
var column_cm = [
    new Ext.grid.RowNumberer(),
	{
		header : '字段名',
		dataIndex : 'column_name',
		width:160
	}, {
		header : '字段描述',
		dataIndex : 'column_desc',
		width:160
	}
];

//调整排序
function showOrderWin(){
	var gridPanel = new Ext.grid.GridPanel ({
		columns : column_cm,
		store : column_ds,
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
	var orderWin = new Ext.Window({
		title:'字段排序',
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
		    		  updateDisOrder(column_ds);
		    		  orderWin.destroy();
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
	          sortStore(rows,index,gridPanel);
		}
	});
	Ext.dd.ScrollManager.register(gridPanel.getView().getEditorParent());
	var prt_metadata_id = '';
	
	column_ds.load({
		params:{
			table_id:metadata_id
		},
		callback:function(){
			var index = column_ds.findBy(function(r){
				if(r.get('column_id')== metadata_id){
					return true;
				}else{
					return false;
				}
			});
			 var r =  column_ds.getAt(index);
			 gridPanel.getSelectionModel().selectRow(index);
		     gridPanel.getView().focusRow(index);
		}
	});
}
function updateDisOrder(store){
	var params = "";
	if(store&&store.getCount()>0){
		for ( var int = 0; int < store.getCount(); int++) {
			var record = store.getAt(int);
			params +=record.get("column_id")+','+(store.indexOf(record)+1)+';';
		}
	}
//	var metadata_id = Ext.getCmp('metadata_id').getValue();
	var index = store.findBy(function(r){
		if(r.get('column_id')==metadata_id){
			return true;
		}else{
			return false;
		}
	});
	
	//field.setValue(index+1);
	Ext.Ajax.request({
		   url:  pathUrl + '/metadata/pageField/updateDisOrder',
		   params:{orderParam : params},
		   method : 'POST',
		   callback: function(success,options,response){
			   var json = Ext.util.JSON.decode(response.responseText);
			   if(json.success){
				   Ext.Msg.alert('提示信息','排序成功');
				   pageFieldStore.load();
			   }else{
				   Ext.Msg.alert('提示信息',json.info);
			   }
		   },
		   failure: function(){}
	});
}
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