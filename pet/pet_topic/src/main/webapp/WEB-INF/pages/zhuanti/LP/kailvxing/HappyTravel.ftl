<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" />
<title>当季旅游景点推荐_热门旅游目的地_当季去哪旅游-驴妈妈旅游网</title>
<meta name="keywords" content="当季旅游景点推荐,近期热门旅游目的地" />
<meta name="description" content="开心驴行是驴妈妈推出的当季旅游景点推荐专题，轻松,纯玩,让旅游更开心的质量标准,盘点近期热门旅游目的地,带领您体验当季旅游的快乐." />
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt_guide/kaixin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div id="container">
	<div class="bg_left"></div>
    <div class="bg_right"></div>
    <div class="wrapper_con">
    	<h1>开心驴行</h1>
        <h2>独立产品，独家开发</h2>
        <ul class="banner">
        	<li><img src="http://pic.lvmama.com/img/zt/kaixin_lv/banner01.jpg" width="994" height="163" /></li>
            <li><img src="http://pic.lvmama.com/img/zt/kaixin_lv/banner02.jpg" width="994" height="162" /></li>
  		</ul>
        <div class="page_main">
        	<h3 class="posi">当季热门推荐</h3>
              <div class="main clearfix">
                  <div class="main_left fl">
                  	<h4 class="lines">独家产品</h4>
                	<div class="view_contant_list">
                    	 <ul class="view_list">
                    	 <@s.iterator value="map.get('${station}_djrmxbt_1')" status="st">
                         		<li>
                                	<div class="open_title"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a><span class="${bakWord4?if_exists}"></span>
                                    </div>
                                    <div class="view_open_list undis">
                                    	<dl>
                                        	
                                            <dt class="img"><img src="${imgUrl?if_exists}"  width="172" height="98"/></dt>
                                            <dd class="text">
                                            	<p><strong>推荐理由：</strong>${remark?if_exists}</p>		
                                                <p>市场价：<del>¥${marketPrice?if_exists?replace(".0","")}</del>驴妈妈价：<strong class="price"><i>¥</i>${memberPrice?if_exists?replace(".0","")}</strong>起<a target="_blank" href="${url?if_exists}"><img src="http://pic.lvmama.com/img/zt/kaixin_lv/order.jpg" height="36" width="104" /></a></p>
                                                </dd>                                            
                                        </dl>
                                    </div>
                                </li>
                          </@s.iterator> 
                         </ul>
                  	</div><!--view_contant_list end-->
                    <h4 class="lines mar_value">今日推荐</h4>
                    <div class="view_contant_list">
                    	 <ul class="view_list">
                    	 <@s.iterator value="map.get('${station}_djrmxbt_2')" status="st">
                         		<li>
                                	<div class="open_title"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a><span class="${bakWord4?if_exists}"></span>
                                    </div>
                                    <div class="view_open_list undis">
                                    	<dl>
                                            <dt class="img"><img src="${imgUrl?if_exists}"  width="172" height="98"/></dt>
                                            <dd class="text">
                                            	<p><strong>推荐理由：</strong>${remark?if_exists}</p>		
                                                <p>市场价：<del>¥${marketPrice?if_exists?replace(".0","")}</del>驴妈妈价：<strong class="price"><i>¥</i>${memberPrice?if_exists?replace(".0","")}</strong>起<a target="_blank" href="${url?if_exists}"><img src="http://pic.lvmama.com/img/zt/kaixin_lv/order.jpg" height="36" width="104" /></a></p>
                                                </dd>                                            
                                        </dl>
                                    </div>
                                </li>
                           </@s.iterator> 
                         </ul>
                  	</div><!--view_contant_list end-->
                    <!--周边游-->
                    <div class="around mar_around">
                    	<h4>开心驴行周边游</h4>
                    	<@s.iterator value="map.get('${station}_kxlxzbyxbt')" status="st">
                        <h5 <#if (st.index==0)>class="hotspr"<#else>class="hotspr marg_h5"</#if>><strong>${title?if_exists}</strong></h5>
                        <ul>
                        	<li class="noborder">
                            	<dl class="tit_dl">
                                	<dt class="line_dt fl">线路</dt>
                                    <dd class=" wid_dd fl">满意度</dd>
                                    <dd class="fl">回访</dd>
                                    <dd class="fl">团期</dd>
                                    <dd class="wid_dd02 fl">价格</dd>
                                </dl>
                            </li>
                           <@s.iterator value="map.get('${station}_kxlxzbyxbt_${st.index+1}')" status="st">
                            <li>
                        		<dl>
                                	<dt class="fl"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                                    <p>${remark?if_exists}</p>
                                    </dt>
                                    <dd class="wid_dd fl">${bakWord1?if_exists}</dd>
                                    <dd class="fl">${bakWord2?if_exists}</dd>
                                    <dd class="fl" >${bakWord3?if_exists}</dd>
                                    <dd class="wid_dd02 fl"><font><i>¥</i>${memberPrice?if_exists?replace(".0","")}</font>起
                                    </dd>
                                </dl>
                             </li>
                            </@s.iterator> 
                            </ul> 
                        </@s.iterator> 
                    </div>
                    <div class="around mar_bott">
                    	<h4>精品一日跟团游</h4>
                    	<@s.iterator value="map.get('${station}_jpyry')" status="st">
                        <h5 <#if (st.index==0)>class="hotspr"<#else>class="hotspr marg_h5"</#if>><strong>${title?if_exists}</strong></h5>
                        <ul>
                        	<li class="noborder">
                            	<dl class="tit_dl">
                                	<dt class="line_dt fl">线路</dt>
                                    <dd class=" wid_dd fl">满意度
                                    </dd>
                                    <dd class="fl">回访
                                    </dd>
                                    <dd class="fl">团期
                                    </dd>
                                    <dd class="wid_dd02 fl">价格
                                    </dd>
                                </dl>
                            </li>
                            <@s.iterator value="map.get('${station}_jpyry_${st.index+1}')" status="st">
                            <li>
                        		<dl>
                                	<dt class="fl"><a target="_blank" href="${url?if_exists}">${title?if_exists}</a>
                                    <p>${remark?if_exists}</p>
                                    </dt>
                                    <dd class="wid_dd fl">${bakWord1?if_exists}</dd>
                                    <dd class="fl">${bakWord2?if_exists}</dd>
                                    <dd class="fl" >${bakWord3?if_exists}</dd>
                                    <dd class="wid_dd02 fl"><font><i>¥</i>${memberPrice?if_exists?replace(".0","")}</font>起</dd>
                                </dl>
                             </li>
                             </@s.iterator> 
                        </ul>
                        </@s.iterator>                 
                    </div>                 
                  </div><!--main_left-->
                  <div class="main_right fr">
                  	<div class="right_top">
                    	<ul>
                        	<li><img src="http://pic.lvmama.com/img/zt/kaixin_lv/a1.jpg" height="47" width="229" /></li>
                            <li><img src="http://pic.lvmama.com/img/zt/kaixin_lv/a2.jpg" height="47" width="229" /></li>
                            <li><img src="http://pic.lvmama.com/img/zt/kaixin_lv/a3.jpg" height="47" width="229" /></li>
                            <li><img src="http://pic.lvmama.com/img/zt/kaixin_lv/a4.jpg" height="47" width="229" /></li>
                            <li><img src="http://pic.lvmama.com/img/zt/kaixin_lv/a5.jpg" height="47" width="229" /></li>
                        </ul>
                        <h4>什么是开心驴行？
                        </h4>
                        <p>开心驴行是驴妈妈旅游网推出的新型跟团游产品，提出“轻松、纯玩、品质、让旅游更开心”的质量标准和运营理念，每一条线路都由资深的旅游产品经理实地考察、精选酒店和景点、潜心设计而成，让游客轻松跟团出游，尽享品质、开心之旅。
                        </p>
                    </div>
                  <div class="right_center">
                  	<h5 class="strateg">
                    	用户回访
                    </h5>
                    <ul>
                    	<li>
                        	<p><span class="name">yiliping829（梅花山）</span></p>
                            <p>刚从南京的梅花山回来，满山遍野都是梅花。不同的颜色，像是莫奈的图画。 风吹过，送来梅花的暗香，仿佛是在仙境里。相信这两周是观花的最佳时间。要去的就要赶紧时间。</p>
                        </li>
                        <li>
                        	<p><span class="name">surprise4（鼓浪屿风景名胜区）</span></p>
                            <p>很好的地方，非常美！日光岩顶端可以看到鼓浪屿的全景，海景超赞！皓月园里的郑成功像那边也很赞，风景很美，而且去的人没有日光岩和菽庄花园多。菽庄花园也真心赞，别有洞天，旁边的海滩人很多，浪打上来很舒服！这3个地方是一定要去的。之后的话，逛逛鼓浪屿的龙头路，小吃超多，很美味！</p>
                        </li>
                        	<li>
                        	<p><span class="name">user76282496（天目湖南山竹海）</span></p>
                            <p>南山竹海缔造了天然氧吧， 3.5万亩竹园，号称万亩竹海。登上吴越第一峰的感觉很爽，景区分登山游览区，索道揽胜、吴越第一峰等景点，且拥有常州最高山峰-——石门尖,海拔508米。南山脚下，一座头高12.8米的老寿星雕塑慈眉善目，和蔼可亲，而他的身体就是整个的山坡。从这里徒步登山，到山顶撞祈福钟，寓意寿比南，坐缆车下山其乐无穷，无限的惬意。看了我的点评，你的心一定按捺不住了吧？！</p>
                        </li>
                        <li>
                        	<p><span class="name">想飞的糖糖（百里画廊漓江）</span></p>
                            <p>作为一个桂林人，对我的家乡是非常的自豪。不过说实话，我自己还是去年才很正式的游览了漓江。游漓江跟游船虽然省事，但是不如自己坐竹筏来得有意思哦，而且省钱很多哦。相约几个好友，租上一个竹筏，畅游于山水之间，淳朴的竹筏大叔会热情的给你介绍景点。从杨堤出发，游玩漓江，到达兴坪探寻小镇风情，轻松惬意。</p>
                        </li>
                        	<li>
                        	<p><span class="name">来自新浪微博的观众教育（石潭村 ）</span></p>
                            <p>从石潭村越往里走，风景越好，也越有野趣。村与村之间仅靠一条羊肠小道相连。当春季来临时，漫山遍野的油菜花、梨花，盛开地如火如荼，那才是真正的世外桃源啊！而且由于交通不便，游人罕至，是摄影、写生的好地方。 </p>
                        </li>
                        <li>
                        	<p><span class="name">gbsummer（婺源）</span></p>
                            <p>11年4月初去的，正是油菜花盛开的黄金期，和网上看到的还有想象中的景色差不多，大片大片的金黄色，包围着一处处白色的徽派建筑。最漂亮的景点应该是江岭，去了两次，第一次是太阳下山了，乘车上了山顶，看到了梯状的花田，很震撼可惜还是没有赶上夕阳普照的瞬间，显得有些昏暗。所以第二天一大早又去了，这回看到了另一种风情的江岭，朝阳烘托着大片花海很是温暖，美得很宁静，很浪漫。</p>
                        </li>
                        <li class="noborder_li">
                        	<p><span class="name">驴仔（光福古镇）</span></p>
                            <p>我第一次到光福的时候就感觉到它的美。光福的梅花是很有名的，每年梅花季节，赏梅游客络绎不绝。因此老觉得这个地方祥和得没话说。后来去的时候，又发现了除自然景观以外更吸引人的地方，就是文化。</p>
                        </li>
                    </ul>
                  </div>
                  <div class="right_center">
                  	<h5 class="strateg">周边攻略</h5>
                    <ul>
                    	<li class="li_bg"><a target="_blank" href="http://www.lvmama.com/guide/zt/chaoshansm/">十里香雪海  超山赏梅穷游攻略</a></li>
                        <li class="li_bg"><a target="_blank" href="http://www.lvmama.com/guide/2012/0228-161544.html">浦东高东草莓园：淡淡的巧克力味</a></li>
                        <li class="li_bg"><a target="_blank" href="http://www.lvmama.com/guide/2012/0228-161543.html">看三月花开成海--2012春季赏花攻略</a></li>
                        <li class="li_bg"><a target="_blank" href="http://www.lvmama.com/guide/2012/0228-161542.html">青浦采草莓，“田园趣古镇美”</a></li>
                        <li class="li_bg"><a target="_blank" href="http://www.lvmama.com/guide/zt/shzhoumo/">大都市，小情怀--上海周末好去处推荐</a></li>
                        <li class="li_bg"><a target="_blank" href="http://www.lvmama.com/guide/2012/0229-161565.html">趣味采草莓，静心赏太湖</a></li>
                        <li class="li_bg"><a target="_blank" href="http://www.lvmama.com/guide/2012/0228-161537.html">2012春季婺源旅游全方位必读攻略</a></li>
                        <li class="li_bg"><a target="_blank" href="http://www.lvmama.com/guide/2012/0301-161624.html">吃草莓？懒人也能玩转的草莓料理 </a></li>
                        <li class="li_bg"><a target="_blank" href="http://www.lvmama.com/guide/2012/0229-161571.html">采草莓注意事项——让你后顾无忧</a></li>
                        <li class="li_bg noborder_li"><a target="_blank" href="http://www.lvmama.com/guide/zt/zhoushanhd/">舟山海钓攻略-不为鱼而渔，不为娱而欲</a></li>                        
                    </ul>
                  </div>
                  <a  target="_blank" href="http://www.lvmama.com/zt/lvyou/cjtq/"  class="mar">
                  <img  src="http://pic.lvmama.com/opi/chunji244x105.jpg" height="105" width="244" />
                  </a>
				  <a  target="_blank" href="http://www.lvmama.com/zt/lvyou/wenquan"  class="mar">
                  <img  src="http://pic.lvmama.com/opi/wenquan244x105.jpg" height="105" width="244" />
                  </a>
				  <a  target="_blank" href="http://www.lvmama.com/zt/lvyou/yunnan"  class="mar">
                  <img  src="http://pic.lvmama.com/opi/yunnan284_135.jpg" height="105" width="244" />
                  </a>
				  <a  target="_blank" href="http://www.lvmama.com/zt/lvyou/zjy"  class="mar">
                  <img  src="http://pic.lvmama.com/opi/zijia244105.jpg" height="105" width="244" />
                  </a>
                  </div>
      		</div><!--main-->
        </div><!--page_main-->   
    </div><!--wrapper_con end -->
	<div class="link">
          <img src="http://pic.lvmama.com/img/zt/kaixin_lv/link_imgtop.jpg" height="15" width="994" />
          <div class="link_main">
              <h4>旅游推荐</h4>
              <p><a target="_blank" href="http://www.lvmama.com/dest/hengdian">横店旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhongguo_shanghai">上海旅游</a><a target="_blank" href="http://www.lvmama.com/dest/jiangsu_jiangsuchangzhou">常州旅游</a><a target="_blank" href="http://www.lvmama.com/dest/jiangsuchangzhou_liyang">天目湖旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhejiang_qiandaohu">千岛湖旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhejiang_hangzhou">杭州旅游</a><a target="_blank" href="http://www.lvmama.com/dest/putuoshan">普陀山旅游</a><a target="_blank" href="http://www.lvmama.com/dest/jiangsu_suzhou">苏州旅游</a><a target="_blank" href="http://www.lvmama.com/dest/anhui_huangshan">黄山旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhejiang_huzhoushianjixian">安吉旅游</a><a target="_blank" href="http://www.lvmama.com/dest/jiangsu_wuxi">无锡旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhejiang_ningbo">宁波旅游</a><a target="_blank" href=" http://www.lvmama.com/dest/zhejiang_linan">临安旅游</a>
                           <a target="_blank" href="http://www.lvmama.com/dest/zhejiang_tonglu"> 桐庐旅游</a><a target="_blank" href="http://www.lvmama.com/dest/jiangsu_nanjing">南京旅游</a><a target="_blank" href="http://www.lvmama.com/dest/jiangsu_yangzhou">扬州旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhejiang_shaoxing">绍兴旅游</a><a target="_blank" href="http://www.lvmama.com/dest/jiangxi_sanqingshan">三清山旅游</a><a target="_blank" href="http://www.lvmama.com/dest/wenzhou_yandangshan">雁荡山旅游</a><a target="_blank" href="http://www.lvmama.com/dest/hainan_sanya/dest2dest_tab_frm79">三亚旅游</a><a target="_blank" href="http://www.lvmama.com/dest/yunnan_lijiang/dest2dest_tab_frm79">丽江旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhongguo_beijing/dest2dest_tab_frm79">北京旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhongguo_guizhou/dest2dest_tab_frm79">贵州旅游</a><a target="_blank" href="http://www.lvmama.com/dest/fujian_xiamen/dest2dest_tab_frm79">厦门旅游</a><a target="_blank"  href="http://www.lvmama.com/dest/shandong_qingdao/dest2dest_tab_frm79">青岛旅游</a><a target="_blank" href="http://www.lvmama.com/dest/shannxi_xian/dest2dest_tab_frm79">西安旅游</a>            
             <a target="_blank" href="http://www.lvmama.com/dest/guangxi_guilin/dest2dest_tab_frm79">桂林旅游</a><a target="_blank" href="http://www.lvmama.com/dest/hunan_zhangjiajie/dest2dest_tab_frm79">张家界旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhongguo_xizang/dest2dest_tab_frm79">西藏旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhongguo_xinjiang/dest2dest_tab_frm79">新疆旅游</a><a target="_blank" href="http://www.lvmama.com/dest/zhongguo_shanxi/dest2dest_tab_frm79">山西旅游</a><a target="_blank" href="http://www.lvmama.com/dest/jiangxi_jiujiang/dest2dest_tab_frm79">庐山旅游</a>		 
		   </p>
          </div>
          <img src="http://pic.lvmama.com/img/zt/kaixin_lv/link_imgbottom.jpg" height="15" width="994" />
    </div><!--link end-->
</div><!--container end-->
<!--footer-->
<div id="footer"> 
     <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
</div>
<script type="text/javascript"> 
$(function(){
	var $view_list=$('.view_list li');
	$view_list.eq(0).find('.open_title').addClass('open_titlebg');
	$view_list.eq(0).find('.view_open_list').show();
	$view_list.mouseover(function(){
		var _index=$view_list.index(this);
		//var _index=$(this).index();
		for(var i=0;i<$view_list.length;i++)
		{
		if(i==_index)
		{
		$(this).find('.open_title').addClass('open_titlebg');
		$(this).find('.view_open_list').show();
		}
		else
		{
		$view_list.eq(i).find('.open_title').removeClass('open_titlebg');
		$view_list.eq(i).find('.view_open_list').hide();
		}
		}
	});
})
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
