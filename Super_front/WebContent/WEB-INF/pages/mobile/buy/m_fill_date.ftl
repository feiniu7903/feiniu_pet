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
.red {color:#f00;}
</style>
</head>

<body>

<div><a name="top"></a>
<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <form action="${contentPath}/m/buy/checkDate.do" method="get">
    <div>

    	<strong>您预订的是:${product.productName}</strong>
        <p class="red">
            <!--=======初始时日期输入提示 请输入2011-11-11至2011-12-12间的日期======-->
        	 ${Request.errorMessages}

            <!--=======End======-->
            <!--=======输入日期格式 错误时 提示文字  您输入的日期暂无门票，请输入2011-11-11至2011-12-12间的日期======-->
           
            <!--=======End======-->
            
        </p>
        <p>
        	
        	<strong>请填写游玩日期:</strong>
        	<input type="hidden" name="branchId" value="${branchId?if_exists}"/>
        	<input type="hidden" name="id" value="${id?if_exists}"/>
            <input type="text" name="visitTime"/><br />
            <span class="c-gray">(产品价格在节假日可能会有小幅上调)</span>
        </p>
    </div>
    <p>
      <input type="submit" accesskey="5" value="下一步" /><br/>
      <a href="${contentPath}/m/buy/prodDetail.do?id=${id}">返回上一页</a>
    </p>
    </form>
   <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
