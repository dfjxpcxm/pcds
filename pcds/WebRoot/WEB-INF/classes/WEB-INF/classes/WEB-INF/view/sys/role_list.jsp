<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 	<head>
	    <title>角色管理</title>
	    <meta http-equiv="pragma" content="no-cache">
    	<meta http-equiv="cache-control" content="no-cache">
		<jsp:include page="../public/skin.jsp"></jsp:include>
		<jsp:include page="../public/ImportExt.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.css">
		<script  src="${pageContext.request.contextPath}/public/scripts/dhtmlx/dhtmlxcommon.js"></script>
		<script  src="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.js"></script>
	</head>
	<body>
		<div id="west"></div>
		<div id="center">
			<iframe id='center-iframe' name='center-iframe' style='width:100%;height:100%;border:0px;'></iframe>
		</div>
		<form name="roleTreeForm" method="post" target="center-iframe" />
	</body>
	<script>
		var addWindow=null,editWindow=null;	
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/role_list_layout.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/role_list.js"></script>
</html>