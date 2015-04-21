<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../css/backstage/chinapnrlist.css">
		<script type="text/javascript" src="${base}/js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${base}/js/base/jquery-ui-1.8.5.js"></script>
		<title>提交表单</title>
		<script>
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
					
		});
		function checkSubmit(){
		    var username = document.getElementById('cardusername').value;	
			var bankGate = document.getElementById('bankgate').value;
			var mobile = document.getElementById('mobilenumber').value
			if(username==""){
				alert("请输入信用卡姓名!");
				return false;
			}	
			if(bankGate==""){
				alert("请选择银行网关!");
				return false;
			}
			if(mobile==""){
				alert("请输入手机号码!");
				return false;
			}
			return true;
		}
	</script>
	</head>
	<body>
		<form action="http://localhost:12366/ipcc/default.jsp"
			name="chinaprnorder" onsubmit="return checkSubmit();return false;">
			<table width="800" border="0" align="center" cellpadding="0"
				cellspacing="0" bgcolor="#e3effe" style="border: 1px solid #6f9dd9;"
				class="table">
				<tr>
					<td colspan="2" width="100%" height="35" align="center" valign="middle">
						汇付天下支付
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
						信用卡姓名：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="text" name="cardusername" id="cardusername" 
							value="" />
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						银行网关：
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
							<option value="Te">
								邮政储蓄
							</option>
							<option value="Th">
								工商银行
							</option>
							<option value="TX">
								浦发银行
							</option>
						</select>
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
						<input type="hidden" name="timestmp" id="timestmp" 	value="${timestmp}" />
						<input type="hidden" name="md5Sign" id="md5Sign" 	value="${md5sign}" />
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<textarea name="textarea" id="textarea" cols="45" rows="5"
							class="input1"></textarea>
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						&nbsp;
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="submit" name="button" id="button" value="提交表单"
							class="input2-button" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
