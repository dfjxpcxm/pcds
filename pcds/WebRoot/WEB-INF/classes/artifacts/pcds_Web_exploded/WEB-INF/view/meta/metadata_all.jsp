<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<html>
  <head>
    <title>元数据</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
	<jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/selector.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_add.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_add_page_tree.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_add_page.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_all.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_all_layout.js"></script>
	</head>

	<body>
		<iframe id="center" style="width: 100%;height: 100%" src="" frameborder="0"></iframe>
	</body>
</html>