<%@page import="com.shuhao.clean.apps.sys.entity.SysUserInfo"%>
<%@page import="com.shuhao.clean.constant.LoginConstant"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
    SysUserInfo user=(SysUserInfo)request.getSession().getAttribute(LoginConstant.CURRENT_USER);
%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
	<script type="text/javascript" src="<%= path %>/public/scripts/header.js"></script>
	<script type="text/javascript" src="<%= path %>/public/scripts/others/miframe.js"></script>
	<script type="text/javascript" src="<%= path %>/public/scripts/ext3.4/ux/TabCloseMenu.js"></script>
	<script type="text/javascript" src="<%= path %>/static/sys/js/manager_dimtable_layout.js"></script>
	<script type="text/javascript" src="<%= path %>/static/sys/js/manager_dimtable.js"></script>
</head>
<body>
<div id="center">
	<div id="cond"></div>
	<div id="list"></div>
</div>
<div id="east"></div>
<script> 
 	var	gridStore;
 	var tableCode;
 	var code;
 	var tableName;
 	var pkName;
 	var fieldsName;
 	//维表的维表-操作
 	function addDimInfo(gridStore)
	{	
		new doAddDimInfo(gridStore);
	}
 	
	function editDimInfo(gridStore,tableCode)
	{
		if(tableCode==''){
			Ext.MessageBox.alert('提示', '请选择要修改的记录！');
			return;
		}
		new doEditDimInfo(gridStore,tableCode);
	}

	function deleteDimInfo(gridStore,tableCode)
	{
		if(tableCode=='')
		{
			Ext.MessageBox.alert('提示', '请选择要删除的记录！');
			return;
		}
		new doDeleteDimInfo(gridStore,tableCode);
	}
</script>
</body>
</html>

