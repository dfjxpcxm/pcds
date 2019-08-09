<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<%@ include file="/importHeader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <title>流程管理</title>
    <!--[if lt IE 9]>
	<?import namespace="v" implementation="#default#VML" ?>
	<![endif]-->
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <link rel="stylesheet" type="text/css" href="<%= path %>/static/workflow/css/GooFlow.css"/>
  <link rel="stylesheet" type="text/css" href="<%= path %>/static/workflow/css/default.css"/>
  <jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
  <script type="text/javascript" src="<%= path %>/public/scripts/ext3.4/ux/RowExpander.js"></script>
  <script type="text/javascript" src="<%= path %>/static/workflow/js/workflow_custinfo.js"></script>
  <script type="text/javascript" src="<%= path %>/static/workflow/js/workflow_layout.js"></script>
  <script type="text/javascript" src="<%= path %>/static/workflow/js/workflow.js"></script>
  </head>
  <body>
    <div id="east"></div>
    <div id="center" width="100%" height="100%" style="background-color:white;overflow:hidden;border:0px"></div>
  </body>
</html>
<script type="text/javascript" src="<%= path %>/static/workflow/js/data.js"></script>
<script type="text/javascript" src="<%= path %>/static/workflow/js/jquery.min.js"></script>
<script type="text/javascript" src="<%= path %>/static/workflow/js/GooFunc.js"></script>
<script type="text/javascript" src="<%= path %>/static/workflow/js/json2.js"></script>
<script type="text/javascript" src="<%= path %>/static/workflow/js/base/GooFlow.js"></script>
<script type="text/javascript" src="<%= path %>/static/workflow/js/base/GooFlow_color.js"></script>

