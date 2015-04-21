     <!--点评招募开始-->
    <div class="aside_box aside_box1 c_shadow">
      <h4 class="ca_title c_iconbg">
      		<span class="reflect">点评招募</span><small>(每个景区前十条点评奖励<b>80</b><a href="http://www.lvmama.com/public/help_center_146" target="_blank">积分</a>)</small>
      </h4>
      <ul class="list_chot JS_imgmo_menu lh24">
      	<@s.iterator value="recommendPlaceList" id="cmtRecommendPlace" status="st">
	        <li class="selected"><span class="fl title"><i class="front3">·</i>
		        <a href="http://www.lvmama.com/dest/${pinYinUrl}"><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(placeName,12)" /></a>
		        	</span><a class="fr" href="/comment/writeComment/fillComment.do?placeId=${placeId}" rel="nofollow">我要点评
		        </a>
	            <p class="c_pt_pic"> 
		            <a class="img_auto" href="http://www.lvmama.com/dest/${pinYinUrl}">
			            <@s.if test="#st.index==0"> 
			           			<img class="scrollLoading" src="${placeLargeImage}" original="${placeLargeImage}" title="${placeName}">
			           	</@s.if>
			           	<@s.else>
			           			<img class="scrollLoading"  src="" original="${placeLargeImage}" title="${placeName}">
			           </@s.else>
			            <span class="c_t_bg"></span> <em class="c_p_tit f14">${placeName}</em>
		            </a>
	            </p>
	        </li>
        </@s.iterator>
      </ul>
    </div>
    
    
    
