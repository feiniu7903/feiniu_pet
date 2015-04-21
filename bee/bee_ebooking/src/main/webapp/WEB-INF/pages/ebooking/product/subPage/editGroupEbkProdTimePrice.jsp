<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/calendar.css " rel="stylesheet" />
<link href="${basePath}/css/base/css.css" rel="stylesheet"/>
<script src="${basePath}/js/ebooking/pandora-calendar_update1.js" type="text/javascript"></script>
<style>
* {
	margin: 0;
	padding: 0;
}

body {
	margin: 50px;
	font-size: 12px;
	color: #666;
}

li {
	list-style: none;
}

/* .tab1,.tab2 {
	width: 398px;
	height: 34px;
	border: 1px #cfedff solid;
	border-bottom: 0;
	background: url(/jzxy/UploadFiles_333/201008/20100817155557621.gif)
		repeat-x;
} */

.tab1 ul,.tab2 ul {
	margin: 0;
	padding: 0;
}

.tab1 li,.tab2 li {
	float: left;
	padding: 0 30px;
	height: 34px;
	line-height: 34px;
	text-align: center;
	border-right: 1px #ebf7ff solid;
	cursor: pointer;
}

.tab1 li.now,.tab2 li.now {
	color: #5299c4;
	background: #fff;
	font-weight: bold;
}

.tablist {
	width: 378px;
	height: 100px;
	padding: 10px;
	font-size: 14px;
	line-height: 24px;
	border: 1px #cfedff solid;
	border-top: 0;
	display: none;
}

.block {
	display: block;
}

.red {
	color: #BD0A01;
}

fieldset {
	width: 378px;
	border: 1px #B0C0D1 solid;
	padding: 10px;
}

legend {
	background: #B0C0D1;
	padding: 4px 10px;
	color: #fff;
}

#c {
	margin-top: 10px;
}

.c1,.c2 {
	width: 378px;
	line-height: 20px;
}

.c1 {
	color: #014CC9;
}

.c2 {
	color: #7E6095;
	display: none;
}

h3 {
	font-size: 16px;
	padding: 5px 0;
}
</style>
<div>
<s:if test="1==newPriceStock">
	修改操作只对此时间段内，<font color="red">审核已通过的价格日期</font>有效。 
</s:if>
<s:elseif test="2==newPriceStock">
	修改操作只对此时间段内，<font color="red">审核已通过的库存日期</font>有效。 
</s:elseif>
<s:elseif test="3==newPriceStock">
	修改操作只对此时间段内，<font color="red">审核已通过的价格、库存日期</font>有效。 
</s:elseif>
<s:else>
	新增操作只对此时间段内，<font color="red">未提交、未审核通过的日期</font>有效。
</s:else>
</div>
<div class="tan_xzkc">
	<s:if test="0==newPriceStock">
	<form action="${basePath }product/branch/updateEbkProdTimePriceStore.do" method="POST" id="ebkProdTimePriceForm">
	</s:if>
	<s:elseif test="1==newPriceStock">
	<form action="${basePath }product/branch/updateEbkPriceTimePrice.do" method="POST" id="ebkProdTimePriceForm">
	</s:elseif>
	<s:elseif test="2==newPriceStock">
	<form action="${basePath }product/branch/updateEbkPriceTimeStore.do" method="POST" id="ebkProdTimePriceForm">
	</s:elseif>
	<s:else>
	<form action="${basePath }product/branch/updateEbkPriceTimePriceAndStore.do" method="POST" id="ebkProdTimePriceForm">
	</s:else>
	
	
	
		    
			
	

    	<tbody>
		    <table class="xzkc_1" >
		    
		     <!-- <div class="tab2">
				<ul id="test2_li_now_">
					<li class="now">按日期</li>
					<li>按周</li>
				</ul>
			</div> -->
			
		    <!-- <div>  -->
	    			<s:include value="./editEbkProdTimePriceDate.jsp"/>
    	
				
    	
    	<s:if test="'HOTEL'==ebkProductViewType">
    		<tr>
    			<td align="right"><font color="red">*</font>早餐份数：</td>
    			<td>　<s:select list="{0,1,2,3,4,5,6,7,8,9,10}" name="paramModel.breackfastCount" theme="simple" cssStyle="width:120px;" value="0"/></td>
    		</tr>
    	</s:if>
    	<s:if  test="2!=newPriceStock">
        <tr>
        	<td align="right"><font color="red">*</font>提前预订小时数：</td>
            <td><input type="text" class="width_58" id="aheadHourDay" name="paramModel.aheadHourDay" maxlength="2" value="1">天<input type="text" class="width_58" id="aheadHour" name="paramModel.aheadHour" errorlabel="提前预订小时数" maxlength="2" value="12">小时<input type="text" class="width_58" id="aheadHourSecend" name="paramModel.aheadHourSecend" maxlength="2" value="0">分</td>
        </tr>
        <tr>
        	<td align="right"><font color="red">*</font>退改策略：</td>
            <td>
            	<input type="radio" name="paramModel.cancelStrategy" value="FORBID"  errorlabel="退改策略">不退不改
            	<input type="radio" name="paramModel.cancelStrategy" value="MANUAL"  errorlabel="退改策略" checked>人工确认
                <span class="red_ff4444">&#12288;&#12288;(必须与退变更说明一致)</span>
            </td>
        </tr>
        </s:if>
    </tbody></table>
    <table class="xzkc_2">
    	<thead>
    	<tr class="timepricequerythead">
    		<th width="40"><input type="checkbox" id="branchcheckboxall" checked="true"/>选择</th>
        	<th>类别</th>
        	<s:if  test="2!=newPriceStock">
            <th width="60"><font color="red">*</font>结算价(￥)</th>
            <th width="60"><font color="red">*</font>门市价(￥)</th>
            <th width="60"><font color="red">*</font>销售价(￥)</th>
            </s:if>
            <s:if test="1!=newPriceStock">
            <th width="60"><font color="red">*</font>设置库存</th>
            </s:if>
            <s:if test="1!=newPriceStock">
	            <th width="60"><font color="red">*</font>日库存量</th>
	            <th width="60"><font color="red">*</font>资源审核</th>
	            <th width="60"><font color="red">*</font>超卖</th>
            </s:if>
        </tr>
        </thead>
        <tbody id="ebkProdTimePriceStockTbody">
        <s:iterator value="ebkProdBranchList" var="branch" status="count">
        	<tr class="editProdMesgTr<s:property value="#count.index"/>" trindexnumber="<s:property value="#count.index"/>">
        	<td><input type="checkbox" class="branchcheckbox" checked="true" value="<s:property value="prodBranchId"/>" rowid="<s:property value="prodBranchId"/>" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].prodBranchId" trindexnumber="<s:property value="#count.index"/>"/><input type="hidden" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].branchType" id="prodBranchTypeHid<s:property value="#count.index"/>" rowid="<s:property value="prodBranchId"/>" value="<s:property value="branchType"/>"/></td>
        	<td><s:property value="branchName"/></td>
        	<s:if  test="2!=newPriceStock">
	        	<s:if test="'VIRTUAL'==#branch.branchType">
		            <td><input type="text" class="width_50" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].settlementPrice" rowid="<s:property value="prodBranchId"/>" trindexnumber="<s:property value="#count.index"/>" maxlength="6" readonly='true' onFocus="this.blur()"/></td>
		            <td><input type="text" class="width_50" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].marketPrice" rowid="<s:property value="prodBranchId"/>" trindexnumber="<s:property value="#count.index"/>" maxlength="6"/ readonly='true' onFocus="this.blur()"></td>
		            <td><input type="text" class="width_50" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].price" rowid="<s:property value="prodBranchId"/>" trindexnumber="<s:property value="#count.index"/>" maxlength="6" readonly='true' onFocus="this.blur()"/></td>
		        </s:if>
		        <s:else>
		        	<td><input type="text" class="width_50" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].settlementPrice" rowid="<s:property value="prodBranchId"/>" trindexnumber="<s:property value="#count.index"/>" maxlength="6"/></td>
		            <td><input type="text" class="width_50" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].marketPrice" rowid="<s:property value="prodBranchId"/>" trindexnumber="<s:property value="#count.index"/>" maxlength="6"/></td>
		            <td><input type="text" class="width_50" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].price" rowid="<s:property value="prodBranchId"/>" trindexnumber="<s:property value="#count.index"/>" maxlength="6"/></td>
		        </s:else>
            </s:if>
            <s:if test="1!=newPriceStock">
            <td>
            	<s:if test="'VIRTUAL'==#branch.branchType">
	        		<select name="paramModel.priceStockSimples[<s:property value="#count.index"/>].stockType">
	            		<option value="FIXED_STOCK" selected>固定</option>
	            	</select>
	        	</s:if>
	        	<s:else>
	            	<select name="paramModel.priceStockSimples[<s:property value="#count.index"/>].stockType">
		            	<option value="FIXED_STOCK" <s:if test="0==newPriceStock"> selected</s:if>>固定</option> 
		            	<option value="UNLIMITED_STOCK">不限</option> 
		            	<s:if test="2==newPriceStock or 3==newPriceStock">
			            	<option value="ADD_STOCK" selected>增加</option>
			            	<option value="MINUS_STOCK">减少</option>
		            	</s:if>
            		</select>
            	</s:else>
            </td>
            </s:if>
            <s:if test="1!=newPriceStock">
	            <td><input type="text" class="width_50" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].dayStock" rowid="<s:property value="prodBranchId"/>"  trindexnumber="<s:property value="#count.index"/>" maxlength="5"/></td>
	            <td><input type="radio" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].resourceConfirm" value="true" rowid="<s:property value="prodBranchId"/>"  trindexnumber="<s:property value="#count.index"/>"/>是<input type="radio" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].resourceConfirm" value="false" rowid="<s:property value="prodBranchId"/>"  trindexnumber="<s:property value="#count.index"/>" checked/>否</td>
	            <td><input type="radio" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].overSale" value="true" rowid="<s:property value="prodBranchId"/>"  trindexnumber="<s:property value="#count.index"/>"/>是<input type="radio" name="paramModel.priceStockSimples[<s:property value="#count.index"/>].overSale" value="false" rowid="<s:property value="prodBranchId"/>"  trindexnumber="<s:property value="#count.index"/>" checked/>否</td>
            </s:if>
        	</tr>
        </s:iterator>
    </tbody></table>
    </form>
    <span class="tan_fp_btn fp_btn <s:if test="0==newPriceStock">newpricestock</s:if><s:elseif test="1==newPriceStock">updateprice</s:elseif><s:else>updatestock</s:else>">保存</span>
    <span><font color="red" id="validateErrorMesssage"></font></span>
    <script>
    	$(function(){ 
			$(".Calendar27").ui("calendar",{
			input:'.Calendar27',
			parm:{dateFmt:'yyyy-MM-dd'}
		   });
		   $(".Calendar28").ui("calendar",{
			input:'.Calendar28',
			parm:{dateFmt:'yyyy-MM-dd'}
		   });
		});
    	
	</script>
</div>
<script src="${basePath}/js/ebooking/js.js" type="text/javascript"></script>