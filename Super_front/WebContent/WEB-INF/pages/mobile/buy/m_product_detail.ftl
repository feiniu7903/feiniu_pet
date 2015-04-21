<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="author" content="chg" /> 
<meta http-equiv="Cache-Control" content="no-cache" />
<title><@s.property value="comPlace.name"/></title>
<link href="http://pic.lvmama.com/styles/wap/lvmamaWap.css?r=2916" rel="stylesheet" type="text/css" />
<style>
h5 {font-style:normal;}
.pc-list {color:#333;padding:5px 0;}
.pc-list li {padding:0 5px;}
.pc-list .odd-line {background-color:#EFEFEF;}
.pc-list del {color:#666;}
.pc-list strong {color:#f00;}
.pc-list em {color:#f60;font-style:normal;}
</style>
</head>

<body>

<div><a name="top"></a>
<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <div>
    	<h3>${prodCProduct.prodProduct.productName}</h3>
        <p>
		支付方式：<#if prodCProduct.prodProduct.payToLvmama=="true">
			线上支付
		<#else>
			取票时付款
				</#if>
		<br />
        <a href="${contentPath}/m/buy/productCharacter.do?id=${prodCProduct.prodProduct.productId}&type=FEATURES">产品特色</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${prodCProduct.prodProduct.productId}&type=IMPORTMENTCLEW">重要提示</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${prodCProduct.prodProduct.productId}&type=COSTCONTAIN">费用说明</a>
        </p>
        <p>
        <strong>[包含景点]</strong><br />
        <a href="${destHost}/place/mobilePlace.do?id=<@s.property value="comPlace.placeId"/>&productId=${prodCProduct.prodProduct.productId}&locate=place">  <@s.property value="comPlace.name"/></a><br />
        </p>
        <h3>票种</h3>
        <ul class="pc-list">
        	
        	<@s.iterator value="branchList">
        	${branchName}
        	原价:<del>￥<@s.property value="marketPriceYuan"/></del>&nbsp;现售:<strong>￥<@s.property value="sellPriceYuan"/></strong>&nbsp;奖金:<em>￥<@s.property value="prodProduct.cashRefund"/></em></li><br/>
        	</@s.iterator>
        </ul>
        <form action="${contentPath}/m/buy/toFillDate.do" method="post">
        	<input type="hidden" name="id" value="${id}"/>
        	<input type="hidden" name="branchId" value="${mainBranch.prodBranchId}"/>
        	<input name="bookRightNow" type="submit" accesskey="5" value=" 立即预订 " />
        </form>
        <h3 class="mt-5">产品经理推荐</h3>
        <p>
        <#if viewPage!=null>
        <#if (viewPage.contents.get('MANAGERRECOMMEND'))??>
		                    <@s.property value="viewPage.contents.get('MANAGERRECOMMEND').contentRn" escape="false"/>
		           </#if>
        </#if>
    	
        </p>
    </div>
    <p>
      <a href="#top">返回顶部</a><br />
  		<#if locate=='search'>

      <a href="${searchHost}/search/mobile.do?keyword=${keyword}&searchType=${searchType}&locate=${locate}">返回搜索结果</a><br /><!--=========== 返回搜索结果,如若是类似“推荐门票”“热门门票”进入的，显示 ============-->
      </#if>
          <#if locate=='index'>
           <a href="${contentPath}/m/prod/index.do?locate=index">返回打折门票首页</a>
      </#if>
      <#if locate=='theme'>
           <a href="${destHost}/place/mobilePlace.do?id=${placeId}&locate=theme">返回上一页</a>
      </#if>
      <#if locate=='place'>
           <a href="${destHost}/place/mobilePlace.do?id=${placeId}&locate=place">返回上一页</a>
      </#if>
      <#if locate=='dest'>
           <a href="${destHost}/place/mobilePlace.do?id=${placeId}&locate=place">返回上一页</a>
      </#if>
    

     
    </p>
   <#include "/WEB-INF/pages/mobile/common/footer2.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
