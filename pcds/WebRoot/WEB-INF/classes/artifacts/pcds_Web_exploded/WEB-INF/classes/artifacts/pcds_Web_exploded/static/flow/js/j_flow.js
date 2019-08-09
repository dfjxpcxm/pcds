//初始化流程面板及相关函数组件，自定义函数
function init(){
	
	demo = $.createGooFlow($("#flow"), property);
	demo.setNodeRemarks(remark);
	demo.onItemDel = function(id, type) {
		if (confirm("确定要删除该单元吗?")) {
			this.blurItem();
			return true;
		} else {
			return false;
		}
	};
	demo.onItemFocus = function(id, model) {
		var obj;
		$("#ele_model").val(model);
		$("#ele_id").val(id);
		if(model=="line"){
	        obj=this.$lineData[id];
	    }else if(model=="node"){
	        obj=this.$nodeData[id];
	    }
		curr_obj = obj;//当前选中对象
		
		//$("#ele_name").val(obj.name);
		//addUser(id,model,obj);
		return true;
	};
	demo.onItemBlur = function(id, model) {
		curr_obj = '';//重置当前选中对象
		return true;
	};
	
	//点击保存的方法
	demo.onBtnSaveClick = function(){
		var params = {
				flow_id:curr_flow_id,
				jsondata:JSON.stringify(demo.exportData())
		};
		
		$.ajax({
			type:'POST',
			url:pathUrl+'/flow/saveJsonData',
			dataType : "json",
			data : params,
//			data : "jsondata="+JSON.stringify(demo.exportData()),
			success : function(msg) {
				if(msg.success == true){
					curr_flow_id = msg.info;
					afterSaveFlowInfo();
					Ext.Msg.alert('提示信息','保存成功'); 
				}else{
					Ext.Msg.alert('提示信息',msg.info);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(textStatus + ":" + errorThrown);
			}
		});
	};
	demo.grantUser = function(){
		if(!curr_obj || !curr_obj.type || curr_obj.type != 'pass'){
			Ext.Msg.alert('提示信息','请选择动作节点');
			return;
		}
		
		alert(this.$focus+'  '+curr_obj.type);//未完成

		/*if(''==id||null==id||'undefined'==id){
			return;
		}*/
		//showGrantUserWindow(id,model,obj);
		//addUser(id,model,obj);
	};
	//添加新流程图
	demo.onBtnNewClick = function(){
		demo.destrory();
		init();
		isNewFlow = true;
		curr_flow_id ='';
	};
	
}

/**
 * 保存路程成功后的执行的方法
 */
function afterSaveFlowInfo(){//
	var flowInfoStore = Ext.getCmp('flow_info_grid').getStore();
	var sm = Ext.getCmp('flow_info_grid').getSelectionModel();
	//重新加载grid
	flowInfoStore.reload({callback:function(){
		//选中
		var indexArr = new Array();
		for(var i = 0;i<flowInfoStore.getCount();i++){
			if(curr_flow_id == flowInfoStore.getAt(i).get('flow_id')){
				indexArr.push(i);
				break;
			}
		}
		sm.selectRows(indexArr,false);
	}
	});
	
}

/***
 * 删除流程信息
 */
function deleteFlowInfo(){
	var sm = Ext.getCmp('flow_info_grid').getSelectionModel();
	var objs = sm.getSelections();
	if(objs.length == 0){
		Ext.Msg.alert('提示信息','请选择流程');
		return ;
	}
	var flow_id = objs[0].get('flow_id');
	Ext.Msg.confirm('提示信息','确认将删除选中流程吗？',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url:pathUrl+'/flow/deleteFlowInfo',
				method:'POST',
				params:{flow_id:flow_id},
				callback:function(options,success,response){
					var json = Ext.util.JSON.decode(response.responseText);
					if(json.success){
						curr_flow_id = '';
						Ext.getCmp('flow_info_grid').getStore().load();
						Ext.Msg.alert('提示信息',json.info);
					}else{
						Ext.Msg.alert('提示信息',json.info);
					}
				
				}
			});
		}else{
			return;
		}
		
	});
	
}
