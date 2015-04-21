<style>.destnt_share{ position: absolute;right: 0; top: 1px; width:212px;} 
</style>
<div class="main-container">
<!--============= 当前位置导航 S ================-->
	<div id="s2-site-nav">
	<div class="destnt_share"> 
<!-- Baidu Button BEGIN -->
<div id="bdshare" class="bdshare_b" style="line-height: 12px;">
<img src="http://bdimg.share.baidu.com/static/images/type-button-5.jpg?cdnversion=20120831" />
<a class="shareCount"></a>
</div>
<script type="text/javascript" id="bdshare_js" data="type=button&amp;uid=162654" ></script>
<script type="text/javascript" id="bdshell_js"></script>
<script type="text/javascript">
document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000);
</script>
<!-- Baidu Button END -->
</div>
	<span>您当前所处的位置:</span>
    	<ul class="quick-menu">
        	<@s.iterator value="navigationMap.get('placeNavigation')" status="sta" id="nav">
			        	<li>
			        		<@s.if test='defaultUrl!=null'>
			            		<a class="menu-hd" href="<@s.property value="defaultUrl"/>" target="_top"><@s.property value="name"/><b></b></a>
			            	</@s.if>	
			            	<@s.else>
			            		<a class="menu-hd" href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>" target="_top"><@s.property value="name"/><b></b></a>
			            	</@s.else>
			            	
			            	<div class="menu-bd">
			                    <dl>
			                        <dd>
			                        <@s.if test='#sta.isFirst()'>
			                          	<a href="http://www.lvmama.com/dest/ouzhou_ouzhou" target="_top">欧洲</a>
			                            <a href="http://www.lvmama.com/public/asia" target="_top">亚洲</a>
			                            <a href="http://www.lvmama.com/dest/feizhou_feizhou" target="_top">非洲</a>
			                            <a href="http://www.lvmama.com/dest/beimeizhou_beimeizhou" target="_top">北美洲</a>
			                            <a href="http://www.lvmama.com/dest/nanmeizhou_nanmeizhou" target="_top">南美洲</a>
			                            <a href="http://www.lvmama.com/dest/dayangzhou_dayangzhou" target="_top">大洋洲</a>
			                        </@s.if>
			                        <@s.else>
			                           <@s.iterator value="(navigationMap.get('placeDest')).get(placeId)">
			                           		
			                            		<@s.if test='defaultUrl!=null'>
								            		<a href="<@s.property value="defaultUrl"/>" target="_top">
								            		 <@s.if test='name==#nav.name'>
			                            			 		<span class="current-pla"><@s.property value="name"/></span>
			                           		  		  </@s.if>
			                           		  		  <@s.else>
			                           		  		  		<@s.property value="name"/>
			                           		  		  </@s.else>
								            		 </a>
								            	</@s.if>	
								            	<@s.else>
								            		<a href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>" target="_top">
								            		 <@s.if test='name==#nav.name'>
			                            			 		<span class="current-pla"><@s.property value="name"/></span>
			                           		  		  </@s.if>
			                           		  		  <@s.else>
			                           		  		  		<@s.property value="name"/>
			                           		  		  </@s.else>
			                           		  		  </a>
								            	</@s.else>
			                            </@s.iterator>
			                        </@s.else>
			                        </dd>
			                    </dl>
			                </div>
			            </li>	            
            </@s.iterator>
            <@s.if test="navigationMap.get('place')!=null">
     			 <@s.if test='"0".equals((navigationMap.get("place")).stage)'>
     			<li><a target="_top" <@s.if test='"0".equals((navigationMap.get("place")).stage)'>class="menu-hd"</@s.if> href="http://www.lvmama.com/dest/<@s.property value="navigationMap.get('place').pinYinUrl"/>"><@s.property value="navigationMap.get('place').name"/><b></b></a>
     			<div class="menu-bd">
                    <dl>
                        <dd>
                          	<a href="http://www.lvmama.com/dest/ouzhou_ouzhou" target="_top">欧洲</a>
                            <a href="http://www.lvmama.com/public/asia" target="_top">亚洲</a>
                            <a href="http://www.lvmama.com/dest/feizhou_feizhou" target="_top">非洲</a>
                            <a href="http://www.lvmama.com/dest/beimeizhou_beimeizhou" target="_top">北美洲</a>
                            <a href="http://www.lvmama.com/dest/nanmeizhou_nanmeizhou" target="_top">南美洲</a>
                            <a href="http://www.lvmama.com/dest/dayangzhou_dayangzhou" target="_top">大洋洲</a>
                        </dd>
                    </dl>
                </div>
     			</li>
     			  </@s.if>
     			  <@s.if test='navigationMap.get("place").stage=="2" || navigationMap.get("place").stage=="3"'>
     			  <li><a target="_top"  href="http://www.lvmama.com/dest/<@s.property value="navigationMap.get('place').pinYinUrl"/>"><@s.property value="navigationMap.get('place').name"/><b></b></a></li>
     			   </@s.if>
    		</@s.if>
    </div>
    
     		
