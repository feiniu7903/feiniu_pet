<@s.iterator value="cmList" status="cmi">
<#if cmi.first>
<div class="search_pp_calendar_box" month="${month}" year="${year}" needReload="false"  style="display:block;">
<#else>
<div class="search_pp_calendar_box" month="${month}">
</#if>
             <h2 class="search_pp_calendar_tit">出行日价格表</h2>
             <div class="search_pp_calendar_m">
                <@s.if test='flagNextMonth >= 0'>
	                <div class="search_pp_cal_nevm">
	                    <span class="search_pp_cal_nevm_icon"></span>
	                    <span class="search_pp_cal_nevm_text">${month}月</span>
	                </div>
		            <div class="search_pp_cal_nextm">
		            	<@s.if test='month==12'>
		               		<span class="search_pp_cal_nextm_text">1月</span>
		               </@s.if>
		               <@s.else>
		                	<span class="search_pp_cal_nextm_text">${month+1}月</span>
		               </@s.else>
		               <span <@s.if test="#cmi.last">class="search_pp_cal_nextm_no_icon"</@s.if><@s.else>class="search_pp_cal_nextm_icon" onclick="loadPrice(this,${month+1})"</@s.else>></span>
		            </div>
		            </@s.if>
	            <@s.else>
	            	<div class="search_pp_cal_nevm">
                    	<span class="search_pp_cal_nevm_icon"></span>
	                </div>
		            <div class="search_pp_cal_nextm">
		               <span class="search_pp_cal_nevm_center">${month}月</span>
		                <span <@s.if test="#cmi.last">class="search_pp_cal_nextm_no_icon"</@s.if><@s.else>class="search_pp_cal_nextm_icon" onclick="loadPrice(this,${month})"</@s.else>></span>
		            </div>
	            </@s.else>  
             </div><!--mounth-->
             <ul class="search_pp_calendar_t">
                 <li>星期日</li>
                 <li>星期一</li>
                 <li>星期二</li>
                 <li>星期三</li>
                 <li>星期四</li>
                 <li>星期五</li>
                 <li>星期六</li>
             </ul>
             <#assign monthColor=1>
             <#assign td_pos=0>
             <@s.iterator value="calendar" status="cal" var="ca1">
             <ul class="search_pp_calendar_d">
             <@s.iterator value="#ca1" status="cal2" var="ca2">
				 <li>

                    <div  <@s.if test="#ca2.sellPriceFloat >0">
				 	 	data-sellbale="true" class="search_pp_calendar_d_box month_${monthColor}"
				 	 </@s.if><@s.else>class="search_pp_calendar_d_box_no_hover month_${monthColor}" </@s.else>>              
                       <span class="search_pp_calendar_day"><@s.date name="#ca2.specDate" format="dd"/></span>
                       
		   				 <@s.if test="#ca2.sellPriceFloat >0">
			   				<span class="search_pp_calendar_price1"></span>
			   				<span class="search_pp_calendar_price">&yen;<@s.property value="#ca2.priceInt"/><@s.if test="#action.hasProdSelfPack()">起</@s.if></span>
		   				 </@s.if>
						 <span style="display:none;" class="search_pp_calendar_day_date"><@s.date name="#ca2.specDate" format="yyyy-MM-dd"/></span>
                    </div>
                 </li>
                 <#assign td_pos=td_pos+1>
                 <#assign day><@s.date name="#ca2.specDate" format="dd"/></#assign>
                 <#if day==1&&td_pos>1><#assign monthColor=monthColor+1/></#if>
			</@s.iterator>
             </ul>
             </@s.iterator>
         </div>
</@s.iterator>