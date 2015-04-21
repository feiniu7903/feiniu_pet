<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>分单管理</title>
	<#assign dialog_inc = true>
	<#assign autocomplete_inc = true>
	<#assign grid_inc = true>
	<#assign datepicker_inc = true>
	<#assign grid_row_auto_height = true> <#include "../../common/define.ftl"/>
	<script type="text/javascript" src="${basePath}js/finance/settlement/fin_supplier_allot.js"></script>
</head>

<body>
	<div class="wapper_accounts" >
		<div class="wapper_list wapper_list_cash">
			<h3 class="order_check">分单管理</h3>
			<div class="cash_seach">
				<form id="search_form" method="post" search_action="search.do" excel_action = "exportAllot.do">
					<ul class="order_top_list">
						<li class="other_list">
							<label> 供&nbsp;应&nbsp;商：</label> 
							<input id="supplierId" name="supplierName" type="text" class="input_text02 input_combox" autocomplete="off" />
							<input type="hidden" id="flag" />
						</li>
						<li class="other_list">
							<label> 指派状态：</label> 
							<select name="status">
								<option value="">全部</option>
								<option value="N" selected="selected">未指派</option>
								<option value="Y">已指派</option>
							</select>
						</li>
						<li class="other_list">
							<label>用&nbsp;户&nbsp;名：</label>
							<input name="userName" type="text" class="input_text02" />
						</li>
					</ul>
					<ul class="order_top_list ">
						<li class="other_list">
							<label> 结算周期：</label> 
							<select id="settlementPeriodSelect" name="settlementPeriod" class="cash_select">
								<option value="">请选择</option>
								<#list settlementPeriod as cur>
									<option value="${cur.code}">${cur.cnName}</option>
								</#list>
							</select>
						</li>
						<li class="other_list" style="width: 450px;">
							<label> 我方结算主体：</label> 
							<select id="settlementCompanySelect" name="settlementCompany" class="cash_select">
								<option value="">请选择</option>
								<#list settlementCompany as item>
									<option value="${item.code}">${item.cnName}</option>
								</#list>
							</select>
						</li>
						<li class="other_list" style="width: 560px;">
							<input id="search_button" type="button" class="left_bt" style="margin-left: 55px;" value="查 询" />
						</li>
					</ul>
				</form>
				<div class="order_list">
					<table id="result_table"></table> 
					<div id="pagebar_div"></div>
				</div>
				<div id="batch_btn_div" style="display: none;">
					<ul class="order_top_list ">
						<li class="other_list">
							<input id="updateBatch" type="button" class="left_bt" style="margin-left: 15px;" value="批量修改" />
							<input id="deleteBatch" type="button" class="left_bt" style="margin-left: 15px;" value="批量删除" />
							<input id="export_button" type="button" class="left_bt" style="margin-left: 15px;" value="导    出" />
						</li>
					</ul>
				</div>
				
			</div>
		</div>
	</div>

	<!--修改指派人-->
    <div id="update_allot_div" class="hid" style="width: 450px">
    	<form id="update_allot_form" method="post"> 
    	   	<ul class="cash_tan" >
    	   		<li style="width:255px;float:left;">
    	   			<label class="cash_name2">供应商名称：</label> 
					<div id="supplierName" class="tan_text"></div>
				</li>
				<li>
    	   			<label class="cash_name2">供应商ID：</label> 
					<div id="supplierId_add" class="tan_text"></div>
					<input type="hidden" name="supplierIdAdd" id="supplierIdAdd" />
				</li>
    	   		<li style="width:255px;float:left;">
    	   			<label class="cash_name2">指派给：</label> 
					<input type="text" name="userNameAdd" id="userName_add_txt" class="input_text02"/>
				</li>
				<li>
    	   			<label class="cash_name2">姓名：</label> 
					<div id="realName" class="tan_text"></div>
				</li>
           	</ul>
           	<div style="width: 290px; padding-left: 160px;">
				<input type="submit" class="left_bt" value="确 定" disabled="disabled" id="update_btn" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="left_bt" value="取 消" id="cancel_btn" />
			</div>
         </form>
    </div>
    
    <!--批量修改指派人-->
    <div id="update_batch_div" class="hid" style="width: 590px">
    	<form id="update_batch_form" method="post"> 
           	<ul class="order_top_list">
    	   		<li class="other_list" style="height: 60px;">
    	   			<label class="cash_name">指派给：</label> 
					<input type="text" name="userNameBatch" id="userName_add_batch" />
					<input type="hidden" name="supplierIds" id="supplierIds" />
				</li>
				<li class="other_list">
    	   			<label class="cash_name">姓名：</label> 
					<div id="realNameBatch" class="tan_text"></div>
				</li>
           	</ul>
           	<div style="width: 390px; padding-left: 160px;">
				<input type="submit" class="left_bt" value="确 定" disabled="disabled" id="update_batch_btn" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="left_bt" value="取 消" id="cancel_batch_btn" />
			</div>
         </form>
    </div>
    
	<script type="text/javascript">
		$(document).ready(function(){
			<#if flag==true>
			  $("#flag").val("Y");
			<#else>
			  $("#flag").val("N");
			</#if>
		});
	</script>
</body>
</html>