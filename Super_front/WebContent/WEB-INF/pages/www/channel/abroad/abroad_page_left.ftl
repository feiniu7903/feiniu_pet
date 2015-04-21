 <div class="aside">
        <div class="side-box cside1 hide">
            <div class="border">
                <div class="head">
                    <h4>推荐主题</h4>
                </div>
                <div class="content">
                    <ul class="themes">
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_PAGE_LEFT_SUBJECT')" status="st">
                        <li><a target="_blank"  href="${url?if_exists}"><i class="xicon theme_icon${st.index+1}"></i> ${title?if_exists} <span>
 	                        <@s.if test="remark!=null && remark.length()>9">
							<@s.property value="remark.substring(0,9)" escape="false"/>
							</@s.if><@s.else>${remark?if_exists}
							</@s.else>
                        </span></a></li>
                        </@s.iterator>  
                    </ul>
                    <div class="more_themes JS_slideDown">
                        <a class="view_more" href="javascript:;">更多主题<i class="css_arrow"></i></a>
                        <div id="themes_list" class="themes_list">
                        <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_PAGE_LEFT_SUBJECT_MORE')" status="st">
                            <a target="_blank"  href="${url?if_exists}">${title?if_exists}</a>
                        </@s.iterator>  
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        
        <div class="side-box border cside2">
            <div class="head">
                <h4>热门景点目的地</h4>
            </div>
            <div class="content">
                <dl class="hotcity">
                 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_PAGE_LEFT_HEAT_PLACE')" status="st">
                	<dt>${title?if_exists}</dt>
                	<dd>
                	    <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="st">
                         <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                        </@s.iterator>
                    </dd>
                 </@s.iterator>
                </dl>
            </div>
        </div>
        
 
        <div class="side-box border cside3"> 
			<div class="head"> 
			<h4>热门推荐</h4> 
			</div> 
			<div class="content"> 
				<ul class="recommend"> 
					<@s.iterator value="hotRecommendXianShiTeMaiSet" status="st"> 
					<li><a target="_blank" href="${url?if_exists}">${title?if_exists}</a><a class="tagsback"  ><em>驴妈妈价</em><i>${bakWord3?if_exists}元</i></a></li> 
					</@s.iterator> 
					<@s.iterator value="hotRecommendReXiaoPaiHangSet" status="st"> 
					<li><a target="_blank" href="${url?if_exists}">${title?if_exists}</a><a class="tagsback"  ><em>销量</em><i>${bakWord2?if_exists}</i></a></li> 
					</@s.iterator> 
				</ul> 
			</div> 
		</div>
		 <!-- 广告位左侧-->
        <div class="side-box sidead" data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|abroad_2013|abroad_2013_bottum01#200px#80px"> 
		</div>

     </div>