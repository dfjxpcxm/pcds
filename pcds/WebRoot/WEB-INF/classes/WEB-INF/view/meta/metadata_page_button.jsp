<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
  <head>
    <title>元数据-页面按钮</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <style type="text/css">
		.p_help {
			margin-top: 10px;
			margin-left: 32px;
			font-size: 12px;
			color: red;
		}
	</style>
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
  	<script>
  		var metadata_id = '${param.metadata_id}';
  		var showRelyMeta = '${param.showRelyMeta}';
  	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/selector.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_page_button.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_page_button_layout.js"></script>
	</head>
	<body>
	</body>
</html>