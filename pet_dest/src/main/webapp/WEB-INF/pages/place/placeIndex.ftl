<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<#include "/WEB-INF/pages/place/newpc_tdk.ftl" />
<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_testnt/reset.css,/styles/new_v/header-air.css,/styles/new_v/ob_testnt/domestic.css,/styles/new_v/ob_testnt/l_destination.css,/styles/new_v/ui_plugin/calendar.css,/styles/new_v/ui_plugin/jquery-ui-1.8.17.custom.css,/styles/new_v/ob_comment/c_common.css,/styles/v3/plugin.css,/styles/new_v/ob_common/ui-components.css"/>
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
<link rel="canonical" href="http://www.lvmama.com/dest/${place.pinYinUrl?if_exists}" />
<#include "/WEB-INF/pages/common/coremetricsHead.ftl">
</head>

	<body class="dstnt_body">
		<!--头部 S-->
		<@s.set var="pageMark" value="'destPlace'" />
 		<#include "/WEB-INF/pages/common/setKeywor.ftl">
		<!--头部 E-->
   		<div class="dstnt_wrap">
	        <!-- left S-->
	        <div class="dstnt_left">
	           <#include "/WEB-INF/pages/place/template_common/dest_left.ftl">         
	        </div>
        	<!--left E-->
       
        	<!--right S-->
        	<div class="dstnt_right">
	         	<@s.if test='"true"==maps'>
		        	<div class="hx-xindest">
						<h4><@s.property value="currentPlace.name"/>地图</h4>
						<div class="xh_map"><iframe src="http://www.lvmama.com/dest/googleMap/getMapCoordinate!getMapCoordinate.do?id=<@s.property value="currentPlace.placeId"/>&windage=0.005&width=690px&height=270px&flag=2" width="702" height="287" scrolling="no"></iframe></div>
						<@s.if test="seoDestList.size() > 0"> 
							<h4 class="line"><@s.property value="currentPlace.name"/>相关旅游地图</h4>
							<ul class="recity-weather clearfix">
								<@s.iterator value="seoDestList" status="seo" >
									<li><a href='http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>/maps'><@s.property value="name"/>地图</a></li>
								</@s.iterator>
							</ul>
						</@s.if>
					</div>
	        	</@s.if>
	        	<@s.else>        
		            <div class="dstnt_focusbox">
		                <ul class="dstnt_focusimg">
		                   <@s.iterator value="specialRecommendationList" status="prd" >
								<li><a href="<@s.property  value="productUrl"/>" title="<@s.property value="productName"/>" target="_blank"><img  width="444" height="222" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" to="${largeImageUrl?if_exists}" alt="${productName?if_exists}"></a></li>
							</@s.iterator>
		                </ul> 
		                <div class="dstnt_focusinfo">
		                	<div class="dstnt_focusinfo_arrow"></div>
		                   		<@s.iterator value="specialRecommendationList" status="prd" >
		                   			<div class="dstnt_focusinfo_inr">
			                   			<div class="dstnt_focus_info_top">
		                           			<a class="dstnt_focus_infotit" title="<@s.property value="productName"/>" href="<@s.property  value="productUrl"/>" target="_blank">${productName?if_exists}</a>
				                            <p class="dstnt_focus_dtl" title="<@s.property value="recommendReason"/>">
				                            	<@s.if test="recommendReason!=null&&recommendReason!=''"><strong>推荐理由：</strong><@s.property value='@com.lvmama.comm.utils.StringUtil@subStringStr(recommendReason,50)' escape="false" /></@s.if>
				                            </p>
		                        		</div>
		                        		<div class="dstnt_focus_pricebox">
		                             		<p>驴妈妈价：<span class="dstnt_focus_price"><i>￥</i><em>${sellPriceInteger?if_exists}</em>起</span></p>
		                             		<p class="dstnt_font_grey">市场价：<del>￥${marketPriceInteger?if_exists}</del></p>
		                        		</div>
		                   			</div>
		                   		</@s.iterator>
		                	</div>
		            	</div><!--focusimg--><!--目的地焦点图-->
	
						<!--right prod start-->
						<@s.if test='currentPlace.template=="template_zhongguo"'>
							<#include "/WEB-INF/pages/place/template_zhongguo/destIndexRight.ftl" />
						</@s.if>
						<@s.if test='currentPlace.template=="template_abroad"'>
							<#include "/WEB-INF/pages/place/template_abroad/destIndexRight.ftl" />
						</@s.if>
					
	        	</@s.else>
	  
				<!--点评列表展示-->
					<div class="dstnt_recomend dstnt_mb10" id="dstnt_dpwhere">
						<h3 class="dstnt_jdtj"><@s.property value="currentPlace.name"/>点评</h3>
					</div> 
					<div class="c_w_score">
				 		<#include "/WEB-INF/pages/hotel/listCmtsOfDest.ftl">
					</div>
					<div >
						<p class="c_seemore B"><a href="http://www.lvmama.com/comment/<@s.property value="currentPlace.placeId"/>-1" target="_blank" class="fr">查看更多点评&gt;&gt;</a></p>      
					</div>
				<#include "/WEB-INF/pages/place/template_common/seoRecommend.ftl"/>
        	</div>
        	<!--right-->
        	<#include "/WEB-INF/pages/place/template_common/seoInternallink.ftl">
        	<#include "/WEB-INF/pages/common/footer/place_footer.ftl">
		</div>
		<script type="text/javascript">
			document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|index_2013|index_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
		</script>
		<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/new_v/ob_testnt/dstnt_all.js,/js/new_v/ui_plugin/jquery.ui.datepicker-zh-CN.js,/js/new_v/ui_plugin/jquery-ui-1.8.17.custom.min.js,/js/new_v/ui_plugin/jquery-time-price-table.js"></script>
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
		<script type="text/javascript">
        	$(document).ready(function(){
		        $('span[class^="tags"]').ui('lvtip',{ 
					hovershow: 200
				});
		   		 $('a[class=discount]').ui('lvtip',{
			 		hovershow: 200
		   		 });
 		         
 		         var placeName="${currentPlace.name?if_exists}";
 		         $.ajax({
                    type:"post",
                    url:"http://www.lvmama.com/weather/home/getDistrict?namecn="+encodeURIComponent(placeName),
                    async:false,
                    dataType:"json",
                    error:function(){
                      alert("error");
                    },
                    success:function(data){
                      if(data.success==true){
                         $('#weather_id').attr("href","http://www.lvmama.com/weather/"+data.data.nameen+"-"+data.data.areaid+"/");
                       }else{
                           $('#weather_id').hide();
                       }
                     }
                });
      	  }); 
    	</script>
    	<script>
    	if("<@s.property value="maps"/>"=="true"){
    	cmCreatePageviewTag("目的地地图_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(currentPlace.name)"/>", "M1003", "", null);
    	}else{
    		
    		cmCreatePageviewTag("目的地产品列表_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(currentPlace.name)"/>_"+"${currentTab}（可预订商品1）", "M1001", "", null);
    	}
      	$(".ibmclass").live("click",function(){
    		eval("("+$(this).attr("ibmc")+")");
    	});
      	function cmCreateShopAction5s(productId, productName, price, subType){
      		cmCreateShopAction5Tag(productId, productName, "1", price, subType);
			cmDisplayShops();
      	}
		</script>
	</body>
</html>
