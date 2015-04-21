<html>
	<head>
		<title>提交表单</title>
		<script type="text/javascript" src="${base}/js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${base}/js/base/jquery-ui-1.8.5.js"></script>
	</head>
	<body>
		<form id="byPay" action="http://pay.lvmama.com/payment/pay/byPay.do"
			name="chinaprnorder" onsubmit="return checkSubmit();return false;"  method="post">
			<div style="position:relative; width:800px;">
			         <div style="position:absolute; width:300px; height:150px; right:40px; top:5px; font-size:13px;">
			         <p>支持的银行(信用卡)：上海银行、兴业银行、浦发银行、农业银行、广发银行、建设银行、平安银行、光大银行、民生银行、中信银行、招商银行、中国银行、<b style="color:red">工商银行</b>、华夏银行</p> 
                     <p>支持的银行(借记卡)：上海银行、兴业银行、浦发银行、农业银行、广发银行、建设银行、平安银行、光大银行、民生银行、中信银行、交通银行、北京银行</p> 
                     <p>支付额度：每日或者单笔最大支付额度40000元</p>
                     <p>支付限制：单张银行卡每天只能成功支付3次</p>
                     <p>号码限制：<b style="color:red">来电手机必须是银行卡开户时预留的手机号才可支付</b></p>
                     <p>号段限制：<b style="color:red">目前支持的手机号段:130~139、150~159、180~183、185~189、145、147</b></p>
                     </div>
			<table width="800" border="0" align="center" cellpadding="0"
				cellspacing="0" bgcolor="#e3effe" style="border: 1px solid #6f9dd9;"
				class="table">
				<tr>
					<td colspan="2" width="100%" height="35" align="center" valign="middle">
						电话支付
					</td>
				</tr>
				<tr>
					<td width="151" height="35" align="right" valign="middle">
						订单号：
					</td>
					<td width="647" height="35" align="left" valign="middle"
						class="td1">
						
							<input type="text" name="objectId" id="objectId" readonly="true"
							value="${orderId}" />
							
							
							<input type="hidden" name="orderidsess" id="orderid" readonly="true"
							value="${orderId}" />
							<input type="hidden" name="orderid" id="orderid" readonly="true"
							value="${orderId}" />
					</td>
				</tr>
				
				<tr>
					<td height="35" align="right" valign="middle">
						支付金额(元)：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="paytotal" id="paytotal" value="${paytotal}" />
					</td>
				</tr>
				<#if (payDeposit?eval >0)>
				<tr>
					<td height="35" align="right" valign="middle">
						产品订金(元)：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="payDeposit" id="payDeposit" value="${payDeposit}" readonly="true" />
					</td>
				</tr>
				</#if>
				<tr>
					<td height="35" align="right" valign="middle">
						姓名<b style="color:red"> *</b>：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="cardusername" id="cardusername" 
							value="" />
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						手机号码<b style="color:red"> *</b>：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="mobilenumber" id="mobilenumber" value=""/>
					</td>
				</tr>
				
				<tr>
					<td height="35" align="right" valign="middle">
						客服号码：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="csno" id="csno" readonly="true"
							value="${csno}" />
							
						<input type="hidden" name="signature" id="signature" readonly="true"
							value="${signature}" />
								
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						卡类型：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="radio" name="bankCardType" checked="checked" value="debitCard"/>储蓄卡
						<input type="radio" name="bankCardType" value="creditCard" id="creditCard"/>信用卡
					</td>
				</tr>
				<tr id="bankChoose" style="display: none;">
					<td height="35" align="right" valign="middle">
						信用卡银行：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<select name="bankgate" id="bankgate">
							<option value="">
								请选择卡种信息
							</option>
							<option value="T1">
								建设银行
							</option>
							<option value="T2">
								民生银行
							</option>
							<option value="T7">
								中信银行
							</option>
							<option value="Ta">
								光大银行
							</option>
							<option value="Tb">
								上海银行
							</option>
							<option value="TB">
								中国银行
							</option>
							<option value="Th">
								工商银行
							</option>
							<option value="TX">
								浦发银行
							</option>
							<option value="BYPAY">
								兴业银行
							</option>
							<option value="BYPAY">
								农业银行
							</option>
							<option value="BYPAY">
								广发银行
							</option>
							<option value="BYPAY">
								平安银行
							</option>
							<option value="BYPAY">
								招商银行
							</option>
							<option value="BYPAY">
								华夏银行
							</option>
						</select>
					</td>
				</tr>
				
				<input type="hidden" name="objectType" id="objectType" readonly="true" value="${objectType}" />
				
				<input type="hidden" name="paymentType" id="paymentType" readonly="true" value="${paymentType}" />
				
				<input type="hidden" name="bizType" id="bizType" readonly="true" value="${bizType}" />
			
				<tr>
					<td height="35" align="right" valign="middle">
						
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="submit" name="button" id="button" value="提交表单" class="input2-button"/>
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle"></td>
					<td height="35" align="left" valign="middle" class="td1"></td>
				</tr>
			</table>
			</div>
		</form>
	</body>
<script type="text/javascript">
	$(function() {
		$.getScript("http://localhost:12366/ipcc/default.jsp?getphoneno&random="+Math.random(),
			function(){
				try{
					$("#mobilenumber").val(phoneNo);
					$("#mobilenumber").attr("readonly","readonly");
				}
				catch(e){
					$("#mobilenumber").removeAttr("readonly");
				}
			}
		);
		
		$("input[name=bankCardType]").click(function (){
			var val=$('input:radio[name="bankCardType"]:checked').val();
			if('creditCard'==val){
				$('#bankChoose').show();
			}
			else{
				$('#bankChoose').hide();
			}
		})	
	});
	function checkSubmit(){
		var paytotal=document.getElementById("paytotal").value;
		var mobilenumber=document.getElementById("mobilenumber").value;
		var username = document.getElementById('cardusername').value;	
		var bankGate = document.getElementById('bankgate').value;
		var bankCardType=$('input:radio[name="bankCardType"]:checked').val();
		var r = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		
		if(username==""){
			alert("请输入姓名!");
			return false;
		}	
		if(bankGate=="" && 'creditCard'==bankCardType){
			alert("请选择信用卡银行!");
			return false;
		}
		if(mobilenumber==null || mobilenumber==''){
			alert("手机号码不能为空!");
			return false;
		}
		if(!r.test(paytotal)){
			alert("支付金额格式不正确!");
	        return false;
        }
		if(paytotal<=0){
			alert("支付金额必须大于零!");
			return false;
		}
		if(paytotal>40000){
			alert("单笔最大支付额度不能超过40000元!");
			return false;
		}
		if(paytotal>${paytotal}){
			alert("支付金额不能超过${paytotal}元");
			return false;
		}
		if(${payDeposit}>0 && paytotal<${payDeposit} && ${order.actualPayFloat}<${payDeposit}){
			alert("首次支付金额不能小于产品订金!");
			return false;
		}
		
		//goto chinapnr
		if(paytotal>=0.1 && paytotal<=2000 && 'creditCard'==bankCardType && 'BYPAY'!=bankGate && "TICKET"!="${order.orderType}"){
			$('#byPay').attr('action', 'http://localhost:12366/ipcc/default.jsp');
		}
	}
</script>
</html>
