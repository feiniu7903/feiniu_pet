<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE7" /> 
<title>景区排行榜_夏季必去十大景点推荐-驴妈妈旅游网</title>
<meta name="Keywords" content="景区排行,夏季必去十大景点推荐">
<meta name="Description" content="不知道去哪玩?请看驴妈妈热门景区排行榜,在这里，驴妈妈旅游网,为你准备最准确的景区排行榜,为你推荐夏季必去十大景点,更有最新的景区点评,还等什么?快来参与吧!">
<link href="http://www.lvmama.com/zt/000global/styles/zt_global.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/zt/phb/phb_cmt.css?r=6262" rel="stylesheet" type="text/css" />

<script src="http://pic.lvmama.com/js/jquery142.js?r=8420" type="text/javascript" ></script>
<script src="http://www.lvmama.com/zt/000global/js/ztTopMenu.js" type="text/javascript"></script>

</head>
<body>

<div id="container" onLoad="init()">
	    <div class="bgleft"></div>
        <div class="bgright"></div>
        <div class="page_wrap">
        	<h1>请看——驴妈妈热门景区排行榜</h1>
            <h2>不知道去哪玩儿？</h2>
   			<ul class="ban_hz">
            	<li><img src="http://pic.lvmama.com/img/zt/phb/banner_01.jpg" height="151" width="964" /></li>
                <li><img src="http://pic.lvmama.com/img/zt/phb/banner_02.jpg" height="151" width="964" /></li>
            </ul>
            <div class="main">
            
            	 <!--  main_left   -->
            	<div class="main_left">
                	<h3><img src="http://pic.lvmama.com/img/zt/phb/suitable.gif" width="613" height="64" /></h3>
                    <h3 class="hidden">最适合游玩的景区排行<span>来自驴妈妈旅游网</span></h3>
                    <ul class="dig_list">
                        <@s.iterator value="cmtTopPlaceVOList" status="st" id="cmtTopPlaceVO">
                    	<li <#if st.index=0>class="mar_val"</#if>>
                        	<div class="img_num">
                            	<h4>${st.index+1}</h4>                            
                            </div>
                            <div class="text_hz">
                            	<h4>
                                	<a href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}" target="_blank" class="big_title"><strong>${titleName?if_exists}</strong></a>
                                	 <input type="hidden" id="placeId_${st.index+1}" value="${placeId}" />
                                	（<a href="http://www.lvmama.com/comment/${placeId}-1" target="_blank"><em>${commentCount?if_exists}</em></a>条点评）
                               		 <span>
                               		 		<a onclick="votes(${st.index+1})"  class="go" ></a><em>（<b>${commentCount?if_exists}</b>人想去）</em>
	                               		 	<a class="jiant"  href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${placeId?if_exists}" target="_blank">写点评</a>
                               		 </span>
									 <script type="text/javascript" src="http://www.lvmama.com/others/paihangbang/votes.php?num=${st.index+1}" ></script>
                                </h4>
                                <p>
                                
			                         <@s.if test="cmtTopCommentList.get(#st.index) != null">    
				                        <span>网友说："
	                                		</span><@s.property value='@com.lvmama.comm.utils.StringUtil@cutString2(80,cmtTopCommentList[#st.index].contentDelEnter)' /><span>"
	                                	</span>
	                                	——
	                                	<a href="http://www.lvmama.com/comment/<@s.property value="cmtTopCommentList[#st.index].commentId" />" target="_blank">
	                                		<@s.if test="cmtTopCommentList[#st.index].userName != null"> 
	                                		<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(15,cmtTopCommentList[#st.index].userName)" />
	                                		 </@s.if>
	                                		 <@s.else>
	                                		 匿名
	                                		 </@s.else> 
	                                	</a>                             
			                         </@s.if> 
                                    
                                    <a href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}" target="_blank" class="posi">产品推荐 »</a>
                                </p>
                            </div>
                        </li>
                   		</@s.iterator>
                  </ul>
                </div>
                
                <!--  main_right   -->
                <div class="main_right">
                
                	<!--  近期口碑榜    -->
                	<div class="praise">
                		<@s.iterator value="map.get('${station}_phbjbbb')" status="st">
                    		<p>${bakWord1?if_exists}——${bakWord2?if_exists}</p>
                    	</@s.iterator>
                    	
                        <h4 class="hidden">近期口碑榜来自驴妈妈网友</h4>
                        
                        <@s.iterator value="map.get('${station}_phbjqkbb')" status="st">
	                        <dl>
	                        	<dt><em <#if (st.index<3) >class="color"</#if>>${st.index+1}</em></dt>
	                            <dd><a href="${url?if_exists}" target="_blank"><strong>${title?if_exists}</strong></a> （${bakWord1?if_exists}条点评）</dd>
	                            <dd class="${bakWord2?if_exists}"></dd>
	                        </dl>
                        </@s.iterator>
                    </div>
                    
                    <!--   景区标签     -->
                    <div class="scenic">
                    	 <h4 class="hidden">景区标签来自驴妈妈网友</h4>
                         <p>
                         <@s.iterator value="map.get('${station}_phbjqbq')" status="st">
                            <a href="${url?if_exists}" target="_blank" class="${bakWord1?if_exists} hot_a">${title?if_exists}</a> 
                         </@s.iterator> 
                         </p>
                    </div>
                    
                    <!--  最新游客点评   -->
                    <div class="sce_spot">
                    	 <h4 class="hidden">最新游客点评来自驴妈妈网友</h4>
                         <@s.iterator value="lastestCommentsList" status="st" id="cmtCommentVO">
                         <dl <#if st.index=0>class="mar_dl"</#if>>
                         	<dt><#if userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(10,userNameExp)" /><#else>匿名</#if>
                         	<span>评价</span>
                         		<a href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}" target="_blank">${placeName?if_exists} ：</a>
                         	</dt>
                            <dd><@s.property value='@com.lvmama.comm.utils.StringUtil@cutString2(64,contentDelEnter)' /></dd>
                            <dd>${formatterCreatedTime?if_exists}<span>${bakWord4?if_exists}</span>
                            	<a class="fr" target="_blank"  href="http://www.lvmama.com/comment/${commentId?if_exists}">查看全文 »</a>
                            </dd>
                         </dl>
                         </@s.iterator> 
                         
                         <a class="spot" target="_blank"  href="http://www.lvmama.com/comment/"><strong>更多游客点评 »</strong></a>
                          <div id="share">
                          <!-- JiaThis Button BEGIN -->
                            <div id="jiathis_style_32x32">
                                <a class="jiathis_button_qzone"></a>
                    
                                <a class="jiathis_button_tsina"></a>
                                <a class="jiathis_button_tqq"></a>
                                <a class="jiathis_button_renren"></a>
                                <a class="jiathis_button_kaixin001"></a>
                                <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                            </div>
                            <!-- JiaThis Button END -->
                  
                    	 </div>
                    </div>
                  </div>           
            </div>
        </div>
        
   <script src="http://www.lvmama.com/zt/000global/js/ztFooter.js" type="text/javascript"></script>
   
  <div class="ban_bg" id="ban_bg"></div>  
  
   <!--  iframe 登陆和发点评页面    -->
   <div id="div_comment" style="display: none;">
  		<iframe width="520" id="iframeId" src="javascript:void(0)"  scrolling="no" marginwidth="0" frameborder="0" marginheight="0" allowTransparency=true></iframe> 
   </div>
</div>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
			$('.hot_a').one('click',function(event){
                $(this).attr('href',encodeURI($(this).attr('href')));
            });
});            
</script>
<script src="http://v2.jiathis.com/code/jia.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js?r=8673"></script> 
<#include "/WEB-INF/pages/comment/jsResource/phb/paiHangBang_js.ftl" />

</body>
</html>
