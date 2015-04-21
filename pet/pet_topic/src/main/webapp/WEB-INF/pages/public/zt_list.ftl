<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>旅游推荐页</title>
<link href="http://pic.lvmama.com/styles/oldlvmama/style/global.css" type="text/css" rel="stylesheet"  />
<link href="http://pic.lvmama.com/min/index.php?g=commonCss" type="text/css" rel="stylesheet"/>
<link href=http://pic.lvmama.com/styles/oldlvmama/style/layout.css type="text/css" rel="stylesheet"  />
<link rel="shortcut icon" href="http://pic.lvmama.com/img/oldlvmama/img/favicon.ico" />
<script type="text/javascript" src=http://pic.lvmama.com/js/oldlvmama/js/common/jquery.js></script>
<#include "/common/commonJsIncluedTop.ftl">	
</head>
<body>
<div id="container">
  <#include "/common/setKeywor2.ftl">     
  <div id="content">
<!--    <div id="navigation"><a href="#">首页</a> > <a href="#">旅行推荐</a> > 专题</div>
    <br />
    <div id="ticket_search_list_title"><div class="right_bg"> 
       
        <span class="ticket_choose"><a href="#" class="on">所有专题</a> <a href="#">本月新上线</a> <a href="#">即将结束</a></span>
        <span class="paixu"><a href="#">按折扣排序</a> <a href="#" class="down">按总价排序</a></span></div></div>
-->
<!--  专题列表 -->
  
	  <#include "/page/static_file/zt_list/zt_list.ftl">
  <!--  专题列表 -->   
    <!-- <div class="email">
     担心错过今后的精彩旅游专题吗？填写您的EMAIL，订阅驴妈妈每期旅游推荐信息！<input name="" type="text" /> <button>提交订阅</button>   
     </div>-->
    
  </div>
<#include "/common/footer.ftl">
</div>
<script src="http://pic.lvmama.com/min/index.php?g=common" type="text/javascript" charset="utf-8"></script>
</body>
</html>
