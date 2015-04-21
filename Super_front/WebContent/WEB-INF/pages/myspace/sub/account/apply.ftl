<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>申请提现-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	
</head>
<body id="page-money">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/account/bonusreturn.do">奖金账户</a>
				&gt;
				<a class="current">申请提现</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
<!-- 申请提现>> -->
<div class="ui-box mod-edit apply-edit">
	<@s.if test='mobileNumber!=null && mobileNumber!=""'>
		<div class="ui-box-title"><h3>申请提现</h3></div>
		<div class="ui-box-container">
	    	<!-- 用户名>> -->
	    	<form name="frm" id="frm" action="saveapply.do" method="post">
	    	<input type="hidden" name="cashRefundYuan" id="cashRefundYuan" value='${cashRefundYuan}'>
	    	<div class="edit-box clearfix apply-edit-box">
	        	<div class="edit-inbox">
		            <p><label>当前奖金余额：</label><dfn><i>${cashRefundYuan}</i></dfn>　　<span class="lv-cc">(奖金余额满200元后，即可申请提现，<a target="_blank" href="http://www.lvmama.com/public/comment_system#m_6">如何赚取奖金？</a>)</span><span id="cash1"></span><#if messageType=="1"><span class="tips-error"><span class="tips-ico02"></span>余额不足</span></#if></p>
		            <p id="apply-tooltip" class="clearfix p_rel"><label>是否提供发票：</label>
		            <label class="invoice">
		            	<input name="bonusDrawMoneyInfo.isInvoice" type="radio" id="radio1" value="Y" 
		            		onclick="javascript:return calculate();">
		            		是
		        	</label>
		            <label class="invoice">
			            <input name="bonusDrawMoneyInfo.isInvoice" type="radio" checked="true"
			            	id="radio2" value="N" onclick="javascript:return calculate();">
			            	否
		            </label>
		            <span class="tips-faq tips-show" 
		            	onmouseover="javascript:$('#invoiceTipWin').show();"
		            	onmouseout="javascript:$('#invoiceTipWin').hide();"></span>
		            <span id="invoiceTipWin" class="tooltip apply-tooltip ie6png" style="left: 125px;">
			            <span>
			            	根据税务局的相关规定，企业的每一笔支出金额都应提供正规发票，因此作为预订奖金的收款方，我们需要您提供符合要求的发票  
							如不能提供发票，我们只能委托税务局代开，代开发票的税点为10%，将直接从您的奖金中扣除，请您理解。<br />
							发票要求:<br />
							1、发票必须符合以下要求：抬头“上海景域文化传播有限公司”，发票内容为“服务费”，发票类型为旅游行业发票或服务业发票（餐饮类发票与定额发票除外），超过3个月的过期发票无效<br />
							2、务必请在发票上注明您在驴妈妈的账户用户名<br />
							3、金沙经理1759号圣诺亚大厦B座7-12层 客服部 结算组收（邮编：200333），为防止发票丢失，请使用挂号邮寄或特快专递<br />
						</span>
		            </span>
		            </p>
		            <p>
		            	<label>申请提现金额：</label>
		            	<input type="text" id="bonusDrawMoneyInfo.amount" name="bonusDrawMoneyInfo.amount" value="" 
		            		class="input-text input-money" onblur="javascript:return calculate();"/>
		            	<dfn><i>00</i></dfn> 元
		            	&nbsp;&nbsp;&nbsp;&nbsp;
		            	<span style="color: #FF0000;">请输入 >=2 且 <=10 的自然数</span>
		            </p>
		            <p><label>税款：</label><dfn><i id="tax"></i></dfn> 元 </p>
		            <p><label>实际可提现金额：</label><dfn><i id="cash"></i></dfn> 元 </p>
		        </div>
		        <div class="edit-inbox">
		            <p><label>开户行：</label><select name="bonusDrawMoneyInfo.bank" id="bonusDrawMoneyInfo.bank" class="lv-select select-bank"><option value="中国工商银行" selected>中国工商银行</option>
		               		 <option value="招商银行">招商银行</option>
		               		 <option value="中国建设银行">中国建设银行</option>
		               		 <option value="中国农业银行">中国农业银行</option>
		               		 <option value="交通银行">交通银行</option>
		               		 <option value="上海浦东发展银行">上海浦东发展银行</option>
		               		 <option value="广东发展银行">广东发展银行</option>
		               		 <option value="中国光大银行">中国光大银行</option>
		               		 <option value="兴业银行">兴业银行</option>
		               		 <option value="深圳发展银行">深圳发展银行</option>
		               		 <option value="中国民生银行">中国民生银行</option></select>
		               		 <span id="bank"></span></p>
	                    <p><label>所属支行：</label><input type="text" name="bonusDrawMoneyInfo.bankBranchName" id="bonusDrawMoneyInfo.bankBranchName" value="" class="input-text input-address" /><span id="bankBranchName"></span></p>
		            <p><label>帐户名：</label><input type="text" name="bonusDrawMoneyInfo.bankName" id="bonusDrawMoneyInfo.bankName" value="" class="input-text input-uname" /><span id="bankName"></span></p>
		            <p><label>帐号：</label><input type="text" name="bonusDrawMoneyInfo.bankNumber" id="bonusDrawMoneyInfo.bankNumber" value="" class="input-text input-bankcard" /><span id="bankNumber"></span></p>
	        	</div>
	        	<div class="edit-inbox">
		            <p><label>手机号：</label><input type="text" name="bonusDrawMoneyInfo.contactMobile" id="bonusDrawMoneyInfo.contactMobile" value="" class="input-text input-phone" /><span id="contactMobile"></span></p>
		            <p><label>联系人：</label><input type="text" name="bonusDrawMoneyInfo.contactName" id="bonusDrawMoneyInfo.contactName" value="" class="input-text input-address" /><span id="contactName"></span></p>
		            <p><label>备注：</label><textarea class="apply-textarea" name="bonusDrawMoneyInfo.userRemark" id="bonusDrawMoneyInfo.userRemark" cols="" rows=""></textarea><span id="userRemark"></span></p>
		            <p><label>您的登录密码：</label><input type="password" id="password" name="password" value="" class="input-text input-pwd" /><#if messageType=="2"><span class="tips-error"><span class="tips-ico02"></span>输入密码错误</span></#if></p>
		            <p><label>您的手机号：</label><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobileNumber)" /></p>
		            <p><label></label><a id="send-verifycode" class="ui-btn ui-btn1" data-atr="${mobileNumber!}" href="javascript:;"><i>免费获取校验码</i></a><span id="JS_countdown" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span></p>
		            <p><label>短信校验码：</label><input type="text" name="validatePasswodCode" id="validatePasswodCode" value="" class="input-text i-checkcode" /><span id="validatePasswodCodeTip"><#if messageType=="3"><span class="tips-error"><span class="tips-ico02"></span>输入验证码错误</span></#if></span></p>
		            <p><a class="ui-btn ui-button" onclick="javascript:saveApply();"><i>&nbsp;确 定&nbsp;</i></a>
	            </div>
	        </div>
		</div>
	</@s.if>
	<@s.else>
     	<p><span class="phone-verified lv-nodo">您的手机号未绑定，请绑定手机号<a href="/myspace/userinfo/phone.do" class="link-btn ui-btn ui-btn1"><i>立即绑定</i></a></span></p>
     </@s.else>	
</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
<script type="text/javascript" src="/js/member/payOut.js"></script>
</body>
</html>