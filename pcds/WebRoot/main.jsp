<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" isELIgnored="false"
	pageEncoding="utf-8"%>
	<%@ include file="public/pages/LoginInfo.jsp" %>
	<jsp:include page="public/pages/ImportExt.jsp"></jsp:include>
	
	<%@page import="java.util.List"%>
	<%@page import="java.util.Map"%>
	
<%
	List datalist = (List)session.getAttribute("userPages");
	String pageName = "未设置";
	String initPageUrl = "ftp/faqWelcome.jsp";
	if(datalist != null && datalist.size() > 0){
		Map pageMap=(Map) datalist.get(0);
		pageName = (String)pageMap.get("resource_name");
		initPageUrl = (String)pageMap.get("handler");
	}
	Integer login_info = null;
	Object loginums = null;
	Object loginParam = session.getAttribute("changePwd");
	if(loginParam == null){
		loginums = session.getAttribute("login_info_");
		if(loginums != null){
			login_info = Integer.parseInt(loginums.toString());
		}
	}
 %>
<html>
	<head>
		<title>首页</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<meta http-equiv="expires" content="0" />
		
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/public/css/ligerUI/skins/Aqua/css/ligerui-all.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/public/css/menu.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/public/css/main.css" />
	<style type="text/css">
		.menu_div_{
			background-color:#F7F5F0;
			position:absolute;
			width: 151px; 
			max-height::200px;
			overflow:auto; 
			z-index: 1;
			display: none;  
		} 
	</style>
	</head>
	<body>
		<div id='main'>
			<div id="north" class="north_layout">
				<iframe src="banner.jsp" class="banner_frame" frameborder="0"></iframe>
			</div>
			<div id="menu_parent">
				<div class="menu_title_l">
					<div class="menu_title_r">
						<div align="center" id="menu_title_c" class="menu_title_c" title="速索引菜单">
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<input type="hidden" id="menuID">
										<input type="hidden" id="menuPID">
										<input type="hidden" id="menuHanler">
										<input type="text" style="margin-top:6px;border:0px;height:20px;line-height:20px;border-radius: 5px 5px 5px 5px;" onfocus="searchFocus()" onblur="searchBlur()" onkeyup="searchKeyUp()" id="searchKey" value="请输入菜单名称" />
									</td>
								</tr>
								<tr>
									<td>
										<div id="searchMenu_" class="menu_div_">
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			<div id="menu"></div>
			</div>
			<div id='center' class="center_layout">
			</div>
		</div>

		<div id="res_tree"
			style="width: 100%; height: 100%; background-color: white; border: 0px; display: none;"></div>
</body>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/public/scripts/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/public/scripts/ligerUI/base.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/public/scripts/ligerUI/ligerTab.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/public/scripts/ligerUI/ligerMenu.js"></script>
			
	<script type="text/javascript">
		var pathUrl = '${pageContext.request.contextPath}';
		var extPath = '${pageContext.request.contextPath}/public/scripts/ext3.4';
		Ext.BLANK_IMAGE_URL = extPath + '/resources/images/default/s.gif';
		Ext.form.Field.prototype.msgTarget = 'side';
		var sessionId = "<%=session.getId()%>";
		var currentBankOrgID = "${sessionScope.currentUser.bank_org_id}";
		var rightOrgNumber = "${requestScope.rightOrgNumber}";
		//Ext.QuickTips.init();
		Ext.QuickTips.init();
		var height;
		var pageName = '<%=pageName%>';
		var initPageUrl = '<%=initPageUrl%>';
		var loginParam = '<%=loginParam%>';
		var loginums = '<%=loginums%>';
		var login_info = '<%=login_info%>';
	</script>

	
	<%-- <script type="text/javascript" 
		src="${pageContext.request.contextPath}/sys/scripts/ModifyPassword.js"></script> --%>
	
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/public/scripts/others/JQuery.MenuTree.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/public/scripts/menu.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/public/scripts/main.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/public/scripts/mainView.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/public/scripts/initPageLayout.js"></script>
	<!-- </body>
 </html>  -->
<script type="text/javascript">
		function closeLeftMenus(){
			Ext.getCmp('leftMenusPanel').collapse();
		}
		
		function openLeftMenus(){
			Ext.getCmp('leftMenusPanel').expand();
		}
		
		function searchFocus(){
			var value = document.getElementById("searchKey").value;
			if(value == '请输入菜单名称'){
				document.getElementById("searchKey").value = '';
			}
		}
		function searchBlur(){
			var value = document.getElementById("searchKey").value;
			if(value == ''){
				document.getElementById("searchKey").value = '请输入菜单名称';
			}
		}
		
		function searchKeyUp(){
			document.getElementById("searchMenu_").style.display='none';
			var value = document.getElementById("searchKey").value;
			var keycode = event.keyCode;
			 if(keycode == 32 || keycode == 8){
			 	if(document.getElementById("searchKey").value != null && document.getElementById("searchKey").value !=""){
			 		Ext.Ajax.request({
					 	//url: pathUrl +'/resourceExtAjax.do?method=getResourceForSearch',
					 	url: pathUrl +'/sysResource/getResourceForSearch',
					 	method : 'POST',
					 	params : {'searchKey':encodeURI(value)},
					 	callback : function(options,success,response){
					 		 var json = Ext.util.JSON.decode(response.responseText);
					 		 document.getElementById("searchMenu_").innerHTML = json.info;
					 	}
				 	});
			 		document.getElementById("searchMenu_").style.display='block';
			 	}else{
			 		document.getElementById("searchMenu_").style.display='none';
			 	}
			 }
			 if(keycode == 13){
			 	getMenu();
			 }
		}
		
		function checkMouseout(obj){
			obj.style.backgroundColor= '#F7F5F0';
			obj.style.cursor= 'default';
		}
		
		function checkMouseover(obj){
			obj.style.backgroundColor= '#F9881F';
			obj.style.cursor= 'pointer';
		}
		
		
		
		function MenuName(obj){
			document.getElementById("searchKey").value = obj.innerHTML;
			document.getElementById("menuID").value = obj.id;
			document.getElementById("menuPID").value = obj.name;
			document.getElementById("menuHanler").value = obj.alt;
			document.getElementById("searchMenu_").style.display = 'none';
			getMenu();
		}
		
		function getMenu(){
			var value = document.getElementById("searchKey").value;
			var menuID = document.getElementById("menuID").value;
			var menuPID = document.getElementById("menuPID").value;
			var menuHandler = document.getElementById("menuHanler").value;
			var n = menuID.split('_');
			if(n.length == 3){
				DoMenu(n[0]);
				DoSecMenu(menuPID);
				gotoPage(menuID,value,menuHandler,document.getElementById('m_'+menuID));
			}
			if(n.length == 2){
				DoMenu(n[0]);
				gotoPage(menuID,value,menuHandler,document.getElementById('m_'+menuID));
			}
		}
		
		//隐藏菜单快捷查询的下拉框
		 $(document).ready(function () {
			 $("*").click(function (event) {
				 if (!$(this).hasClass("menu_div_")){
					document.getElementById("searchMenu_").style.display = 'none';
				 }
				 event.stopPropagation(); 
			 });
		 }); 
	</script>
</html> 