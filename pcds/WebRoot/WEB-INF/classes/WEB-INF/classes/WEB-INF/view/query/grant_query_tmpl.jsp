<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<%@ include file="/importHeader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查询模版权限分配</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
  <script type="text/javascript" src="<%= path %>/static/query/js/grant_query_tmpl.js"></script>
  <script type="text/javascript" src="<%= path %>/static/query/js/grant_query_tmpl_layout.js"></script>
  </head>
  <body>
  </body>
</html>