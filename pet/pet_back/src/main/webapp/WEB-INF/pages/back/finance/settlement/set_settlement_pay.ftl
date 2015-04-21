<#setting number_format="#.##"> 
<#assign needpay = settlement.settlementAmountYuan - settlement.payedAmountYuan - settlement.deductionAmountYuan />
<div id="pay_div"  style="width: 390px">
	<form id="pay_form"  method="post" action="toPay.do"> 
	    <ul class="cash_tan" style ="border-bottom:1px dashed #585858;">
	   		<li>
	   			<input type="hidden"  name="supplierId" value = "${settlement.supplierId}"/>
	   			<input type="hidden"  name="settlementId" value = "${settlement.settlementId}"/>
	   			<input type="hidden"  id="settlementAmount" value = "${needpay}"/>
	   			<input type="hidden"  id="deductionAmount" value = "${supplierMoney.deductionAmountYuan}"/>
	   			<input type="hidden"  id="advanceDepositAmount" value = "${supplierMoney.advanceDepositAmountYuan}"/>
	   			
	   			<label class="cash_name">供应商名称：</label> 
				<div class="tan_text">${settlement.supplierName}</div>
			</li>
	    	<li>
	   			<label class="cash_name">应打款金额：</label> 
				<div class="tan_text">${needpay?string(",##0.00")}</div>
			</li>
	
			<li <#if !supplierMoney.deductionAmount?? || supplierMoney.deductionAmount &lt;= 0 >style = "display:none;" </#if>>
	   			<label class="cash_name">抵扣款余额(元)：</label> 
				<div class="tan_text" style = "float:left;">${supplierMoney.deductionAmountYuan?string(",##0.00")}</div>
				<label class="cash_name" style ="text-align:left;padding-left:10px;">
				<input id="deductionPay"  type="checkbox" name="deductionPay" value = "1" class="check_box_style" /> 使用 </label>
			</li>
			<li id = "deductionPay_li" style = "display:none;">
				<label class="cash_name">使用金额：</label> 
				<input id="deduction_pay_amount" name="deductionPayAmount"  maxlength="8"  type="text" class="input_text02" value="0"/>
			</li>
		
		</ul>
		<ul <#if !supplierMoney.advanceDepositAmount?? || supplierMoney.advanceDepositAmount &lt;= 0 >style = "display:none;" </#if> class="cash_tan" style ="border-bottom:1px dashed #585858;">
			<li>
	   			<label class="cash_name">预存款余额(元)：</label> 
				<div  class="tan_text"  style = "float:left;">${supplierMoney.advanceDepositAmountYuan?string(",##0.00")}</div>
				<label class="cash_name" style ="text-align:left;padding-left:10px;">
				<input id="advanceDepositPay" type="checkbox" name="advanceDepositPay" value = "1" class="check_box_style" /> 使用 </label>
			</li>
			<li id="advanceDepositPay_li" style = "display:none;">
				<label class="cash_name">使用金额：</label> 
				 <input id="advanceDeposit_pay_amount" name="advanceDepositPayAmount"  maxlength="8"  type="text" class="input_text02"  value="0"/>
			</li>
		</ul>
       <ul class="cash_tan">
	   		<li>
	   			<label class="cash_name">打款金额：</label> 
				<input id="bank_pay_amount" name="bankPayAmount" type="text" maxlength="8" class="input_text02" value="${needpay}" />
			</li>
			<li>
	   			<label class="cash_name">打款银行：</label> 
				<select name="bankName" class="cash_select">
					<option value="中国银行">中国银行</option>
					<option value="交通银行">交通银行</option>
					<option value="建设银行" selected="selected" >建设银行</option>
					<option value="招商银行">招商银行</option>
				</select>
			</li>
            <li>
				<label class="cash_name">打款时间：</label> 
				<input name="operatetime" type="text" class="input_text02 Wdate" value="${currentDate}" onclick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})"/></li>
			<li>
				<label class="cash_name">流水号：</label> 
				<input name="serial" type="text" class="input_text02" maxlength="80"/>
			</li>
       </ul>
       <div class="popups_button" style="width: 190px;">
			<input id = "submit_btn" type="submit" class="left_bt" value="确 定" />
		</div>
		
     </form>
</div>
