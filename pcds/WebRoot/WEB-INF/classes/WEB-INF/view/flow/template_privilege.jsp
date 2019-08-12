<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.shuhao.clean.apps.sys.entity.SysUserInfo"%>
<%
	String path = request.getContextPath();
	SysUserInfo user = (SysUserInfo)session.getAttribute("currentUser");
	String userOrgID=user.getBankOrgID();
%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
<body topmargin="0">

<script type="text/javascript" src="<%= path %>/public/scripts/header.js"></script>

<script type="text/javascript" >
		
	var userOrgID = '<%=userOrgID%>';
	
	var pathUrl = '<%= path %>'; //该变量必须存在
	var searchField = "";
</script>

<script type="text/javascript" src="<%= path %>/public/scripts/ux/tree/ux.AsyncTree.js"></script>
<script type="text/javascript" src="<%= path %>/static/flow/js/template_privilege_layout.js"></script>
<script type="text/javascript" src="<%= path %>/static/flow/js/template_privilege.js"></script>
<script type="text/javascript" src="<%= path %>/public/scripts/ux/Selector.js"></script>

<div id="east"></div>
<div id="center" ></div>
<script> 

 	var	gridStore;
 	var panel;
 	var custMgrID;

</script>

</body>

</html>

