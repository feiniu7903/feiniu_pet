<div class="hr_b"></div>

<div class="buttom clearfix">
	<div class="buttom_list">
		<b class="buttom_list_tit1">订购指南</b>
				   <a rel="nofollow" href="http://www.lvmama.com/public/help_center_2" target="_blank">门票订购流程是怎样的？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/public/help_center_4" target="_blank">二维码使用时有什么注意事项？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/public/help_center_5" target="_blank">可以预订多长时间的门票？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/public/help_center_10" target="_blank">想要修改订单，怎么操作？</a>
	</div>
	<div class="buttom_list">
		<b class="buttom_list_tit2">注册与登录</b>
		   <a rel="nofollow" href="http://www.lvmama.com/public/help_center_132" target="_blank">手机没有收到激活/验证短信怎么办？</a>
		   <a rel="nofollow" href="http://login.lvmama.com/nsso/register/registering.do" target="_blank">怎样才能成为驴妈妈会员？</a>
		   <a rel="nofollow" href="http://login.lvmama.com/nsso/login" target="_blank">在哪登录会员帐号？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/public/help_center_149" target="_blank">如何修改我的帐号密码？</a>
	</div>
	<div class="buttom_list">
		<b class="buttom_list_tit3">支付与取票</b>
		   <a rel="nofollow" href="http://www.lvmama.com/public/buy_guide#m_2" target="_blank">付款方式有哪些？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/public/buy_guide#m_2" target="_blank">在线支付安全吗？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/public/buy_guide#m_2" target="_blank">为什么支付不成功？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/public/buy_guide#m_2" target="_blank">想要退款，应该怎么做？</a>
	</div>
	<div class="buttom_list" style="border: medium none;">
		<b class="buttom_list_tit4">沟通与订阅</b>
		   <a rel="nofollow" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do" target="_blank">我想咨询门票，应该怎么办？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do" target="_blank">我有意见，在哪可以提？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do" target="_blank">我想投诉，应该怎么反映？</a>
		   <a rel="nofollow" href="http://www.lvmama.com/public/about_lvmama#m_1" target="_blank">我是景区负责人，怎么联系驴妈妈？</a>
	</div>
</div>

<script src="http://pic.lvmama.com/js/common/copyright.js"></script>

</div>
<!--
<div id="xh_floatR">
    <div class="bm-box ie6png">
    	<i class="close" title="今天不再提醒"></i>
        <a class="bm-link" target="_blank" title="" href="#"></a>
    </div>
</div>
-->
<!--[if lte IE 6]>
<script src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js" ></script>
<script type="text/javascript">
   DD_belatedPNG.fix('.ie6png');
</script>
<![endif]-->

<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script> 
<!--
<script src="http://pic.lvmama.com/js/new_v/jquery_cookie.js"></script>
<script src="http://pic.lvmama.com/js/new_v/util/jurlp.min.js"></script>
<script src="http://pic.lvmama.com/js/new_v/top/header-air.js"></script>
-->
<script src="http://pic.lvmama.com/js/mylvmama/jquery-ui-1.8.21.custom.min.js"></script>
<script src="http://pic.lvmama.com/js/mylvmama/jquery.tools.min.js"></script>
<script src="http://pic.lvmama.com/js/mylvmama/lvmama-custom.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script> 
<script type="text/javascript">
$(function(){
   	$(document.body).ready(function(){
   		$.ajax({
			url: "/myspace/message/unReadCount.do",
			type:"post",
			dataType:"json",
			success:function(json){
				//$("#myspace_message_count,#myspace_message_count_id").text(json.count);
				var num = json.waittingCommentNumber;
				
				
				if(num === "0"){
					$("#lvnav-comment >a").eq(1).hide();
				}else{
					$("#lvnav-comment >a").eq(1).show();
				}
				
				$("#waittingCommentNumber").text(num);
				
			},
			error:function(){
				$("#lvnav-comment >a").eq(1).hide();
			}
		});
		
		var url="http://www.lvmama.com/message/index.php?r=PrivatePm/GetUnreadCount&callback=?";
		$.getJSON(url,function(json){ 
			if (json.code == 200) {
				var num=json.data.unreadCount;
				if(num==0){
					$("#myspace_message_count_a").removeClass("msg-num");
					$("#myspace_message_count_id").text(num);
				}else{
					$("#myspace_message_count,#myspace_message_count_id").text(num);
				}
			}else{
				$("#myspace_message_count_span").hide();
				$("#myspace_message_count_a").removeClass("msg-num");
			}
		});
		
   	});
});
</script>