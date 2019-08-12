var val_array;
var focus_com_id;
var self_table;

var tab_ds = new Ext.data.JsonStore({
	url : pathUrl + '/checkRule/listTargetTab',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['metadata_id','metadata_name']
});
//函数列表
var functions_ds = new Ext.data.JsonStore({
	url : pathUrl + '/checkRule/listFunctions',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['content','desc'],
	autoLoad:true
});
var self_col_ds = new Ext.data.JsonStore({
	url : pathUrl + '/checkRule/listColumns',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['metadata_id','metadata_name']
});
var self_col_cm = [
{
	header : '本表字段',
	dataIndex : 'metadata_name',
	width:150
}];
var tab_cm = [
{
	header : '表名',
	dataIndex : 'metadata_name',
	width:150
}];
//函数列表
var functions_cm = [
{
	header : '函数',
	dataIndex : 'content',
	width:250
},
{
	header : '描述',
	dataIndex : 'desc',
	width:350
}];
var tar_col_ds = new Ext.data.JsonStore({
	url : pathUrl + '/checkRule/listColumns',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['metadata_id','metadata_name']
});
//校验规则column
var tar_col_cm = [
{
	header : '目标表字段',
	dataIndex : 'metadata_name',
	width:150
}];


var last_panel = {
		xtype:'fieldset',
		id:'last_panel',
		title:'校验公式配置',
		layout:'column',
		anchor:'97%',
		items:[{
			xtype:'panel',
			layout:'form',
			columnWidth:0.62,
			labelWidth:80,
			labelAlign :'top',
			defaults:{
				anchor : '96%'
			},
			items:[
			       {
			    	   xtype : 'textarea',
			    	   name : 'chk_formula',
			    	   id : 'chk_formula',
			    	   fieldLabel : '校验公式',
			    	   autoScroll : true,
			    	   height : 220,
			    	   value:''
			       },
			       {
			    	   xtype : 'textarea',
			    	   name : 'chk_formula_desc',
			    	   id : 'chk_formula_desc',
			    	   fieldLabel : '校验公式描述',
			    	   autoScroll : true,
			    	   height : 100
			       }
			       ]
		},{
			xtype:'panel',
			layout:'form',
			columnWidth:0.38,
			items:[{
				xtype:'fieldset',
				id:'gen_btns',
				title:'通用变量',
				layout:'form',
				items:[{
					xtype:'buttongroup', 
					columns: 4,
					defaults:{
						scale: 'small',
						style: 'margin:1px'
					},
					items:[
					       {
					    	   text:'本身',
					    	   handler:function(){
					    		   RangeInsert(Ext.getCmp('chk_formula'),'[@self]');
					    	   }
					       },{
					    	   text:'空值',
					    	   handler:function(){
					    		   //如果是java校验方式02插入nil
					    		   if(Ext.getCmp('chkMethodCode').getValue()=='02'){
					    			   RangeInsert(Ext.getCmp('chk_formula'),'nil');
					    		   }else{
					    			   RangeInsert(Ext.getCmp('chk_formula'),'null');
					    		   }
					    	   }
					       },{
					    	   text:'机构号',
					    	   handler:function(){
					    		   RangeInsert(Ext.getCmp('chk_formula'),'[@bank_org_id]');
					    	   }
					       },{
					    	   text:'普通变量',
					    	   handler:function(){
					    		   RangeInsert(Ext.getCmp('chk_formula'),'[@...]');
					    	   }
					       }]
				},{
					xtype:'buttongroup', 
					columns: 4,
					defaults:{
						scale: 'small',
						style: 'margin:0px 1px 1px 1px'
					},
					items:[{
					    	   text:'字符变量',
					    	   handler:function(){
					    		   RangeInsert(Ext.getCmp('chk_formula'),'[C#...]');
					    	   }
					       },{
					    	   text:'数字变量',
					    	   handler:function(){
					    		   RangeInsert(Ext.getCmp('chk_formula'),'[N#...]');
					    	   }
					       },{
					    	   text:'日期变量',
					    	   handler:function(){
					    		   RangeInsert(Ext.getCmp('chk_formula'),'[D#...]');
					    	   }
					       },{
					    	   text:'Bool变量',
					    	   handler:function(){
					    		   RangeInsert(Ext.getCmp('chk_formula'),'[B#...]');
					    	   }
					       }]
				}]
			},{
				xtype:'fieldset',
				title:'选择函数',
				layout:'form',
				id:'chk_func',
				height:251,
				items : [
					     {
					    	 xtype:'chkListPanel',
					    	 id:'chkListPanel'
					     } 
					 ]
				}
			]
		}
		]
};


var ruleGrid = {
		xtype : 'grid',
		region:'center',
		columns : check_rule_cm,
		store : check_rule_ds,
		loadMask:true,
		frame:true,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		viewConfig : {
			forceFit : true
		},
		listeners : {
			rowclick : function(grid, rowIndex, e) {
				var record = check_rule_ds.getAt(rowIndex);
				Ext.getCmp('chkPriority').setValue(record.get('chk_priority'));
				Ext.getCmp('chk_formula').setValue(record.get('chk_formula'));
				Ext.getCmp('chk_formula_desc').setValue(record.get('chk_formula_desc'));
				Ext.getCmp('chk_failure_tip').setValue(record.get('chk_failure_tip'));
				Ext.getCmp('chk_rule_code').setValue(record.get('chk_rule_code'));
				Ext.getCmp('chk_rule_name').setValue(record.get('chk_rule_name'));
				rule_type_flag = record.get('rule_type_flag');
				if(rule_type_flag!='1'){
					Ext.getCmp('chk_formula').setDisabled(true);
					Ext.getCmp('chk_formula_desc').setDisabled(true);
					Ext.getCmp('chk_failure_tip').setDisabled(true);
				}else{
					Ext.getCmp('chk_formula').setDisabled(false);
					Ext.getCmp('chk_formula_desc').setDisabled(false);
					Ext.getCmp('chk_failure_tip').setDisabled(false);
				}
			}
		  },tbar : ["关键字：", {
				xtype : 'textfield',
				name : 'searchKey',
				id:'searchKey',
				listeners : {
					specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							check_rule_ds.reload();
						}
					}
				},
				width : 200 
			}, {
				xtype : 'button',
				iconCls : 'search',
				handler : function() {
					check_rule_ds.reload();
				}
			}]
		};

var col_fs = {
		   xtype:'fieldset',
		   id:'col_fs',
		   title:'校验公式配置',
		   layout:'column',
		   anchor:'97%',
		   items:[
	          {
	        	  columnWidth:'.20',
	        	  frame:true,
	        	  border:true,
	        	  layout:'fit',
	        	  items:[
	        	     {
						xtype : 'grid',
						id:'self',
						width : 150,
						height : 350,
						columns : self_col_cm,
						store : self_col_ds,
						listeners:{
	        	    	 	rowdblclick:function(){
	        	    	 		var r = this.getSelectionModel().getSelected();
	        	    	 		RangeInsert(Ext.getCmp('chk_formula'),'[@'+r.get('metadata_name')+']');
	        	    	 	}
	        	     	},
						sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						})
	        	     }
	        	  ]
	          },{
	        	  columnWidth:'.50',
	        	  frame:true,
	        	  border:true,
	        	  layout:'form',
	        	  items:[
	        	     {
	        	    	 xtype:'panel',
	        	    	 layout:'form',
	        	    	 labelAlign :'top',
	        	    	 items:[
							{
								xtype : 'textarea',
								name : 'chk_formula',
								id : 'chk_formula',
								fieldLabel : '校验公式',
								autoScroll : true,
								height : 205,
								anchor : '97%'
							}, {
        	    				xtype : 'textarea',
        	    				name : 'chk_formula_desc',
        	    				id : 'chk_formula_desc',
        	    				fieldLabel : '校验公式描述',
        	    				autoScroll : true,
        	    				height : 100,
        	    				value:'',
								anchor : '97%'
        	    			}
	        	    	  ]
	        	     }
	        	  ]
	        	  
	          },{
	  			xtype:'panel',
				layout:'form',
				columnWidth:0.30,
				items:[{
					xtype:'fieldset',
					title:'选择函数',
					layout:'form',
					id:'chk_func',
					height:350,
					items : [
						     {
						    	 xtype:'chkListPanel',
						    	 id:'chkListPanel'
						     } 
						 ]
					}
				]
			}
		   ]
	   };

var tab_fs = {
		   xtype:'fieldset',
		   id:'tab_fs',
		   title:"校验公式配置(说明：多个字段用','隔开)",
		   layout:'column',
		   anchor:'95%',
		   items:[
	          {
	        	  columnWidth:'.18',
	        	  frame:true,
	        	  border:true,
	        	  layout:'fit',
	        	  items:[
	        	     {
						xtype : 'grid',
						id:'self',
						width : 150,
						height : 460,
						columns : self_col_cm,
						store : self_col_ds,
						listeners:{
	        	    	 	rowdblclick:function(){
	        	    	 		setColumn(this);
	        	     		}
	        	     	},
						sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						})
	        	     }
	        	  ]
	          },{
	        	  columnWidth:'.64',
	        	  frame:true,
	        	  border:true,
	        	  layout:'form',
	        	  items:[
    	    		  {
    	    			 xtype:'panel',
	        	    	 layout:'column',
	        	    	 id:'tab_areas',
	        	    	 labelAlign :'top',
	        	    	 items:[
	        	    	    {
	        	    	    	columnWidth:'.425',
	        	    	    	layout:'form',
	        	    	    	items:[
	        	    	    	   {
	        	    	    		   xtype:'textfield',
	        	    	    		   fieldLabel:'本表',
	        	    	    		   id:'self_tab',
	        	    	    		   readOnly:true,
	        	    	    		   anchor : '95%'
	        	    	    	   },
	        	    	    	   {
	        	    	    		   xtype:'textarea',
	        	    	    		   fieldLabel:'分组字段',
	        	    	    		   id:'tab1_col_1',
	        	    	    		   height:70,
	        	    	    		   listeners:{
	        	    	    		   		focus:function(){
	        	    	    		   			focus_com_id = this.id;
	        	    	    		   			setStyle(this.id);
	        	    	    	  			}
	        	    	    	   		},
	        	    	    		   anchor:'95%'
	        	    	    	   },
	        	    	    	   {
	        	    	    		   xtype:'textarea',
	        	    	    		   fieldLabel:'汇总字段',
	        	    	    		   id:'tab1_col_2',
	        	    	    		   height:70,
	        	    	    		   listeners:{
		        	    	    		   focus:function(){
	        	    	    		   			focus_com_id = this.id;
	        	    	    		   			setStyle(this.id);
	        	    	    	  			}
	        	    	    	   		},
	        	    	    		   anchor:'95%'
	        	    	    	   }
	        	    	    	]
	        	    	    },
	        	    	    {
	        	    	    	columnWidth:'.15',
	        	    	    	layout:'form',
	        	    	    	style:'margin:170 0 0 0px',
	        	    	    	items:[
    	    	    		      {
    	    	    		    	xtype:'combo',
  									id : 'logic_char',
  									allowBlank : false,
  									displayField : 'text',
  									valueField : 'id',
  									editable : false,
  									mode : 'local',
  									triggerAction : 'all',
  									store : new Ext.data.SimpleStore({
  										fields : ['id', 'text'],
  										data:[['>','大于'],['<','小于'],['=','等于'],
  											    ['>=','大于等于'],['<=','小于等于']]
  									}),
  									value:'=',
  									anchor : '95%'
    	    	    		      }
	        	    	    	]
	        	    	    },
	        	    	    {
	        	    	    	columnWidth:'.425',
	        	    	    	layout:'form',
	        	    	    	items:[ 
	        	    	    	   {
	        	    	    		   xtype:'combo',
	        	    	    		   fieldLabel:'目标表',
	        	    	    		   id : 'target_tab',
      									displayField : 'metadata_name',
      									valueField : 'metadata_id',
      									editable : false,
      									mode : 'local',
      									triggerAction : 'all',
      									store : tab_ds,
      									listeners:{
	        	    	    		   		select:function(){
	        	    	    					var width = Ext.getCmp("addWindow").getWidth();
//	        	    	    		   			addColumn(width);
	        	    	    		   			var metadata_id = this.getValue();
	        	    	    		   			tar_col_ds.load({params:{metadata_id:metadata_id}});
	        	    	    	   			}
	        	    	    	   		},
      									anchor : '95%'
	        	    	    	   },
	        	    	    	   {
	        	    	    		   xtype:'textarea',
	        	    	    		   fieldLabel:'分组字段',
	        	    	    		   id:'tab2_col_1',
	        	    	    		   height:70,
	        	    	    		   listeners:{
	        	    	    		   		focus:function(){
	        	    	    		   			focus_com_id = this.id;
	        	    	    		   			setStyle(this.id);
	        	    	    	  			}
	        	    	    	   		},
	        	    	    		   anchor:'95%'
	        	    	    	   },
	        	    	    	   {
	        	    	    		   xtype:'textarea',
	        	    	    		   fieldLabel:'汇总字段',
	        	    	    		   id:'tab2_col_2',
	        	    	    		   height:70,
	        	    	    		   listeners:{
			        	    	    		focus:function(){
	        	    	    		   			focus_com_id = this.id;
	        	    	    		   			setStyle(this.id);
	        	    	    	  			}
	        	    	    	   		},
	        	    	    		   anchor:'95%'
	        	    	    	   }
	        	    	    	]
	        	    	    }
	        	    	 ]
	        	     },
	        	     {
	        	    	 xtype:'panel',
	        	    	 layout:'form',
	        	    	 labelAlign :'top',
	        	    	 items:[ 
		        	    	{
								xtype : 'textarea',
								name : 'chk_formula',
								id : 'chk_formula',
								fieldLabel : '校验公式',
								autoScroll : true,
								height : 100,
								readOnly:true,
//								hidden:true,
								anchor : '97%'
							},{
	     	    				xtype : 'textarea',
	    	    				name : 'chk_formula_desc',
	    	    				id : 'chk_formula_desc',
	    	    				fieldLabel : '校验公式描述',
	    	    				autoScroll : true,
	    	    				height : 100,
	    	    				value:'',
								anchor : '97%'
	    	    			}
	        	    	  ]
	        	     }
	        	  ]
	        	  
	          },{
	        	  columnWidth:'.18',
	        	  frame:true,
	        	  border:true,
	        	  layout:'fit',
	        	  items:[
	        	     {
						xtype : 'grid',
						id:'target',
						width : 150,
						height : 460,
						columns : tar_col_cm,
						store : tar_col_ds,
						listeners:{
	        	    	 	rowdblclick:function(){
	        	    	 		setColumn(this);
	        	     		}
		        	     },
						sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						})
	        	     }
	        	  ]
	          }
		   ]
	   };


AddWindowTab = Ext.extend(Ext.Window, {
	title : '添加校验规则',
	buttonAlign : 'center',
	id : 'addWindow',
	maximized:true, 
	constructor: function(config) {
		config = config||{};
		this.rxcfg = {};
		this.rxcfg.doUrl = config.doUrl;
        
        AddWindowTab.superclass.constructor.call(this, config);
    },
	initComponent : function() {
		Ext.applyIf(this, {
			modal : true,
			split : false,
			layout : 'border',
			listeners : {
				close : function() {
					Ext.getCmp("addWindow").destroy();
				},
				activate:function(){
					var fun_win = Ext.getCmp('fun_win');
					var col_win = Ext.getCmp('col_win');
					if(fun_win){
						fun_win.setZIndex(9999999);
					}
					if(col_win){
						col_win.setZIndex(9999999);
					}
				},
				destroy:function(){
					var fun_win = Ext.getCmp('fun_win');
				    if(fun_win){
						fun_win.destroy();
					}
				}
			},
			bodyStyle : 'padding: 10px',
			items : [
			    ruleGrid,  
			    {
				xtype : 'form',
				id : 'addForm',
				layout : 'form',
				region:'west',
				width:760,
				autoScroll:true,
				labelWidth : 85,
				border : true,
				labelAlign : 'left',
				buttonAlign : 'center',
				frame:true,
				url : this.rxcfg.doUrl ,
				items : [
				     {
				    	 xtype:'hidden',
				    	 id:'metadata_id',
				    	 name:'metadata_id'
				     },
				     {
				    	 xtype:'hidden',
				    	 id:'chk_rule_code',
				    	 name:'chk_rule_code'
				     }, {
				    	 xtype:'hidden',
				    	 id:'old_chk_rule_code',
				    	 name:'old_chk_rule_code'
				     }, {
				    	xtype : 'radiogroup',
						name : 'type_rg',
						id : 'type_rg',
						fieldLabel : '校验类型',
						focusClass :'',
						listeners : {
					    	 change:function(){
					    		 		if(resetStatus=='0'){
					    		 			resetStatus='1';
					    		 		}else{
						    		 		Ext.getCmp('chk_failure_tip').reset();
						    		 		Ext.getCmp('chk_failure_tip').setDisabled(false);
						    		 		Ext.getCmp('chk_rule_name').reset();
					    		 		}
				    	 				var type_code = getTypeCode();
				    	 				//校验方法
				 						var checkMethodCode = Ext.getCmp('chkMethodCode'); 
				 						
				 						if(type_code=='00'){ //00,通用
				 							Ext.getCmp('addForm').remove('col_fs');
				 							Ext.getCmp('addForm').remove("last_panel");
				 							Ext.getCmp('addForm').insert(6,last_panel);
				 							Ext.getCmp('addForm').doLayout();
				 							if(record){
				 								var rule_type_flag = record.get('rule_type_flag');
				 								if(rule_type_flag=='0'){
				 									Ext.getCmp('chk_formula').setDisabled(true);
				 									Ext.getCmp('chk_formula_desc').setDisabled(true);
				 									Ext.getCmp('chk_failure_tip').setDisabled(true);
				 								}
				 								Ext.getCmp('chk_formula').setValue(record.get('chk_formula'));
				 								Ext.getCmp('chk_formula_desc').setValue(record.get('chk_formula_desc'));
				 								record = null;
				 							}
				 							
				 							//修改校验方法下拉框
				 							checkMethodCode.getStore().removeAll();
					 						checkMethodCode.getStore().loadData(cell_array,false);
					 						checkMethodCode.setValue(cell_array[0][0]);
					 						
				 						}else if(type_code=='01'){ //01,列间
				 							focus_com_id = 'chk_formula';
				 							Ext.getCmp('addForm').remove("tab_fs");
				 							Ext.getCmp('addForm').remove("last_panel");
				 							Ext.getCmp('addForm').insert(6,col_fs);
				 							Ext.getCmp('addForm').doLayout();
				 							if(record){
				 								var rule_type_flag = record.get('rule_type_flag');
				 								if(rule_type_flag=='0'){
				 									Ext.getCmp('chk_formula').setDisabled(true);
				 									Ext.getCmp('chk_formula_desc').setDisabled(true);
				 									Ext.getCmp('chk_failure_tip').setDisabled(true);
				 								}
				 								Ext.getCmp('chk_formula').setValue(record.get('chk_formula'));
				 								Ext.getCmp('chk_formula_desc').setValue(record.get('chk_formula_desc'));
				 								record = null;
				 							}
				 							//修改校验方法下拉框
				 							checkMethodCode.getStore().removeAll();
					 						checkMethodCode.getStore().loadData(col_array,false);
					 						checkMethodCode.setValue(col_array[0][0]);
					 						
				 						}else if(type_code=='02'){ //02,表间
				 							Ext.getCmp('addForm').remove('col_fs');
				 							Ext.getCmp('addForm').remove("last_panel");
				 							Ext.getCmp('addForm').insert(6,last_panel);
				 							Ext.getCmp('addForm').doLayout();
				 							if(record){
				 								var rule_type_flag = record.get('rule_type_flag');
				 								if(rule_type_flag=='0'){
				 									Ext.getCmp('chk_formula').setDisabled(true);
				 									Ext.getCmp('chk_formula_desc').setDisabled(true);
				 									Ext.getCmp('chk_failure_tip').setDisabled(true);
				 								}
				 								Ext.getCmp('chk_formula').setValue(record.get('chk_formula'));
				 								Ext.getCmp('chk_formula_desc').setValue(record.get('chk_formula_desc'));
				 								record = null;
				 							}
				 							//修改校验方法下拉框
				 							checkMethodCode.getStore().removeAll();
					 						checkMethodCode.getStore().loadData(tab_col_array,false);
					 						checkMethodCode.setValue(tab_col_array[0][0]);
					 						Ext.getCmp('gen_btns').setVisible(false);
					 						Ext.getCmp('chk_func').setHeight(360);
				 						}
				 						check_rule_ds.reload();
//										Ext.getCmp('gridSelector').reset();
				       			}
				       		},
						items:[
					       {
					    	xtype:'radio',
					    	boxLabel:'通用校验',
					    	id:'general',
					    	name:'check_type_code',
					    	inputValue:'00'
					       },{
					    	xtype:'radio',
					    	boxLabel:'列校验',
					    	id:'col',
					    	name:'check_type_code',
					    	inputValue:'01'
					       },{
					    	xtype:'radio',
					    	boxLabel:'表间校验',
					    	id:'tab',
					    	name:'check_type_code',
					    	inputValue:'02'
					       }/*,{
					    	xtype:'radio',
					    	boxLabel:'行间校验',
					    	id:'cell',
					    	name:'check_type_code',
					    	inputValue:'00'
					       }*/
						 ],
						width : 400
				    },
				    {
					   layout:'column',
					   items:[
							{
								columnWidth:'.3',
								layout:'form',
								items:[{
									xtype : 'combo',
									name : 'chk_method_code',
									id : 'chkMethodCode',
									fieldLabel : '校验方式',
									allowBlank : false,
									displayField : 'text',
									valueField : 'id',
									editable : false,
									hiddenName : 'chk_method_code',
									mode : 'local',
									triggerAction : 'all',
									store : new Ext.data.SimpleStore({
										fields : ['id', 'text'],
										data : []
									}),
									listeners:{
										select:function(t,nval,oval){
					 						check_rule_ds.reload();
					 						var chkPanel = Ext.getCmp('chkListPanel');
					 						if(chkPanel){
					 							chkPanel.refreshNode();
					 						}
									}},
									anchor : '90%'
								}]
							},
							{
								columnWidth:'.25',
								layout:'form',
								items:[{
									xtype : 'combo',
								    name:'chk_priority',
								    id:'chkPriority',
								    fieldLabel:'校验优先级',
									allowBlank : false,
									displayField : 'text',
									valueField : 'id',
									editable : false,
									hiddenName : 'chk_priority',
									mode : 'local',
									triggerAction : 'all',
									value:'2',
									store : new Ext.data.SimpleStore({
										fields : ['id', 'text'],
										data : priorityLevel
									}),
									anchor : '90%'
								}]
							},{

								columnWidth:'.25',
								layout:'form',
								labelAlign:'right',
								items:[{
									xtype : 'combo',
								    name:'chk_action',
								    id:'chkAction',
								    fieldLabel:'校验动作',
									allowBlank : false,
									displayField : 'text',
									valueField : 'id',
									editable : false,
									hiddenName : 'chk_action',
									mode : 'local',
									triggerAction : 'all',
									value:'0',
									store : new Ext.data.SimpleStore({
										fields : ['id', 'text'],
										data : chk_action_array
									}),
									anchor : '95%'
								}]
							
							}   
					    ]
				   } ,{
					   xtype:'textfield',
					   fieldLabel : '校验规则名称',
					   name:'chk_rule_name',
					   id : 'chk_rule_name',
					   readOnly:true,
					   cls:'x-my-disabled',
					   allowBlank:false,
					   anchor : '80%'
					} ,{
				    	xtype:'textfield',
				    	fieldLabel : '失败提示',
				    	name:'chk_failure_tip',
				    	id : 'chk_failure_tip',
				    	anchor : '80%'
				    } 
				   //插入公式编辑面板
        	    ]
			}],
			buttons : [{
				text : '保存',
				id : 'save_pro',
				handler : function() {
					var formPanel = Ext.getCmp("addForm");
					if (formPanel.form.isValid()) {
						formPanel.form.submit({
							waitMsg : '正在处理,请稍后......',
							success : function(form, action) {
								Ext.Msg.alert("消息", action.result.info);
								if(action.result.success) {
									meta_rule_ds.reload();
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
		AddWindowTab.superclass.initComponent.call(this);
	}
});

function addChar(content){
	 var component =  Ext.getCmp(focus_com_id);
	var values = val_array[focus_com_id+'_array'];
	if(values.length>0){
		if(focus_com_id!='chk_formula'){
			values.push(','+content);
		}else{
			values.push(content);
		}
	}else{
		values.push(content);
	}
	var value =''; 
	for ( var j = 0; j < values.length; j++) {
		value +=values[j];
	}
//	component.setValue(value);
	RangeInsert(component,content);
	
	createFormula();
}
function delChar(){
	var component =  Ext.getCmp(focus_com_id);
	var values = val_array[focus_com_id+'_array'];
	values = values.slice(0,values.length-1);
	val_array[focus_com_id+'_array']=values;
	var value =''; 
	for ( var j = 0; j < values.length; j++) {
		value +=values[j];
	}
	component.setValue(value);
	createFormula();
}
function setColumn(grid){
	if(focus_com_id){
		if(grid.id=='self'){
			if(focus_com_id.indexOf('tab1')!=-1||focus_com_id=='chk_formula'){
				var r = grid.getSelectionModel().getSelected();
				addChar(r.get('metadata_name'));
			}
		}
		if(grid.id=='col_grid'||grid.id=='target'){
			if(focus_com_id.indexOf('tab2')!=-1){
				var r = grid.getSelectionModel().getSelected();
				addChar(r.get('metadata_name'));
			}
		}
	}
	createFormula();
}

function setStyle(id){
	var ids = ['tab1_col_1','tab1_col_2','tab2_col_1','tab2_col_2']
	 for ( var i = 0; i < ids.length; i++) {
		 var backColor = '#F0F0F0';
		 var color = '#A0A0A0';
		 var component =  Ext.getCmp(ids[i]);
		 if(ids[i]==id){
			 backColor ='#FBFBFF';
			 color='#000000';
		 }
		 component.el.dom.style.background=backColor;
		 component.el.dom.style.color=color; 
	}  
}

function createFormula(){
	if(focus_com_id=='chk_formula'){
		return;
	}
	var tab1 = Ext.getCmp('self_tab').getValue();
	var tab2 = Ext.getCmp('target_tab').getRawValue();
	var formula  = "";
	formula += tab1+'|'+Ext.getCmp('tab1_col_1').getValue()+'|sum|'+Ext.getCmp('tab1_col_2').getValue()+'|';
	formula += Ext.getCmp('logic_char').getValue();
	formula += tab2+'|'+Ext.getCmp('tab2_col_1').getValue()+'|sum|'+Ext.getCmp('tab2_col_2').getValue()+'|';
	Ext.getCmp('chk_formula').setValue(formula);
}

function addFunction(){
	if(Ext.getCmp('fun_win')){
		return;
	}
	var addwin = Ext.getCmp('addWindow');
	var funWin = new Ext.Window({
		title : '添加校验规则',
		buttonAlign : 'center',
		id:'fun_win',
//		modal : true,
		x:addwin.getWidth()-500,
		height:addwin.getHeight(),
		width:500,
		split : false,
		buttonAlign : 'center',
		layout:'fit',
		bodyStyle : 'padding: 10px',
		items : [
		        {
		        	xtype:'grid',
		        	id:'fun_grid',
		        	columns:functions_cm,
		        	store:functions_ds,
		        	sm : new Ext.grid.RowSelectionModel({
		    			singleSelect : true
		    		}),
		    		listeners:{
		    			rowdblclick:function(){
		    				var r = this.getSelectionModel().getSelected();
		    				RangeInsert(Ext.getCmp('chk_formula'),r.get('content'));
//		    				funWin.destroy();
		    			}
		    		}
		        }
		 ],
 		buttons:[
		         {
		        	text:'关闭',
		        	handler:function(){
//		        		var r = Ext.getCmp('fun_grid').getSelectionModel().getSelected();
//		        		if(r){
//		        			RangeInsert(Ext.getCmp('chk_formula'),r.get('content'));
//		        		}
		        		funWin.destroy();
		        	}
		         }
    		]
	});
	funWin.show();
	funWin.toFront();
}
function addColumn(width){
	
	var funWin = new Ext.Window({
		title : '添加字段',
		buttonAlign : 'center',
		id:'col_win',
//		modal : true,
		height:450,
		width:400,
		x:width-400, 
		split : false,
		layout:'border',
		bodyStyle : 'padding: 10px',
		items : [
         {
        	 xtype:'grid',
        	 id:'tab_grid',
        	 region:'center',
        	 columns:tab_cm,
        	 store:tab_ds,
        	 sm : new Ext.grid.RowSelectionModel({
        		 singleSelect : true
        	 }),
        	 listeners:{
        		 rowclick:function(){
        			 var r = this.getSelectionModel().getSelected();
 		   			tar_col_ds.load({params:{metadata_id: r.get('metadata_id')}});
        		 }
        	 }
         },{
        	 xtype:'grid',
        	 id:'col_grid',
        	 region:'east',
        	 width:200,
        	 columns:tar_col_cm,
        	 store:tar_col_ds,
        	 sm : new Ext.grid.RowSelectionModel({
        		 singleSelect : true
        	 }),
        	 listeners:{
        		 rowdblclick:function(){
        			 var r = this.getSelectionModel().getSelected();
        			 setColumn(this);
        		 }
        	 }
         }
         ],
    	 buttons:[
          {
        	  text:'确定',
        	  handler:function(){
        		  funWin.destroy();
        	  }
          }
        ]
	});
	funWin.show();
	funWin.toFront();
}