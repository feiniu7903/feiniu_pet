<!-- 搜素筛选\\ -->
<div class="search-filter">
	<div class="filter-info clearfix"><span class="result-info">共找到<i>${pageConfig.totalResultSize}</i>条结果。</span>
    	<dl id="your-choices" class="your-choices">
             <#if searchvo.startPrice?? || searchvo.endPrice?? 
             		|| searchvo.keyword2!="" || searchvo.city??
             		|| searchvo.hotelType?? ||searchvo.hotelLocation??
        	        || searchvo.landTraffic?? || searchvo.landFeature?? 
        	        || searchvo.visitDay??||searchvo.traffic?? ||searchvo.playBrand?? || searchvo.tag??>
                    <dt>您已选择：</dt>
                    <#if searchvo.city??>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="A"  remove=true/>.html"><h6>包含地区：</h6>${searchvo.city}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.hotelType??>
                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="J"  remove=true/>.html"><h6>酒店类型：</h6>${searchvo.hotelType}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.hotelLocation??>
                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="X"  remove=true/>.html"><h6>酒店位置：</h6>${searchvo.hotelLocation}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.landTraffic??>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="Z"  remove=true/>.html"><h6>上岛交通：</h6>${searchvo.landTraffic}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.landFeature??>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="Y"  remove=true/>.html"><h6>岛屿特色：</h6>${searchvo.landFeature}<span class="icon-close"></span></a></dd>
                    </#if>
                     <#if searchvo.visitDay??>
                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="I"  remove=true/>.html"><h6>游玩天数：</h6>${searchvo.visitDay}<span class="icon-close"></span></a></dd>
                    </#if>
                     <#if searchvo.traffic??>
                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="H"  remove=true/>.html"><h6>往返交通：</h6>${searchvo.traffic}<span class="icon-close"></span></a></dd>
                    </#if>
                     <#if searchvo.playBrand??>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="B"  remove=true/>.html"><h6>特色品牌：</h6>${searchvo.playBrand}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.keyword2!=""> 
						<dd><a href="${base_url}<@fp filter="${filterStr}" type="Q" remove=true/>.html"><h6>关键词：</h6>${searchvo.keyword2}<span class="icon-close"></span></a></dd> 
					</#if>
					<#if searchvo.startPrice?? || searchvo.endPrice??> 
						<dd><a href="${base_url}<@fp filter="${filterStr}" type="K,O" remove=true repeat=true/>.html"><h6>价格：</h6><#if searchvo.endPrice=="">${searchvo.startPrice}以上<#elseif searchvo.startPrice=="">${searchvo.endPrice}以下<#else>${searchvo.startPrice}-${searchvo.endPrice}</#if><span class="icon-close"></span></a></dd> 
					</#if>
					<#if searchvo.tag??> 
						<dd><a href="${base_url}<@fp filter="${filterStr}" type="T" remove=true/>.html"><h6>标签：</h6>${searchvo.tag}<span class="icon-close"></span></a></dd> 
					</#if>
                    <dd class="tags-empty"><a rel="nofollow" href="${base_url}.html">清空全部</a></dd>
                    </#if>
        </dl>
    </div>
    <#assign cities_show = cities?? && cities?size &gt; 0 &&!searchvo.city?? />
    <#assign hotelTypes_show = hotelTypes?? &&  hotelTypes?size &gt; 0 &&!searchvo.hotelType?? />
    <#assign hotelLocations_show = hotelLocations?? && hotelLocations?size &gt; 0 && !searchvo.hotelLocation?? />
    <#assign landTraffics_show = landTraffics?? && landTraffics?size &gt; 0 &&!searchvo.landTraffic?? />
    <#assign landFeatures_show = landFeatures?? && landFeatures?size &gt; 0 &&!searchvo.landFeature?? />
    <#assign visitDays_show = visitDays?? && visitDays?size &gt; 0 &&!searchvo.visitDay?? />
    <#assign traffics_show = traffics?? && traffics?size &gt; 0 &&!searchvo.traffic?? />
    <#assign playBrands_show = playBrands?? && playBrands?size &gt; 0 &&!searchvo.playBrand?? />
    
    
    <#if cities_show || hotelTypes_show  || hotelLocations_show 
		    || landTraffics_show || landFeatures_show 
		    || visitDays_show || traffics_show || playBrands_show>
    <ul id="tags-list" class="filter-tags">
    	       <#if cities_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>包含地区：</span>
                    	<p>
	                    	<a class="s-tag <#if !searchvo.city??>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="A"  remove=true/>.html">全部</a>
	                    	
	                    	<#list cities?keys as key>
	                    		<a class="s-tag <#if searchvo.city=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="A" val="${key}"/>.html">${key}(${cities.get(key)})</a>
	                    	</#list>
                    	</p>
	                 </li>
                </#if>
				<#if hotelTypes_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>酒店类型：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag <#if !searchvo.hotelType??>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="J"  remove=true/>.html">全部</a>
		                    <#list hotelTypes?keys as key>
		                		<a rel="nofollow" class="s-tag <#if searchvo.hotelType=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="J" val="${key}"/>.html">${key}(${hotelTypes.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>  
				<#if hotelLocations_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>酒店位置：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag <#if !searchvo.hotelLocation??>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="X"  remove=true/>.html">全部</a>
		                    <#list hotelLocations?keys as key>
		                		<a rel="nofollow" class="s-tag <#if searchvo.hotelLocation=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="X" val="${key}"/>.html">${key}(${hotelLocations.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>  
				
			   <#if landTraffics_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>上岛交通：</span>
	                    <p>
		                    <a class="s-tag <#if !searchvo.landTraffic??>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="Z"  remove=true/>.html">全部</a>
		                    <#list landTraffics?keys as key>
		                		<a class="s-tag <#if searchvo.landTraffic=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="Z" val="${key}"/>.html">${key}(${landTraffics.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if> 
			  <#if landFeatures_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>岛屿特色：</span>
	                    <p>
		                    <a class="s-tag <#if !searchvo.landFeature??>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="Y"  remove=true/>.html">全部</a>
		                    <#list landFeatures?keys as key>
		                		<a class="s-tag <#if searchvo.landFeature=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="Y" val="${key}"/>.html">${key}(${landFeatures.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if> 
				<#if visitDays_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>游玩天数：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag <#if !searchvo.visitDay??>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="I"  remove=true/>.html">全部</a>
		                    <#list visitDays?keys as key>
		                		<a rel="nofollow" class="s-tag <#if searchvo.visitDay=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="I" val="${key}"/>.html">${key}天(${visitDays.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>
				<#if traffics_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>往返交通：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag <#if !searchvo.traffic??>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="H"  remove=true/>.html">全部</a>
		                    <#list traffics?keys as key>
		                		<a rel="nofollow" class="s-tag <#if searchvo.traffic=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="H" val="${key}"/>.html">${key}(${traffics.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if> 
	    		<#if playBrands_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>特色品牌：</span>
	                    <p>
		                    <a class="s-tag <#if !searchvo.playBrand??>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="B"  remove=true/>.html">全部</a>
		                    <#list playBrands?keys as key>
		                		<a class="s-tag <#if searchvo.playBrand=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="B" val="${key}"/>.html">${key}(${playBrands.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>  
     </ul>
     </#if>
</div><!-- //搜素筛选 -->
