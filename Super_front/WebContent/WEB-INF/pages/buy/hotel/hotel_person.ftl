<div>
    	<h3>入住人信息<em>(请准确填写入住人信息，我们将向入住人手机发送订单短信)</em></h3>

    	<dl>
        	<dt><span>*</span>入住人姓名：
            <strong class="savename">
            <@s.if test="isLogin()">
            <input type="checkbox" value="true" name="contact.useOffen" />保存到常用姓名
            </@s.if>
            </strong></dt>
            <dd><input type="text" id="fetchTicketUserName" value="" rName="rName" name="contact.receiverName" />
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
            
        	<dt><span>*</span>入住人手机：</dt>
            <dd><input type="text" id="fetchTicketUserMobile" rMobileNumber="rMobileNumber" value="" name="contact.mobileNumber" /></dd>
        </dl>
    
    </div>
    
    
    
    <!--========= 取票人信息 E ========-->

