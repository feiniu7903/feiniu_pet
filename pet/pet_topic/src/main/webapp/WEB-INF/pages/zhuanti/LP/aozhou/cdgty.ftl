<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>成都到澳大利亚新西兰跟团游线路_成都到澳大利亚跟团游多少钱,费用-驴妈妈旅游网</title>
<meta name="keywords" content="澳大利亚，新西兰，成都，跟团游" />
<meta name="description" content="驴妈妈旅游网推出精心策划上海到澳大利亚新西兰跟团游线路和上海到澳大利亚新西兰跟团游多少钱,费用,澳新旅游新玩法。" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/aoxin/aoxin.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="main_all">
	<div class="banner">
    	<ul class="img_big" id="img_big">
        	<li style="display:block;"><img src="http://pic.lvmama.com/img/zt/aoxin/banner1.jpg" alt=""></li>
            <li><img src="http://pic.lvmama.com/img/zt/aoxin/banner2.jpg" alt=""></li>
            <li><img src="http://pic.lvmama.com/img/zt/aoxin/banner3.jpg" alt=""></li>
            <li><img src="http://pic.lvmama.com/img/zt/aoxin/banner4.jpg" alt=""></li>
            <li><img src="http://pic.lvmama.com/img/zt/aoxin/banner5.jpg" alt=""></li>
            <li><img src="http://pic.lvmama.com/img/zt/aoxin/banner6.jpg" alt=""></li>
        </ul>
        <ul class="img_list" id="img_list">
        	<li class="img_list_li"></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </div>
     <div class="miaosha">
	<@s.iterator value="map.get('${station}_ms_cd')" status="st">
    	<dl class="ms_dl">
        	<dt><img src="${imgUrl?if_exists}" width="389" height="221" alt=""></dt>
            <dd>
            	<h2>${title?if_exists}</h2>
                <p>惊爆价：<i class="orange">￥</i><span class="orange">${memberPrice?if_exists?replace(".0","")}</span>起 <br>
                出发地：${bakWord1?if_exists}  <br>
                秒杀时间：${bakWord2?if_exists} <a class="ms_yuding" href="${url?if_exists}"><img src="http://pic.lvmama.com/img/zt/aoxin/yuding.gif" width="120" height="35" alt=""></a></p>
            </dd>
        </dl>
	</@s.iterator>
    </div>
    <ul class="nav_all">
    	<li class="click_bg width_177"><span class="nav_bg1"></span></li>
        <li class="width_187"><span class="nav_bg2"></span></li>
        <li class="width_229"><span class="nav_bg3"></span></li>
    </ul>
    
<!--跟团游-->
    <div class="nav_list" style="display:block;">
    	<dl class="nav_cfd">
        	<dt>出发地选择</dt>
            <dd><a target="_self" href="http://www.lvmama.com/zt/lvyou/aozhou/">上海/南京/杭州</a></dd>
            <dd class="nav_cfd_dd"><a target="_self" href="http://www.lvmama.com/zt/lvyou/aozhou/cdgty/">成都/重庆</a></dd>
            <dd><a target="_self" href="http://www.lvmama.com/zt/lvyou/aozhou/bjgty/">北京</a></dd>
            <dd><a target="_self" href="http://www.lvmama.com/zt/lvyou/aozhou/gzgty/">广州/深圳/香港</a></dd>
        </dl>
        <div class="cfd_list" style="height:auto;">
			<@s.iterator value="map.get('${station}_gt_cd')" status="st">
        	<dl class="cp_title">
            		<dt>${title?if_exists}</dt>
                	<dd>${bakWord1?if_exists}</dd>
            </dl>
           	 <ul class="cp_list cp_list_js3">
            	<@s.iterator value="map.get('${station}_gt_cd_${st.index + 1}')" status="sts">
				<li>
					<h5><a href="${url?if_exists}"><span class="gray">【${bakWord2?if_exists}】</span>${title?if_exists}</a></h5>
                    <span class="jiage">¥<b>${memberPrice?if_exists?replace(".0","")}</b><i>起</i></span>
                    <div class="hide_box" style="display:block;">
                    	<img src="${imgUrl?if_exists}" width="158" height="97" alt="">
                        <p>
                       	${bakWord1?if_exists}
                        </p>
                    </div>
                </li>
				</@s.iterator>	
            </ul>
            <span class="zhankai">点击展开</span>
			</@s.iterator>	
        </div>
    </div>
    
<!--自由行-->
    <div class="nav_list">
    	<dl class="nav_cfd">
        	<dt>出发地选择</dt>
            <dd><a target="_self" href="http://www.lvmama.com/zt/lvyou/aozhou/shzyx/">上海/南京/杭州</a></dd>
            <dd class="nav_cfd_dd"><a target="_self" href="http://www.lvmama.com/zt/lvyou/aozhou/cdzyx/">成都/重庆</a></dd>
            <dd><a target="_self" href="http://www.lvmama.com/zt/lvyou/aozhou/bjzyx/">北京</a></dd>
            <dd><a target="_self" href="http://www.lvmama.com/zt/lvyou/aozhou/gzzyx/">广州/深圳/香港</a></dd>
        </dl>
        <div class="cfd_list">
			<@s.iterator value="map.get('${station}_zy_cd')" status="st">
        	<dl class="cp_title">
            		<dt>${title?if_exists}</dt>
                	<dd>${bakWord1?if_exists}</dd>
            </dl>
           	 <ul class="cp_list cp_list_js3">
            	<@s.iterator value="map.get('${station}_zy_cd_${st.index + 1}')" status="sts">
				<li>
					<h5><a href="${url?if_exists}"><span class="gray">【${bakWord2?if_exists}】</span>${title?if_exists}</a></h5>
                    <span class="jiage">¥<b>${memberPrice?if_exists?replace(".0","")}</b><i>起</i></span>
                    <div class="hide_box" style="display:block;">
                    	<img src="${imgUrl?if_exists}" width="158" height="97" alt="">
                        <p>
                       	${bakWord1?if_exists}
                        </p>
                    </div>
                </li>
				</@s.iterator>	
            </ul>
            <span class="zhankai">点击展开</span>
			</@s.iterator>	
        </div>
    </div>
    
    
<!--当地游-->
    <div class="nav_list">
        <div class="cfd_list" style="display:block;">
			<@s.iterator value="map.get('${station}_mp')" status="st">
        	<dl class="cp_title">
            		<dt>${title?if_exists}</dt>
                	<dd>${bakWord1?if_exists}</dd>
            </dl>
           	 <ul class="cp_list cp_list_js3">
            	<@s.iterator value="map.get('${station}_mp_${st.index + 1}')" status="sts">
				<li>
					<h5><a href="${url?if_exists}"><span class="gray">【${bakWord2?if_exists}】</span>${title?if_exists}</a></h5>
                    <span class="jiage">¥<b>${memberPrice?if_exists?replace(".0","")}</b><i>起</i></span>
                    <div class="hide_box" style="display:block;">
                    	<img src="${imgUrl?if_exists}" width="158" height="97" alt="">
                        <p>
                       	${bakWord1?if_exists}
                        </p>
                    </div>
                </li>
				</@s.iterator>	
            </ul>
            <span class="zhankai">点击展开</span>
			</@s.iterator>	
        </div>
    </div>
    
    
    <div class="main_r">
    	<h3>微博最新活动</h3>
        <div class="weibo">
        	<img class="logo" src="http://pic.lvmama.com/img/zt/aoxin/logo.gif" width="195" height="35" alt="">
            <p>驴妈妈澳新微博<a href="http://weibo.com/lvmamas"><img src="http://pic.lvmama.com/img/zt/aoxin/guanzhu.gif" width="69" height="23" alt=""></a></p>
            <img class="weibo_img" src="http://pic.lvmama.com/img/zt/aoxin/weibo_img.gif" width="188" height="112" alt="">
            <a href="http://www.jiathis.com/send/?webid=tsina&url=&title=&uid="><img src="http://pic.lvmama.com/img/zt/aoxin/weibo.gif" width="195" height="34" alt=""></a>
        </div>
        <h3>最新资讯播报</h3>
        <ul class="zxbb">
        	<li>
            	<h4>当地最新攻略</h4>
                <a href="http://www.lvmama.com/guide/place/dayanghzhou_aodaliya/"><img src="http://pic.lvmama.com/img/zt/aoxin/img_gl.jpg" width="188" height="89" alt=""></a>
            </li>
            <li>
            	<h4>当地气候介绍</h4>
                <a href="http://www.lvmama.com/guide/place/dayanghzhou_aodaliya/"><img src="http://pic.lvmama.com/img/zt/aoxin/img_qh.jpg" width="188" height="89" alt=""></a>
            </li>
            <li>
            	<h4>当地景点介绍</h4>
                <a href="http://www.lvmama.com/guide/place/dayanghzhou_aodaliya/"><img src="http://pic.lvmama.com/img/zt/aoxin/img_jd.jpg" width="188" height="89" alt=""></a>
            </li>
            <li>
            	<h4>当地交通信息</h4>
                <a href="http://www.lvmama.com/guide/place/dayanghzhou_aodaliya/"><img src="http://pic.lvmama.com/img/zt/aoxin/img_jt.jpg" width="188" height="89" alt=""></a>
            </li>
            <li>
            	<h4>当地签证所需</h4>
                <a href="http://www.lvmama.com/guide/place/dayanghzhou_aodaliya/"><img src="http://pic.lvmama.com/img/zt/aoxin/img_qz.jpg" width="188" height="89" alt=""></a>
            </li>
        </ul>
    </div>
    
    
    <!--活动进行时-->
        <div class="huodong_img">
            <div class="hd_img_list">
                <ul>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/maerdaifu">
                            <img src="http://pic.lvmama.com/img/zt/meiguo/mei8.jpg" width="284" height="135" alt=""></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/ouzhou">
                            <img src="http://pic.lvmama.com/img/zt/meiguo/mei5.jpg" width="284" height="135" alt=""></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/riben_1">
                            <img src="http://pic.lvmama.com/img/zt/meiguo/mei4.jpg" width="284" height="135" alt=""></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/dsn">
                            <img src="http://pic.lvmama.com/img/zt/meiguo/mei6.jpg" width="284" height="135" alt=""></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/zdf">
                            <img src="http://pic.lvmama.com/img/zt/meiguo/mei1.jpg" width="284" height="135" alt=""></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/qianzheng">
                            <img src="http://pic.lvmama.com/img/zt/meiguo/mei7.jpg" width="284" height="135" alt=""></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/shuqi">
                            <img src="http://pic.lvmama.com/img/zt/meiguo/mei2.jpg" width="284" height="135" alt=""></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/abroad/">
                            <img src="http://pic.lvmama.com/img/zt/meiguo/mei3.jpg" width="284" height="135" alt=""></a>
                    </li>
                </ul>
            </div>
            <span class="zuo"></span>
            <span class="you"></span>
        </div>
    
<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
</div>

<div class="body_bg_t"></div>


<script>
	$(function(){
			   $('#img_big li:first').show().addClass('Index2');
			   var _Numall=0;
			   	   _time  =3000;
			   	   _lenght=$('#img_big>li').length-1;
			   $('#img_list li').click(function(){
					var _Num=$('#img_list li').index(this);
						_Numall=_Num;
					$(this).addClass('img_list_li').siblings('li').removeClass('img_list_li');
					$('.Index2').show().css('z-index','1').siblings('li').hide().css('z-index','0');
  					$('#img_big li').eq(_Num).addClass('Index2').css('z-index','2').fadeIn(700).siblings().removeClass('Index2');
						  });
			    function imgScroll_t(){
					$('#img_list li').eq(_Numall).addClass('img_list_li').siblings('li').removeClass('img_list_li');
					$('.Index2').show().css('z-index','1').siblings('li').hide().css('z-index','0');
  					$('#img_big li').eq(_Numall).addClass('Index2').css('z-index','2').fadeIn(700).siblings().removeClass('Index2');
					_Numall+=1;if(_Numall>_lenght) _Numall=0;
					}
					var jdt_xuanting=setInterval(imgScroll_t,_time);
						$('.banner').hover(function () {clearInterval(jdt_xuanting);},function () {jdt_xuanting = setInterval(imgScroll_t,_time);});
						
						
	$('.cp_list_js3').find('li:gt(2)').hide();
	$('.cp_list_js3').each(function(){			  
		if($(this).find('li').length<4){$(this).next('.zhankai').hide();}
										});
	
	$('.cp_list_js5').find('li:gt(4)').hide();
	$('.cp_list_js5').each(function(){			  
		if($(this).find('li').length<6){$(this).next('.zhankai').hide();}
										});
	
	$('.zhankai').toggle(function(){
	$(this).prev('.cp_list_js3').find('li:gt(2)').slideDown();
	$(this).prev('.cp_list_js5').find('li:gt(4)').slideDown();
	$(this).text('点击收起');
								  },function(){
	$(this).prev('.cp_list_js3').find('li:gt(2)').slideUp();
	$(this).prev('.cp_list_js5').find('li:gt(4)').slideUp();
	$(this).text('点击展开');
								  });
	
	$('.nav_all li').click(function(){
		var _num = $(this).index();
		$(this).addClass('click_bg').siblings().removeClass('click_bg')
		$('.nav_list').eq(_num).show().siblings('.nav_list').hide();
									});
	
	
/*	$('.nav_cfd dd').click(function(){
		var _cfd_num = $(this).index();
		$(this).addClass('nav_cfd_dd').siblings().removeClass('nav_cfd_dd');
		$(this).closest('.nav_cfd').siblings('.cfd_list').eq(_cfd_num-1).show().siblings('.cfd_list').hide();
									});*/
	
	
	$('.cp_list li').hover(function(){
		$(this).find('.hide_box').show();
		$(this).siblings().find('.hide_box').hide();
			   });
				
		var _Allbox  ='.hd_img_list ul,.you,.zuo'	
		var _List    ='.hd_img_list ul li';	
		var _Butleft ='.zuo';
		var _Butright='.you';
		var _Speed   =300;		
		var _liwidth =286;				
		var Automatic=1;	
		var _Speed2  =3000;	
	$(_Butright).live('click',function(){
							var _firstW  = $(_List).first().position().left;
									   if(_firstW==0){
							  var first=$(_List).first().html();
						$(_List).last().after('<li>'+first+'</li>');	  
						$(_List).first().animate({'margin-left':-_liwidth},_Speed).addClass('diyi_first');
						setTimeout("$('.diyi_first').remove();",_Speed);
									   }
					   });
	if(Automatic==1){function imgScroll(){$(_Butright).click();}
						var xuanting=setInterval(imgScroll,_Speed2);
						$(_Allbox).hover(function () {clearInterval(xuanting);},function () {xuanting = setInterval(imgScroll,_Speed2);});};
	$(_Butleft).live('click',function(){
									  var _firstW  = $(_List).first().position().left;
									   if(_firstW==0){
						var last=$(_List).last().html();
						$(_List).first().before('<li>'+last+'</li>');	
						$(_List).first().animate({'margin-left':-_liwidth},0).animate({'margin-left':0},_Speed);
						$(_List).last().addClass('diyi_last').siblings().removeClass('diyi_last')
						setTimeout("$('.diyi_last').remove();",_Speed);
									   }
					   });
				
				
				
				
				
				
});

</script>


<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
