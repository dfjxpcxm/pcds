<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>角色-菜单</title>
	<meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <jsp:include page="../public/ImportExt.jsp"></jsp:include>
    <jsp:include page="../public/skin.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.css">
	<script  src="${pageContext.request.contextPath}/public/scripts/dhtmlx/dhtmlxcommon.js"></script>
	<script  src="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.js"></script>
</head>
<body>
	<div id="north"></div>
  	<div id="center" style="overflow: auto;	height: 100%"></div>
</body>
	<script>
		var xml = '${requestScope.xml}';
		var selectRoleID = '${requestScope.selectRoleID}';
		tree=new dhtmlXTreeObject("center","100%","100%","");
		tree.setImagePath("${pageContext.request.contextPath}/public/scripts/dhtmlx/imgs/");
		tree.enableCheckBoxes(1);
		tree.loadXMLString(xml);
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/role_layout.js"></script>
</html>
