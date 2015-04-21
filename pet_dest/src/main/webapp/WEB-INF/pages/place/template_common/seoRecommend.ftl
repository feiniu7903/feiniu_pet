
<@s.if test="seoDestList.size()>0">          
<div class="dstnt_r_bottom">
	<h3>热门旅游目的地推荐</h3>
	<ul class="dstnt_rb_ul">
	<@s.iterator value="seoDestList" status="seo" >
		<li><a href='http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>'><@s.property value="name"/>旅游</a></li>
	</@s.iterator>
	</ul>
</div>
</@s.if>