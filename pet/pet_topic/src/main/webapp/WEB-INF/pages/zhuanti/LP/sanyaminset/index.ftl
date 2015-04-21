<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三亚旅游景点介绍_三亚有什么好玩的_去三亚玩几天-驴妈妈旅游网</title>
<meta name="keywords" content="三亚旅游,三亚景点"/>
<meta name="description" content="驴妈妈旅游网三亚景点大全介绍,带给您全面的三亚游玩资讯,好玩的景点,玩几天,怎么玩等等.让您去在三亚享受到不一样的体验"/>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link rel="shortcut icon" href="http://pic.lvmama.com/img/favicon.ico" type="image/x-icon" /> 
<base target="_blank" />
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/minsite_sanya/index_ad_gun.js"></script>
<link href="http://pic.lvmama.com/styles/zt/minsite_sanya/index.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js"></script>
<script>DD_belatedPNG.fix('.imgFlashIco1,.hotrooter_tuijian h3,.tujian_list01 li,.gl_lianjie,.tejia,.tujian_list li');</script>
<![endif]-->
</head>
<body>
<div class="sanya_top">
  <div class="sanya_contant"><a href="#" class="sanya_logo"><img alt="" src="http://pic.lvmama.com/img/zt/minsite_sanya/logo.gif"/></a></div>
  <div class="sanya_nav">
    <div class="sanya_contant"> <a href="#" class="sany_logo fl"><img alt="" src="http://pic.lvmama.com/img/zt/minsite_sanya/sany_logo.jpg" /></a>
      <ul class="menus fl">
        <li><a href="http://sanya.lvmama.com/" target="_self" class="current">首页</a></li>
        <li><a href="http://sanya.lvmama.com/gd.html" target="_self">各地到三亚</a></li>
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
        <div class="imgFlash">
          <div class="js_goodsImg imgFlashSmall_imgsrc">
            <ul>
              <@s.iterator value="map.get('${station}_shouye_1')" status="st">
              <@s.if test="(#st.index) == 0">
                 <li class="on"><a href="${url?if_exists}" target="_blank" title=""><img alt="" src="${imgUrl?if_exists}"/></a></li>
              </@s.if>
              <@s.else>
                  <li><a href="${url?if_exists}" target="_blank" title=""><img alt="" src="${imgUrl?if_exists}"/></a></li>
              </@s.else>
              </@s.iterator>
            </ul>
          </div>
          <div class="imgFlashIco1">
            <ul class="fr js_imgFlashIco1">
              <@s.iterator value="map.get('${station}_shouye_1')" status="st">
              <@s.if test="(#st.index) == 0">
                 <li class="on"></li>
              </@s.if>
              <@s.else>
                  <li></li>
              </@s.else>
              </@s.iterator>
            </ul>
          </div>
        </div>
        <div class="hot_mian">
          <div class="hotrooter_tuijian mt">
            <h3>线路推荐</h3>
            <ul class="rooter_city fl">
              <li class="current">上海出发</li>
              <li>北京出发</li>
              <li>成都出发</li>
              <li>广州出发</li>
            </ul>
            <a href="http://sanya.lvmama.com/gd.html" class="more_bt fr" target="_parent">更多》</a> </div>
          <div class="rooter_prod clearfix">
          
          
            <div class="city_list city_list_block">
             <@s.iterator value="map.get('${station}_shouye_2')" status="st">
                <dl class="prod_list box move_style" title="${title?if_exists}">
                        <dt><a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a></dt>
                        <dd>
                          <div>
                            <a href="${url?if_exists}">${title?if_exists}</a>
                            <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></div>
                          <a href="${url?if_exists}">${bakWord1?if_exists}</a>
                          <b>产品特色：</b><span class="laber">${bakWord2?if_exists}</span><span class="laber laber01">${bakWord3?if_exists}</span><span class=" laber laber02">${bakWord4?if_exists}</span></dd>
                      </dl>
              </@s.iterator>
              
              <@s.iterator value="map.get('${station}_shouye_3')" status="st">
              <dl class="prod_list_other">
                <dd class="price"><a href="${url?if_exists}">${title?if_exists}</a></dd>
                <dd ><span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></dd>
              </dl>
              </@s.iterator>
            </div>
            
            <div class="city_list">
              <@s.iterator value="map.get('${station}_shouye_4')" status="st">
                <dl class="prod_list box move_style" title="${title?if_exists}">
                        <dt><a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a></dt>
                        <dd>
                          <div>
                            <a href="${url?if_exists}">${title?if_exists}</a>
                            <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></div>
                           <a href="${url?if_exists}">${bakWord1?if_exists}</a>
                          <b>产品特色：</b><span class="laber">${bakWord2?if_exists}</span><span class="laber laber01">${bakWord3?if_exists}</span><span class=" laber laber02">${bakWord4?if_exists}</span></dd>
                      </dl>
              </@s.iterator>
              
              <@s.iterator value="map.get('${station}_shouye_5')" status="st">
              <dl class="prod_list_other">
                <dd class="price"><a href="${url?if_exists}">${title?if_exists}</a></dd>
                <dd ><span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></dd>
              </dl>
              </@s.iterator>
            </div>
            <div class="city_list"> 
              <@s.iterator value="map.get('${station}_shouye_6')" status="st">
                <dl class="prod_list box move_style" title="${title?if_exists}">
                        <dt><a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a></dt>
                        <dd>
                          <div>
                            <a href="${url?if_exists}">${title?if_exists}</a>
                            <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></div>
                           <a href="${url?if_exists}">${bakWord1?if_exists}</a>
                          <b>产品特色：</b><span class="laber">${bakWord2?if_exists}</span><span class="laber laber01">${bakWord3?if_exists}</span><span class=" laber laber02">${bakWord4?if_exists}</span></dd>
                      </dl>
              </@s.iterator>
              
              <@s.iterator value="map.get('${station}_shouye_7')" status="st">
              <dl class="prod_list_other">
                <dd class="price"><a href="${url?if_exists}">${title?if_exists}</a></dd>
                <dd ><span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></dd>
              </dl>
              </@s.iterator>
            </div>
            <div class="city_list">
              <@s.iterator value="map.get('${station}_shouye_8')" status="st">
                <dl class="prod_list box move_style" title="${title?if_exists}">
                        <dt><a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a></dt>
                        <dd>
                          <div>
                            <a href="${url?if_exists}">${title?if_exists}</a>
                            <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></div>
                           <a href="${url?if_exists}">${bakWord1?if_exists}</a>
                          <b>产品特色：</b><span class="laber">${bakWord2?if_exists}</span><span class="laber laber01">${bakWord3?if_exists}</span><span class=" laber laber02">${bakWord4?if_exists}</span></dd>
                      </dl>
              </@s.iterator>
              
              <@s.iterator value="map.get('${station}_shouye_9')" status="st">
              <dl class="prod_list_other">
                <dd class="price"><a href="${url?if_exists}">${title?if_exists}</a></dd>
                <dd ><span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></dd>
              </dl>
              </@s.iterator>
            </div>
          </div>
        </div>
        <div class="hot_mian">
          <div class="hotrooter_tuijian mt">
            <h3>当地游推荐</h3>
            <ul class="rooter_city01 fl">
              <li class="current">自由行 </li>
              <li> 跟团游</li>
            </ul>
            <a href="http://sanya.lvmama.com/dd.html" class="more_bt fr" target="_parent">更多》</a> </div>
          <div class="rooter_prod clearfix">
            <div class="city_list01 city_list_block"> 
            
            <@s.iterator value="map.get('${station}_shouye_10')" status="st">
                <dl class="prod_list box move_style" title="${title?if_exists}">
                        <dt><a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a></dt>
                        <dd>
                          <div>
                            <a href="${url?if_exists}">${title?if_exists}</a>
                            <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></div>
                         <a href="${url?if_exists}">${bakWord1?if_exists}</a>
                          <b>产品特色：</b><span class="laber">${bakWord2?if_exists}</span><span class="laber laber01">${bakWord3?if_exists}</span><span class=" laber laber02">${bakWord4?if_exists}</span></dd>
                      </dl>
              </@s.iterator>
              <@s.iterator value="map.get('${station}_shouye_11')" status="st">
              <dl class="prod_list_other">
                <dd class="price"><a href="${url?if_exists}">${title?if_exists}</a></dd>
                <dd ><span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></dd>
              </dl>
              </@s.iterator>
            </div>
            <div class="city_list01">  
             <@s.iterator value="map.get('${station}_shouye_12')" status="st">
                <dl class="prod_list box move_style" title="${title?if_exists}">
                        <dt><a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a></dt>
                        <dd>
                          <div>
                            <a href="${url?if_exists}">${title?if_exists}</a>
                            <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></div>
                           <a href="${url?if_exists}">${bakWord1?if_exists}</a>
                          <b>产品特色：</b><span class="laber">${bakWord2?if_exists}</span><span class="laber laber01">${bakWord3?if_exists}</span><span class=" laber laber02">${bakWord4?if_exists}</span></dd>
                      </dl>
              </@s.iterator>
              <@s.iterator value="map.get('${station}_shouye_13')" status="st">
              <dl class="prod_list_other">
                <dd class="price"><a href="${url?if_exists}">${title?if_exists}</a></dd>
                <dd ><span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span></dd>
              </dl>
              </@s.iterator>
            </div>
          </div>
        </div>
      </div>
      <div class="sany_aside fr"> <div class="te_zhuangqu box"> <span class="tejia">特惠专区</span>
       <@s.iterator value="map.get('${station}_shouye_14')" status="st">
        <a href="${url?if_exists}"> <img alt="" src="${imgUrl?if_exists}" /></a>
        <div class="te_title">
          <a href="${url?if_exists}">${title?if_exists}</a>
          <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span> 
         </div>
        </@s.iterator>
        </div> <div class=" mt box" ><a href="http://sanya.lvmama.com/zt.html"><img alt="" src="http://pic.lvmama.com/img/zt/minsite_sanya/j_pic03.jpg" /></a>
        </div>
        <div class="hot_jd_tuijian mt">
          <h3>热门酒店推荐<a href="" target="_parent">更多》</a></h3>
          <div class="common_box"> 
          
          <@s.iterator value="map.get('${station}_shouye_15')" status="st">
          <div class="a_lianjie box">
            <a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a>
            <div class="te_title te_title01 add_titile_24 clearfix">
              <a href="${url?if_exists}">${title?if_exists}</a>
            </div>
          </div>
          </@s.iterator>
            
            <ul class="tujian_list">
            <@s.iterator value="map.get('${station}_shouye_16')" status="st">
              <li><a href="${url?if_exists}">${title?if_exists}</a></li>
            </@s.iterator>
            </ul>
          </div>
        </div>
        <div class="hot_jd_tuijian mt">
          <h3>热门门票推荐<a href="" target="_parent">更多》</a></h3>
          <div class="common_box">
            <ul class="tujian_list01">
            <@s.iterator value="map.get('${station}_shouye_17')" status="st">
              <li><a href="${url?if_exists}">${title?if_exists}</a></li>
            </@s.iterator>
            </ul>
          </div>
        </div>
        <div class="hot_jd_tuijian mt">
          <h3>热门一日游推荐<a href="#" target="_parent">更多》</a></h3>
          <div class="common_box">
            <ul class="tujian_list01 zuche_list01 zuche_list010">
            <@s.iterator value="map.get('${station}_shouye_18')" status="st">
             <li> <a href="${url?if_exists}">${title?if_exists}</a> <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span> </li>
            </@s.iterator>
          </ul>
          </div>
        </div>
      </div>
    </div>
    <@s.iterator value="map.get('${station}_shouye_25')" status="st">
    <a href="${url?if_exists}" class="mt banner_pic"><img alt="" src="${imgUrl?if_exists}" /></a>
    </@s.iterator>
    <div class="sanya_wapper mt">
      <div class="fl aside01 mr">
      
        <div class="wedding_title">
        <h3>婚纱推荐</h3>
        <a href="http://sanya.lvmama.com/dd.html">海景婚纱摄影、海景婚纱摄影自由行套餐预订，爱TA，给TA海天之间的浪漫记忆，大海作证，浓情作伴，让我们倾情记录……</a>
        </div>
        <ul class="web_list">
        <@s.iterator value="map.get('${station}_shouye_19')" status="st">
          <li class="box"> <a href="${url?if_exists}" class="wed_pic"><img alt="" src="${imgUrl?if_exists}" /></a>
            <div class="wed_text"></div>
            <div class="te_title te_title02">
             <a href="${url?if_exists}">${title?if_exists}</a>
              <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span> </div>
          </li>
        </@s.iterator>
        </ul>
        
        <div class="zuche_tuijian mt">
          <h4>【租车推荐】</h4>
        </div>
        <div class="zuche_list">
          <ul class="tujian_list01 zuche_list01">
          <@s.iterator value="map.get('${station}_shouye_22')" status="st">
             <li> <a href="${url?if_exists}">${title?if_exists}</a> <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span> </li>
          </@s.iterator>
          </ul>
        </div>
      </div>
      <div class="fl aside01 mr">
        <ul class="web_list">
        <@s.iterator value="map.get('${station}_shouye_20')" status="st">
          <li class="box"> <a href="${url?if_exists}" class="wed_pic"><img alt="" src="${imgUrl?if_exists}" /></a>
            <div class="wed_text"></div>
            <div class="te_title te_title02">
              <a href="${url?if_exists}">${title?if_exists}</a>
              <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span> </div>
          </li>
        </@s.iterator>
        </ul>
        <div class="wedding_title wedding_title_bg">
        <h3>潜水推荐</h3>
        <a href="http://sanya.lvmama.com/dd.html">全中国独一无二的热带海域，重现“海底总动员”的惊奇场景，沉船、堡礁、香蕉船、摩托艇、高空拖拽伞，你看得到的海天一色，想不到的激情体验……</a>
        </div>
        <div class="zuche_tuijian mt">
          <h4>【高尔夫推荐】</h4>
        </div>
        <div class="zuche_list">
          <ul class="tujian_list01 zuche_list01">
            <@s.iterator value="map.get('${station}_shouye_23')" status="st">
            <li> <a href="${url?if_exists}">${title?if_exists}</a> <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span> </li>
            </@s.iterator>
          </ul>
        </div>
      </div>
      <div class="fl aside01"> <div class="wedding_title wedding_title_bg01">
        <h3>美食推荐</h3>
        <a href="http://sanya.lvmama.com/dd.html">精彩旅程没有美食相伴真心不行！海鲜BBQ、超值风味大餐、餐饮优惠券，应有尽有，智慧防宰霸气抗宰，尽在餐饮美食频道！</a>
        </div>
        <ul class="web_list">
        <@s.iterator value="map.get('${station}_shouye_21')" status="st">
          <li class="box"> <a href="${url?if_exists}" class="wed_pic"><img alt="" src="${imgUrl?if_exists}" /></a>
            <div class="wed_text"></div>
            <div class="te_title te_title02">
              <a href="${url?if_exists}">${title?if_exists}</a>
              <span><i>¥</i>${memberPrice?if_exists?replace(".0","")}<em>起</em></span> </div>
          </li>
        </@s.iterator>
        </ul>
        <div class="gl_lianjie"><a href="http://www.lvmama.com/guide/place/hainan_sanya/">最新三亚官方攻略</a></div>
      </div>
    </div>
    <div class="sanya_contant01 sanya_contant02 mt clearfix">
      <ul class="pic_ad">
        <@s.iterator value="map.get('${station}_shouye_24')" status="st">
        <li><a href="${url?if_exists}"><img alt="" src="${imgUrl?if_exists}" /></a></li>
        </@s.iterator>
      </ul>
    </div>
  </div>
</div>
<script type="text/javascript" src="http://www.lvmama.com/zt/000global/js/ztFooter.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/minsite_sanya/common.js"></script>
<script type="text/javascript">
var scrollstart=new IndexScrollAll({goods_event:".js_goodsImg li",scroll_cont:".js_goodsImg ul",imgFlashIco:".js_imgFlashIco1 li",PageSizeNum:1});
	scrollstart.Start();
</script>
<script type="text/javascript"  src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
