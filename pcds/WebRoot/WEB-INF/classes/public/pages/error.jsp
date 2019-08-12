<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>
<%
String path = request.getContextPath();
//String text = request.getParameter("text");
//System.out.println(text);
//String text1 = URLDecoder.decode(text,"utf-8");
//System.out.println(text1);
//String text2  = new String(text.getBytes("iso-8859-1"),"UTF-8");
//System.out.println(text2);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>error.html</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
    
    <style type="text/css">
    	body{background-color:"F7F5F0"}
    </style>

  </head>
  	<div style="text-align:center;">
  		<div style="float: left"><img alt="" src="<%= path %>/public/images/error.png"></div>
  		<div style="padding-top: 20px;">
  			<div style="text-align:center;position: absolute;top: 35%;left: 40%;">
  				<h2>哎呀，出错了.....</h2><br><br><br>
  				<font face="幼圆" style="font-weight: bold;padd"> 您选择的是目录，不是模板，请重新选择一个模板继续</font>
  			</div>
  		</div>
  	</div>
  <body>
    	
  </body>
</html>