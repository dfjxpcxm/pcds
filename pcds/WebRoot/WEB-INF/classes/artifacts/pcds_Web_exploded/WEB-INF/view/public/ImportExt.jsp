<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/scripts/ext3.4/resources/css/light-blue.css" />
<style>
	.uxHeight {
		margin-top: 12px;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/ext3.4/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/ext3.4/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/ext3.4/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/others/ctrlKeyboard.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/commonExt.js"></script>
<script type="text/javascript">
	Ext.BLANK_IMAGE_URL  =  '${pageContext.request.contextPath}/public/scripts/ext3.4/resources/images/default/s.gif';	
	Ext.QuickTips.init(); 
	Ext.form.Field.prototype.msgTarget  =  'side';
	var pathUrl = '${pageContext.request.contextPath}';
	var PANEL_DISPLAY_MODE = parent.PANEL_DISPLAY_MODE || '0'; //默认版面样式
	var defaultAnchor = '81%'; //默认组件宽度
</script>
