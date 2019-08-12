<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN">

<html>
  <head>
    <title>截图页面</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/scripts/ext3.4/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/icon.css" />

	<%@ include file="../public/skin.jsp"%>
	<!-- Ext -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/ext3.4/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/ext3.4/ext-all.gzjs"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/ext3.4/locale/ext-lang-zh_CN.js"></script>

	<!-- JQUERY -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/jquery/jquery-1.9.1.js"></script>
	<!-- jcrop -->
	<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/public/css/jcrop/jcrop.css" />
	<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/public/css/jcrop/common.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/jquery/jcrop/jcrop-0.9.12.js"></script>
	
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/crop_img_layout.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/crop_img.js"></script>

	<script type="text/javascript">
  		var pathUrl = "${pageContext.request.contextPath}";
    </script>
    <script type="text/javascript">
		if ((typeof Range !== "undefined") && !Range.prototype.createContextualFragment)
		{
		    Range.prototype.createContextualFragment = function(html)
		    {
		        var frag = document.createDocumentFragment(), 
		        div = document.createElement("div");
		        frag.appendChild(div);
		        div.outerHTML = html;
		        return frag;
		    };
		}
	</script>
  </head>
  <body>
   <div id="buttons" style="margin:20px;"></div>
   <!-- 
   <div id="resultDiv"  class="crop_preview" style="display:none;width:48px; height:48px; overflow:hidden;">
   		<img  id="crop_preview" /> 
   </div>
    -->
   <div id = "uploadForm" style="display:none;">
   		<form id="upForm"> 请选择文件:<input type="file" name="file1"></form>
   </div>
   
  </body>
</html>
