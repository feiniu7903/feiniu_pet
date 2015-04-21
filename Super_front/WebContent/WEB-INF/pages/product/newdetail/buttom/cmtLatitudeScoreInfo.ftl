<p><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(20, prodCProduct.prodProduct.productName)" escape="false"/> 共有点评<b><@s.if test="commentStatistics!=null"><@s.property value='commentStatistics.commentCount'/></@s.if><@s.else>0</@s.else></b> 个</p>
<dl>
    <dt><strong>总体评价：</strong>	   
	    <@s.if test="commentStatistics != null && commentStatistics.commentCount!=0">
			<i class="commentsStar${commentStatistics.roundHalfUpOfAvgScore}"></i>&nbsp;&nbsp;#{commentStatistics.avgScore;m1M1}&nbsp;分
	   </@s.if><@s.else>
	    	<i class="commentsStar0"></i>&nbsp;&nbsp;0&nbsp;分
	    </@s.else>
    </dt>
    <@s.if test="commentStatistics != null && commentStatistics.commentCount!=0">
	    <dd><i class="commentsStar5"></i>    
	    <span class="progressBar"><i class="achiveBar" style="width:${(commentStatistics.fiveScoreCount/commentStatistics.commentCount)*100}%"></i></span>&nbsp;
	    	${commentStatistics.fiveScoreCount}&nbsp;条
	    </dd>
	    
	    <dd><i class="commentsStar4"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:${(commentStatistics.fourScoreCount/commentStatistics.commentCount)*100}%"></i></span>&nbsp;
	    	${commentStatistics.fourScoreCount}&nbsp;条
	    </dd>
	    
	    <dd><i class="commentsStar3"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:${(commentStatistics.threeScoreCount/commentStatistics.commentCount)*100}%"></i></span>&nbsp;
	    	${commentStatistics.threeScoreCount}&nbsp;条
	    </dd>
	    
	    <dd><i class="commentsStar2"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:${(commentStatistics.twoScoreCount/commentStatistics.commentCount)*100}%"></i></span>&nbsp;
	    	${commentStatistics.twoScoreCount}&nbsp;条
	    </dd>
	    
	    <dd><i class="commentsStar1"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:${(commentStatistics.oneScoreCount/commentStatistics.commentCount)*100}%"></i></span>&nbsp;
	    	${commentStatistics.oneScoreCount}&nbsp;条
	    </dd>
    </@s.if>
    <@s.else>
	    <dd><i class="commentsStar5"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0&nbsp;条</dd>
	    
	    <dd><i class="commentsStar4"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0&nbsp;条</dd>
	    
	    <dd><i class="commentsStar3"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0&nbsp;条</dd>
	    
	    <dd><i class="commentsStar2"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0&nbsp;条</dd>
	    
	    <dd><i class="commentsStar1"></i>
	    <span class="progressBar"><i class="achiveBar" style="width:0%"></i></span>&nbsp;0&nbsp;条</dd>
   </@s.else>
</dl>
