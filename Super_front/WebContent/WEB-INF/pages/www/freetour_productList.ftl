<div class="pane">
    <h4><span>近期推荐</span></h4>
    <div class="paneRecent">
    <@s.iterator value="productListFreenessRecommend">
        <#include "/WEB-INF/pages/www/common_productList_recommend.ftl">
    </@s.iterator>
    </div><!--paneRecent end-->
    <@s.if test="productListFreeness.size>0">
    <h4><span>自由行（门票+酒店）</span></h4>  
    <ul>
    <@s.iterator value="productListFreeness">
        <#include "/WEB-INF/pages/www/common_productList.ftl">
    </@s.iterator>
    </ul>
    <p><a id="moreFree" class="moreLink" href="#">更多自由行&gt;&gt;</a></p>
    </@s.if>
</div><!--pane end-->
