<script src="/js/details.js" type="text/javascript"></script>
<#if prodProduct.productType == "TICKET">
	<#assign formSubmitUrl = "/orderFill/ticket-${prodProduct.productId}"> 
</#if>
<form method="post" id="buyForm" action="${formSubmitUrl!"/buy/fill.do"}">
<input type="hidden" name="buyInfo.productId" id="productIdHidden" value="${prodProduct.productId}"></input>
<input type="hidden" name="buyInfo.prodBranchId" id="productBranchIdHidden" value="${prodProductBranch.prodBranchId}"></input>
<input type="hidden" name="buyInfo.productType" value="${prodProduct.productType?if_exists}"></input>
<input type="hidden" name="buyInfo.subProductType" value="${prodProduct.subProductType?if_exists}"></input>
<input type="hidden" ordNum="ordNum" id="param${prodProduct.productId}" name="buyInfo.buyNum.product_${prodProductBranch.prodBranchId}" value="${prodProductBranch.minimum?if_exists}"></input>
<input type="hidden" name="buyInfo.visitTime"  id="hiddenVisitTime"/>
<#if prodProduct.days??> 
<input type="hidden" name="buyInfo.days" value="${prodProduct.days}" />
</#if>
<input type="hidden" name="buyInfo.channel" id="hiddenchannel" value="TUANGOU"/>
<input type="hidden" name="tn"  id="tn" value="${tn}"/>
<input type="hidden" name="baiduid" id="baiduid" value="${baiduid}"/>
</form>
<script> 
    var validateFlag = true;
    var canSubmit = false;
    
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
        	showLogin(function(){
        		$('#buyForm').submit();
        	});
    	</#if>
    }
    
    $(document).ready(function(){
   		pandora.calendar({
        	target: "body",
        	offsetAmount:{top:0,left:-200},
        	frequent:true,
        	sourceFn: sourceFunction,
        	selectDateCallback: selectDate,
        	completeCallback: loadComplete
    	});
        
    });
    
    function sourceFunction(cal) {
    	/*var val = '${prodProduct.productId}';
	$.ajax({async:false,type: "get",
        url: "http://www.lvmama.com/product/timePriceJson.do?productId=" + val,
        dataType:"html",
        success: function(data){*/	
        	var obj = ${Request['jsonStr']};
        	for (var i = 0; i < 12; i++) {
    	        var subObj = obj[i];
    	        if (subObj) {
    	            var $td = null;
    	            for (var j = 0; j < subObj.length; j++) { 
    	            	if (subObj[j]) { 
    	            		  $td = cal.warp.find("td[date-map=" + subObj[j].date + "]");
    	            		  if(!subObj[j].price){
    	            			   $td.find("div").unbind("click"); 
    	            			   $td.children().removeClass("caldate").addClass("nodate"); 
    	            		  }
    	            		  if (subObj[j].number) 
    	            			  $td.find("span").eq(1).html(subObj[j].number); 
    	            		  if (subObj[j].price) 
    	            			  $td.find("span").eq(2).html("<dfn>¥" + subObj[j].price + "</dfn>"); 
    	            		  if (subObj[j].active) 
    	            			  $td.find("span").eq(3).html(subObj[j].active); 
    	            	}else{ 
    	            		  $td = cal.warp.find("td[date-map=" + subObj[j].date + "]"); 
    	            		  $td.find("div").unbind("click"); 
    	            		  $td.find("div").removeClass("caldate").addClass("nodate"); 
    	            	} 
    	            }

    	        }
    	        /* else{
    	        	$td = cal.warp.find("td[date-map]"); 
                    $td.find("div").unbind("click"); 
                    $td.find("div").removeClass("caldate").addClass("nodate"); 
    	        } */
    	 	}
        	
      //  }
	//});
}


function selectDate(cal){
    var date = cal.selectedDate;
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
}
function loadComplete() {
    var $calendar = $(".ui-calendar");
    var absHeight = $(window).height() - ($calendar.offset().top - $(document).scrollTop() + $calendar.outerHeight());
    if (absHeight < 0) {
        $(document).scrollTop($(document).scrollTop() + Math.abs(absHeight));
    }
}
 
</script>
