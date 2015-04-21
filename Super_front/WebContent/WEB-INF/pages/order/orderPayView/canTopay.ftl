  <#include "/WEB-INF/pages/order/orderPayView/bankTipsLayer.ftl"/>
  <@s.if test='order.isCanToPay()'>
	 <script>
	 var payAmount=0;
	 var payAmountFen=0;
	 var signature;
	 var money=0;
	 var moneyAccountPay="false";
	    function changeMoney(){
	        getUseAccount();
	       	var oughtPayYuan = parseFloat(document.getElementById("oughtPayYuan").value);//订单金额
			var maxPayMoneyYuan = parseFloat(document.getElementById("maxPayMoneyYuan").value);//现金账户
	        var actualPayYuan = parseFloat(document.getElementById("actualPayYuan").value);//已经支付的钱数.
	       if(moneyAccountPay=="true"){
	         money=oughtPayYuan-maxPayMoneyYuan-actualPayYuan;
	       }else{
	          money=oughtPayYuan-actualPayYuan;
	       }
	       if(money < 0){
			    document.getElementById("payYuan").value=oughtPayYuan;
			}else{
	            document.getElementById("payYuan").value=money;
	       }
	    }
	    
	    
	    function getUseAccount(){
	    var str = $('input[name="useAccount"]:checked').val(); 
	     var openPartPay = document.getElementById("isOpenPartPay").value;//是否可以分笔 
	      if(str=="A"){
	         moneyAccountPay="true";
	       }else{
	          moneyAccountPay="false";
	       }
	      if(openPartPay=='false'){ 
              moneyAccountPay="false"; 
           }
	    }
	 
		function beforeSubmitGateway(a, gateway, noCardFlag){
			reGenerateSignature();
			tipsWindow('tipsWindow','bgColor');
			window.open('${constant.paymentUrl}pay/'+gateway+'.do?objectId=${order.orderId}&amount='+payAmountFen+'&objectType=${objectType}&paymentType=${paymentType}&waitPayment=${waitPayment}&approveTime=${approveTime}&visitTime=${visitTime}&bizType=${bizType}&royaltyParameters=${royaltyParameters?if_exists}&signature='+signature+'&noCardFlag='+noCardFlag,'lvmamaPay');
		}
		
		function alipayWithBank(a, gateway, bankId){
			reGenerateSignature();
			tipsWindow('tipsWindow','bgColor');
		     window.open('${constant.paymentUrl}pay/'+gateway+'.do?objectId=${order.orderId}&bankid='+bankId+
			     '&amount='+payAmountFen+'&objectType=${objectType}&paymentType=${paymentType}&waitPayment=${waitPayment}&approveTime=${approveTime}&visitTime=${visitTime}&bizType=${bizType}&royaltyParameters=${royaltyParameters?if_exists}&signature='+signature+'','lvmamaPay');
		}
		function reGenerateSignature(){
			if(payAmount!=null && payAmount>0){
				payAmountFen=accMul(payAmount,100);
			}
			if(${order.oughtPayFenLong}!=payAmountFen){
				$.ajax({
					type:"POST", 
					url:'${basePath}/view/reGenerateSignature.do?random='+Math.random(), 
					data:{orderId:"${order.orderId}",objectType:"${objectType}",payAmountFen:payAmountFen,paymentType:"${paymentType}",bizType:"${bizType}",royaltyParameters:"${royaltyParameters?if_exists}"}, 
					async: false, 
					success:function (data) {
						signature=eval(data);
					}
				});
			}
			else{
				signature='${signature}';
			}		
		}
		function accMul(arg1,arg2){
		    var m=0,s1=arg1.toString(),s2=arg2.toString();
		    try{m+=s1.split(".")[1].length}catch(e){}
		    try{m+=s2.split(".")[1].length}catch(e){}
		    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
		}

		function selectBanks(form){
		    getUseAccount();
			
			var bank = $('input:radio[name="banks"]:checked');    
			var clickEvent=bank.attr("clickEvent");
	        var r = /^[0-9]+.?[0-9]*$/;　　//
	        payAmount = parseFloat(document.getElementById("payYuan").value);//支付钱数
	        if(payAmount<=0){
	        	document.getElementById("PAY").style.display="none";
				document.getElementById("PAY1").innerHTML="支付金额应大于0元.";
                return false;
	        }
	        if(!r.test(payAmount)){
				document.getElementById("PAY").style.display="none";
				document.getElementById("PAY1").innerHTML="支付金额应为正整数.";
                return false;
            }
	        var partPay = document.getElementById("isOpenPartPay").value;//是否可以分笔
	        var actualPayYuan = parseFloat(document.getElementById("actualPayYuan").value);//已经支付的钱数.
	        if(partPay=='true'){
			var maxPayMoneyYuan = parseFloat(document.getElementById("maxPayMoneyYuan").value);//现金账户
			var oughtPayYuan = parseFloat(document.getElementById("oughtPayYuan").value);//订单金额
			var payDeposit = parseFloat(document.getElementById("payDepositYuan").value);//定金金额
			var paymentStatus = document.getElementById("paymentStatus").value;//支付状态
            var totalPay=payAmount+actualPayYuan; //总共支付的钱数    已经支付
            if(moneyAccountPay=="false"){
             if( totalPay  > oughtPayYuan){
                    document.getElementById("PAY").style.display="none";
                    document.getElementById("PAY1").innerHTML="你支付金额大于订单的金额,请重新填写:支付金额最大不能超过"+(oughtPayYuan-actualPayYuan)+"元.";
                  return false;
              }else{   
                  if(payDeposit-totalPay>0){
                      document.getElementById("PAY").style.display="none";
                      document.getElementById("PAY1").innerHTML="本次支付金额：支付金额应大于等于"+(payDeposit-actualPayYuan)+"元.";
                      return false;
                 }
              }  
            }else{
            totalPay+=maxPayMoneyYuan;
              if( totalPay > oughtPayYuan){
                    if(maxPayMoneyYuan>oughtPayYuan){
                       document.getElementById("PAY").style.display="none";
                       document.getElementById("PAY1").innerHTML="你现金账户和本次支付金额大于订单的金额,请重新填写:支付金额最大不能超过"+(oughtPayYuan-actualPayYuan)+"元.";
                    }else{
                     document.getElementById("PAY").style.display="none";
                     document.getElementById("PAY1").innerHTML="你现金账户和本次支付金额大于订单的金额,请重新填写:支付金额最大不能超过"+(oughtPayYuan-maxPayMoneyYuan-actualPayYuan)+"元.";
                    }
                  return false;
              }else{
                  if(totalPay<payDeposit){
                     document.getElementById("PAY").style.display="none";
                      document.getElementById("PAY1").innerHTML="支付金额：支付金额应大于等于"+(payDeposit-totalPay+payAmount)+"元.";
                       return false;    
                   }
                 }
               } 
             }		   
			if(clickEvent!=null){
        			eval("("+clickEvent+")");
			}else{
    				alert("请选择银行")
			}    
		}
		
		
		//===========================网银分期支付JS=================================
		var selectIndex=0;
			$(window).load(function() {
				$(".instalment-plans-cmb:first").trigger('click');
				$(".instalment-plans-cmbICBC:first").trigger('click');
			});

		$(document).ready(function() {
		
		     $(".instalment_bankinfo:first").show(function(){
		     	$(".instalment-plans-cmb:first").trigger('click');
				$(".instalment-plans-cmbICBC:first").trigger('click');
		     })

		     $(".instalment-banks").bind('change click load',function(){
		        var ins_obj = $("input:radio[name='instalmentBanks']:checked");
		        var ins_index = $("input:radio[name='instalmentBanks']").index(ins_obj);
		        selectIndex = ins_index;
		        $(".instalment_bankinfo").eq(ins_index).show().siblings().hide();//切换银行
				$(".instalment-plans-cmb:first").trigger('click');
				$(".instalment-plans-cmbICBC:first").trigger('click');
		     })
		     	
			$('.instalment-plans-cmb').bind('change click',function() {
				if(!$('.instalment-banks').is(':checked')) {
					alert("请选择银行");
					$(this).attr('checked',false);
					return;
				}
				var param = $(this).attr('data-arrayPay').split(",");
				$.ajax({
					url:"/ajax/initInstalmentPlan.do",
					type:"post",
					dataType:"json",
					data:{orderId:${order.orderId},instalmentNumber:param[0],feeRate:param[1]},
					success:function(data){
						if(data.success){
								$('#downPayment').html("<em>&yen"+data.downPayment+"</em>");
								$('#averagePayment').html("<em>&yen"+data.averagePayment+"</em>");
								$('#totalPayment').html("<em>&yen"+data.totalPayment+"</em>");
						}
					}
				});
			})
			$('.instalment-plans-cmbICBC').bind('change click',function() {
				if(!$('.instalment-banks').is(':checked')) {
					alert("请选择银行")
					$(this).attr('checked',false);
					return;
				}
	            var instalmentBank=$("input:radio[name='instalmentBanks']:checked").val();
	            if(instalmentBank=='icbcInstalment'){
	            	$('.instalment_bankinfo_icbc').show();
	            	$('.instalment_bankinfo').hide();
	            	return ;
	            }
				else{
					$('.instalment_bankinfo_icbc').hide();
				}
			})

			$('.instalment-next').bind('click',function(){
				if(!$('.instalment-banks').is(':checked')){
					alert("请选择银行");
					return;
				}
				var gateway = $('.instalment-banks:checked').val();

				var instalmentNum = -1;

				var instalmentBank=$("input:radio[name='instalmentBanks']:checked").val();
	            if(instalmentBank=='icbcInstalment'){
					instalmentNum = $('.instalment-plans-cmbICBC:checked').val();
				}
				else{
					instalmentNum = $('.instalment-plans-cmb:checked').val();
				}
				var beforeURL = 'http://www.lvmama.com/view/view.do?orderId=' + ${order.orderId};
				payAmount = parseFloat(document.getElementById("payYuan").value);
				reGenerateSignature();
				$.ajax({
					url:"/ajax/checkIsCanInstalment.do",
					type:"post",
					dataType:"json",
					data:{orderId:${order.orderId}},
					success:function(data){
						if(data.success && data.isCanInstalment){
							tipsWindow('tipsWindow','bgColor');
							window.open('${constant.paymentUrl}pay/'+gateway+'.do?objectId=${order.orderId}&amount='+payAmountFen+'&objectType=${objectType}&paymentType=${paymentType}&waitPayment=${waitPayment}&approveTime=${approveTime}&visitTime=${visitTime}&bizType=${bizType}&royaltyParameters=${royaltyParameters?if_exists}&signature='+signature+'&beforeURL='+beforeURL+'&instalmentNum='+instalmentNum+'','lvmamaPay');
						} else {
							window.location.href = beforeURL;
						}
					}
				})
			})
		})
	</script>
	
     <@s.if test='order.isPaymentChannelLimit()'>
        <#include "/WEB-INF/pages/order/orderPayView/orderPaymentChannelLimit.ftl"/> 
     </@s.if>
    <@s.elseif test='isHasAlipayLogin()'>
    	<#include "/WEB-INF/pages/order/orderPayView/orderPaymentAlipay.ftl"/>
    </@s.elseif>
	<@s.else>
	  <!--支付方式-->
	  <#include "/WEB-INF/pages/order/orderPayView/orderPayInfo.ftl"/>
	</@s.else>
</@s.if>
