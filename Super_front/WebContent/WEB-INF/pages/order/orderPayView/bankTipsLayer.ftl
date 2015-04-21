<style>
/*弹出提示层*/
#bgColor {z-index:9990;display:none;position:fixed;_position:absolute;top:0px;background-color:#000;left:0px;width:100%;height:100%;filter:alpha(opacity=40);-moz-opacity:0.4;opacity:0.4;}
        #tipsWindow { z-index: 9999; display: none; position: absolute; top: 30px; left: 40px; width: 500px; border: none;  background-color: rgba(0, 0, 0, 0.4); padding: 5px; font-size: 12px; 
		filter: progid:DXImageTransform.Microsoft.Gradient(startColorstr=#66000000, endColorstr=#66000000);}
		
        #tipsWindow h4 {background: none repeat scroll 0 0 #EEEEEE; font-size: 14px; font-weight: bold;  height: 32px; line-height: 32px;  margin: 0; padding: 0 10px; }
        #tipsContent{background-color: #fff;}
        .tipClose{ color: #000000; cursor: pointer; font-family: Tahoma,Arial,sans-serif; font-size: 20px; font-weight: bold; height: 32px; line-height: 30px; margin: 0; opacity: 0.4;
            outline: medium none; overflow: hidden; padding: 0; position: absolute; right: 5px; text-align: center; top: 5px; width: 36px; z-index: 10; }
        .clearfix:before, .clearfix:after{ content: ""; display: table; line-height: 0; }
        #tipsWindow .tipWrap{padding: 10px 30px 40px 30px;}
        #tipsWindow .tipTitle { color: #666; line-height: 15px; padding: 0 0 5px 0; ertical-align: middle; border-bottom: 1px dashed #ddd; }
        .payFalse{padding: 7px 0;}
        .payOK{padding: 20px 0 10px 0;}
        .payOK span, .payFalse span{background-image: url(http://pic.lvmama.com/img/icons/popImg.png); background-repeat: no-repeat; padding: 9px 15px 9px 35px; font-weight: bold; font-size: 14px; }
        .payFalse span{background-position: 0 -44px;}
        .payFalse a, .payOK a{ padding: 5px 0 5px 15px; text-decoration: none; color: #08c;}
        .payFalse a:hover, .payOK a:hover{ text-decoration: underline}
        .otherPay{border-left: 1px solid #eee;}
</style>
<div id="windowLayer">
    <div id="tipsWindow" class="xh_tipsWindow">
        <div id="tipsContent" class="clearfix">
            <a class="tipClose" onclick="closeMsg('tipsWindow','bgColor')">×</a>
            <h4>支付渠道付款</h4>
            <div class="tipWrap">
                <p class="tipTitle">请在新开页面付款后选择：</p>
                <p class="payOK"><span>付款成功</span><a href="javascript:void(0);" id="completePayBtn" onclick="javascript:completePay()">查看消费记录</a></p>
                <p class="payFalse"><span>付款失败</span><a class="otherPay"  href="javascript:void(0);" onclick="closeMsg('tipsWindow','bgColor');">选择其他方式付款</a><a href="http://www.lvmama.com/public/order_and_pay#m_1_3" target="_blank">查看付款帮助</a></p>
            </div>
        </div>
    </div>
    <div id="bgColor"></div>
</div>
<script>
function completePay(){	
	$.ajax({
		type: "POST",
		url: "/ajax/isOrderSuccessPay.do",
		async: false,
		data: {orderId: <@s.property value="order.orderId"/> },
		dataType: "json",
		success: function(response) {		    		
			if (response.success == true) {				    			    				
				window.location="/view/view.do?orderId=" + <@s.property value="order.orderId"/>;						
			}else{			
			    window.location="/myspace/order.do";
			}
		}
	});
}

function tipsWindow(obj,screenBg){
	var windowLayer = $("#windowLayer").html();
	if(windowLayer!="" || windowLayer!=null){
	  $("body").append(windowLayer);
	  $("#windowLayer").remove();
	}
	
	$("#"+obj).fadeIn("fast");
	$("#"+screenBg).css({"display":"block","opacity":"0.6"});
	screenAlign();
		
	function screenAlign(){
		var leftAlign=($(window).width()-$("#"+obj).outerWidth())/2;
		var topAlign=($(window).height()-$("#"+obj).outerHeight())/2+$(window).scrollTop();
		$("#"+obj).css({"left":leftAlign,"top":topAlign});
		}
}
function closeMsg(obj,screenBg){
	$("#"+obj).fadeOut("fast");
	$("#"+screenBg).fadeOut("fast");
}	
</script>

