<script type="text/javascript" charset="utf-8">
	$(function(){
		var liTitle = $(".content h2").text();
		if (liTitle != "") {
			$(".menu a:contains("+liTitle+")").css("color","#000");
		}
	});
</script>
<div class="menu">
<ul>
<li class="li_1"><a href="/myspace/order.do">我的订单</a></li>
<li class="li_2"><a href="http://www.lvmama.com/myspace/share/comment.do">我的点评</a></li>
<li style="text-indent: 0pt; background:url(http://pic.lvmama.com/img/myspace/spacecouponbg.gif) no-repeat left center; text-indent:35px;"><a href="/member/coupon">我的优惠券</a></li>
<li class="li_4"><a href="/member/point_and_level">我的积分</a></li>
<!-- li class="li_3"><a href="/member/gold">我的驴币</a></li -->

<li style="height:110px;_height:80px;position:relative;text-indent:0;_padding:15px 0px;">
<p><img src="http://pic.lvmama.com/img/myspace/spacegoldbg.gif" style="margin-right:10px; vertical-align:middle;" /><a href="javascript:void(0)" onClick="showbox1(this)" style="zoom:1;">我的账户 <img src="http://pic.lvmama.com/img/myspace/spaceleftdown.gif"></a></p>
<div style="display:block;">
<p style="font-size:12px;font-weight:500;margin-left:35px;line-height:12px;margin-bottom:15px;_margin-bottom:0px;_margin-top:15px;">
<a href="/myspace/mySpace!cashChangeLogList.do">奖金账户</a><span class="button01" biaoshi="tanchu01"><img src="http://pic.lvmama.com/img/myspace/wenhao01.gif" /></span><br /><br />
<a href="/usr/money/account.do">存款账户</a><span class="button01"  biaoshi="tanchu02"><img src="http://pic.lvmama.com/img/myspace/wenhao01.gif" /></span><br />
<!--<a href="/usr/money/viewdraw.do">退款提现</a>-->
</p>
</div>

 <!--------------------------------------------------------------------------------->
        <div id="tanchu">
            <div class="tanchu01">
                <h3>说明</h3>
                <p>奖金账户是指您游玩后发表的体验点评获得的点评奖金都在此账户中管理。您可以：</p>
                <dl class="tanchuDd">
                    <dd>查看点评奖金明细；</dd>
                </dl>
                <img src="http://pic.lvmama.com/img/myspace/sanjiao01.gif" class="sanjiao01" />
            </div>
            <div class="tanchu02">
                <h3>说明</h3>
                <p>存款账户是一个现金充值、退款、提现及支付的账户平台，您可以：</p>
                <dl>
                    <dd>在付款明细中，查看您用现金账户支付订单的历史刻录；</dd>
                    <dd>在充值明细中，完成充值操作及查看您的历史充值刻录；</dd>
                    <dd>在退款明细中，查看订单的退款记录；</dd>
                    <dd>在提现明细中，完成退款提现及查看您的历史提现记录。</dd>
                </dl>
                <img src="http://pic.lvmama.com/img/myspace/sanjiao01.gif" class="sanjiao01" />  
            </div>       
        </div>
        
<!---------------------------------------------------------------->
</li>

<li class="li_9"><a href="/myspace/mySpace!myShar.do">我的分享</a></li>
<!--li class="li_5"><a href="/member/space/<@s.property value="#session.SESSION_USER_OBJECT.userId"/>" target="_blank">我的空间</a></li-->
<li class="li_6"><a href="/member/profile">账户信息</a></li>
</ul>
</div>

<script>
function showbox1(obj){
if (dom.nextElement(obj.parentNode).style.display=='none'){
obj.childNodes[1].src='http://pic.lvmama.com/img/myspace/spaceleftup.gif';
dom.nextElement(obj.parentNode).style.display="";
 
} else {
obj.childNodes[1].src='http://pic.lvmama.com/img/myspace/spaceleftdown.gif';
dom.nextElement(obj.parentNode).style.display="none";
}
}
</script>

<script type="text/javascript">
$(function(){
	$(".button01").mouseover(function(){
								var biaoshi=$(this).attr('biaoshi');
								$("."+biaoshi).show();																
								if(navigator.appVersion.indexOf("MSIE 6.0") !=-1){
								$("#tanchu").append(' <iframe   frameborder=0  id="ifm" style="filter:alpha(opacity=1); border:none; width:260px; display:block; position:absolute; left:-2px; top:42px; z-index:0; height:260px;" ></iframe> ');
								}
	})	
	$(".button01").mouseout(function(){
								$(".tanchu01,.tanchu02").hide();
								if(navigator.appVersion.indexOf("MSIE 6.0") !=-1){
								$("#ifm").remove();
								}
	})

})
</script>


<style type="text/css">
span.button01{position:relative;top:2px;}
#tanchu{position:absolute;top:45px;left:110px;display:block;z-index:9999;font-size:12px;font-family:simsun;}
#tanchu .tanchu01,#tanchu .tanchu02 {display:none;border:1px solid #CCCCCC;color:#333333;width:252px;text-align:left;font-family:simsun;position:relative;background:#ffffff;padding-bottom:15px;}
#tanchu .tanchu02{top:25px;}
#tanchu h3 {font-weight:bold;background:#FFEEFF;padding:0 10px;height:28px;line-height:28px;}
#tanchu ul{margin:0;}
#tanchu p,#tanchu dd{line-height:22px;padding:0 10px;text-indent:0;font-weight:100;border-bottom:none;font-size:12px;}
#tanchu dl dd{padding-left:20px;background:url(http://pic.lvmama.com/img/myspace/titbg01.gif) no-repeat 10px 8px;line-height:20px;_height:40px;}
#tanchu .tanchuDd dd{height:20px;}
#tanchu .sanjiao01{position:absolute;top:5px;left:-8px;}

.menu{ position:relative; z-index:49;}
.menu ul li{ height:50px;}
</style>

