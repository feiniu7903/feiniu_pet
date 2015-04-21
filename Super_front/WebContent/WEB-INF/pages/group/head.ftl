<input name="pageName" type="hidden" id="pageName"  value="tuangou" />
<script src="http://pic.lvmama.com/js/top/top_common.js"></script>
<div class="hh_shortcut_box">
  <div class="hh_shortcut clearfix">
	<ul class="hh_shortcut_nav clearfix">
		<li class="hh_link">
			<a href="http://www.lvmama.com/myspace/index.do" class="hh_mylvmama" rel="nofollow">我的驴妈妈<i class="hh_icon_triangleB"></i></a>
			<div class="hh_lvmama_sub">
				<a href="http://www.lvmama.com/myspace/order.do" rel="nofollow">我的订单</a>
				<a href="http://www.lvmama.com/myspace/account/points.do" rel="nofollow">我的积分</a>
				<a href="http://www.lvmama.com/myspace/account/coupon.do" rel="nofollow">我的优惠券</a>
			</div>
		</li>
		<li class="hh_link">
			<a href="http://login.lvmama.com/nsso/onlineApplyMemberCard/index.do" class="hh_mylvmama" rel="nofollow" target="_blank">会员卡<i class="hh_icon_triangleB"></i></a>
			<div class="hh_lvmama_sub">
				<a href="http://login.lvmama.com/nsso/onlineApplyMemberCard/index.do" rel="nofollow" target="_blank">申请会员卡</a>
				<a href="http://www.lvmama.com/login/loginBindCard.do" rel="nofollow" target="_blank">激活会员卡</a>
				<a href="http://www.lvmama.com/stored/goStoredSearch.do" rel="nofollow" target="_blank">旅游卡</a>
			</div>
		</li>
		<li><a href="http://www.lvmama.com/points" rel="nofollow">积分商城</a></li>
		<li><a href="http://www.lvmama.com/public/help" rel="nofollow">帮助</a></li>
		<li class="hh_link join_weixin"><a class="hh_mylvmama" href="javascript:void(0);" rel="nofollow"><i class="icon-weixin"></i>+微信</a>
			<div class="hh_lvmama_sub" style="text-align:center;">
				<img src="http://pic.lvmama.com/img/v3/weixin.png" width="80" height="80">
			</div>
		</li>
		<li><a href="http://e.weibo.com/lvmamas" rel="nofollow" target="_blank"><i class="icon-weibo"></i>+微博</a></li>
		<li class="sitemap"><a href="http://www.lvmama.com/public/site_map">网站地图</a></li>
		<li class="hh_icon_phone"><a href="http://shouji.lvmama.com" rel="nofollow" target="_blank">手机版</a></li>
	</ul>
	<span class="hh_loginSpanArea" id="hh_loginSpanArea"></span>
  </div>
</div>
<!--shortcut end-->
<div class="wrap">
	<div class="header">
		<div class="logo">
			<!--============ 主站 =================-->
			<a title="驴妈妈旅游网" href="http://www.lvmama.com"><img alt="驴妈妈旅游网" src="http://pic.lvmama.com/img/tuan/group-logo.gif"></a>
			<strong style="right: 5px"><@s.property value="stationName" escape="false"/></strong>
			<!--============== end ===============-->
			<dl class="change-city" style="left: 70px">
				<dt>
					切换城市<b></b>
				</dt>
				<dd>
					<a  <@s.if test="${stationValue=='main'}"> class="this-city"</@s.if>  href="http://www.lvmama.com/changeLocation/t=www">长三角</a>
					<a  <@s.if test="${stationValue=='bj'}"> class="this-city"</@s.if>    href="http://www.lvmama.com/changeLocation/t=bj">北京</a>
					<a  <@s.if test="${stationValue=='sc'}"> class="this-city"</@s.if>    href="http://www.lvmama.com/changeLocation/t=sc">成都</a>
					<a  <@s.if test="${stationValue=='gd'}"> class="this-city"</@s.if> 	  href="http://www.lvmama.com/changeLocation/t=gd">广州</a>
				</dd>
			</dl>
		</div>
		<!--Ad-->
		<div class="newHeadAd">
			<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('ie1addc5b991cbea0001','js',null)"/>
			
		</div>
	</div>
	<div id="channel">
		<div id="vertical-channel">
			<ul>
				<li id="indexPage"><a href="http://www.lvmama.com/"><span>首页</span></a></li>
				<li id="ticketPage"><a href="http://www.lvmama.com/ticket"><span>打折门票</span></a></li>
				<li id="freetourPage"><a href="http://www.lvmama.com/freetour"><span>周边自由行</span></a></li>
				<li id="aroundPage"><a href="http://www.lvmama.com/around"><span>周边跟团游</span></a></li>
				<li id="destroutePage"><a href="http://www.lvmama.com/destroute"><span>国内游</span></a></li>
				<li id="abroadPage"><a href="http://www.lvmama.com/abroad"><span>出境游</span></a></li>
				<li class="current"><a href="http://www.lvmama.com/tuangou"><span>旅游团购</span></a></li>
				<li id="hotel"><a href="http://www.lvmama.com/hotel"><span>特色酒店</span></a></li>
				
				<li class="otherchannel"><a href="http://bbs.lvmama.com"><span>社区</span></a></li>
				<li id="destPage" class="otherchannel"><a href="http://www.lvmama.com/info/"><span>资讯</span></a></li>
				<li id="placePage" class="otherchannel"><a href="http://www.lvmama.com/place"><span>景点大全</span></a></li>
				<li id="guidePage" class="otherchannel"><a href="http://www.lvmama.com/guide/" target="_blank"><span>攻略</span></a></li>
				<li id="conmentesPage" class="otherchannel"><a href="http://www.lvmama.com/comment/"><span>点评</span></a></li>
			</ul>
		</div>
	</div>
	<img alt="" src="http://pic.lvmama.com/img/menuhot.gif" class="menuhot indexmenuhot">
	<img alt="" src="http://pic.lvmama.com/img/menuhot.gif" class="menuhot2 indexmenuhot">
	<img alt="" src="http://pic.lvmama.com/img/icons/hot7.gif" class="menuhot3 indexmenuhot bj">
	<div class="newSubNavbar">
		<!--Group Email-->
		<div class="group-bg group-email">
			<a href="http://www.lvmama.com/edm/showSubscribeEmail.do">邮件订阅</a>
		</div>
		<ul class="group-submenu">
			<li  <@s.if test='#currentTab=="groupIndex"'> class="grop-current-l" </@s.if> >
				<a href="http://www.lvmama.com/tuangou">团购首页</a>
			</li>
			<li <@s.if test='#currentTab=="record"'> class="grop-current-l" </@s.if>  >
				<a  href="http://www.lvmama.com/tuangou/fengqiang_1">疯抢记录</a>
			</li>
		</ul>
	</div>
</div>
<script>
	$(document).ready(function() {
		//我的驴妈妈下拉
		checkCookie();
		showMeunTool();
		readyFun();
	});

</script>
