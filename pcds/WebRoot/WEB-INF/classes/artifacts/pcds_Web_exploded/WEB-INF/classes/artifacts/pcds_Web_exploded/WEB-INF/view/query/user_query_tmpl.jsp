<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ include file="/importHeader.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户查询模板展示</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style>
td {
	font: 24px;
}

.x-tree-node .category-node {
	background: #eee;
	margin-top: 1px;
	border-top: 1px solid #ddd;
	border-bottom: 1px solid #ccc;
	padding-top: 2px;
	padding-bottom: 1px;
}
</style>
<jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=path%>/static/query/js/user_query_tmpl_layout.js"></script>
</head>
<body>
	<form action="" id="f_form">
	</form>
	<div id="center">
		<iframe id='center-iframe' name='center-iframe'  style='height:100%;border:0px;'></iframe>
	</div>
</body>
</html>