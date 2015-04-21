<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>预存款管理</title>
	<#assign dialog_inc = true>
	<#assign autocomplete_inc = true>
	<#assign grid_inc = true>
	<#assign datepicker_inc = true>
	<#assign grid_row_auto_height = true> <#include "../../common/define.ftl"/>
	<script type="text/javascript" src="${basePath}js/finance/settlement/fin_advance_deposit.js"></script>
</head>

<body>
	<div class="wapper_accounts">
		<div class="wapper_list wapper_list_cash">
			<h3 class="order_check">预存款管理</h3>
			<div class="cash_seach">
				<form id="search_form" action="search.do" method="post">
					<ul class="order_top_list">
							<li class="other_list">
								<label> 供应商：</label> 
								<input id="supplier" type="text" class="input_text02" autocomplete="off" />
							</li>
							<li class="other_cash">
								<input id="search_button" type="button" class="left_bt" value="查 询" />
								<input id="add_button"  type="button" class="left_bt" value="新 增" />
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
	
	<!--新增-->
	<div id="add_div" class="hid" style="width: 400px">
		<form id="add_form"  method="post"> 
			<ul class="cash_tan">
				<li><label class="cash_name">供应商名称：</label> 
					<input id="add_supplierId" type="hidden" name="supplierId" />
					<div id="add_supplier" class="tan_text" ></div>
				</li>
				<li>
					<label class="cash_name">交易类型：</label> 
					<select id="advanceDeposit_type" name="type" class="cash_select">
						<#list typeList as cur>
							<option value="${cur.code}">${cur.cnName}</option>
						</#list>
					</select>
				</li>
				
				<li>
					<label class="cash_name">预存款金额(元)：</label> 
					<input id="advanceDeposit_amount" name="amount" type="text" class="input_text02" maxlength="10"/>
				</li>
				<!-- 增加币种选项 -->
				<li>
					<label class="cash_name">币种：</label> 
					<select id="currencySelect" name="advCurrency" class="cash_select">
						<option value="">请选择</option>
						<#list currencyList as cur>
							<option value="${cur.code}">${cur.cnName}</option>
						</#list>
					</select>
					<input type="hidden" id="cur_hidden" />
				</li>
				<li>
					<label class="cash_name">支付平台：</label> 
					<select name="bank" class="cash_select">
						<option value="中国银行">中国银行</option>
						<option value="交通银行">交通银行</option>
						<option value="建设银行">建设银行</option>
						<option value="招商银行">招商银行</option>
					</select>
				</li>
				<li>
					<label class="cash_name">打款时间：</label> 
					<input name="operatetimes" type="text" class="input_text02 Wdate"  onclick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})"/></li>
				<li>
					<label class="cash_name">支付平台流水号：</label> 
					<input name="serial" type="text" class="input_text02" /></li>
	
				<li>
					<label class="cash_name">备注：</label> 
					<textarea name="remark"  class="textarea_box"></textarea></li>
			</ul>
			<div class="popups_button">
				<input type="submit" class="left_bt" value="确 定" />
			</div>
		</form>	
	</div>
	 <!--转为押金-->
    <div id="shiftout_div" class="hid" style="width: 390px">
    	<form id="shiftout_form"  method="post"> 
    	   <ul class="cash_tan">
                <li class="yajin">
                	<input type="hidden" id="shiftout_supplierId" name="supplierId"/>
                    <label class="cash_name">转为押金的金额(元)：</label>
                    <input id="shiftout_amount" name="amount" type="text" class="input_text02" />
                    <input type="hidden" name="advCurrency" id="advCurrency" />
                </li>
           </ul>
           <div class="popups_button">
				<input type="submit" class="left_bt" value="确 定" />
			</div>
         </form>
    </div>
</body>
</html>
