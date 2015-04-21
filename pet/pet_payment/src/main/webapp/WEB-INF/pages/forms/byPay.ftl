<html>
	<head>
	
	<script type="text/javascript">
	function paySubmit(){
		document.byPayForm.submit();
	}
</script>
		<title>提交表单</title>
		
	</head> 
	<body  onload="javascript:paySubmit();">
	
	    <form name="byPayForm" id="byPayForm" action="http://localhost:12366/ipcc/default.jsp"  method="post">
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
						电话支付码：
					</td>
					<td width="647" height="35" align="left" valign="middle"
						class="td1">
						<input type="text" name="payCode" id="payCode" readonly="true" value="${payCode}" />
					</td>
				</tr>
				<tr>
					<td height="35" align="right" valign="middle">
					　	<input type="hidden" name="orderid" id="orderid" readonly="true" value="${objectId}" />
					    <input type="hidden" name="timestmp" id="timestmp" 	value="${timestmp}" />
						<input type="hidden" name="md5Sign" id="md5Sign" 	value="${md5sign}" />
						<input type="hidden" name="paytype" id="paytype" value="0" />
					</td>
					
				</tr>
			</table>
	
		</form>
	</body>
</html>
