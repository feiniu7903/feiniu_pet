<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml
1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="version" content="4.0" />
    <title>财付通-我的钱包</title>
	<link href="https://img.tenpay.com/wallet/v4.0/css/wallet_v4.css" rel="stylesheet" type="text/css" />
    <link href="http://pic.lvmama.com/styles/super_v2/mybank/lvmama_v1.css?r=2916" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
    <script type="text/javascript" src="http://pic.lvmama.com/js/super_v2/mybank/mybank_func.js?r=2913"></script>
</head>
<body>
 <form id="productFrom" action="/purse/createOrder.do" method="post" onsubmit="return checkReceiver()">
 <@s.token></@s.token>
<#include "/WEB-INF/pages/purse/buy/buyInfo.ftl"/>
  <#include "/WEB-INF/pages/purse/top.ftl"/>
        <div class="lvmama-body mg-t0 relative">
        	<span class="back-1" onclick="javascript:history.go(-1);">[返回上一页]</span>
        	<h3 class="bd-b">预订：<a href="<@s.property value="@com.lvmama.product.utils.URLProperties@tencentURL()" />/product/<@s.property value="mainProduct.productId" />" ><@s.property value="mainProduct.productName"/></a></h3>
            	<div class="w-500 relative">
                  <dl class="order-info1">
                      <dt class="mg-t20">取票人信息<span class="c-888">（我们将向取票人手机发送订单短信，请准确填写）</span></dt>
                      <dd>
                        <div><span>*</span> 取票人姓名：<input type="text" id="fetchTicketUserName"  name="usrReceiversList[0].receiverName"/>
                          <input type="hidden" id="fetchTicketUserReceiverId" value="" name="usrReceiversList[0].receiverId" />
                          <input type="hidden"  name="usrReceiversList[0].useOffen"  value="true" />
				                       <select name="myReceivers" id="myReceivers"  onchange="updateFetchTicketInfo(this)">
											<option value="">从常用联系人中选</option>
										    <@s.iterator value="receiversList">
										     <option value="${receiverId?if_exists}" card_type="${cardType?if_exists}" card_num="${cardNum?if_exists}" mobile="${mobileNumber?if_exists}" name="${receiverName?if_exists}">${receiverName?if_exists}</option>
										    </@s.iterator>
										</select>
                        </div>  
                        <div><span>*</span> 取票人手机：<input type="text" id="fetchTicketUserMobile"  style="ime-mode: disabled;"  name="usrReceiversList[0].mobileNumber" /></div>
                      </dd>
                      <dt class="mg-t20">订票人信息</dt>
                      <dd>
                        <div><span>*</span> 订票人姓名：<input type="text" id="buyTicketName" name="buyReceivers.receiverName" />
                        			<input type="hidden" id="buyTicketPersonId"  name="buyReceivers.receiverId"  value="" />
                        			<input type="hidden"  name="buyReceivers.useOffen"  value="true" />
							            <select name="receiversList" id="receiversList" onchange="updateBuyTicketInfo(this)">
											<option value="">从常用联系人中选</option>
										    <@s.iterator value="receiversList">
										     <option value="${receiverId?if_exists}" email="${email?if_exists}" card_type="${cardType?if_exists}" card_num="${cardNum?if_exists}" mobile="${mobileNumber?if_exists}" name="${receiverName?if_exists}">${receiverName?if_exists}</option>
										    </@s.iterator>
										</select>
                        
                        </div>
                        <div><span>*</span> 订票人手机：<input id="buyTicketMobile"  type="text" style="ime-mode: disabled;"   name="buyReceivers.mobileNumber"  /></div>
                      </dd>
                  </dl>
                       <a href="javascript:history.go(-1);" class="step-back-2" >上一步</a><button type="submit" id="sub_button" class="button btn-p1">提交订单</button>
                       <#include "/WEB-INF/pages/purse/buy/sum_price.ftl"/>
                </div>
        </div>
    </form>
<script src="/js/buy/form.js" type="text/javascript"></script>
<script src="/js/buyBase.js" type="text/javascript"></script>
<script src="/js/fillNew.js" type="text/javascript"></script>
</body>
</html>

