<#include "/WEB-INF/pages/buy/bankTipsLayerMergePay.ftl"/>
<@s.if test='order.isCanToPay()'>
	 <script type="text/javascript">
		function gotoMergePay(a, gateway, bankId){
			tipsWindow('tipsWindow','bgColor');	
			//window.open('${constant.paymentUrl}pay/'+gateway+'.do?bankid='+bankId+'&bizType=${bizType}&signature=${signature!}&mergePayData=${mergePayData!}','lvmamaPay');
			window.open("about:blank","lvmamaPay");
			var lvmamaPayForm=document.getElementById("lvmamaPayForm");
			lvmamaPayForm.action='${constant.paymentUrl}pay/'+gateway+'.do?bankid='+bankId;
			lvmamaPayForm.target="lvmamaPay";
			lvmamaPayForm.submit();
		}
		function selectBanks(form){
			var bank = $('input:radio[name="banks"]:checked');    
			var clickEvent=bank.attr("clickEvent");
	        var payAmount = ${oughtPayTotalAmount!};//支付金额
	        if(payAmount<=0){
	        	document.getElementById("PAY1").innerHTML="支付金额应大于0元!";
                return false;
	        }
	        
			if(clickEvent!=null){
        		eval("("+clickEvent+")");
			}
			else{
    			alert("请选择银行");
			}    
		}
	</script>
	<form action="" id="lvmamaPayForm" method="POST">
		<input type="hidden" name="bizType" value="${bizType!}" />
		<input type="hidden" name="signature" value="${signature!}"/>
		<input type="hidden" name="objectIds" value="${orderIds!}" />
		<input type="hidden" name="mergePayData" value=${mergePayData!} />
	</form>
	<#include "/WEB-INF/pages/buy/orderMergePayInfo.ftl"/>
</@s.if>