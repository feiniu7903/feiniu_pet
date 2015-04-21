//团购游玩人js
$(document).ready(function(){
	
	var num;
		num=$("#buyNum").val();//购买数量
		if(num==0){
			return ;
		}
	var firstTravellerInfoOptions,travellerInfoOptions;
		firstTravellerInfoOptions=$("#firstTravellerInfoOptions").val(),
		travellerInfoOptions=$("#travellerInfoOptions").val();
	//1如果第一游玩人不为空其他游玩人为空，显示第一游玩人
	if(firstTravellerInfoOptions!=""&&travellerInfoOptions=="[];"){
		num=1;
	}
	//2如果购买数量大于1且第一游玩人为空，其他游玩人不为空
	if(num >= 1 && firstTravellerInfoOptions=="[];" && travellerInfoOptions!=""){
		firstTravellerInfoOptions= travellerInfoOptions;
	}
	//第一游玩人和其他游玩人为空都不显示
	if(firstTravellerInfoOptions=="[];"&&travellerInfoOptions=="[];"){
		return;
	}
	var arr =[];
		arr.push(firstTravellerInfoOptions);
	var	 html = "";
	//其他游玩人
	for(var j = 0; j < num -1; j++){
		arr.push(travellerInfoOptions);
	}	
	if(arr==","){
		return ;
	}
	var name ="";
	var card ="";
	var mobile="";
	
	for(var i = 1 ; i <= num; i++){	
		//姓名存在
		if(arr[i-1].indexOf("NAME")>=0){
			name='<input type="text"  class="buy-person-name buy-txt" placeholder="姓名" name="travellerList['+[i-1]+'].receiverName">';
		}
		
		if(arr[i-1].indexOf("CARD_NUMBER")>0){
			card= '<select class="buy-sel" data-class="selectbox-small" name="travellerList['+[i-1]+'].cardType"> '+
            	  		'<option value="ID_CARD">-身份证-</option>'+
            	  		'<option value="HUZHAO">-护照-</option>' +
            	  '</select>' +
           '<input type="text"  class="buy-num buy-txt" placeholder="证件号码" name="travellerList['+[i-1]+'].cardNum">'; 	  
		}
		
		if(arr[i-1].indexOf("MOBILE")>0){
			mobile='<input type="text" class="buy-phone buy-txt" placeholder="手机号码" name="travellerList['+[i-1]+'].mobileNumber">';
		}
		
		html+='<dl>'+
					'<dt> 游玩人'+i+'：</dt>'+
					'<dd> <i class="red">*</i>  ' +name+card+mobile+'</dd>'+
			  '</dl>';
		
		var name ="";
		var card ="";
		var mobile="";
	}
	$("#playPerson").append(html).show();
});