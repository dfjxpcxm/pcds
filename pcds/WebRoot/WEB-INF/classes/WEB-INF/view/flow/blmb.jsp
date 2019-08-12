<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/importHeader.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
		 <!--[if lt IE 9]>
		<?import namespace="v" implementation="#default#VML" ?>
		<![endif]-->
		<!-- 清理缓存 -->
	    <meta http-equiv="pragma" content="no-cache"> 
		<meta http-equiv="cache-control" content="no-cache"> 
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/workflow/css/GooFlow.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/workflow/css/default.css"/>
		<jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/ux/tree/ux.AsyncTree.js"></script>
	  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/flow/js/blmb_layout.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/flow/js/blmb.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/workflow/js/data.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/workflow/js/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/workflow/js/GooFunc.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/workflow/js/json2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/workflow/js/base/GooFlow.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/workflow/js/base/GooFlow_color.js"></script>
		<style  type="text/css">
			.ext-el-mask {
				background:#E8FBFF;
				z-index: 100;
			    position: absolute;
			    top:0;
			    left:0;
			    -moz-opacity: 0.5;
			    opacity: .50;
			    filter: alpha(opacity=1);
			    width: 100%;
			    height: 100%;
			    zoom: 1;
			}
		</style>
	</head>
	<body>
    <div id="east"></div>
    <div id="center" width="100%" height="100%" style="background-color:white;overflow:hidden;border:0px"></div>
	<form id="expForm" name="expForm" action="${pageContext.request.contextPath}/pageManager/downInitSql">
	</body>
</html>
