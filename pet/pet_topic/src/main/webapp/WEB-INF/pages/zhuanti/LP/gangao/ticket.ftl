<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>港澳旅游优惠券_香港景点门票优惠_澳门景点门票优惠-驴妈妈旅游网</title>
<meta name="keywords" content="港澳旅游,优惠券,景点门票"/>
<meta name="description" content="驴妈妈港澳旅游专题:选择聪明的旅行!我们为您提供港澳旅游优惠券,香港景点门票优惠,澳门景点门票优惠及实时比价服务，是您预订旅行产品,跟团游及自由行的最佳选择!"/>
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
    <li class="nav_li2"><a class="li_a2" target="_self" href="hotel.html">港澳酒店</a></li>
    <li class="nav_li3 nav_bg"><a class="li_a3" target="_self" href="ticket.html">相关票券</a></li>
    <li class="nav_li4"><a class="li_a4" target="_self" href="gty_sh.html">港澳跟团游</a></li>
    <li class="nav_li5"><a class="li_a5" target="_self" href="zyx_sh.html">港澳自由行</a></li>
</ul>

<div class="main_all">
<!--页面左侧-->
    <div class="main_left">
		<div class="ga-free-pro piaoquan">
			<@s.iterator value="map.get('${station}_pq')" status="st">
				<h5 class="ga3tit ga3tit_top"><span><em>${title?if_exists}</em></span></h5>
				<dl class="ga3list">					
					<dt>
						<span class="ga3-t1"></span>
						<span class="ga3-t2">市场价</span>
						<span class="ga3-t3">驴妈妈价</span>
					</dt>
					<@s.iterator value="map.get('${station}_pq_${st.index + 1}')" status="sts">
						<dd>
							<p class="ga3-pr1">
								<a href="${url?if_exists}">${title?if_exists}</a>
							</p>
							<span class="ga3-pr2">
								<del>￥${marketPrice?if_exists?replace(".0","")}</del>
							</span>
							<span class="ga3-pr3">￥${memberPrice?if_exists?replace(".0","")}起</span>
							<span class="ga3-pr4">
								<a href="${url?if_exists}" class="bookbtn"></a>
							</span>
						</dd>
					</@s.iterator>
				</dl>
				
				<div class="gd">
					<a class="gdbtn zhankai" href="javascript:void(0)" target="_self"></a>	
				</div>
			</@s.iterator>
		</div>
    </div>
    
<!--页面右侧-->
    <ul class="main_right">
    	<li>
         <a href="http://www.lvmama.com/dest/amjinsha"><img src="http://pic.lvmama.com/img/zt/gangao/pj_amjinsha.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/67207">澳门金沙酒店</a><b>¥<span>1049</span></b>起</p>
        </li>
        <li>
         <a href="http://www.lvmama.com/product/34258
"><img src="http://pic.lvmama.com/img/zt/gangao/pj_weiqingxuan.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/34258
">香港4天伟晴轩酒店
</a><b>¥<span>3040</span></b>起</p>
        </li>
        <li>
         <a href="http://www.lvmama.com/product/53649
"><img src="http://pic.lvmama.com/img/zt/gangao/pj_yayi.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/53649
">香港4天雅逸酒店</a><b>¥<span>2909</span></b>起</p>
        </li>
        <li>
         <a href="http://www.lvmama.com/product/67444
"><img src="http://pic.lvmama.com/img/zt/gangao/pj_yayijiudian2.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/67444
">雅逸酒店
</a><b>¥<span>600</span></b>起</p>
        </li>
        <li>
         <a href="http://www.lvmama.com/product/67441
"><img src="http://pic.lvmama.com/img/zt/gangao/pj_jianshzui.jpg" width="220" height="150" alt=""></a>
         <p><a href="http://www.lvmama.com/product/67441
">尖沙咀帝国酒店</a><b>¥<span>820</span></b>起</p>
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
