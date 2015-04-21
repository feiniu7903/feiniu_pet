<#if fromType?? && fromType!="">
<div class="msg-warn"><span class="msg-ico02"></span>
	<#if fromType =="ticket">
		<h3>没有找到&ldquo;${searchvo.keyword}&rdquo;相关的景点门票。</h3>
	<#elseif fromType =="hotel">
		<h3>没有找到&ldquo;${searchvo.keyword}&rdquo;相关的特色酒店。</h3>
	<#elseif fromType =="route">
		<h3>没有找到&ldquo;${searchvo.keyword}&rdquo;相关的度假产品。</h3>
	<#elseif fromType =="freetour">
		<h3>没有找到&ldquo;${searchvo.keyword}&rdquo;相关的自由行（景点+酒店）产品。</h3>
	<#elseif fromType =="freelong">
		<h3>没有找到&ldquo;${searchvo.keyword}&rdquo;相关的自由行（景点+酒店）产品。</h3>
	<#elseif fromType =="group">
		<h3>没有找到&ldquo;${searchvo.keyword}&rdquo;相关的跟团游产品。</h3>
	<#elseif fromType =="around">
		<h3>没有找到&ldquo;${searchvo.keyword}&rdquo;相关的周边/当地跟团游产品。</h3>
	</#if>
    <p>以下是驴妈妈推荐的产品，可能会给您带来惊喜哦！</p>
</div><!-- // div .msg-warn -->
</#if>