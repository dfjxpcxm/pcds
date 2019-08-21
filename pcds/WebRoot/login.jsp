<%@ page contentType="text/html; charset=utf-8" session="false" isELIgnored="false" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>数据补录系统</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
		
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript">
		var pathUrl = "${pageContext.request.contextPath}";
		if (window != top) {
			top.location.href = pathUrl+'/login.jsp';
		}
		
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/login.js"></script>
  </head>
<body>
	<div class="logo"></div>
  	<div class="container">
  		<div class="left"></div>
  		<div class="right">
  			<div class="info"></div>
  			<div class="loginlayout">
  				<div class="input">
  					<div class="name">
  						<input type="text" id="username" value="00000"/>
  					</div>
  					<div class="password">
  						<input type="password" id="password" value="password"/>
  					</div>
  				</div>
  				<div class="button"></div>
  			</div>
  			<div class="loadding"></div>
  		</div>
  	</div> 
</body>
</html>
