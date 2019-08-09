MainFrameUI = Ext.extend(Ext.Viewport,{
	layout : 'border',
	initComponent : function(){
	new Ext.applyIf(this,{
			items: [{
				xtype : 'panel',
				region : 'north',
				height : 50,
				hideCollapseTool : true,
				contentEl : 'north'
			},{
					id : 'centerDiv',
					region : 'center',
					contentEl : 'center',
					autoScroll : false
				},{
				region : 'west',
				xtype : 'panel',
				width : 174,
				collapsible : true,
				border : false,
				split : true,
				collapseMode : 'mini',
//				title: '菜单',
				hideCollapseTool : true,
				bodyStyle : 'padding:0px,0px,0px,0px;',
				contentEl : 'menu_parent',
				frame : false
			}]
		});
		MainFrameUI.superclass.initComponent.call(this);
	}
});

var tabManager = null;
$(function() {
	///请求菜单
	$.ajax({
		async : false,
		catche: false,
		type: 'POST',
		dataType: 'text',
		url: pathUrl +'/sysResource/getUserResource',
		success: function(data){
			$('#menu').menuTree(data,function(rid,label,url){
//				gotoPage(rid,label,url);
			}); 
		}
	});

	var mainFrame = new MainFrameUI();
	height = $("#centerDiv").height()-30;
	GetMenuID();
	menuFix();

	$("#center").ligerTab();
	tabManager = $("#center").ligerGetTabManager();
	tabManager.addTabItem({
		tabid : 'init',
		text : '初始页',
		height : height,
		showClose : false,
		url : ''
//		url : pathUrl +'/ftp/RunDashboard.jsp'
	});
	var h = $('#menu_parent').parent().height();
	$('#menu').height(h-34).css('overflow-y','auto');
	 
	if(currentBankOrgID!='8888'){
		$('#navigate_layout').attr('style','display : none;');
	}
	//$("a:contains('初始页')").html("初始页<span id='setInitPage' style='color:blue'>[设置]</span>");
	//$("#setInitPage").click(setInitPage);
});


//添加Tab页面
addPage = function(id,title,url){
//	if(url.indexOf(".jsp") == -1 && url.indexOf(".do") == -1){
//		url += 'skip.action?rid='+id;
//	}
	var u = url;
	if(url.indexOf('http://') == -1) {
		u = pathUrl +"/"+ url;
	}
	tabManager.addTabItem({
		tabid : id,
		text : title,
		height : height,
		changeHeightOnResize : true,
		showClose : true,
		url : u
	});
}

//打开多个Tab面板
addArrayPage = function(array){
	var exist = tabManager.isTabItemExist(array[0][0])
	if(exist){
		tabManager.selectTabItem(array[0][0]);
		return;
	}
	
	if(tabManager.getTabidList().length>10){
		Ext.Msg.show({
			title:'提示信息',
			msg:'打开窗口太多，容易导致浏览器崩溃，请先关闭部分页面!',
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.WARNING
		});
		return ;
	}
	
	for(var i=0;i<array.length;i++){
		addPage(array[i][0],array[i][1],array[i][2])
	}
	tabManager.selectTabItem(array[0][0]);
}

function gotoPage(id,title,url){
	
	var exist = tabManager.isTabItemExist(id);
	if(exist){
		tabManager.selectTabItem(id);
		return;
	}
	
	if(tabManager.getTabidList().length>10){
		Ext.Msg.show({
			title:'提示信息',
			msg:'打开窗口太多，容易导致浏览器崩溃,请先关闭部分页面!',
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.WARNING
		});
		return ;
	}

	addPage(id,title,url);
	tabManager.selectTabItem(id);
}

//页面销毁时，删除导出状态缓存
function destoryAction(){
	Ext.Ajax.request({
		timeout : 10 * 60000,
		url : pathUrl + '/loginAjax.do?method=destoryCache' ,
		method : 'POST',
		failure : function(response, options) {
		},
		success : function(response, options) {
		}
	});
}

//关闭页面时
function onclose(){
	
	//页面销毁时，删除导出状态缓存
	destoryAction();
	
//	if(event.clientX>document.body.clientWidth&&event.clientY<0||event.altKey)
//	{
  Ext.Ajax.request({
			url: pathUrl+'/loginAjax.do?method=doLogout',
			method: 'POST',
			params: {},
			callback: function (options, success, response) {
				window.location.href= basepath;
			},
			failure:function(response, options){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert(response.responseText);
			}, 
			success:function(response, options){
				Ext.MessageBox.hide();
			}
		});
//	}
}