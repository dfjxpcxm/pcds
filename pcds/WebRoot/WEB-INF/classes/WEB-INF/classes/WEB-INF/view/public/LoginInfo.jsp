<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.shuhao.clean.apps.sys.entity.SysUserInfo"%>
<%
	//用户信息
	SysUserInfo user = (SysUserInfo)session.getAttribute("currentUser");
	String userId = user.getUserID();
	String userName = user.getUserName();
	String pass=user.getPassword();
	String bankOrgId = user.getBankOrgID();//权限机构
	String ownerOrgId = user.getOwner_org_id();//归属机构
%>
<script type="text/javascript">
	var userId = "<%=userId%>";
	var userName = "<%=userName%>";
	var pwd = "<%=pass%>";
	var bankOrgId = "<%=bankOrgId%>";
	var ownerOrgId = "<%=ownerOrgId%>"; 
</script> 	