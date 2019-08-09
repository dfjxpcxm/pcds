<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
  <head>
    <title>流程申请</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.css">
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
	<script>
	</script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/flow/js/bl_apply_layout.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/flow/js/bl_apply.js"></script>
  </head>
  <body>
  	<div id="east"></div>
	<div id="center" ></div>
	<form name="excelForm" id="excelForm" method="post" action="" target=""></form>
  </body>
</html>