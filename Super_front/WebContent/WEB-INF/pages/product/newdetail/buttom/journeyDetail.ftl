<@s.iterator value="viewJourneyList" var="vjl">
<div class="day <@s.if test="#vjl.journeyTipsList==null || #vjl.journeyTipsList.size==0">day_rights_none</@s.if>">
    <div class="day_rights">
    <@s.iterator value="#vjl.journeyTipsList" var="jtl">
    <div class="day_right">
        <s class="day_right_angle"></s>
        <h6>${jtl.tipTitle?if_exists}</h6>
        <p>${jtl.tipContent?if_exists}</p>
    </div><!--day_righyd end-->
    </@s.iterator>
    </div><!--day_rights end-->
    <div class="day_left">
        <div class="day_tit">
            <b>
                <strong>第${vjl.seq}天</strong>
                <span>${vjl.title?if_exists}</span>                                
                 <#if (vjl.traffic)??>
                    <#if (vjl.traffic?index_of("airplane")>-1)>
                        <i class="icon_aircraft"></i>
                    </#if>
                    <#if (vjl.traffic?index_of("bus")>-1)>
                        <i class="icon_car"></i>
                    </#if>
                    <#if (vjl.traffic?index_of("ship")>-1)>
                        <i class="icon_ship"></i>
                    </#if>
                    <#if (vjl.traffic?index_of("train")>-1)>
                        <i class="icon_train"></i>
                    </#if>
                 </#if>
            </b>
        </div><!--day_tit end-->
        <div class="day_introduction">${vjl.contentBr?default("")}</div>
        <#if (vjl.journeyPictureList)!=null>  
        <div class="day_img">
            <@s.iterator value="#vjl.journeyPictureList" var="jp" status="sts">    
            <@s.if test="#sts.index<2">                           
            
            <img class=<@s.if test="#sts.index==0">"day_img_left"</@s.if><@s.elseif test="#sts.index==1">"day_img_right"</@s.elseif> width="320" height="240" 
                    src="${jp.absoluteUrl?if_exists}" 
                    title="${jp.pictureName?if_exists}">
           
            </@s.if>
            </@s.iterator>
        </div>
        </#if>                
        
        <ul>
            <#if (vjl.dinner)??> <li><b>用餐</b>${vjl.dinner?default("")}  </li></#if>
            <#if (vjl.hotel)??><li><b>住宿</b>${vjl.hotel?default("")}</li></#if>
        </ul>
    </div><!--day_left end-->
</div><!--day end-->
</@s.iterator>