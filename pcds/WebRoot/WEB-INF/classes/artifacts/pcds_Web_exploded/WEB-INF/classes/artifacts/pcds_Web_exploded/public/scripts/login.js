
$(function () {
	//默认居中
	var mtop = (document.documentElement.clientHeight-348)/2;
	$(".logo").css("margin-top",mtop)
	
	var L = {};
	L.clean = function(){
		$.post(pathUrl+'/login/doLogout',function(data){
			
		});
	}
	L.IE = function () {
		$(".container").width(screen.width - 2);
		$(".button").click(L.LA);
		$("#password").keypress(function () {if (event.keyCode == 13) {L.LA();}});
		$("#username").focus();
		
		$("#username").keypress(function() {
			if (event.keyCode == 13) {
				if (!$("#password").val()) {
					$("#password").focus();
					return ;
				}
				L.LA();
			}
		});
	};
	L.LA = function () {
		var p = A();
		if (p) {
			$('.loadding').show();
			$.ajax({url:pathUrl + "/login/doLogin", data:p, type:"POST", success:function (d) {
				if (d.success) {
					window.top.location.href = pathUrl + "/main.jsp";
				} else {
					$('.loadding').hide();
					$(".info").html("用户或密码不正确！");
				}
			}, error:function () {
				$('.loadding').hide();
				$(".info").html("访问异常！");
			}});
		}
		function A() {
			var n = $.trim($("#username").val());
			if (!n) {
				$(".info").html("\u5e10\u53f7\u4e0d\u80fd\u4e3a\u7a7a\uff01");
				return false;
			}
			var p = $.trim($("#password").val());
			if (!p) {
				$(".info").html("\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01");
				return false;
			}
			return {user_id:n,password:p,width : screen.width,height : screen.height};
		}
	};
	L.clean(); //强制清除session
	L.IE();
});

