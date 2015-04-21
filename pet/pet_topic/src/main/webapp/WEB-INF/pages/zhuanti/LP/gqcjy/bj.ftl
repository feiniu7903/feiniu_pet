<!DOCTYPE html>
<head>
<meta  charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>北京出发国庆出境游_十一北京出发出境游线路-驴妈妈旅游网</title>
<meta name="keywords" content="北京出发,国庆出境游,十一旅游"/>
<meta name="description" content=" 驴妈妈国庆出境游专题:选择聪明的旅行!我们为您提供北京出发国庆出境游,十一北京出发出境游线路及实时比价服务，是您预订旅行产品,跟团游及自由行的最佳选择!"/>
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/guoqing2/css.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<script src="http://pic.lvmama.com/js/zt/guoqing2/slidefocus.js" type="text/javascript"></script>
<base target="_blank" />
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>

<div class="wrap">
	<div class="wrapbot">

		<div class="citymenu">
			<ul class="cityitems clearfix">
				<li><a href="index.html" target="_self"><span>上海/南京/杭州<b></b></span></a></li> 
				<li><a href="cd.html" target="_self"><span>成都/重庆<b></b></span></a></li> 
				<li class="now"><a href="javascript:void(0)" target="_self"><span>北京<b></b></span></a></li> 
				<li><a href="gz.html" target="_self"><span>广州<b></b></span></a></li> 
				<li><a href="sz.html" target="_self"><span>深圳/香港<b></b></span></a></li>
			</ul>
		</div>

		<div class="cont">
			<div class="state">
				<ul class="stateitems clearfix">
					<@s.iterator value="map.get('${station}_bj')" status="st">
						<@s.if test="#st.isFirst()">
							<li class="curr">
								<a href="javascript:void(0)" target="_self">
									${title?if_exists}
									<b></b>
								</a>
							</li>
						</@s.if>
						<@s.elseif test="#st.isLast()">
							<li class="last">
								<a href="javascript:void(0)" target="_self">
									${title?if_exists}
									<b></b>
								</a>
							</li>
						</@s.elseif>
						<@s.else>
							<li>
								<a href="javascript:void(0)" target="_self">
									${title?if_exists}
									<b></b>
								</a>
							</li>
						</@s.else>
					</@s.iterator>
				</ul>
			</div>
			<div class="clearfix">
				<!--左边主题-->
				<div class="tabbox">
					<!--十个国家-->
					<@s.iterator value="map.get('${station}_bj')" status="st">
						<@s.if test="#st.isFirst()">
							<div class="theme">
								<!--三个主题-->
								<@s.iterator value="map.get('${station}_bj_${st.index + 1}')" status="sts">
									<div class="zhuti1">
										<div class="thetop">${title?if_exists}</div>
										<div class="thecen">
											<ul class="themelist">
												<@s.iterator value="map.get('${station}_bj_${st.index + 1}_${sts.index + 1}')">
													<li>
														<a href="${url?if_exists}">${title?if_exists}</a>
														<@s.if test="null != bakWord3 && '' != bakWord3">
															<@s.if test="'tuijian' == bakWord3">
																<span class="tuijian"></span>
															</@s.if>
															<@s.if test="'rm' == bakWord3">
																<span class="hot"></span>
															</@s.if>
															<@s.if test="'tejia' == bakWord3">
																<span class="tejia"></span>
															</@s.if>
															<@s.if test="'xp' == bakWord3">
																<span class="new"></span>
															</@s.if>
															<@s.if test="'zzt' == bakWord3">
																<span class="zizu"></span>
															</@s.if>
															<@s.if test="'mysx' == bakWord3">
																<span class="miyue"></span>
															</@s.if>
															<@s.if test="'drdh' == bakWord3">
																<span class="duo"></span>
															</@s.if>
															<@s.if test="'zdzh' == bakWord3">
																<span class="zao"></span>
															</@s.if>
														</@s.if>
														<p>
															出发日期：
															<@s.if test="null != bakWord1 && '' != bakWord1">${bakWord1?if_exists}</@s.if>
														</p>
														<div>
															<@s.if test="null != bakWord2 && '' != bakWord2">${bakWord2?if_exists}</@s.if>
														</div>
														<span class="pri">
															￥ <em>${memberPrice?if_exists?replace(".0","")}</em>
															起
														</span>
													</li>
												</@s.iterator>
											</ul>
										</div>
										<div class="thebot"></div>
									</div>
								</@s.iterator>
							</div>
						</@s.if>
						<@s.else>
							<div class="theme" style="display:none;">
								<!--三个主题-->
								<@s.iterator value="map.get('${station}_bj_${st.index + 1}')" status="sts">
									<div class="zhuti1">
										<div class="thetop">${title?if_exists}</div>
										<div class="thecen">
											<ul class="themelist">
												<@s.iterator value="map.get('${station}_bj_${st.index + 1}_${sts.index + 1}')">
													<li>
														<a href="${url?if_exists}">${title?if_exists}</a>
														<@s.if test="null != bakWord3 && '' != bakWord3">
															<@s.if test="'tuijian' == bakWord3">
																<span class="tuijian"></span>
															</@s.if>
															<@s.if test="'rm' == bakWord3">
																<span class="hot"></span>
															</@s.if>
															<@s.if test="'tejia' == bakWord3">
																<span class="tejia"></span>
															</@s.if>
															<@s.if test="'xp' == bakWord3">
																<span class="new"></span>
															</@s.if>
															<@s.if test="'zzt' == bakWord3">
																<span class="zizu"></span>
															</@s.if>
															<@s.if test="'mysx' == bakWord3">
																<span class="miyue"></span>
															</@s.if>
															<@s.if test="'drdh' == bakWord3">
																<span class="duo"></span>
															</@s.if>
															<@s.if test="'zdzh' == bakWord3">
																<span class="zao"></span>
															</@s.if>
														</@s.if>
														<p>
															出发日期：
															<@s.if test="null != bakWord1 && '' != bakWord1">${bakWord1?if_exists}</@s.if>
														</p>
														<div>
															<@s.if test="null != bakWord2 && '' != bakWord2">${bakWord2?if_exists}</@s.if>
														</div>
														<span class="pri">
															￥ <em>${memberPrice?if_exists?replace(".0","")}</em>
															起
														</span>
													</li>
												</@s.iterator>
											</ul>
										</div>
										<div class="thebot"></div>
									</div>
								</@s.iterator>
							</div>
						</@s.else>
					</@s.iterator>
				</div>
				<!--右边广告-->
				<div class="ads">
					<ul class="banner" id="banner1">
						<li>
							<a href="http://www.lvmama.com/zt/product/chujing/  ">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad01.jpg" width="250" height="220" alt="" />
							</a>
						</li>
						<li style="display:none;">
							<a href="  http://www.lvmama.com/zt/abroad/">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad09.jpg" width="250" height="220" alt="" />
							</a>
						</li>
					</ul>
					<ul class="banner" id="banner2">
						<li>
							<a href="http://www.lvmama.com/zt/lvyou/maerdaifu  ">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad04.jpg" width="250" height="220" alt="" />
							</a>
						</li>
						<li style="display:none;">
							<a href=" http://www.lvmama.com/zt/lvyou/riben_1  ">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad08.jpg" width="250" height="220" alt="" />
							</a>
						</li>
					</ul>
					<ul class="banner" id="banner3">
						<li>
							<a href="http://www.lvmama.com/zt/lvyou/meiguo/       ">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad05.jpg" width="250" height="220" alt="" />
							</a>
						</li>
						<li style="display:none;">
							<a href="http://www.lvmama.com/zt/lvyou/ouzhou    ">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad06.jpg" width="250" height="220" alt="" />
							</a>
						</li>
					</ul>
					<ul class="banner" id="banner4">
						<li>
							<a href="http://www.lvmama.com/zt/lvyou/dishini  ">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad02.jpg" width="250" height="220" alt="" />
							</a>
						</li>
						<li style="display:none;">
							<a href="http://www.lvmama.com/zt/lvyou/dsn   ">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad03.jpg" width="250" height="220" alt="" />
							</a>
						</li>
					</ul>
					<ul class="banner" id="banner5">
						<li>
							<a href=" http://www.lvmama.com/zt/lvyou/zdf ">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad10.jpg" width="250" height="220" alt="" />
							</a>
						</li>
						<li style="display:none;">
							<a href=" http://www.lvmama.com/zt/lvyou/qianzheng ">
								<img src="http://pic.lvmama.com/img/zt/guoqing2/ad07.jpg" width="250" height="220" alt="" />
							</a>
						</li>
					</ul>

				</div>
			</div>
			<a href="http://www.lvmama.com/abroad 
" class="more"></a>
		</div>
		<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
	</div>
</div>
<div class="backtop">
	<a href="#" target="_self"></a>
</div>
<script>
	$(function(){
		var $lis =$('.stateitems li');
		$lis.hover(function(){
			var index = $lis.index(this);
			$(this).addClass('curr').siblings().removeClass('curr');
			$('.tabbox > div').eq(index).show().siblings().hide();
		});
		
		
		
		
		
		
		$.slidefocus('banner1');
		$.slidefocus('banner2');
		$.slidefocus('banner3');
		$.slidefocus('banner4');
		$.slidefocus('banner5');
});

</script>
<!-- JiaThis Button BEGIN -->
<script type="text/javascript" >
var jiathis_config={
	showClose:true,
	hideMore:false
}
</script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jiathis_r.js?btn=r5.gif&move=0" charset="utf-8"></script>
<!-- JiaThis Button END -->
<script type='text/javascript' src='http://log.51network.com/wits/jdelivery/pixel?type=sem&stype=fl&rt=1'></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>