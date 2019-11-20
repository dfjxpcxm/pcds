<%@ page contentType="text/html; charset=utf-8" isELIgnored="false" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String casUrl = (String)session.getAttribute("casUrl");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/> 
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/jquery/jquery-1.9.1.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/banner.css" />
		<script type="text/javascript">
			var casUrl = '<%=casUrl%>';
			$(function(){
				$('.container').width(screen.width);
				$('.r_bg').width(screen.width-174-20);
				$('.r_bg_r').width(screen.width-154-20-773);
			});
			var pathUrl = '${pageContext.request.contextPath}';
			//修改用户密码
			function modifyPassword(){
			    new parent.MyModifyWindowUi().show(window.top);
			}
			function logout() {
				$.ajax({
					url : pathUrl + '/login/doLogout',
					type : 'POST',
					data : {},
					success : function(data) {
						window.top.location.href = casUrl;
					}
				});
			}
			function switchOrg(){
				window.top.doSwitchOrg()
			}
			function switchYear(){
				window.top.doSwitchYear()
			}
		</script>
	</head>
<body>
	<div class="container">
		<div class="l_logo"></div>
		<div class="r_bg">
			<div class="r_bg_l"></div>
		</div>
	</div>
	<div class="r_bg_r">
		<div class="operator">
			<%--<div class="opt_item mpsw" onclick="modifyPassword()">修改密码</div>--%>
			<div class="opt_item exit" onclick="logout()">退出系统</div>
		</div>
		<div class="logininfo">
			<font color='#FFFFFF'>
			当前用户: ${currentUser.user_name}&nbsp;|&nbsp;
			用户权限: ${currentUser.bank_org_name}&nbsp;|&nbsp; 
			 系统日期:${sysDate}&nbsp;|&nbsp; 
			</font>
		</div>
	</div>
	<script type="text/javascript">
		if(parseInt(window.top.rightOrgNumber) <= 1) {
			document.getElementById("changeOrgSpan").style.display = "none";
		}
	</script>
</body>
</html>
