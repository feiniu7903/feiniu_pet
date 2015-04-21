<link href="http://pic.lvmama.com/styles/v4style/myspace_fapiao.css?r=3252" rel="stylesheet" type="text/css" />
<h3><s></s>取票人信息<em>（请准确填写取票人信息，我们将向取票人手机发送订单短信）</em></h3>
<dl class="user-info">
	<@s.if test="isLogin() && !receiversList.empty">
		<br/>
    		<div class="lianxi_contant">
			<h4 class="title_add">请选择常用人姓名</h4>
			<ul class="lianxi_person">
    				<@s.iterator value="receiversList" status="st">
    					<!-- 进行格式布局显示,每一行显示5列. -->
    					<@s.if test="((#st.index) % 5 == 0)"><tr></@s.if>
    					<li>
    						<input type="checkbox" name="_recerverName" value="${receiverId}" card_type="${cardType}" card_num="${cardNum}"  mobile="<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobileNumber)" />" pinyin="${pinyin}" receiverName="${receiverName}" class="inp-checkbox" onclick="updateFetchTicketInfo2(this);"/> 
    						<label class="label_names">
							<#if receiverName?length gt 15>&nbsp;${receiverName?substring(0,14)}...&nbsp;<br/><#else>&nbsp;${receiverName?if_exists}&nbsp;<br/></#if>
						</label>
    	 				</li>	
    	 				<@s.if test="((#st.index) % 5 == 4)"></tr></@s.if>
    				</@s.iterator>
    			</ul>
    		</div>
    		<br/>
    	</@s.if>
	
	<@s.if test='contactInfoOptions.contains("NAME")'>
    		<dt><span>*</span>取票人姓名：</dt>
    		<dd>
    			<input type="text" size="20" class="inp-txt" id="fetchTicketUserName" value="<@s.property value="contact.receiverName"/>" rName="rName" name="contact.receiverName"  maxlength="16"/>
    			<@s.if test="isLogin()">
				<span class="contactMsg"></span>
    				<input id="saveToLinkmans" type="checkbox" value="true" name="contact.useOffen" checked="checked"/>&nbsp;保存到常用姓名
    			</@s.if>
    		</dd>
	</@s.if>

	<@s.if test='contactInfoOptions.contains("PINYIN")'>
    		<dt><span>*</span>姓名拼音：</dt>
    		<dd>
    			<input type="text" size="20" class="inp-txt" id="fetchTicketUserNamePinYin" value="<@s.property value="contact.pinyin"/>" rPinYin="rPinYin" name="contact.pinyin"  maxlength="20"/>
				<span>（填写内容请与您的护照或港澳通行证一致）</span>
    		</dd>
	</@s.if>

	<@s.if test='contactInfoOptions.contains("MOBILE")'>
    		<dt><span>*</span>取票人手机：</dt>
    		<dd>
    			<input type="hidden" id="fetchTicketUserReceiverId"  name="contact.receiverId" value="<@s.property value="contact.receiverId"/>"/>
    			<input type="text" size="20" class="inp-txt" id="fetchTicketUserMobile" rMobileNumber="rMobileNumber" value="<@s.property value="contact.mobileNumber"/>" name="contact.mobileNumber"/>
    			<span>（免费接收短信，作为订购与取票凭证，请准确填写）</span>
    		</dd>
	</@s.if>

	<@s.if test='contactInfoOptions.contains("CARD_NUMBER")'>
    		<dt><span>*</span>证件号码：</dt>
    		<dd>
				<select class="select-left" id="cardType1" cardType="cardType" name="contact.cardType" onChange="return checkType('cardType1','cardNum1')">
					<option value="ID_CARD" selected="selected">身份证</option>
					<option value="HUZHAO">护照</option>
				</select>
				<span><input type="text" size="30" class="inp-txt" id="cardNum1" rTraveller="rTraveller" rCardNumber="rCardNumber" name="contact.cardNum"/></span>
				<span id="childrenBrithdayInfoSpan1" name="childrenBrithdayInfoSpan" style="display:none">
						<li class="add_xu_add">
						   <span>
						   	<label id="brithdayDateLabelId" class="laber_names"><em id="brithdayDateEmId">*</em>出生年月：</label> <input id="brithdayDate1" rBrithday="rBrithday" name="contact.brithday" type="text" value=""  readonly="true"/>
							</span> 
						</li>
					</span>		
				 	<span id="genderSpan1" name="genderSpan" style="display:none">
						<li class="add_xu_add">
						   <span id="gender">
						   	<label id="genderLabelId" class="laber_names"><em id="genderEmId">*</em>游玩人性别：</label> 
						   	<input id="genderM1" name="contact.gender" type="radio" value="M" />男&nbsp;
						   	<input id="genderF1" name="contact.gender" type="radio" value="F" />女
							</span> 
						</li>
					</span> 
    		</dd>
	</@s.if>
</dl>

<!-- 根据销售产品是否需要邮寄来决定是否显示邮寄地址页面. -->
<#if mainProdBranch.prodProduct.physical == "true">
	<@s.if test="isLogin()">
		 <@s.if test="!usrReceiversList.empty">
			<div id="receiverAddressDivId">
				<#include "/WEB-INF/pages/buy/201107/receiverAddress.ftl"/>
			</div>
		   </@s.if>
		 <@s.if test="usrReceiversList.empty">
		 	<div id="receiverAddressDivId">
		 	 	<#include "/WEB-INF/pages/buy/201107/receiverAddress2.ftl"/>
		 	</div>
		 </@s.if>
	</@s.if>
	<@s.if test="!isLogin()">
		 <#include "/WEB-INF/pages/buy/201107/receiverAddress2.ftl"/>
	</@s.if>
</#if>
	<script type="text/javascript">
		//保存最近一次选中的复选框对象.
		var checkedBuyRouteName = null;
		//点击"取票人信息"复选框.
		function updateFetchTicketInfo2(obj){
			if (checkedBuyRouteName != null) {
				$(checkedBuyRouteName).attr("checked",false);
			}
			if ($(obj).attr("checked")) {
				$("#fetchTicketUserName").val($(obj).attr("receiverName"));
				$("#fetchTicketUserMobile").val($(obj).attr("mobile"));
				$("#fetchTicketUserMobile").attr("mode","super");
				$("#fetchTicketUserNamePinYin").val($(obj).attr("pinyin"));
				$("#fetchTicketUserReceiverId").val($(obj).val());
				$("#saveToLinkmans").attr("checked",false);
				$("#saveToLinkmans").attr("disabled",true);
				checkedBuyRouteName = obj;
			} else {
				$("#fetchTicketUserName").val("");
				$("#fetchTicketUserMobile").val("");
				$("#fetchTicketUserMobile").attr("mode","simple");
				$("#fetchTicketUserReceiverId").val("");
				$("#saveToLinkmans").attr("checked",true);
				$("#saveToLinkmans").attr("disabled",false);
				checkedBuyRouteName = null;
			}
	
			//联系人姓名 文本框.
			$("#fetchTicketUserName").focus(function () {
				clickNameOrMobile();
			});
			//联系人姓名拼音 文本框.
			$("#fetchTicketUserNamePinYin").focus(function () {
				clickNameOrMobile();
			});
			//联系人手机 文本框.
			$("#fetchTicketUserMobile").focus(function () {
				clickNameOrMobile();
			});
			//点击联系人姓名、手机文本框.
			function clickNameOrMobile() {
				if (checkedBuyRouteName != null) {
					$(checkedBuyRouteName).attr("checked",false);
					$("#saveToLinkmans").attr("disabled",false);
					$("#saveToLinkmans").attr("checked",true);
					checkedBuyRouteName = null;
				}
			}
		}
		
		$(document).ready(function() {
			var minDate = new Date(Date.parse(new Date()) - 1000 * 60 * 60 * 24 * 365 * 100);
			var maxDate = new Date();
			
 			$("input[name='contact.brithday']" ).datepicker({
 				dateFormat:'yy-mm-dd',
 				minDate:minDate,
 				maxDate:maxDate,
 				changeYear: true,
 				changeMonth: true, 
 				monthNamesShort:['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],
 				dayNamesMin: ['日','一','二','三','四','五','六'],
 				yearRange:"1910:2050"
 			});
		});
	</script>
