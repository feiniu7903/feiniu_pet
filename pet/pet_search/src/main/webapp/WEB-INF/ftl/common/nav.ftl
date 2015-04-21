<div class="search-result-nav">
	<ul class="search-nav hor clearfix">
		<#if type =="route" || type =="around" || type == "freelong" || type == "freetour" || type =="group" || tc.route gt 0>
    	<li class="item-all search-nav-item <#if info_item_selected_route??>search-nav-item-selected</#if>">
    		<a href="http://www.lvmama.com/search/route/${fromDest}-${keyword}.html"><b>全部线路</b></a>
    	</li>
    	</#if>
    	<#if type =="group" || tc.group gt 0>
    	<li class="search-nav-item <#if info_item_selected_group??>search-nav-item-selected</#if>">
    		<a href="http://www.lvmama.com/search/group/${fromDest}-${keyword}.html"><b>跟团游</b><small>(${tc.group})</small></a>
    	</li>
    	</#if>
    	<#if type =="freelong" || tc.freelong gt 0>
        <li class="search-nav-item <#if info_item_selected_freelong??>search-nav-item-selected</#if>">
        	<a href="http://www.lvmama.com/search/freelong/${fromDest}-${keyword}.html"><b>自由行</b>[机票+酒店]<small>(${tc.freelong})</small></a>
        </li>
        </#if>
        <#if type =="freetour" || tc.freetour gt 0>
        <li class="search-nav-item <#if info_item_selected_freetour??>search-nav-item-selected</#if>">
        	<a href="http://www.lvmama.com/search/freetour/${fromDest}-${keyword}.html"><b>自由行</b>[景点+酒店]<small>(${tc.freetour})</small></a><i class="icon-hot"></i>
        </li>
        </#if>
        <#if type =="around" || tc.around gt 0>
        <li class="search-nav-item <#if info_item_selected_around??>search-nav-item-selected</#if>" >
        	<#if tc.keywordIsFromDest>
        		<a href="http://www.lvmama.com/search/around/${fromDest}-${keyword}.html"><b><#if place?? && place.stage=="1">${searchvo.keyword}</#if>当地跟团游</b><small>(${tc.around})</small></a>
        	<#else>
        		<a href="http://www.lvmama.com/search/around/${fromDest}-${keyword}.html"><b>周边跟团游</b><small>(${tc.around})</small></a>
        	</#if>
        </li>
        </#if>
        <#if type =="ticket" || tc.ticket gt 0>
        <li class="search-nav-item search-nav-itemb <#if info_item_selected_ticket??>search-nav-item-selected</#if>">
        	<a href="http://www.lvmama.com/search/ticket/${fromDest}-${keyword}.html"><b>景点门票</b><small>(${tc.ticket})</small></a></i>
        </li>
        </#if>
        <#if type =="hotel" || tc.hotel gt 0> 
        <li class="search-nav-item search-nav-itemb <#if info_item_selected_hotel??>search-nav-item-selected</#if>">
        	<a href="http://www.lvmama.com/search/hotel/${keyword}.html"><b>酒店</b><small>(${tc.hotel})</small></a></i>
        </li>
        </#if>
    </ul>
</div>
