/**
 * 
 */
var dim_store = new Ext.data.JsonStore({
	url : pathUrl + '/dimLinkAjax/listDimLinks',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['dim_code', 'dim_name','column_code']
});
var column_ds = new Ext.data.JsonStore({
	url : pathUrl + '/templManager/getTemplTree',
	root : 'list',
	totalProperty : 'totalCount',
	fields : ['col_name', 'col_desc']
});
var column_cm = [
    new Ext.grid.RowNumberer(),
	{
		header : '字段名',
		dataIndex : 'col_name',
		width:160
	}, {
		header : '字段描述',
		dataIndex : 'col_desc',
		width:160
	}
];

function showOrderWin(id){
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
		    	 if((i+1)>=column_ds.getCount()){
		    		 return;
		    	 }
	    		 sortStore(rows,i+1,gridPanel);
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
		id : 'orderWin',
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
		    		  updateDisOrder(column_ds,Ext.getCmp(id));
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
	orderWin.toFront();
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
		params:{tmpl_id:Ext.getCmp('tmpl_id').getValue()},
		callback:function(){
			var index = column_ds.findBy(function(r){
				if(r.get('col_name')==Ext.getCmp('col_name').getValue()){
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
			params +=record.get("col_name")+','+(store.indexOf(record)+1)+';';
		}
	}
	var col_name = Ext.getCmp('col_name').getValue();
	var index = store.findBy(function(r){
		if(r.get('col_name')==col_name){
			return true;
		}else{
			return false;
		}
	});
	field.setValue(index+1);
	

	Ext.Ajax.request({
		   url:  pathUrl + '/templManager/updateDisOrder',
		   params:{tmpl_id:Ext.getCmp('tmpl_id').getValue(),orderParam : params},
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
 
 
 

 var TemplTreePanel = function(param){
	 TemplTreePanel.superclass.constructor.call(this,{
 				title:'模板',
 				region:'center',
 				contentEl:'center',
 				collapsible: false,
 				id:'templTreePanel',
 				frame:false,
 				loader: new Ext.tree.TreeLoader(),
 				lines:true,
 				bodyStyle:'padding:5px',
 				autoScroll:true,
 				root:getRootNode(param.rootId,param.rootName,expandMetadataTreeNode),
 				tbar:[
 				      {
 				    	  text:'新增',
 				    	  iconCls:'add',
 				    	  handler:function(){
 				    		  addTempl();
 				    	  }
 				      },{
 				    	  text:'删除',
 				    	  iconCls:'delete',
 				    	  handler:function(){
 				    		 delTempl();
 				    	  }
 				      }
 				]
 	});
 };
 function getRootNode(id,text,fn)
 {
 	var root = new Ext.tree.AsyncTreeNode({
 		id:id,
 		text:text,
 		type:'root',
 		children:[{
 			text:'loading',
 			cls: 'x-tree-node-loading',
 			leaf:true
 		}]
 	});

 	if(fn!=undefined)
 		root.on('expand',fn);

 	return root;
 }

 function expandMetadataTreeNode(node)
 {
 	if(node.firstChild.text=='loading'){
 		Ext.Ajax.request({
 			url: pathUrl+'/templManager/getTemplTree',
 			waitMsg:'正在处理，请稍候。。。。。。',
 			method: 'POST',
 			params: {nodeID:node.id,tmpl_id:node.attributes.tmpl_id,prt_col_name:node.attributes.col_name},
 			callback: function (options, success, response) {
 				var json=Ext.util.JSON.decode(response.responseText);
 				var tl=json.list;
 				if(tl)
 				{
 					for(var i=0;i<tl.length;i++){
 						var templ = tl[i];
 						var type_flag  = (templ.type=='templ');
 						var cnode=new Ext.tree.AsyncTreeNode({
 							id:templ.tmpl_id+';'+(type_flag?"root":templ.col_name),
 							text:'['+(type_flag?templ.tmpl_id:templ.col_name)+']'+(type_flag?templ.tmpl_desc:templ.col_desc),
// 							iconCls:getIconCls(tl[i].metadata_cate_code),
 							type:templ.type,
 							tmpl_id:templ.tmpl_id,
 							col_name:type_flag?"root":templ.col_name,
 							children:[{
 								text:'loading',
 								cls: 'x-tree-node-loading',
 								leaf:true
 							}]
 						});
 						cnode.on('expand',expandMetadataTreeNode);
 						node.appendChild(cnode);
 					}
 				}
 				node.firstChild.remove();
 			},

 			failure:function(response, options){
 				Ext.MessageBox.hide();
 				var msg = '出错!';
 				Ext.MessageBox.alert(response.responseText+' '+msg);
 			},  

 			success:function(response, options){
 				Ext.MessageBox.hide();
 			}
 		});
 	}
 }

 Ext.extend(TemplTreePanel,Ext.tree.TreePanel);

 var tmpl_form = {
		xtype : 'form',
		id : 'addForm',
		layout : 'form',
		labelWidth : 85,
		border : false,
		labelAlign : 'left',
		items : [
		 {
			xtype:'hidden',
			name:'type',
      	    id:'type',
			value:'templ'
		 },
		 {
			xtype : 'textfield',
			name : 'tmpl_id',
			id : 'tmpl_id',
			fieldLabel : '模板ID',
			allowBlank : false,
			anchor : '95%'
		},{
			xtype : 'textfield',
			name : 'tmpl_desc',
			id : 'tmpl_desc',
			fieldLabel : '模板描述',
			anchor : '95%'
		},{
	    	xtype : 'radiogroup',
			name : 'layout_cd',
			id : 'layout_cd',
			fieldLabel : '布局类型',
			focusClass :'',
			listeners : {
		    	 change:function(){}
	       		},
			items:[
		       {
		    	xtype:'radio',
		    	boxLabel:"上下布局<img src='"+pathUrl+"/static/meta/img/top-bottom.png'><img>",
		    	id:'up_down',
		    	name:'layout_cd',
		    	checked:true,
		    	inputValue:'01'
		       },{
		    	xtype:'radio',
		    	boxLabel:"左右布局<img src='"+pathUrl+"/static/meta/img/left-right.png'><img>",
		    	id:'left_right',
		    	name:'layout_cd',
		    	inputValue:'02'
		       }
			 ],
				anchor : '95%'
	    },{
	    	xtype : 'radiogroup',
			name : 'is_display_tree_col',
			id : 'is_display_tree_col',
			fieldLabel : '优先展示树表',
			focusClass :'',
			listeners : {
		    	 change:function(){}
	       		},
			items:[
		       {
		    	xtype:'radio',
		    	boxLabel:"是",
		    	id:'Y',
		    	name:'is_display_tree_col',
		    	checked:true,
		    	inputValue:'Y'
		       },{
		    	xtype:'radio',
		    	boxLabel:"否",
		    	id:'N',
		    	name:'is_display_tree_col',
		    	inputValue:'N'
		       }
			 ],
				anchor : '95%'
	    },{
			xtype : 'textarea',
			name : 'sql_expr',
			id : 'sql_expr',
			fieldLabel : 'SQL表达式',
			height:100,
			anchor : '95%'
		}]
	};
 var edit_tmpl_form = {
	 xtype : 'form',
	 id : 'editForm',
	 layout : 'form',
	 labelWidth : 85,
	 border : false,
	 labelAlign : 'left', 
	 reader: new Ext.data.JsonReader({
         root: 'list',
         totalProperty: 'totalCount'
     }, [
         {name: 'type' },
         {name: 'tmpl_id'},
         {name: 'tmpl_desc' },
         {name: 'layout_cd'},
         {name: 'is_display_tree_col'},
         {name: 'sql_expr'}
     ]),
	 items : [
	          {
	        	  xtype:'hidden',
	        	  name:'type',
	        	  id:'type_',
	        	  value:'templ'
	          },
	          {
	        	  xtype : 'textfield',
	        	  name : 'tmpl_id',
	        	  id : 'tmpl_id',
	        	  fieldLabel : '模板ID',
	        	  readOnly:true,
	        	  allowBlank : false,
	        	  anchor : '95%'
	          },{
	        	  xtype : 'textfield',
	        	  name : 'tmpl_desc',
	        	  id : 'tmpl_desc',
	        	  fieldLabel : '模板描述',
	        	  anchor : '95%'
	          },{
	        	  xtype : 'radiogroup',
	        	  name : 'layout_cd',
	        	  id : 'layout_cd',
	        	  fieldLabel : '布局类型',
	        	  focusClass :'',
	        	  listeners : {
	        		  change:function(){}
	        	  },
	        	  items:[
        	         {
        	        	 xtype:'radio',
        	        	 boxLabel:"上下布局<img src='"+pathUrl+"/static/meta/img/top-bottom.png'><img>",
        	        	 name:'layout_cd',
        	        	 checked:true,
        	        	 inputValue:'01'
        	         },{
        	        	 xtype:'radio',
        	        	 boxLabel:"左右布局<img src='"+pathUrl+"/static/meta/img/left-right.png'><img>",
        	        	 name:'layout_cd',
        	        	 inputValue:'02'
        	         }
    	         ],
    	         anchor : '95%'
	          },{
	        	  xtype : 'radiogroup',
	        	  name : 'is_display_tree_col',
	        	  id : 'is_display_tree_col',
	        	  fieldLabel : '优先展示树表',
	        	  focusClass :'',
	        	  listeners : {
	        		  change:function(){}
	        	  },
	        	  items:[
	        	         {
	        	        	 xtype:'radio',
	        	        	 boxLabel:"是",
	        	        	 id:'Y',
	        	        	 name:'is_display_tree_col',
	        	        	 checked:true,
	        	        	 inputValue:'Y'
	        	         },{
	        	        	 xtype:'radio',
	        	        	 boxLabel:"否",
	        	        	 id:'N',
	        	        	 name:'is_display_tree_col',
	        	        	 inputValue:'N'
	        	         }
	        	         ],
	        	         anchor : '95%'
	          },{
	        	  xtype : 'textarea',
	        	  name : 'sql_expr',
	        	  id : 'sql_expr_',
	        	  fieldLabel : 'SQL表达式',
	        	  height:100,
	        	  anchor : '95%'
	          }]
 };
 var col_form = {
	 xtype : 'form',
	 id : 'addForm',
	 layout : 'form',
	 labelWidth : 85,
	 border : false,
	 labelAlign : 'left',
	 items : [
          {
        	  xtype:'hidden',
        	  name:'type',
        	  id:'type',
        	  value:'col'
          },
          {
        	  xtype : 'textfield',
        	  name : 'tmpl_id',
        	  id : 'tmpl_id',
        	  fieldLabel : '模板ID',
        	  readOnly: true,
        	  anchor : '95%'
          },{
        	  xtype : 'textfield',
        	  name : 'prt_col_name',
        	  id : 'prt_col_name',
        	  readOnly: true,
        	  fieldLabel : '父级字段名称',
        	  value:'root',
        	  anchor : '95%'
          },{
        	  xtype : 'textfield',
        	  name : 'col_name',
        	  id : 'col_name',
        	  fieldLabel : '字段名称',
        	  anchor : '95%'
          },{
        	  xtype : 'textfield',
        	  name : 'col_desc',
        	  id : 'col_desc',
        	  fieldLabel : '字段描述',
        	  anchor : '95%'
          },{
        	  xtype : 'radiogroup',
        	  name : 'data_type_cd',
        	  id : 'data_type_cd',
        	  fieldLabel : '数据类型',
        	  value:'01',
        	  listeners : {
        		  change:function(){}
        	  },
        	  items:[
        	         {
        	        	 xtype:'radio',
        	        	 boxLabel:"文本",
        	        	 name:'data_type_cd',
        	        	 inputValue:'01'
        	         },{
        	        	 xtype:'radio',
        	        	 boxLabel:"数字",
        	        	 name:'data_type_cd',
        	        	 inputValue:'02'
        	         },{
        	        	 xtype:'radio',
        	        	 boxLabel:"百分比(%)",
        	        	 name:'data_type_cd',
        	        	 inputValue:'03'
        	         }
        	         ],
        	         anchor : '95%'
          	},{
        	  xtype : 'radiogroup',
        	  name : 'is_query_cond',
        	  id : 'is_query_cond',
        	  fieldLabel : '是否查询条件',
        	  value:'N',
        	  items:[
        	         {
        	        	 xtype:'radio',
        	        	 boxLabel:"是",
        	        	 name:'is_query_cond',
        	        	 inputValue:'Y'
        	         },{
        	        	 xtype:'radio',
        	        	 boxLabel:"否",
        	        	 name:'is_query_cond',
        	        	 inputValue:'N'
        	         }
        	         ],
        	         anchor : '95%'
          }, {
        	  xtype : 'radiogroup',
        	  name : 'col_type_cd',
        	  id : 'col_type_cd',
        	  value:'N',
        	  fieldLabel : '是否目录',
        	  items:[
        	         {
        	        	 xtype:'radio',
        	        	 boxLabel:"是",
        	        	 name:'col_type_cd',
        	        	 inputValue:'Y'
        	         },{
        	        	 xtype:'radio',
        	        	 boxLabel:"否",
        	        	 name:'col_type_cd',
        	        	 inputValue:'N'
        	         }
        	         ],
        	         anchor : '95%'
          }, {
				xtype : 'combo',
				fieldLabel : '维度代码',
				id:'dimCd',
				displayField : 'dim_name',
				valueField : 'dim_code',
				editable : true,
				hiddenName : 'dim_cd',
				mode : 'local',
				triggerAction : 'all',
				store:dim_store,
				anchor : '95%'
			},{
	        	  xtype : 'numberfield',
	        	  name : 'display_order',
	        	  id : 'display_order',
	        	  fieldLabel : '显示顺序',
	        	  anchor : '95%'
	          }
          ]
 };
 var edit_col_form = {
		 xtype : 'form',
		 id : 'editForm',
		 layout : 'form',
		 labelWidth : 85,
		 border : false,
		 labelAlign : 'left',
		 reader: new Ext.data.JsonReader({
	            root: 'list',
	            totalProperty: 'totalCount'
	        }, [
	            {name: 'type' },
	            {name: 'tmpl_id'},
	            {name: 'prt_col_name' },
	            {name: 'col_name'},
	            {name: 'col_desc'},
	            {name: 'data_type_cd'},
	            {name: 'is_query_cond'},
	            {name: 'col_type_cd'},
	            {name: 'display_order'},
	            {name: 'dim_cd'}
	        ]),
		 items : [
		          {
		        	  xtype:'hidden',
		        	  name:'type',
		        	  id:'type',
		        	  value:'col'
		          },
		          {
		        	  xtype : 'textfield',
		        	  name : 'tmpl_id',
		        	  id : 'tmpl_id',
		        	  fieldLabel : '模板ID',
		        	  readOnly: true,
		        	  anchor : '95%'
		          },{
		        	  xtype : 'textfield',
		        	  name : 'prt_col_name',
		        	  id : 'prt_col_name',
		        	  readOnly: true,
		        	  fieldLabel : '父级字段名称',
		        	  anchor : '95%'
		          },{
		        	  xtype : 'textfield',
		        	  name : 'col_name',
		        	  id : 'col_name',
		        	  fieldLabel : '字段名称',
		        	  readOnly: true,
		        	  anchor : '95%'
		          },{
		        	  xtype : 'textfield',
		        	  name : 'col_desc',
		        	  id : 'col_desc',
		        	  fieldLabel : '字段描述',
		        	  anchor : '95%'
		          },{
		        	  xtype : 'radiogroup',
		        	  name : 'data_type_cd',
		        	  id : 'data_type_cd',
		        	  fieldLabel : '数据类型',
		        	  value:'01',
		        	  listeners : {
		        		  change:function(){}
		        	  },
		        	  items:[
		        	         {
		        	        	 xtype:'radio',
		        	        	 boxLabel:"文本",
		        	        	 name:'data_type_cd',
		        	        	 inputValue:'01'
		        	         },{
		        	        	 xtype:'radio',
		        	        	 boxLabel:"数字",
		        	        	 name:'data_type_cd',
		        	        	 inputValue:'02'
		        	         },{
		        	        	 xtype:'radio',
		        	        	 boxLabel:"百分比(%)",
		        	        	 name:'data_type_cd',
		        	        	 inputValue:'03'
		        	         }
		        	         ],
		        	         anchor : '95%'
		          },{
		        	  xtype : 'radiogroup',
		        	  name : 'is_query_cond',
		        	  id : 'is_query_cond',
		        	  fieldLabel : '是否查询条件',
		        	  value:'N',
		        	  items:[
		        	         {
		        	        	 xtype:'radio',
		        	        	 boxLabel:"是",
		        	        	 name:'is_query_cond',
		        	        	 inputValue:'Y'
		        	         },{
		        	        	 xtype:'radio',
		        	        	 boxLabel:"否",
		        	        	 name:'is_query_cond',
		        	        	 inputValue:'N'
		        	         }
		        	         ],
		        	         anchor : '95%'
		          }, {
		        	  xtype : 'radiogroup',
		        	  name : 'col_type_cd',
		        	  id : 'col_type_cd',
		        	  fieldLabel : '是否目录',
		        	  value:'N',
		        	  items:[
		        	         {
		        	        	 xtype:'radio',
		        	        	 boxLabel:"是",
		        	        	 name:'col_type_cd',
		        	        	 inputValue:'Y'
		        	         },{
		        	        	 xtype:'radio',
		        	        	 boxLabel:"否",
		        	        	 name:'col_type_cd',
		        	        	 checked:true,
		        	        	 inputValue:'N'
		        	         }
		        	         ],
		        	         anchor : '95%'
		          }, {
		        	  xtype : 'combo',
		        	  fieldLabel : '维度代码',
		        	  id:'dimCd',
		        	  displayField : 'dim_name',
		        	  valueField : 'dim_code',
		        	  editable : true,
		        	  hiddenName : 'dim_cd',
		        	  mode : 'local',
		        	  triggerAction : 'all',
		        	  store:dim_store,
		        	  anchor : '95%'
		          },{
		        	  xtype : 'numberfield',
		        	  name : 'display_order',
		        	  id : 'display_order',
		        	  fieldLabel : '显示顺序',
		        	  anchor : '90%',
		        		listeners:{
		    				afterrender : function(c){
	    						var imgPath = pathUrl+'/public/images/icons/change.png';
	    						var div = Ext.getDom(c.getId()).parentNode;
	    						var span = document.createElement("span");
	    						span.style.border = "1px solid #B5B8C8";
	    						span.style.padding = "1px 1px 1px 1px";
	    						span.style.margin = "0px 0px 0px 2px";
	    						span.style.verticalAlign = "MIDDLE";
	    						span.innerHTML = "<img onclick='javascript:showOrderWin(\""+c.getId()+"\")' src=\""+imgPath+"\">";
	    						div.appendChild(span);
		    				} 
		    			}
		          }]
 };
 
 //添加模板
 function addWindow (tmpl_form){ 
  var AddWindow = Ext.extend(Ext.Window, {
		title : '添加模板',
		buttonAlign : 'center',
		id : 'addWindow',
		width : 400,
		height : 400,
		initComponent : function() {
			Ext.applyIf(this, {
				modal : true,
				split : false,
				layout : 'fit',
				listeners : {
					close : function() {
						Ext.getCmp("addWindow").destroy();
					}
				},
				bodyStyle : 'padding: 10px',
				items : [tmpl_form],
				buttons : [{
					text : '保存',
					id : 'save_btn',
					handler : function() {
						var formPanel = Ext.getCmp("addForm");
						if (formPanel.form.isValid()) {
							formPanel.form.submit({
								url : pathUrl + '/templManager/addTmpl',
								waitMsg : '正在处理,请稍后......',
								success : function(form, action) {
									Ext.Msg.alert("消息", action.result.info);
									if (action.result.success) {
										var node = Ext.getCmp('templTreePanel').getSelectionModel().getSelectedNode();
										var type_flag = Ext.getCmp('type').getValue()=='col';
										if(type_flag){
											var tmpl = {tmpl_id:Ext.getCmp('tmpl_id').getValue(),
													col_name:Ext.getCmp('col_name').getValue(),
													col_desc:Ext.getCmp('col_desc').getValue()};
											addNode(node,tmpl,2);
										}else{
											var tmpl = {tmpl_id:Ext.getCmp('tmpl_id').getValue(),
													tmpl_desc:Ext.getCmp('tmpl_desc').getValue()};
											addNode(node,tmpl,1);
										}
										Ext.getCmp('addWindow').destroy();
									}
								},
								failure : function(form, action) {
									Ext.Msg.alert("消息", action.result.info);
								}
							});
						} else {
							Ext.Msg.alert("提示信息", "请输入完整信息");
						}
					}
				}, {
					text : '取消',
					handler : function() {
						Ext.getCmp("addWindow").destroy();
					}
				}]
			});
			AddWindow.superclass.initComponent.call(this);
		}
	});
  return AddWindow;
 };

 
 function addNode(node,tmpl,type){
	 if(type==1){
		 var cnode=new Ext.tree.AsyncTreeNode({
				id:tmpl.tmpl_id+';root',
				text:'['+tmpl.tmpl_id+']'+tmpl.tmpl_desc,
//				iconCls:getIconCls(tl[i].metadata_cate_code),
				type:'templ',
				tmpl_id:tmpl.tmpl_id,
				col_name:'root',
				children:[{
					text:'loading',
					cls: 'x-tree-node-loading',
					leaf:true
				}]
			}); 
	 }else{
		 var cnode=new Ext.tree.AsyncTreeNode({
				id:tmpl.tmpl_id+';'+tmpl.col_name,
				text:'['+tmpl.col_name+']'+tmpl.col_desc,
//				iconCls:getIconCls(tl[i].metadata_cate_code),
				type:'col',
				tmpl_id:tmpl.tmpl_id,
				col_name:tmpl.col_name,
				children:[{
					text:'loading',
					cls: 'x-tree-node-loading',
					leaf:true
				}]
			}); 
	 }
	 cnode.on('expand',expandMetadataTreeNode);
	 node.appendChild(cnode);
 }
