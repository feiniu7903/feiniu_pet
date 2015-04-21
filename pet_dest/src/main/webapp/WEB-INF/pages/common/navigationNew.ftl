<!-- JiaThis Button END --> 

<div class="p-crumbs oldstyle wrap">
    <div class="crumbs-nav">
        <span class="position">您当前所处的位置：</span>
        <ul class="crumbs-menu">
        <@s.iterator value="navigationDataMap.get('placeNavigation')" status="sta" id="nav">
        	<li>
         	    <span class="xicon crumbs-arrow"></span><i></i>
                <a class="menu-link"  <@s.if test='defaultUrl!=null'>  href="<@s.property value="defaultUrl"/>" </@s.if> <@s.else>  href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>"  </@s.else>  > <@s.property value="name"/></a>
                <div class="xmenu-sub">
                    <@s.if test='#sta.isFirst()'>
                      	<a href="http://www.lvmama.com/dest/ouzhou_ouzhou" target="_top">欧洲</a>
                        <a href="http://www.lvmama.com/public/asia" target="_top">亚洲</a>
                        <a href="http://www.lvmama.com/dest/feizhou_feizhou" target="_top">非洲</a>
                        <a href="http://www.lvmama.com/dest/beimeizhou_beimeizhou" target="_top">北美洲</a>
                        <a href="http://www.lvmama.com/dest/nanmeizhou_nanmeizhou" target="_top">南美洲</a>
                        <a href="http://www.lvmama.com/dest/dayangzhou_dayangzhou" target="_top">大洋洲</a>
                    </@s.if>
                    <@s.else>
                        <@s.iterator value="(navigationDataMap.get('placeDest')).get(placeId)">
                       		
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
                </div>
            </li>
          </@s.iterator>
           <@s.if test="navigationDataMap.get('place')!=null">
     			  <@s.if test='"0".equals((navigationDataMap.get("place")).stage)'>
	     			<li> 
	     			<span class="crumbs-arrow"></span><i></i><a class="menu-link"  <@s.if test='defaultUrl!=null'>  href="<@s.property value="defaultUrl"/>" </@s.if>	
					     <@s.else>  href="http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>"  </@s.else>  > <@s.property value="name"/></a>
	     			<div class="xmenu-sub">
                      	<a href="http://www.lvmama.com/dest/ouzhou_ouzhou" target="_top">欧洲</a>
                        <a href="http://www.lvmama.com/public/asia" target="_top">亚洲</a>
                        <a href="http://www.lvmama.com/dest/feizhou_feizhou" target="_top">非洲</a>
                        <a href="http://www.lvmama.com/dest/beimeizhou_beimeizhou" target="_top">北美洲</a>
                        <a href="http://www.lvmama.com/dest/nanmeizhou_nanmeizhou" target="_top">南美洲</a>
                        <a href="http://www.lvmama.com/dest/dayangzhou_dayangzhou" target="_top">大洋洲</a>
	                </div>
	     			</li>
     			  </@s.if>
     			  
     			  <@s.if test='navigationDataMap.get("place").stage=="2" || navigationDataMap.get("place").stage=="3"'>
     			  <li><span><@s.property value="navigationDataMap.get('place').name"/></span></li>
     			   </@s.if>
    		</@s.if>
        </ul>
    </div>
</div>
