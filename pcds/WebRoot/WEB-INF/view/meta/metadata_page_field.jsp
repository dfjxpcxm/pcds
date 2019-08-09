<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<html>
  <head>
    <title>元数据-页面按钮</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/selector.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_page_field.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_page_field_layout.js"></script>
  	<script>
  		var metadata_id = '${param.metadata_id}';
  	</script>
	</head>

	<body>
	</body>
</html>