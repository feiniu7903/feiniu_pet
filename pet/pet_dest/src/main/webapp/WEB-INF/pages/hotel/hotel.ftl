<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@s.property value="seoIndexPage.seoTitle"/></title>
<meta name="keywords" content="<@s.property value="seoIndexPage.seoKeyword"/>">
<meta name="description" content="<@s.property value="seoIndexPage.seoDescription"/>">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/s2_detailV1_0.css,/styles/new_v/global.css,/styles/new_v/header-air.css,/styles/new_v/ui_plugin/calendar.css,/styles/new_v/ui_plugin/jquery-ui-1.8.17.custom.css,/styles/new_v/ob_comment/c_common.css,/styles/new_v/ob_comment/ty_style.css,/styles/new_v/ob_common/ui-components.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/orderV2.css,/styles/v4/modules/button.css,/styles/v4/modules/dialog.css"/>

<style type="text/css">
.hide{
	display:none;
}
td div{margin:0px auto;}
</style>

</head>
<body>
<!--===== 头部文件区域 S ======-->
<@s.set var="pageMark" value="'destHotel'" />
<#include "/WEB-INF/pages/common/setKeywor.ftl">
<!--===== 头部文件区域 E ======-->

<div class="main-container">
   
    <div class="s2-top-tips-hotel">驴妈妈旅游网致力为您提供最优惠、最舒心的酒店服务，预订前请阅读&nbsp;<a target="_blank" href="http://www.lvmama.com/public/help_269">国内酒店预订协议</a>

    </div>
<!--============= 产品商务区 S ================-->
	<div class="s2-pro-buzz-area">
	<div class="dtl_zyx_icon_jd" id="dtl_zyx"></div>
    	<!--图片展示区 S-->
    	
    	<div id="image_area" class="s2-slider s2-hotel">
    	<div class="l_slider">
    	
     <div id="DestpicFlow">
		<ul class="Slides">
		
			<@s.if test="place.placePhoto!=null && place.placePhoto.size()==0">
		    	<li style="display: none; "></li>
		    </@s.if>
		    <@s.else>
			    <@s.iterator value="place.placePhoto" var="photo">
			      <li style="display: block; "><img src="http://pic.lvmama.com<@s.property value="imagesUrl"/>" width="580" height="221" /></li>
			    </@s.iterator>
		    </@s.else>
		 </ul>
	</div>
	
	</div>
            <div class="hotel-info">
            	<h2>${place.name}&nbsp;
            	<@s.if test="place.hotelStar==@com.lvmama.comm.vo.Constant@HOTEL_STAR_LEVEL_5A"> <span class="full-star five-star-solid" title="五星级酒店"></span></@s.if>
            	<@s.elseif test="place.hotelStar==@com.lvmama.comm.vo.Constant@HOTEL_STAR_LEVEL_5B"> <span class="full-star five-star-hollow" title="豪华型酒店"></span></@s.elseif>
            	<@s.elseif test="place.hotelStar==@com.lvmama.comm.vo.Constant@HOTEL_STAR_LEVEL_4A"> <span class="full-star four-star-solid" title="四星级酒店"></span></@s.elseif>
            	<@s.elseif test="place.hotelStar==@com.lvmama.comm.vo.Constant@HOTEL_STAR_LEVEL_4B"> <span class="full-star four-star-hollow" title="品质型酒店"></span></@s.elseif>
            	<@s.elseif test="place.hotelStar==@com.lvmama.comm.vo.Constant@HOTEL_STAR_LEVEL_3A"> <span class="full-star three-star-solid" title="三星级酒店"></span></@s.elseif>
            	<@s.elseif test="place.hotelStar==@com.lvmama.comm.vo.Constant@HOTEL_STAR_LEVEL_3B"> <span class="full-star three-star-hollow" title="舒适型酒店"></span></@s.elseif>
            	<@s.elseif test="place.hotelStar==@com.lvmama.comm.vo.Constant@HOTEL_STAR_LEVEL_2A"> <span class="full-star two-star-solid" title="二星级酒店"></span></@s.elseif>
            	<@s.elseif test="place.hotelStar==@com.lvmama.comm.vo.Constant@HOTEL_STAR_LEVEL_2B"> <span class="full-star two-star-hollow" title="简约型酒店"></span></@s.elseif>
            	</h2>
                <p><@s.property value="place.remarkes" escape="false"/><a href="#pro_stitle">更多>></a></p>
            </div>
        </div>
    	<!--图片展示区 E-->
        
        <!--右侧商务属性区 S-->
        <div class="s2-pro-attr hotel-attr">
        	
        	<ul class="attribute">
              <li><strong>开业时间：</strong>${place.hotelOpenTime?if_exists}</li>
              <li><strong>酒店地址：</strong>${place.address?if_exists}</li>
              <li><strong>酒店电话：</strong>${place.hotelPhone?if_exists}</li>
              <!--<li><strong>酒店星级：</strong>${place.hotelStar?if_exists}</li>-->
              <li><strong>酒店类型：</strong>${place.hotelType?if_exists}</li>
              <li><strong>是否涉外：</strong>${place.hotelForeignerStr?if_exists}</li>
              <li><strong>房间数量(间)：</strong>${place.hotelRoomNum?if_exists}</li>
               <@s.if test ="placeCoordinateList!=null && placeCoordinateList.size()>0">
               <li><strong>周边景点：</strong><p>
               <@s.iterator value="placeCoordinateList" var="placeCoordinate">
               	<#if placeCoordinate.name??>
               	  <a  target="_blank" href="http://www.lvmama.com/search/hotel/-${placeCoordinate.name!""}.html">${placeCoordinate.name!""}</a>
				</#if>
               </@s.iterator>
              </p></li>
              </@s.if>

              <li class="traffic-info"><div><strong>交通信息：</strong>
              <@s.property value="place.hotelTrafficInfo" escape="false"/></div></li>
              
               <!--总体评价-->
              <@s.if test="cmtTitleStatisticsVO != null && cmtTitleStatisticsVO.commentCount!=0">
              		<li><strong>总体评价：</strong> 
              			<span class="com_StarValueCon"><s class="star_bg cur_def"><i class="ct_Star${cmtTitleStatisticsVO.roundHalfUpOfAvgScore}"></i></s></span>
              			<!--点评数-->
              			<a href="#hotelComment"><b class="cmtBonus">点评数：<@s.property value='cmtTitleStatisticsVO.commentCount'/></b></a>
              		</li>
              </@s.if>
                                      
            </ul>
        </div>
        <!--右侧商务属性区 E-->
        
    </div>

<div class="main main01">
		<@s.if test="place.importantTips!=null">
            <div class="pro_tagcontent3">
                <h3 id="pro_stitle" class="pro_stitle"><span><a href="javascript:scroll(0,0)" class="pro_gotop">回到顶部↑</a></span>公告</h3>
                <div class="hotel-dec">
                <@s.property value="place.importantTips" escape="false"/>
				</div>
            </div>
            </@s.if>
            
			<!--============= 产品商务区 E ================-->
			<#include "/WEB-INF/pages/hotel/productList.ftl">
            
            <@s.if test="place.feature!=null">
            <div class="pro_tagcontent3">
                <h3 id="pro_stitle" class="pro_stitle"><span><a href="javascript:scroll(0,0)" class="pro_gotop">回到顶部↑</a></span>酒店特色</h3>
                <div class="hotel-dec">
                <@s.property value="place.feature" escape="false"/>
				</div>
            </div>
            </@s.if>
            <@s.if test="place.orderNotice!=null">
            <div class="pro_tagcontent3">
                <h3 id="pro_stitle" class="pro_stitle"><span><a href="javascript:scroll(0,0)" class="pro_gotop">回到顶部↑</a></span>预订须知</h3>
                <div class="hotel-dec">
             <@s.property value="place.orderNotice" escape="false"/>
				</div>
            </div>
            </@s.if>
            <@s.if test="place.description!=null">
            <!--========= 酒店简介 S ===========-->
            <div class="pro_tagcontent3">
                <h3 id="pro_stitle" class="pro_stitle"><span><a href="javascript:scroll(0,0)" class="pro_gotop">回到顶部↑</a></span>酒店简介</h3>
                <div class="hotel-dec">
                   <@s.property value="place.description" escape="false"/>
				</div>
            </div>
            </@s.if>
            <!--========= 酒店简介 E ===========-->
            
            <!--========= 酒店周边景点 S ===========-->
            <div class="pro_tagcontent4 gmap">
                <h3 class="pro_stitle"><span><a href="javascript:scroll(0,0)" class="pro_gotop">回到顶部↑</a></span>酒店周边景点</h3>
                <div class="google-map">
                	<iframe width="506" scrolling="no" height="335" src="http://www.lvmama.com/dest/googleMap/getMapCoordinate!getMapCoordinate.do?id=<@s.property value="place.placeId"/>&amp;windage=0.005&amp;width=490px&amp;height=320px&amp;flag=2"></iframe>
                </div>
                <div class="hotel-list">
                	<@s.iterator value="placeList"> 
                		<div>
							<span class='marker'></span>
							<h4>[${placeProvince?if_exists}<#if placeProvince?? && placeCity??>.</#if>${placeCity?if_exists}]
							<a target="_blank" href='http://www.lvmama.com/dest/${pinYinUrl?if_exists}'>${name?if_exists}</a></h4>
							<img width="160" height="80" src='http://pic.lvmama.com/${smallImage?if_exists}'>
							<p><strong>景点特色：</strong>${remarkes?if_exists}</p>
							<p><strong>地址：</strong>${address?if_exists}</p>
						</div>
                	</@s.iterator>
                </div>
            </div>
            <!--========= 酒店周边景点 E ===========-->

            <!--========= 网友点评 S ===========-->
            <div class="pro_tagcontent5 hotel-comment">
            <a name="cmt1" id="cmt1"></a>
            	<h3 class="ty_title"><@s.property value="place.name"/></h3>
              	<div id="hotelComment">
					<#include "/WEB-INF/pages/hotel/listCmtsOfDest.ftl">
                </div>
                <div >
	                <p class="c_seemore B"> <a class="pane_moreCmt" href="http://www.lvmama.com/comment/<@s.property value="place.placeId"/>-1" target="_blank">查看更多点评&gt;&gt;</a></p>
	            </div >
            </div>
            
            <!--========= 网友点评 E ===========-->
            
            <!--========= 相关推荐产品 S ===========-->
            <div class="pro_tagcontent7">
                <h3 class="pro_stitle"><span><a href="javascript:scroll(0,0)" class="pro_gotop">回到顶部↑</a></span>相关推荐产品</h3>
                  
                  <div class="pro-commend hotel-about-pro" id="dest">
                    <ul class="pro-commend-tagmenu"><li class="current">打折门票</li><li>跟团游</li><li>自由行</li><li>同城酒店</li></ul>
                    <ul class="recommend_line clear" name="pro_list">
                    	<#list recommendProductList.productTicketList as productTicket>
                        <li>
                        	<p class="s2-price"><del>&yen;${productTicket.marketPriceInteger?if_exists}</del><strong>&yen;${productTicket.sellPriceInteger?if_exists}</strong><strong>奖金${productTicket.cashRefund?replace("￥","¥")}元</strong>
                        		<a title="${productTicket.productName?if_exists}立即预订" href="http://www.lvmama.com${productTicket.productUrl?if_exists}" target="_blank"><img src="http://pic.lvmama.com/img/icons/order_normal_btn.gif" alt="立即预订" /></a>
                        	</p>
                        	<a title="${productTicket.productName?if_exists}立即预订" href="http://www.lvmama.com${productTicket.productUrl?if_exists}" target="_blank">${productTicket.productName}</a>
                        </li>
                    	</#list>
                    </ul>
                    <ul class="recommend_line display-none" name="pro_list">
                    	<#list recommendProductList.productRouteList as productRoute>
                        <li>
                        	<p class="s2-price"><del>&yen;${productRoute.marketPriceInteger?if_exists}</del><strong>&yen;${productRoute.sellPriceInteger?if_exists}</strong><span>奖金${productRoute.cashRefund?if_exists}元</span>
                        		<a title="${productRoute.productName?if_exists}立即预订" href="http://www.lvmama.com${productRoute.productUrl}" target="_blank"><img src="http://pic.lvmama.com/img/icons/order_normal_btn.gif" alt="立即预订" /></a>
                        	</p>
                        	<a title="${productRoute.productName?if_exists}立即预订" href="http://www.lvmama.com${productRoute.productUrl}" target="_blank">${productRoute.productName?if_exists}</a>
                        </li>
                        </#list>
                    </ul>
                    <ul class="recommend_line display-none" name="pro_list">
                    	<#list recommendProductList.productSinceList as productSince>
                        <li>
                        	<p class="s2-price"><del>&yen;${productSince.marketPriceInteger?if_exists}</del><strong>&yen;${productSince.sellPriceInteger?if_exists}</strong><span>奖金${productSince.cashRefund?if_exists}元</span>
                        		<a title="${productSince.productName}立即预订" href="http://www.lvmama.com${productSince.productUrl}" target="_blank"><img src="http://pic.lvmama.com/img/icons/order_normal_btn.gif" alt="立即预订" /></a>
                        	</p>
                        	<a title="${productSince.productName}立即预订" href="http://www.lvmama.com${productSince.productUrl}" target="_blank">${productSince.productName}</a>
                        </li>
                        </#list>
                    </ul>
                    <ul class="recommend_line display-none" name="pro_list">
                    	<#list hotelCoordinateList as hotelCoordinate>
                        <li>
                        	<p class="s2-price"><del><#if hotelCoordinate.marketPriceInteger?if_exists!=0 >&yen;${hotelCoordinate.marketPriceInteger?if_exists}</#if></del><strong><#if hotelCoordinate.sellPriceInteger?if_exists!=0 >&yen;${hotelCoordinate.sellPriceInteger?if_exists}</#if></strong><span></span>
                        		<a title="${hotelCoordinate.name?if_exists}立即预订" href="http://www.lvmama.com/dest/${hotelCoordinate.pinYinUrl?if_exists}" target="_blank"><img src="http://pic.lvmama.com/img/icons/order_normal_btn.gif" alt="立即预订" /></a>
                        	</p>
                        	<a title="${hotelCoordinate.name?if_exists}立即预订" href="http://www.lvmama.com/dest/${hotelCoordinate.pinYinUrl?if_exists}" target="_blank">${hotelCoordinate.name?if_exists}</a>
                        </li>
                        </#list>
                    </ul>
                  </div>
            </div>
            <!--========= 相关推荐产品 E ===========-->
    </div>
  <!--============= 产品概况区 E ================-->
  </div>
</div>

<!--===== 底部文件区域 S ======-->
<#include "/WEB-INF/pages/common/footer/hotel_footer.ftl">
<!--===== 底部文件区域 E ======-->

<!--===== 立即预订弹出层 S ======-->
<div id="layerbg"></div>
<div id="layer_detail"><img src="http://pic.lvmama.com/img/icons/layer_close_btn.gif" alt="关闭" id="layer_close_btn" onclick="closeLayer('layerbg','layer_detail')" />
	<h3>公告</h3>
    <p>年龄段在23岁以下，或是54岁以上客人费用需另议,请来电 <strong>1010-6060</strong> 咨询</p>
    <div>
    	您选择的入住日期：2011-01-05    离店日期：2011-01-06   共1晚<br />
    	预订房间数：<span class="add-minus">
              <button class="button-add" type="button">-</button>
              &nbsp;<input type="text" class="input-add-minus" size="3" value="1">&nbsp;
              <button class="button-minus" type="button">+</button></span>
    </div>
    <div class="step-btn"><img src="http://pic.lvmama.com/img/icons/next_step.gif" alt="下一步" /></div>
    
</div>
<script type="text/javascript">
	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|hotel_2013|hotel_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
</script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/super_v2/detail_func.js,/js/new_v/ui_plugin/jquery.ui.datepicker-zh-CN.js,/js/new_v/ui_plugin/jquery-ui-1.8.17.custom.min.js,/js/new_v/ui_plugin/jquery-time-price-table.js,/js/dest_xu/dianj.js,/js/dest/destindex_slider.js"></script>
<script src="http://pic.lvmama.com/js/hotel/hotel_search.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js" ></script>
<#if !login>
	<#-- 未登录状态下需要显示快速登录层 S -->
	<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/login/rapidLogin.js" type="text/javascript"></script>
</#if>
<script type="text/javascript">
	function postToWb(){
		var _t = encodeURI(document.title);
		var _url = encodeURIComponent(document.location);
		var _appkey = encodeURI("09fab729da0a45b2bc22f2d69e16f70b");//你从腾讯获得的appkey
		var _pic = encodeURI('');//（例如：var _pic='图片url1|图片url2|图片url3....）
		var _site = '';//你的网站地址
		var _u = 'http://v.t.qq.com/share/share.php?url='+_url+'&appkey='+_appkey+'&site='+_site+'&pic='+_pic+'&title='+_t;
		window.open( _u,'', 'width=700, height=680, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, location=yes, resizable=no, status=no' );
	}

	
	//最近访问过的酒店信息以json数组方式写入cookiecount指定了json数组大小，即存入的json对象数量
	function setHotelCookie(count) {
		var minCount = 1;
		var maxCount = 5;
		if (count < minCount) {
			count = minCount;
		}
		if (count > maxCount) {
			count = maxCount;
		}
		var cookieName = "hotelCookie";
		var cookieValue = $.cookie(cookieName);
		var dates = cookieValue;
		var roundPlaceName='<#if placeCoordinateList??><#list placeCoordinateList as placeCoordinate>${placeCoordinate.name?if_exists},</#list></#if>';
		var date = "{image:'${place.smallImage?if_exists}',name:'${place.name?if_exists}',address:'${place.address?if_exists}',roundPlaceName:'"+roundPlaceName.substring(0,(roundPlaceName.length-1))+"',desc:'${place.desc?if_exists?replace('\n','')}',url:'" + window.location.href + "'}";
		var jsonDate = eval("("+date+")");
		var jsonDates = [];
		if (dates == null) {
			dates = "[" + date + "]";
		} else {
			jsonDates = eval(dates);
			if (jsonDates.length < count) {
				removeSame(jsonDates, jsonDate);
				addFirst(jsonDates, jsonDate);
				dates = toStr(jsonDates);
			} else {
				removeSame(jsonDates, jsonDate);
				addFirstRemoveLast(jsonDates, jsonDate);
				dates = toStr(jsonDates);
			}
		}
		cookieValue = dates;
		//document.write(cookieValue);
		$.cookie(cookieName, cookieValue, {path:'/', expires:1, domain:'lvmama.com'});
	}
	
	function removeSame(jsonDates, jsonDate) {
		if (jsonDates.length >= 1) {
			for (var i = 1; i < jsonDates.length; i++) {
				if (jsonDates[i].name == jsonDate.name) {
					if (i == jsonDates.length - 1) {
						jsonDates[i] = null;
					} else {
						for(var j = i; j < jsonDates.length - 1; j++) {
							jsonDates[j] = jsonDates[j + 1];
						}
					}
				}
			}
		}
	}
	
	function addFirstRemoveLast(jsonDates, jsonDate) {
		if (jsonDates[0].name != jsonDate.name) {
			for (var i = jsonDates.length; i > 1; i--) {
				jsonDates[i - 1] = jsonDates[i - 2];
			}
			jsonDates[0] = jsonDate;
		}
	}
	
	function addFirst(jsonDates, jsonDate) {
		if (jsonDates[0].name != jsonDate.name) {
			for (var i = jsonDates.length; i > 0; i--){
				jsonDates[i] = jsonDates[i - 1];
			}
			jsonDates[0] = jsonDate;
		}
	}
	
	function toStr(jsonDates) {
		var str = "[";
		if (jsonDates.length == 0) {
			str += "{";
			str += "id:'" + jsonDates[0].id + "',";
			str += "image:'" + jsonDates[0].image + "',";
			str += "name:'" + jsonDates[0].name + "',";
			str += "address:'" + jsonDates[0].address + "',";
			str += "roundPlaceName:'" + jsonDates[0].roundPlaceName + "',";
			str += "desc:'" + jsonDates[0].desc + "',"
			str += "url:'" + jsonDates[0].url + "'"
			str += "}";
		} else {
			str += "{";
			str += "id:'" + jsonDates[0].id + "',";
			str += "image:'" + jsonDates[0].image + "',";
			str += "name:'" + jsonDates[0].name + "',";
			str += "address:'" + jsonDates[0].address + "',";
			str += "roundPlaceName:'" + jsonDates[0].roundPlaceName + "',";
			str += "desc:'" + jsonDates[0].desc + "',"
			str += "url:'" + jsonDates[0].url + "'"
			str += "}";
			for (var i = 1; i < jsonDates.length; i++) {
				if (jsonDates[i] != null) {
					str += ",{";
					str += "id:'" + jsonDates[i].id + "',";
					str += "image:'" + jsonDates[i].image + "',";
					str += "name:'" + jsonDates[i].name + "',";
					str += "address:'" + jsonDates[i].address + "',";
					str += "roundPlaceName:'" + jsonDates[i].roundPlaceName + "',";
					str += "desc:'" + jsonDates[i].desc + "',"
					str += "url:'" + jsonDates[i].url + "'"
					str += "}";
				}
			}
		}
		str += "]";
		return str;
	}
$(document).ready(function(){
	$('span[class^="tags"]').ui('lvtip',{ 
		hovershow: 200
	});
	//setKeyword('<@s.property value="keyword" />');
	crumbEff(); 
	//checkCookie();
	//我的驴妈妈下拉	
	//headerSearch();
	//showMeunTool();
	//ticketSearch();
	//readyFun();
	
	setHotelCookie(2);
	
	TB.widget.SimpleSlide.decorate('DestpicFlow', {eventType:'mouse', effect:'fade'});
	
	$("#dest").chajian({pro_tagmenu:".pro-commend-tagmenu>li",pro_tagdetail:"ul[name='pro_list']"});
	
    $("#topicsUrl").hide();
    var startDate = new Date(Date.parse("2011/12/1"));
    var endDate = new Date(Date.parse("2012/1/1")); 
    var today = new Date();  
      
    var placeId = $(":hidden[name='cmtPlace.placePId']").val();
    if (today>startDate && today<endDate) {
        if(placeId=="154001" || placeId=="154186" || placeId=="151200" || placeId=="154287" || placeId=="154185" || placeId=="154635"){
            $("textarea[name='cmtPlace.content']").val('(晒)');
            $("#topicsUrl").show();
        }
    }
});
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
 
</body>
</html>
