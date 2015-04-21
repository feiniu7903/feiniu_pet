<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>回复成功页</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="http://pic.lvmama.com/styles/new_v/header-air.css?r=8681" type="text/css" rel="stylesheet"/>

<link href="http://pic.lvmama.com/styles/new_v/ob_comment/base.css?r=8686" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/common.css?r=8515" rel="stylesheet" />
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
            
              <h2 class="yahei f24"><span class="cxh_ico5"></span>您的回复已发表成功！等待审核.</h2>
              <div class="c_suc_box clearfix"> 
       			 <p class="c_suc_btn">
			        <a href="/comment/${commentId}"><span>查看点评</span></a>
			        <a href="/comment"><span>频道首页</span></a>
		        </p>
        <div class="c_suc_share bc clearfix">
        
        <!-- JiaThis Button END --></div>
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
<script src="http://pic.lvmama.com/js/new_v/ob_comment/x_comment.js?r=8683"></script>
<script src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
<script type="text/javascript" >
var jiathis_config={
	summary:"我在驴妈妈旅游网发现了一篇实用又有趣儿的驴友点评，速速围观！#分享真实感受 说说旅行那点事儿#",
	hideMore:false
}
</script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
