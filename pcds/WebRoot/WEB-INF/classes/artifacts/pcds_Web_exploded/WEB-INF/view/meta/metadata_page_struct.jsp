<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<html>
  <head>
    <title>元数据-页面结构</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
  	<script>
  		var metadata_id = '${param.metadata_id}';
  		var md_cate_cd = '${param.md_cate_cd}';
  		var field_list_id = '${param.field_list_id}';
  		var is_show_field_grid = '${param.is_show_field_grid}'; 
  		var is_show_button_grid = '${param.is_show_button_grid}'; 
  	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/selector.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_page_struct_field.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_page_struct_button.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_page_struct_table_tree.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_page_struct.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/meta/js/metadata_page_struct_layout.js"></script>
	</head>
	<body>
	</body>
</html>