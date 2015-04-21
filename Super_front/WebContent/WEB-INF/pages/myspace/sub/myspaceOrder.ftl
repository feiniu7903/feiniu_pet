<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的订单-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-order">
	<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
	<div class="lv-nav wrap">
		<p><a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a> &gt; <a class="current">我的订单</a></p>
	</div>
	<div class="wrap ui-content lv-bd">
		<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
		<div class="lv-content">
			<div class="ui-box mod-plist">
				<div class="ui-box-title">
			        <h3>我的订单</h3>
			    </div>
			    <#include "/WEB-INF/pages/myspace/sub/order/myspaceOrder_inner.ftl"/>
			    <div class="pages"><@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/></div>
			</div>
		</div><!-- //div .lv-content-->
	</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<script>
      cmCreatePageviewTag("我的订单", "D0001", null, null);
 	</script>
</body>
</html>