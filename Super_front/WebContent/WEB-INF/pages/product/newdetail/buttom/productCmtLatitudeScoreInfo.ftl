<div class="c_w_score">
      <p class="c_p_link">      
      <span class="com_StarValueCon total_val_posi">
      <@s.if test="productCommentStatistics != null && productCommentStatistics.commentCount!=0">
      <font><em>#{productCommentStatistics.avgScore;m1M1}</em>分</font><s class="star_bg cur_def"><i class="ct_Star${productCommentStatistics.roundHalfUpOfAvgScore}"></i></s>
      </@s.if>
      <@s.else>
      <font><em>0</em>分</font><s class="star_bg cur_def"><i class="ct_Star0"></i></s>
      </@s.else>
      </span>
      <a target="_blank" href="http://www.lvmama.com/comment/comment/listCmtsOfProduct.do?productId=${prodCProduct.prodProduct.productId}"><@s.if test="productCommentStatistics!=null"><@s.property value='productCommentStatistics.commentCount'/></@s.if><@s.else>0</@s.else>封点评</a></p>
		 <ul class="comments">
		   <@s.iterator value="productCmtLatitudeStatisticsVOList" id="cmtLatitudeStatistics">
		      <li><strong class="com_des">${cmtLatitudeStatistics.latitudeName}:</strong><span class="progressBar">
		      <i class="achiveBar" style="width:${(cmtLatitudeStatistics.avgScore/cmtLatitudeStatistics.maxScore)*100}%"><s></s></i>
		      <small></small></span>#{cmtLatitudeStatistics.avgScore;m1M1}分</li>
		   </@s.iterator>
		 </ul>
      	<div style="width:500px;" class="c_love_tips fl">
            <p>游玩后发表点评，
                <#if couponEnabled == "Y">
                返<b class="orange">￥<@s.property value="prodCProduct.prodProduct.cashRefundY" escape="false"/></b>奖金，
                </#if>
                赠100积分，精华点评追加150积分！<a href="http://www.lvmama.com/public/help_center_146" target="_blank" hidefocus="false">规则详情&gt;&gt;</a></p>
            <#if couponEnabled == "Y">
            <p>我购买过该产品，<a href="http://www.lvmama.com/myspace/share/comment.do" class="btn btn-small btn-pink white" hidefocus="false">发点评返奖金</a></p>
            </#if>
        </div>
</div><!--c_w_score end-->
