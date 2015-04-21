<!-- 左侧导航>> -->
<div class="lv-aside">
    <div class="mod-welcome">
        <div class="lv-media">
            <div class="lv-photo">
	            <a href="/myspace/userinfo.do" title="<@s.property value="user.userName" />"><img src="http://pic.lvmama.com<@s.property value="user.imageUrl" />" width="130" height="130"></a>
            </div>
            <div class="lv-info">
                <p><a class="link-edit" href="/myspace/userinfo.do">编辑我的资料</a></p>
            </div>
        </div>
    </div>
    <!-- 应用列表>> -->
    <!-- 维护说明：
    	上边线ui-box-title
        若无右侧的.msg-num,.link-edit等，则a需class="normal"以增加用户体验
     -->
    <div class="ui-box lvcenter-list">
    	<div class="ui-box-title ui-hide"></div>
        <div class="ui-box-container">
            <ul>
                <li id="lvnav-order" class="lvapp-item clearfix">
                    <a href='/myspace/order.do' class="normal">
                    <span class="lvapp-icon icon-apps16-1000"></span>
                    <span>我的订单</span>
                    </a>
                </li>
                <!--<li class="lvapp-item lvapp-subitem clearfix" id="lvnav-globalorder">
                    <a class="normal" href="/globalhotel/myspace/order.do">
                    <span>境外酒店订单</span>
                    </a>
                </li>-->
                <li class="lvapp-item lvapp-subitem clearfix" id="lvnav-flight">
                    <a class="normal" href="/myspace/flight.do">
                    <span>国际机票订单</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="ui-box lvcenter-list">
    	<div class="ui-box-title"></div>
        <div class="ui-box-container">
            <ul>
                <li id="lvnav-points" class="lvapp-item clearfix">
                    <a href="/myspace/account/points.do" class="normal">
                    <span class="lvapp-icon icon-apps16-1001"></span>
                    <span>我的积分</span>
                    </a>
                </li>
                <li id="lvnav-coupon" class="lvapp-item clearfix">
                    <a href="/myspace/account/coupon.do" class="normal">
                    <span class="lvapp-icon icon-apps16-1002"></span>
                    <span>我的优惠券</span>
                    </a>
                </li>
                <li id="lvnav-money" class="lvapp-item clearfix">
                    <a href="/myspace/account/bonusreturn.do">
                    <span class="lvapp-icon icon-apps16-1003"></span>
                    <span>现金账户</span>
                    </a>
                </li>
                <li id="lvnav-lipinka" class="lvapp-item clearfix">
                    <a href="/myspace/lipinka/doLipinka.do">
                    <span class="lvapp-icon icon-apps16-1018"></span>
                    <span>礼品卡</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="ui-box lvcenter-list">
        <div class="ui-box-title"></div>
        <div class="ui-box-container">
            <ul>
                <li id="lvnav-userinfo" class="lvapp-item clearfix">
                    <a href="/myspace/userinfo.do" class="normal">
                    <span class="lvapp-icon icon-apps16-1005"></span>
                    <span>个人资料</span>
                    </a>
		    		<a class="link-edit fr" href="/myspace/userinfo.do">完善</a>
                </li>
                <li id="lvnav-password" class="lvapp-item clearfix">
                    <a href="/myspace/userinfo/password.do" class="normal">
                    <span class="lvapp-icon icon-apps16-1006"></span>
                    <span>登录密码修改</span>
                    </a>
                </li>
                <li id="lvnav-visitor" class="lvapp-item clearfix">
                    <a href="/myspace/userinfo/visitor.do" class="normal">
                    <span class="lvapp-icon icon-apps16-1007"></span>
                    <span>常用游客信息</span>
                    </a>
                </li>
                <li id="lvnav-message" class="lvapp-item clearfix">
                    <a href="/myspace/message.do">
                    <span class="lvapp-icon icon-apps16-1008"></span>
                    <span>站内消息</span>
                    </a>
                    <a class="msg-num" href="/myspace/message.do" id="myspace_message_count_a" ><i id="myspace_message_count"></i></a>
                </li>
                <li id="lvnav-subscribe" class="lvapp-item clearfix">
                    <a href="/myspace/userinfo/email.do" class="normal">
                    <span class="lvapp-icon icon-apps16-1009"></span>
                    <span>邮件订阅</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="ui-box lvcenter-list">
    	<div class="ui-box-title"></div>
        <div class="ui-box-container">
            <ul>
                <li id="lvnav-comment" class="lvapp-item clearfix">
                    <a href="/myspace/share/comment.do">
                    <span class="lvapp-icon icon-apps16-1010"></span>
                    <span>我的点评</span>
                    </a>
                    <a class="msg-num" href="/myspace/share/comment.do"><b><i id="waittingCommentNumber"></i></b></a>
                 </li>
                <li id="lvnav-guide" class="lvapp-item clearfix">
                    <a href="/myspace/share/guide.do" class="normal">
                    <span class="lvapp-icon icon-apps16-1011"></span>
                    <span>我的攻略</span>
                    </a>
                </li>
               
                 <li id="lvnav-favorites" class="lvapp-item clearfix">
                    <a href="/myspace/share/favorites.do" class="normal">
                    <span class="lvapp-icon icon-apps16-1014"></span>
                    <span>我的收藏</span>
                    </a>
                </li>
                <li id="lvnav-trip" class="lvapp-item clearfix">
                    <a href="/myspace/share/trip.do" class="normal">
                    <span class="icon-new"></span>
                    <span class="lvapp-icon icon-apps16-1012"></span>
                    <span>我的旅程</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <!--
    <div class="ui-box lvcenter-list">
        <div class="ui-box-title clearfix" title="亲~点击可以收放应用列表">
            <div class="ui-box-title-border fn-clear sl-linear">
                <h3></h3>
            </div>
        </div>
    </div>
    <div class="ui-box ui-box-nocontainer lvcenter-list"></div>
    -->
    <!-- <<应用列表 -->
    <div class="hr_b"></div>
    <!-- 边栏广告位>> -->
    <div class="lv-ad mod-s-ad">
        <p><!-- 我的驴妈妈 - 左侧小横幅 -->
        <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxMnxvdGhlcnBhZ2VfMjAxMnxvdGhlcnBhZ2VfYWNjb3VudF8yMDEyX2JvdHR1bTAxJmRiPWx2bWFtaW0mYm9yZGVyPTAmbG9jYWw9eWVzJmpzPWll" charset="gbk"></script>
        <!-- 我的驴妈妈 - 左侧小横幅/End -->
</p>
    </div>
    <!-- <<边栏广告位 -->
</div>
<!-- <<左侧导航 -->