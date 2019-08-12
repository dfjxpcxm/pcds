function showArea(ofLeft,ofTop){
	$("#addr").remove();
	var iptName="location";
	var iptOffSet=500;
	var iptLeft=ofLeft+350;
	var iptTop=ofTop+150;
	var str="<div id='addr'><span>请选择地点<a id='fh'>返回省份列表</a><a id='gb'>[&nbsp;关闭&nbsp;]</a></span><ul></ul><div style='clear:both;'></div></div>";
	areasLen=provinceArr.length;
	var str2="";
	for(var i=0;i<areasLen;i++){
		str2=str2+"<li id='P"+provinceArr[i][0]+"'>"+provinceArr[i][1]+"</li>";
	}
	$("body").append(str);
	$("#addr ul").append(str2);
	$("#addr").css({left:iptLeft+"px",top:iptTop+"px"});
	$("#addr span a#fh").bind("click",function(){
		$("#addr ul").empty();
		$("#addr ul").append(str2);
		$("#addr ul li").bind("click",{iptn:iptName},liBind);
	});
	$("#addr span a#gb").bind("click",function(){
		$("#addr").remove();
	});
	$("#addr ul li").bind("click",{iptn:iptName},liBind);
	
//	alert($("#addr").css("display"))
}
function setVal(event){
	var setipt2=event.data.ipt2;
	var iptText=$(this).text();
	var iptVal=$(this).attr("id");
	$("#"+setipt2+"_code").val(iptVal);//$("#"+setipt2+"CD").val(iptVal);
	$("#"+setipt2).val(iptText);
	$("#addr").css("display","none");
}
function liBind(event){
	var setipt=event.data.iptn;
	var liId=$(this).attr("id");
	var liText=$(this).text();
	var pArr=eval(liId);
	pLen=pArr.length;
	if(pLen==0){
		$("#"+setipt+"_code").val(liId.substring(1));//$("#"+setipt+"CD").val(liId.substring(1));
		$("#"+setipt).val(liText);
		$("#addr").css("display","none");
		}
	else{
		listr="";
		for(j=0;j<pLen;j++){
			listr=listr+"<li id='"+pArr[j][0]+"'>"+pArr[j][1]+"</li>";
			}
			$("#addr ul").empty();
			$("#addr ul").append(listr);
			$("#addr ul li").bind("click",{ipt2:setipt},setVal);
		}
}

/**
 * 获取地区名称
 * 前提：严格的名称变量定义
 */
function getLocationName(location_code){
	var location_name = '';
	for(var i = 0;i<provinceArr.length;i++){
		if(provinceArr[i][0] == location_code){
			return provinceArr[i][1];
		}
		var tempObj = window['P'+provinceArr[i][0]];
		for(var j = 0;j<tempObj.length;j++){
			if(tempObj[j][0] == location_code){
				return tempObj[j][1];
			}
		}
	}
	
	return location_name;
}