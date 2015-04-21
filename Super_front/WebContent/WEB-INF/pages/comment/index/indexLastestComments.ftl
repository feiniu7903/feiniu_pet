 
 <div class="aside_box c_shadow">
      <h4 class="ca_title c_iconbg reflect">最新点评</h4>
      <ul class="c_new_list clearfix last_child">
     	 <@s.iterator value="lastestCmtList">
	        <li>
	          <h5><span class="fl"><#if userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(8,userNameExp)" /><#else>匿名</#if> 点评
	          	<#if placeId ??>
	          	  <b> <a href="http://www.lvmama.com/comment/${placeId}-1"  target="_blank"><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(16,placeName)" /></a>
	          	</#if>
	          	<#if productId ??>
	          	  <b> <a href="http://www.lvmama.com/product/${productId}/comment"  target="_blank">当前产品</a>
	          	</#if>
	          	  </b></span><span class="fr">${simpleCreatedTime}</span></h5>
	              <p><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(40,contentDelEnter)" /></p>
	        </li>
		 </@s.iterator>
      </ul>
    </div>
