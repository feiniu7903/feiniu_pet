<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>押金预警</title>
	<#assign dialog_inc = true>
	<#assign autocomplete_inc = true>
	<#assign grid_inc = true>
	<#assign datepicker_inc = true>
	<#assign grid_row_auto_height = true> <#include "../../common/define.ftl"/>
	<script type="text/javascript" src="${basePath}js/finance/settlement/fin_deposit_warning.js"></script>
</head>

<body>
	<div class="wapper_accounts">
		<div class="wapper_list wapper_list_cash">
			<h3 class="order_check">押金预警</h3>
			<div class="cash_seach">
				<div class="order_list">
					<table id="result_table"></table> 
					<div id="pagebar_div"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
