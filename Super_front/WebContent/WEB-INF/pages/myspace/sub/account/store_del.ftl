<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>退款提现-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	
</head>
<body id="page-store">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/account/store.do">存款账户</a>
				&gt;
				<a class="current">退款提现</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
			<script src="/js/myspace/account.js" type="text/javascript"></script>
			<div class="ui-box mod-edit store_del-edit">
				<div class="ui-box-title"><h3>退款提现</h3></div>
				<div class="ui-box-container">
			        <!-- 操作步骤2>> -->
					<@s.if test='null != errorMessage'>
						<div class="hr_a"></div>
						<p class="tips-error"><span class="tips-ico02"></span><@s.property value="errorMessage" /></p>
					</@s.if>
					<@s.if test='moneyAccount.valid=="Y"'>
						   <form id="forms" action="/myspace/account/submitdraw.do" method=post>
					        <input type="hidden" id="bankName" name="bankName"/>
					   		<input type="hidden" name="payAmount" value="${moneyAccount.maxDrawMoney}" />
					        <div class="edit-box clearfix store_del-edit-box">
						        <div class="set-step set-step1 clearfix">
						        	<ul class="hor">
					    	            <li class="s-step1"><span class="s-num">1</span>验证信息</li>
					    	            <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>等待审核</li>
					                </ul>
						        </div>
					        	<div class="edit-inbox">
					            <p><label><span>*</span>请选择银行：</label><select id="bank" name="bank" class="lv-select select-bank">
						            <option value="0">--请选择--</option>
									<option value="2">中国工商银行</option>
									<option value="3">招商银行</option>
									<option value="4">中国建设银行</option>
									<option value="5">中国农业银行</option>
									<option value="6">交通银行</option>
									<option value="7">上海浦东发展银行</option>
									<option value="8">广东发展银行</option>
									<option value="9">中国光大银行</option>
									<option value="10">兴业银行</option>
									<option value="11">深圳发展银行</option>
									<option value="12">中国民生银行</option>
					            </select>
					            <span style="color:red">暂不支持公司卡的提现申请</span>
					            </p>
					            <p><label><span>*</span>请输入收款户名：</label><input type="text" id="accountName" name="accountName" class="input-text input-uname"><span id="accountNameTip"></span></p>
					            <p><label><span>*</span>请输入银行卡号：</label><input type="text" id="account" name="account" class="input-text input-bankcard"></p>
					            <p><label><span>*</span>提现金额：</label> <span class="lv-c1 f16 B">${moneyAccount.maxDrawMoneyYuan}</span> 元</p>
					            <p><label>请输入支付密码：</label><input type="password" id="paymentPassword_input" name="paymentPassword" value="" class="input-text input-pwd"><span id="paymentPassword_inputRight"></span><span id="paymentPassword_inputTip"></span>
					            <p><label>您的支付手机号：</label><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(moneyAccount.mobileNumber)" /></p>
					            <p><label></label><a id="send-verifycode" class="ui-btn ui-btn1" href="javascript:;"><i>免费获取校验码</i></a><span id="JS_countdown" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span></p>
					            <p><label>请输入短信校验码：</label><input type="text" name="cashAccountVerifyCode" id="cashAccountVerifyCode" value="" class="input-text i-checkcode" /></p>
					            <p><a href="javascript:submitForms();" class="ui-btn ui-btn4"><i>&nbsp;下一步&nbsp;</i></a></p>
					            </div>
					        </div>
					        </form>
					</@s.if>
			        <!-- <<操作步骤2 -->
				</div>
			</div>
			<!-- <<退款提现 -->
		</div>
	</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script>
$('#send-verifycode').click(function(){
	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=${moneyAccount.mobileNumber}&jsoncallback=?",function(json){
		if (json.success) {
			$('#reSendAuthenticationCodeDiv').show();
			$('#sendAuthenticationCodeDiv').hide();	
			$('#send-verifycode').hide();
			$("#JS_countdown").show();
			JS_countdown("#JS_countdown span.num-second");
		} else {
			alert('验证码发送失败，请重新尝试');
		}
	});	
 });
	 
	function submitForms(){
		if (${moneyAccount.maxDrawMoneyYuan}==0){
			alert('可提现金额为0，不能提交'); return;
		}
		if ($("#accountName").val()==''){
			alert('收款户名不能为空!'); return;
		}
		if ($("#account").val()==''){
			alert('账号不能为空!');return;
		}
		if ($("#bank").val()==''||$("#bank").val()=='0'){
			alert('银行不能为空!');return;
		}
		$("#bankName").val($("#bank").find("option:selected").text());

		$('#forms').submit();	
	}
</script>
</body>
</html>