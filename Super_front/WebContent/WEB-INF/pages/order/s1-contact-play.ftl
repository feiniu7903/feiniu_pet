<div class="hr_a" data-hide="play">
	<input type="hidden" valid="true" value="4">
</div>
<!-- 游玩人信息 -->
<div class="order-title" data-hide="play">
    <h3>游玩人信息</h3>
    <input type ="hidden" id="firstTravellerInfoOptions" value="${firstTravellerInfoOptions};"/>
    <input type ="hidden" id="travellerInfoOptions" value="${travellerInfoOptions};"/>
</div>
<div class="order-content xdl-hor" data-hide="play">    
    <div class="form-small form-inline">
		<@s.if test="!receiversList.isEmpty()">
			<div class="choose-info">
				<dl class="xdl">
					<dt >常用联系人：</dt>
					<dd id="play-list" class="form-inline" recever-list="play-person">
						<@s.iterator value="receiversList" var="receiver">
							<label receiver-id="${receiver.receiverId}" class="checkbox inline" data-info="{receiverId:'${receiver.receiverId}', cardType:'${receiver.cardType}', receiverName:'${receiver.receiverName}', pinyin:'${receiver.pinyin}', cardNum:'${receiver.cardNum}', mobileNumber:'<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobileNumber)"/>'}">
								<input type="checkbox" class="input-checkbox" autocomplete="off" name="play-person" /><span>${receiver.receiverName}</span>
							</label> 
						</@s.iterator>
					</dd>
				</dl>
			</div>
		</@s.if>
		
		<div id="play-edit" class="form-edit">
	        <dl class="xdl">
	            <dt></dt>
	            <dd>
	                <button id="play-submit" class="pbtn pbtn-mini pbtn-blue">保存</button>
	            </dd>
	            <dd><p class="help-block">友情提示：游玩人信息可以到我的订单中补全。</p></dd>
	        </dl>
        </div>
        
        <div id="play-info" class="form-info hide">
			     
				 <dl class="xdl">              
	                <dt><i class="req">*</i>游玩人${i}：</dt>
	                <dd></dd>
	             </dl>
              
	             <dl class="xdl">
	                <dt></dt>
	                <dd>
	                    <button id="play-btn" class="pbtn pbtn-mini pbtn-light">修改</button>
	                </dd>
	                <dd><p class="help-block">友情提示：点击修改可以进行信息编辑。</p></dd>
	            </dl>
		</div>
    </div>
</div> 
<!-- //游玩人信息 -->
