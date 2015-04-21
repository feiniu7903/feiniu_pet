    function loadPopDiv(url,title,width,height) {
        $("#popDiv").load(url, function() {
            $(this).dialog({
                modal:true,
                title:title,
                width:width,
                height:height
            });
         });                            
    } 
    function popClose(){
        $("span.ui-icon-closethick").click();
    }
    /**
     * 控制按钮重复提交
     * @param object 按钮对象
     * @param func  方法
     * @param param  一个参数
     */
    function controlOneSumit(object,func,param){
        var buttonV=  $(object).val();
	     $(object).val("正在进行中...");
	     $(object).attr("disabled",true);
            //具体业务
             func(param) ;
        	 $(object).attr("disabled",false);
        	 $(object).val(buttonV); 
     }
    /**
     * 控制按钮重复提交
     * @param object 按钮对象
     * @param func  方法
     * @param param  一个参数
     */
    function controlOneSumit(object,func,param1,param2){
        var buttonV=  $(object).val();
 	     $(object).val("正在进行中...");
         //具体业务
          func(param1,param2);
        $(object).val(buttonV); 
    }
    
    
    function countAmount(){
    	var sum = 0;
		var _amount,_count;
		$(".detail").each(function(index){
			if(index != 0){
				_amount = $(this).find(".amountModel").val();
				_count = $(this).find(".outDetailCount").val();
				if(!isNaN(_amount) && !isNaN(_count)){
					sum = sum + _amount * _count;
				}
			}
		});
		if(sum < 300000){
			$("input:[name=saleDiscountAmount]").val(0);
		}else if(sum >= 300000 && sum < 600000){
			$("input:[name=saleDiscountAmount]").val(3);
		}else if(sum >= 600000 && sum < 1000000){
			$("input:[name=saleDiscountAmount]").val(4);
		}else if(sum >= 1000000){
			$("input:[name=saleDiscountAmount]").val(5);
		}
		$("input:[name=saleDisMoney]").val(sum*$("input:[name=saleDiscountAmount]").val()*0.01);
		if(sum == 0){
			return false;
		}
		var _rebate = $("#saleRebate").val();
		var _bonus,_bonusMoney;
		var _rebateMoney = $("#saleRebateMoney").val();
		if($("#rebate").val()==0 ){
			if(_rebate!=''){
				_bonus = (parseFloat($("input:[name=saleDiscountAmount]").val() * 100) - parseFloat(_rebate * 100))/100;
				$("#saleBonus").val(_bonus);
				_rebateMoney = sum * _rebate * 0.01;
				$("#saleRebateMoney").val(_rebateMoney);
				_bonusMoney = (parseFloat($("#saleDisMoney").val() * 100) - parseFloat(_rebateMoney*100))/100;
				$("#saleBonusMoney").val(_bonusMoney);
				if(parseFloat($("#saleDiscountAmount").val()) < parseFloat(_rebate)){
					$("#saleRebate").val("");
					$("#saleBonus").val("");
				}
			}else{
				$("#saleBonus").val("");
				$("#saleBonusMoney").val("");
				$("#saleRebateMoney").val("");
			}
		}else{
			if(_rebateMoney != ''){
				$("#saleBonusMoney").val((parseFloat($("input:[name=saleDisMoney]").val()*100)-parseFloat(_rebateMoney*100))/100);
			}
			if(parseFloat($("#saleDisMoney").val()) < parseFloat(_rebateMoney)){
				$("#saleRebateMoney").val("");
				$("#saleBonusMoney").val("");
			}
		}
		
		$("#saleSum").val(Number((parseFloat(sum*100) - parseFloat($("#saleRebateMoney").val()*100))/100));
    }
    
$(function(){
	$("#rebate").change(function(){
		if(this.value == 1){
			$("#saleRebate").hide().val("");
			$("#saleRebateMoney").show();
			$("#saleBonus").hide().val("");
			$("#saleBonusMoney").show();
			$("#bonus").val(1);
		}else if(this.value == 0){
			$("#saleRebate").show();
			$("#saleRebateMoney").hide().val("");
			$("#saleBonus").show();
			$("#saleBonusMoney").hide().val("");
			$("#bonus").val(0);
		}
		countAmount();
	});
	$("#rebate2").change(function(){
		if(this.value == 1){
			$("#saleRebate").hide();
			$("#saleRebateMoney").show();
			$("#saleBonus").hide();
			$("#saleBonusMoney").show();
			$("#bonus").val(1);
		}else if(this.value == 0){
			$("#saleRebate").show();
			$("#saleRebateMoney").hide();
			$("#saleBonus").show();
			$("#saleBonusMoney").hide();
			$("#bonus").val(0);
		}
	});
	
	$(".radio").click(function(){
		if(this.value == "0"){
			$("#remarks").val("");
			$("#remarks").attr("readonly",true);
		}else{
			$("#remarks").attr("readonly",false);
		}
	});
	$('.amountModel').live('change', function(){
		var that = $(this);
		var index = $('.amountModel').index($(this))-1;
		var thisVal = $(this).val();
		$('.amountModel:visible').each(function(i){
			if($(this).find(':selected').index() != 0){
				if(thisVal == $(this).val() && index != i){
					alert('不能选择已经存在的面值');
					that.find('option').eq(0).attr("selected",true);
					return false;
				}
			}
		});
	});
	
	$(".onlyNum2").keypress(function(event){
		if(event.which>=48 && event.which <= 57 || event.which == 8){
			return ;
		}else{
			return false;
		}
	});
	$(".onlyNum").keypress(function(event){
		var _len = 0;
		if(this.value.indexOf(".")!=-1)
			_len = this.value.toString().split(".")[1].length;
		if(event.which>=48 && event.which <= 57 || event.which == 8){
			if(event.which>=48 && event.which <= 57 && _len == 2 ){
				return false;
			}
			
			return ;
		}
		if(event.which==46){
			if(this.value.substr(0,1)=="" || this.value.indexOf(".")!=-1){
				return false;
			}
		}else{
			return false;
		}
	}).keyup(function(event){
		if(event.which>=48 && event.which <= 57){
			if($("#rebate").val()==0){
				if(parseFloat($("#saleDiscountAmount").val()) < parseFloat(this.value)){
					alert('客户返点不能超过销售转让额度');$(this).val("");
					$("#saleBonus").val("");
				}
			}else{
				if(parseFloat($("#saleDisMoney").val()) < parseFloat(this.value)){
					alert('客户返点不能超过销售转让额度');$(this).val("");
					$("#saleBonusMoney").val("");
				}
			}	
		}
		countAmount();
	});
});



/**
 * 校验用户名和密码 
 * @param url
 * @returns {Boolean}
 */
function validatePasswordSumit(url){
 	if($("#validateCardNo").val()==""){
		alert("请选择卡号");return false;
	}
	if($("#validateCardpassword").val()==""){
        alert("请选择密码");return false;
    }
	var cardNo=$("#validateCardNo").val();var password=$("#validateCardpassword").val();
     $.ajax({
        type:"POST",
        url:url,
        data:{
        	validateCardNo :cardNo,validatePassword:password
        },
        dataType:"json",
        success:function (data) {
             if(data.success=="true"){
            	 $("#validateResultId").html("密码正确！");
            }else if(data.success=="false2"){
            	$("#validateResultId").html("卡号不存在！");
            }else{
            	$("#validateResultId").html("密码错误！");
            }
        },
        error:function (data) {
        	alert("没有连通");
            return false;
        }
    });
return true;
}

/**
 * 做url ajax提交
 * @param url
 */
function doUrlAjax(url){
    $.ajax({
         type:"POST",
         url:url,
	     async:false,
         dataType:"json",
        success:function (data) {
             if(data.success=="true"){
                alert("成功！");
            }else{
                alert("失败！");
            }
        },
        error:function (data) {
            alert("没有连通");
            return false;
        }
    });
}