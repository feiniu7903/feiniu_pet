<!doctype html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>西部旅游_西部旅游去哪好_西部旅游必去景点有哪些-驴妈妈旅游网</title>
    <meta name="Keywords" content="西部旅游,西部旅游去哪好">
    <meta name="Description" content="穿越大西部,触摸梦想的高度,驴妈妈旅游网推出西部穿越活动,为你推荐西部旅游去哪好,为你介绍西部旅游必去景点有哪些,告诉你西部旅游哪里最好玩,带给你一个不一样的旅程!">
    <link href="http://pic.lvmama.com/styles/zt/dxb/index.css" rel="stylesheet" type="text/css">
    <link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js" ></script>
    <script type="text/javascript">
   DD_belatedPNG.fix('.city_prod_tujian li .icon_post img,.city_prod_list,.cfd_nav');
</script>
    <![endif]-->
</head>

<body>
    <!--header-->
    <script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
    <!--header-->
    <div class="wapper_head">
        <!--header_ s-->
        <div class="head_bg">
            <ul class="head_nav">
                <li class="yl_list">
                    <a href="#yl">惊艳云南</a>
                </li>
                <li class="jz_list">
                    <a href="#jz">天堂九寨</a>
                </li>
                <li class="xy_list">
                    <a href="#xz">雪域西藏</a>
                </li>
                <li class="xj_list cur">
                    <a href="#xj">旖旎新疆</a>
                </li>
                <li class="lm_list">
                    <a href="#lm">豪情内蒙</a>
                </li>
                <li class="sc_list">
                    <a href="#sc">丝绸之路</a>
                </li>
                <li class="ca_list">
                    <a href="#ca">古都长安</a>
                </li>
                <li class="sx_list">
                    <a href="#sx">诗画三峡</a>
                </li>

                <li class="gz_list">
                    <a href="#gz">多情贵州</a>
                </li>

            </ul>
	    <dl class="cfd_nav">
            	<dt>出发地</dt>
                <dd><a class="nav_dd1" target="_self" href="http://www.lvmama.com/zt/lvyou/xibu">上海</a></dd>
                <dd><a class="nav_dd2" target="_self" href="http://www.lvmama.com/zt/lvyou/xibu/bj.html">北京</a></dd>
                <dd class="nav_click"><a class="nav_dd3" target="_self" href="http://www.lvmama.com/zt/lvyou/daxibu">广州</a></dd>
            </dl>
        </div>
        <!--header_ e--> </div>
    <!--contant_ s-->
    <div class="xz_wpper">
        <div class="through_lab p_rel">
            <div class="thr_info fl">
                <h2>穿越系列</h2>
                <p>
                    中国的大西部，一片神秘而美丽的净土。五色的海子，苍茫的雪山，浩瀚的草原，无边的沙漠...一切景象都是每个人都曾有过的梦境。为了心中的那份悸动，人生少留下一些遗憾，驴妈妈带您穿越最美大西部。
                </p>
                <a href="#">
                    <img alt="" src="http://pic.lvmama.com/opi/xibu11.jpg"></a>
            </div>
            <!--产品 s-->
            <div class="thr_prod fl">
                <ul class="thr_list">
                    <@s.iterator value="map.get('${station}_gzchuanyue')">
                        <li>
                            <span class="prod_info">
                                <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                            </span>
                            <p class="prod_text">${remark?if_exists}</p>

                            <span class="prod_price"> <del>¥${marketPrice?if_exists?replace(".0","")}</del> <em>¥</em>
                                ${memberPrice?if_exists?replace(".0","")} <em>起</em>
                            </span>
                            <a href="${url?if_exists}" class="book_bt ft">立即预订</a>
                        </li>
                    </@s.iterator>
                </ul>
            </div>
            <!--产品 e--> </div>

        <div class="city_prod_contant clearfix">
            <ul class="city_prod_list">
                <li id="yl">云南</li>
                <li id="jz">四川</li>
                <li id="xz">西藏</li>
                <li id="xj" class="cur">新疆</li>
                <li id="lm">内蒙</li>
                <li id="sc">丝路</li>
                <li id="ca">陕西</li>
                <li id="sx">三峡</li>
                <li id="gz">贵州</li>
            </ul>
            <div>
                <div class="prod_nums">
                    <div class="city_prod_main fl">
                        <ul class="city_prod_tujian mt5">
                            <@s.iterator value="map.get('${station}_gzyunnanzt')">
                                <li>
                                    <div class="pic_list fl">
                                        <a href="${url?if_exists}">
                                            <img alt="" src="${imgUrl?if_exists}"></a>
                                        <span class="icon_post">
                                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/tt_icon.png"></span>
                                    </div>
                                    <div class="tujian_info">
                                        <a href="${url?if_exists}" class="tit">${title?if_exists}</a>
                                        <p>★ ${bakWord2?if_exists}</p>
                                        <p>★ ${bakWord3?if_exists}</p>
                                        <p>★ ${remark?if_exists}</p>
                                    </div>
                                    <span class="prod_price prod_price_city">
                                        <em>¥</em>
                                        ${memberPrice?if_exists?replace(".0","")}
                                        <em>起</em>
                                    </span>
                                    <a href="${url?if_exists}" class="book_bt ft book_city">立即预订</a>
                                </li>
                            </@s.iterator>
                        </ul>

                        <@s.iterator value="map.get('${station}_gzyunnan')" status="st">
                            <h3 class="tits mt10">${title?if_exists}</h3>
                            <ul class="thr_list ciyt_list mt5">
                                <@s.iterator value="map.get('${station}_gzyunnan_${st.index+1}')" status="st1">
                                    <li>
                                        <span class="prod_info">
                                            <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                        </span>
                                        <p class="prod_text">${remark?if_exists}</p>

                                        <span class="prod_price prod_price_city01">
                                            <em>¥</em>
                                            ${memberPrice?if_exists?replace(".0","")}
                                            <em>起</em>
                                        </span>
                                        <a href="${url?if_exists}" class="book_bt ft book_city01">立即预订</a>
                                    </li>
                                </@s.iterator>
                            </ul>
                            <div class="comon">
                                <a href="${url?if_exists}" class="more_bt mt5 fr">更多>></a>
                            </div>
                        </@s.iterator>
                    </div>
                    <div class="pic_show_aside fl">
                        <div class="pic_show_list">
                            <h3>云南旅游全攻略</h3>
                            <a href="http://www.lvmama.com/info/chinagonglue/2011-1107-34549.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/yunnan1.jpg"></a>
                            <p>
                                1.印象丽江不可不知的丽江十个关键词
                                <br/>
                                我想每个人都有个丽江梦吧，说起丽江，你会想起什么？古镇，艳遇，慵懒，逃避...
                                <a href="http://www.lvmama.com/info/chinagonglue/2011-1107-34549.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/guide/2011/0215-127215.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/yunnan2.jpg"></a>
                            <p>
                                2.穷游丽江吃住行游购娱全方位省钱
                                <br/>
                                江旅游如果不是口袋太鼓的朋友建议去新城吃饭，古城里面有的新城都有。什么野山菌，腊排骨...
                                <a href="http://www.lvmama.com/guide/2011/0215-127215.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/guide/2010/1101-56083-1.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/yunnan3.jpg"></a>
                            <p>
                                3.丽江艳遇攻略 ～只可意会不可言传
                                <br/>
                                丽江被称为“艳遇之都”，广义的丽江艳遇包括在丽江艳遇一景一物一人一民俗一文化时的惊艳情绪…
                                <a href="http://www.lvmama.com/guide/2010/1101-56083-1.html" target="_blank">[查看详情]</a>
                            </p>

                            <a href="http://www.lvmama.com/guide/2012/0522-163033.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/yunnan4.jpg"></a>
                            <p>
                                4.丽江周边那些沾染上丽江气息的地方
                                <br/>
                                走在白沙古镇里，感叹于恍如玩穿越的错觉，每一户人家的院墙内都会伸出几个盈盈桃枝...
                                <a href="http://www.lvmama.com/guide/2012/0522-163033.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/info/chinagonglue/2011-1108-34559.html " target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/yunnan5.jpg"></a>
                            <p>
                                5.避世的天堂流浪在丽江的古城
                                <br/>
                                很多人来到丽江，并不是为了做什么，什么不做，这就是丽江古镇，一个不必顾忌目光...
                                <a href="http://www.lvmama.com/info/chinagonglue/2011-1108-34559.html " target="_blank">[查看详情]</a>
                            </p>
                        </div>
                        <ul class="gl_list">
                            <li>
                                <div class="pic fl">
                                    <a href="http://www.lvmama.com/guide/place/yunnan_lijiang/" target="_blank">
                                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/yunnan_115x162.jpg"></a>
                                </div>
                                <div class="lianjie">
                                    <a href="http://www.lvmama.com/guide/place/yunnan_lijiang/" target="_blank">丽江官方攻略</a>
                                </div>
                                <a href="http://www.lvmama.com/guide/place/yunnan_lijiang/" class="dowm_bt">立即下载</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="prod_nums">
                    <div class="city_prod_main fl">
                        <ul class="city_prod_tujian mt5">
                            <@s.iterator value="map.get('${station}_gzsichuanzt')">
                                <li>
                                    <div class="pic_list fl">
                                        <a href="${url?if_exists}">
                                            <img alt="" src="${imgUrl?if_exists}"></a>
                                        <span class="icon_post">
                                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/tt_icon.png"></span>
                                    </div>
                                    <div class="tujian_info">
                                        <a href="${url?if_exists}" class="tit">${title?if_exists}</a>
                                        <p>★ ${bakWord2?if_exists}</p>
                                        <p>★ ${bakWord3?if_exists}</p>
                                        <p>★ ${remark?if_exists}</p>
                                    </div>
                                    <span class="prod_price prod_price_city">
                                        <em>¥</em>
                                        ${memberPrice?if_exists?replace(".0","")}
                                        <em>起</em>
                                    </span>
                                    <a href="${url?if_exists}" class="book_bt ft book_city">立即预订</a>
                                </li>
                            </@s.iterator>
                        </ul>

                        <@s.iterator value="map.get('${station}_gzsichuan')" status="st">
                            <h3 class="tits mt10">${title?if_exists}</h3>
                            <ul class="thr_list ciyt_list mt5">
                                <@s.iterator value="map.get('${station}_gzsichuan_${st.index+1}')" status="st1">
                                    <li>
                                        <span class="prod_info">
                                            <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                        </span>
                                        <p class="prod_text">${remark?if_exists}</p>

                                        <span class="prod_price prod_price_city01">
                                            <em>¥</em>
                                            ${memberPrice?if_exists?replace(".0","")}
                                            <em>起</em>
                                        </span>
                                        <a href="${url?if_exists}" class="book_bt ft book_city01">立即预订</a>
                                    </li>
                                </@s.iterator>
                            </ul>
                            <div class="comon">
                                <a href="${url?if_exists}" class="more_bt mt5 fr">更多>></a>
                            </div>
                        </@s.iterator>
                    </div>
                    <div class="pic_show_aside fl">
                        <div class="pic_show_list">
                            <h3>四川旅游全攻略</h3>
                            <a href="http://www.lvmama.com/guide/2012/0409-162169.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sichuan1.jpg"></a>
                            <p>
                                1.味道成都，只为美食走街串巷
                                <br/>
                                成都当地的美食家有语：成都无大菜。成都美食的精髓就在满街满巷的一道道小吃里...
                                <a href="http://www.lvmama.com/guide/2012/0409-162169.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/info/chinalife/2011-0602-6139.html " target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sichuan2.jpg"></a>
                            <p>
                                2.爱修行爱幽静 青城山旅游攻略
                                <br/>
                                青城山旅游攻略《功夫熊猫2》剧组对青城山的厚爱，从影片中轻易就可以看出，多次出现...
                                <a href="http://www.lvmama.com/info/chinalife/2011-0602-6139.html " target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/info/chinagonglue/2012-0508-78953.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sichuan3.jpg"></a>
                            <p>
                                3.麻辣鲜香诱惑难挡 十大火锅店
                                <br/>
                                正宗的毛肚火锅以厚味重油著称，传统汤汁选用郫县辣豆瓣…
                                <a href="http://www.lvmama.com/info/chinagonglue/2012-0508-78953.html" target="_blank">[查看详情]</a>
                            </p>

                            <a href="http://www.lvmama.com/guide/2009/1230-132651.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sichuan4.jpg"></a>
                            <p>
                                4.成都：在锦里，做一尾闲逛的鱼
                                <br/>
                                锦里，因为老成都的生活安逸美食诱人，是一座来了就不想走走了还想再来的城市...
                                <a href="http://www.lvmama.com/guide/2009/1230-132651.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/info/chinagonglue/2012-0509-79253.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sichuan5.jpg"></a>
                            <p>
                                5.最全成都美食地图 带你吃遍成都
                                <br/>
                                三万多家大小餐馆，世界各地风味美食遍布大街小巷，要想品尝到正宗美味，一张美食地图必不可少...
                                <a href="http://www.lvmama.com/info/chinagonglue/2012-0509-79253.html" target="_blank">[查看详情]</a>
                            </p>
                        </div>
                        <ul class="gl_list">
                            <li>
                                <div class="pic fl">
                                    <a href="http://www.lvmama.com/guide/place/sichuan_chengdu/" target="_blank">
                                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sixchuan_115x162.jpg"></a>
                                </div>
                                <div class="lianjie">
                                    <a href="http://www.lvmama.com/guide/place/sichuan_chengdu/" target="_blank">成都官方攻略</a>
                                </div>
                                <a href="http://www.lvmama.com/guide/place/sichuan_chengdu/" target="_blank" class="dowm_bt">立即下载</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="prod_nums">
                    <div class="city_prod_main fl">
                        <ul class="city_prod_tujian mt5">
                            <@s.iterator value="map.get('${station}_gzxizangzt')">
                                <li>
                                    <div class="pic_list fl">
                                        <a href="${url?if_exists}">
                                            <img alt="" src="${imgUrl?if_exists}"></a>
                                        <span class="icon_post">
                                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/tt_icon.png"></span>
                                    </div>
                                    <div class="tujian_info">
                                        <a href="${url?if_exists}" class="tit">${title?if_exists}</a>
                                        <p>★ ${bakWord2?if_exists}</p>
                                        <p>★ ${bakWord3?if_exists}</p>
                                        <p>★ ${remark?if_exists}</p>
                                    </div>
                                    <span class="prod_price prod_price_city">
                                        <em>¥</em>
                                        ${memberPrice?if_exists?replace(".0","")}
                                        <em>起</em>
                                    </span>
                                    <a href="${url?if_exists}" class="book_bt ft book_city">立即预订</a>
                                </li>
                            </@s.iterator>
                        </ul>

                        <@s.iterator value="map.get('${station}_gzxizang')" status="st">
                            <h3 class="tits mt10">${title?if_exists}</h3>
                            <ul class="thr_list ciyt_list mt5">
                                <@s.iterator value="map.get('${station}_gzxizang_${st.index+1}')" status="st1">
                                    <li>
                                        <span class="prod_info">
                                            <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                        </span>
                                        <p class="prod_text">${remark?if_exists}</p>

                                        <span class="prod_price prod_price_city01">
                                            <em>¥</em>
                                            ${memberPrice?if_exists?replace(".0","")}
                                            <em>起</em>
                                        </span>
                                        <a href="${url?if_exists}" class="book_bt ft book_city01">立即预订</a>
                                    </li>
                                </@s.iterator>
                            </ul>
                            <div class="comon">
                                <a href="${url?if_exists}" class="more_bt mt5 fr">更多>></a>
                            </div>
                        </@s.iterator>
                    </div>
                    <div class="pic_show_aside fl">
                        <div class="pic_show_list">
                            <h3>西藏旅游全攻略</h3>
                            <a href="http://www.lvmama.com/guide/2012/0423-162508.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xizang1.jpg"></a>
                            <p>
                                1.为什么，我们一定要去西藏？
                                <br/>
                                我们不是为了西藏而去，而仅仅只是想用血肉之躯去经历这样一场近似苦行的旅程，想要在...
                                <a href="http://www.lvmama.com/guide/2012/0423-162508.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/guide/2012/0420-162285.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xizang2.jpg"></a>
                            <p>
                                2.初次进藏需要注意什么—行前准备篇
                                <br/>
                                初次进藏最好提前3-4周进行身体锻炼，每天慢跑或爬楼梯是最佳方式。应提前两周服用藏药红景天制品...
                                <a href="http://www.lvmama.com/guide/2012/0420-162285.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/guide/2012/0418-162281.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xizang3.jpg"></a>
                            <p>
                                3.西藏怎么走最安全景色最美？旅程50问为你解惑
                                <br/>
                                我们此次的行程从成都出发，海拔是在500米到600米，这次…
                                <a href="http://www.lvmama.com/guide/2012/0418-162281.html" target="_blank">[查看详情]</a>
                            </p>

                            <a href="http://www.lvmama.com/guide/2012/0420-162507.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xizang4.jpg"></a>
                            <p>
                                4.藏地之美 你我见证
                                <br/>
                                对任何一个喜欢自助旅行的人来说，走川藏线都该是第一选择。最能让你感受到"在路上"的美妙和神奇...
                                <a href="http://www.lvmama.com/guide/2012/0420-162507.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/guide/2012/0518-162672.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xizang5.jpg"></a>
                            <p>
                                5.3300元从西藏穷游到尼伯尔
                                <br/>
                                从西藏穷游到尼泊尔，出发只是偶然，但是却留下了太多回忆，从而领悟：生命就是一场无所谓终点...
                                <a href="http://www.lvmama.com/guide/2012/0518-162672.html" target="_blank">[查看详情]</a>
                            </p>
                        </div>
                        <ul class="gl_list">
                            <li>
                                <div class="pic fl">
                                    <a href="http://www.lvmama.com/guide/place/xizang_lasa/" target="_blank">
                                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xizang_115x162.jpg"></a>
                                </div>
                                <div class="lianjie">
                                    <a href="http://www.lvmama.com/guide/place/xizang_lasa/" target="_blank">拉萨官方攻略</a>
                                </div>
                                <a href="http://www.lvmama.com/guide/place/xizang_lasa/" target="_blank" class="dowm_bt">立即下载</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="prod_nums"  style="display:block">
                    <div class="city_prod_main fl">
                        <ul class="city_prod_tujian mt5">
                            <@s.iterator value="map.get('${station}_gzxinjiangzt')">
                                <li>
                                    <div class="pic_list fl">
                                        <a href="${url?if_exists}">
                                            <img alt="" src="${imgUrl?if_exists}"></a>
                                        <span class="icon_post">
                                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/tt_icon.png"></span>
                                    </div>
                                    <div class="tujian_info">
                                        <a href="${url?if_exists}" class="tit">${title?if_exists}</a>
                                        <p>★ ${bakWord2?if_exists}</p>
                                        <p>★ ${bakWord3?if_exists}</p>
                                        <p>★ ${remark?if_exists}</p>
                                    </div>
                                    <span class="prod_price prod_price_city">
                                        <em>¥</em>
                                        ${memberPrice?if_exists?replace(".0","")}
                                        <em>起</em>
                                    </span>
                                    <a href="${url?if_exists}" class="book_bt ft book_city">立即预订</a>
                                </li>
                            </@s.iterator>
                        </ul>

                        <@s.iterator value="map.get('${station}_gzxinjiang')" status="st">
                            <h3 class="tits mt10">${title?if_exists}</h3>
                            <ul class="thr_list ciyt_list mt5">
                                <@s.iterator value="map.get('${station}_gzxinjiang_${st.index+1}')" status="st1">
                                    <li>
                                        <span class="prod_info">
                                            <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                        </span>
                                        <p class="prod_text">${remark?if_exists}</p>

                                        <span class="prod_price prod_price_city01">
                                            <em>¥</em>
                                            ${memberPrice?if_exists?replace(".0","")}
                                            <em>起</em>
                                        </span>
                                        <a href="${url?if_exists}" class="book_bt ft book_city01">立即预订</a>
                                    </li>
                                </@s.iterator>
                            </ul>
                            <div class="comon">
                                <a href="${url?if_exists}" class="more_bt mt5 fr">更多>></a>
                            </div>
                        </@s.iterator>
                    </div>
                    <div class="pic_show_aside fl">
                        <div class="pic_show_list">
                            <h3>新疆旅游全攻略</h3>
                            <a href="http://www.lvmama.com/guide/2012/0518-162962.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xijiang1.jpg"></a>
                            <p>
                                1.新疆禾木草原：上帝的后花园
                                <br/>
                                在旅游部门的大喀纳斯概念中，喀纳斯湖是其核心，禾木和白哈巴是它的两翼，双湖和白湖...
                                <a href="http://www.lvmama.com/guide/2012/0518-162962.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/info/outdoor/2012-0523-83810.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xinjiang2.jpg"></a>
                            <p>
                                2.掀起你的盖头来 行摄新疆
                                <br/>
                                当我踏上西行的列车，内心的激动是无法言表的，踏上了曾经萦绕脑海多年的梦想旅程...
                                <a href="http://www.lvmama.com/info/outdoor/2012-0523-83810.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/info/hangkong/2012-0524-86937.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xinjiang3.jpg"></a>
                            <p>
                                3.穿越狰狞奇幻的新疆魔鬼城之简介
                                <br/>
                                魔鬼城风光魔鬼城风光魔鬼城简介魔鬼城又称乌尔禾风城。位于准噶尔盆地…
                                <a href="http://www.lvmama.com/info/hangkong/2012-0524-86937.html" target="_blank">[查看详情]</a>
                            </p>

                            <a href="http://www.lvmama.com/info/star/2011-0728-16885.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xinjiang4.jpg"></a>
                            <p>
                                4.新还珠格格香妃情 访喀什香妃墓
                                <br/>
                                新还珠格格香妃 香妃墓：她被清朝皇帝选为妃子，赐号“香妃”，因不服京城水土病故...
                                <a href="http://www.lvmama.com/info/star/2011-0728-16885.html" target="_blank">[查看详情]</a>
                            </p>
                            <a href="http://www.lvmama.com/info/hangkong/2012-0524-86957.html" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/xinjiang5.jpg"></a>
                            <p>
                                5.看世间罕见的羊肉串是怎样炼成的
                                <br/>
                                新疆沙雅的烤鱼既是当地一道特色美食，又是一道民俗风味浓郁的风景...
                                <a href="http://www.lvmama.com/info/hangkong/2012-0524-86957.html" target="_blank">[查看详情]</a>
                            </p>

                        </div>
                        <ul class="gl_list">
                            <!-- <li>
                            <div class="pic_list fl">
                                <a href="http://www.lvmama.com/guide/place/xizang_lasa/" target="_blank">
                                    <img src="http://pic.lvmama.com/img/zt/dxb/gl.jpg"></a>
                            </div>
                            <div class="lianjie">
                                <a href="http://www.lvmama.com/guide/place/xizang_lasa/" target="_blank">拉萨官方攻略</a>
                            </div>
                            <a href="http://www.lvmama.com/guide/place/xizang_lasa/" class="dowm_bt">立即下载</a>
                        </li>
                        -->
                    </ul>
                </div>
            </div>
            <div class="prod_nums">
                <div class="city_prod_main fl">
                        <ul class="city_prod_tujian mt5">
                            <@s.iterator value="map.get('${station}_gzneimengzt')">
                                <li>
                                    <div class="pic_list fl">
                                        <a href="${url?if_exists}">
                                            <img alt="" src="${imgUrl?if_exists}"></a>
                                        <span class="icon_post">
                                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/tt_icon.png"></span>
                                    </div>
                                    <div class="tujian_info">
                                        <a href="${url?if_exists}" class="tit">${title?if_exists}</a>
                                        <p>★ ${bakWord2?if_exists}</p>
                                        <p>★ ${bakWord3?if_exists}</p>
                                        <p>★ ${remark?if_exists}</p>
                                    </div>
                                    <span class="prod_price prod_price_city">
                                        <em>¥</em>
                                        ${memberPrice?if_exists?replace(".0","")}
                                        <em>起</em>
                                    </span>
                                    <a href="${url?if_exists}" class="book_bt ft book_city">立即预订</a>
                                </li>
                            </@s.iterator>
                        </ul>

                        <@s.iterator value="map.get('${station}_gzneimeng')" status="st">
                            <h3 class="tits mt10">${title?if_exists}</h3>
                            <ul class="thr_list ciyt_list mt5">
                                <@s.iterator value="map.get('${station}_gzneimeng_${st.index+1}')" status="st1">
                                    <li>
                                        <span class="prod_info">
                                            <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                        </span>
                                        <p class="prod_text">${remark?if_exists}</p>

                                        <span class="prod_price prod_price_city01">
                                            <em>¥</em>
                                            ${memberPrice?if_exists?replace(".0","")}
                                            <em>起</em>
                                        </span>
                                        <a href="${url?if_exists}" class="book_bt ft book_city01">立即预订</a>
                                    </li>
                                </@s.iterator>
                            </ul>
                            <div class="comon">
                                <a href="${url?if_exists}" class="more_bt mt5 fr">更多>></a>
                            </div>
                        </@s.iterator>
                    </div>
                <div class="pic_show_aside fl">
                    <div class="pic_show_list">
                        <h3>内蒙古旅游全攻略</h3>
                        <a href="http://www.lvmama.com/guide/2012/0507-162644.html" target="_blank">
                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/neimeng1.jpg"></a>
                        <p>
                            1.呼伦贝尔大草原行摄攻略
                            <br/>
                            美丽、富饶、神奇的呼伦贝尔大草原是我国目前保存最完好的草原，这里地域辽阔，风光旖旎...
                            <a href="http://www.lvmama.com/guide/2012/0507-162644.html" target="_blank">[查看详情]</a>
                        </p>
                        <a href="http://www.lvmama.com/info/zijia/2011-0831-20175.html" target="_blank">
                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/neimeng2.jpg"></a>
                        <p>
                            2.北京自驾内蒙古 在草原上尽情撒野
                            <br/>
                            “天苍苍，野茫茫，风吹草低现牛羊”。再次开上那辆久经沙场的越野车，从呼和浩特...
                            <a href="http://www.lvmama.com/info/zijia/2011-0831-20175.html" target="_blank">[查看详情]</a>
                        </p>
                        <a href="http://www.lvmama.com/info/photo/pics/2012-0321-58212.html " target="_blank">
                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/neimeng3.jpg"></a>
                        <p>
                            3.网友自驾游内蒙古 路遇各色雷人和牛车
                            <br/>
                            各种雷人，欢迎偶遇。O(∩_∩)O哈哈~…
                            <a href="http://www.lvmama.com/info/photo/pics/2012-0321-58212.html " target="_blank">[查看详情]</a>
                        </p>

                        <a href="http://www.lvmama.com/info/chinagonglue/2012-0416-67858.html" target="_blank">
                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/neimeng4.jpg"></a>
                        <p>
                            4.一起出发 游荡在辽阔的呼伦贝尔
                            <br/>
                            呼伦是蒙古语“水獭”的意思，贝尔的意思是“雄水獭”，由于当地人有...
                            <a href="http://www.lvmama.com/info/chinagonglue/2012-0416-67858.html" target="_blank">[查看详情]</a>
                        </p>
                        <a href="http://www.lvmama.com/info/chinagonglue/2011-0824-19326-2.html" target="_blank">
                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/neimeng5.jpg"></a>
                        <p>
                            5.纵情呼伦贝尔 飞奔在内蒙古草原之上
                            <br/>
                            我不是第一次、第二次去蒙古草原了近的比如坝上，远的比如锡林郭勒...
                            <a href="http://www.lvmama.com/info/chinagonglue/2011-0824-19326-2.html" target="_blank">[查看详情]</a>
                        </p>
                    </div>
                    <ul class="gl_list">
                        <!-- <li>
                        <div class="pic fl">
                            <a href="#">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/gl.jpg"></a>
                        </div>
                        <div class="lianjie">
                            <a href="#">三亚官方攻略</a>
                        </div>
                        <a href="#" class="dowm_bt">立即下载</a>
                    </li>
                    -->
                </ul>
            </div>
        </div>
        <div class="prod_nums">
            <div class="city_prod_main fl">
                        <ul class="city_prod_tujian mt5">
                            <@s.iterator value="map.get('${station}_gzsiluzt')">
                                <li>
                                    <div class="pic_list fl">
                                        <a href="${url?if_exists}">
                                            <img alt="" src="${imgUrl?if_exists}"></a>
                                        <span class="icon_post">
                                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/tt_icon.png"></span>
                                    </div>
                                    <div class="tujian_info">
                                        <a href="${url?if_exists}" class="tit">${title?if_exists}</a>
                                        <p>★ ${bakWord2?if_exists}</p>
                                        <p>★ ${bakWord3?if_exists}</p>
                                        <p>★ ${remark?if_exists}</p>
                                    </div>
                                    <span class="prod_price prod_price_city">
                                        <em>¥</em>
                                        ${memberPrice?if_exists?replace(".0","")}
                                        <em>起</em>
                                    </span>
                                    <a href="${url?if_exists}" class="book_bt ft book_city">立即预订</a>
                                </li>
                            </@s.iterator>
                        </ul>

                        <@s.iterator value="map.get('${station}_gzsilu')" status="st">
                            <h3 class="tits mt10">${title?if_exists}</h3>
                            <ul class="thr_list ciyt_list mt5">
                                <@s.iterator value="map.get('${station}_gzsilu_${st.index+1}')" status="st1">
                                    <li>
                                        <span class="prod_info">
                                            <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                        </span>
                                        <p class="prod_text">${remark?if_exists}</p>

                                        <span class="prod_price prod_price_city01">
                                            <em>¥</em>
                                            ${memberPrice?if_exists?replace(".0","")}
                                            <em>起</em>
                                        </span>
                                        <a href="${url?if_exists}" class="book_bt ft book_city01">立即预订</a>
                                    </li>
                                </@s.iterator>
                            </ul>
                            <div class="comon">
                                <a href="${url?if_exists}" class="more_bt mt5 fr">更多>></a>
                            </div>
                        </@s.iterator>
                    </div>
            <div class="pic_show_aside fl">
                <div class="pic_show_list">
                    <h3>丝路旅游全攻略</h3>
                    <a href="http://www.lvmama.com/guide/2011/0609-128620.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/silu1.jpg"></a>
                    <p>
                        1.千元游敦煌，你不得不信 如假包换
                        <br/>
                        沙漠戈壁地区的气候适合夏末秋初去游览，旺季之后的淡季虽然景区门票都打折掉近一半...
                        <a href="http://www.lvmama.com/guide/2011/0609-128620.html" target="_blank">[查看详情]</a>
                    </p>
                    <a href="http://www.lvmama.com/info/chinagonglue/2011-0613-6723.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/silu2.jpg"></a>
                    <p>
                        2.西北敦煌之旅 大漠孤烟处的文化奇葩
                        <br/>
                        其悠久的历史孕育了灿烂的古代文化，遍地的文物遗迹、浩繁的典籍文献、精美的石窟艺术...
                        <a href="http://www.lvmama.com/info/chinagonglue/2011-0613-6723.html" target="_blank">[查看详情]</a>
                    </p>
                    <a href="http://www.lvmama.com/guide/2009/1230-133217.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/silu3.jpg"></a>
                    <p>
                        3.宁夏中卫：《新龙门客栈》里的浪漫与传奇
                        <br/>
                        中卫市的沙坡头是目前国内沙漠旅游的典型代表。古老的黄河…
                        <a href="http://www.lvmama.com/guide/2009/1230-133217.html" target="_blank">[查看详情]</a>
                    </p>

                    <a href="http://www.lvmama.com/guide/2011/0609-128618.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/silu4.jpg"></a>
                    <p>
                        4.两关悲戚，春风不度的地貌残骸
                        <br/>
                        果说莫高窟是文化上的弃尸的话，那么整个西线就是地理上的残骸了吧。由于我独自一人...
                        <a href="http://www.lvmama.com/guide/2011/0609-128618.html" target="_blank">[查看详情]</a>
                    </p>
                    <a href="http://www.lvmama.com/guide/2010/1217-106469.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/silu5.jpg"></a>
                    <p>
                        5.青海湖 夏季的调色板
                        <br/>
                        从山下到湖畔，是广袤平坦、苍茫无际的千里草原...
                        <a href="http://www.lvmama.com/guide/2010/1217-106469.html" target="_blank">[查看详情]</a>
                    </p>
                </div>
                <ul class="gl_list">
                    <li>
                        <div class="pic fl">
                            <a href="http://www.lvmama.com/guide/place/gansu_dunhuangshi/" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/silu_115x162.jpg"></a>
                        </div>
                        <div class="lianjie">
                            <a href="http://www.lvmama.com/guide/place/gansu_dunhuangshi/" target="_blank">敦煌旅游攻略</a>
                        </div>
                        <a href="http://www.lvmama.com/guide/place/gansu_dunhuangshi/" target="_blank"class="dowm_bt">立即下载</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="prod_nums">
            <div class="city_prod_main fl">
                        <ul class="city_prod_tujian mt5">
                            <@s.iterator value="map.get('${station}_gzshanxizt')">
                                <li>
                                    <div class="pic_list fl">
                                        <a href="${url?if_exists}">
                                            <img alt="" src="${imgUrl?if_exists}"></a>
                                        <span class="icon_post">
                                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/tt_icon.png"></span>
                                    </div>
                                    <div class="tujian_info">
                                        <a href="${url?if_exists}" class="tit">${title?if_exists}</a>
                                        <p>★ ${bakWord2?if_exists}</p>
                                        <p>★ ${bakWord3?if_exists}</p>
                                        <p>★ ${remark?if_exists}</p>
                                    </div>
                                    <span class="prod_price prod_price_city">
                                        <em>¥</em>
                                        ${memberPrice?if_exists?replace(".0","")}
                                        <em>起</em>
                                    </span>
                                    <a href="${url?if_exists}" class="book_bt ft book_city">立即预订</a>
                                </li>
                            </@s.iterator>
                        </ul>

                        <@s.iterator value="map.get('${station}_gzshanxi')" status="st">
                            <h3 class="tits mt10">${title?if_exists}</h3>
                            <ul class="thr_list ciyt_list mt5">
                                <@s.iterator value="map.get('${station}_gzshanxi_${st.index+1}')" status="st1">
                                    <li>
                                        <span class="prod_info">
                                            <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                        </span>
                                        <p class="prod_text">${remark?if_exists}</p>

                                        <span class="prod_price prod_price_city01">
                                            <em>¥</em>
                                            ${memberPrice?if_exists?replace(".0","")}
                                            <em>起</em>
                                        </span>
                                        <a href="${url?if_exists}" class="book_bt ft book_city01">立即预订</a>
                                    </li>
                                </@s.iterator>
                            </ul>
                            <div class="comon">
                                <a href="${url?if_exists}" class="more_bt mt5 fr">更多>></a>
                            </div>
                        </@s.iterator>
                    </div>
            <div class="pic_show_aside fl">
                <div class="pic_show_list">
                    <h3>陕西旅游全攻略</h3>
                    <a href="http://www.lvmama.com/info/chinagonglue/2012-0528-89759.html " target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/shanxi1.jpg"></a>
                    <p>
                        1.面条竟然像腰带？细数陕西十大怪
                        <br/>
                        陕西十大怪是一种独特的生活。俗话说，“百里不同风，十里不同俗。”在陕西这块黄土地上...
                        <a href="http://www.lvmama.com/info/chinagonglue/2012-0528-89759.html " target="_blank">[查看详情]</a>
                    </p>
                    <a href="http://www.lvmama.com/guide/2011/0111/122347.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/shanxi2.jpg"></a>
                    <p>
                        2.骑单车逛西安-书院门 历史博物馆
                        <br/>
                        书院门就在南门边的城墙脚下，但它并不是一个城门，而是一条古文化街...
                        <a href="http://www.lvmama.com/guide/2011/0111/122347.html" target="_blank">[查看详情]</a>
                    </p>
                    <a href="http://www.lvmama.com/info/chinagonglue/2011-0412-91.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/shanxi3.jpg"></a>
                    <p>
                        3.西安美食全介绍 泡馍里的西北风情
                        <br/>
                        西安是旅游名城，也是著名的美食与小吃之城，在这里可以尽情地品尝三秦美食…
                        <a href="http://www.lvmama.com/info/chinagonglue/2011-0412-91.html" target="_blank">[查看详情]</a>
                    </p>

                    <a href="http://www.lvmama.com/guide/2009/1230-132534.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/shanxi4.jpg"></a>
                    <p>
                        4.陕西汉中：兼南北之美的风水宝地
                        <br/>
                        汉中地处山岭夹沟，整个是狭长盆地，历来是入蜀门户，兵家必争的之地...
                        <a href="http://www.lvmama.com/guide/2009/1230-132534.html" target="_blank">[查看详情]</a>
                    </p>
                    <a href="http://www.lvmama.com/info/chinagonglue/2011-0622-6880.html " target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/shanxi5.jpg"></a>
                    <p>
                        5.陕西延安红色之旅 拜访红色圣地 祭奠黄帝陵庙
                        <br/>
                        延安不仅仅是一个红色革命圣地，同时也有丰富的旅游资源...
                        <a href="http://www.lvmama.com/info/chinagonglue/2011-0622-6880.html" target="_blank">[查看详情]</a>
                    </p>
                </div>
                <ul class="gl_list">
                    <li>
                        <div class="pic fl">
                            <a href="http://www.lvmama.com/guide/place/shannxi_xian/" target="_blank">
                                <img alt="" src="http://pic.lvmama.com/img/zt/dxb/shanxi_115x162.jpg"></a>
                        </div>
                        <div class="lianjie">
                            <a href="http://www.lvmama.com/guide/place/shannxi_xian/" target="_blank">西安旅游攻略</a>
                        </div>
                        <a href="http://www.lvmama.com/guide/place/shannxi_xian/" target="_blank" class="dowm_bt">立即下载</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="prod_nums">
            <div class="city_prod_main fl">
                        <ul class="city_prod_tujian mt5">
                            <@s.iterator value="map.get('${station}_gzsanxiazt')">
                                <li>
                                    <div class="pic_list fl">
                                        <a href="${url?if_exists}">
                                            <img alt="" src="${imgUrl?if_exists}"></a>
                                        <span class="icon_post">
                                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/tt_icon.png"></span>
                                    </div>
                                    <div class="tujian_info">
                                        <a href="${url?if_exists}" class="tit">${title?if_exists}</a>
                                        <p>★ ${bakWord2?if_exists}</p>
                                        <p>★ ${bakWord3?if_exists}</p>
                                        <p>★ ${remark?if_exists}</p>
                                    </div>
                                    <span class="prod_price prod_price_city">
                                        <em>¥</em>
                                        ${memberPrice?if_exists?replace(".0","")}
                                        <em>起</em>
                                    </span>
                                    <a href="${url?if_exists}" class="book_bt ft book_city">立即预订</a>
                                </li>
                            </@s.iterator>
                        </ul>

                        <@s.iterator value="map.get('${station}_gzsanxia')" status="st">
                            <h3 class="tits mt10">${title?if_exists}</h3>
                            <ul class="thr_list ciyt_list mt5">
                                <@s.iterator value="map.get('${station}_gzsanxia_${st.index+1}')" status="st1">
                                    <li>
                                        <span class="prod_info">
                                            <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                        </span>
                                        <p class="prod_text">${remark?if_exists}</p>

                                        <span class="prod_price prod_price_city01">
                                            <em>¥</em>
                                            ${memberPrice?if_exists?replace(".0","")}
                                            <em>起</em>
                                        </span>
                                        <a href="${url?if_exists}" class="book_bt ft book_city01">立即预订</a>
                                    </li>
                                </@s.iterator>
                            </ul>
                            <div class="comon">
                                <a href="${url?if_exists}" class="more_bt mt5 fr">更多>></a>
                            </div>
                        </@s.iterator>
                    </div>
            <div class="pic_show_aside fl">
                <div class="pic_show_list">
                    <h3>三峡旅游全攻略</h3>
                    <a href="http://www.lvmama.com/info/city/2011-1122-37176.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sanxia1.jpg"></a>
                    <p>
                        1.逆流而上游三峡 看长江最深处的美
                        <br/>
                        中学时曾学习的刘白羽的散文《长江三日》里三峡景色险峻、秀丽、雄伟，让人心驰神往...
                        <a href="http://www.lvmama.com/info/city/2011-1122-37176.html" target="_blank">[查看详情]</a>
                    </p>
                    <a href="http://www.lvmama.com/info/chinagonglue/2011-0921-22390.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sanxia2.jpg"></a>
                    <p>
                        2.乘游轮赏三峡美景 秋高气爽正当时
                        <br/>
                        可能每个乘邮轮去旅行的人都想要偷偷模仿泰坦尼克号上Jack和Rose在船头张开双臂、凌风而拥的动作...
                        <a href="http://www.lvmama.com/info/chinagonglue/2011-0921-22390.html" target="_blank">[查看详情]</a>
                    </p>
                    <a href="http://www.lvmama.com/info/chinalife/2012-0516-81303.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sanxia3.jpg"></a>
                    <p>
                        3.秀丽神奇文艺 游三峡不可错过的景点
                        <br/>
                        小柯有一首歌叫《江南电影》，其中最打动我的歌词是这句：“看这一切宛如一部江南电影…
                        <a href="http://www.lvmama.com/info/chinalife/2012-0516-81303.html" target="_blank">[查看详情]</a>
                    </p>

                    <a href="http://www.lvmama.com/info/chinagonglue/2011-0608-6399.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sanxia4.jpg"></a>
                    <p>
                        4.巴东三峡之旅 动人美景与凄美传说交相辉映
                        <br/>
                        长江三峡举世闻名。它西起奉节县白帝城，东抵宜昌的南津关..
                        <a href="http://www.lvmama.com/info/chinagonglue/2011-0608-6399.html" target="_blank">[查看详情]</a>
                    </p>
                    <a href="http://www.lvmama.com/info/chinalife/2012-0503-77596.html" target="_blank">
                        <img alt="" src="http://pic.lvmama.com/img/zt/dxb/sanxia5.jpg"></a>
                    <p>
                        5.巫山小三峡观六奇之美
                        <br/>
                        没有江南水乡那么秀丽，没有万里长城那么雄伟，重庆有的是那颗翻滚着的炽热的心...
                        <a href="http://www.lvmama.com/info/chinalife/2012-0503-77596.html" target="_blank">[查看详情]</a>
                    </p>
                </div>
                <ul class="gl_list">
                    <!-- <li>
                    <div class="pic fl">
                        <a href="#">
                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/gl.jpg"></a>
                    </div>
                    <div class="lianjie">
                        <a href="#">三亚官方攻略</a>
                    </div>
                    <a href="#" class="dowm_bt">立即下载</a>
                </li>
                -->
            </ul>
        </div>
    </div>
    <div class="prod_nums">
        <div class="city_prod_main fl">
                        <ul class="city_prod_tujian mt5">
                            <@s.iterator value="map.get('${station}_gzguizhouzt')">
                                <li>
                                    <div class="pic_list fl">
                                        <a href="${url?if_exists}">
                                            <img alt="" src="${imgUrl?if_exists}"></a>
                                        <span class="icon_post">
                                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/tt_icon.png"></span>
                                    </div>
                                    <div class="tujian_info">
                                        <a href="${url?if_exists}" class="tit">${title?if_exists}</a>
                                        <p>★ ${bakWord2?if_exists}</p>
                                        <p>★ ${bakWord3?if_exists}</p>
                                        <p>★ ${remark?if_exists}</p>
                                    </div>
                                    <span class="prod_price prod_price_city">
                                        <em>¥</em>
                                        ${memberPrice?if_exists?replace(".0","")}
                                        <em>起</em>
                                    </span>
                                    <a href="${url?if_exists}" class="book_bt ft book_city">立即预订</a>
                                </li>
                            </@s.iterator>
                        </ul>

                        <@s.iterator value="map.get('${station}_gzguizhou')" status="st">
                            <h3 class="tits mt10">${title?if_exists}</h3>
                            <ul class="thr_list ciyt_list mt5">
                                <@s.iterator value="map.get('${station}_gzguizhou_${st.index+1}')" status="st1">
                                    <li>
                                        <span class="prod_info">
                                            <a href="${url?if_exists}" title="${title?if_exists}">${title?if_exists}</a>
                                        </span>
                                        <p class="prod_text">${remark?if_exists}</p>

                                        <span class="prod_price prod_price_city01">
                                            <em>¥</em>
                                            ${memberPrice?if_exists?replace(".0","")}
                                            <em>起</em>
                                        </span>
                                        <a href="${url?if_exists}" class="book_bt ft book_city01">立即预订</a>
                                    </li>
                                </@s.iterator>
                            </ul>
                            <div class="comon">
                                <a href="${url?if_exists}" class="more_bt mt5 fr">更多>></a>
                            </div>
                        </@s.iterator>
                    </div>
        <div class="pic_show_aside fl">
            <div class="pic_show_list">
                <h3>贵州旅游全攻略</h3>
                <a href="http://www.lvmama.com/info/chinagonglue/2011-0622-6878.html " target="_blank">
                    <img alt="" src="http://pic.lvmama.com/img/zt/dxb/guizhou1.jpg"></a>
                <p>
                    1.贵州红色旅游 访遵义会议旧址
                    <br/>
                    贵州红色旅游，遵义会议，四渡赤水和《忆秦娥》遵义旅游有四大特色...
                    <a href="http://www.lvmama.com/info/chinagonglue/2011-0622-6878.html " target="_blank">[查看详情]</a>
                </p>
                <a href="http://www.lvmama.com/guide/2012/0328-162047.html" target="_blank">
                    <img alt="" src="http://pic.lvmama.com/img/zt/dxb/guizhou2.jpg"></a>
                <p>
                    2.多彩贵州 印象趣玩黄果树
                    <br/>
                    夏季无疑是去贵州的好季节，也无疑是看黄果树瀑布的好季节，贵州若隐若现的神秘夹杂着淳朴的民风...
                    <a href="http://www.lvmama.com/guide/2012/0328-162047.html" target="_blank">[查看详情]</a>
                </p>
                <a href="http://www.lvmama.com/guide/2012/0306-161740.html" target="_blank">
                    <img alt="" src="http://pic.lvmama.com/img/zt/dxb/guizhou3.jpg"></a>
                <p>
                    3.吃喝苗家，收藏苗家
                    <br/>
                    贵州有句民谣：“三天不吃酸，走路打蹿蹿”。众多苗族菜肴中，以酸汤最为着名，其酸香丰富…
                    <a href="http://www.lvmama.com/guide/2012/0306-161740.html" target="_blank">[查看详情]</a>
                </p>

                <a href="http://www.lvmama.com/guide/2012/0307-161767.html" target="_blank">
                    <img alt="" src="http://pic.lvmama.com/img/zt/dxb/guizhou4.jpg"></a>
                <p>
                    4.镇远-此生不能错过的宁静古镇
                    <br/>
                    随着历史的变迁，这座小城逐渐沉默，却也成就了它的另一种美丽--与世无争、宁静淡雅...
                    <a href="http://www.lvmama.com/guide/2012/0307-161767.html" target="_blank">[查看详情]</a>
                </p>
                <a href="http://www.lvmama.com/guide/2011/0118-126623-1.html" target="_blank">
                    <img alt="" src="http://pic.lvmama.com/img/zt/dxb/guizhou5.jpg"></a>
                <p>
                    5.黔东南11天最新实用攻略
                    <br/>
                    出发贵州之前花了5天时间上网研究攻略，很多陌生绕口的地名，当时看得我眼花缭乱...
                    <a href="http://www.lvmama.com/guide/2011/0118-126623-1.html" target="_blank">[查看详情]</a>
                </p>
            </div>
            <ul class="gl_list">
                <li>
                    <div class="pic fl">
                        <a href="http://www.lvmama.com/guide/place/guizhou_qiandongnanmiaozudongz/" target="_blank">
                            <img alt="" src="http://pic.lvmama.com/img/zt/dxb/guizhou_115x162.jpg"></a>
                    </div>
                    <div class="lianjie">
                        <a href="http://www.lvmama.com/guide/place/guizhou_qiandongnanmiaozudongz/" target="_blank">黔东南攻略</a>
                    </div>
                    <a href="http://www.lvmama.com/guide/place/guizhou_qiandongnanmiaozudongz/" target="_blank" class="dowm_bt">立即下载</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="pic_wall clearfix">
    <ul class="pic_show">
        <li class="">
            <a href="http://www.lvmama.com/info/photo/sheying/2012-0525-88722.html" target="_blank">
                <img alt="" src="http://pic.lvmama.com/opi/daxibu2.jpg"></a>
            <a href="http://www.lvmama.com/info/photo/sheying/2012-0525-88722.html" target="_blank" class="tits">
                地球之巅
                <br>珠穆朗玛峰</a>
        </li>
        <li class="">
            <a href="http://www.lvmama.com/info/photo/sheying/2012-0525-88716.html" target="_blank">
                <img alt="" src="http://pic.lvmama.com/opi/daxibu3.jpg"></a>
            <a href="http://www.lvmama.com/info/photo/sheying/2012-0525-88716.html" target="_blank" class="tits">
                锦绣河山
                <br>美丽的雅鲁藏布江</a>
        </li>
        <li class="">
            <a href="http://www.lvmama.com/info/photo/lvtu/2012-0608-93017.html" target="_blank">
                <img alt="" src="http://pic.lvmama.com/opi/daxibu_465x230.jpg"></a>
            <a href="http://www.lvmama.com/info/photo/lvtu/2012-0608-93017.html" target="_blank" class="tits">
                敦煌八景鸣沙山
                <br>月牙泉畔相依偎</a>
        </li>
        <li class="">
            <a href="http://www.lvmama.com/info/photo/sheying/2012-0608-93020.html" target="_blank">
                <img alt="" src="http://pic.lvmama.com/opi/daxibu2_465x230.jpg"></a>
            <a href="http://www.lvmama.com/info/photo/sheying/2012-0608-93020.html" target="_blank" class="tits">
                新疆魔鬼城
                <br>恶魔留在世间的烙印</a>
        </li>
        <li class="">
            <a href="http://www.lvmama.com/info/photo/picnews/2012-0525-88713.html" target="_blank">
                <img alt="" src="http://pic.lvmama.com/opi/daxibu4.jpg"></a>
            <a href="http://www.lvmama.com/info/photo/picnews/2012-0525-88713.html" target="_blank" class="tits">
                布达拉宫
                <br>灵魂升华之地</a>
        </li>
        <li class="">
            <a href="http://www.lvmama.com/info/photo/sheying/2012-0525-88714.html" target="_blank">
                <img alt="" src="http://pic.lvmama.com/opi/daxibu1.jpg"></a>
            <a href="http://www.lvmama.com/info/photo/sheying/2012-0525-88714.html" target="_blank" class="tits">
                纳木措
                <br>纯净的天上湖</a>
        </li>
    </ul>
</div>
<div class="hot_pic">
    <a href="http://www.lvmama.com/dest/maomingfangjidao/package
" target="_blank">
        <img alt="" src="http://pic.lvmama.com/opi/gz_fangjidao284x135.jpg"></a>
    <a href="http://www.lvmama.com/zt/dongbu/index.html" target="_blank">
        <img alt="" src="http://pic.lvmama.com/opi/gz_huaqiaocheng284x135.jpg"></a>
    <a href="http://www.lvmama.com/dest/changlongshuishangleyuan " target="_blank">
        <img alt="" src="http://pic.lvmama.com/opi/gz_shuishangshijie_284x135.jpg"></a>
</div>
</div>
</div>
<div class="bottom_pic">
<img alt="" src="http://pic.lvmama.com/img/zt/dxb/bottom_pic.jpg"></div>
<!--pop-->

<div class="pop_fast">
<span class="closed">关闭</span>
<ul class="fast_mu">
<li>
    <a href="#yl">云南</a>
</li>
<li>
    <a href="#jz">四川</a>
</li>
<li>
    <a href="#xz">西藏</a>
</li>
<li  class="cur">
    <a href="#xj">新疆</a>
</li>
<li>
    <a href="#lm">内蒙</a>
</li>
<li>
    <a href="#sl">丝路</a>
</li>
<li>
    <a href="#ca">陕西</a>
</li>
<li>
    <a href="#sx">三峡</a>
</li>
<li>
    <a href="#gz">贵州</a>
</li>

</ul>
<a  href="javascript:scroll(0,0)" target="_self" class="callback">返回顶部</a>
</div>
<div class="footer_list">
<script type="text/javascript" src="http://www.lvmama.com/zt/000global/js/ztFooter.js"></script>
</div>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"  type="text/javascript"></script>

<script type="text/javascript">
       backtop();
       $(window).scroll(backtop);
         function  backtop(){
            if($(window).scrollTop()>300){
                $(".pop_fast").show();
            }else{
                $(".pop_fast").hide();
            }
        };//backtop
        //弹出层关闭
        close_evt(".closed");//关闭弹出层
        function close_evt(close_btn){        
                $(close_btn).bind("click",function(){
                $(this).parent().hide();
             });
        }

$(".fast_mu li").click(function(){
    $(this).addClass("cur").siblings().removeClass("cur");
})
$('.cfd_nav dd').click(function(){
		   $(this).addClass('nav_click').siblings('dd').removeClass('nav_click')
		   });

</script>
<script type="text/javascript">
var $tab_nav_li = $('.city_prod_list > li');
var $city_li = $(".head_nav li")
var $pro_cont = $(".prod_nums");
var $md_li = $(" .fast_mu li");
$tab_nav_li.click(function(){
  $(this).addClass("cur").siblings().removeClass("cur");
  var s_index = $tab_nav_li.index(this);
  $pro_cont.eq(s_index).show().siblings().hide(); 
  $city_li.eq(s_index).addClass("cur").siblings().removeClass("cur");
  $md_li.eq(s_index).addClass("cur").siblings().removeClass("cur");
  })
 
 $city_li.click(function(){
            $(this).addClass('cur').siblings().removeClass("cur");
            var scenic_index = $city_li.index(this);
            $pro_cont.eq(scenic_index).show().siblings().hide();
            $tab_nav_li.eq(scenic_index).addClass("cur").siblings().removeClass("cur");
            $md_li.eq(scenic_index).addClass("cur").siblings().removeClass("cur");
        });
        
 $md_li.click(function(){
            var md_index = $md_li.index(this);
            $pro_cont.eq(md_index).show().siblings().hide();
            $city_li.eq(md_index).addClass('cur').siblings().removeClass("cur");
            $tab_nav_li.eq(md_index).addClass("cur").siblings().removeClass("cur");
        });
</script>
</body>
</html>