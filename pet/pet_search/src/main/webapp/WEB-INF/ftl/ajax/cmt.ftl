<#if cmtTitleStatisticsVO?? && cmtTitleStatisticsVO.avgScore gt 0 >
<p class="score-total">
<dfn><i>${cmtTitleStatisticsVO.avgScore?string("#.00")}</i>分</dfn>
<span class="re-star"><span class="starbg"><i style="width:${(cmtTitleStatisticsVO.avgScore*100/5)?string("#.##")}%"></i></span></span>
<a rel="nofollow" class="comment-num" href="<#if cmtTitleStatisticsVO.stage=='2'>http://ticket.lvmama.com/scenic-${cmtTitleStatisticsVO.placeId}#comments<#else>http://www.lvmama.com/comment/${cmtTitleStatisticsVO.placeId}-1</#if>" target="_blank">${cmtTitleStatisticsVO.commentCount}条评论</a>
</p>
<p class="score-item">
<#list cmtLatitudeStatisticsList as item>
	${item.latitudeName}：${item.avgScore?string("#.00")}分<br>
</#list>
</p>
</#if>