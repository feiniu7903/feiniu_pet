<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>提交成功提示信息</title>

<link href="http://pic.lvmama.com/styles/v4style/myspace_fapiao.css?r=3252" rel="stylesheet" type="text/css" />
</head>

<body>


<#if invoiceResult>
<div class="suess">
	<a class="lianjie" href="${base}/usr/invoiceApplyList.do">查询发票申请</a>
</div>
<#else>
	<h2>${globalResult}</h2>
</#if>
</body>
</html>
