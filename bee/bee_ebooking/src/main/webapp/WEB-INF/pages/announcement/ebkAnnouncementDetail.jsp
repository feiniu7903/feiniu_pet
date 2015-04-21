<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
</head>
<body>
<div align="center">
	<div class="gg_box">
	    <h2 style="font-size: 25px;">${announcement.title}</h2>
		<span class="gg_riqi"><s:date name="#request.announcement.createTime" format="yyyy-MM-dd HH:mm" /></span>
		<div class="gonggao_main" style="display: block;">
	     		<p>${announcement.content}</p>
	     		<s:if test="#request.announcement.haveAttachment">
					<b>普通附件</b>
					<span class="c_gray"> （已通过杀毒引擎扫描）</span>
					<h6>
						${announcement.attachmentName}
					</h6>
					<a class="download"
						href="${basePath}announcement/downloadAnnouncementAttachment.do?attachment=${announcement.attachment}">下载</a>
				</s:if>
	    </div>
	</div>
</div>
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
