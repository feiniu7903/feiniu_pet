<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate"> 
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1000 08:21:57 GMT"> 
<META HTTP-EQUIV="expires" CONTENT="0"> 
<title>订单结算数据修复</title> <#assign dialog_inc = true>
<#assign grid_row_auto_height = true> <#include "../common/define.ftl"/>
</head>

<body>
	<div class="wapper_accounts" style="width: 1500px;">
		<div class="wapper_list wapper_listadd_26">
			<div class="order_top order_top_26_add current01">
				<form id="settlement_data_repair_form" action="${basePath}/finance/set/datarepair.do" method="post">
					<table border="0">
						<tr>
							<td height="30" align="right"><label>订单结算修复数据：</label>
							</td>
							<td height="30">
								<textarea name="settlementData" style="width: 624px; height: 362px;" value=""></textarea>
							</td>
							<td>
								格式A：关联表##订单号（订单子子项ID）##消息类型<br/>
								格式B：关联表##订单号（订单子子项ID）##消息类型##附加内容<br/>
								消息类型：<br/>
								ORDER_SETTLE 结算<br/>
								ORDER_MODIFY_TOTAL_SETTLEMENT_PRICE 修改结算总价<br/>
								ORDER_MODIFY_SETTLEMENT_PRICE 修改结算价<br/>
								ORDER_APPROVE订单审核<br/>
								ORDER_PAYMENT订单支付<br/>
								ORDER_RESTORE订单恢复（从取消状态改为正常状态）<br/>
								ORDER_CANCEL 订单取消<br/>
								ORDER_REFUNDED订单退款成功<br/>
								PASSCODE_APPLY_SUCCESS 申码成功<br/>
								<font color="red">${msg?if_exists}</font>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" align="right">
							<input id="save_button" type="button" class="left_bt" value="修 复"/>
							</td>
							<td></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
<script  type="text/javascript">
	$(function(){
		$("#save_button").click(function(){
			$("#settlement_data_repair_form").submit();
			$("textarea").val("");
		});
	});
</script>
</body>
</html>
