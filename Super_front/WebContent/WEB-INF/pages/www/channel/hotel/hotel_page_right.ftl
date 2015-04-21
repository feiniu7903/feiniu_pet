 <div class="col-w-small">
		      <div id="slides" class="slide-box slide-hotel">
			    <ul class="slide-content" id="js-slides">
				    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|hotel_2013|hotel_2013_focus01_new#680px#260px" ></li>
				    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|hotel_2013|hotel_2013_focus02#680px#260px" ></li>
				    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|hotel_2013|hotel_2013_focus03#680px#260px" ></li>
				    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|hotel_2013|hotel_2013_focus04#680px#260px" ></li>
				    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|hotel_2013|hotel_2013_focus05#680px#260px" ></li>
				    <li data-type="ad" data-adsrc="http://lvmamim.allyes.com/main/s?db=lvmamim&border=0&local=yes&kv=key|${fromPlaceCode}&user=lvmama_2013|hotel_2013|hotel_2013_focus06#680px#260px" ></li>
				</ul> 
 			 <ul class="slide-nav"><@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsTextProxy(115)"/></ul>
 		 	</div> <!-- //.slide-box -->
        <div class="hr_d"></div>
        
        <div class="cbox hotel-cbox1">
            <div class="ctitle">
                <h3>国内景点酒店</h3>
                <ul class="tabnav JS_tabnav">
                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_PAGE_RIGHT_NATIONAL_HOTEL')" status="st">
                	<li <@s.if test="#st.index==0">class="selected"</@s.if>>
                	<a paramId="tab${st.count}"  paramDataCode="${bakWord1?if_exists}" paramDataSearchName="${title?if_exists}" >${title?if_exists}<i class="css_arrow"></i></a></li>
                	</@s.iterator> 
                </ul>
             </div>
            <div class="content JS_tabsbox">
              <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_PAGE_RIGHT_NATIONAL_HOTEL')" status="st">
                 <@s.if test="#st.count==1"> 
	                <div class="tabcon selected" id="tab${st.count}">
	                    <ul class="hotel-list">
	                       <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="st">
	                        <li>
	                            <a target="_blank" class="text-cover" href="${url?if_exists}">
	                                <img src="${imgUrl?if_exists}" width="200" height="130" alt="" />
	                                <span></span>
	                                <i>${title?if_exists}</i>
	                            </a>
	                            <p class="rank">
	                                <dfn>&yen;<i>${memberPrice?if_exists}</i>起</dfn>
 	                                <#include "/WEB-INF/pages/www/channel/hotel/star.ftl">
	                                
	                            </p>
	                            <p class="info"><@s.if test="remark!=null && remark.length()>31"><@s.property value="remark.substring(0,31)" escape="false"/>...
									</@s.if><@s.else>${remark?if_exists}</@s.else></p>
	                            <p class="local">
	                                <a target="_blank" class="link-more" href="http://www.lvmama.com/search/hotel/${fromPlaceName?if_exists}-${bakWord2?if_exists}.html">更多酒店 &raquo;</a>
	                                <span class="icon-local"></span>${bakWord2?if_exists}
	                            </p>
	                        </li>
	                        </@s.iterator> 
						</ul>
	                    <p class="link-more"><a target="_blank" href="http://www.lvmama.com/search/hotel/${fromPlaceName?if_exists}-${title?if_exists}.html">— — 查看${title?if_exists}更多 — —</a></p>
	                </div>
                </@s.if>
	            <@s.else>
	                <div class="tabcon" id="tab${st.count}">
	                </div>
	            </@s.else>
              </@s.iterator> 
            </div>
        </div> <!-- //国内景点酒店 -->
        
        
        <div class="cbox hotel-cbox2">
            <div class="ctitle">
                <h3>境外景点酒店</h3>
                <ul class="tabnav JS_tabnav">
                   <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_PAGE_RIGHT_ABROAD_HOTEL')" status="st">
                	<li <@s.if test="#st.index==0">class="selected"</@s.if>>
                	<a paramId2="tab2_${st.count}" paramDataCode2="${bakWord1?if_exists}" paramDataSearchName2="${title?if_exists}" paramBakWord2="${bakWord2?if_exists}"  paramBakWord3="${bakWord3?if_exists}">${title?if_exists}<i class="css_arrow"></i></a></li>
                	</@s.iterator> 
                </ul>
             </div>
            <div class="content JS_tabsbox">
               <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_PAGE_RIGHT_ABROAD_HOTEL')" status="st">
               <@s.if test="#st.count==1">
                <div class="tabcon selected" id="tab2_${st.count}">
                    <ul class="hotel-list">
                       <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="st">
                        <li>
                            <a target="_blank" class="text-cover" href="${url?if_exists}">
                                <img src="${imgUrl?if_exists}" width="200" height="130" alt="" />
                                <span></span>
                                <i>${title?if_exists}</i>
                            </a>
                            <p class="rank">
                                <dfn>&yen;<i>${bakWord4?if_exists}</i>起</dfn>
                               <#include "/WEB-INF/pages/www/channel/hotel/star2.ftl">                       
                             </p>
                            <p class="info"><@s.if test="remark!=null && remark.length()>31">
							<@s.property value="remark.substring(0,31)" escape="false"/>...
							</@s.if><@s.else>${remark?if_exists}
							</@s.else></p>
                            <p class="local">
                                <a target="_blank" class="link-more" href="${bakWord2?if_exists}">更多酒店 &raquo;</a>
                                <span class="icon-local"></span>${bakWord3?if_exists}
                            </p>
                        </li>
                        </@s.iterator> 
                    <p class="link-more"><a target="_blank" href="${bakWord2?if_exists}">— — 查看${bakWord3?if_exists}更多 — —</a></p>
                </div>
                </@s.if>
                <@s.else>
	                <div class="tabcon" id="tab2_${st.count}">
 	                </div>
	            </@s.else>
              </@s.iterator> 
                 
            </div>
        </div> <!-- //境外景点酒店 -->
        
    </div> <!-- //.col-w-small -->