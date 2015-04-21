<script src="/js/details.js" type="text/javascript"></script>
<#if prodProduct.productType == "TICKET">
	<#assign formSubmitUrl = "/orderFill/ticket-${prodProduct.productId}"> 
</#if>
<form method="post" id="buyForm" action="${formSubmitUrl!"/buy/fill.do"}">
<input type="hidden" name="buyInfo.productId" id="productIdHidden" value="${prodProduct.productId}"></input>
<input type="hidden" name="buyInfo.prodBranchId" id="productBranchIdHidden" value="${prodProductBranch.prodBranchId}"></input>
<input type="hidden" name="buyInfo.productType" value="${prodProduct.productType?if_exists}"></input>
<input type="hidden" name="buyInfo.subProductType" value="${prodProduct.subProductType?if_exists}"></input>
<input type="hidden" ordNum="ordNum" id="param${prodProduct.productId}" name="buyInfo.buyNum.product_${prodProductBranch.prodBranchId}" value=""></input>
<input type="hidden" name="buyInfo.visitTime"  id="hiddenVisitTime"/>
<#if prodProduct.days??> 
<input type="hidden" name="buyInfo.days" value="${prodProduct.days}" />
</#if>
<input type="hidden" name="buyInfo.channel" id="hiddenchannel" value="TUANGOU"/>
<input type="hidden" name="tn"  id="tn" value="${tn}"/>
<input type="hidden" name="baiduid" id="baiduid" value="${baiduid}"/>
<input type="hidden" name="buyInfo.seckillId" id="seckillId" value="${prodSeckillRule.id}"/>
<input type="hidden" name="buyInfo.seckillBranchId" id="seckillBranchId" value="${branchId}"/>
<input type="hidden" name="buyInfo.waitPayment" id="waitPayment" value="${prodSeckillRule.payValidTime}"/>
</form>
<script> 
    var validateFlag = true;
    var canSubmit = false;
	  $(document).ready(function(){
	  	pandora.calendar({
	        	target: "body",
	        	trigger:".seckillBtn",
	        	offsetAmount:{top:0,left:0},
	        	frequent:true,
	        	sourceFn: function(cal){
	        		var obj = datePriceObj; 
	        		for (var i = 0; i < 12; i++) {
			        var subObj = obj[i];
			        if (subObj) {
			            var $td = null;
			            for (var j = 0; j < subObj.length; j++) {
			                if (subObj[j]) {
			                    $td = cal.warp.find("td[date-map=" + subObj[j].date + "]");
			                    if (subObj[j].number)
			                        $td.find("span").eq(1).html(subObj[j].number);
			                    if (subObj[j].price)
			                        $td.find("span").eq(2).html("<dfn>￥" + subObj[j].price + "</dfn>");
			                    if (subObj[j].active)
			                        $td.find("span").eq(3).html(subObj[j].active);
				                }
				            }
				        }
				 	}
	        		return isValid;
	        	},
	        	selectDateCallback: selectDate,
	        	completeCallback: loadComplete
	    	});
	    	
	    $(".seckill-time-price").click(sourceFunction);	
	  });
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
        var buyNum = $(".js_isNumber").val();
        $("input[ordNum='ordNum']").val(buyNum);
        <#if login>
    		$('#buyForm').submit();
    	<#else>
        	showLogin(function(){
        		$('#buyForm').submit();
        	});
    	</#if>
    }
    
var isValid = true;
var datePriceObj=null;
    function sourceFunction() {
    var buyNum = $(".js_isNumber").val();
    //验证码
    var verifycode = "123456";
    var codeFlag = <@s.property value="seckillVerifyCodeFlag" />;
    if(codeFlag == true){
		verifycode = $(".seckill_verifycode").val();
    }
	if(verifycode == ""){
		alert("请输入验证码");
	}else{
		$(".seckill-time-price").attr("disabled","true");
		$.ajax({
			type: "POST",
			async:false,
	        url: "http://www.lvmama.com/fill/seckill.do?buyInfo.seckillId=${prodSeckillRule.id}&buyInfo.seckillBranchId=${branchId}&buyInfo.waitPayment=${prodSeckillRule.payValidTime}&buyInfo.buyNum.product_${branchId}="+buyNum+"&verifycode="+verifycode,
	        dataType:"json",
	        success: function(data){
			    refreshCheckCode("image");
			    $(".seckill-time-price").removeAttr("disabled");
	        	if(data.flag){
			    	datePriceObj = data.msg;
				 	$("#seckillBtn").trigger("click");
	        	}else{
	        		alert(data.msg);
	        		isValid = false;
	        	}	
	       }
		});
	}
	return isValid;
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
	var offset=$("a.seckill-time-price").offset();
    var $calendar = $(".ui-calendar");
    $calendar.css({
    	top:offset.top+45,
    	left:offset.left
    });
    var absHeight = $(window).height() - ($calendar.offset().top - $(document).scrollTop() + $calendar.outerHeight());
    if (absHeight < 0) {
        $(document).scrollTop($(document).scrollTop() + Math.abs(absHeight));
    }
}
 
</script>
