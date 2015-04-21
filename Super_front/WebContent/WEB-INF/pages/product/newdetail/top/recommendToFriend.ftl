      <div class="recomment_wra">
            <i class="arrow-bg"></i>
            <div class="recomment_wra_mid">
                <h3 class="recomment_tit"><i class="close" data-hide="recomment_wra"></i>推荐给好友</h3>
                <ul class="recomment_input">
                    <li><label>您的推荐：</label>
                        <i>
                        <@s.if test="prodCProduct.prodProduct.productName.length()>0&&prodCProduct.prodProduct.productName.length()<=28">
                            ${prodCProduct.prodProduct.productName}
                         </@s.if>
                         <@s.elseif test="prodCProduct.prodProduct.productName.length()>28">
                            ${prodCProduct.prodProduct.productName.substring(0,28)+"...."}
                         </@s.elseif>
                        </i>
                    </li>
                    <li><label><i>*</i>您的姓名：</label><input id="referrerName" name="username" class="email-input01" type="text" value="请输入姓名" /><strong><b id="referrerNameWarn"></b></strong></li>
                    <li><label><i>*</i>发送到：</label><input id="firstEmail" name="firstEmail" class="email-input01"  type="text" value="例：xiaoming@163.com" /><strong><b id="firstEmailWarn"></b></strong></li>
                    <li class="recomment_margin"><input id="secondEmail" name="secondEmail" class="email-input01"  type="text" value="@" /><strong><b id="secondEmailWarn"></b></strong></li>
                    <li class="recomment_margin"><input id="thirdEmail" name="thirdEmail" class="email-input01"  type="text" value="@" /><strong><b id="thirdEmailWarn"></b></strong></li>
                    <li class="recomment_margin"><input id="fourthEmail" name="fourthEmail" class="email-input01"  type="text" value="@" /><strong><b id="fourthEmailWarn"></b></strong></li>
                    <li class="recomment_margin"><i class="send_recomment" id="send-email"></i></li>
                </ul>
            </div>
            <div class="recom-succeed" style="font-size:14px;color:#333;display:none;"> 
				<h3 class="recomment_tit"><i class="close" data-hide="recomment_wra"></i>推荐给好友</h3>
				<p class="frist-p">发送成功</p> 
				<p>您的好友将会在邮箱中看到您推荐的产品信息。</p> 
			</div>
      </div><!--recomment_wra end-->
      <input type="hidden" id="productId" name="productId" value="${prodCProduct.prodProduct.productId}" />
      <input type="hidden" id="productName" name="productName" value="${prodCProduct.prodProduct.productName}" />
