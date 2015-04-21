<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> 
<#include "/WEB-INF/pages/comment/dest/scenicList_tdk.ftl" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/new_v/ob_comment/base.css,/styles/new_v/ob_comment/c_common.css,/styles/new_v/ob_comment/common.css" />
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/new_v/ob_comment/x_comment.js"></script>
<#include "/common/coremetricsHead.ftl">
</head>
<body>
<!--头部开始-->
<#include "/common/header.ftl">
  
<!--页面导航-->
<#if place??>
	<#include "/WEB-INF/pages/comment/navigation.ftl">
</#if>
			
<input type="hidden" value="placePage" id="pageName">
<script type="text/javascript">
//setKeyword('<@s.property value="keyword" />');
</script>

 <div class="container clearfix">
 
<!--left_section_began> -->
	<div class="c_list_lt">
		<!-- 点评页 - 大旗广告 -->
		<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('mf26093ba46662520001','js',null)"/>
		<!-- 点评页 - 大旗广告/End -->
		
        <div class="c_w_com c_shadow">
            <div class="c_w_tit">
            	<div class="w_330"> 
                    <h1 class="yahei"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(11,place.name)" /><@s.if test="currentTab == 'dish'">美食</@s.if><@s.if test="currentTab == 'hotel'">住宿</@s.if><@s.if test="currentTab == 'traffic'">交通</@s.if><@s.if test="currentTab == 'entertainment'">游玩</@s.if><@s.if test="currentTab == 'shop'">购物</@s.if><@s.if test="currentTab == 'weekendtravel'">行程</@s.if><@s.if test="currentTab == 'scenery'">景点介绍</@s.if><@s.if test="currentTab == 'place'">介绍</@s.if><@s.if test="currentTab == 'photo'">图片</@s.if>
                    </h1>
                    <div class="c_w_score">
                         <p class="c_p_link"><span class="com_StarValueCon total_val_posi">
                         
                         <@s.if test="cmtCommentStatisticsVO != null && cmtCommentStatisticsVO.commentCount!=0">
                          		<font><em>#{cmtCommentStatisticsVO.avgScore;m1M1}</em>分</font><s class="star_bg cur_def"><i class="ct_Star${cmtCommentStatisticsVO.roundHalfUpOfAvgScore}"></i></s></span>
                         </@s.if>
                         <@s.else>
                           		<font><em>0</em>分</font><s class="star_bg cur_def"><i class="ct_Star0"></i></s></span>
                         </@s.else>
                         <@s.if test="cmtCommentStatisticsVO != null">
                           		<a href="http://www.lvmama.com/comment/${cmtCommentStatisticsVO.placeId}-1">${allCmtRecords}封点评</a>
                         </@s.if>
                         <@s.else>
                           		<span>0封点评</span>
                         </@s.else> 
                         
                         </p>
                         <!--点评平均分维度统计开始-->
                          <@s.if test="cmtLatitudeStatisticsList != null">
  	                     		<#include "/WEB-INF/pages/comment/cmtAvgLatitudeStatisticsInfo.ftl">
  	                     </@s.if>
                    </div>
                </div><!--fl end-->
                
            	<p class="xh_imgauto fr">
            	 <@s.if test="cmtCommentStatisticsVO != null && cmtCommentStatisticsVO.placeLargeImage != null">
            			<img src="http://pic.lvmama.com${cmtCommentStatisticsVO.placeLargeImage}" />
            	 </@s.if>
            	 <@s.else>
                    <img src="${place.middleImageUrl}" />
                 </@s.else>
            	</p>
            	
            </div>
           <div class="comment_tips">
	            <p><i class="bgorange">返</i> 有订单？发表体验点评，返点评奖金。<a rel="nofollow" href="http://www.lvmama.com/myspace/share/comment.do">写体验点评&gt;&gt;</a></p>
	            <p><i class="bgorange">送</i> 没订单？发表普通点评，赠50积分，精华追加150积分！</p>
	            <p><a class="white_comment_btn" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.placeId!?c}" target="_blank" rel="nofollow">我要写点评</a></p>
	        </div>
        </div>
        
        <!-- c_w_com end -->
        <div class="c_w_com c_shadow">
        	
        	<div class="c_w_nav">
	            <ul class="JS_nav">
	            	<li <@s.if test='currentTab == null || currentTab == ""'>class="c_w_navcur"</@s.if>>点评</li>
	            	<li <@s.if test="currentTab == 'place' || currentTab == 'scenery'">class="c_w_navcur"</@s.if>>景点</li>
	            	<li id="a3" <@s.if test="currentTab == 'weekendtravel'">class="c_w_navcur"</@s.if>>推荐</li>
	            	<li <@s.if test="currentTab == 'dish'">class="c_w_navcur"</@s.if>>美食</li>
	            	<li <@s.if test="currentTab == 'hotel'">class="c_w_navcur"</@s.if>>住宿</li>
	            	<li <@s.if test="currentTab == 'traffic'">class="c_w_navcur"</@s.if>>交通</li>
	            	<li <@s.if test="currentTab == 'entertainment'">class="c_w_navcur"</@s.if>>娱乐</li>
	            	<li <@s.if test="currentTab == 'shop'">class="c_w_navcur"</@s.if>>购物</li>
	            </ul>
	             <a  target="_blank"  href="http://www.lvmama.com/guide/place/${place.pinYinUrl}/">攻略</a>
	            <a target="_blank" href="http://bbs.lvmama.com/">社区</a>
            </div>
            <!--c_w_nav end-->
            
            <div id="JS_tab_switch"> 

           	<div  class="c_spot_intro" <@s.if test='currentTab != null && currentTab != ""'>style="display: none;"</@s.if> >	
	             		<@s.action name="listAllCmtsOfPlace" namespace="/comment/listCmtsOfDest" executeResult="true">
			                     <@s.param name="placeId"><@s.property value="place.placeId"/></@s.param>
			                     <@s.param name="pageFlag">Y</@s.param>
	                    </@s.action>
				</div>
				
                 <!--景点介绍 -->
                <div class="c_spot_intro" <@s.if test="currentTab != 'place' && currentTab != 'scenery'">style="display: none;"</@s.if>  info_type="34"></div><!--c_spot_intro-->
                
                <!--推荐介绍 -->
                <div class="c_spot_intro"  <@s.if test="currentTab != 'weekendtravel'">style="display: none;"</@s.if> info_type="39"> </div><!--c_spot_intro-->
              
                <!--美食介绍 -->
                <div class="c_spot_intro"  <@s.if test="currentTab != 'dish'">style="display: none;"</@s.if>  info_type="40"></div><!--c_spot_intro-->
              
                <!--住宿介绍 -->
                <div class="c_spot_intro" <@s.if test="currentTab != 'hotel'">style="display: none;"</@s.if>  info_type="41"></div><!--c_spot_intro-->
              
                <!--交通介绍 -->
                <div class="c_spot_intro"  <@s.if test="currentTab != 'traffic'">style="display: none;"</@s.if>  info_type="42"></div><!--c_spot_intro-->
              
                <!--娱乐介绍 -->
                <div class="c_spot_intro"  <@s.if test="currentTab != 'entertainment'">style="display: none;"</@s.if>  info_type="43"></div><!--c_spot_intro-->
              
                <!--购物介绍 -->
                <div class="c_spot_intro"  <@s.if test="currentTab != 'shop'">style="display: none;"</@s.if>  info_type="44"></div><!--c_spot_intro-->

			
            </div> 
        </div>  
        <div class="c_write" id="c_write">
			<h4 class="c_my_com">我的点评</h4> 
			<div id="allComments" >
				<@s.action name="getCommonFillComment" namespace="/comment/writeComment" executeResult="true">
				        <@s.param name="placeId"><@s.property value="place.placeId"/></@s.param>
		        </@s.action>
		    </div>
    	</div>
    </div>
    
<#include "/WEB-INF/pages/comment/dest/rightToll.ftl" />
 	
<#include "/WEB-INF/pages/comment/generalComment/commentListFooter.ftl" />

<div id="c_overlay"></div>

<div style="display: none;">
<a href="http://www.lvmama.com/travel/${place.pinYinUrl}/place" >${place.name}景点</a>
<a href="http://www.lvmama.com/travel/${place.pinYinUrl}/weekendtravel" >${place.name}推荐</a>
<a href="http://www.lvmama.com/travel/${place.pinYinUrl}/dish" >${place.name}美食</a>
<a href="http://www.lvmama.com/travel/${place.pinYinUrl}/hotel" >${place.name}住宿</a>
<a href="http://www.lvmama.com/travel/${place.pinYinUrl}/traffic" >${place.name}交通</a>
<a href="http://www.lvmama.com/travel/${place.pinYinUrl}/entertainment" >${place.name}娱乐</a>
<a href="http://www.lvmama.com/travel/${place.pinYinUrl}/shop" >${place.name}购物</a>
<a href="http://www.lvmama.com/guide/place/${place.pinYinUrl}/" >${place.name}攻略</a>
<a href="http://bbs.lvmama.com/" >旅游论坛</a>
<a href="http://www.lvmama.com/dest/${place.pinYinUrl}/ticket_12_1_1" >${place.name}门票</a>
<a href="http://www.lvmama.com/dest/${place.pinYinUrl}/package_12_1_1" >${place.name}自由行</a>
<a href="http://www.lvmama.com/dest/${place.pinYinUrl}/line_12_1_1" >${place.name}跟团游</a>
<a href="http://www.lvmama.com/travel/${place.pinYinUrl}/hotel" >${place.name}特色酒店</a>
</div>

<script type="text/javascript" charset="utf-8">
		
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
			 } else{
			 	xh_addClass(obj,"d_xing");
				var newUsefulCount = varUsefulCount + 1;
				obj.innerHTML= "<i>（<big>" + newUsefulCount + "</big>）</i>" ;
			 }
		}
	});
}
$(function(){
	$(document.body).ready(function(){
		$(".c_spot_intro").each(function(){
			var current = $(this);
			var columnid = $(this).attr("info_type");
		    if($.trim(columnid) =="" || null== $.trim(columnid) || undefined == columnid){
		    	return;
		    }
			$.ajax({
			  type:"GET",
			        url:"http://www.lvmama.com/guide/ajax/api.php",
			        data:"action=getOrgColumnInfo&placeid=<@s.property value="place.placeId"/>&columnid="+columnid,
			        async:false,
			        error:function(){
			        },
			        success:function(data){
			         current.empty().append(data);
			        }
			 });
		});
	});
});
</script>
<script src="http://pic.lvmama.com/js/dest_xu/dianj.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
<script>
      cmCreatePageviewTag("目的地资讯详情页_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(place.name)"/>", "M1002", null, null);
</script>
</body>
</html>
