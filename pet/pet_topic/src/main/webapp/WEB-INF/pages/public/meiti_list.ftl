<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>帮助中心-关于我们</title><link charset="utf-8" media="screen" type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss">
<link href="http://pic.lvmama.com/styles/oldlvmama/style/global.css" type="text/css" rel="stylesheet"  />
<link href="http://pic.lvmama.com/styles/oldlvmama/style/layout.css" type="text/css" rel="stylesheet"  />
<link rel="shortcut icon" href="http://pic.lvmama.com/img/oldlvmama/img/favicon.ico" />
<script type="text/javascript" src="http://pic.lvmama.com/js/oldlvmama/js/common/jquery.js"></script>
<#include "/common/commonJsIncluedTop.ftl">	
</head>
<body>
<div id="container">
  <#include "/common/setKeywor2.ftl">  
  <div id="content">
  <div id="menu">
  <div class="faq_box">
          <div class="title"><a href="http://www.lvmama.com/public/help">帮助中心</a></div>
  </div>
  <div class="faq_box">
          <div class="title">常见问题</div>
          <div class="detail">
          <ul>
          	<li>· <a href="/public/reg_and_login">注册、登录及常见问题</a></li>
          	<li>· <a href="/public/buy_guide">订购流程及常见问题</a></li>
          </ul>
          </div>
        </div>
        <div class="faq_box">
          <div class="detail">
          <ul>
            <li><strong>客服中心</strong></li>
          	<li>服务时间：8:00－24：00 周一至周日</li>
			<li>客服热线：<span>1010-6060</span></li>
            <li>客服传真：021-69108793</li>
			<li>客服邮箱：<a href="mailto:service@lvmama.com">service@lvmama.com</a></li>
          </ul>
          
          
          <ul>
            <li><strong>顾客投诉</strong></li>
          	<li>Email：<a href="mailto:service@lvmama.com">service@lvmama.com</a></li>
			<li>投诉专线：<span>1010-6060转3</span></li>
          </ul>
          </div>
        </div>
  </div>
  <div id="faq_area">
  <div class="faq_title">媒体报道</div>
  <div class="faq_detail">
    <div style="line-height:25px;">
          <ul style="padding-top:10px;">
            <@s.iterator value="noticeList">
            <li>·  <a href="http://www.lvmama.com/news/new/<@s.property value='noticeId'/>.htm" target="_blank"><@s.property value='title'/></a></li>
            </@s.iterator>
           </ul>
          </div>
          <div id="n_fenye"><@l.splitPage  pageSize=2 totalPage=pagination.totalPages action=pagination.actionUrl mode=10
			page=pagination.page></@l.splitPage></div>
			</div>

  </div>
  </div>
  </div>
  
<#include "/common/footer.ftl">
</div>
<script src="http://pic.lvmama.com/min/index.php?g=common" type="text/javascript" charset="utf-8"></script>
</body>
</html>

