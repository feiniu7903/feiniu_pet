<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> 
<title>邮件订阅</title>
<link href="http://pic.lvmama.com/styles/v7style/globalV1_0.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/min/index.php?g=commonCss" type="text/css" rel="stylesheet"/>
<link href="http://pic.lvmama.com/styles/rss.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="http://pic.lvmama.com/img/oldlvmama/img/favicon.ico" />
<script type="text/javascript" src=http://pic.lvmama.com/js/oldlvmama/js/common/jquery.js></script>
<#include "/common/commonJsIncluedTop.ftl">	
</head>

<body>
<#include "/common/setKeywor2.ftl">   
<script type="text/javascript" src="http://pic.lvmama.com/js/oldlvmama/js/buy/subscribeMail.js"></script>
<div class="rssbg">
<div class="rssmain">
<h2>邮件订阅</h2>
<form>
<input name="subscribeType" value="<@s.property value="subscribeType"/>"  id="subscribeType" type="hidden" />
<input name="sendMail"  id="sendMail" type="text" onBlur="btn_mail_onBlur(this)"  onfocus="btn_clear(this)" value="输入Email随时掌握特价信息" />
<a><img src="http://pic.lvmama.com/img/index1008/rssbtn.gif" onclick="btn_sendMail()"/></a>
<br/><font color="red"><div id="errorMsg"></div></font>
<div id="div_code" style="display:none">
<input type="text" size="8" id="vCode"  style="width:60px;height:24px;" />
<img id="image" src="http://www.lvmama.com/account/checkcode.htm" />
<a onclick="refresh()" style="cursor: hand;">看不清楚</a>
</div>
</form>
<ul>
<li>*此服务可以随时取消</li>
<li>*请放心，我们和您一样讨厌垃圾邮件</li>
<li>*邮件订阅内容包括：实时可信的景点打折门票信息、精品团购等</li>
</ul>
</div>
</div>
<#include "/common/footer.ftl">
<script src="http://pic.lvmama.com/min/index.php?g=common" type="text/javascript" charset="utf-8"></script>
</body>
</html>
