<!--用户最新点评小列表-->
var _comentContent = "";
<#if lastestCommentsList!=null && lastestCommentsList.size()!=0>
    <@s.iterator value="lastestCommentsList" status="cmt">
        _comentContent = _comentContent + '<div class="spacebox"><a href=http://www.lvmama.com/dest/${pinYinUrl}><img src="http://www.lvmama.com/dest/img/myspace/img_120_90.jpg" class="spaceboxpro" /></a><div class="spacetxt"><div class="spaceboxmt"><span><@s.date name="createdTime" format="MM-dd hh:mm"/></span>点评的景点：<a href=http://dest.lvmama.com/place${placeId}>${placeName}</a></div><div class="spacecmt">评价：<@s.if test='isRecomend=="Y"'><img src="http://pic.lvmama.com/cmt/img/yes.gif"/></@s.if><@s.else><img src="http://pic.lvmama.com/cmt/img/no.gif"/></@s.else></div><p><@s.property value="contentDelEnter"/></p><a href=http://www.lvmama.com/comment/${commentId} target="_blank">详情</a>&nbsp;&nbsp;回复<strong>(${replyCount})</strong>&nbsp;&nbsp;有用<strong>(${usefulCount})</strong> </div></div>';
    </@s.iterator>
</#if> 
document.write(_comentContent);
