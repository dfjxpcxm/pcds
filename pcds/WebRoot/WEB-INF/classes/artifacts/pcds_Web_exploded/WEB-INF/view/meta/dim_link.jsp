<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	isELIgnored="false"%>
	<%@ include file="/importHeader.jsp" %>
<%
	String path = request.getContextPath();
	String link_id = request.getParameter("link_id");
	link_id = (null == link_id ? "" : link_id);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>元数据维度维护</title>
		<!-- 清理缓存 -->
	    <meta http-equiv="pragma" content="no-cache"> 
		<meta http-equiv="cache-control" content="no-cache"> 
		<meta http-equiv="expires" content="0">
		<jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css"
			href="<%= path %>/public/css/arrow.css" />
		<script type="text/javascript"
			src="<%= path %>/public/scripts/ext3.4/ux/RowExpander.js"></script>
		<script type="text/javascript">
			Ext.MessageBox.buttonText.yes = "确定"; 
			Ext.MessageBox.buttonText.no = "取消"; 
			var addWindow=null,editWindow=null;
			var link_id_in_dataconfig = "<%= link_id %>";
	    </script>
		<%-- 引入键盘控制--%>
		<script type="text/javascript"
			src="<%= path %>/static/meta/js/AsyncTree.js"></script>
		<script type="text/javascript"
			src="<%= path %>/static/meta/js/dim_link.js"></script>
		<script type="text/javascript"
			src="<%= path %>/static/meta/js/dim_link_layout.js"></script>
		<script type="text/javascript"
			src="<%= path %>/static/meta/js/Selection.js"></script>
		<script type="text/javascript"
			src="<%= path %>/static/meta/js/dim_detail.js"></script>
        <script type="text/javascript"
			src="<%= path %>/public/scripts/map.js"></script>
	</head>
	<body>
	</body>
</html>
