<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/new_v/ob_common/ui-components.css,/styles/v3/module.css,/styles/v3/form.css,/styles/v3/channel.css">
    <link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/step.css">
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
    <@navigation stepView="1"/>
    <h3 class="buy-head"><b>团购预约通道</b>游客在团购平台购买了驴妈妈的产品，通过从团购平台获取的券码在此进行预约</h3>
    <div class="buy-main border">
        <div class="buy-ticket">
            <div class="buy-ticket-wrap">
                <dl>
                    <dt>券码：</dt>
                    <dd>游客在团购网站下单购买驴妈妈的产品后，从团购网站获取的凭证码，游客可以使用该凭证码在此进行出游的预约。预约成功后驴妈妈会向游客发送入园（景区）
                        入住（酒店）时使用的电子票券。
                    </dd>
                </dl>
            </div>
            <form action="/product/distributionDetail.do" method="post" id="tuanCouponFormId">
	            <div class="buy-form">
	                <dl>
	                    <dt>券&nbsp;&nbsp;&nbsp;码：</dt>
	                    <dd><input type="text" class="buy-ticket-num" id="couponCodeId" name="tuanCouponLists[0].distributionCouponCode" onkeyup="value=value.replace(/[^\d\w]/g,'');"/></dd>
	                </dl>
	                <dl>
	                    <dt>验证码：</dt>
	                    <dd><input type="text" class="buy-check-num" name="verifycode"><img class="buy-check-img" id="image" src="/account/checknewcode.htm"><a class="buy-check-change" href="#" onClick="refreshCheckCode('image');return false;">看不清，换一张</a> </dd>
	                </dl>
	                <dl>
	                    <dt></dt>
	                    <dd><button class="btn btn-pink" href="#" id="tuanNextId"> 下一步 </button></dd>
	                </dl>
	            </div>
            </form>
        </div>
    </div>
</div>
<div id="order-footer" class="lv-footer wrap">
</div>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/common/footer.js"></script>
<script src="/js/tuangou/tuan_distribution.js"></script>
<!--<script src="http://s2.lvjs.com.cn/js/common/copyright.js"></script>-->
</body>
</html>