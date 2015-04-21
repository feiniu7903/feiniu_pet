<dl>
    <dt><strong>总体评价：</strong>
	   
	    <@s.if test="cmtCommentStatisticsVO != null && cmtCommentStatisticsVO.commentCount!=0">
			<i class="commentsStar${cmtCommentStatisticsVO.roundHalfUpOfAvgScore}"></i>${cmtCommentStatisticsVO.avgScoreStr}&nbsp;分
	   </@s.if><@s.else>
	    	0&nbsp;分
	    </@s.else>
    </dt>
    <@s.if test="cmtCommentStatisticsVO != null && cmtCommentStatisticsVO.commentCount!=0">
	    <dd><i class="commentsStar5"></i>    
	    <span class="progressBar"><i class="achiveBar" style="width:${(cmtCommentStatisticsVO.fiveScoreCount/cmtCommentStatisticsVO.commentCount)*100}%"></i></span>&nbsp;
	    	${cmtCommentStatisticsVO.fiveScoreCount}条
	    </dd>
	    
	    <dd><i class="commentsStar4"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:${(cmtCommentStatisticsVO.fourScoreCount/cmtCommentStatisticsVO.commentCount)*100}%"></i></span>&nbsp;
	    	${cmtCommentStatisticsVO.fourScoreCount}条
	    </dd>
	    
	    <dd><i class="commentsStar3"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:${(cmtCommentStatisticsVO.threeScoreCount/cmtCommentStatisticsVO.commentCount)*100}%"></i></span>&nbsp;
	    	${cmtCommentStatisticsVO.threeScoreCount}条
	    </dd>
	    
	    <dd><i class="commentsStar2"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:${(cmtCommentStatisticsVO.twoScoreCount/cmtCommentStatisticsVO.commentCount)*100}%"></i></span>&nbsp;
	    	${cmtCommentStatisticsVO.twoScoreCount}条
	    </dd>
	    
	    <dd><i class="commentsStar1"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:${(cmtCommentStatisticsVO.oneScoreCount/cmtCommentStatisticsVO.commentCount)*100}%"></i></span>&nbsp;
	    	${cmtCommentStatisticsVO.oneScoreCount}条
	    </dd>
    </@s.if>
    <@s.else>
	    <dd><i class="commentsStar5"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0条</dd>
	    
	    <dd><i class="commentsStar4"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0条</dd>
	    
	    <dd><i class="commentsStar3"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0条</dd>
	    
	    <dd><i class="commentsStar2"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0条</dd>
	    
	    <dd><i class="commentsStar1"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0条</dd>
   </@s.else>
</dl>
