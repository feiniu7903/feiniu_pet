<!-- 绑定手机遮罩层-->
<div id="bgDiv">
</div>
<div id="msgDiv">
<div class="div_block">
<@s.form id="regForm" name="regForm" theme="simple">
<input type="hidden" name="ma" id="ma" value='y' />
<div><a href="#"><img class="closeBlock" src="http://pic.lvmama.com/img/icons/close.gif" id="msgShut" alt="关闭" /></a></div>
<span id="successInfo2">
	<div class="msgNewBlock">
        <p class="msgTitle">恭喜您！绑定手机成功</p>
        <p class="msgRecord">绑定手机号为：<span id="msgTel"></span></p>
        <@s.if test='ma=="y"'>
        <p><strong>您可以继续以下操作：</strong></p>
        <p class="msgNoMargin"><input onclick="window.location.href='/usr/money/recharge2.do?temp='+Math.random().toString().split('\.')[1];" type="button" class="newbutton" value="充值到存款账户" />
        	<input onclick="window.location.href='/usr/money/viewdraw.do?temp='+Math.random().toString().split('\.')[1];" class="newbutton" type="button" value="申请退款提现" /></p>
        <a target="_blank" href="http://www.lvmama.com/public/pay_to_explain">存款账户使用说明</a>
        </@s.if>
    </div>
</span>
<span id="successInfo3">
<center><font color="red"><div id="info"></div></font></center>
<div class="msgNewBlock">
	<p class="msgTitle">绑定手机</p>
    <label>
    	<span>手机号码：</span>
        <input type="text"  name="mobileNumber" id="mobileNumber"  maxlength="11" class="writein_field" size="22"/>
    </label>
    <label>
    	<span>验证码：</span>
        <input name="valDate" type="text" style="width:88px;" id="valDate" size="6"/>
        <img style="vertical-align:middle;" id="image2" src="/account/checkcode.htm" />
    	<a href="#" onClick="refreshCode('image2');return false;" style="*position:relative;top:-5px;color:#0056AE">换一张</a>
    </label>
    <p class="msgError"><span id="mobileNumberTip"></span></p>
    <p><input id="msgButs" name="msgButs" type="button" class="newbutton" value="发送手机绑定码" /><span id="msgButs_Msg"></span></p>
    <p class="msgRecord"><span>注：</span>手机绑定码将以短信方式发送到您的手机，60秒后可重发。</p>
    <p style="margin-bottom:18px;">请在下面填写您收到的手机绑定码</p>
    <label>
    	<span>手机绑定码：</span><input style="width:88px;" type="text" name="validateNumber" id="validateNumber" class="writein_field" size="6" maxlength="6" />
    </label>
    <p class="msgError"><span id="validateNumberTip"></span></p>
    <p><input name="checkRealNumber" id="checkRealNumber" type="button" class="newbutton" value="确认" /></p>
</div>
</span>
</@s.form>
</div>
</div>


