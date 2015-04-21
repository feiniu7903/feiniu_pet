<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>港澳星级酒店推荐_香港酒店预订_澳门酒店预订-驴妈妈旅游网</title>
<meta name="keywords" content="港澳旅游,酒店预订,星级酒店"/>
<meta name="description" content="驴妈妈港澳旅游专题:选择聪明的旅行!我们为您提供港澳星级酒店推荐,香港酒店预订,澳门酒店预订及实时比价服务，是您预订旅行产品,跟团游及自由行的最佳选择!"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/gangao/base.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/zt/gangao/index.css">
<link rel="stylesheet" href="http://www.lvmama.com/zt/000global/styles/zt_global.css" >
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="http://pic.lvmama.com/js/zt/gangao/gangao.js"></script>
<base target="_blank">
</head>

<body>
<!--专题公共头部START-->
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>
<!--专题公共头部END-->

<div class="banner">
    <span></span>
</div>
<ul class="main_nav">
    <li class="nav_li1"><a class="li_a1" target="_self" href="index.html">港澳门票</a></li>
    <li class="nav_li2 nav_bg"><a class="li_a2" target="_self" href="hotel.html">港澳酒店</a></li>
    <li class="nav_li3"><a class="li_a3" target="_self" href="ticket.html">相关票券</a></li>
    <li class="nav_li4"><a class="li_a4" target="_self" href="gty_sh.html">港澳跟团游</a></li>
    <li class="nav_li5"><a class="li_a5" target="_self" href="zyx_sh.html">港澳自由行</a></li>
</ul>

<div class="main_all">
    
<!--页面左侧-->
    <div class="main_left">
        <ul class="main_tab jd_tab">
            <@s.iterator value="map.get('${station}_jd')" status="st">
                <@s.if test="#st.isFirst()">
                    <li class="tab_bg"><span>${title?if_exists}</span></li>
                </@s.if>
                <@s.else>
                    <li><span>${title?if_exists}</span></li>
                </@s.else>
            </@s.iterator>
        </ul>

        <@s.iterator value="map.get('${station}_jd')" status="st">
            <@s.if test="#st.isFirst()">
        		<div class="main_list jd_list_top" style="display:block;">
                    <@s.iterator value="map.get('${station}_jd_${st.index + 1}')" status="sts">
                    	<dl class="jd_list">                        
                        	<dt>${title?if_exists}</dt>
                            <@s.iterator value="map.get('${station}_jd_${st.index + 1}_${sts.index + 1}')" status="stst">
                                <dd>
                                	<div class="jd_list_l">
                                    	<img src="${imgUrl?if_exists}" alt="">
                                        <span>${bakWord2?if_exists}</span>
                                    </div>
                                    <div class="jd_list_r">
                                    	<h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                                        <p>${bakWord3?if_exists}</p>
                                    </div>
                                    <span class="jd_jiage">￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                </dd>
                            </@s.iterator>
                        </dl>
                        <div class="gd clear">
            				<span class="gdbtn"></span>
            			</div>
                    </@s.iterator>
                </div>
            </@s.if>
            <@s.else>
                <div class="main_list jd_list_top">
                    <@s.iterator value="map.get('${station}_jd_${st.index + 1}')" status="sts">
                        <dl class="jd_list">                        
                            <dt>${title?if_exists}</dt>
                            <@s.iterator value="map.get('${station}_jd_${st.index + 1}_${sts.index + 1}')" status="stst">
                                <dd>
                                    <div class="jd_list_l">
                                        <img src="${imgUrl?if_exists}" alt="">
                                        <span>${bakWord1?if_exists}</span>
                                    </div>
                                    <div class="jd_list_r">
                                        <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                                        <p>${bakWord3?if_exists}</p>
                                    </div>
                                    <span class="jd_jiage">￥<b>${memberPrice?if_exists?replace(".0","")}</b>起</span>
                                </dd>
                            </@s.iterator>
                        </dl>
                        <div class="gd clear">
                            <span class="gdbtn"></span>
                        </div>
                    </@s.iterator>
                </div>
            </@s.else>
        </@s.iterator>
    </div>
    
<!--页面右侧-->
    <ul class="main_right">
    	<li>
         <a href="http://www.lvmama.com/product/53432
"><img src="http://pic.lvmama.com/img/zt/gangao/jd_pzsy.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/53432
">香港品质双园4天
</a><b>¥<span>3300</span></b>起</p>
        </li>
        <li>
         <a href="http://www.lvmama.com/dest/amyinhejiudian"><img src="http://pic.lvmama.com/img/zt/gangao/jd_amyinhe.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/68763">澳门银河酒店</a><b>¥<span>829</span></b>起</p>
        </li>
        <li>
         <a href="http://www.lvmama.com/product/70271
"><img src="http://pic.lvmama.com/img/zt/gangao/jd_jiulongfuhao.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/70271
">九龙富豪酒店4天 
</a><b>¥<span>3319</span></b>起</p>
        </li>
        <li>
         <a href="http://www.lvmama.com/product/65016
"><img src="http://pic.lvmama.com/img/zt/gangao/jd_xianggangdsn.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/65016
">迪士尼1日电子换票证
</a><b>¥<span>288</span></b>起</p>
        </li>
        <li>
         <a href="http://www.lvmama.com/product/70195
"><img src="http://pic.lvmama.com/img/zt/gangao/jd_taipingshan.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/70195
">太平山缆车双程+摩天台
</a><b>¥<span>48</span></b>起</p>
        </li>
        <!--<li>
         <a href="#"><img src="http://pic.lvmama.com/img/zt/gangao/mp_r_6.jpg" width="220" height="150" alt=""></a>
         <p><a href="#">香港迪士尼乐园酒店</a><b>¥<span>1717</span></b>起</p>
        </li>-->
    </ul>
    
<!--热门活动进行时-->
	<dl class="main_hd">
    	<dt>热门活动进行时</dt>
        <dd>
            <div class="list_box">
                <ul id="hd_list">
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/maerdaifu/" target="_blank">

                            <img width="284" height="135" src="http://pic.lvmama.com/img/zt/gangao/huodong2.jpg"></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/ouzhou/" target="_blank">
                            <img width="284" height="135" src="http://pic.lvmama.com/img/zt/gangao/huodong3.jpg"></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/riben_1/" target="_blank">
                            <img width="284" height="135" src="http://pic.lvmama.com/img/zt/gangao/huodong4.jpg"></a>

                    </li>
                    <li>
                        <a href="http://www.lvmama.com/activity/3/" target="_blank">
                            <img width="284" height="135" src="http://pic.lvmama.com/img/zt/gangao/huodong8.jpg"></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/dishini" target="_blank">
                            <img width="284" height="135" src="http://pic.lvmama.com/img/zt/gangao/huodong5.jpg"></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/dsn/" target="_blank">
                            <img width="284" height="135" src="http://pic.lvmama.com/img/zt/gangao/huodong6.jpg"></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/lvyou/zdf" target="_blank">
                            <img width="284" height="135" src="http://pic.lvmama.com/img/zt/gangao/huodong7.jpg"></a>
                    </li>
                    <li>
                        <a href="http://www.lvmama.com/zt/abroad/" target="_blank">
                            <img width="284" height="135" src="http://pic.lvmama.com/img/zt/gangao/huodong1.jpg"></a>
                    </li>
                </ul>
            </div>
            <span id="hd_left"></span>
            <span id="hd_right"></span>
        </dd>
    </dl>

</div>



<script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
