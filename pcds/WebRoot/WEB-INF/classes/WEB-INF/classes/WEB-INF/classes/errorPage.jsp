<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/importHeader.jsp" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>错误</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		.wrapper{
			width:100%;
			height:100%;
		}
		.content{
			width:100%;
			margin-top:100px;
			
		}
		.leftCon{
			float:left;
			width:57%;
			height:372px;
			text-align:right;
		}
		.rightCon{
			float:right;
			width:42%;
			height:252px;
			text-align:left;
			padding-top:'130px';
		}
		
		.leftImg{
			padding-right:115px;
		}
		
		.rightImg{
			padding-left:115px;
			border:0px;
		}
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript">
			var pathUrl = '${pageContext.request.contextPath}';
			function logout() {
				$.ajax({
					url : pathUrl + '/login/doLogout',
					type : 'POST',
					data : {},
					success : function(data) {
						window.top.location.href = "/bsbopm/login.jsp";
					}
				});
			}
		</script>
  </head>
  
  <body>
   <div class="wrapper">
            <div class="content"> 
             	<div class="leftCon"><img class ='leftImg' src="public/images/error/error01.png" ></img></div>
             	<div class=rightCon>
             		<img class='rightImg' src="public/images/error/error05.png" ></img>
	             	<div>
	             		<a href='javascript:logout();' ><img class='rightImg' src="public/images/error/error06.png" ></img></a>
	             	</div>
               </div>
            </div>
    </div>
  </body>
</html>
