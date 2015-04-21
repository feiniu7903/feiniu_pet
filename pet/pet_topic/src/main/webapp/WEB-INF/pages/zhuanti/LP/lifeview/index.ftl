<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>热门旅游地推荐_国内最佳旅游胜地-驴妈妈旅游网</title>
<meta name="keywords" content="热门旅游,旅游胜地" />
<meta name="description" content="驴妈妈活出风采,活出风景,最热门的旅游胜地,最值得去玩的热门景点,等待你的选择,各种主题游,景点游应有竟有." />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/huochufengjing/huochufengjing.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<base target="_blank" />
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--内容从这开始-->
<div class="top">
	<div class="banner-bg">
    <div class="banner-in" id="banner-in">
    	<div class="leftNar" id="leftNar"></div>
        <div class="rightNar" id="rightNar"></div>
    	<ul id="banner" class="banner">
        	<@s.iterator value="map.get('${station}_9550')" status="st">
        	<li><img src="${imgUrl?if_exists}" width="621" height="359" alt="${title?if_exists}" /></li>
		</@s.iterator>
        </ul>
    </div>
    </div>
</div>
<div class="wraper">
  <div class="wraper-in">
    <div class="mainCont">
    	<div class="jijing"><img src="http://pic.lvmama.com/img/zt/huochufengjing/jijing.jpg" width="956" height="25" align="" /></div>
      <ul class="ad-list">
	    <@s.iterator value="map.get('${station}_9551')" status="st">
           <li>
          <div class="ad-detail"><strong>${title?if_exists}</strong>${remark?if_exists}</div>
          <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="460" height="200" alt="" /></a> </li>
		  </@s.iterator>
        
      </ul>
    </div>
  </div>
  <div class="zixun">
    <div class="zixun-tit">
      <h3>资讯活动</h3>
    </div>
    <div class="zixun-cont clearfix">
      <div class="zixun-weibo">
        <h6>微博互动</h6>
		<@s.iterator value="map.get('${station}_9552')" status="st">
          <div class="weibo-in">
          <div class="weibo-tit">${title?if_exists}</div>
          <p class="weibo-p">${remark?if_exists}${bakWord1?if_exists}</p>
          <a href="${url?if_exists}">转发有奖</a>
		</div>
		</@s.iterator>
      </div>
      <div class="zixun-luntan">
        <h6>论坛活动</h6>
        <@s.iterator value="map.get('${station}_9553')" status="st">
       <#if (st.index==0)>
        <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="434" height="129" alt="${title?if_exists}" /></a>
       </#if>
        </@s.iterator>
        <ul class="luntan-list">
		  <@s.iterator value="map.get('${station}_9553')" status="st">
            <li><a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a><span>${bakWord1?if_exists}</span></li>
          </@s.iterator>
        </ul>
      
      </div>
    </div>
  </div>
  <@s.if test="null != map.get('${station}_9554') && !map.get('${station}_sh_s').isEmpty()">
  <div class="yugao" id="yugao1">
    <div class="zixun-tit">
      <h3>精彩预告</h3>
    </div>
    <div class="jiantoul"></div>
    <div  class="jiantour"></div>
    <div class="main_jp">
      <ul id="sell_order" class="sell_order">
	  <@s.iterator value="map.get('${station}_9554')" status="st">
        <li><img src="${imgUrl?if_exists}" alt="${title?if_exists}" />${title?if_exists}</li>
       </@s.iterator> 
      </ul>
      <div  class="sell_order_t"></div>
    </div>
  </div>
  </@s.if>
  <@s.if test="null != map.get('${station}_9555') && !map.get('${station}_sh_s').isEmpty()">
  <div class="huigu" id="yugao2">
    <div class="zixun-tit">
      <h3>往期回顾</h3>
    </div>
	<div  class="jiantoul"></div>
    <div  class="jiantour"></div>
    <div class="main_jp">
      <ul id="sell_order" class="sell_order">
	    <@s.iterator value="map.get('${station}_9555')" status="st">
        <li><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" alt="${title?if_exists}" /></a>${title?if_exists}</li>
       </@s.iterator>
      </ul>
      <div  class="sell_order_t"></div>
    </div>
  </div>
  </@s.if>
  <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
</div>
<div id="rightNavbox" class="r_nav">
<span class="close" ></span>
  <ul>
    <li><a href="http://www.lvmama.com/zt/lvyou/yunnan">新云南心旅程</a></li>
    <li><a href="http://www.lvmama.com/zt/lvyou/ouzhou">别样欧洲</a></li>
    <li><a href="http://www.lvmama.com/zt/lvyou/wyly">国内五一</a></li>
    <li><a href="http://www.lvmama.com/zt/lvyou/jiuhuashan">朝拜九华山</a></li>
    <li><a href="http://www.lvmama.com/zt/lvyou/maerdaifu/">私奔到马尔代夫</a></li>
    <li><a href="http://www.lvmama.com/zt/lvyou/wyych/">走进婺源</a></li>
    <li><a href="http://www.lvmama.com/zt/lvyou/riben/">日本赏樱</a></li>
    <li><a href="http://www.lvmama.com/zt/lvyou/cjtq/">春季踏青</a></li>
    <li><a href="http://www.lvmama.com/zt/lvyou/yzly/">慢游扬州</a></li>
    <li><a href="http://www.lvmama.com/zt/lvyou/zjy">自驾游天下</a></li>
    
    
    
    <!--不要复制这行代码--><li class="dingbu"><a href="#"  target="_self" class="narr">返回顶部</a></li>
  </ul>
</div>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/huochufengjing/jquery.imgSlide.js"></script>
<script type="text/javascript">
var cur_index=0;
var imgnum=$("#banner li").length;//总的图片数目
	//点击右边（左边）按钮时触发图片滚动函数pic_switch
		function pic_switch2(method){ 
	 			if (method=="right"){
					$("#banner li").eq(cur_index).fadeIn().siblings().hide();
					 cur_index++; 
					 if (cur_index>=imgnum) cur_index=0;	
					 
					  
				}else{ 
				    $("#banner li").eq(cur_index).fadeIn().siblings().hide();
					 cur_index--;
					 if (cur_index<0) cur_index=imgnum-1;
					
				}
			
					return cur_index;
	 }	
	 function autoImg(){
		 	  $("#banner li").eq(cur_index).fadeIn().siblings().hide();
	 		  cur_index++;
			  if(cur_index>=imgnum)  cur_index=0;
			
				
	}		
$(function(){
	  var timer=setInterval(autoImg,4000);
	  
	  $("#banner-in").hover(function(){
	  		clearInterval(timer);
	  },function(){
	  		timer=setInterval(autoImg,4000);
	  });
	//点击右边（左边）按钮时触发图片滚动函数pic_switch
	$("#leftNar").click(function(){ cur_index=pic_switch2("left");})
	$("#rightNar").click(function(){ cur_index=pic_switch2("right");})
});


</script>
<script type="text/javascript">
$(function(){
		$(".jiantoul").hover(function(){
                        $(this).addClass('jiantoul2');
                      },function(){
                         $(this).removeClass('jiantoul2');
                         });
		$(".jiantour").hover(function(){
                        $(this).addClass('jiantour2');
                      },function(){
                         $(this).removeClass('jiantour2');
                         })		 
		$("#yugao1").imgSlide();
		$("#yugao2").imgSlide();			 
						 
		});
</script>
<script type='text/javascript'>
    $(function () {
			 $(window).scroll(function(){ 
		var _scroll=$(document).scrollTop(); 
		/* if (!window.XMLHttpRequest){ 
		$("#re_top").css({"top":_scroll+300}); 
		}*/ 
		if(_scroll>0){ 
		$("#rightNavbox").show(); 
		// alert("scroll:"+_scroll); 
		}else{ 
		$("#rightNavbox").hide(); 
		} 
		}); 
		
		$(".close").click(function(){
			$("#rightNavbox").remove();
		});
    });
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
