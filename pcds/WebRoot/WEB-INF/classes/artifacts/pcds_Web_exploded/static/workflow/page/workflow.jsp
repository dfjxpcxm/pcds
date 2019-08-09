<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String CurrentRole = String.valueOf(request.getAttribute("CurrentRole"));
	String hasApply = String.valueOf(request.getAttribute("hasApply"));
	String hasApprove = String.valueOf(request.getAttribute("hasApprove"));
	
	String tmplId = (String)request.getAttribute("templateId");
	String workflowId = String.valueOf(request.getSession().getAttribute("workflowId_"+tmplId));
	String approveNodeStr  = (String)request.getAttribute("approveNodeStr");
%>
<script type="text/javascript" src="<%= path %>/static/workflow/js/workflow_common.js"></script>
<script type="text/javascript">
	var workflowId = "<%= workflowId %>";
	var approveNodeStr = '<%=approveNodeStr%>';
	//同步请求判断当前登陆用户类型
	var CurrentRole = "<%= CurrentRole %>";
	var hasApply = "<%= hasApply %>";
	var hasApprove = "<%= hasApprove %>";
	var btnHidden = true; //弹出审批面板或提交面板
	if(workflowId != null && workflowId != '' && workflowId != 'null'){
		if(hasApply == 'N' && hasApprove == 'N'){ //首节点
			Ext.getCmp('flowBar').hide();
			Ext.Msg.alert('提示','你没有该模板审批权限，数据仅供查看');
		}else{
			//有申请权限
	 		if(hasApply == 'Y'){
	 			//显示提交
				Ext.getCmp('doSubmit').show(); 
				Ext.getCmp('doBack').show(); //显示撤回
	 		}else{
	 			//隐藏提交
	 			Ext.getCmp('doSubmit').hide(); 
				Ext.getCmp('doBack').hide(); //隐藏撤回
	 		}
	 		if(hasApprove == 'Y'){
	 			//显示审批
	 			Ext.getCmp('doApprove').show(); 
	 		}else{
	 			//隐藏审批
	 			Ext.getCmp('doApprove').hide(); 
	 		}
		}
	}else{
		Ext.getCmp('flowBar').hide();
		//Ext.Msg.alert('提示','当前功能未配置流程信息');
	}
</script>