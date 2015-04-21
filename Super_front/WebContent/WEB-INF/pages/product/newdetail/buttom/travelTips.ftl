<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<#include "/WEB-INF/pages/product/newdetail/seo_product.ftl"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/new_v/ob_detail/free.css,/styles/new_v/ui_plugin/calendar.css,/styles/new_v/ui_plugin/jquery-ui-1.8.17.custom.css,/styles/new_v/global.css,/styles/new_v/ob_comment/c_common.css,/styles/new_v/ob_comment/c_free.css,/styles/new_v/ob_common/ui-components.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/v3/buttons.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/button.css,/styles/v4/modules/dialog.css"/>
<script src="http://pic.lvmama.com/min/index.php?f=js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/new_v/ob_comment/x_comment.js,/js/new_v/ui_plugin/jquery-time-price-table.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js" ></script>
<style type="text/css">
table {border:2px solid #FF008D;font-size:14px;line-height:24px;} 
table td {border:1px solid #FF008D;padding:8px 8px;}
</style>
<#if !login>
	<#-- 未登录状态下需要显示快速登录层 S -->
	<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/login/rapidLogin.js" type="text/javascript"></script>
</#if>
</head>
<body>
<img src="http://pic.lvmama.com/img/new_v/newBtn_bg.gif" style="display: none;"/>
<br/>
<@s.set var="pageMark" value="'productDetail'" />
	<#include "/common/setKeywor.ftl">  
	<input type="hidden" id="checkDate" value="${Parameters.checkDate?if_exists?string}"> 
	<div id="warp">            
	    <div class="main">
	        <@s.if test="travelTips != null">
	        <br/>
	        <p align="center"><font size="16"><@s.property value="travelTips.tipsName"/></font></p>
	        <br/>
			<@s.property escape="false" value="travelTips.content"/>
			</@s.if>	
			<br/>
			<p align="center"><font size="5">
			<@s.if test="affix.path != null">
			<a href="http://pic.lvmama.com/pics/<@s.property value="affix.path"/>" target="_blank">下载</a>
			</@s.if>
			</font></p>
	    </div><!--main end-->
	</div><!--warp end-->
	<#include "/common/footer.ftl">
</html>
