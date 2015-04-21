                <@s.if test="prodCProduct.prodProduct.hasSelfPack()">
               		<div class="dtl_box_left" style="height:245px">   
                </@s.if>
                <@s.else>
               		<div class="dtl_box_left">   
                </@s.else>
                         <div class="dtl_focusbox">
                              <ul class="dtl_crtimg">
                                  <@s.iterator value="comPictureList" var="cpl" status="sts">
                                  <@s.if test="${sts.index<4}">
                                  <li><img src="${cpl.absolute580x290Url?if_exists}" width="440px" height="220px"/></li>
                                  </@s.if>
                                 </@s.iterator>
                              </ul>
                              <ul class="dtl_focuslist">
                                 <@s.iterator value="comPictureList" var="cpl" status="sts">                                
                                 <@s.if test="#sts.index<4">                                     
                                     <li <@s.if test="#sts.index==0">class="dtl_focus_crt"</@s.if>>                                    
                                     <img src="${cpl.absolute580x290Url?if_exists}" width="97" height="71" alt="${cpl.pictureName?if_exists}" rev="${cpl.absolute580x290Url?if_exists}"/>
                                     <span class="zz_panel"></span>
                                 </li>
                                 </@s.if>
                                 </@s.iterator>
                              </ul>
                         </div><!--focus-->
                         <!--不定期产品不显示时间价格表 -->
                         <@s.if test='prodCProduct.prodProduct.IsAperiodic()'>
                         	<@s.if test='prodCProduct.prodProduct.isTicket()'>
					        	<div class="product-process">
								    <div class="bright-points">
								        <img src="http://pic.lvmama.com/img/new_v/ob_detail/bright-points.png" />
								        <h4>期票产品亮点</h4>
								        <p>1.随心所欲，预订不用选择指定游玩日期<br>
								           2.有效期内均可游玩
								        </p>
								    </div>
								    <h4>期票（门票类）产品预订流程</h4>
								    <img src="http://pic.lvmama.com/img/new_v/ob_detail/product-process2.png" width="554" height="85" alt=""/>
								</div>
					        </@s.if>
                         	<@s.if test='prodCProduct.prodProduct.isHotel()'>
					        	<div class="product-process">
								    <div class="bright-points">
								        <img src="http://pic.lvmama.com/img/new_v/ob_detail/bright-points.png" />
								        <h4>期票产品亮点</h4>
								        <p>1.随心所欲，预订不用选择指定入住日期<br>
								        2.有效期内提前${prodCProduct.prodProduct.aheadBookingDays}天致电预约入住日期，如期出游
								        </p>
								    </div>
								    <h4>期票（酒店类）产品预订流程</h4>
								    <img src="http://pic.lvmama.com/img/new_v/ob_detail/product-process.png" width="554" height="85" alt=""/>
								</div>
					        </@s.if>
                         	<@s.if test='prodCProduct.prodProduct.isRoute()'>
					        	<div class="product-process">
								    <div class="bright-points">
								        <img src="http://pic.lvmama.com/img/new_v/ob_detail/bright-points.png" />
								        <h4>期票产品亮点</h4>
								        <p>1.随心所欲，预订不用选择指定游玩日期<br>
								        2.有效期内提前${prodCProduct.prodProduct.aheadBookingDays}天致电预约出游日期，如期出游
								        </p>
								    </div>
								    <h4>期票（线路类）产品预订流程</h4>
								    <img src="http://pic.lvmama.com/img/new_v/ob_detail/product-process1.png" width="554" height="85" alt=""/>
								</div>
					        </@s.if>
                         </@s.if>
                         <@s.else>
                          <@s.if test="!prodCProduct.prodProduct.hasSelfPack()">
                         	      <div class="time-price-one" data-pid="<@s.property value="prodBranch.productId" escape="false"/>" data-bid="<@s.property value="prodBranch.prodBranchId" escape="false"/>"></div>
                     	    </@s.if>
                         	
                         </@s.else>
                     </div><!--boxleft-->
