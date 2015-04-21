<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8" />
	<title>点评发表成功</title>
</head>
<link href="http://pic.lvmama.com/styles/zt/phb/phb_write_cmt.css?r=3332" rel="stylesheet" type="text/css" />

<body>
	<div id="com_suc">
  		<div class="suc_hz">
  			<input type="hidden" id="targetUrl" value="../comment/writeComment/fillComment.do?placeId=${placeId}" />
  			
        	<p class="succ">点评发表成功</p>
            <p class="view">刚刚发表的点评，请点<a href="../comment/${commentId}" target="_blank" class="hereurl">这里</a></p>
            <p class="continue">
            	<a href="javascript:void(0)" id="returnUrl">继续点评</a>
          	  	<a href="../comment/" target="_blank" >返回首页</a>
            </p>
        </div>
        <p class="copy"><em>这是您的点评链接，请通过MSN或QQ发送给您的好友</em></p>
        <p class="copy_bg" id="copy_bg">
	        <input class="co_input"  id="url_text" type="text" value="http://www.lvmama.com/comment/${commentId}"/>
	        <input  class="co_but" type="button"  id="co_but" value="复制链接"/>
        </p>
        
        <a class="close" id="close"><img src="http://pic.lvmama.com/img/zt/phb/close.gif" height="15" width="15" /></a>
 </div>
	
<script src="http://pic.lvmama.com/js/jquery142.js?r=8420" type="text/javascript" ></script> 
<#include "/WEB-INF/comment/jsResource/phb/writeSimpleCmtSucceed_js.ftl" />

</body>
</html>
