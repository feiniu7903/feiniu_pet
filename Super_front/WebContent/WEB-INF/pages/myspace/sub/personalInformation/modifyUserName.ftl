<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>修改用户名-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-userinfo">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/userinfo.do">我的信息</a>
				&gt;
				<a class="current">修改用户名</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit name-edit">
					<div class="ui-box-title"><h3>修改用户名</h3></div>
					<div class="ui-box-container">
						<div class="edit-box clearfix name-edit-box">
    						<p class="tips-info"><span class="tips-ico03"></span>用户名可用来登录，只能修改一次哦！建议取一个好记的用户名，如：驴小宝。</p>
    						<div class="edit-inbox">
							<form action="/myspace/submitModifyUserName.do" method="post">
								<p><label>您现在的用户名：</label><span class="u-info-big">${oldUserName}</span></p>
								<input type="hidden" id="oldUserName" name="oldUserName" value="${oldUserName}"/>
								<p><label>请输入新用户名：</label><input type="text" id="sso_username" name="newUserName" class="input-text input-name" /></p>
								<p><a class="ui-btn ui-button" id="submitBtn"><i>&nbsp;确 定&nbsp;</i></a></p>
							</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
<script type="text/javascript">

	function sso_username_callback(call){
		$.getJSON("http://login.lvmama.com/nsso/ajax/checkUniqueField.do?jsoncallback=?",
		
		{
			userName: encodeURI($("#sso_username").val(),"UTF-8") 
		},
		function(json){
			if (json.success == true) {
				call();	
			} else {
				error_tip('#sso_username','此用户名已被使用',":last");
				refreshCheckCode('image');
			}	
		});
	}

		
function validate_pass(){
	$("form").submit();
}
</script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script>
	cmCreatePageviewTag("修改用户名", "D1003", null, null);
</script>
</body>
</html>