<html>
	<head>
		<title>提交表单</title>
		<script>
		function checkSubmit(){
		    var username = document.getElementById('cardusername').value;	
			var mobile = document.getElementById('mobilenumber').value
			if(username==""){
				alert("借记卡姓名!");
				return false;
			}	
			if(mobile==""){
				alert("输入手机号码!");
				return false;
			}
			return true;
		}
	</script>
	</head>
	<body>
		<form id="allInPay" action="http://localhost:12366/ipcc/default.jsp"
			name="chinaprnorder" onsubmit="return checkSubmit();return false;"  method="post">
			<div style="position:relative; width:800px;">
			            <div style="position:absolute; width:300px; height:150px; right:40px; top:58px; font-size:13px;">
			            <p>支持的银行：招商银行、中国农业银行、中国邮政储蓄银行、中国银行、建设银行、浦东发展银行、华夏银行、交通银行、中信银行。</p> 
<p>支付额度：除建设银行外，其他银行的单笔额度1万，日累计额度5万。建行单笔最高3500元/日，月累计达到10000元。</p>
</div>
			
			<table width="800" border="0" align="center" cellpadding="0"
				cellspacing="0" bgcolor="#e3effe" style="border: 1px solid #6f9dd9;"
				class="table">
				<tr>
					<td colspan="2" width="100%" height="35" align="center" valign="middle">
						通联电话支付
					</td>
				</tr>
				<tr>
					<td width="151" height="35" align="right" valign="middle">
						订单号：
					</td>
					<td width="647" height="35" align="left" valign="middle"
						class="td1">
						<input type="text" name="orderidsess" id="orderid" readonly="true"
							value="${orderId}" />
							<input type="hidden" name="orderid" id="orderid" readonly="true"
							value="${orderId}" />
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						订单金额：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="paytotal" id="paytotal" readonly="readonly" value="${paytotal}" />
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						借记卡姓名：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="cardusername" id="cardusername" 
							value="" />
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						客服号码：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="csno" id="csno" readonly="true"
							value="${csno}" />
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						手机号码：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="mobilenumber" id="mobilenumber" 	value="" />
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<textarea name="textarea" id="textarea" cols="45" rows="5"
							class="input1"></textarea>
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						<input type="hidden" name="timestmp" id="timestmp" 	value="${timestmp}" />
						<input type="hidden" name="md5Sign" id="md5Sign" 	value="${md5sign}" />
						<input type="hidden" name="paytype" id="paytype" value="2" />
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="submit" name="button" id="button" value="提交表单"
							class="input2-button" />
					</td>
				</tr>
			</table>
			</div>
		</form>
	</body>
</html>
