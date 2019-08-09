<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
	<script type="text/javascript">
    	var pathUrl = "${pageContext.request.contextPath}";
		var extPath = "${pageContext.request.contextPath}/public/scripts/ext3.4";
		Ext.BLANK_IMAGE_URL = extPath + '/resources/images/default/s.gif';
		Ext.QuickTips.init();
		var addWindow=null,editWindow=null;
		var bank_org_id = "${currentUser.bank_org_id}",bank_org_name = "${currentUser.bank_org_name}";	
    </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/selector.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/user_manager_layout.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/user_manager.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/selector.js"></script>
  </head>
  <body>
    <div id="res_tree" style="width:100%;height: 100%;background-color:white;border:0px;"></div>
  </body>
</html>