<%@page import="com.shuhao.clean.constant.LoginConstant"%>
<%@page import="com.rx.security.domain.IUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String dsId = (String)request.getAttribute("dsId");
String extCode = (String)request.getAttribute("extCode");
IUser user = (IUser)request.getSession().getAttribute(LoginConstant.CURRENT_USER);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>元素数据展示功能</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%= path %>/public/scripts/ext3.4/ux/css/RowEditor.css" />
	<jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
	<style type="text/css">
		.x-grid3-hd-row TD {
			height: 21px;
			font-family: arial;
			font-size: 12px;
			border: 1px solid;
			border-color: white Gray Gray white;
			text-align: center;
			margin: 0px;
			font-weight: normal;
			-moz-user-select: none;
			overflow: hidden;
		}
		
		.x-grid3-hd-inner {
			width: auto;
			padding:1px;
			line-height:14px;
			white-space: nowrap;
			font-family: Arial;
			font-size: 12px;
			font-weight: bold;
		}
	</style>
	<script type="text/javascript" src="<%= path %>/public/scripts/ext3.4/ux/RowEditor.js"></script>
  </head>
  <body>
    <div id="north"></div>
	<div id="center" width="100%" height="100%" style="background-color:white;overflow:hidden;border:0px"></div>
	<form id="expForm" name="expForm">
		<input type="hidden" id="baseparams" name="baseparams"/>
	</form>
  </body>
</html>
<script type="text/javascript" src="<%= path %>/static/query/js/user_query_fn_config.js"></script>
<script type="text/javascript" src="<%= path %>/static/query/js/user_query_fn.js"></script>
<script  type="text/javascript">
	//查询模版ID
	var userDsId = '<%=dsId%>';
	//当前用户
	var curtUserId  = '<%= user.getUserID() %>';
	
    <%=extCode%>
	
	Ext.onReady(function(){	
		new Ext.Viewport({
	    layout : 'fit',
	    border : false,
	    items: [{
	    		id : 'bodyPanel',
				xtype : 'panel',
				border : false,
				layout : 'fit',
				tbar:configTbar,
				items : [mainpanel]
	    	}]
	    });
	});
</script>