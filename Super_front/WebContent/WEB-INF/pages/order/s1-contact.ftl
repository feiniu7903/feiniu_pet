<!-- 订单联系人信息 -->
<input type="hidden" valid="true" value="2">
<div class="order-title">
    <h3>订单联系人信息</h3>
</div>
<div class="order-content xdl-hor order-contact">    
    <div class="form-small">
		<@s.if test="!receiversList.isEmpty()">
			<div class="choose-info">
				<dl class="xdl">
				<dt>常用联系人：</dt>
				<dd id="order-list" class="form-inline">
					<@s.iterator value="receiversList" var="orderReceiver">
						<label class="radio inline" data-info="${orderReceiver.receiverId}|${orderReceiver.receiverName}|<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobileNumber)"/>|${orderReceiver.email}">
							<input name="order-person" autocomplete="off" type="radio" receiver-id="${orderReceiver.receiverId}" class="input-radio" />${orderReceiver.receiverName}
						</label>
					</@s.iterator>
				</dd>
				</dl>
			</div>
		</@s.if>
        <div id="order-edit" class="person">
            <dl class="xdl">
                <dt>订单联系人：</dt>
                <dd class="form-inline">
                    <label class="inline">
                    	<input type="hidden" class="input-text" autocomplete="off" name="text-order-person"/>
                    	<input type="text" class="input-text" autocomplete="off" name="text-order-person"/>
                    </label>
                </dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>手机号码：</dt>
                <dd class="form-inline">
                    <label class="inline">
                   		 <input id="order-mobile" type="text" class="input-text" autocomplete="off" name="text-order-person"/>
                  	     <span class="help-inline">免费接受订单确认短信，请务必填写正确</span>
                  	</label>
                </dd>
            </dl>
            <dl class="xdl">
                <dt>电子邮箱：</dt>
                <dd>
                    <label class="inline"><input type="text" autocomplete="off" class="input-text" name="text-order-person"/>
                    <span class="help-inline">用于接收邮件订单详情、邮件出行通知、行程确认等服务</span></label>
                </dd>
            </dl>
            <dl class="xdl">
				<dt></dt>
				<dd>
					<button id="order-submit" class="pbtn pbtn-mini pbtn-blue">保存</button>
					<p class="help-block">友情提示：准确填写订单联系人信息，便于我们跟你联系。</p>
				</dd>
			</dl>
        </div>
		
		<!-- 保存后状态 -->
        <div id="order-info" class="person form-info hide">
     	   <input type="hidden" name="contact.receiverId" value="" >
        	<input type="hidden" name="contact.receiverName" value="" >
        	<input type="hidden" name="contact.mobileNumber" value="" >
        	<input type="hidden" name="contact.email" value="" >
            <dl class="xdl">
                <dt>订单联系人：</dt>
                <dd></dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>手机：</dt>
                <dd></dd>
            </dl>
            <dl class="xdl">
                <dt>电子邮箱：</dt>
                <dd></dd>
            </dl>
            <dl class="xdl">
                <dt></dt>
                <dd>
                    <button id="order-btn" class="pbtn pbtn-mini pbtn-light">修改</button>
                    <p class="help-block">友情提示：点击修改可以进行信息编辑。</p>
                </dd>
            </dl>
        </div>

    </div>
</div> <!-- //联系人信息 -->
