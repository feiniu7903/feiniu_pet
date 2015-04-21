<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title><@s.property value="prodProduct.productName" escape="false"/>_驴妈妈团购网</title>
<meta name="description" content="驴妈妈旅游团购 打折门票团购 自由行线路团购 跟团游|<@s.property value="productInfo.prodCProduct.to.name"  escape="false"/>购物|<@s.property value="productInfo.prodCProduct.to.name"  escape="false"/>团购|<@s.property value="productInfo.prodCProduct.to.name"  escape="false"/>打折" />
<meta name="keywords" content="驴妈妈团购网, <@s.property value="productInfo.prodCProduct.to.name"  escape="false"/>, <@s.property value="productInfo.prodCProduct.to.name"  escape="false"/>驴妈妈团购网，<@s.property value="productInfo.prodCProduct.to.name"  escape="false"/>购物，<@s.property value="productInfo.prodCProduct.to.name"  escape="false"/>团购，<@s.property value="productInfo.prodCProduct.to.name"  escape="false"/>打折，团购，打折，精品消费，购物指南，消费指南" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/v7style/globalV1_0.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/group.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_tg/lvtg.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/button.css,/styles/v4/modules/dialog.css"/>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"  charset="utf-8"></script>
<script src="http://pic.lvmama.com/js/new_v/top/header-air_new.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript" src="/js/tuan.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/ob_tg/tg.js"  charset="utf-8"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js" ></script>
<#if !login>
	<#-- 未登录状态下需要显示快速登录层 S -->
	<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/login/rapidLogin.js" type="text/javascript"></script>
</#if>
<style type=text/css>.input-date { 
BORDER-BOTTOM: #e290ba 1px solid; BORDER-LEFT: #e290ba 1px solid; BACKGROUND: url(http://s1.lvjs.com.cn/img/super_v2/cal_ui.gif) #fff no-repeat right center; BORDER-TOP: #e290ba 1px solid; BORDER-RIGHT: #e290ba 1px solid;color:#666; 
} 
.cal-ui { 
POSITION: absolute; MARGIN: 5px 12px 0px 2px; WIDTH: 17px; BACKGROUND: url(http://s2.lvjs.com.cn/img/super_v2/cal_ui.gif) no-repeat 0px 0px; HEIGHT: 16px; CURSOR: pointer; RIGHT: 15px; _right: 37px 
} 
.calendar-ui { 
Z-INDEX: 999; BORDER-BOTTOM: #c06 1px solid; POSITION: absolute; BORDER-LEFT: #c06 1px solid; WIDTH: 225px; BORDER-TOP: #c06 1px solid; BORDER-RIGHT: #c06 1px solid 
} 
.calendar-ui .cal-left-btn { 
POSITION: absolute; CURSOR: pointer 
} 
.calendar-ui .cal-left-btn-c { 
POSITION: absolute; CURSOR: pointer 
} 
.calendar-ui .cal-right-btn { 
POSITION: absolute; CURSOR: pointer 
} 
.calendar-ui .cal-right-btn-c { 
POSITION: absolute; CURSOR: pointer 
} 
.calendar-ui .cal-left-btn { 
LEFT: 0px 
} 
.calendar-ui .cal-left-btn-c { 
LEFT: 0px 
} 
.calendar-ui .cal-right-btn { 
RIGHT: 0px 
} 
.calendar-ui .cal-right-btn-c { 
RIGHT: 0px 
} 
.calendar-ui TABLE { 
BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BACKGROUND-COLOR: #fff; WIDTH: 225px; BORDER-TOP: medium none; BORDER-RIGHT: medium none 
} 
.calendar-ui TABLE THEAD TR TH { 
POSITION: relative; TEXT-ALIGN: center; BACKGROUND-COLOR: #cd0568; WIDTH: 14%; HEIGHT: 25px; COLOR: #fff; FONT-SIZE: 14px; FONT-WEIGHT: bold 
} 
.calendar-ui TABLE THEAD TR TH STRONG { 
WIDTH: 45px; DISPLAY: inline-block; COLOR: #fff; FONT-WEIGHT: bold 
} 
.calendar-ui TABLE TBODY TH { 
TEXT-ALIGN: center; PADDING-BOTTOM: 3px; BACKGROUND-COLOR: #e8e3e7; PADDING-LEFT: 3px; WIDTH: 14%; PADDING-RIGHT: 3px; HEIGHT: 22px; FONT-WEIGHT: 100; PADDING-TOP: 3px 
} 
.calendar-ui TABLE TBODY TR TD { 
BORDER-BOTTOM: #ccc 1px solid; BORDER-RIGHT: #ccc 1px solid 
} 
.calendar-ui TABLE TBODY TR TD { 
TEXT-ALIGN: center; PADDING-BOTTOM: 3px; LINE-HEIGHT: 15px; PADDING-LEFT: 3px; WIDTH: 14%; PADDING-RIGHT: 3px; HEIGHT: 22px; COLOR: #999; FONT-SIZE: 12px; VERTICAL-ALIGN: top; PADDING-TOP: 3px 
} 
.calendar-ui TABLE TBODY TR TD.first { 
BORDER-LEFT: #ccc 1px solid 
} 
.calendar-ui TABLE TBODY TR TD.p_has { 
COLOR: #000 
} 
.calendar-ui TABLE TBODY TR TD SPAN { 
COLOR: #999; FONT-WEIGHT: 100 
} 
.calendar-ui TABLE TBODY TR TD.has-pro { 
DISPLAY: block; COLOR: #05e; CURSOR: pointer; FONT-WEIGHT: bold 
} 
.calendar-ui TABLE TBODY TR TD EM { 
COLOR: #666; FONT-WEIGHT: bold 
} 
.calendar-ui TABLE TBODY TR TD FONT { 
FONT-WEIGHT: bold 
} 
.calendar-ui TABLE TBODY TR TD I { 
FONT-STYLE: normal; FONT-WEIGHT: bold 
} 
</style>
<#include "/common/coremetricsHead.ftl">
</head>
<body id="detail-body" class="tuangou">
<script>
	var conextPath='';
</script>
<@s.if test="diff>15">
	<script type="text/javascript" src="/js/clock.js"></script>
</@s.if>
<#include "/common/header.ftl">
<!--Content Area-->
<ul class="hh_sub_recom clearfix">
	<li><i class="hh_icon_now"></i>当前城市：</li>
    <li><dl class="hh_now_place" id="hh-now-place">
			<dt><strong class="hh_now_city">${fromPlaceName}<i class="hh_icon"></i></strong></dt>
			<dd class="hh_now_city_group">
				<p style="overflow: hidden;zoom:1;">
                    <a href="javascript:switchIndex('SH','79','上海');">上海</a>
                    <a href="javascript:switchIndex('BJ','1','北京');" >北京</a>
                    <a href="javascript:switchIndex('CD','279','成都');" >成都</a>
                    <a href="javascript:switchIndex('GZ','229','广州');" >广州</a>
                </p>
                <p style="overflow: hidden;zoom:1;">
                    <a href="javascript:switchIndex('HZ','100','杭州');" >杭州</a>
                    <a href="javascript:switchIndex('NJ','82','南京');" >南京</a>
                    <a href="javascript:switchIndex('SZ','231','深圳');" >深圳</a>
                </p>
			</dd>
		</dl>
		<form method="post" id="switchIndexForm" action="http://www.lvmama.com/tuangou">
			<input type="hidden" name="fromPlaceCode" id="fromPlaceCode" value="${fromPlaceCode}" />
			<input type="hidden" name="fromPlaceId" id="fromPlaceId" value="${fromPlaceId}" />
			<input type="hidden" name="fromPlaceName" id="fromPlaceName" value="${fromPlaceName}" />
		</form>
    <!--now_place end-->
    </li>    
    <#-- 
    <li>
   <span class="lvtg_headtxt">驴妈妈旅游团购：最专业、最保障、最新颖、最实惠的旅游团购！</span>
    </li>
    -->
    <li class="lvtg_headR">
        <a href="http://www.lvmama.com/tuangou/" class="lvtg_link_blue">往期团购</a>
        <a href="http://www.lvmama.com/edm/showSubscribeEmail.do" class="lvtg_email" target="_blank">邮件订阅</a>
    </li>
</ul><!--hh_sub_recom end-->

<div class="shortinner">
	<!--Ad-->
   <#include "/WEB-INF/pages/group/include/ad.ftl" />
    <!--leftContent-->
    <div class="gropu-l-c">
    	<!---product-->
        <div class="group-bor">
        	<div class="group-pro">
                <h1>
               <#include "/WEB-INF/pages/group/include/guide.ftl" />
                • <@s.property value="productInfo.prodCProduct.to.name"  escape="false"/></strong><@s.property value="prodProduct.productName"  escape="false"/></h1>
                <p class="recomment-icon">推荐语：
	                <@s.if test="productInfo.viewPage.contents.get('IMPORTMENTCLEW')!=null">
	                		<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(productInfo.viewPage.contents.get('IMPORTMENTCLEW').content,55)"  escape="false"/>
	                </@s.if>
                </p>
                <@s.if test="tags.size>0">
                <p class="lable-icon">标签：
					 <@s.iterator value="tags" status="tag">
					 		<@s.if test="${tag.index==0}">
					 			${TAG_NAME}
					 		</@s.if>
					 		<@s.if test="${tag.index&gt;0}">
					 			;${TAG_NAME}
					 		</@s.if>
					 </@s.iterator>
				</p>
                </@s.if>
                <!--leftContent-->
                <div class="g-c-l">
                	<div class="group-infor">
                    	
	                    <@s.if test="diff>15">
							<input name="visitTimeQuick" id="visitTimeQuick" class="input-date" type="hidden" />
							<p class="price-look group-bg"><span>&yen;${prodProduct.sellPriceYuan}</span>
							<!--不定期-->
							<@s.if test="prodProduct.IsAperiodic() && prodProductBranch!=null">
								<button class="tuan-btn no-time-price" data-bid="${prodProductBranch.prodBranchId}">购买</button>
							</@s.if>
							<@s.else>
								<button class="tuan-btn" id="tuan_buy_btn">购买</button>
							</@s.else>
							</p>
		        		</@s.if>
		        		<@s.else>
							<p class="price-look group-bg"><span>&yen;${prodProduct.sellPriceYuan}</span><a href="#" class="buy-over" >结束</a></p>
			        		</p>
		        		</@s.else>
                        <ol>
                        	<@s.if test="prodProduct.IsAperiodic() && prodProductBranch!=null">
                        		<li>有效期：<em>${prodProductBranch.validBeginTime?string('yyyy-MM-dd')}至${prodProductBranch.validEndTime?string('yyyy-MM-dd')}</em>
                        			<@s.if test='prodProductBranch.invalidDateMemo != null && prodProductBranch.invalidDateMemo != ""'>
										(${prodProductBranch.invalidDateMemo})
									</@s.if>
                        		</li>
                        	</@s.if>
							
							<#-- <li><b><@s.if test="${discount<10}">${discount}</@s.if><@s.else>无</@s.else></b><big>折</big></li> -->
                        	<li>市场价：<del>&yen;${prodProduct.marketPriceYuan}</del></li>
                            <li>节省：<small>&yen;</small><big>${prodProduct.marketPriceYuan-prodProduct.sellPriceYuan}</big></li>
							
                            <@s.if test="prodProduct.IsAperiodic() && prodProductBranch!=null">
                            	<font color="red">购买本产品无需选择游玩时间</font>
                            </@s.if>	
                        </ol>
                        
                        <@s.iterator value="productInfo.productCouponList" status="coupon">
	                        <p class="infor-list"><span class="group-bg"></span>${couponName}</p>
						</@s.iterator>
              			<!--<#include "/WEB-INF/pages/group/include/shareweibo.ftl" />-->
                    </div>
                    <!--otherBg-->
					<p class="other-bgcolor"><span class="yiqiang"><big>${orderCount}</big>人已抢购</span>还剩:<@s.if test="diff<172800000"><span class="deal-timeleft"  diff="<@s.property  value="diff"/>"  id="counter"></@s.if> <@s.else>三天以上</@s.else></span></p>
                    
                    <!--otherBg-->
                    <!--<div class="other-bgcolor">
                    	<p class="bug-cart"><strong>${orderCount}</strong>人已购买成功</p>
                        <p>
                        <@s.if test="prodProduct.groupMin-orderCount<=0 ">
                			<@s.if test="diff>15">
									团购成功！ 还可以继续购买
							</@s.if>
                		</@s.if>
                		<@s.else>
                			<@s.if test="diff>15">
	                			还需${prodProduct.groupMin-orderCount}人，本团购成功</p>
	                			赶紧邀请您的朋友来参加吧
	                			<#include "/WEB-INF/pages/group/progressbar.ftl"/>
	                		</@s.if>
                		</@s.else>
                       
                    </div>-->
                </div>
                <!--rightContent-->
                <div class="g-c-r">
                	<!--显示图片-->
                	<@s.if test="productInfo.comPictureList.size>0">
	                		<div class="scrollDiv">
								<ul class="scrollNum">
									<@s.iterator value="productInfo.comPictureList" status="comPic">
										<li <@s.if test="#comPic.index==0"> class="curNumLI"</@s.if> >${comPic.index+1}</li>
									</@s.iterator>
								</ul>
								<ul class="scrollImg">
									<@s.iterator value="productInfo.comPictureList" status="comPic">
										<li <@s.if test="#comPic.index==0"> class="curImgLI" </@s.if> ><img width=512 height=256 src="${absolute580x290Url}" alt="${productName}" /></li>
									</@s.iterator>
								</ul>
							</div>
                	</@s.if>
					
					<span class="bd_btn_box">
                            <samp class="bd_btn">我要分享<dfn class="jt_ud"></dfn></samp>
                            <div id="bdshare" class="bd_btn_list bdshare_t bds_tools get-codes-bdshare">
                                <a class="bds_qzone">QQ空间</a>
                                <a class="bds_tsina">新浪微博</a>
                                <a class="bds_tqq">腾讯微博</a>
                                <a class="bds_renren">人人网</a>
                                <a class="bds_t163">网易微博</a>
                            </div>
                        </span>
                        <script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=162654" ></script>
						<script type="text/javascript" id="bdshell_js"></script>
                        <script type="text/javascript">
                        document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
                        </script>
                        <script>
						$(function(){ 
							$('.bd_btn_box').hover(function(){ 
								$(this).find('.bd_btn').addClass('bd_btn_hover');
								$(this).find('.bd_btn_list').show();
							},function(){ 
								$(this).find('.bd_btn').removeClass('bd_btn_hover');
								$(this).find('.bd_btn_list').hide();
							});
							
							$('.bd_btn_list li').hover(function(){ 
								$(this).addClass('this_bg');
							},function(){ 
								$(this).removeClass('this_bg');
							});
						});
						
						</script>
					
 					
                   	
                </div>
                <p class="clear"></p>
            </div>
        </div>
        <!---recommend-->
        <div class="other-recommend">
			<@s.if test="productInfo.viewPage.contents.get('FEATURES')!=null">
     			${productInfo.viewPage.contents["FEATURES"].content}
     		</@s.if>
        </div>
        <!--list-->
        <table class="list-table">
        	<thead>
            	<tr>
                	<th>参团人</th>
                    <th>产品名称</th>
                    <th>出价</th>
                    <th>购买数量</th>
                    <th>成交时间</th>
                    <th>状态</th>
                </tr>
            </thead>
            <tbody id="joinUsersBody"> 	
            
            </tbody>
        </table>
        <script>
        	var productId = ${productId};
        	var pageSize = 5;
        	var page=1;
        		ajaxQueryJoinGroupUsers(pageSize,page,productId);
        </script>
        <!--PK-->
        
        <!--PK-->
        <div class="group-o-b">
        	<h5>你可能会喜欢</h5>
            <ul class="maybe-lover" id="maybe_lover">
            </ul>
        </div>
    </div>
    <!--rightContent-->
    <div class="gropu-r-c">
    	<div class="gropu-r-bg">
			<ul class="bottom-border">
                <li>
					<@s.if test="productInfo.viewPage.contents.get('MANAGERRECOMMEND')!=null">
                		${productInfo.viewPage.contents["MANAGERRECOMMEND"].content}
                	</@s.if>
				</li>
            </ul>
			
            <h4>客户服务</h4>
            <ul class="bottom-border">
                <li>服务时间：8:00-24:00</li>
                <li>客服热线：<strong><@s.if test="showDifferntHotLine != null && showDifferntHotLine != ''">4006-040-${showDifferntHotLine?if_exists}</@s.if><@s.else>1010-6060</@s.else></strong></li>
                <li>客服传真：021-69108793</li>
                <li>邮箱地址：<a href="mailto:service@lvmama.com">service@lvmama.com</a></li>
            </ul>
            <!--今日其他团购-->
            <#include "/WEB-INF/pages/group/include/todayOtherGroupPrd.ftl" >
            <h4>关注我们</h4>
            <ul class="bottom-border list-last">
                <li class="icon-sina"><a href="http://t.sina.com.cn/lvmamas">请关注新浪微博lvmama主页</a></li>
                <li class="icon-qq"><a href="http://t.qq.com/lvmamalvyou">请关注腾讯微博lvmama主页</a></li>
                <li class="icon-kai"><a href="http://www.kaixin001.com/lvmama">请关注开心微博lvmama主页</a></li>
                <li class="icon-dou"><a href="http://www.douban.com/people/51704419/">请关注豆瓣微博lvmama主页</a></li>
            </ul>
        </div>  
       	<#include "/WEB-INF/pages/group/include/business.ftl" />
    </div>
    <!--clear-->
    <p class="clear"></p>
</div>
<!--background-->
<div class="group-body-bg"></div>
<!--popup-->
<div class="popup-email">
	<span class="group-bg" >关闭</span>
	<p><strong>梦想成功，请邮件通知我！</strong></p>
    <input type="text" value="邮箱地址" class="email-input" />
    <a class="group-bg" href="#">确认提交</a>
</div>
<!--Js Area-->
	<div style="width:980px;margin:0 auto 5px;">
		<#include "/WEB-INF/pages/group/include/group_footer.ftl">
	</div>

<script>
    //刷新你可能喜欢数据区域
    <@s.if test="prodProduct.isHotel() && prodProduct.subProductType!='SINGLE_ROOM'">
        $("#maybe_lover").empty();
        var rowHTML = "";
        <@s.if test="placeCoordinateHotel!=null&&!placeCoordinateHotel.isEmpty()">
        <@s.iterator value="placeCoordinateHotel" status="sts">
        <@s.if test="#sts.index<6">
            rowHTML = rowHTML + "<li><a href='http://www.lvmama.com/dest/${placePinYinUrl}' title='${placeName}'> ${placeName}</a></li>";
        </@s.if>
        </@s.iterator>
        </@s.if>
        if(rowHTML!=""){
            $("#maybe_lover").append(rowHTML);  
        }
    </@s.if>
    <@s.else>
        ajaxQueryEnjoyPrdList(${productId},5);
    </@s.else>
</script>	
	
 <script language="javascript">
$(document).ready(function () {
	$(".list-table").find("tbody tr:odd").addClass("even-td");
});


$(function(){
		var numb = $(".scrollNum li").size(),xx=0;

		$(".scrollNum li").mouseover(function(num){
			num = $(this).index();
			xx = num;
			$(".scrollImg li").eq(num).fadeIn(600).siblings().hide();
			$(this).addClass("curNumLI").siblings().removeClass("curNumLI");
		});
		function scrollIMG() {
			$(".scrollImg li").eq(xx).fadeIn(600).siblings().hide();
			$(".scrollNum li").eq(xx).addClass("curNumLI").siblings().removeClass("curNumLI");
			xx+=1;
			if(xx>numb) xx=0;
		}
		setInterval(scrollIMG,3000);
		
		//不定期产品无需选择游玩日期,不展示时间价格表,直接判断是否可售,转向产品详情页
		$(".no-time-price").live("click", function() {
			var bid = $(this).attr('data-bid');
			var fill_url = "http://www.lvmama.com/buy/fill.do?buyInfo.prodBranchId=" + bid+"&buyInfo.channel=TUANGOU";
			<#if login>
					window.location.href=fill_url;
        	<#else>
            	showLogin(function(){window.location.href=fill_url;});
        	</#if>
		});
	});
</script>
<!--xx-->
<@s.if test="diff>15 && prodProductBranch != null">
	<#include "/WEB-INF/pages/group/include/calendar.ftl">
</@s.if>

<script type="text/javascript">
	  function switchIndex(placeCode,placeId,placeName){
	        var $switchIndexForm = $('#switchIndexForm');
	        $('#fromPlaceCode').val(placeCode);
	        $('#fromPlaceId').val(placeId);
	        $('#fromPlaceName').val(placeName);
	        $switchIndexForm.submit();
	   }
</script>
		<script>
        	cmCreatePageviewTag("团购产品详情页_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(prodProduct.productName)"  escape="false"/>", "N0001", null, null);
        	cmCreateProductviewTag("${prodProduct.productId?if_exists}","<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(prodProduct.productName)"  escape="false"/>","${prodProduct.subProductType?if_exists}");
        </script>
</body>
</html>
