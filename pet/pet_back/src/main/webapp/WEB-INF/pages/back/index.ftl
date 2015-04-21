<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈信息管理系统</title>
	<link rel="stylesheet" type="text/css" href="/pet_back/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/pet_back/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/pet_back/themes/main.css">
	<script type="text/javascript" src="/pet_back/js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="/pet_back/js/base/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		function loginFuc(){ // 当登录超时的时候调用此方法
			this.location='./login.do';
		};
		
		function iframeRedirect(url,permId) {
			if (url=="") {
				return;
			}else if(url.indexOf('?') > -1){
				$("#iframe").attr("src", url + "&permId="+permId);
			}else{
				$("#iframe").attr("src", url + "?permId="+permId);
			}
		};
		function loginOut(t) {
			t.href='${basePath}'+"loginOut.do";
		};
		var taskMessage = function (msg,title) {
			$('div.window .panel-tool-close').click();
			$.messager.show({
				id: 'taskMessageDiv',
				name: 'taskMessageDiv',
				title: title,
				msg: msg,
				timeout: 0,
				height:'130',
				width:'400',
				showType: 'slide'
			});
		};
		function jumpUrl(url) {
			$("#iframe").attr("src", url);
		};
		function time(){
				$.ajax( {
				url : "/super_back/msg/initMessage.do",
				type: "POST",
				success : function(result) {
						if(result.length>0){
							taskMessage(result,"系统提醒消息");
						}
					}
				});
		};

		function updateMsgRecreiver(msgId){
			$.ajax( {
			url : "/super_back/msg/updateMsgRecreiver.do",
			data:"msgId="+msgId,
			type: "POST",
			success : function() {
					var _msg="#"+msgId+"_msg";
					var _finish="#"+msgId+"_finish";
					$(_msg).hide();
					$(_finish).hide();
				}
			});
		};
		
		(function($) {
			$(function(){
				setInterval('time()',60000);
				$("div.logIcon").parent().addClass("logIconTitle");
				$("div.layout-button-left").parent().addClass("layoutButtonLeft");
			});
		})(jQuery);
	</script>
</head>

<body class="easyui-layout">
		<div region="west" split="true" title=" " icon="logIcon" style="width:180px;padding1:1px;overflow:hidden;"> 
			<div class="easyui-accordion" fit="true" border="false">
				<#list menuList as obj>
					<div title="${obj.name}" icon="itemIcon${obj.category}">
						<ul class="easyui-tree"  animate="true" dnd="true">
						<#list obj.subList as subObj>
								<#if subObj.container>
									<li state="closed">
										<span><a onClick="javascript:iframeRedirect('${subObj.url?if_exists}','${subObj.permissionId?c}');");">${subObj.name}</a></span>
										<ul class="easyui-tree">
										<#list subObj.subList as thirdObj>
											<li>
												<span><a onClick="javascript:iframeRedirect('${thirdObj.url?if_exists}','${thirdObj.permissionId?c}');");">${thirdObj.name}</a></span>
											</li>
										</#list>
										</ul>
									</li>
								<#else>
									<li>
										<span><a onClick="javascript:iframeRedirect('${subObj.url?if_exists}','${subObj.permissionId?c}');">${subObj.name}</a></span>
									</li>
								</#if>
						</#list>
						</ul>
					</div>
				</#list>
				<div></div>
			</div>
		</div>
		<div region="center" style="overflow:hidden;">
			<div class="easyui-layout" fit="true" style="background:#ccc;">
				<div region="north" split="true" border="true" class="logoHead" align="right" id="indexHead">
					<span style="float:left;color:red;margin-left:20px;">欢迎:${Request["user"].userName}/${Request["user"].realName}</span>
					<a href='javascript:jumpUrl("/pet_back/perm_user/to_change_password.do");'   align="right">修改密码</a>
					<a href='javascript:jumpUrl("/super_back/push/task_input.zul");'   align="right">新增任务</a>
					<a href='javascript:jumpUrl("/super_back/log/viewAnnounceQuery.zul");'   align="right">我的公告</a>
					<a href='javascript:jumpUrl("/super_back/log/viewTaskQuery.zul");'  style="color:red;cursor:hand" align="right">我的任务</a>
					<a href='javascript:jumpUrl("/super_back/log/viewMessageQuery.zul");' style="color:red;cursor:hand" align="right">我的消息</a>
					<a onClick="loginOut(this)" style="color:red;cursor:hand" align="right">登出</a>
				</div>
				<div region="center">
					<iframe  id="iframe" width="100%" height="99%"  src=""></iframe>
				</div>
				<!--<div region="south" title="South Title" split="true" style="height:10px;padding:10px;background:#efefef;">sub center</div>-->
			</div>
		</div>
</body>
</html>