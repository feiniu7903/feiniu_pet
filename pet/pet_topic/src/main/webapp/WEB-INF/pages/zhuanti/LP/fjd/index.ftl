<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>茂名放鸡岛旅游_放鸡岛旅游攻略_放鸡岛在哪里-驴妈妈旅游网</title>
  <meta name="Keywords" content="茂名放鸡岛旅游,放鸡岛旅游攻略">
  <meta name="Description" content="海岛度假何必东奔西跑,放鸡岛一样都不少!驴妈妈旅游网茂名放鸡岛旅游,带你去体验广东最好的潜水基地-放鸡岛,远离了都市的喧吵,抛弃了工作的烦恼,茶余饭后,或坐在半山休息小亭,或躺在林间摇摇床上,吹着清凉的海风,听着下面娱乐广场飘来的歌声,远眺波澜壮">
  <link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
  <link href="http://pic.lvmama.com/styles/common.css" rel="stylesheet" type="text/css" />
  <link href="http://pic.lvmama.com/styles/zt/fjd/css.css" rel="stylesheet" type="text/css" />
  <base target="_blank" />
</head>

<body class="fjd_body">
  <!--header-->
  <script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
  <!--header-->
  <div class="fjd_wrap">
    <div class="fjd_head"></div>
    <div class="fjd_head1"></div>
    <div class="fjd_cont_bg1">
      <div class="fjd_cont1">
        <div class="fjd_decr_l"></div>
        <div class="fjd_decr_r"></div>
        <div class="fjd_decr_img"></div>
        <div class="fjd_box1  wrapfix">
          <div class="fjd_box1_left">
            <@s.iterator value="map.get('${station}_gztj')" status="st">
              <div class="fjd_pro_bg1">
                <a href="${bakWord6?if_exists}" target="_blank">
                  <img src="${imgUrl?if_exists}" width="402" height="252" alt=""/>
                </a>
                <div class="fjd_pro1_price"> <del>原价：${marketPrice?if_exists?replace(".0","")}元</del> <em>团购价： <b>${memberPrice?if_exists?replace(".0","")}</b>
                    元</em> 
                </div>
              </div>
              <!--fjd_pro_bg1-->
              <p class="fjd_pro1_txt"> <em>【${bakWord2?if_exists}】</em>
                ${remark?if_exists}
                <a href="${bakWord6?if_exists}" target="_blank">[查看详情]</a>
              </p>
            </@s.iterator>
          </div>
          <!--box1left-->

          <div class="fjd_box1_r">
            <div class="">
              <h3 class="fjd_tit1">『茂名放鸡岛全攻略』</h3>
            </div>
            <p class="fjd_p1">
              海岛总是充满着太多浪漫的元素，如果你经常幻想着在长滩岛、夏威夷或者马尔代夫邂逅浪漫故事，那还不如花一、两天时间，踏足放鸡岛，感受质朴和优雅自在的浪漫。这里的蓝天、碧海、清风、沙滩……在微风徐徐吹过的曼妙天籁中，享受到真正不受干扰的浪漫度假感觉。
            </p>
            <h4 class="fjd_tit2">畅游放鸡岛的五大理由</h4>
            <ul class="fjd_box1_list">
              <li>★踏足茂名放鸡岛，享受一种优雅自在</li>
              <li>★零距离地与珊瑚接触，与斑斓多彩的热带鱼儿四目相对</li>
              <li>★世界公认的潜水胜地，能见度高达8米，堪称广东至佳潜水胜地</li>
              <li>★岛景自然风光秀丽，天然景物奇异多姿，人在丛林，听潮观浪</li>
              <li>★海上天堂潜水圣地，各项水上游玩项目乐趣无穷</li>
            </ul>
            <div class="fjd_btn_box">
              <@s.iterator value="map.get('${station}_gztuijian')" status="st">
                <a href="${url?if_exists}" target="_blank" class="fjd_btn"></a>
                ${title?if_exists} <b>¥${memberPrice?if_exists?replace(".0","")}</b>
              </@s.iterator>
            </div>
          </div>
          <!--box1_r--> </div>
        <!--box1-->

        <div class="fjd_titbox">特色自由行</div>
        <div class="fjd_box2 wrapfix">
          <div class="fjd_box2_left">
            <@s.iterator value="map.get('${station}_gzzyxbiaoti')" status="st">
              <h4 class="fjd_box2_tit">
                <span>【${title?if_exists}】</span>
              </h4>
              <ul class="fjd_box2_list">
                <@s.iterator value="map.get('${station}_gzzyxfjd_${st.index+1}')" status="st2">
                  <li class="fjd_box2_li1 wrapfix">
                    <div class="fjd_box2_txt">
                      <a href="${url?if_exists}" target="_blank" class="fjd_box2_txt_tit">${title?if_exists}</a>
                      <em>◎ ${remark?if_exists}</em>
                      <em>◎ ${bakWord3?if_exists}</em>
                      <div class="fjd_yd_box1">
                        <span class="fjd_price3">
                          ¥
                          <b>${memberPrice?if_exists?replace(".0","")}</b>
                        </span>
                        起
                        <a href="${url?if_exists}" target="_blank" class="fjd_btn_yd2"></a>
                      </div>
                    </div>
                    <!--r-->
                    <a href="${url?if_exists}" target="_blank" class="fjd_box2_img">
                      <img src="${imgUrl?if_exists}" width="190"  height="108" alt=""/>
                    </a>
                  </li>
                </@s.iterator>
                <@s.iterator value="map.get('${station}_gzzyxfjdt_${st.index+1}')" status="st3">
                  <li>
                    <span class="fjd_box2list_l">
                      <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
                      <em>${remark?if_exists}</em>
                    </span>
                    <span class="fjd_box2list_r">
                      <em class="fjd_price2">¥
                        <b>${memberPrice?if_exists?replace(".0","")}</b></em> 
                      起
                    </span>
                  </li>
                </@s.iterator>
              </ul>
              <!--list1-->
              <p class="fjd_more_box">
                <a href="${url?if_exists}"></a>
              </p>
            </@s.iterator>

          </div>
          <!--left-->
          <div class="fjd_box2_r">
            <div class="fjd_box2_r_unit">
              <h4 class="fjd_tit3">【放鸡岛全景地图】</h4>
              <div class="fjd_map">
                <a href="http://www.lvmama.com/dest/maomingfangjidao#traffic" target="_blank" class="fjd_box2_r_img">
                  <img src="http://pic.lvmama.com/img/zt/fjd/pro2.jpg" width="261" height="311"/>
                </a>
                <div class="fjd_btn2" id="fjd_bmap_btn1">点击查看大图</div>
              </div>
              <p class="fjd_box2_r_unit_txt">
                放鸡岛（原名湾舟岛，又名汾洲岛）位于电白县水东镇东南14.5公里。原名汾洲山，又称湾舟山。岛上最高点高122米。面积1.9平方公里，是该县最大的海岛。
              </p>
            </div>
            <!--unit-->
            <div class="fjd_box2_r_unit">
              <h4 class="fjd_tit3">【放鸡岛全景地图】</h4>
              <div class="fjd_map">
                <a href="http://www.lvmama.com/dest/maomingfangjidao#traffic" target="_blank" class="fjd_box2_r_img">
                  <img src="http://pic.lvmama.com/img/zt/fjd/pro3.jpg"  width="259" height="346"/>
                </a>
                <div class="fjd_btn2" id="fjd_bmap_btn2">点击查看大图</div>
              </div>
              <p class="fjd_box2_r_unit_txt">
                广州汽车站和省站都有到电白的直达车，行程约在3个半小时左右，电白有很中巴车到博贺镇，招手就停。到了博贺可以打摩的到渔港码头。
              </p>
            </div>
            <!--unit--> </div>
          <!--r--> </div>
        <!--box2-->

        <div class="fjd_titbox">特色跟团游</div>
        <div class="fjd_box3">
          <div class="fjd_box3_l">
            <h4 class="fjd_tit3">【茂名放鸡岛全攻略】</h4>
            <div class="fjd_proimg_bg1">
              <a href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904.html" target="_blank">
                <img src="http://pic.lvmama.com/img/zt/fjd/pro4.jpg" width="254" height="144" alt="" />
              </a>
            </div>
            <p class="fjd_p2">
              放鸡岛是一个浑身充满奇妙色彩的海岛，从白天到黑夜都让人兴奋不已。因为白天可以学习潜水，深夜还可以玩“海捞”。在放鸡岛，不需要“海钓”，而只需要用一个大“网勺”乘船出海去捞鱼，那种感觉就像在自家的鱼塘里面自由自在地捞鱼一样…
              <a href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904.html" target="_blank">[详情]</a>
            </p>
          </div>
          <!--l-->

          <div class="fjd_box3_r">
            <div class="fjd_tab_bg wrapfix">
              <em>特色跟团游</em>
              <ul class="fjd_tab_tit wrapfix">
                <@s.iterator value="map.get('${station}_gzgtybiaoti')" status="st">
                  <li>${title?if_exists}</li>
                </@s.iterator>
              </ul>
            </div>
            <!--tab-->

            <div class="fjd_tab_cont">
              <@s.iterator value="map.get('${station}_gzgtyfjd_1')" status="st">
                <ul class="fjd_tabcont_list">
                  <li class="fjd_tabcont_list1">
                    <a href="${url?if_exists}" target="_blank" class="fjd_titlink">${title?if_exists}</a>
                    <span>${remark?if_exists}</span>
                  </li>
                  <li class="fjd_tabcont_list2">
                    <em class="fjd_price2">
                      ¥
                      <b>${memberPrice?if_exists?replace(".0","")}</b>
                    </em>
                    起
                  </li>
                  <li class="fjd_tabcont_list3">
                    <a class="fjd_btn_yd2" target="_blank" href="${url?if_exists}"></a>
                  </li>
                </ul>
              </@s.iterator>
              <p class="fjd_more_box">
                <a href="http://www.lvmama.com/search/around-from-%E5%B9%BF%E5%B7%9E-to-%E8%8C%82%E5%90%8D%E6%94%BE%E9%B8%A1%E5%B2%9B-group.html"></a>
              </p>
            </div>
            <!--tabcont-->

            <!--tabcont--> </div>
          <!--r--> </div>
        <!--box3-->

        <div class="fjd_titbox">放鸡岛门票</div>
        <div class="fjd_box3">
          <div class="fjd_box3_l">
            <h4 class="fjd_tit3">【茂名放鸡岛全攻略】</h4>
            <div class="fjd_proimg_bg1">
              <a href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904.html" target="_blank">
                <img src="http://pic.lvmama.com/img/zt/fjd/pro5.jpg" width="254" height="144" alt="" />
              </a>
            </div>
            <p class="fjd_p2">
              放鸡岛上有一个8万平方米海域的潜水池，里面放养了5万条各种海生鱼类。配备了300位教练可供300名游客同时潜水，这个海上观光潜水池是全国和亚洲最大的潜水基地...
              <a href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904.html" target="_blank">[详情]</a>
            </p>
            <div class="fjd_box3_l_line"></div>
            <h4 class="fjd_tit3">【茂名放鸡岛全攻略】</h4>
            <div class="fjd_proimg_bg1">
              <a href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904.html" target="_blank">
                <img src="http://pic.lvmama.com/img/zt/fjd/pro6.jpg" width="254" height="144" alt="" />
              </a>
            </div>
            <p class="fjd_p2">
              最受发烧友喜欢的自然是放鸡岛的探险潜。这可是其他国家难以实现的“寻宝探险”之旅...
              <a href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904.html" target="_blank">[详情]</a>
            </p>
          </div>
          <!--l-->
          <div class="fjd_box3_r">
            <ul class="fjd_prolist">
              <@s.iterator value="map.get('${station}_gzmpfjd')" status="st">
                <li>
                  <a href="${url?if_exists}" target="_blank">
                    <img src="${imgUrl?if_exists}" width="201" height="112" />
                  </a>
                  <div class="fjd_prolist_tit">${title?if_exists}</div>
                  <div class="fjd_prolist_price">
                    <span class="fjd_price3">
                      ¥
                      <b>${memberPrice?if_exists?replace(".0","")}</b>
                    </span>
                    起
                    <a class="fjd_btn_yd2" target="_blank" href="${url?if_exists}"></a>
                  </div>
                </li>
              </@s.iterator>
            </ul>
            <p class="fjd_more_box">
              <a href="http://www.lvmama.com/dest/maomingfangjidao"></a>
            </p>
          </div>
          <!--r--> </div>
        <!--box4-->

        <div class="fjd_titbox">特色精品酒店</div>
        <div class="fjd_box2 wrapfix">
          <div class="fjd_box2_left">
            <@s.iterator value="map.get('${station}_gzjdbiaoti')" status="st">
              <h4 class="fjd_box2_tit">
                <span>【${title?if_exists}】</span>
              </h4>
              <ul class="fjd_box2_list">
                <@s.iterator value="map.get('${station}_gzjdfjd_${st.index+1}')" status="st2">
                  <li class="fjd_box2_li1 wrapfix">
                    <div class="fjd_box2_txt">
                      <a href="${url?if_exists}" target="_blank" class="fjd_box2_txt_tit">${title?if_exists}</a>
                      <em>${bakWord2?if_exists}</em>
                      <em>◎ ${bakWord10?if_exists}</em>
                      <div class="fjd_yd_box1">
                        <span class="fjd_price3">
                          ¥
                          <b>${memberPrice?if_exists?replace(".0","")}</b>
                        </span>
                        起
                        <a href="${url?if_exists}" target="_blank" class="fjd_btn_yd2"></a>
                      </div>
                    </div>
                    <!--r-->
                    <a href="${url?if_exists}" target="_blank" class="fjd_box2_img">
                      <img src="${imgUrl?if_exists}" width="190"  height="108" alt=""/>
                    </a>
                  </li>
                </@s.iterator>
              </ul>
              <p class="fjd_more_box">
                <a href="${url?if_exists}"></a>
              </p>
            </@s.iterator>

          </div>
          <!--left-->
          <div class="fjd_box2_r">
            <div class="fjd_box2_r_unit1">
              <h4 class="fjd_tit3">【独栋别墅】</h4>
              <div class="fjd_proimg_bg1">
                <a target="_blank" href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904-2.html">
                  <img width="254" height="144" alt="" src="http://pic.lvmama.com/opi/dudongbieshu.jpg" />
                </a>
              </div>
              <p class="fjd_box2_r_unit_txt1">
                小别墅座落于放鸡岛中间的山坡上，与大海一线之隔。是看景、聆听鸟类鸣声的最佳之处。从房间阳台上俯瞰细幼的沙滩碧蓝的大海，美丽的景色尽收眼底。套房内部如梦如幻的星空彩绘，配套设施齐全。
                <a target="_blank" href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904-2.html">［详情］</a>
              </p>
            </div>
            <!--unit-->

            <div class="fjd_box2_r_unit1 fjd_border_top">
              <h4 class="fjd_tit3">【凤凰别墅】</h4>
              <div class="fjd_proimg_bg1">
                <a target="_blank" href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904-2.html">
                  <img width="254" height="144" alt="" src="http://pic.lvmama.com/opi/fenghuangbieshu.jpg" />
                </a>
              </div>
              <p class="fjd_box2_r_unit_txt1">101幢拥有纯木结构建造而成，异域风情的凤凰别墅沿山而建，依山傍海，站在阳台上一览无际的大海。</b>
              <a target="_blank" href="http://www.lvmama.com/info/chinagonglue/2012-0508-78904-2.html">［详情］</a>
            </p>
          </div>
          <!--unit--> </div>
        <!--r--> </div>
      <!--box5-->

      <div class="fjd_link">
        <h4 class="fjd_tit6">热门目的地</h4>
        <div class="fjd_link_list">
          <a target="_blank" href="http://www.lvmama.com/dest/guangdong_guangdongqingyuan/freeness_tab">
            <img src="http://pic.lvmama.com/opi/qywq1205.jpg"  width="284" height="135" alt="" />
          </a>
          <a target="_blank" href="http://www.lvmama.com/search/freetourSearch!freetourSearch.do?toDest=%E8%82%87%E5%BA%86&routeType=freeness">
            <img src="http://pic.lvmama.com/opi/qiqing1205.jpg"  width="284" height="135" alt="" />
          </a>
          <a target="_blank" href="http://www.lvmama.com/tuangou">
            <img src="http://pic.lvmama.com/opi/tjtg1205.jpg"  width="284" height="135" alt="" />
          </a>
        </div>
      </div>
      <img src="http://pic.lvmama.com/img/zt/fjd/bg_f.jpg" width="1028" height="15"  class="fjd_f"/>
    </div>
    <!--content1--> </div>
  <!--content_bg1-->
  <!--footer-->
  <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
  <!--footer-->
</div>
<!--wrap-->
<div id="pageOverlay"></div>
<!--pop1-->
<div class="fjd_pop_map fjd_map1">
  <div class="fjd_pop_close"></div>
  <img src="http://pic.lvmama.com/img/zt/fjd/fjd_bigmap2.jpg" width="600"  alt="" />
</div>
<!--pop1-->
<!--pop2-->
<div class="fjd_pop_map fjd_map2">
  <div class="fjd_pop_close"></div>
  <img src="http://pic.lvmama.com/img/zt/fjd/fjd_bigmap1.jpg" width="600"  alt="" />
</div>
<!--pop2-->
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/fjd/common.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"  type="text/javascript"></script>
</body>
</html>