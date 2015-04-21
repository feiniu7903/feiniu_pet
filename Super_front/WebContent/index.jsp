<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>



<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
<!--  欢乐谷需要引入 以下两个文件  需要放在 欢乐谷自己的 jquery.js文件的后面 -->
<link href="http://www.lvmama.com/style/jscalendar.css" type="text/css" rel="stylesheet"  />
<script type="text/javascript" src="http://www.lvmama.com/js/jsProdCalendar.js"></script>

</head>
<script>
	//日历上选择的可售卖日期后，回调函数,如果发生价格变化，可重新计算
	function visitTiemCallback(price,date){
		alert("price="+price);
		alert("date="+date);
		if(price!=0){
			$("#totalPrice").val(price*$("#byNum").val());
		}
	}
	//重新计算总价格
	function caculatePrice(){
			$("#totalPrice").val($("#prdUnitPrice").val()*$("#byNum").val());
	}
</script>

<body>
	<form action="/buy/fill.do" method="post">
		数量：
		<input type="text"  id="byNum" name="buyInfo.buyNum.product_2266" value="1" onchange="caculatePrice()">
		<input type="text" name="buyInfo.productId" value="2266">
		<input type="text" name="buyInfo.productType" value="TICKET">
		<input type="text" name="buyInfo.subProductType" value="SINGLE">
			<!--  visitDateOnclick 函数说明：   第一个参数是日期输入对象  第二个参数是 门票单价对象ID,是欢乐谷需要增加一个hidden域用来记录选择日期的门票价格  第三个参数:欢乐谷门票ID,是固定的,第四个参数是固定的 event,第四个参数是当日历上选择一个具体的日期后，回调函数，可返回日期以及对应的价格 -->
			游玩日期：<input type="text" name="buyInfo.visitTime" id="txtGodate" value=""  onclick="visitDateOnclick(this,'prdUnitPrice','2266',event,'visitTiemCallback')">
		<input type="text" name="contact.mobileNumber" value="13044102235">
		<input type="text" name="contact.receiverName" value="余志兵">
		<input type="text" name="buyInfo.userMemo" value="欢乐谷测试">
		<input type="text" name="buyInfo.channel" value="HUANLEGU">
		单价<input type="text" id="prdUnitPrice"name="prdUnitPrice" value="0">
		总价格<input name="totalPrice" type="text" id="totalPrice" value="0">
		<input type="button" value="提交" name="btnSubmitOrder" onclick="document.forms[0].submit();" />

		
	</form>
</body>
</html>
