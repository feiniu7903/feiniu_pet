<div class="dstnt_left_box1">
	 <h4 class="dstnt_left_boxtit">
	 <@s.if test="commonPlaceTopicList.size>1">
	  <!--<span class="dstnt_morepage">
	   <span class="dstnt_more_btn"><span class="dstnt_more_prev"></span><span class="dstnt_more_next"></span></span>
	   <span class="dstnt_more_page"><span class="dstnt_more_crtpage"></span>/<span class="dstnt_more_allpage"></span></span>
	   </span>-->
	   </@s.if>
	<@s.property value="currentPlace.name"/>旅游资讯
	 </h4>
	 
	 <div class="dstnt_left_boxcont1">
	<@s.if test="commonPlaceTopicList.size>0">
		 <div class="dstnt_l_focusbox">
			 <ul class="dstnt_leftimg_list">
				<@s.iterator  value="commonPlaceTopicList"  status="topic">
					<li><a rel="nofollow" href="<@s.property value="url"/>" target="_blank"><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" to="<@s.property value="imgUrl"/>" width="239" height="116" alt="<@s.property value="title"/>" title="<@s.property value="title"/>"></a></li>
				</@s.iterator>
			 </ul>
			 <ul class="dstnt_left_focusbar"></ul>
		 </div><!--dstnt_l_focusbox-->
	 </@s.if>


     <div class="dstnt_left_ifmt">
         <h4 class="dstnt_tit4"><@s.property value="currentPlace.name"/>资讯</h4>
         <ul class="dstnt_left_ifmt_ul">
         <li><a href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/dish" target="_blank" title="<@s.property value="currentPlace.name"/>美食">美食</a></li>
         <li><a href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/hotel" target="_blank" title="<@s.property value="currentPlace.name"/>住宿">住宿</a></li>
         <li><a href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/traffic"  target="_blank" title="<@s.property value="currentPlace.name"/>交通">交通</a></li>
         <li><a href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/entertainment" target="_blank" title="<@s.property value="currentPlace.name"/>娱乐">娱乐</a></li>
         <li><a href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/shop" target="_blank" title="<@s.property value="currentPlace.name"/>购物">购物</a></li>
         <li><a href="http://www.lvmama.com/travel/<@s.property value="currentPlace.pinYinUrl"/>/weekendtravel" target="_blank" title="<@s.property value="currentPlace.name"/>行程">行程</a></li>
         <li><a href="http://www.lvmama.com/dest/<@s.property value="currentPlace.pinYinUrl"/>/maps" target="_blank" title="<@s.property value="currentPlace.name"/>地图">地图</a></li>
         <li ><a id="weather_id" href="http://www.lvmama.com/weather/" target="_blank" title="<@s.property value="currentPlace.name"/>天气">天气</a></li>
         </ul>
     </div>

  </div>
</div><!--dstnt_left_box1--><!--上海旅游资讯-->    