     <div class="footer">
    	<p class="nav"><a href="${wapIndex}">首页</a>|<a href="${contentPath}/m/feedback/tofeed.do">意见反馈</a>|<a href="${contentPath}/m/help/index.do">帮助</a>|<a href="${contentPath}/m/help/location.do?id=about">关于</a><br /> 
        <a href="${contentPath}/m/myorder/list.do">我的订单</a>|<a href="${contentPath}/m/account/info.do">我的账户</a>|
         <#if user!=null><a href="${sso}/logout?serviceId=${wapIndex}">退出登录</a><br />
         <#else>
         <a href="${sso}/wap/toLogin.do">登录/注册</a></a> 
         </#if>
        <span> 
        <#if user!=null>
        ${wapUserName},你好！</span>
        </#if>
        </span>
        <br/>
    	<span>热线电话：</span><a href="wtai://wp/mc;10106060" class="tel-400">10106060</a>
        <br />
        访问主站：<a href="http://www.lvmama.com">www.lvmama.com</a>
        </p> 
    </div> 
