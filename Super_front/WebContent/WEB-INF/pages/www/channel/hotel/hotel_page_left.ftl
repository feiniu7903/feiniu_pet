    <div class="aside-big">
        <div class="pro-search search-hotel">
            <div class="search-nav">
                <ul class="hor clearfix">
                    <li><a href="javascript:;">国内酒店</a></li>
                    <li class="selected"><a target="_blank" href="http://hotel.lvmama.com/?aid=367242">海外酒店 <small>&raquo;</small></a></li>
                </ul>
            </div>
            <div class="content">
                <div class="hotel-domestic">
                    	<label class="control-label" for="">入住城市/周边景点/酒店名称：</label>
                        <input id="fromDest" type="hidden" value="${fromPlaceName}"/>
                        <div id="txtHotelSearchDiv"><input class="input-text" type="text" placeholder="中文/拼音" id="txtHotelSearch" name="txtCity" autocomplete="off" />
                        </div>
                        <div class="checkbox-list">
                            <label class="checkbox inline"><input class="input-checkbox" name="hotelType" type="checkbox" value="E5">五星级/豪华型</label>
                                <label class="checkbox inline"><input class="input-checkbox" name="hotelType" type="checkbox" value="E4">四星级/高档型</label>
                                <label class="checkbox inline"><input class="input-checkbox" name="hotelType" type="checkbox" value="E3">三星级/舒适型</label>
                                <label class="checkbox inline"><input class="input-checkbox" name="hotelType" type="checkbox" value="E2">二星级/简约型</label>
                        </div>
                        <button class="xicon searchbtn" type="button" id="searchHotelBtn">搜索</button>
                 </div> <!-- //.hotel-domestic -->
                
            </div> <!-- //.content -->
        </div> <!-- //.pro-search -->
        
        <div class="hr_d"></div>
        
        <div class="side-box border hotel-cside1">
            <div class="head">
                <h4>热门国内酒店目的地</h4>
            </div>
            <div class="content">
                <ul class="hotdest">
                  <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_PAGE_LEFT_HEAT_NATIONAL')" status="st">
                	<li><b>${title}</b>
                	     <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="st">
                          <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                         </@s.iterator>
                    </li>
                    </@s.iterator>
                </ul>
            </div>
        </div>
        <div class="side-box border hotel-cside2">
            <div class="head">
                <h4>热门境外酒店目的地</h4>
            </div>
            <div class="content">
                <ul class="hotdest">
                	 <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_PAGE_LEFT_HEAT_ABROAD')" status="st">
                	<li><b>${title}</b>
                	     <@s.iterator value="(map.get('recommendInfoMainList')).get('${channelPage}_${bakWord1?if_exists}')" status="st">
                          <a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                         </@s.iterator>
                    </li>
                    </@s.iterator>
                </ul>
            </div>
        </div>
        <@s.if test="(map.get('TUANGOU_PRODUCT'))!=null">
        <div class="side-box border hotel-tuan">
            <div class="head">
                <h4>酒店团购</h4>
                <a  target="_blank" class="link-more" href="http://www.lvmama.com/tuangou/hotel-all-all-1">更多团购 &raquo;</a>
            </div>
            <div class="content">
                <ul class="hotel-buy">
                   <@s.iterator value="(map.get('TUANGOU_PRODUCT'))" status="st">
				   <@s.if test="#st.first">
				   <li>
                        <p class="info">${productName?if_exists}</p>
                        <div class="imgtext">
                            <a class="text-cover" target="_blank" href="http://www.lvmama.com/product/${productId?if_exists}">
                                <img src="${absoluteSmallImageUrl?if_exists}"  width="180" height="120" alt="${productName?if_exists}" />
                              </a>
                            <div class="textbox">
                                <p>
                                    <dfn>&yen;<i>${sellPriceYuan?if_exists}</i></dfn>
                                    <span class="price">节省：&yen;${marketPriceYuan?if_exists?number-sellPriceYuan?if_exists?number}</span>
                                </p>
                                <p><a target="_blank" class="btn btn-orange B" href="http://www.lvmama.com/product/${productId?if_exists}">&nbsp;&nbsp;抢购&nbsp;&nbsp;</a></p>
                            </div>
                        </div>
                    </li>
                    </@s.if>
                   </@s.iterator>
                </ul>
            </div>
        </div>
        </@s.if>
       
    </div> <!-- //.aside-big -->
