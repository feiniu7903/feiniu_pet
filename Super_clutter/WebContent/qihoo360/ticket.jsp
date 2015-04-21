<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>景点门票</title>
    <link rel='stylesheet' href='http://super.lvmama.com/clutter/qihoo360/css/reset-grids-comm.css' />
    <link rel='stylesheet' href='http://super.lvmama.com/clutter/qihoo360/css/frameset.css' />
    <link rel='stylesheet' href='http://super.lvmama.com/clutter/qihoo360/css/iframe.css' />
</head>
<body>
<ul class="rank-list">
	<s:iterator value="productList" var="product" status="i">
	    <li class="gloomy clearfix">
		    <span class="order top"><s:property value="#i.index+1"/></span>
		    <span class="title"><a href="http://www.lvmama.com/dest/<s:property value="#product.url"/>" target="_blank">
			    <s:if test="#product.placeName.length()>6">
			    	<s:property value="#product.placeName.substring(0,6)+'...'"/>
			    </s:if>
			    <s:else>
			    	<s:property value="#product.placeName"/>
			    </s:else>
		    </a></span>
		    <span class="full-prize">¥<em><s:property value="#product.marketPrice"/></em></span>
		    <span class="prize red">¥<s:property value="#product.sellPrice"/></span>
	    </li>
    </s:iterator>
</ul>
</body>
</html>
