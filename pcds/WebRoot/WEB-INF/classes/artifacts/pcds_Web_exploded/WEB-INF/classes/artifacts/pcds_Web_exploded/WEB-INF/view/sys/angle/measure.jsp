<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%= path %>/public/css/window/windows.css" />
<style>
td{
	font:24px;
}
.x-tree-node .category-node{
    background:#eee;
    margin-top:1px;
    border-top:1px solid #ddd;
    border-bottom:1px solid #ccc;
    padding-top:2px;
    padding-bottom:1px;
}
</style>
<%@ include file="../../public/ImportExt.jsp"%>
<%@ include file="../../public/skin.jsp"%>
<script type="text/javascript" src="<%= path %>/static/sys/js/selector.js"></script>
<script type="text/javascript" src="<%= path %>/static/sys/js/angle/measure_layout.js"></script>
<script type="text/javascript" src="<%= path %>/static/sys/js/angle/measure.js"></script>
<script type="text/javascript" src="<%= path %>/static/sys/js/angle/measure_tree.js"></script>
<script>
	var pathUrl = '<%= path %>'; //该变量必须存在
</script>
</head>
<body>
<div id="north"></div>
<div id="center" style="height:100%;overflow:auto;"></div>
</body>
</html>