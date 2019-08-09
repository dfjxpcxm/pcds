<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@page import="com.shuhao.clean.apps.sys.entity.SysUserInfo"%>
<%@page import="com.shuhao.clean.constant.LoginConstant"%>
<%
	String path = request.getContextPath();
	SysUserInfo user = (SysUserInfo)session.getAttribute(LoginConstant.CURRENT_USER);
	String login_user_id = user.getUser_id();
	String approve_role = request.getParameter("approve_role");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
  <head>
    <title>补录审批</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.css">
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
	<script>
	</script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/flow/js/bl_approve_layout.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/flow/js/bl_approve.js"></script>
	<script>
		 var login_user_id = '<%=login_user_id%>';   //当前用户Id
		 var approve_role = '<%=approve_role%>';   //当前用户Id  进入页面的角色   审批角色   复核或终审
	</script>
  </head>
  <body>
  	<div id="east"></div>
	<div id="center" >
	</div>
	<form name="excelForm" id="excelForm" method="post" action="" target=""></form>
  </body>
</html>