<style>.destnt_share{ position: absolute;right: 0; top: 1px; width:212px;} 
</style>
<div class="main-container">
<!--============= 当前位置导航 S ================-->
	<div id="s2-site-nav">
	<div class="destnt_share"> 
<!-- Baidu Button BEGIN -->
<div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
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
     			 <@s.if test="navigationMap.get('place').stage=='0'">
     			<li><a target="_top" <@s.if test='navigationMap.get("place").stage=="0"'>class="menu-hd"</@s.if> href="http://www.lvmama.com/dest/<@s.property value="navigationMap.get('place').pinYinUrl"/>"><@s.property value="navigationMap.get('place').name"/><b></b></a>
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
    