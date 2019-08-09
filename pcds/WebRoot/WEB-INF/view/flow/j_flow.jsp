<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程图</title>
<?import namespace="v" implementation="#default#VML" ?>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/j_flow/css/GooFlow2.css" />
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
<!--<link rel="stylesheet" type="text/css" href="codebase/GooFlow.css"/>-->
<style>
.myForm {
	display: block;
	margin: 0px;
	padding: 0px;
	line-height: 1.5;
	border: #ccc 1px solid;
	font: 12px Arial, Helvetica, sans-serif;
	margin: 5px 5px 0px 0px;
	border-radius: 4px;
}

.myForm .form_title {
	background: #428bca;
	padding: 4px;
	color: #fff;
	border-radius: 3px 3px 0px 0px;
}

.myForm .form_content {
	padding: 4px;
	background: #fff;
}

.myForm .form_content table {
	border: 0px
}

.myForm .form_content table td {
	border: 0px
}

.myForm .form_content table .th {
	text-align: right;
	font-weight: bold
}

.myForm .form_btn_div {
	text-align: center;
	border-top: #ccc 1px solid;
	background: #f5f5f5;
	padding: 4px;
	border-radius: 0px 0px 3px 3px;
}

#propertyForm {
	float: right;
	width: 260px
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/j_flow/js/data.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/j_flow/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/j_flow/js/GooFunc.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/j_flow/js/json2.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/public/j_flow/css/default.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/j_flow/js/GooFlow.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/j_flow/js/GooFlow_color.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/flow/js/j_flow_layout.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/flow/js/j_flow.js"></script>
<script type="text/javascript">

	var pathUrl = '<%= path %>';
	var property = {
		width : 900,//850
		height : 540,
		//toolBtns : [ "start", "end", "task", "node", "chat", "state", "plug", "join", "fork", "complex mix" ],
		toolBtns : [ "start", "end", "task","fork","join",  "pass","back"],
		haveTool : true,
		haveHead : true,
		//headBtns : [ "new", "open", "save", "undo", "redo", "reload","grant" ],//如果haveHead=true，则定义HEAD区的按钮
		headBtns : [ "new", "save", "undo", "redo", "reload","grant" ],//如果haveHead=true，则定义HEAD区的按钮
		haveGroup : true,
		useOperStack : true
	};
	
	var demo;
	var remark = {
		pass : "通过",
		back : "退回",
		cursor : "选择指针",
		direct : "转换连线",
		start : "开始结点",
		"end" : "结束结点",
		"task" : "任务结点",
		node : "自动结点",
		chat : "决策结点",
		state : "状态结点",
		plug : "附加插件",
		fork : "分支结点",
		"join" : "联合结点",
		"complex mix" : "复合结点",
		group : "组织划分框编辑开关",
		save:"保存"
	};
	
	$(document).ready(function() {
		init();
	});
	
	var out;
	function Export() {
		document.getElementById("result").value = JSON.stringify(demo.exportData());
	}
</script>
</head>
<body style="background:#EEEEEE">
	<div id="center">
		<div id="flow" style="margin:5px;float:left"></div>
	</div>
	<div id="east"></div>
</body>
</html>
