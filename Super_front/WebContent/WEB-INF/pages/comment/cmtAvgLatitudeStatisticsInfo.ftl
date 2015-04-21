 <ul class="comments">
  	<@s.if test="cmtLatitudeStatisticsList != null">
	   <@s.iterator value="cmtLatitudeStatisticsList" id="cmtLatitudeStatistics">
	      <li><strong class="com_des">${cmtLatitudeStatistics.latitudeName}:</strong><span class="progressBar">
	      <i class="achiveBar" style="width:${(cmtLatitudeStatistics.avgScore/cmtLatitudeStatistics.maxScore)*100}%"><s></s></i>
	      <small></small></span>#{cmtLatitudeStatistics.avgScore;m1M1}分</li>
	   </@s.iterator>
	 </@s.if>
 </ul>
