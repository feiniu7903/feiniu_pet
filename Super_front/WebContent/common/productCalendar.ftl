<!doctype html> 
<html> 
<head> 
<meta charset="utf-8" />
<!--link href="http://pic.lvmama.com/styles/new_v/ui_plugin/iframe_calendar.css?r=7743" type="text/css" rel="stylesheet"-->
<script type="text/javascript">
	var height = document.documentElement.clientHeight;
	var head = document.getElementsByTagName('head')[0];
    var link = document.createElement('link');
    if(height>400){
    	link.href = "http://pic.lvmama.com/styles/new_v/ui_plugin/calendar.css?r=8648";
    }else{
    	link.href = "http://pic.lvmama.com/styles/new_v/ui_plugin/iframe_calendar.css?r=7743";
    }
    link.rel = 'stylesheet';
    link.type = 'text/css';
    head.appendChild(link);

	if (navigator.appName == 'Microsoft Internet Explorer') {  
	         document.getElementsByClassName = function() {  
	             var tTagName = "*";  
	             if (arguments.length > 1) {  
	                 tTagName = arguments[1];  
	             }  
	             if (arguments.length > 2) {  
	                 var pObj = arguments[2]  
	             } else {  
	                var pObj = document;  
	             }  
	             var objArr = pObj.getElementsByTagName(tTagName);  
	             var tRObj = new Array();  
	             for ( var i = 0; i < objArr.length; i++) {  
	                 if (objArr[i].className == arguments[0]) {  
	                     tRObj.push(objArr[i]);  
	                 }  
	             }  
	             return tRObj;  
	         }  
	     }  


</script>
</head>
<body  style="overflow:hidden;">
<div class="calendar_free" data-super-free="<@s.property value="hasProdSelfPack()"/>" data-product-type="<@s.if test='prodProductBranch.prodProduct!=null'>${prodProductBranch.prodProduct.productType}</@s.if>" data-sub-product-type="<@s.if test='prodProductBranch.prodProduct!=null'>${prodProductBranch.prodProduct.subProductType}</@s.if>">
<@s.iterator value="cmList" status="cmi">
<@s.if test='hasProdSelfPack()'>
<div class="search_pp_calendar_box">
             <h2 class="search_pp_calendar_tit">出行日价格表</h2>
             <div class="search_pp_calendar_m">
                <@s.if test='flagNextMonth > 0'>
	                <div class="search_pp_cal_nevm">
	                    <span <@s.if test="#cmi.first">class="search_pp_cal_nevm_no_icon"</@s.if><@s.else>class="search_pp_cal_nevm_icon" onclick="javascript:document.getElementsByClassName('search_pp_calendar_box')[0].style.display='none';document.getElementsByClassName('search_pp_calendar_box')[1].style.display='block';"</@s.else>></span>
	                    <span class="search_pp_cal_nevm_text">${month}月</span>
	                </div>
		            <div class="search_pp_cal_nextm">
		               <span class="search_pp_cal_nextm_text">${(month+1)%12}月</span>
		               <span <@s.if test="#cmi.last">class="search_pp_cal_nextm_no_icon"</@s.if><@s.else>class="search_pp_cal_nextm_icon" onclick="javascript:document.getElementsByClassName('search_pp_calendar_box')[0].style.display='none';document.getElementsByClassName('search_pp_calendar_box')[1].style.display='block';"</@s.else>></span>
		            </div>
		            </@s.if>
		            <@s.else>
		            	<div class="search_pp_cal_nevm">
	                    	<span class="search_pp_cal_nevm_icon"></span>
		                </div>
			            <div class="search_pp_cal_nextm">
			               <span class="search_pp_cal_nevm_center">${month}月</span>
			                <span <@s.if test="#cmi.last">class="search_pp_cal_nextm_no_icon"</@s.if><@s.else>class="search_pp_cal_nextm_icon" onclick="javascript:document.getElementsByClassName('search_pp_calendar_box')[0].style.display='none';document.getElementsByClassName('search_pp_calendar_box')[1].style.display='block';"</@s.else>></span>
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
                       <span style="display:none;" class="search_pp_calendar_day_date"><@s.date name="#ca2.specDate" format="yyyy-MM-dd"/></span>
		   				 <@s.if test="#ca2.sellPriceFloat >0">
			   				<span class="search_pp_calendar_price1"></span>
			   				<span class="search_pp_calendar_price">&yen;<@s.property value="#ca2.priceInt"/><@s.if test="hasProdSelfPack()">起</@s.if></span>
		   				 </@s.if>
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
             	<@s.if test='flagNextMonth > 0'>
                <div class="search_pp_cal_nevm">
                    <span <@s.if test="#cmi.first">class="search_pp_cal_nevm_no_icon"</@s.if><@s.else>class="search_pp_cal_nevm_icon" onclick="javascript:document.getElementsByClassName('search_pp_calendar_box')[<@s.property value="#cmi.index"/>].style.display='none';document.getElementsByClassName('search_pp_calendar_box')[<@s.property value="#cmi.index-1"/>].style.display='block';"</@s.else>></span>
                    <span class="search_pp_cal_nevm_text">${month}月</span>
                </div>
	            <div class="search_pp_cal_nextm">
	               <span class="search_pp_cal_nextm_text">${(month+1)%12}月</span>
	                <span <@s.if test="#cmi.last">class="search_pp_cal_nextm_no_icon"</@s.if><@s.else>class="search_pp_cal_nextm_icon" onclick="javascript:document.getElementsByClassName('search_pp_calendar_box')[<@s.property value="#cmi.index"/>].style.display='none';document.getElementsByClassName('search_pp_calendar_box')[<@s.property value="#cmi.index+1"/>].style.display='block';"</@s.else>></span>
	            </div>
	            </@s.if>
	            <@s.else>
	            	<div class="search_pp_cal_nevm">
                    <span <@s.if test="#cmi.first">class="search_pp_cal_nevm_no_icon"</@s.if><@s.else>class="search_pp_cal_nevm_icon" onclick="javascript:document.getElementsByClassName('search_pp_calendar_box')[<@s.property value="#cmi.index"/>].style.display='none';document.getElementsByClassName('search_pp_calendar_box')[<@s.property value="#cmi.index-1"/>].style.display='block';"</@s.else>></span>
	                </div>
		            <div class="search_pp_cal_nextm">
		               <span class="search_pp_cal_nevm_center">${month}月</span>
		                <span <@s.if test="#cmi.last">class="search_pp_cal_nextm_no_icon"</@s.if><@s.else>class="search_pp_cal_nextm_icon" onclick="javascript:document.getElementsByClassName('search_pp_calendar_box')[<@s.property value="#cmi.index"/>].style.display='none';document.getElementsByClassName('search_pp_calendar_box')[<@s.property value="#cmi.index+1"/>].style.display='block';"</@s.else>></span>
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
                                   <span style="display:none;" class="search_pp_calendar_day_date"><@s.date name="#ca2.specDate" format="yyyy-MM-dd"/></span>
                                   		<@s.if test="#ca2.dayStock==-1 || #ca2.dayStock==0 || #ca2.dayStock>0 || #ca2.onlyForLeave==true || #ca2.overSale=='true' ">		
                                   				<@s.if test="(!#ca2.isNeedResourceConfirm()&&#ca2.dayStock==-1)||#ca2.dayStock>9">
										   			<span class="search_pp_calendar_balance">充足</span>
										   			<span class="search_pp_calendar_price">&yen;<@s.property value="#ca2.priceInt"/><@s.if test="hasPriceQiFlag()">起</@s.if></span>
										   		</@s.if>
										   		<@s.elseif test="#ca2.dayStock>-1&&#ca2.dayStock!=0">
										   			<span class="search_pp_calendar_price1">余  <@s.property value="#ca2.dayStock"/></span>
										   			<span class="search_pp_calendar_price">&yen;<@s.property value="#ca2.priceInt"/><@s.if test="hasPriceQiFlag()">起</@s.if></span>
										   		</@s.elseif >
										   		<@s.elseif test="((#ca2.isOverSaleAble()||#ca2.isNeedResourceConfirm())&&#ca2.dayStock==-1)||(#ca2.isOverSaleAble()&&#ca2.dayStock==0)">
										   			<span class="search_pp_calendar_price1"></span>
										   			<span class="search_pp_calendar_price">&yen;<@s.property value="#ca2.priceInt"/><@s.if test="hasPriceQiFlag()">起</@s.if></span>
										   		</@s.elseif >
										   		<@s.elseif test="!#ca2.isSellable(1)" >
										   			<span class="search_pp_calendar_price1">售完</span>
										   		</@s.elseif>
										</@s.if>
                                </div>
                             </li>                 
			</@s.iterator>
             </ul>
             </@s.iterator>
</div>    
</@s.else>                
</@s.iterator>
</div>
</body>
</html>
