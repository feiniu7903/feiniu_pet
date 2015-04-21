<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驴妈妈打折门票索引第${placeList.currentPage}页</title>
<link href="/style/global.css" type="text/css" rel="stylesheet"  />
<link href="/style/layout.css" type="text/css" rel="stylesheet"  />
<link href="http://pic.lvmama.com/styles/map.css" type="text/css" rel="stylesheet"  />
<link charset="utf-8" media="screen" type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss">
<link rel="shortcut icon" href="/img/favicon.ico" />
<script type="text/javascript" src="/js/common/jquery.js"></script>
<#include "/WEB-INF/pages/seo/commonJsIncluedTop.ftl">	
<#include "/WEB-INF/pages/common/coremetricsHead.ftl">
</head>


<body>
<div id="container">  
<center><#include "/WEB-INF/pages/common/header.ftl"></center>
<div class="m_book"> <div style="margin-top:10px;"><a href="http://www.lvmama.com">驴妈妈旅游网 </a>><a href="http://www.lvmama.com/public/site_map">网站地图 </a>><span>更多景点门票</span></div>
  <div class="map_names">驴妈妈打折门票</div>
  <ul class="map_list2">
	<#list placeList.items as place >
		 <li><a href="http://www.lvmama.com/dest/${place.pinYinUrl}">${place.name}门票</a></li>
	</#list>  
  </ul>
<div class="page_list">
	<#if (placeList.currentPage!=placeList.totalPageNum) > 
	   <a href="menpiao_<#if (placeList.currentPage==placeList.totalPageNum)>${placeList.currentPage}<#else>${placeList.currentPage+1}</#if>" class="page_up">下一页</a>
	   <a href="menpiao_${placeList.totalPageNum}">${placeList.totalPageNum}</a> 
	   <a href="menpiao_${placeList.currentPage+6}" class="dot_list">...</a> 
	   <#if (placeList.currentPage+5) < placeList.totalPageNum><a href="menpiao_${placeList.currentPage+5}">${placeList.currentPage+5}</a> </#if>
	   <#if (placeList.currentPage+4) < placeList.totalPageNum><a href="menpiao_${placeList.currentPage+4}">${placeList.currentPage+4}</a> </#if>
	   <#if (placeList.currentPage+3) < placeList.totalPageNum><a href="menpiao_${placeList.currentPage+3}">${placeList.currentPage+3}</a> </#if>
	   <#if (placeList.currentPage+2) < placeList.totalPageNum><a href="menpiao_${placeList.currentPage+2}">${placeList.currentPage+2}</a> </#if>
	   <#if (placeList.currentPage+1) < placeList.totalPageNum><a href="menpiao_${placeList.currentPage+1}">${placeList.currentPage+1}</a> </#if>
	   <a class="page_hover">${placeList.currentPage}</a> 
	   <a href="menpiao_<#if (placeList.currentPage-1)<=0>1<#else>${placeList.currentPage-1}</#if>" class="page_up">上一页</a> </div>
	</#if>
</div>
<#include "/WEB-INF/pages/common/footer.ftl">
<#include "/WEB-INF/pages/common/mvHost.ftl"/></div>
<script src="http://pic.lvmama.com/min/index.php?g=common" type="text/javascript" charset="utf-8"></script>
<script>
	cmCreatePageviewTag("网站地图-门票", "A0001", null, null);
</script>
</div>
</body>
</html>
