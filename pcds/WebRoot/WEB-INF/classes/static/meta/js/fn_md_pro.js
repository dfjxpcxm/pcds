/**
 * 
 */
var dim_store = new Ext.data.JsonStore({
	url : pathUrl + '/dimLinkAjax/listDimLinks',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['dim_code', 'dim_name','column_code']
});
var biz_type_store = new Ext.data.JsonStore({
	
	url : pathUrl + '/managerFnMdPro/listBizType',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['col_biz_type_cd', 'col_biz_type_desc'],
	autoLoad :true
});
var column_ds = new Ext.data.JsonStore({
	url : pathUrl + '/managerFnMdPro/fnColumns',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['metadata_id', 'metadata_name', 'component_tab']
});
var column_cm = [
    new Ext.grid.RowNumberer(),
	{
		header : '字段名',
		dataIndex : 'metadata_name',
		width:160
	}, {
		header : '字段描述',
		dataIndex : 'component_tab',
		width:160
	}
];
//控件类型
var component_array = [
    ['textfield','文本框'],
    ['numberfield','数字框'],
    ['textarea','文本域'],
    ['combobox','下拉框'],
    ['moneyfield','金额框'],
    ['datefield','日期框'],
    ['custfield','分配框'],
    ['uxtree','下拉树'],
//    ['uxgrid','下拉表'],
    ['hidden','隐藏域'],
    ['add','增加按钮'],
    ['delete','删除按钮'],
    ['edit','编辑按钮'],
    ['update','更新按钮'],
    ['load','加载按钮'],
    ['import','导入按钮'],
    ['export','导出按钮'],
    ['query','查询按钮']
//    ['apply_up','提交审批'],
//    ['apply_recall','审批撤回'],
//    ['apply_agree','审批通过'],
//    ['apply_back','审批退回']
                       
];
//
Ext.MyField=Ext.extend(Ext.form.TextField ,{
	xtype:"textfield",
	readOnly:true,
	id:Ext.id(),
	constructor: function(config) {
		config = config||{};
		this.rxcfg = {};
		this.rxcfg.id = config.id;
		this.rxcfg.name = config.name;
		this.rxcfg.showIcon = config.showIcon;
		this.rxcfg.anchor = config.anchor||'88%';
		this.rxcfg.readOnly = config.readOnly||true;
		this.rxcfg.hidden = config.hidden||false;
		this.rxcfg.fieldLabel = config.fieldLabel||'显示顺序';
		this.rxcfg.allowBlank= config.allowBlank || true;
		Ext.MyField.superclass.constructor.call(this, config);
    },
	initComponent: function(){
		//var p = this;
		Ext.applyIf(this, {
			id : this.rxcfg.id,
			anchor:this.rxcfg.anchor,
			fieldLabel:this.rxcfg.fieldLabel,
			name:this.rxcfg.name,
			readOnly : this.rxcfg.readOnly,
			hidden : this.rxcfg.hidden,
			allowBlank : this.rxcfg.allowBlank,
			listeners:{
				afterrender : function(c){
					if(this.rxcfg.showIcon){
						var imgPath = pathUrl+'/public/images/icons/change.png';
						var div = Ext.getDom(c.getId()).parentNode;
						var span = document.createElement("span");
						span.style.border = "1px solid #B5B8C8";
						span.style.padding = "1px 1px 1px 1px";
						span.style.margin = "0px 0px 0px 2px";
						span.style.verticalAlign = "MIDDLE";
						span.innerHTML = "<a href='javascript:Ext.getCmp(\""+c.getId()+"\").showMgr()'><img src=\""+imgPath+"\"></a>";
						div.appendChild(span);
					}
				} 
			}
		});
		Ext.MyField.superclass.initComponent.call(this);
	} ,
	showMgr :function(){
		showOrderWin(this); 
	}
});

function showOrderWin(field){
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
		height : 450,
		layout:'fit',
		modal : true,
		items:[
		       gridPanel
		 ],
		buttons:[
		      {
		    	  text:'确定',
		    	  handler:function(){
		    		  updateDisOrder(column_ds,field);
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
	column_ds.load({
		params:{metadata_id:Ext.getCmp('metadata_id').getValue()},
		callback:function(){
			var index = column_ds.findBy(function(r){
				if(r.get('metadata_id')==Ext.getCmp('metadata_id').getValue()){
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
function updateDisOrder(store,field){
	var params = "";
	if(store&&store.getCount()>0){
		for ( var int = 0; int < store.getCount(); int++) {
			var record = store.getAt(int);
			params +=record.get("metadata_id")+','+(store.indexOf(record)+1)+';';
		}
	}
	var metadata_id = Ext.getCmp('metadata_id').getValue();
	var index = store.findBy(function(r){
		if(r.get('metadata_id')==metadata_id){
			return true;
		}else{
			return false;
		}
	});
	field.setValue(index+1);
	Ext.Ajax.request({
		   url:  pathUrl + '/managerFnMdPro/updateDisOrder',
		   params:{orderParam : params},
		   success: function(){},
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