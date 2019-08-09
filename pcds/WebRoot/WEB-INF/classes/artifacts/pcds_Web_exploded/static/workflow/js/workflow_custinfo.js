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
		url : pathUrl + '/workflow/getUserInfo',
		root : 'results',
		totalProperty : 'totalCount',
		fields : ['nodeid','nodename'] 
	});

	//复选框 监听事件 
	var sm = new Ext.grid.CheckboxSelectionModel({
			checkOnly: true,
			listeners:{
				rowselect : function(selectionModel, rowIndex, record){
					var nodeid   = record.get("nodeid");
					var nodename = record.get("nodename");
					var index   =  allotRateDS.find('nodeid',new RegExp("^"+nodeid+"$"));
					if(index == -1){
						var allotRateRecord = new Ext.data.Record.create([
							{name:'nodeid'},
							{name:'nodename'}
						]);
						var newRecord = new allotRateRecord({
							nodeid	  : nodeid, 
							nodename : nodename
						});
						allotRateDS.add([newRecord]);  
					}
				},
				rowdeselect: function(selectionModel, rowIndex, record){
					var nodeid = record.get("nodeid");
					var index = allotRateDS.find('nodeid',new RegExp("^"+nodeid+"$"));
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
		  {header:"用户编号"	   ,dataIndex:"nodeid"    ,width: 95},
		  {header:"用户名称"	   ,dataIndex:"nodename"  ,width: 95}
	  ]);
	  
	  custMgrStore.on("beforeload",function(store){
		  var key 		  = Ext.getCmp("key").getValue();							
		  custMgrStore.baseParams = {
			  key   : key
		  }; 
	  }); 
	  
     
     var searchField = new Ext.form.TextField({
		  id:   "key",
		  name: "key", 
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
		  text:'用户编号/名称:'
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
		  width 	    : 380,
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
		 id 	: 'nodeid',
         fields: [ "nodeid","nodename"]
     }); 
	  //
     var allotRateCM=new Ext.grid.ColumnModel([
		  new Ext.grid.RowNumberer(),
		  {header:'用户编号'	,dataIndex:'nodeid'   ,align: 'center' ,width: 125, id:'nodeid'}, 
		  {header:'用户名称'	,dataIndex:'nodename' ,align: 'center' ,width: 125}
	]);
	  var defaultValue = t.getValue();
	  var defaultArys = [];
	  if(defaultValue){
		  var arys = defaultValue.split(",");
		  for(var i=0;i<arys.length;i++){
			  var mgrAry = arys[i].split("@");
			  var mgrMsg = mgrAry[0];
			  var aryRow = [mgrMsg.slice(mgrMsg.indexOf('[')+1,mgrMsg.indexOf(']')),mgrMsg.split('[')[0]];
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
			 text:"确定",
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
			
				
				/*拼接客户经理号,分配率(考虑按照比例分配的情况下)*/
				var allot_rates=''; 
				for(var i=0; i<count; i++){
					if(i>0){
						allot_rates+=",";
					}
					allot_rates += allotRateDS.getAt(i).get('nodename') + '['+allotRateDS.getAt(i).get('nodeid')+']';

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
		var nodeid = record.get('nodeid');
		
		if(isChecked(nodeid)){
			indexArray[index] = i;
			index++;
		}
		
	}
	Ext.getCmp('custMgrListPanel').getSelectionModel().selectRows(indexArray,true);
}

function isChecked(nodeid){
	
	for(var j = 0;j<allotRateDS.getCount();j++){
		var record = allotRateDS.getAt(j);
		if(nodeid == record.get('nodeid')){
			return true;
		}
	}
	return false;
}