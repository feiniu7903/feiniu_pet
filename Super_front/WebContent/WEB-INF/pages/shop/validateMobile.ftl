 <h3>校验信息</h3>
 <#if user.mobileNumber!=null&&user.isMobileChecked=="Y" >
  <div class="xhbox">
	  <dl class="dl-hor">
	  	<dt>手机号码：</dt>
	  	<dd>
			<b>	
				<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" />
			</b>
			<a class="link-edit" href="http://www.lvmama.com/myspace/userinfo/phone.do" hidefocus="false" target="_blank">更换号码</a>
		</dd>
  	 </dl>
	  <dl class="dl-hor">
		  <dt>校验码：</dt>
		  <dd>
		  	<input type="text" class="input-text input-code" placeholder="请勿泄漏" id="cashAccountVerifyCode" onkeyup="value=value.replace(/[^\d]/g,'')"/>
            <div class="xhoper">
                <a href="javascript:;" id="send-verifycode" class="btn btn-small sendcode" mobile="${user.mobileNumber}">免费获取校验码</a>
                <p id="JS_countdown" class="xhinfo1 hide">
                    <span class="tiptext tip-success tip-line">
                        <span class="tip-icon tip-icon-success"></span><span class="xhtipinfo">校验码已发送成功，请查看手机</span>
                    </span>
                    <span class="tiptext tip-default tip-line">60秒内没有收到短信? <a href="javascript:;" class="btn btn-small disabled">(<span class="J_num">60</span>)秒后再次发送</a>
                    </span>
                </p>
                <p class="xhinfo2 hide">
                    <span class="tiptext tip-error tip-line"><span class="tip-icon tip-icon-error"></span>已超过每日发送上限，请于次日再试</span>
                </p>
                <p class="xhinfo3 hide">
                    <span class="tiptext tip-warning tip-line"><span class="tip-icon tip-icon-warning"></span>当前IP发送频率过快，请稍候重试</span>
                </p>
                <p class="xhinfo4 hide">
                    <span class="tiptext tip-warning tip-line"><span class="tip-icon tip-icon-warning"></span>发送频率过快，请稍候重试</span>
                </p>
            </div>
         </dd>
	  </dl>
  </div>
<#else>
  	 <div class="xhbox">
  		<dl class="dl-hor">
 	 		<dt>手机号码：</dt>
 	 		<dd>
 	 			<b>暂无号码</b>
 	 			<a class="link-edit" href="http://www.lvmama.com/myspace/userinfo/phone.do" hidefocus="false" target="_blank">马上去绑定</a>
 	 		</dd>
		</dl>  	
	 </div>
  </#if>