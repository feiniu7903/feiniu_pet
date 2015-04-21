<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title><@s.property value="seoIndexPage.seoTitle"/></title>
<meta name="keywords" content="<@s.property value="seoIndexPage.seoKeyword"/>">
<meta name="description" content="<@s.property value="seoIndexPage.seoDescription"/>">
<link rel="stylesheet" href="http://s1.lvjs.com.cn/min/index.php?f=/styles/new_v/ob_testnt/reset.css,/styles/new_v/header-air.css,/styles/new_v/ob_testnt/domestic.css,/styles/new_v/ob_testnt/l_destination.css,/styles/new_v/ui_plugin/calendar.css,/styles/new_v/ui_plugin/jquery-ui-1.8.17.custom.css,/styles/new_v/ob_comment/c_common.css,/styles/v3/plugin.css,/styles/new_v/ob_common/ui-components.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/orderV2.css,/styles/v4/modules/button.css,/styles/v4/modules/dialog.css"/>
<script type="text/javascript">
  var curPlaceId='<@s.property value="id"/>';
  var pinyin='<@s.property value="currentPlace.pinYin"/>';
  var currentTab='${currentTab}';
</script>
<!--[if IE 6]>
<script type="text/javascript" src="http://pic.lvmama.com/js/zt/DD_belatedPNG.js" ></script>
<script type="text/javascript">
  var curPlaceId='<@s.property value="id"/>';
  var pinyin='<@s.property value="currentPlace.pinYin"/>';
   DD_belatedPNG.fix('.dstnt_focusinfo,.dstnt_focustit,.dstnt_focusinfo_arrow');
</script>
<![endif]-->
</head>

<body class="dstnt_body">
<!--头部 S-->
 <#include "/WEB-INF/pages/common/setKeywor.ftl">
 <!--头部 E-->
   <div class="dstnt_wrap">
   
        <div class="dstnt_left">
           <#include "/WEB-INF/pages/place/template_common/dest_left.ftl">         
        </div>
        <!--left-->
       
        <!--right-->
        <div class="dstnt_right">
	         <div class="hx-xindest">
	         <@s.if test="weeklyWeatherList!=null && weeklyWeatherList.size>0">
	        	 <h4 class="weather-title">${weeklyWeatherList[0].city?if_exists}最近几天天气情况</h4>
					<div class="weather-7days clearfix">
						<div class="weather-list">
							<#list weeklyWeatherList as dailyWeather>
							<div class="weather-item">
								<p>
									${dailyWeather.date?if_exists}</br>
									(${dailyWeather.week?if_exists})</br>
									<i class="ico-weather">
										<img src="${dailyWeather.imgPathDay?if_exists}" alt="${dailyWeather.imgTitleDay?if_exists}" />
										<img src="${dailyWeather.imgPathNight?if_exists}" alt="${dailyWeather.imgTitleNight?if_exists}" />
									</i>
									${dailyWeather.weather?if_exists}</br>
									${dailyWeather.temp?if_exists}</br>
									${dailyWeather.wind?if_exists}</br>
								</p>
							</div>
							</#list>
						</div>
					</div>
				</@s.if>
			</div>
			
		<div class="hx-xindest">
			<@s.if test="seoPersonDestList.size()>0"> 
			   <h4 class="line"><@s.property value="currentPlace.name"/>相关目的地天气</h4>
			   <ul class="recity-weather clearfix">
			    <@s.iterator value="seoPersonDestList" status="seo" >
			     <li><a href='http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>/weather'><@s.property value="name"/>天气</a></li>
			    </@s.iterator>
			   </ul>
   			</@s.if>
   		</div>
			
		<!--点评列表展示-->
			<div class="dstnt_recomend dstnt_mb10" id="dstnt_dpwhere">
				<h3 class="dstnt_jdtj"><@s.property value="currentPlace.name"/>点评</h3>
			</div> 
			<div class="c_w_score">
				 <#include "/WEB-INF/pages/hotel/listCmtsOfDest.ftl">
			</div>        
		     <div >
		     	<p class="c_seemore"><a rel="nofollow" href="http://www.lvmama.com/comment/<@s.property value="currentPlace.placeId"/>-1" target="_blank" class="fr gray">查看更多点评&gt;&gt;</a></p>      
			</div >
			<#include "/WEB-INF/pages/place/template_common/seoRecommend.ftl"/>
			
        </div>
        <!--right-->
        <#include "/WEB-INF/pages/place/template_common/seoInternallink.ftl">
        <#include "/WEB-INF/pages/common/footer/place_footer.ftl">
</div>
<script charset="utf-8" type="text/javascript" src="http://pic.lvmama.com/min/index.php?g=common"></script>
<script src="http://pic.lvmama.com/min/index.php?g=commonDest" type="text/javascript" charset="utf-8"></script>
<script charset="utf-8" type="text/javascript" src="http://pic.lvmama.com/min/index.php?g=hotel_autocomplete_new"></script>
<script charset="utf-8" type="text/javascript" src="http://pic.lvmama.com/min/index.php?g=destnt_js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/min/index.php?g=calendar" charset="utf-8"></script> 
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/ui_plugin/jquery-time-price-table.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js" ></script>
<#if !login>
	<#-- 未登录状态下需要显示快速登录层 S -->
	<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/login/rapidLogin.js" type="text/javascript"></script>
</#if>
<script type="text/javascript">
	getOrgInfo(<@s.property value="id"/>); //获取攻略信息
	
/**
 * 点评“有用”
 * @param {Object} varCommentId
 * @param {Object} obj
 * @param {Object} count
 */
 function xh_addClass(target,classValue) {
			var pattern = new RegExp("(^| )" + classValue + "( |$)"); 
			if (!pattern.test(target.className)) { 
				if(target.className ==" ") { 
					target.className = classValue; 
				}else { 
					target.className = " " + classValue; 
				} 
			} 
			$(target).addClass(classValue);
			return true; 
		}; 

function addUsefulCount(varUsefulCount,varCommentId,obj) {
	
	$.ajax({
		type: "post",
		url: "http://www.lvmama.com/comment/ajax/addUsefulCountOfCmt.do",
		data:{
			commentId: varCommentId
		},
		dataType:"json",
		success: function(jsonList, textStatus){
			 if(!jsonList.result){
			   alert("已经点过一次");
			 }else{
			 	xh_addClass(obj,"d_xing");
				var newUsefulCount = varUsefulCount + 1;
				obj.innerHTML= "<i>（<big>" + newUsefulCount + "</big>）</i>" ;
			 }
		}
	});
}

</script>
</body>
</html>
