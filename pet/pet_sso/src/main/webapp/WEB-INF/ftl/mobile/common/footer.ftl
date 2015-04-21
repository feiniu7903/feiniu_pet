     <div class="footer"> 
    	<p class="nav"><a href="${index}">首页</a>|<a href="${wapIndex}/m/feedback/tofeed.do">意见反馈</a>|<a href="${wapHost}/m/help/index.do">帮助</a>|<a href="${wapHost}/m/help/location.do?id=about">关于</a><br /> 
        <a href="${wapHost}/m/myorder/list.do">我的订单</a>|<a href="${wapHost}/m/account/info.do">我的帐户</a><br /> 
        <span> 
        <#if user!=null>
        <#if user.mobileNumber!=null>
        ${user.mobileNumber},你好！</span><br />
        <#else>
         ${user.userName},你好！</span><br />
        </#if>
        </#if>
        </span>
    	<span>热线电话：</span><a href="wtai://wp/mc;10106060" class="tel-400">10106060</a>
    <br />
    访问主站：<a href="http://www.lvmama.com">www.lvmama.com</a>
    </p>   
    </div> 