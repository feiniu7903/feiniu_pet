        <!--小驴说事-->
        <div class="c_item_a c_hot_com">
         	<h3 class="c_title cbd_a"><strong>城市自游志</strong><small>最权威的城市出游指南 告诉你该怎么玩……</small></h3>
        <div>
         <@s.if test="recommendInfoMap != null">
		    <@s.iterator value="recommendInfoMap.get('${station}_cmtIndexPageYouJi')" status="sts">
			          <@s.if test="#sts.index==0">
					            <a href="${url}" target="_blank" class="c_pic_cen"><img src="${imgUrl}" width="280" height="200"></a>
					            <h3 class="tc"><b class="cxh_ico2 f12">当期</b>
						            <a target="_blank" href="${url}">
						            	<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(16,title)" />
						            </a>
					            </h3>
					            <p><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(96,remark)" /></p>
			          </@s.if>
		      </@s.iterator> 
		 </@s.if> 
         <div class="qikan_box clearfix">
              <ul class="list_qikan">
              <@s.if test="recommendInfoMap != null">
	              	<@s.iterator value="recommendInfoMap.get('${station}_cmtIndexPageYouJi')" status="sts">
			              <@s.if test="#sts.index!=0">
			                 <li><span class="cxh_ico3">第<@s.property value="${bakWord1}"/>期</span>
			                 	<a target="_blank" href="${url}">
			                 <@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(16,title)" /></a>
			                 </li>
			         	  </@s.if>
		       		</@s.iterator>
	       	  </@s.if> 
            </ul>
           <a class="view_more clr fr"  target="_blank" href="http://www.lvmama.com/zt/">更多&gt;&gt;</a></div>
        </div>
  	 </div>
