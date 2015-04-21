            <i id="row_9" class="pkg-maodian">&nbsp;</i>
            <h3 class="h3_tit"><span>目的地情报</span></h3>
            <div class="row dest_intelligence">
                <dl>
                    <dt>${comPlace.name}</dt>
                    <dd>
                        <img src = "${comPlace.smallImageUrl}" height="60" width="120" />
                        <p> 
            
                            <@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(195,comPlace.remarkes)" escape="false"/>
                        <a rel="nofollow" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>" target="_blank">详细介绍 >></a>
                        </p>
                    </dd>
                </dl>
                <dl class="raiders">                    
                    <dd>
                    	<!--  国内目的地  -->
                    	<@s.if test='comPlace!=null && comPlace.stage=="1" && comPlace.template == "template_zhongguo"'>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>">
                    			<@s.property value="comPlace.name" escape="false"/>旅游
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/ticket_tab">
                    			<@s.property value="comPlace.name" escape="false"/>景点门票
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/freeness_tab">
                    			<@s.property value="comPlace.name" escape="false"/>自由行
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/hotel_tab">
                    			<@s.property value="comPlace.name" escape="false"/>酒店预订
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/surrounding_tab">
                    			<@s.property value="comPlace.name" escape="false"/>跟团游
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/guide/place/<@s.property value="comPlace.pinYinUrl"/>/">
                    			<@s.property value="comPlace.name" escape="false"/>旅游攻略
                    		</a>                    		
                    		<a target="_blank" href="http://www.lvmama.com/comment/<@s.property value="comPlace.placeId"/>-1">
                    			<@s.property value="comPlace.name" escape="false"/>点评
                    		</a>
				<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/scenery">
                    			<@s.property value="comPlace.name" escape="false"/>旅游景点
                    		</a>
							<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/dish">
                    			<@s.property value="comPlace.name" escape="false"/>美食
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/hotel">
                    			<@s.property value="comPlace.name" escape="false"/>住宿
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/traffic">
                    			<@s.property value="comPlace.name" escape="false"/>交通
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/entertainment">
                    			<@s.property value="comPlace.name" escape="false"/>娱乐
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/shop">
                    			<@s.property value="comPlace.name" escape="false"/>购物
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/weekendtravel">
                    			<@s.property value="comPlace.name" escape="false"/>行程
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/photo">
                    			<@s.property value="comPlace.name" escape="false"/>摄影
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/maps">
                    			<@s.property value="comPlace.name" escape="false"/>地图
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/weather">
                    			<@s.property value="comPlace.name" escape="false"/>天气
                    		</a>                    	
                    	</@s.if>
                    	<!--  境外目的地  -->
                    	<@s.if test='comPlace!=null && comPlace.stage=="1" && comPlace.template == "template_abroad"'>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>">
                    			<@s.property value="comPlace.name" escape="false"/>旅游
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/ticket_tab">
                    			<@s.property value="comPlace.name" escape="false"/>景点门票
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/dest2destFreeness_tab">
                    			<@s.property value="comPlace.name" escape="false"/>自由行
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/hotel_tab">
                    			<@s.property value="comPlace.name" escape="false"/>酒店预订
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/dest2destGroup_tab">
                    			<@s.property value="comPlace.name" escape="false"/>跟团游
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/guide/place/<@s.property value="comPlace.pinYinUrl"/>/">
                    			<@s.property value="comPlace.name" escape="false"/>旅游攻略
                    		</a>                    		
                    		<a target="_blank" href="http://www.lvmama.com/comment/<@s.property value="comPlace.placeId"/>-1">
                    			<@s.property value="comPlace.name" escape="false"/>点评
                    		</a>
				<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/scenery">
                    			<@s.property value="comPlace.name" escape="false"/>旅游景点
                    		</a>
				<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/dish">
                    			<@s.property value="comPlace.name" escape="false"/>美食
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/hotel">
                    			<@s.property value="comPlace.name" escape="false"/>住宿
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/traffic">
                    			<@s.property value="comPlace.name" escape="false"/>交通
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/entertainment">
                    			<@s.property value="comPlace.name" escape="false"/>娱乐
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/shop">
                    			<@s.property value="comPlace.name" escape="false"/>购物
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/weekendtravel">
                    			<@s.property value="comPlace.name" escape="false"/>行程
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/photo">
                    			<@s.property value="comPlace.name" escape="false"/>摄影
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/maps">
                    			<@s.property value="comPlace.name" escape="false"/>地图
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/weather">
                    			<@s.property value="comPlace.name" escape="false"/>天气
                    		</a>
                    	</@s.if> 
                    	                   	
                    	<!--  景点   -->
                    	<@s.if test='comPlace!=null && comPlace.stage=="2"'>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>">
                    			<@s.property value="comPlace.name" escape="false"/>门票
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/package">
                    			<@s.property value="comPlace.name" escape="false"/>自由行
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/hotels">
                    			<@s.property value="comPlace.name" escape="false"/>酒店预订
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/line">
                    			<@s.property value="comPlace.name" escape="false"/>跟团游
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/guide/place/<@s.property value="comPlace.pinYinUrl"/>/">
                    			<@s.property value="comPlace.name" escape="false"/>旅游攻略
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/comment/<@s.property value="comPlace.placeId"/>-1">
                    			<@s.property value="comPlace.name" escape="false"/>点评
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/dish">
                    			<@s.property value="comPlace.name" escape="false"/>美食
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/hotel">
                    			<@s.property value="comPlace.name" escape="false"/>住宿
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/traffic">
                    			<@s.property value="comPlace.name" escape="false"/>交通
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/entertainment">
                    			<@s.property value="comPlace.name" escape="false"/>娱乐
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/shop">
                    			<@s.property value="comPlace.name" escape="false"/>购物
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/weekendtravel">
                    			<@s.property value="comPlace.name" escape="false"/>行程
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/photo">
                    			<@s.property value="comPlace.name" escape="false"/>摄影
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/maps">
                    			<@s.property value="comPlace.name" escape="false"/>地图
                    		</a>
                    	</@s.if>
                    	
                    	<!--  酒店   -->
                    	<@s.if test='comPlace!=null && comPlace.stage=="3"'>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>">
                    			<@s.property value="comPlace.name" escape="false"/>预订
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/comment/<@s.property value="comPlace.placeId"/>-1">
                    			<@s.property value="comPlace.name" escape="false"/>点评
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/dish">
                    			<@s.property value="comPlace.name" escape="false"/>美食
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/hotel">
                    			<@s.property value="comPlace.name" escape="false"/>住宿
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/traffic">
                    			<@s.property value="comPlace.name" escape="false"/>交通
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/entertainment">
                    			<@s.property value="comPlace.name" escape="false"/>娱乐
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/shop">
                    			<@s.property value="comPlace.name" escape="false"/>购物
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/weekendtravel">
                    			<@s.property value="comPlace.name" escape="false"/>行程
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/travel/<@s.property value="comPlace.pinYinUrl"/>/photo">
                    			<@s.property value="comPlace.name" escape="false"/>摄影
                    		</a>
                    		<a target="_blank" href="http://www.lvmama.com/dest/<@s.property value="comPlace.pinYinUrl"/>/maps">
                    			<@s.property value="comPlace.name" escape="false"/>地图
                    		</a>
                    	</@s.if>
                           
                    </dd>                    
                </dl>
                
                <#if comments??>
                <ul>
                    <li class="comment">
                        <#include "/WEB-INF/pages/product/newdetail/buttom/cmtLatitudeScoreInfo.ftl">
                    </li>
                    <li>                   
                        <table width="595" border="0" cellspacing="0" cellpadding="0" class="users_comment">                      
                          <tr>
                            <td colspan="3"><a rel="nofollow" href="http://www.lvmama.com/comment/${comPlace.placeId}-1" target="_blank" class="more">查看更多点评&gt;&gt;</a>网友点评</td>
                          </tr>                      
                          <@s.iterator value="comments" status="sts">
                          <tr>
                            <td><a rel="nofollow" href=http://www.lvmama.com/comment/${commentId} target="_blank"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(28,content)"  escape="false"/></a></td>
                            <td><@s.if test="userName!=null && userName!=''"><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(8,userNameExp)" /></@s.if><@s.else>匿名</@s.else></td>
                            <td><@s.date name="createdTime" format="yyyy-MM-dd"/></td>
                          </tr>
                          </@s.iterator>
                        </table>                    
                    </li>
                </ul>
                </#if>
                
            </div><!--dest_intelligence end-->
