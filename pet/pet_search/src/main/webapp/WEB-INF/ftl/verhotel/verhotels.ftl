<#if pageConfig.items?? && pageConfig.items?size gt 0>
	<#list pageConfig.items as item_hotel>
				<div class="J_l hotel-object J_productId_${item_hotel.hotel_id}" data-coordinate="${item_hotel.baidu_geo}" data-sources="${item_hotel.hotel_id}">
	                    <div class="top clearfix" >
	                        <div class="object-left">
	                            <a hidefocus="false" href="http://hotels.lvmama.com/hotel/${item_hotel.hotel_id}.html" target="_blank" onclick="submitDetail('${item_hotel.hotel_id}')">
	                            	<img width="150" height="100" alt=${item_hotel.hotel_name}${item_hotel.photo_content} 
	                            	<#if  item_hotel.hotel_pic ?? && item_hotel.hotel_pic?length gt 0>
						            	src="http://pic.lvmama.com/${item_hotel.hotel_pic}">
						         	<#else>
						             	src="http://pic.lvmama.com/img/cmt/img_80_60.png">
						          	</#if>
	                           
	                            </a>
	                        </div>
	                        <div class="object-right">
	                            <div class="J_s_price pp-infos">
	                            </div>
	                            <div class="object-info">
	                                <div class="hotel-name">
	                                    <i class="icon landmarks">${item_hotel_index+1}</i>
	                                    <a href="http://hotels.lvmama.com/hotel/${item_hotel.hotel_id}.html" target="_blank" class="f18" title="${item_hotel.hotel_name}" onclick="submitDetail('${item_hotel.hotel_id}');return false">${item_hotel.hotel_name}</a>
	                                    <span class="cc2">  
		                                    <#if (item_hotel.hotelstar ==100) >五星级酒店</#if>
		                                    <#if (item_hotel.hotelstar ==101) >豪华型酒店</#if>
		                                    <#if (item_hotel.hotelstar ==102) >四星级酒店</#if>
		                                    <#if (item_hotel.hotelstar ==103) >品质型酒店</#if>
		                                    <#if (item_hotel.hotelstar ==104) >三星级酒店</#if>
		                                    <#if (item_hotel.hotelstar ==105) >舒适型酒店</#if>
		                                    <#if (item_hotel.hotelstar ==106) >二星级酒店</#if>
		                                    <#if (item_hotel.hotelstar ==107) >简约型酒店</#if>
	                                    </span>
	                                </div>
	                                <div class="service">
	                                 	<#if ((item_hotel.facilities_name?contains("免费停车场")?string)=="true" )><span class="icon free-park" title="免费停车场"></span></#if>
	                                    <#if ((item_hotel.facilities_name?contains("免费wifi")?string)=="true" )><span class="icon free-wireless" title="免费wifi"></span></#if>
	                                    <#if ((item_hotel.facilities_name?contains("免费接机服务")?string)=="true" )><span class="icon free-pick" title="免费接机服务"></span></#if>
	                                    <#if ((item_hotel.facilities_name?contains("免费宽带")?string)=="true" )><span class="icon free-broadband" title="免费宽带"></span></#if>
	                                </div>
	                                <ul class="hotel-info">
	                                    <#if item_hotel.surrondings?? && (item_hotel.surrondings?length>0) >
	                                    <li>周边：${item_hotel.surrondings}</li>
	                                    </#if>
	                                    <#if item_hotel.hotel_adress?? && (item_hotel.hotel_adress?length>0)>
										<li><li>地址：${item_hotel.hotel_adress} <a href="http://hotels.lvmama.com/hotel/${item_hotel.hotel_id}.html" target="_blank" rel="nofollow" title="${item_hotel.hotel_name}" onclick="submitDetail('${item_hotel.hotel_id}');return false">地图</a> </li></li>                                    	
	                                    </#if>
	                                     <#if item_hotel.distance?? && (item_hotel.distance>0)>
										<li>距离${item_hotel.distance}米</li>                              	
	                                    </#if>
	                                    
	                                </ul>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="J_room room room_loading clearfix">
	                        <table>
	                        </table> 
	                    </div>
	                    <div class="unfold-wrapper"><a class="J_more_room" href="javascript:;" rel="nofollow">展开全部房型</a><i class="ui-arrow-bottom gray-ui-arrow-bottom"></i></div>
	                </div>
	                
	</#list>
<#else>
	<div class="tipbox tip-success tip-nowrap">
        <span class="tip-icon-big tip-icon-big-warning"></span>
        <div class="tip-content">
            <h3 class="tip-title">非常抱歉，没有找到符合您条件的酒店。</h3>
            <p class="tip-explain">建议您适当减少已选择的条件或 <a class="J_clear_filter" href="javascript:;">清除所有条件</a></p>
        </div>
    </div>
</#if>

<#if pageConfig.totalPageNum gt 1 > 
    <div class="pages orangestyle"> 
        <@s.property escape="false" value="@com.lvmama.search.util.PaginationVer@pagination(pageConfig)"/>
    </div>
</#if>

<form id="detailForm" method="post" action="" target="_blank">
		 <input type="hidden" name="startDate" id="startDate"  value="${verHotelSearchVO.beginBookTime}" />
         <input type="hidden" name="endDate" id="endDate" value="${verHotelSearchVO.endBookTime}"  />
</form>
<script type="text/javascript">
	function submitDetail(hotelId){
		var url = "http://hotels.lvmama.com/hotel/"+hotelId+".html";
		var thisStartDate = $("#beginBookTime").val();
		var thisEndDate = $("#endBookTime").val();
		$("startDate").val(thisStartDate);
		$("startDate").val(thisEndDate);
		$("#detailForm").attr("action", url).submit();
		return false;
	}
</script>
