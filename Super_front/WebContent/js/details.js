$(document).ready(function(){
$("#close_btn").click(function(){
$("#pro_window").fadeOut();
});

//弹出日历层自适应屏幕居中
$("input[name='yuding_btn']").each(
   function(){
	var pro_window = $("#pro_window");
	var windows = $(window);
	$(this).click(function(){
	var i = $("#pro_calendar2").find("table[name='lvmama_calendar']").length;
	var k = 0;
   $("#pro_calendar2").find("table[name='lvmama_calendar']").each(function(){
	   if ($(this).css("display")=="none") {
		   k++;
	   }
   });
   
   if(parseInt(i)==parseInt(k)){
	   $("#pro_calendar2").find("table[name='lvmama_calendar']").eq(0).show();
   }
	$("#pro_window").fadeIn();
	var ctop = (windows.height() - pro_window.height())/2;
	var cleft = (windows.width() - pro_window.width())/2;
	if(ctop<=0){ctop = 0 + windows.scrollTop()}else{ctop=parseInt(ctop + windows.scrollTop())};
	if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
	pro_window.css({"top":ctop + "px","left":cleft + "px"})
	})
	})
});




var clickObj;
$(document).ready(function(){
$("#pro_calendar").find("td[id='hasTimePrice']").each(function(){
$(this).mouseover(function(){
 			$(this).addClass("yellow_bg");
 			});
 			
 			$(this).mouseout(function(){
 			$(this).removeClass("yellow_bg");
 			});
 			
 			$(this).click(function(){
 				var date=$(this).attr("date");
 				var productId = $("#productIdHidden").val();
 				var branch_id = $('#productBranchIdHidden').val(); 
 				var being_date = date;
 				$("#leaveTimeQuick").val("");
 				$("#leaveTimeQuick2").val("");
 				var jsonData = getJsonData(productId,being_date,"","ordNum",branch_id);
 				var flag = updateTimePrice("prod_",productId,jsonData);
 				if(!flag){
 					return;
 				}
 				validateFlag = true;
	 			$(this).unbind("mouseout");
	 			$(this).unbind("mouseover");
	 			if (clickObj!=null) {
		 			 clickObj.removeClass("yellow_bg");
		 			 clickObj.mouseover(function(){
		 			$(this).addClass("yellow_bg");
		 			});
		 			clickObj.mouseout(function(){
		 			$(this).removeClass("yellow_bg");
		 			});
	 			}
	 			
	 			$(this).addClass("yellow_bg");
	 			 clickObj=$(this);
	 			 $("#pro_calendar2").find("table[name='lvmama_calendar']").each(function(){$(this).hide();});
	 			 if(document.getElementById("leaveTimeQuick2") != null) {
	 			 	$("#daysInput2").val("");
	 			 	$("#daysInput2Span").html("");
	 			 }
	 			 $("#selectedDate").html($(this).attr("date"));
	 			 var priceInt=$(this).attr("price");
	 			 $("#product_price").html(priceInt.substring(0,priceInt.lastIndexOf('.')));
	 			 
	 			 $("#visitTime").val($(this).attr("date"));
 				 if($("#visitTimeQuick").attr("id")!=undefined){
 					 $("#visitTimeQuick").val($(this).attr("date"));
 				 }
	 			 showDiv();
	 			
 			});
});



$("#pro_calendar2").find("td[id='hasTimePrice']").each(function(){
$(this).mouseover(function(){
 			$(this).addClass("yellow_bg");
 			});
 			
 			$(this).mouseout(function(){
 			$(this).removeClass("yellow_bg");
 			});
 			
 			$(this).click(function(){
 			$(this).unbind("mouseout");
 			$(this).unbind("mouseover");
 			if (clickObj!=null) {
 			 clickObj.removeClass("yellow_bg");
 			 clickObj.mouseover(function(){
 			$(this).addClass("yellow_bg");
 			});
 			clickObj.mouseout(function(){
 			$(this).removeClass("yellow_bg");
 			});
 			
 			}
 			$(this).addClass("yellow_bg");
 			 clickObj=$(this);
 			 $("#selectedDate").html($(this).attr("date"))
 			});
});


});


function showDiv(){
	var pro_window = $("#pro_window");
	var windows = $(window);
	$("#pro_window").fadeIn();
	var ctop = (windows.height() - pro_window.height())/2;
	var cleft = (windows.width() - pro_window.width())/2;
	if(ctop<=0){ctop = 0 + windows.scrollTop()}else{ctop=parseInt(ctop + windows.scrollTop())};
	if(cleft<=0){cleft = 0}else{cleft=parseInt(cleft)};
	pro_window.css({"top":ctop + "px","left":cleft + "px"})
}


function redirectOrder(){
$("#visitTime").val(getDate());
var num = 0;
$("input[id='numAdult']").each(function(){num+=parseInt($(this).val())});
$("input[id='numChild']").each(function(){num+=parseInt($(this).val())});
	if(getDate()==null){
	alert("请选择游玩日期");
	} else {
	if(num==0){
	 alert("产品订购数量不能都为0");
	} else {
	$("#buyForm").submit();
	}
	}

}
function getDate(){
if(clickObj!=null){
return clickObj.attr("date");
} else {
return null;
}

}

$(document).ready(function(){
$("#adultJian").click(function(){
var num = $("#numAdult").val();
var min = $(this).attr("minAmt");
if(num!=null){
if(parseInt(num)==min){
alert("最小订购量为："+min);
}else{
 if(parseInt(num)>min){
  $("#numAdult").val(parseInt(num)-1)
 }
 }
} else {
 $("#numAdult").val(1);
}
return false;
});


$("#adultAdd").click(function(){
var num = $("#numAdult").val();
var min = $(this).attr("minAmt");
if(num!=null){
if(parseInt(num)==min){
$("#numAdult").val(parseInt(num)+1)
}else{
 if(parseInt(num)>min){
  $("#numAdult").val(parseInt(num)+1)
 }
 }
} else {
 $("#numAdult").val(1);
}
return false;
});




});

function add(obj,productId){
var num = $(obj).parent().find("input[id='"+productId+"numChild']").val();
var min = parseInt($(obj).attr(productId+"minAmt"));
if(num!=null){
if(parseInt(num)==min){
$(obj).parent().find("input[id='"+productId+"numChild']").val(parseInt(num)+1);
} else {
 if(parseInt(num)>min){
  $(obj).parent().find("input[id='"+productId+"numChild']").val(parseInt(num)+1)
 }
 }
}else{
 $(obj).parent().find("input[id='"+productId+"numChild']").val(1)
}
}

function jian(obj,productId){
var num = $(obj).parent().find("input[id='"+productId+"numChild']").val();
var min = $(obj).attr(productId+"minAmt");
if(num!=null){
if(parseInt(num)==parseInt(min)){
alert("最小订购量为："+min);
} else {
 if(parseInt(num)>parseInt(min)){
  $(obj).parent().find("input[id='"+productId+"numChild']").val(parseInt(num)-1)
 }
 }
}else{
 $(obj).parent().find("input[id='"+productId+"numChild']").val(1)
}
}

function checkInput(obj,productId){
var min = $(obj).attr(productId+"minAmt");
if(parseInt($(obj).val())<parseInt(min)){
 alert("最小起订量为"+min);
 $(obj).val(min);
}
}

function getJsonData(product_id,date,endDate,ordNumName, prodBranch_id){
	var ordNum = $("["+ordNumName+"]");
	var param = "";
	for(var i=0;i<ordNum.length;i++){
		var id = ordNum[i].getAttribute("id");
		var obj = $("#"+id);
 		param +=  "'ordNum."+(ordNum[i].getAttribute("id"))+"':'"+obj.val()+"',";
	}
 	var param = "{"+param+"productId:'"+product_id+"',choseDate:'"+date+"'";
  	if(endDate!="null" && endDate!="" && endDate!=undefined){
		param += ",endDate:'"+endDate+"'";
	}
  	if (prodBranch_id!=null || prodBranch_id!="") {
  		param += ",branchId:'" + prodBranch_id + "'";
  	}
 	param += "}";
   	return eval("("+param+")")
}

function updateTimePrice(div_id,product_id,jsonData){
	var limittime = "";
	var flag = true;
 	$.ajax({type:"POST",async:false,
			url:"/product/price.do",
			data: jsonData,
			dataType:"json",
			success:function (data) {
			var json=data.jsonMap;
				//error 
				if(json.flag=='N'){
					alert(json.error);
					flag = false;
 					return;
				}
 				if(json.price.length>0){
					var priceList = json.price;
 					for(var i=0;i<priceList.length;i++){
						if(priceList[i].price!=0){
						 	 $("#"+div_id+priceList[i].productId+"_price").html(priceList[i].price.replace(".0",""));
						 } else {
						 	$("#"+div_id+priceList[i].productId+"_price").html("");
						 }
					}
 					
				}
 				if(data.jsonMap.days>0){
					$("#"+product_id+"days").val(data.jsonMap.days);
				}
			}});
	return flag;
}

function showOrHide(event,hide,show){
	if (event.stopPropagation) event.stopPropagation();
  		else event.cancelBubble = true;
		if($("#"+show).attr("id")!=undefined){
		$("#"+hide).hide();
		$("#"+show).fadeIn();
	}

	
}
