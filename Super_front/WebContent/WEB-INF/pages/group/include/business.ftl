<!--business-->
<!--<dl class="business">
        	<dt>商务合作</dt>
            <dd class="right-margin"><img src="http://pic.lvmama.com/img/test/img1.jpg" /></dd>
            <dd><img src="http://pic.lvmama.com/img/test/img2.jpg" /></dd>
            <dd class="right-margin"><img src="http://pic.lvmama.com/img/test/img3.jpg" /></dd>
            <dd><img src="http://pic.lvmama.com/img/test/img4.jpg" /></dd>
</dl>    -->


	 <@s.if test="${stationValue=='main'}">
	<!-- 右横幅 -->
	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('4df1901e0e360e910001','js',null)"/> 
	<!-- 右横幅/End -->
            
     </@s.if>  
  	<@s.if test="${stationValue=='bj'}">
    		
    	
    	<!-- 右横幅 -->
			<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('4df58990390ae6150001','js',null)"/> 
        <!-- 右横幅/End -->
    
    </@s.if>
    <@s.if test="${stationValue=='sc'}">
    
        <!-- 右横幅 -->
        			<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('pe1a9eef4511fc900001','js',null)"/> 
        
        <!-- 右横幅/End -->
    
    </@s.if>
    <@s.if test="${stationValue=='gd'}">
    
        <!-- 右横幅 -->
        	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('4df5a4c43d98efaf0001','js',null)"/> 
        <!-- 右横幅/End -->
    
    </@s.if>
