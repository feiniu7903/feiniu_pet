<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>广州长隆主题乐园门票_长隆主题乐园自由行-驴妈妈旅游网</title>
  <meta name="keywords" content="长隆主题乐园,自由行,门票" />
  <meta name="description" content="驴妈妈旅游广州长隆主题乐园专题:我们提供广州长隆主题乐园门票预订,长隆主题乐园自由行,跟团游产品,更有周边特色酒店预订!" />
  <link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
  <link href="http://pic.lvmama.com/styles/zt/changlong/changlong.css" rel="stylesheet" type="text/css" />
  <script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
  <base target="_blank" />
</head>

<body>
  <script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
  <div id="wrap">
    <div class="banner" id="slide">
      <div class="slidePic">
      	<a>
          <img src="http://www.lvmama.com/zt/promo/gzcl/images/banner4.jpg" width="998" height="382" alt="banner图片" />
        </a>
      	<a>
          <img src="http://www.lvmama.com/zt/promo/gzcl/images/banner3.jpg" width="998" height="382" alt="banner图片" />
        </a>
        <a>
          <img src="http://www.lvmama.com/zt/promo/gzcl/images/banner1.jpg" width="998" height="382" alt="banner图片" />
        </a>
        <a>
          <img src="http://www.lvmama.com/zt/promo/gzcl/images/banner2.jpg" width="998" height="382" alt="banner图片" />
        </a>
        <a>
          <img src="http://www.lvmama.com/zt/promo/gzcl/images/banner5.jpg" width="998" height="382" alt="banner图片" />
        </a>
      </div>
      <ul class="slideControl">
        <li class="curr"></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
      </ul>
    </div>
    <!--banner end-->
    <div class="page">
      <div class="maplinkCon"> <i class="jt_lt"></i> <i class="jt_rt"></i>
        <ul class="maplink">
          <li class="menpiao">
            <a href="#menpiao" target="_self">
              <span>◆</span>
            </a>
          </li>
          <li class="ziyouxing">
            <a href="#ziyouxing" target="_self">
              <span>◆</span>
            </a>
          </li>
          <li class="jiudian">
            <a href="#jiudian" target="_self">
              <span>◆</span>
            </a>
          </li>
          <li class="gentuanyou">
            <a href="#gentuanyou" target="_self">
              <span>◆</span>
            </a>
          </li>
          <li class="huodong">
            <a href="#huodong" target="_self">
              <span>◆</span>
            </a>
          </li>
        </ul>
      </div>
      <div class="mainTop clearfix">
        <div class="videoCon">
         <embed src="http://player.youku.com/player.php/sid/XNjYxNDgxNjY0/v.swf" allowFullScreen="true" quality="high" width="480" height="400" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed>
      </div>
      <div class="lv_reason">
        <h3>为什么选择驴妈妈</h3>
        <p class="reason_tit">
          <span class="line_lt"></span> <strong></strong>
          <span class="line_rt"></span>
        </p>
        <div class="reason_text">
          <@s.iterator value="map.get('${station}_cllvmama')" status="st">
            <p>
              <@s.if test="null != bakWord2 && '' != bakWord2">${bakWord2?if_exists}</@s.if>
            </p>
            <p>
              <@s.if test="null != bakWord3 && '' != bakWord3">${bakWord3?if_exists}</@s.if>
            </p>
            <p>
              <@s.if test="null != bakWord4 && '' != bakWord4">${bakWord4?if_exists}</@s.if>
            </p>
            <p>
              <@s.if test="null != bakWord5 && '' != bakWord5">${bakWord5?if_exists}</@s.if>
            </p>
            <p>
              <@s.if test="null != bakWord6 && '' != bakWord6">${bakWord6?if_exists}</@s.if>
            </p>
          </@s.iterator>
        </div>
        <p class="reason_img" id="reason_img">
          <a>
            <img src="http://pic.lvmama.com/img/zt/changlong/zhengshu1.jpg" width="93" height="114" alt="证书">
            <span>点击查看大图</span>
          </a>
          <a>
            <img src="http://pic.lvmama.com/opi/816zhengshu1.jpg" width="93" height="114" alt="证书">
            <span>点击查看大图</span>
          </a>
        </p>
      </div>
      <!--lv_reason end--> </div>
    <!--mainTop end-->
    <!--长隆门票开始-->
    <h3 class="proList_nav nav_menpiao" id="menpiao">长隆门票</h3>
    <div class="product pro_menpiao">
      <div class="pro_list pro_gp">
        <div class="proDescripCon">
          <@s.iterator value="map.get('${station}_clmpbiaoti')" status="st">
            <@s.if test="#st.isFirst()">
              <@s.iterator value="map.get('${station}_clmp_${st.index}')" status="sts">
                <div class="proDescrip">
                  <img class="smallPic" src="${imgUrl?if_exists}" width="218" height="113">
                  <h4>${title?if_exists}</h4>
                  <dl>
                    <dt>
                      <@s.if test="null != bakWord2 && '' != bakWord2">${bakWord2?if_exists}</@s.if>
                    </dt>
                    <dd>
                      <a href="${url?if_exists}" class="toSee">去看看>></a>
                      <i>¥</i> <em>${memberPrice?if_exists?replace(".0","")}</em>
                      起
                    </dd>
                  </dl>
                </div>
              </@s.iterator>
              <a href="<@s.if test="null != bakWord3 && '' != bakWord3">${bakWord3?if_exists}</@s.if>
              " class="mp_more">更多广州门票&gt;&gt;
            </a>
          </@s.if>
        </@s.iterator>
      </div>
      <div class="pro_rt">
        <!--<ul class="mpTab" id="mpTab" style="width:650px;">
	  	<li class="mpTabSelected">
            <a class="tabli1">长隆水上乐园</a>
          </li>
          <li class="mpTabSelected">
            <a class="tabli1">长隆欢乐世界</a>
          </li>
          <li>
            <a class="tabli2">长隆国际大马戏</a>
          </li>
          <li>
            <a class="tabli3">香江野生动物世界</a>
          </li>
          <li>
            <a class="tabli4">广州鳄鱼公园</a>
          </li>
        </ul>-->
        <ul class="mpTab" id="mpTab" style="width:650px;">
	 	  <li class="mpTabSelected">
            <a class="tabli1">长隆水上乐园</a>
          </li>
          <li>
            <a class="tabli2">长隆欢乐世界</a>
          </li>
          <li>
            <a class="tabli3">长隆国际大马戏</a>
          </li>
          <li>
            <a class="tabli4">香江野生动物世界</a>
          </li>
          <li>
            <a class="tabli5">广州鳄鱼公园</a>
          </li>
          
        </ul>
        <div id="mpTab_con">
          <@s.iterator value="map.get('${station}_clmpbiaoti')" status="st">
            <@s.if test="!#st.isFirst()">
              <@s.if test="${st.index}==1">
                <ul class="gty dis" style="display: block;">
                  <@s.iterator value="map.get('${station}_clmp_${st.index}')" status="sts">
                    <li>
                      <div  class="gty_lt">
                        <a href="${url?if_exists}">${title?if_exists}</a>
                        <p>${remark?if_exists}</p>
                      </div>
                      <p class="gty_rt">
                        <span> <font>¥ <em>${memberPrice?if_exists?replace(".0","")}</em></font> 
                          起
                        </span>
                        <a href="${url?if_exists}"></a>
                      </p>
                    </li>
                  </@s.iterator>
                  <li class="last_li">
                    <a href="${url?if_exists}" class="more">更多&gt;&gt;</a>
                  </li>
                </ul>
              </@s.if>
              <@s.else>
                <ul class="gty dis" style="display: none;">
                  <@s.iterator value="map.get('${station}_clmp_${st.index}')" status="sts">
                    <li>
                      <div  class="gty_lt">
                        <a href="${url?if_exists}">${title?if_exists}</a>
                        <p>${remark?if_exists}</p>
                      </div>
                      <p class="gty_rt">
                        <span> <font>¥
                            <em>${memberPrice?if_exists?replace(".0","")}</em></font> 
                          起
                        </span>
                        <a href="${url?if_exists}"></a>
                      </p>
                    </li>
                  </@s.iterator>
                  <li class="last_li">
                    <a href="${url?if_exists}" class="more">更多&gt;&gt;</a>
                  </li>
                </ul>
              </@s.else>
            </@s.if>
          </@s.iterator>

        </div>
        <!--tab_con end--> </div>
    </div>
    <!--pro_list end-->
    <div class="pro_mp_botbg"></div>
  </div>
  <!--product end-->
  <!--长隆自由行开始-->
  <h3 class="proList_nav nav_ziyouxing" id="ziyouxing">长隆自由行</h3>
  <div class="product pro_ziyouxing">
    <div class="pro_list">
      <div class="pro_lt">
        <@s.iterator value="map.get('${station}_clzyxbiaoti')" status="st">
          <dl>
            <dt>
              <span>
                <i></i>
                <em>${title?if_exists}</em>
                <i  class="icon_rt"></i>
              </span>
              <a href="${url?if_exists}" class="more">更多&gt;&gt;</a>
            </dt>
            <@s.iterator value="map.get('${station}_clzyx_${st.index + 1}')" status="sts">
              <@s.if test="#sts.isFirst()">
                <dd class="pro_img">
                  <a  class="pro_link" href="${url?if_exists}">
                    <img src="${imgUrl?if_exists}" width="190" height="108" alt="" />
                  </a>
                  <div class="pro_text">
                    <a href="${url?if_exists}"> <b>${title?if_exists}</b>
                      <span class="share tuijian"></span>
                    </a>
                    <p class="padd_val">
                      ◎
                      <@s.if test="null != bakWord1 && '' != bakWord1">${bakWord1?if_exists}</@s.if>
                    </p>
                    <p>
                      ◎
                      <@s.if test="null != bakWord2 && '' != bakWord2">${bakWord2?if_exists}</@s.if>
                    </p>
                    <p class="padd_val">
                      <font>
                        ¥
                        <em>${memberPrice?if_exists?replace(".0","")}</em>
                      </font>
                      <a href="${url?if_exists}"></a>
                    </p>
                  </div>
                </dd>
              </@s.if>
              <@s.else>
                <dd class="pro_desc">
                  <div>
                    <a href="${url?if_exists}">${title?if_exists}</a>
                    <p>${remark?if_exists}</p>
                  </div>
                  <p class="desc_price">
                    <font>
                      ¥
                      <em>${memberPrice?if_exists?replace(".0","")}</em>
                    </font>
                    起
                  </p>
                </dd>
              </@s.else>
            </@s.iterator>
          </dl>
        </@s.iterator>
      </div>
      <!--pro_lt end-->
      <div class="pro_rt" id="big_img">
        <div class="small_img">
          <p class="line">【长隆交通地图】</p>
          <p class="posi">
            <img src="http://pic.lvmama.com/img/zt/changlong/small01.jpg" width="263" height="309" alt="交通指南" />
            <a class="big_img">点击查看大图</a>
          </p>
          <a href="http://www.lvmama.com/guide/2012/0711-163907.html" target="_blank">汉溪长隆站E出口距长隆水上乐园、马戏、动物园、欢乐世界不到1千米，景区提供免费穿梭巴士往返地铁长隆站E出口。</a>
        </div>
        <div class="small_img">
          <p class="line">【长隆全景地图】</p>
          <p class="posi">
            <img src="http://pic.lvmama.com/img/zt/changlong/small02.jpg" width="259" height="346" alt="交通指南" />
            <a class="big_img">点击查看大图</a>
          </p>
          <a href="http://www.lvmama.com/guide/2012/0710-163899.html" target="_blank">最新、最齐全的长隆度假区游玩攻略！想用最少的时间收获最多？快进来看看吧！</a>
        </div>
      </div>
      <!--pro_rt end--> </div>
    <!--pro_list end-->
    <div class="pro_zyx_botbg"></div>
  </div>
  <!--product end-->
  <!--长隆酒店开始-->
  <h3 class="proList_nav nav_jiudian" id="jiudian">长隆酒店产品</h3>
  <div class="product pro_jiudian">
    <div class="pro_list pro_hotel">
      <div class="pro_lt">
        <@s.iterator value="map.get('${station}_clhbiaoti')" status="st">
          <dl>
            <dt>
              <span>
                <i></i>
                <em>${title?if_exists}</em>
                <i  class="icon_rt"></i>
              </span>
              <a class="more" href="${url?if_exists}">更多&gt;&gt;</a>
            </dt>
            <dd>
              <@s.iterator value="map.get('${station}_clh_${st.index + 1}')" status="sts">
                <div class="hotel_box">
                  <a class="hotel_img" href="${url?if_exists}">
                    <img src="${imgUrl?if_exists}" width="120" height="61"  alt="" />
                  </a>
                  <p>
                    <a href="${url?if_exists}">${title?if_exists}</a>
                    <span class="xin xin3">
                      <@s.if test="null != bakWord2 && '' != bakWord2">${bakWord2?if_exists}</@s.if>
                    </span>
                  </p>
                  <p>${remark?if_exists}</p>
                  <p class="hotel_price">
                    <font>
                      ¥
                      <em>${memberPrice?if_exists?replace(".0","")}</em>
                    </font>
                    起
                  </p>
                </div>
              </@s.iterator>
            </dd>
          </dl>
        </@s.iterator>

      </div>
      <div class="pro_rt">
        <p class="line">【长隆周边住宿全攻略】</p>
        <a href="http://www.lvmama.com/guide/2012/0711-163908.html" target="_blank" class="hotelPicDescri">
          <img height="144" width="254" src="http://pic.lvmama.com/img/zt/changlong/jiudianYOUshangru.jpg" alt="婺源住宿" />
        </a>
        <dl class="kezhan">
          <dt> <b>广州的星级宾馆和酒店上百家，非常便利，应有尽有。普遍费用偏高，中档一般都在200元/天，但在广州的住宿有极大的选择余地，从高档的星级酒店至中低档的各类招待所、青年求职旅社等，都可以根据个人的要求灵活掌握。
              <a href="http://www.lvmama.com/guide/2012/0711-163908.html" target="_blank">［详情］</a>
            </dd>
          </dl>
          <p class="line line_border">【长隆住宿全攻略】</p>
          <a href="http://www.lvmama.com/guide/2012/0711-163908.html" target="_blank" class="hotelPicDescri">
            <img height="144" width="254"  src="http://pic.lvmama.com/img/zt/changlong/jiudianYOUxiatu.jpg" alt="" />
          </a>
          <dl class="kezhan kezhan2">
            <dt>
              <b>NO.1 长隆酒店</b>
            </dt>
            酒店首创动物中庭和动物岛，引入雪虎、火烈鸟等珍稀动物。宾客入住酒店，即可与白虎共餐，与仙鹤共赏星夜，享受快速通道畅游长隆五大乐园！
          </dd>
          <a href="http://www.lvmama.com/guide/2012/0711-163908.html" target="_blank">［详情］</a>
        </dd>
      </dl>
    </div>

  </div>
  <!--pro_hotel end-->
  <div class="pro_jd_botbg"></div>
</div>
<!--product end-->
<!--长隆跟团游开始-->
<h3 class="proList_nav nav_gentuanyou" id="gentuanyou">长隆跟团游</h3>
<div class="product pro_gentuanyou">
  <div class="pro_list pro_gp">
    <div class="proDescripCon">
      <div class="proDescrip">
        <@s.iterator value="map.get('${station}_clgtbiaoti')" status="st">
          <@s.if test="#st.isFirst()">
            <@s.iterator value="map.get('${station}_clgt_${st.index + 1}')" status="sts">
              <img class="smallPic" src="${imgUrl?if_exists}" width="218" height="113">
              <h4>${title?if_exists}</h4>
              <dl>
                <dt>${remark?if_exists}</dt>
                <dd>
                  <a href="${url?if_exists}" class="toSee">去看看>></a>
                  <i>¥</i>
                  <em>${memberPrice?if_exists?replace(".0","")}</em>
                  起
                </dd>
              </dl>
            </@s.iterator>
          </@s.if>
        </@s.iterator>
      </div>
    </div>
    <div class="pro_rt"> <strong class="tyou">特色跟团游</strong>
      <ul class="tab" id="gtyTab">
        <@s.iterator value="map.get('${station}_clgtbiaoti')" status="st">
          <@s.if test="!#st.isFirst()">
            <@s.if test="${st.index}==1">
              <li class="cur">${title?if_exists}</li>
            </@s.if>
            <@s.else>
              <li>${title?if_exists}</li>
            </@s.else>
          </@s.if>
        </@s.iterator>
      </ul>
      <div id="gtyTab_con">
        <@s.iterator value="map.get('${station}_clgtbiaoti')" status="st">
          <@s.if test="!#st.isFirst()">
            <@s.if test="${st.index}==1">
              <ul class="gty dis" style="display: block; ">
                <@s.iterator value="map.get('${station}_clgt_${st.index + 1}')" status="sts">
                  <li>
                    <div  class="gty_lt">
                      <a href="${url?if_exists}">${title?if_exists}</a>
                      <p>${remark?if_exists}</p>
                    </div>
                    <p class="gty_rt">
                      <span>
                        <font>
                          ¥
                          <em>${memberPrice?if_exists?replace(".0","")}</em>
                        </font>
                        起
                      </span>
                      <a href="${url?if_exists}"></a>
                    </p>
                  </li>
                </@s.iterator>
                <li class="last_li">
                  <a href="${url?if_exists}" class="more">更多&gt;&gt;</a>
                </li>
              </ul>
            </@s.if>
            <@s.else>
              <ul class="gty dis" style="display: none; ">
                <@s.iterator value="map.get('${station}_clgt_${st.index + 1}')" status="sts">
                  <li>
                    <div  class="gty_lt">
                      <a href="${url?if_exists}">${title?if_exists}</a>
                      <p>${remark?if_exists}</p>
                    </div>
                    <p class="gty_rt">
                      <span>
                        <font>
                          ¥
                          <em>${memberPrice?if_exists?replace(".0","")}</em>
                        </font>
                        起
                      </span>
                      <a href="${url?if_exists}"></a>
                    </p>
                  </li>
                </@s.iterator>
                <li class="last_li">
                  <a href="${url?if_exists}" class="more">更多&gt;&gt;</a>
                </li>
              </ul>
            </@s.else>
          </@s.if>
        </@s.iterator>
      </div>
      <!--tab_con end--> </div>
  </div>
  <!--pro_list end-->
  <div class="pro_gty_botbg"></div>
</div>
<!--product end-->
<!--热门活动正在进行中-->
<h4 class="hotAct_tit" id="huodong">热门活动正在进行</h4>
<div class="hotAct_pic">
  <p>
    <a href="http://www.lvmama.com/zt/lvyou/hqc">
      <img src="http://www.lvmama.com/zt/promo/gzcl/images/huodong1.jpg" width="285" height="135" alt="热门活动1"></a>
    <a href="http://www.lvmama.com/zt/lvyou/fjd">
      <img src="http://www.lvmama.com/zt/promo/gzcl/images/huodong2.jpg" width="285" height="135" alt="热门活动2"></a>
    <a href="http://www.lvmama.com/dest/guangdong_guangdongqingyuan/freeness_tab">
      <img src="http://www.lvmama.com/zt/promo/gzcl/images/huodong3.jpg" width="285" height="135" alt="热门活动3"></a>
  </p>
</div>
</div>
<!--page end-->

<div class="bg_container">
<div class="wrapbg1"></div>
<div class="wrapbg2"></div>
</div>
<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
</div>
<!--wraper end-->
<div id="ban_bg"></div>
<!--查看证书-->
<div id="honour" class="big_pic">
<a class="close"></a>
<img src="http://pic.lvmama.com/img/zt/changlong/youxiu.jpg" width="600" height="792" alt="证书1" />
<img src="http://pic.lvmama.com/opi/816changlongyou.jpg" width="600" height="792" alt="证书2" />
</div>
<!--查看地图-->
<div id="big_pic" class="big_pic">
<a class="close"></a>
<img src="http://pic.lvmama.com/img/zt/changlong/bigpic1.jpg" width="913" height="537" alt="交通地图" />
<img src="http://pic.lvmama.com/img/zt/changlong/bigpic2.jpg" width="800" height="592" alt="交通地图" />
</div>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/changlong/jquery.imgSlide.js"></script>
<script type="text/javascript">
    $(function(){
      /*图片切换代码*/
      $("#slide").imgSlide();
      var _height=$(document).height();
      if(!window.XMLHttpRequest){
        $("#ban_bg").css({"position":"absolute","top":0,"height":_height});
        $(".bg_container").height(_height);
        $(".wrapbg2").css("display","block");
        }
      $("#gtyTab li").bind("mouseover",function(){
        var index=$(this).index(); 
        tab("#gtyTab li","#gtyTab_con .gty","cur",index)
      });
      $("#mpTab li").bind("mouseover",function(){
        var index=$(this).index(); 
        tab("#mpTab li","#mpTab_con .gty","mpTabSelected",index)
      });
      //点击关闭
      $(".close").click(function(){
        $(this).parent().hide();
        $("#ban_bg").hide();
      });
      /*点击查看大图*/
      showBigPic("#big_img .big_img","#big_pic","#ban_bg");
      showBigPic("#reason_img a","#honour","#ban_bg");
    });
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>