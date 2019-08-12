<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<%@ include file="/importHeader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查询模版</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="<%= path %>/public/scripts/ext3.4/ux/css/RowEditor.css" />
	<jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
	<script type="text/javascript" src="<%= path %>/public/scripts/ext3.4/ux/RowEditor.js"></script>
	<script type="text/javascript" src="<%= path %>/static/query/js/query_tmpl.js"></script>
	<script type="text/javascript" src="<%= path %>/static/query/js/query_tmpl_designer.js"></script>
	<script type="text/javascript" src="<%= path %>/static/query/js/query_tmpl_layout.js"></script>
	<script type="text/javascript" src="<%= path %>/static/query/js/query_tmpl_order.js"></script>
	<style type="text/css">
	<%--设置复选框居中显示--%>
	.x-form-check-wrap{text-align: center;}
	</style>
  </head>
  <body>
    <div id="east"></div>
    <div id="center" width="100%" height="100%" style="background-color:white;overflow:hidden;border:0px"></div>
  </body>
</html>