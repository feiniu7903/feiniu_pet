<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站地图</title>
<link href="/style/global.css" type="text/css" rel="stylesheet"  />
<link charset="utf-8" media="screen" type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss">
<link href="/style/layout.css" type="text/css" rel="stylesheet"  />
<link href="http://pic.lvmama.com/styles/map.css" type="text/css" rel="stylesheet"  />
<link rel="shortcut icon" href="/img/favicon.ico" />
<script type="text/javascript" src="/js/common/jquery.js"></script>
<#include "/WEB-INF/pages/seo/commonJsIncluedTop.ftl">	
<#include "/WEB-INF/pages/common/coremetricsHead.ftl">
</head>

<body>
<div id="container">  
<#include "/WEB-INF/pages/common/header.ftl">
	<div class="m_book">
    		<div style="margin-top:10px;"><a href="http://www.lvmama.com">首页 </a>><span>网站地图 </span></div>
		    <#list mapList as map>
		  		<div class="map_names">${map['parent']}</div>
		  		<ul class="map_list1">
		  		<#list map['item'] as m>
		  			<li><a href="${m[1]}">${m[0]}</a></li>
		  		</#list>
		  		</ul>
		    </#list>
	</div>
<#include "/WEB-INF/pages/common/footer.ftl">
<#include "/WEB-INF/pages/common/mvHost.ftl"/>
</div>
</div>
<script src="http://pic.lvmama.com/min/index.php?g=common" type="text/javascript" charset="utf-8"></script>
<script>
	cmCreatePageviewTag("网站地图", "A0001", null, null);
</script>
</body>
</html>
