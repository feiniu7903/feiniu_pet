<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="author" content="chg" /> 
<meta http-equiv="Cache-Control" content="no-cache" />
<title>驴妈妈-手机版</title>
<link href="http://pic.lvmama.com/styles/wap/lvmamaWap.css?r=2916" rel="stylesheet" type="text/css" />
<style>
.pc-list {color:#333;padding:5px 0;}
.pc-list li {padding:0 5px;}
.pc-list .odd-line {background-color:#EFEFEF;}
.pc-list del {color:#666;}
.pc-list strong {color:#f00;}
.pc-list em {color:#f60;font-style:normal;}
</style>
</head>

<body>

<div>


<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <div class="nav">
    	<p>
    	<#if user!=null>
    	 <!--====== 登录状态时显示    ======-->
          <a href="${contentPath}/m/myorder/list.do">我的订单</a>|<a href="${contentPath}/m/account/info.do">我的账户</a>|<a href="${sso}/logout?serviceId=${wapIndex}">退出登录</a><br />
          <span>
       	 ${wapUserName},你好！</span><br />
        </span>
    	<#else>
    	 <a href="${contentPath}/m/myorder/list.do">我的订单</a>|<a href="${contentPath}/m/account/info.do">我的账户</a>|<a href="${sso}/wap/toLogin.do?service=${wapIndex}">登录/注册</a></a><br />
          <!--====== End ======-->
    	</#if>
         
        </p>
    </div>
    <div class="search">
      <form id="searchForm" method="get" action="${searchHost}/search/mobile.do">
        <input type="text" size="20" name="keyword" value="漂流" /><br />
        <input type="hidden" name="locate" value="search"/>
        <input type="submit" value=" 搜景点 " name="searchType" />&nbsp;&nbsp;&nbsp;<input type="submit" value=" 淘门票 " name="searchType" />
      </form>
      <div><a href="${contentPath}/m/help/location.do?id=15">【推荐】驴妈妈手机客户端发布啦！</a></div>
    </div>
    <div class="search-index">
    	<h3>景点查找</h3>
        <p>
        	[<a href="${destHost}/place/mobileDest.do">目的地</a>]
            <a href="${destHost}/place/mobileDest.do?id=86&city=city">常州</a>&nbsp;<a href="${destHost}/place/mobileDest.do?id=92&city=city">扬州</a>&nbsp;<a href="${destHost}/place/mobileDest.do?id=87&city=city">苏州</a>&nbsp;<a href="${destHost}/place/mobileDest.do?id=79&city=city">上海</a>&nbsp;<a href="${destHost}/place/mobileDest.do?id=100&city=city">杭州</a>&nbsp;<a href="${destHost}/place/mobileDest.do">更多>></a>
        <br />
        	[<a href="${searchHost}/search/themeSearch.do?topic=index">游玩主题</a>]
            <a href="${searchHost}/search/themeSearch.do?topic=%E6%BC%82%E6%B5%81">漂流</a>&nbsp;<a href="${searchHost}/search/themeSearch.do?topic=%E9%85%92%E5%BA%97">酒店</a>&nbsp;<a href="${searchHost}/search/themeSearch.do?topic=%E4%B8%BB%E9%A2%98%E4%B9%90%E5%9B%AD">主题乐园</a>&nbsp;<a href="${searchHost}/search/themeSearch.do?topic=%E6%B0%B4%E4%B9%A1%E5%8F%A4%E9%95%87">水乡古镇</a>&nbsp;<a href="${searchHost}/search/themeSearch.do?topic=index">更多>></a>
        </p>
    </div>
    <div>
    	<h3><a href="${contentPath}/m/prod/index.do">热门打折门票</a></h3>
        <ul class="pc-list">
        	<li><a href="${contentPath}/m/buy/prodDetail.do?id=2266&locate=index">上海欢乐谷门票</a><br />
            原价:<del>￥200</del>&nbsp;现售:<strong>￥180</strong>&nbsp;</li>
                	<li class="odd-line"><a href="${contentPath}/m/buy/prodDetail.do?id=375&locate=index">上海野生动物园门票</a><br />
            原价:<del>￥130</del>&nbsp;现售:<strong>￥117</strong>&nbsp;</li>
                	<li><a href="${contentPath}/m/buy/prodDetail.do?id=26906&locate=index">杭州野生动物世界亲子票</a><br />
            原价:<del>￥375</del>&nbsp;现售:<strong>￥355</strong>&nbsp;</li>
                	<li class="odd-line"><a href="${contentPath}/m/buy/prodDetail.do?id=2648&locate=index">千岛湖门船票</a><br />
            原价:<del>￥195</del>&nbsp;现售:<strong>￥175</strong>&nbsp;</li>
                	<li><a href="${contentPath}/m/buy/prodDetail.do?id=1442&locate=index">常州中华恐龙园门票</a><br />
            原价:<del>￥160</del>&nbsp;现售:<strong>￥150</strong>&nbsp;</li>
                	<li class="odd-line"><a href="${contentPath}/m/buy/prodDetail.do?id=2933&locate=index">溧阳天目湖山水园（含船）、南山竹海联票</a><br />
            原价:<del>￥160</del>&nbsp;现售:<strong>￥130</strong>&nbsp;</li>
                	<li><a href="${contentPath}/m/buy/prodDetail.do?id=182&locate=index">余姚丹山赤水门票</a><br />
            原价:<del>￥50</del>&nbsp;现售:<strong>￥40</strong>&nbsp;</li>
                	<li class="odd-line"><a href="${contentPath}/m/buy/prodDetail.do?id=5077&locate=index">杭州印象西湖第一场成人票</a><br />
            原价:<del>￥260</del>&nbsp;现售:<strong>￥220</strong>&nbsp;</li>
        </ul>
        &nbsp;<a href="${contentPath}/m/prod/index.do">更多>></a>
    </div>
   <#include "/WEB-INF/pages/mobile/common/footer.ftl">
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>


