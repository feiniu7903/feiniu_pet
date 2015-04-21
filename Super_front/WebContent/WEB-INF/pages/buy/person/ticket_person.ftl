<div class="s2-info-area">
    	<h3>取票人信息<em>(请准确填写取票人信息，我们将向取票人手机发送订单短信)</em></h3>

    	<dl>
        	<dt><span>*</span>取票人姓名：
            <strong class="savename">
            <@s.if test="isLogin()">
            <input type="checkbox" value="true" checked="true" name="contact.useOffen" />保存到常用姓名
            </@s.if>
            </strong></dt>
            <dd><input type="text" id="fetchTicketUserName"  rName="rName" name="contact.receiverName"  value="<@s.property value="contact.receiverName"/>"/>
            <input type="hidden" id="fetchTicketUserReceiverId" value="" name="contact.receiverId" />
	            <@s.if test="isLogin()">
		            <select name="myReceivers" id="myReceivers"  onchange="updateFetchTicketInfo(this)">
						<option value="">从常用联系人中选</option>
					    <@s.iterator value="receiversList">
					     <option value="${receiverId?if_exists}" card_type="${cardType?if_exists}" card_num="${cardNum?if_exists}" mobile="${mobileNumber?if_exists}" name="${receiverName?if_exists}">${receiverName?if_exists}</option>
					    </@s.iterator>
					</select>
				</@s.if>
            </dd>
            
        	<dt><span>*</span>取票人手机：</dt>
            <dd><input type="text" id="fetchTicketUserMobile" rMobileNumber="rMobileNumber"  name="contact.mobileNumber"   value="<@s.property value="contact.mobileNumber"/>"/></dd>
        </dl>
    
    </div>
    
    
    
    <!--========= 取票人信息 E ========-->

