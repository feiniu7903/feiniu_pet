<script type="text/javascript" src="/js/member/headerMenu.js"></script>
<script type="text/javascript" src="/js/member/tools.js"></script>
<div class="container">
<div class="header">
<a href=" http://www.lvmama.com/myspace/index.do"><div class="logo"></div></a>
<div class="topmenu"><a href="http://www.lvmama.com/">首页</a>&nbsp;|&nbsp;<a href="http://www.lvmama.com/ticket">景点门票</a>&nbsp;|&nbsp;<a href="http://www.lvmama.com/freetour">周边自由行</a>&nbsp;|&nbsp;<a href="http://www.lvmama.com/around">周边跟团游</a>&nbsp;|&nbsp;<a href="http://www.lvmama.com/destroute">国内游</a>&nbsp;|&nbsp;<a href="http://www.lvmama.com/abroad">出境游</a>&nbsp;|&nbsp;<a href="http://tuan.lvmama.com/">旅游团购</a>&nbsp;|&nbsp;<a href="http://hotel.lvmama.com/">特色酒店</a>&nbsp;|&nbsp;<a href="http://www.lvmama.com/comment/">旅游点评</a>&nbsp;|&nbsp;<a href="http://guide.lvmama.com/">旅游攻略</a>&nbsp;|&nbsp;<a href="http://www.lvmama.com/place">景点大全</a>&nbsp;|&nbsp;<a href="http://info.lvmama.com/">旅游资讯</a>&nbsp;|&nbsp;<a href="http://bbs.lvmama.com/">社区</a></div>
<div class="dy_menu"><a href="http://www.lvmama.com/myspace/index.do">我的首页</a>&nbsp;|&nbsp;<a onclick="shwoDivssss();" id="pox" href="/member/friend">好友<img src="/img/myspace/dy_menu_arrow_bg.gif"></a>&nbsp;|&nbsp;<a href="/msg/inbox.htm">消息<font id="msgNum"></font></a></div>
<div class="user_menu">
 
<!--chengang |  --><a href="/buy/shoppingcart">购物车</a>&nbsp;|&nbsp;<a href="http://login.lvmama.com/nsso/logout">退出</a></div>
</div>
 <div  style="display:none;border:1px #a70679 solid; width:70px; text-align:left; position:absolute; z-index:999; background:#fff; overflow:hidden; zoom:1;" id="menuLoginC">
        <div class="user_center_pic" ></div>
        <div class="user_center_p" style="line-height:24px; padding:5px 10px; ">
        <a href="/member/friend">我的好友</a><br>
        <a href="/member/friend/add">添加好友</a>       
         </div>
     </div>
    <script type="text/javascript">
    $.ajax({type:"POST", url:"/ajax/ajax!getMsgNum.do",dataType:"json", success:function (res) {
			if(res.msgCounts>0)
			{
				$("#msgNum").html("（"+res.msgCounts+"）");
				$("#msgNum2").html(res.msgCounts+"");
			}
		}});
    </script>
