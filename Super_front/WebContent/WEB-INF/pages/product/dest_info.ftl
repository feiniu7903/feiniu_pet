<@s.if test="place!=null">
<div class="free_hotel_btitle">
	<h1>酒店详情</h1>
	<input type="image" src="/img/free/free_star.jpg" onmouseover="this.src='/img/free/free_hotel_close.png'" onmouseout="this.src='/img/free/free_star.jpg'" class="free_hb_close" id="free_hb_close"/>
</div>
<div class="free_hotel_bcontent">
	<div class="free_hotel_left">
		<h1>${place.name}</h1>
		<p class="free_hotel_star"><i class="icon_hotel0${place.hotelStar}"></i></p>
		<p><b>开业时间：</b>${place.startTime}</p>
		<p><b>酒店地址：</b>${place.province}${place.city}${place.address}</p>
		<p><b>酒店电话：</b>${place.phone}</p>
		<p><b>酒店类型：</b>${place.hotelType}</p>
		<p><b>是否涉外：</b>${place.entertainForeigner}</p>
		<p><b>房间数量(间)：</b>${place.roomNum}</p>
		<p class="free_hotel_jgxx">交通信息：</p>
		<@s.if test="viewPlaceInfos!=null">
		<@s.iterator value="viewPlaceInfos" id="placeInfo">
		<p><@s.property value="#placeInfo.content" escape="false"/></p>
		</@s.iterator>
		</@s.if>
	</div>
	<div class="free_hotel_right">
		<@s.if test="place.largeImage!=null"><img title="" alt="" src="http://pic.lvmama.com/pics${place.largeImage}"/></@s.if>
	</div>
	<div class="clear"></div>
	<div class="free_hotel_bottom">
		<b>酒店介绍：</b>
		<p>
			<@s.property value="place.recommendReason" escape="false"/>
		</p>
	</div>
</div>
</@s.if>
