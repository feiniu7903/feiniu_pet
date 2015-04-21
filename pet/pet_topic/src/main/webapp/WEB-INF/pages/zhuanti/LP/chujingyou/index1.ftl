<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>出境旅游去哪里好_出境游有哪些好地方_出国游有哪些国家好玩-驴妈妈旅游网</title>
<meta name="keywords" content="出境，出国，旅游" />
<meta name="description" content="驴妈妈推出最新特价大狂欢出境旅游去哪里好，出境抄底游，不用去寻找出国游有哪些国家好玩和出境游有哪些好地方，小编已经为您筛选好了，赶快来吧！" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/shuaimai/shuaimai.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery142.js"></script>

</head>

<body style="display:block;">
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="top_bg"></div>
<div class="main_all">
	<div class="banner"></div>
    <!--<ul class="nav_cfd">
    	<li class="width_240 nav_cfd_li"><span></span></li>
        <li class="width_300"><span></span></li>
        <li class="width_207"><span></span></li>
        <li class="width_126"><span></span></li>
    </ul>-->
    <div class="cfd_list">
    	<!--<dl class="nav_mdd">
        	<dt>旅游目的地：</dt>
            <dd class="nav_mdd_dd">港澳</dd>
            <dd>日韩</dd>
            <dd>欧洲</dd>
            <dd>美洲</dd>
            <dd>大洋洲</dd>
            <dd>中东非</dd>
            <dd>东南亚</dd>
            <dd>海岛</dd>
        </dl>-->
        <ul class="mdd_list">
        	<li>
				<@s.iterator value="map.get('${station}_cj_sm')" status="st">
            		<div class="cp_list">
                		<a target="_blank" href="${bakWord1?if_exists}"><img src="${bakWord2?if_exists}" width="312" height="289" alt=""></a>
                	</div>
				</@s.iterator>
               
            

                <!--<dl class="cp_list">
                	<dt><a href="#">新欧亚之魅蓝色土耳其6晚9日游</a></dt>
                    <dd>
                    	<div class="cp_img">
                    		<a href="#"><img src="http://pic.lvmama.com/img/zt/shuaimai/img_cp1.jpg" width="288" height="111" alt=""></a>
                            <div class="cp_jg">
                            	<p>现价<span class="f16">：￥</span>10660<span class="f14">起</span></p>
                            	<i>原价<br>￥</i><span class="yuanjia">12000</span>
                            </div>
                        </div>
                        <p class="jieshao">★ 仅限XX日前支付<br>★ 活动条款2<br>★ 活动条款3</p>
                        <a class="yuding" href="#"></a>
                    </dd>
                </dl>
                <div class="clear"></div>-->
                <!--<div class="pages">
                	<a class="pages_a" href="#">1</a>
                    <a href="#">2</a>
                    <a href="#">3</a>
                    <a href="#">4</a>
                    <a href="#">5</a>
                    <a href="#">6</a>
                    <a href="#">7</a>
                    <a href="#">8</a>
                    <a href="#">9</a>
                    <a href="#">10</a>
                    <a href="#">11</a>
                    <a href="#">12</a>
                    <a href="#">&gt;&gt;</a>
                </div>-->
            </li>
        </ul>
    </div>
    
<!--热门活动-->
    <div class="hd_all">
    	<div class="hd_title"></div>
        <div class="img_box">
        	<ul class="img_list">
            	<li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/aozhou/"><img src="http://pic.lvmama.com/img/zt/shuaimai/hd1.jpg" width="235" height="135" alt=""></a></li>
                <li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/gangao/"><img src="http://pic.lvmama.com/img/zt/shuaimai/hd2.jpg" width="235" height="135" alt=""></a></li>
                <li><a href="http://www.lvmama.com/zt/lvyou/haidaoyou/"><img src="http://pic.lvmama.com/img/zt/shuaimai/hd3.jpg" width="235" height="135" alt=""></a></li>
                <li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/huixiong/"><img src="http://pic.lvmama.com/img/zt/shuaimai/hd4.jpg" width="235" height="135" alt=""></a></li>
                <li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/meiguo/"><img src="http://pic.lvmama.com/img/zt/shuaimai/hd5.jpg" width="235" height="135" alt=""></a></li>
		<li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/ouzhou/"><img src="http://pic.lvmama.com/opi/ozlyj1130.jpg" width="235" height="135" alt=""></a></li>
                <li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/niboer/"><img src="http://pic.lvmama.com/img/zt/shuaimai/hd6.jpg" width="235" height="135" alt=""></a></li>
            </ul>
        </div>
        <span class="left"></span>
        <span class="right"></span>
    </div>
</div>


<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>

<script>
$(function(){

		var _Allbox  ='.img_box ul,.right,.left';
		var _List    ='.img_box ul li';
		var _Butleft ='.left'
		var _Butright='.right'
		var _Speed   =300;
		var _liwidth =286;
		var Automatic=1;
		var _Speed2  =3000;
	$(_Butright).click(function(){
							var _firstW  = $(_List).first().position().left;
									   if(_firstW==0){
							  var first=$(_List).first().html();
						$(_List).last().after('<li>'+first+'</li>');	  
						$(_List).first().stop(true).animate({'margin-left':-_liwidth},_Speed).addClass('diyi_first');
						setTimeout("$('.diyi_first').remove();",_Speed);
									   }
					   });
	if(Automatic==1){function imgScroll(){$(_Butright).click();}
						var xuanting=setInterval(imgScroll,_Speed2);
						$(_Allbox).hover(function () {clearInterval(xuanting);},function () {xuanting = setInterval(imgScroll,_Speed2);});};
	$(_Butleft).click(function(){
									  var _firstW  = $(_List).first().position().left;
									   if(_firstW==0){
						var last=$(_List).last().html();
						$(_List).first().before('<li>'+last+'</li>');	
						$(_List).first().animate({'margin-left':-_liwidth},0).stop(true).animate({'margin-left':0},_Speed);
						$(_List).last().addClass('diyi_last').siblings().removeClass('diyi_last')
						setTimeout("$('.diyi_last').remove();",_Speed);
									   }
					   });
		   });
</script>
</body>
</html>
