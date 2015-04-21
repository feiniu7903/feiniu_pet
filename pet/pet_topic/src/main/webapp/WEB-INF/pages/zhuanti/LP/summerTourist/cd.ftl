<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>暑期旅游推荐_暑期旅游好去处_暑期乐翻天全家环球行-驴妈妈旅游网</title>
<meta name="keywords" content="暑期旅游,暑期环球行" />
<meta name="description" content="新的一年暑期就要到了,忘却那些紧张的气氛,订一个暑期乐翻天全家环球行吧,和家人一起,不管是去海岛度假还是去文化探秘，或是领略自然风光或是感受城市休闲,驴妈妈旅游网都会给大家带来全新旅游度假行程！" />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/shuqi/trip_summer.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<!--[if IE 6]>
<script src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script>
<script>DD_belatedPNG.fix('.png_bg');</script>
<![endif]-->
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div id="fix_nav" class="png_bg">
	<div class="menu">
		<a href="#xueyuan" title="边玩边学感受学院风">边玩边学<br /><em class="red">感受</em>学院风</a>
	</div>
	<a href="javascript:scroll(0,0)" class="top_back">返回顶部</a>
	<em class="close" title="关闭">关闭</em>
</div>
<div class="wrap">
	<div class="p_rel header">
		<img src="http://pic.lvmama.com/img/zt/shuqi/head.jpg" alt="暑期乐翻天" />
		<h1>暑期乐翻天全家环球行</h1>
		<!-- S 出发地选择 -->
		<div class="p_abs city_sel">
			<ul class="clearfix city_area">
				<li><a href="http://www.lvmama.com/zt/lvyou/shuqi">上海出发</a></li>
				<li><a href="http://www.lvmama.com/zt/lvyou/shuqi/bj">北京出发</a></li>
				<li><a href="http://www.lvmama.com/zt/lvyou/shuqi/gz">广州出发</a></li>
				<li class="cur">成都出发</a></li>
			</ul>
		</div><!-- E 出发地选择 -->
	</div>
	<div class="cont_wrap">
		<h2 class="dn">驴妈妈暑期度假环球行指南</h2>
		<!-- S tabs -->
		<div class="tabs">
			<ul>
				<li class="edu cur">
					<p id="xueyuan" class="edu_cont">边玩边学<em>感受</em>浓浓学院风
					暑期特别限定行程，感受异域浓郁学术风<br />（仅6.15~8.25期间限量行程）</p>
				</li>
			</ul>
		</div>
		<div id="summer_product">

			<!-- S 学院风 -->
			<div class="pro_cont college">
				<div class="summer_intro">
					<h3>暑期限定</h3>
					<ul class="t_list">
						<li>当地特色景点全程陪同游览，食住行游购娱全面贴心服务</li>
						<li>世界名校游览+特色课程体验，让孩子开阔眼界树立远大目标</li>
						<li>深入当地居民家中，体验当地人文风貌，真实全验多元文化</li>
						<li>6至12天精致紧凑行程，极力打造沪上最具性价比游学体验</li>
					</ul>
				</div>
				<!-- S 产品区 -->
				<div class="col_pro_cont pr360 edutainment">
					<h3>适合年龄段：<em>10岁以下</em></h3>
					<div class="children_list">
						<ul>
							<@s.iterator value="map.get('${station}_cd_xyf_10yx')" status="st">
							<li <@s.if test="#st.index == 0">class="cur"</@s.if>>
								<h4><a target="_blank"  href="${url?if_exists}">${title?if_exists}</a></h4>
								<div class="p_abs price">
									&yen;<em>${memberPrice?if_exists?replace(".0","")}</em>起<a target="_blank"  href="${url?if_exists}" class="btn_yd" title="立即预订">立即预订</a>
								</div>
								<ol>
									<@s.if test="bakWord1!=null || bakWord1==''"><li><em>1.</em>${bakWord1}</li></@s.if>
									<@s.if test="bakWord2!=null || bakWord2==''"><li><em>2.</em>${bakWord2}</li></@s.if>
									<@s.if test="bakWord3!=null || bakWord3==''"><li><em>3.</em>${bakWord3}</li></@s.if>
								</ol>
							</li>
							</@s.iterator>							
						</ul>
						<@s.if test="map.get('${station}_cd_xyf_10yx')!=null && map.get('${station}_cd_xyf_10yx').size>3">
						<p class="t_r mt10 toggle"><span class="open">展开</span></p>
						</@s.if>
					</div>
				</div><!-- E 产品区 -->
				<!-- S 产品区 -->
				<div class="col_pro_cont pl360 wonderful">
					<span class="dot"></span>
					<h3>适合年龄段：<em>10岁~15岁</em></h3>
					<div class="children_list">
						<ul>
							<@s.iterator value="map.get('${station}_cd_xyf_10z15')" status="st">
							<li <@s.if test="#st.index == 0">class="cur"</@s.if>>
								<h4><a target="_blank"  href="${url?if_exists}">${title?if_exists}</a></h4>
								<div class="p_abs price">
									&yen;<em>${memberPrice?if_exists?replace(".0","")}</em>起<a href="${url?if_exists}" class="btn_yd" title="立即预订">立即预订</a>
								</div>
								<ol>
									<@s.if test="bakWord1!=null || bakWord1==''"><li><em>1.</em>${bakWord1}</li></@s.if>
									<@s.if test="bakWord2!=null || bakWord2==''"><li><em>2.</em>${bakWord2}</li></@s.if>
									<@s.if test="bakWord3!=null || bakWord3==''"><li><em>3.</em>${bakWord3}</li></@s.if>
								</ol>
							</li>
							</@s.iterator>
						</ul>
						<@s.if test="map.get(${station}_cd_xyf_10z15')!=null && map.get('${station}_cd_xyf_10z15').size>3">
						<p class="t_r mt10 toggle"><span class="open">展开</span></p>
						</@s.if>
					</div>
				</div><!-- E 产品区 -->
				<!-- S 产品区 -->
				<div class="col_pro_cont pr360 exotic">
					<span class="dot"></span>
					<h3>适合年龄段：<em>15岁以上</em></h3>
					<div class="children_list">
						<ul>
							<@s.iterator value="map.get('${station}_cd_xyf_15ys')" status="st">
							<li <@s.if test="#st.index == 0">class="cur"</@s.if>>
								<h4><a target="_blank"  href="${url?if_exists}">${title?if_exists}</a></h4>
								<div class="p_abs price">
									&yen;<em>${memberPrice?if_exists?replace(".0","")}</em>起<a target="_blank"  href="${url?if_exists}" class="btn_yd" title="立即预订">立即预订</a>
								</div>
								<ol>
									<@s.if test="bakWord1!=null || bakWord1==''"><li><em>1.</em>${bakWord1}</li></@s.if>
									<@s.if test="bakWord2!=null || bakWord2==''"><li><em>2.</em>${bakWord2}</li></@s.if>
									<@s.if test="bakWord3!=null || bakWord3==''"><li><em>3.</em>${bakWord3}</li></@s.if>
								</ol>
							</li>
							</@s.iterator>
						</ul>
						<@s.if test="map.get('${station}_cd_xyf_15ys')!=null && map.get('${station}_cd_xyf_15ys').size>3">
						<p class="t_r mt10 toggle"><span class="open">展开</span></p>
						</@s.if>
					</div>
				</div><!-- E 产品区 -->
			</div><!-- E 学院风 -->
		</div>
		<!-- S 热门活动进行时 -->
		<div class="p_rel hot_acting">
			<span class="dot"></span>
			<h3>热门活动进行时</h3>
			<div class="prize" id="prize">
            <div class="prize_img">
                <ul class="prize_con" id="prize_con">
                    <li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/ouzhou 
"><img src="http://pic.lvmama.com/opi/0626guanggao.jpg" width="284" height="135"></a></li> 
<li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/maerdaifu 
"><img src="http://pic.lvmama.com/opi/0626guanggao1.jpg" width="284" height="135"></a></li> 
<li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/shuqi 
"><img src="http://pic.lvmama.com/opi/0626guanggao4.jpg" width="284" height="135"></a></li> 
<li><a target="_blank" href="http://www.lvmama.com/zt/abroad/ 
"><img src="http://pic.lvmama.com/opi/0626guanggao5.jpg" width="284" height="135"></a></li> 
<li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/zdf"><img src="http://pic.lvmama.com/opi/0626guanggao6.jpg" width="284" height="135"></a></li> 
<li><a target="_blank" href="http://www.lvmama.com/zt/product/chujing/"><img src="http://pic.lvmama.com/opi/0626guanggao7.jpg" width="284" height="135"></a></li> 
<li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/dishini 
"><img src="http://pic.lvmama.com/opi/0626guanggao8.jpg" width="284" height="135"></a></li> 
<li><a target="_blank" href="http://www.lvmama.com/zt/lvyou/qianzheng"><img src="http://pic.lvmama.com/opi/0626guanggao9.jpg" width="284" height="135"></a></li>
                </ul>
             </div>
            <a class="btn_lt"></a>
           <a class="btn_rt"></a>
        </div>
		</div><!-- E 热门活动进行时 -->
	</div>
	<!-- S footer -->
	<div class="footer">   		
		  <script type="text/javascript" src="http://www.lvmama.com/zt/000global/js/ztFooter.js"></script>
	</div><!-- E footer -->
</div>
<!-- S 背景 -->
<div class="body_bg_wrap">
	<div class="body_bg1"></div><div class="body_bg2"></div>
</div>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/advertising/jquery.imgSlide.js"></script>
<!-- E 背景 -->
<script type="text/javascript">
		$(function(){
			/* tab菜单切换
			var $tabs_li = $(".tabs li");
			var $sum_cont = $("#summer_product .pro_cont");
			$tabs_li.click(function(){
				$(this).addClass("cur").siblings().removeClass("cur");
				var tab_index = $tabs_li.index(this);
				$sum_cont.eq(tab_index).show(200).siblings().hide(200);
			});*/
			// 跟随菜单
			$("#fix_nav em.close").click(function(){
				$(this).parent().hide(400);
			});
			//广告位图片切换代码
			$("#prize").imgSlide({scro_screen:false,auto_scroll:true});
			var $fix_nav = $("#fix_nav .menu a");
			//var $main_cont = $("#summer_product .pro_cont");
			$fix_nav.click(function(){
				var tab_index = $fix_nav.index(this);
				$sum_cont.eq(tab_index).show(200).siblings().hide(200);
				$tabs_li.eq(tab_index).addClass("cur").siblings().removeClass("cur");
			});
			// 切换
			var $sort_li = $(".sort_data li,.children_list ul >li");
			var $sort_det = $(".details_cont .details_cont_list");
			$sort_li.hover(function(){
				$(this).addClass("cur").siblings().removeClass("cur");
				var s_index = $sort_li.index(this);
				$sort_det.eq(s_index).show().siblings().hide();
			});
			// 产品展开
			$(".cont_data .cont_list").height(136);
			var $tog_btn = $(".cont_data .open");
			$tog_btn.toggle(function(){
				var list_num = $(this).parents(".cont_data_wrap").find(".cont_list li").length;
				var list_h = list_num*68;
				$(this).parents(".cont_data_wrap").find(".cont_list").animate({height:list_h},300);
				$(this).text("关闭");
				$(this).addClass("close");
			},function(){
				$(this).parents(".cont_data_wrap").find(".cont_list").animate({height:'136px'},200);
				$(this).text("展开");
				$(this).removeClass("close");
			});
			// 学院风
			var $op_btn = $(".children_list .open");
			$op_btn.toggle(function(){
				$(this).parents(".children_list").find("ul").css("height","auto");
				$(this).text("关闭");
				$(this).addClass("close");
			},function(){
				$(this).parents(".children_list").find("ul>li:first").addClass("cur").siblings().removeClass("cur");
				$(this).parents(".children_list").find("ul").css("height",'252px');
				$(this).text("展开");
				$(this).removeClass("close");
			});
		});
</script>
<!-- JiaThis Button BEGIN -->
<script type="text/javascript" src="http://v2.jiathis.com/code/jiathis_r.js?btn=r3.gif&move=0" charset="utf-8"></script> 
<!-- JiaThis Button END -->
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script> 
</body>
</html>