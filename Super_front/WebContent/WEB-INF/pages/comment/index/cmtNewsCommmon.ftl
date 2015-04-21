    <!--小驴说事开始-->
    <div class="c_item_a fr w_item_a">
      <h3 class="c_title cbd_b"><strong>城市自游志</strong><small>最权威的城市出游指南 告诉你该怎么玩……</small></h3>
      <div class="pd10">
       <@s.if test="recommendInfoMap != null">
	      <@s.iterator value="recommendInfoMap.get('${station}_cmtIndexPageYouJi')" status="sts">
		      <@s.if test="#sts.index==0">
			        <a href="${url}" target="_blank">
			        	<img src="${imgUrl}" width="310" height="205">
			        </a>
			        <h3 class="tc"><b class="cxh_ico2 f12">当期</b>
			        <a  target="_blank" href="${url}"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(100,title)" /></a></h3>
			        <p>${remark}</p>
		       </@s.if>
	       </@s.iterator>
	    </@s.if>
        <div class="qikan_box clearfix">
          <ul class="list_qikan">
            <@s.if test="recommendInfoMap != null">
	            <@s.iterator value="recommendInfoMap.get('${station}_cmtIndexPageYouJi')" status="sts">
		            <@s.if test="#sts.index!=0">
		            	<li><span class="cxh_ico3">第<@s.property value="${bakWord1}"/>期</span>
			            	<a href="${url}" target="_blank"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(100,title)" /></a>
		            	</li>
		            </@s.if>
	       		</@s.iterator>
       		 </@s.if>
          </ul>
          <a class="view_more clr fr" href="http://www.lvmama.com/zt/" target="_blank">更多&gt;&gt;</a></div>
      </div>
    </div>
    <div class="hr_a"></div>
