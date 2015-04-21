<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>结算单管理</title> <#assign dialog_inc = true>
<#assign grid_row_auto_height = true> <#include "../common/define.ftl"/>
<script type="text/javascript" src="${basePath}js/fin/settlement/set_settlement.js"></script>
</head>

<body>
	<div class="wapper_accounts" style="width: 1200px;">
		<div class="wapper_list wapper_list_cash">
			<h3 class="order_check">结算单管理</h3>
			<div class="cash_seach">
				<form id="search_form" grid_action="searchList.do" excel_action = "exportExcel.do" method="post" >
					<input type="hidden" id="settlementType" name="settlementType" value="${settlementType}"/>
					<ul class="order_top_list">
						<li class="other_list">
							<label> 结算对象：</label> 
							<input id="targetId" type="text" class="input_text02 input_combox"   autocomplete="off" />
						</li>
						<li class="other_list">
							<label> 采购产品：</label> 
							<input id="metaProductId" type="text" class="input_text02 input_combox"   autocomplete="off" />
						</li>
						<li class="other_list2" >
							<label>生成时间：</label> 
							<input name="createTimeBegin" type="text" class="input_text01 Wdate" onclick="WdatePicker({isShowClear:true,readOnly:true})"/>
								~
							<input name="createTimeEnd" type="text" class="input_text01 Wdate" onclick="WdatePicker({isShowClear:true,readOnly:true})"/>
						</li>
					</ul>
					<ul class="order_top_list ">
						<li class="other_list">
							<label class="data_list">结算单号：</label>
							<input name="settlementId" type="text" class="input_text02" value="${settlementId!""}" maxlength= "80">
						</li>
						<li class="other_list">
							<label class="data_list">订单号：</label>
							<input name="orderId" type="text" class="input_text02" maxlength= "80">
						</li>
						<li class="other_list">
							<label>用&nbsp;户&nbsp;名：</label>
							<input name="userName" type="text" class="input_text02 input_combox" autocomplete="off" />
						</li>
					</ul>
					<ul class="order_top_list ">
						<li class="other_list"  >
							<label>状态：</label> 
							<select name="status"	class="cash_select">
									<option value="">全部</option>
									<#list setSettlementStatus as item>
										<option value="${item.code}">${item.cnName}</option>
									</#list>
							</select>
						</li>
							<li class="other_list" style ="width:300px;">
							<label>所属分公司：</label>
							<select  name="filialeName" class="cash_select" style="width:180px;">
								<option value="">请选择</option>
								<#list filialeNames as item>
									<option value="${item.code}">${item.cnName}</option>
								</#list>
							</select>
						</li>
						<li class="other_cash" >
							<input id="search_button" type="button" class="left_bt" style="margin-left: 55px;" value="查 询" />
							<a id ="export_button" href="javascript:void(0);" class="hid" >导出excel</a> 
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
    <#if init??>
		<script type="text/javascript">
			$(document).ready(function(){
			  $("#search_button").click();
			});
		</script>
	</#if>
</body>
</html>
