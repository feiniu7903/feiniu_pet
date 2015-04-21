<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>结算单订单明细  - ${settlementId}</title>
	<#assign dialog_inc = true>
	<#assign autocomplete_inc = true>
	<#assign grid_inc = true>
	<#assign datepicker_inc = true>
	<#assign grid_row_auto_height = true> <#include "../common/define.ftl"/>
	<script type="text/javascript" src="${basePath}js/fin/settlement/set_settlement_item_detail.js?v=20140411"></script>
	<script type="text/javascript" src="${basePath}js/base/accounting.min.js"></script>
</head>
<body>
	<div class="wapper_accounts" style="width: 2550px;">
		<div class="wapper_list wapper_list_cash">
			<h3 class="order_check">
				结算单订单明细  - ${settlementId}
			</h3>
			<div class="cash_seach">
				<input id = "settlement_status" type="hidden" value="${settlement.status}"/>
				
				<form id="search_form" grid_action="searchOrderDetail.do" excel_action = "exportExcel.do?type=single" method="post">
					<input id="main_settlementId" type="hidden" name="settlementId" value="${settlementId}"/>
					<input id = "settlement_type" type="hidden" name = "settlementType" value="${settlement.settlementType}"/>
					<ul class="order_top_list">
						<li class="other_list">
							<label> 订单号：</label> 
							<input type="text" name="orderId" class="input_text02"   />
						</li>
						<li class="other_list2" style="width: 650px;">
							<label> 游玩时间：</label> 
							<input name="visitTimeBegin" type="text" class="input_text02 Wdate" onclick="WdatePicker({isShowClear:true,readOnly:true})"/>
								<label style="float:left;width: 20px"> 到 </label>
							<input name="visitTimeEnd" type="text" class="input_text02 Wdate" onclick="WdatePicker({isShowClear:true,readOnly:true})"/>
						</li>
						<li class="other_cash">
							<input id="search_button" type="button" class="left_bt" value="查 询" />
						</li>
					</ul>
				</form>
				<div class="order_list">
					<table id="result_table"></table>
					<div id="pagebar_div"></div>
				 	<div id="search_footer">
				 		<table width="100%" border="0">
				 		<tr class="list_contant list_contant01">
                           	<td>
                           		<a id ="export_button" href="javascript:void(0);" class="left_bt_add yajin_seacher">导出Excel</a> 
                           		<#if settlement.status != 'SETTLEMENTED'>
                           			 <a id ="add_button" href="javascript:void(0);" class="left_bt_add yajin_seacher">新 增</a> 
                           		</#if>
	                           	<#if settlement.status != 'SETTLEMENTED'>
                       				<a id="del_button" href="javascript:void(0);" class="left_bt_add delect_list">删 除</a>
                       			</#if>
	                           	<a id="record_button" href="javascript:void(0);" class="left_bt_add dariy_yajin">查看日志</a>
                           	</td>
                            <td><div class="total_nums_money">结算金额：<em id="sumprice">查询中...</em> 元</div></td>
                          </tr>  
                          </table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="modify_price_div" class="hid" style="width: 420px">
		<form id="modify_price_form"  method="post"> 
    	   <ul class="cash_tan">
                <li class="yajin">
                    <label class="cash_name">销售价格(元)：</label>
                    <input id="item_product_price" type="hidden" name="productPrice" />
					<div id="product_price" class="tan_text" ></div>
                </li>
                <li class="yajin">
                	<input type="hidden" id="modify_price_settlementItemId" name="settlementItemId"/>
                    <label class="cash_name">实际结算单价(元)：</label>
                    <input id="modify_price" name="settlementPrice" type="text" class="input_text02" />
                </li>
                <li class="yajin">
                    <label class="cash_name">修改原因：</label>
                    <input id="modify_remark" name="remark" type="text" class="input_text02" />
                </li>
           </ul>
           <div class="popups_button">
				<input type="submit" class="left_bt" value="确 定" />
			</div>
   	  	</form>
	</div>
	<div id="batch_modify_price_div" class="hid" style="width: 420px">
		<form id="batch_modify_price_form"  method="post"> 
    	   <ul class="cash_tan">
    	   		<li class="yajin">
                    <label class="cash_name">采购产品ID：</label>
                    <input id="batch_modify_metaProductId" type="hidden" name="metaProductId" />
					<div id="batch_modify_metaProduct" class="tan_text" ></div>
                </li>
                <li class="yajin">
                    <label class="cash_name">销售价格(元)：</label>
					<div id="product_prices" class="tan_text" ></div>
                </li>
                <li class="yajin">
                    <label class="cash_name">实际结算单价(元)：</label>
                    <input id ="batch_modify_price" name="settlementPrice" type="text" class="input_text02" />
                </li>
                <li class="yajin">
                    <label class="cash_name">修改原因：</label>
                    <input  name="remark" type="text" class="input_text02" />
                </li>
           </ul>
           <div class="popups_button">
				<input type="submit" class="left_bt" value="确 定" />
			</div>
   	  	</form>
	</div>
	
	<div id="add_div" class="hid" style="width: 800px;">
		<form id="add_form"  method="post"> 
    	   <ul class="cash_tan">
    	   		<li class="yajin">
                	<input type="hidden" name="targetId" value="${settlement.targetId}"/>
                    <label class="cash_name" style="width: 65px;">订单号：</label>
                    <input name="orderId" type="text" class="input_text02" />
                    <input type="submit" class="left_bt" value="查 询" />
                    <input id="add_order_button" type="button" class="left_bt" value="新 增" />
                </li>
           </ul>
           </form>
           <form id="add_order_form" method="post">
				<div class="jie_add_contant">
				<table id="order_table" width="100%" border="0">
					<tr class="title_table cash_titie">
						<td><input id="checkbox_all" type="checkbox" class="check_box_style check_box_style01" />
						</td>
						<td>订单号</td>
						<td>销售产品</td>
						<td>采购产品</td>
						<td>打包数</td>
						<td>购买数</td>
						<td>取票人</td>
						<td>游玩时间</td>
						<td>订单状态</td>
						<td>结算价</td>
						<td>结算总价</td>
						<td>状态</td>
					</tr>
				</table>
			</div>
   	  	</form>
	</div>
	
	<!-- 修改结算总价 -->
	<div id="modify_total_price_div" class="hid" style="width: 420px">
		<form id="modify_total_price_form"  method="post"> 
    	   <ul class="cash_tan">
                <li class="yajin">
                	<input type="hidden" id="total_settlementItemId" name="settlementItemId"/>
                    <label class="cash_name">结算总价(元)：</label>
                    <input id="total_price" name="settlementPrice" type="text" class="input_text02" />
                </li>
                <li class="yajin">
                    <label class="cash_name">修改原因：</label>
                    <input id="price_remark" name="remark" type="text" class="input_text02" />
                </li>
           </ul>
           <div class="popups_button">
				<input type="submit" class="left_bt" value="确 定" />
			</div>
   	  	</form>
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
