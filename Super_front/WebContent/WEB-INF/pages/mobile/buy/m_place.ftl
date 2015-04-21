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
.jindian {padding:0 0 5px;}
.jindian img {margin:0 5px;}
.title {background-color:#EFEFEF;padding:5px;}
.content {padding:0 5px 5px;}
</style>
</head>

<body>

<div><a name="top"></a>
		<#include "/WEB-INF/pages/mobile/common/header.ftl"> 
    <div>
    	<h3>包含景点</h3>
        <div class="jindian">
        <@s.iterator value="comPlaceList">
       
        	<div class="title"><a href="${destHost}/place/mobilePlace.do?id=${placeId}">${name}</a></div>
            <div class="content">
              <a href="#"><img src="http://pic.lvmama.com/${smallImage}" width="120" alt="" /></a><br />
              <a href="${destHost}/place/mobilePlace.do?id=${placeId}&type=1">查看全部图片</a>
            </div>
        	 </@s.iterator>
        </div>
        
        <p><a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=FEATURES">产品特色</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=IMPORTMENTCLEW">重要提示</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=COSTCONTAIN">费用说明</a>&nbsp;&nbsp;包含景点</p>
    </div>
    <p>
      <a href="#top">返回顶部</a><br />
      <a href="${contentPath}/m/buy/prodDetail.do?id=${productId}">返回门票页面</a>
    </p>
 <#include "/WEB-INF/pages/mobile/common/footer2.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
