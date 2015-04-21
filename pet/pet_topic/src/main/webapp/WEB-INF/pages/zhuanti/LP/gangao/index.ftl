<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>港澳旅游景点联票_港澳景点门票优惠_港澳景点电子门票-驴妈妈旅游网</title>
<meta name="keywords" content="港澳旅游,景点门票,电子票"/>
<meta name="description" content="驴妈妈港澳旅游专题:选择聪明的旅行!我们为您提供港澳旅游景点联票,港澳景点门票优惠,港澳景点电子门票推荐及实时比价服务，是您预订旅行产品,跟团游及自由行的最佳选择!"/>
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
    <li class="nav_li1 nav_bg"><a class="li_a1" target="_self" href="index.html">港澳门票</a></li>
    <li class="nav_li2"><a class="li_a2" target="_self" href="hotel.html">港澳酒店</a></li>
    <li class="nav_li3"><a class="li_a3" target="_self" href="ticket.html">相关票券</a></li>
    <li class="nav_li4"><a class="li_a4" target="_self" href="gty_sh.html">港澳跟团游</a></li>
    <li class="nav_li5"><a class="li_a5" target="_self" href="zyx_sh.html">港澳自由行</a></li>
</ul>

<div class="main_all">
    <ul class="main_tab">
        <@s.iterator value="map.get('${station}_mp')" status="st">
            <@s.if test="#st.isFirst()">
                <li class="tab_bg"><span>${title?if_exists}</span></li>
            </@s.if>
            <@s.else>
                <li><span>${title?if_exists}</span></li>
            </@s.else>
        </@s.iterator>
    </ul>
    
<!--页面左侧-->
    <div class="main_left">
        <@s.iterator value="map.get('${station}_mp')" status="st">
            <@s.if test="#st.isFirst()">
        		<div class="main_list" style="display:block;">
                    <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="680" height="272" alt=""></a>
                    <div class="jiage_title"><span>市场价</span><span>驴妈妈价</span></div>
                    <ul class="ul_list">
                        <@s.iterator value="map.get('${station}_mp_${st.index + 1}')" status="sts">
                            <li>
                            	<h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                                <p>
                                	<span><del>￥${marketPrice?if_exists?replace(".0","")}</del></span>
                                    <span class="yellow">￥<b>${memberPrice?if_exists?replace(".0","")}</b></span>
                                    <a href="${url?if_exists}"></a>
                                </p>
                            </li>
                        </@s.iterator>                      
                    </ul>
                    <div class="gd clear">
        				<span class="gdbtn"></span>
        			</div>
                </div>
            </@s.if>
            <@s.else>
                <div class="main_list">
                    <a href="${url?if_exists}"><img src="${imgUrl?if_exists}" width="680" height="272" alt=""></a>
                    <div class="jiage_title"><span>市场价</span><span>驴妈妈价</span></div>
                    <ul class="ul_list">
                        <@s.iterator value="map.get('${station}_mp_${st.index + 1}')" status="sts">
                            <li>
                                <h4><a href="${url?if_exists}">${title?if_exists}</a></h4>
                                <p>
                                    <span><del>￥${marketPrice?if_exists?replace(".0","")}</del></span>
                                    <span class="yellow">￥<b>${memberPrice?if_exists?replace(".0","")}</b></span>
                                    <a href="${url?if_exists}"></a>
                                </p>
                            </li>
                        </@s.iterator>                      
                    </ul>
                    <div class="gd clear">
                        <span class="gdbtn"></span>
                    </div>
                </div>
            </@s.else>
        </@s.iterator>        
    </div>
    
<!--页面右侧-->
    <ul class="main_right">
        <li>
         <a href="http://www.lvmama.com/product/66865"><img src="http://pic.lvmama.com/uploads/pc/place2/15091/1369720311898.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/66865">迪士尼乐园1日票</a><b>¥<span>260</span></b>起</p>
        </li>    	
        <li>
         <a href="http://www.lvmama.com/product/53316

"><img src="http://pic.lvmama.com/img/zt/gangao/mp_gangaopinzhiyry.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/53316

">港澳一日品质双园
 
</a><b>¥<span>3600</span></b>起</p>
        </li>
        <li>
         <a href="http://www.lvmama.com/product/38440"><img src="http://pic.lvmama.com/img/zt/gangao/tianji100.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/38440">天际100观景台

</a><b>¥<span>99</span></b>起</p>
        </li>

        <li>
         <a href="http://www.lvmama.com/product/65454

"><img src="http://pic.lvmama.com/img/zt/gangao/mp_dsn.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/65454

">迪士尼好莱坞酒店

</a><b>¥<span>1348</span></b>起</p>
        </li>
		<li>
         <a href="http://www.lvmama.com/product/47847

"><img src="http://pic.lvmama.com/img/zt/gangao/mp_haiyanggyuan.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/47847

">港澳海洋公园精选游

</a><b>¥<span>3230</span></b>起</p>
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
