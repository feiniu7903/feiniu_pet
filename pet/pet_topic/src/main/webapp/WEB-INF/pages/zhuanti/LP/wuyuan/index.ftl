<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" />
<title>婺源特色旅游推荐_2012婺源油菜花_婺源油菜花最佳时间-驴妈妈旅游网</title>
<meta name="keywords" content="婺源特色,油菜花,婺源旅游" />
<meta name="description" content="婺源,这个中国最美的乡村，即将在春天来临之时，盛开出最灿烂的景色，璀璨金黄油菜花田,蔓遍婺源,驴妈妈特荐大家不容错过的赏油菜花最佳时间." />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/wuyuan/wuyuan.css" rel="stylesheet" type="text/css" />
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
	<div class="banner"><img src="http://pic.lvmama.com/img/zt/wuyuan/banner1.jpg" width="1002" height="129" alt="婺源油菜花" /><img src="http://pic.lvmama.com/img/zt/wuyuan/banner2.jpg" width="1002" height="129" alt="" />
        <img src="http://pic.lvmama.com/img/zt/wuyuan/banner3.jpg" width="1002" height="129" alt="" />    </div>
	<!--banner end-->
    <div class="page">
			<div class="group">
            	<div class="group_lt">
            	<@s.iterator value="map.get('${station}_wytg')" status="st">
            	    <a  class="gp_link" href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="402" height="252"  alt="婺源旅游"/></a>
                    <p class="gp_bg">
                    <del>原价：${marketPrice?if_exists?replace(".0","")}元</del>
                    <b>团购价：<em>${memberPrice?if_exists?replace(".0","")}</em>元</b>
                    </p>
                    <p><strong>【${bakWord5?if_exists}】</strong>${title?if_exists}<a href="${url?if_exists}">[查看详情]</a></p>
            	</@s.iterator>
                </div><!--group_left end-->
                <div class="group_rt">
                	<h3>『最美乡村  水墨婺源』</h3>
                    <p>春未到，困在城市里的我们却早已盼望着春天的气息，是花草，是乡野，是小桥流水世外桃源，还有那片片金黄的油菜花田。婺源——这个中国最美的乡村，即将在春天来临之时，盛开出最灿烂的景色，白墙黑瓦袅袅炊烟，璀璨金黄油菜花田。本期驴妈妈赏油菜花专题，将带你走进这个江西东北部的小村落，拥抱最美的春天……</p>
                    <dl>
                    	<dt>走进最美乡村 去婺源的五个理由</dt>
                        <dd>1、现代版的水墨桃源</dd>
                        <dd>2、赏十万梯田油菜花海</dd>
                        <dd>3、古镇、小桥、流水、人家和青石板巷</dd>
                        <dd>4、白墙黑瓦的徽式建筑</dd>
                        <dd>5、历史人文和民俗风情</dd>
                    </dl>
                    <p class="price"><b>婺源通票<font>¥<em>170</em></font></b><a href="http://www.lvmama.com/product/374
">立即预订</a></p>
                </div>
            </div><!--group end-->
            <div class="product">
            	<h3 class="speaci"><span>『特色自由行』</span></h3>
                <div class="pro_list">
                		<div class="pro_lt">
                		    <@s.iterator value="map.get('${station}_wyzyx')" status="st">
                        	<dl>
                            	<dt><span><i></i><em>${title?if_exists}</em><i  class="icon_rt"></i></span><a href="${url?if_exists}" class="more">更多&gt;&gt;</a></dt>
                            	<@s.iterator value="map.get('${station}_wyzyxtt_${st.index+1}')" status="st1">
                                <dd class="pro_img">
                                	<a  class="pro_link" href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="190" height="108" alt="油菜花" /></a>
                                    <div class="pro_text">
                                    	<a href="${url?if_exists}"><b>${title?if_exists}</b>
                                    	<@s.if test="null != bakWord1">
                                	    <span class="share ${bakWord1?if_exists}"></span>
                                	    </@s.if>
                                        </a>
										<p class="padd_val">◎ ${remark?if_exists}</p>
										<p>◎ ${bakWord3?if_exists}</p>
                                        <p class="padd_val"><font>¥<em>${memberPrice?if_exists?replace(".0","")}</em></font><a href="${url?if_exists}"></a></p>
                                    </div>
                                </dd>
                                </@s.iterator>
                                <@s.iterator value="map.get('${station}_wyzyx_${st.index+1}')" status="st2">
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
                        			<p class="line">【婺源交通指南  各地到婺源线路】</p>
                                    <p class="posi"><img src="http://pic.lvmama.com/img/zt/wuyuan/small01.jpg" width="263" height="309" alt="婺源地图" /><a class="big_img">点击查看大图</a></p>
                              <a href="http://www.lvmama.com/info/lvxingtop/2012-0223-45201-3.html" target="_blank"><u>婺源位于江西省东北部，隶属于江西上饶市。 附近的衢州、景德镇和上饶都有机场。</u></a>                            </div>
                          <div class="small_img">
                        			<p class="line">【黄山交通指南  各地到黄山线路】</p>
                                    <p class="posi"><img src="http://pic.lvmama.com/img/zt/wuyuan/small02.jpg" width="259" height="346" alt="婺源交通" /><a class="big_img">点击查看大图</a></p>
                              <a href="http://www.lvmama.com/info/chinagonglue/2012-0306-45439-6.html" target="_blank"><u>黄山位于安徽省南部，与景德镇、临安等相邻，黄山机场、火车站与汽车站均位于市中心屯溪区，班次众多，交通便利。</u></a>                            </div>
                        </div><!--pro_rt end-->
                </div><!--pro_list end-->
      </div>
            <!--product end-->
      <div class="product">
                <h3 class="posi_h3"><span>『特色跟团游』</span></h3>
                  <div class="pro_list pro_gp">
                  		<div class="pro_lt">
                        	<p class="line">【宝典：2012婺源看油菜花全攻略】</p>
                            <a href="http://www.lvmama.com/info/lvxingtop/2012-0223-45201.html" target="_blank" class="imgbg2"><img src="http://pic.lvmama.com/img/zt/wuyuan/gengtuanZUO.jpg" width="254" height="144" alt="2012婺源油菜花节" /></a>
                            <p class="more_text">又到了一年一度赏油菜花的时节，从春寒料峭的城市里逃离，来到这中国最美的乡村，那山、那水、那花、那人家，在山野乡村间，在小桥流水间，在花姿百态间，共赴一场一年一度的花海盛宴… <a href="http://www.lvmama.com/info/lvxingtop/2012-0223-45201.html " target="_blank">[详情]</a></p>
                        </div>
                        <div class="pro_rt">
                        	<strong class="tyou">特色跟团游</strong>
                        	<ul class="tab" id="tab">
                        	    <@s.iterator value="map.get('${station}_wygty')" status="st">
                        	    <@s.if test="#st.index == 0">
                        	    <li class="cur">${title?if_exists}</li>
                        	    </@s.if>
                        	    <@s.else>
                        	    <li>${title?if_exists}</li>
                        	    </@s.else>
                            	</@s.iterator>
                            </ul>
                            <div id="tab_con">
                                <@s.iterator value="map.get('${station}_wygty')" status="st">
                                <@s.if test="#st.index == 0">
							    <ul class="gty dis">
						        </@s.if>
						        <@s.else>
							    <ul class="gty">
						        </@s.else>
                                
                                <@s.iterator value="map.get('${station}_wygty_${st.index+1}')" status="st1">
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
                <h3 class="posi_h3"><span>『婺源黄山门票』</span></h3>
                  <div class="pro_list pro_gp">
                  		<div class="pro_lt">
                        	<p class="line">【婺源不止油菜花 更多好景点推荐】</p>
                            <a href="http://www.lvmama.com/info/lvxingtop/2012-0223-45201-5.html" target="_blank" class="imgbg2"><img src="http://pic.lvmama.com/img/zt/wuyuan/menpiaoZUOshangtu.jpg" width="254" height="144" alt="婺源景点" /></a>
                            <p class="more_text">婺源美，不止美在油菜花。一条小河九曲十弯，村中明清古宅沿河而建，粉墙黛瓦，远山，近水，活色生香成一幅梦中才有的美丽画卷...<a href="http://www.lvmama.com/info/lvxingtop/2012-0223-45201-5.html" target="_blank">[详情]</a></p>
                            <p class="line line_border">【黄山攻略-黄山清静之美唯你独享】</p>
                            <a href="http://www.lvmama.com/dest/anhui_huangshan/guide" target="_blank" class="imgbg2"><img src="http://pic.lvmama.com/img/zt/wuyuan/mengpiaoZUOxiatu.jpg" width="254" height="144" alt="黄山" /></a>
                            <p class="more_text">黄山依山傍水、古朴典雅，北有黄山、九华山、太平湖，西有齐云山、蓬莱仙洞、鱼龙洞，新安江横穿市区，醉温泉和飘雪温泉泉意盛浓…<a href=" http://www.lvmama.com/dest/anhui_huangshan/guide" target="_blank">[详情]</a></p>
                        </div>
                        <div class="pro_rt pro_hsmp">
                            <ul class="hsmp">
                            
                                <@s.iterator value="map.get('${station}_wyhsmp_1')" status="st">
                                <@s.if test="#st.index%3 == 0">
                            	<li>
                            	</@s.if>
                                	<div class="hsmp_box">
                                    	<a href="${url?if_exists}"><img src="${imgUrl?if_exists}" height="112" width="201" alt="" /></a>
                                        <p>${title?if_exists}</p>
                                        <p class="order"><span><font>¥<em>${memberPrice?if_exists?replace(".0","")}</em></font>起</span><a href="${url?if_exists}"></a></p>
                                    </div>
                                <@s.if test="(#st.index+1)%3 == 0">
                            	</li>
                            	</@s.if>
                              </@s.iterator>
                            </ul>
                            <@s.iterator value="map.get('${station}_wyhsmp')" status="st">
                              <a href="${url?if_exists}" class="more">更多&gt;&gt;</a>
                            </@s.iterator>
                        </div><!--pro_hsmp end-->
                  </div><!--pro_list end-->
      </div><!--product end-->       
       <div class="product">
                <h3 class="posi_h3"><span>『特色精品酒店』</span></h3>
                  <div class="pro_list pro_hotel">
                  		<div class="pro_lt">
                  		        <@s.iterator value="map.get('${station}_wyjd')" status="st">
                        		<dl>
                                	<dt><span><i></i><em>${title?if_exists}</em><i  class="icon_rt"></i></span><a class="more" href="${url?if_exists}">更多&gt;&gt;</a></dt>
                                    <@s.iterator value="map.get('${station}_wyjd_${st.index+1}')" status="st1">
                                    <@s.if test="#st1.index%2 == 0">
                            	    <dd>
                            	    </@s.if>
                                    	<div class="hotel_box">
                                        	<a class="hotel_img" href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="120" height="61"  alt="" /></a>
                                            <p><a href="${url?if_exists}">${title?if_exists}</a>
                                            <span class="${bakWord2?if_exists}"></span>
                                            </p>
                                            <p>${remark?if_exists}</p>
                                            <p class="hotel_price"><font>¥<em>${bakWord1?if_exists?replace(".0","")}</em></font>起</p>
                                        </div>
                                    <@s.if test="(#st1.index+1)%2 == 0">
                            	    </dd>
                            	    </@s.if>
                                    </@s.iterator>
                                </dl>
                                </@s.iterator>
                        </div>
                        <div class="pro_rt">
                        		<p class="line">【睡在最美乡村—婺源住宿全攻略】</p>
                                <a href="http://www.lvmama.com/info/lvxingtop/2012-0223-45201-9.html" target="_blank" class="imgbg2"><img height="144" width="254" src="http://pic.lvmama.com/img/zt/wuyuan/jiudianYOUshangru.jpg" alt="婺源住宿" /></a>
                                <dl class="kezhan">
                                	<dt><b>婺源的住宿分为两类:</b></dt>
                                    <dd>一是在<b>婺源县城住宿</b>，条件好一些，比较适合包车和自驾车；</dd>
                                    <dd>二是在<b>村里留宿</b>，条件相对差些，但可享受农家的纯朴乡土气息，便于更好地品味婺源的魅力。<a href="http://www.lvmama.com/info/lvxingtop/2012-0223-45201-9.html" target="_blank">［详情］</a></dd>
                                </dl>
                                <p class="line line_border">【天下第一奇山—黄山住宿全攻略】</p>
                                <a href="http://www.lvmama.com/zt/110223_hs/" target="_blank" class="imgbg2"><img height="144" width="254"  src="http://pic.lvmama.com/img/zt/wuyuan/jiudianYOUxiatu.jpg" alt="" /></a>
                                <dl class="kezhan kezhan2">
                                	<dt><b>来黄山旅游一般在下面三个地方入住:</b></dt>
                                	<dd>&middot;<b>屯溪</b>(黄山市政府所在地，高中低档酒店俱全；)</dd>
                                    <dd>&middot;<b>汤口</b>(有当地人开的经济型酒店，性价比较高；)</dd>
                                    <dd>·<b>黄山山上</b>(游黄山比较方便，但价格会高很多。)<a href="http://www.lvmama.com/zt/110223_hs/" target="_blank">［详情］</a></dd>
                                </dl>
                        </div>
                  		
                  </div><!--pro_hotel end-->
      </div><!--product end-->       
      <div class="product gonglue"> 
      		   <h3 class="posi_h3"><span>『旅游攻略』</span></h3>
               <div class="pro_list">
			  		<a href="http://www.lvmama.com/dest/jiangxi_wuyuan/guide" target="_blank" class="imgbg3"><img src="http://pic.lvmama.com/img/zt/wuyuan/wuyuan_230x269.jpg" width="230" height="269" alt="" /></a>
                    <dl>
                    	<dt>【婺源看油菜花攻略】</dt>
                        <dd class="sj">婺源被誉为“中国最美的乡村”，而油菜花盛开的春季则是婺源一年中最美的时节。根据资深驴友预测，一般油菜花会在三月初开，大规模开放时间在3月25日左右，花期结束时间为4月30日后。春暖花开之时，徒步穿行在无边花海中该是最好的春日旅行吧…</dd>
                        <dd><a href="http://www.lvmama.com/dest/jiangxi_wuyuan/guide" target="_blank">[查看详情］</a></dd>
                        <dd><a href="http://www.lvmama.com/guide/downs.php?f=wuyuan.pdf" target="_blank" class="load">[官方攻略下载］</a></dd>
                    </dl>
                    <a href="http://www.lvmama.com/dest/anhui_huangshan/guide" target="_blank" class="imgbg3"><img src="http://pic.lvmama.com/img/zt/wuyuan/huangshan_230x269.jpg" width="230" height="269" alt="" /></a>
                    <dl>
                    	<dt>【黄山旅游攻略】</dt>
                        <dd class="sj">古时称"黟山"(黟按字面就是黑色很多的意思),上古时代的黄帝在统一中原的时候,就想长生不老,于是有大臣就推荐黟山,说这里很有灵气,于是黄帝服用了在经过九九八十一天练修成的仙丹后,羽化成仙,从此黟山改名为黄山,意思就是黄帝的山…</dd>
                        <dd><a href="http://www.lvmama.com/dest/anhui_huangshan/guide" target="_blank">[查看详情］</a></dd>
                        <dd><a href="http://www.lvmama.com/guide/downs.php?f=huangshan.pdf" target="_blank" class="load">[官方攻略下载］</a></dd>
                    </dl>
		</div><!--pro_list end-->
                <div class="pro_list pro_enjoy">
                		<dl>
                    	<dt>【关注微博，免费住黄山+泡醉温泉】</dt>
                        <dd class="sj">游婺源观徽州美景览黄山【免费住黄山+泡醉温泉】获奖条件：1）关注@ 驴妈妈旅游网 2）转发此条微博并@一位好友；就有机会得到黄山【醉温泉景区门票2张】和黄山【天都国际饭店】免费标准房型住宿1晚。3月31日抽出3名幸运粉丝。
</dd>
                        <dd><a href="http://e.weibo.com/1534939700/yaJHvbx0G
" target="_blank">点击分享微博，赢大奖</a></dd>
                  	   </dl>
                       <dl class="enjoy_pic">
                       		<dt>【婺源徽州美景欣赏】</dt>
                            <dd><img src="http://pic.lvmama.com/img/zt/wuyuan/wuyuangtu.jpg" alt="婺源美景" width="675" height="320" border="0" usemap="#Map" />
<map name="Map" id="Map">
  <area shape="rect" coords="1,-1,294,170" href="http://www.lvmama.com/info/chinagonglue/2012-0222-45066-3.html

" target="_blank" /><area shape="rect" coords="430,2,673,98" href="http://www.lvmama.com/info/chinagonglue/2012-0306-45442.html
" target="_blank" /><area shape="rect" coords="230,175,524,312" href="http://www.lvmama.com/info/chinagonglue/2012-0306-45439.html
" /><area shape="rect" coords="298,2,425,166" href="http://www.lvmama.com/info/chinagonglue/2012-0222-45066.html
" target="_blank" />
<area shape="rect" coords="532,106,670,306" href="http://www.lvmama.com/info/chinagonglue/2012-0222-45066-2.html
" target="_blank" />
<area shape="rect" coords="4,176,225,315" href="http://www.lvmama.com/info/chinagonglue/2012-0222-45066.html
" target="_blank" />
<area shape="rect" coords="433,105,523,164" href="http://www.lvmama.com/info/chinagonglue/2012-0222-45066-4.html
" target="_blank" />
</map></dd>
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
	<a class="close"><img src="http://pic.lvmama.com/img/zt/wuyuan/closed.gif" width="47" height="19" alt="关闭" /></a>
    <img src="http://pic.lvmama.com/img/zt/wuyuan/bigpic2.jpg" width="557" height="649" alt="婺源交通地图" />
    <img src="http://pic.lvmama.com/img/zt/wuyuan/bigpic1.gif" width="550" height="711" alt="黄山交通地图" />
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
				$("#big_pic >img").hide().eq(index).show();
			});
		});
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
