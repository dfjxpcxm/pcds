<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	isELIgnored="false"%>
	<%@ include file="/importHeader.jsp" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>元数据校验维护</title>
		<!-- 清理缓存 -->
	    <meta http-equiv="pragma" content="no-cache"> 
		<meta http-equiv="cache-control" content="no-cache"> 
		<meta http-equiv="expires" content="0">
		<style>
			td{
				font:24px;
			}
			.x-tree-node .category-node{
			    background:#eee;
			    margin-top:1px;
			    border-top:1px solid #ddd;
			    border-bottom:1px solid #ccc;
			    padding-top:2px;
			    padding-bottom:1px;
			}
			.mycls{
				background:#F0F0F0;
				color : #A0A0A0
			}
			
			.x-my-disabled {
			    cursor: default;
			    opacity: .6;
			    -moz-opacity: .6;
			    filter: alpha(opacity=70);
			}
		</style>
		<jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css"
			href="<%= path %>/public/css/arrow.css" />
		<script type="text/javascript"
			src="<%= path %>/public/scripts/ext3.4/ux/RowExpander.js"></script>
		<script type="text/javascript">
			Ext.MessageBox.buttonText.yes = "确定"; 
			Ext.MessageBox.buttonText.no = "取消"; 
			var addWindow=null,editWindow=null;
			
	    </script>
		<%-- 引入键盘控制--%>
		<script type="text/javascript"src="<%= path %>/static/meta/js/metadata_tree_node.js"></script>
		<script type="text/javascript"src="<%= path %>/static/meta/js/AsyncTree.js"></script>
		<script type="text/javascript"src="<%= path %>/static/meta/js/metadata_tree.js"></script>
		<script type="text/javascript"src="<%= path %>/static/meta/js/Selection.js"></script>
		<script type="text/javascript"src="<%= path %>/public/scripts/others/ctrlKeyboard.js"></script>
		<script type="text/javascript"src="<%= path %>/public/scripts/ux/Selector.js"></script>
		<script type="text/javascript"src="<%= path %>/static/validate/js/ChkListPanel.js"></script>
		<script type="text/javascript"src="<%= path %>/static/validate/js/check_rule_config_col.js"></script>
		<script type="text/javascript"src="<%= path %>/static/validate/js/check_rule_config_tab.js"></script>
		<script type="text/javascript"src="<%= path %>/static/validate/js/check_rule_config_layout.js"></script>
	</head>
	<body>
	<div id="center"></div>
	</body>
</html>
