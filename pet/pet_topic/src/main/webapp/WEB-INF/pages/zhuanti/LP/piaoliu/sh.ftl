<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>2014上海漂流推荐_上海周边漂流_哪里有漂流_附近玩水-驴妈妈旅游网</title>
<meta name="keywords" content="上海漂流,上海周边漂流,上海附近漂流,线路"/>
<meta name="description" content="014上海漂流推荐:上海周边漂流线路,上海附近哪里有漂流,上海哪里有漂流,上海漂流去哪里好,快随小驴的脚步来看看吧！" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/wanshui/index.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/wanshui/global.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<base target="_blank">
<!--[if IE 6]> 
<script type="text/javascript" src="http://s2.lvjs.com.cn/js/zt/DD_belatedPNG.js"></script> 
<script>DD_belatedPNG.fix('.ie6Box,img');</script> 
<![endif]-->
</head>

<body>

<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->

<div class="top_bg">
	<div class="mod_ban ban1"></div>
    <div class="mod_ban ban2">
    	<!--<ul class="navList"> 
			<li class="nav_li"><a href="">上海</a></li> 
			<li><a href="">北京</a></li> 
			<li><a href="">广州</a></li> 
			<li><a href="">重庆</a></li> 
		</ul>-->
    </div>
</div>
<div class="wrap_bg">
	<div class="mod_Box bc0_mod">
    	<h3><span class="hdgzBtn1">活动细则</span></h3>
        <div class="itemBox">
        	<div class="leftBox">
            	<h4><b>APP专享</b><br/>扫二维码即可参与</h4>
                <p></p>
                <a class="mfxzBtn ie6Box" href="http://m.lvmama.com/download?ch=wanshuipiaoliu" title="免费下载"></a>
            </div>
        	
            <div class="rightBox">
	            <div class="slides"> 
					<ul class="slides_container"> 
					<@s.iterator value="map.get('${station}_zero_money_sh')" status="st">
						<li> 
							<img src="${imgUrl?if_exists}" width="643" height="366" alt="${title?if_exists}"/> 
							<div class="tmBox bc0jsBox"> 
								<p> 
									<em>${title?if_exists}</em> 
									<i>领票时间：${bakWord1?if_exists}</i> 
								</p> 
								<span> 
									<b>当天活动：</b><br/> 
									 ${bakWord2?if_exists}
								</span> 
							</div> 
						</li> 
						</@s.iterator>
					</ul> 
					<span class="prev"><img src="http://pic.lvmama.com/img/zt/wanshui/left.png" width="40" height="40" alt="上一张"></span> 
					<span class="next"><img src="http://pic.lvmama.com/img/zt/wanshui/right.png" width="40" height="40" alt="下一张"></span> 
				</div>   
            </div>
            
        </div>
    </div>

    <div class="mod_Box ms1_mod">
    	<h3><span class="hdgzBtn2">活动细则</span></h3>
        <div class="itemBox">
        	<ul class="ms1_list">
            	<li class="li_one">
                	<div class="timeBox">
                    	<b>距离下一波秒杀还有</b><span class="countdown effect" id="timer"><i></i>天<i></i>时<i></i>分<i></i>秒</span><b>开始</b>
            		</div>
                </li>
                <li class="li_two">
                	<h4><b>APP专享</b><br/>扫二维码即可参与</h4>
                	<p></p>
                	<a class="mfxzBtn ie6Box" href="http://m.lvmama.com/download?ch=wanshuipiaoliu" title="免费下载"></a>
                </li>
                <!--秒杀推荐-->
                <@s.iterator value="map.get('${station}_seckill_sh')" status="st">
				<!--<li>
                	<div class="picBox"><img src="${imgUrl?if_exists}" width="292" height="158" alt="${title?if_exists}"/></div>
                    <h5>${title?if_exists}</h5>
                    <div class="priceBox"><span>驴妈妈价&yen;<b>1</b></span><em>原价：&yen;${marketPrice?if_exists?replace(".0","")}</em></div>
                </li>-->
				<li>
                	<div class="picBox"><img src="${imgUrl?if_exists}" width="292" height="158" alt="${title?if_exists}"/></div>
                    <h5>${title?if_exists}</h5>
                    <div class="priceBox">
                    	<p><em>原价：&yen;${marketPrice?if_exists?replace(".0","")}</em><span>驴妈妈价&yen;<b>1</b></span></p>
                        <a class="djxqBtn ewmtcBtn">点击详情</a>
                    </div>
                </li>
                 </@s.iterator>
            </ul>
        	
        </div>
    </div>
    
    <!--买一送一-->
    <div class="mod_Box m1s1_mod">
    	<h3></h3>
        <div class="itemBox">
        	<ul class="ms1_list">
        	 <@s.iterator value="map.get('${station}_buy_one_show_sh')" status="st">
                <li>
                	<div class="picBox"><a href="${url?if_exists}" title="${title?if_exists}"><img src="${imgUrl?if_exists}" width="292" height="158" alt="${title?if_exists}"/></a>
                	<#if bakWord3?exists && bakWord3=="买A送A"><span class="tips1"></span></#if>
                	<#if bakWord3?exists && bakWord3=="买A送B"><span class="tips2"></span></#if>
                	</div>
                    <h5><a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a></h5>
                    <div class="priceBox">
                    	<p><em>原价：&yen;${marketPrice?if_exists?replace(".0","")}</em><span>驴妈妈价&yen;<b>${memberPrice?if_exists?replace(".0","")}</b>起</span></p>
                        <a class="djxqBtn" href="${url?if_exists}" title="${title?if_exists}">点击详情</a>
                    </div>
                </li>
               </@s.iterator>
            </ul>
        </div>
    </div>
    
    <div class="mod_Box bxzl_mod">
    	<h3></h3>
        <div class="itemBox">
        	<div class="leftBox">
            	<h4><b>APP专享</b><br/>扫二维码即可参与</h4>
                <p></p>
                <a class="mfxzBtn ie6Box" href="http://m.lvmama.com/download?ch=wanshuipiaoliu" title="免费下载"></a>
            </div>
            <div class="rightBox">
            </div>
        </div>
    </div>
</div>


<div class="PopBox"> 	
    <div class="PopBox_p">
    	<div class="tcc_mod1">
        	<h3></h3>
            <ul class="hdgz_list">
            	<li>1，此活动仅限驴妈妈新版本客户端（iphone、安卓、iPad、WP8）用户，请提前更新哦；</li>
                <li>2，同一账号同一手机限购1次包场产品，产品仅限页面展示的开园时间当天使用，不得转让；</li>
                <li>3，为安全起见，达到包场景区的当日入园上限，将停止售卖；</li>
                <li>4，若15分钟内未能支付成功，则该订单取消。</li>
                <li>5，出游人以预订时所填写为准，预订后不得变更出游人。</li>
            </ul>
        </div>
        <div class="tcc_mod2">
        	<h3></h3>
            <ul class="hdgz_list">
            	<li>1，此活动仅限驴妈妈新版本客户端（iphone、安卓、iPad、WP8）用户，请提前更新哦；</li>
                <li>2，同一账号同一手机限购1次1元产品；</li>
                <li>3，若15分钟内未能支付成功，则该订单取消；</li>
                <li>4，产品以第一次填写信息为准，转让无效。</li>
            </ul>
        </div>
		<div class="tcc_mod3">
        	<div class="close_btn"></div>
        </div>
        <div class="close_Btn ">关闭</div>
    </div>
</div>
<div class="pop_body_bg"></div>


<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js" type="text/javascript"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/wanshui/index.js"></script>
<script src="http://pic.lvmama.com/js/zt/wanshui/jquery.easing.min.js"></script> 
<script src="http://pic.lvmama.com/js/zt/wanshui/slides.min.jquery.js"></script>
<script>
$(function(){
	//热门推荐焦点图切换设置
	$('.slides').slides({
		preload: true,
		preloadImage: 'http://pic.lvmama.com/img/zt/wanshui/loading.gif',
		play: 3000,
		pause: 1500,
		hoverPause: true
	});
})
</script>

<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
<script src="http://www.lvmama.com/zt/000global/js/eventCM.js" type="text/javascript"></script>
</body>
</html>
