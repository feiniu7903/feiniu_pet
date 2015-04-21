<div class="calendar_free" data-super-free="<@s.property value="#action.hasProdSelfPack()"/>" data-product-type="<@s.if test='prodProductBranch.prodProduct!=null'>${prodProductBranch.prodProduct.productType}</@s.if>" data-sub-product-type="<@s.if test='prodProductBranch.prodProduct!=null'>${prodProductBranch.prodProduct.subProductType}</@s.if>">
<@s.iterator value="cmList" status="cmi">
<@s.if test='#action.hasProdSelfPack()'>
<#if cmi.first>
<div class="search_pp_calendar_box" month="${month}" year="${year}" needReload="false">
<#else>
<div class="search_pp_calendar_box" month="${month}">
</#if>

             <h2 class="search_pp_calendar_tit">出行日价格表</h2>
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
</@s.if>                    
<@s.else>                
<div class="search_pp_calendar_box" <@s.if test='#cmi.index > 0'>style="display:none;"</@s.if>>
             <h2 class="search_pp_calendar_tit"><@s.if test='prodProductBranch.prodProduct!=null&&prodProductBranch.prodProduct.subProductType=="SINGLE_ROOM"'>入住日价格表</@s.if><@s.else>出行日价格表</@s.else></h2>
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
							 	 	data-sellbale="true" class="search_pp_calendar_d_box month_${monthColor}"
							 	</@s.elseif>
							 	 <@s.else>class="search_pp_calendar_d_box_no_hover month_${monthColor}" </@s.else>>              
                                   <span class="search_pp_calendar_day"><@s.date name="#ca2.specDate" format="dd"/></span>
                                   
                                   		<@s.if test="#ca2.dayStock==-1 || #ca2.dayStock==0 || #ca2.dayStock>0 || #ca2.onlyForLeave==true || #ca2.overSale=='true' ">		
                                   				<@s.if test="(!#ca2.isNeedResourceConfirm()&&#ca2.dayStock==-1)||#ca2.dayStock>9">
										   			<span class="search_pp_calendar_balance">充足</span>
										   			<@s.if test="(#ca2.favorJsonParams != null && #ca2.favorJsonParams != '')||(#ca2.cuCouponFlag > 0)">
										   			  <span class="calendar_active">促</span>
										   			</@s.if>
										   			<span class="search_pp_calendar_price">&yen;<@s.property value="#ca2.priceInt"/><@s.if test="hasPriceQiFlag()">起</@s.if></span>
										   		</@s.if>
										   		<@s.elseif test="#ca2.dayStock>-1&&#ca2.dayStock!=0">
										   			<span class="search_pp_calendar_price1">余  <@s.property value="#ca2.dayStock"/></span>
										   			<@s.if test="(#ca2.favorJsonParams != null && #ca2.favorJsonParams != '')||(#ca2.cuCouponFlag > 0)">
										   			  <span class="calendar_active">促</span>
										   			</@s.if>
										   			<span class="search_pp_calendar_price">&yen;<@s.property value="#ca2.priceInt"/><@s.if test="hasPriceQiFlag()">起</@s.if></span>
										   		</@s.elseif >
										   		<@s.elseif test="((#ca2.isOverSaleAble()||#ca2.isNeedResourceConfirm())&&#ca2.dayStock==-1)||(#ca2.isOverSaleAble()&&#ca2.dayStock==0)">
										   			<span class="search_pp_calendar_price1"></span>
										   			<@s.if test="(#ca2.favorJsonParams != null && #ca2.favorJsonParams != '')||(#ca2.cuCouponFlag > 0)">
										   			  <span class="calendar_active">促</span>
										   			</@s.if>
										   			<span class="search_pp_calendar_price">&yen;<@s.property value="#ca2.priceInt"/><@s.if test="hasPriceQiFlag()">起</@s.if></span>
										   		</@s.elseif >
										   		<@s.elseif test="!#ca2.isSellable(1)" >
										   			<span class="search_pp_calendar_price1">售完</span>
										   		</@s.elseif>
										</@s.if>
										
										<@s.if test="#ca2.favorJsonParams != null && #ca2.favorJsonParams != ''">
											<!--日历上展示的优惠信息参数-->
							                <input type="hidden" value='${favorJsonParams}' name="tipData"/>
							   			</@s.if>
										<span style="display:none;" class="search_pp_calendar_day_date"><@s.date name="#ca2.specDate" format="yyyy-MM-dd"/></span>
                                </div>
                             </li>                 
			</@s.iterator>
             </ul>
             </@s.iterator>
</div>    
</@s.else>                
</@s.iterator>
</div>

<#--产品详情页快速预订时间价格表-->
<select id="selectTimePrice" style="display:none;">   
    <@s.iterator value="cmList" status="cmi">
        <@s.iterator value="calendar" var="ca3" status="cal3">     
            <@s.iterator value="#ca3" status="cal4" var="ca4">
                <@s.if test='(#lastDate==null||#lastDate.before(#ca4.specDate)) && ((#action.hasProdSelfPack() && #ca4.sellPriceFloat >0) || #ca4.isSellable(1))'>                      
                <option value='<@s.date name="#ca4.specDate" format="yyyy-MM-dd"/>' <@s.if test="#cmi.index==0 && #cal3.index==0 && #cal4.index==0">selected</@s.if>> 
                    <@s.set name="lastDate" value="#ca4.specDate"></@s.set>        
                    <@s.date name="#ca4.specDate" format="MM-dd"/>（<@s.date name="#ca4.specDate" format="EE"/>）  
                    <@s.if test="!#action.hasProdSelfPack()">
                        <@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(prodProductBranch.branchName,8)"/>￥<@s.property value="#ca4.priceInt"/> 
                    </@s.if>                                        
                </option>
                </@s.if>
            </@s.iterator>
        </@s.iterator>    
    </@s.iterator>
</select>


<!--日历上展示的优惠信息模板-->
<input type="hidden" value="{1}， 立减<i class='orange'>{2}</i>元/份。" id="tipTemplete1"/>
<input type="hidden" value="{1}，{2}份立减<i class='orange'>{3}</i>元，{4}份立减<i class='orange'>{5}</i>元，以此类推。" id="tipTemplete2"/>
<input type="hidden" value="{1}，{2}份起订，每份立减<i class='orange'>{3}</i>元。" id="tipTemplete3"/>
<input type="hidden" value="{1}，{2}份起订，再订{3}份立减<i class='orange'>{4}</i>元，再订{5}份立减<i class='orange'>{6}</i>元，以此类推。" id="tipTemplete4"/>
<input type="hidden" value="<b>预订{1}日产品，可享以下优惠</b>" id="tipTemplete5"/>
<input type="hidden" value="<b>同时多买可享优惠</b></br>(预订同种产品以最实惠规则让利)" id="tipTemplete6"/>
<input type="hidden" value="<b>当前日期的价格为促销价</b>" id="tipTemplete7"/>
