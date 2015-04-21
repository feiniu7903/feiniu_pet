<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="author" content="chg" /> 
<meta http-equiv="Cache-Control" content="no-cache" />
<title>驴妈妈-手机版</title>
<link href="http://pic.lvmama.com/styles/wap/lvmamaWap.css?r=2916" rel="stylesheet" type="text/css" />
</head>

<body>

<div><a name="top"></a>
	<#include "/WEB-INF/pages/mobile/common/page_common.ftl">
	<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <div>
    <#if viewPage!=null>
    	<@s.if test="type=='FEATURES'">
			<h3>产品特色</h3>
	        <#if (viewPage.contents.get('FEATURES'))??>
				<p>${viewPage.contents.get('FEATURES').contentRn?if_exists}	</p>
			</#if>
		</@s.if>
		<@s.elseif test="type=='IMPORTMENTCLEW'">
			<h3>重要提示</h3>
	        <#if (viewPage.contents.get('IMPORTMENTCLEW'))??>
                <p>
                <@s.property value="viewPage.contents.get('IMPORTMENTCLEW').contentRn" escape="false"/>
               </p>
	        </#if>
	      
		</@s.elseif>
		<@s.else>
			<h3>费用说明</h3>
            <#if (viewPage.contents.get('COSTCONTAIN'))??>
            	 <p>
                	<@s.property value="viewPage.contents.get('COSTCONTAIN').contentRn" escape="false"/>
                </p>
        	</#if>
        	
		</@s.else>
		<#if textPageConfig!=null>
		<@pagination textPageConfig.currentPage textPageConfig.totalPage contentPath+(textPageConfig.url)></@pagination>
		</#if>
		<p>
		<@s.if test="type=='FEATURES'">
			<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=FEATURES">产品特色<a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=IMPORTMENTCLEW">重要提示</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=COSTCONTAIN">费用说明</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/loadComPlace.do?id=${id}&productId=${id}">包含景点</a>
		</@s.if>
		<@s.elseif test="type=='IMPORTMENTCLEW'">
			<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=FEATURES">产品特色</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=IMPORTMENTCLEW">重要提示</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=COSTCONTAIN">费用说明</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/loadComPlace.do?id=${id}&productId=${id}">包含景点</a>
		</@s.elseif>
		<@s.elseif test="type=='COSTCONTAIN'">
			<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=FEATURES">产品特色</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=IMPORTMENTCLEW">重要提示</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/productCharacter.do?id=${id}&type=COSTCONTAIN">费用说明</a>&nbsp;&nbsp;<a href="${contentPath}/m/buy/loadComPlace.do?id=${id}&productId=${id}">包含景点</a>
		</@s.elseif>
        </p>
    </div>
    <p>
      <a href="#top">返回顶部</a><br />
      <a href="${contentPath}/m/buy/prodDetail.do?id=${id}">返回门票页面</a>
    </p>
    <#else>
    无产品展示相关的数据
    </#if>
    <#include "/WEB-INF/pages/mobile/common/footer2.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
