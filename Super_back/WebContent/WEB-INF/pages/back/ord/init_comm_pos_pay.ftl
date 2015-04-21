<html>
	<head>
		<title>交通银行POS机支付初始化</title>
		<script type="text/javascript" src="${base}/js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${base}/js/base/jquery-ui-1.8.5.js"></script>
	</head>
	<body>
		<form id="commPos" name="commPos" action="http://pay.lvmama.com/payment/pay/payPos.do" method="post">
			<table width=380 border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#e3effe" style="border: 1px solid #6f9dd9;" class="table">
				<tr>
					<td height="35" align="right" valign="middle">
						订单号：
					</td>
					<td height="35" align="left" valign="middle"
						class="td1">
						
							<input type="text" name="objectId" id="objectId" readonly="true"
							value="${orderId}" />
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						支付金额(元)：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="paytotal" id="paytotal" value="${paytotal}" />
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						支付类型：
					</td>
					<td align="left" valign="middle" class="td1">
						<select name="posPaymentType" style="height: 22px;width: 185px;">
							<option value="COMM_POS" selected="selected">银行卡</option>
							<option value="COMM_POS_CASH">现金</option>
						</select>
					</td>
				</tr>
				
				<input type="hidden" name="objectType" id="objectType" readonly="true" value="${objectType}" />
				<input type="hidden" name="paymentType" id="paymentType" readonly="true" value="${paymentType}" />
				<input type="hidden" name="bizType" id="bizType" readonly="true" value="${bizType}" />
				<input type="hidden" name="signature" id="signature" readonly="true"/>
				<input type="hidden" name="amount" id="amount" readonly="true"/>
				<tr>
					<td height="35" align="right" valign="middle">
						
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="button" value="生成POS机支付流水号" class="input2-button" onclick="checkSubmit();"/>
					</td>
				</tr>
			</table>
			</div>
		</form>
	</body>
<script type="text/javascript">
	function checkSubmit(){
		var paytotal=document.getElementById("paytotal").value;
		var r = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		if(!r.test(paytotal)){
			alert("支付金额格式不正确!");
	        return false;
        }
		if(paytotal<=0){
			alert("支付金额必须大于零!");
			return false;
		}
		if(paytotal>${paytotal}){
			alert("支付金额不能超过${paytotal}元");
			return false;
		}
		reGenerateSignatureAndAmount();
		$('#commPos').submit();
	}
	
	function reGenerateSignatureAndAmount(){
		var paytotal=document.getElementById("paytotal").value;
		$.ajax({
			type:"POST", 
			url:'${base}/ord/payment/posReGenerateSignature.do?random='+Math.random(), 
			data:{orderId:${orderId},amount:paytotal}, 
			async: false, 
			success:function (data) {
				var dt = eval("(" + data + ")");
				$('#signature').val(dt.newSignature);
				$('#amount').val(dt.amount);
			}
		});
	}
</script>
</html>
