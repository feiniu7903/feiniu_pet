<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>抵扣款管理</title>
	<#assign dialog_inc = true>
	<#assign autocomplete_inc = true>
	<#assign grid_inc = true>
	<#assign datepicker_inc = true>
	<#assign grid_row_auto_height = true> <#include "../../common/define.ftl"/>
	<script type="text/javascript" src="${basePath}js/fin/settlement/fin_deduction.js"></script>
</head>

<body>
	<div class="wapper_accounts">
		<div class="wapper_list wapper_list_cash">
			<h3 class="order_check">抵扣款管理</h3>
			<div class="cash_seach">
				<form id="search_form" action="search.do" method="post">
					<ul class="order_top_list ">
						<li class="other_list"><label> 供应商：</label> 
							<input id="supplier" type="text" class="input_text02 input_combox"   autocomplete="off" />
						</li>
							<li class="other_cash">
							<input id="search_button" type="button" class="left_bt" value="查 询" />
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
	
	<!--修改-->
	<div id="update_div" class="hid" style="width: 400px;">
		<form id="update_form" method="post"> 
			<table>
				<tr height="50">
					<td width="50%" align="right">供应商名称：</td>
					<td width="50%" align="left">
						<input id="supplierId" type="hidden" name="supplierId" />
						<div id="update_supplier" class="tan_text"></div>
					</td>
				</tr>
				<tr height="50">
					<td width="50%" align="right">抵扣款：</td>
					<td width="50%" align="left">
						<div id="deduction_amount_yuan" class="tan_text"></div>
					</td>
				</tr>
				<tr height="50" valign="top">
					<td width="50%" align="right">
						<select name="type" width="50px" class="cash_name" id="type">
							<option value="DEPOSIT">增加</option>
							<option value="RETURN">退回</option>
						</select>
					</td>
					<td width="50%" align="left">
						&nbsp;<input id="amount" name="amount" type="text" maxlength="8"/>
					</td>
				</tr>
			</table>
			<div class="popups_button">
				<input type="submit" class="left_bt" value="确 定" />
			</div>
		</form>
	</div>
</body>
</html>
