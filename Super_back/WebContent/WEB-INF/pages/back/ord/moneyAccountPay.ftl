<html>
	<head>
		<title>提交表单</title>
		<script type="text/javascript" src="/super_back/js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="/super_back/js/moneyAccount/moneyAccountPay.js"></script>
	</head>
	<body>
		<!-- /super_back/pay/moneyAccountPay.do-->
		<form id="moneyAccount" action="http://localhost:12366/ipcc/default.jsp" name="moneyAccount" >
			<table width="800" border="0" align="center" cellpadding="0"
				cellspacing="0" bgcolor="#e3effe" style="border: 1px solid #6f9dd9;"
				class="table">
				<tr>
					<td colspan="2" width="100%" height="35" align="center" valign="middle">
						存款帐户电话支付
					</td>
				</tr>
				<tr>
					<td width="151" height="35" align="right" valign="middle">
						订单号：
					</td>
					<td width="647" height="35" align="left" valign="middle"
						class="td1">
						<input type="hidden" name="orderidsess" id="orderid" readonly="true" value="${orderId}" />
						<input name="orderid" type="text"  id="orderid" value="${orderId}" class="txt_dis" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						订单金额：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input name="paytotal" type="text" id="paytotal" value="${paytotal}" readonly="true" class="txt_dis"/>
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
						存款帐户手机号码：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<#if moneyAccountMobileSafe?exists>
		                	<input name="hasBinded" type="hidden" id="hasBinded" value="Y" /> 
							<input name="moneyAccountMobile" type="hidden" id="moneyAccountMobile" value="${moneyAccountMobile}" class="txt_dis"/>
		                	<input name="moneyAccountMobileSafe" type="text" id="moneyAccountMobileSafe" value="${moneyAccountMobileSafe}" readonly="true" class="txt_dis"/>
			           </#if>
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
						帐户名称：
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input name="accountName" type="text" id="accountName" value="${accountName}"  class="txt_dis" readonly="true"/>
					</td>
				</tr>

				<tr>
					<td height="35" align="right" valign="middle">
						<input name="hasDynamicCode" type="hidden" id="" value="N" class="txt_dis" />
						<input type="hidden" name="userid" id="userid" value="${order.userId}" />
						<input type="hidden" name="timestmp" id="timestmp" 	value="${timestmp}" />
						<input type="hidden" name="md5Sign" id="md5Sign" 	value="${md5sign}" />
						<input type="hidden" name="paytype" id="paytype" value="3" />
					</td>
					<td height="35" align="left" valign="middle" class="td1">
						<input type="submit" name="button" id="submitButton" value="提交表单"
							class="input2-button" />
					</td>
				</tr>
			</table>
			</div>
		</form>
	</body>
</html>
