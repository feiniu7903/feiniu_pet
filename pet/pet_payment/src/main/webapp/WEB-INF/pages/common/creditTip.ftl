<#-- 页面保障或信用贴士 -->
<#macro creditTip productName="" productType="TICKET">
	<div class="order-title w-980">
		<em>预订：</em><h1>${productName}</h1>
		<div class="o-tips">
			<#if productType=="TICKET"><a class="btn-promise">优惠承诺</a></#if>
			<#if productType=="TICKET"><a class="btn-ensure">入园保障</a></#if>
			<a class="btn-safepay">放心支付</a>
	
			<div class="btn-content"><div>如您在驴妈妈的订购价格高于景点的当天门市价，我们无条件退还差价。</div></div>
			<div class="btn-content"><div>只要您通过驴妈妈旅游网预订了景点门票，并收到了驴妈妈确认短信，驴妈妈就会保障您按照驴妈妈优惠价顺利入园游玩。</div></div>
			<div class="btn-content"><div>可信网站示范单位，AAA级互联网信用，支付放心无忧。</div></div>
	    </div>
	</div>
</#macro>
