<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>找回支付密码-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	
</head>
<body id="page-store">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/account/bonusreturn.do">现金账户</a>
				&gt;
				<a class="current">找回支付密码</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<!-- 找回支付密码>> -->
				<div class="ui-box mod-edit store_pwd_change-edit">
					<div class="ui-box-title"><h3>找回支付密码</h3></div>
					<div class="ui-box-container">
				    	<!-- 操作步骤1 -->
				    	<div id="store_password_back1" class="edit-box clearfix store_pwd_change-edit-box">
					        <div class="set-step set-step1 clearfix">
					        	<ul class="hor">
				    	            <li class="s-step1"><span class="s-num">1</span>验证手机号，设置新密码</li>
				    	            <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>新密码设置成功</li>
				                </ul>
					        </div>
				        	<div class="edit-inbox">
				            <p><label>请输入您的手机号：</label><input type="text" disabled=true  class="input-text input-phone" value="<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(moneyAccount.mobileNumber)" />"/>
				            <div class="ui-font-right"><a href="/myspace/userinfo/contactCustomService.do">原手机号已丢失或停用? </a></div></p>      
				            <input type="hidden" id="mobileNumber" name="mobileNumber2"  value="${moneyAccount.mobileNumber}"/>
				            <p id="sendFindPasswordMobileCodeId"><label></label><a class="ui-btn ui-btn1" href="#"><i><span id="sendFindPasswordMobileCode">免费获取校验码</span></i></a></p>
				            <p><span id="sendFindPasswordMobileCodeTip"></span><span id="sendFindPasswordMobileCodeRight"></span></p>
				            <p><label>请输入短信校验码：</label><input type="text" id="validatePasswodCode" name="validatePasswodCode" class="input-text i-checkcode" /><span id="validatePasswodCodeTip"></span><span id="validatePasswodCodeRight"></span></p>
				            <p><label>设置新密码：</label><input type="password" id="paymentPassword_" name="paymentPassword" class="input-text input-pwd" disabled="true"/><span id="paymentPassword_Tip"></span><span id="paymentPassword_Right"></span></p>
		            		<p><label>请确认新密码：</label><input type="password" id="confirmPassword_" name="confirmPassword_" class="input-text input-pwd"  disabled="true"/><span id="confirmPassword_Tip"></span><span id="confirmPassword_Right"></span></p>
				            <p id="findInitPasswordButton"><a class="ui-btn ui-btn2 btn-disabled" id="submitBtn"><i>&nbsp;确 定&nbsp;</i></a></p>
				            </div>
				        </div>
				        <!-- <<操作步骤1 -->
				        
				        <!-- <<操作步骤2 -->
				        <div id="store_password_back2" class="ui-box-container" style="display:none">
					        <div class="set-step set-step2 clearfix">
					        	<ul class="hor">
				    	            <li class="s-step1"><span class="s-num">1</span>验证手机号，设置新密码</li>
				    	            <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>新密码设置成功</li>
				                </ul>
					        </div>
					        <div class="msg-success"><span class="msg-ico01"></span>
					            <h3>恭喜！新支付密码设置成功！</h3>
					            <p class="mt10">您现在可以：<a href="/myspace/account/bonusreturn.do">返回现金账户</a></p>
					        </div>
						</div>
					</div>
				<!-- <<找回支付密码 -->
				</div>
			</div>
	</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
<script src='/js/myspace/account.js' type='text/javascript'></script>
</body>
</html>