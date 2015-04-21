 		<h3 class="buy-head3">取票人信息</h3>
            <div class="buy-info buy-check">
              <@s.if test='contactInfoOptions.contains("NAME")'>
                <dl>
                    <dt>取票人：</dt>
                    <dd>
                        <i class="red">*</i>
                        <input type="text" class="buy-person-name buy-txt" placeholder="姓名" name="contact.receiverName">
                    </dd>
                </dl>
               </@s.if>
               
               <@s.if test='contactInfoOptions.contains("MOBILE")'>
                <dl>
                    <dt>手机号码：</dt>
                    <dd>
                        <i class="red">*</i>
                        <input type="text" class="buy-phone buy-txt" placeholder="手机号" name="contact.mobileNumber">
                        <span class="buy-phone-tip">免费接受订单确认短信，请务必填写正确。</span>
                    </dd>
                </dl>
               </@s.if>
              
               <@s.if test='contactInfoOptions.contains("CARD_NUMBER")'>
                <dl>
                    <dt>证件类型：</dt>
                    <dd>
                        <i class="red">*</i>
                        <select class="buy-sel" data-class="selectbox-small" name="contact.cardType">
                            <option value="ID_CARD">-身份证-</option>
                            <option value="HUZHAO">-护照-</option>
                        </select>
                        <input type="text" class="buy-num buy-txt" placeholder="证件号码" name="contact.cardNum">
                    </dd>
                </dl>
               </@s.if>
            </div>