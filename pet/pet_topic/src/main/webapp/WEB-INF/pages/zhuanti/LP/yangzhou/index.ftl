<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" />
<title>扬州特色景点介绍_扬州有哪些景点_扬州有什么好玩的-驴妈妈旅游网</title>
<meta content="扬州景点,扬州旅游" name="keywords" />
<meta content="烟花三月下扬州,扬州可以说为江南一方美景,瘦西湖,个园等特色景点都为著名旅游胜地,除了游玩,还有当地扬州美食,艺术文化等,都是大玩特玩的好地方." name="description" />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/yzly/yangzhou.css"  rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<!--[if IE 6]> 
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script> 
<script type="text/javascript"> 
DD_belatedPNG.fix('.gp_bg'); 
</script> 
<![endif]--> 
<base target="_blank" />
</head>

<body>
	<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div id="wrap">
	<div class="banner">
    	<img src="http://pic.lvmama.com/img/zt/yzly/banner1.jpg" width="1002" height="179" alt="扬州旅游" />
        <img src="http://pic.lvmama.com/img/zt/yzly/banner2.jpg" width="1002" height="179" alt="扬州景点介绍" />
    </div><!--banner end-->
    <div class="page">
			<div class="group">
			<@s.iterator value="map.get('${station}_yztg')" status="st">
            	<div class="group_lt">
                	<a  class="gp_link" href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="402" height="252"  alt="${title?if_exists}"/></a>
                    <p class="gp_bg">
                    <del>原价：${marketPrice?if_exists?replace(".0","")}元</del>
                    <b>团购价：<em>${memberPrice?if_exists?replace(".0","")}</em>元</b>
                    </p>
                    <p><strong>【${bakWord1?if_exists}】</strong>${remark?if_exists}<a href="${url?if_exists}">[查看详情]</a></p>
                </div><!--group_left end-->
            </@s.iterator>    
                <div class="group_rt">
                	<h3>『休闲小城扬州 属于你的慢生活』</h3>
                    <p>又是春始，又到“烟花三月下扬州”时节，有诗云“若到江南赶上春，千万和春住”，这第一站当然非扬州莫属。有诗形容扬州是“二分无赖”，那扬州到底“赖”在哪里，作为一个匆匆游客，不妨深入老扬州人的生活，从早上“皮包水”到晚上“水包皮”，漫游扬州这个老城，细品那份闲适和古意……</p>
                    <dl>
                    	<dt>去扬州的五个理由：</dt>
                        <dd>1、闻名天下的扬州三把刀：厨刀、修脚刀、理发刀</dd>
                        <dd>2、富春茶社的汤包 早晨从“皮包水”开始</dd>
                        <dd>3、二十四桥明月夜——游览瘦西湖</dd>
                        <dd>4、个园——中国四大园林之一</dd>
                        <dd>5、晚上享受“水包皮” 足浴好享受</dd>
                    </dl>
                   
                </div>
            </div><!--group end-->
            <div class="product">
            	<h3 class="speaci"><span>『特色自由行』</span></h3>
                <div class="pro_list">
                		<div class="pro_lt">
                		<@s.iterator value="map.get('${station}_yzzyx')" status="st">
                        	<dl>
                            	<dt><span><i></i><em>${title?if_exists}</em><i  class="icon_rt"></i></span><a href="${url?if_exists}" class="more">更多&gt;&gt;</a></dt>
                                <@s.iterator value="map.get('${station}_yzzyxtt_${st.index+1}')" status="st">
                                <dd class="pro_img">
                                	<a  class="pro_link" href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="108" alt="${title?if_exists}" /></a>
                                    <div class="pro_text">
                                    	<a href="${url?if_exists}"><b>${title?if_exists}</b><span class="share ${bakWord1?if_exists}"></span></a>
										<p class="padd_val">◎ ${remark?if_exists}</p>
										<p>◎ ${bakWord3?if_exists}</p>
                                        <p class="padd_val"><font>¥<em>${memberPrice?if_exists?replace(".0","")}</em></font><a href="${url?if_exists}"></a></p>
                                    </div>
                                </dd>
                                </@s.iterator>
                                <@s.iterator value="map.get('${station}_yzzyx_${st.index+1}')" status="st">
                                <dd class="pro_desc">
                                	<div>
                                	<a href="${url?if_exists}">·${title?if_exists}</a>
                                    <p>${remark?if_exists}</p>
                                    </div>
                                     <p class="desc_price"><font>¥<em>${memberPrice?if_exists?replace(".0","")}</em></font>起</p>
                                </dd>
                                </@s.iterator>
                            </dl>
                            </@s.iterator>
                        </div><!--pro_lt end-->
                        <div class="pro_rt" id="big_img">
                        		<div class="small_img">
                        			<p class="line">【扬州景点导游图】</p>
                                    <p class="posi"><img src="http://pic.lvmama.com/img/zt/yzly/yangzhou1.jpg" width="263" height="309" alt="扬州" /><a class="more big_img" >点击查看大图</a></p>
                                    <p><a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659-3.html" target="_blank">扬州主要景点推荐，蜀冈-瘦西湖区：瘦西湖、大明寺、观音山、唐城遗址等。古城区：个园、何园、汪氏小苑、吴道台宅第等。新区：扬州文化艺术中心、京华城全生活广场等……</a></p>
                                </div>
                                <div class="small_img">
                        			<p class="line">【扬州交通地图】</p>
                                    <p class="posi"><img src="http://pic.lvmama.com/img/zt/yzly/yangzhou2.jpg" width="259" height="346" alt="扬州交通" /><a class="more big_img">点击查看大图</a></p>
                                    <p><a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659-11.html" target="_blank">扬州火车站位于扬州城西郊文昌西路，公交22、26路可达。扬州目前有到北京的直达特快列车和到上海、汉口、西安、广州的列车，从南京到扬州的列车班次较多……</a></p>
                                </div>
                        </div><!--pro_rt end-->
                </div><!--pro_list end-->
      </div>
            <!--product end-->
      <div class="product">
                <h3 class="posi_h3"><span>『特色跟团游』</span></h3>
                  <div class="pro_list pro_gp">
                  		<div class="pro_lt">
                        	<p class="line">【扬州自助游全攻略】</p>
                            <a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659.html" target="_blank" class="imgbg2"><img src="http://pic.lvmama.com/img/zt/yzly/yangzhou_254x144.jpg" width="254" height="144" alt="扬州旅游攻略" /></a>
                            <p class="more_text">烟花三月下扬州，又到了春天江南万物生发的季节，第一站当然逃不开扬州了。每年的4月18日——5月18日期间，扬州都会举办“烟花三月经贸旅游节”，此时的扬州烟雨蒙蒙，琼花盛开，是诗人眼中最美的季节，也是一年中最热闹的季节……<a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659.html" target="_blank">[详情]</a></p>
                        </div>
                        <div class="pro_rt">
                        	<strong class="tyou">特色跟团游</strong>
                        	<ul class="tab" id="tab">
                        	<@s.iterator value="map.get('${station}_yzgty')" status="st">
                            	<li<#if st.index=0> class="cur"</#if>>${title?if_exists}</li>
                            </@s.iterator>
                            </ul>
                            
                            <div id="tab_con">
                                <@s.iterator value="map.get('${station}_yzgty')" status="st">
                                <ul class="gty<#if st.index=0> dis</#if>">
                                    <@s.iterator value="map.get('${station}_yzgty_${st.index+1}')" status="st">
                                    <li>
                                        <div  class="gty_lt">
                                            <a href="${url?if_exists}">${title?if_exists}</a>
                                            <p>${remark?if_exists}</p>
                                        </div>
                                        <p class="gty_rt"><span><font>¥<em>${memberPrice?if_exists?replace(".0","")}</em></font>起</span><a href="${url?if_exists}"></a></p>
                                    </li>
                                    </@s.iterator> 
                                    <li class="last_li"><a href="${url?if_exists}" class="more">更多&gt;&gt;</a></li>
                                </ul>
                              </@s.iterator>
                           </div><!--tab_con end-->
                        </div>
                  </div><!--pro_list end-->
      </div><!--product end-->       
      <div class="product">
                <h3 class="posi_h3"><span>『扬州门票』</span></h3>
                  <div class="pro_list pro_gp">
                  		<div class="pro_lt">
                        	<p class="line">【漫游个园、何园 品园林艺术精华】</p>
                            <a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659-6.html" target="_blank" class="imgbg2"><img src="http://pic.lvmama.com/opi/jinguanyu.jpg" width="254" height="144" alt="个园" /></a>
                            <p class="more_text">个园是中国四大名园之一，她的正门在东关街上。个园由两淮盐业商总黄至筠建于清嘉庆23年(公元1818年)，是扬州历史最悠久、保存最完整、最具艺术价值的园林了…<a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659-6.html" target="_blank">[详情]</a></p>
                            <p class="line line_border">【谁知花局里  歌吹是扬州】</p>
                            <a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659-5.html" target="_blank" class="imgbg2"><img src="http://pic.lvmama.com/opi/hujuli1.jpg" width="254" height="144" alt="双东街" /></a>
                            <p class="more_text">“烟花三月下扬州”的古典名句，就像是今日“花局里”的聚焦和写照。花局里位于扬州古城东区，南面就是双东历史街区东关街，西面相邻中国四大名园之一的个园，北面是一条工艺美术街盐阜路，东边相接古运河和宋大城东门遗址，面积达一万多平米……<a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659-5.html" target="_blank">[详情]</a></p>
                        </div>
                        <div class="pro_rt pro_hsmp">
                            <ul class="hsmp">
                                <@s.iterator value="map.get('${station}_yzmp_1')" status="st">
                                <@s.if test="#st.index%3 == 0">
                            	<li>
                            	</@s.if>
                                	<div class="hsmp_box">
                                    	<a href="${url?if_exists}"><img src="${imgUrl?if_exists}" height="112" width="201" alt="${title?if_exists}" /></a>
                                        <p>${title?if_exists}</p>
                                        <p class="order"><span><font>¥<em>${memberPrice?if_exists?replace(".0","")}</em></font>起</span><a href="${url?if_exists}"></a></p>
                                    </div>
                                <@s.if test="(#st.index+1)%3 == 0">
                            	</li>
                            	</@s.if>
                              </@s.iterator>
                            </ul>
                            <@s.iterator value="map.get('${station}_yzmp')" status="st">
                              <a href="${url?if_exists}" class="more">更多&gt;&gt;</a>
                           </@s.iterator>
                        </div><!--pro_hsmp end-->
                  </div><!--pro_list end-->
      </div><!--product end-->       
       <div class="product">
                <h3 class="posi_h3"><span>『特色精品酒店』</span></h3>
                  <div class="pro_list pro_hotel">
                  		<div class="pro_lt">
							<@s.iterator value="map.get('${station}_yzjd')" status="st">
                        		<dl>
                                	<dt><span><i></i><em>${title?if_exists}</em><i  class="icon_rt"></i></span><a class="more" href="${url?if_exists}">更多&gt;&gt;</a></dt>
                                    <@s.iterator value="map.get('${station}_yzjd_${st.index+1}')" status="st1">
                                    <@s.if test="#st1.index%2 == 0">
                            	    <dd>
                            	    </@s.if>
                                    	<div class="hotel_box">
                                        	<a class="hotel_img" href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="120" height="61"  alt="" /></a>
                                            <p><a href="${url?if_exists}">${title?if_exists}</a>
                                	         <span class="${bakWord2?if_exists}"></span>
                                            </p>
                                            <p>${remark?if_exists}</p>
                                            <p class="hotel_price"><font>¥<em>${memberPrice?if_exists?replace(".0","")}</em></font>起</p>
                                        </div>
                                    <@s.if test="(#st1.index+1)%2 == 0">
                            	    </dd>
                            	    </@s.if>
                                    </@s.iterator>
                                </dl>
                                </@s.iterator>
                        </div>
                        <div class="pro_rt">
                        		<p class="line">【扬州住宿指南 百年老字号旅社】</p>
                                <a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659-9.html" target="_blank" class="imgbg2"><img height="144" width="254" src="http://pic.lvmama.com/img/zt/yzly/yangzhou3.jpg" alt="扬州住宿" /></a>
                                <dl class="kezhan">
                                	<dt><b>扬州住宿:</b></dt>
                                   
                                    <dd>到扬州旅游，可以很容易地找到合适的宾馆，这里拥有各种档次的宾馆适合各个层次的旅游者，无论下榻何处，都能得到令人满意的服务……<a href="http://www.lvmama.com/info/lvxingtop/2012-0314-45659-9.html" target="_blank">［详情］</a></dd>
                                </dl>
                                <p class="line line_border">【住看得见历史的长乐客栈】</p>
                                <a href="http://www.lvmama.com/guide/2012/0319-161914.html" target="_blank" class="imgbg2"><img height="144" width="254"  src="http://pic.lvmama.com/img/zt/yzly/jiudianYANG.jpg" alt="长乐客栈" /></a>
                                <dl class="kezhan kezhan2">
                                	<dt><b>扬州住宿:</b></dt>
                                	
                                    <dd>扬州的精品酒店不仅古色古香，充满古韵，而且环境优美，服务到位。这样的精品酒店全国少见，价格也比其他城市便宜得多。住在这一园林之中的精品酒店，徜徉在东关街上，游游扬州古运河。不仅可以品尝到扬州的美食，更让人感受到入住一天，穿越千年啊…<a href="http://www.lvmama.com/guide/2012/0319-161914.html" target="_blank">［详情］</a></dd>
                                </dl>
                        </div>
                  		
                  </div><!--pro_hotel end-->
      </div><!--product end-->       
      <div class="product gonglue"> 
      		   <h3 class="posi_h3"  style=" font-weight:normal"><span>『旅游攻略』</span></h3>
               <div class="pro_list">
                 <a href="http://www.lvmama.com/dest/jiangsu_yangzhou/guide" target="_blank" class="imgbg3"><img src="http://pic.lvmama.com/img/zt/yzly/yangzhou_232x270.jpg" width="230" height="269" alt="扬州攻略" /></a>
                 <dl>
                    	<dt>【扬州新老玩法推荐】</dt>
                        <dd class="sj">你属于哪座城？你在哪里工作？是否每日步履匆匆，忙碌不堪？你是否也曾这样想过“给我生活，地方随便”？暂时逃离，找一座慢城去慢游，让灵魂跟上脚步。小编精心准备 ，扬州新老路线详细攻略，带你真正的慢游扬州……</dd>
                     <dd><a href="http://www.lvmama.com/dest/jiangsu_yangzhou/guide" target="_blank">[查看详情］</a></dd>
                        <dd><a href="http://www.lvmama.com/guide/downs.php?f=yangzhou.pdf" target="_blank" class="load">[官方攻略下载］</a></dd>
                    </dl>
                    <a href="http://www.lvmama.com/guide/zt/yangzhou/ " target="_blank" class="imgbg3"><img src="http://pic.lvmama.com/img/zt/yzly/yz_232x270.jpg" width="230" height="269" alt="" /></a>
                    <dl>
                    	<dt>【慢时光的绝妙之地】</dt>
                        <dd class="sj">暂时逃离，找一座慢城去慢游，让灵魂跟上脚步。“烟花三月”是扬州的节日，四月江南是春日的气息。带上行囊，跟着小编，在这座慢城里，乘小舟游览那一泓曲水“瘦西湖”，漫步于晚清的园林 街巷，穿越于二十四桥之上，行住于看得见历史的长乐客栈，放松于湖边的天沐温泉……</dd>
                        <dd><a href="http://www.lvmama.com/guide/zt/yangzhou/ " target="_blank">[查看详情］</a></dd>
                        <!--<dd><a href="http://www.lvmama.com/guide/zt/yangzhou/" target="_blank" class="load">[官方攻略下载］</a></dd>-->
                    </dl>
		</div><!--pro_list end-->
                <div class="pro_list pro_enjoy">
                		<dl>
                    	<dt>【免费游瘦西湖+个园+何园！】</dt>
                        <dd class="sj">去江南最美的地方—扬州，漫步湖畔 静享春意江南！
获奖条件：1）关注@ 驴妈妈旅游网 2）转发此条微博并@一位好友；就有机会得到扬州瘦西湖+何园+个园三点联票2张。4月12日抽出5名幸运粉丝公布。
</dd>
                        <dd><a href="http://e.weibo.com/1534939700/ybRnExxuq" target="_blank">点击分享微博，赢大奖</a></dd>
                  	   </dl>
                       <dl class="enjoy_pic">
                       		<dt>【最美扬州美景欣赏】</dt>
                            <dd><img src="http://pic.lvmama.com/img/zt/yzly/yangzhou_675x320.jpg" alt="图片欣赏" width="675" height="320" border="0" usemap="#Map" />
<map name="Map" id="Map">
  <area shape="rect" coords="481,4,671,102" href="http://www.lvmama.com/info/chinayouji/2012-0315-47299-3.html" target="_blank" /><area shape="rect" coords="353,108,513,202" href="http://www.lvmama.com/info/chinayouji/2012-0315-47299-4.html" target="_blank" /><area shape="rect" coords="5,4,205,139" href="http://www.lvmama.com/info/chinayouji/2012-0315-47299-5.html" target="_blank" />
<area shape="rect" coords="0,142,124,317" href="http://www.lvmama.com/info/chinayouji/2012-0315-47299.html" target="_blank" /><area shape="rect" coords="350,4,478,102" href="http://www.lvmama.com/info/chinayouji/2012-0315-47299-2.html" target="_blank" /><area shape="rect" coords="133,147,205,199" href="http://www.lvmama.com/info/chinayouji/2012-0315-47299-7.html" target="_blank" /><area shape="rect" coords="127,205,514,319" href="http://www.lvmama.com/info/chinayouji/2012-0315-47299-6.html" target="_blank" /><area shape="rect" coords="519,112,672,312" href="http://www.lvmama.com/info/chinayouji/2012-0315-47299.html" target="_blank" /><area shape="rect" coords="205,4,346,200" href="http://www.lvmama.com/info/chinayouji/2012-0315-47299-8.html" target="_blank" /></map></dd>
                       </dl>
                </div><!--pro_list end-->                                 
      </div><!--product end-->         
	</div><!--page end-->

	<div class="bg_container">
    		<div class="wrapbg1"></div>
            <div class="wrapbg2"></div>
            <div class="wrapbg3"></div>
    </div>
	 <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
</div><!--wraper end-->
<div id="ban_bg"></div>
<div id="big_pic">
	<a class="close"><img src="http://pic.lvmama.com/img/zt/yzly/closed.gif" width="47" height="19" alt="关闭" /></a>
    <img src="http://pic.lvmama.com/img/zt/yzly/BIGyangzhou1.jpg" width="476" height="548" alt="扬州交通地图" />
    <img src="http://pic.lvmama.com/img/zt/yzly/yyy.gif" width="510" height="520" alt="交通地图" />
</div>  
<script type="text/javascript">
		$(function(){
			var index=0;
			var top;
			var _height=$(document).height();
			if(!window.XMLHttpRequest){
				$("#ban_bg").css({"position":"absolute","top":0,"height":_height});
				}
			$("#tab li").hover(function(){
				index=$(this).index(); 
				$("#tab li").removeClass("cur").eq(index).addClass("cur");
				$("#tab_con .gty").hide().eq(index).show();
			});
				//点击关闭
			$("#big_pic .close").click(function(){
					$(this).parent().hide();
					$("#ban_bg").hide();
			});
			//点击显示大图片
			$("#big_img .big_img").click(function(){
				top=$(document).scrollTop();
				index=$("#big_img .big_img").index(this);
				$("#big_pic").css("top",top+100);
				$("#big_pic,#ban_bg").show();
				$("#big_pic>img").hide().eq(index).show();
			});
		});
</script> 
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
