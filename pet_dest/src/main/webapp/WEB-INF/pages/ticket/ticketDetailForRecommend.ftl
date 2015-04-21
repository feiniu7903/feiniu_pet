<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/ticket/piao-<@s.property value="place.placeId"/>/">
<title><@s.property value="scenicVo.seoTitle"/></title>
<meta name="keywords" content="<@s.property value="scenicVo.seoKeyword"/>">
<meta name="description" content="<@s.property value="scenicVo.seoDescription"/>">

<!--生产线引用-->
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/base.css,/styles/v5/common.css,/styles/new_v/header-air.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/form.css,/styles/v5/modules/button.css,/styles/v5/modules/table.css,/styles/v5/modules/tags.css,/styles/v5/modules/tip.css" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/dialog.css,/styles/new_v/ob_login/l_fast_login.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/dest.css" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css" />

<#include "/WEB-INF/pages/common/coremetricsHead.ftl">
</head>
<body class="dest" data-spy="scroll" data-target=".J_scrollnav">

<!-- 公共头部开始  -->
<!-- 
 * 此示例展示，暂时使用了js来展现，
 * 但开发上线，务必请引用头部的那个公共模块，参考其他项目
-->
    <!--头部 S-->
    <@s.set var="pageMark" value="'destScenic'" />
    <#include "/WEB-INF/pages/common/header.ftl">
<!-- 公共头部结束  -->


<!-- wrap\\ 1 -->
<div class="wrap">
    
    <!--面包屑导航-->
    <div class="crumbs clearfix">
        <p class="crumbs-link">
            <a href="http://www.lvmama.com/ticket">景点门票</a> &gt; 
            <@s.if test="null!=scenicVo.grandfatherPlace "> <a href="http://www.lvmama.com/search/ticket/<@s.if test="null!=scenicVo.grandfatherPlace.codeId">${scenicVo.grandfatherPlace.codeId!?c}</@s.if><@s.else>${scenicVo.grandfatherPlace.name?if_exists}</@s.else>.html">${scenicVo.grandfatherPlace.name?if_exists}景点门票</a> &gt;</@s.if> 
            <a href="http://www.lvmama.com/search/ticket/<@s.if test="null!=scenicVo.fatherPlace.codeId">${scenicVo.fatherPlace.codeId!?c}</@s.if><@s.else>${scenicVo.fatherPlace.name?if_exists}</@s.else>.html">${scenicVo.fatherPlace.name}景点门票</a> &gt; 
            <a class="current">${place.name}</a>
        </p>
    </div>
    
    <div class="overview">
        <div class="dtitle clearfix">
            <span class="xorder">
                
            </span>
            <div class="titbox">
                <h1 class="tit">${place.name}</h1>
            </div>
        </div>
        <div class="dcontent clearfix">
            <ul class="dimg ul-hor J_photo">
                <@s.iterator value="scenicVo.placePhoto" var="v" status="st">
                  <@s.if test="#st.count<=3">
	                <li <@s.if test="#st.first">class="big-img"</@s.if> >
	                    <img src="${absoluteImageUrl}" data-big-img="${absoluteImageUrl}"
	                    <@s.if test="#st.first">width="405" height="270"</@s.if><@s.else>width="198" height="132" </@s.else> alt="<@s.property value="place.name"/>">
	                </li>
	             </@s.if>
                </@s.iterator>
            </ul>
            <div class="dinfo">
                <div class="sec-info">
                    <div class="sec-inner">
                        <@s.if test="null!=trafficInfo"><a href="#traffic" class="xlink"><i class="icon dicon-local"></i>地图</a></@s.if>
                        <@s.if test="null!=place.address"><dl class="dl-hor">
                            <dt>景点地址</dt>
                            <dd><p class="linetext">${place.address?if_exists}</p></dd>
                        </dl>
                        </@s.if>
                         <@s.if test="null!=place.scenicOpenTime"> <dl class="dl-hor">
                            <dt>入园时间</dt>
                            <dd>
                                ${place.scenicOpenTime?if_exists}
                            </dd>
                        </dl>
                        </@s.if>
                        <@s.if test="null!=scenicVo.placeActivity&&(scenicVo.placeActivity.size()>0)" >
                        <dl class="dl-hor link-active">
                            <dt><span class="tags-active">景点活动</span></dt>
                            <dd>
                                 <@s.iterator value="scenicVo.placeActivity" var="bean" status="st">
                                    <@s.if test="#st.count<=2">
                                    <a href="#activity">${bean.title?if_exists}</a>
                                    </@s.if>
                                 </@s.iterator>
                             </dd>
                        </dl>
                        </@s.if>
                        
                        <@s.if test="null!=place.kuaiSuRuYuan ||null!=place.guiJiuPei ||null!=place.suiShiTui ||null!=place.ruYuanBaoZhang">
                        <dl class="dl-hor service_list"> 
							<dt>服务保障</dt> 
							<dd> 
							<@s.if test="null!=place.ruYuanBaoZhang"><a class="service_poptip service_list_ensure" tip-content="顺利入园，快速服务" href="http://www.lvmama.com/public/user_security#mp" target="_blank">入园保证</a> </@s.if>
							<@s.if test="null!=place.kuaiSuRuYuan"><a class="service_poptip service_list_fast" tip-content="便捷入园，无需排队" href="http://www.lvmama.com/public/user_security#mp" target="_blank">快速入园</a> </@s.if>
							<@s.if test="null!=place.suiShiTui"><a class="service_poptip service_list_return" tip-content="无条件退，放心订票" href="http://www.lvmama.com/public/user_security#mp" target="_blank">随时退</a> </@s.if>
							<@s.if test="null!=place.guiJiuPei"><a class="service_poptip service_list_indemnity" tip-content="买贵就赔，保证便宜" href="http://www.lvmama.com/public/user_security#mp" target="_blank">贵就赔</a> </@s.if>
							</dd> 
						</dl> 
                        </@s.if>
                    </div>
                </div>
                <div class="comment-info">
                       <@s.iterator value="scenicVo.cmtLatitudeStatisticsList">
						<@s.if test='latitudeId == "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
                         <dl class="dl-hor">
                            <dt>好 评 率</dt>
                            <dd><span class="dnum"><i class="orange" id="totalCmt_1"></i> 人真实评价</span><dfn><i>${avgScore * 20}</i>%</dfn></dd>
                        </dl>
                        </@s.if>
                     </@s.iterator>
                    <div class="dot-line"></div>
                    <a class="quote" href="#comments" title="查看详细">
                        <i class="icon dicon-comment"></i>
                        <i class="qstart">“</i>
                        <i class="qend">”</i>
                        <p id="news"><@s.if test="scenicVo.lastcommonCmtCommentVO!=null">${scenicVo.lastcommonCmtCommentVO.content?if_exists}</@s.if>
                        <@s.else>暂无点评</@s.else></p>
                    </a>
                </div>
            </div>
        </div>
    </div>
    
    <div class="dest-main">
        <div id="destorder" class="tab-outer">
            <div class="tab-dest tab-fixed J_scrollnav">
                <ul class="ul-hor">
                <@s.if test="(null != scenicVo.victinityScenic && scenicVo.victinityScenic.size() > 0)||(null != scenicVo.sameSubjectScenic && scenicVo.sameSubjectScenic.size() > 0)">
                    <li class="active"><a hideFocus="hidefocus"  href="#destorder">关联推荐</a></li>
                </@s.if>
                    <@s.if test="null!=descripTion"><li><a href="#introduction">景点介绍</a></li></@s.if>
                    <@s.if test="null!=trafficInfo"><li><a href="#traffic">交通指南</a></li></@s.if>
                    <li class=""><a hideFocus="hidefocus" href="#comments">用户点评<span id="totalCmt">(0)</span></a></li>
                </ul>
            </div>
        </div>
        <@s.if test="(null != scenicVo.victinityScenic && scenicVo.victinityScenic.size() > 0)||(null != scenicVo.sameSubjectScenic && scenicVo.sameSubjectScenic.size() > 0)">
        <div class="dcontent">
            <div class="nolist clearfix">
            	<@s.if test="(null != scenicVo.victinityScenic && scenicVo.victinityScenic.size() > 0)">
                <h4>${place.name}- 附近景点</h4>
                <ul class="ul-hor">
                	<@s.iterator value="scenicVo.victinityScenic" status="st">
                	<@s.if test="#st.count%4==0 || #st.count==1"><li></@s.if>
                    	<dl class="ptditem">
    						<dd class="pdpaytype">
        						<a  target="_blank" href="http://ticket.lvmama.com/scenic-${placeId!?c}" class="btn btn-w btn-small btn-link">查看</a>
    						</dd>
    						<dd class="pdlvprice">
        						<dfn><@s.if test="proMap.size()==0">0</@s.if>
        								     <@s.iterator value="proMap.keySet()"  id="key">
										<@s.if test="#key==placeId">    
											 &yen;<i><@s.property value="proMap.get(#key)"/></i><em>起</em>
										</@s.if>
											</@s.iterator> 
						 </dfn>
    						</dd>
    					<dt class="pdname">
        					<a target="_blank" href="http://ticket.lvmama.com/scenic-${placeId!?c}" class="ptlink">${name?if_exists}</a>
        					<p class="distance">距离约<span class="num">${distance?if_exists}km</span></p>
    					</dt>
						</dl>     
					<@s.if test="#st.count%4==3"></li></@s.if>
                    </@s.iterator>
                </ul>
                </@s.if>
                <@s.if test="null != scenicVo.sameSubjectScenic && scenicVo.sameSubjectScenic.size() > 0">
                <h4>${place.name} - 同类型景点</h4>
                <ul class="ul-hor">
                        <@s.iterator value="scenicVo.sameSubjectScenic" status="st">
                            <@s.if test="#st.count%4==0 || #st.count==1"><li></@s.if>
                    	<dl class="ptditem">
    						<dd class="pdpaytype">
        						<a  target="_blank"  href="http://ticket.lvmama.com/scenic-${placeId!?c}" class="btn btn-w btn-small btn-link">查看</a>
    						</dd>
    						<dd class="pdlvprice">
        						<dfn><@s.if test="proMap.size()==0">0</@s.if>
        								     <@s.iterator value="proMap.keySet()"  id="key">
										<@s.if test="#key==placeId">    
											 &yen;<i><@s.property value="proMap.get(#key)"/></i><em>起</em>
										</@s.if>
											</@s.iterator> 
						 		</dfn>
    						</dd>
    					<dt class="pdname">
        					<a target="_blank" title="${name?if_exists}"  href="http://ticket.lvmama.com/scenic-${placeId!?c}" class="ptlink">${name?if_exists}</a>
        					<p class="distance">距离约<span class="num">${distance?if_exists}km</span></p>
    					</dt>
						</dl>     
					<@s.if test="#st.count%4==3"></li></@s.if>
                        </@s.iterator>
                    
                </ul>
                </@s.if>
            </div>
        </div>
        </@s.if>
        <div class="dside">
                
    <!--景点门票搜索-->
			<div class="sidebox dside-search">
			    <div class="stitle">
			        <h4 class="stit">景点门票搜索</h4>
			    </div>
			    <div class="scontent">
			        <div class="dsearch form-inline">
			            <input type="text" class="input-text" placeholder="请输入目的地/景点/主题/城市" id="searchKey"/><a href="javascript:;" onclick="onclicksearch()" class="dsearch-btn"><i class="icon dicon-search"></i></a>
			        </div>
			        <hr>
			
			        <@s.if test="null!=scenicVo.listPlace && scenicVo.listPlace.size()>0">
			        <dl class="dl-ver">
			            <dt>相关景点推荐</dt>
			            <dd class="list2row">
			            	<@s.iterator value="scenicVo.listPlace" var="listpalce">
				            	<a  target="_blank" title="${listpalce.name}" href="http://ticket.lvmama.com/scenic-${listpalce.placeId?c}">${listpalce.name}</a>
			            	</@s.iterator>
			            </dd>
			        </dl>
			       </@s.if>
			       
			    </div>
            </div><!--//.sidebox-->
            
          <!--攻略-->
			<div class="sidebox dside-guide" style="display: none;" >
            </div><!--//.sidebox-->
            
        </div><!--//.dside-->
       
       <!--景点介绍--> 
        <@s.if test="descripTion!=null">
        <div class="dmain">
            <div id="introduction" class="dbox introduction">
                <div class="dtitle">   
                    <h3 class="dtit"><i class="icon dicon-introduction"></i>景点介绍</h3>
                </div>
                <div class="dcontent">
             	 	  <@s.if test="descripTion!=''"> 
                   			${descripTion?if_exists}
                  	 </@s.if>
                </div>
                	 <@s.if test="destinationExplore!=null"> 
                   			${destinationExplore?if_exists}
                  	 </@s.if>	
            </div><!--//.dbox-->

            </@s.if>
            <!--交通-->
            <@s.if test="null!=trafficInfo">

            <div id="traffic" class="dbox traffic">
                <div class="dtitle">
                    <h3 class="dtit"><i class="icon dicon-traffic"></i>交通指南</h3>
                </div>
                <div class="dcontent">
                    <div class="traffic-map">
                     	<iframe marginheight="0" marginwidth="0" border="0" src="http://www.lvmama.com/dest/baiduMap/getBaiduMapCoordinate.do?id=<@s.property value="place.placeId"/>&windage=0.005&width=716px&height=287px&flag=2" width="750" height="300" scrolling="no"></iframe>
                    </div>
                    <h5>公共交通</h5>
		                  <@s.if test="trafficInfo!=''"> 
		                   			${trafficInfo?if_exists}
		                  </@s.if>
                </div>
            </div><!--//.dbox-->

            </@s.if>
       
                    
<!-- 点评信息概况 -->
 <@s.if test="null!=scenicVo.cmtLatitudeStatisticsList&&scenicVo.cmtLatitudeStatisticsList.size>0" >
        <div id="comments" class="dbox comments">
                <div class="dtitle">
                    <h3 class="dtit"><i class="icon dicon-comments"></i>用户点评</h3>
                </div>
                <div class="dcontent">
                    <div class="cominfo">
                        <div class="dscore">
                              <@s.iterator value="scenicVo.cmtLatitudeStatisticsList">
                                     <@s.if test='latitudeId == "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
                                              <span class="comlevel"><dfn><em>好评率</em><i data-mark="dynamicNum" data-level="${avgScore * 20}">${avgScore * 20}</i>%</dfn></span>
                                           </@s.if>
                            </@s.iterator>
                            <span class="scorebox">
                             <@s.iterator value="scenicVo.cmtLatitudeStatisticsList">
                                    <@s.if test='latitudeId != "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
                                         <p class="score-item">
                                            <em>${latitudeName?if_exists}</em>
                                            <span class="score-level"><i data-mark="dynamicNum" data-level="${avgScore?if_exists}"></i></span>
                                            <em>#{avgScore;m1M1}分</em>
                                          </p>              
                                       </@s.if>
                            </@s.iterator>
                            </span>
                        </div>
                        <div class="dtext">
                            <a class="btn btn-mini btn-w btn-orange" href="http://www.lvmama.com/myspace/share/comment.do">&nbsp;&nbsp;有订单，写点评，返现金&nbsp;&nbsp;</a>
                            <p>没订单？<a  class="dlink" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.placeId!?c}">发表普通点评</a> 赠50积分，精华追加150积分！</p>
                        </div>
                    </div><!--//.cominfo-->
                    <!-- 点评详情 -->
                    <div class="dcomment">
                        <div class="tab-dcom">
                            <ul class="ul-hor JS_tabnav">
                                <li class="selected"><a href="javascript:;">体验点评<span id="experienceCommentCount"></span></a></li>
                                <li><a href="javascript:;">精华点评<span id="isBestCommentCount"></span></a></li>
                                <li><a href="javascript:;">普通点评<span id="commonCommentCount"></span></a></li>
                            </ul>
                        </div>
                        <div class="dcombox JS_tabsbox">
                            <div class="tabcon selected"  id="experienceComment">
                               
                            </div><!--//.tabcon-->
                            
                            <div class="tabcon" id="bestComment">
                            </div><!--//.tabcon-->
                            
                            <div class="tabcon" id="commonComment">
                            </div><!--//.tabcon-->
                            <!-- // div.p_content  点评内容-->
                        </div><!--//.dcombox-->
                    </div><!--//.dcomment-->
                </div>
        </div><!--//.dbox-->
</@s.if> 
<@s.else>
<div id="comments" class="dbox comments">
	    <div class="dtitle">
	        <h3 class="dtit"><i class="icon dicon-comments"></i>用户点评</h3>
	    </div>
	    <div class="dcontent">
            <div class="cominfo">
                <div class="dtext">
                    <a class="btn btn-mini btn-w btn-orange" href="http://www.lvmama.com/myspace/share/comment.do">&nbsp;&nbsp;有订单，写点评，返现金&nbsp;&nbsp;</a>
                    <p>没订单？<a  class="dlink" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.placeId!?c}">发表普通点评</a> 赠50积分，精华追加150积分！</p>
                </div>
            </div><!--//.cominfo-->
        </div><!--dcontent -->
</div><!--//.dbox-->
</@s.else>
        </div>
        
    </div>
</div> <!-- //.wrap 1 -->



<div class="xh_float">
   <img src="http://ticket.lvmama.com/placeQr/${place.placeId!?c}.png" width="70" height="70" alt="手机订购二维码">
   <p>扫描此二维码 <span>手机订更优惠</span></p>
   <span class="zhiyin"></span>
</div>

<div class="xfloatbar">
    <ul class="xfloatitem">
        <li class="xcollect"><a rel="nofollow" class="icon" title="收藏" href="javascript:favorites();"></a></li>
        <li class="xsharebox"><a rel="nofollow" class="icon xshare" href="javascript:;"></a>
            <div class="xsharelink bdsharebuttonbox">
                <a href="#" class="icon xsharesina bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                <a href="#" class="icon xshareweibo bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
                <a href="#" class="icon xshareqzone bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
            </div>
        </li>
        <li class="xfeed"><a target="_blank" rel="nofollow" class="icon" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do" title="意见反馈"></a></li>
        <li class="xgotop"><a rel="nofollow" class="icon" href="javascript:;" title="返回顶部"></a></li>
    </ul>
</div>

<div class="wrap" style="margin-top:20px;"><a href="http://www.lvmama.com/public/user_security#mp" target="_blank"><img src="http://pic.lvmama.com/img/v5/lybz_banner.gif" width="1000" height="50"></a></div> 


<!--公共底部-->
<script src="http://pic.lvmama.com/js/v4/copyright.js"></script>

   <div class="hh_cooperate">
  <#include "/WEB-INF/pages/common/footer.ftl"/>
   <p> 
      <@s.property value='scenicVo.seoPublicContent' escape="false"/>
   </p>
   
   <@s.if test="null!=scenicVo.seoList && scenicVo.seoList.size()>0">
   <p><b>友情链接：</b>
   <span>
       <@s.iterator value="scenicVo.seoList" var ="v" status="st">
          <a target="_blank" href="${linkUrl?if_exists }">${linkName?if_exists }</a>
       </@s.iterator>
   </span>
   </p>
 </@s.if>
  </div>


<!-- 频道公用js-->
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js,/js/v4/login/rapidLogin.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/placeholder.js,/js/v5/modules/pandora-poptip.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/bt-scrollspy.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/dest.js"></script>
<script src="/js/dest.js"></script>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdPic":"","bdStyle":"0","bdSize":"16"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script> 
<script type="text/javascript">

    $(function(){
        $.ajax({
            url:"http://ticket.lvmama.com/newplace/getCommentCount.do",
            data:{
                placeId :<@s.property value="place.placeId"/>
            },
            dataType:'json',
            success:function(data) {
                $("#experienceCommentCount").html("(" + data.experienceCommentCount + ")");
                $("#isBestCommentCount").html("(" + data.isBestCommentCount + ")"); 
                $("#commonCommentCount").html("(" + data.commonCommentCount + ")");
                $("#totalCmt").html( "("+(parseInt(data.experienceCommentCount) + parseInt(data.commonCommentCount))+")"); 
                 $("#totalCmt_1").html(parseInt(data.experienceCommentCount) + parseInt(data.commonCommentCount)); 
            }

        });
         loadExperienceComment(1,'Defalut');
        
         loadBestComment(1,'Defalut');
        
         loadCommonComment(1,'Defalut');  
         
         $(".dside-guide").hide();
          //攻略
		$.getJSON("http://www.lvmama.com/guide/ajax/api.php?action=getOrgInfo&id=<@s.property value="place.placeId"/>&callback=?", function(result){
			if (result.data.title) {
				var content ="<div class=\"stitle\">";
				    content+="<h4 class=\"stit\">攻略随身带</h4>";
					content+="</div>";
				    content+="<div class=\"scontent\">";
				    content+="<dl class=\"dl-hor\">";
			     	content+="<dt><a href=\"" + result.data.pdfurl + "\" class=\"hot_pic\"> <img src=\"" + result.data.thumb + "\" width=\"90\" height=\"125\" /></a> </dt>";
				    content+="<dd>";
				    content+="<h5><a href=\""+ result.data.pdfurl +"\"> "+result.data.title +" </a> </h5>";
			    	content+="<p>官方攻略2014版</p>";
					content+="<p class=\"gray\">"+result.data.downs+"人下载</p>";
					content+="<a href=\""+  result.data.pdfurl+ "\" class=\"btn btn-mini btn-orange\">下载攻略</a>";
					content+="</dd>";
					content+="</dl>";
   				    content+="</div>";
				$(".dside-guide").html(content);
				$(".dside-guide").show();
			}
  		});
    
    });
    
    var _flag = 0;
		function favorites(){ 
			  $.ajax({
            type: "get",
            url: "http://ticket.lvmama.com/check/login.do",
            dataType:"html",
          success: function(data){
			if(data == "true"){
				$.ajax({
				type: "get",
            	async:false,
            	dataType : "jsonp",
            	jsonp: "jsoncallback",
            	jsonpCallback:"success_jsonpCallback", 
				url:"http://www.lvmama.com/myspace/share/ticketFavorite.do",
				data:{ 
				objectId :<@s.property value="place.placeId"/>, 
				<@s.if test="scenicVo.placePhoto!=null"> 
				objectImageUrl : '<@s.property value="scenicVo.placePhoto.get(0).imagesUrl"/>', 
				</@s.if> 
				objectName :'<@s.property value="place.name"/>' 
				}, 
				success : function(json){
					if(json.success == "true"){ 
						alert("收藏成功"); 
					}else if(json.success == "false"){ 
						alert("已经收藏过此景点"); 
					} 
				},error : function(){ 
				alert("收藏失败"); 
				} 
				 }); 
			}else{
				_flag = 1;
				$(UI).ui("login");
			}
		  }
		});
		}
    
    /***/
    function loadExperienceComment(currentPage,defalut) {
        loadPaginationOfComment($("#experienceComment"), currentPage, 'EXPERIENCE','',defalut);
    }
    
    function loadBestComment(currentPage,defalut) {
        loadPaginationOfComment($("#bestComment"), currentPage, '','Y',defalut);
    }       
        
    function loadCommonComment(currentPage,defalut) {
        loadPaginationOfComment($("#commonComment"), currentPage, 'COMMON','',defalut);
    }

    function loadPaginationOfComment(obj, currentPage, cmtType, isBest,defalut) {
        $.ajax({
            url:"http://ticket.lvmama.com/newplace/paginationOfCommentsNew.do",
            data:{
                placeId :<@s.property value="place.placeId"/>,
                startRow:(currentPage - 1) * 5,
                cmtType:cmtType,
                isBest:isBest
            },
            dataType:'html',
            success:function(data) {
                obj.html(data);
            }
        });     
        
    }
        function addUsefulCount(varUsefulCount,varCommentId,obj) {
        $.ajax({
            type: "get",
            async:false,
            dataType : "jsonp",
            jsonp: "jsoncallback",
            jsonpCallback:"success_jsonpCallback",
            url: "http://www.lvmama.com/comment/ajax/addUsefulCountOfCmt.do",
            data:{
                commentId: varCommentId
            },
             success: function(jsonList, textStatus){
                 if(!jsonList.result){
                   alert("已经点过一次");
                 }else{
                    var newUsefulCount = varUsefulCount + 1;
                    $("#userfulCount_" + varCommentId).html("<i class=\"icon dicon-plus\"></i> <em>(" + newUsefulCount + ")</em>") ;
                 }
            }
        });
    }
    function reply(commentId) {
        if ($("#newReplyContent_" + commentId).val() == "") {
            alert('请输入需要回复的内容!');
            $("#newReplyContent_" + commentId).focus();
            return;
        }
        $.ajax({
            type: "get",
            url: "http://ticket.lvmama.com/check/login.do",
            dataType:"html",
            success: function(data){
                 if(data == "true"){
		                    $.ajax({
		                        type: "get",
					            async:false,
					            dataType : "jsonp",
					            jsonp: "jsoncallback",
					            jsonpCallback:"success_jsonpCallback",
		                        url: "http://www.lvmama.com/comment/ajax/addReply.do",
		                        data:{
		                            commentId: commentId,
		                            content:$("#newReplyContent_" + commentId).val()
		                        },
		                         success: function(data){
		                             if(data.success){
		                                alert("您的回复已经发布成功，请等待审核！");
		                                $("#newReplyContent_" + commentId).val("");
		                             }else{
		                                alert("您的回复发布失败，请重新尝试!");    
		                             }
		                        }
		                    });                
                 }else{
 	                    showLogin(function(){window.location.href="http://ticket.lvmama.com/scenic-"+'${place.placeId!?c}'+"#comments";window.location.reload();});
	
                 }
            }
        });
    }
    
    function showCompleteData(commentId) {
		var content = $("#cmtContent_" + commentId).attr("complete-data");
		$("#cmtContent_" + commentId).html(content);
	}
	 $(function(){
		$('.js_zhankai').live('click',function(){
			$(this).parent().html($(this).parent().attr("complete-data"));
		})
	})
  </script>
    <script>
      cmCreatePageviewTag("新版景点产品详情页_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(place.name)" escape="false"/>"+"_无商品", "M1004", null, null);
  </script>
</body>
</html>
