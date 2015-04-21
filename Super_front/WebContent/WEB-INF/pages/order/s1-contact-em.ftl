<input type="hidden" valid="true" value="5">
<!-- 紧急联系人信息 -->
<div class="hr_a"></div>
<!-- 紧急联系人信息 -->
<div class="order-title">
    <h3>紧急联系人信息</h3>
</div>
<div class="order-content xdl-hor order-emcontact" >  
    <div class="form-small">
        <div class="choose-info">
            <dl class="xdl">
                <dt>常用联系人：</dt>
                <dd id="em-list" class="form-inline">
               		 <@s.iterator value="receiversList" var="emReceiver">
               	        <label class="radio inline" data-info="${emReceiver.receiverId}|${emReceiver.receiverName}|<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobileNumber)"/>">
               	        	<input autocomplete="off" name="em-person" type="radio" class="input-radio" receiver-id="${emReceiver.receiverId}"   receiver-name="${emReceiver.receiverName}" receiver-mobile="${emReceiver.mobileNumber}">${emReceiver.receiverName}
               	        </label>
					</@s.iterator>
                </dd>
            </dl>
        </div>
        
        <!-- 默认可编辑状态 -->
        <div id="em-edit" class="form-edit">
            <dl class="xdl">
                <dt><i class="req">*</i>姓名：</dt>
                <dd>
                    <label>
                    	   <input type="hidden" class="input-text" value="" autocomplete="off" name="text-em-person"/> 
                    	   <input type="text" class="input-text" id="em-user" autocomplete="off" autocomplete="off" name="text-em-person"/>
                    </label>
                </dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>手机：</dt>
                <dd>
                    <label><input type="text" class="input-text" id="em-mobile"  autocomplete="off" name="text-em-person"/></label>
                    <span class="help-inline">免费接受订单确认短信，请务必填写正确</span>
                </dd>
            </dl>
            <dl class="xdl">
                <dt></dt>
                <dd>
                    <button class="pbtn pbtn-mini pbtn-blue" id="em-submit">保存</button>
                    <p class="help-block">友情提示：紧急联系人信息不能与游玩人相同。</p>
                </dd>
            </dl>
        </div>
        
        <!-- 保存后状态 -->
        <div id="em-info" class="form-info hide">
       	    <input type="hidden" name="emergencyContact.receiverId" value="" >
        	<input type="hidden" name="emergencyContact.receiverName" value="" >
        	<input type="hidden" name="emergencyContact.mobileNumber" value="" >
            <dl class="xdl">
                <dt>姓名：</dt>
                <dd></dd>
            </dl>
            <dl class="xdl">
                <dt><i class="req">*</i>手机：</dt>
                <dd></dd>
            </dl>
            <dl class="xdl">
                <dt></dt>
                <dd>
                    <button class="pbtn pbtn-mini pbtn-light" id="em-btn">修改</button>
                    <p class="help-block">友情提示：点击修改可以进行信息编辑。</p>
                </dd>
            </dl>
        </div>
        
    </div>
    
</div> <!-- //紧急联系人信息 -->            