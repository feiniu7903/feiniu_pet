<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>春季泡温泉推荐_春季泡温泉好吗_春季去哪泡温泉-驴妈妈旅游网</title>
<meta content="温泉,春季" name="keywords" />
<meta content="驴妈妈春季泡温泉推荐:春天来临,并不意味着温泉的告别,反而,越来越多人喜爱泡温泉的舒适.一边看着室外的风景,一边享受温泉的放松." name="description" />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/spring/spring.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script>
<script type="text/javascript">
   DD_belatedPNG.fix('div, ul, img, li, input , a');
</script>
<!--[if IE 6]>
<script type="text/javascript" src="/javascripts/DD_belatedPNG_0.0.8a.js" ></script>
<script type="text/javascript">
   DD_belatedPNG.fix('div, ul, img, li, input , a');
</script>
<![endif]-->
</head>

<body class="spring_body">
<a name="sp_top"></a> 
<!--header>>-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--header<<-->
<div class="spring_wrap">
        <div class="spring_header">
   		  <img src="http://pic.lvmama.com/img/zt/spring/banner_01.jpg" width="1055" height="100" alt="灿烂春光" />
          <img src="http://pic.lvmama.com/img/zt/spring/banner_02.jpg" width="1055" height="100" alt="泉" />
          <img src="http://pic.lvmama.com/img/zt/spring/banner_03.jpg" width="1055" height="100" alt="和你分享" />
        </div><!--header-->
        <div class="spring_content">
            <ul class="spring_box1">
                <li class="sp_box1_left">
                <div class="sp_box1_left_inner">
                  <div class="sp_box1_tit"><img src="http://pic.lvmama.com/img/zt/spring/box1_tit.jpg" width="86" height="355" alt="0元抢购，每周二等你" /></div>
                  <div class="sp_box1_cont">
					<@s.if test="null != map.get('${station}_mjqg_1') && !map.get('${station}_mjqg_1').isEmpty()">
						<@s.iterator value="map.get('${station}_mjqg_1')" status="st">
							<h3 class="sp_titxt">【限时抢购】<a href="${url?if_exists}" class="link_orange" target="_blank">${title?if_exists}</a></h3>
							<div class="sp_box1_img"><a href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="390" height="146" alt="${title?if_exists}" /></a></div>
							<div class="sp_txt">
								<div class="sp_txt_l">
								  ${bakWord1?if_exists}<br/>
								  ${bakWord2?if_exists}<br/>
								  ★${remark?if_exists}
								</div>
								<a href="${url?if_exists}" class="sp_txt_r sp_btn1" target="_blank"></a>
							</div>
						</@s.iterator>
					</@s.if>
                        <ul class="sp_box1_list">
                            <li>
                                 <div class="sp_titxt1"><em>周边景点</em></div>
								 <@s.iterator value="map.get('${station}_mjqg_2')" status="st">
									<a href="${url?if_exists}" class="sp_box1_linktxt" target="_blank"><strong>${title?if_exists}</strong><em>￥${memberPrice?if_exists?replace(".0","")}起</em></a>
								 </@s.iterator>
								 <!--a href="#" class="link_brown" target="_blank">查看更多&gt;&gt;</a-->
                            </li>
                            <li>
                                 <div class="sp_titxt1"><em>周边酒店</em></div>
                                 <@s.iterator value="map.get('${station}_mjqg_3')" status="st">
									<a href="${url?if_exists}" class="sp_box1_linktxt" target="_blank"><strong>${title?if_exists}</strong><em>￥${memberPrice?if_exists?replace(".0","")}起</em></a>
								 </@s.iterator>
                                 <!--a href="#" class="link_brown" target="_blank">查看更多&gt;&gt;</a-->
                            </li>
                        </ul>
                  </div><!--box1_cont-->
                  </div><!--sp_box1_left_inner-->
                </li><!--box1_left-->
				<@s.iterator value="map.get('${station}_mjtg')" status="st">
					<li class="sp_box1_right">
					  <a name="sp_tg"></a>
					  <div class="sp_box1_r_tit"></div>
					  <a class="sp_box1_right_img" href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" width="393" height="200" alt=" " /></a>
					  <h3 class="sp_titxt0"><a href="${url?if_exists}" class="link_brown1" target="_blank">${title?if_exists}</a></h3>
					  <p class="sp_box1_r_p">${remark?if_exists}</p>
					  <div  class="sp_box1_r_div">
						 <table class="sp_box1_r_tab">
						  <tr>
							<th>团购价</th>
							<th>折扣</th> 
							<th>节省</th>
							<th rowspan="2">
								<a href="${url?if_exists}" class="sp_btn2" target="_blank"></a>
							</th>
						  </tr>
						  <tr>
							<td>￥<em class="sp_num">${bakWord1?if_exists}</em></td>
							<td><em class="sp_num">${bakWord2?if_exists}</em>折</td>
							<td>￥<em class="sp_num">${bakWord3?if_exists}</em></td>
						   </tr>
						</table> 
					 </div>
					</li>
				</@s.iterator>
                <!--box1_right-->
            </ul><!--box1-->
            
            <div class="spring_box2">
             <a name="sp_ss"></a>
             <div class="sp_box3_tit">
             		<div class="sp_boxtit_txt">怡情山水间，不再是文人骚客闲云野鹤，你也可以享受这番浪漫，当温泉融入到山川湖泊，给你不一样的旅游体验。</div>
             </div>
               <div class="sp_box_inner">
                  <div class="sp_box_l">
                     <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-3.html" target="_blank"><img src="http://pic.lvmama.com/opi/shagnshui236147.jpg" width="236" height="147" alt="温泉山水旅游攻略" /></a>
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>温泉山水旅游攻略</em></h4>
                        <div class="sp_left_cont">
                          <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-3.html" target="_blank">春日总是乍暖还寒，躲了一冬的身体还在城市里张望春天，却不知郊外花苞已满枝，山水已苏醒！你的春宴开始准备了吗？赏花？踏春？……这样的线路多少有些单调，好不容易凑齐的行程，就走马观花了，确心有不甘。春日当爬山涉水，漫赏风光，且能劳其筋骨，还能身心舒畅，下面这些地方，不仅好山好水，还能泡温泉养健康，一天的身体劳累之后，泡泡温泉让疲乏一扫而光，绝对酣畅！</a>
                          </div>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门景点推荐</em></h4>
                         <ul class="sp_left_cont  sp_hotlist">
						 
						  <li><a href="http://www.lvmama.com/dest/zhedongdaxiagu" target="_blank">浙东大峡谷</a></li>
                             <li><a href="http://www.lvmama.com/dest/yeheqiu" target="_blank">野鹤湫</a></li>
							  <li><a href="http://www.lvmama.com/dest/yushuiwenquan" target="_blank">御水温泉</a></li>
                             <li><a href="http://www.lvmama.com/dest/lianghuangshan" target="_blank">梁皇山</a></li>
                             <li><a href="http://www.lvmama.com/dest/jiangsu_liyang/ticket_tab" target="_blank">天目湖</a></li>
                             <li><a href="http://www.lvmama.com/dest/jiangsu_wuxi" target="_blank">灵山大佛</a></li><br/>
							 <li><a href="http://www.lvmama.com/dest/shangougou" target="_blank">杭州山沟沟</a></li>
                             <li><a href="http://www.lvmama.com/product/246" target="_blank">浦江仙华山 </a></li>
							 <li><a href="http://www.lvmama.com/dest/xixishidi" target="_blank">西溪湿地</a></li>
                            
							 <li><a href="http://www.lvmama.com/product/88" target="_blank">南京颐尚温泉</a></li>
                             <li><a href="http://www.lvmama.com/product/3387" target="_blank">汤山一号温泉</a></li>
							 <li><a href="http://www.lvmama.com/product/2521" target="_blank">瘦西湖温泉</a></li>
                             
                         </ul>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门酒店推荐</em></h4>
                        <ul class="sp_left_cont  sp_hotlist1">
                            
                           
                             <li><a href="http://www.lvmama.com/product/28004" target="_blank">扬州玉蜻蜓雅致酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/yangzhouzitengyuanfan" target="_blank">扬州紫藤园饭店</a></li>
						
                             <li><a href="http://www.lvmama.com/dest/lynanshanxiangcunhuisuo" target="_blank">溧阳南海乡村会所</a></li>
                             <li><a href="http://www.lvmama.com/dest/liyanghantiandujiacun" target="_blank">溧阳涵田度假村酒店 </a></li>
                             <li><a href="http://www.lvmama.com/dest/yangzhoucuiyuanchengshi" target="_blank">扬州萃园城市酒店</a></li>
							   <li><a href="http://www.lvmama.com/dest/nhnanyuanwenquan" target="_blank">宁海南苑温泉山庄</a></li>
							    <li><a href="http://www.lvmama.com/dest/nhtaipingyang" target="_blank">宁海太平洋国际大酒店</a></li>
							 	 <li><a href="http://www.lvmama.com/dest/lyyushuiwenquan" target="_blank">溧阳天目湖御水温泉度假酒店</a></li>
                         </ul>
                     </div> <!-- sp_left_box1-->
                  </div>
                  <!--boxleft-->
                  <div class="sp_box_r">
					<@s.iterator value="map.get('${station}_mjlsss')" status="st">
                      <em class="sp_tit1">${title?if_exists}</em>
                      <ul class="sp_prolist">
						<@s.iterator value="map.get('${station}_mjlsss_${st.index + 1}')">
                          <li>
                             <span class="sp_prolist_l1">
                               <a href="${url?if_exists}" class="sp_prolist_tit" target="_blank"><b>【${bakWord1?if_exists}】</b>${title?if_exists} <em class="${bakWord2?if_exists}">&nbsp;</em></a>
                               <a href="${url?if_exists}" class="sp_prolist_info"  target="_blank">${remark?if_exists}</a>
                             </span>
                             <span class="sp_prolist_l2">￥<em>${marketPrice?if_exists?replace(".0","")}</em></span>
                             <span class="sp_prolist_l3">￥<em>${memberPrice?if_exists?replace(".0","")}</em>起</span>
                             <span class="sp_prolist_l4"><a href="${url?if_exists}" class="sp_btn2" target="_blank"></a></span>
                          </li>
                        </@s.iterator>
                      </ul><!--box_ultop-->
                      <!--div class="sp_more"><a href="#" class="link_brown" target="_blank">更多&gt;&gt;</a></div-->
					</@s.iterator>
                  </div><!--boxright-->
               </div><!--sp_boxinner-->
            <img src="http://pic.lvmama.com/img/zt/spring/box_bottom.jpg" width="1003" height="25" alt=" " />
          </div><!--box2-->
          
          <div class="spring_box3">
          <a name="sp_yl"></a>
             <div class="sp_box4_tit">
             		<div class="sp_boxtit_txt">精致的园林，沧桑的古镇，行走其间，感怀历史的厚重。泡一池温泉，让思绪回荡、升华。</div>
             </div>
               <div class="sp_box_inner">
                  <div class="sp_box_l">
                     <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-5.html" target="_blank"><img src="http://pic.lvmama.com/opi/guzhen236147.jpg" width="236" height="147" alt=" " /></a>
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>温泉古镇旅游攻略</em></h4>
                        <div class="sp_left_cont">
                          <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-5.html" target="_blank">身处江南，虽都有过到访古镇且被人群烦扰的经历，但春色一到，想起清风、小桥、流水，春心早已按耐不住。在如许的春色里，一个古镇，一家园林，漫步其中，寻花问柳，访茶问道，慢下来再慢下来，唯有这样才能真正体会这春的慵懒吧！春之懒，泡温泉，在水汽氤氲中，随思绪飞扬，任时间停滞，也是一场身心灵的洗礼了！下面选择的古镇和园林，相对清雅，人群较少，加上可以泡泡温泉，可清心可静心，宜舒心宜宽心。</a>
                          </div>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门景点推荐</em></h4>
                         <ul class="sp_left_cont  sp_hotlist">
                           
                             <li><a href="http://www.lvmama.com/dest/yangzhougeyuan" target="_blank">个园</a></li>
                             <li><a href="http://www.lvmama.com/dest/yangzhouheyuan" target="_blank">何园</a></li>
                             <li><a href="http://www.lvmama.com/dest/shouxihu" target="_blank">瘦西湖</a></li>
							 <li><a href="http://www.lvmama.com/dest/nanxun" target="_blank">南浔古镇</a></li>
							   <li><a href="http://www.lvmama.com/dest/qiantong" target="_blank">前童古镇</a></li>
                             <li><a href="http://www.lvmama.com/dest/baguacun" target="_blank">诸葛八卦村</a></li>
                             <li><a href="http://www.lvmama.com/dest/sijiyuewenquan" target="_blank">四季悦温泉</a></li>
                             <li><a href="http://www.lvmama.com/dest/tianyi" target="_blank">天颐温泉</a></li>
                             
                         </ul>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门酒店推荐</em></h4>
                        <ul class="sp_left_cont  sp_hotlist1">
                             <li><a href="http://www.lvmama.com/dest/wzmengjia" target="_blank">乌镇梦佳商务宾馆</a></li>
                             <li><a href="http://www.lvmama.com/dest/wzxiangshuiwanbinguan" target="_blank">乌镇巷水湾宾馆</a></li>
                             <li><a href="http://www.lvmama.com/dest/wzqinshuiyuan" target="_blank">乌镇沁水园宾馆</a></li>
                             <li><a href="http://www.lvmama.com/dest/shouxihutianmuwenquandujiacun" target="_blank">扬州瘦西湖温泉</a></li>
							 
                             <li><a href="http://www.lvmama.com/dest/sztonglihudafan" target="_blank">苏州同里湖大饭店</a></li>
                             <li><a href="http://www.lvmama.com/dest/yangguangyizhanshangwujiudian" target="_blank">同里阳光驿站商务酒店</a></li>
							 <li><a href="http://www.lvmama.com/dest/xingchengyonglezhuti" target="_blank">扬州星程永乐主题宾馆</a></li>
                            
							  <li><a href="http://www.lvmama.com/dest/yzhuameidakaisha" target="_blank">扬州凯莎华美达广场酒店</a></li>
							   <li><a href="http://www.lvmama.com/dest/szwangshangengdaodujia" target="_blank">苏州旺山耕岛别墅度假酒店</a></li>
                         </ul>
                     </div> <!-- sp_left_box1-->
                  </div>
                  <!--boxleft-->
                  <div class="sp_box_r">
					<@s.iterator value="map.get('${station}_mjxhgz')" status="st">
                      <em class="sp_tit1">${title?if_exists}</em>
                      <ul class="sp_prolist">
						<@s.iterator value="map.get('${station}_mjxhgz_${st.index + 1}')">
                          <li>
                             <span class="sp_prolist_l1">
                               <a href="${url?if_exists}" class="sp_prolist_tit" target="_blank"><b>【${bakWord1?if_exists}】</b>${title?if_exists} <em class="${bakWord2?if_exists}">&nbsp;</em></a>
                               <a href="${url?if_exists}" class="sp_prolist_info"  target="_blank">${remark?if_exists}</a>
                             </span>
                             <span class="sp_prolist_l2">￥<em>${marketPrice?if_exists?replace(".0","")}</em></span>
                             <span class="sp_prolist_l3">￥<em>${memberPrice?if_exists?replace(".0","")}</em>起</span>
                             <span class="sp_prolist_l4"><a href="${url?if_exists}" class="sp_btn2" target="_blank"></a></span>
                          </li>
                        </@s.iterator>
                      </ul><!--box_ultop-->
                      <!--div class="sp_more"><a href="#" class="link_brown" target="_blank">更多&gt;&gt;</a></div-->
					</@s.iterator>
				  </div><!--boxright-->
               </div><!--sp_boxinner-->
            <img src="http://pic.lvmama.com/img/zt/spring/box_bottom.jpg" width="1003" height="25" alt=" " />
          </div><!--box3-->
          
          
          <div class="spring_box4">
          <a name="sp_ly"></a>
           <div class="sp_box5_tit">
             		<div class="sp_boxtit_txt">疯狂的过山车、浪漫的摩天轮、温馨的旋转木马。游乐园总能带来最happy的经历，游完之后泡温泉，洗去一天的疲惫。</div>
             </div>
               <div class="sp_box_inner">
                  <div class="sp_box_l">
                     <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-7.html" target="_blank"><img src="http://pic.lvmama.com/opi/leyuan236147.jpg" width="236" height="147" alt=" " /></a>
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>温泉乐园旅游攻略</em></h4>
                        <div class="sp_left_cont">
                          <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-7.html" target="_blank">各种主题乐园总有吸引我们的能力，嬉戏谷的动漫游戏风格，恐龙园的科普娱乐性质，和苏州乐园的欢乐世界，对于那些玩过山车、海盗船成瘾的年轻人来说，这些地方总有一种魔力，吸引我们一次又一次的前往。春光明媚之时，主题乐园里面的喧闹和欢笑声多了起来，在结束一天的大汗淋漓之时，不妨到就近泡泡温泉，真正能够乘兴而来，舒适而归。</a>
                          </div>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门景点推荐</em></h4>
                         <ul class="sp_left_cont  sp_hotlist">
                             <li><a href="http://www.lvmama.com/dest/dapengshan" target="_blank">达蓬山</a></li>
                             <li><a href="http://www.lvmama.com/dest/jiangsu_jiangsuchangzhou" target="_blank">恐龙园</a></li>
                             <li><a href="http://www.lvmama.com/dest/nongfuleyuan" target="_blank">杭州农夫乐园</a></li>
                             <li><a href="http://www.lvmama.com/dest/changzhouxixigu" target="_blank">常州嬉戏谷</a></li>
                             <li><a href="http://www.lvmama.com/dest/konglongguwenquan" target="_blank">恐龙谷温泉</a></li>
							  <li><a href="http://www.lvmama.com/dest/changshouhuadu" target="_blank">常州花都温泉</a></li>
                             
                         </ul>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门酒店推荐</em></h4>
                        <ul class="sp_left_cont  sp_hotlist1">
                             <li><a href="http://www.lvmama.com/dest/hangzhoudiyishijiejiudian" target="_blank">杭州第一世界酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/szsijiyuewenquan" target="_blank">苏州四季悦温泉酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/konglonggudujia" target="_blank">常州恐龙谷温泉度假村 </a></li>
                             <li><a href="http://www.lvmama.com/dest/czxiangshuwanhuayuan" target="_blank">常州环球恐龙城香树湾花园酒店 </a></li>
							 
                         </ul>
                     </div> <!-- sp_left_box1-->
                  </div>
                  <!--boxleft-->
                  <div class="sp_box_r">
					<@s.iterator value="map.get('${station}_mjmsly')" status="st">
                      <em class="sp_tit1">${title?if_exists}</em>
                      <ul class="sp_prolist">
						<@s.iterator value="map.get('${station}_mjmsly_${st.index + 1}')">
                          <li>
                             <span class="sp_prolist_l1">
                               <a href="${url?if_exists}" class="sp_prolist_tit" target="_blank"><b>【${bakWord1?if_exists}】</b>${title?if_exists} <em class="${bakWord2?if_exists}">&nbsp;</em></a>
                               <a href="${url?if_exists}" class="sp_prolist_info"  target="_blank">${remark?if_exists}</a>
                             </span>
                             <span class="sp_prolist_l2">￥<em>${marketPrice?if_exists?replace(".0","")}</em></span>
                             <span class="sp_prolist_l3">￥<em>${memberPrice?if_exists?replace(".0","")}</em>起</span>
                             <span class="sp_prolist_l4"><a href="${url?if_exists}" class="sp_btn2" target="_blank"></a></span>
                          </li>
                        </@s.iterator>
                      </ul><!--box_ultop-->
                      <!--div class="sp_more"><a href="#" class="link_brown" target="_blank">更多&gt;&gt;</a></div-->
					</@s.iterator>
				  </div><!--boxright-->
               </div><!--sp_boxinner-->
            <img src="http://pic.lvmama.com/img/zt/spring/box_bottom.jpg" width="1003" height="25" alt=" " />
          </div><!--box4-->
          
          <div class="spring_box5">
         <a name="sp_sy"></a>
           <div class="sp_box6_tit">
             		<div class="sp_boxtit_txt">黄灿灿的油菜花、金色的灵山大佛、七彩的梯田，春天的表情用相机来记录，且看温泉如何焕发全新的色彩。</div>
            </div>
               <div class="sp_box_inner">
                  <div class="sp_box_l">
                     <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-9.html" target="_blank"><img src="http://pic.lvmama.com/opi/sheying236147.jpg" width="236" height="147" alt="温泉摄影旅游攻略 " /></a>
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>温泉摄影旅游攻略</em></h4>
                        <div class="sp_left_cont">
                          <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-9.html" target="_blank">春日是油菜花开的时节，炫目的油菜花黄，在青山绿水间，在晨雾萦绕中，在层层梯田上，宛如玉带，譬如油画，给大地涂抹上最美的彩妆，相信此时很多摄友都蠢蠢欲动了吧！武义的七彩梯田，婺源的油菜花黄……都是当季精选，其实来到这些地方，不仅有如许的美色，更有许多你意想不到的精心享受，在一天劳累抓拍之后，可到附近的温泉解除疲乏，也算是额外的奖励了！</a>
                          </div>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门景点推荐</em></h4>
                         <ul class="sp_left_cont  sp_hotlist">
                             <li><a href="http://www.lvmama.com/dest/qingshuiwan" target="_blank">清水湾沁温泉</a></li>
                             <li><a href="http://www.lvmama.com/dest/ninghaisenlinwenquan" target="_blank">宁海森林温泉</a></li>
                             <li><a href="http://www.lvmama.com/dest/lingshandafo" target="_blank">灵山大佛</a></li>
                             <li><a href="http://www.lvmama.com/dest/huangshanzuiwenquan" target="_blank">醉温泉</a></li>
                             <li><a href="http://www.lvmama.com/dest/jiangxiwuyuan" target="_blank">婺源油菜花</a></li>
							 <li><a href="http://www.lvmama.com/dest/lixingwenquan" target="_blank">元一丽星温泉</a></li>
                             <li><a href="http://www.lvmama.com/dest/yinghuawenquan" target="_blank">无锡樱花温泉</a></li>
                             
                         </ul>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门酒店推荐</em></h4>
                        <ul class="sp_left_cont  sp_hotlist1">
                             <li><a href="http://www.lvmama.com/dest/quzhouguojidajiudian" target="_blank">衢州国际大酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/qzminghaodajiudian" target="_blank">衢州铭豪大酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/mumianhuajiudian" target="_blank">无锡木棉花酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/wuxihuameidaguangchang" target="_blank">无锡华美达广场酒店</a></li>
							  <li><a href="http://www.lvmama.com/dest/wxbulagetiyan" target="_blank">无锡布拉格体验酒店</a></li>
                             
                         </ul>
                     </div> <!-- sp_left_box1-->
                  </div>
                  <!--boxleft-->
                  <div class="sp_box_r">
					<@s.iterator value="map.get('${station}_mjassy')" status="st">
                      <em class="sp_tit1">${title?if_exists}</em>
                      <ul class="sp_prolist">
						<@s.iterator value="map.get('${station}_mjassy_${st.index + 1}')">
                          <li>
                             <span class="sp_prolist_l1">
                               <a href="${url?if_exists}" class="sp_prolist_tit" target="_blank"><b>【${bakWord1?if_exists}】</b>${title?if_exists} <em class="${bakWord2?if_exists}">&nbsp;</em></a>
                               <a href="${url?if_exists}" class="sp_prolist_info"  target="_blank">${remark?if_exists}</a>
                             </span>
                             <span class="sp_prolist_l2">￥<em>${marketPrice?if_exists?replace(".0","")}</em></span>
                             <span class="sp_prolist_l3">￥<em>${memberPrice?if_exists?replace(".0","")}</em>起</span>
                             <span class="sp_prolist_l4"><a href="${url?if_exists}" class="sp_btn2" target="_blank"></a></span>
                          </li>
                        </@s.iterator>
                      </ul><!--box_ultop-->
                      <!--div class="sp_more"><a href="#" class="link_brown" target="_blank">更多&gt;&gt;</a></div-->
					</@s.iterator>
				  </div><!--boxright-->
               </div><!--sp_boxinner-->
            <img src="http://pic.lvmama.com/img/zt/spring/box_bottom.jpg" width="1003" height="25" alt=" " />
          </div><!--box5-->
          
           <div class="spring_box6">
              <a name="sp_hd"></a>
            <div class="sp_box7_tit">
             		<div class="sp_boxtit_txt">春天犯花痴？请别介意，谁叫这春天是那么灿烂，当温泉四周开满鲜花，人在花下池中荡漾，你一定会明白这春天的真谛。</div>
             </div>
               <div class="sp_box_inner">
                  <div class="sp_box_l">
                    <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-8.html" target="_blank"> <img src="http://pic.lvmama.com/opi/huaduo236147.jpg" width="236" height="147" alt="温泉花朵旅游攻略 " /></a>
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>温泉花朵旅游攻略</em></h4>
                        <div class="sp_left_cont">
                          <a href="http://www.lvmama.com/info/lvxingtop/2012-0328-63346-8.html" target="_blank">万紫千红，枝头喧闹，这是春的盛宴，这是花的海洋，我们又怎能错过呢？！婺源、同里的油菜花，无锡的樱花，尚湖的牡丹……还有新鲜的草莓采摘。这些赏花采摘线路，看起来新鲜诱惑，但是玩起来似乎不能过足春瘾，在此搭配上温泉的组合，一边赏其春光，一边采摘春光，一边沐浴春光，这也是孔子所谓的“莫春者，春服既成……浴乎沂，风乎舞雩，咏而归。”</a>
                          </div>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门景点推荐</em></h4>
                         <ul class="sp_left_cont  sp_hotlist">
                              <li><a href="http://www.lvmama.com/dest/taihuyuantouzhu" target="_blank">鼋头渚</a></li>
							 <li><a href="http://www.lvmama.com/dest/pujiangyuanwenquan" target="_blank">浦江源温泉 </a></li>
                             <li><a href="http://www.lvmama.com/dest/xikou" target="_blank">奉化溪口景区</a></li>
                             <li><a href="http://www.lvmama.com/dest/yuyaosimingshan" target="_blank">四明山森林公园</a></li>
                             <li><a href="http://www.lvmama.com/dest/mingguoshanzhuang" target="_blank">兰溪名果山庄</a></li>
                             <li><a href="http://www.lvmama.com/dest/xihu" target="_blank">杭州西湖</a></li>
							 <li><a href="http://www.lvmama.com/dest/chaoshan" target="_blank">超山风景区</a></li>
                            
                             <li><a href="http://www.lvmama.com/dest/changshushanghu" target="_blank">尚湖牡丹花会</a></li>
                             <li><a href="http://www.lvmama.com/dest/yinghuawenquan" target="_blank">无锡樱花温泉</a></li>
                             <li><a href="http://www.lvmama.com/dest/jinfenghuang" target="_blank">金凤凰温泉</a></li>
                         </ul>
                     </div> <!-- sp_left_box1-->
                     
                     <div class="sp_left_box1">
                        <h4 class="sp_left_tit"><em>热门酒店推荐</em></h4>
                        <ul class="sp_left_cont  sp_hotlist1">
                             <li><a href="http://www.lvmama.com/dest/fubangguojidajiudian" target="_blank">杭州富邦国际大酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/hzchengshihuayuan" target="_blank">杭州城市花园酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/yzhuijinxuanwu" target="_blank">扬州汇金玄武饭店 </a></li>
                             <li><a href="http://www.lvmama.com/dest/yangzhouhenchunyuandajiudian" target="_blank">扬州恒春缘大酒店</a></li>
							 <li><a href="http://www.lvmama.com/dest/szshushanyunquanshanzhuang" target="_blank">苏州树山云泉山庄</a></li>
                             <li><a href="http://www.lvmama.com/dest/ksdongfangyundingwenquandujia" target="_blank">东方云鼎温泉度假酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/shushanwenduandujiajiudian" target="_blank">苏州书香世家树山温泉度假酒店</a></li>
                             <li><a href="http://www.lvmama.com/dest/yzmotaiwenhedian" target="_blank">莫泰168（扬州瘦西湖汶河路店）</a></li>
                         </ul>
                     </div> <!-- sp_left_box1-->
                  </div>
                  <!--boxleft-->
                  <div class="sp_box_r">
					<@s.iterator value="map.get('${station}_mjyshd')" status="st">
                      <em class="sp_tit1">${title?if_exists}</em>
                      <ul class="sp_prolist">
						<@s.iterator value="map.get('${station}_mjyshd_${st.index + 1}')">
                          <li>
                             <span class="sp_prolist_l1">
                               <a href="${url?if_exists}" class="sp_prolist_tit" target="_blank"><b>【${bakWord1?if_exists}】</b>${title?if_exists} <em class="${bakWord2?if_exists}">&nbsp;</em></a>
                               <a href="${url?if_exists}" class="sp_prolist_info"  target="_blank">${remark?if_exists}</a>
                             </span>
                             <span class="sp_prolist_l2">￥<em>${marketPrice?if_exists?replace(".0","")}</em></span>
                             <span class="sp_prolist_l3">￥<em>${memberPrice?if_exists?replace(".0","")}</em>起</span>
                             <span class="sp_prolist_l4"><a href="${url?if_exists}" class="sp_btn2" target="_blank"></a></span>
                          </li>
                        </@s.iterator>
                      </ul><!--box_ultop-->
                      <!--div class="sp_more"><a href="#" class="link_brown" target="_blank">更多&gt;&gt;</a></div-->
					</@s.iterator>
				  </div><!--boxright-->
               </div><!--sp_boxinner-->
            <img src="http://pic.lvmama.com/img/zt/spring/box_bottom.jpg" width="1003" height="25" alt=" " />
          </div><!--box6-->
          
          
           <div class="spring_box7">
               <img src="http://pic.lvmama.com/img/zt/spring/box8_tit.jpg"   width="1004" height="75" alt=" " />
               <div class="sp_glbox_inner">
     				<h4 class="sp_left_tit"><em>春季泡汤小秘诀</em></h4>
                    <ul class="sp_glbox_top">
                       <li class="sp_glbox_top_l"><img width="236" height="147" alt=" " src="http://pic.lvmama.com/opi/wenquanA236147.jpg"/></li>
                       <li class="sp_glbox_top_r">
                          <div class="sp_glimg_l"></div>
                          <div class="sp_glimg_m">
                              <dl class="sp_glimg_dl_l">
                                 <dt> <a href="http://www.lvmama.com/info/chinalife/2012-0326-63285.html" target="_blank">春季泡汤好处：</a> </dt>
                                 <dd> <a href="http://www.lvmama.com/info/chinalife/2012-0326-63285.html" target="_blank">春天是万物生发季节，这时人体的每一个细胞都苏醒过来，每一条血管也都舒展开来。<em>此时泡温泉，可舒筋活血，升阳固脱，美容养颜。</em></a></dd>
                              </dl>
                              <dl class="sp_glimg_dl_m">
                                 <dt><a href="http://www.lvmama.com/info/chinalife/2011-1114-35660-3.html" target="_blank">泡汤注意事项：</a> </dt>
                                 <dd> <a href="http://www.lvmama.com/info/chinalife/2011-1114-35660-3.html" target="_blank">温度较高时，不可长久浸泡，以免出现胸闷、头晕等症状；泡温泉时，最好敷上面膜，或以冷毛巾敷面，以冥想的心情，配合缓慢的深呼吸，真正舒缓身心；泡完温泉后，一般不必再用清水冲洗，但如果是浸泡强酸或硫化氢温泉，则最好冲洗，以免刺激皮肤。<!--<em>适用于有慢性盆腔炎、前列腺炎、便秘、痔疮、脱肛、妇女月经不调、痛经、下肢风湿关节痛等的人。</em>--></a></dd>
                              </dl>
                              <dl class="sp_glimg_dl_r">
                                 <dt><a href="http://www.lvmama.com/info/chinalife/2011-1114-35660-4.html" target="_blank">如何泡汤才治病：</a></dt>
                                 <dd><a href="http://www.lvmama.com/info/chinalife/2011-1114-35660-4.html" target="_blank"><em>慢性支气管炎、便秘：</em>选用氡泉或单纯温泉全身浸浴；<em>高血压、痛风、神经衰弱、失眠：</em>可用氡泉全身浸浴；泡温泉后1小时内不进食可促进<em>瘦身。</em></a></dd>
                              </dl>
                          </div>
                          <div class="sp_glimg_r"></div>
                       </li><!--sp_gltop_r-->
                    </ul><!--sp_gltop-->
                    <h4 class="sp_left_tit"><em>要想疗效好 正确方法少不了</em></h4>
                    <ul class="sp_gl_detail">
                        <li><b>洗净身体：</b>入浴前都应先将身体冲洗干净，并注意避免水温太烫。在清洗时，要记得远离浴池并将身体放低，不要让污水溅到池子里。</li>
                        <li><b>冷热交替法：</b>犹如三温暖，能促进循环、增强体力。若要尝试三温暖式温泉，泡完温泉可先至蒸气，让水蒸气稀释身上的硫磺后，再进入桑拿房；
                          而进入桑拿房时，不妨用干净毛巾覆盖双眼，避免汗液将身上的矿物质带入眼睛。医师提醒，泡完温泉后，千万别直接进桑拿房，否则温泉中的硫磺
                          及矿物质可能因附着于眼球表面，而导致角膜破皮、发炎。 泡完温泉就进入桑拿房，因桑拿房温度高，泪液会被快速蒸发，眼睛将失去排除脏物的功
                          能，容易繁殖细菌。</li>
                        <li><b>瀑布浴：</b> 藉水压冲击，可活络筋骨，达到治疗酸痛的效果。但请避免与泉水成直角直接冲击，以斜角舒缓水压并以毛巾敷于患部为宜。</li>
                        <li><b>半身浴法：</b>脏以下部位浸泡于温泉中，可调整体温，平衡内分泌的平衡。</li>
                        <li><b>瘦身小秘方：</b>泡汤时以浸泡15分钟、起身5分钟、再浸泡15分钟为原则，反覆2~3次，，且浴后1小时内不进食，持之以恒，效果良好。</li>
                    </ul>
               </div><!--sp_boxinner-->
            <img src="http://pic.lvmama.com/img/zt/spring/box_bottom.jpg" width="1003" height="25" alt=" " />
          </div><!--box7-->
          
          <div class="spring_box8">
               <img src="http://pic.lvmama.com/img/zt/spring/box9_tit.jpg"   width="1004"  height="56" alt=" " />
               <ul class="sp_morebox">
                  <li><a href="http://www.lvmama.com/zt/lvyou/yunnan" target="_blank"><img src="http://pic.lvmama.com/opi/yunnan284_135.jpg" width="284" height="135" /></a></li>
                   <li><a href="http://www.lvmama.com/zt/lvyou/jiuhuashan/" target="_blank"><img src="http://pic.lvmama.com/opi/jiuhuashan32.jpg" width="284" height="135" /></a></li>
                   <li><a href="http://www.lvmama.com/zt/lvyou/yzly/" target="_blank"><img src="http://pic.lvmama.com/opi/yangA.jpg" width="284" height="135" /></a></li>
               </ul><!--sp_boxinner-->
            <img src="http://pic.lvmama.com/img/zt/spring/box_bottom.jpg" width="1003" height="25" alt=" " />
          </div><!--box8-->
        </div><!--body-->
</div><!--wrap-->
<!--pop-->
<div class="sp_pop">
   <ul class="sp_poplist">
      <li><a href="#sp_tg">携手团购</a></li>
      <li><a href="#sp_ss">恋上山水</a></li>
      <li><a href="#sp_yl">邂逅古镇园林</a></li>
      <li><a href="#sp_ly">迷上乐园</a></li>
      <li><a href="#sp_sy">爱上摄影</a></li>
      <li><a href="#sp_hd">遇上花朵</a></li>
      <li><a href="#sp_top">返回顶部</a></li>
   </ul>
</div>
<!--pop-->
<!--footer>>-->
<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<!--footer<<-->
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/spring/spring.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
