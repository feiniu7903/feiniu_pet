<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>分销系统</title>
<meta name="keywords" content="">
<meta name="description" content="">
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link rel="canonical" href="http://www.lvmama.com/search/ticket/79-79.html">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/fx/b2c_front/header-air.css,/styles/fx/b2c_front/core.css,/styles/fx/b2c_front/module.css,/styles/fx/b2c_front/ui-search.css,/styles/fx/b2c_front/ui-components.css,/styles/fx/b2c_front/calendar.css,/styles/fx/b2c_front/tip.css,/styles/fx/b2c_front/b2b_fx2.css">
<base target="_self">
</head>
<body>
	${httpInclude.include("/WEB-INF/pages/common/head.jsp")}
	<div class="fx_front_nav">
		<ul class="fx_front_navList">
			<li class="nav_li"><a href="/" title="景点门票">景点门票</a></li>
			<li><a href="/help/index" title="帮助中心">帮助中心</a></li>
		</ul>
	</div>
	<#include "search_box.ftl"/>
	<div class="content_Box ui-content wrap">
		<div class="search-main search-tickets">
			<!-- 门票搜索列表\\ -->
			<div id="list" class="search-result-box search-tickets-list">
				<#include "ticket_seach.ftl">
			</div>
			<!-- //门票搜索列表 -->
		</div>
		<div class="search-aside">
			<div class="search-aside side-setbox">
				<div class="aside-box lv-bd side-stuan clearfix tipsTsBox">
					<span class="tiptext tip-line"> <span
						class="tip-icon tip-icon-warning"> </span> 预订提示信息：
					</span>
					<p>
						尊敬的客户，只有通过此处预订才可以享受分销价优惠， <br /> 通过产品详情页进行预订不享受分销价优惠！
					</p>
					<!-- <span class="tiptext tip-line"> <span
						class="tip-icon tip-icon-warning"> </span> 返现说明：
					</span>
					<p>
						对于景区支付的商品，按驴妈妈销售价进行销售； <br /> 驴妈妈从景区获取到游客出游信息后，驴妈妈返现金给分销商； <br />
						现金返还到分销商的预存款账户中；
					</p> -->
				</div>
			</div>
		</div>
	</div>
	<form method="post" action="/search" id="searchVaForm">
		<input name="search" type="hidden" id="searchVa"/>
	</form>
	${httpInclude.include("/WEB-INF/pages/common/login.jsp")}
	<div id="ticketHiddenDiv" style="display:none">
	</div>
	${httpInclude.include("/WEB-INF/pages/common/footer.jsp")}
	<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/v3/plugins.js,/js/v3/app_fx.js"></script>
	<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/ob_common/ori-tooltip.js,/js/new_v/ob_common/ori-affix.js,/js/new_v/ob_search/search-custom.js,/js/v4/modules/pandora-dialog.js"></script>
	<script src="http://pic.lvmama.com/js/common/losc.js"></script>
	<script src="http://pic.lvmama.com/js/fx/b2c_front/pandora-calendar.js"></script>
	<script src="http://pic.lvmama.com/js/fx/b2c_front/fx_b2c.js"></script>
	<script src="http://pic.lvmama.com/js/fx/b2c_front/b2cfx_page.js"></script>
	<script src="/js/search.js"></script>
	<script src="/js/timePrice.js" type="text/javascript"></script>
	<script src="/js/jquery.form.js"></script>
	<script type="text/javascript">
	$.ajaxSetup({
		cache : false
	});
	</script>
</body>
</html>