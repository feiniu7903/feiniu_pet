<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
</head>
<body id="body_index">
<jsp:include page="../common/head.jsp"></jsp:include>
<div>
  <div class="breadcrumb">
		<ul class="breadcrumb_nav">
			<li class="home">首页</li>
	    </ul>
	</div>
	
	<div class="snSpt_mainBox">
        <div class="snSpt_indexBox snSpt_fl">
             <em class="snSpt_indexBox_tit">今日概况</em>
             <ul class="snSpt_todaylist">
                 <li>今日订购票数：<i>${tongJI[0]} </i></li>
                 <li>今日预计游玩人数：<i>${tongJI[3]}</i></li>
                 <li>今日已取票数：<i>${tongJI[1]}</i></li>
                 <li>今日已游玩人数：<i>${tongJI[4]}</i></li>
                 <li>今日待取票数：<i>${tongJI[2]}</i></li>
                 <li>今日未游玩人数：<i>${tongJI[5]}</i></li>
             </ul>
        </div><!--snSpt_indexBox-->
       
         <div class="snSpt_indexBox snSpt_fr">
             <em class="snSpt_indexBox_tit">
             	重要公告 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="${contextPath }/announcement/ebkAnnouncementList.do">更多>></a>
             </em>
             <ul class="snSpt_postlist">
                 <s:iterator value="#request.announceList" var="item">
				 	<li>
				 		<span>[<s:date name="beginDate" format="yyyy-MM-dd"/>]</span> 
				 		<a href="${contextPath }/announcement/ebkAnnouncementList.do">${item.title}</a>
				 	</li>
				 </s:iterator>
             </ul>
        </div><!--snSpt_indexBox-->
    </div><!--snspt_mainbox-->
    <!--footer-->
</div><!--wrap-->
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>