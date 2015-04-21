<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息推送管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<link type="text/css" href="http://code.jquery.com/ui/1.9.1/themes/smoothness/jquery-ui.css" rel="stylesheet" />
<link type="text/css" href="<s:url value='/css/timePicker/jquery-ui-timepicker-addon.css' />" rel="stylesheet"  />
<style type="text/css">
.notificationLabel{
	width:300px;
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
#notificationBeginTime{
	width:20%;
}
#pushIds {
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
<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.9.1/jquery-ui.min.js"></script>
<script type="text/javascript" src="<s:url value='/js/timePacker/jquery-ui-timepicker-addon.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/timePacker/jquery-ui-sliderAccess.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/timePacker/jquery-ui-timepicker-zh-CN.js' />"></script>
<script type="text/javascript">
	$(function(){
		$('#notificationBeginTime').datetimepicker({
            timeFormat: "HH:mm:ss",
            dateFormat: "yy-mm-dd"
        });
		
		$("#notificationSubmit").click(function(){
			var pushIds = $("#pushIds").val();
			var notificationTitle = $("#notificationTitle").val();
			var notificationContent = $("#notificationContent").val();
			var notificationUrl = $("#notificationUrl").val();
			var notificationType = $("#notificationType").val();
			var deviceType = $("#deviceType").val();
			var notificationBeginTime = $("#notificationBeginTime").val();
			
			
			if(notificationTitle==''){
				alert("推送标题不能为空!");
				return;
			}
			if(notificationContent==''){
				alert("推送内容不能为空!");
				return;
			}
			if(notificationUrl==''){
				alert("推送链接不能为空!");
				return;
			}
			if(notificationBeginTime==''){
				alert("推送时间不能为空!");
				return;
			}
			
			$(this).attr('disabled',true);
			
			var data = {
					"notificationTitle":notificationTitle,
					"notificationContent":notificationContent,
					"notificationUrl":notificationUrl,
					"notificationType":notificationType,
					"notificationBeginTime":notificationBeginTime,
					"deviceType":deviceType,
					"pushIds":pushIds
			}
			$.ajax({
			   type: "POST",
			   //url: "/pet_back/push/notificationPush.do",
			   url: "/pet_back/push/addNotification.do",
			   data: data,
			   dataType: 'json',
			   success: function(jsonObj){
				 $("#notificationSubmit").attr('disabled',false);
			     alert(jsonObj.description);
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
				<div class="notificationBtn"><h3>驴妈妈客户端信息推送测试</h3></div>
			</li>
			<li>
			<div class="urlLabel notificationLabel"><font style="color:red;">*</font>设备号(半角(,)逗号分隔最长支持4000个字符)</div>
			<div class="notificationInfo">
			<input id="pushIds" name="pushIds" type="text" value="" placeholder="多个设备号请用半角逗号分隔" >
			</div>
			</li>
			<li>
			<div class="urlLabel notificationLabel"><font style="color:red;">*</font>推送标题</div>
			<div class="notificationInfo">
			<input id="notificationTitle" name="notificationTitle" type="text" value="" >
			</div>
			</li>
			<li>
			<div class="contentLabel notificationLabel"><font style="color:red;">*</font>推送内容</div>
			<div class="notificationInfo"><textarea id="notificationContent" name="notificationContent"></textarea></div>
			</li>
			<li>
			<div class="urlLabel notificationLabel"><font style="color:red;">*</font>内容链接</div>
			<div class="notificationInfo">
			<input id="notificationUrl" name="notificationUrl" type="text" value="" placeholder="Example:http://m.lvmama.com/clutter/place/120044" >
			</div>
			</li>
			<li>
			<div class="urlLabel notificationLabel"><font style="color:red;">*</font>推送时间</div>
			<div class="notificationInfo">
			<input type="text" id="notificationBeginTime" name="notificationBeginTime" >
			</div>
			</li>
			<li>
			<div class="urlLabel notificationLabel"><font style="color:red;">*</font>链接类型</div>
			<div class="notificationInfo">
				<select id="notificationType" name="notificationType" >
					<option value="webbrower">浏览器</option>
					<option value="webview">内置网页</option>
					<option value="place">景点</option>
					<option value="route">线路</option>
					<option value="guide">攻略</option>
				</select>
			</div>
			</li>
			<li>
			<div class="urlLabel notificationLabel"><font style="color:red;">*</font>推送设备类型</div>
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
		</ul>
	</div>
</body>
</html>