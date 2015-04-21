<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8" />
	<title></title>
   <link href="http://pic.lvmama.com/styles/zt/phb/phb_write_cmt.css?r=3332" rel="stylesheet" type="text/css" />
</head>
<body>
		
 <!--我要点评弹出层--> 
   <div id="comment">
		<form id="myForm" action="" method="POST" enctype="multipart/form-data">
		<input type="hidden" id="targetUrl" value="${targetUrl}" />
		<input type="hidden" name="placeId" value="${placeId}" />
        <h5><em>发表点评</em><span>(<font>*</font>为必填项)</span></h5>
        <p>
        	<span class="commentStarValueCon">
                <strong><font>*</font>总体评价：</strong>
                <i class="commentsStar4"> 
                <input type="hidden" name="latitudeIds" value="FFFFFFFFFFFFFFFFFFFFFFFFFFFF" />
				<input type="hidden" name="latitudeNames" value="总体点评" />
				<input type="hidden" name="scores" value="commentsStar4"/>
                <b title="差" data-star="commentsStar1">差</b> 
                <b title="一般" data-star="commentsStar2">一般</b> 
                <b title="好" data-star="commentsStar3">好</b> 
                <b title="较好" data-star="commentsStar4">较好</b> 
                <b title="非常好" data-star="commentsStar5" class="commentsStar5">非常好</b>                
                </i>
                <span class="commentStarValue">&nbsp;</span>
            </span><!--commentsStartValuesCon end-->
       </p>
        <p class="sort">
			<@s.iterator value="commentLatitudeList">			
        	<span class="commentStarValueCon">
                <strong><font>*</font>${name}：</strong>
                <i class="commentsStar4"> 
                <input type="hidden" name="latitudeIds" value="${latitudeId}" />
				<input type="hidden" name="latitudeNames" value="${name}" />
				<input type="hidden" name="scores" value="commentsStar4"/>
                <b title="差" data-star="commentsStar1">差</b> 
                <b title="一般" data-star="commentsStar2">一般</b> 
                <b title="好" data-star="commentsStar3">好</b> 
                <b title="较好" data-star="commentsStar4">较好</b> 
                <b title="非常好" data-star="commentsStar5" class="commentsStar5">非常好</b>
                </i>
                <span class="commentStarValue">&nbsp;</span>
             </span><!--commentsStartValuesCon end-->
             </@s.iterator>
             <!--commentsStartValuesCon end-->
       </p>
       <p>
       		<strong><font>*</font>点评内容：</strong><span class="text_num">[请不少于20字] 
       		&nbsp <i id="showContentLen" style="display: inline;font-size:12px;font-style: normal;"></i>
       		</span>
       </p>
       <p><textarea name="content"  id="content" class="commentsText"></textarea></p>
       <p><a class="submit" type="button"  value=""  id="submit" ></a></p>
       
       <a class="close" id="close"><img src="http://pic.lvmama.com/img/zt/phb/close.gif" height="15" width="15" /></a>
       </form>
   </div>
		
<script src="http://pic.lvmama.com/js/jquery142.js?r=8420" type="text/javascript" ></script> 
<script src="http://pic.lvmama.com/js/zt/phb/phb_cmt.js?r=3184" type="text/javascript" ></script>
<#include "/WEB-INF/comment/jsResource/phb/writeSimpleCmt_js.ftl" />

</body>
</html>
