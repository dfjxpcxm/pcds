/**
 * 截图页面逻辑
 */
$(function(){
	//由于有的IE版本问题 启动crop 通过方法来调用  
	//startCrop() ;
	
	//记得放在jQuery(window).load(...)内调用，否则Jcrop无法正确初始化
//		$("#cropImg").Jcrop({
//			aspectRatio:1,
//			onChange:showCoords,
//			onSelect:showCoords
//		});	
	
});


//简单的事件处理程序，响应自onChange,onSelect事件，按照上面的Jcrop调用
function showCoords(obj){
	$("#x").val(obj.x);
	$("#y").val(obj.y);
	$("#w").val(obj.w);
	$("#h").val(obj.h);
	if(parseInt(obj.w) > 0){
		//计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到
		var rx = $("#resultDiv").width() / obj.w; 
		var ry = $("#resultDiv").height() / obj.h;
		//通过比例值控制图片的样式与显示
		$("#crop_preview").css({
			width:Math.round(rx * $("#cropImg").width()) + "px",	//预览图片宽度为计算比例值与原图片宽度的乘积
			height:Math.round(rx * $("#cropImg").height()) + "px",	//预览图片高度为计算比例值与原图片高度的乘积
			marginLeft:"-" + Math.round(rx * obj.x) + "px",
			marginTop:"-" + Math.round(ry * obj.y) + "px"
		});
	}
}
$("#crop_submit").click(function(){
	if(parseInt($("#x").val())){
		$("#crop_form").submit();	
	}else{
		alert("要先在图片上划一个选区再单击确认剪裁的按钮哦！");	
	}
});


var api = null;
function startCrop() {
	if(api){
		api.destroy();
	}
	api = $.Jcrop("#cropImg",{
			aspectRatio:1,//选框宽高比
			onChange:showCoords,
			onSelect:showCoords
			,bgColor:'none',
			setSelect: [20,20,68,68] //默认选中区域  x1,y1   x2,y2
		});
		
 }


/*

var resCode = "";
var resName = "";
var dhtmlXml = "";
	var resTreePanel = new Ext.Panel({
		id : 'card-1',
		region : 'center',
		split : true,
		title : '可选功能页面',
		autoScroll :true,
		layout : 'fit',
		items : [{
			xtype : 'panel',
			autoScroll :true,
			contentEl : 'res_tree',
			border : true,
			split : false
		}]
	});

	var infoFormPanel  = new Ext.form.FormPanel({
		id:'currentPage',
		region:'north',
		split : true,
		height : 80,
		title : '用户初始页',
		frame : true,
		labelWidth : 85,
		bodyStyle : 'padding:10px 10px 0px 10px',
		layout : 'form',
		items : [{
			xtype : 'textfield',
			id : 'pageInfo',
			fieldLabel : '用户初始页',
			value : '',
			readOnly : true,
			anchor : '91%'
		}]
	});


var addWindow = new Ext.Window({
		width : 640,
		height : 500,
		layout : 'border',
		title : '设置初始页',
		modal : true,
		buttonAlign : 'center',
		listeners : {
			beforeclose : function(){
				addWindow.hide();
				return false;
			}
		},
		items : [infoFormPanel,resTreePanel],
		buttons : [{
			text : '保存',
			handler : function() {
				if(resCode == ''){
					Ext.MessageBox.alert("提示信息","请选择初始页!");
					return ;
				}
	 			Ext.Ajax.request({
					url : pathUrl + '/init_updateUserDefaultInitPage.action?default_page='+resCode,
					waitMsg : '正在处理,请稍候......',
					method : 'POST',
					timeout : 30000,
					failure : function(response, options) {
						Ext.MessageBox.alert("提示信息","保存失败!");
					},
					success : function(response, options) {
						addWindow.hide();
						window.frames[1].location = pathUrl + '/bsc/init_defaultPage.action';
					}
				});
			}
		}, {
			text : '取消',
			handler : function() {
				addWindow.hide();
			}
		}]
	});
	
	
	
$(function(){
	Ext.Ajax.request({
		url : pathUrl + '/init_getResourceTree.action',
		params:{exp:'0'},
		method : 'POST',
		timeout : 30000,
		callback : function(options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				tree=new dhtmlXTreeObject("res_tree","100%","100%","");
				tree.setImagePath(pathUrl + "/public/scripts/dhtmlx/imgs/");
				tree.enableCheckBoxes(1);
				tree.attachEvent("onCheck",function(){
					var allCheckedId = tree.getAllChecked();
					if(allCheckedId.split(",").length > 1) {
						tree.setCheck(resCode,0);
						resCode = tree.getAllChecked();
					}else{
						resCode = allCheckedId;
					}
					Ext.getCmp("pageInfo").setValue(tree.getItemText(resCode));
				});
				tree.loadXMLString(json.info);
				
				if(tree.getAllItemsWithKids() != ''){
					var ids = tree.getAllItemsWithKids().split(",");
					for(var i=0;i<ids.length; i++){
						tree.disableCheckbox(ids[i],true);
					}
				}
			} else {
				Ext.MessageBox.alert('提示信息', "加载用户菜单树失败!");
			}
		}
	});
});


var setInitPage = function(){
	resCode = tree.findItemIdByLabel(resName,0,top);
	var allCheckedIds = tree.getAllChecked().split(",");
	for(var i=0;i<allCheckedIds.length;i++){
		tree.setCheck(allCheckedIds[i],0);
	}
	tree.setCheck(resCode,true);
	
	Ext.getCmp("pageInfo").setValue(resName);
	$("#res_tree").show();
	addWindow.show();
}

var changeOrgDS = new Ext.data.JsonStore({
	url : pathUrl + '/login_listChangeOrg.action',
	root : 'results',
	totalProperty : 'totalCount',
	fields : ['bank_org_id', 'bank_org_name'],
	autoLoad : false
});
changeOrgDS.on("load",function(){
	if(changeOrgDS.getCount() > 0) {
		for (var i = 0; i < changeOrgDS.getCount(); i++) {
			var record = changeOrgDS.getAt(i);
			if(record.get('bank_org_id') == currentBankOrgID)
				Ext.getCmp("changeOrgGrid").getSelectionModel().selectRow(i,false);
		}
	}	
});

var rs = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true
	//handleMouseDown : Ext.emptyFn()
})
var changrOrgHeader = [new Ext.grid.RowNumberer(),rs, {
	header : '机构ID',
	dataIndex : 'bank_org_id'
}, {
	header : '机构名称',
	dataIndex : 'bank_org_name'
}];

function doSwitchOrg() {
	var changeOrgWindow = new Ext.Window({
		title : '切换权限机构',
		width : 400,
		height : 300,
		modal : true,
		border : false,
		buttonAlign : 'center',
		id : 'changeOrgWindow',
		listeners : {
			close : function(){
				Ext.getCmp("changeOrgWindow").destroy();			
			}
		},
		layout : 'fit',
		items : [{
			xtype : 'grid',
			id : 'changeOrgGrid',
			columns : changrOrgHeader,
			store : changeOrgDS,
			loadMask : true,
			split : true,
			sm : rs,
			flex : 1,
			viewConfig : {
				forceFit : true
			}
		}],
		buttons : [{
				text : '确定',
				id : 'save_link',
				handler : function() {
					var grid = Ext.getCmp("changeOrgGrid");
					var sm = grid.getSelectionModel();
					if(sm.getSelections().length < 1) {
						Ext.Msg.alert("提示信息","请选择需要切换到的机构");
						return;
					}
					
					var record = sm.getSelections()[0];
					document.switchOrgForm.special_org_id.value = record.get('bank_org_id');
					Ext.getCmp("changeOrgWindow").destroy();
					document.switchOrgForm.submit();
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp("changeOrgWindow").destroy();
				}
			}]
	});
	changeOrgWindow.show();
	changeOrgDS.load();
}

function doSwitchYear() {
	
	var yearStore = new Ext.data.JsonStore({
			url : pathUrl + '/selector_listYear.action',
			root : 'results',
			fields : ['year_id', 'year_name'],
			autoLoad : true,
			listeners : {
				load : function(store, records, options) {
					if(yearStore.getCount() > 0) {
						var r = yearStore.getAt(0);
						Ext.getCmp("yearSelector").setValue(r.get('year_id'));
					}
				}
			}
		});
	
	var changeYearWindow = new Ext.Window({
		title : '切换查询年份机构',
		width : 300,
		height : 120,
		modal : true,
		border : false,
		buttonAlign : 'center',
		id : 'changeYearWindow',
		listeners : {
			close : function(){
				Ext.getCmp("changeYearWindow").destroy();			
			}
		},
		bodyStyle : 'padding: 10px',
		layout : 'fit',
		items : [{
			xtype : 'form',
			labelWidth : 60,
			items : [{
				xtype : 'combo',
				valueField : 'year_id',
				displayField : 'year_name',
				hiddenName : 'year_id',
				store : yearStore,
				triggerAction : 'all',
				mode : 'local',
				name : 'year_id',
				id : 'yearSelector',
				fieldLabel : '年份',
				editable : false,
				anchor : '91%'
			}]
		}],
		buttons : [{
				text : '确定',
				id : 'save_link',
				handler : function() {
					var n_year_id = Ext.getCmp("yearSelector").getValue();
					if(n_year_id == '') {
						Ext.Msg.alert("提示信息","请选择需要切换到的年份");
						return;
					}
					
					document.switchYearForm.n_year_id.value = n_year_id;
					Ext.getCmp("changeYearWindow").destroy();
					document.switchYearForm.submit();
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp("changeYearWindow").destroy();
				}
			}]
	});
	changeYearWindow.show();
}

*/
