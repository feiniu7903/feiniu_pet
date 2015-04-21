<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
<link href="http://pic.lvmama.com/styles/new_v/help_center/hc_index.css?r=4589" rel="stylesheet" type="text/css" />
<title>${seoCurrentContentTitle?if_exists}-驴妈妈旅游网</title>
<meta name="keywords" content="${currentContentTypeName?if_exists},帮助中心"/>
<meta name="description" content="${seoCurrentContentContent?if_exists}"/>
<style type="text/css">
.daodu_title{ height:30px; line-height:30px; vertical-align:middle; padding-left:5px; font-size:14px; font-weight:bold; color:#333; background:#ddd; margin:0 auto;width:695px;}
.daodu_list{ border:#ddd solid 1px; width:698px; margin:0 auto}
.add_margin{ margin-top:15px;}
.reback_bt{   margin-left: 342px;}
.wenti_info{width:734px; margin:0 auto;}
.wenti_info table{ border-top:#ddd 1px solid; border-left:#ddd 1px solid; height:25px;margin:10px auto;width:96%;}
.wenti_info table td{border-bottom:#ddd 1px solid;border-right:#ddd 1px solid; height:25px; border-left:0px; padding:0px; margin:0px; text-align:center; line-height:25px;}
</style>
<#include "/common/coremetricsHead.ftl">
</head>
<body>
<#include "/WEB-INF/pages/helpCenter/head.ftl"/>
<div class="wapper_hc">
  <div class="hc_main">
    <ul class="daohang_bars">
      <li><a href="/public/help">帮助中心 </a></li>
      <li id="businessTypeLi"></li>
      <li><a id="contentTypeLi" href="/public/help_${contentTypeId}"></a></li>
    </ul>
    <h2 class="time_biao" id="contentTypeH2"></h2>
    <h3 class="wenti_titile"> <@s.property value="${detailContentIndex}+1"/>. <@s.property value="helpCenterContentList[${detailContentIndex}].title"/></h3>
    <div class="wenti_info">
    <p><@s.property value="helpCenterContentList[${detailContentIndex}].content" escape="false"/></p>
    <a href="/public/help_${contentTypeId}" class="info_page page_margin  reback_bt" style="">返回</a>
    </div>
     <br class="clear" />
    <h4 class="daodu_title">相关问题导读</h4>
    <div class="daodu_list">
    	<ul class="common_wenti_list">
	    	<@s.if test="${detailContentIndex} > 1 ">
		          <li>
					<@s.property value="${detailContentIndex}-1"/>.<a href="/public/help_center_<@s.property value="helpCenterContentList[${detailContentIndex}-2].id"/>"  >
					<@s.property  value="helpCenterContentList[${detailContentIndex}-2].title"/></a>
				</li>
			 </@s.if>
		     <@s.if test="${detailContentIndex} > 0 ">
			     <li>
				 	<@s.property value="${detailContentIndex}"/>.<a href="/public/help_center_<@s.property value="helpCenterContentList[${detailContentIndex}-1].id"/>"  >
					<@s.property  value="helpCenterContentList[${detailContentIndex}-1].title"/></a>
			 	 </li>
		 	 </@s.if>
	 	  <@s.if test="${detailContentIndex} < helpCenterContentList.size()-1">
	          <li>
			   	 <@s.property value="${detailContentIndex}+2"/>.<a href="/public/help_center_<@s.property value="helpCenterContentList[${detailContentIndex}+1].id"/>"  >
			   	 <@s.property value="helpCenterContentList[${detailContentIndex}+1].title"/></a>
		 	</li>
	 	</@s.if>
	 	<@s.if test="${detailContentIndex} < helpCenterContentList.size()-2">
	        <li>
		   		<@s.property value="${detailContentIndex}+3"/>.<a href="/public/help_center_<@s.property value="helpCenterContentList[${detailContentIndex}+2].id"/>"  >
		   		<@s.property value="helpCenterContentList[${detailContentIndex}+2].title"/></a>
		   	</li>
	   	</@s.if>
    </ul>
    </div>
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
<script>
	var conextPath='';
</script>
<script type="text/javascript" src="/js/help/help.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js?r=8673" language="javascript"></script>
	<script>
		cmCreatePageviewTag("帮助详情_${currentContentTypeName?if_exists}_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStrNoSuffix(helpCenterContentList[${detailContentIndex}].title,6)"/>", "${currentBusinessTypeName?if_exists}", null, null);
	</script>
</body>
</html>
