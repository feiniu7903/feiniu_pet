	<!--Ad-->
    <div style="margin:0 0 10px;" class="abroadban">
    


	 <@s.if test="${stationValue=='main'}">
	 	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('qe1d137f861cf7920001','js',null)"/>
     </@s.if>  
  	<@s.if test="${stationValue=='bj'}">
    	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('fe1d138f29ec4ab50001','js',null)"/>
    </@s.if>
    <@s.if test="${stationValue=='sc'}">
    	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('ve1d13b2fe811c5d0001','js',null)"/>
    </@s.if>
    <@s.if test="${stationValue=='gd'}">
    	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('be1d139ecfc680740001','js',null)"/>
    </@s.if>




    </div>

