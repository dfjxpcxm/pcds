var baseUrl = pathUrl+'/queryTmpl';
//**********************************面板****************/


//指标选中
var sstore = new Ext.data.JsonStore({
    root: 'metaResults',
    fields: ['field_id','field_label','field_type']
});
var tstore = new Ext.data.JsonStore({
    root: 'dimResults',
    id : 'field_id',
    fields: [ 'field_id','field_label','dim_cd']
});
var scm = [
    new Ext.grid.RowNumberer(),
    {
        header: "字段名", 
        dataIndex: 'field_id'
       
    },{
        header: "字段描述", 
        dataIndex: 'field_label',
        editor: new Ext.grid.GridEditor(  
                new Ext.form.TextField({  
                    allowBlank: false
                })  
            )  
    },{
        header: "字段类型", 
        dataIndex: 'field_type',
        hidden:true
    }
];

var tcm = [
    new Ext.grid.RowNumberer(),
    {
        header: "字段名", 
        dataIndex: 'field_id'
    },{
        header: "字段描述", 
        dataIndex: 'field_label',
        hidden:true
    },{
        header: "关联纬度", 
        dataIndex: 'dim_cd',
        hidden:true
    }
];

//字段配置
var p_card1={
        id:'card-1',
        layout:'border',
        border:false,
        modal:true,
        defaults:{
            split : true ,
            autoScroll:true,
            enableDragDrop : true
        },
        items:[{
        		tbar: [{
        				xtype:'tbtext',
    					text:'过滤框:'
        			},{
        				 id:"itemInputKey",
        				 name:"itemInputKey",
        				 xtype:'textfield',
        				 width:120
        			},{
				    	text:"搜索",
				    	tooltip:"搜索指标项",
				    	iconCls:"search",
				    	handler:function(){
				    	 	var params={
					    	 			    ds_id:getCurrentDsRecord().attributes.id,
					    	 			    dsSql:Ext.getCmp("ds_txarea").getValue(),
					    	 			    filterValue:Ext.getCmp("itemInputKey").getValue()
				    	 			    };
				        	loadMetaDimData(params);
				    	}
					} ],
				loadMask:true,
                xtype : 'editorgrid',
                region:'center',
                id : 'source',
                title : '字段列表',
                ddGroup : 'g2',
                sm : new Ext.grid.RowSelectionModel(),
                columns : scm,
                viewConfig:{
                   forceFit : true
                },
                store : sstore 
            },{
                xtype : 'grid',
                id : 'local',
                region:'east',
                width:300,
                title : '维度列表',
                ddGroup : 'g1',
                sm : new Ext.grid.RowSelectionModel(),
                columns : tcm,
                viewConfig:{
                   forceFit : true
                },
                store : tstore 
            }]
    };

var p_card0 = {
    id: 'card-0',
    title:'请输入sql语句：',
    layout:'fit',
    items:[ {
    	xtype:'textarea',
    	id:'ds_txarea' ,
    	name:'ds_txarea' 
    }] ,
    buttons:[{
    	text:'校验数据源',
    	handler:function(){
    		var dsSql = Ext.getCmp('ds_txarea').getValue();
    		if(!dsSql){
    			Ext.Msg.alert('提示信息','请在上方输入数据源sql语句');
    			return;
    		}
    		Ext.Ajax.request({
    		   method:'post',
			   url: baseUrl+'/checkDsSql',
			   params:{dsSql:Ext.getCmp('ds_txarea').getValue()},
			   success: function(response, opts) {
			   		var json=Ext.util.JSON.decode(response.responseText);
			   		if(json.success){
			   			Ext.Msg.alert('提示信息','校验数据源sql成功。');
			   		}else{
			   			Ext.Msg.alert('提示信息',json.info);
			   		}
			   },
			   failure: function(response, opts) {
			      Ext.Msg.alert('校验失败',reponse.responseText);
			   }
			});
    	}
    }]
}


var p_card2 = {
	    id: 'card-2',
	    xtype:'panel',
	    layout:'form',
	    bodyStyle:'padding:20px',
	    items:[{
            xtype:'fieldset',
            title:'选择展示方式',
    	    bodyStyle:'padding:10px 0px 0px 0px;',
            height:90,
            defaultType: 'radio',   
            items:[
                   {
                       checked: true,
                       fieldLabel: '',
                       labelSeparator: '',
                       boxLabel: '普通列表',
                       name: 'show_type',
                       inputValue: '0'
                   },{
                       fieldLabel: '',
                       labelSeparator: '',
                       boxLabel: '树型列表',
                       name: 'show_type',
                       disabled:true,
                       inputValue: '1'
                   }
            ]},{
                xtype:'fieldset',
                title: '说明(*)',
                height:150,
                bodyStyle: 'padding-bottom:10px;background:#eee;',
                autoScroll: true,
                html: '<h2>1.普通列表</h2><br>' +
                       '<p> 普通的查询列表</p><br>'+
                       '<h2>2.树形列表</h2><br>'+
                       '<p> 根据树形纬度，生成的树形列表</p>'
            }]
		} 

//上一步，下一步调用的事件
var cardNav = function(incr){
    var l = Ext.getCmp('card-wizard-panel').getLayout();
    var i = l.activeItem.id.split('card-')[1];
    if(!Ext.getCmp('ds_txarea').getValue()){
    	Ext.Msg.alert('提示信息','请输入数据源sql语句');
    	return;
    } else{
    	var next = parseInt(i) + incr;
    	if(next==1){
        	var params={ds_id:getCurrentDsRecord().attributes.id,dsSql:Ext.getCmp("ds_txarea").getValue()};
        	loadMetaDimData(params);
    	}
    	if(next==2){
  	    	if(Ext.getCmp('source').getStore().getTotalCount() ==0){
  	      	  Ext.Msg.alert('提示信息','没有字段数据配置');
  	      	  return;
  	        }else{
  	        	Ext.getCmp('saveValue').enable();
  	        }
  	    }else{
  	    	Ext.getCmp('saveValue').setDisabled(true);
  	    }
    	l.setActiveItem(next);
    	
    	//根据视角选中情况跳过disabled 页
    	/*if(l.activeItem.disabled){
	        next=next+incr;
	        l.setActiveItem(next);
	    }*/
    	Ext.getCmp('card-prev').setDisabled(next==0);
    	Ext.getCmp('card-next').setDisabled(next==2);
    	
    }
};

//主面板
var cardWizard = {
    id:'card-wizard-panel',
    layout:'card',
    activeItem: 0,
    border:false,
    defaults: {border:false},
    bbar: [{
        text:'<b>保 存</b>',
        id:'saveValue',
        iconCls:'save',
        disabled:true,
        handler:function(){
        	var ds_id=getCurrentDsRecord().attributes.id;
        	var ds_sql=Ext.getCmp("ds_txarea").getValue();
        	var dsMetaArray =new Array();
        	var dimArray=new Array();
        	sstore.each(function(rec){
        		dsMetaArray.push(rec.data);
        	});
        	tstore.each(function(dim){
        		dimArray.push(dim.data);
        	});
        	var dsMetaJson=Ext.util.JSON.encode(dsMetaArray);
        	var dimJson=Ext.util.JSON.encode(dimArray);
            Ext.Ajax.request({
 			   url: baseUrl+'/configDsAndMeta',
 			   params:{dsMetaJson:dsMetaJson,dimJson:dimJson,ds_id:ds_id,ds_sql:ds_sql},
 			   success: function(response, opts) {
 			   		var json=Ext.util.JSON.decode(response.responseText);
 			   		if(json.success){
 			   		  Ext.Msg.alert('提示信息',json.info);
 			   		  Ext.getCmp('dsMetaGridPanel').getStore().reload();
 			   		}
 			   },
 			   failure: function(response, opts) {
 			      Ext.Msg.alert('校验失败',reponse.responseText);
 			   }
 			});
            hwindow.close();
        }
    },'-',{
        text:'<b>退 出</b>',
        id:'exitValue',
        iconCls:'cross',
        handler:function(){
            hwindow.close();
        }
    },'->', {
        id: 'card-prev',
        text: '&laquo; 上一步', 
        iconCls:'color', 
        handler:cardNav.createDelegate(this, [-1]),
        disabled: true
    },{
        id: 'card-next',
        text: '下一步 &raquo;',
        iconCls:'color', 
        handler: cardNav.createDelegate(this, [1])
    }],
    items: [p_card0,
            p_card1,
            p_card2
    ]
};
var hwindow;
function showDimWindow(node){
    if(hwindow!=null){
        initQuery();
        //hwindow.doLayout(true);
        hwindow.show();
    }
    else{
	    hwindow=new Ext.Window({
	        title:'基于sql的灵活查询配置',
	        layout:'fit',
	        closeAction: 'hide',
//	        maximized:true,
	        width:700,
	        height:500,
	        modal:true,
	        border:false,
            resizable:false,
	        buttonAlign:'right',
	        items: cardWizard ,
	        listeners:{'beforeclose':function(){
	            hwindow.hide();
	            return false;
	        }}
	    });
	    
	    hwindow.show();
	  
	    
		var sourceEl = Ext.getCmp('source').getView().scroller.dom;
		var sourceTarget = new Ext.dd.DropTarget(sourceEl, {
		    ddGroup: 'g1',
		    notifyDrop: function(ddSource, e, data){
		        var records=ddSource.dragData.selections;
		        Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
		        return true;
		    }
		});
		var localEl = Ext.getCmp('local').getView().scroller.dom;
		var localTarget = new Ext.dd.DropTarget(localEl, {
		    ddGroup: 'g2',
		    notifyDrop: function(ddSource, e, data){
		        var records=ddSource.dragData.selections;
		        var exist = false ;
		        for (var i = 0; i < records.length; i++) {
		        	var columnIndex = records[i].get('field_id');
		        	var localCount = Ext.getCmp('local').store.getCount();
		        	if(localCount==0){
		        		Ext.getCmp('local').store.add(records[i]);
		        	}else{
		        		for (var j = 0; j < localCount; j++) {
			        		var localIndex = Ext.getCmp('local').store.getAt(j).get('field_id');
			        		if(columnIndex == localIndex){
			        			exist = true;
			        		}
			        	}
		        	}
		        	if(!exist&&localCount!=0){
			       		Ext.getCmp('local').store.add(records[i]);
			        }
		        }
		        return true;
		    }
		});
    }
    setSqlContent();//设置sql内容
}

function initQuery(){
    /*bankList='';
    productList='';
    measureList='';
    showDim='';
    unCheckTree(bankTree);
    unCheckTree(productTree);
    tstore.removeAll();*/
    //初始化面板
    var p=Ext.getCmp('card-wizard-panel').getLayout();
    p.setActiveItem(0);
    Ext.getCmp('card-prev').setDisabled(true);
    Ext.getCmp('card-next').setDisabled(false);
    Ext.getCmp('saveValue').setDisabled(true);
}

/**
 * 获取选中的gird列表
 * @param {} store
 * @param {} id
 * @return {}
 */
function getSelectRecords(store,id){
	var ids="";
	for (var index = 0; index < store.getCount(); index++) {
		if(index==0)
			ids+=store.getAt(index).get(id);
		else
			ids+=','+store.getAt(index).get(id);
	}
	return ids;
}
function setSqlContent(){
	var ds_id=getCurrentDsRecord().attributes.id;
	  Ext.Ajax.request({
		   url: baseUrl+'/getDsInfo',
		   params:{ds_id:ds_id},
		   success: function(response, opts) {
			   var ds_sql="";
		   		var json=Ext.util.JSON.decode(response.responseText);
		   		var data=json.results[0];//返回唯一一条数据源记录
		   		if(data.ds_sql!=null){
		   			ds_sql=data.ds_sql;
		   		}
		   		Ext.getCmp('ds_txarea').setValue(ds_sql);
		   },
		   failure: function(response, opts) {
		      Ext.Msg.alert('提示信息',json.info);
		   }
		});
}
/**
 * 加载字段和维度列表数据
 * @param params
 */
function loadMetaDimData(params){
	Ext.Ajax.request({ 
		url: baseUrl+'/getDsSqlData',//后台取数据的地址 
		params : params,
        success:function(response){
            var responseJson = Ext.util.JSON.decode(response.responseText);
            if(responseJson.success)
            { 
            	sstore.loadData(responseJson);
            	tstore.loadData(responseJson);
            } 
        }, 
        failure:function(response){
        	Ext.Msg.alert('提示信息','加载数据失败');
        } 
  });
}
