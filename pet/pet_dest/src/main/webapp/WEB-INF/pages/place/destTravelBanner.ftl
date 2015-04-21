	 <!--- 广告 -->
	 <div class="dstnt_banner1">
		<!-- 目的地_左侧横幅01 -->
        <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxMnxkZXN0X2NpdHlfMjAxMnxkZXN0X2NpdHlfMjAxMl9ib3R0dW0wMSZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script>
        <!-- 目的地_左侧横幅01 -->
	</div>

	<!--- 点评列表 -->
 	<div class="dstnt_left_box1">
         <h4 class="dstnt_left_boxtit">
              	网友对<label class="label_red"><@s.property value="currentPlace.name"/></label>的评分
         </h4>
         	<div class="dstnt_left_boxcont">
         	
			   <@s.if test="cmtTitleStatisticsVO != null && cmtTitleStatisticsVO.commentCount!=0">
                     <div class="dstnt_grade_l">
	                     <@s.if test="cmtTitleStatisticsVO != null && cmtTitleStatisticsVO.commentCount!=0">
	                          <span class="dstnt_grade" onclick="location.href='#dstnt_dpwhere'"><em>#{cmtTitleStatisticsVO.avgScore;m1M1}</em>分</span>
							  <s class="dstnt_star star_bg cur_def"><i class="ct_Star${cmtTitleStatisticsVO.roundHalfUpOfAvgScore}"></i></s>
	                     </@s.if>
	                     <@s.else>
	                     	  <span class="dstnt_grade" onclick="location.href='#dstnt_dpwhere'"><em>0</em>分</span>
	                          <s class="dstnt_star star_bg cur_def"><i class="ct_Star0"></i></s>
	                     </@s.else>
	                     <@s.if test="cmtTitleStatisticsVO != null">
	                            <a class="dstnt_cmt_txt" target="_blank" rel="nofollow" href="http://www.lvmama.com/comment/${cmtTitleStatisticsVO.placeId!?c}-1"><em>${cmtTitleStatisticsVO.commentCount!?c}</em>封点评</a>
	                      </@s.if>
	                      <@s.else>
	                           <span>0封点评</span>
	                      </@s.else> 
                     </div>
          		</@s.if>
          		
				<@s.else>
						<span>目前还没人发表点评哦，快来成为第一人吧。</span>
				</@s.else>
				
                <div class="dstnt_grade_r">
                    <dl class="dstnt_grade_r_dl" onclick="location.href='#dstnt_dpwhere'">
                    	 <@s.if test="cmtLatitudeStatisticsList != null">
							 <@s.iterator value="cmtLatitudeStatisticsList" id="cmtLatitudeStatistics">
							 	 <#if cmtLatitudeStatistics??>
							     	<dt>${cmtLatitudeStatistics.latitudeName}：</dt><dd>#{cmtLatitudeStatistics.avgScore;m1M1}分</dd>
							      </#if >
							 </@s.iterator>
						 </@s.if>
                    </dl>
                </div>
               </div><!--boxcont-->
               
              <a class="dstnt_cmt_btn" rel="nofollow" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${currentPlace.placeId!?c}" target="_blank">写点评</a>
  		</div>
            
          <!-- 攻略 --> 
          <div class="dstnt_left_box1" id="dstnt_guide" style="display:none">
             <h4 class="dstnt_left_boxtit">
               <a rel="nofollow" href="http://www.lvmama.com/guide/place/${currentPlace.pinYinUrl}" target="_blank" class="dstnt_more">更多&gt;&gt;</a>
                <@s.property value="currentPlace.name"/>旅游攻略  
             </h4>
              <div class="dstnt_left_boxcont1" id="dstnt_guidelist"></div><!--cont-->
          </div><!--leftbox--> <!--上海攻略推荐-->  

