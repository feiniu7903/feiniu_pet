<!-- 搜素筛选\\ -->
<div class="search-filter">
	<div class="filter-info clearfix"><span class="result-info">共找到<i>${tc.route}</i>条结果。</span>
    	<dl id="your-choices" class="your-choices">
             <#if searchvo.startPrice?? || searchvo.endPrice??
             		|| searchvo.keyword2!="" || searchvo.city?? 
             		|| searchvo.subject?? ||  searchvo.visitDay??
             		|| searchvo.tag??>
                    <dt>您已选择：</dt>
                    <#if searchvo.city?exists>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="A"  remove=true/>.html"><h6>包含地区：</h6>${searchvo.city}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.subject?exists>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="C"  remove=true/>.html"><h6>主题：</h6>${searchvo.subject}<span class="icon-close"></span></a></dd>
                    </#if>
                    <#if searchvo.visitDay?exists>
                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="I" rel="nofollow" remove=true/>.html"><h6>游玩天数：</h6>${searchvo.visitDay}<span class="icon-close"></span></a></dd>
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
    <#assign cityes_show = cities?? && cities?size gt 0 &&!searchvo.city?? />
    <#assign subjects_show = subjects?? && subjects?size gt 0 &&!searchvo.subject?? />
    <#assign visitDays_show = visitDays?? && visitDays?size &gt; 0 &&!searchvo.visitDay?? />
    <#if cityes_show || subjects_show || visitDays_show >
    <ul id="tags-list" class="filter-tags">
    	       <#if cityes_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>包含地区：</span>
                    	<p>
	                    	<a rel="nofollow" class="s-tag <#if !searchvo.city?exists>selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="A"  remove=true/>.html">全部</a>
	                    	<#list cities?keys as key>
	                    		<a class="s-tag <#if searchvo.city=="${key}">selected</#if>" href="${base_url}<@fp filter="${filterStr}" type="A" val="${key}"/>.html">${key}(${cities.get(key)})</a>
	                    	</#list>
                    	</p>
	                 </li>
                </#if>
                <#if subjects_show>
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
				<#if visitDays_show>
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
     </ul>
     </#if>
</div><!-- //搜素筛选 -->
