<!DOCTYPE html> 
<html>
<head>
<meta charset="utf-8"> 
<title></title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/base.css?r=8686" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/c_common.css?r=8690" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/common.css?r=8515" rel="stylesheet" />
 
<script src="http://pic.lvmama.com/js/jquery142.js?r=8420" type="text/javascript"></script>
<script>
$(function(){
//依据每个iframeId中显示的记录数，设置3个iframeId的高度 
    
	$s1=$("#s1").attr("data");
	$s2=$("#s2").attr("data"); 
	$s3=$("#s3").attr("data");
	var $sa1=parseInt($s1)*198;
	var $sa2=parseInt($s2)*198;
	var $sa3=parseInt($s3)*198;
	if($sa1>0){$("#iframeId1",window.parent.document).height($sa1 + 35); }
	if($sa2>0){$("#iframeId2",window.parent.document).height($sa2 + 35); }
    if($sa3>0){$("#iframeId3",window.parent.document).height($sa3 + 35); }
  
	window.parent.scrollTo(0,135);  
});
</script>
</head>
<body>
<div id="s1" data="${allCommentsCurrentRowNum}"></div>
<div id="s2" data="${bestCmtsCurrentRowNum}"></div>
<div id="s3" data="${experienceCmtsCurrentRowNum}"></div>

		<div class="u_comment c_w_comment">
			<#include "/WEB-INF/pages/comment/dest/listCmtsOfDestCommon.ftl" />
		</div>

</body>
</html>
