<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/new_v/ob_common/ui-components.css,/styles/v3/module.css,/styles/v3/form.css,/styles/v3/channel.css" >
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ui_plugin/calendar.css"/>
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/order-common.css,/styles/v4/order.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/step.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/button.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/group-buy.css">
</head>
<body class="groupBuy">
<div class="order-header wrap">
    <div class="header-inner">
        <a class="logo" href="http://www.lvmama.com/">自助天下游 就找驴妈妈</a>
        <p class="serverTime">
            服务热线时间：8:00-20:00（全年无休）
        </p>
        <p class="serverPhone">
            <strong>400-6040-616</strong>（免长话费）
        </p>
    </div>
</div>
<div class="wrap">
    <#include "/WEB-INF/pages/tuangou/navigation.ftl">
    <@navigation stepView="2"/>
    <h3 class="buy-head"><b>券码来源：${channelInfo.distributorName?if_exists}</b></h3>
    <h3 class="buy-head"><b>预订产品：${prodProduct.productName?if_exists}</b></h3>
    <h3 class="buy-head"><b>　　　　　${productBranch.branchName?if_exists}</b></h3>
    <div class="buy-main border equalheight-box">
        <div class="sidebar equalheight-item">
            <div class="sidebox side-setbox" style="">
                <dl>
                    <dt>一 出行日库存表说明：</dt>
                    <dd>日期上显示“充足”，表示该日产品余量充足，可以预约；显示具体数值，表示该日产品余量的数量，最多可预约该数量，请尽快预约，库存量随时间会有减少，以实际下单时刻为准；不显示信息，表示没有该日不能预约，请修改日期。</dd>
                </dl>
                <dl>
                    <dt>二 预订数量说明：</dt>
                    <dd>
                        1.预订数量值对应着券码的量，比如：预订数量为3对应需要3个券码<br>
                        2.最多一次可预订的数量，表示最多一次可使用券码的数量。
                    </dd>
                </dl>
                <dl>
                    <dt>三 怎么才算预约成功：</dt>
                    <dd>
                        1.在线提交预约申请，页面跳转到订单创建成功页面，预约才算成功。预约成功后，驴妈妈将向您发送【驴妈妈】确认短信作为消费凭证
                        2.如遇不可抗因素无法如期消费，请提前与驴妈妈客服联系，我们将尽量为您安排改期，具体提前天数请以团购网的产品说明为准，有些产品一经预约成功不可改期，视具体产品而定。
                    </dd>
                </dl>
            </div>
        </div>
        <form method="post" id="tuanDetailFormId">
        	<input type="hidden" name="buyInfo.productId" value="${prodProduct.productId}"></input>
			<input type="hidden" name="buyInfo.prodBranchId" value="${productBranch.prodBranchId}"></input>
			<input type="hidden" name="buyInfo.productType" value="${prodProduct.productType?if_exists}"></input>
			<input type="hidden" name="buyInfo.subProductType" value="${prodProduct.subProductType?if_exists}"></input>
			<input type="hidden" name="buyInfo.channel" value="${channelInfo.distributorCode?if_exists}"></input>
		    <div class="main equalheight-item">
		        <div class="buy-title">
		            <h3 class="buy-head3">选择出行日期</h3>
		            <div id="buy-calendar">
		                <div class="time-price-one" data-pid="${productBranch.productId?if_exists}" data-bid="${productBranch.prodBranchId?if_exists}" validEndTime="${validEndTime?if_exists}"></div>
					</div>
		            <h3 class="buy-head3">请填写预订信息</h3>
		            <div class="buy-order-info">
		                <dl>
		                    <dt>您选择的出行日期是：</dt>
		                    <dd>
		                    	<span class="buy-date" id="yuyueDateId"></span><i class="buy-date-tip">预约时间一经确认，则无法更改。</i>
		                    	<input type="hidden" name="buyInfo.visitTime"/>
		                    </dd>
		                </dl>
		                <dl>
		                    <dt>预订数量：</dt>
		                    <dd>
		                        <span class="oper-numbox">
		                            <input type="hidden" id="param${productBranch.prodBranchId}" textNum="${productBranch.prodBranchId}"  minAmt="${productBranch.minimum}" maxAmt="<#if (productBranch.maximum>0) >${productBranch.maximum}<#else>${productBranch.stock}</#if>">
		                            <a class="op-reduce op-disable-reduce J_reduce">-</a>
		                            <input class="op-number" type="text" maxlength="8" value="1" id="${productBranch.prodBranchId?if_exists}" name="buyInfo.buyNum.product_${productBranch.prodBranchId}">
		                            <a class="op-increase J_increase">+</a>
		                        </span>
		                    </dd>
		                </dl>
		                <dl class="couponInput">
		                    <dt>券&nbsp;&nbsp;&nbsp;号：</dt>
		                    <#if tuanCouponLists?? && tuanCouponLists.size() gt 0>
			                    <dd><input type="text" class="buy-ticket-num" name="tuanCouponLists[0].distributionCouponCode" value="${tuanCouponLists[0].distributionCouponCode?if_exists}" onkeyup="value=value.replace(/[^\d\w]/g,'')"></dd>
		                    <#else>
		                    	<dd><input type="text" class="buy-ticket-num" name="tuanCouponLists[0].distributionCouponCode" value="" onkeyup="value=value.replace(/[^\d\w]/g,'')"></dd>
		                    </#if>
		                </dl>
		                <dl>
		                    <dt>验证码：</dt>
		                    <dd><input type="text" class="buy-check-num" name="verifycode"><img class="buy-check-img" src="/account/checknewcode.htm" id="image"><a class="buy-check-change" href="#" onClick="refreshCheckCode('image');return false;">看不清，换一张</a> </dd>
		                </dl>
		                <input type="hidden" name="batchId" value="${batchId?if_exists}"></input>
		                <dl>
		                    <dt></dt>
		                    <dd><input type="button" class="btn btn-pink"  href="#" id="tuanDetailsNextId" value="下一步"/></dd>
		                </dl>
		            </div>
		        </div>
		    </div>
        </from>
    </div>
</div>
<div id="order-footer" class="lv-footer wrap"></div>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js"></script>
<script src="/js/tuangou/tuan_distribution.js"></script>
<!--<script src="http://s2.lvjs.com.cn/js/common/copyright.js"></script>-->
</body>
</html>