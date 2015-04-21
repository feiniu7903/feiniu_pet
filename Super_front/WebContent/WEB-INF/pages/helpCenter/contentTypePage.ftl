<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
<link href="http://pic.lvmama.com/styles/new_v/help_center/hc_index.css?r=4589" rel="stylesheet" type="text/css" />
<title>驴妈妈${currentBusinessTypeName?if_exists},${currentContentTypeName?if_exists}-驴妈妈旅游网</title>
<meta name="keywords" content="${currentContentTypeName?if_exists}"/>
<meta name="description" content="驴妈妈帮助中心服务,提供${currentBusinessTypeName?if_exists}帮助中心,其中包括驴妈妈${currentBusinessTypeName?if_exists}${currentContentTypeName?if_exists},旅游优惠劵使用.旅游付款发票,以及旅游退款跟换旅游产品和途中酒店住宿,旅游保险,旅游合同签署等。"/>
<#include "/common/coremetricsHead.ftl">
</head>
<script>
	var conextPath='';
</script>
<script type="text/javascript" src="/js/help/help.js"></script>
<body>
<#include "/WEB-INF/pages/helpCenter/head.ftl"/>
<div class="wapper_hc">
  <div class="hc_main">
    <ul class="daohang_bars">
      <li><a href="/public/help">帮助中心 </a></li>
      <li id="businessTypeLi"></li>
      <li id="contentTypeLi"></li>
    </ul>
    <h2 class="time_biao" id="contentTypeH2"></h2>
    <ul class="common_wenti_list">
      <@s.iterator value="helpCenterContentList" status="helpCenterContent" id="outer">
         <form name="form${id}" id="form${id}" action="/public/help_center_${id}" method="post">
           <input type="hidden"  name="contentTypeId" value="${typeId}"/>
        <li><a href="javascript:void(0)" onclick="submitToContentDetailPage('form${id}')"> <@s.property value="#helpCenterContent.index+1"/>. ${title}</a></li>
        </form>
      </@s.iterator>
    </ul>
  </div>
<#include "/WEB-INF/pages/helpCenter/left_aside.ftl"/>
<br class="clear"/>
<!-- 底通 -->
<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('nee5a1b3b8a570b30001','js',null)"/>
<!-- 底通/End -->
<!-- footer start--> 
<#include "/common/footer.ftl"> 
<!-- footer end-->
</div>
<script src="http://pic.lvmama.com/js/common/losc.js?r=8673" language="javascript"></script>
	<script>
		cmCreatePageviewTag("帮助列表_${currentContentTypeName?if_exists}", "${currentBusinessTypeName?if_exists}", null, null);
	</script>
</body>
</html>
