 	<i id="row_1" class="pkg-maodian">&nbsp;</i>
        <h3 class="h3_tit"><span>产品特色</span></h3>
        <div class="row pro_special">
            <div class="pro_special_mid">                        
            <#if viewPage?? && viewPage.hasContent('FEATURES')>
            ${viewPage.contents.get('FEATURES').content}
            </#if>
            </div><!--pro_special_mid end-->
        </div><!--pro_special end-->
