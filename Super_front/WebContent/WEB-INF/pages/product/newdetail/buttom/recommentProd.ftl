            <i id="row_10" class="pkg-maodian">&nbsp;</i>
            <h3 class="h3_tit"><span>相关产品推荐</span></h3>
            <div class="row related_recommend">
                <ul class="recommend_tabs">
                    <@s.if test="guestProductList.productTicketList!=null && guestProductList.productTicketList.size>0">
                    <li><a href="javascript:void(0)">打折门票</a></li>
                    </@s.if>
                    
                    <@s.if test="guestProductList.productSinceList!=null && guestProductList.productSinceList.size>0">
                    <li><a href="javascript:void(0)">自由行</a></li>
                    </@s.if>
                    
                    <@s.if test="guestProductList.productRouteList!=null && guestProductList.productRouteList.size>0">
                    <li><a href="javascript:void(0)">跟团游</a></li>
                    </@s.if>
                    
                    <@s.if test="placeCoordinateHotel!=null && placeCoordinateHotel.size>0">
                    <li><a href="javascript:void(0)">周边酒店</a></li>
                    </@s.if>
                </ul>
                <div class="recommend_panes">
                    <@s.if test="guestProductList.productTicketList!=null && guestProductList.productTicketList.size>0">
                    <div class="recommend_pane">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">                      
                          <tr class="recommend_pane_tit">
                            <td width="505"><span>产品名称</span></td>
                            <td>市场价</td>
                            <td>驴妈妈价</td>
                            <td>点评奖金</td>
                            <td>在线预订</td>
                          </tr>
                          <@s.iterator value="guestProductList.productTicketList" >
                          <tr>
                            <td><a target="_blank" href="${productUrl}">${productName}</a></td>
                            <td><del>&yen;${marketPriceInteger}</del></td>
                            <td><strong>&yen;${sellPriceInteger}起</strong></td>
                            <td><strong>&yen;${cashRefund}</strong></td>
                            <td><a rel="nofollow" target="_blank" href="${productUrl}" class="recommend_yuding"></a></td>
                          </tr>
                          </@s.iterator>                      
                        </table>
                    </div><!--recommend_pane end-->  
                    </@s.if>
                    
                    <@s.if test="guestProductList.productSinceList!=null && guestProductList.productSinceList.size>0">
                    <div class="recommend_pane">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr class="recommend_pane_tit">
                            <td width="505"><span>产品名称</span></td>
                            <td>市场价</td>
                            <td>驴妈妈价</td>
                            <td>点评奖金</td>
                            <td>在线预订</td>
                          </tr>
                          <@s.iterator value="guestProductList.productSinceList" >
                          <tr>
                            <td><a target="_blank" href="${productUrl}">${productName}</a></td>
                            <td><del>&yen;${marketPriceInteger}</del></td>
                            <td><strong>&yen;${sellPriceInteger}起</strong></td>
                            <td><strong>&yen;${cashRefund}</strong></td>
                            <td><a rel="nofollow" target="_blank" href="${productUrl}" class="recommend_yuding"></a></td>
                          </tr>
                          </@s.iterator>
                        </table>
                    </div><!--recommend_pane end-->
                    </@s.if>
                    
                    <@s.if test="guestProductList.productRouteList!=null && guestProductList.productRouteList.size>0">
                    <div class="recommend_pane">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr class="recommend_pane_tit">
                            <td width="505"><span>产品名称</span></td>
                            <td>市场价</td>
                            <td>驴妈妈价</td>
                            <td>点评奖金</td>
                            <td>在线预订</td>
                          </tr>
                          <@s.iterator value="guestProductList.productRouteList" >
                          <tr>
                            <td><a target="_blank" href="${productUrl}">${productName}</a></td>
                            <td><del>&yen;${marketPriceInteger}</del></td>
                            <td><strong>&yen;${sellPriceInteger}起</strong></td>
                            <td><strong>&yen;${cashRefund}</strong></td>
                            <td><a rel="nofollow" target="_blank" href="${productUrl}" class="recommend_yuding"></a></td>
                          </tr>
                          </@s.iterator>
                        </table>
                    </div><!--recommend_pane end-->
                    </@s.if>
                    
                    <@s.if test="placeCoordinateHotel!=null && placeCoordinateHotel.size>0">
                    <div class="recommend_pane">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr class="recommend_pane_tit">
                            <td width="505"><span>产品名称</span></td>                                                    
                            <td>在线预订</td>
                          </tr>
                          <@s.iterator value="placeCoordinateHotel" status="sts">
                          <@s.if test="#sts.index<6">
                          <tr>
                            <td><a href="http://www.lvmama.com/dest/${placePinYinUrl}">${placeName}</a></td>                            
                            <td><a rel="nofollow" href="http://www.lvmama.com/dest/${placePinYinUrl}" class="recommend_yuding"></a></td>
                          </tr>
                          </@s.if>
                          </@s.iterator>
                        </table>
                    </div><!--recommend_pane end--> 
                    </@s.if>               
                </div><!--recommend_panes end-->
            </div><!--related_recommend end-->
