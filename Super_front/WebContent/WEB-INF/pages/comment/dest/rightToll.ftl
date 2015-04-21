<div class="container_rt">
   
   <#include "/WEB-INF/pages/comment/dest/placeTipsSaleProduct.ftl" />
   
    <div class="hotL_box c_price">
            <div class="ca_title clearfix"> 
              <h3 class="fl">猜你喜欢</h3>
              <small></small></div>
             <ul class="cL_hot B f14 lh22" id="guessYourFavorite">
 
      </ul>
    </div>
          
     <!--驴友热评-->
 	   <!-- #include "/WEB-INF/pages/comment/index/smallCmtSpecialSubject.ftl" -->
 
	  <div class="aside_box c_shadow">
          <h4 class="c_iconbg reflect"><@s.property value="place.name"/>景点地图</h4>
           <iframe src="http://www.lvmama.com/dest/googleMap/getSimpleMapCoordinate!getSimpleMapCoordinate.do?id=<@s.property value="place.placeId"/>&windage=0.005&width=190px&height=140px&mapZoom=15" width="260" height="190"></iframe>
           <p class="c_spot_fb">
          		<a href="http://www.lvmama.com/dest/<@s.property value="place.pinYinUrl"/>/maps" target="_blank"><img src="http://pic.lvmama.com/img/viewspot/vs_map_button.jpg" class="vs_map_button"/></a>
           </p>
        </div><!--aside_box end-->
        
        <!--
	  <div class="aside_box c_shadow">
        	<h4 class="c_iconbg reflect">推荐景点</h4>
              <ul class="c_spot_list c_recom_spot">
              	<@s.iterator value="placeInfoRecommed">
			 	     <li><a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>"><@s.property value="title"/></a></li>
			    </@s.iterator>
       		  </ul>
      </div>aside_box end-->
      
         <div class="aside_box c_shadow">
            <h4 class="c_iconbg reflect">旅游景点推荐</h4>
            <ul class="r_jdtj_list">
            
                <@s.iterator value="recommendPlaceList" status="st" >
                <li>
                    <a href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}" target="_blank"><img src="${smallImageUrl?if_exists}" width="225" height="150" alt=""></a>
                    <h5><a href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}" target="_blank">${name?if_exists}</a></h5>
                    <p> <@s.if test="remarkes!=null && remarkes.length()>54">
                                    <@s.property value="remarkes.substring(0,54)" escape="false"/>...
                                    </@s.if><@s.else>${remarkes?if_exists}
                                    </@s.else></p>
                </li>
                </@s.iterator>
            </ul>
        </div>
      
       
	  <div>
	  <!--AdForward Begin:-->
	  <iframe marginheight="0" marginwidth="0" frameborder="0" width="280" height="200" scrolling="no" src="http://lvmamim.allyes.com/main/s?user=lvmama_2013|dianping_2013|dianping_2013_nei_banner01&db=lvmamim&border=0&local=yes"></iframe>
	  <!--AdForward End-->
	  </div> 
	    
</div><!--container_rt-->

  
