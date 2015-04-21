<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>【秒杀】特价出境游_特价出境游线路_出境游哪里最便宜-驴妈妈旅游网</title> 
<meta name="keywords" content="出国，出境，出境游" /> 
<meta name="description" content="驴妈妈本期推荐最便宜的出国旅游，我们提供非常便宜的特价出境游线路，提供出国旅游线路报价和出国旅游要多少钱，告诉您去出境游哪里最便宜又好玩的地方!" /> 
<link rel="stylesheet" href="http://www.lvmama.com/zt/promo/chujing/css/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<base target="_blank">
<style>
</style>
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->
<div class="main_all">
	<div class="banner">
    	<p>
            <a href="http://itunes.apple.com/cn/app/id443926246?mt=8" class="apple"></a>
            <a href="http://m.lvmama.com/apk/Lvmm.apk" class="android"></a>
    	</p>
    </div>
    <div class="cjContent">
        <div class="cjWrap">
        <div class="cjActive">
            <h3 class="bigHead"><a name="mszq"></a> 贴钱秒杀专区<em>ACTIVITIES</em></h3>
            <div class="activeContent">
                <!--listContent-->
                <div class="listContent">
                    <h4 class="listHead">
                        <span>产品秒杀大放送</span>
                    </h4>
                    <div class="listMain">
                        <ul>
                        <@s.iterator value="map.get('${station}_bt')" status="st">
						<@s.if test="#st.isFirst()">
                            <li>
                                <a href="${url?if_exists}">
                                    <em class="${bakWord2?if_exists}">${bakWord1?if_exists}</em>
                                    <span>${title?if_exists}</span>
                                    <i class="bigAngle"></i>
                                </a>
                            </li>
						</@s.if>
						<@s.else>
						 <li>
                                <a href="${url?if_exists}">
                                    <em class="${bakWord2?if_exists}">${bakWord1?if_exists}</em>
                                    <span>${title?if_exists}</span>
									<i class=""></i>
                                </a>
                         </li>
						</@s.else>
                        </@s.iterator>  
                        </ul>
                    </div>
                    <div class="listfoot">
                        <p class="lvPage">
                            <span>1</span>
                            <span>2</span>
                            <span class="listNext"></span>
                        </p>
                    </div>
                </div><!--listContent-->
                <!--detailContent-->
                <div class="detailContent">
                    <dl>
                     <@s.iterator value="map.get('${station}_cp')" status="st">
					 <@s.if test="#st.isFirst()">
                        <dd class="activeDD">
                            <div class="bigImgContent">
                                <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="650" height="275"></a>
                                <div class="imgTag1">
                                    <p>
                                        <span><a href="${url?if_exists}">${bakWord1?if_exists}</a></span><br>
                                        <em>直降</em><dfn>¥<i>${bakWord2?if_exists}</i></dfn>
                                    </p>
                                </div>
                            </div>
                            <div class="detailInfo">
                                <div class="detailLeft">
                                    <div class="detailLeftWrap">
                                        <div class="prices">
                                            <div class="bigPrice">
                                                <em>RMB</em>
                                                <i>现价</i>
                                                 <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                                            </div>
                                            <p>市场价：<del>${marketPrice?if_exists?replace(".0","")}</del>元</p>
                                            <p>限额：${bakWord5?if_exists}名</p>
                                        </div>
                                        <a class="buyBtn" href="${url?if_exists}">立即抢购</a>
                                    </div>
                                   <p class="fourLinks">
                                        <!--<img src="http://www.lvmama.com/zt/promo/chujing/images/infoTags.jpg" usemap="#infoTag">
                                        <map name="infoTag">
                                            <area shape="rect" coords="0, 0, 25, 25" href="#">
                                            <area shape="rect" coords="25, 0, 60, 25" href="##">
                                            <area shape="rect" coords="60, 0, 95, 25" href="###">
                                            <area shape="rect" coords="95, 0, 145, 25" href="####">
                                        </map>
                                        
                                        <a class="tel" href="#">1</a>
                                        <a class="sina" href="#">2</a>
                                        <a class="qq" href="#">3</a>
                                        <a class="renren" href="#">4</a>
                                        -->
                                    </p>
                                </div>
                                <div class="detailRight">
                                    <div class="detailRightWrap">
                                        <p class="tips">
                                            <i>${bakWord3?if_exists}</i>
                                            <em>${bakWord4?if_exists}</em>
                                        </p>
                                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                                        <p class="detailIntro">
                                           ${bakWord6?if_exists}
                                        </p>
                                    </div>
                                    <p class="readMore">
                                      <a href="${url?if_exists}">
                                          <em>READ MORE</em> <i></i>
                                      </a>
                                    </p>
                                </div>
                            </div>
                        </dd>
						</@s.if>
            			<@s.else>
						<dd class="">
                            <div class="bigImgContent">
                                <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="650" height="275"></a>
                                <div class="imgTag1">
                                    <p>
                                        <span><a href="${url?if_exists}">${bakWord1?if_exists}</a></span><br>
                                        <em>直降</em><dfn>¥<i>${bakWord2?if_exists}</i></dfn>
                                    </p>
                                </div>
                            </div>
                            <div class="detailInfo">
                                <div class="detailLeft">
                                    <div class="detailLeftWrap">
                                        <div class="prices">
                                            <div class="bigPrice">
                                                <em>RMB</em>
                                                <i>现价</i>
                                                 <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                                            </div>
                                            <p>市场价：<del>${marketPrice?if_exists?replace(".0","")}</del>元</p>
                                            <p>限额：${bakWord5?if_exists}名</p>
                                        </div>
                                        <a class="buyBtn" href="${url?if_exists}">立即抢购</a>
                                    </div>
                                   <p class="fourLinks">
                                        <!--<img src="http://www.lvmama.com/zt/promo/chujing/images/infoTags.jpg" usemap="#infoTag">
                                        <map name="infoTag">
                                            <area shape="rect" coords="0, 0, 25, 25" href="#">
                                            <area shape="rect" coords="25, 0, 60, 25" href="##">
                                            <area shape="rect" coords="60, 0, 95, 25" href="###">
                                            <area shape="rect" coords="95, 0, 145, 25" href="####">
                                        </map>
                                        
                                        <a class="tel" href="#">1</a>
                                        <a class="sina" href="#">2</a>
                                        <a class="qq" href="#">3</a>
                                        <a class="renren" href="#">4</a>
                                        -->
                                    </p>
                                </div>
                                <div class="detailRight">
                                    <div class="detailRightWrap">
                                        <p class="tips">
                                            <i>${bakWord3?if_exists}</i>
                                            <em>${bakWord4?if_exists}</em>
                                        </p>
                                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                                        <p class="detailIntro">
                                           ${bakWord6?if_exists}
                                        </p>
                                    </div>
                                    <p class="readMore">
                                      <a href="${url?if_exists}">
                                          <em>READ MORE</em> <i></i>
                                      </a>
                                    </p>
                                </div>
                            </div>
                        </dd>
						 </@s.else>
                        </@s.iterator> 
                    </dl>
                </div>
                <div class="cb"></div>
                <!--detailContent-->
            </div>
        </div>
        <div class="cjAreas">
            <!--oneArea-->
            <div class="oneArea">
            	<@s.iterator value="map.get('${station}_xbt')" status="st">
                <h3 class="bigHead"><a name="${bakWord2?if_exists}"></a> ${title?if_exists}<em>${bakWord1?if_exists}</em></h3>
                <div class="areaContent">
                    <!--areaL-->
                    <div class="areaL">
                    <@s.iterator value="map.get('${station}_tt_${st.index + 1}')" status="sts">
                        <div class="areaImg">
                            <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="340" height="150"></a>
                            <div class="imgTag2">
                                <p>
                                    <span>市场价：</span><dfn>¥<i>${marketPrice?if_exists?replace(".0","")}</i></dfn><br>
                                    <dfn class="areaPrice">¥<i class="font26">${memberPrice?if_exists?replace(".0","")}</i>起</dfn>
                                </p>
                            </div>
                        </div>
                        <div class="areaInfo">
                            <h5><a href="${url?if_exists}">${title?if_exists}</a></h5>
                            <ul>
                                <li>★${bakWord1?if_exists}</li>
                                <li>★${bakWord2?if_exists}</li>
                                <li>★${bakWord3?if_exists}</li>
                            </ul>
                        </div>
                     </@s.iterator> 
                    </div><!--areaL-->
                    <!--areaR-->
                    <dl class="areaR">
                      	<@s.iterator value="map.get('${station}_lb_${st.index + 1}')" status="sts">
                        <dd>
                            <a href="${url?if_exists}"  class="">
                                <div class="areaList">
                                    <strong>${bakWord2?if_exists}</strong>
                                    <div class="tripInfo">
                                        <h5>${title?if_exists}</h5>
                                        <p>${bakWord1?if_exists}</p>
                                    </div>
                                    <div class="tripPrice">
                                        <p>
                                            <span>市场价：</span><dfn>¥<i>${marketPrice?if_exists?replace(".0","")}</i></dfn><br>
                                            <dfn class="areaPrice">¥<i class="font26">${memberPrice?if_exists?replace(".0","")}</i>起</dfn>
                                        </p>
                                    </div>
                                </div>
                            </a>
                        </dd>
                        </@s.iterator>
                        
                    </dl><!--areaR-->
                    <div class="cb"></div>
                </div>
                </@s.iterator>
            </div><!--oneArea-->
        </div>
        </div>
    </div>
    <div class="cjHot">
        <div class="hotContent">
            <p class="arrows">
                <button class="arrowLeft"></button>
                <button class="arrowRight"></button>
            </p>
            <div class="hotWrap">
                <div class="hotOut">
                    <dl>
                        <dd class="hotHead">
                            <h5>超热卖目的地</h5>
                        </dd>
                        <dd>
                            <div class="bigImgWrap">
                                <a href="http://www.lvmama.com/search/freelong/79-5723.html"><img src="http://www.lvmama.com/zt/promo/chujing/images/img1.jpg" height="280" width="280"></a>
                                <p class="bottomTag">
                                    <a href="http://www.lvmama.com/search/freelong/79-5723.html">
                                        <em></em>
                                        <span class="bigTitle">马尔代夫</span>
                                        <i class="smallTitle">Maldives</i><!--shortLen-->
                                    </a>
                                </p>
                            </div>
                        </dd>
                        <dd>
                            <div class="bigImgWrap">
                                <a href="http://www.lvmama.com/dest/taiguo_qingmai"><img src="http://www.lvmama.com/zt/promo/chujing/images/img3.jpg" height="280" width="280"></a>
                                <p class="topTag">
                                    <a href="http://www.lvmama.com/dest/taiguo_qingmai">
                                        <em></em>
                                        <span class="bigTitle">泰国清迈</span>
                                        <i class="smallTitle">Thailand Chiang Mai</i>
                                    </a>
                                </p>
                            </div>
                        </dd>
                        <dd>
                            <div class="twoImgWrap">
                                <div class="smallImgWrap">
                                    <a href="http://www.lvmama.com/search/route/79-679.html"><img src="http://www.lvmama.com/zt/promo/chujing/images/img4.jpg" height="140" width="390"></a>
                                    <p class="greenRightTag">
                                        <a href="http://www.lvmama.com/search/route/79-679.html">
                                            <em></em>
                                            <span>香港<br>澳门</span>
                                            <i>hongkong<br>Macau</i>
                                        </a>
                                    </p>
                                </div>
                                <div class="smallImgWrap">
                                    <a href="http://www.lvmama.com/search/route/79-7191-A济州岛.html"><img src="http://www.lvmama.com/zt/promo/chujing/images/img5.jpg" height="140" width="390"></a>
                                    <p class="greyRightTag">
                                        <a href="http://www.lvmama.com/search/route/79-7191-A济州岛.html">
                                            <em></em>
                                            <span>韩国<br>济州岛</span>
                                            <i>jeju Island</i>
                                        </a>
                                    </p>
                                </div>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dd class="hotHead">
                            <h5>超热卖目的地</h5>
                        </dd>
                        <dd>
                            <div class="bigImgWrap">
                                <a href="http://www.lvmama.com/search/group/79-495.html"><img src="http://www.lvmama.com/zt/promo/chujing/images/img6.jpg" height="280" width="280"></a>
                                <p class="bottomTag">
                                    <a href="http://www.lvmama.com/search/group/79-495.html">
                                        <em></em>
                                        <span class="bigTitle">欧洲</span>
                                        <i class="smallTitle">Europe</i>
                                    </a>
                                </p>
                            </div>
                        </dd>
                        <dd>
                            <div class="bigImgWrap">
                                <a href="http://www.lvmama.com/search/route/79-1165.html"><img src="http://www.lvmama.com/zt/promo/chujing/images/img7.jpg" height="280" width="280"></a>
                                <p class="topTag">
                                    <a href="http://www.lvmama.com/search/route/79-1165.html">
                                        <em></em>
                                        <span class="bigTitle">美洲</span>
                                        <i class="smallTitle">America</i>
                                    </a>
                                </p>
                            </div>
                        </dd>
                        <dd>
                            <div class="twoImgWrap">
                                <div class="smallImgWrap">
                                    <a href="http://www.lvmama.com/search/route/79-862-P1.html#list"><img src="http://www.lvmama.com/zt/promo/chujing/images/img8.jpg" height="140" width="390"></a>
                                    <p class="greenRightTag">
                                        <a href="http://www.lvmama.com/search/route/79-862-P1.html#list">
                                            <em></em>
                                            <span>澳洲</span>
                                            <i>Australia</i>
                                        </a>
                                    </p>
                                </div>
                                <div class="smallImgWrap">
                                    <a href="http://www.lvmama.com/search/route/79-1132.html"><img src="http://www.lvmama.com/zt/promo/chujing/images/img9.jpg" height="140" width="390"></a>
                                    <p class="greyRightTag">
                                        <a href="http://www.lvmama.com/search/route/79-1132.html">
                                            <em></em>
                                            <span>邮轮</span>
                                            <i>Cruise</i>
                                        </a>
                                    </p>
                                </div>
                            </div>
                        </dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="rollNav">
    <div class="promoteZoom">
        <a href="#mszq" target="_self">秒杀专区</a>
    </div>
    <ul>
    <@s.iterator value="map.get('${station}_xbt')" status="st">
        <li><a href="#${bakWord2?if_exists}" target="_self">${title?if_exists}</a></li>
    </@s.iterator>
    </ul>
    <div class="toTop"></div>
</div>
<script src="http://pic.lvmama.com/js/jquery-1.7.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/zt/promo/chujing/js/index.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/zt/promo/chujing/js/lv_page.js"></script>
<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>

<script>
$(function(){

    //产品秒杀大放送
    $('.listMain li').mouseenter(function(){
        var index = $(this).index();
        $('.listMain li i').removeClass('bigAngle').eq(index).addClass('bigAngle');
        $('.detailContent dd').eq(index).show().siblings().hide();
    })

    //分页
    var lv_page1=new lv_page({
        $list:$(".listMain ul li"),
        $pageWrap:$(".lvPage"),
        multi_lvpage:true,
        pSize:8
    });
    lv_page1.start();
    //分页

    //最热目的地图片轮播
    var len = $('.hotOut dl').length;
    var hotWidth = $('.hotWrap').outerWidth();
    var hotWrapHTML = $('.hotOut').html();
    $('.hotOut').html(hotWrapHTML+hotWrapHTML);
    var i = 0;
    $('.arrowRight').bind('click',function(){
        if( !$('.hotOut').is(":animated") ){
            if(i==len){
                $('.hotOut').css('left', 0);
                i=0;
            }
            $('.hotOut').animate({'left': '-='+hotWidth+'px'}, 'slow');
            i++;
        }
    })
    $('.arrowLeft').bind('click', function(){
        if( !$('.hotOut').is(":animated") ){
            if(i==0){
                $('.hotOut').css('left', -len*hotWidth+'px');
                i=len;
            }
            $('.hotOut').animate({'left': '+='+hotWidth+'px'}, 'slow');
            i--;
        }
    })

    //右侧跟随滚动
    $.fn.smartFloat = function() {
        var position = function(element) {
            var top = element.position().top, pos = element.css("position"); //pos没用
            $(window).scroll(function() {
                var scrolls = $(this).scrollTop();
                if (scrolls > top) {
                    if (window.XMLHttpRequest) {
                        element.css({
                            position: "fixed",
                            top: 0
                        });
                    } else {
                        element.css({
                            top: scrolls
                        });
                    }
                }else {
                    element.css({
                        position: "absolute",
                        top: top
                    });
                }
            });
        };
        return $(this).each(function() {
            position($(this));
        });
    };
    //绑定
    $(".rollNav").smartFloat();

    $('.toTop').bind('click',function(){
        $(window).scrollTop(0);
        // $('html,body').animate({scrollTop: '0px'},500);
    })
})
</script>
</body>
</html>
