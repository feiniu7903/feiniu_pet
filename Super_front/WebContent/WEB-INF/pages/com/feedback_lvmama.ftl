<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>意见反馈</title>
		<link href="http://pic.lvmama.com/styles/oldlvmama/style/global.css" type="text/css" rel="stylesheet" />
		<link href="http://pic.lvmama.com/styles/oldlvmama/style/layout.css" type="text/css" rel="stylesheet" />
		<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/new_v/header-air.css">
		<script type="text/javascript">
		function btn_clickSubmit(){
			var feedBackType=document.getElementById("feedBackType");
			var feedbackEmail=document.getElementById("feedbackEmail");
			var feedBackContent=document.getElementById("feedBackContent");
			
			if(feedBackContent.value==""||feedBackContent.value.length<10||feedBackContent.value.length>500){
					alert("请至少输入10个汉字，且不得超过500个汉字的内容");
					return false;
			}else if(feedbackEmail.value==""){
				alert("请留下您的手机号，以便收到我们的回复");
				return false;
			}else{
					var param2 = {randNum:$("#vCode").val()};
					$.ajax({type:"POST", url:"/userCenter/user_checkRegRang.do", data:param2, dataType:"json", success:function (data) {			
						if(data.stats.indexOf("true")>-1){
						
						var frmFeedback=document.getElementById("frmFeedback");
						frmFeedback.submit();
						}else{
							alert("请输入正确的验证码！");
						}
					}});
				}
		}
		function refresh(){
     		 document.getElementById("image").src='/account/checkcode.htm?now=' + new Date();
   		}
		</script>
    <#include "/common/commonJsIncluedTop.ftl">	
   
	</head>


	<body>
    <script src="http://www.lvmama.com/zt/web/common/header.js"></script>
	<div id="container" class="container_bg">

		
		<form action="/userCenter/user_savefeedBack.do" method="post" id="frmFeedback" >
		<@s.token name="feedToken"></@s.token>
			<div class="fb_wrap">
				<div class="fb_title">
					感谢您提出意见和建议
				</div>
				<div class="fb_main">
					<p class="fb_main_top">
						驴妈妈的成长需要您的关爱！
						<br />
						如果对网站有好的建议，在浏览网站时有疑问，或是在订购过程中需要投诉，请给我们留言，或直接联系我们。
						<br />
						客服电话：10106060 客服邮箱：
						<a href="mailto:service@lvmama.com">service@lvmama.com</a>
					</p>
					<table width="100%" border="0">
						<tr>
							<td width="12%">
								<span class="red">*</span>留言类型：
							</td>
							<td width="88%">
								<select size="1" name="userFeedback.type" id="feedBackType">
									<option value="suggest">
										我有好的建议
									</option>
									<option value="ask">
										我要咨询问题
									</option>
									<option value="complain">
										我要投诉
									</option>
								</select>
								<span class="fb_error">*请选择留言类型</span>
							</td>
						</tr>
						<tr>
							<td valign="top">
								<span class="red">*</span>留言内容：
							</td>
							<td>
								<label>
									<textarea name="userFeedback.content" id="feedBackContent"
										cols="40" rows="5"></textarea>
									<span class="fb_error">*请输入10至500个汉字</span>
									<p class="fb_tip">
										(请至少输入10个汉字，且不得超过500个汉字的内容)
									</p>
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<span class="red">*</span>手机号码：
							</td>
							<td>
								<label>
									<input name="userFeedback.instantMessaging" type="text"
										id="feedbackEmail" class="fb_input"
										value="${userMobile}" size="45" />
									<span class="fb_error">*请输入您正确的手机号码</span>
								</label>
							</td>
						</tr>
						<tr>
							<td>
								联系Email：
							</td>
							<td>
								<label>
									<input name="userFeedback.email" type="text"
										id="userFeedback_email" class="fb_input"
										value="${userEmail}" size="45" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								验证码：
							</td>
							<td>
								<label>
									<input type="text" size="8" id="vCode" />
									<img id="image" src="/account/checkcode.htm" />
									<a onclick="refresh()" style="cursor: hand;">看不清楚</a>
								</label>
							</td>
						</tr>
						<tr>
							<td>&nbsp;
								
							</td>
							<td>
								<p class="fb_login_btn">
									<a href="#" id="submitBtn" onclick="btn_clickSubmit()">提 交</a>

								</p>
							</td>
						</tr>
					</table>
				</div>
				<!--fb_main end-->
			</div>
			<!--fb_wrap end-->
		</form>
	
        <div class="fb_msg" style="display: none" id="div_fb_msg">
            您的留言已经提交，感谢您的支持！
            <br />
            我们会在一个工作日内处理您的意见和建议。
            <br />
            由于留言众多，无法逐一回复，请见谅。
            <br />
            <div style="margin: 0 auto; text-align: center;">
                <input type="submit" value="返回" onclick="javascript:window.close();" />
            </div>
        </div>
    </div>
    <script src="http://s1.lvjs.com.cn/js/common/copyright.js"></script>
<script src="http://s2.lvjs.com.cn/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script>
</body>
</html>

