<%@page import="com.shuhao.clean.constant.LoginConstant"%>
<%@page import="com.rx.security.domain.IUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String templateId = (String)request.getAttribute("templateId");
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <jsp:include page="/public/pages/ImportExt.jsp"></jsp:include>
  <script type="text/javascript" src="<%= path %>/public/scripts/ext3.4/ux/RowExpander.js"></script>
  <body>
    <div id="north"></div>
	<div id="center" width="100%" height="100%" style="background-color:white;overflow:hidden;border:0px"></div>
	<form id="expForm" name="expForm">
	<input type="hidden" id="templateId" name="templateId"/>
	<input type="hidden" id="baseparams" name="baseparams"/>
	<input type="hidden" id="hasApprove" name="hasApprove"/>
	</form>
  </body>
</html>
<script type="text/javascript" src="<%= path %>/static/meta/js/bck_track_func.js"></script>
<script type="text/javascript" src="<%= path %>/static/meta/js/simple_acct_allot.js"></script>
<script  type="text/javascript">
	//缓存模版ID
	var templateId = '<%=templateId%>';
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
				tbar : flowBar,
				items : [tbpanel]
	    	}]
	    });
	});
</script>
<jsp:include page="/static/workflow/page/workflow.jsp"></jsp:include>