var allotRateDS ; 

/**
 * 显示账户分配界面
 * 由于有回显 对于没有显示分配信息的 将删除账户对应信息
 * @param acct_id
 * @param begin_date
 */
function showAccountAllotWin(acct_id,begin_date,store){
	
	if(!acct_id){
		Ext.Msg.alert('提示信息','账户号为空，不可进行分配');
		return;
	}
	if(!begin_date){
		begin_date = new Date();
	}
	
	var allotActionName = '/acctMgrRateH';//分配提交action
	
	//客户经理列表
	var custMgrStore = new Ext.data.JsonStore({
		url : pathUrl + '/custMgr/queryCustMgrList',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['cust_mgr_id','cust_mgr_name'] 
	});

	//复选框 监听事件 
	var sm = new Ext.grid.CheckboxSelectionModel({
			checkOnly: true,
			listeners:{
				rowselect : function(selectionModel, rowIndex, record){
					var cust_mgr_id   = record.get("cust_mgr_id");
					var cust_mgr_name = record.get("cust_mgr_name");
					var index = allotRateDS.find('cust_mgr_id',new RegExp("^"+cust_mgr_id+"$"));
					if(index == -1){
						var allotRateRecord = new Ext.data.Record.create([
							{name:'cust_mgr_id'},
							{name:'cust_mgr_name'},
							{name:'alloc_rate'}
						]);
						var newRecord = new allotRateRecord({
							cust_mgr_id	  : cust_mgr_id, 
							cust_mgr_name : cust_mgr_name,
							alloc_rate		  : 1 
						});
						allotRateDS.add([newRecord]);  
					}
				},
				rowdeselect: function(selectionModel, rowIndex, record){
					var cust_mgr_id = record.get("cust_mgr_id");
					var index = allotRateDS.find('cust_mgr_id',new RegExp("^"+cust_mgr_id+"$"));
					if(index != -1){
						allotRateDS.remove(allotRateDS.getAt(index));
					}
			    }
		    }
	  });      

	  var nsm = new Ext.grid.RowSelectionModel({
		  singleSelect : false
	  });
	
	  var columnModel = new Ext.grid.ColumnModel([
		  new Ext.grid.RowNumberer(),
		  sm,
		  {header:"客户经理号"	   ,dataIndex:"cust_mgr_id"    ,width: 95},
		  {header:"客户经理名"	   ,dataIndex:"cust_mgr_name"  ,width: 95}
	  ]);
  
	
	  
	  custMgrStore.on("beforeload",function(store){
		  var custMgrStr 		  = Ext.getCmp("searchCustMgr").getValue();							
		  custMgrStore.baseParams = {
			  enter_cust_mgr_id   : custMgrStr
		  }; 
	  }); 
	  
	  custMgrStore.on("load",function(store){
		  checkCustMgr(store);
	  }); 
	  //分配比例数据源
	  allotRateDS = new Ext.data.JsonStore({
		  root : 'results',
		  totalProperty : 'totalCount',
		  url : pathUrl + '/acctMgrRateH/queryAcctMgrRateH',
		  id 	: 'cust_mgr_id',
         fields: [ "cust_mgr_id","cust_mgr_name", "alloc_rate"]
     }); 
	  //
     var allotRateCM=new Ext.grid.ColumnModel([
		  new Ext.grid.RowNumberer(),
		  {header:'客户经理号'	,dataIndex:'cust_mgr_id'   ,align: 'center' ,width: 85, id:'cust_mgr_id'}, 
		  {header:'客户经理名'	,dataIndex:'cust_mgr_name' ,align: 'center' ,width: 85},
		  {header:'分配比例(双击编辑)'	,dataIndex:'alloc_rate'	   ,align: 'right'  ,width: 140 ,
			  editor: new Ext.form.NumberField({
											allowBlank: false,
											allowNegative: false,
											nanText:'输入有误',
											minValue:0,
											maxValue:1
										})} 
		  ]);
     
     var searchField = new Ext.form.TextField({
		  id:   "searchCustMgr",
		  name: "searchCustMgr", 
		  listeners : {
			specialkey : function(field, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					queryCustMgr(custMgrStore); 
				} 
			}
     }
	  });
	
     
	 var custMgrToolbar = [{
		  xtype:'tbtext',
		  text:'客户经理号/名:'
	  },searchField,{
		  text:"搜索",
		  tooltip:"搜索机构",
		  iconCls:"search",
		  handler:function(){
			  queryCustMgr(custMgrStore); 
		  }
	  }];
	 
	//1.1 分配比率表单  
	var allotFormPanel = new Ext.form.FormPanel({
  	    region	  	: 'center',
  		layout		: 'border',
		frame 		: true,
		split  		: true,
		width		: 360, 
		timeout		: 300000,
		bodyStyle	: 'padding:5px',
		labelWidth	: 60,
		method		: 'post',
		url     	: pathUrl + allotActionName+ '/saveBatchAcctMgrRateH',	
		items:[ 
		{xtype: "hidden", name : "sum_rates"       			,id: "sum_rates"						   		  	  },//总分配率(按比例分配情况下)
		{xtype: "hidden", name : "allot_rates"     			,id: "allot_rates"					   			      },//分配率+新客户经理(拼接字符串)
		{
			xtype  : 'panel',
			border : false,
			region : 'center',
			split  : true,
			layout : 'form',
			items  : [{
                xtype     : 'textfield',
                fieldLabel: '账户号',
                id        : 'acct_id',
                name      : 'acct_id',
                readOnly  : true,
                disabled  : true,
                anchor    : "81%",
                value     : acct_id
			},
			{
                xtype     : 'datefield',
                fieldLabel: '开始日期',
                id        : 'begin_date',
                name      : 'begin_date',
                anchor    : "81%",
                format    : 'Y-m-d',
                value     : begin_date
			}
		]
		}]
  	});
	
	//客户经理列表    		
	  var custMgrListPanel = new Ext.grid.GridPanel({
		  id			: 'custMgrListPanel',	
		  region		: 'south',
		  collapsible 	: false, 
		  autoScroll  	: true,
		  height 	    : 320,
		  split			: true,
		  frame			: false,
		  tbar			: custMgrToolbar,
		  store			: custMgrStore,
		  cm			: columnModel, 
		  sm			: sm,
		  loadMask 		: true,
		  bbar			: new Ext.PagingToolbar({ 
     		  pageSize 		: 30,
    	 	  store	 		: custMgrStore,
     		  displayInfo 	: true,
     		  displayMsg	: '第{0}-{1}条记录,共{2}条记录',
     		  emptyMsg		: "没有记录"
 		  })
	  });
	
	//分配窗口
	  allotWindow = new Ext.Window({
		  title			: "账户分配窗口", 
		  layout		: "border",
		  width			: 700, 
		  modal			: true,
		  height		: 450,
		  plain 		: true,
		  border		: false,
		  buttonAlign	: "center",
		  items:[{
			  region		: 'center',
			  layout		: 'border',
			  split			: true, 
			  border		: false,
			  items			:[
				  allotFormPanel,
				  custMgrListPanel
	          ]
		  },{
			  region	: 'east',
			  split		: true, 
			  layout	: 'border',
			  width		: 360, 
            viewConfig : {
                forceFit : true
            },
			  items:[
				  custMgrAllotPanel = new Ext.grid.EditorGridPanel({
					  id			: 'custMgrAllotPanel',
		              region 		: 'center',
		              autoScroll	: true,
		              collapsible	: false,
		              frame 		: false,
		              loadMask 		: true,
		              ds 			: allotRateDS,
		              cm 			: allotRateCM
	              })
			  ]
		 }],
		 buttons:[{
			 text:"分配",
			 handler:function(){
				 if(!allotWindow){
					 return;
				 }
				 var count = allotRateDS.getCount();//分配数						
				//判断是否选择
				/*if(count < 1){
					Ext.MessageBox.alert('错误',"请选择客户经理,并分配比例");
					return;
				}*/
				var sumRate=0;
				var zeroFlag;
				//判断是否在0到1之间
				for(var i=0; i<count; i++){
					var record = allotRateDS.getAt(i);
					var allotRate = parseFloat(record.get('alloc_rate'));
					
					if(allotRate==0){
						zeroFlag=true;
						Ext.MessageBox.alert('错误',"分配比例不可以为0,必须在0到1之间");
						break;
					}
					sumRate += parseFloat(record.get('alloc_rate'));
				}
				if(zeroFlag){return;}
				
				if(sumRate>1){
					Ext.MessageBox.alert('错误',"总分配比例不可以大于1");
					return; 
				}
				
				/*拼接客户经理号,分配率(考虑按照比例分配的情况下)*/
				var allot_rates=''; 
				for(var i=0; i<count; i++){
					if (i == 0) {
						allot_rates += allotRateDS.getAt(i).get('cust_mgr_id')    + ',' +
									   parseFloat(allotRateDS.getAt(i).get('alloc_rate'))/1;
					}else{
						allot_rates += '@'+allotRateDS.getAt(i).get('cust_mgr_id') + ',' +
									   parseFloat(allotRateDS.getAt(i).get('alloc_rate'))/1;
					}
				}							
				/*设置隐藏域中的值*/
				Ext.getCmp("allot_rates").setValue(allot_rates);
				Ext.getCmp("sum_rates").setValue(parseFloat(sumRate)/1);
				
				var acct_id = Ext.getCmp('acct_id').getValue();
				begin_date = Ext.getCmp('begin_date').getRawValue();
				var params={
					allot_rates:allot_rates,
					acct_id :acct_id,
					begin_date:begin_date
				}
				
				Ext.Ajax.request({
					url: pathUrl + allotActionName+'/saveBatchAcctMgrRateH', //url to server side script
					method: 'POST',
					params: params,
					callback: function (options, success, response) {
						var json=Ext.util.JSON.decode(response.responseText);
						if (success) { 
							Ext.MessageBox.alert('确认', json.info);
							store.reload();
							allotWindow.destroy();
                            allotWindow=null;				              
						} else {
							Ext.MessageBox.alert('错误',json.info);
						
						}
					}
				});
			}
		},{
			text:"取消",
			handler:function(){
				allotWindow.destroy();
				allotWindow = null;
			}
		}]
	});
	  allotWindow.show();
	  allotRateDS.load({params:{acct_id:acct_id}});
	  queryCustMgr(custMgrStore);
}

/**2. 客户经理查询 */
function queryCustMgr(custMgrStore){
	custMgrStore.load({ 
		params:{
				start			: 0,
				limit			: 30
		}
	});
	
}

/***选中已经分配比例的客户经理***/
function checkCustMgr(custMgrStore){
	
	if(!allotRateDS || allotRateDS.getCount() ==0){
		return;
	}
	var indexArray = new Array();
	var index= 0;
	var record ;
	for(var i = 0;i<custMgrStore.getCount();i++){
		record = custMgrStore.getAt(i);
		var cust_mgr_id = record.get('cust_mgr_id');
		
		if(isChecked(cust_mgr_id)){
			indexArray[index] = i;
			index++;
		}
		
	}
	
	Ext.getCmp('custMgrListPanel').getSelectionModel().selectRows(indexArray,true);
	
}

function isChecked(cust_mgr_id){
	
	for(var j = 0;j<allotRateDS.getCount();j++){
		var record = allotRateDS.getAt(j);
		if(cust_mgr_id == record.get('cust_mgr_id')){
			return true;
		}
	}
	return false;
}

 