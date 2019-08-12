 var allotRateDS;
/**
 * 显示账户分配界面
 * 由于有回显 对于没有显示分配信息的 将删除账户对应信息
 * @param acct_id
 * @param begin_date
 */
function showAccountAllotWin(t){
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
					var index 		  = allotRateDS.find('cust_mgr_id',new RegExp("^"+cust_mgr_id+"$"));
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
		  iconCls:"search",
		  handler:function(){
			  queryCustMgr(custMgrStore); 
		  }
	  }];
	 
	//客户经理列表    		
	  var custMgrListPanel = new Ext.grid.GridPanel({
		  id			: 'custMgrListPanel',	
		  region		: 'west',
		  collapsible 	: false, 
		  autoScroll  	: true,
		  width 	    : 320,
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
	  
	  //分配比例数据源
	 allotRateDS = new Ext.data.ArrayStore({
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

	  //100352[德泉],0.1@004242[徐克琼],0.1
	  var defaultValue = t.getValue();
	  var defaultArys = [];
	  if(defaultValue){
		  var arys = defaultValue.split(";");
		  for(var i=0;i<arys.length;i++){
			  var mgrAry = arys[i].split("@");
			  var mgrMsg = mgrAry[0];
			  var mgrAllot = mgrAry[1];
			  var aryRow = [mgrMsg.split('[')[0],mgrMsg.slice(mgrMsg.indexOf('[')+1,mgrMsg.indexOf(']')),mgrAllot];
			  defaultArys.push(aryRow);
		  }
		 allotRateDS.loadData(defaultArys);
		 
		 custMgrStore.on("load",function(store){
			  checkCustMgr(store);
		  }); 
	  }
	
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
		  items:[custMgrListPanel,  custMgrAllotPanel = new Ext.grid.EditorGridPanel({
			  id			: 'custMgrAllotPanel',
              region 		: 'center',
              autoScroll	: true,
              collapsible	: false,
              frame 		: false,
              loadMask 		: true,
              ds 			: allotRateDS,
              cm 			: allotRateCM
          })],
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
					if(i>0){
						allot_rates+=";";
					}
					allot_rates += allotRateDS.getAt(i).get('cust_mgr_id')    + '['+allotRateDS.getAt(i).get('cust_mgr_name')+']@' +
					   parseFloat(allotRateDS.getAt(i).get('alloc_rate'))/1;

				}							
				
				t.setValue(allot_rates);
				allotWindow.destroy();
				allotWindow = null;
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