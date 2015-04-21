<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>春季踏青游推荐_春季踏青好去处_2012去哪踏青好-驴妈妈旅游网</title>
<meta name="keywords" content="春季踏青,赏花,踏青游"/>
<meta name="description" content="驴妈妈特推春季踏青赏花游,国内多处赏花胜地推荐,让您在2012春季里,闻百花香,游百里地,享受春风的沐浴.一边赏花,一边郊游."/>
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<link href="http://pic.lvmama.com/styles/zt/taq/chun.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
    <script type="text/javascript" src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script>
    <script type="text/javascript">
    DD_belatedPNG.fix('.banner_list li .youcai_nums01_dowm,.banner_list li .youcai_nums02_dowm,.banner_list li .youcai_nums03_dowm,.banner_list li .youcai_nums04_dowm,.banner_list li .youcai_nums05_dowm,.banner_list li .youcai_nums06_dowm,.banner_list li .youcai_nums_dowm,.banner_list li .youcai_nums:hover,.banner_list li .youcai_nums01:hover,.banner_list li .youcai_nums02:hover,.banner_list li .youcai_nums03:hover,.banner_list li .youcai_nums04:hover,.banner_list li .youcai_nums05:hover,.banner_list li .youcai_nums06:hover,.banner_list li .youcai_nums_down');
    </script>
<![endif]-->
</head>
<body>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<div class="chun_wapper">
  <div class="chun_contant">
    <div class="banner_top">
      <div class="chun_top" id="Slide_pic">
        <div class="chun_top_box">
          <ul class="bakc_pic">
		  <@s.iterator value="map.get('${station}_tqjdt')">
			<li>
				<a href="${url?if_exists}" target="_blank"><img src="${imgUrl?if_exists}" /></a>
				<h3 class="title_pic"><a href="${url?if_exists}" target="_blank">${title?if_exists}</a><a href="${url?if_exists}" class="lianj_biaoshi"></a></h3>
			</li>
		  </@s.iterator>
          </ul>
        </div>

        <ul class="bakc_pic_nums">
          <li><a href="#"></a></li>
          <li><a href="#"></a></li>
          <li  class="add_color_01"><a href="#"></a></li>
        </ul>
      </div>
    </div>
    <div class="chun_info">
      <div class="chun_banner">
        <ul class="banner_list">
          <li><a href="#" class="youcai_nums youcai_nums_down" name="youcai" id="youcai">油菜花系列</a></li>
          <li><a href="#yinghua" class="youcai_nums01">樱花系列　</a></li>
          <li><a href="#taohua" class="youcai_nums02">桃花系列</a></li>
          <li><a href="#meihua" class="youcai_nums03">梅花系列</a></li>
          <li><a href="#mudan" class="youcai_nums04">牡丹系列</a></li>
          <li><a href="#taqing" class="youcai_nums05">踏青胜地</a></li>
          <li><a href="#changtu" class="youcai_nums06">国内长途精选</a></li>
        </ul>
      </div>
      <div class="chun_info_list clearfix">
        <div class="info_main">
			<@s.iterator value="map.get('${station}_ych')" status="st">
				 <@s.if test="#st.index == 0">
					<div class="pro_nums pro_nums01">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_ych_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if> <em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del><br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.if>
				 <@s.else>
					<div class="pro_info pro_nums pro_nums02">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_ych_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
								<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.else>
			</@s.iterator>
        </div>
        <div class="info_side"><img src="http://pic.lvmama.com/img/zt/taq/side_pic.jpg" width="219" height="356" />
          <dl class="side_jd">
            <dt>热门景点：</dt>
       <dd><a href="http://www.lvmama.com/dest/jiangxiwuyuan" target="_blank">婺源　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhejiang_xianju" target="_blank">仙居</a></dd>
            <dd><a href="http://www.lvmama.com/dest/hangzhou_jiande" target="_blank">建德</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhejiang_tonglu" target="_blank">桐庐</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tongliguzhen" target="_blank">同里</a></dd>
            <dd><a href="http://www.lvmama.com/dest/qiyunshan" target="_blank">齐云山</a></dd>
            <dd><a href="http://www.lvmama.com/dest/dazonghu" target="_blank">大纵湖　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/shanghaihaiwangongyuan" target="_blank">海湾国家森林公园</a></dd>
            
            
          </dl>
		  <!--star-->
		<br class="clear" />
		          <dl class="side_jd">
            <dt>热门酒店：</dt>
       <dd><a href=" http://www.lvmama.com/dest/xjshijianshangwu" target="_blank">仙居时间商务宾馆　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/lantingdujia" target="_blank">桐庐岚庭度假酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tlwanhaowanjia" target="_blank">万好万家快捷酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tlwanqiang" target="_blank">桐庐万强农庄</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tlhongkai" target="_blank">桐庐宏凯宾馆</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tonglukaiyuanmingduda" target="_blank">桐庐开元名都酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tonglurongyeda" target="_blank">桐庐荣业大酒店　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tongludaqishanjingyuandujiacun" target="_blank">桐庐大奇山景苑</a></dd>
			 <dd><a href="http://www.lvmama.com/dest/yangguangyizhanshangwujiudian" target="_blank">同里阳光驿站酒店 </a></dd>
			  <dd><a href="http://www.lvmama.com/dest/sztonglihudafan" target="_blank">苏州同里湖大饭店</a></dd>
			   
            
           
          </dl>
		  
		  <!--end-->
          
          
             
                   <br class="clear" />
          <dl class="side_jd01">
            <dt>热门专题：</dt>
            <dd><a href=" http://www.lvmama.com/comment/zt/shanghua/?losc=dpxxtq001_zt_lvmama" target="_blank">赏花召集令！网友热议十大赏花胜地</a></dd>
            <dd><a href="http://www.lvmama.com/info/lvxingtop/2012-0305-45405.html" target="_blank">黄了黄了！上海周边赏油菜花攻略！</a></dd>
            <dd><a href="http://www.lvmama.com/info/lvxingtop/2012-0223-45201.html " target="_blank">婺源看油菜花攻略 超实用攻略分享！</a></dd>
            
          </dl>
        </div>
      </div>
    </div>
    <div class="chun_info">
      <div class="chun_banner">
        <ul class="banner_list">
          <li><a href="#youcai" class="youcai_nums">油菜花系列</a></li>
          <li><a name="yinghua" id="yinghua" class="youcai_nums01 youcai_nums01_dowm">樱花系列　</a></li>
          <li><a href="#taohua" class="youcai_nums02">桃花系列</a></li>
          <li><a href="#meihua" class="youcai_nums03">梅花系列</a></li>
          <li><a href="#mudan" class="youcai_nums04">牡丹系列</a></li>
          <li><a href="#taqing" class="youcai_nums05">踏青胜地</a></li>
          <li><a href="#changtu" class="youcai_nums06">国内长途精选</a></li>
        </ul>
      </div>
      <div class="chun_info_list1 clearfix">
        <div class="info_main">
			<@s.iterator value="map.get('${station}_yh')" status="st">
				 <@s.if test="#st.index == 0">
					<div class="pro_nums pro_nums01">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_yh_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
									<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.if>
				 <@s.else>
					<div class="pro_info pro_nums pro_nums02">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_yh_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.else>
			</@s.iterator>

	</div>
        <div class="info_side  info_side01"><img src="http://pic.lvmama.com/img/zt/taq/side_pic01.jpg" width="234" height="385" />
          <dl class="side_jd">
            <dt>热门景点：</dt>
            <dd><a href="http://www.lvmama.com/dest/taihuyuantouzhu" target="_blank">鼋头渚</a></dd>
            <dd><a href="http://www.lvmama.com/dest/shijigongyuan" target="_blank">上海世纪公园</a></dd>
            <dd><a href="http://www.lvmama.com/dest/simingshan" target="_blank">四明山</a></dd>
            <dd><a href="http://www.lvmama.com/dest/dapengshan" target="_blank">达蓬山</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xihu" target="_blank">西湖</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhongnanbaicaoyuan" target="_blank">中南百草原</a></dd>
            
          
          </dl>
		  <!--star-->
		<br class="clear" />
		          <dl class="side_jd">
            <dt>热门酒店：</dt>
       <dd><a href=" http://www.lvmama.com/dest/nbyageerdapengshanzhuang" target="_blank">雅戈尔达蓬山庄　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhongnanbaicaoyuandujiajiudian" target="_blank">中南百草原酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xingchenglinglinbinguan" target="_blank">杭州星程灵隐宾馆</a></dd>
            <dd><a href="http://www.lvmama.com/dest/hangzhoumanjuzhutidujia" target="_blank">杭州漫居主题酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/wxshanshuilijing" target="_blank">无锡山水丽景酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/wxbulagetiyan" target="_blank">无锡布拉格酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/wxxuxiakeqingnianlvshe" target="_blank">徐霞客青年旅舍　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/wx1881bandao" target="_blank">无锡1881半岛酒店</a></dd>
			 <dd><a href="http://www.lvmama.com/dest/wxjiangyuanshanzhuang" target="_blank">无锡江原山庄 </a></dd>
			  <dd><a href="http://www.lvmama.com/dest/xingchengyougejingpinspajiudian" target="_blank">星程优阁SPA酒店</a></dd>
			   
            
           
          </dl>
		  
		  <!--end-->
          
             
                   <br class="clear" />
          <dl class="side_jd01">
            <dt>热门专题：</dt>
            <dd><a href=" http://www.lvmama.com/dest/xjshijianshangwu" target="_blank">45°樱花拂过的小清新 国内赏樱胜地</a></dd>
          <dd><a href="http://www.lvmama.com/guide/zt/wuxiyhj/" target="_blank">无锡鼋头渚--且为樱花醉一场【原创】</a></dd>
		  
		  
          </dl>
        </div>
      </div>
    </div>
    <div class="chun_info">
      <div class="chun_banner">
        <ul class="banner_list">
          <li><a href="#youcai" class="youcai_nums">油菜花系列</a></li>
          <li><a href="#yinghua" class="youcai_nums01">樱花系列　</a></li>
          <li><a name="taohua" id="taohua" class="youcai_nums02  youcai_nums02_dowm">桃花系列</a></li>
          <li><a href="#meihua" class="youcai_nums03">梅花系列</a></li>
          <li><a href="#mudan" class="youcai_nums04">牡丹系列</a></li>
          <li><a href="#taqing" class="youcai_nums05">踏青胜地</a></li>
          <li><a href="#changtu" class="youcai_nums06">国内长途精选</a></li>
        </ul>
      </div>
      <div class="chun_info_list2 clearfix">
        <div class="info_main">
			<@s.iterator value="map.get('${station}_th')" status="st">
				 <@s.if test="#st.index == 0">
					<div class="pro_nums pro_nums01">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_th_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.if>
				 <@s.else>
					<div class="pro_info pro_nums pro_nums02">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_th_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.else>
			</@s.iterator>		  
        </div>
        <div class="info_side  info_side01 info_side02"><img src="http://pic.lvmama.com/img/zt/taq/side_pic02.jpg" width="234" height="386" />
          <dl class="side_jd">
            <dt>热门景点：</dt>
       <dd><a href="http://www.lvmama.com/dest/xikou" target="_blank">溪口</a></dd>
            <dd><a href="http://www.lvmama.com/dest/taohuadaofengjingqu" target="_blank">桃花岛</a></dd>
            <dd><a href="http://www.lvmama.com/dest/fuchuntaoyuan" target="_blank">富春桃源</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xihu" target="_blank">西湖</a></dd>
            <dd><a href="http://www.lvmama.com/dest/houshan" target="_blank">吼山</a></dd>
            <dd><a href="http://www.lvmama.com/dest/nanhuitaohuayuan" target="_blank">南汇桃花村</a></dd>
            <dd><a href="http://www.lvmama.com/dest/datuantaoyuan" target="_blank">大团桃园</a></dd>
            
           
          </dl>
		  
		  <!--star-->
		<br class="clear" />
		          <dl class="side_jd">
            <dt>热门酒店：</dt>
       <dd><a href=" http://www.lvmama.com/dest/xkyinfengdujia" target="_blank">溪口银凤度假村　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xkjindishangwu" target="_blank">奉化溪口锦堤宾馆</a></dd>
            <dd><a href="http://www.lvmama.com/dest/nanyuanejiasxikou" target="_blank"> 南苑e家商务旅店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/hangzhouolufengqingjiari" target="_blank">欧陆风情假日酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/hzliutong" target="_blank">杭州六通宾馆</a></dd>
            <dd><a href="http://www.lvmama.com/dest/hzouyu" target="_blank">杭州莲遇酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xianwuchengshixihu" target="_blank">鲜屋城市酒店　</a></dd>
           
			   
            
           
          </dl>
		  
		  <!--end-->
             
                   <br class="clear" />
          <dl class="side_jd01">
            <dt>热门专题：</dt>
            <dd><a href="http://www.lvmama.com/guide/zt/taohua/ " target="_blank">江南春色好，桃李竞芳菲-赏花全情报</a></dd>
            
          </dl>
        </div>
      </div>
    </div>
    <div class="chun_info">
      <div class="chun_banner">
        <ul class="banner_list">
          <li><a href="#youcai" class="youcai_nums">油菜花系列</a></li>
          <li><a href="#yinghua" class="youcai_nums01">樱花系列　</a></li>
          <li><a href="#taohua" class="youcai_nums02">桃花系列</a></li>
          <li><a name="meihua" id="meihua" class="youcai_nums03 youcai_nums03_dowm">梅花系列</a></li>
          <li><a href="#mudan" class="youcai_nums04">牡丹系列</a></li>
          <li><a href="#taqing" class="youcai_nums05">踏青胜地</a></li>
          <li><a href="#changtu" class="youcai_nums06">国内长途精选</a></li>
        </ul>
      </div>
      <div class="chun_info_list3 clearfix">
	<div class="info_main">
 			<@s.iterator value="map.get('${station}_mh')" status="st">
				 <@s.if test="#st.index == 0">
					<div class="pro_nums pro_nums01">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_mh_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.if>
				 <@s.else>
					<div class="pro_info pro_nums pro_nums02">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_mh_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.else>
			</@s.iterator>
       
	</div>
        <div class="info_side  info_side01 info_side03"><img src="http://pic.lvmama.com/img/zt/taq/side_pic03.jpg" width="234" height="386" />
          <dl class="side_jd">
            <dt>热门景点：</dt>
            <dd><a href="http://www.lvmama.com/dest/chaoshan" target="_blank">超山　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xiangxuehai" target="_blank">香雪海</a></dd>
            <dd><a href="http://www.lvmama.com/dest/linwudong" target="_blank">林屋洞</a></dd>
            <dd><a href="http://www.lvmama.com/dest/meiyuan" target="_blank">梅园</a></dd>
            <dd><a href="http://www.lvmama.com/dest/meihuashan" target="_blank">梅花山</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xixishidi" target="_blank">西溪</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhongnanbaicaoyuan" target="_blank">中南百草原　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/shijigongyuan" target="_blank">上海世纪公园</a></dd>
           
         
          </dl>
		    <!--star-->
		<br class="clear" />
		          <dl class="side_jd">
            <dt>热门酒店：</dt>
       <dd><a href=" http://www.lvmama.com/dest/haiwaihaixixi598binguan" target="_blank">海外海西溪598宾馆　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/hzxixijiudian" target="_blank">杭州西溪度假酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xixibulukejiudian" target="_blank"> 西溪布鲁克酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/njguojihuiyi" target="_blank">南京国际会议酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/njjinyaoshi" target="_blank">金钥匙国际大酒店</a></dd>
            <dd><a href=" http://www.lvmama.com/dest/sztaihugaoerfu" target="_blank">太湖高尔夫酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/sztaihuniuzaifengqingdujiacun" target="_blank">太湖牛仔风情度假　</a></dd>
			<dd><a href="http://www.lvmama.com/dest/szdeyuegehujingdujia" target="_blank">得月阁湖景别墅</a></dd>
            <dd><a href="http://www.lvmama.com/dest/wxbulagetiyan" target="_blank">布拉格体验酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/guluomadajiudian" target="_blank">古罗马大酒店　</a></dd>
           
			   
            
            
          </dl>
		  
		  <!--end-->
          
             
                    <br class="clear" />
          <dl class="side_jd01">
            <dt>热门专题：</dt>
            <dd><a href=" http://www.lvmama.com/guide/zt/chaoshansm/" target="_blank">超山梅花开--杭州赏梅穷游攻略</a></dd>
           
          </dl>
        </div>
      </div>
    </div>
    <div class="chun_info">
      <div class="chun_banner">
        <ul class="banner_list">
          <li><a href="#youcai" class="youcai_nums">油菜花系列</a></li>
          <li><a href="#yinghua" class="youcai_nums01">樱花系列　</a></li>
          <li><a href="#taohua" class="youcai_nums02">桃花系列</a></li>
          <li><a href="#meihua" class="youcai_nums03">梅花系列</a></li>
          <li><a name="mudan" id="mudan" class="youcai_nums04 youcai_nums04_dowm">牡丹系列</a></li>
          <li><a href="#taqing" class="youcai_nums05">踏青胜地</a></li>
          <li><a href="#changtu" class="youcai_nums06">国内长途精选</a></li>
        </ul>
      </div>
 <div class="chun_info_list4 clearfix">
 <div class="info_main">
			<@s.iterator value="map.get('${station}_md')" status="st">
				 <@s.if test="#st.index == 0">
					<div class="pro_nums pro_nums01">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_md_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								  <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.if>
				 <@s.else>
					<div class="pro_info pro_nums pro_nums02">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_md_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.else>
			</@s.iterator>
         
        </div>
        <div class="info_side  info_side01 info_side04"><img src="http://pic.lvmama.com/img/zt/taq/side_pic04.jpg" width="234" height="386" />
          <dl class="side_jd">
            <dt>热门景点：</dt>
             <dd><a href="http://www.lvmama.com/dest/changshushanghu" target="_blank">尚湖　</a></dd>
            
          </dl>
        </div>
      </div>
    </div>
    <div class="chun_info">
      <div class="chun_banner">
        <ul class="banner_list">
          <li><a href="#youcai" class="youcai_nums">油菜花系列</a></li>
          <li><a href="#yinghua" class="youcai_nums01">樱花系列　</a></li>
          <li><a href="#taohua" class="youcai_nums02">桃花系列</a></li>
          <li><a href="#meihua" class="youcai_nums03">梅花系列</a></li>
          <li><a href="#mudan" class="youcai_nums04">牡丹系列</a></li>
          <li><a name="taqing" id="taqing" class="youcai_nums05 youcai_nums05_dowm">踏青胜地</a></li>
          <li><a href="#changtu" class="youcai_nums06">国内长途精选</a></li>
        </ul>
      </div>
  <div class="chun_info_list5 clearfix">
 <div class="info_main">
			<@s.iterator value="map.get('${station}_cjtq')" status="st">
				 <@s.if test="#st.index == 0">
					<div class="pro_nums pro_nums01">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_cjtq_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.if>
				 <@s.else>
					<div class="pro_info pro_nums pro_nums02">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_cjtq_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.else>
			</@s.iterator>
		  
        </div>
        <div class="info_side  info_side01 info_side05"><img src="http://pic.lvmama.com/img/zt/taq/side_pic05.jpg" width="234" height="386" />
          <dl class="side_jd">
            <dt>热门目的地：</dt>
            <dd><a href="http://www.lvmama.com/dest/zhongguo_shanghai" target="_blank">上海</a></dd>
            <dd><a href="http://www.lvmama.com/dest/jiangsu_suzhou" target="_blank">苏州</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhejiang_hangzhou" target="_blank">杭州</a></dd>
            <dd><a href="http://www.lvmama.com/dest/jiangsu_wuxi" target="_blank">无锡</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhejiang_ningbo" target="_blank">宁波</a></dd>
            <dd><a href="http://www.lvmama.com/dest/jiangsu_jiangsuchangzhou" target="_blank">常州</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhejiang_huzhoushianjixian" target="_blank">安吉　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/jiangsu_nanjing" target="_blank">南京</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhejiang_shaoxing" target="_blank">绍兴　</a></dd>
			  <dd><a href="http://www.lvmama.com/dest/jiangsu_yangzhou" target="_blank">扬州　</a></dd>
			  <dd><a href="http://www.lvmama.com/dest/zhejiang_jinhua" target="_blank">金华</a></dd>
			  <dd><a href="http://www.lvmama.com/dest/zhejiang_zhoushan" target="_blank">普陀山　</a></dd>
			   <dd><a href="http://www.lvmama.com/dest/anhui_huangshan" target="_blank">黄山　</a></dd>
			   <dd><a href="http://www.lvmama.com/dest/jiangxi_wuyuan" target="_blank">婺源　</a></dd>
			            
          </dl>
		  
		    <!--star-->
		<br class="clear" />
		          <dl class="side_jd">
            <dt>热门酒店：</dt>
       <dd><a href=" http://www.lvmama.com/dest/nxxiaolianzhuang" target="_blank">南浔小莲庄宾馆　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/hztaihuleidisen" target="_blank">太湖阳光雷迪森</a></dd>
            <dd><a href="http://www.lvmama.com/dest/nbqianhuyuezhuang" target="_blank"> 宁波钱湖悦庄酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/shanghaibeihuwandujiacun" target="_blank">上海北湖湾度假村</a></dd>
            <dd><a href="http://www.lvmama.com/dest/shqqxunyicaoputibeshudujiacun" target="_blank">上海77薰衣岛璞缇</a></dd>
            <dd><a href=" http://www.lvmama.com/dest/hangzhouwankaixihudian" target="_blank">万凯（杭州西湖店）</a></dd>
            <dd><a href="http://www.lvmama.com/dest/hangzhouyilaihubindian" target="_blank">怡莱（湖滨路店)　</a></dd>
			<dd><a href="http://www.lvmama.com/dest/szwangshangengdaodujia" target="_blank">苏州旺山耕岛别墅</a></dd>
            <dd><a href="http://www.lvmama.com/dest/taohuawuchuanyibinguan" target="_blank">桃花坞创意宾馆</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xiyonggubaojiudian" target="_blank">西雍古堡酒店　</a></dd>
           
			   
            
           
          </dl>
		  
		  <!--end-->
             
                   
 <br class="clear" />
          <dl class="side_jd01">
            <dt>热门专题：</dt>
            <dd><a href=" http://www.lvmama.com/comment/zt/csjtaqing/?losc=dpxxtq001_zt_lvmama" target="_blank">太阳出来踏青去！长三角踏青必去地</a></dd>
            <dd><a href=" http://www.lvmama.com/guide/zt/shcaomei/" target="_blank">春季踏青第一站：草莓抢先采</a></dd>
          </dl>
        </div>
      </div>
    </div>
    <div class="chun_info">
      <div class="chun_banner">
        <ul class="banner_list">
          <li><a href="#youcai" class="youcai_nums">油菜花系列</a></li>
          <li><a href="#yinghua" class="youcai_nums01">樱花系列　</a></li>
          <li><a href="#taohua" class="youcai_nums02">桃花系列</a></li>
          <li><a href="#meihua" class="youcai_nums03">梅花系列</a></li>
          <li><a href="#mudan" class="youcai_nums04">牡丹系列</a></li>
          <li><a href="#taqing" class="youcai_nums05">踏青胜地</a></li>
          <li><a name="#chantu" id="changtu" class="youcai_nums06 youcai_nums06_dowm">国内长途精选</a></li>
        </ul>
      </div>
   <div class="chun_info_list6 clearfix">
 <div class="info_main">
			<@s.iterator value="map.get('${station}_gnct')" status="st">
				 <@s.if test="#st.index == 0">
					<div class="pro_nums pro_nums01">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_gnct_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.if>
				 <@s.else>
					<div class="pro_info pro_nums pro_nums02">
						<div class=" main_title">
							<h3>${title?if_exists}</h3>
						</div>
						<ul class="main_list clearfix">
						  <@s.iterator value="map.get('${station}_gnct_${st.index + 1}')">
							<li class="clearfix"> 
								<strong>【${bakWord1?if_exists}】</strong>
								<a href="${url?if_exists}"  class="info_text" target="_blank">${title?if_exists}</a>
								<#if bakWord2?exists && bakWord2!=""><span class="${bakWord2}_pic"><@s.if test="'tuijian' == ${bakWord2}">推荐</@s.if><@s.if test="'hot' == ${bakWord2}">热门</@s.if><@s.if test="'new' == ${bakWord2}">新品</@s.if></span></#if><em>¥${memberPrice?if_exists?replace(".0","")}<i>起</i></em> <del>¥${marketPrice?if_exists?replace(".0","")}</del> 
										<br class="clear" />
								<p class="clear clearfix info_tips">${remark?if_exists}</p>
								 <a href="${url?if_exists}" class="info_bt" target="_blank">立即预订</a>
							</li>
						  </@s.iterator>
						  <#if url?exists && url!=""><a href="${url}" class="more_bt" target="_blank">更多</a></#if>
						</ul>
					</div>
				 </@s.else>
			</@s.iterator>

	</div>
        <div class="info_side  info_side01 info_side06"><img src="http://pic.lvmama.com/img/zt/taq/side_pic06.jpg" width="234" height="386" />
          <dl class="side_jd">
            <dt>热门景点：</dt>
               <dd><a href="http://www.lvmama.com/dest/fujian_wuyishan/dest2dest_tab_frm79" target="_blank">福建武夷山　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/fujian_xiamen/dest2dest_tab_frm79" target="_blank">厦门鼓浪屿</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhongguo_jiangxi/dest2dest_tab_frm79" target="_blank">江西庐山</a></dd>
            <dd><a href="http://www.lvmama.com/dest/hainan_sanya/dest2dest_tab_frm79" target="_blank">海南三亚</a></dd>
            <dd><a href="http://www.lvmama.com/dest/guangxi_yangshuo/dest2dest_tab_frm79" target="_blank">广西桂林</a></dd>
            <dd><a href="http://www.lvmama.com/dest/sichuan_scjiuzhaigou/dest2dest_tab_frm79" target="_blank">四川九寨沟</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhongguo_shannxi/dest2dest_tab_frm79" target="_blank">西安兵马俑　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhongguo_xizang/dest2dest_tab_frm79" target="_blank">西藏拉萨</a></dd>
            <dd><a href="http://www.lvmama.com/dest/zhongguo_beijing/dest2dest_tab_frm79" target="_blank">北京八达岭　</a></dd>
			 <dd><a href="http://www.lvmama.com/dest/guangxi_beihai/dest2dest_tab_frm79" target="_blank">广西北海　</a></dd>
			  <dd><a href="http://www.lvmama.com/dest/hunan_zhangjiajie/dest2dest_tab_frm79" target="_blank">湖南张家界　</a></dd>
			   <dd><a href="http://www.lvmama.com/dest/zhongguo_hubei/dest2dest_tab_frm79" target="_blank">长江三峡　</a></dd>
            
          </dl>
		  
		   <!--star-->
		<br class="clear" />
		          <dl class="side_jd">
            <dt>热门酒店：</dt>
       <dd><a href=" http://www.lvmama.com/dest/xiamenbisite" target="_blank">厦门毕思特　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/bjnanyuanejiawangfujing" target="_blank">南苑e家（王府井店）</a></dd>
            <dd><a href="http://www.lvmama.com/dest/guoguanghaoshengdujiajiudian" target="_blank">三亚国光豪生酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/xatianyukailai" target="_blank">西安天域凯莱饭店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/chengduxingyijiudian" target="_blank">成都星逸酒店</a></dd>
            <dd><a href=" http://www.lvmama.com/dest/yintelagenheisenlin" target="_blank">东部华侨城黑森林</a></dd>
            <dd><a href="http://www.lvmama.com/dest/guangzhouchanglongjiudian" target="_blank">广州长隆酒店　</a></dd>
			<dd><a href="http://www.lvmama.com/dest/glxingchengjinwan" target="_blank">桂林星程金湾酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/ljhuajiantangkezhan" target="_blank">花间堂高级客栈</a></dd>
         
           
			   
            
           
          </dl>
		  
		  <!--end-->
          
             
                   <!--<br class="clear" />
          <dl class="side_jd01">
            <dt>热门酒店：</dt>
            <dd><a href=" http://www.lvmama.com/dest/xjshijianshangwu" target="_blank">仙居时间商务宾馆　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/lantingdujia" target="_blank">桐庐岚庭度假酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tlwanhaowanjia" target="_blank">万好万家快捷酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tlwanqiang" target="_blank">桐庐万强农庄</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tlhongkai" target="_blank">桐庐宏凯宾馆</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tonglukaiyuanmingduda" target="_blank">桐庐开元名都酒店</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tonglurongyeda" target="_blank">桐庐荣业大酒店　</a></dd>
            <dd><a href="http://www.lvmama.com/dest/tongludaqishanjingyuandujiacun" target="_blank">桐庐大奇山景苑</a></dd>
            <dd><a href="http://www.lvmama.com/dest/yangguangyizhanshangwujiudian" target="_blank">同里阳光驿站酒店 </a></dd>
            <dd><a href="http://www.lvmama.com/dest/sztonglihudafan" target="_blank">苏州同里湖大饭店</a></dd>
          </dl>-->
		  
        </div>
      </div>
    </div>
	
    <h3 class="hot_title">热门活动正在进行</h3>
    <ul class="hot_pic_list">
      <li><a href="http://www.lvmama.com/zt/lvyou/kailvxing/
" target="_blank"><img src="http://pic.lvmama.com/img/zt/taq/hot_pic.jpg" width="284" height="135" /></a></li>
      <li><a href="http://www.lvmama.com/zt/lvyou/yunnan" target="_blank"><img src="http://pic.lvmama.com/opi/yunnan284_135.jpg" width="284" height="135" /></a></li>
      <li><a href="http://www.lvmama.com/zt/lvyou/wenquan" target="_blank"><img src="http://pic.lvmama.com/img/zt/taq/hot_pic2.jpg" width="284" height="135" /></a></li>
    </ul>
  </div>
</div>
<div class="footer_bg">
  <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js"></script>
</div>
<div class="submenu1" id="submenu1">
  <div><img src="http://pic.lvmama.com/img/zt/taq/side_01.jpg" width="120" height="66" /></div>
  <ul class="nav_bg">
    <li><a href="#youcai">油菜花</a></li>
    <li><a href="#yinghua">樱花</a></li>
    <li><a href="#taohua">桃花</a></li>
    <li><a href="#meihua">梅花</a></li>
    <li><a href="#mudan">牡丹</a></li>
    <li><a href="#taqing">踏青胜地</a></li>
    <li><a href="#changtu">国内长途</a></li>
    <li><a href="javascript:scroll(0,0)">返回顶部</a></li>
  </ul>
  <div><img src="http://pic.lvmama.com/img/zt/taq/side_02.jpg" width="120" height="18" /></div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	var num=0;
	$(".bakc_pic_nums li").mouseover(function(){
	var index=$(this).index();
	num=index;
	$(this).addClass("add_color_01").siblings().removeClass("add_color_01");
	$(".bakc_pic li").eq(index).fadeIn(500).siblings().hide();
		});
	function imgScroll(){
		$(".bakc_pic_nums li").eq(num).addClass("add_color_01").siblings().removeClass("add_color_01");
		$(".bakc_pic li").eq(num).fadeIn(500).siblings().hide();
		num=num+1;
		if(num>$(".bakc_pic li").length-1) num=0;
	}
		var timer=setInterval(imgScroll,5000);
		$("#Slide_pic").hover(function(){
				clearInterval(timer);
			},
			function(){
				timer = setInterval(imgScroll,5000);			
			}
		);
});
</script>
<script type="text/javascript">
  $(".main_list li").hover(function(){
    $(this).css("background","#f6f6f6");},function(){
    $(this).css("background","none");
});
  
  </script>
  <script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
