<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>退款提现-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-store">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/account/store.do">存款账户</a>
				&gt;
				<a class="current">退款提现</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit store_del-edit">
					<div class="ui-box-title"><h3>退款提现</h3></div>
			        <!-- 操作步骤3>> -->
			        <div class="set-step set-step2 clearfix">
			        	<ul class="hor">
				            <li class="s-step1"><span class="s-num">1</span>验证信息</li>
			    	        <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>等待审核</li>
			            </ul>
			        </div>
			        <div class="msg-success"><span class="msg-ico01"></span>
			           <h3>恭喜！退款提现申请成功！请等待审核。</h3>
			           <p>我们将尽快进行处理，请耐心等待，如有疑问，可拨打<span class="lv-c1">10106060</span>进行咨询。</p>
			        </div>
			        <!-- <<操作步骤3 -->
				</div>
				<!-- <<退款提现 -->
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<script>
		cmCreatePageviewTag("提交提现", "D1002", null, null);
	</script>
	
</body>
</html>