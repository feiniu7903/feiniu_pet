<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>品牌合作专题-驴妈妈旅游网</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/pinpai/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<!--[if IE 6]> 
<script type="text/javascript" src="http://s2.lvjs.com.cn/js/zt/DD_belatedPNG.js"></script> 
<script>DD_belatedPNG.fix('.top_r,.top_l_num,.zijia_l,.photo,.part_list li,.bottom img');</script> 
<![endif]--> 
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="banner">
	<img src="http://www.lvmama.com/zt/promo/pinpai/images/banner.jpg" width="929" height="310" alt="">
</div>

</div>
<div class="main_all">
    <div class="top" >
    	<div class="top_l" id="top_l">
        	<ul class="top_l_list">
            <@s.iterator value="map.get('${station}_jdt')" status="st">
            <@s.if test="#st.isFirst()">
                <li class="top_l_list_li"><a href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="544" height="261" alt=""></a></li>
            </@s.if>
            <@s.else>
                <li style="display:none"><a href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="544" height="261" alt=""></a></li>
             </@s.else>
        	 </@s.iterator>
            </ul>
            <div class="top_l_num">
            	<ul class="num_list">
                	<li class="num_list_li"></li>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                </ul>		
            </div>
        </div>
        <div class="top_r">
        	<ul class="top_r_list">
            <@s.iterator value="map.get('${station}_gg')" status="st">
            <li><a href="${url?if_exists}" target="_blank">${title?if_exists}</a></li>
			</@s.iterator>
            </ul>	
        </div>
        <div class="clear"></div>
    </div>
	<div class="main">
    	<div class="zijia">
        <@s.iterator value="map.get('${station}_pp')" status="st">
            <div class="zijia_l">
            	<h3><a href="#" target="_blank">${title?if_exists}</a></h3>
                <ul class="acti">
                    <li class="photo"><a href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="318" height="178" alt=""></a></li>
                    <li class="detail">
                        <h4>活动时间:<span>${bakWord2?if_exists}</span></h4>
                        <p>${bakWord1?if_exists}</p>
                        <p>
                            
                            <a href="${url?if_exists}" target="_blank">更多详情</a>
                        </p>
                    </li>
                </ul>
                <div class="clear"></div>
            </div>
      	</@s.iterator> 
            
        </div>
        <div class="pinpai">
            <ul class="zijia_r">
            <@s.iterator value="map.get('${station}_pp')" status="st">
            <li>
            	<h4>${title?if_exists}</h4>
            	<a href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="187" height="88" alt=""></a>
            </li>
            </@s.iterator> 
            </ul>
        </div>
    </div>
    <div class="partner">
		<div class="title">
            <p></p>
            <b>品牌合作伙伴</b>
        </div>
        <ul class="part_list">
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part1.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part2.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part3.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part4.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part5.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part6.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part7.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part8.jpg" width="70" height="30" alt=""></li>
        </ul>
        <ul class="part_list">
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part9.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part10.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part9.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part3.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part6.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part8.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part1.jpg" width="70" height="30" alt=""></li>
                <li><img src="http://www.lvmama.com/zt/promo/pinpai/images/part7.jpg" width="70" height="30" alt=""></li>
        </ul>
        

      
    </div>
    
</div>
<div class="bottom">
	<img src="http://www.lvmama.com/zt/promo/pinpai/images/bottom.png" width="929" height="50" alt="">
</div>
<div class="footer">
	<p class="web">
    	<a href="http://www.lvmama.com/public/about_lvmama" target="_blank">关于我们</a>
        <a href="http://www.lvmama.com/public/site_map" target="_blank">网站地图</a>
        <a href="http://www.lvmama.com/public/help" target="_blank">帮助中心</a>
        <a href="http://www.lvmama.com/public/links" target="_blank">友情链接</a>
        <a href="http://www.lvmama.com/public/jobs" target="_blank">诚聘英才</a>
        <a href="http://www.lvmama.com/userCenter/user/transItfeedBack.do" target="_blank">意见反馈</a>
        <a href="http://www.lvmama.com/public/adwy" target="_blank">广告业务</a>
        <a href="http://fenxiao.lvmama.com" target="_blank">分销合作</a>
    </p>
    <p class="txt">
    	Copyright © 2013 www.lvmama.com. 上海景域文化传播有限公司版权所有　沪ICP备07509677　增值电信业务经营许可证编号：
        <a href="http://pic.lvmama.com/img/ICP.jpg" target="_blank">沪B2-20100030</a>
    
    </p>
</div>
<script type="text/javascript" src="js/jquery.imgSlide.js"></script>
<script type="text/javascript">
$(function(){
	var cur_index=1;
	var imgnum=$(".top_l_list li").length;//总的图片数目
	$(".num_list li").mouseover(function(){
		var s_index=$(this).index();
		$(this).addClass("num_list_li").siblings().removeClass("num_list_li");
		$(".top_l_list li").eq(s_index).fadeIn().siblings().hide();
		cur_index=s_index+1;
		if(cur_index>=imgnum) cur_index=0;		
	});
	 function autoImg(){
	 	  $(".num_list li").eq(cur_index).addClass("num_list_li").siblings().removeClass("num_list_li");
		  $(".top_l_list li").eq(cur_index).fadeIn().siblings().hide();
		  cur_index++;
		  if(cur_index>=imgnum)  cur_index=0;
	}	
	  var timer=setInterval(autoImg,4000);
	  $("#top_l").hover(function(){
	  		clearInterval(timer);
	  },function(){
	  		timer=setInterval(autoImg,4000);
	  });
});
</script>









<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
