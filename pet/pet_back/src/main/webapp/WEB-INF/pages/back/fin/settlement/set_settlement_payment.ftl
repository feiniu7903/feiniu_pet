<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>打款记录</title>
	<#assign dialog_inc = true>
	<#assign autocomplete_inc = true>
	<#assign grid_inc = true>
	<#assign datepicker_inc = true>
	<#assign grid_row_auto_height = true> <#include "../common/define.ftl"/>
	<script type="text/javascript" src="${basePath}js/fin/settlement/set_settlement_payment.js"></script>
</head>

<body>
	<div class="wapper_accounts">
		<div class="wapper_list wapper_list_cash">
			<h3 class="order_check">打款记录</h3>
			<div class="cash_seach">
				<form id="searchPaymentHistoryForm"
					action="searchPaymentHistory.do" method="post">
					<ul class="order_top_list">
						<li><label>团&nbsp;&nbsp;&nbsp;&nbsp;号：</label> <input
							id="groupId" name="groupId" type="text" class="input_text" /></li>
						<li><label>结算单号：</label> <input id="settlementId"
							name="settlementId" type="text" class="input_text" /></li>
						<li><label>供应商：</label> <input id="supplier"
							autocomplete="off" type="text" class="input_text02" /></li>
					</ul>
					<ul class="order_top_list">
						<li><label>支付平台：</label> <SELECT id="platformSelect"
							name="platform">
								<OPTION selected value="">请选择</OPTION>
								<#list bankList as cur>
									<option value="${cur.code}">${cur.cnName}</option>
								</#list>
						</SELECT></li>
						<li class="order_top_list"><label>币种：</label> <SELECT
							id="currencySelect" name="currency">
								<OPTION selected value="">请选择</OPTION> 
								<#list currency as cur>
									<option value="${cur.code}">${cur.cnName}</option>
								</#list>
						</SELECT></li>
						<li style="width: 400px;"><label>打款时间：</label> <input
							id="payTimeStart" type="text" name="payTimeStart"
							class="input_text01 Wdate" onFocus="WdatePicker()" /> ~ <input
							id="payTimeEnd" type="text" name="payTimeEnd"
							class="input_text01 Wdate" onFocus="WdatePicker()" /></li>

						<li style="width:90px;"><INPUT type="button" value="查询"
							class="left_bt" onclick="searchPaymentHistoryHandler()">
						</li>
					</ul>

				</form>
				<div class="order_list">
					<table id="result_table"></table>
					<div id="pagebar_div"></div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
