<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的订单-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<link href="/style/invoice.css" rel="stylesheet" />	
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-order">
	<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
	<div class="lv-nav wrap">
		<p><a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a> &gt; 
		<a href="http://www.lvmama.com/myspace/order.do">我的订单</a>&gt;
		<a class="current">开具发票</a>
		</p>
	</div>
	<div class="wrap ui-content lv-bd">
		<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
		<div class="lv-content">
			<div class="ui-box mod-plist">
				<div class="ui-box-title">
			        <h3>开具发票</h3>
			    </div>
			    <div class="invoice_success_content">
			        <span class="msg-ico01 invoice_success_img"></span>
			        <div class="invoice_success_note">
			        <#if message!=null>
			        	<h3 class="invoice_success">${message}</h3>
			        <#else>
			        	<h3 class="invoice_success">您的开票申请已提交成功，请您耐心等待！</h3>
				        <p>工作人员会在5-7个工作日内处理完毕，您可以在
				        <#list orderIdList as id>
				        	<#if id_has_next><span class="color_blue"><a href="${base}/myspace/order_detail.do?orderId=${id}#invoiceMaodian">${id};</a></span></#if>
				        	<#if !id_has_next><span class="color_blue"><a href="${base}/myspace/order_detail.do?orderId=${id}#invoiceMaodian">${id}</a></span></#if>
				        </#list>查看发票详细信息。
				        </p>
			        </#if>
			        
			        </div>
			    </div>
			</div>
		</div><!-- //div .lv-content-->
	</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<script>
		cmCreatePageviewTag("保存发票信息", "D1003", null, null);
	</script>
</body>
</html>