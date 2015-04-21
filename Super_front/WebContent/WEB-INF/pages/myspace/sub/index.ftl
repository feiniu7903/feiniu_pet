<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的驴妈妈-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body>
<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
<div class="lv-nav wrap"><p><a class="current">我的驴妈妈</a></p></div>
<div class="wrap ui-content lv-bd">
	<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
	<div class="lv-content">
		<!--个人信息-->
		<div class="ui-box mod-top">
			<div class="ui-box-container">
				<div class="my-account">
					<div class="my-account-info">
					<div class="codeapp_box">
                        <span class="codeapp">
                             <img class="erweima"  src='/callback/generateCodeImage.do?userid=<@s.property value="user.userNo" />' />
                        </span>
                    </div>
						<p class="my-welcome"><b><@s.property value="user.userName" /></b><span>，欢迎来到驴妈妈！</span></p>
						<p class="verification">
							<@s.if test="null == user.email">
								<span class="email-verified lv-nodo">登录邮箱： <i class="lv-cc">未绑定</i><a href="/myspace/userinfo/email_bind.do" class="link-btn ui-btn ui-btn1"><i>立即绑定</i></a></span>
							</@s.if>
							<@s.if test='null != user.email && !"Y".equals(user.isEmailChecked)'>
								<span class="email-verified lv-waitdo">登录邮箱：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(user.email)" /> <i class="lv-cc">(未验证)</i><a href="/myspace/userinfo/email_send.do" class="link-btn  ui-btn ui-btn1"><i>立即验证</i></a></span>
							</@s.if>
							<@s.if test='null != user.email && "Y".equals(user.isEmailChecked)'>
								<span class="email-verified lv-done">登录邮箱：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(user.email)" /> <i class="lv-cc">(已验证)</i></span>
							</@s.if>
							<@s.if test="null == user.mobileNumber">
								<span class="phone-verified lv-nodo">手机号： <i class="lv-cc">未绑定</i><a href="/myspace/userinfo/phone.do" class="link-btn ui-btn ui-btn1"><i>立即绑定</i></a></span>
							</@s.if>
							<@s.if test='null != user.mobileNumber && !"Y".equals(user.isMobileChecked)'>
								<span class="phone-verified lv-waitdo">手机号：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /><i class="lv-cc">(未验证)</i><a href="/myspace/userinfo/phone.do" class="link-btn ui-btn ui-btn1"><i>立即验证</i></a></span>
							</@s.if>
							<@s.if test='null != user.mobileNumber && "Y".equals(user.isMobileChecked)'>
								<span class="phone-verified lv-done">手机号：<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /> <i class="lv-cc">(已验证)</i></span>
							</@s.if>
							
						</p>
					</div>
					<div class="tips-yellow">
						<p>交易提醒：<a href="/myspace/order.do" class="tips-msg">待支付订单</a> <span class="lv-c1">(<b>${unPaymentOrderNumber}</b>) </span><br>
						消息提醒：<a href="/myspace/account/points.do" class="tips-msg">可用积分</a><span class="lv-c1">(<b><@s.property value="user.point"/></b>) </span>
						<a href="/myspace/account/coupon.do" class="tips-msg">可用优惠券</a><span class="lv-c1">(<b><@s.property value="usefulCoupon"/></b>)</span>
						<a href="/myspace/share/comment.do" class="tips-msg">待点评</a><span class="lv-c1">(<b><@s.property value="waittingCommentNumber"/></b>)</span>
						<a href="/myspace/message.do" class="tips-msg"><span class="lvapp-icon icon-apps16-1008"></span><i class="ie6_blank"></i>站内消息</a><span class="lv-c1" id="myspace_message_count_span">(<b id="myspace_message_count_id"></b>)</span>
					</div>
				</div>
			</div>
		</div><!--ui-box mod-top-->

		<!-- 订单列表 -->
		<div class="ui-box mod-plist index-plist">
		    <div class="ui-box-title"><span class="view_more fr"><a href="/myspace/order.do">查看所有订单</a><i>&gt;&gt;</i></span>
			<h3>近期订单</h3>
		    </div>
		    <#include "/WEB-INF/pages/myspace/sub/order/myspaceOrder_inner.ftl"/>
		</div>
		<!-- <<订单列表 --> 
		 
		<!-- 猜你喜欢>> -->
		<div class="hr_c"></div>
		<div class="ui-box view-list scoll-box" style="display:none">
			<div class="ui-box-title"><span id="scoll-navi" class="navi"><a class="active"></a><a></a></span><h3>猜你喜欢</h3></div>
			<div id="scoll-content">
				<div class="ajax-loader">
					<div class="ui-overlay"></div>
					<p class="loading"><img src="http://pic.lvmama.com/img/mylvmama/ajax-loading.gif" width="23" height="23">　加载中，请稍等…</p>
				</div>
				<a class="prev"></a><a class="next"></a>
				<div id="scrollable" class="view-box guess_p-vlist scrollable clearfix" >
					<div class="items" id="ruiguang"></div>
				</div>
			</div>
		</div><!-- <<猜你喜欢 -->	
		<div class="lv-ad mod-c-ad">
		    <p><!-- 我的驴妈妈 - 右大旗1 -->
		    <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxMnxvdGhlcnBhZ2VfMjAxMnxvdGhlcnBhZ2VfYWNjb3VudF8yMDEyX2ZsYWcwMSZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script>
            <!-- 我的驴妈妈 - 右大旗1/End -->
            <!-- 我的驴妈妈 - 右大旗2 -->
		    <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxMnxvdGhlcnBhZ2VfMjAxMnxvdGhlcnBhZ2VfYWNjb3VudF8yMDEyX2ZsYWcwMiZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script>

            <!-- 我的驴妈妈 - 右大旗2/End --></p>
		</div>		
	</div>
</div>

<script type="text/javascript">
	function getCookie(name){
         var strCookie=document.cookie;
         var arrCookie=strCookie.split("; ");
         for(var i=0;i<arrCookie.length;i++){
               var arr=arrCookie[i].split("=");
               if(arr[0]==name)return arr[1];
         }
         return "";
	}
	$(function(){
	   var read = getCookie('MY_SPACE_READ_IS_TRUE_<@s.property value="user.userNo" />');
	   if(read!="true" && read!=true){
		$("a.lv-guide").click();
		var date=new Date();
        date.setTime(date.getTime()+10*365*1000*3600*24);
		var cookieString ='MY_SPACE_READ_IS_TRUE_<@s.property value="user.userNo" />='+escape("true")+";domain=lvmama.com;path=/;expires="+date.toGMTString();
		document.cookie=cookieString;
		var strCookie=document.cookie;
	   }
	});
</script>


<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
<script>
      cmCreatePageviewTag("会员中心首页", "D0001", null, null);
 </script>
</body>
</html>