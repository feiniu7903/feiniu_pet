<script type="text/javascript">
$(function(){
	$('#loginBtn').click(function(){
		if ($('#loginName').val() == "" || $('#loginName').val() == "用户名/手机号/Email/会员卡") {
			alert('请输入用户名');
			return;
		}
		if ($('#password').val() == "") {
			alert('请输入密码');
			return;
		}
		$.getJSON("http://login.lvmama.com/nsso/ajax/login.do?mobileOrEMail=" + $('#loginName').val()  + "&password=" + $('#password').val() + "&jsoncallback=?" ,function (data){ 
			if (data.success) {
				window.location.href = $('#targetUrl').val();
			} else {
				alert("用户名密码出错，请重新登录");
			}
		}); 
	});
});

$(function(){
	$(".close").click(function(){
		$("#log_layer").hide();
		parent.document.getElementById("ban_bg").style.display = "none";
	    parent.document.getElementById("div_comment").style.display = "none";
	   
	});
});
</script>

<script type="text/javascript"> 
$(window).load(function(){ 
var frame_name="iframeId"; //iframe的id名称, iframe紧贴外框div高度
var body_name="log_layer"; //潜入的div的id名称
return iframeResizeHeight(frame_name,body_name,0); 
}) 
function iframeResizeHeight(frame_name,body_name,offset) { 
parent.document.getElementById(frame_name).height=document.getElementById(body_name).offsetHeight+offset; 
} 
</script>
