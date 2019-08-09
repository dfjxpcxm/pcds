<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%= path %>/public/css/window/windows.css" />
<link rel="stylesheet" type="text/css" href="<%= path %>/public/css/icon.css" />
<%@ include file="../../public/ImportExt.jsp"%>
<%@ include file="../../public/skin.jsp"%>
<script type="text/javascript" src="<%= path %>/static/sys/js/angle/app_type_term_layout.js"></script>
<script type="text/javascript" src="<%= path %>/static/sys/js/angle/app_type_term.js"></script>
<script>
	var pathUrl = '<%= path %>'; //该变量必须存在
</script>
</head>
<body>
<div id="west"></div>
<div id="center">
<div id="terms"></div>
</div>
</body>
</html>
