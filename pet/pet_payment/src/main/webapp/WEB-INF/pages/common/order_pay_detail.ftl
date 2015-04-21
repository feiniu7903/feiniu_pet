<#include "navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="description">
<meta content="" name="keywords">
<title>下单完成_驴妈妈旅游网</title>
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/s2_globalV1_0.css">
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
</head>

<body>
	<!--===== 头部文件区域 S ======-->
		<div style="text-align:center;">
			<#include "orderHeaderNew.ftl">
		</div>
	<!--===== 头部文件区域 E ======-->

	<!--===== 导航条 S ======-->

	<!--===== 导航条 E ======-->

	<!--===== 页面信用贴士S ======-->

	<!--===== 页面信用贴士E ======-->

<div class="tips-info">
  <p class="tips-title align-center">
 
      <strong><s></s>
      <#if cashaccountRecharge>
      	您的充值操作成功！ 请到 <a href="http://www.lvmama.com/myspace/account/viewcharge.do" class="normal">我的充值  </a>中查看.
      <#else>
                     您的订单已支付成功！ 请到 <a href="http://www.lvmama.com/myspace/order.do" class="normal">我的订单  </a>中查看该订单.
      </#if>
                </li>
                 
      </strong>
  </p>
</div>


<!--===== 底部文件区域 S ======-->
<#include "orderFooter.ftl">

<script type='text/javascript'>
    if (window.GridsumWebDissector) {
        var _gsTracker = GridsumWebDissector.getTracker("GWD-000268");
        _gsTracker.track("/targetpage/order_completed");
    }
</script>
<!--===== 底部文件区域 E ======-->
<script language="javascript" src="http://pic.lvmama.com/js/super_v2/order_func.js"></script>
<#include "ga.ftl"/>
</body>
</html>
