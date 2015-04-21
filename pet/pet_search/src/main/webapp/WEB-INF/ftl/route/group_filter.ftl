<!-- 搜素筛选\\ -->
<div class="search-filter">
	<div class="filter-info clearfix"><span class="result-info">共找到<i>${pageConfig.totalResultSize}</i>条结果。</span>
    	<dl id="your-choices" class="your-choices">
             <#if searchvo.startPrice!="" || searchvo.endPrice!="" || searchvo.keyword2!="" || searchvo.city?exists || searchvo.playLine?exists || searchvo.scenicPlace?exists
        		||searchvo.subject?exists||searchvo.traffic?exists||searchvo.hotelType?exists||
        		 searchvo.visitDay?exists||searchvo.playFeature?exists>
                    <dt>您已选择：</dt>
                    <#if searchvo.city?exists>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="A"  remove=true/>.html"><h6>包含地区：</h6>${searchvo.city}<span class="icon-close"></span></a></dd>
                    </#if>
                     <#if searchvo.playLine?exists>
                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="L"  remove=true/>.html"><h6>游玩线路：</h6>${searchvo.playLine}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.scenicPlace?exists>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="D"  remove=true/>.html"><h6>景点：</h6>${searchvo.scenicPlace}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.subject?exists>
                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="C"  remove=true/>.html"><h6>主题：</h6>${searchvo.subject}<span class="icon-close"></span></a></dd>
                    </#if>
                     <#if searchvo.traffic?exists>
                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="H"  remove=true/>.html"><h6>往返交通：</h6>${searchvo.traffic}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.hotelType?exists>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="J"  remove=true/>.html"><h6>酒店类型：</h6>${searchvo.hotelType}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.visitDay?exists>
                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="I"  remove=true/>.html"><h6>游玩天数：</h6>${searchvo.visitDay}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.playFeature?exists>
                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="G"  remove=true/>.html"><h6>游玩特色：</h6>${searchvo.playFeature}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.keyword2!=""> 
						<dd><a href="${base_url}<@fp filter="${filterStr}" type="Q" remove=true/>.html"><h6>关键词：</h6>${searchvo.keyword2}<span class="icon-close"></span></a></dd> 
					</#if>
					<#if searchvo.startPrice!="" || searchvo.endPrice!=""> 
						<dd><a href="${base_url}<@fp filter="${filterStr}" type="K,O" remove=true repeat=true/>.html"><h6>价格：</h6><#if searchvo.endPrice=="">${searchvo.startPrice}以上<#elseif searchvo.startPrice=="">${searchvo.endPrice}以下<#else>${searchvo.startPrice}-${searchvo.endPrice}</#if><span class="icon-close"></span></a></dd> 
					</#if>
                    <dd class="tags-empty"><a rel="nofollow" href="${base_url}.html">清空全部</a></dd>
                    </#if>
        </dl>
    </div>
    <ul id="tags-list" class="filter-tags">
    	       <#if cities?? && cities?size &gt; 0 &&!searchvo.city??>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>包含地区：</span>
                    	<p>
	                    	<a class="s-tag <#if !searchvo.city?exists>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="A"  remove=true/>.html">全部</a>
	                    	<#list cities?keys as key>
	                    		<a class="s-tag <#if searchvo.city=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="A" val="${key}"/>.html">${key}(${cities.get(key)})</a>
	                    	</#list>
                    	</p>
	                 </li>
                </#if>
                <#if playLines?? && playLines?size &gt; 0 &&!searchvo.playLine??>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>游玩路线：</span>
                    	<p>
	                    	<a rel="nofollow" class="s-tag <#if !searchvo.playLine?exists>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="L"  remove=true/>.html">全部</a>
	                    	<#list playLines?keys as key>
	                    		<a rel="nofollow" class="s-tag <#if searchvo.playLine=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="L" val="${key}"/>.html">${key}(${playLines.get(key)})</a>
	                    	</#list>
                    	</p>
	                 </li>
                </#if>
                <#if scenicPlaceMap?? && scenicPlaceMap?size &gt; 0 &&!searchvo.scenicPlace??>
	                <li>
	                	<a href="javascript:void(0);" class="view-more" rel="nofollow">更多<i class="arrow"></i></a>
	                    <span>景　　点：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag <#if !searchvo.scenicPlace?exists>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="D"  remove=true/>.html">全部</a>
		                    <#list scenicPlaceMap?keys as key>
	                    		<a rel="nofollow" class="s-tag <#if searchvo.scenicPlace=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="D" val="${key}"/>.html">${key}(${scenicPlaceMap.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>     
                <#if subjects?? &&  subjects?size &gt; 0 &&!searchvo.subject??>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>主　　题：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag <#if !searchvo.subject?exists>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="C"  remove=true/>.html">全部</a>
		                    <#list subjects?keys as key>
		                		<a rel="nofollow" class="s-tag <#if searchvo.subject=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="C" val="${key}"/>.html">${key}(${subjects.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>    
				
				<#if traffics?? && traffics?size &gt; 0 &&!searchvo.traffic??>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>往返交通：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag <#if !searchvo.traffic?exists>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="H"  remove=true/>.html">全部</a>
		                    <#list traffics?keys as key>
		                		<a rel="nofollow" class="s-tag <#if searchvo.traffic=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="H" val="${key}"/>.html">${key}(${traffics.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if> 
				<#if hotelTypes?? && hotelTypes?size &gt; 0 &&!searchvo.hotelType??>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>酒店类型：</span>
	                    <p>
		                    <a class="s-tag <#if !searchvo.hotelType?exists>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="J"  remove=true/>.html">全部</a>
		                    <#list hotelTypes?keys as key>
		                		<a class="s-tag <#if searchvo.hotelType=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="J" val="${key}"/>.html">${key}(${hotelTypes.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>  
				<#if hotelTypes?? && hotelTypes?size &gt; 0 &&!searchvo.hotelType??>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>酒店类型：</span>
	                    <p>
		                    <a class="s-tag <#if !searchvo.hotelType?exists>selected</#if>" href="#">全部</a>
		                    <#list hotelTypes?keys as key>
		                		<a class="s-tag <#if searchvo.hotelType=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="X" val="${key}"/>.html">${key}(${hotelTypes.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>  
				<#if visitDays?? && visitDays?size &gt; 0 &&!searchvo.visitDay??>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>游玩天数：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag <#if !searchvo.visitDay?exists>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="I"  remove=true/>.html">全部</a>
		                    <#list visitDays?keys as key>
		                		<a rel="nofollow" class="s-tag <#if searchvo.visitDay=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="I" val="${key}"/>.html">${key}天(${visitDays.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>
	    		<#if playFeatures?? && playFeatures?size &gt; 0 &&!searchvo.playFeature??>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>游玩特色：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag <#if !searchvo.playFeature?exists>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="G"  remove=true/>.html">全部</a>
		                    <#list playFeatures?keys as key>
		                		<a rel="nofollow" class="s-tag <#if searchvo.playFeature=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="G" val="${key}"/>.html">${key}(${playFeatures.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>  
     </ul>
</div><!-- //搜素筛选 -->
