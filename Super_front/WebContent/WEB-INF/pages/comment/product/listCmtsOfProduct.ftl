<!DOCTYPE html> 
<html>
<head>
<meta charset="utf-8"> 
<title>${cmtTitleStatisticsVO.titleName}点评-驴妈妈旅游网</title>
<meta name="keywords" content="${cmtTitleStatisticsVO.titleName},点评" />
<meta name="description" content="驴妈妈旅游网提供关于${cmtTitleStatisticsVO.titleName}点评等介绍指南.来自游客最真实的体验,游玩点评." />
<link href="http://pic.lvmama.com/styles/new_v/header-air.css?r=8681" type="text/css" rel="stylesheet"/>

<link href="http://pic.lvmama.com/styles/new_v/ob_comment/base.css" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/c_common.css" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/common.css" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ui_plugin/lmmcomplete.css" rel="stylesheet"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/v3/buttons.css">
<script src="http://pic.lvmama.com/min/index.php?g=common" charset="utf-8"></script>
</head>
<body>
<!--头部开始-->
<#include "/common/header.ftl">
<div class="container clearfix">
<!--left_section_began>>-->
	<div class="c_list_lt">
        <div class="c_w_com c_shadow">
            <div class="c_w_tit">
            <h4 class="c_line_tit"><a href="http://www.lvmama.com/product/${productId}" target="_blank">${cmtTitleStatisticsVO.titleName}</a></h4>
            	<div class="w_330 mt10">
                    <div class="c_w_score">
                         <p class="c_p_link"><span class="com_StarValueCon total_val_posi">
                         <@s.if test="cmtTitleStatisticsVO != null && cmtTitleStatisticsVO.commentCount!=0">
                          <font><em>#{cmtTitleStatisticsVO.avgScore;m1M1}</em>分</font><s class="star_bg cur_def"><i class="ct_Star${cmtTitleStatisticsVO.roundHalfUpOfAvgScore}"></i></s></span>
                         </@s.if>
                         <@s.else>
                           <font><em>0</em>分</font><s class="star_bg cur_def"><i class="ct_Star0"></i></s></span>
                         </@s.else>
                         <@s.if test="cmtTitleStatisticsVO != null">
                            <a href="http://www.lvmama.com/product/${productId}/comment">${cmtTitleStatisticsVO.commentCount}封点评</a>
                         </@s.if>
                         <@s.else>
                           <span>0封点评</span>
                         </@s.else> 
                         
                         </p>
                         <!--点评平均分维度统计开始-->
  	                     <#include "/WEB-INF/pages/comment/cmtAvgLatitudeStatisticsInfo.ftl">
                    </div>
                </div><!--fl end-->
            	<div class="c_w_comtips fr">
                    <p>游玩后发表点评，返相应奖金，赠100积分，精华点评追加150积分！<a href="http://www.lvmama.com/public/help_center_146" target="_blank">规则详情&gt;&gt;</a></p>
                    <p>我购买过该产品，<a href="http://www.lvmama.com/myspace/share/comment.do" class="btn btn-small btn-pink white" target="_blank">发点评返奖金</a></p>
              </div>
            </div>
        </div><!--c_w_com end-->

        <div class="c_w_com c_shadow">
        	<div class="c_w_nav c_alist_nav">
            <ul class="JS_nav"><li class="c_w_navcur">产品点评(<span>${cmtTitleStatisticsVO.commentCount}</span>)<i>◆</i></li><li>相关点评(<span id="relateCommentCount">0</span>)<i>◆</i></li></ul>
            <a href="http://www.lvmama.com/public/help_center_222" class="">什么是体验点评？</a>
            </div><!--c_w_nav end-->
             <div id="JS_tab_switch">
              <!----- 产品点评 ----->
              <div>
  			  <Iframe src="/comment/getProductList.do?productId=${productId}" id="iframeId"   width="100%"  scrolling="no" frameborder="0"></iframe> 

              </div>
              <!----- 相关产品点评 ----->
              <div class="undis">
  			  <iframe src="/comment/getRelateProductList.do" id="relateProductCommentFrame" width="100%"  scrolling="no" frameborder="0"></iframe>

  			  </div>
            </div><!--JS_tab_switch end-->
          
        </div><!--c_w_com end-->

    </div><!--c_list_lt end-->
<!--right_section_began>>-->
    <div class="container_rt">
<!--Hot line began>>-->      
          <div class="hotL_box c_price">
            <div class="ca_title clearfix">
              <h3 class="fl">猜你喜欢</h3>
              <small></small></div>
             <ul class="cL_hot B f14 lh22" id="guessYourFavorite">
      </ul>
          </div>
              
    </div><!--container_rt-->

</div><!--container-->

<div id="fb_tips" class="see_example"><div class="tips_inner"></div>
  <h4>驴妈妈会员点评举例</h4>
  <p>景点：千岛湖<br/>内容：外界盛传千岛湖的景美如天堂，有幸一见，果不其然。搭着酒店的游艇在湖中心游览了一会，碧水蓝天，一尘不染，远远得看到梅峰观岛，形如梅花，这也许正是名字的由来。同事们各随其愿，有的选择步行到达观景台，有的选择乘缆车观全景，缆车收费，来回40元/人。步行用时并不多，一路拾阶而上，两旁绿树成荫，虽说低头看路抬头看树，但也难得做一回山林野人了。到达观景台，世界都安静了，被眼前的一切所折服，该用什么语言来表达那时的心情呢，壮阔的美，静谧和谐，偶尔也有闯入画中的小船和天空自由飞翔的鸟儿……也难怪连香港电影都会选择这里取景了。下山选择去坐了导游介绍的“滑草”，20元每人。说实话，刚开始见到滑草本尊，着实被雷到了，心想，这次被骗惨了。草是那种塑料做的，共三条滑道，工作人员把每个人放进一个超大的盆里，开始数一二三你就下去了。当我坐进那个盆里的时候，我竟然有点害怕了，工作人员一放手瞬间就滑了起来，速度之快，与过山车有得一拼，另我措手不及。此游乐设施不可貌相。好在下山还有一小段路，可以用来平复一下心情，一路走走停停拍拍照，将这无边的美景带回。  
  </p>
  <b class="close"></b>
</div>

<div id="ty_tips" class="see_example"><div class="tips_inner"></div>
  <h4>什么是体验点评？</h4>
  <p>体验点评是指，在驴妈妈购买了相关产品的用户，所发表的点评 。</p>
  <b class="close"></b>
</div>

<div id="jh_tips" class="see_example"><div class="tips_inner"></div>
  <h4>什么是精华点评？</h4>
  <p>点评不少300字，内容有较强感染力和代表性，具有推荐性。注：点评被加精后 ，你将再获得 150 积分。</p>
  <b class="close"></b>
</div>
<div id="c_overlay"></div>

<!--footer>>-->
<#include "/WEB-INF/pages/comment/generalComment/commentListFooter.ftl" />
<!--footer<<-->

<script src="http://pic.lvmama.com/js/new_v/ob_comment/x_comment.js?r=8683"></script>
<!--script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js?r=8673"></script-->
</body>
</html>
