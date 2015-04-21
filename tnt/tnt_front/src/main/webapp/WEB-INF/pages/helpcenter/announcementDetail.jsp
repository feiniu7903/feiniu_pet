<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>平台公告</title>
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<h3>平台公告</h3>  
    <div class="rightBox notice_detail">
        <h1>${tntAnnouncement.title}</h1>
        <div class="text_time">时间：${tntAnnouncement.formatdate}</div>
        <div class="notice_detail_main">
            <p>${tntAnnouncement.body}</p>
        </div>
    </div>
</body>
</html>
