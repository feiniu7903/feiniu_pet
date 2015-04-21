<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" />
<link href="http://pic.lvmama.com/styles/zt/maerdaifu/madai.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/maerdaifu/common.css" rel="stylesheet" type="text/css"> 
<title>杭州出发 马尔代夫系列</title>
<base target="_blank" />
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
</head>

<body>
    <div  class="pagehead">
        <ul class="header_inner"> 
           <li class="logo"><a href="#"><img alt="" src="http://pic.lvmama.com/img/zt/maerdaifu/logo.jpg" width="257" height="48"></a></li>
           <li class="call"><img alt="" src="http://pic.lvmama.com/img/zt/maerdaifu/call.jpg" width="249" height="62"></li>
        </ul>
     </div><!--header-->
	<div class="prod_Contant">
		<@s.if test="null != map.get('${station}_hz_s') && !map.get('${station}_hz_s').isEmpty()">
			<a name="single" id="single"></a>
			<div class="prod_Contant_01">
				<div class="prod_title">
					<span class="prod_addess prod_samll">杭州出发</span>
					<span class="prod_addess01 prod_samll">马尔代夫单岛系列</span>
					<p class="tips clear">随心挑选适合您的产品</p>
				</div>
				<div class="prod_info clearfix">
					<ul class="prod_info_nums">
						<@s.iterator value="map.get('${station}_hz_s')" status="st">
							<li>
								<div class="prod_pic"><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" /></a></div>
								<div class="prop_pic_title">
									<a class="prod_samll pic_title small_001" href="${url?if_exists}">${title?if_exists}</a>
									<span class="prod_samll pic_pice small_001">¥${memberPrice?if_exists?replace(".0","")}<em>起</em></span>
								</div>
							</li>
						</@s.iterator>
					 </ul>
				</div>
			</div>
		</@s.if>

		<@s.if test="null != map.get('${station}_hz_t') && !map.get('${station}_hz_t').isEmpty()">
			<a name="twin" id="twin"></a>
			<div class="prod_Contant_01">
				<div class="prod_title">
					<span class="prod_addess prod_samll">杭州出发</span>
					<span class="prod_addess01 prod_samll">马尔代夫双岛系列</span>
					<p class="tips clear">随心挑选适合您的产品</p>
				</div>
				<div class="prod_info clearfix">
					<ul class="prod_info_nums">
						<@s.iterator value="map.get('${station}_hz_t')" status="st">
							<li>
								<div class="prod_pic"><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" /></a></div>
								<div class="prop_pic_title">
									<a class="prod_samll pic_title small_001" href="${url?if_exists}">${title?if_exists}</a>
									<span class="prod_samll pic_pice small_001">¥${memberPrice?if_exists?replace(".0","")}<em>起</em></span>
								</div>
							</li>
						</@s.iterator>
					 </ul>
				</div>
			</div>
		</@s.if>

		<@s.if test="null != map.get('${station}_hz_m') && !map.get('${station}_hz_m').isEmpty()">
			<a name="multi" name="id"></a>
			<div class="prod_Contant_01">
				<div class="prod_title">
					<span class="prod_addess prod_samll">杭州出发</span>
					<span class="prod_addess01 prod_samll">马尔代夫多国系列</span>
					<p class="tips clear">随心挑选适合您的产品</p>
				</div>
				<div class="prod_info clearfix">
					<ul class="prod_info_nums">
						<@s.iterator value="map.get('${station}_hz_m')" status="st">
							<li>
								<div class="prod_pic"><a href="${url?if_exists}"><img src="${imgUrl?if_exists}" /></a></div>
								<div class="prop_pic_title">
									<a class="prod_samll pic_title small_001" href="${url?if_exists}">${title?if_exists}</a>
									<span class="prod_samll pic_pice small_001">¥${memberPrice?if_exists?replace(".0","")}<em>起</em></span>
								</div>
							</li>
						</@s.iterator>
					 </ul>
				</div>
			</div>
		</@s.if>
	</div>
</body>

<script type="text/javascript">
	$(".prod_info_nums li").each(function(i){
			$(this).hover(function(){
				$(this).addClass("current");
			},function(){
				$(this).removeClass("current")
			});
		});
  
  </script>
  
      <div class="footer">
           <b>免责声明:</b>马尔代夫旅游节的相关活动最终解释权归驴妈发旅游网所有<br/>
          <span>客服热线：1010-6060</span><span>服务时间：周一至周日早8:00-晚24:00</span><span>客服邮箱：service@lvmama.com</span><br/>
           <span>Copyright © 2012 www.lvmama.com.</span><span>上海景域文化传播有限公司版权所有</span><span>沪ICP备07509677</span>
     </div>
     <!--footer-->
<!-- JiaThis Button BEGIN -->
<script type="text/javascript" src="http://v2.jiathis.com/code/jiathis_r.js?move=0&amp;btn=r2.gif" charset="utf-8"></script>
<!-- JiaThis Button END -->   
</html>
