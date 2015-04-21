<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上海出发到三亚旅游_上海到三亚旅游路线-驴妈妈旅游网</title>
<meta name="keywords" content="上海到三亚旅游,三亚旅游"/>
<meta name="description" content="驴妈妈旅游网推出各地至三亚旅游路线,最热上海出发到三亚旅游,现在赶快来订购当季最热路线,一起去三亚旅游吧!"/>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<base target="_blank" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<script type="text/javascript" src="http://www.lvmama.com/comment/generalNewComment/getComment.do?name=listCmtId&count=6&targetPage=list_hotelCMT_js&placeIds=272"></script>
<link href="http://pic.lvmama.com/styles/zt/minsite_sanya/index.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="http://pic.lvmama.com/img/favicon.ico" type="image/x-icon" /> 
<!--[if IE 6]>
<script src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script>
<script>DD_belatedPNG.fix('.imgFlashIco1,.hotrooter_tuijian h3,.tujian_list01 li,.gl_lianjie,.tejia,.tujian_list li,.place_more_bt,.hot_rooter');</script>
<![endif]-->
</head>
<body>
<div class="sanya_top">
  <div class="sanya_contant"><a href="#" class="sanya_logo"><img alt="" src="http://pic.lvmama.com/img/zt/minsite_sanya/logo.gif"/></a></div>
  <div class="sanya_nav">
    <div class="sanya_contant"> <a href="#" class="sany_logo fl"><img alt="" src="http://pic.lvmama.com/img/zt/minsite_sanya/sany_logo.jpg" /></a>
      <ul class="menus fl">
        <li><a href="http://sanya.lvmama.com/" target="_self">首页</a></li>
        <li><a href="http://sanya.lvmama.com/gd.html" target="_self" class="current">各地到三亚</a></li>
        <li><a href="http://sanya.lvmama.com/dd.html" target="_self">三亚当地游 </a></li>
        <li><a href="http://sanya.lvmama.com/jq.html" target="_self">景点门票</a></li>
        <li><a href="http://sanya.lvmama.com/jd.html" target="_self">特色酒店 </a></li>
        <li><a href="http://sanya.lvmama.com/zt.html"  target="_self">驴行秘制团</a></li>
        <li><a href="http://sanya.lvmama.com/wd.html" target="_self">在线问答</a></li>
      </ul>
    </div>
  </div>
  <div class="sanya_contant01 mt clearfix">
    <div class="sanya_wapper">
       <div class="sanya_main fl">
       <div class="hot_mian">
               <div class="hotrooter_tuijian place_choice">
                    <h3>线路推荐</h3>
                    <ul class="rooter_city place_city fl">
                      <li class="current">三亚当地</li>
                      <li>上海出发</li>
                      <li>北京出发</li>
                      <li>成都出发</li>
                      <li>广州出发</li>
                    </ul>
                    <dl class="more_btlist">
                    	<dt>更 多 <em class="arrow"></em></dt>
                        <dd><span>杭州出发</span><span>南京出发</span><span>重庆出发</span><span>深圳出发</span><span>武汉出发</span><span>石家庄出发</span><span>沈阳出发</span><span>海口出发</span><span>西安出发</span><span>贵阳出发</span></dd>
                    
                    </dl>
   <!--                 <span class="place_more_bt fr">更 多</span>
                  <div>                   
                	<ul class="place_more_city place_more_city01">
                    	<li>杭州出发</li>
                        <li>南京出发</li>
                        <li>重庆出发</li>
                        <li>深圳出发</li>
                        <li>武汉出发</li>
                        <li>石家庄出发</li>
                        <li>沈阳出发</li>
                        <li>海口出发</li>
                        <li>西安出发</li>
                        <li>贵阳出发</li>
                    </ul>
          </div>-->
                </div>
<div class="rooter_prod clearfix">
           
           <#list 1..15 as t>
              <@s.if test="${t} == 1">
                 <div class="city_list city_list_block">
              </@s.if>
              <@s.else>
                  <div class="city_list"> 
              </@s.else>
              <@s.iterator value="map.get('${station}_gedi_${t}')" status="st">     
               <dl class="prod_list prod_place_list prod_place_list01 box move_style" title="${title?if_exists}">
                        <dt><a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a></dt>
                        <dd>
                          <div>
                            <a href="${url?if_exists}">${title?if_exists}</a>
                            <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></div>
                         <a href="${url?if_exists}">${bakWord1?if_exists}</a>
                          <b>产品特色：</b><span class="laber">${bakWord2?if_exists}</span><span class="laber laber01">${bakWord3?if_exists}</span><span class=" laber laber02">${bakWord4?if_exists}</span></dd>
               </dl>
               </@s.iterator>
            </div>
            </#list>
            
          </div>
          </div>
          </div>
      <div class="sany_aside fr">  
        <div class="hot_jd_tuijian">
          <h3>热卖线路</h3>
          <div class="common_box"> 
          <!--hot_rooter S-->
           <ul class="hot_rooter">
               <@s.iterator value="map.get('${station}_gedi_16')" status="st"> 
           		<li>
                	<a href="${url?if_exists}">${title?if_exists}</a>
                    <span class="price_pose"><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></li>
               </@s.iterator>
           </ul>
           <!--hot_rooter E-->
          </div>
        </div>
        <div class="hot_jd_tuijian mt">
          <h3>攻略游记</h3>
          <div class="common_box clearfix">
          <!--gonglie S-->
          <dl class="gonglie_list">
          	<dt>1</dt>
            <dd><a href="http://www.lvmama.com/guide/2012/0206-141475.html">浪漫的三亚：国内蜜月之旅首选地</a></dd>
          </dl>
           <dl class="gonglie_list">
          	<dt>2</dt>
            <dd><a href="http://www.lvmama.com/guide/2011/1209-140582.html">三亚吃住行、购物实用攻略信息</a></dd>
          </dl>
           <dl class="gonglie_list">
          	<dt>3</dt>
            <dd><a href="http://www.lvmama.com/guide/2011/0602-128598.html">毕业旅行季：热情三亚 碧海蓝天</a></dd>
          </dl>
           <dl class="gonglie_list">
          	<dt>4</dt>
            <dd><a href="http://www.lvmama.com/guide/2010/1125-59845.html">三亚自助游攻略–必经的十大体验</a></dd>
          </dl>
             <dl class="gonglie_list">
          	<dt>5</dt>
            <dd><a href="http://www.lvmama.com/guide/2010/1125-59833.html">最受网友追捧的三亚自助游完全攻略</a></dd>
          </dl>
          <!--gonglie E-->
          </div>
        </div>
        <div class="hot_jd_tuijian hot_dp mt">
          <h3>最新点评</h3>
          <div id="listCmtId" class="common_box common_news"></div>
          <!--comment S-->
          <!--comment E-->
          </div>
        </div>
      </div>
      <div class="sanya_contant01 sanya_contant02  clearfix mt">
      <ul class="pic_ad">
        <@s.iterator value="map.get('${station}_gedi_17')" status="st">
        <li><a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a></li>
        </@s.iterator>
      </ul>
    </div>
    </div>
    
  </div>
</div>
<script type="text/javascript" src="http://www.lvmama.com/zt/000global/js/ztFooter.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/minsite_sanya/common.js"></script>
<script type="text/javascript"  src="http://pic.lvmama.com/js/common/losc.js"></script>
<script type="text/javascript" 
src="http://www.lvmama.com/comment/generalNewComment/getComment.do?name=listCmtId&count=6&targetPage=list_hotelCMT_js&placeIds=272"> 
</script>
</body>
</html>
