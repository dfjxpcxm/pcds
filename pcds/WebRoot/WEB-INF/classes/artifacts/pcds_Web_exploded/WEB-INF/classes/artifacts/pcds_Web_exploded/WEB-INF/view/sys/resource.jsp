<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
  <head>
    <title>系统菜单</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.css">
	<jsp:include page="../public/skin.jsp"></jsp:include>
	<jsp:include page="../public/ImportExt.jsp"></jsp:include>
	<script  src="${pageContext.request.contextPath}/public/scripts/dhtmlx/dhtmlxcommon.js"></script>
	<script  src="${pageContext.request.contextPath}/public/scripts/dhtmlx/tree/dhtmlxtree.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/resource_layout.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/resource.js"></script>
	<!-- JQUERY -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/jquery/jquery-1.9.1.js"></script>
	<!-- jcrop -->
	<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/public/css/jcrop/jcrop.css" />
	<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/public/css/jcrop/common.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/scripts/jquery/jcrop/jcrop-0.9.12.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/crop_img_layout.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/sys/js/crop_img.js"></script>
  </head>
  <body>
  	<div id="north"></div>
  	<div id="center" style="overflow: auto;	height: 100%"></div>
	<script type="text/javascript">
		var pathUrl = "${pageContext.request.contextPath}";
		var extPath = "${pageContext.request.contextPath}/public/scripts/ext3.4";
		Ext.BLANK_IMAGE_URL = extPath + '/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.Ajax.request({
			url:pathUrl + "/sysResource/tree",
			callback:function(options,success,response){
				var json = Ext.util.JSON.decode(response.responseText);
				if(json.success){
					tree=new dhtmlXTreeObject("center","100%","100%","");
					tree.setImagePath("${pageContext.request.contextPath}/public/scripts/dhtmlx/imgs/");
					tree.loadXMLString(json.info);//'${requestScope.xml}'
					tree.setOnDblClickHandler(editNode);
				}else{
					Ext.Msg.alert('提示',json.info);
				}
			}
		});
  		
		
    </script>
    
    <script type="text/javascript">
    	//使用jcrop相关
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
	<!-- Jcrop相关  start -->
	
  	<div id="cropDiv" align="center"  style="display:block;" >
           
    </div>
    
    <!-- Jcrop相关  end -->
  </body>
</html>
