//省份、城市联动.
$("select[name=province]").change(function(){
			var val=$(this).val();
			var $selectCity=$("select[name=city]");
			$selectCity.empty();
			if($.trim(val)!=''){
				$.post("/buy/citys.do",{"provinceId":val},function(dt){
					var data=eval("("+dt+")");
					for(var i=0;i<data.list.length;i++){
						var city=data.list[i];
						var $option=$("<option/>");
						$option.text(city.cityName).val(city.cityId);
						$selectCity.append($option);
					}
				});
			}else{
				var $option=$("<option/>");
				$option.val("").text("请选择");
				$selectcity.append($option);
			}
		});

//根据参数出现游玩人
function showPlay(num,flag){
	var firstTravellerInfoOptions,travellerInfoOptions;
	if(flag == true){
		firstTravellerInfoOptions='NAME;'+'CARD_NUMBER;'+$("#firstTravellerInfoOptions").val(),
		travellerInfoOptions='NAME;'+'CARD_NUMBER;'+$("#travellerInfoOptions").val();
	}else{
		firstTravellerInfoOptions=$("#firstTravellerInfoOptions").val(),
		travellerInfoOptions=$("#travellerInfoOptions").val();
	}
	if(num >= 1 && firstTravellerInfoOptions=="[];" && travellerInfoOptions!=""){
		firstTravellerInfoOptions= travellerInfoOptions
	}
	var arr =[];
		arr.push(firstTravellerInfoOptions);
		
	var  playSubmitHtml = "",
		 html = "",
		 htmlInfo = "",
		 hiddenHtml ="",
		 playInfoHtml="",
		 num = parseInt(num,10);
	
	//其他游玩人
	for(var j = 0; j < num -1; j++){
		arr.push(travellerInfoOptions);
	}
		
	if(num == 0){
		$("div[id='play-info']").find("input[type='hidden']").remove();
		$("div[data-hide=play]").hide();
		$("div[data-hide=play]").eq(0).find("input[value=4]").attr("valid","false");
		return ;
	}else{
		$("div[data-hide=play]").eq(0).find("input[value=4]").attr("valid","true");
	}
	
	var name ="";
	var card ="";
	var mobil="";
	var pinyin="";
	for(var i = 1 ; i <= num; i++){	
		hiddenHtml += '<input type="hidden" value="" name="travellerList['+i+'].receiverId" />';
		if(arr[i-1].indexOf("NAME")>=0){
			name = 			
							'<input type="hidden" value="" autocomplete="off" names="text-play-person" class="input-text" data-field="receiverId" />'+
			       			'<input type="hidden" value="" autocomplete="off" names="text-play-person" class="input-text" data-field="cardType" />'+
			       			'<input type="text" autocomplete="off" names="text-play-person" id="play-name'+i+'" placeholder="姓名" class="input-text w_70" data-field="receiverName" maxlength="20">';//+
			hiddenHtml += '<input type="hidden" value="" name="travellerList['+i+'].receiverName" />';
		}
		
		if(arr[i-1].indexOf("CARD_NUMBER")>0){
			hiddenHtml += '<input type="hidden" value="" name="travellerList['+i+'].cardType" />';
			card='<div class="selectbox selectbox-small">'+
					'<p class="select-info like-input">'+
						//'<span class="select-arrow"><i class="ui-arrow-bottom dark-ui-arrow-bottom"></i></span>'+
						'<span value="ID_CARD" class="select-value">身份证</span>'+
					'</p>'+
					/*'<div class="selectbox-drop">'+
						'<ul class="select-results">'+
							'<li data-value="ID_CARD">身份证</li>'+
							'<li data-value="HUZHAO">护照</li>'+
						'</ul>'+
					'</div>'+*/
				'</div>'+
				'<input type="hidden" value="" autocomplete="off" names="text-play-person" class="input-text" data-field="receiverId" />'+
       			'<input type="hidden" value="" autocomplete="off" names="text-play-person" class="input-text" data-field="cardType" />'+
				'<input type="text" id="play-cardNum'+i+'" names="text-play-person" autocomplete="off" placeholder="证件号码" data-field="cardNum" class="input-text input-middle">';
			hiddenHtml += '<input type="hidden" value="" name="travellerList['+i+'].cardNum" />';
		}
		
		if(arr[i-1].indexOf("MOBILE")>0){
			
			mobil='<input type="hidden" value="" autocomplete="off" names="text-play-person" class="input-text" data-field="receiverId" />'+
   				  '<input type="hidden" value="" autocomplete="off" names="text-play-person" class="input-text" data-field="cardType" />'+
				  '<input type="text" class="play-mobile input-text" id="play-mobile'+i+'" names="text-play-person" autocomplete="off" placeholder="手机号" data-field="mobileNumber">';
			hiddenHtml += '<input type="hidden" value="" name="travellerList['+i+'].mobileNumber">';
		}
		
		if(arr[i-1].indexOf("PINYIN")>0){
			 pinyin='<input type="hidden" value="" autocomplete="off" names="text-play-person" class="input-text" data-field="receiverId" />'+
					'<input type="hidden" value="" autocomplete="off" names="text-play-person" class="input-text" data-field="cardType" />'+
					'<input type="text" autocomplete="off" names="text-play-person" id="play-pinyin'+i+'" placeholder="拼音" class="input-text w_70" data-field="pinyin" maxlength="100">';//+
			 hiddenHtml += '<input type="hidden" value="" name="travellerList['+i+'].pinyin" />'; 
			
		}
		
		html += '<dl class="xdl">' + 
			       '<dt><i class="req">*</i> <span>游玩人'+ i +'<span>：</span></span></dt>'+
			       '<dd names="play-person-dd">'+name+			       		
			       		'<div class="select-group">'+ card + mobil + pinyin +
			       		'</div>'+
			       	'</dd>'+	
				'</dl>';
		
		htmlInfo +='<dl class="xdl"><dt><i class="req">*</i>游玩人'+i+'：</dt><dd></dd></dl>';		
		name ="";
		card ="";
		mobil="";
		pinyin="";
	}	
	
	playSubmitHtml = $("#play-edit").find("dl").last().clone(true);
	playInfoHtml = $("#play-info").find("dl").last().clone(true);
	$("#play-edit").html("");
	$("#play-edit").append(html).append(playSubmitHtml).show();
	$("#play-info").html("");
	$("#play-info").append(hiddenHtml).append(htmlInfo).append(playInfoHtml).hide();
	$("div[data-hide=play]").show();
	
	$("#play-list").find("input[type=checkbox]").attr("checked",false);
	$("#play-list").find("input[type=checkbox]").attr("disabled",false);
	// 动态绑定游玩人信息验证
	bindEventValid(num);
	bindSelect();
}

function bindSelect(){
	$("div.selectbox-drop").delegate("[data-value]","click",function(){
        var _selectbox = $(this).parents(".selectbox");
        _selectbox.find("[data-value]").removeClass("selected");
        $(this).addClass("selected")
        _selectbox.find(".select-value").text($(this).text())
        $(this).parents("dd").find("input[names=text-play-person]").eq(1).attr("value",$(this).attr("data-value"));
        
        if($(this).parents(".selectbox").hasClass("certtype")){
            if($(this).text() == "台湾通行证" ){
                $(this).parents(".xdl").find(".form-more").removeClass("hide");
            }else{
                $(this).parents(".xdl").find(".form-more").addClass("hide");
            }
        }
    });
}

function bindEventValid(num){
	delete formValid["4"];
	var $dl = $("#play-edit").find("dl");
	
	for(var i = 1; i <= num; i++){
		
		if($dl.eq(i-1).find("input[data-field=receiverName]").length > 0){
    		$("#play-name" + i).liteValid({ vGroup: "4", valid: ["empty", "reg"], regExp: "userName", msg:["请输入游玩人姓名","姓名只能为中文或英文,长度为2-20字符"] });
    	}
		
		if($dl.eq(i-1).find("input[data-field=pinyin]").length > 0){
    		$("#play-pinyin" + i).liteValid({ vGroup: "4", valid: ["empty", "reg"], regExp: "pinyin", msg:["请输入拼音","请输入姓名拼音，不含空格。例：zhangsan"] });
    	}
		
		if($dl.eq(i-1).find("input[data-field=cardNum]").length > 0){
    		$("#play-cardNum" + i).liteValid({ vGroup: "4", valid: ["empty","reg"], regExp: "idcard",msg:["请输入证件号码","请输入正确的证件号码"] });
    	}
    	
    	if($dl.eq(i-1).find("input[data-field=mobileNumber]").length > 0){
    		$("#play-mobile" + i).liteValid({ vGroup: "4", valid: ["empty","customer"], msg:["请输入手机号码","请输入正确的手机号码"], callback:function(o, setting){
    			var mobile=$(setting.self).val();
     			if($.trim(mobile)==""){
     				return false;
     			}
     			var model=$(setting.self).attr("model");
     			
     			var bool = mobile.indexOf("****");
     			if(model=="super" && bool !== -1){
    	      		if(!/^1[3458]{1,1}[0-9]{1,1}[\*]{4,4}[0-9]{4,4}$/.test(mobile)&&!/^1[0-9]{10,}$/.test(mobile)){
    	      			return false;
    	      		}
     	     	}else{
     	     	
     	     		if(!/^((\(\d{2,3}\))|(\d{3}\-))?(13|14|15|18)\d{9}$/.test(mobile)){
     	     			return false;
     	     		}
     	     	}
     			return true;
     		 }});
    	}
	}
	 
	$("#play-submit").siblings("span").remove();
}

//显示邮寄地址
function showAddress(address){
	var  showHtml = "";
	if(address){
		$("#showAddress").show();
	}else{
		$("#showAddress").hide();;
	}
	
}

