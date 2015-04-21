<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>订单结算</title> <#assign dialog_inc = true>
<#assign grid_row_auto_height = true> <#include "../common/define.ftl"/>
<script type="text/javascript" src="${basePath}js/fin/settlement/set_settlement_item.js?v=20140411"></script>
</head>

<body>
	<div class="wapper_accounts" style="width: 1500px;">
		<ul class="add_tap_list">
			<li id="payToLvmamaTab" class="current"><a onclick="changePaymentTargetHandler(0)" href="#">支付给驴妈妈</a></li>
		</ul>
		<div class="wapper_list wapper_listadd_26">

			<div class="order_top order_top_26_add current01">
				<form id="search_settlement_item_form" action="searchList.do" method="post">
					<table border="0">
						<tr>
							<td height="30" align="right"><label>结算对象：</label>
							</td>
							<td height="30">
							<input id = "settlementType" type ="hidden" name = "settlementType" value="${settlementType}"/>
							<input id="targetId" name="settlementTarget" autocomplete="off" type="text" class="table_input_style" /> 
							<label class="none_list" for="notContainLvmama">
							<input id ="notContainLvmama" name="notContainLvmama" type="checkbox" class="check_box_style" value ="1"/> 不包含驴妈妈 
							</label>
							</td>
							<td height="30" align="right"><label class="data_list">游玩日期：</label>
							</td>
							<td height="30"><input id="visitDateStart" name="visitDateStart" type="text" class="input_text01 table_input_other Wdate" onClick="WdatePicker()" /> ~ <input id="visitDateEnd" name="visitDateEnd" type="text" class="input_text01 table_input_other Wdate" onClick="WdatePicker()" /></td>
							<td height="30" align="right"><label>结算周期：</label>
							</td>
							<td height="30">
								<select id="settlementPeriodSelect" name="settlementPeriod" class="select_option" 
									onchange="periodChangeHandler()">
									<option value="">请选择</option>
									<option value="PERORDER">每单结算</option>
									<option value="PERMONTH">每月结算</option>
									<option value="PERQUARTER">每季结算</option>
									<option value="PER_WEEK">每周结算</option>
									<option value="PER_HALF_MONTH">每半月结算</option>
								</select> 
								<label class="none_list" for="hasRefunded"> 
								<input id = "hasRefunded" name="hasRefunded"  type="checkbox" class="check_box_style" value = "1" /> 有退款的订单 </label>
							</td>
						</tr>
						<tr>
							<td height="30" align="right"><label>采购产品：</label>
							</td>
							<td height="30">
							<input id="metaProductId" name="metaProduct" autocomplete="off" type="text" class="input_text02 table_input_style" />&nbsp;&nbsp; 
							<select id="metaBranchTypeSelect" name="metaBranchType" class="select_option" style="width: 100px;">
									<option value="">采购产品类别</option>
							</select></td>
							<td height="30" align="right"><label class="data_list">支付时间：</label>
							</td>
							<td height="30"><input id="payTimeStart" name="payTimeStart" type="text" class="input_text01 Wdate table_input_other " onClick="WdatePicker()" /> ~ <input id="payTimeEnd" name="payTimeEnd" type="text" class="input_text01 Wdate table_input_other " onClick="WdatePicker()" /></td>
							<td height="30" align="right"><label>履行状态：</label></td>
							<td height="30" >
								<select id="orderPerformStatusSelect" name="performStatus" class="select_option" >
									<option value="">请选择</option>
									<#list orderPerformStatus as item>
										<option value="${item.code}">${item.cnName}</option>
									</#list>
								</select>
								&nbsp;&nbsp;
								<label>状态：</label>
								<#list setSettlementItemStatus as item>
								<label class="none_list" for="status_${item.code}">
									<input id = "status_${item.code}" type="checkbox" name="status" value = "${item.code}"  <#if item.code == 'NORMAL'>checked="checked" </#if> class="check_box_style"/> ${item.cnName}
								</label>
							</#list>
							</td>
						</tr>
						<tr>
							<td height="30" align="right"><label> 供应商：</label></td>
							<td height="30"><input id="supplierId" name="supplier" type="text" autocomplete="off" class="input_text02 table_input_style" /></td>
							<td height="30" align="right"><label>建议打款时间：</label>
							</td>
							<td height="30"><input id="suggestionPayDateStart" name="suggestionPayDateStart" type="text" class="input_text01 Wdate table_input_other" onFocus="WdatePicker({minDate:'%y-%M-%d'})" /> ~ <input id="suggestionPayDateEnd" name="suggestionPayDateEnd" type="text" class="input_text01 Wdate table_input_other" onClick="WdatePicker({minDate:'%y-%M-%d'})" /></td>
							<td height="30" align="right">&nbsp;&nbsp;&nbsp;<label> 供应商银行账号：</label>
							</td>
							<td height="30"><input id="bankAccountInput" name="bankAccount" type="text" class="input_text02 input_text03 table_input_style table_input_style01" />
							</td>
						</tr>
						<tr>
							<td height="30" align="right"><label>订单号：</label>
							</td>
							<td height="30"><input id="orderIdInput" name="orderId" type="text" class="input_text02 table_input_style" maxlength= "80">
							</td>
							<td height="30" align="right"><label> 我方结算主体：</label>
							</td>
							<td height="30">
							<select id="settlementCompanySelect" name="settlementCompany" class="select_option" style="width:180px;">
								<option value="">请选择</option>
								<#list settlementCompany as item>
									<option value="${item.code}">${item.cnName}</option>
								</#list>
							</select>
							</td>
							<td height="30" align="right"><label> 用户名：</label>
							</td>
							<td height="30">
								<input name="username" type="text" class="input_text02 table_input_style"
									hidden_value="${userName}"/>
							</td>
							<td height="30" align="right"></td>
							<td height="30"></td>
						</tr>
						<tr>
							<td height="30" align="right"><label>下单日期：</label></td>
							<td height="30">
								<input id="createOrderTimeBegin" name="createOrderTimeBegin" type="text" class="input_text01 Wdate table_input_other " onClick="WdatePicker()" /> ~
								<input id="createOrderTimeEnd" name="createOrderTimeEnd" type="text" class="input_text01 Wdate table_input_other " onClick="WdatePicker()" />
							</td>
								
								<td height="30" align="right"><label> 采购主体：</label>
								</td>
								<td height="30">
								<select  name="metaFilialeName" class="select_option" style="width:180px;">
									<option value="">请选择</option>
									<#list filialeNames as item>
										<option value="${item.code}">${item.cnName}</option>
									</#list>
								</select>
								</td>
							
								<td height="30" align="right"><label> 所属分公司：</label>
								</td>
								<td height="30">
								<select  name="filialeName" class="select_option" style="width:180px;">
									<option value="">请选择</option>
									<#list filialeNames as item>
										<option value="${item.code}">${item.cnName}</option>
									</#list>
								</select>
								</td>
								<td>&nbsp;</td>
						</tr>
						<tr>
								<td height="30" align="right"><label> 销售产品类型：</label>
								</td>
								<td height="30">
								<label style="width: 85px;"><input name="productType" type="checkbox" value="TICKET" checked="checked" />门票</label>
								<label style="width: 85px;"><input name="productType" type="checkbox" value="HOTEL" checked="checked" />酒店</label>
								<label style="width: 85px;"><input name="routeType" type="checkbox" value="GROUP" checked="checked" />短途跟团游</label>
								<label style="width: 85px;"><input name="routeType" type="checkbox" value="GROUP_LONG" checked="checked" />长途跟团游</label>
								<label style="width: 85px;"><input name="routeType" type="checkbox" value="GROUP_FOREIGN" checked="checked" />出境跟团游</label>
								<label style="width: 85px;"><input name="routeType" type="checkbox" value="TRAIN" checked="checked" />火车票</label>
								<br/>
								<label style="width: 85px;"><input name="routeType" type="checkbox" value="FREENESS" checked="checked" />目的地自由行 </label>
								<label style="width: 85px;"><input name="routeType" type="checkbox" value="FREENESS_LONG" checked="checked" />长途自由行</label>
								<label style="width: 85px;"><input name="routeType" type="checkbox" value="FREENESS_FOREIGN" checked="checked" />出境自由行</label>
								<label style="width: 85px;"><input name="routeType" type="checkbox" value="SELFHELP_BUS" checked="checked" />自助巴士班</label>
								
								<label style="width: 85px;"><input name="productType" type="checkbox" value="OTHER" checked="checked" />其他</label>
								</td>
								<td height="30" align="right"><input id="search_button" type="button" class="left_bt" value="查 询" />
								</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="gridDiv" style="display: none;">
				<div class="order_list">
					<table id="result_table"></table> 
					<div id="pagebar_div"></div>
				</div>
				<div class="list_nums clearfix">
					<a id="noSettleBtn" href="javascript:void(0);" >不结算</a> <a href="javascript:void(0);" id = "settleBtn" >生成结算单</a> <a href="javascript:void(0);" id ="settleAllBtn" class="account_order01" >全部生成结算单</a>
				</div>
			</div>
		</div>
	</div>

</body>
</html>