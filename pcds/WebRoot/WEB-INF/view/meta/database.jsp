<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>数据库元数据维护</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.css">
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
	<script  src="${pageContext.request.contextPath}/public/scripts/dhtmlx/dhtmlxcommon.js"></script>
	<script  src="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/resource_layout.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/resource.js"></script>
	<!-- JQUERY -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/jquery/jquery-1.9.1.js"></script>
	<!-- jcrop -->
	<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/public/css/jcrop/jcrop.css" />
	<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/public/css/jcrop/common.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/jquery/jcrop/jcrop-0.9.12.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/database_layout.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/database.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/database_tbspace.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/database_user.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/database_dblink.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/selector.js"></script>

</head>

<body> 
<div id="content">
<div id="north"></div>
<div id="west"></div>
<div id="center"></div>
<div id="east"></div>
</div>
</body>
</html>
