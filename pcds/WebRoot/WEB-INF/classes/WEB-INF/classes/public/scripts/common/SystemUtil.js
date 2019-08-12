/**
 * 初始化用户桌面图标
 */
function initDesktop(){
	Ext.Ajax.request({
		url :  pathUrl+'/sys/userInfoAction_initDesktop.action',
		method : 'POST',
		callback : function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			$('#x-shortcuts').html('');
			$('#x-shortcuts').html(json.data);
		}
	});
}

/**
 * 退出登录
 * @param {} isLogout  是否为正常退出
 */
function logout(isLogout){
	if(isLogout){ //正常退出需要提示
		Ext.Msg.confirm('提示','你确定要退出登录吗?',function(btn){
			if (btn == 'yes') {
				userLogout(loc_href());
			}
		});
	}else{  //锁屏
		userLogout(safeLock());
	}
}

/**
 * 用户退出
 * @param {} callbackFunc
 */
function userLogout(callbackFunc){
	Ext.Ajax.request({
		url: pathUrl+'/login/doLogout',
		method: 'POST',
		callback: function (options, success, response) {
			var json = Ext.util.JSON.decode(response.responseText);
			if (json.success) {
				callbackFunc;
			} else {
				Ext.MessageBox.alert('提示','用户注销失败!');
			}
		}
	});
}


/**
 * 锁定系统
 * @type 
 */
var lockwindow ;

function safeLock() {
	var html=
	 '<table width="300" height="110" style ="background:#B0B0AD;" z-index:100000; border="0" cellpadding="0" cellspacing="0">'
	+'	<tr>'
	+'		<td width="54" rowspan="2"><img width="106" height="100" src="public/images/login/lock.jpg" /></td>'
	+'		<th height="20"><strong><font style="font-size:16px;" color="red" id="info"></font></strong></th>'
	+'	</tr>'
	+'	<tr>'
	+'		<td>'
	+'			<table width="185" height="27" border="0">'
	+'			   <tr height="25">'
	+'					<td colspan="2" style="font-size:14px; font-style: italic">请输入密码：</td>'
	+'			   </tr>'
	+'             <tr width="182">'
	+'					<td colspan="2"><input type="password" size="25" title="登录密码" name="password" id="password" /></td>'
	+'             </tr>'
	+'			   <tr height="9">'
	+'					<td width="100" align="right"></td>'
	+'					<td align="center"></td>'
	+'			   </tr>'
	+'			   <tr height="25">'
	+'				    <td width="100" align="right"><input type="button" onclick="removeSafeLock()" value="确定"/></td>'
	+'					<td align="center"><input type="button" onclick="reSet()" value="重置"/></td>'
	+'			   </tr>'
	+'			</table> '
	+'      </td>'
	+'	</tr>'
	+'</table>';
	
lockwindow = new Ext.Window({
	height : 115,
	width : 305,
	layout:"fit", 
	modal:true, 
	frame : false,
	draggable:false, 
	closable:false,
	resizable:false,
	html : html
	});
	lockwindow.show();
}

/**
 * 解锁
 */
function removeSafeLock(){
	var password = $("#password").val();
	if(password == null || password == ''){
			$("#info").html( "密码不能为空!");
			return;
	}else{
		Ext.Ajax.request({
			url: pathUrl+'/login/doLogin',
			method: 'POST',
			params: {user_id:user_id,password: password},
			callback: function (options, success, response) {
				var json = Ext.util.JSON.decode(response.responseText);
				if (json.success) {
					lockwindow.destroy();
				} else {
					$("#info").html("密码错误!");
				}
			}
		});
	}
}

function reSet(){
	$("#password").val('');
	$("#info").html("");
}



/**
 * 等待提示框
 */
function showMessageBox(){
	Ext.MessageBox.show({
           msg: '通讯中断,正在连接...',
           width:300,
           wait:true,
           waitConfig: {interval:400},
           animEl: 'mb7'
       });
}

var isMask = false;   //滚动条状态
var setTime = 5000;   //探测间隔时间   单位：ms
var waitTime = 0;     //会话计数器
var messageTime = 0;  //邮件计数器
var sessionTimeOut = 30*60*1000    //session会话超时时间   30 min
var messageFlashTime = 2*60*1000   //邮件接收定时器     2 min

function checkLogin(){
	initDesktop();            //加载桌面
	getSysMessage();     //加载邮件信息
	window.setTimeout(getServerStatus,setTime);
}

function getServerStatus(){
	if(isMask){
		waitTime += setTime;
	}else{
		messageTime += setTime;
	}
	if(messageTime == messageFlashTime){
		messageTime = 0; //重置邮件计数器
		//调用邮件通知服务
		getSysMessage();
	}
	Ext.Ajax.request({
			url: pathUrl+'/TestServer.jsp',
			method: 'POST',
			timeout: setTime,
			callback: function (options, success, response) {
				if (200!=response.status) {
					if(!isMask){
						showMessageBox();
						isMask = true;
					}
				}else{
					if(isMask){
						Ext.MessageBox.hide();
						isMask = false;
						if(waitTime < sessionTimeOut){   //判断session是否超时  默认session会话时间为30分钟  30×60×1000   单位：ms
							Ext.Ajax.request({
								url: pathUrl+'/sys/loginAction_doLogin.action',
								method: 'POST',
								params: {user_id:user_id,user_pass: user_pass},
								callback: function (options, success, response) {
									var json = Ext.util.JSON.decode(response.responseText);
									if (!json.success) {
										callBackMsg('提示','数据加载失败,请重新登录',userLogout(loc_href()));
									}else{
										waitTime = 0;     //重置会话计数器
									}
								}
							});
						}else{
							//用户手动登录
							logout(false);
						}
					}else{
						if(response.responseText != null && response.responseText != '' ){
							if(!lockwindow){
								callBackMsg('提示','账号已在其他地方登录，您被迫下线!',loc_href);
							}
						}
					}
				}
					window.setTimeout(getServerStatus,setTime);
			}
		});
}
/**
 * 扫描系统邮件信息 
 */
function getSysMessage(){
	Ext.Ajax.request({
		url: pathUrl+'/sys/messageAction_getAllReceiveMsgCounts.action',
		method: 'POST',
	    params: {user_id:user_id},
	    success:function(result){
	    	var data = Ext.decode(result.responseText);
	    	if(data.totalCount > 0){
	    		$('#M_02-msgCounts').html('<br/><font style="color: #FF0000;">'+data.totalCount+'</font>');
	    		$('#M_02-msgCounts').attr('title','你有'+data.totalCount+'封新邮件');
	    	}else{
	    		$('#M_02-msgCounts').attr('title','无邮件');
	    	}
	    }
	});
}
/**
 * 带回掉函数的信息框
 * @param {} title
 * @param {} info
 * @param {} fun
 */
function callBackMsg(title,info,fun){
	Ext.Msg.show({
       title: title,
       msg: info,
       buttons: Ext.MessageBox.OK,
       fn: fun,
       animEl: 'mb3'
   });
}
/**
 * 警告框
 * @param {} info
 */
function warnMsgBox(info){
	Ext.MessageBox.show({
       title: '警告',
       msg: info,
       buttons: Ext.MessageBox.OK,
       animEl: 'mb9',
       icon: Ext.MessageBox.WARNING
   });
}

function loc_href(){
	window.location.href = pathUrl+'/login.jsp'
}