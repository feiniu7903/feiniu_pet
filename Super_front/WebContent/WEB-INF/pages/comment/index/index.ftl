<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${seoIndexPage.seoTitle}</title>
<meta name="keywords" content="${seoIndexPage.seoKeyword}"/>
<meta name="description" content="${seoIndexPage.seoDescription}"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/new_v/ob_comment/base.css,/styles/new_v/ob_comment/c_common.css,/styles/new_v/ob_comment/common.css"/>
<#include "/common/coremetricsHead.ftl">
</head>
<body class="lvcomment">
<!--头部开始-->
<#include "/common/header.ftl">
<!--页面主体部分-->
<div id="content" class="cArea clearfix"> 

  <!--页面主体部分-->
  <div id="c_search">
    <div class="cs_box fl">
    
	    <@s.if test="prodsCountOfWaitingForCmt!=null && prodsCountOfWaitingForCmt > 0">
		      <p class="c_info">您有<b>${prodsCountOfWaitingForCmt}</b>个待点评内容<a href="http://www.lvmama.com/myspace/share/comment.do" target="_blank">立即点评</a></p>
	    </@s.if>
      <div class="hr_b"></div>
      <p class="text">驴妈妈点评，每月产生<b>50000</b>条真实点评，<b>500</b>件积分礼品，<b>30</b>万点评返现。 <a href="http://www.lvmama.com/public/help_center_146" target="_blank" rel="nofollow">什么是积分？</a> | <a href="http://www.lvmama.com/comment/zt/fanxian/"  target="_blank" rel="nofollow">什么是奖金？</a></p>
     
      <div class="c_sbox">
        <ul class="c_stitle yahei JS_tab_nav hor">
          <li class="first selected"><b></b>景点/目的地</li>
          <li><b></b>酒店</li>
        </ul>
					
         <div class="JS_tab_switch cs_input clr">
          <div class="tabcon selected">
          	<!-- 景点/目的地 -->
            <input class="text"  type="text" id="searchKey" name="keyword" value="全站搜索" />
            <input type="button" value="" class="button xh_btn1" name="writeSearchKeyCmt" onclick="writeSearchKeyCmt()">
            <input type="button" value="" class="button xh_btn2" name="viewSearchKeyCmt" onclick="viewSearchKeyCmt()">
          </div>
          
          <!-- 酒店 -->
          <div class="tabcon">
            <input class="text" type="text" id="keyword" name="some_name" value="全站搜索" />
            <input type="button" value="" class="button xh_btn1" name="writekeywordCmt" onclick="writekeywordCmt()">
            <input type="button" value="" class="button xh_btn2" name="viewkeywordCmt" onclick="viewkeywordCmt()">
          </div>
        </div>
         <div id="searchTip" style="display: none;">
         	<p class="text" style="color:#ff0000;">未找到您搜索的内容</p>
         </div>
      </div>
    </div>
    
    <div class="cs_act fl">
    		<#if cmtActivity??>
		    <a href="${cmtActivity.picUrl}" target="_blank" rel="nofollow">
		    	<img src="http://s2.lvjs.com.cn/img/new_v/ui_scrollLoading/pixImagePath.gif" to="${cmtActivity.absolutePictureUrl}" width="300" height="200"/>
		    </a>
	        <p class="c_huodong"><b class="cxh_ico1">活动</b><a class="yahei f14" target="_blank" rel="nofollow" href="${cmtActivity.url}">${cmtActivity.title}</a></p>
	        </#if >
    </div>
    
  </div>
  <div class="c_inx_main fl"> 
  
    <!--驴友热评开始-->
  	<!--  #include "/WEB-INF/pages/comment/index/cmtSpecialSubject.ftl"  -->
  
    <!--  #include "/WEB-INF/pages/comment/index/cmtNewsCommmon.ftl"  -->
   
    <!--热门景区/酒店点评开始-->
    <div class="c_item_b fl w_item_a">
      <h3 class="c_title cbd_c"><strong>上周热门景区排行</strong></h3>
      <div class="pd10">	<!--list_chot中控制其下p a中图片自动缩放，a必须设置块状，且指定宽高，图片不能设置宽高-->
        <ul class="list_chot comments JS_imgmo_menu">
	        <@s.iterator value="indexOfHotScenicSpot" id="placeCommentVO" status="st">
		          <li <@s.if test="#st.index==0"> class="selected"</@s.if>>
		          <span class="cxh_num"><a href="http://www.lvmama.com/comment/${placeId}-1" target="_blank">${commentCount}条点评</a></span>
		          	   <span class="title"><a href="http://www.lvmama.com/dest/${pinYinUrl}"  target="_blank">${titleName}</a>
		          	   <span class="clh_score"><span class="progressBar"><i class="achiveBar" style="width:${avgScorePercent}%"><s></s></i><small></small></span>#{avgScore;m1M1}</span></span>
			           <p><a class="img_auto"  target="_blank" rel="nofollow" href="http://www.lvmama.com/dest/${pinYinUrl}">
			           		<@s.if test="#st.index==0"> 
			           			<img original="http://pic.lvmama.com${placeLargeImage}"  src="http://pic.lvmama.com${placeLargeImage}">
			           		</@s.if>
			           		 <@s.else>
			           			<img original="http://pic.lvmama.com${placeLargeImage}"  src="http://pic.lvmama.com${placeLargeImage}">
             				 </@s.else>
			           	  </a>
			           </p>
		          </li>
	         </@s.iterator>
        </ul>
        <div class="chot_dest"><span class="fl">热门目的地：</span>
          <ul class="hor" last_child>
              	<li><a rel="nofollow" href="http://www.lvmama.com/search/ticket/%E4%B8%8A%E6%B5%B7.html" target="_blank">上海</a></li>
	            <li><a rel="nofollow" href="http://www.lvmama.com/search/ticket/%E6%9D%AD%E5%B7%9E.html" target="_blank">杭州</a></li>
	            <li><a rel="nofollow" href="http://www.lvmama.com/search/ticket/%E6%89%AC%E5%B7%9E.html" target="_blank">扬州</a></li>
	            <li><a rel="nofollow" href="http://www.lvmama.com/search/ticket/%E8%8B%8F%E5%B7%9E.html" target="_blank">苏州</a></li>
	            <li><a rel="nofollow" href="http://www.lvmama.com/search/ticket/%E5%B8%B8%E5%B7%9E.html" target="_blank">常州</a></li>
          </ul>
        </div>
      </div>
    </div>
    
    <div class="c_item_b fr w_item_a">
      <h3 class="c_title cbd_d"><strong>上周热门酒店排行</strong></h3>
      <div class="pd10">
        <ul class="list_chot comments JS_imgmo_menu">
           <@s.iterator value="indexOfHotHotel" id="placeCommentVO" status="st">
		          <li <@s.if test="#st.index==0"> class="selected"</@s.if>>
		          <span class="cxh_num"><a href="http://www.lvmama.com/comment/${placeId}-1" target="_blank">${commentCount}条点评</a></span>
		          	   <span class="title"><a href="http://www.lvmama.com/dest/${pinYinUrl}"  target="_blank">${titleName}</a>
		          	   <span class="clh_score"><span class="progressBar"><i class="achiveBar" style="width:${avgScorePercent}%"><s></s></i><small></small></span>#{avgScore;m1M1}</span></span>
			           <p><a class="img_auto"  target="_blank" rel="nofollow" href="http://www.lvmama.com/dest/${pinYinUrl}">
			           		<@s.if test="#st.index==0"> 
			           			<img original="http://pic.lvmama.com${placeLargeImage}" src="http://pic.lvmama.com${placeLargeImage}" />
			           		</@s.if>
			           		 <@s.else>
			           			<img original="http://pic.lvmama.com${placeLargeImage}" src="http://pic.lvmama.com${placeLargeImage}" />
             				 </@s.else>
             			 </a>
			           </p>
		          </li>
	         </@s.iterator>
        </ul>
        <div class="chot_dest"><span class="fl">热门目的地：</span>
          <ul class="hor last_child">
            <li><a rel="nofollow" href="http://hotels.lvmama.com/list/%E4%B8%8A%E6%B5%B7.html" target="_blank">上海</a></li>
            <li><a rel="nofollow" href="http://hotels.lvmama.com/list/%E6%9D%AD%E5%B7%9E.html" target="_blank">杭州</a></li>
            <li><a rel="nofollow" href="http://hotels.lvmama.com/list/%E6%89%AC%E5%B7%9E.html" target="_blank">扬州</a></li>
            <li><a rel="nofollow" href="http://hotels.lvmama.com/list/%E8%8B%8F%E5%B7%9E.html" target="_blank">苏州</a></li>
            <li><a rel="nofollow" href="http://hotels.lvmama.com/list/%E5%B8%B8%E5%B7%9E.html" target="_blank">常州</a></li>
          </ul>
        </div>
      </div>
    </div>

    <div class="hr_b"></div>
    <div style="margin:10px 0;">
    <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxM3xkaWFucGluZ18yMDEzfGRpYW5waW5nXzIwMTNfaW5kZXhfYmFubmVyJmRiPWx2bWFtaW0mYm9yZGVyPTAmbG9jYWw9eWVzJmpzPWll" charset="gbk"></script>
	</div>
    <div id="c_inx_main_list" >
         <#include "/WEB-INF/pages/comment/index/indexPageOfBestCmtList.ftl">
	 <div class="hr_b"></div>
    </div>

  <!--页面侧栏部分开始-->
  <div class="c_aside fr">
    <div class="hr_b"></div>
    
        <!--点评招募开始-->
    <div class="aside_box aside_box1 c_shadow">
      <h4 class="ca_title c_iconbg">
      		<span class="reflect">点评招募</span><small>(每个景区前十条点评奖励<b>80</b><a href="http://www.lvmama.com/public/help_center_146" target="_blank" rel="nofollow">积分</a>)</small>
      </h4>
      <ul class="list_chot JS_imgmo_menu lh24">
      	<@s.iterator value="recommendPlaceList" id="cmtCommentStatisticsVO" status="st">
      		  <li <@s.if test="#st.index==0"> class="selected"</@s.if>><span class="fl title"><i class="front3">·</i>
		        <a href="http://www.lvmama.com/dest/${pinYinUrl}"><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(14,placeName)" /></a>
		        	</span><a class="fr" rel="nofollow" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${placeId}">我要点评
		        </a>
	            <p class="c_pt_pic"> 
		            <a class="img_auto" rel="nofollow" href="http://www.lvmama.com/dest/${pinYinUrl}">
			            <@s.if test="#st.index==0"> 
			           			<img original="http://pic.lvmama.com${placeLargeImage}" src="http://pic.lvmama.com${placeLargeImage}" title="${placeName}">
			           	</@s.if>
			           	<@s.else>
			           			<img original="http://pic.lvmama.com${placeLargeImage}" src="http://pic.lvmama.com${placeLargeImage}" title="${placeName}">
			           </@s.else>
			            <span class="c_t_bg"></span> <em class="c_p_tit f14">${placeName}</em>
		            </a>
	            </p>
	        </li>
        </@s.iterator>
      </ul>
    </div>
    
    <!--热门线路开始-->
    <div class="hotL_box c_price">
      <div class="ca_title clearfix">
        <h3 class="fl">热门线路</h3>
        <small></small></div>
      <ul class="cL_hot B f14 lh22" id="guessYourFavorite">

      </ul>
    </div>
    <div style="margin:0 0 10px;">
   <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?user=lvmama_2013|dianping_2013|dianping_2013_index_button01&db=lvmamim&border=0&local=yes&js=ie" charset="gbk"></script>
	</div>
    <!--十大热门目的地攻略开始-->
    <div class="aside_box c_shadow yahei clearfix">
      <h4 class="ca_title c_iconbg reflect">驴妈妈十大热门目的地攻略</h4>
      <ul class="c_guide hor clearfix">
        <li><a href="http://www.lvmama.com/guide/place/sichuan_chengdu/" target="_blank">成都旅游攻略</a></li>
        <li><a href="http://www.lvmama.com/guide/place/anhui_huangshan/" target="_blank">黄山旅游攻略</a></li>
        <li><a href="http://www.lvmama.com/guide/place/fujian_xiamen/" target="_blank">厦门旅游攻略</a></li>
        <li><a href="http://www.lvmama.com/guide/place/zhejiang_hangzhou/" target="_blank">杭州旅游攻略</a></li>
        <li><a href="http://www.lvmama.com/guide/place/shannxi_xian/" target="_blank">西安旅游攻略</a></li>
        <li><a href="http://www.lvmama.com/guide/place/zhongguo_beijing/" target="_blank">北京旅游攻略</a></li>
        <li><a href="http://www.lvmama.com/guide/place/yazhou_riben/" target="_blank">日本旅游攻略</a></li>
        <li><a href="http://www.lvmama.com/guide/place/yazhou_taiguo/" target="_blank">泰国旅游攻略</a></li>
        <li><a href="http://www.lvmama.com/guide/place/yazhou_maerdaifu/" target="_blank">马尔代夫旅游攻略</a></li>
        <li><a href="http://www.lvmama.com/guide/place/yazhou_niboer/" target="_blank">尼泊尔旅游攻略</a></li>
      </ul>
    </div>
    
    <!--最新点评-->
	<#include "/WEB-INF/pages/comment/index/indexLastestComments.ftl" />
    <div>
   <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?user=lvmama_2013|dianping_2013|dianping_2013_index_button02&db=lvmamim&border=0&local=yes&js=ie" charset="gbk"></script>
	</div>
  </div>


<!--footer>>-->
<#include "/WEB-INF/pages/comment/generalComment/commentIndexFooter.ftl" />
<!--footer<<-->
</div>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/new_v/ob_comment/x_comment.js"></script>

<script type="text/javascript" charset="utf-8">



window.onload = function(){
  $("a#xh_gotop").click();
}

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


$(function(){
	$("body").append('<link href="http://pic.lvmama.com/styles/new_v/ch_hotel/hote_index.css?r=9617" rel="stylesheet" />');
	$("#c_search").one("mouseover",function(){
		UI.css(true,"http://pic.lvmama.com/styles/new_v/ui_plugin/lmmcomplete.css");
		$("#searchKey").ui("autoComplete",{
			type:2,
			recommend : false,
			page : true,
			url:"http://www.lvmama.com/search/destSearch.do"
		});
		$("#keyword").ui("autoComplete",{
			type:3,
			recommend : false,
			url:"http://www.lvmama.com/search/hotelNameSearch.do"
		});
	});
});


//酒店查看点评
function viewkeywordCmt() {
	var keyword = $("#keyword").value;
	var url = $("#keyword").attr("url");
	var placeId = $("#keyword").attr("sublabel");
	
	if( placeId =="" || placeId == null){
			$("#searchTip").attr('style','display:block;');
			$("#keyword").attr('style','border:1px solid red;');
	} else {
			$("#searchTip").attr('style','display:none;');
			location.href="http://www.lvmama.com/comment/"+placeId+"-1";
	}
}
function writekeywordCmt() {
 
	var keyword = $("#keyword").value;
	var placeId = $("#keyword").attr("sublabel");
	
	if( placeId =="" || placeId == null){
			$("#searchTip").attr('style','display:block;');
			$("#keyword").attr('style','border:1px solid red;');
	} else {
			$("#searchTip").attr('style','display:none;');
			location.href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=" + placeId;
	}
}
//景点查看点评
function viewSearchKeyCmt() { 
	
	var searchKey = $("#searchKey").value;
	var url = $("#searchKey").attr("url");
	var placeId = $("#searchKey").attr("sublabel");
	
	if( placeId =="" || placeId == null){
			$("#searchTip").attr('style','display:block;');
			$("#searchKey").attr('style','border:1px solid red;');
	} else {
			$("#searchTip").attr('style','display:none;');
			location.href="http://www.lvmama.com/comment/"+placeId+"-1";
	}
}
function writeSearchKeyCmt() { 
	var searchKey = $("#searchKey").value;
	var placeId = $("#searchKey").attr("sublabel");
	
	if( placeId =="" || placeId == null){
			$("#searchTip").attr('style','display:block;');
			$("#searchKey").attr('style','border:1px solid red;');
	} else {
		$("#searchTip").attr('style','display:none;');
		//window.open("http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=" + placeId);
		location.href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=" + placeId;
	}
}



	</script>
	<script>
	function al(msg){
		alert(msg);
	}
	<#if errorText??>
		al('${errorText}'); 
	</#if> 
	
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js?r=8673"></script>
 <script>
      cmCreatePageviewTag("旅游点评首页", "T0001", null, null);
 </script>
</body>
</html>
