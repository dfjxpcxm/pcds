<%@page import="java.io.PrintStream"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>
<!DOCTYPE html>
<html>
<head>
	<title>500 - 系统内部错误</title>
	<script type="text/javascript">
		function showError(){
			if(document.getElementById('errorMsg').style.display=='none'){
				document.getElementById('errorMsg').style.display = 'inline';
			}else{
				document.getElementById('errorMsg').style.display = 'none';
			}
		}
	</script>
</head>
<body>
	<h2>500 - 系统发生内部错误.</h2>
	<hr />
	<div><a href="#" onclick="showError();">显示详情</a></div>
	<div id='errorMsg' style="display: none;font-size: 14px;">
	<% 
		ByteArrayOutputStream ostr = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(ostr)); 
		out.print(ostr);
	%>
	</div>
</body>
</html>