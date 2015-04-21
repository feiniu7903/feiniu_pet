<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息推送管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<style type="text/css">
.notificationLabel{
	width:100px;
	clear:left;
	float:left;
	text-align: center;
	margin-top:10px;
}
.contentLabel {
	height:100px;
	line-height:100px;
}
.urlLabel {
	height:30px;
	line-height:30px;
}
.notificationInfo {
	margin-top:10px;
	width: 50%;
	float:left;
}
#notificationContent {
	height: 100px;
	width:100%;
}
#notificationUrl {
	width:100%;
}
#notificationTitle {
	width:100%;
}
#deviceToken{
	width:100%;
}
input {
	width:100px;
	height:30px;
}
input[name=notificationUrl] {
	
}
input[type=button] {
	padding:5px 5px;
}
.notificationBtn {
	clear:left;
	margin-left:90px;
	padding:10px 10px;
}
</style>
<script type="text/javascript">
	$(function(){
		$("#notificationSubmit").click(function(){
			var notificationTitle = $("#notificationTitle").val();
			var notificationContent = $("#notificationContent").val();
			var notificationUrl = $("#notificationUrl").val();
			var notificationType = $("#notificationType").val();
			var deviceType = $("#deviceType").val();
			
			$(this).attr('disabled',true);
			
			var data = {
					"notificationTitle":notificationTitle,
					"notificationContent":notificationContent,
					"notificationUrl":notificationUrl,
					"notificationType":notificationType,
					"deviceType":deviceType
			}
			$.ajax({
			   type: "POST",
			   url: "/pet_back/push/notificationPush.do",
			   data: data,
			   success: function(jsonObj){
				 $("#notificationSubmit").attr('disabled',false);
			     alert($.parseJSON(jsonObj).description);
			   }
			});
		});
		
		$("#onlineNotificationSubmit").click(function(){
			var notificationTitle = $("#notificationTitle").val();
			var notificationContent = $("#notificationContent").val();
			var notificationUrl = $("#notificationUrl").val();
			var notificationType = $("#notificationType").val();
			var deviceType = $("#deviceType").val();
			var deviceToken = $("#deviceToken").val();
			
			$(this).attr('disabled',true);
			
			var data = {
					"notificationTitle":notificationTitle,
					"notificationContent":notificationContent,
					"notificationUrl":notificationUrl,
					"notificationType":notificationType,
					"deviceType":deviceType,
					"deviceToken":deviceToken
			}
			$.ajax({
			   type: "POST",
			   url: "/pet_back/push/notificationPush.do",
			   data: data,
			   success: function(jsonObj){
				 $("#onlineNotificationSubmit").attr('disabled',false);
			     alert($.parseJSON(jsonObj).description);
			   }
			});
		});
	});
</script>
</head>
<body>
	<div>
		<ul>
			<li>
				<div class="notificationBtn"><h3>驴妈妈客户端信息推送</h3></div>
			</li>
			<li>
			<div class="urlLabel notificationLabel">推送标题</div>
			<div class="notificationInfo">
			<input id="notificationTitle" name="notificationTitle" type="text" value="" >
			</div>
			</li>
			<li>
			<div class="contentLabel notificationLabel">推送内容</div>
			<div class="notificationInfo"><textarea id="notificationContent" name="notificationContent"></textarea></div>
			</li>
			<li>
			<div class="urlLabel notificationLabel">内容链接</div>
			<div class="notificationInfo">
			<input id="notificationUrl" name="notificationUrl" type="text" value="http://m.lvmama.com/clutter/place/120044" >
			</div>
			</li>
			<li>
			<div class="urlLabel notificationLabel">链接类型</div>
			<div class="notificationInfo">
				<select id="notificationType" name="notificationType" >
					<option value="webbrower">浏览器</option>
					<option value="webview">内置网页</option>
					<option value="place">景点</option>
					<option value="route">线路</option>
					<option value="guide">攻略</option>
				</select>
			</div>
			<div class="notificationInfo">
				<select id="deviceType" name="deviceType" >
					<option value="IPHONE">IPHONE推送</option>
					<option value="IPAD">IPAD推送</option>
					<option value="ANDROID">安卓推送</option>
				</select>
			</div>
			</li>
			<li>
			<div class="notificationBtn"><input id="notificationSubmit" type="button" value="推送"></div>
			</li>
			<li>
			<div class="notificationBtn">线网推送，请谨慎使用！如果不明白咨询开发人员！</div>
			</li>
			<li>
			<div class="urlLabel notificationLabel">线网设备号</div>
			<div class="notificationInfo">
			<input id="deviceToken" name="deviceToken" type="text" value="c0580a71511752b1b49e46d87a948fa5987bfe519d4f43314c1e3c373e8724d4" >
			</div>
			</li>
			<li>
			<div class="notificationBtn"><input id="onlineNotificationSubmit" type="button" value="线网推送测试"></div>
			</li>
		</ul>
	</div>
</body>
</html>