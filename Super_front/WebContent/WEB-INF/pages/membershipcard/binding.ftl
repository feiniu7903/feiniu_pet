<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员卡激活</title>
<link href="http://pic.lvmama.com/styles/login/Login_20100420.css" rel="stylesheet" type="text/css" />
<link charset="utf-8" media="screen" type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss">
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="http://www.lvmama.com/scripts/util/UniformResourceLocator.js" type="text/javascript"></script> 
<script src="http://pic.lvmama.com/js/top/jquery.remote.jsonSuggest.js" type="text/javascript"></script>
</head>
<body>
<#include "/common/header.ftl">
<!--body start-->
<div class="card-active">
	<span class="card-tl"></span>
	<span class="card-tr"></span>
    
    <div class="card-content">
    	<div>
    	    <label>会员卡号：</label>
    	     <input name="membershipCard" id="sso_membership" type="text" />
    	     <span id="sso_membership_pic" style="display:none;"><img src="http://login.lvmama.com/nsso/images/input_ok.gif" /></span>
			 <span id="sso_membership_errorText" class="c_Gray"></span>
		</div>
    	<div><label>用户名/手机号/Email：</label>
    	    <input name="mobileOrEMail" id="sso_mobileAndEmail" type="text" />
			<span id="sso_mobileAndEmail_pic" style="display:none;"><img src="http://login.lvmama.com/nsso/images/input_ok.gif" /></span>
			<span id="sso_mobileAndEmail_errorText" class="c_Gray"></span>
		</div>
    	<div><label>密码：</label>
    	   <input name="password" id="sso_password" type="password" />
    	   <span  id="sso_password_pic" style="display:none;"></span>
		   <span  id="sso_password_errorText" class="c_Gray"></span></div>
        <div class="active-btn"><img src="http://pic.lvmama.com/img/icons/btn_active.gif" alt="激活" id="activeCard" onClick="submitMembershipCard()"/>
		<p><em>驴妈妈会员卡激活说明：</em><br>
		·若您已经是驴妈妈会员，请在此页面中输入会员卡号及账号密码，完成激活。<br>
		·若您没有驴妈妈账号，请在注册页面中完成激活。<a href="http://login.lvmama.com/nsso/register/registering.do">前往注册&gt;&gt;</a><br>
		·更多驴妈妈会员卡相关说明，请<a href="http://www.lvmama.com/public/order_and_pay#m_7" target="_blank">查看详细&gt;&gt</a>
		</p>	
	</div>
    </div>
    
	<span class="card-bl"></span>
	<span class="card-br"></span>
</div>
<!--body end-->
<#include "/common/footer.ftl">
<!--激活弹出层 S-->
<div id="overcardLy"></div>
<div id="cardLy">
	<h3>会员卡激活成功</h3><img src="http://pic.lvmama.com/img/icons/close.gif" alt="close" id="closeBtn" onClick="closeCardLayer()"/>
    <div>
    　　恭喜您，会员卡激活成功！您可在“我的驴妈妈-我的优惠券”中查收会员卡激活成功赠送的驴妈妈优惠券。
    </div>
    <div class="go-links"><a href="http://www.lvmama.com/"><span>&lt;&lt;返回首页</span></a>　　　　<a href="http://www.lvmama.com/myspace/account/coupon.do">我的优惠券&gt;&gt;</a></div>
</div>
<script type="text/javascript">
       function activeCard(){
					var cardLayer = $("#cardLy");
					var leftX = ($(window).width()-cardLayer.width())/2+190;
					var topY = ($(window).height()-cardLayer.height())/2+50;

					cardLayer.css({"left":leftX,"top":topY+$(window).scrollTop()});
					$("#overcardLy").css("opacity","0.6").fadeIn("fast");
					cardLayer.fadeIn("fast");
		}
		
		function closeCardLayer() {
			$("#cardLy,#overcardLy").fadeOut("fast");
		}
		
		function showContent(name, content) {
			if ($('#' + name).length > 0) {
				$('#' + name).html(content);
				$('#' + name).show();
			} 
		}
		function clearContent(name) {
			if ($('#' + name).length > 0) {
				$('#' + name).empty();
				$('#' + name).hide();
			} 
		};
		$('#sso_membership').blur(function(){
			clearMembershipTextErrorInfo();
			MembershipFieldOnBlur(this.value);
		});
		$('#sso_membership').focus(function(){
			clearMembershipTextErrorInfo();
			showContent("sso_membership_errorText","&nbsp;&nbsp;&nbsp;请输入有效的会员卡号");
		});
		function clearMembershipTextErrorInfo(){
			clearContent("sso_membership_pic");
			clearContent("sso_membership_errorText");
		}

		function validMembershipTextErrorInfo() {
			showContent("sso_membership_pic","&nbsp;&nbsp;&nbsp;<img src='http://login.lvmama.com/nsso/images/input_ok.gif' />");
			clearContent("sso_membership_errorText");	
		}

		function MembershipFieldOnBlur(value){
			if (value.length == 0) {
				return false;
			}
			if(!/^[\da-zA-Z]+$/.test(value)){ 
				alert("会员卡号必须全部为数字或字母") 
			} 
			$.getJSON(
				"http://login.lvmama.com/nsso/ajax/checkUniqueField.do?jsoncallback=?",
				{
					membershipCard: value
				},
				function(data) {	   
					if (data.success) {
						validMembershipTextErrorInfo();
						return true;
					} else {
						showContent("sso_membership_errorText","<font color='red'>&nbsp;&nbsp;&nbsp;*会员卡不存在或已被激活</font>");	
						return false;
					}
				}
			);					
		}
		function submitMembershipCard(){
		   $('#sso_mobileAndEmail_errorText').empty();
		   $('#sso_mobileAndEmail_errorText').hide();
		   $('#sso_password_errorText').empty();
		   $('#sso_password_errorText').hide();
		
			var card_val=$("#sso_membership").val();
			if (card_val.length == 0) {
			    showContent("sso_membership_errorText","<font color='red'>&nbsp;&nbsp;&nbsp;*会员卡不能为空</font>");	
				return false;
			}
			clearMembershipTextErrorInfo();
			MembershipFieldOnBlur(card_val);
			
			var mobileOrEMail=$('#sso_mobileAndEmail').val();		
			if(mobileOrEMail==''){
				showContent("sso_mobileAndEmail_errorText","<font color='red'>&nbsp;&nbsp;&nbsp;*用户名不能为空</font>");	
				return false;
			};
			
			var password=$('#sso_password').val();
			if(password==''){
				showContent("sso_password_errorText","<font color='red'>&nbsp;&nbsp;&nbsp;*密码不能为空</font>");	
				return false;
			};
			
			$.getJSON(
					"http://login.lvmama.com/nsso/ajax/bindCard.do?jsoncallback=?",
					{
						membershipCard: card_val,
						mobileOrEMail : $('input:text[name=mobileOrEMail]').val(),
						password	  : $('input:password[name=password]').val()
					},
					function(data) {	   
						if (data.success) {
							activeCard();
							return true;
						} else {
							alert("请确认你的用户名，密码及会员卡是否正确!");	
							return false;
						}
					}
				);				
		}
</script>
<script src="http://pic.lvmama.com/min/index.php?g=common" type="text/javascript" charset="utf-8"></script>

</body>
</html>
