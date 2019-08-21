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
				id:'leftMenusPanel',
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
var mainHeight ;//主显示区高度
var tabManager = null;
$(function() {

	///请求菜单
	$.ajax({
		async : false,
		catche: false,
		type: 'POST',
		dataType: 'text',
		//url: pathUrl +'/resourceExtAjax.do?method=getUserResource',
		url: pathUrl +'/sysResource/getUserResource',
		success: function(data){
			$('#menu').menuTree(data,function(rid,label,url){}); 
			if(loginParam == 'changePwd'){
				changePwd("首次登录请修改密码");
			}else{
				if(login_info != null && login_info != '' && login_info != 'null'){
					if(parseInt(login_info) > 0){
					//	alert('密码还有'+login_info+'天过期，请及时修改密码！');
					}else{
						changePwd("密码已过期请修改密码");
					}
				}
				
			}
				
				
			//--------------------------------------------------------------新增调用函数
		//	 checkLogin();
		}
	});

	var mainFrame = new MainFrameUI();
	mainHeight = $("#centerDiv").height()-30;
	GetMenuID();
	menuFix();

	$("#center").ligerTab();
	tabManager = $("#center").ligerGetTabManager();
	tabManager.addTabItem({
		tabid : 'init',
		text : '初始页',
		height : mainHeight,
		showClose : false,
		url : pathUrl +'/'+initPageUrl
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
	var u = url;
	if(url.indexOf('http://') == -1) {
		if(url.indexOf('?') == -1){
			u = pathUrl +"/"+ url+'?exc_title='+encodeURI(title)+'&id='+id;
		}else{
			u = pathUrl +"/"+ url+'&exc_title='+encodeURI(title)+'&id='+id;
		}
		
	}
	tabManager.addTabItem({
		tabid : id,
		text : title,
		height : mainHeight,
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
var lastOpenMenuId = '';//记录最后一次点击菜单的id
var lastOpenMenuObj = null; //记录最后一次点击菜单对应对象
function gotoPage(id,title,url,obj){
	var exist = tabManager.isTabItemExist(id);
	if(exist){
		tabManager.selectTabItem(id);
		updateMenuBg(id,obj);
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
	
	updateMenuBg(id,obj);

	addPage(id,title,url);
	tabManager.selectTabItem(id);
}

/**
 * 更新菜单背景
 * @param {Object} id
 * @param {Object} obj
 */
function updateMenuBg(id,obj){
	if(!obj){
		return;
	}
	if(!lastOpenMenuId){//第一次进入点击菜单时直接更换背景
		obj.style.backgroundImage="url('public/images/menu/menu-bg.png')";
	}else{
		if(lastOpenMenuId != id){//不是第一次点击  而且点击的不是上次点击的菜单
			obj.style.backgroundImage="url('public/images/menu/menu-bg.png')";//更新背景
			lastOpenMenuObj.style.backgroundImage='';//清空上次背景
		}
	}
	//图片不重复加载
	obj.style.backgroundRepeat='no-repeat';
	//更新最后更新记录
	lastOpenMenuId = id;
	lastOpenMenuObj = obj;
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


function logout(){
	alert("--"+casUrl)
	Ext.Ajax.request({
		url : pathUrl + '/login.do?method=doLogout',
		method: 'POST',
			params: {},
			failure:function(response, options){
				Ext.MessageBox.alert(response.responseText);
			}, 
			success:function(response, options){
				window.location.href= casUrl;
			}
	});
}

//---------------------------------------------------------------新增服务探测功能----------------


function showMessageBox(){
	Ext.MessageBox.show({
           msg: '通讯中断,正在连接...',
           width:300,
           wait:true,
           waitConfig: {interval:400},
           animEl: 'mb7'
       });
}

var isMask = false;    //滚动条状态
var setTime = 15000;   //探测间隔时间   单位：ms
var connNum = 0;       //自动失败探测次数
var loginNum = 0;       //自动失败探测次数
var loginWin ;

function checkLogin(){
	window.setTimeout(getServerStatus,setTime);
}

function getServerStatus(){
		$.ajax({
		async : true,
		type: 'POST',
		timeout : 10000,
		url: pathUrl +'/TestServer.jsp',
		success: function(data,status,response){
			if (200!=response.status) {
					if(!isMask){
						showMessageBox();
						isMask = true;
					}
				}else{
					if(isMask){
						Ext.MessageBox.hide();
						if(connNum >= 8){
							Ext.Msg.show({
						       title: '提示',
						       msg: '通讯已恢复,请重新登录!',
						       buttons: Ext.MessageBox.OK,
						       fn: loc_href,
						       animEl: 'mb3'
						   });
						}else{
							connNum = 0;  //重置
							if(!loginWin){
								Ext.Msg.show({
							       title: '提示',
							       msg: '通讯已恢复,请解锁!',
							       buttons: Ext.MessageBox.OK,
							       fn: reLogin,
							       animEl: 'mb3'
							   	});
							}
						}
					}else{
						if(data != null && data != '' ){
							if(loginNum > 2){
								Ext.Msg.show({
							       title: '提示',
							       msg: '会话被终止，系统将退出！</br>如用户在其他终端登陆或会话超时等原因引起！',
							       buttons: Ext.MessageBox.OK,
							       fn: loc_href,
							       animEl: 'mb3'
							   });
							   loginNum = 0;
							}else{
								loginNum += 1;
							}
						}
						
					}
				}
		},
		error:function(response){
			connNum += 1;
			if(connNum >= 3){
				if(!isMask){
					showMessageBox();
					isMask = true;
				}
			}
		}
	});
		window.setTimeout(getServerStatus,setTime);
}

function reLogin(){
	Ext.Ajax.request({
		url : pathUrl + '/loginAjax.do?method=doLogout',
		method: 'POST',
			params: {},
			failure:function(response, options){
			}, 
			success:function(response, options){
			}
	});
	 loginWin = new Ext.Window({
		title : '登陆',
		modal : true,
		width : 300,
		height : 200,
		closable : false,
		buttonAlign : 'center',
		resizable : false,
		layout  :'fit',
		items : [{
			    xtype : 'form',
			    id : 'modifyPasswordForm',
			    labelWidth : 60,
			    frame : true,
	            bodyStyle: 'padding:5px 20px',
			    items: [{
			        id : 'user_id',
			        name : 'user_id',
			        xtype : 'hidden',
			        fieldLabel : '当前用户',
			        readOnly : true,
			        allowBlank:false,
			        value : userId
			    },{
			        id : 'user_name',
			        name : 'user_name',
			        xtype : 'textfield',
			        fieldLabel : '当前用户',
			        readOnly : true,
			        allowBlank:false,
			        value : userName
			    },{
			        id : 'password',
			        name : 'password',
			        xtype : 'textfield',
			        inputType:'password',
			        fieldLabel : '密码'
			    }]
		}],
		buttons : [{
		text : '解锁',
		id : 'confrim',
		handler : function() {
			    var p = Ext.getCmp('password').getValue();
				Ext.Ajax.request({
					url : pathUrl + '/loginAjax.do?method=doLogin',
					method: 'POST',
					params: {user_id:userId,password:p,width : screen.width,height : screen.height},
					callback : function(options,success,response){
						var json = Ext.util.JSON.decode(response.responseText);
						if(json.success){
							loginWin.destroy();
						}else{
							Ext.Msg.alert('提示',json.info);
						}
					}
				});
			}
		},{
		text : '退出',
		id : 'exit',
		handler : function() {
			    loc_href();
			}
		}]
	});
	loginWin.show();
}

function loc_href(){
	window.location.href = pathUrl+'/login.jsp'
}