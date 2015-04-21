<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>成都暑期主题旅游_成都娱乐/科普/自然景点好去处-驴妈妈旅游网 </title> 
<meta name="keywords" content="成都暑期,成都主题旅游"> 
<meta name="description" content="炎热的暑期别赖在空调下啦!跟着驴妈妈玩乐大课堂,踏足成都娱乐,科普,自然的旅游景点,寻找童年大乐趣!还有幸运大奖等您来抽取,同时我们将和您一起进行一公斤公益旅行!让世界因旅行而更美好!"> 
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/liuyi/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<base target="_blank">

</head>

<body>

<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->

<div class="wrap classStyle1">

    <div class="topBox">
        <div class="topBanner">
        	<ul class="btnList">
            	<li class="nav_li"><span class="classBtn1"></span></li>
                <li><span class="classBtn2"></span></li>
                <li><span class="classBtn3"></span></li>
            </ul>
        </div>
    </div>
    
    <h3 class="titleBox"></h3>
    <div class="cpBox" style="display:block">
    	<div class="cpBoxBg">
        	<@s.iterator value="map.get('${station}_cd_ylk_recommend')" status="st">
        	<div class="itemBox">
            	<div class="leftBox">
                	<h3><a href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <#if remark?exists && remark!=""> <p><span>推荐理由</span>${remark?if_exists}</p></#if>
                    <#if bakWord3?exists && bakWord3!=""> <p class="teshhu"><span>景区活动</span>${bakWord3?if_exists}</p></#if>
                    <span class="price mt20">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                   <#if bakWord6?exists && bakWord6!=""> <span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                    <a href="${url?if_exists}" target="_blank" class="orderBtn1"></a>
                </div>
                <div class="rightBox">
                	<a href="${url?if_exists}" target="_blank" title="${title?if_exists}"><img src="${imgUrl?if_exists}" width="230" height="261" alt="${title?if_exists}"/></a>
                </div>
            </div>
          </@s.iterator>
            <ul class="cpList">
            	<@s.iterator value="map.get('${station}_cd_ylk')" status="st">
            	<li>
                	 <#if bakWord4?exists && bakWord4=="买小送大"> <span class="tips1"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="买大送小"> <span class="tips2"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="大小同价"> <span class="tips3"></span></#if>
                    <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="240" height="154" alt="${title?if_exists}"/></a>
                    <h3><a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <div class="priceBox">
                    	<div class="jiageBox">
                        	<span class="price">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                   	 		<#if bakWord6?exists && bakWord6!=""><span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                        </div>
                        <a href="${url?if_exists}" class="orderBtn2"></a>
                    </div>
                </li>
                </@s.iterator>
            </ul>
        </div>
    </div>
    
    <div class="cpBox">
    	<div class="cpBoxBg">
        	<@s.iterator value="map.get('${station}_cd_kpk_recommend')" status="st">
        	<div class="itemBox">
            	<div class="leftBox">
                	<h3><a href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <#if remark?exists && remark!=""> <p><span>推荐理由</span>${remark?if_exists}</p></#if>
                    <#if bakWord3?exists && bakWord3!=""> <p class="teshhu"><span>景区活动</span>${bakWord3?if_exists}</p></#if>
                    <span class="price mt20">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                   <#if bakWord6?exists && bakWord6!=""> <span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                    <a href="${url?if_exists}" target="_blank" class="orderBtn1"></a>
                </div>
                <div class="rightBox">
                	<a href="${url?if_exists}" title="${title?if_exists}"><img src="${imgUrl?if_exists}" width="230" height="261" alt="${title?if_exists}"/></a>
                </div>
            </div>
          </@s.iterator> 
            
            <ul class="cpList">
            	<@s.iterator value="map.get('${station}_cd_kpk')" status="st">
            	<li>
                	 <#if bakWord4?exists && bakWord4=="买小送大"> <span class="tips1"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="买大送小"> <span class="tips2"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="大小同价"> <span class="tips3"></span></#if>
                    <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="240" height="154" alt="${title?if_exists}"/></a>
                    <h3><a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <div class="priceBox">
                    	<div class="jiageBox">
                        	<span class="price">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                   	 		<#if bakWord6?exists && bakWord6!=""><span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                        </div>
                        <a href="${url?if_exists}" class="orderBtn2"></a>
                    </div>
                </li>
                </@s.iterator>
            </ul>
        </div>
    </div>
	<div class="cpBox">
    	<div class="cpBoxBg">
        <@s.iterator value="map.get('${station}_cd_zrk_recommend')" status="st">
        	<div class="itemBox">
            	<div class="leftBox">
                	<h3><a href="${url?if_exists}" target="_blank" title="${title?if_exists}">${title?if_exists}</a></h3>
                    <#if remark?exists && remark!=""> <p><span>推荐理由</span>${remark?if_exists}</p></#if>
                    <#if bakWord3?exists && bakWord3!=""> <p class="teshhu"><span>景区活动</span>${bakWord3?if_exists}</p></#if>
                    <span class="price mt20">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
                   <#if bakWord6?exists && bakWord6!=""> <span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
                    <a href="${url?if_exists}" target="_blank" class="orderBtn1"></a>
                </div>
                <div class="rightBox">
                	<a href="${url?if_exists}" title="${title?if_exists}"><img src="${imgUrl?if_exists}" width="230" height="261" alt="${title?if_exists}"/></a>
                </div>
            </div>
          </@s.iterator>   
            <ul class="cpList">
            	 <@s.iterator value="map.get('${station}_cd_zrk')" status="st">
	            	<li>
	               	 <#if bakWord4?exists && bakWord4=="买小送大"> <span class="tips1"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="买大送小"> <span class="tips2"></span></#if>
                	 <#if bakWord4?exists && bakWord4=="大小同价"> <span class="tips3"></span></#if>
	                    <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="240" height="154" alt="${title?if_exists}"/></a>
	                    <h3><a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></h3>
	                    <div class="priceBox">
	                    	<div class="jiageBox">
	                        	<span class="price">${bakWord5?if_exists}：<em>&yen;${bakWord1?if_exists}起</em></span>
	                   	 		<#if bakWord6?exists && bakWord6!=""><span class="price">${bakWord6?if_exists}：<em>&yen;${bakWord2?if_exists}起</em></span></#if>
	                        </div>
	                        <a href="${url?if_exists}" class="orderBtn2"></a>
	                    </div>
	                </li>
	              </@s.iterator>
            </ul>
        </div>
    </div>
   
  
  	<div class="sp_pop" style="background:none;height:320px;width:65px;top:50%;margin-top:-160px;">
        <ul class="fdcList"> 
        	<li><a href="http://www.lvmama.com/zt/lvyou/shuqi1" target="_self">上海</a></li>
            <li><a href="http://www.lvmama.com/zt/lvyou/shuqi1/guangzhou.html" target="_self">广州</a></li>
            <li><a href="http://www.lvmama.com/zt/lvyou/shuqi1/beijing.html" target="_self">北京</a></li>
            <li class="nav_li"><a href="http://www.lvmama.com/zt/lvyou/shuqi1/chengdou.html" target="_self">成都</a></li>
        </ul>
    </div>
    
</div>


<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js" type="text/javascript"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/tongxintongle/index.js"></script>

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
<script src="http://www.lvmama.com/zt/000global/js/eventCM.js" type="text/javascript"></script>
</body>
</html>
