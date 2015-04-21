<input type="hidden" valid="true" value="3">
<!--取票人信息 -->
<div class="order-title">
    <h3>取票人信息</h3>
</div>
<div class="order-content xdl-hor order-contact">    
    <div class="form-small">
		<@s.if test="!receiversList.isEmpty()">
			<div class="choose-info">
				<dl class="xdl">
				<dt>常用联系人：</dt>
				<dd id="take-list" class="form-inline" recever-list="take-person">
					<@s.iterator value="receiversList" var="tackReceiver">
						<label receiver-id="${tackReceiver.receiverId}" class="checkbox inline" data-info="{receiverId:'${tackReceiver.receiverId}', cardType:'${tackReceiver.cardType}', receiverName:'${tackReceiver.receiverName}', cardNum:'${tackReceiver.cardNum}', mobileNumber:'<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobileNumber)"/>',email:'<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(email)"/>',pinyin:'${tackReceiver.pinyin}'}">
							<input name="take-person" autocomplete="off" type="checkbox" receiver-id="${tackReceiver.receiverId}" class="input-radio" /><span>${tackReceiver.receiverName}</span>
						</label>
					</@s.iterator>
				</dd>
				</dl>
			</div>
		</@s.if>
        <div id="take-edit" class="person">
            <dl class="xdl">
                <dt><i class="req">*</i>取票人：</dt>
                <dd class="form-inline">
                    <label class="inline">
                    	<input type="hidden"  class="input-text" autocomplete="off" name="text-take-person" data-field="receiverId"/>
                    	<input type="text" id="take-user" class="input-text" autocomplete="off" name="text-take-person" placeholder="姓名" data-field="receiverName" maxlength="20"/>
                    </label>
                </dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>手机号码：</dt>
                <dd class="form-inline">
                    <label class="inline">
                    	<input type="hidden"  name=""/>
                   		 <input id="take-mobile" type="text" class="input-text" autocomplete="off" name="text-take-person" placeholder="手机号" data-field="mobileNumber"/>
                  	     <span class="help-inline">免费接受订单确认短信，请务必填写正确</span>
                  	</label>
                </dd>
            </dl>
            <@s.if test='contactInfoOptions.contains("CARD_NUMBER")'>
            <dl class="xdl">
            	<dt><i class="req">*</i>证件类型：</dt>
            	 <dd class="form-inline">
				<div class="selectbox selectbox-small">
					<p class="select-info like-input">
						<!--<span class="select-arrow"><i class="ui-arrow-bottom dark-ui-arrow-bottom"></i></span>-->
						<span value="ID_CARD" class="select-value">身份证</span>
					</p>
					<!--<div class="selectbox-drop">
						<ul class="select-results">
							<li data-value="ID_CARD">身份证</li>
							<li data-value="HUZHAO">护照</li>
						</ul>
					</div>-->
				</div>
				<input type="hidden"  name=""/>
				<input type="hidden"  name=""/>
				<input type="text" id="take-cardNum" name="text-take-person" autocomplete="off" placeholder="证件号码" class="input-text input-middle" data-field="cardNum"/>
      			 </dd>
      		 </dl>
      	</@s.if>
      	
      	<@s.if test='contactInfoOptions.contains("EMAIL")'>
            <dl class="xdl">
                <dt><i class="req">*</i>电子邮箱：</dt>
                <dd class="form-inline email_orderfill">
                    <label class="inline">
                    	<input type="hidden"  name=""/>
                   		 <input id="sso_email_c" type="text" class="input-text email_text_Box" name='text-take-person' autocomplete="off" placeholder="电子邮箱" data-field="email"/>
                  		<span class="help-inline">此邮箱用于接收门票信息，请务必填写正确，确保能收到电子邮件。</span>
                  	</label>
                </dd>
            </dl>
      	</@s.if>
      	 <@s.if test='contactInfoOptions.contains("PINYIN")'>
             <dl class="xdl">
                <dt><i class="req">*</i>姓名拼音：</dt>
                <dd class="form-inline">
                    <label class="inline">
                        <input type="hidden" name=""/>
                        <input type="text" id="take-pinyin" class="input-text pinyin_text_Box"  autocomplete="off" name="text-take-person" placeholder="拼音" data-field="pinyin" maxlength="50"/>
                        <span class="help-inline">请输入取票人的姓名拼音，不含空格。例：zhangsan</span>
                    </label>
                </dd>
            </dl>
            </@s.if>
      		<dl class="xdl">
				<dt></dt>
				<dd>
					<button id="take-submit" class="pbtn pbtn-mini pbtn-blue">保存</button>
					
				</dd><dd>
				<p class="help-block">友情提示：准确填写订单联系人信息，便于我们跟你联系。</p>
				</dd>
			</dl>
        </div>
		
		<!-- 保存后状态 -->
        <div id="take-info" class="person form-info hide">
       	    <input type="hidden" name="contact.receiverId" value="" autocomplete="off">
        	<input type="hidden" name="contact.receiverName" value="" autocomplete="off">
        	<input type="hidden" name="contact.mobileNumber" value="" autocomplete="off">
        	<input type="hidden" name="contact.cardType" value="" autocomplete="off">
            <input type="hidden" name="contact.cardNum" value="" autocomplete="off">
            <input type="hidden" name="contact.email" value="" autocomplete="off">
            <input type="hidden" name="contact.pinyin" value="" autocomplete="off">
            <dl class="xdl">
                <dt><i class="req">*</i>取票人：</dt>
                <dd></dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>手机号码：</dt>
                <dd></dd>
            </dl>            
            <@s.if test='contactInfoOptions.contains("CARD_NUMBER")'>
	            <dl class="xdl">
	                <dt dtType="card"><i class="req">*</i>身份证：</dt>
	                <dd></dd>
	            </dl>
         	</@s.if>
         	<@s.if test='contactInfoOptions.contains("EMAIL")'>
	            <dl class="xdl">
	                <dt dtType="email"><i class="req">*</i>电子邮箱：</dt>
	                <dd></dd>
	            </dl>
         	</@s.if>
         	 <@s.if test='contactInfoOptions.contains("PINYIN")'>
             <dl class="xdl">
                <dt><i dtType="pinyin" class="req">*</i>用户拼音：</dt>
                <dd></dd>
            </dl>
            </@s.if>
            <dl class="xdl">
                <dt></dt>
                <dd>
                    <button id="take-btn" class="pbtn pbtn-mini pbtn-light">修改</button>
                    <p class="help-block">友情提示：点击修改可以进行信息编辑。</p>
                </dd>
            </dl>
        </div>

    </div>
</div> <!-- //取票人信息 -->
