<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="keywords" content="${specialTopic.seoKeywords?if_exists}">
<meta name="description" content="${specialTopic.seoDescription?if_exists}">
<title>${specialTopic.seoTitle?if_exists}</title>
<link rel="shortcut icon" href="http://www.lvmama.com/img/favicon.ico" />
<link href="http://pic.lvmama.com/min/index.php?g=commonIncluedTop" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/lp_template.css?r=8647" />
<link href="http://pic.lvmama.com/min/index.php?g=commonCss" rel="stylesheet" type="text/css" media="screen" charset="utf-8"/>
<script src="http://pic.lvmama.com/min/index.php?g=common" type="text/javascript" charset="utf-8"></script>
<script language="javascript" src="http://pic.lvmama.com/scripts/front/base/dom.js" type="text/javascript"></script>
</head>
<body>

<!--====================LPmoban content go===================-->

<!--=============lpmoban head content go=============-->
<#--
<div class="lpmb_head"><img src="http://pic.lvmama.com/img/lp_template/temp/lpmb_top.gif" title="" alt=""/></div>
-->
<#include "/common/header.ftl">
<!--=============lpmoban head content end!===========-->
<style>.destnt_share{ position: absolute;right: 0; top: 1px; width:212px;} 
</style>
<div class="main-container">
<!--============= 当前位置导航 S ================-->
	<div id="s2-site-nav">
	<div class="destnt_share"> 
		<!-- JiaThis Button BEGIN --> 
		<div id="ckepop"> 
		<a rel="nofollow" href="http://www.jiathis.com/share" class="jiathis jiathis_txt" target="_blank"><img src="http://v3.jiathis.com/code_mini/images/btn/v1/jiathis1.gif" border="0" /></a> 
		</div> 
		<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
		
		
		<!-- JiaThis Button END --> 
	</div>
	<span>您当前所处的位置:</span>
    </div>
 </div>
<!--=============lpmoban banner&flash content go=============-->
<div class="lpmb_div">
<#assign lpFileHost=lpFileHost/>
<#if lpFileHost?has_content==false>
<#assign lpFileHost="http://pic.lvmama.com"/>
</#if>
<div class="lpmb_banner"><img src="${lpFileHost}${specialTopic.bannerImage?if_exists}" title="banner" alt="暂无图片"/></div>
<!--=============lpmoban banner&flash content end!===========-->
<!--=============lpmoban bengyueremai content go================-->
<#assign headText1="本月热卖"/>
<#assign headText2="驴友人气榜"/>
<#assign headText3="门票"/>
<#assign headText4="套餐"/>
<#assign headText5="酒店"/>
<#assign headText6="跟团游"/>
<#assign headText7="旅游推荐"/>
<#assign headText8="更多精彩之处"/>
<#assign headText9=""/>
<#assign headText10=""/>
<#if lpPageBlockList?has_content>
<#list lpPageBlockList as obj>
<#if obj_index=0>
	<#assign headText1=obj.title?default("")/>
<#elseif obj_index=1>
	<#assign headText2=obj.title?default("")/>
<#elseif obj_index=2>
	<#assign headText3=obj.title?default("")/>
<#elseif obj_index=3>
	<#assign headText4=obj.title?default("")/>
<#elseif obj_index=4>
	<#assign headText5=obj.title?default("")/>
<#elseif obj_index=5>
	<#assign headText6=obj.title?default("")/>
<#elseif obj_index=6>
	<#assign headText7=obj.title?default("")/>
<#elseif obj_index=7>
	<#assign headText8=obj.title?default("")/>
<#elseif obj_index=8>
	<#assign headText9=obj.title?default("")/>
<#elseif obj_index=9>
	<#assign headText10=obj.title?default("")/>
<#else>
</#if>
</#list>
</#if>

<#if block1ItemList?has_content><!--本月热卖 -->
<div class="lpmb_hot">
	<div class="lpmb_hotleft">
   	  <div class="lpmb_hotleft_top"></div>
        <ul class="lpmb_hotleft_body">
        	<#assign block1Title=""/>
        	<#assign block1Url=""/>
        	<#assign block1Imgurl=""/>
        	<#assign block1Iconurl=""/>
        	<#assign block1Remark=""/>
        	<#assign block1MemberPrice=""/>
        	<#assign block1MarketPrice=""/>
        	<#assign block1DetailTitle=""/>
        	<#assign block1DetailRemark=""/>
          	<#list block1ItemList as obj>
          	<#assign block1Title=obj.title?default("")/>
          	<#assign block1Url=obj.url?default("#")/>
          	<#assign block1Imgurl=obj.imgUrl?default("")/>
          	<#assign block1Iconurl=obj.bakWord1?default("#")/><!-- bakWord1规定放小图标 -->
          	<#assign block1Remark=obj.remark?default("")/>
          	<#assign block1MemberPrice=obj.memberPrice?default("")/>
          	<#assign block1MarketPrice=obj.marketPrice?default("")/>
          	<#assign block1DetailTitle=obj.bakWord2?default("")/><!-- bakWord2规定放简介标题 -->
          	<#assign block1DetailRemark=obj.bakWord3?default("")/><!-- bakWord3规定简介内容 -->
          	<#break/>
          	</#list>
        	<li class="lpmb_hotleft_bt">${headText1}</li>
            <li class="lpmb_hotleft_name">
            <a href="${block1Url}" target="_blank">${block1Title}</a>
            <#if block1Iconurl?has_content>
            <img src="${block1Iconurl}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
            </#if><!-- 描述标题右边的小图片 --></li>
            <li class="lpmb_hotleft_pic"><a href="${block1Url}" target="_blank"><img src="${block1Imgurl}" width="100" height="75" alt="" /></a><!-- 本月热卖图片--></li>
          	<li class="lpmb_hotleft_txt"><span class="lpmb_gaytxt">驴妈妈价：</span><span class="lpmb_redtxt">&yen;</span><span class="lpmb_red14data">${block1MemberPrice?default(0)}</span>　　<span class="lpmb_gaytxt">门市价：</span><span class="lpmb_deletetxt">&yen;${block1MarketPrice?default(0)}</span><br />${block1Remark?if_exists}</li>
        </ul>
        <div class="lpmb_hotleft_foot"></div>
    </div><!-- end of div:lpmb_hotleft-->
    <div class="lpmb_hotright">
    <h2>${block1DetailTitle?if_exists}</h2><br/>${block1DetailRemark?if_exists}
    </div>
</div><!-- end of lpmb_hot-->
</#if>
<!--=============lpmoban bengyueremai content end!==============-->


<!--=============lpmoban mainbody go================-->
<div class="lpmb_main">
  <div class="lpmb_mainleft">
    
    <#if block4ItemList?has_content><!--驴友人气榜 -->
    <div class="lpmb_leftlist_btbox">
    <h1>${headText2}</h1>
    <span>市场价</span><span>驴妈妈价</span></div>
    <div class="lpmb_listbox">
      <table width="720" border="0" cellspacing="0" cellpadding="0">
        <#list block4ItemList as obj>
        <tr onmouseover="this.bgColor='#f9f3f9'" onmouseout="this.bgColor=''">
          <td><#if obj.bakWord4?has_content>[${obj.bakWord4?if_exists}]</#if> <a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a>
          <#if obj.bakWord1?has_content>
          <img src="${obj.bakWord1?default("#")}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
          </#if>
          ${obj.remark?if_exists}</td>
          <td width="56" class="redstyle">&yen;<span>${obj.memberPrice?default(0)?number}</span></td>
          <td width="56" class="deletestyle">&yen;${obj.marketPrice?default(0)?number}</td>
        </tr>
        </#list>
      </table>
    </div>
    </#if>
    
    <#if block5ItemList?has_content><!--门票推荐 -->
    <div class="lpmb_leftlist_btbox">
    <h2>${headText3}</h2>
    <span>市场价</span><span>驴妈妈价</span></div>
    <div class="lpmb_listbox">
      <table width="720" border="0" cellspacing="0" cellpadding="0">
        <#list block5ItemList as obj>
        <tr onmouseover="this.bgColor='#f9f3f9'" onmouseout="this.bgColor=''">
          <td><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a>
          <#if obj.bakWord1?has_content>
          <img src="${obj.bakWord1?default("#")}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
          </#if>
          ${obj.remark?if_exists}</td>
          <td width="56" class="redstyle">&yen;<span>${obj.memberPrice?default(0)?number}</span></td>
          <td width="56" class="deletestyle">&yen;${obj.marketPrice?default(0)?number}</td>
        </tr>
        <#if obj_index=5>
        <#break/>
        </#if>
        </#list>
      </table>
      <!--门票推荐 more -->
      <div class="lpmb_listmore"><a href="javascript:void(0)" onclick="showbox(this)">更多</a></div>
      <table width="720" border="0" cellspacing="0" cellpadding="0" id="search_box" style="display:none;">
        <#list block5ItemList as obj>
        <#if obj_index gt 5>
        <tr onmouseover="this.bgColor='#f9f3f9'" onmouseout="this.bgColor=''">
          <td><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a>
          <#if obj.bakWord1?has_content>
          <img src="${obj.bakWord1?default("#")}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
          </#if>
          ${obj.remark?if_exists}</td>
          <td width="56" class="redstyle">&yen;<span>${obj.memberPrice?default(0)?number}</span></td>
          <td width="56" class="deletestyle">&yen;${obj.marketPrice?default(0)?number}</td>
        </tr>
        </#if>
        </#list>
      </table>
    </div>
    </#if>
    
    <#if block6ItemList?has_content><!--套餐推荐 -->
    <div class="lpmb_leftlist_btbox">
    <h2>${headText4}</h2>
    <span>市场价</span><span>驴妈妈价</span></div>
    <div class="lpmb_listbox">
      <table width="720" border="0" cellspacing="0" cellpadding="0">
        <#list block6ItemList as obj>
        <tr onmouseover="this.bgColor='#f9f3f9'" onmouseout="this.bgColor=''">
          <td><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a>
          <#if obj.bakWord1?has_content>
          <img src="${obj.bakWord1?default("#")}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
          </#if>
          ${obj.remark?if_exists}</td>
          <td width="56" class="redstyle">&yen;<span>${obj.memberPrice?default(0)?number}</span></td>
          <td width="56" class="deletestyle">&yen;${obj.marketPrice?default(0)?number}</td>
        </tr>
        <#if obj_index=5>
        <#break/>
        </#if>
        </#list>
      </table>
      <!--套餐推荐 more -->
      <div class="lpmb_listmore"><a href="javascript:void(0)" onclick="showbox(this)">更多</a></div>
      <table width="720" border="0" cellspacing="0" cellpadding="0" id="search_box" style="display:none;">
        <#list block6ItemList as obj>
        <#if obj_index gt 5>
        <tr onmouseover="this.bgColor='#f9f3f9'" onmouseout="this.bgColor=''">
          <td><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a>
          <#if obj.bakWord1?has_content>
          <img src="${obj.bakWord1?default("#")}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
          </#if>
          ${obj.remark?if_exists}</td>
          <td width="56" class="redstyle">&yen;<span>${obj.memberPrice?default(0)?number}</span></td>
          <td width="56" class="deletestyle">&yen;${obj.marketPrice?default(0)?number}</td>
        </tr>
        </#if>
        </#list>
      </table>
    </div>
    </#if>
    
    <#if block7ItemList?has_content><!--酒店推荐 -->
    <div class="lpmb_leftlist_btbox">
    <h2>${headText5}</h2>
    <span>市场价</span><span>驴妈妈价</span></div>
    <div class="lpmb_listbox">
      <table width="720" border="0" cellspacing="0" cellpadding="0">
        <#list block7ItemList as obj>
        <tr onmouseover="this.bgColor='#f9f3f9'" onmouseout="this.bgColor=''">
          <td><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a>
          <#if obj.bakWord1?has_content>
          <img src="${obj.bakWord1?default("#")}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
          </#if>
          ${obj.remark?if_exists}</td>
          <td width="56" class="redstyle">&yen;<span>${obj.memberPrice?default(0)?number}</span></td>
          <td width="56" class="deletestyle">&yen;${obj.marketPrice?default(0)?number}</td>
        </tr>
        <#if obj_index=5>
        <#break/>
        </#if>
        </#list>
      </table>
      <!--酒店推荐 more -->
      <div class="lpmb_listmore"><a href="javascript:void(0)" onclick="showbox(this)">更多</a></div>
      <table width="720" border="0" cellspacing="0" cellpadding="0" id="search_box" style="display:none;">
        <#list block7ItemList as obj>
        <#if obj_index gt 5>
        <tr onmouseover="this.bgColor='#f9f3f9'" onmouseout="this.bgColor=''">
          <td><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a>
          <#if obj.bakWord1?has_content>
          <img src="${obj.bakWord1?default("#")}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
          </#if>
          ${obj.remark?if_exists}</td>
          <td width="56" class="redstyle">&yen;<span>${obj.memberPrice?default(0)?number}</span></td>
          <td width="56" class="deletestyle">&yen;${obj.marketPrice?default(0)?number}</td>
        </tr>
        </#if>
        </#list>
      </table>
    </div>
    </#if>
    
    <#if block8ItemList?has_content><!--拼团游推荐 -->
    <div class="lpmb_leftlist_btbox">
    <h2>${headText6}</h2>
    <span>市场价</span><span>驴妈妈价</span></div>
    <div class="lpmb_listbox">
      
      <table width="720" border="0" cellspacing="0" cellpadding="0">
        <#list block8ItemList as obj>
        <tr onmouseover="this.bgColor='#f9f3f9'" onmouseout="this.bgColor=''">
          <td><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a>
          <#if obj.bakWord1?has_content>
          <img src="${obj.bakWord1?default("#")}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
          </#if>
          ${obj.remark?if_exists}</td>
          <td width="56" class="redstyle">&yen;<span>${obj.memberPrice?default(0)?number}</span></td>
          <td width="56" class="deletestyle">&yen;${obj.marketPrice?default(0)?number}</td>
        </tr>
        <#if obj_index=5>
        <#break/>
        </#if>
        </#list>
      </table>
      <!--拼团游推荐 more -->
      <div class="lpmb_listmore"><a href="javascript:void(0)" onclick="showbox(this)">更多</a></div>
      <table width="720" border="0" cellspacing="0" cellpadding="0" id="search_box" style="display:none;">
        <#list block8ItemList as obj>
        <#if obj_index gt 5>
        <tr onmouseover="this.bgColor='#f9f3f9'" onmouseout="this.bgColor=''">
          <td><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a>
          <#if obj.bakWord1?has_content>
          <img src="${obj.bakWord1?default("#")}" alt="暂无图片" width="32" height="15" class="lpmb_icon" />
          </#if>
          ${obj.remark?if_exists}</td>
          <td width="56" class="redstyle">&yen;<span>${obj.memberPrice?default(0)?number}</span></td>
          <td width="56" class="deletestyle">&yen;${obj.marketPrice?default(0)?number}</td>
        </tr>
        </#if>
        </#list>
      </table>
    </div>
    </#if>
    
  </div><!-- end of lpmb_hotleft -->
  
  <div class="lpmb_mainright"><!-- 更多精彩之处 -->
    <h2>${headText8}</h2>
    <#if block10ItemList?has_content>
    <ul>
      <#list block10ItemList as obj>
      <li><a href="${obj.url?default("#")}" target="_blank"><img src="${pFileHost}${obj.imgUrl?default("")}" width="200" height="40" alt="${obj.title?if_exists}" /></a>
        <p><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a></p>
      </li>
      </#list>
    </ul>
    </#if>
  </div><!-- end of lpmb_mainright -->
  
</div><!-- end of lpmb_hot -->
<!--=============lpmoban footlink end!==============-->
<!-- 旅游推荐 -->
<div class="lpmb_footlink">
	<h2>${headText7}</h2>
    <#if block9ItemList?has_content>
    <ul>
    	<#list block9ItemList as obj>
    	<li><a href="${obj.url?default("#")}" target="_blank">${obj.title?if_exists}</a></li>
    	</#list>
    </ul>
    <#else>
    <!--<ul>
    	<li><a href="http://www.lvmama.com/zt/huaqiaocheng/" target="_blank">东部华侨城</a></li>
    	<li><a href="http://www.lvmama.com/zt/spring/index.html" target="_blank">春游踏青</a></li>
    	<li><a href="http://www.lvmama.com/zt/expo/" target="_blank">世博会</a></li>
    	<li><a href="http://www.lvmama.com/zt/wuyuan/" target="_blank">婺源旅游</a></li>

    	<li><a href="http://www.lvmama.com/zt/hangzhou/" target="_blank">杭州旅游</a></li>
    	<li><a href="http://www.lvmama.com/zt/yangzhou/" target="_blank">扬州旅游</a></li>
    	<li><a href="http://www.lvmama.com/zt/happyvalley/" target="_blank">上海欢乐谷</a></li>
    	<li><a href="http://www.lvmama.com/zt/meihua/index.html" target="_blank">春季旅游</a></li>
    	<li><a href="http://www.lvmama.com/zt/qiandaohu/" target="_blank">千岛湖</a></li>
    	<li><a href="http://www.lvmama.com/zt/orientalpearl/" target="_blank">东方明珠</a></li>

    	<li><a href="http://www.lvmama.com/zt/tianmuwenquan/" target="_blank">扬州温泉</a></li>
    	<li><a href="http://www.lvmama.com/zt/linan/" target="_blank">临安旅游</a></li>
    	<li><a href="http://www.lvmama.com/lp/wuyuan/" target="_blank">婺源旅游</a></li>
    	<li><a href="http://www.lvmama.com/lp/shanghaishibohui/" target="_blank">世博会门票</a></li>
    	<li><a href="http://www.lvmama.com/lp/spring/" target="_blank">春季旅游</a></li>
    	<li><a href="http://www.lvmama.com/lp/yingshibo/" target="_blank">世博旅游</a></li>

    	<li><a href="http://www.lvmama.com/lp/qdh/" target="_blank">千岛湖</a></li>
    	<li><a href="http://www.lvmama.com/lp/wenquan/" target="_blank">温泉度假</a></li>
    	<li><a href="http://www.lvmama.com/lp/wuxi/" target="_blank">无锡旅游</a></li>
    	<li><a href="http://www.lvmama.com/zt/huangshan/" target="_blank">黄山旅游</a></li>
    	<li><a href="http://www.lvmama.com/zt/qingdao/" target="_blank">青岛旅游</a></li>
    	<li><a href="http://www.lvmama.com/zt/dalian/" target="_blank">大连旅游</a></li>

    	<li><a href="http://www.lvmama.com/zt/xiamen/" target="_blank">厦门旅游</a></li>
    	<li><a href="http://www.lvmama.com/info/" target="_blank">旅游资讯</a></li>
    	<li><a href="http://happyvalley.lvmama.com/" target="_blank">欢乐谷</a></li>
    	<li><a href="http://www.lvmama.com/lp/ningbo/" target="_blank">宁波旅游</a></li>
    </ul>-->
    <script type='text/javascript' src='http://suipian.lvmama.com/api/js.php?tagname=LP%E5%BA%95%E9%83%A8%E6%96%87%E4%BB%B6'></script>
    </#if>
</div>
<!--=============lpmoban footlink end!==============-->



<!--=============lpmoban mainbody end!==============-->



<!--=============lpmoban footer content go================-->
<#--
<div class="lpmb_head"><img src="http://pic.lvmama.com/img/lp_template/temp/lpmb_foot.gif" title="footer" alt="footer" /></div>
-->
<!--=============lpmoban footer content end!==============-->

<!--====================LPmoban content end!=================-->
</div>

<SCRIPT type=text/javascript>
var __aObj=document.getElementsByTagName("a");
var __length=__aObj.length;
for(var i=0;i<__length;i++){
	__aObj[i].onfocus=function(){this.blur();}
}

function showbox(obj){
if (dom.nextElement(obj.parentNode).style.display=='none'){
obj.innerHTML='关闭';
dom.nextElement(obj.parentNode).style.display="";

} else {
obj.innerHTML='更多';
dom.nextElement(obj.parentNode).style.display="none";
}
}
</SCRIPT>
<script type="text/javascript">

//===========================点击展开关闭效果====================================

function openShutManager(oSourceObj,oTargetObj,shutAble,oOpenTip,oShutTip){
var sourceObj = typeof oSourceObj == "string" ? document.getElementById(oSourceObj) : oSourceObj;
var targetObj = typeof oTargetObj == "string" ? document.getElementById(oTargetObj) : oTargetObj;
var openTip = oOpenTip || "";
var shutTip = oShutTip || "";
if(targetObj.style.display!="none"){
   if(shutAble) return;
   targetObj.style.display="none";
   if(openTip && shutTip){
    sourceObj.innerhtml = shutTip;
   }
} else {
   targetObj.style.display="block";
   if(openTip && shutTip){
    sourceObj.innerhtml = openTip;
   }
}
}
</script>
</body>
<script src="http://pic.lvmama.com/js/common/losc.js?r=8673" type="text/javascript"></script>
</html>
