function passPort(orderId,passQuantity){
	var url=ctx+"/port/doPassPortInfo.do?orderId="+orderId+"&passQuantity="+passQuantity;
	window.open(url,'','height=510,width=590,top=200, left=400,scrollbars=yes');
}

function chk_submitQueryPassPort(){
	if($("#port_mobile").val()==""&&$("#port_orderId").val()==""&&$("#port_userName").val()==""&&$("#port_passPort").val()==""){
		alert("请输入至少一个条件进行查询!");
		return false;
	}
	return true;
}
function updateTotalQuantity(orderId,targetId){
	$.ajax( {
		url :  ctx+"/port/updateTotalQuantity.do",
		data : "&orderId="+orderId+"&portQuantity="+$("#id_port_quantity").val()+"&targetId="+targetId,
		type: "POST",
		success : function(result) {
			$("#port_price").html("<strong>"+result+"</strong>");
			var port_quantity=$("#id_port_quantity").val();
			showPortQuantity(port_quantity);
		}
	});
}
function showPortQuantity(quantity){
		var ipt_adult=$("#ipt_adult").val()*quantity;
		var ipt_child=$("#ipt_child").val()*quantity;
		var ipt_totalMan=ipt_adult+ipt_child;
		var _div_quantity="<strong>"+ipt_totalMan+"</strong>("+ipt_adult+"/"+ipt_child+")<span>共计"+ipt_totalMan+"人，其中成人"+ipt_adult+"人，儿童"+ipt_child+"人</span>";
		$("#div_port_quantity").html(_div_quantity);
}
function passPortInfo(orderId,targetId,quantity){
	$.ajax( {
		url :  ctx+"/port/passInfo.do",
		data : "&orderId="+orderId+"&portQuantity="+$("#id_port_quantity").val()+"&targetId="+targetId+"&oldQuantity="+quantity,
		type: "POST",
		success : function(result) {
			alert(result);
		}
	});
}

//关闭
function closeBtn(obj){
	$(obj).parent().hide();
	$("div[class='alphabg']").hide();
}
function canc_order(quantity,totalmoney){
	var port_quantity=$("#port_quantity").val();
	showPortQuantity(port_quantity);
	$("#port_price").html("<strong>"+totalmoney+"</strong>");
	var id_port_quantity=$("#id_port_quantity").val();
	funcBtn('canc-btn');
}
//功能按钮
function funcBtn(objName){
		if(objName=="edit-btn"){
			$(".edit-info").show();
		}
		if(objName=="canc-btn"){
			$(".edit-info").hide();
		}
}

var ctop=0;
var cleft=0;
jQuery(function($){
	var orderTable=$("table[name='order-table']");
	orderTable.find("tbody>tr:odd").addClass("cbg");	//每隔一行加背景色
})	
//屏幕居中
function screenCenter(objName){
		ctop=($(window).height()-$("div[name='"+objName+"']").outerHeight())/2+$(window).scrollTop(),
		cleft=($(window).width()-$("div[name='"+objName+"']").outerWidth())/2;
		$("div[name='"+objName+"']").css({"position":"absolute","top":ctop,"left":cleft,"z-index":"10"})
}
//查看详情
function viewOrder(obj,faxMemo){
		var view=$("div[name='view']"),
			alphaBg=$("div[class='alphabg']");
			$("#p_divFaxMemo").html(faxMemo);
		view.show();
		screenCenter("view");
		alphaBg.show();
		alphaBg.css({"position":"absolute","opacity":"0.5","filter":"alpha(opacity=10)","background-color":"#ccc","width":(view.outerWidth()+10),"height":(view.outerHeight()+10),"top":(ctop-5)+"px","left":(cleft-5)+"px","z-index":"9"});
}
//关闭
function closeBtn(obj){
		$(obj).parent().hide();
		$("div[class='alphabg']").hide();
}