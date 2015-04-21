﻿﻿<%@page import="java.util.Enumeration"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%
  response.setStatus(response.SC_NOT_FOUND);
  String url=request.getHeader("Referer");
  String toUrl = request.getAttribute("coremetricsRequestUrl").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>小驴找不到妈妈了</title>
<style type="text/css">
*{margin:0;padding:0;}
.top_mod{background:url(http://pic.lvmama.com/img/v7_404bg.jpg) no-repeat;width:890px;height:396px}
.nav_mod{width:890px;margin:0 auto;overflow:hidden;}
.nav_mod span{float:left;display:inline-block;color:#343434;line-height:34px;height:34px;width:auto;padding:0 5px;font-size:24px;font-family:"微软雅黑";margin-left:60px;}
.nav_list{list-style:none;padding:0;margin:0;float:left;}
.nav_list li{height:34px;float:left;width:auto;padding:0 15px;background:#ea4c88;margin:0 0 0 10px;line-height:34px;border-radius:2px; cursor:pointer;}
.nav_list li a{color:#fff; text-decoration:none;font-size:12px;font-family:"宋体";}
.nav_list li:hover{background:#DF1078}
.end_mod{width:890px;height:199px;}
</style>
<%@ include file="/common/coremetricsHead.jsp"%> 
</head>

<body>

<div style="width:890px;margin:0 auto;background:url(http://pic.lvmama.com/img/v7_404bg.jpg) no-repeat 0 0;padding-top:395px;">
    <div class="nav_mod">
    	<span>您还可以</span>
        <ul class="nav_list">
        	<li><a href="http://www.lvmama.com" title="回到驴妈妈首页">回到驴妈妈首页</a></li>
            <li><a href="http://www.lvmama.com/destroute" title="看看其他优惠线路">看看其他优惠线路</a></li>
            <li><a href="http://www.lvmama.com/comment/" title="驴友真实点评">驴友真实点评</a></li>
            <li><a href="http://www.lvmama.com/zt/s/" title="精品专题聚合页">精品专题聚合页</a></li>
            <li><a href="http://www.lvmama.com/info/" title="驴妈妈资讯频道">驴妈妈资讯频道</a></li>
        </ul>
    </div>
    <div class="end_mod">
    	<img src="http://pic.lvmama.com/img/v7_404_bg.jpg" width="890" height="199" border="0" usemap="#Map">
        <map name="Map" id="Map">
          <area shape="rect" coords="326,79,485,113" href="http://itunes.apple.com/cn/app/id443926246?mt=8" target="_blank">
          <area shape="rect" coords="504,79,662,114" href="http://www.windowsphone.com/zh-cn/store/app/%E9%A9%B4%E5%A6%88%E5%A6%88%E6%97%85%E6%B8%B8/e864b7e7-db6a-432d-8ab8-2e18ec690f41" target="_blank">
          <area shape="rect" coords="326,129,485,164" href="http://m.lvmama.com/rewrite/d.php" target="_blank">
          <area shape="rect" coords="505,131,664,166" href="http://itunes.apple.com/cn/app/id715334091?mt=8" target="_blank">
        </map>
    </div>
</div>

<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-5493172-1");
pageTracker._setDomainName(".lvmama.com");
pageTracker._setAllowHash(false);
pageTracker._addOrganic("soso","w");   
pageTracker._addOrganic("sogou","query");  
pageTracker._addOrganic("youdao","q");     
pageTracker._trackPageview();
} catch(err) {}</script>

<script type="text/javascript"> 
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F006c64491cb8acf2092ce0e0341797fe' type='text/javascript'%3E%3C/script%3E"));
</script>


<!-- Gridsum tracking code begin. -->
<script type='text/javascript'>
    (function () {
        var s = document.createElement('script');
        s.type = 'text/javascript';
        s.async = true;
        s.src = (location.protocol == 'https:' ? 'https://ssl.' : 'http://static.')
         + 'gridsumdissector.com/js/Clients/GWD-000268-6F8036/gs.js';
        var firstScript = document.getElementsByTagName('script')[0];
        firstScript.parentNode.insertBefore(s, firstScript);
    })();
</script>
<!--Gridsum tracking code end. -->

<script>
     cmCreatePageviewTag("错误页&from="+"<%=url %>"+"&to=<%=toUrl %>", "U0001", null, null);
</script>
</body>
</html>
