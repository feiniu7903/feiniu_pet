<div class="calendar_free" data-super-free="<@s.property value="#action.hasProdSelfPack()"/>" data-product-type="<@s.if test='prodProductBranch.prodProduct!=null'>${prodProductBranch.prodProduct.productType}</@s.if>" data-sub-product-type="<@s.if test='prodProductBranch.prodProduct!=null'>${prodProductBranch.prodProduct.subProductType}</@s.if>">
<@s.iterator value="cmList" status="cmi">
<div class="search_pp_calendar_box" <@s.if test='#cmi.index > 0'>style="display:none;"</@s.if>>
             <h2 class="search_pp_calendar_tit">出行日库存表</h2>
             <div class="search_pp_calendar_m">
             	<@s.if test='flagNextMonth >= 0'>
                <div class="search_pp_cal_nevm">
                    <span <@s.if test="#cmi.first">class="search_pp_cal_nevm_no_icon"</@s.if><@s.else>class="search_pp_cal_nevm_icon"</@s.else>></span>
                    <span class="search_pp_cal_nevm_text">${month}月</span>
                </div>
	            <div class="search_pp_cal_nextm">
	            	<@s.if test='month==12'>
	            			 <span class="search_pp_cal_nextm_text">1月</span>
		               </@s.if>
		               <@s.else>
		               		 <span class="search_pp_cal_nextm_text">${month+1}月</span>
		               </@s.else>
	                <span <@s.if test="#cmi.last">class="search_pp_cal_nextm_no_icon"</@s.if><@s.else>class="search_pp_cal_nextm_icon"</@s.else>></span>
	            </div>
	            </@s.if>
	            <@s.else>
	            	<div class="search_pp_cal_nevm">
                    <span <@s.if test="#cmi.first">class="search_pp_cal_nevm_no_icon"</@s.if><@s.else>class="search_pp_cal_nevm_icon"</@s.else>></span>
	                </div>
		            <div class="search_pp_cal_nextm">
		               <span class="search_pp_cal_nevm_center">${month}月</span>
		                <span <@s.if test="#cmi.last">class="search_pp_cal_nextm_no_icon"</@s.if><@s.else>class="search_pp_cal_nextm_icon"</@s.else>></span>
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

             	 <#assign td_pos=td_pos+1>
                 <#assign day><@s.date name="#ca2.specDate" format="dd"/></#assign>
                 <#if day=='01'&&td_pos gt 1><#assign monthColor=monthColor+1/></#if>
				  <li>
                                <div 
                                <@s.if test="!#ca2.isSellable(1)" >
                                	class="search_pp_calendar_d_box_no_hover month_${monthColor}" 
                                </@s.if>
                                <@s.elseif test="#ca2.dayStock==-1 || #ca2.dayStock==0 || #ca2.dayStock>0 || #ca2.onlyForLeave==true || #ca2.overSale=='true' ">
							 	 	<@s.if test="hasMultiJourney() && multiJourneyId == null">class="search_pp_calendar_d_box_no_hover month_${monthColor}"</@s.if>
							 	 	<@s.else>data-sellbale="true" class="search_pp_calendar_d_box month_${monthColor}"</@s.else>
							 	</@s.elseif>
							 	 <@s.else>class="search_pp_calendar_d_box_no_hover month_${monthColor}" </@s.else>>              
                                   <span class="search_pp_calendar_day"><@s.date name="#ca2.specDate" format="dd"/></span>
                                   <span style="display:none;" class="search_pp_calendar_day_date"><@s.date name="#ca2.specDate" format="yyyy-MM-dd"/></span>
                                   		<@s.if test="#ca2.dayStock==-1 || #ca2.dayStock==0 || #ca2.dayStock>0 || #ca2.onlyForLeave==true || #ca2.overSale=='true' ">		
                                   				<@s.if test="(!#ca2.isNeedResourceConfirm()&&#ca2.dayStock==-1)||#ca2.dayStock>9">
                                   					<@s.if test="hasMultiJourney() && multiJourneyId == null"></@s.if>
                                   					<@s.else>
											   			<span class="search_pp_calendar_balance">充足</span>
											   		</@s.else>
											   		<@s.if test="hasMultiJourney() && multiJourneyId != null">
										   				<input type="hidden" name="multiDate_${specDate?string("yyyy-MM-dd")}" value="${multiJourneyId}:${specDate?string("yyyy-MM-dd")}"/>
										   			</@s.if>
										   		</@s.if>
										   		<@s.elseif test="#ca2.dayStock>-1&&#ca2.dayStock!=0">
										   			<@s.if test="hasMultiJourney() && multiJourneyId == null"></@s.if>
                                   					<@s.else>
											   			<span class="search_pp_calendar_price1">余  <@s.property value="#ca2.dayStock"/></span>
											   		</@s.else>
											   		<@s.if test="hasMultiJourney() && multiJourneyId != null">
										   				<input type="hidden" name="multiDate_${specDate?string("yyyy-MM-dd")}" value="${multiJourneyId}:${specDate?string("yyyy-MM-dd")}"/>
										   			</@s.if>
										   		</@s.elseif >
										   		<@s.elseif test="((#ca2.isOverSaleAble()||#ca2.isNeedResourceConfirm())&&#ca2.dayStock==-1)||(#ca2.isOverSaleAble()&&#ca2.dayStock==0)">
										   			<@s.if test="hasMultiJourney() && multiJourneyId == null"></@s.if>
                                   					<@s.else>
											   			<span class="search_pp_calendar_balance">充足</span>
											   		</@s.else>
											   		<@s.if test="hasMultiJourney() && multiJourneyId != null">
										   				<input type="hidden" name="multiDate_${specDate?string("yyyy-MM-dd")}" value="${multiJourneyId}:${specDate?string("yyyy-MM-dd")}"/>
										   			</@s.if>
										   		</@s.elseif >
										   		<@s.elseif test="!#ca2.isSellable(1)" >
										   			<@s.if test="hasMultiJourney() && multiJourneyId == null"></@s.if>
                                   					<@s.else>
											   			<span class="search_pp_calendar_price1">售完</span>
											   		</@s.else>
											   		<@s.if test="hasMultiJourney() && multiJourneyId != null">
										   				<input type="hidden" name="multiDate_${specDate?string("yyyy-MM-dd")}" value="${multiJourneyId}:${specDate?string("yyyy-MM-dd")}"/>
										   			</@s.if>
										   		</@s.elseif>
										</@s.if>
                                </div>
                             </li>                 
			</@s.iterator>
             </ul>
             </@s.iterator>
	</div>    
</@s.iterator>
</div>