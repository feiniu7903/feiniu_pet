<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>写点评页面</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/new_v/ob_comment/base.css,/styles/new_v/ob_comment/c_common.css,/styles/new_v/ob_comment/common.css"/>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/new_v/ob_comment/x_comment.js"></script>
<#include "/common/coremetricsHead.ftl">
</head>
<body class="lvcomment">
<!--头部开始-->
<#include "/common/header.ftl">
	<div class="c_write_con c_shadow">
  <h1 class="c_w_spot yahei">
  
  <#if place??>
		<a  href="http://www.lvmama.com/dest/${place.pinYinUrl}"  target="_blank" class="fl"/>${place.name}</a>
  </#if>
  <#if product??>
		<a  href="http://www.lvmama.com/product/${product.productId}"  target="_blank"  class="fl"/>${product.productName}</a>
  </#if>
  <a href="/comment" class="c_w_retuen fr" >返回点评首页</a></h1>

   <#include "/WEB-INF/pages/comment/writeCmtCommon.ftl">
   </div>
<!--footer>>-->
<#include "/WEB-INF/pages/comment/generalComment/commentListFooter.ftl" />
<!--footer<<-->
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
 <script>
 	if("<@s.property value="place"/>"!=""){
 		cmCreatePageviewTag("写点评_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(place.name)" />", "T0001", null, null);
 	}else if("<@s.property value="product"/>"!=""){
 		cmCreatePageviewTag("写点评_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(product.productName)" />", "T0001", null, null);
 	}
 </script>
</body>
</html>
