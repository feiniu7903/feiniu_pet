<!--========= 游玩人信息 S ========-->
  	<div class="s2-info-area">
    	<h3>游玩人信息<em>(请准确填写游玩人信息，我们将向游玩人手机发送订单短信)</em></h3>
    	<dl>

        	<dt><span>*</span>游玩人姓名：
            <strong class="savename"> 
            <@s.if test="isLogin()">
            	<input type="checkbox" value="true" checked="true" name="contact.useOffen" />保存到常用姓名
            </@s.if>
            </strong>
            </dt>
            <dd><input name="buyRouteName" type="text" id="buyRouteName" rName="rName" value="<@s.property value="contact.receiverName"/>" name="contact.receiverName" >
	            <@s.if test="isLogin()">
		    		<select name="receiversList" id="receiversList" onchange="updateBuyRouteInfo(this)">
						<option value="">从常用联系人中选</option>
		    			<@s.iterator value="receiversList">
		     				<option value="${receiverId?if_exists}" email="${email?if_exists}" card_type="${cardType?if_exists}" card_num="${cardNum?if_exists}" mobile="${mobileNumber?if_exists}" name="${receiverName?if_exists}">${receiverName?if_exists}</option>
		    			</@s.iterator>
		   			</select>
		   		</@s.if>
            </dd>
        	<dt><span>*</span>游玩人手机：</dt>
            <dd><input type="text" id="buyRouteMobile" rMobileNumber="rMobileNumber"  name="contact.mobileNumber" value="<@s.property value="contact.mobileNumber"/>" ></dd>
            <dt><span>*</span>证件类型：</dt>
            <dd><select id="cardType0" name="contact.cardType" class="mg">
                    <option value="">请选择证件类型</option>
                    <option value="ID_CARD" selected="selected">身份证</option>
                    <option value="HUZHAO">护照</option>
                    <option value="OTHER">其他</option>
            </select></dd>
            <dt><span>*</span>证件号码：</dt>
            <dd><input type="text" value="" id="cardType0" rTraveller="rTraveller" name="contact.cardNum" onKeyDown="return checkType('cardType0',this)">
            <input type="hidden" id="buyTicketPersonId" name="contact.receiverId" class="text_inp" />
            </dd>
        </dl>
    
    <!--========= 旅客信息 S ========-->

		<strong class="trav-info-title">其他游玩人信息&nbsp;</strong><em>(请准确填写旅客信息，以便办理登机手续和保险)</em><a href="javascript:void(0)"  onmouseover="tipsTool(this,'qijiashuoming','shuoming')"  id="qijiashuoming">填写说明</a>
        <div class="shuoming">
            <strong>填写说明：</strong><br>
            1.乘客姓名必须与登机时所使用证件上的名字一致，如“王小明”。<br>
            2.如持护照登机，使用中文姓名，必须确认护照上有中文姓名。<br>
            3.持护照登机的外宾，必须按照护照顺序区分姓与名，<br>&nbsp;&nbsp;例如“Smith/Black”，不区分大小写。
        </div>
    	<div>
        	<ul>
              <li><p><input type="radio" id="tra_id" checked="checked" onclick='$("#tra_info").html("")' name="radioWrite"/><label for="tra_id">客服电话联系我时提供</label>&nbsp;&nbsp;&nbsp;<input type="radio" id="radioWriteId" onclick="addPerson('paramName')" name="radioWrite" /><label for="radioWriteId">现在填写</label></p></li>
              <li class="travellor"  id="tra_info">
              
              </li>
            </ul>
        </div>
    
       <!--========= 旅客信息 E ========-->
    </div>

    <!--========= 发票信息 S ========-->

