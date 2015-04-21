var branchNumObjArray = new Array();

/**
 * 点击“快速预订”提交
 */
function beforeSubmit(form) {
    var $visitTime = $(".quickBooker_select");
    if ($visitTime.length > 0) {
	    var dateReg = new RegExp("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
	    var dateType = $("#date_type").html().replace("：","");
	    if($visitTime.val() === "0"){
	    	$(".quick-error").show();
	    	return false;
	    }
	    
	    if (!dateReg.test($visitTime.val())) {
	//    	var html= '<div id="toolTip">请选择' + dateType + '！</div>';
	    	alert("很抱歉，該产品已售完！")
	        return false;
	    }
	    return true;
    } else {
    	var flag = true;
		if(form != 'undefined' && form != undefined) {
			var $form = $(form);
			var productId = $form.find("input[name='buyInfo.productId']").val();
			var jsonStr = "{";
			$form.find("input[name^='buyInfo.buyNum.product_']").each(function() {
				var id = "'ordNum." + $(this).attr("id") + "'";
				var val = $(this).val();
				jsonStr += id + ":" + val + ",";
			});
			jsonStr += "'productId': " + productId + "}";
			var jsonData = eval("("+jsonStr+")");
			$.ajax({
				type: "POST",
				async: false,
				url: "/product/price.do",
				data: jsonData,
				dataType: "json",
				success: function(data) {
					var json = data.jsonMap;
					flag = true;
					if (json.flag == 'N') {
						alert(json.error);
						flag = false;
						return;
					}
				}
			});
		}
		return flag;
    }
} 
/**
 * 加减数量
 * @param paramId 
 * @param operator
 * @return
 */
function updateOperator(paramId,operator, that){
    if (!beforeSubmit()) {
        return false;
    }
    var porintNum = 0;
    var miunsObj = $("#param"+paramId);
    var textNumObj = $("#"+miunsObj.attr("textNum"));
    var min = parseInt(miunsObj.attr("minAmt"));
    var max = parseInt(miunsObj.attr("maxAmt"));
    var people = parseInt(miunsObj.attr("people"));
    var textNum = parseInt(miunsObj.val());
    
    function errorMsg(type, msg){
	     var html = "";
	     if(type === 1){
	         html = '<div class="zxerror" style="display: block; position: absolute; top: 0;" >';
	     }else{
	         html = '<div class="zxerror" style="display: block; position: absolute; top: 15px; left:441px;" >';
	     }
	
	     html +='<span class="zxerror-text">'+
                           '<div class="error-arrow">'+
                               '<em>◆</em>'+
                               '<i>◆</i>'+
                           '</div>'+
                           '<p>'+msg+'</p>'+
                       '</span>'+
                   '</div>';
                   
        return html;
	}
    
    
    if(operator=='miuns'){

        if (!checkMiuns(min,textNum)) {
            porintNum = min;
            
            //alert("最小起订量为" + min);
        }else{
            if(operator=='input'){
                porintNum = min;
            }else{
                porintNum = (textNum-1);
                porintNum = porintNum>0?porintNum:0;
            }
            
            if(!checkMiuns(min,porintNum)){           	
            	$(that).addClass("price-disable");
            }
                      
            $(that).siblings("em").removeClass("price-disable");            
        }
        
        $(".zxerror").remove();
    }
    
    if(operator=='add'){   	
        if (!checkAdd(textNum,max,'add')) {
            porintNum = max;
            $(that).addClass("price-disable");
            if($(that).parents("dl").find("span.bookerBtn").length > 0){
                $(that).parents("tr").find("td").last().append(errorMsg(1, "最大预订量为" + max));
            }else{
            	$(that).parents("tr").find("td").last().append(errorMsg(2, "最大预订量为" + max));
            }
            //alert("最大订量为" + max);
        }else{
            if(operator=='input'){
                porintNum = max;
            }else{
                if(textNum!=max){
                	porintNum = textNum+1;
                } else {
                	porintNum=max;
                }

            }           
            $(that).siblings("em").removeClass("price-disable");     
        }
    } 
    
    if(operator=='input'){
        if (!checkMiuns(min,textNum)) {
            porintNum = min;
            $(that).parent().find("em").removeClass("price-disable");
            $(that).parent().find("em").eq(0).addClass("price-disable");
            $(".zxerror").remove();
            //alert("最小订量为" + min);
        }else if (!checkAdd(textNum,max,'input')) {
            porintNum = max;
            $(that).parent().find("em").removeClass("price-disable");
            $(that).parent().find("em").eq(1).addClass("price-disable");
            //$(that).parents("tr").find("td").last().append(errorMsg(1, "最大订量为" + max));
            //alert("最大订量为" + max);
        }else{
        	 $(that).parent().find("em").removeClass("price-disable");
             porintNum = textNum;
        }
    }
    
    $("input[textNum=textNum"+paramId+"]").each(function() {
        $(this).attr("value",porintNum);
    });   
    var $timePriceOne = $('.time-price-one');
    //不定期没有时间价格框
    if($timePriceOne.length > 0) {
	    var selfPack = $timePriceOne.find('.calendar_free').data('superFree');	//$("#selfPack").val();
	    if (selfPack||selfPack == "true") {  //超級自由行
	 		updateOptionalJourenyPageInfo(getJsonData());
		} else {		 	
			updateCurrentPrince();
		}  
    } else {
    	//不定期
    	updateCurrentPrince();
    }
    
    modifyBranchObj(paramId);
 }

function checkMiuns(min,textNum){
    if (textNum >-1) {
        if (textNum <= min) {
        } else {
             return true;
        }
    } 
    return false;
}
function checkAdd(textNum,max,op){
    if (textNum >-1) {
        if(max==-1){
            return true;
        }else if (textNum > max) {
            return false;
        } else if(textNum==max){
            if(op=='add'){
            return false;
            } else {
            return true;
            }
        } else {
            return true;
        }
    } 
    return false;
}

/**
 * 選擇游玩日期
 * @param {Object} productId
 * @param {Object} flag
 * @return {TypeName} 
 */
 $(".quickBooker_select").on('change',function(){
	  /**
	   * chenzhuxing 8.28
	   */
	 	var len = branchNumObjArray.length;
	 	var html = "";
	 	var className="";
	 	if($(this).val()==="0"){	 	 		
	 		for(var i = 0; i <len; i++){
	 			className = "p_" + branchNumObjArray[i].branchId;
	 			html += "<tr class="+className+">" + branchNumObjArray[i].htmlScript + "</tr>";
	 		}
	 		$(".quickBooker_select").val("0");
	 		$(".free_dtl_pro_tab").find("tbody").html(html);
	 		$(".free_dtl_pro_tab").find("input").val("0");
	 		$("#SUM").html("0");
	 		return ;
	 	}
	 	
	 	$(".zxerror").remove();
	 	
	    var selfPack = $('.time-price-one').find('.calendar_free').data('superFree'); //$("#selfPack").val();
	 	var productId = $('.time-price-one').data('pid');	 	
	 	var date = this.value;	 	
	 	if(date==null || date==""){
	 		$(".bookerBtn").html('<input type="button" class="immediateB_gray" value=""/>');
	 		return false;
	 	}
	 	
	 	$(".quickBooker_select").val(date);
	 	var jsonData = getJsonData();
	  	updateTimePrice("product_", productId, jsonData);
	    if (selfPack||selfPack == "true") {  //超級自由行
 			updateOptionalJourenyPageInfo(jsonData);
	  	} else {		 	
		  	updateCurrentPrince();
	  	}
 });

/**
 * 保存类别对象到类别数组
 */
function saveBranchObjToArray() {	
	var ordNum = $("#quickBooker1_tab2 [ordNum]");
	var flag = false;
	for(var i=0;i<ordNum.length;i++){
		var branchId = ordNum[i].getAttribute("branchId");		
		
		var branchObj = new Object();
		branchObj.branchId = branchId;
		branchObj.htmlScript = $(".p_"+branchId).html();
		branchNumObjArray[branchNumObjArray.length] = branchObj;
	}	
}

/**
 * 隐藏所有类别
 */
function hiddenBranchObj() {
	var ordNum = $("#quickBooker1_tab2 [ordNum]");
	for(var i=0;i<ordNum.length;i++){
		var branchId = ordNum[i].getAttribute("branchId");		
		$(".p_" + branchId).css({"display":"none"});
		$(".p_" + branchId).empty();
	}
}

/**
 * 修改保存在类别数组中的branchId的类别
 * @param {Object} branchId
 */
function modifyBranchObj(branchId) {
	var flag = false;
	for (var j=0; j<branchNumObjArray.length; j++) {
		var branchObj = branchNumObjArray[j];
		if (branchObj.branchId == branchId) {
			branchObj.htmlScript = $(".p_"+branchId).html();
		}
	}
}

/**
 * 根据返回的价格对象显示相应类别
 * @param {Object} div_id
 * @param {Object} priceList
 */
function showBranchObj(div_id, priceList) {
	hiddenBranchObj();
	for (var j=0; j<branchNumObjArray.length; j++) {
		var branchObj = branchNumObjArray[j];
		for(var i=0;i<priceList.length;i++){			
			if (priceList[i].branchId == branchObj.branchId) {
				$(".p_" + branchObj.branchId).css({"display":""});
				$(".p_" + branchObj.branchId).html(branchObj.htmlScript);
				$("." + div_id+branchObj.branchId + "_price").html(priceList[i].price.replace(".0",""));
			}
		}
	}
}

/**
 * 從服務器獲取時間價格，更新本地時間價格
 * @param {Object} div_id
 * @param {Object} product_id
 * @param {Object} jsonData
 * @return {TypeName} 
 */
function updateTimePrice(div_id,product_id,jsonData){
 	$.ajax({url:"/product/price.do",
 			type:"POST",
 			async:false,
			data: jsonData,
			dataType:"json",
			success:function (data) {
 				clearBranchInfo();
				var json=data.jsonMap;	
				if(json.flag=='N'){					
					flag = false;
 					$(".bookerBtn").html('<input type="button" class="immediateB_gray" value=""/>');
 					return;
				}
 				if(json.price.length>0){
					var priceList = json.price;					
					showBranchObj(div_id,priceList); 	
					$(".bookerBtn").html('<input type="submit" class="immediateB" value="" style="cursor:pointer" />');
				}
			}
 	});
}

/**
 * 封裝JSON數據向服務器發送請求
 * @param {Object} product_id
 * @param {Object} date
 * @param {Object} ordNumName
 * @return {TypeName} 
 */
function getJsonData(){
	var product_id = $('.time-price-one').data('pid');
	var branch_id = $('.time-price-one').data('bid');	
	var date = $(".quickBooker_select").val();
	var ordNum = $("#quickBooker1_tab2 [ordNum]");
	var param = "";
	
	for(var i=0;i<ordNum.length;i++){
		var id = ordNum[i].getAttribute("id");
		var obj = $("#"+id);
 		param +=  "'ordNum."+(ordNum[i].getAttribute("id"))+"':'"+obj.val()+"',"; 		
	}
	
 	param = "{"+param+"productId:'"+product_id+"',choseDate:'"+date+"',branchId:'"+branch_id+"'}";
   	return eval("("+param+")")
}

/*更新当前价格*/
function updateCurrentPrince() {
	var sum = 0;
	var ordNum = $("#quickBooker1_tab2 [ordNum]");
	for(var i=0;i<ordNum.length;i++){
		var branchId = ordNum[i].getAttribute("branchId");
		var price = $("#product_"+branchId+"_price").html();
		var count = $("#param"+branchId).val();
		sum = sum + price*count;
 				
	}
	$("#SUM").html(sum);
}

/**
 * 清除类别信息
 */
function clearBranchInfo () {
	var ordNum = $("#quickBooker1_tab2[ordNum]");
	if(ordNum.length>0){
		for(var i=0;i<ordNum.length;i++){	
			
			var branchId = ordNum[i].getAttribute("branchId");
			$(".p_"+branchId).empty();
			$(".p_"+branchId).css({"display":"none"});
		}
	}
}

/**
 * 更新可選行程頁面信息
 */
function updateOptionalJourenyPageInfo(jsonData) {
	$('#superFreeDetail').addClass('loading').html('');
	//移除dialog start
	$('.superFreeSubMain').detach();//
	$('.superFreeSubTitle').detach();//
	//移除dialog end
	$.ajax({
		url:"http://www.lvmama.com/product/journey.do",
		dataType: "html",
		async:true,
		data: jsonData,
		success: function( data ) {
			//$('#select-travel').html(data);
			$('#superFreeDetail').removeClass('loading').html(data);
			$(window).trigger('selectTp');
		},error:function(x,e,c){
			$('#superFreeDetail').removeClass('loading')
		}
	});
}