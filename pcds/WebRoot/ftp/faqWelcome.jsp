<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'faqWelcome.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <body>
    <div style="text-align:center;">
        <div style="float:left;background-size:100% 100%;background-repeat-x: no-repeat;"><img alt="" src="<%= path %>/public/images/main.jpg"></div>
        <div style="padding-top: 20px;">
            <div style="text-align:center;position: absolute;top: 35%;left: 40%;">
                <h2>欢迎您.....</h2><br><br><br>
                <font face="数据录入系统" style="font-weight: bold;padd">数据录入系统</font>
            </div>
        </div>
    </div>
  </body>
</html>
