<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="pragma" content="no-cache" />
<link href="/style/myspace.css" type="text/css" rel="stylesheet">
<script src='/js/common/jquery.js' type='text/javascript'></script>
<script type="text/javascript" src="/js/member/pop2.js"></script>
<script type="text/javascript" src="/js/member/selectOrder.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
<style type="text/css">
.online_list_table{color:#000;font-family:Arial,'宋体';border:1px solid #ccc;margin:20px 0 0 0;}
.online_list_table tr{height:30px;}
.online_list_table td{border:1px solid #ccc;padding-left:8px;line-height:16px;}
.order_cancel{backgroundColor:#000;width:280px;height:40px;border:2px solid #000;text-align:center;color:#000;padding:20px 0px;font-family:Arial, Helvetica, sans-serif}
/*float layer start*/
.fl-bg {display:none;background-color:#000;-moz-opacity:0.4;opacity:.4;filter:alpha(opacity=40);width: 100%;height:100%;left:0;top:0;position:fixed!important;position:absolute;}
.fl-layer {display:none;position:absolute;width:360px;height:150px;border:2px solid #CC0467;background-color:#fff;}
.fl-layer h3 {background-color:#cc0467;height:23px;line-height:23px;padding-left:5px;color:#fff;font-weight:bold;}
.fl-layer div {padding:20px 20px 40px 20px;line-height:30px;}
.fl-layer div span {display:block;color:#f00;padding-left:85px;}
.fl-layer div em {color:#d06;}
.close-btn {position:absolute;right:3px;top:3px;cursor:pointer;}	
.close-btn img {cursor:pointer;}
.sm-btn {border:none;width:98px;height:32px;margin-left:85px;background:url(http://pic.lvmama.com/img/myspace/sentMMS.gif) no-repeat 0 0;}
.sm-gray-btn{background:url(http://pic.lvmama.com/img/myspace/no_sentMMS.gif) no-repeat 0 0;}
.sent-mms {cursor:pointer;color:#05e;}
.fl-layer div span.sent-time {color: #999999;display: inline;font-weight: bold;padding-left: 15px;}
.fl-layer div.fl-bm {position:absolute;bottom:0;padding:0;margin:0;z-index:1000;width:360px;height:25px;line-height:25px;text-align:center;background-color:#E9E9E9;}



/*徐婷婷*/
.online_list_table td.other_xu_add{ padding-left:0px;word-break: break-all;width:110px; }
.add_xu_cont{float:left;margin:5px; display:inline}
.add_xu_cont .add_input_xu{background:url(http://pic.lvmama.com/img/myspace/myspace_fapiao/bg_xu.gif) no-repeat left top; width:59px; height:22px; line-height:22px; vertical-align:middle; text-align:center; display:inline-block;cursor:pointer; margin-right:2px; margin-left:3px;}
.add_xu_cont a.add_input01_xu{background:url(http://pic.lvmama.com/img/myspace/myspace_fapiao/bg_xu01.gif) no-repeat left top; width:39px; height:22px; line-height:22px; vertical-align:middle; text-align:center; display:inline-block; cursor:pointer; color:#000}
.add_xu_cont .add_info_xu{display:none;position:absolute; z-index:200;top:15px; background:url(http://pic.lvmama.com/img/myspace/myspace_fapiao/add_info.gif) no-repeat left top; width:229px;right:-12px; height:52px; padding:15px 10px; color:#cc0066; line-height:20px;  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);}
.add_closed{ color:#CCC}
.add_xu_cont a.add_hover{ background:url(http://pic.lvmama.com/img/myspace/myspace_fapiao/bg_xu01.gif) no-repeat left top; width:39px; height:22px; line-height:22px; vertical-align:middle; text-align:center; display:inline-block; cursor:pointer;text-decoration:none; color:#CCC;}
.add_anniu_l{ background:url(http://pic.lvmama.com/img/myspace/myspace_fapiao/add_listpic.gif) no-repeat 0px 0px; padding-left:8px; display:inline-block; height:22px;cursor:pointer;margin-top:5px;;margin-left:5px;} 
.add_anniu_r{ background:url(http://pic.lvmama.com/img/myspace/myspace_fapiao/add_listpic.gif) no-repeat 100% 0px;padding-right:8px; line-height:22px; vertical-align:middle;display:inline-block; height:22px; font-size:12px;cursor:pointer
} 

.add_anniu_l:hover{  text-decoration: none;}
.order_top a{ background:none; float:none; margin:0;paddong:0}
.order_top p{ float:none}
/*end*/
</style>
<title>我的订单</title>
</head>
<body>
<!-- modified:20100804_001 -->
<div class="">
<div class="order_top" style="height:75px;margin-top:5px;margin-bottom:0;">
<#assign orderCount=0/>
<#assign totalAmountFloat=0/>
<DIV id=u105_container class="u105_container">
<DIV id="u105_img" class="u105_original"></DIV>
<DIV id=u106 class="u106" >
<DIV id=u106_rtf style="border:1px solid #cccccc;overflow:hidden">
<INPUT id=u107 type=button class="u107" value="查询历史订单"  onclick="javascript:parent.window.location.href='/orderPros/orderPros!allOrder.do'" style="margin:12px;float:right" />
<p style="text-align:left;"><span style="font-family:宋体;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">亲爱的用户，以下订单为驴妈妈</span><span style="font-family:宋体;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#FF0000;">新订单系统</span><span style="font-family:宋体;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">产生的订单。</span></p>
<p style="text-align:left;"><span style="font-family:宋体;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">如果您找不到以前的订单，请查询</span><span style="font-family:宋体;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#000000;">历史订单</span><span style="font-family:宋体;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">。或请致电客服1010-6060！</span></p>
<p style="text-align:left;"><span style="font-family:宋体;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">我出钱你旅游！晒游记晒行程，晒酒店晒美景，来驴妈妈论坛分享旅行体验，有机会获得1000元旅游基金！<a href="http://bbs.lvmama.com/thread-1223876-1-1.html" target="_blank">活动详情&gt;&gt;</a></span></p>
</DIV></DIV>
</DIV>

</div>
<table width="732" border="0" cellpadding="0" class="online_list_table" style="margin-top:10px;">
  <tr class="order_tr1">
    <td width="50">订单号</td>
    <td width="200">预订产品</td>
    <td width="55">应付金额</td>
    <td width="70">下单时间</td>
    <td width="70">支付方式</td>
    <td width="50">订单状态</td>
    <td>操作</td>
  </tr>
  <#if pageConfig.items?has_content>
  <#list pageConfig.items as obj>
  <tr>
    <td><a href="${base}/myspace/order_detail.do?orderId=${obj.orderId?if_exists}" target="_blank">${obj.orderId?if_exists}</a>&nbsp;</td>
    <td>
    <#if obj.selfPack=='true'><a href="/product/${obj.mainProduct.productId?if_exists}" target="_blank">
    	<#if obj.mainProduct.productName?length gt 28>
          	&nbsp;${obj.mainProduct.productName?substring(0,27)}...&nbsp;<br/>
        <#else>
            &nbsp;${obj.mainProduct.productName?if_exists}&nbsp;<br/>
        </#if></a>
    <#else>
	    <#list obj.ordOrderItemProds as itemObj>
	    	<!-- 如果单独下保险，则显示保险名称 -->
	    	<#if obj.mainProduct.productType=='OTHER' && obj.mainProduct.subProductType=='INSURANCE'>
	    		&nbsp;${obj.mainProduct.productName?if_exists}&nbsp;<br/>
	    	<#else>
	    		<#if itemObj.additional?default("false")=="false">
	            <#if itemObj.wrapPage?default("false")=="true">
	            <a href="/product/${itemObj.productId?if_exists}" target="_blank">
	            </#if>
	            <#if itemObj.productName?length gt 28>
	            &nbsp;${itemObj.productName?substring(0,27)}...&nbsp;<br/>
	            <#else>
	            &nbsp;${itemObj.productName?if_exists}&nbsp;<br/>
	            </#if>
	            <#if itemObj.wrapPage?default("false")=="true"></a></#if>
	         </#if>
	    	</#if>
	    </#list>
    </#if>
    </td>
    <td style="color: #f50;">&nbsp;¥${obj.oughtPayYuan?if_exists}&nbsp;</td>
    <td>&nbsp;<#if obj.createTime?exists>${obj.createTime?string("yyyy-MM-dd")}</#if>&nbsp;</td>
    <td><#if obj.paymentTarget?default("")=="TOLVMAMA">在线支付</#if><#if obj.paymentTarget?default("")=="TOSUPPLIER">景区支付</#if>&nbsp;</td>
    <td>
    <#if obj.isPayToLvmama() && !obj.isPaymentSucc() && obj.isApprovePass() && obj.isExpireToPay()>
        取消
    <#else>
        ${obj.zhOrderViewStatus?if_exists}
    </#if>
    &nbsp;</td>
       <td class="other_xu_add">
        <#if !obj.expireToPay> 
			<#if obj.isCanToPay()> 
				<a href="${base}/view/view.do?orderId=${obj.orderId?if_exists}" target="_blank" class="add_anniu_l"><span class="add_anniu_r">进行支付</span></a><br class="clear"/> 
			</#if> 
		</#if>
         
       <#if obj.orderStatus?default("")=="NORMAL">    
		        <#if obj.paymentStatus?default("")=="UNPAY" && !obj.expireToPay>
		       		 <a href="javascript:void(0);" onClick="toCancel(${obj.orderId?if_exists});"  class="add_anniu_l"><span class="add_anniu_r">取消</span></a><br class="clear"/>
		        </#if>
        </#if>

		<#if obj.isNeedEContract() && obj.isPaymentSucc()>      
         	 <a href="/ord/downPdfContractDetail.do?orderId=${obj.orderId?if_exists}"  class="add_anniu_l"><span class="add_anniu_r">下载合同</span></a><br class="clear"/>
        </#if> 
   
        
       <#if obj.isGroupForeign() || obj.isFreenessForeign()>
			<a href="/ord/downloadTravel.do?productId=${obj.mainProduct.productId?if_exists}&productName=${obj.mainProduct.productName?if_exists}" target="_blank"  class="add_anniu_l"><span class="add_anniu_r">下载行程</span></a><br class="clear" />
	   </#if>
	    
	    <#if obj.shouldSendCert>
            <#if (obj.travellerList?size == 0)>
					<a href="#" onclick="sendSms(this,'${obj.orderId?if_exists}','${obj.contact.mobile?if_exists}');" name="sent_mms" class="add_anniu_l"><span class="add_anniu_r">重发短信凭证</span></a><br class="clear"/>
            <#else>
            <a href="#" onclick="sendSms(this,'${obj.orderId?if_exists}','${obj.travellerList.get(0).mobile?if_exists}');" name="sent_mms"  class="add_anniu_l"><span class="add_anniu_r">重发短信凭证</span></a><br class="clear"/>
            </#if>
        </#if>  
	    <#if obj.orderStatus?default("")=="NORMAL" && obj.paymentStatus?default("")=="PAYED" &&  obj.isHasLastCancelTime()>
                <div class="add_xu_cont"><a href="javascript:void(0);"  class="add_hover">取消</a>
				<div class="add_info_xu">${obj.lastCancelStr}</div>
         </#if>
</div>
    </td>
  </tr>
  </#list>
</table>
<div class="page_order">
    <@s.property escape="false" value="@com.lvmama.common.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>
</div>
<#else>
</table>
<br/>
<DIV id=u25 class="u25" >
<DIV id=u25_rtf><p style="text-align:left;"><span style="font-family:宋体;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#000000;">亲爱的用户，您在驴妈妈还没有下过订单，现在就去看看吧 ：
<span style="font-family:宋体;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#3300FF;">
<a href="http://www.lvmama.com" target="_blank">驴妈妈首页</a> 
<a href="http://www.lvmama.com/ticket" target="_blank">打折门票</a> 
<a href="http://www.lvmama.com/destroute" target="_blank">国内旅游</a>
<a href="http://www.lvmama.com/abroad" target="_blank">出境旅游</a>
</span></span></p></DIV></DIV>

</#if><#-- end of pageConfig.items -->
</div><!-- end-->



<!--=====  弹出层 Start ========-->
<div id="float_layer" style="z-index:1000;" class="fl-layer">
    <span><img src="http://pic.lvmama.com/img/myspace/close_dot.gif" alt="关闭" id="close_btn" class="close-btn" /></span>
    <h3>发送短信凭证</h3>
    <div id="resendmsgdiv">
        您的手机号码：<input id="sendmobile" type="text" size="25" readonly="true"/>
        <input id="resendmsg" type="button" onclick="resendmsg()"  value="" class="sm-btn"  /><input id="reorderheadId" type="hidden" size="25" /><span class="sent-time" id="timer"></span>
    </div>
    <div id="remsgerrordiv" class="fl-bm">若还收不到确认短信，请拨打<em>1010-6060</em>与客服取得联系</div>
</div>
<div id="float_bg" class="fl-bg"></div>



<!--=====  弹出层 End ========-->
<!-- 弹出层 -->
<div class="bg-tan"></div>
<div class="cancel_wapper">
    <input type="hidden" id="orderIdBtn" name="orderId"/>
    <h4>取消订单</h4>
    <a class="cancel_bg"><img src="http://pic.lvmama.com/img/myspace/myspace_fapiao/cancel_bt.jpg" alt="关闭" id="close_btn" /></a>
    <div class="cancel_list">
        <h3>为了更好的改进我们的服务，请选择取消原因：</h3>
        <ul class="cancel_list_s">
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="50">支付遇到问题</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="51">行程变更</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="52">产品定错（时间、房型、数量等）</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="53">重复订单</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="54">在其他网站购买</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="55">价格不够优惠</li>
            <li><input type="radio" id="reason" class="input_radio" name="itemCode" value="56">想直接去景区/酒店</li>
            <li><input type="radio" id="other" class="input_radio"  name="itemCode" value="57">其他<input name="" id="otherReason" type="text" class="input_01" /></li>
            <li class="sub_bt"><input name="" type="button" value="确认" class="denglv denglv01" onclick="cancel();" /><input name="" type="button" value="取消" class="denglv denglv03" /></li>
        </ul>
    </div>
</div>


<div class="cancel_wapper01">    
    <h4>取消订单</h4>
    <a class="cancel_bg"><img src="http://pic.lvmama.com/img/myspace/myspace_fapiao/cancel_bt.jpg" alt="关闭" id="close_btn" /></a>
    <div class="cancel_list">
     <div class="cancel_ka"><span class="cancel_closed"> 您的订单已取消</span><span style="display: block; margin-top: -2px; margin-left: 20px;">谢谢反馈！</span>
     <div class="tips_list" id="fleshId"></div>
</div>
    </div>
</div>



<script type="text/javascript" language="JavaScript" charset=“utf-8”>
{       
    $('.cancel_bg,.denglv02,.denglv03').click(function(){
                $('.bg-tan,.cancel_wapper,.cancel_wapper01').hide();
      })      
     }

    function toCancel(orderId) {
        $("#orderIdBtn").val(orderId);
        var leftN=$(window).width()/2-162,topN=$(window).height()/2-151;
        $('.bg-tan').show();
        $(".cancel_wapper").fadeIn(10).css({"top":topN+$(window).scrollTop(),"left":leftN});
    }
    
    function cancel() {
        var reasonRadio = $('input:radio[name="itemCode"]:checked');
        var itemCode = reasonRadio.val();
        var orderId = $("#orderIdBtn").val();
        var reason = "";
        if (itemCode=="57") {
            reason = "其他_" + $("#otherReason").val();
        }
        if(itemCode==null){
        	itemCode="30";
        }
        canel(orderId,itemCode,reason);
        
        var leftN=$(window).width()/2-162,topN=$(window).height()/2-151;
        $('.bg-tan').show();
        $('.cancel_wapper').hide();
        $(".cancel_wapper01").fadeIn(10).css({"top":topN+$(window).scrollTop(),"left":leftN});
        
        for(i=0;i<=5;i++) { 
           window.setTimeout("update(" + i + ")", i * 1000);
        } 
        
    }
    
    function update(num) { 
        if(num == 5) {     
           $('.bg-tan,.cancel_wapper,.cancel_wapper01').hide(); 
        }else {
           printnr = 5-num;                
           $('#fleshId').html("页面将在<strong>" + printnr + "</strong>秒内自动刷新") ;
           $('#fleshId').show(); 
        } 
    } 
</script>
<script type="text/javascript"> 
$(function(){
    $(".add_xu_cont .add_hover").bind("mouseover",function(){
        $(this).parent().css({'position':'relative'});
        $(this).next().show();
    });
      $(".add_xu_cont .add_hover").bind("mouseout",function(){
        $(this).parent().css({'position':'static'});
         $(this).next().hide();
    })
})
</script>

</body>
</html>
