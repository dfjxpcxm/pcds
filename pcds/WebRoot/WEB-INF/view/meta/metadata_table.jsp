<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<html>
  <head>
    <title>元数据--表</title>
    <meta http-equiv="pragma" content="no-cache">
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/ext3.4/ux/CheckColumn.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_table.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_table_col_order.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_table_layout.js"></script>
  	<script>
  		var metadata_id = '${param.metadata_id}';
  	</script>
	</head>
	<body>
	</body>
</html>