<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>点评成功页</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="http://pic.lvmama.com/styles/new_v/header-air.css" type="text/css" rel="stylesheet"/>
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/base.css" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/common.css" rel="stylesheet" />
</head>
<body class="lvcomment">

<!--头部开始-->
<#include "/common/header.ftl">
<!--页面主体部分-->
<div id="content" class="cArea cArea_padd clearfix"> 

  <!--页面侧栏部分开始(点评招募)-->
  <div class="c_aside fr">
    	<#include "/WEB-INF/pages/comment/cmtRecommentPlace.ftl">
  </div>
  
  <!--页面主体部分-->
  <div class="c_w_com c_shadow">
          <div class="c_main"> 
            <div class="reback_box">
            
              <h2 class="yahei f24"><span class="cxh_ico5"></span>您的点评已发表成功！</h2>
              <div class="c_suc_box clearfix"><p class="tc lh24">为了保证内容质量，我们会对点评进行审核，一般会在1个工作日内完成。<br>
        		您的点评被审核通过后，将会获得<b>50-100</b>积分的奖励，被加为精华将再获得<b>150</b>积分以上的奖励。<br>
			您可以进入“<a href="http://www.lvmama.com/myspace/share/alreadyComment.do?currentNeedProductCommentPage=1&currentAlreadyCommentPage=1">我的驴妈妈—我的点评</a>”中，查看点评审核状态，积分赠送状态，点评奖金状态。</p>
        <p class="c_suc_btn">
	        <#if place??><a id="xhsharelink" title="${place.name}" href="http://www.lvmama.com/comment/${commentId}"><span>查看点评</span></a></#if> 
	        <#if product??><a id="xhsharelink" title="${product.productName}" href="http://www.lvmama.com/comment/${commentId}"><span>查看点评</span></a></#if>
	        <a href="http://www.lvmama.com/myspace/share/comment.do"><span>继续点评(${needProductCommentCount})</span></a>
	        <a href="/comment"><span>频道首页</span></a>
        </p>
        <div class="c_suc_share bc clearfix">
        <!-- JiaThis Button BEGIN -->
        <div id="ckepop">
            <a class="jiathis_button_tsina"></a>
            <a class="jiathis_button_tqq"></a>
            <a class="jiathis_button_kaixin001"></a>
            <a class="jiathis_button_qzone"></a>
            <a class="jiathis_button_renren"></a>
            <a class="jiathis_button_douban"></a>
            <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
            <a class="jiathis_counter_style"></a>
        </div>
        
        <!-- JiaThis Button END --></div>
        </div>
       
       <div class="codeapp_box">
            <span class="codeapp">
                   <img class="erweima"  src='/callback/generateCodeImage.do?userid=<@s.property value="users.userNo" />' />
            </span>
      </div>
        
            </div>
          </div><!--c_main end-->
  
  		<#if cmtActivity??>
   		 <a href="${cmtActivity.picUrl}" class="c_suc_link" target="_blank">
		    	<img src="${cmtActivity.absolutePictureUrl}" >
		 </a>
		</#if>
</div>
<!--footer>>-->
<#include "/WEB-INF/pages/comment/generalComment/commentListFooter.ftl" />
<!--footer<<-->

<script src="http://pic.lvmama.com/min/index.php?g=common" charset="utf-8"></script>
<script src="http://pic.lvmama.com/js/new_v/ob_comment/x_comment.js"></script>
<script src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
<script type="text/javascript" >
var xhsharelink = document.getElementById('xhsharelink');
var jiathis_config={
    url: xhsharelink.href,
    title: "我在驴妈妈旅游网对" + xhsharelink.title + "进行了点评，",
    summary: "还送了积分和返现！求围观！#分享真实感受 说说旅行那点事儿#",
	hideMore: false
}
</script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
