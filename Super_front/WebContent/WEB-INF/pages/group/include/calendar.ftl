<script src="/js/details.js" type="text/javascript"></script>
<#if prodProduct.productType == "TICKET">
	<#assign formSubmitUrl = "/orderFill/ticket-${prodProduct.productId}"> 
</#if>
<form method="post" id="buyForm" action="${formSubmitUrl!"/buy/fill.do"}">
<script>
	var firstCanSaleDate = null;
	var firstCanSalePrice = null;
</script>
<input type="hidden" name="buyInfo.productId" id="productIdHidden" value="${prodProduct.productId}"></input>
<input type="hidden" name="buyInfo.prodBranchId" id="productBranchIdHidden" value="${prodProductBranch.prodBranchId}"></input>
<input type="hidden" name="buyInfo.productType" value="${prodProduct.productType?if_exists}"></input>
<input type="hidden" name="buyInfo.subProductType" value="${prodProduct.subProductType?if_exists}"></input>
<input type="hidden" ordNum="ordNum" id="param${prodProduct.productId}" name="buyInfo.buyNum.product_${prodProductBranch.prodBranchId}" value="${prodProductBranch.minimum?if_exists}"></input>
<input type="hidden" name="buyInfo.visitTime"  id="hiddenVisitTime"/>
<input type="hidden" name="buyInfo.channel" id="hiddenchannel" value="TUANGOU"/>
<input type="hidden" name="tn"  id="tn" value="${tn}"/>
<input type="hidden" name="baiduid" id="baiduid" value="${baiduid}"/>
<@s.set name="firstCanSaleDate" value="'N'" />
<DIV style="POSITION: absolute; DISPLAY: none; TOP: 10px; LEFT: 200px" id=quick_calendar class=calendar-ui>
 <@s.iterator value="cmList" status="cmi">
	<TABLE id="q_calendar_table_${year}_${month}" border=0 <@s.if test="#cmi.index!=0">style="display:none;" </@s.if>  cellSpacing=0 cellPadding=0>
	  <thead>
	  <tr>
	    <th colSpan=7>
	       <img src="http://pic.lvmama.com/img/super_v2/cal_left_btn.gif" class="cal-left-btn" onclick="showOrHide(event,'q_calendar_table_${year}_${month}','q_calendar_table_<#if (month-1)==0>${year-1}<#else>${year}</#if>_<#if (month-1)==0>12<#else>${month-1}</#if>');"/>
	       <strong id="quick_calendar_disp_year${seq}">${year}</strong>年
	       <strong id="quick_calendar_disp_month${seq}">${month}</strong>月 
	       <img src="http://pic.lvmama.com/img/super_v2/cal_right_btn.gif" class="cal-right-btn" onclick="showOrHide(event,'q_calendar_table_${year}_${month}','q_calendar_table_<#if month==12>${year+1}<#else>${year}</#if>_<#if month==12>1<#else>${month+1}</#if>');"/>
 	    </th>
	    </tr>
	   </thead>
	  <tbody>
	  <tr>
	    <th>日</th>
	    <th>一</th>
	    <th>二</th>
	    <th>三</th>
	    <th>四</th>
	    <th>五</th>
	    <th>六</th>
	   </tr>
	   </tbody>
	  <tbody>
	  
	   <@s.iterator value="calendar" status="cal" var="ca1">
			  <tr>
			  	<@s.iterator value="#ca1" status="cal2" var="ca2">
			   	<td style="<@s.if test= "#ca2.isSellable(0)" >color: blue; cursor: pointer</@s.if>"
				   	 <@s.if test='#ca2.priceF!=null && #ca2.priceF != "0"'> id="hasTimePricedate" </@s.if>
				   	 stock="<@s.property value="#ca2.dayStock"/>" 
				   	 date="<@s.date name="#ca2.specDate" format="yyyy-MM-dd"/>" 
				   	 price="<@s.property value="#ca2.priceF"/>">
				   	 <@s.if test= "#firstCanSaleDate=='N' && #ca2.dayStock>=-1  && #ca2.specDate.time>=#sysDate">
				   	 	
				   	 	<@s.set name="firstCanSaleDate" value="'Y' "/>
				   	 	<script>
				   	 		//设置第一个可卖日期
				   	 		firstCanSaleDate="<@s.date name="#ca2.specDate" format="yyyy-MM-dd"/>";
				   	 		firstCanSalePrice=<@s.property value="#ca2.priceF"/>;
				   	 	</script>
				   	 </@s.if>
			   	 	<@s.date name="#ca2.specDate" format="dd"/>
			   	 </td>
			    </@s.iterator>
			  </tr>
		</@s.iterator>
     </tbody>
    </table>
  </@s.iterator>   
</DIV></LI>
<P></P>
<SCRIPT language=JavaScript> 
    var validateFlag = true;
    var canSubmit = false, currentDateInput = "visitTimeQuick";
    
    function beforeSubmit() {
    
        if ($("input[name='buyInfo.visitTime']").val() == "") {
            alert("请选择游玩日期!");
            return false;
        }
        var end_date = $("#leaveTimeQuick").val();
        var jsonData = getJsonData('${prodProduct.productId}', $(
                "input[name='buyInfo.visitTime']").val(), end_date, "ordNum",'${prodProductBranch.prodBranchId}');
        if (!updateTimePrice("product_", '${prodProduct.productId}', jsonData)) {
            return false;
        }
        <#if login>
    		$('#buyForm').submit();
    	<#else>
        	showLogin(function(){$('#buyForm').submit();});
    	</#if>
    }
    
    $(document).ready(function(){
        //$("#quick-order").css("position","absolute");
        $(document).bind("click",function(event){
            if(event.currentTarget === this ){
                $('#quick_calendar').hide();        
            } else {
                
            }
        });
        
        /**
        * 把日期点击事件提取出来
        */
        var DATE_STOCK_TRIGGER_SIGN=true;
        var date_trigger = function(current_date_id){
            current_date_id.bind("click",function(event){                   
                        validateFlag = true;
                        event.stopPropagation();
                        var date=$(this).attr("date");
                        var end_date=$("#leaveTimeQuick").val();
                        var productBranchId = $("#productBranchIdHidden").val();                    
                        var jsonData = getJsonData('${prodProduct.productId}',date,end_date,"ordNum", productBranchId);
                        var flag = updateTimePrice("product_",'${prodProduct.productId}',jsonData);
                        canSubmit = false;
                        if(flag){
                                $("#quick_calendar").hide();
                                $("#hiddenVisitTime").val(date);
                                canSubmit = true;
                                beforeSubmit();
                        }                  
                        
                        /*
                        var date=$(this).attr("date");
                        $("#quick_calendar").hide();
                        $("#hiddenVisitTime").val(date);
                        $('#buyForm').submit();
                        */
                });
        };
            
        /**
        * 1.选择日期前备份可选日期
        */
        var visitTime_backup=$("#visitTimeQuick").val();
        $("#tuan_buy_btn").each( function(){
            $(this).bind("click",function(event){
                currentDateInput = $(this).attr("id");
                event.stopPropagation();
                //只有产品是酒店并且为单房型的才触发事件
                //var offset = $("#currentDateInput").offset();
                //var position = $("#"+currentDateInput).position();
                var leftNum = $('#tuan_buy_btn').offset().left;
                var topNum = $('#tuan_buy_btn').offset().top;
                $('#quick_calendar').fadeIn().css({"left":leftNum-73,"top":topNum+22});
            });
        });
    
        $("td[id='hasTimePricedate']").each(function(){
            date_trigger($(this))
        });
        
        if(firstCanSaleDate!=null){
            $("#"+currentDateInput).val(firstCanSaleDate);
            $("span[id$='_price']:eq(0)").html(firstCanSalePrice);
            canSubmit = true;
        }
    });
 
</SCRIPT>
