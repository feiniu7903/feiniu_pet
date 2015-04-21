<div>
          <p class="dtl_r_hint" style="background:none">该产品只可在线预订，给您带来的不便敬请谅解！ 
							<br/>咨询热线：<span><@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else></span></p>
          <#include "/WEB-INF/pages/product/newdetail/top/recommendToFriend.ftl">      
          
          </div>   
          
<div style="display:none">
<dt id="date_type"><#if prodCProduct.prodProduct.productType == "HOTEL">入住日期<#else>游玩日期</#if>：</dt>
</div>
